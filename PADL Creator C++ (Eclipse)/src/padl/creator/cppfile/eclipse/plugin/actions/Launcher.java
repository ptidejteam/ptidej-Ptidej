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
package padl.creator.cppfile.eclipse.plugin.actions;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import org.apache.commons.io.FileUtils;
import org.eclipse.equinox.app.IApplication;
import org.eclipse.equinox.app.IApplicationContext;
import padl.creator.cppfile.eclipse.Common;
import padl.creator.cppfile.eclipse.plugin.internal.Constants;
import padl.creator.cppfile.eclipse.plugin.internal.GeneratorFromCPPProject;
import padl.creator.cppfile.eclipse.plugin.internal.Utils;
import padl.kernel.ICodeLevelModel;
import util.io.ProxyConsole;

public class Launcher implements IApplication {
	private static String getArgumentValue(
		final Map<?, ?> someArguments,
		final String anArgumentName) {

		String argumentValue = null;
		final String[] arguments =
			(String[]) someArguments.get("application.args");
		for (int i = 0; i < arguments.length; i++) {
			final String argument = arguments[i];
			if (argument.startsWith(anArgumentName)) {
				argumentValue = argument.substring(anArgumentName.length() + 1);
			}
		}

		return argumentValue;
	}
	private boolean areArgumentsEmpty(Map<?, ?> someArguments) {
		final String[] arguments =
			(String[]) someArguments.get("application.args");
		return arguments.length == 0;
	}
	private File getDirMetadata(final File directoryPtidej) {
		final File destination = new File(
			directoryPtidej.getAbsolutePath() + File.separatorChar
					+ Common.EQUINOX_RUNTIME_WORKSPACE
					+ Constants.META_DATA_DIRECTORY);

		return destination;
	}
	private File getDirOrigin(final Map<?, ?> someArguments) {
		final String rootDirectoryContainingCPPFiles =
			Launcher.getArgumentValue(
				someArguments,
				Common.ARGUMENT_DIRECTORY_TARGET_CPP_FILES);

		// Yann 2013/05/16: Current working directory
		// I search for the source folder from the CWD
		// which is given by System.getProperty("user.dir")
		File source = new File(
			System.getProperty("user.dir") + File.separatorChar
					+ rootDirectoryContainingCPPFiles);
		ProxyConsole
			.getInstance()
			.debugOutput()
			.print("Looking for C++ files in ");
		ProxyConsole.getInstance().debugOutput().println(source);
		if (!source.exists()) {
			// Yann 2013/05/16: Relativity!
			// First, I tried to find the sources relative to the CWD,
			// if I cannot find them, I assume that we gave an absolute
			// path and try to locate it... if not, I abort!
			source = new File(rootDirectoryContainingCPPFiles);
			ProxyConsole
				.getInstance()
				.debugOutput()
				.print("Looking for C++ files in ");
			ProxyConsole.getInstance().debugOutput().println(source);
			if (!source.exists()) {
				ProxyConsole.getInstance().errorOutput().print(
					"Cannot find the C++ files to analyses in \"");
				ProxyConsole.getInstance().errorOutput().print(
					source.getAbsolutePath());
				ProxyConsole.getInstance().errorOutput().println('\"');
			}
			else {
				ProxyConsole
					.getInstance()
					.debugOutput()
					.println("\tFound C++ files!");
			}
		}

		return source;
	}
	private File getDirPtidej(Map<?, ?> someArguments) {
		final File directoryPtidej = new File(
			Launcher.getArgumentValue(
				someArguments,
				Common.ARGUMENT_DIRECTORY_PTIDEJ_WORKSPACE));
		if (!directoryPtidej.exists()) {
			ProxyConsole.getInstance().errorOutput().print(
				"Cannot find the Ptidej Workspace at ");
			ProxyConsole.getInstance().errorOutput().println(
				directoryPtidej.getAbsolutePath() + File.separatorChar);
		}
		else {
			ProxyConsole
				.getInstance()
				.debugOutput()
				.println("Found Ptidej Workspace!");
		}
		return directoryPtidej;
	}
	private File getDirSafe(final File directoryPtidej) {
		final File destination = new File(
			directoryPtidej.getAbsolutePath() + File.separatorChar
					+ Common.EQUINOX_RUNTIME_WORKSPACE
					+ Constants.SAFE_CPP_PROJECT_NAME);

		return destination;
	}
	private File getDirTarget(final File directoryPtidej) {
		final File destination = new File(
			directoryPtidej.getAbsolutePath() + File.separatorChar
					+ Common.EQUINOX_RUNTIME_WORKSPACE
					+ Constants.CPP_PROJECT_NAME);

		return destination;
	}
	private boolean isRunningInRemoteJVM(Map<?, ?> someArguments) {
		return Launcher.getArgumentValue(
			someArguments,
			Common.ARGUMENT_OSGi_RUNNING_IN_REMOTE_JVM) != null;
	}
	private void prepareTargetCPPFiles(
		final File directorySafeCPPProject,
		final File directoryOrigin,
		final File directoryDestination) {

		try {
			if (directoryDestination.exists()) {
				ProxyConsole.getInstance().debugOutput().println(
					"Destination exists at "
							+ directoryDestination.getAbsolutePath()
							+ File.separatorChar);
				FileUtils.deleteQuietly(directoryDestination);
			}
			FileUtils
				.copyDirectory(directorySafeCPPProject, directoryDestination);

			FileUtils.copyDirectory(
				directoryOrigin,
				directoryDestination,
				Utils.FILTER_OF_HIDDEN_FILES);
		}
		catch (final IOException e) {
			e.printStackTrace(ProxyConsole.getInstance().errorOutput());
		}
		ProxyConsole.getInstance().debugOutput().println("Files copied.");
	}
	public Object run(final Object someArguments) {
		final Map<String, String[]> arguments = new HashMap<String, String[]>();
		arguments.put("application.args", (String[]) someArguments);

		return this.start(arguments);
	}
	@Override
	public Object start(final IApplicationContext anApplicationContext) {
		final Map<?, ?> arguments = anApplicationContext.getArguments();

		return this.start(arguments);
	}
	private Object start(final Map<?, ?> someArguments) {
		// For debug purposes...
		//	ProxyConsole.getInstance().setDebugOutput(
		//		ProxyConsole.getInstance().errorOutput());

		if (this.areArgumentsEmpty(someArguments)) {
			ProxyConsole.getInstance().errorOutput().println(
				"No arguments given to the application, please check your runtime configuration. (application.args = new Sring[0])");
			throw new RuntimeException(
				"No arguments given to the application, please check your runtime configuration. (application.args = new Sring[0])");
		}

		final boolean isInRemoteJVM = this.isRunningInRemoteJVM(someArguments);
		final File directoryPtidej = this.getDirPtidej(someArguments);
		final File directoryOriginCPPFiles = this.getDirOrigin(someArguments);
		final File directoryTargetCPPFiles = this.getDirTarget(directoryPtidej);
		final File directorySafeCPPProject = this.getDirSafe(directoryPtidej);

		if (directoryOriginCPPFiles.exists()
				&& directorySafeCPPProject.exists()) {

			// Yann 2013/09/12: Startup problem!
			// In middle of testing, I could not run anymore Eclipse...
			// Thanks to StackOverflow, I found out that the problem
			// was this useless .snap file, which I now delete each time.
			// See http://stackoverflow.com/questions/3505187/eclipse-wont-start-log-error-says-objectnotfoundexception-tree-element
			FileUtils.deleteQuietly(this.getDirMetadata(directoryPtidej));

			this.prepareTargetCPPFiles(
				directorySafeCPPProject,
				directoryOriginCPPFiles,
				directoryTargetCPPFiles);

			ProxyConsole.getInstance().debugOutput().println(
				"Starting creating code-level model from C++ file(s).");

			try {
				final GeneratorFromCPPProject generator =
					new GeneratorFromCPPProject();
				final ICodeLevelModel model = generator.build();

				ProxyConsole.getInstance().debugOutput().print(
					"Done creating code-level model from C++ file(s), found ");

				if (isInRemoteJVM) {
					Common.writeCodeLevelModel(model);
				}

				// Yann 2016/09/21: Clean up!
				// I don't forget to erase the source directory used by Eclipse
				FileUtils.deleteQuietly(directoryTargetCPPFiles);

				return model;
			}
			catch (final Throwable e) {
				e.printStackTrace(ProxyConsole.getInstance().errorOutput());
			}
		}

		return null;
	}
	@Override
	public void stop() {
		ProxyConsole.getInstance().debugOutput().flush();
		ProxyConsole.getInstance().errorOutput().flush();
		ProxyConsole.getInstance().normalOutput().flush();
		ProxyConsole.getInstance().warningOutput().flush();
	}
}
