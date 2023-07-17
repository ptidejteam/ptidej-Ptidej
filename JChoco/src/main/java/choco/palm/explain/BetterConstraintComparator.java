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

import java.util.Comparator;
import choco.Constraint;

/**
 * Standard Constraint Comparator. For Instance,
 * <code>Collections.min(aCollection, new BetterConstraintComparator());</code> returns the constraint
 * with the smaller weight, or if their weights are equal, the younger constraint
 * (w.r.t. the constraint timestamp).
 */

public class BetterConstraintComparator implements Comparator {

	/**
	 * Compares two <code>PalmConstraint</code>. o1 is less than o2 (returns a negative value) if and only if
	 * (o1.weight < o2.weight) or ( (o1.weight = o2.weight) and (o1.timestamp > o2.timestamp) ).
	 * @param o1 First constraint.
	 * @param o2 Second constraint.
	 * @return A negative value if o1 < o2, a positive value if o1 > o2, 0 else.
	 */

	public int compare(final Object o1, final Object o2) {
		final PalmConstraintPlugin plug1 =
			(PalmConstraintPlugin) ((Constraint) o1).getPlugIn();
		final PalmConstraintPlugin plug2 =
			(PalmConstraintPlugin) ((Constraint) o2).getPlugIn();
		if (plug1.getWeight() < plug2.getWeight()) {
			return -1;
		}
		else if (plug1.getWeight() == plug2.getWeight()) {
			if (plug1.getTimeStamp() > plug2.getTimeStamp()) {
				return -1;
			}
			else if (plug1.getTimeStamp() < plug2.getTimeStamp()) {
				return 1;
			}
			else {
				return 0;
			}
		}
		else {
			return 1;
		}
	}
}
