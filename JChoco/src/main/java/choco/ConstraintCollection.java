package choco;

import java.util.Collection;

// **************************************************
// *                   J-CHOCO                      *
// *   Copyright (C) F. Laburthe, 1999-2003         *
// **************************************************
// *  an open-source Constraint Programming Kernel  *
// *     for Research and Education                 *
// **************************************************

/**
 * An interface for handling collections (sets/sequences) of constraints.
 * This is useful for representing explanations, states, paths in a search tree, and so on.
 */
public interface ConstraintCollection {
	/**
	 * Adds a new constraint in the explain.
	 * @param constraint The constraint that should be added to the explain.
	 * It must be a <code>PalmConstraint</code>.
	 */

	void add(Constraint constraint);

	/**
	 * Adds several constraints at a time
	 * @param collection The set of constraints
	 */

	void addAll(Collection collection);

	/**
	 * Deletes all indirect constraints.
	 */

	void clear();

	/**
	 * test if a constraint is in the collection
	 */
	boolean contains(Constraint ct);

	/**
	 * test inclusion
	 */
	boolean containsAll(ConstraintCollection collec);

	/**
	 * Clones the collection as a new one.
	 */
	ConstraintCollection copy();

	/**
	 * Deletes a constraint from the explain.
	 * @param constraint The constraint that must be removed.
	 */

	void delete(Constraint constraint);

	/**
	 * Checks if the explain is empty (that is wether the size of the set is null).
	 */

	boolean isEmpty();

	/**
	 * Merges an explain with the current one.
	 * @param collection The collection of constraints that must be added to this
	 */
	void merge(ConstraintCollection collection);

	/**
	* return the size of the bitSet
	*/

	int size();
}
