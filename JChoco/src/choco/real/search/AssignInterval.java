package choco.real.search;

import choco.ContradictionException;
import choco.Problem;
import choco.branch.AbstractBranching;
import choco.branch.IntBranching;
import choco.real.RealMath;
import choco.real.RealVar;
import choco.search.AbstractBinIntBranching;
import choco.search.AbstractGlobalSearchSolver;

/**
 * A binary branching assigning interval to subinterval.
 */

public class AssignInterval extends AbstractBinIntBranching implements
		IntBranching {
	protected RealVarSelector varSelector;

	public AssignInterval(final RealVarSelector varSelector) {
		this.varSelector = varSelector;
	}

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

	public void goDownBranch(final Object x, final int i)
			throws ContradictionException {
		super.goDownBranch(x, i);
		if (i == 1) {
			((RealVar) x).intersect(RealMath.firstHalf((RealVar) x));
			this.manager.problem.propagate();
		}
		else if (i == 2) {
			((RealVar) x).intersect(RealMath.secondHalf((RealVar) x));
			this.manager.problem.propagate();
		}
		else {
			AbstractBranching.logger
				.severe("!! Not a valid value for AssignInterval branching !!");
		}
	}

	public void goUpBranch(final Object x, final int i)
			throws ContradictionException {
		super.goUpBranch(x, i);
	}

	public Object selectBranchingObject() {
		return this.varSelector.selectRealVar();
	}
}
