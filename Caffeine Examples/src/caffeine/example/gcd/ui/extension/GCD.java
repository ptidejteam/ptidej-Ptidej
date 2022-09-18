/*******************************************************************************
 * Copyright (c) 2002-2014 Yann-Gaël Guéhéneuc and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/old-licenses/gpl-2.0.html
 * 
 * Contributors:
 *     Yann-Gaël Guéhéneuc and others, see in file; API and its implementation
 ******************************************************************************/
package caffeine.example.gcd.ui.extension;

import javax.swing.JInternalFrame;
import javax.swing.JTextField;
import javax.swing.event.InternalFrameAdapter;
import javax.swing.event.InternalFrameEvent;

public final class GCD extends JInternalFrame {
	private static final long serialVersionUID = -2222451761439949549L;

	public static final int X_ORIGIN = 450;
	public static final int Y_ORIGIN = 10;
	public static final int WIDTH = 300;
	public static final int HEIGHT = 200;

	private static int gcdNumber = -1;

	// This field plays part in a composition relationship:
	// The composition relationship between one instance of
	// class GCD and one instance of class Thread.
	private final Thread thread;
	private final int number;

	public GCD() {
		super("", false, true, true, true);

		this.number = this.hashCode();
		GCD.gcdNumber++;

		this.setTitle(this.getName());
		this.setBounds(
			GCD.X_ORIGIN - 100 * GCD.gcdNumber,
			GCD.Y_ORIGIN + 100 * GCD.gcdNumber,
			GCD.WIDTH,
			GCD.HEIGHT);
		this.addInternalFrameListener(new InternalFrameAdapter() {
			public void internalFrameClosing(InternalFrameEvent e) {
				GCD.this.dispose();
			}
		});
		final JTextField text = new JTextField("");
		this.getContentPane().add(text);
		this.setVisible(true);

		this.thread = new Thread(new Runnable() {
			public void run() {
				int a = GCD.this.number;
				int b = a;
				final StringBuffer buffer = new StringBuffer();
				while (!GCD.this.isClosed()) {
					buffer.setLength(0);
					buffer.append(text.getText());
					buffer.append("\n\rGCD(");
					buffer.append(a);
					buffer.append(", ");
					buffer.append(b);
					buffer.append(") = ");
					buffer.append(GCD.computeGCD(a, b));
					text.setText(buffer.toString());
					a = a + 1;
					b = b + 4;
				}
			}
		});
		this.thread.setName(this.getName());
		this.thread.setPriority(Thread.MIN_PRIORITY);
		this.thread.start();
	}
	public static int computeGCD(int a, int b) {
		if (b == 0) {
			return a;
		}
		else {
			return GCD.computeGCD(b, a % b);
		}
	}
	public String getName() {
		return "GCD: " + this.number;
	}
}