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
import choco.branch.AbstractBranching;
import choco.branch.ObjectBranching;

/**
 * Abstract class for branching algorithms.
 */

public abstract class PalmAbstractBranching extends AbstractBranching implements
		ObjectBranching {

	/**
	 * The extender the branching which uses this branching.
	 */

	protected PalmExtend extender;

	/**
	 * Checks if decisions are acceptable.
	 * @param csts The decisions that will be taken.
	 */

	public abstract boolean checkAcceptable(List csts);

	/**
	 * Gets the extender which uses this branching.
	 */

	public PalmExtend getExtender() {
		return this.extender;
	}

	/**
	 * Learns from rejection for unacceptable decisions.
	 */

	public abstract void learnFromRejection();

	/*
	 * Computes decisions authorized by the learner that can be taken on the specified item by the solver.
	 * @param var The item the solver branchs on.
	 */
	public List selectAuthorizedDecisions(final Object item) {
		final PalmLearn learner = this.extender.manager.learning;
		List decisionlist = (List) this.getNextBranch(item, null);
		while (!this.checkAcceptable(decisionlist)
				| !learner.checkAcceptable(decisionlist)) {
			this.learnFromRejection();
			decisionlist = (List) this.getNextBranch(item, decisionlist);
		}
		return decisionlist;
	}

	/**
	 * Sets the extender which uses this branching.
	 */

	public void setExtender(final PalmExtend extender) {
		this.extender = extender;
	}

}
