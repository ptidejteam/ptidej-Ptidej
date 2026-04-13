/*******************************************************************************
 * Copyright (c) 2001-2014 Yann-Gaël Guéhéneuc and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/old-licenses/gpl-2.0.html
 * 
 * Contributors:
 *     Yann-Gaël Guéhéneuc and others, see in file; API and its implementation
 ******************************************************************************/
package padl.creator.cppfile.eclipse.misc;

import java.io.File;
import java.io.FilenameFilter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.Writer;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import org.apache.commons.io.FileUtils;
import org.eclipse.core.runtime.adaptor.EclipseStarter;

import com.sun.jdi.Bootstrap;
import com.sun.jdi.VirtualMachine;
import com.sun.jdi.VirtualMachineManager;
import com.sun.jdi.connect.Connector.Argument;
import com.sun.jdi.connect.IllegalConnectorArgumentsException;
import com.sun.jdi.connect.LaunchingConnector;
import com.sun.jdi.connect.VMStartException;

import padl.cpp.kernel.impl.CPPFactoryEclipse;
import padl.creator.cppfile.eclipse.Common;
import padl.creator.cppfile.eclipse.plugin.actions.Launcher;
import padl.kernel.ICodeLevelModel;
import util.io.Files;
import util.io.OutputMonitor;
import util.io.ProxyConsole;

public final class EclipseCPPParserCaller {
	private static EclipseCPPParserCaller UniqueInstance;
	private static final String RUNTIME_LIBRARIES_PATH =
		"PADL Creator C++ (Eclipse) Helper/Runtime Libraries/";
	private static final String RUNTIME_PLUGINS_PATH =
		RUNTIME_LIBRARIES_PATH + "plugins/";
	private static final String PADL_RUNTIME_BUNDLE_NAME =
		"PADL_Creator_Cpp_Eclipse_1.0.0";

	public static EclipseCPPParserCaller getInstance() {
		if (EclipseCPPParserCaller.UniqueInstance == null) {
			EclipseCPPParserCaller.UniqueInstance = new EclipseCPPParserCaller();
		}
		return EclipseCPPParserCaller.UniqueInstance;
	}

	private static void addBundleByPrefix(
			final List<String> someBundleEntries,
			final File aPluginsDirectory,
			final String aBundlePrefix,
			final boolean shouldStart) {

		final File bundle = findNewestBundleByPrefix(aPluginsDirectory, aBundlePrefix);
		if (bundle != null) {
			final String suffix = shouldStart ? "@4:start" : "";
			final String entry = "reference:" + bundle.toURI().toString() + suffix;
			if (!someBundleEntries.contains(entry)) {
				someBundleEntries.add(entry);
			}
		}
	}

	private static File findNewestBundleByPrefix(
			final File aPluginsDirectory,
			final String aBundlePrefix) {

		final File[] bundles = aPluginsDirectory.listFiles(new FilenameFilter() {
			@Override
			public boolean accept(final File dir, final String name) {
				if (name == null || !name.startsWith(aBundlePrefix)) {
					return false;
				}
				if (name.endsWith(".jar")) {
					return true;
				}
				final File candidate = new File(dir, name);
				return candidate.isDirectory()
						&& new File(candidate, "META-INF/MANIFEST.MF").exists();
			}
		});
		if (bundles == null || bundles.length == 0) {
			return null;
		}
		Arrays.sort(bundles, (left, right) -> left.getName().compareTo(right.getName()));
		return bundles[bundles.length - 1];
	}

	private static void copyJarContentToDirectory(
			final File aJarFile,
			final File aDestinationDirectory) throws IOException {

		try (final JarFile jarFile = new JarFile(aJarFile)) {
			final Iterator<JarEntry> entries = jarFile.stream().iterator();
			while (entries.hasNext()) {
				final JarEntry entry = entries.next();
				final File destination = new File(aDestinationDirectory, entry.getName());
				if (entry.isDirectory()) {
					FileUtils.forceMkdir(destination);
				}
				else {
					FileUtils.forceMkdirParent(destination);
					try (final InputStream inputStream =
							jarFile.getInputStream(entry)) {
						FileUtils.copyInputStreamToFile(inputStream, destination);
					}
				}
			}
		}
	}

