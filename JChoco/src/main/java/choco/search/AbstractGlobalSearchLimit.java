package choco.search;

import choco.AbstractEntity;

// **************************************************
// *                   J-CHOCO                      *
// *   Copyright (C) F. Laburthe, 1999-2003         *
// **************************************************
// *  an open-source Constraint Programming Kernel  *
// *     for Research and Education                 *
// **************************************************

/* File choco.search.AbstractGlobalSearchLimit.java, last modified by flaburthe 21 oct. 2003 10:02:53 */

/**
 * An abstract class for limiting tree search (imposing conditions on depth, ...)
 */
public abstract class AbstractGlobalSearchLimit extends AbstractEntity
		implements IGlobalSearchLimit {
	/**
	 * the solver that delegates the limit checking task to such AbstractGlobalSearchLimit objects
	 */
	protected AbstractGlobalSearchSolver solver;

	/**
	 * for pretty printing
	 */
	protected String unit = "";

	/**
	 * maximal value limitting the search exploration
	 */
	protected int nbMax = Integer.MAX_VALUE;

	/**
	 * a counter who is limited to values below max
	 */
	protected int nb = 0;

	/**
	 * counting for successive tree search
	 */
	protected int nbTot = 0;

	public AbstractGlobalSearchLimit(final int theLimit) {
		this.nb = 0;
		this.nbTot = 0;
		this.nbMax = theLimit;
	}

	public String pretty() {
		String res = this.nbTot + "[+" + this.nb + "]";
		if (this.nbMax != Integer.MAX_VALUE) {
			res += "/" + this.nbMax;
		}
		return res + " " + this.unit;
	}

	public void reset(final boolean first) {
		if (first) {
			this.nbTot = 0;
		}
		else {
			this.nbTot += this.nb;
		}
		this.nb = 0;
	}
}
