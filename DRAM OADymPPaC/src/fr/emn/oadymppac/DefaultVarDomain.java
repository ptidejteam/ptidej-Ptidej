/*
 * $Id: DefaultVarDomain.java,v 1.1 2004/03/01 15:37:16 guehene Exp $
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
 * Default implementation of the <code>VarDomain</code> interface.
 *
 * @author Jean-Daniel Fekete
 * @author Mohammad Ghoniem
 * @version $Revision: 1.1 $
 */

public class DefaultVarDomain extends DefaultValueList implements VarDomain {
	/**
	 * The name of the associated variable.
	 */
	String vname;

	int max, min;

	/**
	 * Default constructor.
	 */
	public DefaultVarDomain() {
	}
	public DefaultVarDomain(final int n, final String vname) {
		super(n);
		this.vname = vname;
		this.max = Integer.MAX_VALUE;
		this.min = Integer.MIN_VALUE;
	}
	public DefaultVarDomain(final String vname) {
		this(vname, Integer.MAX_VALUE, Integer.MIN_VALUE);
	}

	public DefaultVarDomain(final String vname, final int max, final int min) {
		this.vname = vname;
		this.max = max;
		this.min = min;
	}
	/**
	 * Copy constructor.
	 */
	public DefaultVarDomain(final VarDomain other, final boolean deep) {
		super(other, deep);
		this.vname = other.getVName();
		this.max = other.getMax();
		this.min = other.getMin();
	}

	/**
	 * @see fr.emn.oadymppac.VarDomain#getMax()
	 */
	public int getMax() {
		return this.max;
	}
	/**
	 * @see fr.emn.oadymppac.VarDomain#getMin()
	 */
	public int getMin() {
		return this.min;
	}

	/**
	 * Return <code>vname</code>.
	 */
	public String getVName() {
		return this.vname;
	}

	/**
	 * @see fr.emn.oadymppac.VarDomain#setMax(int)
	 */
	public void setMax(final int max) {
		this.max = max;
	}

	/**
	 * @see fr.emn.oadymppac.VarDomain#setMin(int)
	 */
	public void setMin(final int min) {
		this.min = min;
	}

	/**
	 * Sets <code>vname</code>.
	 */
	public void setVName(final String vname) {
		this.vname = vname;
	}

}
