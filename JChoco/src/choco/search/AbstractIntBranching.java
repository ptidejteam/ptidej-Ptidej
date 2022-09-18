package choco.search;

import java.util.logging.Level;
import choco.ContradictionException;
import choco.branch.AbstractBranching;
import choco.branch.IntBranching;

// **************************************************
// *                   J-CHOCO                      *
// *   Copyright (C) F. Laburthe, 1999-2003         *
// **************************************************
// *  an open-source Constraint Programming Kernel  *
// *     for Research and Education                 *
// **************************************************

/* File choco.search.AbstractIntBranching.java, last modified by flaburthe 20 oct. 2003 12:00:27 */

/**
 * An abstract class for all implementations of branching objets (objects controlling the tree search)
 */
public abstract class AbstractIntBranching extends AbstractBranching implements
		IntBranching {

	/**
	 * a method performing the exploration at the choice point level
	 */
	public boolean explore(final int n) {
		final Object v = this.selectBranchingObject();
		if (v != null) {
			try {
				return this.branchOn(v, n);
			}
			catch (final ContradictionException e) {
				return false;
			}
		}
		else if (this.nextBranching != null) {
			return ((IntBranching) this.nextBranching).explore(n);
		}
		else {
			this.manager.recordSolution();
			this.manager.showSolution();
			return this.manager.stopAtFirstSol;
		}
	}

	public void goDownBranch(final Object x, final int i)
			throws ContradictionException {
		if (AbstractBranching.logger.isLoggable(Level.FINE)) {
			final int n = this.manager.problem.getEnvironment().getWorldIndex();
			if (n <= this.manager.loggingMaxDepth) {
				AbstractBranching.logger.fine("!! world " + n + ": on " + x
						+ " down branch " + i);
			}
		}
	}

	public void goUpBranch(final Object x, final int i)
			throws ContradictionException {
		if (AbstractBranching.logger.isLoggable(Level.FINE)) {
			final int n = this.manager.problem.getEnvironment().getWorldIndex();
			if (n <= this.manager.loggingMaxDepth) {
				AbstractBranching.logger.fine("!! world " + n + ": on " + x
						+ " up branch " + i);
			}
		}
	}
}
