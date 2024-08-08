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
package caffeine.statemachine;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Iterator;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JFrame;

import util.io.ProxyConsole;

import com.sun.jdi.Bootstrap;
import com.sun.jdi.VMDisconnectedException;
import com.sun.jdi.VirtualMachine;
import com.sun.jdi.VirtualMachineManager;
import com.sun.jdi.connect.LaunchingConnector;
import com.sun.jdi.connect.Connector.Argument;
import com.sun.jdi.event.EventQueue;
import com.sun.jdi.request.EventRequestManager;
import com.sun.jdi.request.MethodEntryRequest;

public final class Caffeine {
	public static void main(String[] args) {
		try {
			// I create a remote Java Virtual Machine.
			final VirtualMachineManager jvmManager =
				Bootstrap.virtualMachineManager();
			final LaunchingConnector launchingConnector =
				jvmManager.defaultConnector();
			final Map arguments = launchingConnector.defaultArguments();
			final Iterator iterator = arguments.values().iterator();
			while (iterator.hasNext()) {
				final Argument argument = (Argument) iterator.next();
				if (argument.name().equals("main")) {
					// argument.setValue("caffeine.example.GCD");
					argument.setValue("caffeine.example.queens.ObjectQueens");
				}
			}
			final VirtualMachine jvm = launchingConnector.launch(arguments);

			// I make sure I monitor the method entries,
			// and only for the required classes.
			final EventRequestManager erm = jvm.eventRequestManager();
			erm.createMethodEntryRequest();
			erm.createMethodExitRequest();
			Iterator i;
			i = erm.methodEntryRequests().listIterator();
			while (i.hasNext()) {
				MethodEntryRequest methodEntryRequest =
					(MethodEntryRequest) i.next();
				methodEntryRequest.addClassFilter(
					"caffeine.example.queens.*");
				methodEntryRequest.enable();
			}

			// Let's go girls...
			jvm.resume();
			System.out.println("Remote JVM started.");

			// I create a new thread to monitor what's happening in the remote JVM.
			final EventQueue eq = jvm.eventQueue();
			final Thread monitor = new Thread(new CaffeineMonitor(eq));
			monitor.setName("Monitor");
			monitor.setPriority(Thread.MIN_PRIORITY);
			monitor.start();

			// I create a small UI to control the remote JVM.
			final JFrame frame = new JFrame("Caffeine (State machine)");
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
				public void actionPerformed(ActionEvent e) {
					try {
						JButton button = (JButton) e.getSource();
						if (button.getText().equals("Suspend")) {
							button.setText("Resume");
							jvm.suspend();
						}
						else {
							button.setText("Suspend");
							jvm.resume();
						}
					}
					catch (VMDisconnectedException vmde) {

					}
				}
			});
			frame.getContentPane().add(
				suspendResumeButton,
				BorderLayout.CENTER);

			final JButton stopButton = new JButton("Stop");
			stopButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					try {
						jvm.exit(0);
					}
					catch (VMDisconnectedException vmde) {
					}

				}
			});
			frame.getContentPane().add(stopButton, BorderLayout.EAST);
			frame.setVisible(true);
		}
		catch (Exception e) {
			e.printStackTrace(
				ProxyConsole.getInstance().errorOutput());
		}
	}
}
