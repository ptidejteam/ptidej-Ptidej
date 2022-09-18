package choco.search;

import choco.Entity;

// **************************************************
// *                   J-CHOCO                      *
// *   Copyright (C) F. Laburthe, 1999-2003         *
// **************************************************
// *  an open-source Constraint Programming Kernel  *
// *     for Research and Education                 *
// **************************************************

/* File choco.search.IGlobalSearchLimit.java, last modified by flaburthe 31 oct. 2003 18:26:20 */

/**
 * The interface of objects limiting the global search exploration
 */
public interface IGlobalSearchLimit extends Entity {
	/**
	 * TODO
	 * @param solver
	 * @return
	 */
	public boolean endNode(AbstractGlobalSearchSolver solver);

	/**
	 * TODO
	 * @param solver
	 * @return
	 */
	public boolean newNode(AbstractGlobalSearchSolver solver);

	/**
	 * TODO
	 * @param first
	 */
	public void reset(boolean first);

}
