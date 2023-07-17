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
package pom.operators;

/**
 * @author Farouk ZAIDI
 * @since  2004/01/31
 *
 * The class Operators uses the Singleton pattern.
 * It allows operations on sets.
 * The class to describe a set is HashSet (from package java.util). 
 */

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;

public final class Operators {
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

	private Operators() {
	}

	/**
	 * Returns a new Collection that is the union of the two sets.
	 * The method uses the method <code>addAll</code> to copy Collections A and B.
	 * @param setA
	 * @param setB
	 * @return the union of A and B
	 */
	public Collection union(final Collection setA, final Collection setB) {
		final Collection resultOfUnion = new ArrayList();

		resultOfUnion.addAll(setA);
		resultOfUnion.addAll(setB);

		return resultOfUnion;
	}

	/**
	 * Returns a new Collection that is the intersection of the two sets
	 * 
	 * @param setA
	 * @param setB
	 * @return the intersection of A and B
	 */
	public Collection intersection(
		final Collection setA,
		final Collection setB) {

		final Collection resultOfIntersection = new HashSet(setA);

		resultOfIntersection.retainAll(setB);

		return resultOfIntersection;
	}

	/**
	 * Returns the cardinality of A
	 * 
	 * @param setA
	 * @return the cardinality of the set
	 */
	public int size(final Collection setA) {
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
	public boolean equal(final Collection setA, final Collection setB) {
		if (setA.size() != setB.size()) {
			return false;
		}
		return setA.containsAll(setB) && setB.containsAll(setA);
	}

	/**
	 * Returns if A and B are different.
	 * 
	 * @param setA
	 * @param setB
	 * @see Operators.equals
	 * @return if A and B are different.
	 */
	public boolean different(final Collection setA, final Collection setB) {
		return !this.equal(setA, setB);
	}

	/**
	 * Returns if the set contains the specified element
	 * 
	 * @param element
	 * @param set
	 * @return true if the set contains the specified element, otherwise false
	 */
	public boolean belongTo(final Object element, final Collection set) {
		return set.contains(element);
	}

	/**
	 * Returns if a subset is included or equal into another set.
	 *  
	 * @param subCollection
	 * @param aCollection
	 * @return true if the subset is included or equal to the set, otherwise false
	 */
	public boolean includeOrEqual(
		final Collection subCollection,
		final Collection aCollection) {

		return aCollection.containsAll(subCollection);
	}

	/**
	 * Returns if a subset is included into another set. 
	 * 
	 * @param subCollection
	 * @param aCollection
	 * @return true if the subset is included to the set, otherwise false
	 */
	public boolean include(
		final Collection subCollection,
		final Collection aCollection) {

		return aCollection.containsAll(subCollection)
			&& !subCollection.containsAll(aCollection);
	}

	/**
	 * Returns a new Collection that is the difference between A and B (A - B)
	 * It is important to know that the operation is not commutative.
	 * First, the Collection A is copied into a new Collection. Then, the method <code>removeAll</code>
	 * removes elements that are common with B.
	 * 
	 * @param setA
	 * @param setB
	 * @return the difference of A and B
	 */
	public Collection difference(
		final Collection aCollectionA,
		final Collection aCollectionB) {

		final Collection result = new ArrayList();

		result.addAll(aCollectionA);
		result.removeAll(aCollectionB);

		return result;
	}

	/**
	 * @param Collection
	 * @param entity
	 * @return CollectionA - CollectionB
	 */
	public Collection difference(
		final Iterator anIteratorOnCollectionA,
		final Collection aCollectionB) {

		final Collection result = new HashSet();

		while (anIteratorOnCollectionA.hasNext()) {
			final Object object = anIteratorOnCollectionA.next();
			if (!aCollectionB.contains(object)) {
				result.add(object);
			}
		}

		return result;
	}
}
