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
package caffeine.example.gcd.ui;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JTextField;

import caffeine.Constants;

public final class GCD {
	public GCD() {
		super();
	}
	public int computeGCD(int a, int b) {
		if (b == 0) {
			return a;
		}
		else {
			return this.computeGCD(b, a % b);
		}
	}
	public static void main(final String[] args) {
		if (Constants.DEBUG) {
			System.out.println("GCD: 1");
		}

		final JFrame frame = new JFrame("Euclid's GCD algorithm");
		frame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
		frame.setLocation(400, 100);
		frame.setSize(300, 100);
		final JTextField text = new JTextField("Results");
		frame.getContentPane().add(text);
		frame.setVisible(true);

		if (Constants.DEBUG) {
			System.out.println("GCD: 2");
		}

		int a = 6;
		int b = 18;
		final StringBuffer buffer = new StringBuffer();
		final GCD gcd = new GCD();

		if (Constants.DEBUG) {
			System.out.println("GCD: 3");
		}

		while (true) {
			buffer.setLength(0);
			buffer.append(text.getText());
			buffer.append("\n\rGCD(");
			buffer.append(a);
			buffer.append(", ");
			buffer.append(b);
			buffer.append(") = ");
			buffer.append(gcd.computeGCD(a, b));
			text.setText(buffer.toString());
			a = a + 1;
			b = b + 3;
		}
	}
}