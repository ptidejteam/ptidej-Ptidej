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
package ptidej.viewer;

import java.io.PrintWriter;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import ptidej.viewer.ui.Console;
import ptidej.viewer.ui.DesktopFrame;
import ptidej.viewer.ui.splash.SplashScreen;
import ptidej.viewer.utils.Resources;
import util.io.NullWriter;
import util.io.ProxyConsole;
import util.io.UnclosablePrintStream;
import util.io.WriterOutputStream;

public class ProjectViewer {
	public static void main(final String[] args) {
		// Setting up the Console...
		ProxyConsole.getInstance().setDebugOutput(
			new PrintWriter(new NullWriter()));
		ProxyConsole.getInstance().setErrorOutput(
			Console.getInstance().getErrorWriter());
		ProxyConsole.getInstance().setNormalOutput(
			Console.getInstance().getNormalWriter());
		System.setErr(new UnclosablePrintStream(new WriterOutputStream(Console
			.getInstance()
			.getErrorWriter())));
		System.setOut(new UnclosablePrintStream(new WriterOutputStream(Console
			.getInstance()
			.getNormalWriter())));

		// Splash screen...
		final SplashScreen splash = new SplashScreen(Resources.PTIDEJ_LOGO);
		splash.setVisible(true);

		// A thread that waits few seconds and then queue
		// the code to close the splash-screen into the 
		// event list of the event thread.
		final Runnable closerRunner = new Runnable() {
			public void run() {
				splash.setVisible(false);
				splash.dispose();
			}
		};
		final Runnable waitRunner = new Runnable() {
			public void run() {
				try {
					Thread.sleep(4000);
					SwingUtilities.invokeAndWait(closerRunner);
				}
				catch (final Exception e) {
					e.printStackTrace();
				}
			}
		};
		final Thread splashThread =
			new Thread(waitRunner, "SplashScreen-Closer");
		splashThread.start();

		// As per http://www.oracle.com/technetwork/articles/javase/swingworker-137249.html#Swing
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				// Verifying if VM version >= 1.1.2
				// Swing applications can only run under VM version >= 1.1.2
				String ver = System.getProperty("java.version");
				if (ver.compareTo("1.1.2") < 0) {
					System.out
						.println("You need the JRE/JDK 1.1.2 or greater to run Ptidej.");
				}
				else {
					// Force The UI to come up in the System L&F
					try {
						UIManager.setLookAndFeel(UIManager
							.getSystemLookAndFeelClassName());
					}
					catch (final Exception e) {
						e.printStackTrace(ProxyConsole
							.getInstance()
							.errorOutput());
					}

					DesktopFrame.getInstance().run();
				}
			}
		});
	}
}
