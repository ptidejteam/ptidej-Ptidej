//     ___  ___         PaLM library for Choco system
//    /__ \/ __\        (c) 2001 - 2004 -- Narendra Jussien
//   ____\ |/_____
//  /_____\/_____ \     Explanation based constraint programming tool
// |/   (_)(_)   \|
//       \ /            Version 0.1
//       \ /            January 2004
//       \ /
// ______\_/_______     Contibutors: François Laburthe, Hadrien Cambazard, Guillaume Rochart...

package choco.palm.integer.explain;

import java.util.BitSet;
import choco.Constraint;
import choco.Problem;
import choco.palm.integer.PalmIntVar;

public class IncInfExplanation extends BoundExplanation {
	public IncInfExplanation(
		final Problem pb,
		final BitSet explanation,
		final int previousValue,
		final PalmIntVar variable) {
		super(pb);
		this.explanation = explanation;
		this.previousValue = previousValue;
		this.variable = variable;
	}

	public void postUndoRemoval(final Constraint removed) {
		this.removeDependencies(removed);
		this.variable.restoreInf(this.previousValue);
	}

	public String toString() {
		return this.variable + ".inf > " + this.previousValue + " because "
				+ super.toString();
	}
}
