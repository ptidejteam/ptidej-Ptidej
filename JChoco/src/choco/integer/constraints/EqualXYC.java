// *********************************************
// *                   J-CHOCO                 *
// *   Copyright (c) F. Laburthe, 1999-2003    *
// *********************************************
// * Event-base contraint programming Engine   *
// *********************************************

// CVS Information
// File:               $RCSfile: EqualXYC.java,v $
// Version:            $Revision: 1.2 $
// Last Modification:  $Date: 2005/12/30 05:02:42 $
// Last Contributor:   $Author: guehene $

package choco.integer.constraints;

import org.junit.Assert;
import choco.AbstractConstraint;
import choco.ContradictionException;
import choco.Problem;
import choco.integer.var.IntDomain;

//import choco.search.AbstractBinIntConstraint;

/**
 * Implements a constraint X == Y + C, with X and Y two variables and C a constant.
 */
public class EqualXYC extends AbstractBinIntConstraint {

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

	public EqualXYC(
		final choco.integer.IntVar x0,
		final choco.integer.IntVar x1,
		final int c) {
		this.v0 = x0;
		this.v1 = x1;
		this.cste = c;
	}

	public void awakeOnInf(final int idx) throws ContradictionException {
		if (idx == 0) {
			AbstractIntConstraint.logger.finest("INF(" + this.v1.toString()
					+ ") >= INF(" + this.v0.toString() + ") - " + this.cste
					+ " = " + (this.v0.getInf() - this.cste));
			this.v1.updateInf(this.v0.getInf() - this.cste, this.cIdx1);
		}
		else {
			AbstractIntConstraint.logger.finest("INF(" + this.v0.toString()
					+ ") >= INF(" + this.v1.toString() + ") + " + this.cste
					+ " = " + (this.v1.getInf() + this.cste));
			this.v0.updateInf(this.v1.getInf() + this.cste, this.cIdx0);
		}
	}

	public void awakeOnInst(final int idx) throws ContradictionException {
		if (idx == 0) {
			AbstractIntConstraint.logger.finest(this.v1.toString() + " = "
					+ this.v0.toString() + ") - " + this.cste + " = "
					+ (this.v0.getValue() - this.cste));
			this.v1.instantiate(this.v0.getValue() - this.cste, this.cIdx1);
		}
		else {
			AbstractIntConstraint.logger.finest(this.v0.toString() + " = "
					+ this.v1.toString() + ") + " + this.cste + " = "
					+ (this.v1.getValue() + this.cste));
			this.v0.instantiate(this.v1.getValue() + this.cste, this.cIdx0);
		}
	}

	public void awakeOnRem(final int idx, final int x)
			throws ContradictionException {
		if (idx == 0) {
			AbstractIntConstraint.logger.finest(this.v1.toString() + " = "
					+ this.v0.toString() + ") - " + this.cste + " != "
					+ (x - this.cste));
			this.v1.removeVal(x - this.cste, this.cIdx1);
		}
		else {
			Assert.assertTrue(idx == 1);
			AbstractIntConstraint.logger.finest(this.v0.toString() + " = "
					+ this.v1.toString() + ") + " + this.cste + " != "
					+ (x + this.cste));
			this.v0.removeVal(x + this.cste, this.cIdx0);
		}
	}

	public void awakeOnSup(final int idx) throws ContradictionException {
		if (idx == 0) {
			AbstractIntConstraint.logger.finest("SUP(" + this.v1.toString()
					+ ") <= SUP(" + this.v0.toString() + ") - " + this.cste
					+ " = " + (this.v0.getSup() - this.cste));
			this.v1.updateSup(this.v0.getSup() - this.cste, this.cIdx1);
		}
		else {
			AbstractIntConstraint.logger.finest("SUP(" + this.v0.toString()
					+ ") <= SUP(" + this.v1.toString() + ") + " + this.cste
					+ " = " + (this.v1.getSup() + this.cste));
			this.v0.updateSup(this.v1.getSup() + this.cste, this.cIdx0);
		}
	}

