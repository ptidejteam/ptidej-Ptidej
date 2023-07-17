// *********************************************
// *                   J-CHOCO                 *
// *   Copyright (c) F. Laburthe, 1999-2003    *
// *********************************************
// * Event-base contraint programming Engine   *
// *********************************************

// CVS Information
// File:               $RCSfile: VarEventQueue.java,v $
// Version:            $Revision: 1.1 $
// Last Modification:  $Date: 2004/05/04 22:23:15 $
// Last Contributor:   $Author: guehene $

package choco.prop;

import java.util.Iterator;
import java.util.logging.Logger;
import choco.ContradictionException;
import choco.util.PriorityQueue;

public class VarEventQueue implements EventQueue {

	/**
	 * A priority queue with all the var. Default number of priority levels: 5
	 * from 0 (most important) to 4.
	 */
	protected PriorityQueue queue = new PriorityQueue();

	/**
	 * The last popped var (may be useful for flushing popping events).
	 */
	protected PropagationEvent lastPopped = null;

	/**
	 * Reference to object for logging trace statements related to propagation events (using the java.util.logging package)
	 */

	private static Logger logger = Logger.getLogger("choco.prop");

	/**
	 * Removes all the events (including the popping one).
	 */

	public void flushEventQueue() {
		if (null != this.lastPopped) {
			this.lastPopped.clear();
		}

		for (final Iterator it = this.queue.iterator(); it.hasNext();) {
			final PropagationEvent event = (PropagationEvent) it.next();
			event.clear();
		}

		this.queue.clear();
	}

	public PropagationEvent get(int idx) {
		for (final Iterator it = this.queue.iterator(); it.hasNext();) {
			final PropagationEvent event = (PropagationEvent) it.next();
			if (idx == 0) {
				return event;
			}
			else {
				idx--;
			}
		}
		return null;
	}

	/**
	 * Checks if the queue is empty.
	 */
	public boolean isEmpty() {
		return this.queue.isEmpty();
	}

	/**
	 * Pops an event to propagate.
	 */
	public PropagationEvent popEvent() {
		final PropagationEvent event = (PropagationEvent) this.queue.popFirst();
		this.lastPopped = event;
		VarEventQueue.logger.finest("just popped " + event.toString());
		return event;
	}

	/**
	 * Propagates one single event from the queue (usefull for tracing)
	 * @throws choco.ContradictionException
	 */
	public void propagateOneEvent() throws ContradictionException {
		if (this.queue.size() != 0) {
			this.popEvent();
		}
	}

	/**
	 * Propagates some events: in fact all the events of the queue, since there
	 * are the most important events.
	 * @throws choco.ContradictionException
	 */
	public void propagateSomeEvents() throws ContradictionException {
		while (this.queue.size() != 0) {
			final PropagationEvent evt = this.popEvent();
			evt.propagateEvent();
			// in case the propagation of the event is not complete
			// the event will be pushed right back onto the queue
			/* if (!evt.propagateEvent()) {
			  pushEvent(evt);
			} */
		}
	}

	/**
	 * Adds an event to the queue.
	 */

	public boolean pushEvent(final PropagationEvent event) {
		this.queue.add(event);
		return true;
	}

	/**
	 * Removes an event. This method should not be useful for variable events.
	 */

	public void remove(final PropagationEvent event) {
		this.queue.remove(event);
	}

	public int size() {
		return this.queue.size();
	}

	/**
	 * Updates the priority level of an event (after adding a basic var).
	 */

	public void updatePriority(final PropagationEvent event) {
		this.queue.updatePriority(event);
	}

}
