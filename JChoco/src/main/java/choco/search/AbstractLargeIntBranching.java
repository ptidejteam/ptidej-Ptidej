// **************************************************
// *                   J-CHOCO                      *
// *   Copyright (C) F. Laburthe, 1999-2003         *
// **************************************************
// *  an open-source Constraint Programming Kernel  *
// *     for Research and Education                 *
// **************************************************

/* File choco.search.AbstractLargeIntBranching.java, last modified by Francois 22 oct. 2003 00:19:06 */
package choco.search;

import choco.ContradictionException;
import choco.Problem;

public abstract class AbstractLargeIntBranching extends AbstractIntBranching {

	public boolean branchOn(final Object x, final int n)
			throws ContradictionException {
		final AbstractGlobalSearchSolver algo = this.manager;
		final Problem pb = algo.problem;
		boolean nodeSuccess = false;
		boolean nodeFinished = false;
		int i = this.getFirstBranch(x);
		algo.newTreeNode();
		try {
			do {
				boolean branchSuccess = false;
				try {
					pb.getPropagationEngine().checkCleanState();
					pb.getEnvironment().worldPush();
					this.goDownBranch(x, i);
					if (this.explore(n + 1)) {
						branchSuccess = true;
					}
				}
				catch (final ContradictionException e) {
					;
				}
				if (!branchSuccess) {
					pb.getEnvironment().worldPop();
				}
				algo.endTreeNode();
				algo.postDynamicCut();
				this.goUpBranch(x, i);
				if (branchSuccess) {
					nodeSuccess = true;
				}
				if (!this.finishedBranching(x, i)) {
					i = this.getNextBranch(x, i);
				}
				else {
					nodeFinished = true;
				}
			}
			while (!nodeSuccess && !nodeFinished);
		}
		catch (final ContradictionException e) {
			nodeSuccess = false;
		}
		return nodeSuccess;
	}

}