	public void awakeOnVar(final int idx) throws ContradictionException {
		if (idx == 0) {
			AbstractIntConstraint.logger.finest(this.v1.toString() + " = "
					+ this.v0.toString() + ") - " + this.cste + " within ["
					+ (this.v0.getInf() - this.cste) + ", "
					+ (this.v0.getSup() - this.cste) + "]");
			this.v1.updateInf(this.v0.getInf() - this.cste, this.cIdx1);
			this.v1.updateSup(this.v0.getSup() - this.cste, this.cIdx1);
			if (this.v1.hasEnumeratedDomain() && this.v0.hasEnumeratedDomain()) {
				final IntDomain dom1 = this.v1.getDomain();
				int val1;
				for (val1 = dom1.getInf(); dom1.hasNextValue(val1); val1 =
					dom1.getNextValue(val1)) {
					if (!this.v0.canBeInstantiatedTo(val1 + this.cste)) {
						AbstractIntConstraint.logger.finest(this.v1.toString()
								+ " = " + this.v0.toString() + ") - "
								+ this.cste + " != " + val1);
						this.v1.removeVal(val1, this.cIdx1);
					}
				}
			}
		}
		else {
			Assert.assertTrue(idx == 1);
			this.v0.updateInf(this.v1.getInf() + this.cste, this.cIdx0);
			this.v0.updateSup(this.v1.getSup() + this.cste, this.cIdx0);
			if (this.v1.hasEnumeratedDomain() && this.v0.hasEnumeratedDomain()) {
				final IntDomain dom0 = this.v0.getDomain();
				int val0;
				for (val0 = dom0.getInf(); dom0.hasNextValue(val0); val0 =
					dom0.getNextValue(val0)) {
					if (!this.v1.canBeInstantiatedTo(val0 - this.cste)) {
						AbstractIntConstraint.logger.finest(this.v0.toString()
								+ " = " + this.v1.toString() + ") + "
								+ this.cste + " != " + val0);
						this.v0.removeVal(val0, this.cIdx0);
					}
				}
			}
		}
	}

	/**
	 * tests if the constraint is consistent with respect to the current state of domains
	 * @return true iff the constraint is bound consistent (weaker than arc consistent)
	 */
	public boolean isConsistent() {
		return this.v0.getInf() == this.v1.getInf() + this.cste
				&& this.v0.getSup() == this.v1.getSup() + this.cste;
	}

	/**
	 * Checks if the listeners must be checked or must fail.
	 */

	public Boolean isEntailed() {
		if (this.v0.getSup() < this.v1.getInf() + this.cste
				|| this.v0.getInf() > this.v1.getSup() + this.cste) {
			return Boolean.FALSE;
		}
		else if (this.v0.isInstantiated() && this.v1.isInstantiated()
				&& this.v0.getValue() == this.v1.getValue()) {
			return Boolean.TRUE;
		}
		else {
			return null;
		}
	}

	/**
	 * Checks if the constraint is satisfied when the variables are instantiated.
	 */

	public boolean isSatisfied() {
		return this.v0.getValue() == this.v1.getValue() + this.cste;
	}

	public AbstractConstraint opposite() {
		final Problem pb = this.getProblem();
		return (AbstractConstraint) pb
			.neq(this.v0, pb.plus(this.v1, this.cste));
		// return NotEqualXYC(v0, v1, cste);
	}

	/**
	 * The one and only propagation method, using foward checking
	 */

	public void propagate() throws ContradictionException {
		AbstractIntConstraint.logger.finest("INF(" + this.v0.toString()
				+ ") >= INF(" + this.v1.toString() + ") + " + this.cste + " = "
				+ (this.v1.getInf() + this.cste));
		this.v0.updateInf(this.v1.getInf() + this.cste, this.cIdx0);
		AbstractIntConstraint.logger.finest("SUP(" + this.v0.toString()
				+ ") <= SUP(" + this.v1.toString() + ") + " + this.cste + " = "
				+ (this.v1.getSup() + this.cste));
		this.v0.updateSup(this.v1.getSup() + this.cste, this.cIdx0);
		AbstractIntConstraint.logger.finest("INF(" + this.v1.toString()
				+ ") >= INF(" + this.v0.toString() + ") - " + this.cste + " = "
				+ (this.v0.getInf() - this.cste));
		this.v1.updateInf(this.v0.getInf() - this.cste, this.cIdx1);
		AbstractIntConstraint.logger.finest("SUP(" + this.v1.toString()
				+ ") <= SUP(" + this.v0.toString() + ") - " + this.cste + " = "
				+ (this.v0.getSup() - this.cste));
		this.v1.updateSup(this.v0.getSup() - this.cste, this.cIdx1);

		// ensure that, in case of enumerated domains, holes are also propagated
		if (this.v1.hasEnumeratedDomain() && this.v0.hasEnumeratedDomain()) {
			final IntDomain dom0 = this.v0.getDomain();
			int val0;
			for (val0 = dom0.getInf(); dom0.hasNextValue(val0); val0 =
				dom0.getNextValue(val0)) {
				if (!this.v1.canBeInstantiatedTo(val0 - this.cste)) {
					AbstractIntConstraint.logger.finest(this.v0.toString()
							+ " = " + this.v1.toString() + ") + " + this.cste
							+ " != " + val0);
					this.v0.removeVal(val0, this.cIdx0);
				}
			}

			final IntDomain dom1 = this.v1.getDomain();
			int val1;
			for (val1 = dom1.getInf(); dom1.hasNextValue(val1); val1 =
				dom1.getNextValue(val1)) {
				if (!this.v0.canBeInstantiatedTo(val1 + this.cste)) {
					AbstractIntConstraint.logger.finest(this.v1.toString()
							+ " = " + this.v0.toString() + ") - " + this.cste
							+ " != " + val1);
					this.v1.removeVal(val1, this.cIdx1);
				}
			}
		}
	}
}
