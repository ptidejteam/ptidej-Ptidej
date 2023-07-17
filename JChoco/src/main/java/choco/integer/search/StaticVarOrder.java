// **************************************************
// *                   J-CHOCO                      *
// *   Copyright (C) F. Laburthe, 1999-2003         *
// **************************************************
// *  an open-source Constraint Programming Kernel  *
// *     for Research and Education                 *
// **************************************************

/* File choco.search.search.StaticVarOrder.java, last modified by Francois 10 nov. 2003 10:23:19 */
package choco.integer.search;

/**
 * A variable selector selecting the first non instantiated variable according to a given static order
 */
public class StaticVarOrder extends AbstractIntVarSelector {
	/**
	 * the sequence of variables that need be instantiated
	 */
	protected choco.integer.var.IntDomainVar[] vars;

	public choco.integer.var.IntDomainVar selectIntVar() {
		for (int i = 0; i < this.vars.length; i++) {
			if (!this.vars[i].isInstantiated()) {
				return this.vars[i];
			}
		}
		return null;
	}
}
