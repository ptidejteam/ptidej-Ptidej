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
import java.util.List;
import java.util.logging.Logger;
import choco.palm.PalmProblem;
import choco.palm.integer.PalmIntVar;

/**
 * A classic implementation of branching algorithm for Palm. It selects the variable with a minimal domain size
 * and tries to instantiate it to the inf bound.
 */

public class PalmAssignVar extends PalmAbstractBranching {

	/**
	 * Not defined in this algorithm.
	 */

	public boolean checkAcceptable(final List csts) {
		System.err
			.println("PalmAssignVar.checkAcceptable(List) should not be not be used !");
		Logger.getLogger("choco.palm").severe(
			"PalmAssignVar.checkAcceptable(List) should not be not be used !");
		return false;
	}

	/**
	 * Not defined in this algorithm.
	 * @param branchingItem
	 * @param previousBranch
	 */

	public Object getNextBranch(
		final Object branchingItem,
		final Object previousBranch) {
		System.err
			.println("PalmAssignVar.getNextDecisions() should not be not be used !");
		Logger.getLogger("choco.palm").severe(
			"PalmAssignVar.getNextDecisions() should not be not be used !");
		return null;
	}

	/**
	 * Not defined in this algorithm.
	 */

	public void learnFromRejection() {
		System.err
			.println("PalmAssignVar.learnFromRejection() should not be not be used !");
		Logger.getLogger("choco.palm").severe(
			"PalmAssignVar.learnFromRejection() should not be not be used !");
	}

	/**
	 * Selects an item to branch on. In this algorithm, the variable with the minimal domain size is choosen.
	 * @return An item to branch on.
	 */

	public Object selectBranchingObject() {
		return ((PalmProblem) this.extender.getManager().getProblem())
			.getMinDomVar();
	}

	/**
	 * Returns a decision to take on the choosen item. In this algorithm, the variable is instantiated to the
	 * inf bound.
	 * @param item The variable (item) involved.
	 * @return The decision the solver should take.
	 */

	public Object selectFirstBranch(final Object item) {
		final List list = new LinkedList();
		final PalmIntVar var = (PalmIntVar) item;
		list.add(var.getDecisionConstraint(var.getInf()));
		return list;
	}
}
