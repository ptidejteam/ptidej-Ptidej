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

import java.util.Enumeration;

import javax.swing.ButtonGroup;
import javax.swing.JRadioButton;

import ptidej.viewer.utils.Resources;
import ptidej.viewer.widget.EmbeddedPanel;

public class SolverGeneralPanel extends EmbeddedPanel {
	private static final long serialVersionUID = 1L;

	public SolverGeneralPanel() {
		super();

		final ButtonGroup bgSolvers = new ButtonGroup();
		this.addRadioButton(
			Resources.SOLVER_PROBLEM_AUTOMATIC,
			SolverGeneralPanel.class,
			Resources.PTIDEJ_SOLVERS,
			bgSolvers,
			true,
			false,
			false);
		this.addRadioButton(
			Resources.SOLVER_COMBINATORIAL_AUTOMATIC,
			SolverGeneralPanel.class,
			Resources.PTIDEJ_SOLVERS,
			bgSolvers,
			false,
			false,
			false);
		this.addRadioButton(
			Resources.SOLVER_SIMPLE_AUTOMATIC,
			SolverGeneralPanel.class,
			Resources.PTIDEJ_SOLVERS,
			bgSolvers,
			false,
			false,
			false);

		// Yann 2006/07/19: Good start!
		// I make sure at least one of the 
		// radio-button is selected.
		final Enumeration enumeration = bgSolvers.getElements();
		if (enumeration.hasMoreElements()) {
			final JRadioButton button =
				(JRadioButton) enumeration.nextElement();
			button.doClick();
		}
	}
}
