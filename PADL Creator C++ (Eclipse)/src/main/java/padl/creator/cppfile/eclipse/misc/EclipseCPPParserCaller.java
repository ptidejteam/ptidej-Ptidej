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
import java.io.Writer;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

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
import padl.kernel.ICodeLevelModel;
import util.io.Files;
import util.io.OutputMonitor;
import util.io.ProxyConsole;

public final class EclipseCPPParserCaller {
	private static EclipseCPPParserCaller UniqueInstance;

	public static EclipseCPPParserCaller getInstance() {
		if (EclipseCPPParserCaller.UniqueInstance == null) {
			EclipseCPPParserCaller.UniqueInstance = new EclipseCPPParserCaller();
		}
		return EclipseCPPParserCaller.UniqueInstance;
	}

    private void configureOSGi(final String aPathToCurrentWorkspace) {
        try {
            final String cfgRoot = aPathToCurrentWorkspace + Common.EQUINOX_CONFIGURATION_DATA;
            final File cfgDir = new File(cfgRoot);
            if (!cfgDir.exists()) {
                FileUtils.forceMkdir(cfgDir);
            }
            // Always clean stale OSGi persisted state to avoid version mismatch errors
            FileUtils.deleteQuietly(new File(cfgDir, "org.eclipse.osgi"));

            final File src = new File(cfgRoot + Common.EQUINOX_CONFIGURATION_TEMPLATE_CONFIG_FILE);
            // If a template config.ini is available, generate one pointing to this workspace
            if (src.exists()) {
                final File dst = new File(cfgRoot + Common.EQUINOX_CONFIGURATION_CONFIG_FILE);
                try (final Writer writer = new FileWriter(dst)) {
                    final List<String> placeHolderLines = FileUtils.readLines(src, Charset.defaultCharset());
                    final Iterator<String> it = placeHolderLines.iterator();
                    while (it.hasNext()) {
                        final String line = it.next();
                        writer.write(line.replace(Common.PLACEHOLDER_TAG, aPathToCurrentWorkspace));
                        writer.write('\n');
                    }
                }
            } else {
                ProxyConsole.getInstance().debugOutput()
                    .println("No template config.ini found; using clean configuration at " + cfgRoot);
            }
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
        argsList.add("-data");
        argsList.add(pathToCurrentWorkspace + Common.EQUINOX_RUNTIME_WORKSPACE);
        argsList.add("-configuration");
        argsList.add("file:" + pathToCurrentWorkspace + Common.EQUINOX_CONFIGURATION_DATA);
        // Only add -dev if dev.properties exists
        final File devProps = new File(pathToCurrentWorkspace + Common.EQUINOX_CONFIGURATION_DATA + "dev.properties");
        if (devProps.exists()) {
            argsList.add("-dev");
            argsList.add("file:" + devProps.getPath().replace('\\','/'));
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
			Map<String, String> properties = new HashMap<String, String>();
			properties.put(EclipseStarter.PROP_ADAPTOR,
					"padl.creator.cppfile.eclipse.misc.EclipseCPPParserAdaptor");
			EclipseStarter.setInitialProperties(properties);
			final Object result = EclipseStarter.run(startupArgs, null);
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
				if (serialised.exists()) {
					Common.readCodeLevelModel(aCodeLevelModel);
				}
				else {
					throw new RuntimeException(
							"Headless Eclipse did not return a model and no serialised model was produced. "
									+ "Verify -directoryCPPFiles points to existing C++ sources.");
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
		String pathToCurrentWorkspace;
		try {
			final File path = Paths.get(this.getClass().getProtectionDomain()
					.getCodeSource().getLocation().toURI()).toFile();
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
			return pathToCurrentWorkspace.replace('\\', '/') + '/';
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
