/** -------------------------------------------------
 *                   J-CHOCO
 *   Copyright (C) F. Laburthe, 1999-2003
 * --------------------------------------------------
 *    an open-source Constraint Programming Kernel
 *          for Research and Education
 * --------------------------------------------------
 *
 * file: choco.AbstractVar.java
 * last modified by Francois 28 août 2003:14:59:09
 */
package choco;

import java.util.ArrayList;
import choco.prop.VarEvent;

/**
 * An abstract class for all implementations of domain variables
 */
public abstract class AbstractVar extends AbstractEntity implements Var {

	/**
	 * A name may be associated to each variable
	 */

	protected String name;

	/**
	 * The variable var associated to this variable.
	 */

	protected VarEvent event;

	/**
	 * The list of constraints (listeners) observing the variable.
	 */

	protected ArrayList constraints;

	/**
	 * List of indices encoding the constraint network:
	 * <i>v.indices[i]=j</i> means that v is the j-th variable
	 * of its i-th constraint.
	 */

	protected ArrayList indices;

	/**
	 * Initializes a new variable.
	 */

	public AbstractVar() {
		this.constraints = new ArrayList();
		this.indices = new ArrayList();
	}

	/**
	 * Makes a listener active.
	 */

	public void activateConstraint(final int constraintIdx) {
		this.event.activateListener(constraintIdx);
	}

	/**
	 * Adds a new constraints, and makes it active if needed.
	 */
	// TODO: this needs to become backtrackable
	public int addConstraint(
		final Constraint c,
		final int varIdx,
		final boolean active) {
		this.constraints.add(c);
		this.indices.add(Integer.valueOf(varIdx));

		final int constraintIdx = this.constraints.size() - 1;

		this.event.addListener(constraintIdx, active);

		return constraintIdx;
	}

	/**
	 * Makes a listener passive.
	 */

	public void deactivateConstraint(final int constraintIdx) {
		this.event.deactivateListener(constraintIdx);
	}

	/**
	 * removes (permanently) a constraint from the list of constraints connected to the variable
	 * @param constraintIdx
	 */
	public void eraseConstraint(final int constraintIdx) {
		this.constraints.set(constraintIdx, null);
	}

	/**
	 * Retrieve the i-th constraint involving the variable.
	 */

	public Constraint getConstraint(final int i) {
		return (Constraint) this.constraints.get(i);
	}

	/**
	 * Gets an array with all the constraints of the variable.
	 */

	public Constraint[] getConstraints() {
		final Constraint[] tab = new Constraint[this.constraints.size()];
		return (Constraint[]) this.constraints.toArray(tab);
	}

	/**
	 * Returns the variable var.
	 */

	public VarEvent getEvent() {
		return this.event;
	}

	/**
	 * Gets an array with all the indices.
	 */

	public Integer[] getIndices() {
		final Integer[] tab = new Integer[this.constraints.size()];
		return (Integer[]) this.indices.toArray(tab);
	}

	// ============================================
	// Managing Listeners.
	// ============================================

	/**
	 * Returns the number of constraints involving the variable.
	 */

	public int getNbConstraints() {
		return this.constraints.size();
	}

	// Si besoin de plus de precision, on pourra faire des methodes dediees
	// a differents types de Listener : I_IncInfListener....
	// soit addNewIncInfListener, etc. Chaque contrainte pourra alors choisir
	// entre appeler la methode par defaut qui modifie tout le monde pareil
	// (il faudra juste la modifier pour lexecuter sur chaque evenement) ou
	// appeler la methode specialisee...

	/**
	 * Checks if the var associated with the <code>idx</code>th constraint
	 * is active.
	 */

	public boolean isActive(final int idx1) {
		return this.event.isActive(idx1);
	}

	/**
	 * useful for debugging
	 */
	public String toString() {
		return this.name;
	}
}
