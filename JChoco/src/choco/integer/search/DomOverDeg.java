package choco.integer.search;

import choco.mem.IStateInt;

// **************************************************
// *                   J-CHOCO                      *
// *   Copyright (C) F. Laburthe, 1999-2003         *
// **************************************************
// *  an open-source Constraint Programming Kernel  *
// *     for Research and Education                 *
// **************************************************

/* File choco.search.search.DomOverDeg.java, last modified by flaburthe 20 oct. 2003 11:41:23 */

/**
 * A heuristic selecting the {@link choco.integer.var.IntDomainVar} with smallest ration (domainSize / degree)
 *  (the degree of a variable is the number of constraints linked to it)
 */
public final class DomOverDeg extends AbstractIntVarSelector {
	/**
	 * the IIntVarSelector can be asked to return an {@link choco.integer.var.IntDomainVar}
	 * @return a non instantiated search variable
	 */
	public choco.integer.var.IntDomainVar selectIntVar() {
		int minDomSize = IStateInt.MAXINT;
		int maxDeg = 0;
		choco.integer.var.IntDomainVar v0 = null;
		final int n = this.vars.length;
		for (int i = 0; i < n; i++) {
			final choco.integer.var.IntDomainVar v = this.vars[i];
			if (!v.isInstantiated()) {
				final int domSize = v.getDomainSize();
				final int deg = v.getNbConstraints();
				if (domSize * maxDeg < minDomSize * deg) {
					minDomSize = domSize;
					maxDeg = deg;
					v0 = v;
				}
			}
		}
		return v0;
	}
}