	private static File getCodeSourceLocation() throws IOException {
		try {
			return Paths
				.get(EclipseCPPParserCaller.class
					.getProtectionDomain()
					.getCodeSource()
					.getLocation()
					.toURI())
				.toFile();
		}
		catch (final URISyntaxException e) {
			throw new IOException(e);
		}
	}

	private static boolean isWorkspaceRoot(final String aCandidatePath) {
		if (aCandidatePath == null) {
			return false;
		}
		final File runtimeLibraries = new File(
			aCandidatePath + File.separator + RUNTIME_LIBRARIES_PATH);
		final File moduleLibraries = new File(
			aCandidatePath + File.separator + "PADL Creator C++ (Eclipse)/libs");
		return runtimeLibraries.isDirectory() && moduleLibraries.isDirectory();
	}

	private static String toWorkspacePath(final String aPath) {
		return aPath.replace('\\', '/') + '/';
	}

	private static void preparePadlRuntimeBundle(
			final String aPathToCurrentWorkspace,
			final File aPluginsDirectory) throws IOException {

		final File compiledClassesDirectory =
			new File(aPathToCurrentWorkspace + "PADL Creator C++ (Eclipse)/target/classes");
		final File runtimeBundleDirectory =
			new File(aPluginsDirectory, PADL_RUNTIME_BUNDLE_NAME);
		FileUtils.deleteQuietly(runtimeBundleDirectory);
		FileUtils.forceMkdir(runtimeBundleDirectory);
		if (compiledClassesDirectory.isDirectory()) {
			FileUtils.copyDirectory(compiledClassesDirectory, runtimeBundleDirectory);
		}
		else {
			final File codeSourceLocation = getCodeSourceLocation();
			if (codeSourceLocation.isDirectory()) {
				FileUtils.copyDirectory(codeSourceLocation, runtimeBundleDirectory);
			}
			else if (codeSourceLocation.isFile()
					&& codeSourceLocation.getName().endsWith(".jar")) {
				copyJarContentToDirectory(codeSourceLocation, runtimeBundleDirectory);
			}
			else {
				throw new IOException(
					"Cannot prepare runtime bundle, classes not found at "
						+ compiledClassesDirectory.getAbsolutePath());
			}
		}
		final File cplClassesDirectory =
			new File(aPathToCurrentWorkspace + "CPL/target/classes");
		if (cplClassesDirectory.isDirectory()) {
			FileUtils.copyDirectory(cplClassesDirectory, runtimeBundleDirectory);
		}
		final File padlCoreClassesDirectory =
			new File(aPathToCurrentWorkspace + "PADL/target/classes");
		if (padlCoreClassesDirectory.isDirectory()) {
			FileUtils.copyDirectory(padlCoreClassesDirectory, runtimeBundleDirectory);
		}
		final File padlStatementsClassesDirectory =
			new File(aPathToCurrentWorkspace + "PADL Statements/target/classes");
		if (padlStatementsClassesDirectory.isDirectory()) {
			FileUtils.copyDirectory(
				padlStatementsClassesDirectory, runtimeBundleDirectory);
		}

		final File pluginXml = new File(runtimeBundleDirectory, "plugin.xml");
		try (final Writer writer = new FileWriter(pluginXml)) {
			writer.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
			writer.write("<?eclipse version=\"3.4\"?>\n");
			writer.write("<plugin>\n");
			writer.write("   <extension\n");
			writer.write("         id=\"Launcher\"\n");
			writer.write("         point=\"org.eclipse.core.runtime.applications\">\n");
			writer.write("      <application>\n");
			writer.write(
				"         <run class=\"padl.creator.cppfile.eclipse.plugin.actions.Launcher\"/>\n");
			writer.write("      </application>\n");
			writer.write("   </extension>\n");
			writer.write("</plugin>\n");
		}

		final File manifestDirectory = new File(runtimeBundleDirectory, "META-INF");
		FileUtils.forceMkdir(manifestDirectory);
		final File manifest = new File(manifestDirectory, "MANIFEST.MF");
		try (final Writer writer = new FileWriter(manifest)) {
			writer.write("Manifest-Version: 1.0\n");
			writer.write("Bundle-ManifestVersion: 2\n");
			writer.write("Bundle-Name: PADL Creator Cpp Eclipse\n");
			writer.write("Bundle-SymbolicName: PADL_Creator_Cpp_Eclipse;singleton:=true\n");
			writer.write("Bundle-Version: 1.0.0\n");
			writer.write("Bundle-Activator: padl.creator.cppfile.eclipse.plugin.Activator\n");
			writer.write("Bundle-ActivationPolicy: lazy\n");
			writer.write("Bundle-ClassPath: .\n");
			writer.write("DynamicImport-Package: *\n");
		}
	}

