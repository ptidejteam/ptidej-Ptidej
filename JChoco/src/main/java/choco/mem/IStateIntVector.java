// *********************************************
// *                   J-CHOCO                 *
// *   Copyright (c) F. Laburthe, 1999-2003    *
// *********************************************
// * Event-base contraint programming Engine   *
// *********************************************

// CVS Information
// File:               $RCSfile: IStateIntVector.java,v $
// Version:            $Revision: 1.1 $
// Last Modification:  $Date: 2004/05/04 22:23:15 $
// Last Contributor:   $Author: guehene $

package choco.mem;

/**
 * Describes an search vector with states (describing some history of the data structure).
 */
public interface IStateIntVector {

	/**
	 * Minimal capacity of a vector
	 */
	public static final int MIN_CAPACITY = 8;

	/**
	 * Adds a new search at the end of the vector.
	 * @param i The search to add.
	 */

	public void add(int i);

	/**
	 * Returns the <code>index</code>th element of the vector.
	 */

	public int get(int index);

	/**
	 * Checks if the vector is empty.
	 */

	public boolean isEmpty();

	/**
	 * removes the search at the end of the vector.
	 * does nothing when called on an empty vector
	 */

	public void removeLast();

	/**
	 * Assigns a new value <code>val</code> to the element <code>index</code>.
	 */

	public int set(int index, int val);

	/**
	 * Returns the current size of the stored search vector.
	 */

	public int size();
}
