/** -------------------------------------------------
 *                   J-CHOCO
 *   Copyright (C) F. Laburthe, 1999-2003
 * --------------------------------------------------
 *    an open-source Constraint Programming Kernel
 *          for Research and Education
 * --------------------------------------------------
 *
 * file: choco.AbstractConstraint.java
 * last modified by Francois 28 aoet 2003:14:59:09
 */
package choco;

import choco.model.IConstraintPlugin;
import choco.prop.ChocEngine;
import choco.prop.ConstraintEvent;
import choco.prop.EventQueue;
import choco.prop.PropagationEvent;

/**
 * An abstract class for all implementations of listeners
 */
public abstract class AbstractConstraint extends AbstractEntity implements
		Constraint {

	/**
	 * The priority of the constraint.
	 */

	protected int priority;

	/**
	 * The constraint <i>awake</i> var attached to the constraint.
	 */

	protected ConstraintEvent constAwakeEvent;

	/**
	 * A field for attaching additional data util (useful for PaLM)
	 */
	protected IConstraintPlugin hook;

	/**
	 * Constraucts a constraint with the priority 0.
	 */

	public AbstractConstraint() {
		this(0);
	}

	/**
	 * Constructs a constraint with the specified priority.
	 * @param priority The wished priority.
	 */

	public AbstractConstraint(final int priority) {
		this.priority = priority;
		this.constAwakeEvent = new ConstraintEvent(this, false, priority);
	}

	/**
	 * Activates the listener in all the variables.
	 */

	public void activateListener() {
		final int n = this.getNbVars();
		for (int i = 0; i < n; i++) {
			this.getVar(i).activateConstraint(this.getConstraintIdx(i));
		}
		if (this.hook != null) {
			this.hook.activateListener();
		}
	}

	/**
	 * This function connects a constraint with its variables in several ways.
	 *  Note that it may only be called once the constraint
	 *  has been fully created and is being posted to a problem.
	 *  Note that it should be called only once per constraint.
	 */

	public void addListener() {
		final int n = this.getNbVars();
		for (int i = 0; i < n; i++) {
			this.setConstraintIndex(
				i,
				this.getVar(i).addConstraint(this, i, true));
		}
		if (this.hook != null) {
			this.hook.addListener();
		}
	}

	/**
	 * Default initial propagation: full constraint re-propagation.
	 */

	public void awake() throws ContradictionException {
		this.propagate();
	}

	/**
	 * Default propagation on variable revision: full constraint re-propagation.
	 */

	public void awakeOnVar(final int idx) throws ContradictionException {
		this.propagate();
	}

	public void connectVar(final AbstractVar v, final int j) {
		this.setConstraintIndex(j, v.addConstraint(this, j, true));
	}

	/**
	 * Initial propagation of the constraint.
	 */

	public void constAwake(final boolean isInitialPropagation) {
		((ChocEngine) this.getProblem().getPropagationEngine()).postConstAwake(
			this,
			isInitialPropagation);
	}

	/**
	 * Deactivates this listener in all the variables.
	 */

	public void deactivateListener() {
		final int n = this.getNbVars();
		for (int i = 0; i < n; i++) {
			this.getVar(i).deactivateConstraint(this.getConstraintIdx(i));
		}
		if (this.hook != null) {
			this.hook.deactivateListener();
		}
	}

	/**
	 * Deactivates this listener in all the variables.
	 */

	public void deactivateListener(final int varIndex) {
		this.getVar(varIndex).deactivateConstraint(
			this.getConstraintIdx(varIndex));
		if (this.hook != null) {
			this.hook.deactivateListener(varIndex);
		}
	}

	/**
	 * Removes a constraint from the network.
	 * Beware, this is a permanent removal, it may not be backtracked
	 */

	public void delete() {
		final Constraint rootConstraint = this.getRootConstraint();
		if (rootConstraint == this) {
			this.setPassive();
			this.getProblem().eraseConstraint(this.getSelfIndex());
			for (int i = 0; i < this.getNbVars(); i++) {
				final AbstractVar v = (AbstractVar) this.getVar(i);
				final int cidx = this.getConstraintIdx(i);
				v.eraseConstraint(cidx);
			}
		}
	}

	/**
	 * raise a contradiction during propagation when the constraint can definitely not be satisfied given the current domains
	 */
	public void fail() throws ContradictionException {
		this.getProblem().getPropagationEngine().raiseContradiction(this);
	}

	/**
	 * Returns the constraint awake var attached to the constraint.
	 */

	public PropagationEvent getEvent() {
		return this.constAwakeEvent;
	}

	/**
	 * Returns the constraint plugin. Useful for extending the solver.
	 */

	public IConstraintPlugin getPlugIn() {
		return this.hook;
	}

	/**
	 * Returns the priority.
	 */

	public int getPriority() {
		return this.priority;
	}
	/**
	 * Retrieve the problem of the constraint.
	 */

	// ??????????? add the case when the constraint c involves only
	// constant variables, considered as belonging to CURRENT_PB
	public Problem getProblem() {
		final int nVars = this.getNbVars();
		for (int i = 0; i < nVars; i++) {
			Var v = null;
			v = this.getVar(i);
			if (!(v.getProblem() == null)) {
				return v.getProblem();
			}
		}
		return null;
	}

	/**
	 * Returns the constraint that is in the network and involving the
	 * current constraint as a subconstraint.
	 */

	Constraint getRootConstraint() {
		Constraint rootConstraint = null;
		rootConstraint = this.getVar(0).getConstraint(this.getConstraintIdx(0));
		return rootConstraint;
	}

	/**
	 * returns the (global) index of the constraint among all constraints of the problem
	 */
	public int getSelfIndex() {
		return this.getProblem().getConstraintIndex(this);
	}

	// defaut implementation: returns the same numbering in a constraint and its counterpart.
	public int getVarIdxInOpposite(final int i) {
		return i;
	}

	/**
	* Checks if the constraint is active (e.g. plays a role in the propagation phase).
	* @return true if the constraint is indeed currently active
	*/

	public boolean isActive() {
		for (int i = 0; i < this.getNbVars(); i++) {
			if (this.isActive(i)) {
				return true;
			}
		}
		return false;
	}

	public boolean isActive(final int varIdx) {
		return this.getVar(varIdx).isActive(this.getConstraintIdx(varIdx));
	}

	public Boolean isEntailed() {
		if (this.isCompletelyInstantiated()) {
			return Boolean.valueOf(this.isSatisfied());
		}
		else {
			return null;
		}

	}

	public AbstractConstraint opposite() {
		throw new UnsupportedOperationException();
	}

	/**
	 * Un-freezing a constraint (this is useful for mimicking dynamic
	 * constraint posts...).
	 */

	public void setActive() {
		final Constraint rootConstraint = this.getRootConstraint();
		if (rootConstraint == this) {
			if (!this.isActive()) {
				this.activateListener();
				this.constAwake(true);
			}
		}
	}

	/**
	 * Freezing a constraint (this is useful for backtracking when mimicking
	 * dynamic constraint posts...).
	 */

	public void setPassive() {
		final Constraint rootConstraint = this.getRootConstraint();
		if (rootConstraint == this) {
			if (this.isActive()) {
				final ConstraintEvent evt = this.constAwakeEvent;
				final EventQueue q =
					((ChocEngine) this.getProblem().getPropagationEngine())
						.getQueue(evt);
				this.deactivateListener();
				q.remove(evt);
			}
		}
	}

	/**
	 * making the constraint passive upon events to a variable
	 * @param varIndex the index of the variable in the constraint upon which propagation becomes passive
	 */
	public void setPassive(final int varIndex) {
		final Constraint rootConstraint = this.getRootConstraint();
		if (rootConstraint == this) {
			if (this.isActive(varIndex)) {
				this.deactivateListener(varIndex);
			}
		}
	}
}
