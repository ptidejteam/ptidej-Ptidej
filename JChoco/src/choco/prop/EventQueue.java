// *********************************************
// *                   J-CHOCO                 *
// *   Copyright (c) F. Laburthe, 1999-2003    *
// *********************************************
// * Event-base contraint programming Engine   *
// *********************************************

// CVS Information
// File:               $RCSfile: EventQueue.java,v $
// Version:            $Revision: 1.1 $
// Last Modification:  $Date: 2004/05/04 22:23:15 $
// Last Contributor:   $Author: guehene $

package choco.prop;

import choco.ContradictionException;

/**
 * An interface for all the implementations of var queues (Constraint awake, on
 * Variable events for instance).
 */
public interface EventQueue {

	/**
	 * Removes all the events and clears all the events if needed.
	 */

	public void flushEventQueue();

	/**
	 * returns the i-th pending event in the queue
	 * @param idx the index of the event
	 * @return null if the index is inproper (idx<0 or idx>=size())
	 */
	public PropagationEvent get(int idx);

	/**
	 * Checks if the queue is empty.
	 */

	public boolean isEmpty();

	/**
	 * Pops the next var to propagate.
	 */

	public PropagationEvent popEvent();

	/**
	 * Propagate one single event from the queue).
	 * @throws choco.ContradictionException
	 */

	public void propagateOneEvent() throws ContradictionException;

	/**
	 * Propagate some events (one or several depending on the queue).
	 * @throws choco.ContradictionException
	 */

	public void propagateSomeEvents() throws ContradictionException;

	/**
	 * Adds an event to the queue.
	 * @param event
	 */

	public boolean pushEvent(PropagationEvent event);

	/**
	 * Removes an event.
	 */

	public void remove(PropagationEvent event);

	/**
	 * returns the number of pending events in the queue
	 * @return the number of pending events in the queue
	 */
	public int size();
}
