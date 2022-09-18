// **************************************************
// *                   J-CHOCO                      *
// *   Copyright (C) F. Laburthe, 1999-2003         *
// **************************************************
// *  an open-source Constraint Programming Kernel  *
// *     for Research and Education                 *
// **************************************************

/* File choco.search.search.IValSelector.java, last modified by Francois 2 nov. 2003 15:03:47 */
package choco.integer.search;

/**
 * An interface for control objects that model a binary choice to an search value
 */
public interface IValSelector {
	/**
	 * A method selecting the search value used for the alternative
	 * @return
	 */
	int getBestVal(choco.integer.var.IntDomainVar x);
}
