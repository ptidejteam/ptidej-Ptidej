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
package caffeine.simulator;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.io.StringReader;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFrame;

import util.io.ProxyConsole;
import util.io.ReaderInputStream;
import JIP.engine.JIPEngine;
import JIP.engine.JIPSyntaxErrorException;
import JIP.engine.JIPTerm;
import caffeine.Constants;
import caffeine.logic.PrologListener;

import com.sun.jdi.VMDisconnectedException;

/**
 * @version 	0.3
 * @author		Yann-Gaël Guéhéneuc
 */
public final class Caffeine {
	private static Caffeine UNIQUE_INSTANCE;

	public static Caffeine getUniqueInstance() {
		if (Caffeine.UNIQUE_INSTANCE == null) {
			Caffeine.UNIQUE_INSTANCE = new Caffeine();
		}
		return Caffeine.UNIQUE_INSTANCE;
	}

	private JIPEngine jipEngine;
	private StringBuffer consultStringBuffer;

	private Caffeine() {
	}
	private void initialize(
		final String ruleFileName,
		final String traceFileName,
		final PrologListener prologListener)
		throws ClassNotFoundException {

		try {
			final LineNumberReader reader =
				new LineNumberReader(
					new InputStreamReader(
						this.getClass().getClassLoader().getResourceAsStream(
							traceFileName)));
			final Vector eventDataVector = new Vector();
			String line;
			while ((line = reader.readLine()) != null) {
				eventDataVector.add(line);
			}
			final String[] eventData = new String[eventDataVector.size()];
			eventDataVector.copyInto(eventData);

			this.jipEngine = new PrologEngine(eventData);
			this.jipEngine.addErrorListener(prologListener);
			this.jipEngine.addEventListener(prologListener);
			this.jipEngine.addTraceListener(prologListener);
			// jipEngine.setTrace(true);
			this.consultStringBuffer = new StringBuffer();

			this.consult(
				caffeine.logic.Engine.class.getResourceAsStream("Rules.pl"));
			this.consult(
				this.getClass().getClassLoader().getResourceAsStream(
					ruleFileName));
			System.out.println("Caffeine Simulator initialized.");
		}
		catch (final IOException ioe) {
			System.err.println("Error while loading file: " + traceFileName);
			ioe.printStackTrace(ProxyConsole.getInstance().errorOutput());
		}
	}
	public void run(final String ruleFileName, final String traceFileName) {
		try {
			// The Prolog engine...
			final PrologListener prologListener = new PrologListener();
			final Caffeine caffeine = new Caffeine();
			caffeine.initialize(ruleFileName, traceFileName, prologListener);

			// I create a small UI to control the remote JVM.
			final JFrame frame = new JFrame("Caffeine (Simulator)");
			frame.setLocation(100, 100);
			frame.setSize(300, 100);
			frame.addWindowListener(new WindowAdapter() {
				public void windowClosing(WindowEvent e) {
					System.exit(0);
				}
			});
			frame.getContentPane().setLayout(new BorderLayout());

			final JButton suspendResumeButton = new JButton("Resume");
			suspendResumeButton.addActionListener(new ActionListener() {
				public void actionPerformed(final ActionEvent e) {
					try {
						final JButton button = (JButton) e.getSource();
						if (button.getText().equals("Suspend")) {
							if (prologListener.suspend()) {
								button.setText("Resume");
							}
						}
						else {
							if (prologListener.resume()) {
								button.setText("Suspend");
							}
						}
					}
					catch (final VMDisconnectedException vmde) {
					}
				}
			});
			frame.getContentPane().add(
				suspendResumeButton,
				BorderLayout.WEST);

			final JButton verboseButton = new JButton("Quiet");
			frame.getContentPane().add(verboseButton, BorderLayout.CENTER);

			final JButton stopButton = new JButton("Stop");
			frame.getContentPane().add(stopButton, BorderLayout.EAST);
			frame.setVisible(true);

			Thread.sleep(2000);
			caffeine.start();
		}
		catch (final Exception e) {
			e.printStackTrace(ProxyConsole.getInstance().errorOutput());
		}
	}
	private void start() {
		System.out.println("Caffeine Simulator started.");
		this.query(caffeine.logic.Engine.class, "Query.pl");
	}
	private void consult(final InputStream stream) {
		try {
			int i;
			while ((i = stream.read()) > 0) {
				if (Constants.DEBUG) {
					System.out.print((char) i);
					System.out.flush();
				}
				this.consultStringBuffer.append((char) i);
			}
		}
		catch (IOException ioe) {
			ioe.printStackTrace(ProxyConsole.getInstance().errorOutput());
		}
		catch (NullPointerException npe) {
			System.err.println("Is the rule available?");
			npe.printStackTrace(ProxyConsole.getInstance().errorOutput());
		}
	}
	private void query(final Class resourceClass, String resourceName) {
		try {
			final String oldEventManagerCall = "caffeine.logic.EventManager";
			final int index =
				this.consultStringBuffer.toString().indexOf(
					oldEventManagerCall);
			this.consultStringBuffer.replace(
				index,
				index + oldEventManagerCall.length(),
				"caffeine.simulator.EventManager");

			this.jipEngine.consultStream(
				new ReaderInputStream(
					new StringReader(this.consultStringBuffer.toString())),
				resourceName);
		}
		catch (final JIPSyntaxErrorException jipsee) {
			System.err.println(
				"Error while adding the clauses in the engine.");
			jipsee.printStackTrace(ProxyConsole.getInstance().errorOutput());
		}
		catch (final IOException ioe) {
			System.err.println(
				"Error while adding the clauses in the engine.");
			ioe.printStackTrace(ProxyConsole.getInstance().errorOutput());
		}

		final StringBuffer queryText = new StringBuffer();
		try {
			final LineNumberReader reader =
				new LineNumberReader(
					new InputStreamReader(
						resourceClass.getResourceAsStream(resourceName)));
			String line;
			while ((line = reader.readLine()) != null) {
				queryText.append(line);
				queryText.append('\n');
			}

			this.jipEngine.openQuery(JIPTerm.parseQuery(queryText.toString()));
			//  jipEngine.nextSolution(jipQueryHandle);
			//	while(jipEngine.moreSolutions(jipQueryHandle)) {
			//		System.out.println("Done.");
			//		jipEngine.nextSolution(jipQueryHandle);
			//	}
		}
		catch (final IOException ioe) {
			System.err.println("Error while querying the engine.");
			ioe.printStackTrace(ProxyConsole.getInstance().errorOutput());
		}
		catch (final Exception e) {
			System.err.println("Error while querying the engine.");
			e.printStackTrace(ProxyConsole.getInstance().errorOutput());
			System.err.println("With query text:");
			System.err.println(queryText.toString());
		}
	}
}
