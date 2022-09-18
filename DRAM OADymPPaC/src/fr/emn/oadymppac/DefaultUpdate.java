/*
 * $Id: DefaultUpdate.java,v 1.1 2004/03/01 15:37:16 guehene Exp $
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
 * Default implementation of the <code>Update</code> interface.
 *
 * @author Jean-Daniel Fekete
 * @author Mohammad Ghoniem
 * @version $Revision: 1.1 $
 */

public class DefaultUpdate implements Update {
	String cname;
	String vname;
	short type;
	ValueList valueList;

	/**
	 * Default constructor.
	 */
	public DefaultUpdate() {
	}
	public DefaultUpdate(final String vname) {
		this(null, vname, SolverConstants.TYPE_NONE);
	}
	public DefaultUpdate(final String vname, final short type) {
		this(null, vname, type);
	}
	public DefaultUpdate(
		final String cname,
		final String vname,
		final short type) {
		this.cname = cname;
		this.vname = vname;
		this.type = type;
	}
	/**
	 * Copy constructor.
	 */
	public DefaultUpdate(final Update other, final boolean deep) {
		this.cname = other.getCName();
		this.vname = other.getVName();
		this.type = other.getType();
		if (other.getValueList() != null && deep) {
			this.valueList = new DefaultValueList(other.getValueList(), true);
		}
		else {
			this.valueList = other.getValueList();
		}
	}

	/**
	 * Returns <code>cname</code>.
	 */
	public String getCName() {
		return this.cname;
	}
	/**
	 * Returns <code>type</code>.
	 */
	public short getType() {
		return this.type;
	}
	/**
	 * Returns <code>valueList</code>.
	 */
	public ValueList getValueList() {
		return this.valueList;
	}
	/**
	 * Returns <code>vname</code>.
	 */
	public String getVName() {
		return this.vname;
	}

	/**
	 * Sets <code>cname</code>.
	 */
	public void setCName(final String name) {
		this.cname = name;
	}
	/**
	 * Sets <code>type</code>.
	 */
	public void setType(final short t) {
		this.type = t;
	}
	/**
	 * Sets <code>valueList</code>.
	 */
	public void setValueList(final ValueList v) {
		this.valueList = v;
	}
	/**
	 * Sets <code>vname</code>.
	 */
	public void setVName(final String name) {
		this.vname = name;
	}

	public String toString() {
		String res = this.getClass().getName() + "\n";
		res += "cname = " + this.cname + "\n";
		res += "vname = " + this.vname + "\n";
		res += "type = " + this.type + "\n";
		res += "value list " + this.valueList.toString();
		return res;
	}
}
