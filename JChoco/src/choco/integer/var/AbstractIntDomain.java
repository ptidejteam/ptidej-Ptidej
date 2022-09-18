package choco.integer.var;

import java.util.logging.Logger;
import choco.AbstractEntity;
import choco.ContradictionException;
import choco.prop.VarEvent;
import choco.util.IntIterator;

// *********************************************
// *                   J-CHOCO                 *
// *   Copyright (c) F. Laburthe, 1999-2003    *
// *********************************************
// * Event-base contraint programming Engine   *
// *********************************************

// CVS Information
// File:               $RCSfile: AbstractIntDomain.java,v $
// Version:            $Revision: 1.2 $
// Last Modification:  $Date: 2004/05/26 22:00:49 $
// Last Contributor:   $Author: guehene $

public abstract class AbstractIntDomain extends AbstractEntity implements
		IntDomain {
	protected class IntDomainIterator implements IntIterator {
		protected AbstractIntDomain domain;
		protected int currentValue = Integer.MIN_VALUE;

		public IntDomainIterator(final AbstractIntDomain dom) {
			this.domain = dom;
			this.currentValue = Integer.MIN_VALUE; // dom.getInf();
		}

		public boolean hasNext() {
			return Integer.MIN_VALUE == this.currentValue ? true
					: this.currentValue < this.domain.getSup();
		}

		public int next() {
			this.currentValue =
				Integer.MIN_VALUE == this.currentValue ? this.domain.getInf()
						: this.domain.getNextValue(this.currentValue);
			return this.currentValue;
		}

		public void remove() {
			if (this.currentValue == Integer.MIN_VALUE) {
				throw new IllegalStateException();
			}
			else {
				throw new UnsupportedOperationException();
			}
		}
	}

	/**
	 * Reference to an object for logging trace statements related to domains of search variables (using the java.util.logging package)
	 */
	protected static Logger logger = Logger.getLogger("choco.integer.domain");

	/**
	 * The involved variable.
	 */
	protected choco.integer.var.IntDomainVar variable;

	/**
	 * for the delta domain: current value of the inf (domain lower bound) when the bound started beeing propagated
	 * (just to check that it does not change during the propagation phase)
	 */
	protected int currentInfPropagated;

	/**
	 * for the delta domain: current value of the sup (domain upper bound) when the bound started beeing propagated
	 * (just to check that it does not change during the propagation phase)
	 */
	protected int currentSupPropagated;

	/**
	 * Instantiating a variable to an search value. Returns true if this was
	 * a real modification or not
	 */

	protected boolean _instantiate(final int x) throws ContradictionException {
		if (this.variable.isInstantiated()) {
			if (this.variable.getValue() != x) {
				throw new ContradictionException(this);
			}
			else {
				return false;
			}
		}
		else {
			if (x < this.getInf() || x > this.getSup()) {
				throw new ContradictionException(this.getProblem());
			}
			else if (this.contains(x)) {
				this.restrict(x);
			}
			else {
				throw new ContradictionException(this);
			}
			this.updateSup(x);
			this.updateInf(x);
			this.variable.value.set(x);
			return true;
		}
	}

	/**
	 * Removing a value from the domain of a variable. Returns true if this
	 * was a real modification on the domain.
	 */
	protected boolean _removeVal(final int x) throws ContradictionException {
		final int infv = this.getInf(), supv = this.getSup();
		if (infv <= x && x <= supv) {
			if (x == infv) {
				this._updateInf(x + 1);
				if (this.getInf() == supv) {
					this._instantiate(supv);
				}
				return true;
			}
			else if (x == supv) {
				this._updateSup(x - 1);
				if (this.getSup() == infv) {
					this._instantiate(infv);
				}
				return true;
			}
			else {
				return this.remove(x);
			}
		}
		else {
			return false;
		}
	}

	/**
	 * Improving the lower bound.
	 */

	// note: one could have thrown an OutOfDomainException in case (x > IStateInt.MAXINT)
	protected boolean _updateInf(final int x) throws ContradictionException {
		if (x > this.getInf()) {
			if (x > this.getSup()) {
				throw new ContradictionException(this);
			}
			else {
				this.updateInf(x);
				return true;
			}
		}
		else {
			return false;
		}
	}

	/**
	 * Improving the upper bound.
	 */
	protected boolean _updateSup(final int x) throws ContradictionException {
		if (x < this.getSup()) {
			if (x < this.getInf()) {
				//if (x < IStateInt.MININT)
				throw new ContradictionException(this);
			}
			else {
				this.updateSup(x);
				return true;
			}
		}
		else {
			return false;
		}
	}

	public void clearDeltaDomain() {
		this.currentInfPropagated = Integer.MIN_VALUE;
		this.currentSupPropagated = Integer.MAX_VALUE;
	}

	public void freezeDeltaDomain() {
		this.currentInfPropagated = this.getInf();
		this.currentSupPropagated = this.getSup();
	}

	// ============================================
	// Private methods for maintaining the
	// domain.
	// ============================================

	/**
	 * Returns an getIterator.
	 */

	public IntIterator getIterator() {
		return new IntDomainIterator(this);
	}

	public boolean getReleasedDeltaDomain() {
		return true;
	}

	/**
	 * Internal var: instantiation of the variable caused by its i-th constraint
	 * Returns a boolean indicating whether the call indeed added new information.
	 * @param x 	the new upper bound
	 * @param idx  the index of the constraint (among all constraints linked to the
	 * variable) responsible for the update
	 */

	public boolean instantiate(final int x, final int idx)
			throws ContradictionException {
		if (this._instantiate(x)) {
			AbstractIntDomain.logger.finest("INST(" + this.toString() + "): "
					+ x);
			this.problem.getChocEngine().postInstInt(this.variable, idx);
			return true;
		}
		else {
			return false;
		}
	}

	public boolean releaseDeltaDomain() {
		final boolean noNewUpdate =
			this.getInf() == this.currentInfPropagated
					&& this.getSup() == this.currentSupPropagated;
		this.currentInfPropagated = Integer.MIN_VALUE;
		this.currentSupPropagated = Integer.MAX_VALUE;
		return noNewUpdate;
	}

	/**
	 * Internal var: remove an interval (a sequence of consecutive values) from
	 * the domain of a variable caused by its i-th constraint.
	 * Returns a boolean indicating whether the call indeed added new information.
	 * @param a 	the first removed value
	 * @param b 	the last removed value
	 * @param idx  the index of the constraint (among all constraints linked to the variable)
	 * responsible for the update
	 */

	public boolean removeInterval(final int a, final int b, final int idx)
			throws ContradictionException {
		if (a <= this.getInf()) {
			return this.updateInf(b + 1, idx);
		}
		else if (this.getSup() <= b) {
			return this.updateSup(a - 1, idx);
		}
		else if (this.variable.hasEnumeratedDomain()) { // TODO: really ugly .........
			boolean anyChange = false;
			for (int v = a; v <= b; v++) {
				if (this.removeVal(v, idx)) {
					anyChange = true;
				}
			}
			return anyChange;
		}
		else {
			return false;
		}
	}

	/**
	 * Internal var: update (value removal) on the domain of a variable caused by
	 * its i-th constraint.
	 * <i>Note:</i> Whenever the hole results in a stronger var (such as a bound update or
	 *    an instantiation, then we forget about the index of the var generating constraint.
	 *    Indeed the propagated var is stronger than the initial one that
	 *    was generated; thus the generating constraint should be informed
	 *    about such a new var.
	 * Returns a boolean indicating whether the call indeed added new information.
	 * @param x    The removed value
	 * @param idx  The index of the constraint (among all constraints linked to the variable) responsible for the update
	 */

	public boolean removeVal(final int x, final int idx)
			throws ContradictionException {
		if (this._removeVal(x)) {
			AbstractIntDomain.logger.finest("REM(" + this.toString() + "): "
					+ x);
			if (this.getInf() == this.getSup()) {
				this.problem.getChocEngine().postInstInt(
					this.variable,
					VarEvent.NOCAUSE);
			}
			else if (x < this.getInf()) {
				this.problem.getChocEngine().postUpdateInf(
					this.variable,
					VarEvent.NOCAUSE);
			}
			else if (x > this.getSup()) {
				this.problem.getChocEngine().postUpdateSup(
					this.variable,
					VarEvent.NOCAUSE);
			}
			else {
				this.problem.getChocEngine().postRemoveVal(
					this.variable,
					x,
					idx);
			}
			return true;
		}
		else {
			return false;
		}
	}

	/**
	 * Internal var: update on the variable lower bound caused by its i-th
	 * constraint.
	 * Returns a boolean indicating whether the call indeed added new information
	 * @param x    The new lower bound.
	 * @param idx  The index of the constraint (among all constraints linked to
	 * the variable) responsible for the update.
	 */

	public boolean updateInf(final int x, final int idx)
			throws ContradictionException {
		this.getInf();
		if (this._updateInf(x)) {
			int cause = VarEvent.NOCAUSE;
			if (this.getInf() == x) {
				cause = idx;
			}
			if (this.getSup() == this.getInf()) {
				this.instantiate(this.getInf(), cause);
			}
			else {
				this.problem
					.getChocEngine()
					.postUpdateInf(this.variable, cause);
			}
			// TODO      problem.getChocEngine().postUpdateInf(variable, cause, oldinf);
			return true;
		}
		else {
			return false;
		}
	}

	/**
	* Internal var: update on the variable upper bound caused by its i-th
	* constraint.
	* Returns a boolean indicating whether the call indeed added new information.
	* @param x    The new upper bound
	* @param idx  The index of the constraint (among all constraints linked to
	* the variable) responsible for the update
	*/
	public boolean updateSup(final int x, final int idx)
			throws ContradictionException {
		if (this._updateSup(x)) {
			int cause = VarEvent.NOCAUSE;
			if (this.getSup() == x) {
				cause = idx;
			}
			if (this.getInf() == this.getSup()) {
				this.instantiate(this.getSup(), cause);
			}
			else {
				this.problem
					.getChocEngine()
					.postUpdateSup(this.variable, cause);
			}
			return true;
		}
		else {
			return false;
		}
	}
}
