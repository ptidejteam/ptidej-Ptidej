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

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JDesktopPane;
import javax.swing.JFrame;

public final class ExtendedGCD {
	public static final int X_ORIGIN = 100;
	public static final int Y_ORIGIN = 100;
	public static final int WIDTH = 800;
	public static final int HEIGHT = 500;

	public ExtendedGCD() {
		super();
	}

	public static void main(final String[] args) {
		final JFrame frame =
			new JFrame("Association, aggregation, and composition relationships demonstrated");
		frame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
		frame.setBounds(
			ExtendedGCD.X_ORIGIN,
			ExtendedGCD.Y_ORIGIN,
			ExtendedGCD.WIDTH,
			ExtendedGCD.HEIGHT);

		final JDesktopPane layeredPane = new JDesktopPane();
		frame.getContentPane().add("Center", layeredPane);

		final GCDController gcdController = new GCDController();
		layeredPane.add(gcdController);

		// Now that all the components belong to a container,
		// all the components know their parents. I can
		// initialize the GCDController instance.
		gcdController.initialize();

		// I show the window.
		frame.setVisible(true);
	}
}