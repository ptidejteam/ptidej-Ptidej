// *********************************************
// *                   J-CHOCO                 *
// *   Copyright (c) F. Laburthe, 1999-2003    *
// *********************************************
// * Event-base contraint programming Engine   *
// *********************************************

// CVS Information
// File:               $RCSfile: Constraint.java,v $
// Version:            $Revision: 1.1 $
// Last Modification:  $Date: 2004/05/04 22:23:15 $
// Last Contributor:   $Author: guehene $

package choco;

import choco.bool.AbstractCompositeConstraint;
import choco.model.IConstraintPlugin;
import choco.model.IVarListener;
import choco.prop.PropagationEvent;

/**
 * An interface for all implementations of listeners.
 */
public interface Constraint extends Entity, IVarListener {

	/**
	 * <i>Network management:</i>
	 *  Adding back a constraint that was some time before in the network.
	 * (a.k.a. reconnect in Claire version).
	 */

	public void activateListener();

	/**
	 * <i>Network management:</i>
	 *   Adding the constraint to the network. (a.k.a. connect in Claire version).
	 */

	public void addListener();

	/**
	 * performs the global numbering (wrt root) of the variables contained in the subtree this, starting from i
	 * @param root the overall root constraint, for which the variables are numbered
	 * @param i the index that will assigned to the first variable in the subtree this (originally 0)
	 * @return the index of the last variable in the subtree
	 */
	public int assignIndices(AbstractCompositeConstraint root, int i);

	/**
	 * <i>Propagation:</i>
	 *     Propagating the constraint for the very first time until local
	 * consistency is reached.
	 */

	public void awake() throws ContradictionException;

	/**
	 * <i>Propagation:</i>
	 *     Propagating the constraint when the domain of a variable has been
	 * modified (shrunk) since the last consistent state.
	 */

	public void awakeOnVar(int idx) throws ContradictionException;

	/**
	 * <i>Network management :</i>
	 * Removing the constraint from the network.
	 * (a.k.a. disconnect in Claire version).
	 */

	public void deactivateListener();

	/**
	 * <i>Network management:</i>
	 *    Among all listeners linked to the idx-th variable of c,
	 *    find the index of constraint c.
	 *    @param idx index of the variable in the constraint
	 */

	public int getConstraintIdx(int idx);

	/**
	 * Returns the constraint awake var associated with this constraint.
	 */

	public PropagationEvent getEvent();

	/**
	 * <i>Network management:</i>
	 *     Get the number of variables involved in the constraint.
	 */

	public int getNbVars();

	/**
	 * Returns the constraint plugin. Useful for extending the solver.
	 */

	public IConstraintPlugin getPlugIn();

	/**
	 * <i>Propagation:</i>
	 *    Accessing the priority level of the queue handling the propagation
	 * of the constraint. Results range from 1 (most reactive, for listeners
	 * with fast propagation algorithms) to 4 (most delayed, for listeners
	 * with lengthy propagation algorithms).
	 */

	public int getPriority();

	/**
	 * <i>Network management:</i>
	 *     Accessing the ith variable of a constraint.
	 *    @param i index of the variable in the constraint
	 */

	public Var getVar(int i);

	/**
	 * computes the index of the i-th variable in the counter-opposite of the constraint
	 * @param i the index of the variable in the current constraint (this)
	 * @return the index of the variable in the opposite constraint (this.opposite())
	 */
	public int getVarIdxInOpposite(int i);

	/**
	 * <i>Propagation:</i>
	 *   A constraint is active if it is connected to the network and if it
	 * does propagate.
	 */

	public boolean isActive();

	/**
	 * <i>Utility:</i>
	 *    Testing if all variables involved in the constraint are instantiated.
	 */

	public boolean isCompletelyInstantiated();

	/**
	 * tests if the constraint is consistent with respect to the current state of domains
	 * @return
	 */
	public boolean isConsistent();

	/**
	 * Checks whether the constraint is definitely satisfied, no matter what further restrictions
	 * occur to the domain of its variables.
	 */
	public Boolean isEntailed();

	/**
	 * <i>Semantic:</i>
	 *    Testing if the constraint is satisfied.
	 *    Note that all variables involved in the constraint must be
	 *  instantiated when this method is called.
	 */

	public boolean isSatisfied();

	/**
	  * computes the constraint modelling the counter-opposite condition of this
	  * @return
	  */
	public AbstractConstraint opposite();

	/**
	 * <i>Propagation:</i>
	 *     Propagating the constraint until local consistency is reached.
	 */

	public void propagate() throws ContradictionException;

	/**
	 * <i>Propagation:</i>
	 *   un-freezes a constraint
	 *   [a constraint is active if it is connected to the network and if it
	 *   does propagate]
	 */

	public void setActive();

	/**
	 * <i>Network management:</i>
	 *    Storing that among all listeners linked to the i-th variable of c,
	 *    this (the current constraint) is found at index idx.
	 *    @param i   index of the variable in the constraint
	 *    @param idx index of the constraint in the among all listeners linked to that variable
	 */

	public void setConstraintIndex(int i, int idx);

	/**
	 * <i>Propagation:</i>
	 *   freezes a constraint
	 *   [a constraint is active if it is connected to the network and if it
	 *   does propagate]
	 */

	public void setPassive();

}