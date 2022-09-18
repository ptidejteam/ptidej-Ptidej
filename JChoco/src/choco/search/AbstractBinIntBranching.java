package choco.search;

import org.junit.Assert;
import choco.ContradictionException;
import choco.branch.IntBranching;

// **************************************************
// *                   J-CHOCO                      *
// *   Copyright (C) F. Laburthe, 1999-2003         *
// **************************************************
// *  an open-source Constraint Programming Kernel  *
// *     for Research and Education                 *
// **************************************************

/* File choco.search.AbstractBinIntBranching.java, last modified by flaburthe 20 oct. 2003 10:59:19 */

public abstract class AbstractBinIntBranching extends AbstractIntBranching {
	public boolean explore(final int n) {
		final AbstractGlobalSearchSolver algo = this.manager;
		final Object x = this.selectBranchingObject();
		if (null != x) {
			try {
				return this.branchOn(x, n);
			}
			catch (final ContradictionException e) {
				return false;
			}
		}
		else if (null != this.nextBranching) {
			return ((IntBranching) this.nextBranching).explore(n);
		}
		else {
			algo.recordSolution();
			algo.showSolution();
			return algo.stopAtFirstSol;
		}
	}

	public boolean finishedBranching(final Object x, final int i) {
		return i == 2;
	}

	public int getFirstBranch(final Object x) {
		return 1;
	}

	public int getNextBranch(final Object x, final int i) {
		Assert.assertTrue(i == 1);
		return 2;
	}
}
