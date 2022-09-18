/*
 * $Id: DefaultMiscList.java,v 1.1 2004/03/01 15:37:16 guehene Exp $
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

import java.util.Vector;

/**
 * Default implementation of the <code>MiscList</code> interface.
 *
 * @author Jean-Daniel Fekete
 * @author Mohammad Ghoniem
 * @version $Revision: 1.1 $
 */

public class DefaultMiscList implements MiscList {
	Vector list;

	/**
	 * Default constructor.
	 */
	DefaultMiscList() {
		this.list = new Vector();
	}
	/**
	 * Copy constructor.
	 */
	DefaultMiscList(final MiscList l, final boolean deep) {
		this.list = new Vector(l.getLength());
		for (int i = 0; i < l.getLength(); i++) {
			if (deep) {
				this.addMisc(new DefaultMisc(l.getMisc(i), true));
			}
			else {
				this.addMisc(l.getMisc(i));
			}
		}
	}

	/**
	 * Adds an element to the list.
	 */
	public void addMisc(final Misc m) {
		this.list.addElement(m);
	}
	/**
	 * Clears the list.
	 */
	public void clear() {
		this.list.clear();
	}

	/**
	 * Returns the number of elements in the list.
	 */
	public int getLength() {
		return this.list.size();
	}
	/**
	 * Returns the element at <code>index</code>.
	 */
	public Misc getMisc(final int index) {
		return (Misc) this.list.elementAt(index);
	}
	/**
	 * Removes the given element from the list.
	 */
	public void removeMisc(final Misc m) {
		this.list.removeElement(m);
	}
}
