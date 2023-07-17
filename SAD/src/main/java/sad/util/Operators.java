/*******************************************************************************
 * Copyright (c) 2001-2014 Yann-Gaël Guéhéneuc and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/old-licenses/gpl-2.0.html
 * 
 * Contributors:
 *     Yann-Gaël Guéhéneuc and others, see in file; API and its implementation
 ******************************************************************************/
package sad.util;

/**
 * @author Naouel MOHA
 * @since  2005/12/05
 *
 * The class Operators uses the Singleton pattern.
 * It allows operations on sets.
 * The class to describe a set is HashSet (from package java.util). 
 */

import java.util.HashSet;
import java.util.Set;

public final class Operators {

	/**
	 * UNIQUE_INSTANCE is the unique possible Operators object.
	 */
	private static Operators UniqueInstance;

	/**
	 *  Returns an unique instance of the Operators class
	 * @return the unique instance of a Singleton
	 */
	public static Operators getInstance() {
		if (Operators.UniqueInstance == null) {
			Operators.UniqueInstance = new Operators();
		}
		return Operators.UniqueInstance;
	}

	/**
	 * Constructor
	 */
	private Operators() {
		// empty block
	}

	/**
	 * Returns a new set that is the union of the two sets.
	 * The method uses the method <code>addAll</code> to copy sets A and B.
	 * @param setA
	 * @param setB
	 * @return the union of A and B
	 */
	public Set union(final Set setA, final Set setB) {
		final Set resultOfUnion = new HashSet();

		resultOfUnion.addAll(setA);
		resultOfUnion.addAll(setB);

		return resultOfUnion;
	}

	/**
	 * Returns a new set that is the intersection of the two sets
	 * 
	 * @param setA
	 * @param setB
	 * @return the intersection of A and B
	 */
	public Set intersection(final Set setA, final Set setB) {
		final Set resultOfIntersection = new HashSet(setA);

		resultOfIntersection.retainAll(setB);

		return resultOfIntersection;
	}

	/**
	 * Returns a new set that is the difference between A and B (A - B)
	 * It is important to know that the operation is not commutative.
	 * First, the set A is copied into a new set. Then, the method <code>removeAll</code>
	 * removes elements that are common with B.
	 * 
	 * @param setA
	 * @param setB
	 * @return the difference of A and B
	 */
	public Set difference(final Set setA, final Set setB) {
		final Set resultOfIntersection = new HashSet(setA);

		resultOfIntersection.removeAll(setB);

		return resultOfIntersection;
	}

	/**
	 * Returns the cardinality of A
	 * 
	 * @param setA
	 * @return the cardinality of the set
	 */
	public int size(final Set setA) {
		return setA.size();
	}

	/**
	 * Returns if A and B are equal.
	 * If A and B have the same size and contain the same objects, A and B are equal.
	 *  Does not take into account an order relation.
	 * 
	 * @param setA
	 * @param setB
	 * @return if A and B are equal
	 */
	public boolean equal(final Set setA, final Set setB) {
		if (setA.size() != setB.size()) {
			return false;
		}
		return setA.containsAll(setB) && setB.containsAll(setA);
	}

	/**
	 * Returns if A and B are different. {@link #equals}
	 * 
	 * @param setA
	 * @param setB
	 * 
	 * @return if A and B are different.
	 */
	public boolean different(final Set setA, final Set setB) {
		return !this.equal(setA, setB);
	}

	/**
	 * Returns if the set contains the specified element
	 * 
	 * @param element
	 * @param set
	 * @return true if the set contains the specified element, otherwise false
	 */
	public boolean belongTo(final Object element, final Set set) {
		return set.contains(element);
	}

	/**
	 * Returns if a subset is included or equal into another set.
	 *  
	 * @param subSet
	 * @param aSet
	 * @return true if the subset is included or equal to the set, otherwise false
	 */
	public boolean includeOrEqual(final Set subSet, final Set aSet) {
		return aSet.containsAll(subSet);
	}

	/**
	 * Returns if a subset is included into another set. 
	 * 
	 * @param subSet
	 * @param aSet
	 * @return true if the subset is included to the set, otherwise false
	 */
	public boolean include(final Set subSet, final Set aSet) {
		return aSet.containsAll(subSet) && !subSet.containsAll(aSet);
	}

	/**
	 * @param setA
	 * @param setB
	 */
	public Set minus(final Set setA, final Set setB) {
		final Set result = new HashSet();
		result.addAll(setA);
		result.removeAll(setB);
		return result;
	}
}
