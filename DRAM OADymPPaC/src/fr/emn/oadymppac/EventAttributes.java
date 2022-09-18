/*
 * $Id: EventAttributes.java,v 1.1 2004/03/01 15:37:16 guehene Exp $
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
 * This interface gathers all the attributes of solver events, namely:
 * <br><code>depth</code>, <code>n</code>, <code>time</code>,
 * <code>context</code>, <code>stages</code>.
 *
 * @author Jean-Daniel Fekete
 * @author Mohammad Ghoniem
 * @version $Revision: 1.1 $
 */

public interface EventAttributes extends SolverConstants {
	/**
	 * Default value when the <code>depth</code> is not stated.
	 */
	public static final int DEPTH_NONE = -1;
	/**
	 * Default value when the <code>time</code> is not stated.
	 */
	public static final int TIME_NONE = -1;
	/**
	 * Returns the <code>context</code>
	 */
	public String getContext();
	/**
	 * Returns the <code>depth</code> of the event.
	 */
	public int getDepth();
	/**
	 * Returns <code>n</code>.
	 */
	public int getN();
	/**
	 * Returns the associated solver.
	 */
	public Solver getSolver();
	/**
	 * Returns an array of stages.
	 */
	public String[] getStages();
	/**
	 * Returns the <code>time</code>.
	 */
	public long getTime();
}
