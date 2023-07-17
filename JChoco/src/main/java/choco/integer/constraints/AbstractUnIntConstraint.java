// *********************************************
// *                   J-CHOCO                 *
// *   Copyright (c) F. Laburthe, 1999-2003    *
// *********************************************
// * Event-base contraint programming Engine   *
// *********************************************

// CVS Information
// File:               $RCSfile: AbstractUnIntConstraint.java,v $
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
 * An abstract class for all implementations of (unary) listeners over
 * one search variable.
 */
public abstract class AbstractUnIntConstraint extends AbstractIntConstraint {

	/**
	 * The unique variable of the constraint.
	 */

	public choco.integer.IntVar v0;

	/**
	 * The index of the constraint among all listeners of its first (and unique)
	 * variable.
	 */

	public int cIdx0;

	/**
	 * Activates this listener in all the variables.
	 */

	public void activateConstraint() {
		this.v0.activateConstraint(this.cIdx0);
		if (this.hook != null) {
			this.hook.activateListener();
		}
	}

	/**
	 * Adds this constraint as new constraint in each variable.
	 */

	public void addConstraint() {
		this.setConstraintIndex(0, this.v0.addConstraint(this, 0, true));
		if (this.hook != null) {
			this.hook.addListener();
		}
	}

	public int assignIndices(final AbstractCompositeConstraint root, final int i) {
		int j = i;
		j++;
		root.connectVar((AbstractVar) this.v0, j);
		this.setConstraintIndex(0, this.v0.getNbConstraints());
		return j;
	}

	/**
	 * Deactivates this listener in all the variables.
	 */

	public void deactivateConstraint() {
		this.v0.deactivateConstraint(this.cIdx0);
		if (this.hook != null) {
			this.hook.deactivateListener();
		}
	}

	/**
	 * Returns the index of this listeners in the variable <code>idx</code>.
	 * @param idx Index of the variable.
	 */

	public int getConstraintIdx(final int idx) {
		if (idx == 0) {
			return this.cIdx0;
		}
		else {
			return -1;
		}
	}

	/**
	 * Gets the <code>i</code>th search valued variable.
	 */

	public choco.integer.IntVar getIntVar(final int i) {
		if (i == 0) {
			return this.v0;
		}
		else {
			return null;
		}
	}

	/**
	 * Returns the number of variables: 1 for an unIntConstraint.
	 */

	public int getNbVars() {
		return 1;
	}

	/**
	 * Returns the variable number <code>i</code>. Here, <code>i</code>
	 * should be 0.
	 */

	public Var getVar(final int i) {
		if (i == 0) {
			return this.v0;
		}
		else {
			return null;
		}
	}

	/**
	 * Checks if the constraint is active.
	 */

	public boolean isActive() {
		return this.v0.isActive(this.cIdx0);
	}

	/**
	 * Checks if all the variables of the constraint are instantiated.
	 */

	public boolean isCompletelyInstantiated() {
		return this.v0.isInstantiated();
	}

	/**
	 * Propagation on the constraint awake var.
	 * @throws choco.ContradictionException
	 */

	public void propagate() throws ContradictionException {
		// Ca risque de boucler avec cette definition car awakeOnVar est defini
		// par propagate...
		this.awakeOnVar(1);
	}

	/**
	 * Let v be the i-th var of c, records that c is the n-th constraint involving v.
	 */

	public void setConstraintIndex(final int i, final int val) {
		if (i == 0) {
			this.cIdx0 = val;
		}
		else {
			throw new Error("bug in setConstraintIndex i:" + i + " this: "
					+ this);
		}
	}

}
