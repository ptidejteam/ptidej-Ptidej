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

import java.util.Collections;
import choco.Constraint;

/**
 * A repairing algorithm.
 */

public class PalmRepair extends PalmAbstractSolverTool {

	/**
	 * Selects a decision to undo for repairing a contradiction. In this default implementation, it
	 * selects the minimal constraints in the provided explain w.r.t. the BetterConstraintComparator
	 * order.
	 * @param expl The explain of the contradiction.
	 */

	public Constraint selectDecisionToUndo(
		final choco.palm.explain.Explanation expl) {
		return (Constraint) Collections.min(
			expl.toSet(),
			new choco.palm.explain.BetterConstraintComparator());
	}
}
