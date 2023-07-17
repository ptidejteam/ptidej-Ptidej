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
package ptidej.viewer.widget;

import java.awt.Cursor;
import java.awt.event.ActionEvent;
import javax.swing.Action;
import javax.swing.Icon;
import javax.swing.JMenuItem;
import ptidej.viewer.ui.DesktopFrame;

public class MenuItem extends JMenuItem {
	private static final long serialVersionUID = 1L;

	public MenuItem() {
	}

	public MenuItem(Icon icon) {
		super(icon);
	}

	public MenuItem(String text) {
		super(text);
	}

	public MenuItem(Action a) {
		super(a);
	}

	public MenuItem(String text, Icon icon) {
		super(text, icon);
	}

	public MenuItem(String text, int mnemonic) {
		super(text, mnemonic);
	}

	protected void fireActionPerformed(final ActionEvent anEvent) {
		DesktopFrame.getInstance().setCursor(
			Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));

		super.fireActionPerformed(anEvent);

		DesktopFrame.getInstance().setCursor(Cursor.getDefaultCursor());
	}
}
