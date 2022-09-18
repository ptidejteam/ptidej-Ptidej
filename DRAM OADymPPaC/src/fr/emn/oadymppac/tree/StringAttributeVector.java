/*
 * $Id: StringAttributeVector.java,v 1.1 2004/03/01 15:37:17 guehene Exp $
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

public class StringAttributeVector extends AttributeVector {
	static public StringAttributeVector find(
		final String name,
		final AttributedTree tree) {
		StringAttributeVector vec =
			(StringAttributeVector) tree.getAttributeVector(name);
		if (vec == null) {
			vec = new StringAttributeVector(name, tree.capacity());
			tree.addAttributeVector(vec);
		}
		return vec;
	}

	protected String[] attribute;
	public StringAttributeVector(final String name, final int capacity) {
		super(name, capacity);
		this.attribute = new String[capacity];
	}

	public StringAttributeVector(final StringAttributeVector other) {
		super(other);
		this.attribute = new String[other.capacity()];
		System.arraycopy(other.attribute, 0, this.attribute, 0, this.size);
	}

	public int capacity() {
		return this.attribute.length;
	}

	public final String getAttribute(final int index) {
		return this.attribute[index];
	}

	public Class getAttributeClass() {
		return String.class;
	}

	public Object getValue(final int index) {
		return this.getAttribute(index);
	}
	public void reserve(final int sz) {
		if (this.attribute.length < sz) {
			final String[] newattribute = new String[sz];
			System.arraycopy(this.attribute, 0, newattribute, 0, this.size);
			this.attribute = newattribute;
		}
	}
	public final void setAttribute(final int index, final String val) {
		this.attribute[index] = val;
	}

}
