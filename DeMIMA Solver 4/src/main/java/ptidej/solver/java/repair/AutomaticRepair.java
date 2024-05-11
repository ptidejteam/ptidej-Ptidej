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

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

import util.io.ProxyConsole;
import choco.AbstractConstraint;
import choco.ContradictionException;
import choco.integer.IntVar;
import choco.palm.explain.BetterConstraintComparator;
import choco.palm.explain.Explanation;
import choco.palm.explain.PalmConstraintPlugin;
import choco.palm.prop.PalmEngine;
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
public class AutomaticRepair extends MemoryRepair {
	private boolean areAllConstraintsRelaxed;
	private Branching jPtidejBranching = null;

	public AutomaticRepair(final Problem problem) {
		super(problem);
		this.areAllConstraintsRelaxed = false;
	}

	public ArrayList[] ptidejSelectDecisionToUndo(
		final Explanation anExplanation) throws PalmContradiction {

		if (this.jPtidejBranching == null) {
			this.jPtidejBranching =
				((Solver) this.getManager()).getNextBranching();
		}

		final ArrayList[] results = { new ArrayList(), new ArrayList() };
		final ArrayList removedConstraints = this.getRemovedConstraints();
		final StringBuffer message = new StringBuffer();
		final StringBuffer xCommand = new StringBuffer();

		// Yann 2007/02/27: Best!
		// I compute the best constraint to be changed
		// in a smarter way... Taking care of dealing
		// with assignations first...
		final ArrayList overConstrainingConstraints =
			new ArrayList(anExplanation.toSet());
		Collections.sort(
			overConstrainingConstraints,
			new BetterConstraintComparator());
		AbstractConstraint newBestConstraint = null;
		final Iterator iterator = overConstrainingConstraints.iterator();
		while (iterator.hasNext() && newBestConstraint == null) {
			final AbstractConstraint ct = (AbstractConstraint) iterator.next();
			if (!(ct instanceof Constraint)
					|| (ct instanceof Constraint && ((Constraint) ct)
						.getNextConstraint() != null)) {

				newBestConstraint = ct;
			}
		}
		// If there was no assignation and no constraint
		// with a next weaker one, I then grab the first
		// one to be there...
		if (newBestConstraint == null) {
			newBestConstraint =
				(AbstractConstraint) overConstrainingConstraints.get(0);
		}

		//	final AbstractConstraint oldBestConstraint =
		//		(AbstractConstraint) Collections.min(
		//			overConstrainingConstraints,
		//			new BetterConstraintComparator());
		//
		//	System.out.println(oldBestConstraint);
		//	System.out.println(newBestConstraint);
		//	System.out.println();

		final AbstractConstraint constraint = newBestConstraint;
		// If the weight of the constconstraintraint is 0, I just relax it.
		PalmConstraintPlugin plug =
			(PalmConstraintPlugin) constraint.getPlugIn();
		if (plug.getWeight() == 0) {
			results[0].add(constraint);
		}
		else if (constraint != null) {
			int solutionWeight = 0;

			// I add to the current message the message of the
			// previously removed constraints.
			for (int i = 0; i < removedConstraints.size(); i++) {
				final Constraint ct = (Constraint) removedConstraints.get(i);
				plug = (PalmConstraintPlugin) ct.getPlugIn();
				if (plug.getWeight() > 0) {
					message.append("# Removed constraint: ");
					message.append(ct.getSymbol());
					message.append(": ");
					message.append(ct.getName());
					message.append('\n');

					solutionWeight += plug.getWeight();
				}
			}

			if (!this.areAllConstraintsRelaxed) {
				// I remove the ligthtest constraint
				// I only relaxed those constraints
				// that are user defined
				plug = (PalmConstraintPlugin) constraint.getPlugIn();
				if (plug.getWeight() < this.getProblem().getMaxRelaxLevel()) {

					final Constraint currentConstraint =
						(Constraint) constraint;
					final String nextConstraint =
						currentConstraint.getNextConstraint();
					if (nextConstraint != null) {
						// I instantiate the next constraint using reflection.
						final ArrayList args = new ArrayList();
						args.add(currentConstraint.getName());
						args.add(currentConstraint.getXCommand());
						for (int j = 0; j < constraint.getNbVars(); j++) {
							args.add((IntVar) constraint.getVar(j));
						}
						args.add(Integer.valueOf(plug.getWeight()));
						args.add(currentConstraint.getApproximations());

						final Class nClass =
							currentConstraint
								.getNextConstraintConstructor(nextConstraint);
						final Constructor[] constructors =
							nClass.getConstructors();
						final Constructor constr = constructors[0];
						Constraint nextCt = null;
						try {
							nextCt =
								(Constraint) constr.newInstance(args.toArray());
						}
						catch (IllegalArgumentException e) {
							e.printStackTrace(ProxyConsole
								.getInstance()
								.errorOutput());
						}
						catch (InstantiationException e) {
							e.printStackTrace(ProxyConsole
								.getInstance()
								.errorOutput());
						}
						catch (IllegalAccessException e) {
							e.printStackTrace(ProxyConsole
								.getInstance()
								.errorOutput());
						}
						catch (InvocationTargetException e) {
							e.printStackTrace(ProxyConsole
								.getInstance()
								.errorOutput());
						}
						((PalmConstraintPlugin) nextCt.getPlugIn())
							.setWeight(plug.getWeight());

						message.append("# Removed constraint: ");
						message.append(currentConstraint.getSymbol());
						message.append(": ");
						message.append(currentConstraint.getName());
						message
							.append("\n# Constraint replaced with this weaker constraint:\n#\t");
						message.append(nextCt.getSymbol());
						message.append(": ");
						message.append(nextCt.getName());
						message.append('\n');

						results[0].add(constraint);
						results[1].add(nextCt);
					}

					xCommand.append(currentConstraint.getXCommand());
				}
				else {
					// I finished relaxing all possible constraints.
					// Now, I put them back one after the other.
					this.areAllConstraintsRelaxed = true;
				}

				this.jPtidejBranching.setMessage(message.toString());
				this.jPtidejBranching.setWeight(solutionWeight);
				this.jPtidejBranching.setXCommand(xCommand.toString());
			}
		}
		//	else {
		//		// I finished relaxing all possible constraints.
		//		// Now, I put them back one after the other.
		//		this.areAllConstraintsRelaxed = true;
		//	}

		if (this.areAllConstraintsRelaxed) {
			// I deal with the (re)addition of constraints.
			// I start with the weakest constraint
			// because the last one that I have removed it's 
			// the constraint of max weight. 

			if (removedConstraints.size() > 0) {
				Collections.sort(
					removedConstraints,
					new BetterConstraintComparator());
				// Constraints led to contradictions.
				// I only add to the user-relaxed constraint list those constraints
				// that are defined by the user (weight > 0).
				plug =
					(PalmConstraintPlugin) ((AbstractConstraint) removedConstraints
						.get(0)).getPlugIn();
				if (plug.getWeight() > 0) {
					results[1].add((AbstractConstraint) removedConstraints
						.get(0));
					removedConstraints.remove(0);
				}
			}
		}

		// The search stops if no constraint was 
		// withdrawn or added to the current problem.
		if (results[0].size() == 0 && results[1].size() == 0) {
			final PalmEngine pe =
				(PalmEngine) this.getProblem().getPropagationEngine();
			try {
				pe.raiseSystemContradiction();
			}
			catch (final ContradictionException e) {
				this.getProblem().getWriter().close();
				//	System.out.println("No more solution found");
				// e.printStackTrace();
			}
		}

		// TODO: Why do we get a contradiction when we propagate added constraint?
		return results;
	}
}