	private static File prepareFrameworkBundle(
			final String aPathToCurrentWorkspace,
			final File aPluginsDirectory) throws IOException {

		final File preferredFramework =
			new File(aPathToCurrentWorkspace + "PADL Creator C++ (Eclipse)/libs/org.eclipse.osgi_3.15.jar");
		if (preferredFramework.isFile()) {
			final File target = new File(aPluginsDirectory, preferredFramework.getName());
			FileUtils.copyFile(preferredFramework, target);
			return target;
		}

		final File fallbackFramework =
			findNewestBundleByPrefix(aPluginsDirectory, "org.eclipse.osgi_");
		if (fallbackFramework == null) {
			throw new IOException(
				"Cannot find any org.eclipse.osgi framework bundle in "
						+ aPluginsDirectory.getAbsolutePath());
		}
		return fallbackFramework;
	}

	private static void writeGeneratedConfigIni(
			final String aPathToCurrentWorkspace,
			final File aConfigurationDirectory,
			final File aPluginsDirectory,
			final File aFrameworkBundle) throws IOException {

		final List<String> bundles = new ArrayList<String>();
		addBundleByPrefix(bundles, aPluginsDirectory, PADL_RUNTIME_BUNDLE_NAME, true);
		addBundleByPrefix(bundles, aPluginsDirectory, "org.eclipse.equinox.common_", true);
		addBundleByPrefix(bundles, aPluginsDirectory, "org.eclipse.equinox.registry_", true);
		addBundleByPrefix(bundles, aPluginsDirectory, "org.eclipse.equinox.preferences_", true);
		addBundleByPrefix(bundles, aPluginsDirectory, "org.eclipse.core.runtime_", true);
		addBundleByPrefix(bundles, aPluginsDirectory, "org.eclipse.equinox.app_", true);

		addBundleByPrefix(bundles, aPluginsDirectory, "org.eclipse.core.contenttype_", false);
		addBundleByPrefix(bundles, aPluginsDirectory, "org.eclipse.core.commands_", false);
		addBundleByPrefix(bundles, aPluginsDirectory, "org.eclipse.core.jobs_", false);
		addBundleByPrefix(bundles, aPluginsDirectory, "org.eclipse.core.resources_", false);
		addBundleByPrefix(bundles, aPluginsDirectory, "org.eclipse.core.filesystem_", false);
		addBundleByPrefix(bundles, aPluginsDirectory, "org.eclipse.core.expressions_", false);
		addBundleByPrefix(bundles, aPluginsDirectory, "org.eclipse.core.variables_", false);
		addBundleByPrefix(bundles, aPluginsDirectory, "org.eclipse.core.filebuffers_", false);
		addBundleByPrefix(bundles, aPluginsDirectory, "org.eclipse.osgi.services_", false);
		addBundleByPrefix(bundles, aPluginsDirectory, "org.eclipse.ltk.core.refactoring_", false);
		addBundleByPrefix(bundles, aPluginsDirectory, "org.eclipse.team.core_", false);
		addBundleByPrefix(bundles, aPluginsDirectory, "org.eclipse.text_", false);

		addBundleByPrefix(bundles, aPluginsDirectory, "org.eclipse.cdt.core_", false);
		addBundleByPrefix(bundles, aPluginsDirectory, "org.eclipse.cdt.core.win32_", false);
		addBundleByPrefix(bundles, aPluginsDirectory, "org.eclipse.cdt.core.win32.x86_64_", false);
		addBundleByPrefix(bundles, aPluginsDirectory, "org.eclipse.cdt.make.core_", false);

		addBundleByPrefix(bundles, aPluginsDirectory, "com.ibm.icu_", false);
		addBundleByPrefix(bundles, aPluginsDirectory, "javax.annotation_", false);
		addBundleByPrefix(bundles, aPluginsDirectory, "javax.inject_", false);
		addBundleByPrefix(bundles, aPluginsDirectory, "javax.xml_", false);
		addBundleByPrefix(bundles, aPluginsDirectory, "org.apache.commons.lang_", false);

		final File configIni =
			new File(aConfigurationDirectory, Common.EQUINOX_CONFIGURATION_CONFIG_FILE);
		final File runtimeLibrariesDirectory =
			new File(aPathToCurrentWorkspace + RUNTIME_LIBRARIES_PATH);
		final File runtimeWorkspaceDirectory =
			new File(aPathToCurrentWorkspace + Common.EQUINOX_RUNTIME_WORKSPACE);
		try (final Writer writer = new FileWriter(configIni)) {
			writer.write("osgi.install.area=" + runtimeLibrariesDirectory.toURI().toString());
			writer.write('\n');
			writer.write("osgi.configuration.cascaded=false\n");
			writer.write(
				"osgi.instance.area.default="
						+ runtimeWorkspaceDirectory.toURI().toString());
			writer.write('\n');
			writer.write("osgi.bundles=");
			for (int i = 0; i < bundles.size(); i++) {
				if (i > 0) {
					writer.write(',');
				}
				writer.write(bundles.get(i));
			}
			writer.write('\n');
			writer.write("osgi.bundles.defaultStartLevel=4\n");
			writer.write("osgi.framework=" + aFrameworkBundle.toURI().toString());
			writer.write('\n');
			writer.write("osgi.compatibility.bootdelegation=true\n");
			writer.write(
				"org.osgi.framework.bootdelegation="
						+ "com.thoughtworks.xstream.*\n");
			writer.write("osgi.parentClassloader=app\n");
			writer.write("org.osgi.framework.bundle.parent=app\n");
		}
	}

