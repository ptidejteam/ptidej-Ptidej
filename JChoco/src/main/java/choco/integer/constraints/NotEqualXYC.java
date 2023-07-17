// *********************************************
// *                   J-CHOCO                 *
// *   Copyright (c) F. Laburthe, 1999-2003    *
// *********************************************
// * Event-base contraint programming Engine   *
// *********************************************

// CVS Information
// File:               $RCSfile: NotEqualXYC.java,v $
// Version:            $Revision: 1.2 $
// Last Modification:  $Date: 2005/12/30 05:02:42 $
// Last Contributor:   $Author: guehene $

package choco.integer.constraints;

import org.junit.Assert;
import choco.AbstractConstraint;
import choco.ContradictionException;
import choco.Problem;

//import choco.search.AbstractBinIntConstraint;

/**
 * Implements a constraint X !== Y + C, with X and Y two variables and C a constant.
 */
public class NotEqualXYC extends AbstractBinIntConstraint {

	/**
	 * The search constant of the constraint
	 */
	protected final int cste;

	/**
	 * Constructs the constraint with the specified variables and constant.
	 *
	 * @param x0 first IntDomainVar
	 * @param x1 second IntDomainVar
	 * @param c The search constant used in the disequality.
	 */

	public NotEqualXYC(
		final choco.integer.IntVar x0,
		final choco.integer.IntVar x1,
		final int c) {
		this.v0 = x0;
		this.v1 = x1;
		this.cste = c;
	}

	public void awakeOnInf(final int idx) throws ContradictionException {
		if (this.v0.isInstantiated()) {
			AbstractIntConstraint.logger.finest("VAL(" + this.v1.toString()
					+ ") != VAL(" + this.v0.toString() + ") - " + this.cste);
			this.v1.removeVal(this.v0.getValue() - this.cste, this.cIdx1);
		}
		else if (this.v1.isInstantiated()) {
			AbstractIntConstraint.logger.finest("VAL(" + this.v0.toString()
					+ ") != VAL(" + this.v1.toString() + ") + " + this.cste);
			this.v0.removeVal(this.v1.getValue() + this.cste, this.cIdx0);
		}
	}

	public void awakeOnInst(final int idx) throws ContradictionException {
		if (idx == 0) {
			AbstractIntConstraint.logger.finest("VAL(" + this.v1.toString()
					+ ") != VAL(" + this.v0.toString() + ") - " + this.cste);
			this.v1.removeVal(this.v0.getValue() - this.cste, this.cIdx1);
		}
		else {
			Assert.assertTrue(idx == 1);
			AbstractIntConstraint.logger.finest("VAL(" + this.v0.toString()
					+ ") != VAL(" + this.v1.toString() + ") + " + this.cste);
			this.v0.removeVal(this.v1.getValue() + this.cste, this.cIdx0);
		}
	}

	/**
	 * Nothing to do when only a hole in a domain is made
	 */

	public void awakeOnRem(final int idx, final int x)
			throws ContradictionException {
		;
	}

	public void awakeOnSup(final int idx) throws ContradictionException {
		if (this.v0.isInstantiated()) {
			AbstractIntConstraint.logger.finest("VAL(" + this.v1.toString()
					+ ") != VAL(" + this.v0.toString() + ") - " + this.cste);
			this.v1.removeVal(this.v0.getValue() - this.cste, this.cIdx1);
		}
		else if (this.v1.isInstantiated()) {
			AbstractIntConstraint.logger.finest("VAL(" + this.v0.toString()
					+ ") != VAL(" + this.v1.toString() + ") + " + this.cste);
			this.v0.removeVal(this.v1.getValue() + this.cste, this.cIdx0);
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
		return this.v0.isInstantiated() ? this.v1.hasEnumeratedDomain() ? !this.v1
			.canBeInstantiatedTo(this.v0.getValue())
				: this.v1.getInf() != this.v0.getValue()
						&& this.v1.getSup() != this.v0.getValue()
				: this.v1.isInstantiated() ? this.v0.hasEnumeratedDomain() ? !this.v0
					.canBeInstantiatedTo(this.v1.getValue())
						: this.v0.getInf() != this.v1.getValue()
								&& this.v0.getSup() != this.v1.getValue()
						: true;
	}

	/**
	 * Checks if the listeners must be checked or must fail.
	 */

	public Boolean isEntailed() {
		if (this.v0.getSup() < this.v1.getInf() + this.cste
				|| this.v1.getSup() < this.v0.getInf() - this.cste) {
			return Boolean.TRUE;
		}
		else if (this.v0.getInf() == this.v0.getSup()
				&& this.v1.getInf() == this.v1.getSup()
				&& this.v0.getInf() == this.v1.getInf() + this.cste) {
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
		return this.v0.getValue() != this.v1.getValue() + this.cste;
	}

	public AbstractConstraint opposite() {
		//    return new EqualXYC(v0, v1, cste);
		final Problem pb = this.getProblem();
		return (AbstractConstraint) pb.eq(this.v0, pb.plus(this.v1, this.cste));
	}

	/**
	 * The one and only propagation method, using foward checking
	 */

	public void propagate() throws ContradictionException {
		if (this.v0.isInstantiated()) {
			AbstractIntConstraint.logger.finest("VAL(" + this.v1.toString()
					+ ") != VAL(" + this.v0.toString() + ") - " + this.cste);
			this.v1.removeVal(this.v0.getValue() - this.cste, this.cIdx1);
		}
		else if (this.v1.isInstantiated()) {
			AbstractIntConstraint.logger.finest("VAL(" + this.v0.toString()
					+ ") != VAL(" + this.v1.toString() + ") + " + this.cste);
			this.v0.removeVal(this.v1.getValue() + this.cste, this.cIdx0);
		}
	}
}
