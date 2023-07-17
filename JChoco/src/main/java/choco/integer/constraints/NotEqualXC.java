// *********************************************
// *                   J-CHOCO                 *
// *   Copyright (c) F. Laburthe, 1999-2003    *
// *********************************************
// * Event-base contraint programming Engine   *
// *********************************************

// CVS Information
// File:               $RCSfile: NotEqualXC.java,v $
// Version:            $Revision: 1.2 $
// Last Modification:  $Date: 2005/12/30 05:02:42 $
// Last Contributor:   $Author: guehene $

package choco.integer.constraints;

import org.junit.Assert;
import choco.AbstractConstraint;
import choco.ContradictionException;
import choco.Problem;

//import choco.search.AbstractUnIntConstraint;

/**
 * Implements a constraint X <= C, with X a variable and C a constant.
 */
public class NotEqualXC extends AbstractUnIntConstraint {

	/**
	 * The search constant of the constraint
	 */
	protected final int cste;

	/**
	 * Constructs the constraint with the specified variables and constant.
	 *
	 * @param x0 the search valued domain variable
	 * @param c the search constant used in the inequality.
	 */

	public NotEqualXC(final choco.integer.IntVar x0, final int c) {
		this.v0 = x0;
		this.cste = c;
	}

	/** in case the bounds have changed and the domain is approximated by an interval,
	 *  we need to re-propagate
	 */
	public void awakeOnInf(final int idx) throws ContradictionException {
		Assert.assertTrue(idx == 0);
		if (!this.v0.hasEnumeratedDomain()) {
			this.propagate();
		}
	}

	/** When the one and only variable of the constraint becomes instantiated
	 *    Need to check that the value of the variable is not the value forbidden by the constraint
	 * @param idx  index of the variable (always 0)
	 * @throws choco.ContradictionException
	 */
	public void awakeOnInst(final int idx) throws ContradictionException {
		Assert.assertTrue(idx == 0);
		AbstractIntConstraint.logger.finest("VAL(" + this.v0.toString()
				+ ") != " + this.cste);
		if (this.v0.getValue() == this.cste) {
			this.v0.wipeOut();
		}
	}

	/** When a value is removed from the domain of the one and only variable:
	 *     Nothing to be propagated.
	 * @param idx index of the variable (always 0)
	 * @param x   value that was removed
	 * @throws choco.ContradictionException
	 */
	public void awakeOnRem(final int idx, final int x)
			throws ContradictionException {
		Assert.assertTrue(idx == 0);
	}

	/** in case the bounds have changed and the domain is approximated by an interval,
	 *  we need to re-propagate
	 */
	public void awakeOnSup(final int idx) throws ContradictionException {
		Assert.assertTrue(idx == 0);
		if (!this.v0.hasEnumeratedDomain()) {
			this.propagate();
		}
	}

	/** in case the bounds have changed and the domain is approximated by an interval,
	 *  we need to re-propagate
	 */
	public void awakeOnVar(final int idx) throws ContradictionException {
		Assert.assertTrue(idx == 0);
		if (!this.v0.hasEnumeratedDomain()) {
			this.propagate();
		}
	}

	/**
	 * tests if the constraint is consistent with respect to the current state of domains
	 * @return true iff the constraint is bound consistent (same as arc consistent)
	 */
	public boolean isConsistent() {
		return this.v0.hasEnumeratedDomain() ? !this.v0
			.canBeInstantiatedTo(this.cste) : this.v0.getInf() != this.cste
				&& this.v0.getSup() != this.cste;
	}

	/**
	 * When the whole domain of <code>v0</code> is below or above <code>cste</code>,
	 * we know for sure whether the constraint will be satisfied or not
	 */

	public Boolean isEntailed() {
		if (!this.v0.canBeInstantiatedTo(this.cste)) {
			return Boolean.TRUE;
		}
		else if (this.v0.isInstantiatedTo(this.cste)) {
			return Boolean.FALSE;
		}
		else {
			return null;
		}
	}

	/**
	 * tests if the constraint is satisfied when the variables are instantiated.
	 */

	public boolean isSatisfied() {
		Assert.assertTrue(this.v0.isInstantiated());
		return this.v0.getValue() != this.cste;
	}

	public AbstractConstraint opposite() {
		//    return new EqualXC(v0, cste);
		final Problem pb = this.getProblem();
		return (AbstractConstraint) pb.eq(this.v0, this.cste);
	}

	/**
	 * The single propagation method. <br>
	 * Note that after the first propagation, the constraint is set passive
	 * (to prevent from further calls to propagation methods)
	 */

	public void propagate() throws ContradictionException {
		AbstractIntConstraint.logger.finest("VAL(" + this.v0.toString()
				+ ") != " + this.cste);
		if (this.v0.removeVal(this.cste, this.cIdx0)) {
			this.setPassive();
		}
	}
}
