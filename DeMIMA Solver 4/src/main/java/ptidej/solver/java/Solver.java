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
package ptidej.solver.java;

import java.util.ArrayList;
import java.util.logging.Logger;
import choco.AbstractConstraint;
import choco.Constraint;
import choco.ContradictionException;
import choco.integer.constraints.AbstractIntConstraint;
import choco.palm.PalmProblem;
import choco.palm.explain.Explanation;
import choco.palm.explain.PalmConstraintPlugin;
import choco.palm.integer.PalmIntDomain;
import choco.palm.integer.PalmIntVar;
import choco.palm.prop.PalmEngine;
import choco.palm.search.DecisionConstraint;
import choco.palm.search.PalmContradiction;
import choco.palm.search.PalmLearn;
import choco.palm.search.PalmRepair;
import choco.palm.search.PalmSolver;
import choco.palm.search.PalmState;

/**
 * Writen in CLAIRE by
 * @author Yann-Gaël Guéhéneuc
 * Translated and adapted from CLAIRE version to JAVA by
 * @author Iyadh Sidhom
 * @author Salim Bensemmane
 * @author Fayeal Skhiri
 */
public class Solver extends PalmSolver {
	public Solver(final Problem pb) {
		super(pb);
	}
	public void repair() throws ContradictionException {
		final Problem pb = (Problem) this.getProblem();
		final PalmEngine pe = (PalmEngine) pb.getPropagationEngine();
		final PalmState state = this.getState();
		final PalmLearn learner = this.getLearning();

		if (pe.isContradictory()) {
			final Explanation expl =
				((PalmProblem) this.getProblem()).makeExplanation();

			final PalmIntVar cause = (PalmIntVar) pe.getContradictionCause();

			cause.self_explain(PalmIntDomain.DOM, expl);
			Logger.getLogger("choco.palm").fine("Repairing");
			Logger.getLogger("choco.palm").fine("Cause : " + cause);
			Logger.getLogger("choco.palm").fine("Expl : " + expl);

			pe.setContradictory(false);
			learner.learnFromContradiction(expl);

			if (expl.isEmpty()) {
				pe.raiseSystemContradiction();
			}
			else {
				// First, I deal with the removal of a constraint.
				final ArrayList[] re =
					((Repair) this.getRepair())
						.ptidejSelectDecisionToUndo(expl);

				if (re != null) {
					boolean hasAnyConstraintBeenRemoved = false;
					for (int i = 0; i < re[0].size(); i++) {
						final AbstractConstraint ct =
							(AbstractConstraint) re[0].get(i);
						final PalmConstraintPlugin plug =
							(PalmConstraintPlugin) ct.getPlugIn();

						if (plug.getWeight() < pb.getMaxRelaxLevel()) {
							this.incRuntimeStatistic(PalmProblem.RLX, 1);
							if (plug.getWeight() <= 0) {
								// An enumeration constraint.
								// I need to maintain the current search state.
								state.removeDecision(ct);
							}
							else {
								// I save the relaxed constraint.
								((Repair) this.getRepair()).remove(ct);
							}
							// I remove the constraint from the problem.
							pb.remove(ct);
							hasAnyConstraintBeenRemoved = true;
						}
					}
					//					if (re[1].size() > 0)
					//					{
					//						hasAnyConstraintBeenRemoved = true;
					//					}

					// If no constraint has been removed, this mean
					// that there is no solution to the problem:	
					// Whatever we do, we will not get a solution.
					// Then, I need to raise a system contradiction
					// to stop any further computations.
					if (!hasAnyConstraintBeenRemoved) {
						pe.raiseSystemContradiction();
					}
					//if (re[0].size() > 0)
					//{
					try {
						// Over-propagated!
						// We need to propagate now in case the
						// problem is still over-constrained
						// despite the removed contraints.

						pb.propagate();
					}
					catch (final PalmContradiction e) {
						this.repair();
					}
					//}

					// I negate the constraint.
					for (int i = 0; i < re[0].size(); i++) {
						Explanation temporaryExplanation = expl;
						final AbstractConstraint ct =
							(AbstractConstraint) re[0].get(i);
						final PalmConstraintPlugin plug =
							(PalmConstraintPlugin) ct.getPlugIn();
						if (plug.getWeight() == 0) {
							temporaryExplanation = (Explanation) expl.copy();
							temporaryExplanation.delete(ct);
							final Constraint negCt =
								((DecisionConstraint) ct).negate();
							if (negCt != null) {
								if (temporaryExplanation.isValid()) {
									temporaryExplanation.clear();
									pb.post(negCt, temporaryExplanation);
								}
							}
						}
					}

					// I deal with the addition of constraints.
					for (int i = 0; i < re[1].size(); i++) {
						final AbstractIntConstraint ct =
							(AbstractIntConstraint) re[1].get(i);
						final PalmConstraintPlugin plug =
							(PalmConstraintPlugin) ct.getPlugIn();
						pb.post(ct, plug.getWeight());
					}

					// I propagate the negation and addition of the constraint.
					try {
						pb.propagate();
					}
					catch (final PalmContradiction e) {
						this.repair();
					}
				}
				else {
					pe.raiseSystemContradiction();
				}
			}
		}
	}
	public PalmRepair getRepair() {
		return this.repairing;
	}
	public Branching getNextBranching() {
		return (Branching) this.extending.getBranching().getNextBranching();
	}
}
