package choco.integer.search;

// **************************************************
// *                   J-CHOCO                      *
// *   Copyright (C) F. Laburthe, 1999-2003         *
// **************************************************
// *  an open-source Constraint Programming Kernel  *
// *     for Research and Education                 *
// **************************************************

/* File choco.search.search.IValIterator.java, last modified by flaburthe 21 oct. 2003 11:26:35 */

/**
 * An interface for control objects that model the iteration of (search) values associated to an {@link choco.integer.var.IntDomainVar}
 */
public interface IValIterator {
	/**
	 * Accessing the index of the first branch for variable x
	 * @param x the variable under scrutiny
	 * @return the index of the first branch (such as the first value to be assigned to the variable)
	 */
	public int getFirstVal(choco.integer.var.IntDomainVar x);

	/**
	 * generates the index of the next branch after branch i, on the alternative associated to variable x
	 * @param x the variable under scrutiny
	 * @param i the index of the last branch explored
	 * @return the index of the next branch to be expanded after branch i
	 */
	public int getNextVal(choco.integer.var.IntDomainVar x, int i);

	/**
	 * testing whether more branches can be considered after branch i, on the alternative associated to variable x
	 * @param x the variable under scrutiny
	 * @param i the index of the last branch explored
	 * @return true if more branches can be expanded after branch i
	 */
	public boolean hasNextVal(choco.integer.var.IntDomainVar x, int i);
}
