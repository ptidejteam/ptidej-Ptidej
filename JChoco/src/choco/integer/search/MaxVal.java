// **************************************************
// *                   J-CHOCO                      *
// *   Copyright (C) F. Laburthe, 1999-2003         *
// **************************************************
// *  an open-source Constraint Programming Kernel  *
// *     for Research and Education                 *
// **************************************************

/* File choco.search.search.MaxVal.java, last modified by Francois 2 nov. 2003 15:10:39 */
package choco.integer.search;

import choco.search.AbstractSearchHeuristic;

public class MaxVal extends AbstractSearchHeuristic implements IValSelector {
	/**
	 * selecting the highest value in the domain
	 * @param x the variable under consideration
	 * @return what seems the most interesting value for branching
	 */
	public int getBestVal(final choco.integer.var.IntDomainVar x) {
		return x.getSup();
	}
}
