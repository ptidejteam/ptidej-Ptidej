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

import choco.Problem;
import choco.palm.integer.PalmIntVar;

public abstract class BoundExplanation extends
		choco.palm.explain.GenericExplanation {
	int previousValue;
	PalmIntVar variable;

	public BoundExplanation(final Problem pb) {
		super(pb);
	}

	public int getPreviousValue() {
		return this.previousValue;
	}

	public PalmIntVar getVariable() {
		return this.variable;
	}
}
