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
package epi.util;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * Class: SparseBitSet
 * 
 * Description:
 * 
 * A sparse bitset is a large collection of boolean values where many of the
 * values are off (or false). For maintaining these sparsely populated sets, the
 * BitSet class can be very inefficient. Since the majority of the bits will be
 * off, space will be occupied to store nothing. To work with these sparse
 * bitsets, we use a representation backed by a HashMap. Only the positions
 * where a value is set are then stored in the mapping.
 * 
 */
public class BitVector {
	private final int size;
	private final Set underlyingSet;
	private int offset;

	// Replace BitSet no-arg constructor
	public BitVector(final int aSize) {
		this.underlyingSet = new HashSet();
		this.size = aSize;
	}

	// Performs a logical AND of this target bit set
	// with the argument bit set
	public void and(final BitVector set) {
		Iterator e = this.underlyingSet.iterator();
		while (e.hasNext()) {
			final int position = ((Integer) e.next()).intValue();
			if (!set.contains(position + this.offset % this.size)) {
				e.remove();
			}
		}
	}

	private boolean contains(final int aPosition) {
		return this.checkForOne(aPosition);
	}

	// Clears all of the bits in this bitset whose
	// corresponding bit is set in the specified bit set
	public void andNot(final BitVector set) {
		final Iterator i = this.underlyingSet.iterator();
		while (i.hasNext()) {
			final int position =
				((Integer) i.next()).intValue() + this.offset % this.size;
			if (set.checkForOne(position)) {
				i.remove();
			}
		}
	}
	// Removes the bit specified from the set
	public void clear(final int bit) {
		this.underlyingSet.remove(Integer.valueOf(bit));
	}

	// Clone
	public Object clone() {
		final BitVector set = new BitVector(this.size());
		set.or(this);
		return set;
	}

	// Equality check
	public boolean equals(final Object obj) {
		if (obj == this) {
			return true;
		}
		if (!(obj instanceof BitVector)) {
			return false;
		}
		else {
			final BitVector other = (BitVector) obj;
			return this.underlyingSet.equals(other.underlyingSet);
		}
	}

	// Checks if specific bit contained in set
	public boolean checkForOne(final int aPositionOfOne) {
		return this.underlyingSet.contains(Integer.valueOf(aPositionOfOne
				- this.offset % this.size));
	}

	// Return internal set hashcode
	public int hashCode() {
		return this.underlyingSet.hashCode();
	}

	// Returns the maximum element in set + 1
	// Returns true if this set contains no true bits.
	public boolean isEmpty() {
		return this.underlyingSet.isEmpty();
	}

	// Performs a logical OR of this bit set
	// with the bit set argument
	public void or(final BitVector set) {
		final Iterator iterator = set.underlyingSet.iterator();
		while (iterator.hasNext()) {
			final int position =
				((Integer) iterator.next()).intValue() + set.offset % set.size;
			this.set(position);
		}
	}
	// Return size of internal set
	public int getNumberOfOnes() {
		return this.underlyingSet.size();
	}
	// Adds bit specified to set
	// By its position.
	public void set(final int aPositionOfOne) {
		this.underlyingSet.add(Integer.valueOf(aPositionOfOne + this.offset
				% this.size));
	}
	// Performs a circular shift of this bitset by an offset. The set have a
	// maximum fixed size of shiftSize.
	public void shift(final int anOffset) {
		this.offset += anOffset;
	}

	// Return size of internal set
	public int size() {
		return this.size;
	}

	// Return string representation of internal set
	public String toString() {
		return this.underlyingSet.toString();
	}

	// Performs a logical XOR of this bit set
	// with the bit set argument
	public void xor(final BitVector set) {
		final Set thisSet = new HashSet(this.underlyingSet);
		final Set thatSet = new HashSet(set.underlyingSet);
		// Find out what elements are in this set
		// but not in the other set
		thisSet.removeAll(thatSet);
		// Find out what elements are in the other set
		// but not in this set
		thatSet.removeAll(this.underlyingSet);
		// Combine sets
		this.underlyingSet.retainAll(thisSet);
		this.underlyingSet.addAll(thatSet);
	}
}
