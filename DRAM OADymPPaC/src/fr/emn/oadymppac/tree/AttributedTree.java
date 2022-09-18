/*
 * $Id: AttributedTree.java,v 1.1 2004/03/01 15:37:17 guehene Exp $
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

import java.util.Vector;

public class AttributedTree extends Tree {
	protected Vector attributes = new Vector();

	public AttributedTree() {
	}
	public AttributedTree(final AttributedTree other) {
		super(other);
		for (int i = 0; i < other.getAttributeCount(); i++) {
			this.attributes.addElement(other.getAttributeVector(i).clone());
		}
	}
	public AttributedTree(final int capacity) {
		super(capacity);
	}
	public AttributedTree(final Tree other) {
		super(other);
	}

	public void addAttributeVector(final AttributeVector v) {
		// should check if names conflict
		v.reserve(this.capacity());
		v.resize(this.size());
		this.attributes.addElement(v);
	}

	public int getAttributeCount() {
		return this.attributes.size();
	}

	public AttributeVector getAttributeVector(final int index) {
		return (AttributeVector) this.attributes.elementAt(index);
	}

	public AttributeVector getAttributeVector(final String name) {
		for (int i = 0; i < this.getAttributeCount(); i++) {
			if (this.getAttributeVector(i).getName().equals(name)) {
				return this.getAttributeVector(i);
			}
		}
		return null;
	}

	public void reserve(final int sz) {
		super.reserve(sz);
		for (int i = 0; i < this.getAttributeCount(); i++) {
			this.getAttributeVector(i).reserve(sz);
		}
	}

	public void resize(final int sz) {
		super.resize(sz);
		for (int i = 0; i < this.getAttributeCount(); i++) {
			this.getAttributeVector(i).resize(sz);
		}
	}

};