    private void configureOSGi(final String aPathToCurrentWorkspace) {
        try {
            final File cfgDir =
            	new File(aPathToCurrentWorkspace + Common.EQUINOX_CONFIGURATION_DATA);
            FileUtils.forceMkdir(cfgDir);

            final File pluginsDir =
            	new File(aPathToCurrentWorkspace + RUNTIME_PLUGINS_PATH);
            FileUtils.forceMkdir(pluginsDir);

            preparePadlRuntimeBundle(aPathToCurrentWorkspace, pluginsDir);
            final File frameworkBundle =
            	prepareFrameworkBundle(aPathToCurrentWorkspace, pluginsDir);

            // Always clean stale OSGi persisted state to avoid stale or corrupt registries.
            FileUtils.deleteQuietly(new File(cfgDir, "org.eclipse.osgi"));
            FileUtils.deleteQuietly(new File(cfgDir, "org.eclipse.core.runtime"));
            FileUtils.deleteQuietly(new File(cfgDir, "org.eclipse.equinox.app"));

            writeGeneratedConfigIni(
            	aPathToCurrentWorkspace, cfgDir, pluginsDir, frameworkBundle);
        } catch (final IOException e) {
            e.printStackTrace(ProxyConsole.getInstance().errorOutput());
            throw new RuntimeException(e);
        }
    }

    private static String detectOsArg() {
        final String osName = System.getProperty("os.name", "").toLowerCase();
        if (osName.contains("win")) return "win32";
        if (osName.contains("mac")) return "macosx";
        if (osName.contains("nux") || osName.contains("nix")) return "linux";
        return "win32"; // default
    }

    private static String detectWsArg(final String osArg) {
        if ("macosx".equals(osArg)) return "cocoa";
        if ("linux".equals(osArg)) return "gtk";
        return "win32";
    }

    private static String detectArchArg() {
        final String arch = System.getProperty("os.arch", "").toLowerCase();
        if (arch.contains("64") || arch.contains("amd64") || arch.contains("x86_64")) return "x86_64";
        if (arch.contains("aarch64")) return "aarch64";
        return "x86";
    }

