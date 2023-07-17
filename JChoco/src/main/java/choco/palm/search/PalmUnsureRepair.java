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

import java.util.ArrayList;
import choco.AbstractConstraint;
import choco.Constraint;

public class PalmUnsureRepair extends PalmRepair {

	public Constraint selectDecisionToUndo(
		final choco.palm.explain.Explanation expl) {
		final PalmSolver solver = this.getManager();
		final PalmLearn learner = solver.getLearning();
		final ArrayList constraints = learner.sortConstraintToUndo(expl);
		final int nbCandidates = constraints.size();
		int idx_ct_out = 0;
		boolean foundCandidate = false;
		if (!constraints.isEmpty()) {
			AbstractConstraint ct_out = (AbstractConstraint) constraints.get(0);
			foundCandidate = learner.checkAcceptableRelaxation(ct_out);
			while (idx_ct_out < nbCandidates & !foundCandidate) {
				idx_ct_out++;
				ct_out = (AbstractConstraint) constraints.get(idx_ct_out);
				foundCandidate = learner.checkAcceptableRelaxation(ct_out);
			}
			//((PathRepairLearn) learner).debugMemory();
			if (foundCandidate) {
				return ct_out;
			}
			else {
				return null;
			}
		}
		else {
			return null;
		}
	}

}
