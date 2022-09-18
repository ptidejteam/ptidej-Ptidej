//     ___  ___         PaLM library for Choco system
//    /__ \/ __\        (c) 2001 - 2004 -- Narendra Jussien
//   ____\ |/_____
//  /_____\/_____ \     Explanation based constraint programming tool
// |/   (_)(_)   \|
//       \ /            Version 0.1
//       \ /            January 2004
//       \ /
// ______\_/_______     Contibutors: François Laburthe, Hadrien Cambazard, Guillaume Rochart...

package choco.palm.explain;

import choco.Constraint;

public class PalmControlConstraint {
	/**
	 * The controlling constraint.
	 */

	Constraint constraint;

	/**
	 * The index of the controlled contraint in the controlling one.
	 */

	int index;

	/**
	 * Creates a control constraint whit the specified constraint as controlling one.
	 * @param constraint The controlling constraint.
	 * @param index The index of the controlled one.
	 */

	public PalmControlConstraint(final Constraint constraint, final int index) {
		this.constraint = constraint;
		this.index = index;
	}

	/**
	 * Gets the controlling constraint.
	 */

	public Constraint getConstraint() {
		return this.constraint;
	}

	/**
	 * Gets the index of the controlled constraint in the controlling one.
	 */

	public int getIndex() {
		return this.index;
	}
}
