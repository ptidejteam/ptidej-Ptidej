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

import java.util.List;
import choco.ContradictionException;
import choco.branch.AbstractBranching;
import choco.palm.PalmProblem;

/**
 * An extension algorithm for Palm solvers.
 */

public class PalmExtend extends PalmAbstractSolverTool {

	/**
	 * A branching or a linked list of branchings for solving the problem.
	 */

	protected PalmAbstractBranching branching;

	/**
	 * Extension algorithm. In this default one, it selects a branchiong item. If an not null item is returned,
	 * te propagates decisions on this item, else, it launchs the same algorithm with the next branching,
	 * if available..
	 * @param branching
	 * @throws ContradictionException
	 */

	public void explore(final PalmAbstractBranching branching)
			throws ContradictionException {
		final Object item = branching.selectBranchingObject();
		if (item != null) {
			((PalmProblem) this.manager.getProblem())
				.propagateAllDecisionsConstraints((List) branching
					.selectFirstBranch(item));
		}
		else {
			final AbstractBranching br = branching.getNextBranching();
			if (br != null) {
				this.explore((PalmAbstractBranching) br);
			}
			else {
				this.manager.setFinished(true);
			}
		}
	}

	/**
	 * Gets the branching used by this extension.
	 */

	public PalmAbstractBranching getBranching() {
		return this.branching;
	}

	/**
	 * Sets the branching used by this extension.
	 */

	public void setBranching(final PalmAbstractBranching branching) {
		this.branching = branching;
	}
}
