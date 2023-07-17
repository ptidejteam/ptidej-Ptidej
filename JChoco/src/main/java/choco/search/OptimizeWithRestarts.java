// **************************************************
// *                   J-CHOCO                      *
// *   Copyright (C) F. Laburthe, 1999-2003         *
// **************************************************
// *  an open-source Constraint Programming Kernel  *
// *     for Research and Education                 *
// **************************************************

/* File choco.search.OptimizeWithRestarts.java, last modified by Francois 10 nov. 2003 10:28:07 */
package choco.search;

import org.junit.Assert;
import choco.ContradictionException;
import choco.Problem;
import choco.util.Arithm;

public class OptimizeWithRestarts extends AbstractOptimize {
	/**
	 * counting the number of iterations
	 */
	protected int nbIter = 0;

	/**
	 * counting the overall number of solutions
	 */
	protected int baseNbSol = 0;

	/**
	 * total nb of backtracks (all trees in the optimization process)
	 */
	protected int nbBkTot = 0;

	/**
	 * total nb of nodes expanded in all trees
	 */
	protected int nbNdTot = 0;

	public OptimizeWithRestarts(
		final choco.integer.var.IntDomainVar obj,
		final boolean maximize) {
		super(obj, maximize);
	}

	private void endLoop() {
		/*
		    -> let t := time_get() in
		       trace(SVIEW,"Optimisation over => ~A(~A) = ~S found in ~S iterations, [~S], ~S ms.\n",
		              (if a.doMaximize "max" else "min"),a.objective.name,
		              getBestObjectiveValue(a),  // v1.013 using the accessor
		              a.nbIter,a.limits,t)]
		*/
	}

	/**
	 * called before a new search tree is explored
	 */
	public void endTreeSearch() {
		for (int i = 0; i < this.limits.size(); i++) {
			final AbstractGlobalSearchLimit lim =
				(AbstractGlobalSearchLimit) this.limits.get(i);
			lim.reset(false);
		}
		this.problem.worldPopUntil(this.baseWorld);
	}

	// should we call a fullReset on limits ? (to reset cumulated counter?)
	private void newLoop() {
		this.initBounds();
		// time_set()
	}

	public void newTreeSearch() throws ContradictionException {
		super.newTreeSearch();
		this.nbIter = this.nbIter + 1;
		this.baseNbSol = this.nbSolutions;
		this.postTargetBound();
		this.problem.propagate();
	}

	/**
	 * loop until the lower bound equals the upper bound
	 * @return
	 */
	private boolean oneMoreLoop() {
		return this.lowerBound < this.upperBound;
	}

	private void recordNoSolution() {
		// (trace(SVIEW,"... no solution with ~A:~S [~S]\n",obj.name,objtgt,a.limits),
		if (this.doMaximize) {
			this.upperBound =
				Arithm.min(this.upperBound, this.getObjectiveTarget() - 1);
		}
		else {
			this.lowerBound =
				Arithm.max(this.lowerBound, this.getObjectiveTarget() + 1);
		}
	}

	public void run() {
		final int w = this.problem.getWorldIndex() + 1;
		final Problem pb = this.problem;
		boolean finished = false;
		this.newLoop();
		try {
			pb.propagate();
		}
		catch (final ContradictionException e) {
			finished = true;
			this.recordNoSolution();
		}
		if (!finished) {
			pb.worldPush();
			while (this.oneMoreLoop()) {
				boolean foundSolution = false;
				try {
					this.newTreeSearch();
					if (this.mainGoal.explore(1)) {
						foundSolution = true;
					}
				}
				catch (final ContradictionException e) {
				}
				this.endTreeSearch();
				if (!foundSolution) {
					this.recordNoSolution();
				}
			}
			Assert.assertTrue(this.problem.getWorldIndex() == w);
			this.problem.worldPop();
		}
		this.endLoop();
		if (this.maxNbSolutionStored > 0 && this.existsSolution()) {
			this.restoreBestSolution();
		}
	}

}
