// *********************************************
// *                   J-CHOCO                 *
// *   Copyright (c) F. Laburthe, 1999-2003    *
// *********************************************
// * Event-base contraint programming Engine   *
// *********************************************

// CVS Information
// File:               $RCSfile: GreaterOrEqualXYC.java,v $
// Version:            $Revision: 1.1 $
// Last Modification:  $Date: 2004/05/04 22:23:15 $
// Last Contributor:   $Author: guehene $

package choco.integer.constraints;

import choco.AbstractConstraint;
import choco.ContradictionException;
import choco.Problem;

//import choco.search.AbstractBinIntConstraint;

/**
 * Implements a constraint X > Y + C, with X and Y two variables and C a constant.
 */
public class GreaterOrEqualXYC extends AbstractBinIntConstraint {

	/**
	 * The search constant of the constraint
	 */
	protected final int cste;

	/**
	 * Constructs the constraint with the specified variables and constant.
	 *
	 * @param x0 Should be greater than <code>x0+c</code>.
	 * @param x1 Should be less than <code>x0-c</code>.
	 * @param c The search constant used in the inequality.
	 */

	public GreaterOrEqualXYC(
		final choco.integer.IntVar x0,
		final choco.integer.IntVar x1,
		final int c) {
		this.v0 = x0;
		this.v1 = x1;
		this.cste = c;
	}

	/**
	 * Propagation when a minimal bound of a variable was modified.
	 * @param idx The index of the variable.
	 * @throws choco.ContradictionException
	 */

	public void awakeOnInf(final int idx) throws ContradictionException {

		if (idx == 1) {
			AbstractIntConstraint.logger.finest("INF(" + this.v0.toString()
					+ ") >= INF(" + this.v1.toString() + ") + " + this.cste);
			this.v0.updateInf(this.v1.getInf() + this.cste, this.cIdx0);
		}
		else if (this.v0.getInf() >= this.v1.getSup() + this.cste) {
			this.setPassive();
		}
	}

	/**
	 * Propagation when a variable is instantiated.
	 *
	 * @param idx The index of the variable.
	 * @throws choco.ContradictionException
	 */

	public void awakeOnInst(final int idx) throws ContradictionException {
		if (idx == 0) {
			AbstractIntConstraint.logger.finest("SUP(" + this.v1.toString()
					+ ") <= SUP(" + this.v0.toString() + ") - " + this.cste);
			this.v1.updateSup(this.v0.getSup() - this.cste, this.cIdx1);
		}
		else if (idx == 1) {
			AbstractIntConstraint.logger.finest("INF(" + this.v0.toString()
					+ ") >= INF(" + this.v1.toString() + ") + " + this.cste);
			this.v0.updateInf(this.v1.getInf() + this.cste, this.cIdx0);
		}
		if (this.v0.getInf() >= this.v1.getSup() + this.cste) {
			this.setPassive();
		}
	}

	/**
	 * Propagation when a value <code>x</code> of variable is removed.
	 *
	 * Not implemented yet.
	 * @param idx The index of the variable.
	 * @throws choco.ContradictionException
	 */

	public void awakeOnRem(final int idx, final int x)
			throws ContradictionException {
		;
	}

	/**
	 * Propagation when a maximal bound of a variable was modified.
	 * @param idx The index of the variable.
	 * @throws choco.ContradictionException
	 */

	public void awakeOnSup(final int idx) throws ContradictionException {
		if (idx == 0) {
			AbstractIntConstraint.logger.finest("SUP(" + this.v1.toString()
					+ ") <= SUP(" + this.v0.toString() + ") - " + this.cste);
			this.v1.updateSup(this.v0.getSup() - this.cste, this.cIdx1);
		}
		else if (this.v0.getInf() >= this.v1.getSup() + this.cste) {
			this.setPassive();
		}
	}

	public void awakeOnVar(final int idx) throws ContradictionException {
		;
	}

	/**
	 * tests if the constraint is consistent with respect to the current state of domains
	 * @return true iff the constraint is bound consistent (weaker than arc consistent)
	 */
	public boolean isConsistent() {
		return this.v0.getInf() >= this.v1.getInf() + this.cste
				&& this.v1.getSup() <= this.v0.getSup() - this.cste;
	}

	/**
	 * Checks if the listeners must be checked or must fail.
	 */

	public Boolean isEntailed() {
		if (this.v0.getSup() < this.v1.getInf() + this.cste) {
			return Boolean.FALSE;
		}
		else if (this.v0.getInf() >= this.v1.getSup() + this.cste) {
			return Boolean.TRUE;
		}
		return null;
	}

	/**
	 * Checks if the constraint is satisfied when the variables are instantiated.
	 * @return true if the constraint is satisfied
	 */

	public boolean isSatisfied() {
		return this.v0.getValue() >= this.v1.getValue() + this.cste;
	}

	public AbstractConstraint opposite() {
		final Problem pb = this.getProblem();
		return (AbstractConstraint) pb
			.lt(this.v0, pb.minus(this.v1, this.cste));
		//    return new GreaterOrEqualXYC(v1, v0, 1 - cste);
	}

	/**
	 * The propagation on constraint awake events.
	 * @throws choco.ContradictionException
	 */

	public void propagate() throws ContradictionException {
		this.awakeOnInf(1);
		this.awakeOnSup(0);
	}
}
