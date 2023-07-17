package choco.integer.search;

import choco.search.IVarSelector;

// **************************************************
// *                   J-CHOCO                      *
// *   Copyright (C) F. Laburthe, 1999-2003         *
// **************************************************
// *  an open-source Constraint Programming Kernel  *
// *     for Research and Education                 *
// **************************************************

/* File choco.search.search.IIntVarSelector.java, last modified by flaburthe 20 oct. 2003 11:14:30 */

/**
 * an interface for objects controlling the selection of an search variable (for heuristic purposes)
 */
public interface IIntVarSelector extends IVarSelector {
	/**
	 * the IIntVarSelector can be asked to return an {@link choco.integer.var.IntDomainVar}
	 * @return a non instantiated search variable
	 */
	public choco.integer.var.IntDomainVar selectIntVar();
}
