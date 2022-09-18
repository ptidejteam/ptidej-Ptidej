/*
 * $Id: DefaultMisc.java,v 1.1 2004/03/01 15:37:16 guehene Exp $
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

/**
 * Default implementation of the <code>Misc</code> interface.
 *
 * @author Jean-Daniel Fekete
 * @author Mohammad Ghoniem
 * @version $Revision: 1.1 $
 */

public class DefaultMisc implements Misc {
	String type;
	String value;

	/**
	 * Default constructor.
	 */
	public DefaultMisc() {
	}
	/**
	 * Copy constructor.
	 */
	public DefaultMisc(final Misc other, final boolean deep) {
		this.type = other.getType();
		this.value = other.getValue();
	}
	/**
	 * Returns the <code>type</code>.
	 */
	public String getType() {
		return this.type;
	}
	/**
	 * Returns the <code>value</code>.
	 */
	public String getValue() {
		return this.value;
	}

	/**
	 * Sets the <code>type</code>.
	 */
	public void setType(final String s) {
		this.type = s;
	}
	/**
	 * Sets the <code>value</code>.
	 */
	public void setValue(final String s) {
		this.value = s;
	}
}
