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
/*
 * File: SparseBitSet.java 
 * 
 *	Author				Date				Modification
 * 	
 * 	MageLang Institute	1999				Original version
 * 	Olivier				2005-25-05			Methods shift(...) and isEmpty() added						
 *
 */
package epi.util;

//import java.util.BitSet;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * Class: SparseBitSet
 * 
 * Description:	
 * 
 * A sparse bitset is a large collection of boolean values where many of the values are off (or false). 
 * For maintaining these sparsely populated sets, the BitSet class can be very inefficient. Since the majority
 * of the bits will be off, space will be occupied to store nothing. To work with these sparse bitsets, 
 * we use a representation backed by a HashMap. Only the positions where a value is set are then stored in 
 * the mapping.
 * 	
 */
public class SparseBitSet {// extends BitSet {

	protected Set bitset;

	// Replace BitSet no-arg constructor
	public SparseBitSet() {
		this.bitset = new HashSet();
	}

	// Performs a logical AND of this target bit set 
	// with the argument bit set
	public void and(final SparseBitSet set) {
		this.bitset.retainAll(set.getBitSet());
	}

	// Clears all of the bits in this bitset whose 
	// corresponding bit is set in the specified bit set
	public void andNot(final SparseBitSet set) {
		this.bitset.removeAll(set.getBitSet());
	}

	// Removes the bit specified from the set
	public void clear(final int bit) {
		this.bitset.remove(Integer.valueOf(bit));
	}

	// Clone
	public Object clone() {
		final SparseBitSet set = new SparseBitSet();
		set.or(this);
		return set;
	}

	// Equality check
	public boolean equals(final Object obj) {
		if (obj == this) {
			return true;
		}
		if (!(obj instanceof SparseBitSet)) {
			return false;
		}
		else {
			final SparseBitSet other = (SparseBitSet) obj;
			return this.bitset.equals(other.getBitSet());
		}
	}

	// Checks if specific bit contained in set
	public boolean get(final int bit) {
		return this.bitset.contains(Integer.valueOf(bit));
	}

	protected Set getBitSet() {
		return this.bitset;
	}

	// Return internal set hashcode
	public int hashCode() {
		return this.bitset.hashCode();
	}

	// Returns true if this set contains no true bits.
	public boolean isEmpty() {
		return this.bitset.isEmpty();
	}

	// Returns the maximum element in set + 1
	public int length() {
		int returnValue = 0;
		// Make sure set not empty
		// Get maximum value +1
		if (this.bitset.size() > 0) {
			final Integer max = (Integer) Collections.max(this.bitset);
			returnValue = max.intValue() + 1;
		}
		return returnValue;
	}

	// Performs a logical OR of this bit set 
	// with the bit set argument
	public void or(final SparseBitSet set) {
		this.bitset.addAll(set.getBitSet());
	}

	// Adds bit specified to set
	public void set(final int bit) {
		this.bitset.add(Integer.valueOf(bit));
	}

	// Performs a circular shift of this bitset by an offset
	public void shift(final int offset) {
		this.shift(offset, 0);
	}

	// Performs a circular shift of this bitset by an offset. The set have a maximum fixed size of shiftSize.
	public void shift(final int offset, final int shiftSize) {
		final SparseBitSet set = (SparseBitSet) this.clone();

		this.bitset.clear();

		int position = -1;
		// TODO Must only loop over the elements set to true
		for (int index = 0; index < set.length(); index++) {

			// The modulo operation is use to perform a circular shift
			if (set.get(index)) {
				position =
					(index + offset)
							% java.lang.Math.max(set.length(), shiftSize);

				// left-shift
				if (position < 0) {
					position += java.lang.Math.max(set.length(), shiftSize);
				}

				this.bitset.add(Integer.valueOf(position));
			}
		}
	}

	// Return size of internal set
	public int size() {
		return this.bitset.size();
	}

	// Return string representation of internal set
	public String toString() {
		return this.bitset.toString();
	}

	// Performs a logical XOR of this bit set 
	// with the bit set argument
	public void xor(final SparseBitSet set) {
		final Set thisSet = new HashSet(this.bitset);
		final Set thatSet = new HashSet(set.getBitSet());
		// Find out what elements are in this set
		// but not in the other set
		thisSet.removeAll(thatSet);
		// Find out what elements are in the other set
		// but not in this set
		thatSet.removeAll(this.bitset);
		// Combine sets
		this.bitset.retainAll(thisSet);
		this.bitset.addAll(thatSet);
	}
}
