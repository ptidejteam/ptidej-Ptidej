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

import ptidej.viewer.awt.entities.StatisticsPanel;
import ptidej.viewer.event.SourceAndGraphModelEvent;
import ptidej.viewer.ui.AbstractExternalWindow;
import ptidej.viewer.ui.DesktopPane;
import ptidej.viewer.utils.Resources;

/**
 * @author Yann-Gaël Guéhéneuc
 * @since  2007/06/13
 */
public final class ModelStatisticsWindow extends AbstractExternalWindow {
	private static final long serialVersionUID = 1L;
	private final StatisticsPanel statisticsPanel;

	public ModelStatisticsWindow() {
		super("Model Statistics");

		this.setResizable(true);
		this.setBounds(50, 50, 500, 300);
		DesktopPane.getInstance().setLayer(this, Resources.PROJECTS_LAYER);

		this.statisticsPanel = StatisticsPanel.getInstance();
		this.getContentPane().add(this.statisticsPanel);
	}
	public void sourceModelAvailable(
		final SourceAndGraphModelEvent aSourceModelEvent) {

		this.statisticsPanel.sourceModelAvailable(aSourceModelEvent);
	}
	public void sourceModelChanged(
		final SourceAndGraphModelEvent aSourceModelEvent) {

		this.statisticsPanel.sourceModelChanged(aSourceModelEvent);
	}
	public void sourceModelUnavailable() {
		this.statisticsPanel.sourceModelUnavailable();
	}
}
