// *********************************************
// *                   J-CHOCO                 *
// *   Copyright (c) F. Laburthe, 1999-2003    *
// *********************************************
// * Event-base contraint programming Engine   *
// *********************************************

// CVS Information
// File:               $RCSfile: GreaterOrEqualXC.java,v $
// Version:            $Revision: 1.2 $
// Last Modification:  $Date: 2005/12/30 05:02:42 $
// Last Contributor:   $Author: guehene $

package choco.integer.constraints;

import org.junit.Assert;
import choco.AbstractConstraint;
import choco.ContradictionException;

//import choco.search.AbstractUnIntConstraint;

/**
 * Implements a constraint X >= C, with X a variable and C a constant.
 */
public class GreaterOrEqualXC extends AbstractUnIntConstraint {

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

	public GreaterOrEqualXC(final choco.integer.IntVar x0, final int c) {
		this.v0 = x0;
		this.cste = c;
	}

	public void awakeOnInf(final int idx) throws ContradictionException {
		;
	}

	public void awakeOnInst(final int idx) throws ContradictionException {
		Assert.assertTrue(idx == 0);
		AbstractIntConstraint.logger.finest("VAL(" + this.v0.toString()
				+ ") >= " + this.cste);
		if (this.v0.getValue() < this.cste) {
			this.v0.wipeOut();
		}
	}

	public void awakeOnRem(final int idx, final int x)
			throws ContradictionException {
		;
	}

	public void awakeOnSup(final int idx) throws ContradictionException {
		;
	}

	public void awakeOnVar(final int idx) throws ContradictionException {
		;
	}

	/**
	 * tests if the constraint is consistent with respect to the current state of domains
	 * @return true iff the constraint is bound consistent (same as arc consistent)
	 */
	public boolean isConsistent() {
		return this.v0.getInf() >= this.cste;
	}

	/**
	 * When the whole domain of <code>v0</code> is below or above <code>cste</code>,
	 * we know for sure whether the constraint will be satisfied or not
	 */

	public Boolean isEntailed() {
		if (this.v0.getInf() >= this.cste) {
			return Boolean.TRUE;
		}
		else if (this.v0.getSup() < this.cste) {
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
		return this.v0.getValue() >= this.cste;
	}

	public AbstractConstraint opposite() {
		return (AbstractConstraint) this.getProblem().lt(this.v0, this.cste);
		//    return new LessOrEqualXC(v0, cste - 1);
	}

	/**
	 * The one and only propagation method. <br>
	 * Note that after the first propagation, the constraint is set passive
	 * (to prevent from further calls to propagation methods)
	 */

	public void propagate() throws ContradictionException {
		AbstractIntConstraint.logger.finest("VAL(" + this.v0.toString()
				+ ") >= " + this.cste);
		this.v0.updateInf(this.cste, this.cIdx0);
		this.setPassive();
	}
}
