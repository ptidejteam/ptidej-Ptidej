package choco.mem;

// **************************************************
// *                   J-CHOCO                      *
// *   Copyright (C) F. Laburthe, 1999-2003         *
// **************************************************
// *  an open-source Constraint Programming Kernel  *
// *     for Research and Education                 *
// **************************************************

/* File choco.mem.TwoStateIntVector.java, last modified by flaburthe 14 oct. 2003 10:01:52 */

/**
 * A class that implements a vector of integers with two states (one current and one single historical state)
 *  TODO: implement all those darn methods
 */
public class TwoStateIntVector implements IStateIntVector {
	/**
	 * Minimal capacity of a vector
	 */
	public static final int MIN_CAPACITY = 8;

	/**
	 *
	 * @param env the memory environment
	 * @param initialSize the initial size of the vector
	 * @param initialValue the initial value for all entries of the vector
	 */
	public TwoStateIntVector(
		final Environment env,
		final int initialSize,
		final int initialValue) {
	}

	/**
	 * Adds a new search at the end of the vector.
	 * @param i The search to add.
	 */

	public void add(final int i) {
	}

	/**
	 * Returns the <code>index</code>th element of the vector.
	 */

	public int get(final int index) {
		return 0;
	}

	/**
	 * Checks if the vector is empty.
	 */

	public boolean isEmpty() {
		return false;
	}

	/**
	 * removes the search at the end of the vector.
	 * does nothing when called on an empty vector
	 */

	public void removeLast() {
	}

	public void restoreState() {
	}

	public void saveState() {
	}

	/**
	 * Assigns a new value <code>val</code> to the element <code>index</code>.
	 */

	public int set(final int index, final int val) {
		return 0;
	}

	/**
	 * Returns the current size of the stored search vector.
	 */

	public int size() {
		return 0;
	}
}
