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
import java.util.Properties;
import javax.swing.AbstractAction;
import ptidej.solver.OccurrenceGenerator;
import ptidej.viewer.ViewerCommons;
import ptidej.viewer.ui.DesktopPane;
import ptidej.viewer.utils.Resources;
import util.help.Browser;

public class Solver4MetricalAction extends AbstractAction {
	private static final long serialVersionUID = 1L;
	private static Solver4MetricalAction UniqueInstance;
	public static Solver4MetricalAction getInstance() {
		return (Solver4MetricalAction.UniqueInstance == null) ? Solver4MetricalAction.UniqueInstance =
			new Solver4MetricalAction()
				: Solver4MetricalAction.UniqueInstance;
	}

	private Solver4MetricalAction() {
	}
	public void actionPerformed(final ActionEvent e) {
		final String action = e.getActionCommand();
		if (action
			.equals(Resources.METRICAL_PTIDEJ_SOLVER_4_FIND_SIMILAR_MICRO_ARCHITECTURE)) {

			final OccurrenceGenerator solutionGenerator =
				OccurrenceGenerator.getInstance();

			final Properties solutions =
				solutionGenerator
					.getOccurrences(
						DesktopPane.getInstance().getPatternName(),
						DesktopPane
							.getInstance()
							.getAbstractRepresentationWindow()
							.getSourceModel(),
						OccurrenceGenerator.VERSION_METRICAL_PTIDEJSOLVER4,
						DesktopPane.getInstance().getSolver(),
						DesktopPane.getInstance().getProblem());

			ViewerCommons.loadConstraintsData(DesktopPane
				.getInstance()
				.getAbstractRepresentationWindow(), solutions);
		}
		else if (action
			.equals(Resources.METRICAL_PTIDEJ_SOLVER_4_SIMILAR_MICRO_ARCHITECTURE_HELP)) {
			Browser
				.displayURL(Resources
					.getLink(Resources.METRICAL_PTIDEJ_SOLVER_4_SIMILAR_MICRO_ARCHITECTURE_HELP));
		}
	}
}
