//     ___  ___         PaLM library for Choco system
//    /__ \/ __\        (c) 2001 - 2004 -- Narendra Jussien
//   ____\ |/_____
//  /_____\/_____ \     Explanation based constraint programming tool
// |/   (_)(_)   \|
//       \ /            Version 0.1
//       \ /            January 2004
//       \ /
// ______\_/_______     Contibutors: François Laburthe, Hadrien Cambazard, Guillaume Rochart...

package choco.palm.search;

import java.util.LinkedList;
import java.util.logging.Logger;
import choco.Constraint;
import choco.ContradictionException;
import choco.integer.IntVar;
import choco.palm.PalmProblem;
import choco.palm.integer.constraints.PalmGreaterOrEqualXC;
import choco.palm.integer.constraints.PalmLessOrEqualXC;

/**
 * An optimizing solver.
 */

public class PalmBranchAndBound extends PalmSolver {

	/**
	 * States if the solver is maximizing (or minimizing).
	 */

	protected boolean maximizing = false;

	/**
	 * The variable that should be maximized (or minimized).
	 */

	protected IntVar objective;

	/**
	 * Some bounds for search use.
	 */

	protected int lowerBound = Integer.MIN_VALUE,
			upperBound = Integer.MAX_VALUE;

	/**
	 * Optimum value found during the search.
	 */

	protected int optimum;

	/**
	 * Dynamic cut constraints.
	 */

	protected LinkedList dynamicCuts;

	/**
	 * Creates the solver for the specified problem.
	 */

	public PalmBranchAndBound(
		final PalmProblem pb,
		final IntVar obj,
		final boolean max) {
		super(pb);
		this.objective = obj;
		this.maximizing = max;
		this.lowerBound = obj.getInf();
		this.upperBound = obj.getSup();
		this.dynamicCuts = new LinkedList();
	}

	public int getObjectiveValue() {
		if (this.maximizing) {
			return this.objective.getSup();
		}
		else {
			return this.objective.getInf();
		}
	}

	public void postDynamicCut() {
		final int bv = this.getObjectiveValue();
		Constraint cut;
		try {
			this.reset();
			if (this.maximizing) {
				this.lowerBound = Math.max(this.lowerBound, bv) + 1;
			}
			else {
				this.upperBound = Math.min(this.upperBound, bv) - 1;
			}
			if (this.maximizing) {
				cut = new PalmGreaterOrEqualXC(this.objective, bv + 1);
			}
			else {
				cut = new PalmLessOrEqualXC(this.objective, bv - 1);
			}
			this.dynamicCuts.add(cut);
			this.problem.post(cut);
			try {
				this.problem.propagate();
			}
			catch (final PalmContradiction e) {
				this.repair();
			}
		}
		catch (final ContradictionException e) {
			this.finished = true;
			this.problem.feasible = Boolean.FALSE;
		}
	}

	public void run() {
		try {
			this.problem
				.post(this.problem.geq(this.objective, this.lowerBound));
			this.problem
				.post(this.problem.leq(this.objective, this.upperBound));
			Logger.getLogger("choco.palm").info("Initial Propagation");
			this.problem.propagate();
		}
		catch (final ContradictionException e) {
			Logger.getLogger("choco.palm").info("Optimality proven");
			this.finished = true;
			this.problem.feasible = Boolean.FALSE;
		}
		this.problem.feasible = Boolean.TRUE;
		while (this.problem.feasible.booleanValue()) {
			Logger.getLogger("choco.palm").info("Searching for one solution");
			this.runonce();
			this.storeSolution();
			if (this.problem.feasible.booleanValue()) {
				this.postDynamicCut();
			}
		}
	}

	/**
	 * solving the feasibility problem
	 * same code as in run(PalmSolver) in order to be able
	 * to store consecutive solutions ...
	 */
	public void runonce() {
		final long time = System.currentTimeMillis();
		try {
			while (!this.finished) {
				try {
					Logger.getLogger("choco.palm").fine(
						"New extension launched.");
					this.extend();
					this.problem.propagate();
				}
				catch (final PalmContradiction e) {
					this.repair();
				}
			}
			this.problem.feasible = Boolean.TRUE;
		}
		catch (final ContradictionException e) {
			this.finished = true;
			this.problem.feasible = Boolean.FALSE;
		}
		this.setRuntimeStatistic(
			PalmProblem.CPU,
			(int) (System.currentTimeMillis() - time));
	}

}
