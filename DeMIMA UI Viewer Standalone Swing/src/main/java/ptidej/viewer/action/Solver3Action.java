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
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Properties;

import javax.swing.AbstractAction;

import padl.kernel.IAbstractModel;
import padl.visitor.IWalker;
import ptidej.solver.claire.DomainGenerator3AC4;
import ptidej.solver.claire.DomainGenerator3Custom;
import ptidej.solver.claire.OccurrenceGenerator;
import ptidej.viewer.ViewerCommons;
import ptidej.viewer.ui.DesktopFrame;
import ptidej.viewer.ui.DesktopPane;
import ptidej.viewer.utils.Resources;
import ptidej.viewer.utils.Utils;
import util.help.Browser;
import util.io.ProxyConsole;

public class Solver3Action extends AbstractAction {
	private static final long serialVersionUID = 1L;
	private static Solver3Action UniqueInstance;

	public static Solver3Action getUniqueInstance() {
		return (Solver3Action.UniqueInstance == null)
				? Solver3Action.UniqueInstance = new Solver3Action()
				: Solver3Action.UniqueInstance;
	}

	private Solver3Action() {
	}

	public void actionPerformed(final ActionEvent e) {
		final String action = e.getActionCommand();

		if (action.equals(Resources.PROBLEM_AC4)) {
			DesktopPane.getInstance()
					.setProblem(OccurrenceGenerator.PROBLEM_AC4);
		}
		else if (action.equals(Resources.PROBLEM_CUSTOM)) {
			DesktopPane.getInstance()
					.setProblem(OccurrenceGenerator.PROBLEM_CUSTOM);
		}
		else if (action.equals(Resources.GENERATE_PROGRAM_MODEL)) {
			final IAbstractModel abstractModel = DesktopPane.getInstance()
					.getAbstractRepresentationWindow().getSourceModel();

			// I generate the Claire code corresponding to the current model.
			final IWalker generator;
			if (DesktopPane.getInstance()
					.getProblem() == OccurrenceGenerator.PROBLEM_AC4) {
				generator = new DomainGenerator3AC4();
			}
			else {
				generator = new DomainGenerator3Custom();
			}
			abstractModel.walk(generator);

			// Where should I save the model?
			final File file = Utils.saveFile(DesktopFrame.getInstance(),
					"../Ptidej Tests/ptidej/Examples/",
					"Choose Ptidej Solver domain file", "cl",
					"Ptidej Solver domain file");
			if (file == null) {
				return;
			}
			else {
				try {
					// TODO Use ProxyDisk
					final FileWriter writer = new FileWriter(file);
					writer.write(generator.getResult().toString());
					writer.close();
				}
				catch (final IOException ioe) {
					ioe.printStackTrace(
							ProxyConsole.getInstance().errorOutput());
				}
			}
		}
		else if (action.equals(Resources.GENERATE_SOLVER_EXECUTION_DATA)) {

			final IAbstractModel abstractModel = DesktopPane.getInstance()
					.getAbstractRepresentationWindow().getSourceModel();

			final OccurrenceGenerator solutionGenerator = OccurrenceGenerator
					.getInstance();
			solutionGenerator.generatePtidejSolver3ExecutionData(
					DesktopPane.getInstance().getPatternName(),
					DesktopPane.getInstance().getPattern(), abstractModel,
					DesktopPane.getInstance().getSolver(),
					DesktopPane.getInstance().getProblem());
		}
		else if (action.equals(
				Resources.PTIDEJ_SOLVER_3_FIND_SIMILAR_MICRO_ARCHITECTURE)) {

			final IAbstractModel abstractModel = DesktopPane.getInstance()
					.getAbstractRepresentationWindow().getSourceModel();

			final OccurrenceGenerator solutionGenerator = OccurrenceGenerator
					.getInstance();

			final Properties solutions = solutionGenerator.getOccurrences(
					DesktopPane.getInstance().getPatternName(), abstractModel,
					OccurrenceGenerator.VERSION_PTIDEJSOLVER3,
					DesktopPane.getInstance().getSolver(),
					DesktopPane.getInstance().getProblem());

			ViewerCommons.loadConstraintsData(
					DesktopPane.getInstance().getAbstractRepresentationWindow(),
					solutions);
		}
		else if (action.equals(
				Resources.PTIDEJ_SOLVER_3_SIMILAR_MICRO_ARCHITECTURE_HELP)) {

			Browser.displayURL(Resources.getLink(
					Resources.PTIDEJ_SOLVER_3_SIMILAR_MICRO_ARCHITECTURE_HELP));
		}
	}
}
