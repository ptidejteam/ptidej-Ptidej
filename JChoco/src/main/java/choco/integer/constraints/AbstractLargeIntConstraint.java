// *********************************************
// *                   J-CHOCO                 *
// *   Copyright (c) F. Laburthe, 1999-2003    *
// *********************************************
// * Event-base contraint programming Engine   *
// *********************************************

// CVS Information
// File:               $RCSfile: AbstractLargeIntConstraint.java,v $
// Version:            $Revision: 1.3 $
// Last Modification:  $Date: 2005/12/30 05:02:42 $
// Last Contributor:   $Author: guehene $

package choco.integer.constraints;

import choco.AbstractVar;
import choco.ContradictionException;
import choco.Var;
import choco.bool.AbstractCompositeConstraint;

//import choco.choco.*;
//import choco.search.AbstractIntConstraint;

/**
 * An abstract class for all implementations of listeners over many search variables.
 */
public abstract class AbstractLargeIntConstraint extends AbstractIntConstraint {

	/**
	 * The list of variables of the constraint.
	 */

	public choco.integer.IntVar[] vars;

	/**
	 * The list, containing, for each variable, the index of the constraint among all
	 * its incident listeners.
	 */

	public int[] cIndices;

	/**
	 * The search constant attached to the constraint.
	 */

	public int cste;

	/**
	 * constructor: allocates the data util for n variables
	 * @param n the number of variables involved in the constraint
	 */
	public AbstractLargeIntConstraint(final int n) {
		this.vars = new choco.integer.var.IntDomainVar[n];
		this.cIndices = new int[n];
	}

	/**
	 * Activates this listener in variables.
	 */

	public void activateConstraint() {
		for (int i = 0; i < this.vars.length; i++) {
			this.vars[i].activateConstraint(this.cIndices[i]);
		}
		if (this.hook != null) {
			this.hook.activateListener();
		}
	}

	/**
	 * Adds this constraint as new listeners in all the variables.
	 */

	public void addConstraint() {
		for (int i = 0; i < this.vars.length; i++) {
			this.setConstraintIndex(
				i,
				this.vars[i].addConstraint(this, i, true));
		}
		if (this.hook != null) {
			this.hook.addListener();
		}
	}

	public int assignIndices(final AbstractCompositeConstraint root, final int i) {
		int j = i;
		for (int k = 0; k < this.getNbVars(); k++) {
			j++;
			this.connectVar((AbstractVar) this.vars[k], j);
			this.setConstraintIndex(k, this.vars[k].getNbConstraints());
		}
		return j;
	}

	/**
	 * Deativates this listener in all the variables.
	 */

	public void deactivateConstraint() {
		for (int i = 0; i < this.vars.length; i++) {
			this.vars[i].deactivateConstraint(this.cIndices[i]);
		}
		if (this.hook != null) {
			this.hook.deactivateListener();
		}
	}

	/**
	 * Returns the index of the constraint in the specified variable.
	 */

	public int getConstraintIdx(final int i) {
		if (i >= 0 && i < this.vars.length) {
			return this.cIndices[i];
		}
		else {
			return -1;
		}
	}

	/**
	 * Gets the <code>i</code>th search valued variable.
	 */

	public choco.integer.IntVar getIntVar(final int i) {
		if (i >= 0 && i < this.getNbVars()) {
			return this.vars[i];
		}
		else {
			return null;
		}
	}

	/**
	 * Returns the number of variables.
	 */

	public int getNbVars() {
		return this.vars.length;
	}

	/**
	 * Returns the <code>i</code>th variable.
	 */

	public Var getVar(final int i) {
		if (i >= 0 && i < this.vars.length) {
			return this.vars[i];
		}
		else {
			return null;
		}
	}

	/**
	 * Checks if the constraint is active (e.g. plays a role in the propagation phase).
	 * @return true if the constraint is indeed currently active
	 */

	public boolean isActive() {
		for (int i = 0; i < this.vars.length; i++) {
			if (this.vars[i].isActive(this.cIndices[i])) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Checks wether all the variables are instantiated.
	 */

	public boolean isCompletelyInstantiated() {
		final int nVariables = this.vars.length;
		for (int i = 0; i < nVariables; i++) {
			if (!this.vars[i].isInstantiated()) {
				return false;
			}
		}
		return true;
	}

	/**
	 * Propagates the constraint awake events.
	 * @throws choco.ContradictionException
	 */

	public void propagate() throws ContradictionException {
		for (int i = 0; i < this.vars.length; i++) {
			this.awakeOnVar(i);
		}
	}

	/**
	 * Let <i>v</i> be the <i>i</i>-th var of <i>c</i>, records that <i>c</i> is the
	 * <i>n</i>-th constraint involving <i>v</i>.
	 */

	public void setConstraintIndex(final int i, final int val) {
		if (i >= 0 && i < this.vars.length) {
			this.cIndices[i] = val;
		}
		else {
			throw new Error("bug in setConstraintIndex i:" + i + " this: "
					+ this);
		}
	}
}
