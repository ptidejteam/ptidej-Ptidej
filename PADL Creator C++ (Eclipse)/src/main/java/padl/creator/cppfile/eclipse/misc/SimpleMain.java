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
package padl.creator.cppfile.eclipse.misc;

import java.awt.Dimension;
import java.awt.Toolkit;
import javax.swing.JButton;
import javax.swing.JFrame;
import util.io.ProxyConsole;

public class SimpleMain {
	public static void main(final String[] args) {
		System.out.println("SimpleMain");
		ProxyConsole.getInstance().normalOutput().println("SimpleMain");

		final JFrame frame = new JFrame("SimpleMain");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().add(
			new JButton(ProxyConsole.class.getClassLoader().toString()));
		final Dimension screenSize =
			Toolkit.getDefaultToolkit().getScreenSize();
		frame.setLocation(
			(screenSize.width - 200) / 2,
			(screenSize.height - 100) / 2);
		frame.setSize(200, 100);
		frame.setVisible(true);
	}
}
