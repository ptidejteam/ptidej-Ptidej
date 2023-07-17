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
package ptidej.solver.combination;

import util.multilingual.MultilingualManager;

/**
 * Writen in CLAIRE by
 * @author Yann-Gaël Guéhéneuc
 * Translated and adapted from CLAIRE version to JAVA by
 * @author Iyadh Sidhom
 * @author Salim Bensemmane
 * @author Fayçal Skhiri
 */
public final class BitString {
	private final int numberOfBits;
	private int value;

	public BitString(final int numberOfBits) {
		this.numberOfBits = numberOfBits;
	}
	private static int compose(final boolean[] bits) {
		final int numberOfBits = bits.length;
		int v = 0;

		for (int i = 0; i < numberOfBits; i++) {
			v += ((bits[i] == true) ? Math.pow(2, numberOfBits - 1 - i) : 0);
		}

		return v;
	}
	public void dec() {
		this.value--;
	}
	private static boolean[] decompose(final int v, final int numberOfBits) {
		final boolean bits[] = new boolean[numberOfBits];
		int value = v;
		int i = numberOfBits;

		while (value > 0 && i >= 0) {
			i--;
			if (value % 2 > 0) {
				bits[i] = true;
			}
			else {
				bits[i] = false;
			}
			value = value / 2;
		}

		return bits;
	}
	public int getInteger() {
		return this.value;
	}
	public String getString() {
		final boolean[] bits =
			BitString.decompose(this.value, this.numberOfBits);
		final StringBuffer buffer = new StringBuffer(this.numberOfBits);

		for (int i = 0; i < this.numberOfBits; i++) {
			buffer.append(((bits[i] == true) ? '1' : '0'));
		}

		return buffer.toString();
	}
	public void inc() {
		this.value++;
	}
	public void set(final boolean[] bits) {
		if (bits.length != this.numberOfBits) {
			throw new RuntimeException(MultilingualManager.getString(
				"NB_OF_BITS",
				BitString.class,
				new Object[] { Integer.valueOf(this.numberOfBits) }));
		}
		this.value = BitString.compose(bits);
	}
	public void set(final int value) {
		this.value = value;
	}
	public void set(final String v) {
		if (v.length() != this.numberOfBits) {
			throw new RuntimeException(MultilingualManager.getString(
				"NB_OF_BITS",
				BitString.class,
				new Object[] { Integer.valueOf(this.numberOfBits) }));
		}

		final boolean[] bits = new boolean[this.numberOfBits];
		for (int i = 0; i < this.numberOfBits; i++) {
			bits[i] = (v.charAt(i) == '1') ? true : false;
		}

		this.value = BitString.compose(bits);
	}
	public String toString() {
		return this.getString();
	}
}
