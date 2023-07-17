// *********************************************
// *                   J-CHOCO                 *
// *   Copyright (c) F. Laburthe, 1999-2003    *
// *********************************************
// * Event-base contraint programming Engine   *
// *********************************************

// CVS Information
// File:               $RCSfile: IntDomainVar.java,v $
// Version:            $Revision: 1.1 $
// Last Modification:  $Date: 2004/05/04 22:23:16 $
// Last Contributor:   $Author: guehene $

package choco.integer.var;

import java.util.logging.Logger;
import choco.AbstractVar;
import choco.ContradictionException;
import choco.Problem;
import choco.integer.IntVar;
import choco.mem.Environment;
import choco.mem.StoredInt;
import choco.prop.VarEvent;
import choco.util.IntIterator;

/**
 * Implements search valued domain variables.
 */
public class IntDomainVar extends AbstractVar implements IntVar {

	/**
	 * The backtrackable value of the variable, if instantiated.
	 */

	public final StoredInt value; // voir si il faut le storer...

	/**
	 * The backtrackable domain of the variable.
	 */

	protected AbstractIntDomain domain;

	/**
	 * Reference to an object for logging trace statements related to IntDomainVar (using the java.util.logging package)
	 */

	protected static Logger logger = Logger
		.getLogger("choco.search.IntDomainVar");

	/**
	 * Constructs a new variable for the specified problem and with the
	 * specified name and bounds.
	 * @param pb The problem of the variable.
	 * @param name Its name.
	 * @param domainType the type of encoding for the domain (BOUNDS, LIST, ...)
	 * @param a Its minimal value.
	 * @param b Its maximal value.
	 */

	public IntDomainVar(
		final Problem pb,
		final String name,
		final int domainType,
		final int a,
		final int b) {
		final Environment env = pb.getEnvironment();

		this.problem = pb;
		this.name = name;
		if (domainType == IntVar.LIST) {
			this.domain = new BitSetIntDomain(this, a, b);
		}
		else {
			this.domain = new IntervalIntDomain(this, a, b);
		}
		this.value = (StoredInt) env.makeInt();
		if (a == b) {
			this.value.set(a);
		}
		this.event = new IntVarEvent(this);
	}

	// ============================================
	// Methods of the interface
	// ============================================

	/**
	 * Checks if it can be equals to another variable.
	 */

	public boolean canBeEqualTo(final IntVar x) {
		if (x.getInf() <= this.getSup()) {
			if (this.getInf() <= x.getSup()) {
				if (!this.hasEnumeratedDomain() && !x.hasEnumeratedDomain()) {
					return true;
				}
				else {
					for (final IntIterator it = this.getDomain().getIterator(); it
						.hasNext();) {
						final int v = it.next();
						if (x.canBeInstantiatedTo(v)) {
							return true;
						}
					}
					return false;
				}
			}
			else {
				return false;
			}
		}
		else {
			return false;
		}
	}

	/**
	 * Checks if the variables can be instantiated to at least one value
	 * in the array.
	 * @param sortedValList The value array.
	 * @param nVals The number of interesting value in this array.
	 */

	public boolean canBeInstantiatedIn(
		final int[] sortedValList,
		final int nVals) {
		if (this.getInf() <= sortedValList[nVals - 1]) {
			if (this.getSup() >= sortedValList[0]) {
				if (this.domain == null) {
					return true;
				}
				else {
					for (int i = 0; i < nVals; i++) {
						if (this.canBeInstantiatedTo(sortedValList[i])) {
							return true;
						}
					}
					return false;
				}
			}
			else {
				return false;
			}
		}
		else {
			return false;
		}
	}

	/**
	 * Checks if a value is still in the domain.
	 */

	public boolean canBeInstantiatedTo(final int x) {
		return this.getInf() <= x && x <= this.getSup()
				&& (this.domain == null || this.domain.contains(x));
	}

	public void fail() throws ContradictionException {
		this.problem.getChocEngine().raiseContradiction(this);
	}

	public IntDomain getDomain() {
		return this.domain;
	}

	/**
	 * Gets the domain size.
	 */

	public int getDomainSize() {
		return this.domain.getSize();
	}

	/**
	 * Gets the minimal value of the variable.
	 */

	public int getInf() {
		return this.domain.getInf();
	}

	/**
	 * Gets the next value in the domain.
	 */

	public int getNextDomainValue(final int currentv) {
		if (currentv < this.getInf()) {
			return this.getInf();
		}
		else if (this.domain == null) {
			return currentv + 1;
		}
		else {
			return this.domain.getNextValue(currentv);
		}
	}

	/**
	 * Gets the previous value in the domain.
	 */

	public int getPrevDomainValue(final int currentv) {
		if (currentv > this.getSup()) {
			return this.getSup();
		}
		else if (this.domain == null) {
			return currentv - 1;
		}
		else {
			return this.domain.getPrevValue(currentv);
		}
	}

	/**
	 * Returns a randomly choosed value in the domain.
	 *
	 * Not implemented yet.
	 */

	public int getRandomDomainValue() {
		if (this.domain == null) {
			return this.getInf();
			// TODO     return inf.get() + random(sup.get() - inf.get() + 1);
		}
		else {
			return this.domain.getRandomValue();
		}
	}

	/**
	 * Gets the maximal value of the variable.
	 */

	public int getSup() {
		return this.domain.getSup();
	}

	/**
	 * Gets the value of the variable if instantiated.
	 */

	public int getValue() {
		return this.value.get();
	}

	public boolean hasEnumeratedDomain() {
		return this.domain.isEnumerated();
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
		return this.domain.instantiate(x, idx);
	}

	/**
	 * Checks if the variables is instantiated to any value.
	 */

	public boolean isInstantiated() {
		return this.value.isKnown();
	}

	/**
	 * Checks if the variable is instantiated to a specific value.
	 */

	public boolean isInstantiatedTo(final int x) {
		return this.getValue() == x;
	}

	/**
	 * pretty printing
	 * @return a String representation of the variable
	 */
	public String pretty() {
		return this.toString() + this.domain.pretty();
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
		return this.domain.removeInterval(a, b, idx);
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
		return this.domain.removeVal(x, idx);
	}

	/**
	 * Removes a value.
	 * @throws choco.ContradictionException
	 */

	public void remVal(final int x) throws ContradictionException {
		this.removeVal(x, VarEvent.NOCAUSE);
	}

	/**
	 * Sets the maximal value.
	 */

	public void setMax(final int x) throws ContradictionException {
		this.updateSup(x, VarEvent.NOCAUSE);
	}

	/**
	 * Sets the minimum value.
	 */

	public void setMin(final int x) throws ContradictionException {
		this.updateInf(x, VarEvent.NOCAUSE);
	}

	/**
	 * Instantiates the variable.
	 * @throws choco.ContradictionException
	 */

	public void setVal(final int x) throws ContradictionException {
		this.instantiate(x, VarEvent.NOCAUSE);
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
		IntDomainVar.logger.finest("INF(" + this.toString() + "): "
				+ this.getInf() + " -> " + x);
		return this.domain.updateInf(x, idx);
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
		IntDomainVar.logger.finest("SUP(" + this.toString() + "): "
				+ this.getSup() + " -> " + x);
		return this.domain.updateSup(x, idx);
	}

	public void wipeOut() throws ContradictionException {
		throw new ContradictionException(this);
	}
}
