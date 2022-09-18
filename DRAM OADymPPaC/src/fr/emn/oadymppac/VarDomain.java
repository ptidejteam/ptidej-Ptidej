/*
 * $Id: VarDomain.java,v 1.1 2004/03/01 15:37:16 guehene Exp $
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
 * This interface exposes the contents of the <code>vardomain</code>
 * element in the trace.
 *
 * @author Jean-Daniel Fekete
 * @author Mohammad Ghoniem
 * @version $Revision: 1.1 $
 */
public interface VarDomain extends ValueList {
	/**
	 * Returns the upper bound of the domain.
	 */
	public int getMax();

	/**
	 * Returns the lower bound of the domain.
	 */
	public int getMin();

	/**
	 * Returns the name of the correponding variable.
	 */
	public String getVName();

	/**
	 * Sets the upper bound of the domain.
	 */
	public void setMax(int m);

	/**
	 * Sets the lower bound of the domain.
	 */
	public void setMin(int m);
}
