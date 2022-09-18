// *********************************************
// *                   J-CHOCO                 *
// *   Copyright (c) F. Laburthe, 1999-2003    *
// *********************************************
// * Event-base contraint programming Engine   *
// *********************************************

// CVS Information
// File:               $RCSfile: IVarListener.java,v $
// Version:            $Revision: 1.1 $
// Last Modification:  $Date: 2004/05/04 22:23:15 $
// Last Contributor:   $Author: guehene $

package choco.model;

import java.util.EventListener;
import choco.ContradictionException;

/**
 * An interface for all the search variable listeners.
 */
public interface IVarListener extends EventListener {

	/**
	 * Reacts to a modification of the variable
	 * @param varIndex the index of the variable in the listener
	 * @param evt the object representing the change on the variable
	 */
	//public void propagateEvent(int varIndex, VarEvent evt)  throws ContradictionException;

	/**
	 * Activates the listener in all the variables.
	 */

	public void activateListener();

	/**
	 * This function connects a constraint with its variables in several ways.
	 *  Note that it may only be called once the constraint
	 *  has been fully created and is being posted to a problem.
	 *  Note that it should be called only once per constraint.
	 */

	public void addListener();

	/**
	 * propagation on domain revision.
	 */
	public void awakeOnVar(int idx) throws ContradictionException;
	/**
	 * Deactivates this listener in all the variables.
	 */

	public void deactivateListener();

	/**
	 * Deactivates this listener in all the variables.
	 */

	public void deactivateListener(int varIndex);

}
