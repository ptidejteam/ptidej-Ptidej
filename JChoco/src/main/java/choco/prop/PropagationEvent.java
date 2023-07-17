// *********************************************
// *                   J-CHOCO                 *
// *   Copyright (c) F. Laburthe, 1999-2003    *
// *********************************************
// * Event-base contraint programming Engine   *
// *********************************************

// CVS Information
// File:               $RCSfile: PropagationEvent.java,v $
// Version:            $Revision: 1.1 $
// Last Modification:  $Date: 2004/05/04 22:23:15 $
// Last Contributor:   $Author: guehene $

package choco.prop;

import choco.ContradictionException;

/**
 * An interface for all implementations of events.
 */
public interface PropagationEvent {
	/**
	 * Value of the state in the queue: -1 means the var is being propagated.
	 * (see VarEvent.qState)
	 */
	public final static int POPPING = -1;

	/**
	 * Clears the var if it not useful anymore.
	 */

	public void clear();

	/**
	 * Returns the object, whose modification is described by the event
	 */

	public Object getModifiedObject();

	/**
	 * Tests whether a propagation var is active in the propagation network.
	 */

	public boolean isActive(int idx);

	/**
	 * Propagates the var through calls to the propagation engine.
	 * @return true if the event has been fully propagated (and can thus be discarded), false otherwise
	 * @throws choco.ContradictionException
	 */
	public boolean propagateEvent() throws ContradictionException;
}