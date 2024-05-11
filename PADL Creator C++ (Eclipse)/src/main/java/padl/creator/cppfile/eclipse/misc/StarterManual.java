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

import java.io.IOException;
import util.io.OutputMonitor;
import util.io.ProxyConsole;

public class StarterManual {
	public static void main(final String[] args) {
		// http://stackoverflow.com/questions/2502518/eclipse-export-running-configuration
		//	final String commandLine =
		//		"\"C:/Program Files (x86)/Java/jdk1.7.0_21/bin/javaw.exe\" -Declipse.pde.launch=true -Dfile.encoding=Cp1252 -classpath \"C:/Program Files (x86)/eclipse/plugins/org.eclipse.equinox.launcher_1.2.0.v20110502.jar\" org.eclipse.equinox.launcher.Main -application PADL_Creator_Cpp_Eclipse.Launcher -data \"D:/Software/Ptidej 5 Workspace 3/PADL Creator C++ (Eclipse) Helper/Runtime Workspace\" -configuration \"file:D:/Software/Ptidej 5 Workspace 3/.metadata/.plugins/org.eclipse.pde.core/PADL Creator C++ (Eclipse)/\" -dev \"file:D:/Software/Ptidej 5 Workspace 3/.metadata/.plugins/org.eclipse.pde.core/PADL Creator C++ (Eclipse)/dev.properties\" -os win32 -ws win32 -arch x86 -nl en_CA -consoleLog";
		final String[] brokenCommandLine =
			new String[] {
					"C:/Program Files (x86)/Java/jre7/bin/javaw.exe",
					"-Declipse.pde.launch=true",
					"-Dfile.encoding=Cp1252",
					"-classpath",
					"\"C:/Program Files (x86)/eclipse/plugins/org.eclipse.equinox.launcher_1.3.0.v20120522-1813.jar\"",
					"org.eclipse.equinox.launcher.Main",
					"-application",
					"PADL_Creator_Cpp_Eclipse.Launcher",
					"-data",
					"\"D:/Software/Ptidej 5 Workspace 3/PADL Creator C++ (Eclipse) Helper/Runtime Workspace\"",
					"-configuration",
					"\"file:D:/Software/Ptidej 5 Workspace 3/.metadata/.plugins/org.eclipse.pde.core/PADL Creator C++ (Eclipse)/\"",
					"-dev",
					"\"file:D:/Software/Ptidej 5 Workspace 3/.metadata/.plugins/org.eclipse.pde.core/PADL Creator C++ (Eclipse)/dev.properties\"",
					"-os", "win32", "-ws", "win32", "-arch", "x86", "-nl",
					"en_CA", "-consoleLog" };
		final ProcessBuilder processBuilder =
			new ProcessBuilder(brokenCommandLine);
		try {
			final Process process = processBuilder.start();
			final OutputMonitor outMonitor =
				new OutputMonitor(
					"Out Monitor",
					"",
					process.getInputStream(),
					ProxyConsole.getInstance().debugOutput());
			outMonitor.start();
			final OutputMonitor errMonitor =
				new OutputMonitor(
					"Err Monitor",
					"",
					process.getErrorStream(),
					ProxyConsole.getInstance().errorOutput());
			errMonitor.start();
			process.waitFor();
			ProxyConsole
				.getInstance()
				.debugOutput()
				.println(process.exitValue());
		}
		catch (final IOException e) {
			e.printStackTrace();
		}
		catch (final InterruptedException e) {
			e.printStackTrace();
		}
	}
}
