/*
 * $Id: AttributeVector.java,v 1.1 2004/03/01 15:37:17 guehene Exp $
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

abstract class AttributeVector implements Cloneable {
	protected String name;
	protected int size;

	public AttributeVector(final AttributeVector other) {
		this.name = other.name;
		this.size = other.size;
	}
	public AttributeVector(final String name, final int capacity) {
		this.name = name;
		this.size = 0;
	}

	public abstract int capacity();
	public Object clone() {
		try {
			return super.clone();
		}
		catch (final CloneNotSupportedException ex) {
			return null;
		}
	}

	public boolean defined(final int index) {
		return true;
	}

	public abstract Class getAttributeClass();

	public String getName() {
		return this.name;
	}

	public abstract Object getValue(int index);
	public abstract void reserve(int size);
	public void resize(final int sz) {
		if (this.capacity() < sz) {
			this.reserve(this.size * 2 < sz ? sz : this.size * 2);
		}
		this.size = sz;
	}
	public int size() {
		return this.size;
	}
}
