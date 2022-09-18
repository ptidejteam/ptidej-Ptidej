// **************************************************
// *                   J-CHOCO                      *
// *   Copyright (C) F. Laburthe, 1999-2003         *
// **************************************************
// *  an open-source Constraint Programming Kernel  *
// *     for Research and Education                 *
// **************************************************

/* File choco.search.search.MinVal.java, last modified by Francois 2 nov. 2003 15:08:40 */
package choco.integer.search;

import choco.search.AbstractSearchHeuristic;

public class MinVal extends AbstractSearchHeuristic implements IValSelector {
	/**
	 * selecting the lowest value in the domain
	 * @param x the variable under consideration
	 * @return what seems the most interesting value for branching
	 */
	public int getBestVal(final choco.integer.var.IntDomainVar x) {
		return x.getInf();
	}
}
