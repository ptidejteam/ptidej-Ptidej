package choco.search;

import choco.AbstractVar;
import choco.branch.IntBranching;

// **************************************************
// *                   J-CHOCO                      *
// *   Copyright (C) F. Laburthe, 1999-2003         *
// **************************************************
// *  an open-source Constraint Programming Kernel  *
// *     for Research and Education                 *
// **************************************************

/* File choco.search.IVarSelector.java, last modified by flaburthe 20 oct. 2003 11:02:56 */

/**
 * an interface for objects controlling the selection of a variable (for heuristic purposes)
 */
public interface IVarSelector {
	/**
	 * each IVarSelector is associated to a branching strategy
	 * @return the associated branching strategy
	 */
	public IntBranching getBranching();

	/**
	 * the IVarSelector can be asked to return a variable
	 * @return a variable on whose domain an alternative can be set (such as a non instantiated search variable)
	 */
	public AbstractVar selectVar();
}
