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

import java.util.BitSet;
import java.util.Set;
import choco.Constraint;
import choco.ConstraintCollection;
import choco.palm.integer.PalmIntVar;
import choco.palm.integer.explain.DecSupExplanation;
import choco.palm.integer.explain.IncInfExplanation;
import choco.palm.integer.explain.RemovalExplanation;

/**
 * Explanation interface.
 */

public interface Explanation extends ConstraintCollection {

	/**
	 * Checks if the explain is valid, that is wether all the constraint are active.
	 */

	public boolean isValid();

	/**
	 * Makes a DecSupExplanation from the current explain by adding dependencies.
	 * @param sup The previous value of the bound.
	 * @param var The involved variable.
	 */

	public DecSupExplanation makeDecSupExplanation(int sup, PalmIntVar var);

	/**
	 * Makes an IncInfExplanation from the current explain by adding dependencies.
	 * @param inf The previous value of the bound.
	 * @param var The involved variable.
	 */

	public IncInfExplanation makeIncInfExplanation(int inf, PalmIntVar var);

	/**
	 * Makes a RemovalExplanation from the current explain by adding dependencies.
	 * @param value The removed value of the domain.
	 * @param var The involved variable.
	 */

	public RemovalExplanation makeRemovalExplanation(int value, PalmIntVar var);

	/**
	 * yet another form of merging two collection of constraints
	 * @param set a collection of constraints represented by a BitSet
	 */
	public void merge(BitSet set);

	/**
	 * Posts a restoration prop.
	 * @param constraint
	 */

	public void postUndoRemoval(Constraint constraint);

	/**
	 * Copies the explain set and returns the new bitset.
	 * @return The explain as a BitSet.
	 */

	public BitSet toBitSet();

	/**
	 * Creates a set with all the constraints in the explain..
	 * @return The explain as a set.
	 */

	public Set toSet();

}
