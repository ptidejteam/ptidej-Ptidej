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
package ptidej.viewer.extension.repository;

import java.io.IOException;
import javax.swing.JFrame;
import ptidej.viewer.IRepresentation;
import util.io.OutputMonitor;
import util.io.ProxyConsole;

public class Dotty extends JFrame {
	private static final long serialVersionUID = 1L;

	protected void callDotty(
		final IRepresentation aRepresentation,
		final String aFilePath) {

		this.setVisible(false);
		this.getContentPane().removeAll();

		try {
			final StringBuffer commandLine = new StringBuffer();
			commandLine.append("..\\DOT\\bin\\dotty ");
			commandLine.append(aFilePath);
			final Process process =
				Runtime.getRuntime().exec(commandLine.toString());
			final OutputMonitor errorStreamMonitor =
				new OutputMonitor(
					"Dotty Stream Monitor",
					"",
					process.getErrorStream(),
					System.out);
			errorStreamMonitor.start();
			final OutputMonitor inputStreamMonitor =
				new OutputMonitor(
					"Dotty Input Stream Monitor",
					"",
					process.getInputStream(),
					System.out);
			inputStreamMonitor.start();

			// I wait for the process to finish
			// in a new thread to allow multi-tasking.
			new Thread(new Runnable() {
				public void run() {
					try {
						process.waitFor();
					}
					catch (final InterruptedException ie) {
						ie.printStackTrace(ProxyConsole
							.getInstance()
							.errorOutput());
					}

					// I check if everything went alright.
					if (process.exitValue() != 0) {
						try {
							while (process.getErrorStream().available() > 0) {
								System.out.print((char) process
									.getErrorStream()
									.read());
							}
						}
						catch (final IOException ioe) {
							ioe.printStackTrace(ProxyConsole
								.getInstance()
								.errorOutput());
						}
					}
				}
			}).start();
		}
		catch (final IOException ioe) {
			ioe.printStackTrace(ProxyConsole.getInstance().errorOutput());
		}
	}
}
