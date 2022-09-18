/*
 * $Id: CountingBitSet.java,v 1.1 2004/03/01 15:37:16 guehene Exp $
 *
 * Copyright (c) 2001-2002 Jean-Daniel Fekete, Mohammad Ghoniem and
 * Ecole des Mines de Nantes.  All rights reserved.
 *
 * This software is proprietary information of Jean-Daniel Fekete and
 * Ecole des Mines de Nantes.  You shall use it only in accordance
 * with the terms of the license agreement you accepted when
 * downloading this software.  The license is available in the file
 * licence.txt and at the following URL:
 * http://www.emn.fr/fekete/oadymppac/licence.html
 */
package fr.emn.oadymppac;

import java.util.BitSet;

/**
 * A <code>BitSet</code> that maintains the count of bits set to 1.
 *
 * @author Jean-Daniel Fekete
 * @version $Revision: 1.1 $
 */
public class CountingBitSet extends BitSet {
	/**
	 * 
	 */
	private static final long serialVersionUID = -960594556211562978L;
	protected int count;

	/**
	 * Creates a new counting bit set. All bits are initially <code>false</code>.
	 */
	public CountingBitSet() {
		this.count = 0;
	}

	/**
	 * Copy a counting bit.
	 */
	public CountingBitSet(final CountingBitSet other) {
		this.or(other);
	}

	/**
	 * Creates a counting bit set whose initial size is large enough to explicitly
	 * represent bits with indices in the range <code>0</code> through
	 * <code>nbits-1</code>. All bits are initially <code>false</code>. 
	 *
	 * @param     nbits   the initial size of the bit set.
	 * @exception NegativeArraySizeException if the specified initial size
	 *               is negative.
	 */
	public CountingBitSet(final int nbits) {
		super(nbits);
	}

	public void and(final BitSet set) {
		super.and(set);
		this.updateCount();
	}

	public void andNot(final BitSet set) {
		super.andNot(set);
		this.updateCount();
	}

	/**
	 * Sets the bit specified by the index to <code>false</code>.
	 *
	 * @param     bitIndex   the index of the bit to be cleared.
	 * @exception IndexOutOfBoundsException if the specified index is negative.
	 * @since     JDK1.0
	 */
	public void clear(final int bitIndex) {
		if (this.get(bitIndex)) {
			super.clear(bitIndex);
			this.count--;
		}
	}

	public int getTrueCount() {
		return this.count;
	}

	public void or(final BitSet set) {
		super.or(set);
		this.updateCount();
	}
	/**
	 * Sets the bit specified by the index to <code>true</code>.
	 *
	 * @param     bitIndex   a bit index.
	 * @exception IndexOutOfBoundsException if the specified index is negative.
	 * @since     JDK1.0
	 */
	public void set(final int bitIndex) {
		if (!this.get(bitIndex)) {
			super.set(bitIndex);
			this.count++;
		}
	}
	protected void updateCount() {
		this.count = 0;
		for (int bit = 0; bit < this.size(); bit++) {
			if (this.get(bit)) {
				this.count++;
			}
		}
	}
	public void xor(final BitSet set) {
		super.xor(set);
		this.updateCount();
	}
}
