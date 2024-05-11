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
package ptidej.viewer.ui.window;

import ptidej.ui.event.GraphEvent;
import ptidej.viewer.awt.entities.ListPanel;
import ptidej.viewer.event.SourceAndGraphModelEvent;
import ptidej.viewer.ui.AbstractExternalWindow;
import ptidej.viewer.ui.DesktopPane;
import ptidej.viewer.utils.Resources;

/**
 * @author Yann-Gaël Guéhéneuc
 * @since  2007/06/29
 */
public final class ListEntitiesWindow extends AbstractExternalWindow {
	private static final long serialVersionUID = 1L;
	private final ListPanel listPanel;

	public ListEntitiesWindow() {
		super("List of Entities");

		this.setResizable(true);
		this.setBounds(50, 50, 500, 500);
		DesktopPane.getInstance().setLayer(this, Resources.PROJECTS_LAYER);

		this.listPanel = ListPanel.getInstance();
		this.getContentPane().add(this.listPanel);
	}
	public void graphModelAvailable(
		final SourceAndGraphModelEvent aSourceModelEvent) {

		this.listPanel.graphModelAvailable(aSourceModelEvent);
	}
	public void graphModelChanged(
		final SourceAndGraphModelEvent aSourceModelEvent) {

		this.listPanel.graphModelChanged(aSourceModelEvent);
	}
	public void graphModelUnavailable() {
		this.listPanel.graphModelUnavailable();
	}
	public void constituentSelected(final GraphEvent aGraphEvent) {
		this.listPanel.constituentSelected(aGraphEvent);
	}
	public void constituentUnSelected(final GraphEvent aGraphEvent) {
		this.listPanel.constituentUnSelected(aGraphEvent);
	}
}
