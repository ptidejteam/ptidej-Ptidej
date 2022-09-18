/*
 * $Id: DefaultUpdateList.java,v 1.1 2004/03/01 15:37:16 guehene Exp $
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
 * Default implementation of the <code>UpdateList</code> interface.
 *
 * @author Jean-Daniel Fekete
 * @author Mohammad Ghoniem
 * @version $Revision: 1.1 $
 */

public class DefaultUpdateList implements UpdateList {
	/**
	 * Holds a list of updates or causes which are identical objects structure
	 * wise but semantically different from the point of view of constraint
	 * oriented programming.
	 */
	Vector list;

	/**
	 * Default constructor, creates an empty list.
	 */
	public DefaultUpdateList() {
		this.list = new Vector();
	}
	/**
	 * Creates an <code>n</code> element long list.
	 */
	public DefaultUpdateList(final int n) {
		this.list = new Vector(n);
	}
	/**
	 * Copy constructor.
	 */
	public DefaultUpdateList(final UpdateList other, final boolean deep) {
		this.list = new Vector(other.getLength());
		for (int i = 0; i < other.getLength(); i++) {
			if (deep) {
				this.list
					.addElement(new DefaultUpdate(other.getUpdate(i), true));
			}
			else {
				this.list.addElement(other.getUpdate(i));
			}
		}
	}

	/**
	 * Adds the given <code>Cause</code> to the list.
	 */
	public void addCause(final Update u) {
		this.addUpdate(u);
	}
	/**
	 * Adds the given <code>Update</code> to the list.
	 */
	public void addUpdate(final Update u) {
		this.list.addElement(u);
	}
	/**
	 * Clears the list.
	 */
	public void clear() {
		this.list.clear();
	}

	/**
	 * Returns the <code>Cause</code> at <code>index</code>.
	 */
	public Update getCause(final int index) {
		return this.getUpdate(index);
	}
	/**
	 * Returns the length of the list.
	 */
	public int getLength() {
		return this.list.size();
	}
	/**
	 * Returns the <code>Update</code> at <code>index</code>.
	 */
	public Update getUpdate(final int index) {
		return (Update) this.list.elementAt(index);
	}
	/**
	 * Removes the given <code>Cause</code> from the list.
	 */
	public void removeCause(final Update u) {
		this.removeUpdate(u);
	}
	/**
	 * Removes the given <code>Update</code> from the list.
	 */
	public void removeUpdate(final Update u) {
		this.list.removeElement(u);
	}
}
