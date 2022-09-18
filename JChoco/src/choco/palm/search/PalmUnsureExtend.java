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

import choco.ContradictionException;
import choco.branch.AbstractBranching;
import choco.palm.PalmProblem;

public class PalmUnsureExtend extends PalmExtend {

	public void explore(final PalmAbstractBranching branching)
			throws ContradictionException {
		final Object item = branching.selectBranchingObject();
		if (item != null) {
			((PalmProblem) this.manager.getProblem())
				.propagateAllDecisionsConstraints(branching
					.selectAuthorizedDecisions(item));
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

}
