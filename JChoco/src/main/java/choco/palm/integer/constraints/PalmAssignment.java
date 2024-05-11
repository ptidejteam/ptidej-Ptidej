//     ___  ___         PaLM library for Choco system
//    /__ \/ __\        (c) 2001 - 2004 -- Narendra Jussien
//   ____\ |/_____
//  /_____\/_____ \     Explanation based constraint programming tool
// |/   (_)(_)   \|
//       \ /            Version 0.1
//       \ /            January 2004
//       \ /
// ______\_/_______     Contibutors: Franeois Laburthe, Hadrien Cambazard, Guillaume Rochart...

package choco.palm.integer.constraints;

import choco.Constraint;
import choco.integer.IntVar;
import choco.palm.integer.PalmIntVar;
import choco.palm.search.DecisionConstraint;

/**
 * Created by IntelliJ IDEA.
 * User: grochart
 * Date: 26 ao?t 2003
 * Time: 10:47:40
 * To change this template use Options | File Templates.
 */
public class PalmAssignment extends PalmEqualXC implements DecisionConstraint {
	public PalmAssignment(final IntVar v0, final int cste) {
		super(v0, cste);
	}

	public Constraint negate() {
		final PalmIntVar v = (PalmIntVar) this.v0;
		return v.getNegDecisionConstraint(this.cste);
		/*if (v.hasEnumeratedDomain())
		  return v.getNegnConstraint(this.cste);
		else
		  return v.getNegEnumerationConstraint(this.cste); */
	}
}
