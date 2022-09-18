/*
 * $Id: DefaultValueList.java,v 1.1 2004/03/01 15:37:17 guehene Exp $
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
import java.util.Vector;

/**
 * Default implementation of the <code>DefaultValueList</code> interface.
 *
 * @author Jean-Daniel Fekete
 * @author Mohammad Ghoniem
 * @version $Revision: 1.1 $
 */

public class DefaultValueList implements ValueList {
	/**
	 * Holds isolated values and/or range boudaries.
	 */
	Vector list;
	/**
	 * Holds a set of bits where 1 indicates the beginning (lower bound) of a
	 * range.
	 */
	BitSet isRange;

	/**
	 * Default constructor.
	 */
	public DefaultValueList() {
		this.list = new Vector();
		this.isRange = new BitSet();
	}
	/**
	 * Creates an n element long value list and an equally long
	 * <code>BitSet</code>.
	 */
	public DefaultValueList(final int n) {
		this.list = new Vector(n);
		this.isRange = new BitSet(n);
	}
	/**
	 * Copy constructor.
	 */
	public DefaultValueList(final ValueList other, final boolean deep) {
		this(other.getLength());
		for (int i = 0; i < other.getLength(); i++) {
			if (other.isRange(i)) {
				this.addRange(other.getValue(i), other.getValue(i + 1));
				i++;
			}
			else {
				this.addValue(other.getValue(i));
			}
		}
	}

	/**
	 * Adds the given range to the value list.
	 */
	public void addRange(int o1, final int o2) {
		while (o1 <= o2) {
			this.list.addElement(Integer.valueOf(o1++));
		}
		this.isRange.clear(this.list.size());
	}
	/**
	 * Adds the given value to the value list.
	 */
	public void addValue(final int obj) {
		this.list.addElement(Integer.valueOf(obj));
		this.isRange.clear(this.list.size());
	}
	/**
	 * Clears the value list.
	 */
	public void clear() {
		this.list.clear();
		this.isRange = new BitSet();
	}
	/**
	 * Returns the cardinality of the value list i.e. it does also count
	 * the values falling inside the ranges.
	 */
	public int domainCount() {
		int cnt = 0;
		for (int i = 0; i < this.getLength(); i++) {
			cnt++;
			if (this.isRange(i)) {
				cnt += this.getValue(i + 1) - this.getValue(i);
				i++;
			}
		}
		return cnt;
	}

	/**
	 * Returns the <code>BitSet</code>
	 */
	public BitSet getIsRange() {
		return this.isRange;
	}
	/**
	 * Returns the length of the value list.
	 */
	public int getLength() {
		return this.list.size();
	}
	/**
	 * Returns the value list.
	 */
	public Vector getList() {
		return this.list;
	}
	/**
	 * Returns the value at <code>index</code>
	 */
	public int getValue(final int index) {
		return ((Integer) this.list.elementAt(index)).intValue();
	}
	/**
	 * Returns 1 if the value at <code>index</code> is the lower bound of a
	 * range and 0 otherwise.
	 */
	public boolean isRange(final int index) {
		return this.isRange.get(index);
	}
}