    private static String joinJarPaths(final File dir) {
        if (dir == null || !dir.isDirectory()) return "";
        final String sep = System.getProperty("path.separator");
        final StringBuilder sb = new StringBuilder();
        final File[] jars = dir.listFiles(new FilenameFilter() {
            @Override public boolean accept(File d, String name) {
                return name != null && name.toLowerCase().endsWith(".jar");
            }
        });
        if (jars != null) {
            for (final File j : jars) {
                if (sb.length() > 0) sb.append(sep);
                sb.append(j.getAbsolutePath());
            }
        }
        return sb.toString();
    }

    private static String buildAdditionalClasspath(final String workspaceRoot) {
        // workspaceRoot ends with '/'
        final String libsPath = workspaceRoot + "PADL Creator C++ (Eclipse)/libs/";
        final String pluginsPath = workspaceRoot + "PADL Creator C++ (Eclipse) Helper/Runtime Libraries/plugins/";
        final String libs = joinJarPaths(new File(libsPath));
        final String plugins = joinJarPaths(new File(pluginsPath));
        final String sep = System.getProperty("path.separator");
        final StringBuilder sb = new StringBuilder();
        if (!libs.isEmpty()) sb.append(libs);
        if (!plugins.isEmpty()) {
            if (sb.length() > 0) sb.append(sep);
            sb.append(plugins);
        }
        return sb.toString();
    }

	public void createCodeLevelModelUsingOSGiEmbedded(
			final String aRootDirectoryContainingCPPFiles,
			final ICodeLevelModel aCodeLevelModel) {

		final String pathToCurrentWorkspace = this.getPathToCurrentWorkspace();
		// Prepare serialisation area in case we need to fall back
		Common.prepareForCodeLevelModel();

        final List<String> argsList = new ArrayList<String>();
        argsList.add("-application");
        argsList.add(Common.EQUINOX_LAUNCHER_NAME);
        argsList.add("-clean");
        argsList.add("-consoleLog");
        argsList.add("-data");
        argsList.add(new File(pathToCurrentWorkspace + Common.EQUINOX_RUNTIME_WORKSPACE).getAbsolutePath());
        argsList.add("-configuration");
        argsList.add(new File(pathToCurrentWorkspace + Common.EQUINOX_CONFIGURATION_DATA).toURI().toString());
        // Only add -dev if dev.properties exists
        final File devProps = new File(pathToCurrentWorkspace + Common.EQUINOX_CONFIGURATION_DATA + "dev.properties");
        if (devProps.exists()) {
            argsList.add("-dev");
            argsList.add(devProps.toURI().toString());
        }
        argsList.add(Common.ARGUMENT_DIRECTORY_TARGET_CPP_FILES + "=" + aRootDirectoryContainingCPPFiles);
        argsList.add(Common.ARGUMENT_DIRECTORY_PTIDEJ_WORKSPACE + "=" + pathToCurrentWorkspace);
        // Force the launcher to serialise the model, so we can read it back if
        // EclipseStarter.run does not return the object in-process
        argsList.add(Common.ARGUMENT_OSGi_RUNNING_IN_REMOTE_JVM + "=" + true);

        final String[] startupArgs = argsList.toArray(new String[0]);

		this.configureOSGi(pathToCurrentWorkspace);

		try {
			// I cannot cast here because the returned object has been
			// loaded by the class loader(s) in OSGi... so it is not
			// cast-compatible with any type available here...
			//	codeLevelModel = (ICodeLevelModel) 
			//	codeLevelModel.moveIn(aCodeLevelModel);
			// To allow compatibility, I must change the config.ini to
			// tell OSGi that everything "PADL" will be provided by
			// the JVM class-loader and should not be loaded only by
			// its own class-loader(s). See the config.ini file.
				final Object result = EclipseStarter.run(startupArgs, null);
				ProxyConsole.getInstance().debugOutput().println(
					"EclipseStarter.run(...) returned: "
						+ (result == null ? "null"
								: result.getClass().getName() + " -> " + result));
				EclipseStarter.shutdown();
			if (result instanceof ICodeLevelModel) {
				final ICodeLevelModel codeLevelModel = (ICodeLevelModel) result;
				codeLevelModel.moveIn(aCodeLevelModel);
			}
			else {
				// Fall back to reading the serialised model if available
				final File serialised = new File(
						util.io.ProxyDisk.getInstance().directoryTempFile(),
						Common.SERIALISED_MODEL_FILENAME);
					ProxyConsole.getInstance().debugOutput().println(
						"Checking serialised model at: " + serialised.getAbsolutePath()
							+ " exists=" + serialised.exists());
				if (serialised.exists()) {
					try {
						Common.readCodeLevelModel(aCodeLevelModel);
					}
					catch (final RuntimeException serialisationReadFailure) {
						// Large models may occasionally produce unreadable XML in
						// transient runs. Fall back to direct launcher execution.
						final String[] launcherArgs = new String[] {
								Common.ARGUMENT_DIRECTORY_TARGET_CPP_FILES + "="
										+ aRootDirectoryContainingCPPFiles,
								Common.ARGUMENT_DIRECTORY_PTIDEJ_WORKSPACE + "="
										+ pathToCurrentWorkspace };
						final Object directResult = new Launcher().run(launcherArgs);
						if (directResult instanceof ICodeLevelModel) {
							((ICodeLevelModel) directResult).moveIn(aCodeLevelModel);
						}
						else {
							throw serialisationReadFailure;
						}
					}
				}
				else {
					// Last-resort fallback: invoke the launcher directly in-process.
					// This avoids depending on OSGi application registry wiring.
					final String[] launcherArgs = new String[] {
							Common.ARGUMENT_DIRECTORY_TARGET_CPP_FILES + "="
									+ aRootDirectoryContainingCPPFiles,
							Common.ARGUMENT_DIRECTORY_PTIDEJ_WORKSPACE + "="
									+ pathToCurrentWorkspace };
					final Object directResult = new Launcher().run(launcherArgs);
					if (directResult instanceof ICodeLevelModel) {
						((ICodeLevelModel) directResult).moveIn(aCodeLevelModel);
					}
					else {
						throw new RuntimeException(
								"Headless Eclipse did not return a model and no serialised model was produced. "
										+ "Verify -directoryCPPFiles points to existing C++ sources.");
					}
				}
			}
		}
		catch (final Exception e) {
			e.printStackTrace(ProxyConsole.getInstance().errorOutput());
			throw new RuntimeException(e);
		}
	}

