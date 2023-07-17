package choco.integer.search;

// **************************************************
// *                   J-CHOCO                      *
// *   Copyright (C) F. Laburthe, 1999-2003         *
// **************************************************
// *  an open-source Constraint Programming Kernel  *
// *     for Research and Education                 *
// **************************************************

/* File choco.search.search.IncreasingDomain.java, last modified by flaburthe 21 oct. 2003 11:40:05 */

public final class IncreasingDomain implements IValIterator {

	/**
	 * Accessing the index of the first branch for variable x
	 * @param x the variable under scrutiny
	 * @return the index of the first branch (such as the first value to be assigned to the variable)
	 */
	public int getFirstVal(final choco.integer.var.IntDomainVar x) {
		return x.getInf();
	}

	/**
	 * generates the index of the next branch after branch i, on the alternative associated to variable x
	 * @param x the variable under scrutiny
	 * @param i the index of the last branch explored
	 * @return the index of the next branch to be expanded after branch i
	 */
	public int getNextVal(final choco.integer.var.IntDomainVar x, final int i) {
		return x.getNextDomainValue(i);
	}

	/**
	 * testing whether more branches can be considered after branch i, on the alternative associated to variable x
	 * @param x the variable under scrutiny
	 * @param i the index of the last branch explored
	 * @return true if more branches can be expanded after branch i
	 */
	public boolean hasNextVal(
		final choco.integer.var.IntDomainVar x,
		final int i) {
		return i < x.getSup();
	}
}
