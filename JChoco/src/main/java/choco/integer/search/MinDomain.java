package choco.integer.search;

import choco.Problem;
import choco.mem.IStateInt;

// **************************************************
// *                   J-CHOCO                      *
// *   Copyright (C) F. Laburthe, 1999-2003         *
// **************************************************
// *  an open-source Constraint Programming Kernel  *
// *     for Research and Education                 *
// **************************************************

/* File choco.search.search.MinDomain.java, last modified by flaburthe 20 oct. 2003 11:06:52 */

public final class MinDomain extends AbstractIntVarSelector {
	public MinDomain(final Problem pb) {
		this.problem = pb;
	}

	/**
	 * the IIntVarSelector can be asked to return an {@link choco.integer.var.IntDomainVar}
	 * @return a non instantiated search variable
	 */
	public choco.integer.var.IntDomainVar selectIntVar() {
		int minDomSize = IStateInt.MAXINT;
		choco.integer.var.IntDomainVar v0 = null;
		if (null != this.vars) {
			final int n = this.vars.length;
			for (int i = 0; i < n; i++) {
				final choco.integer.var.IntDomainVar v = this.vars[i];
				if (!v.isInstantiated()) {
					final int domSize = v.getDomainSize();
					if (domSize < minDomSize) {
						minDomSize = domSize;
						v0 = v;
					}
				}
			}
		}
		else {
			final int n = this.problem.getNbIntVars();
			for (int i = 0; i < n; i++) {
				final choco.integer.var.IntDomainVar v =
					(choco.integer.var.IntDomainVar) this.problem.getIntVar(i);
				if (!v.isInstantiated()) {
					final int domSize = v.getDomainSize();
					if (domSize < minDomSize) {
						minDomSize = domSize;
						v0 = v;
					}
				}
			}
		}
		return v0;
	}
}
