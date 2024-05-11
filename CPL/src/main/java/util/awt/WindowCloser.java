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
package util.awt;

/**
 * Utility class to catch window close events 
 * on a target window and to dispose the window.
 */

import java.awt.Window;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

public class WindowCloser implements WindowListener {
	private final boolean exitOnClose;

	/**
	 * Create an adaptor to listen for window closing events
	 * on the given window and actually perform the close.
	 */
	public WindowCloser(final Window w) {
		this(w, false);
	}
	/**
	 * Create an adaptor to listen for window closing events
	 * on the given window and actually perform the close.
	 * If "exitOnClose" is true we do a System.exit on close.
	 */
	public WindowCloser(final Window w, final boolean exitOnClose) {
		this.exitOnClose = exitOnClose;
		w.addWindowListener(this);
	}
	public void windowActivated(final WindowEvent e) {
	}
	public void windowClosed(final WindowEvent e) {
		if (this.exitOnClose) {
			System.exit(0);
		}
	}
	public void windowClosing(final WindowEvent e) {
		if (this.exitOnClose) {
			System.exit(0);
		}
		e.getWindow().dispose();
	}
	public void windowDeactivated(final WindowEvent e) {
	}
	public void windowDeiconified(final WindowEvent e) {
	}
	public void windowIconified(final WindowEvent e) {
	}
	public void windowOpened(final WindowEvent e) {
	}
}
