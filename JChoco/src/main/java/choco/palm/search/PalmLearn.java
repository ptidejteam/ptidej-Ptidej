//     ___  ___         PaLM library for Choco system
//    /__ \/ __\        (c) 2001 - 2004 -- Narendra Jussien
//   ____\ |/_____
//  /_____\/_____ \     Explanation based constraint programming tool
// |/   (_)(_)   \|
//       \ /            Version 0.1
//       \ /            January 2004
//       \ /
// ______\_/_______     Contibutors: Franeois Laburthe, Hadrien Cambazard, Guillaume Rochart...

package choco.palm.search;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import choco.Constraint;
import choco.ContradictionException;
import choco.palm.PalmProblem;
import choco.palm.explain.PalmConstraintPlugin;

public class PalmLearn extends PalmAbstractSolverTool {
	public boolean checkAcceptable(final List constraints) {
		return true;
	}

	public boolean checkAcceptableRelaxation(final Constraint constraint) {
		return true;
	}

	public void learnFromContradiction(final choco.palm.explain.Explanation expl)
			throws ContradictionException {
	}

	public void learnFromRemoval(final Constraint constraint) {
	}

	public ArrayList sortConstraintToUndo(
		final choco.palm.explain.Explanation expl) {
		final PalmProblem pb = (PalmProblem) this.getManager().getProblem();
		final ArrayList list = new ArrayList();
		final BitSet bset = expl.toBitSet(); // iterate on the bitset and avoid HashSet !!!!!!
		for (int i = bset.nextSetBit(0); i >= 0; i = bset.nextSetBit(i + 1)) {
			final Constraint ct = pb.getConstraintNb(i);
			if (((PalmConstraintPlugin) ct.getPlugIn()).getWeight() == 0) {
				list.add(ct);
			}
		}
		// We assume that all decision constraints use the same comparator defined by the user
		if (!list.isEmpty()) {
			final Comparator Comp =
				((PalmConstraintPlugin) ((Constraint) list.get(0)).getPlugIn())
					.getSearchInfo()
					.getComparator();
			Collections.sort(list, Comp);
		}
		return list;
	}

}
