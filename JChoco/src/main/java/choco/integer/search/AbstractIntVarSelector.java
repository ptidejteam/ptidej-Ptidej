package choco.integer.search;

import choco.AbstractVar;
import choco.search.AbstractSearchHeuristic;

// **************************************************
// *                   J-CHOCO                      *
// *   Copyright (C) F. Laburthe, 1999-2003         *
// **************************************************
// *  an open-source Constraint Programming Kernel  *
// *     for Research and Education                 *
// **************************************************

/* File choco.search.search.AbstractIntVarSelector.java, last modified by flaburthe 20 oct. 2003 11:43:32 */

public abstract class AbstractIntVarSelector extends AbstractSearchHeuristic
		implements IIntVarSelector {

	/**
	 * a specific array of IntVars from which the object seeks the one with smallest domain
	 */
	protected choco.integer.var.IntDomainVar[] vars;

	/**
	 * the IVarSelector can be asked to return a variable
	 * @return a variable on whose domain an alternative can be set (such as a non instantiated search variable)
	 */
	public AbstractVar selectVar() {
		return this.selectIntVar();
	}
}
