//     ___  ___         PaLM library for Choco system
//    /__ \/ __\        (c) 2001 - 2004 -- Narendra Jussien
//   ____\ |/_____
//  /_____\/_____ \     Explanation based constraint programming tool
// |/   (_)(_)   \|
//       \ /            Version 0.1
//       \ /            January 2004
//       \ /
// ______\_/_______     Contibutors: Franeois Laburthe, Hadrien Cambazard, Guillaume Rochart...

package choco.palm.integer.explain;

import java.util.BitSet;
import choco.Constraint;
import choco.Problem;
import choco.palm.integer.PalmIntVar;

public class RemovalExplanation extends choco.palm.explain.GenericExplanation {
	protected int value;
	protected PalmIntVar variable;

	public RemovalExplanation(
		final Problem pb,
		final BitSet explanation,
		final int value,
		final PalmIntVar variable) {
		super(pb);
		this.value = value;
		this.explanation = explanation;
		this.variable = variable;
	}

	public void postUndoRemoval(final Constraint removed) {
		this.removeDependencies(removed);
		this.variable.restoreVal(this.value);
		this.variable.resetExplanationOnVal(this.value);
	}

	public String toString() {
		return this.variable + " != " + this.value + " because "
				+ super.toString();
	}
}
