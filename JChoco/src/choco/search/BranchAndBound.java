// **************************************************
// *                   J-CHOCO                      *
// *   Copyright (C) F. Laburthe, 1999-2003         *
// **************************************************
// *  an open-source Constraint Programming Kernel  *
// *     for Research and Education                 *
// **************************************************

/* File choco.search.BranchAndBound.java, last modified by Francois 10 nov. 2003 10:27:32 */
package choco.search;

import choco.ContradictionException;

public class BranchAndBound extends AbstractOptimize {

	public BranchAndBound(
		final choco.integer.var.IntDomainVar obj,
		final boolean maximize) {
		super(obj, maximize);
	}

	public void endTreeSearch() {
		for (int i = 0; i < this.limits.size(); i++) {
			final AbstractGlobalSearchLimit lim =
				(AbstractGlobalSearchLimit) this.limits.get(i);
			lim.reset(false);
		}
		if (this.problem.feasible == Boolean.TRUE) {
			//[SVIEW] solve => ~S sol, best:~S [~S]// a.nbSol,(if a.doMaximize a.lowerBound else a.upperBound),a.limits
		}
		else if (this.problem.feasible == Boolean.FALSE) {
			//[SVIEW] solve => no sol [~S]// a.limits
		}
		else {
			//[SVIEW] solve interrupted before any solution was found [~S]// a.limits
		}
	}

	public void newTreeSearch() {
		this.initBounds();
		for (int i = 0; i < this.limits.size(); i++) {
			final AbstractGlobalSearchLimit lim =
				(AbstractGlobalSearchLimit) this.limits.get(i);
			lim.reset(true);
		}
	}

	/**
	 * we use  targetBound data structures for the optimization cuts
	 */
	public void postDynamicCut() throws ContradictionException {
		this.postTargetBound();
		this.problem.propagate();
	}

}
