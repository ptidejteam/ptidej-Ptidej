// *********************************************
// *                   J-CHOCO                 *
// *   Copyright (c) F. Laburthe, 1999-2003    *
// *********************************************
// * Event-base contraint programming Engine   *
// *********************************************

// CVS Information
// File:               $RCSfile: Var.java,v $
// Version:            $Revision: 1.1 $
// Last Modification:  $Date: 2004/05/04 22:23:15 $
// Last Contributor:   $Author: guehene $

package choco;

import choco.prop.VarEvent;

/**
 * Interface for all implementations of domain variables.
 */
public interface Var extends Entity {

	/**
	 * Adds a listener.
	 */

	public void activateConstraint(int constraintIdx);

	/**
	 * Adds a new listener for the variable, that is a constraint which
	 * should be informed as soon as the variable domain is modified.
	 * (a.k.a. activateListener in Claire version)
	 */

	public int addConstraint(Constraint c, int varIdx, boolean active);

	/**
	 * Removes a listener.
	 */

	public void deactivateConstraint(int constraintIdx);

	/**
	 * a constraint may fail during propagation, raising a contradiction
	 */

	public void fail() throws ContradictionException;

	/**
	 * Returns the <code>i</code>th constraint. <code>i</code>
	 * should be more than or equal to 0, and less or equal to
	 * the number of constraint minus 1.
	 */

	public Constraint getConstraint(int i);

	/**
	 * Returns the list of listeners.
	 */

	public Constraint[] getConstraints();

	/**
	 * returns the object used by the propagation engine to model a propagation event associated to the variable
	 * (an update to its domain)
	 * @return  the propagation event
	 */
	public VarEvent getEvent();

	/**
	 * Returns the list of indices.
	 * TODO: Should be modified to return int[] !
	 */

	public Integer[] getIndices();

	/**
	 * Returns the number of listeners involving the variable.
	 */

	public int getNbConstraints();

	/**
	 * Returns the problem to which it belongs.
	 */

	public Problem getProblem();

	/**
	 * Checks if the var associated with the <code>idx</code>th constraint
	 * is active.
	 */

	public boolean isActive(int idx);

	/**
	 * <b>Public user API:</b>
	 *    <i>Domains :</i> testing whether a variable is instantiated or not.
	 */

	public boolean isInstantiated();

}
