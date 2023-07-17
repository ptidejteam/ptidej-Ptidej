// *********************************************
// *                   J-CHOCO                 *
// *   Copyright (c) F. Laburthe, 1999-2003    *
// *********************************************
// * Event-base contraint programming Engine   *
// *********************************************

// CVS Information
// File:               $RCSfile: AbstractBinIntConstraint.java,v $
// Version:            $Revision: 1.3 $
// Last Modification:  $Date: 2005/12/30 05:02:42 $
// Last Contributor:   $Author: guehene $

package choco.integer.constraints;

import choco.AbstractVar;
import choco.ContradictionException;
import choco.Var;
import choco.bool.AbstractCompositeConstraint;

// import choco.search.AbstractIntConstraint;

/**
 * An abstract class for all implementations of (binary) constraints over two search variable.
 */
public abstract class AbstractBinIntConstraint extends AbstractIntConstraint {

	/**
	 * The first variable of the constraint.
	 */

	public choco.integer.IntVar v0;

	/**
	 * The second variable of the constraint.
	 */

	public choco.integer.IntVar v1;

	/**
	 * The index of the constraint among all listeners of its first variable.
	 */

	public int cIdx0;

	/**
	 * The index of the constraint among all listeners of its second variable.
	 */

	public int cIdx1;

	/**
	 * Activates this listener in all the variables.
	 */

	public void activateConstraint() {
		this.v0.activateConstraint(this.cIdx0);
		this.v1.activateConstraint(this.cIdx1);
		if (this.hook != null) {
			this.hook.activateListener();
		}
	}

	/**
	 * Adds this constraint as new listeners for all the variables.
	 */

	public void addConstraint() {
		this.setConstraintIndex(0, this.v0.addConstraint(this, 0, true));
		this.setConstraintIndex(1, this.v1.addConstraint(this, 1, true));
		if (this.hook != null) {
			this.hook.addListener();
		}
	}

	public int assignIndices(final AbstractCompositeConstraint root, final int i) {
		int j = i;
		j++;
		root.connectVar((AbstractVar) this.v0, j);
		this.setConstraintIndex(0, this.v0.getNbConstraints());
		j++;
		root.connectVar((AbstractVar) this.v1, j);
		this.setConstraintIndex(1, this.v1.getNbConstraints());
		return j;
	}

	/**
	 * Desactivate this listener from all the variables.
	 */

	public void deactivateConstraint() {
		this.v0.deactivateConstraint(this.cIdx0);
		this.v1.deactivateConstraint(this.cIdx1);
		if (this.hook != null) {
			this.hook.deactivateListener();
		}
	}

	/**
	 * Returns the index of this constraint for the specified variables.
	 */

	public int getConstraintIdx(final int idx) {
		if (idx == 0) {
			return this.cIdx0;
		}
		else if (idx == 1) {
			return this.cIdx1;
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
		else if (i == 1) {
			return this.v1;
		}
		else {
			return null;
		}
	}

	/**
	 * Returns the number of varibles.
	 */

	public int getNbVars() {
		return 2;
	}

	/**
	 * Returns the specified variable.
	 */

	public Var getVar(final int i) {
		if (i == 0) {
			return this.v0;
		}
		else if (i == 1) {
			return this.v1;
		}
		else {
			return null;
		}
	}

	/**
	 * Checks if the constraint is active.
	 */

	public boolean isActive() {
		return this.v0.isActive(this.cIdx0) || this.v1.isActive(this.cIdx1);
	}

	/**
	 * Checks if all the variables are instantiated.
	 */

	public boolean isCompletelyInstantiated() {
		return this.v0.isInstantiated() && this.v1.isInstantiated();
	}

	/**
	 * Propagates on the constraint awake events.
	 * @throws choco.ContradictionException
	 */

	public void propagate() throws ContradictionException {
		this.awakeOnVar(0);
		this.awakeOnVar(1);
	}

	/**
	 * Let v be the i-th var of c, records that c is the n-th constraint involving v.
	 */

	public void setConstraintIndex(final int i, final int val) {
		if (i == 0) {
			this.cIdx0 = val;
		}
		else if (i == 1) {
			this.cIdx1 = val;
		}
		else {
			throw new Error("bug in setConstraintIndex i:" + i + " this: "
					+ this);
		}
	}

}
