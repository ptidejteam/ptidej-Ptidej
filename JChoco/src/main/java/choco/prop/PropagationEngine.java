// *********************************************
// *                   J-CHOCO                 *
// *   Copyright (c) F. Laburthe, 1999-2003    *
// *********************************************
// * Event-base contraint programming Engine   *
// *********************************************

// CVS Information
// File:               $RCSfile: PropagationEngine.java,v $
// Version:            $Revision: 1.1 $
// Last Modification:  $Date: 2004/05/04 22:23:15 $
// Last Contributor:   $Author: guehene $

package choco.prop;

import choco.ContradictionException;
import choco.Entity;

/**
 * An interface for all implementations of propagation engines.
 */
public interface PropagationEngine extends Entity {

	/**
	 * checking that the propagation engine remains in a proper state
	 */
	public boolean checkCleanState();

	/**
	 * Removes all pending events (used when interrupting a propagation because
	 * a contradiction has been raised)
	 */
	public void flushEvents();

	/**
	 * Retrieving the cause of the last contradiction.
	 */

	public Entity getContradictionCause();

	/**
	 * Returns the next active var queue to propagate some events.
	 * If it returns null, the propagation is finished.
	 */

	public EventQueue getNextActiveEventQueue();

	/**
	 * Raising a contradiction with no cause.
	 */

	public void raiseContradiction() throws ContradictionException;

	/**
	 * Raising a contradiction with a cause.
	 */

	public void raiseContradiction(Entity cause) throws ContradictionException;

	/**
	 * Recording the cause of the last contradiction.
	 */

	public void setContradictionCause(Entity cause);

	// Event psots depends on the implementation => should not be in the interface

	/** posting a new var (lower bound increase on the domain of an search variable)
	 * to the engine (will stored in the appropriate queue) */
	//public void postUpdateInf(IntDomainVar v, int idx);

	/** posting a new var (upper bound increase on the domain of an search variable)
	 * to the engine (will stored in the appropriate queue) */
	//public void postUpdateSup(IntDomainVar v, int idx);

	/** posting a new var (instantiation of an search variable)
	 * to the engine (will stored in the appropriate queue) */
	//public void postInstInt(IntDomainVar v, int idx);

	/** posting a new var (removal of a value from the domain of an search variable)
	 * to the engine (will stored in the appropriate queue) */
	//public void postRemoveVal(IntDomainVar v, int x, int idx);

	/**
	 * Recording that there was no known cause for the last contradiction.
	 */

	public void setNoContradictionCause();
}
