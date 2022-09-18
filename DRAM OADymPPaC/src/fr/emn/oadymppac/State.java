/*
 * $Id: State.java,v 1.1 2004/03/01 15:37:16 guehene Exp $
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
 * This interface exposes the contents of the <code>state</code>
 * element in the trace.
 *
 * @author Jean-Daniel Fekete
 * @author Mohammad Ghoniem
 * @version $Revision: 1.1 $
 */
public interface State {
	/**
	 * No precision available.
	 */
	public static final short PRECISION_NONE = 0;
	/**
	 * Only the differences are mentionned.
	 */
	public static final short PRECISION_CHANGED = 1;
	/**
	 * The state is given in extenso.
	 */
	public static final short PRECISION_FULL = 2;

	/**
	 * Returns the <code>alist</code>.
	 */
	public String[] getAList();
	/**
	 * Returns the <code>miscList</code>.
	 */
	public MiscList getMiscList();
	/**
	 * Returns the <code>precision</code>.
	 */
	public short getPrecision();
	/**
	 * Returns the <code>qlist</code>.
	 */
	public String[] getQList();
	/**
	 * Returns the <code>rlist</code>.
	 */
	public String[] getRList();
	/**
	 * Returns the <code>slist</code>.
	 */
	public String[] getSList();
	/**
	 * Returns the <code>tlist</code>.
	 */
	public String[] getTList();
	/**
	 * Returns the <code>ulist</code>.
	 */
	public UpdateList getUList();
	/**
	 * Returns the <code>vlist</code>.
	 */
	public VarDomainList getVList();
}
