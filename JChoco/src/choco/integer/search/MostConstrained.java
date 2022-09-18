package choco.integer.search;

// **************************************************
// *                   J-CHOCO                      *
// *   Copyright (C) F. Laburthe, 1999-2003         *
// **************************************************
// *  an open-source Constraint Programming Kernel  *
// *     for Research and Education                 *
// **************************************************

/* File choco.search.search.MostConstrained.java, last modified by flaburthe 20 oct. 2003 11:53:43 */

public class MostConstrained extends AbstractIntVarSelector {
	/**
	 * the IIntVarSelector can be asked to return an {@link choco.integer.var.IntDomainVar}
	 * @return a non instantiated search variable
	 */
	public choco.integer.var.IntDomainVar selectIntVar() {
		int maxDeg = 0;
		choco.integer.var.IntDomainVar v0 = null;
		final int n = this.vars.length;
		for (int i = 0; i < n; i++) {
			final choco.integer.var.IntDomainVar v = this.vars[i];
			if (!v.isInstantiated()) {
				final int deg = v.getNbConstraints();
				if (maxDeg < deg) {
					maxDeg = deg;
					v0 = v;
				}
			}
		}
		return v0;
	}
}
