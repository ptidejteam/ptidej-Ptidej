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
package modec.tool;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.IOException;
import java.io.Writer;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.WindowConstants;
import util.io.ProxyDisk;

public class UI extends JFrame {
	private static final long serialVersionUID = -2356097739670395566L;
	public UI(final String aTraceFileName) {
		this.addWindowListener(new WindowListener() {
			public void windowOpened(final WindowEvent e) {
			}
			public void windowIconified(final WindowEvent e) {
			}
			public void windowDeiconified(final WindowEvent e) {
			}
			public void windowDeactivated(final WindowEvent e) {
			}
			public void windowClosing(final WindowEvent e) {
			}
			public void windowClosed(final WindowEvent e) {
			}
			public void windowActivated(final WindowEvent e) {
			}
		});
		this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		this.setResizable(false);
		this.setTitle("MoDeC Marker");

		final JTextField textField = new JTextField();
		final JButton buttonStart = new JButton("Insert Start Marker");
		buttonStart.setBackground(Color.GREEN);
		final JButton buttonStop = new JButton("Insert Stop Marker");
		buttonStop.setEnabled(false);

		buttonStart.addActionListener(new ActionListener() {
			public void actionPerformed(final ActionEvent anActionEvent) {
				try {
					final Writer writer =
						ProxyDisk.getInstance().fileAbsoluteOutput(
							aTraceFileName,
							true);
					writer.write("# MARKER START: ");
					writer.write(textField.getText());
					writer.write('\n');
					writer.close();

					buttonStop.setBackground(Color.GREEN);
					buttonStop.setEnabled(true);
					buttonStart.setBackground(Color.LIGHT_GRAY);
					buttonStart.setEnabled(false);
				}
				catch (final IOException e) {
					e.printStackTrace();
				}
			}
		});
		buttonStop.addActionListener(new ActionListener() {
			public void actionPerformed(final ActionEvent anActionEvent) {
				try {
					final Writer writer =
						ProxyDisk.getInstance().fileAbsoluteOutput(
							aTraceFileName,
							true);
					writer.write("# MARKER STOP: ");
					writer.write(textField.getText());
					writer.write('\n');
					writer.close();

					buttonStart.setBackground(Color.GREEN);
					buttonStart.setEnabled(true);
					buttonStop.setBackground(Color.LIGHT_GRAY);
					buttonStop.setEnabled(false);
				}
				catch (final IOException e) {
					e.printStackTrace();
				}
			}
		});

		this.getContentPane().setLayout(
			new BoxLayout(this.getContentPane(), BoxLayout.Y_AXIS));
		this.getContentPane().add(textField);
		this.getContentPane().add(buttonStart);
		this.getContentPane().add(buttonStop);
		this.pack();

		final Dimension uiDimension = this.getSize();
		final Dimension screenDimension =
			Toolkit.getDefaultToolkit().getScreenSize();
		//	this.setLocation((int) (screenDimension.getWidth() - uiDimension
		//		.getWidth()) / 2, (int) (screenDimension.getHeight() - uiDimension
		//		.getHeight()) / 2);
		this.setLocation(
			(int) (screenDimension.getWidth() - uiDimension.getWidth()),
			(int) (screenDimension.getHeight() - uiDimension.getHeight()));
	}
}
