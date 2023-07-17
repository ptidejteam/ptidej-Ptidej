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

import java.awt.Component;

import javax.swing.JMenu;
import javax.swing.JMenuItem;

import ptidej.viewer.utils.Resources;

public class Menu extends JMenu {
	private static final long serialVersionUID = 1L;
	private String mnemonic;
	private String icon;

	public Menu() {
	}
	public Menu(final String strKey, final Class aClass) {
		this.setText(Resources.getMenuText(strKey, aClass));
		if (this.hasMnemonic(strKey, aClass)) {
			this.setMnemonic(this.mnemonic.charAt(0));
		}
		if (this.hasIcon(strKey, aClass)) {
			// this.setIcon(SwingUtils.getImageIcon(this.icon, aClass));
		}
	}
	public boolean hasMnemonic(final String strKey, final Class aClass) {
		this.mnemonic = Resources.getMenuMnemonic(strKey, aClass);
		return (this.mnemonic.equals(Resources.NULL_SYM))
			? false
			: true;
	}
	public boolean hasIcon(final String strKey, final Class aClass) {
		this.icon = Resources.getMenuIconPath(strKey, aClass);
		return (this.icon.equals(Resources.NULL_SYM)) ? false : true;
	}
	public boolean isIconNeeded() {
		Component[] peers = ((JMenu) this.getParent()).getComponents();
		for (int i = 0; i < peers.length; i++) {
			if (((JMenuItem) (peers[i])).getIcon() != null)
				return true;
		}
		return false;
	}
}