	public void createCodeLevelModelUsingOSGiRemote(
			final String aRootDirectoryContainingCPPFiles,
			final ICodeLevelModel aCodeLevelModel) {

		Common.prepareForCodeLevelModel();

		// Yann 2013/06/11: User-friendliness!
		// I check that I can find Eclipse in the most obvious places,
		// if not, I warn the user and kill everything for debug.
		// Yann 2014/04/16: Path...
		// I know use the path of the "mother" JVM... no need to look 
		// for Eclipse and such, I know that they are available...
		//	String equinoxLauncherPath;
		//	String architecture;
		//	if (new File(EclipseCPPParserCaller.EQUINOX_LAUNCHER_PATH_X86).exists()) {
		//		equinoxLauncherPath =
		//			EclipseCPPParserCaller.EQUINOX_LAUNCHER_PATH_X86;
		//		architecture = "-arch x86";
		//	}
		//	else if (new File(EclipseCPPParserCaller.EQUINOX_LAUNCHER_PATH_X64)
		//		.exists()) {
		//		equinoxLauncherPath =
		//			EclipseCPPParserCaller.EQUINOX_LAUNCHER_PATH_X64;
		//		architecture = "-arch x86_64";
		//	}
		//	else {
		//		ProxyConsole
		//			.getInstance()
		//			.errorOutput()
		//			.print("Cannot find Eclipse install at neither\n\t");
		//		ProxyConsole
		//			.getInstance()
		//			.errorOutput()
		//			.println(EclipseCPPParserCaller.EQUINOX_LAUNCHER_PATH_X86);
		//		ProxyConsole.getInstance().errorOutput().print("not\n\t");
		//		ProxyConsole
		//			.getInstance()
		//			.errorOutput()
		//			.println(EclipseCPPParserCaller.EQUINOX_LAUNCHER_PATH_X64);
		//		ProxyConsole
		//			.getInstance()
		//			.errorOutput()
		//			.println("Please modify padl.creator.cppfile.eclipse.misc.Disk");
		//		return;
		//	}

		final String pathToCurrentWorkspace = getPathToCurrentWorkspace();

		this.configureOSGi(pathToCurrentWorkspace);

		final VirtualMachineManager vmManager = Bootstrap
				.virtualMachineManager();
		final LaunchingConnector launchingConnector = vmManager
				.defaultConnector();
		final Map<String, Argument> arguments = launchingConnector
				.defaultArguments();
		final StringBuffer arg = new StringBuffer();

        arguments.get("home").setValue("");

        arg.setLength(0);
        arg.append("-Dosgi.noShutdown=false ");
        arg.append("-Dosgi.compatibility.bootdelegation=true ");
        arg.append("-Xmx2048M ");
        arg.append("-Dosgi.clean=true ");
        arg.append("-classpath \"");
        final String baseCp = System.getProperty("java.class.path");
        final String extraCp = buildAdditionalClasspath(pathToCurrentWorkspace);
        if (extraCp.isEmpty()) {
            arg.append(baseCp);
        } else {
            arg.append(baseCp);
            arg.append(System.getProperty("path.separator"));
            arg.append(extraCp);
        }
        arg.append('\"');
        arguments.get("options").setValue(arg.toString());

		arg.setLength(0);
        arg.append("org.eclipse.core.runtime.adaptor.EclipseStarter");
        // Tests for debugging:
		//	arg.append("padl.creator.cppfile.eclipse.misc.SimpleStarter");
		// Shoudn't it be anyways:
		//	arg.append("org.eclipse.equinox.launcher.Main");
		arg.append(" -application ");
		arg.append(Common.EQUINOX_LAUNCHER_NAME);
        arg.append(" -data \"");
        arg.append(pathToCurrentWorkspace);
        arg.append(Common.EQUINOX_RUNTIME_WORKSPACE);
        arg.append("\"");
        arg.append(" -configuration \"file:");
        arg.append(pathToCurrentWorkspace);
        arg.append(Common.EQUINOX_CONFIGURATION_DATA);
        arg.append("\"");
        // Only include -dev if available
        final File devProps = new File(pathToCurrentWorkspace + Common.EQUINOX_CONFIGURATION_DATA + "dev.properties");
        if (devProps.exists()) {
            arg.append(" -dev \"file:");
            arg.append(pathToCurrentWorkspace);
            arg.append(Common.EQUINOX_CONFIGURATION_DATA);
            arg.append("dev.properties\"");
        }
        // Add OS/WS/ARCH to help Equinox pick correct native fragments
        final String osArg = detectOsArg();
        final String wsArg = detectWsArg(osArg);
        final String archArg = detectArchArg();
        arg.append(" -os ");
        arg.append(osArg);
        arg.append(" -ws ");
        arg.append(wsArg);
        arg.append(" -arch ");
        arg.append(archArg);
		//	arg.append(" ");
		//	arg.append(architecture);
		//	arg.append(" -consoleLog");
		arg.append(" ");
		arg.append(Common.ARGUMENT_DIRECTORY_TARGET_CPP_FILES);
		arg.append("=\"");
		arg.append(aRootDirectoryContainingCPPFiles);
		arg.append("\" ");
		arg.append(Common.ARGUMENT_DIRECTORY_PTIDEJ_WORKSPACE);
		arg.append("=\"");
		arg.append(pathToCurrentWorkspace);
		arg.append("\" ");
		arg.append(Common.ARGUMENT_OSGi_RUNNING_IN_REMOTE_JVM);
		arg.append("=\"");
		arg.append(true);
		arg.append("\" ");
		arguments.get("main").setValue(arg.toString());

		try {
			ProxyConsole.getInstance().debugOutput().println(arguments);
			final VirtualMachine vm = launchingConnector.launch(arguments);
			final Process process = vm.process();
			final OutputMonitor outMonitor = new OutputMonitor("Out Monitor",
					"VM running CDT analysis:", process.getInputStream(),
					ProxyConsole.getInstance().debugOutput());
			outMonitor.start();
			final OutputMonitor errMonitor = new OutputMonitor("Err Monitor",
					"VM running CDT analysis:", process.getErrorStream(),
					ProxyConsole.getInstance().errorOutput());
			errMonitor.start();

			final EventMonitor eventMonitor = new EventMonitor(vm);
			eventMonitor.start();

			final long startTime = System.currentTimeMillis();
			ProxyConsole.getInstance().debugOutput()
					.println("Starting the remote VM.");

			vm.resume();
			eventMonitor.join();
			outMonitor.join();
			errMonitor.join();

			ProxyConsole.getInstance().debugOutput()
					.print("Remote VM done in ");
			ProxyConsole.getInstance().debugOutput()
					.print(System.currentTimeMillis() - startTime);
			ProxyConsole.getInstance().debugOutput().println(" ms.");

			Common.readCodeLevelModel(aCodeLevelModel);
		}
		catch (final IOException e) {
			e.printStackTrace(ProxyConsole.getInstance().errorOutput());
		}
		catch (final IllegalConnectorArgumentsException e) {
			e.printStackTrace(ProxyConsole.getInstance().errorOutput());
		}
		catch (final VMStartException e) {
			e.printStackTrace(ProxyConsole.getInstance().errorOutput());
		}
		catch (final InterruptedException e) {
			e.printStackTrace(ProxyConsole.getInstance().errorOutput());
		}
	}

