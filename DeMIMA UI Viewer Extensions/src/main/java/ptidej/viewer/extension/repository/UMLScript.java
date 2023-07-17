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

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import javax.swing.JFrame;
import padl.visitor.IGenerator;
import ptidej.viewer.IRepresentation;
import ptidej.viewer.event.SourceAndGraphModelEvent;
import ptidej.viewer.extension.IViewerExtension;
import util.help.IHelpURL;
import util.io.OutputMonitor;
import util.io.ProxyConsole;

public final class UMLScript extends JFrame implements IViewerExtension,
		IHelpURL {

	private static final long serialVersionUID = 1L;
	public UMLScript() {
		this.addWindowListener(new WindowAdapter() {
			public void windowClosed(final WindowEvent e) {
				UMLScript.this.setVisible(false);
			}
			public void windowClosing(final WindowEvent e) {
				this.windowClosed(e);
			}
		});
	}
	private void createFrame(final IRepresentation aRepresentation) {
		this.setVisible(false);
		this.getContentPane().removeAll();

		// Yann 2003/07/11: Improvements!
		// The builder uses the visibility of elements in the
		// graph model to minimize the number of elements to
		// display by SugiBib.
		final IGenerator builder =
			new UMLScriptGenerator(aRepresentation
				.getSourceGraph()
				.getVisibleElements());
		aRepresentation.getSourceModel().generate(builder);

		final String path =
			System.getProperty("user.dir") + File.separatorChar + "Temp.uml";
		try {
			// TODO Use ProxyDisk
			final FileWriter writer = new FileWriter(path);
			writer.write(builder.getCode().toString());
			writer.close();

			final StringBuffer commandLine = new StringBuffer();
			commandLine.append("..\\SugiBib\\SugiBib");
			commandLine.append(" \"");
			commandLine.append(path);
			commandLine.append('\"');
			final Process process =
				Runtime.getRuntime().exec(commandLine.toString());
			final OutputMonitor errorStreamMonitor =
				new OutputMonitor(
					"UMLScript Stream Monitor",
					"",
					process.getErrorStream(),
					System.out);
			errorStreamMonitor.start();
			final OutputMonitor inputStreamMonitor =
				new OutputMonitor(
					"UMLScript Input Stream Monitor",
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
	public String getName() {
		return "SugiBib";
	}
	public void invoke(final IRepresentation aRepresentation) {
		this.createFrame(aRepresentation);
	}
	public void sourceModelChanged(final SourceAndGraphModelEvent aSourceEvent) {
		this.sourceModelAvailable(aSourceEvent);
	}
	public String getHelpURL() {
		return "http://link.springer.com/chapter/10.1007/3-540-45848-4_49";
	}
	public void sourceModelAvailable(SourceAndGraphModelEvent aSourceEvent) {
		if (this.isVisible()) {
			this.createFrame(aSourceEvent.getRepresentation());
		}
	}
	public void sourceModelUnavailable() {
		// Nothing to do...
	}
}
