// *********************************************
// *                   J-CHOCO                 *
// *   Copyright (c) F. Laburthe, 1999-2003    *
// *********************************************
// * Event-base contraint programming Engine   *
// *********************************************

// CVS Information
// File:               $RCSfile: AbstractTernIntConstraint.java,v $
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
 * An abstract class for all implementations of (unary) listeners over one
 * search variable.
 */
public abstract class AbstractTernIntConstraint extends AbstractIntConstraint {

	/**
	 * The first variable of the constraint.
	 */
	public choco.integer.IntVar v0;

	/**
	 * The second variable of the constraint.
	 */
	public choco.integer.IntVar v1;

	/**
	 * The third variable of the constraint.
	 */
	public choco.integer.IntVar v2;

	/**
	 * The index of the constraint among all listeners of its first variable.
	 */
	public int cIdx0;

	/**
	 * The index of the constraint among all listeners of its second variable.
	 */
	public int cIdx1;

	/**
	 * The index of the constraint among all listeners of its third variable.
	 */
	public int cIdx2;

	/***
	 * Activates this listener in all the variables.
	 */
	public void activateConstraint() {
		this.v0.activateConstraint(this.cIdx0);
		this.v1.activateConstraint(this.cIdx1);
		this.v2.activateConstraint(this.cIdx2);
		if (this.hook != null) {
			this.hook.activateListener();
		}
	}

	/**
	 * Adds this constraint as a new listener for each variable.
	 */
	public void addConstraint() {
		this.setConstraintIndex(0, this.v0.addConstraint(this, 0, true));
		this.setConstraintIndex(1, this.v1.addConstraint(this, 1, true));
		this.setConstraintIndex(2, this.v2.addConstraint(this, 2, true));
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
		j++;
		root.connectVar((AbstractVar) this.v2, j);
		this.setConstraintIndex(2, this.v2.getNbConstraints());
		return j;
	}

	/**
	 * Deactivates this listener in all the variables.
	 */
	public void deactivateConstraint() {
		this.v0.deactivateConstraint(this.cIdx0);
		this.v1.deactivateConstraint(this.cIdx1);
		this.v2.deactivateConstraint(this.cIdx2);
		if (this.hook != null) {
			this.hook.deactivateListener();
		}
	}

	/**
	 * Returns the index of the constraint in the specified variable.
	 */
	public int getConstraintIdx(final int idx) {
		if (idx == 0) {
			return this.cIdx0;
		}
		else if (idx == 1) {
			return this.cIdx1;
		}
		else if (idx == 2) {
			return this.cIdx2;
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
		else if (i == 2) {
			return this.v2;
		}
		else {
			return null;
		}
	}

	/**
	 * Returns the number of variables.
	 */
	public int getNbVars() {
		return 3;
	}

	/**
	 * Gets the specified variable.
	 */
	public Var getVar(final int i) {
		if (i == 0) {
			return this.v0;
		}
		else if (i == 1) {
			return this.v1;
		}
		else if (i == 2) {
			return this.v2;
		}
		else {
			return null;
		}
	}

	/**
	 * Checks if the constraint is active.
	 */
	public boolean isActive() {
		return this.v0.isActive(this.cIdx0) || this.v1.isActive(this.cIdx1)
				|| this.v2.isActive(this.cIdx2);
	}

	/**
	 * Checks if all the variables are instantiated.
	 */
	public boolean isCompletelyInstantiated() {
		return this.v0.isInstantiated() && this.v1.isInstantiated()
				&& this.v2.isInstantiated();
	}

	/**
	 * Propagation for the constraint awake var.
	 * @throws choco.ContradictionException
	 */
	public void propagate() throws ContradictionException {
		this.awakeOnVar(0);
		this.awakeOnVar(1);
		this.awakeOnVar(2);
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
		else if (i == 2) {
			this.cIdx2 = val;
		}
		else {
			throw new Error("bug in setConstraintIndex i:" + i + " this: "
					+ this);
		}
	}
}
