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
package ptidej.solver.java.repair;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

import choco.AbstractConstraint;
import choco.palm.explain.Explanation;
import choco.palm.explain.PalmConstraintPlugin;
import choco.palm.search.PalmContradiction;
import ptidej.solver.java.Branching;
import ptidej.solver.java.Constraint;
import ptidej.solver.java.Problem;
import ptidej.solver.java.Solver;

/**
 * Writen in CLAIRE by
 * @author Yann-Gaël Guéhéneuc
 * Translated and adapted from CLAIRE version to JAVA by
 * @author Iyadh Sidhom
 * @author Salim Bensemmane
 * @author Fayeal Skhiri
 */
public class SimpleAutomaticRepair extends NoMemoryRepair {
	private String solutionMessage = "# Solution with all constraints.";
	private int percentage = 100;
	private String xCommand =
		"XCommand = System.out.println(\"No source transformation needed.\");";
	private Branching jPtidejBranching = null;

	public SimpleAutomaticRepair(final Problem problem) {
		super(problem);
	}

	public ArrayList[] ptidejSelectDecisionToUndo(Explanation expl)
			throws PalmContradiction {
		if (this.jPtidejBranching == null) {

			this.jPtidejBranching =
				((Solver) this.getManager()).getNextBranching();
		}

		//array of 2 arrayList of abstractConstraint	
		ArrayList[] re = { new ArrayList(), new ArrayList() };
		AbstractConstraint ct =
			(AbstractConstraint) Collections.min(
				expl.toSet(),
				new choco.palm.explain.BetterConstraintComparator());
		PalmConstraintPlugin plug1 = (PalmConstraintPlugin) ct.getPlugIn();
		if (plug1.getWeight() == 0) {
			re[0].add(ct);

		}
		else {

			ArrayList cts = new ArrayList();
			Iterator iterator = (expl.toSet()).iterator();
			while (iterator.hasNext()) {
				cts.add((AbstractConstraint) iterator.next());

			}
			Collections.sort(
				cts,
				new choco.palm.explain.BetterConstraintComparator());

			final int lightestConstraint = 0;
			PalmConstraintPlugin plug =
				(PalmConstraintPlugin) ((AbstractConstraint) cts
					.get(lightestConstraint)).getPlugIn();
			Problem pb = (Problem) (this.getManager().getProblem());
			//if not a negation Constraint
			if (plug.getWeight() != Integer.MAX_VALUE) {

				if (plug.getWeight() < pb.getMaxRelaxLevel()) {
					this.percentage =
						(this.percentage * plug.getWeight()) / 100;

					if ((((Constraint) cts.get(lightestConstraint))
						.getXCommand()).length() > 0) {
						this.xCommand =
							((Constraint) cts.get(lightestConstraint))
								.getXCommand();
					}
					else {
						this.xCommand =
							"XCommand = System.out.println(\"No source transformation needed.\");";
					}
					this.solutionMessage =
						"# More solution without constraint "
								+ lightestConstraint
								+ ": "
								+ ((Constraint) cts.get(lightestConstraint))
									.getName() + " of weight "
								+ plug.getWeight() + '\n';
					re[0].add((AbstractConstraint) cts.get(lightestConstraint));

					this.jPtidejBranching.setMessage(this.solutionMessage);
					this.jPtidejBranching.setWeight(this.percentage);
					this.jPtidejBranching.setXCommand(this.xCommand);
				}
				else {
					pb.getWriter().close();
					throw new PalmContradiction();

				}
			}

		}
		return re;
	}

}
