/*
 * $Id: IntAttributeVector.java,v 1.1 2004/03/01 15:37:17 guehene Exp $
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

package fr.emn.oadymppac.tree;

public class IntAttributeVector extends AttributeVector {
	static public IntAttributeVector find(
		final String name,
		final AttributedTree tree) {
		IntAttributeVector vec =
			(IntAttributeVector) tree.getAttributeVector(name);
		if (vec == null) {
			vec = new IntAttributeVector(name, tree.capacity());
			tree.addAttributeVector(vec);
		}
		return vec;
	}

	protected int[] attribute;
	public IntAttributeVector(final IntAttributeVector other) {
		super(other);
		this.attribute = new int[other.capacity()];
		System.arraycopy(other.attribute, 0, this.attribute, 0, this.size);
	}
	public IntAttributeVector(final String name, final int capacity) {
		super(name, capacity);
		this.attribute = new int[capacity];
	}

	public int capacity() {
		return this.attribute.length;
	}

	public final int getAttribute(final int index) {
		return this.attribute[index];
	}

	public Class getAttributeClass() {
		return Integer.class;
	}

	public Object getValue(final int index) {
		return Integer.valueOf(this.getAttribute(index));
	}
	public void reserve(final int sz) {
		if (this.attribute.length < sz) {
			final int[] newattribute = new int[sz];
			System.arraycopy(this.attribute, 0, newattribute, 0, this.size);
			this.attribute = newattribute;
		}
	}
	public final void setAttribute(final int index, final int val) {
		this.attribute[index] = val;
	}

}
