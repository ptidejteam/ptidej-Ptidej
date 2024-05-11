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
import java.beans.PropertyVetoException;

import javax.swing.AbstractAction;
import javax.swing.JInternalFrame;

import ptidej.viewer.ui.DesktopPane;
import ptidej.viewer.utils.Resources;
import util.io.ProxyConsole;

public class WindowAction extends AbstractAction {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static WindowAction UniqueInstance;
	public static WindowAction getInstance() {
		return (WindowAction.UniqueInstance == null)
			? WindowAction.UniqueInstance =
			new WindowAction() : WindowAction.UniqueInstance;
	}

	private WindowAction() {
	}
	public void actionPerformed(final ActionEvent e) {
		final String action = e.getActionCommand();

		if (action.equals(Resources.MINIMIZE_ALL)) {
			final JInternalFrame[] frames =
				DesktopPane.getInstance().getAllFramesInLayer(
					Resources.PROJECTS_LAYER);
			for (int i = 0; i < frames.length; i++) {
				try {
					frames[i].setIcon(true);
				}
				catch (final PropertyVetoException pve) {
					pve.printStackTrace(ProxyConsole.getInstance().errorOutput());
				}
			}
			DesktopPane.getInstance().revalidate();
		}
		else if (action.equals(Resources.CASCADE)) {
			// TODO: To implement.
		}
		else if (action.equals(Resources.TILE_VERTICALLY)) {
			final JInternalFrame[] frames =
				DesktopPane.getInstance().getAllFramesInLayer(
					Resources.PROJECTS_LAYER);
			final int numberOfWindows = frames.length;
			final int height = DesktopPane.getInstance().getHeight();
			final int width =
				DesktopPane.getInstance().getWidth() / numberOfWindows;
			for (int i = 0; i < numberOfWindows; i++) {
				frames[i].setLocation(width * i, 0);
				frames[i].setSize(width, height);
			}
			DesktopPane.getInstance().revalidate();
		}
		else if (action.equals(Resources.TILE_HORIZONTALLY)) {
			final JInternalFrame[] frames =
				DesktopPane.getInstance().getAllFramesInLayer(
					Resources.PROJECTS_LAYER);
			final int numberOfWindows = frames.length;
			final int height =
				DesktopPane.getInstance().getHeight() / numberOfWindows;
			final int width = DesktopPane.getInstance().getWidth();
			for (int i = 0; i < numberOfWindows; i++) {
				frames[i].setLocation(0, height * i);
				frames[i].setSize(width, height);
			}
			DesktopPane.getInstance().revalidate();
		}
	}
}
