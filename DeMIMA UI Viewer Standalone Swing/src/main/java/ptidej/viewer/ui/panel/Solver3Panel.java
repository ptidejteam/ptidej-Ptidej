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

import ptidej.viewer.utils.Resources;
import ptidej.viewer.utils.Controls;
import ptidej.viewer.widget.EmbeddedPanel;
import ptidej.viewer.widget.RadioButton;
public class Solver3Panel extends EmbeddedPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Solver3Panel() {
		super();

		final ButtonGroup bgProblems = new ButtonGroup();
		this.addRadioButton(
			Resources.PROBLEM_AC4,
			Solver3Panel.class,
			Resources.PTIDEJ_SOLVER_3,
			bgProblems,
			true,
			false,
			true);
		this.addRadioButton(
			Resources.PROBLEM_CUSTOM,
			Solver3Panel.class,
			Resources.PTIDEJ_SOLVER_3,
			bgProblems,
			false,
			false,
			true);

		// Yann 2006/07/19: Good start!
		// I make sure at least one of the 
		// radio-button is selected.
		final Enumeration enumeration = bgProblems.getElements();
		if (enumeration.hasMoreElements()) {
			final RadioButton button =
				(RadioButton) enumeration.nextElement();
			button.doClick();
		}

		this.addButton(
			Resources.GENERATE_PROGRAM_MODEL,
			Solver3Panel.class,
			Resources.PTIDEJ_SOLVER_3,
			false,
			false,
			Controls.getInstance().areDesignPatternsListening());
		this.addButton(
			Resources.GENERATE_SOLVER_EXECUTION_DATA,
			Solver3Panel.class,
			Resources.PTIDEJ_SOLVER_3,
			false,
			false,
			Controls.getInstance().areDesignPatternsListening());
		this.addButton(
			Resources.PTIDEJ_SOLVER_3_FIND_SIMILAR_MICRO_ARCHITECTURE,
			Solver3Panel.class,
			Resources.PTIDEJ_SOLVER_3,
			false,
			false,
			Controls.getInstance().areDesignPatternsListening());
	}
}
