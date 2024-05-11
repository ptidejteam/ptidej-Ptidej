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
package ptidej.viewer.action;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import ptidej.viewer.ui.DesktopFrame;
import ptidej.viewer.ui.about.AboutDialog;
import ptidej.viewer.ui.window.HelpWindow;
import ptidej.viewer.utils.Resources;
import ptidej.viewer.utils.SingletonBag;

public class HelpAction extends AbstractAction {
	private static final long serialVersionUID = 1L;
	private static HelpAction UniqueInstance;
	public static HelpAction getInstance() {
		return (HelpAction.UniqueInstance == null)
			? HelpAction.UniqueInstance =
			new HelpAction() : HelpAction.UniqueInstance;
	}

	private HelpAction() {
	}
	public void actionPerformed(final ActionEvent anActionEvent) {
		final String action = anActionEvent.getActionCommand();

		if (action.equals(Resources.ABOUT)) {
			AboutDialog.getUniqueInstance(
				DesktopFrame.getInstance()).setVisible(
				true);
		}
		else if (action.equals(Resources.USAGE)) {
			final HelpWindow window =
				(HelpWindow) SingletonBag.getInstance(HelpWindow.class);
			window.setVisible(true);
		}
	}
}
