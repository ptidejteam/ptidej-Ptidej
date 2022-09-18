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

public class DesignMotifPanel extends JPanel {
	private static final long serialVersionUID = 1L;

	public DesignMotifPanel() {
		final EmbeddedPanel panel = new EmbeddedPanel();
		this.setLayout(new BorderLayout());
		this.add(panel, BorderLayout.NORTH);

		panel.addCollapsablePanel(
			Resources.DESIGN_MOTIFS,
			DesignMotifPanel.class,
			new DesignMotifChoicePanel());
		panel.addCollapsablePanel(
			Resources.PTIDEJ_SOLVERS,
			DesignMotifPanel.class,
			new SolverGeneralPanel());
		panel.addCollapsablePanel(
			Resources.PTIDEJ_SOLVER_3,
			DesignMotifPanel.class,
			new Solver3Panel());
		panel.addCollapsablePanel(
			Resources.PTIDEJ_SOLVER_4,
			DesignMotifPanel.class,
			new Solver4Panel());
		panel.addCollapsablePanel(
			Resources.PTIDEJ_METRICAL_SOLVER_4,
			DesignMotifPanel.class,
			new MetricalSolver4Panel());
		panel.addCollapsablePanel(
			Resources.PTIDEJ_EPI,
			DesignMotifPanel.class,
			new EPIPanel());
	}
}
