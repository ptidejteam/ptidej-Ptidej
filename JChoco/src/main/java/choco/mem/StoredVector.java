// *********************************************
// *                   J-CHOCO                 *
// *   Copyright (c) F. Laburthe, 1999-2003    *
// *********************************************
// * Event-base contraint programming Engine   *
// *********************************************

// CVS Information
// File:               $RCSfile: StoredVector.java,v $
// Version:            $Revision: 1.1 $
// Last Modification:  $Date: 2004/05/04 22:23:15 $
// Last Contributor:   $Author: guehene $

package choco.mem;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Vector;

/**
 * StoredVector implement backtrackable dynamic arrays, without using time-stamping.
 *
 * TODO: Should certainly be reimplemented with an array.
 */
public class StoredVector implements List {
	/**
	 * The current values of the vector
	 */
	private final Vector entries;

	/**
	 * The current number of values stored in the vector
	 */
	private final StoredInt nEntries;

	/**
	 * The main environment object handling all memory management issues
	 */
	public Environment environment;

	/**
	 * The trailing stack holding updates to such {@link StoredVector}
	 */
	private final StoredVectorTrail trail;

	/**
	 * Constructor
	 * @param env The main object handling all memory/backtrack management issues
	 */
	public StoredVector(final Environment env) {
		this.environment = env;
		this.entries = new Vector();
		this.nEntries = new StoredInt(env, 0);
		this.trail =
			(StoredVectorTrail) this.environment
				.getTrail(Environment.VECTOR_TRAIL);
	}

	/**
	 * modifying an entry without storing the former value on the trailing stack
	 * @param o the new entry
	 * @param index the index in the vector
	 * */
	protected void _set(final int index, final Object o) {
		this.entries.set(index, o);
	}

	/**
	 * Adding an objet at a given index of a vector, and shifting all object after it, one index further
	 * @param index the index where the object is to be inserted
	 * @param o the object to be inserted
	 */
	public void add(final int index, final Object o) {
		// TODO
	}

	/**
	 * Adding an object add the end ofs the {@link StoredVector}
	 * @param o the object to be added
	 * @return always true
	 */
	public boolean add(final Object o) {
		this.entries.add(o);
		final int n = this.nEntries.get();
		this.nEntries.set(n + 1);
		return true;
	}

	public boolean addAll(final Collection c) {
		// TODO
		return true;
	}

	public boolean addAll(final int index, final Collection c) {
		// TODO
		return true;
	}

	public void clear() {
		// TODO
	}

	/**
	 * Testing if an object is contained in a {@link StoredVector}
	 * @param o the object that is being looked for
	 * @return true in case the object is present
	 */
	public boolean contains(final Object o) {
		final int idx = this.entries.indexOf(o);
		if (idx >= 0) {
			if (idx <= this.nEntries.get()) {
				return true;
			}
			else {
				return false;
			}
		}
		else {
			return false;
		}
	}

	public boolean containsAll(final Collection c) {
		// TODO
		return true;
	}

	public boolean equals(final Object o) {
		// TODO
		return false;
	}

	/**
	 * accessor: read one entry from the {@link StoredVector}
	 * @param index the index in the vector
	 * @return the value contained at that index
	 */
	public Object get(final int index) {
		if (index < this.nEntries.get() && index >= 0) {
			return this.entries.get(index);
		}
		else {
			return null;
		}
	}

	public int hashCode() {
		// TODO
		return this.entries.hashCode();
	}

	public int indexOf(final Object o) {
		final int idx = this.entries.indexOf(o);
		if (idx >= 0) {
			if (idx <= this.nEntries.get()) {
				return idx;
			}
			else {
				return -1;
			}
		}
		else {
			return -1;
		}
	}

	public boolean isEmpty() {
		return this.nEntries.get() == 0;
	}

	public Iterator iterator() {
		// TODO
		return null;
	}

	public int lastIndexOf(final Object o) {
		// TODO
		return -1;
	}

	public ListIterator listIterator() {
		// TODO
		return null;
	}

	public ListIterator listIterator(final int index) {
		// TODO
		return null;
	}

	public Object remove(final int index) {
		// TODO
		return null;
	}

	public boolean remove(final Object o) {
		// TODO
		return true;
	}

	public boolean removeAll(final Collection c) {
		// TODO
		return true;
	}

	public boolean retainAll(final Collection c) {
		// TODO
		return true;
	}

	/**
	 * modifying an entry storing if needed the former value on the trailing stack
	 * @param o the new entry
	 * @param index the index in the vector
	 * */
	public Object set(final int index, final Object o) {
		// assert (index <= size.get())
		final Object formerValue = this.entries.get(index);
		if (o != formerValue) {
			this.trail.notifyUpdate(this, index, formerValue);
		}
		//((StoredVectorTrail)this.environment.
		//                  getTrail(Environment.VECTOR_TRAIL)).
		//                  notifyUpdate(this, index, formerValue);
		//environment.notifyUpdate(this, index, formerValue);
		this.entries.set(index, o);
		return formerValue;
	}

	public int size() {
		return this.nEntries.get();
	}

	public List subList(final int fromIndex, final int toIndex) {
		return this.entries.subList(fromIndex, toIndex);
	}

	public Object[] toArray() {
		return this.entries.subList(0, this.nEntries.get()).toArray();
	}

	public Object[] toArray(final Object[] a) {
		return this.entries.subList(0, this.nEntries.get()).toArray(a);
	}
}