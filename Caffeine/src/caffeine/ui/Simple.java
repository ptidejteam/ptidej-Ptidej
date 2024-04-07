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
package caffeine.ui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JFrame;

import util.io.OutputMonitor;
import caffeine.logic.PrologListener;

import com.sun.jdi.VMDisconnectedException;
import com.sun.jdi.VirtualMachine;

/**
 * @author	Yann-Gaël Guéhéneuc
 * @version 0.1
 */
public final class Simple extends JFrame {
	private static final long serialVersionUID = -8592609882923126128L;

	private final VirtualMachine jvm;
	private final OutputMonitor streamOutputMonitor;
	private final PrologListener prologListener;

	public Simple(
		final VirtualMachine jvm,
		final OutputMonitor streamOutputMonitor,
		final PrologListener prologListener) {

		this.jvm = jvm;
		this.streamOutputMonitor = streamOutputMonitor;
		this.prologListener = prologListener;

		this.setTitle("Caffeine");
		this.setLocation(100, 100);
		this.setSize(300, 100);
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
		this.getContentPane().setLayout(new BorderLayout());

		final JButton suspendResumeButton = new JButton("Resume");
		suspendResumeButton.addActionListener(new ActionListener() {
			public void actionPerformed(final ActionEvent e) {
				try {
					final JButton button = (JButton) e.getSource();
					if (button.getText().equals("Suspend")) {
						if (Simple.this.prologListener.suspend()) {
							button.setText("Resume");
						}
					}
					else {
						if (Simple.this.prologListener.resume()) {
							button.setText("Suspend");
						}
					}
				}
				catch (final VMDisconnectedException vmde) {
				}
			}
		});
		this.getContentPane().add(suspendResumeButton, BorderLayout.WEST);

		final JButton verboseButton = new JButton("Quiet");
		verboseButton.addActionListener(new ActionListener() {
			public void actionPerformed(final ActionEvent e) {
				final JButton button = (JButton) e.getSource();
				if (button.getText().equals("Quiet")) {
					button.setText("Verbose");
					Simple.this.streamOutputMonitor.setVerbose(false);
				}
				else {
					button.setText("Quiet");
					Simple.this.streamOutputMonitor.setVerbose(true);
				}
			}
		});
		this.getContentPane().add(verboseButton, BorderLayout.CENTER);

		final JButton stopButton = new JButton("Stop");
		stopButton.addActionListener(new ActionListener() {
			public void actionPerformed(final ActionEvent e) {
				try {
					Simple.this.jvm.exit(0);
				}
				catch (final VMDisconnectedException vmde) {
				}

			}
		});
		this.getContentPane().add(stopButton, BorderLayout.EAST);
		this.setVisible(true);
	}
}
