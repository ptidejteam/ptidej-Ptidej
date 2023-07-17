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
package ptidej.viewer.ui.panel;

import java.awt.BorderLayout;

import javax.swing.JPanel;

import ptidej.viewer.utils.Resources;
import ptidej.viewer.widget.EmbeddedPanel;

public class AntiPatternPanel extends JPanel {
	private static final long serialVersionUID = 1L;

	public AntiPatternPanel() {
		final EmbeddedPanel panel = new EmbeddedPanel();

		panel.addCollapsablePanel(
			Resources.DESIGN_SMELLS,
			AntiPatternPanel.class,
			new DesignSmellChoicePanel());
		
		panel.addCollapsablePanel(
			Resources.DESIGN_SMELLS_ACTION,
			AntiPatternPanel.class,
			new AntiPatternActionPanel());
		
		this.setLayout(new BorderLayout());
		this.add(panel, BorderLayout.NORTH);
	}
}
