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
package util.process;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import util.io.ProxyConsole;

public class CallerHelper {
	public static void execute(final String aHeader, final String aCommandLine) {
		try {
			ProxyConsole.getInstance().normalOutput().print("Executing: \"");
			ProxyConsole.getInstance().normalOutput().print(aCommandLine);
			ProxyConsole.getInstance().normalOutput().println("\"...");
			// Yann 2013/04/29 and 2013/09/12: MacOS X vs. Windows
			// The use of one single String for the command line does
			// not work under MacOS X see 
			//	http://wiki.ptidej.net/doku.php?id=java_class_process
			// so I now "cut" the command line into pieces, taking care
			// of keeping text between quotes as one single piece of
			// text to build a nice command line.
			// See also
			//	http://stackoverflow.com/questions/7804335/split-string-on-spaces-except-if-between-quotes-i-e-treat-hello-world-as
			final List<String> commandAndArguments = new ArrayList<String>();
			final Matcher matcher =
				Pattern
					.compile("([^\"]\\S*|\".+?\")\\s*")
					.matcher(aCommandLine);
			while (matcher.find()) {
				commandAndArguments.add(matcher.group(1).replace("\"", ""));
			}

			// Yann 2013/09/12: ProcessBuilder
			// I replace the "old" fashion with the recommanded use of ProcessBuilder
			//	final Process process = Runtime.getRuntime().exec(aCommandLine);
			//
			//	// Threads to catch and to display the output of the remote JVM.
			//	final OutputMonitor streamOutputMonitor =
			//		new OutputMonitor(
			//			"Ouput Stream Monitor",
			//			aHeader,
			//			process.getInputStream(),
			//			new PrintStream(new WriterOutputStream(ProxyConsole
			//				.getInstance()
			//				.normalOutput())));
			//	streamOutputMonitor.start();
			//
			//	final OutputMonitor streamErrorMonitor =
			//		new OutputMonitor(
			//			"Error Stream Monitor",
			//			aHeader,
			//			process.getErrorStream(),
			//			new PrintStream(new WriterOutputStream(ProxyConsole
			//				.getInstance()
			//				.errorOutput())));
			//	streamErrorMonitor.start();
			final ProcessBuilder processBuilder =
				new ProcessBuilder(commandAndArguments);
			processBuilder.redirectErrorStream(true);
			processBuilder.redirectOutput(ProcessBuilder.Redirect.INHERIT);
			final Process process = processBuilder.start();

			final long startTime = System.currentTimeMillis();
			try {
				process.waitFor();
			}
			catch (final InterruptedException ie) {
				ie.printStackTrace(ProxyConsole.getInstance().errorOutput());
			}
			final long endTime = System.currentTimeMillis();

			// No need anymore! Because I use "processBuilder.redirectErrorStream(true);"
			//	// I check if everything went alright.
			//	if (process.exitValue() != 0) {
			//		while (process.getErrorStream().available() > 0) {
			//			ProxyConsole
			//				.getInstance()
			//				.errorOutput()
			//				.print((char) process.getErrorStream().read());
			//		}
			//	}

			ProxyConsole.getInstance().normalOutput().print("Done in ");
			ProxyConsole
				.getInstance()
				.normalOutput()
				.print(endTime - startTime);
			ProxyConsole.getInstance().normalOutput().println("ms");

			//	try {
			//		Thread.sleep(5000);
			//	}
			//	catch (final InterruptedException e) {
			//		e.printStackTrace();
			//	}
		}
		catch (final IOException e) {
			e.printStackTrace(ProxyConsole.getInstance().errorOutput());
		}
	}
}
