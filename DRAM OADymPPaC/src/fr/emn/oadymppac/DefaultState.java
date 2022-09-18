/*
 * $Id: DefaultState.java,v 1.1 2004/03/01 15:37:16 guehene Exp $
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
 * Default implementation of the <code>State</code> interface.
 *
 * @author Jean-Daniel Fekete
 * @author Mohammad Ghoniem
 * @version $Revision: 1.1 $
 */

public class DefaultState implements State {
	/**
	 * Provides the precision level of the information conveyed in the
	 * <code>State</code>.
	 */
	short precision;
	/**
	 * Holds the active constraints/events.
	 */
	String[] alist;
	/**
	 * Holds the suspended constraints/events.
	 */
	String[] slist;
	/**
	 * Holds the active constraints/events.
	 */
	String[] qlist;
	/**
	 * Holds the active constraints/events.
	 */
	String[] tlist;
	/**
	 * Holds the active constraints/events.
	 */
	String[] rlist;
	/**
	 * Holds the list of updates.
	 */
	UpdateList ulist;
	/**
	* Holds the list of vardomains.
	*/
	VarDomainList vlist;
	/**
	 * Holds a list of miscelleneous information.
	 */
	MiscList miscList;

	/**
	 * Default constructor.
	 */
	public DefaultState() {
	}
	/**
	 * Copy constructor.
	 */
	public DefaultState(final State other, final boolean deep) {
		this.precision = other.getPrecision();
		if (deep) {
			this.alist = (String[]) other.getAList().clone();
			this.slist = (String[]) other.getSList().clone();
			this.qlist = (String[]) other.getQList().clone();
			this.tlist = (String[]) other.getTList().clone();
			this.rlist = (String[]) other.getRList().clone();
			this.ulist = new DefaultUpdateList(other.getUList(), true);
			this.vlist = new DefaultVarDomainList(other.getVList(), true);
			this.miscList = new DefaultMiscList(other.getMiscList(), true);
		}
		else {
			this.alist = other.getAList();
			this.slist = other.getSList();
			this.qlist = other.getQList();
			this.tlist = other.getTList();
			this.rlist = other.getRList();
			this.ulist = other.getUList();
			this.vlist = other.getVList();
			this.miscList = other.getMiscList();
		}
	}

	/**
	 * Returns <code>alist</code>.
	 */
	public String[] getAList() {
		return this.alist;
	}
	/**
	 * Returns <code>miscList</code>.
	 */
	public MiscList getMiscList() {
		return this.miscList;
	}
	/**
	 * Returns the precision.
	 */
	public short getPrecision() {
		return this.precision;
	}
	/**
	 * Returns <code>qlist</code>.
	 */
	public String[] getQList() {
		return this.qlist;
	}
	/**
	 * Returns <code>rlist</code>.
	 */
	public String[] getRList() {
		return this.rlist;
	}
	/**
	 * Returns <code>slist</code>.
	 */
	public String[] getSList() {
		return this.slist;
	}
	/**
	 * Returns <code>tlist</code>.
	 */
	public String[] getTList() {
		return this.tlist;
	}
	/**
	 * Returns <code>ulist</code>.
	 */
	public UpdateList getUList() {
		return this.ulist;
	}
	/**
	 * Returns <code>vlist</code>.
	 */
	public VarDomainList getVList() {
		return this.vlist;
	}
	/**
	 * Sets <code>alist</code>.
	 */
	public void setAList(final String[] l) {
		this.alist = l;
	}
	/**
	 * Sets  <code>miscList</code>.
	 */
	public void setMiscList(final MiscList l) {
		this.miscList = l;
	}
	/**
	 * Sets the precision.
	 */
	public void setPrecision(final short p) {
		this.precision = p;
	}
	/**
	 * Sets <code>qlist</code>.
	 */
	public void setQList(final String[] l) {
		this.qlist = l;
	}
	/**
	 * Sets <code>rlist</code>.
	 */
	public void setRList(final String[] l) {
		this.rlist = l;
	}
	/**
	 *  Sets <code>slist</code>.
	 */
	public void setSList(final String[] l) {
		this.slist = l;
	}
	/**
	 * Sets <code>tlist</code>.
	 */
	public void setTList(final String[] l) {
		this.tlist = l;
	}
	/**
	 * Sets <code>ulist</code>.
	 */
	public void setUList(final UpdateList l) {
		this.ulist = l;
	}
	/**
	 * Sets <code>vlist</code>.
	 */
	public void setVList(final VarDomainList l) {
		this.vlist = l;
	}
}
