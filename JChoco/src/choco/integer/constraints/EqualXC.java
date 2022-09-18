// *********************************************
// *                   J-CHOCO                 *
// *   Copyright (c) F. Laburthe, 1999-2003    *
// *********************************************
// * Event-base contraint programming Engine   *
// *********************************************

// CVS Information
// File:               $RCSfile: EqualXC.java,v $
// Version:            $Revision: 1.1 $
// Last Modification:  $Date: 2004/05/04 22:23:15 $
// Last Contributor:   $Author: guehene $

package choco.integer.constraints;

import choco.AbstractConstraint;
import choco.ContradictionException;

//import choco.search.AbstractUnIntConstraint;

/**
 * Implements a constraint X = C, with X a variable and C a constant.
 */
public class EqualXC extends AbstractUnIntConstraint {

	/**
	 * The search constant of the constraint
	 */
	protected final int cste;

	/**
	 * Constructs the constraint with the specified variables and constant.
	 *
	 * @param x0 the search valued domain variable
	 * @param c the search constant used in the equality.
	 */

	public EqualXC(final choco.integer.IntVar x0, final int c) {
		this.v0 = x0;
		this.cste = c;
	}

	public void awakeOnInf(final int idx) throws ContradictionException {
		AbstractIntConstraint.logger.finest("VAL(" + this.v0.toString()
				+ ") = " + this.cste);
		this.v0.instantiate(this.cste, this.cIdx0);
	}

	public void awakeOnInst(final int idx) throws ContradictionException {
		AbstractIntConstraint.logger.finest("VAL(" + this.v0.toString()
				+ ") = " + this.cste);
		this.v0.instantiate(this.cste, this.cIdx0);
	}

	public void awakeOnRem(final int idx, final int x)
			throws ContradictionException {
		AbstractIntConstraint.logger.finest("VAL(" + this.v0.toString()
				+ ") = " + this.cste);
		this.v0.instantiate(this.cste, this.cIdx0);
	}

	public void awakeOnSup(final int idx) throws ContradictionException {
		AbstractIntConstraint.logger.finest("VAL(" + this.v0.toString()
				+ ") = " + this.cste);
		this.v0.instantiate(this.cste, this.cIdx0);
	}

	public void awakeOnVar(final int idx) throws ContradictionException {
		AbstractIntConstraint.logger.finest("VAL(" + this.v0.toString()
				+ ") = " + this.cste);
		this.v0.instantiate(this.cste, this.cIdx0);
	}

	/**
	 * tests if the constraint is consistent with respect to the current state of domains
	 * @return true iff the constraint is bound consistent (same as arc consistent)
	 */
	public boolean isConsistent() {
		return this.v0.isInstantiatedTo(this.cste);
	}

	/**
	 * Checks if the listeners must be checked or must fail.
	 */

	public Boolean isEntailed() {
		if (this.v0.getInf() == this.v0.getSup()
				&& this.v0.getInf() == this.cste) {
			return Boolean.TRUE;
		}
		else if (this.v0.getSup() < this.cste || this.v0.getInf() > this.cste) {
			return Boolean.FALSE;
		}
		else {
			return null;
		}
	}

	/**
	 * Checks if the constraint is satisfied when the variables are instantiated.
	 */

	public boolean isSatisfied() {
		return this.v0.getValue() == this.cste;
	}

	public AbstractConstraint opposite() {
		return (AbstractConstraint) this.getProblem().neq(this.v0, this.cste);
		// return NotEqualXC(v0, cste);
	}

	/**
	 * The one and only propagation method. <br>
	 * Note that after the first propagation, the constraint could be set passive
	 * (to prevent from further calls to propagation methods), but this management seems too heavy to be worthwhile
	 */

	public void propagate() throws ContradictionException {
		AbstractIntConstraint.logger.finest("VAL(" + this.v0.toString()
				+ ") = " + this.cste);
		this.v0.instantiate(this.cste, this.cIdx0);
	}
}
