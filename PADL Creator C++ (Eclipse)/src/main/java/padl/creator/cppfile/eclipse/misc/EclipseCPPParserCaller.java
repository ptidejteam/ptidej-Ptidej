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
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.nio.charset.Charset;
import java.util.HashMap;
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
import util.repository.impl.FileRepositoryFactory;

public final class EclipseCPPParserCaller {
	private static EclipseCPPParserCaller UniqueInstance;
	public static EclipseCPPParserCaller getInstance() {
		if (EclipseCPPParserCaller.UniqueInstance == null) {
			EclipseCPPParserCaller.UniqueInstance =
				new EclipseCPPParserCaller();
		}
		return EclipseCPPParserCaller.UniqueInstance;
	}

	private void configureOSGi(final String aPathToCurrentWorkspace) {
		try {
			final File src =
				new File(aPathToCurrentWorkspace
						+ Common.EQUINOX_CONFIGURATION_DATA
						+ Common.EQUINOX_CONFIGURATION_TEMPLATE_CONFIG_FILE);
			final File dst =
				new File(aPathToCurrentWorkspace
						+ Common.EQUINOX_CONFIGURATION_DATA
						+ Common.EQUINOX_CONFIGURATION_CONFIG_FILE);
			final Writer writer = new FileWriter(dst);

			final List<String> placeHolderLines = FileUtils.readLines(src, Charset.defaultCharset());
			final Iterator<String> iteratorOnPlaceHolderLines =
				placeHolderLines.iterator();
			while (iteratorOnPlaceHolderLines.hasNext()) {
				final String line = (String) iteratorOnPlaceHolderLines.next();
				final String newLine =
					line.replace(
						Common.PLACEHOLDER_TAG,
						aPathToCurrentWorkspace);
				writer.write(newLine);
				writer.write('\n');
			}

			writer.close();
		}
		catch (final IOException e) {
			e.printStackTrace(ProxyConsole.getInstance().errorOutput());
		}
	}
	public void createCodeLevelModelUsingOSGiEmbedded(
		final String aRootDirectoryContainingCPPFiles,
		final ICodeLevelModel aCodeLevelModel) {

		final String pathToCurrentWorkspace = this.getPathToCurrentWorkspace();

		final String[] startupArgs =
			new String[] {
					"-application",
					Common.EQUINOX_LAUNCHER_NAME,
					"-data",
					pathToCurrentWorkspace + Common.EQUINOX_RUNTIME_WORKSPACE,
					"-configuration",
					"file:" + pathToCurrentWorkspace
							+ Common.EQUINOX_CONFIGURATION_DATA,
					"-dev",
					"file:" + pathToCurrentWorkspace
							+ Common.EQUINOX_CONFIGURATION_DATA
							+ "dev.properties",
					// "-consoleLog",
					Common.ARGUMENT_DIRECTORY_TARGET_CPP_FILES + "="
							+ aRootDirectoryContainingCPPFiles,
					Common.ARGUMENT_DIRECTORY_PTIDEJ_WORKSPACE + "="
							+ pathToCurrentWorkspace };

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
			properties.put(
				EclipseStarter.PROP_ADAPTOR,
				"padl.creator.cppfile.eclipse.misc.EclipseCPPParserAdaptor");
			EclipseStarter.setInitialProperties(properties);
			final ICodeLevelModel codeLevelModel =
				(ICodeLevelModel) EclipseStarter.run(startupArgs, null);
			EclipseStarter.shutdown();
			codeLevelModel.moveIn(aCodeLevelModel);
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

		final VirtualMachineManager vmManager =
			Bootstrap.virtualMachineManager();
		final LaunchingConnector launchingConnector =
			vmManager.defaultConnector();
		final Map<String, Argument> arguments =
			launchingConnector.defaultArguments();
		final StringBuffer arg = new StringBuffer();

		arguments.get("home").setValue("");

		arg.setLength(0);
		arg.append("-Dosgi.noShutdown=false ");
		arg.append("-Dosgi.compatibility.bootdelegation=true ");
		arg.append("-Xmx2048M ");
		arg.append("-classpath \"");
		arg.append(System.getProperty("java.class.path"));
		arg.append('\"');
		arguments.get("options").setValue(arg.toString());

		arg.setLength(0);
		arg.append("org.eclipse.core.runtime.adaptor.EclipseStarter");
		// Tests for debugging:
		//	arg.append("padl.creator.cppfile.eclipse.misc.SimpleStarter");
		// Shoudn't it be anyways:
		//	arg.append("org.eclipse.equinox.launcher.Main");
		arg.append("    -application ");
		arg.append(Common.EQUINOX_LAUNCHER_NAME);
		arg.append("    -data \"");
		arg.append(pathToCurrentWorkspace);
		arg.append(Common.EQUINOX_RUNTIME_WORKSPACE);
		arg.append("\"");
		arg.append("    -configuration \"file:");
		arg.append(pathToCurrentWorkspace);
		arg.append(Common.EQUINOX_CONFIGURATION_DATA);
		arg.append("\"");
		arg.append("    -dev \"file:");
		arg.append(pathToCurrentWorkspace);
		arg.append(Common.EQUINOX_CONFIGURATION_DATA);
		arg.append("dev.properties\"");
		//	arg.append(" ");
		//	arg.append(architecture);
		//	arg.append("    -consoleLog");
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
			final VirtualMachine vm = launchingConnector.launch(arguments);
			final Process process = vm.process();
			final OutputMonitor outMonitor =
				new OutputMonitor(
					"Out Monitor",
					"VM running CDT analysis:",
					process.getInputStream(),
					ProxyConsole.getInstance().debugOutput());
			outMonitor.start();
			final OutputMonitor errMonitor =
				new OutputMonitor(
					"Err Monitor",
					"VM running CDT analysis:",
					process.getErrorStream(),
					ProxyConsole.getInstance().errorOutput());
			errMonitor.start();

			final EventMonitor eventMonitor = new EventMonitor(vm);
			eventMonitor.start();

			final long startTime = System.currentTimeMillis();
			ProxyConsole
				.getInstance()
				.debugOutput()
				.println("Starting the remote VM.");

			vm.resume();
			eventMonitor.join();
			outMonitor.join();
			errMonitor.join();

			ProxyConsole
				.getInstance()
				.debugOutput()
				.print("Remote VM done in ");
			ProxyConsole
				.getInstance()
				.debugOutput()
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
			if (FileRepositoryFactory.getRunningPath().isDirectory()) {
				pathToCurrentWorkspace =
					new File(Files.getClassPath(EclipseCPPParserCaller.class)
							+ "../../").getCanonicalPath();
			}
			else if (FileRepositoryFactory.getRunningPath().isFile()
					&& FileRepositoryFactory
						.getRunningPath()
						.getName()
						.endsWith(".jar")) {

				pathToCurrentWorkspace =
					FileRepositoryFactory
						.getRunningPath()
						.getParentFile()
						.getCanonicalPath();
			}
			else {
				return null;
			}
			return pathToCurrentWorkspace.replace('\\', '/') + '/';
		}
		catch (final IOException e) {
			e.printStackTrace(ProxyConsole.getInstance().errorOutput());
			return null;
		}
	}
	public ICodeLevelModel getCodeLevelModelUsingOSGiEmbedded(
		final String aRootDirectoryContainingCPPFiles) {

		final ICodeLevelModel codeLevelModel =
			CPPFactoryEclipse.getInstance().createCodeLevelModel("C++ Model");
		this.createCodeLevelModelUsingOSGiEmbedded(
			aRootDirectoryContainingCPPFiles,
			codeLevelModel);
		return codeLevelModel;
	}
	public ICodeLevelModel getCodeLevelModelUsingOSGiRemote(
		final String aRootDirectoryContainingCPPFiles) {

		final ICodeLevelModel codeLevelModel =
			CPPFactoryEclipse.getInstance().createCodeLevelModel("C++ Model");
		this.createCodeLevelModelUsingOSGiRemote(
			aRootDirectoryContainingCPPFiles,
			codeLevelModel);
		return codeLevelModel;
	}
}