	private String getPathToCurrentWorkspace() {
		// Yann 2013/06/11: Path!
		// I make things as relative as possible...
		try {
			final File path = Paths.get(this.getClass().getProtectionDomain()
					.getCodeSource().getLocation().toURI()).toFile();
			String pathToCurrentWorkspace;
			if (path.isDirectory()) {
				pathToCurrentWorkspace = new File(
						Files.getClassPath(EclipseCPPParserCaller.class)
								+ "../../../")
						.getCanonicalPath();
			}
			else if (path.isFile() && path.getName().endsWith(".jar")) {
				pathToCurrentWorkspace = path.getParentFile()
						.getCanonicalPath();
			}
				else {
					throw new RuntimeException(
							"Path " + path + " is neither a file nor a directory");
				}
				if (isWorkspaceRoot(pathToCurrentWorkspace)) {
					return toWorkspacePath(pathToCurrentWorkspace);
				}

				String userDirectory = new File(System.getProperty("user.dir"))
						.getCanonicalPath();
				if (isWorkspaceRoot(userDirectory)) {
					return toWorkspacePath(userDirectory);
				}
				File cursor = new File(userDirectory);
				while (cursor != null) {
					userDirectory = cursor.getCanonicalPath();
					if (isWorkspaceRoot(userDirectory)) {
						return toWorkspacePath(userDirectory);
					}
					cursor = cursor.getParentFile();
				}

				return toWorkspacePath(pathToCurrentWorkspace);
			}
			catch (final IOException | URISyntaxException e) {
				e.printStackTrace(ProxyConsole.getInstance().errorOutput());
			throw new RuntimeException(e);
		}
	}

	public ICodeLevelModel getCodeLevelModelUsingOSGiEmbedded(
			final String aRootDirectoryContainingCPPFiles) {

		final ICodeLevelModel codeLevelModel = CPPFactoryEclipse.getInstance()
				.createCodeLevelModel("C++ Model");
		this.createCodeLevelModelUsingOSGiEmbedded(
				aRootDirectoryContainingCPPFiles, codeLevelModel);
		return codeLevelModel;
	}

	public ICodeLevelModel getCodeLevelModelUsingOSGiRemote(
			final String aRootDirectoryContainingCPPFiles) {

		final ICodeLevelModel codeLevelModel = CPPFactoryEclipse.getInstance()
				.createCodeLevelModel("C++ Model");
		this.createCodeLevelModelUsingOSGiRemote(
				aRootDirectoryContainingCPPFiles, codeLevelModel);
		return codeLevelModel;
	}
}
