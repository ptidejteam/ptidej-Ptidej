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

import ptidej.ui.layout.repository.InheritanceClusterLayout;
import ptidej.ui.layout.repository.InheritanceDepthLayout;
import ptidej.ui.layout.repository.SimpleLayout;
import ptidej.ui.layout.repository.SugiyamaLayout;
import ptidej.viewer.ui.AbstractRepresentationWindow;
import ptidej.viewer.ui.DesktopPane;
import ptidej.viewer.utils.Resources;

public class GraphLayoutAction extends AbstractAction {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static GraphLayoutAction UniqueInstance;
	public static GraphLayoutAction getInstance() {
		return (GraphLayoutAction.UniqueInstance == null)
			? GraphLayoutAction.UniqueInstance =
			new GraphLayoutAction() : GraphLayoutAction.UniqueInstance;
	}

	private GraphLayoutAction() {
	}
	public void actionPerformed(final ActionEvent anActionEvent) {
		final AbstractRepresentationWindow window =
			DesktopPane.getInstance().getAbstractRepresentationWindow();
		if (window != null) {
			final String action = anActionEvent.getActionCommand();
			if (action.equals(Resources.INHERITANCE_CLUSTERING_LAYOUT)) {
				window.setGraphLayout(new InheritanceClusterLayout());
			}
			else if (
				action.equals(
					Resources.INHERITANCE_DEPTH_CLUSTERING_LAYOUT)) {

				window.setGraphLayout(new InheritanceDepthLayout());
			}
			else if (action.equals(Resources.SIMPLE_LAYOUT)) {
				window.setGraphLayout(new SimpleLayout());
			}
			else if (action.equals(Resources.SUGIYAMA_LAYOUT)) {
				window.setGraphLayout(new SugiyamaLayout());
			}
		}
	}
}
