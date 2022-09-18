// *********************************************
// *                   J-CHOCO                 *
// *   Copyright (c) F. Laburthe, 1999-2003    *
// *********************************************
// * Event-base contraint programming Engine   *
// *********************************************

// CVS Information
// File:               $RCSfile: ConstraintEventQueue.java,v $
// Version:            $Revision: 1.1 $
// Last Modification:  $Date: 2004/05/04 22:23:15 $
// Last Contributor:   $Author: guehene $

package choco.prop;

import java.util.Iterator;
import java.util.logging.Logger;
import choco.ContradictionException;
import choco.util.BipartiteSet;

/**
 * Implements an {@link choco.prop.EventQueue} for managing the constraint awake events.
 */
public class ConstraintEventQueue implements EventQueue {

	/**
	 * The propagation engine using this queue.
	 */

	private final PropagationEngine engine;

	/**
	 * A private stucture to store all the constraint. The left part of the bipartite
	 * set contains the events to propagate.
	 */

	private final BipartiteSet partition;

	/**
	 * Reference to the root Logger, an object for logging trace statements related to propagation events (using the java.util.logging package)
	 */

	private static Logger logger = Logger.getLogger("choco.prop");

	/**
	 * Constructs a new queue for the specified engine.
	 */

	public ConstraintEventQueue(final PropagationEngine engine) {
		this.engine = engine;
		this.partition = new BipartiteSet();
	}

	/**
	 * Adds a new constraint in the right part of the set (will not be propagated).
	 * It should be done just after creating the constraint.
	 */

	public void add(final PropagationEvent event) {
		if (this.partition.isIn(event)) {
			ConstraintEventQueue.logger
				.severe("Event added is already attached to engine !");
		}
		else {
			this.partition.addRight(event);
		}
	}

	/**
	 * Removes all the events from the queue.
	 */

	public void flushEventQueue() {
		this.partition.moveAllRight();
	}

	public PropagationEvent get(int idx) {
		for (final Iterator it = this.partition.leftIterator(); it.hasNext();) {
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
		return this.partition.getNbLeft() == 0;
	}

	/**
	 * Pops the next var to propagate.
	 */

	public PropagationEvent popEvent() {
		final PropagationEvent event =
			(PropagationEvent) this.partition.moveLastLeft();
		if (event == null) {
			ConstraintEventQueue.logger
				.severe("Error: There is no more events in the queue.");
		}
		else {
			if (!((ConstraintEvent) event).isInitialized()) {
				((ChocEngine) this.engine).decPendingInitConstAwakeEvent();
			}
		}
		return event;
	}

	/**
	 * Propagates one var in the queue.
	 * @throws choco.ContradictionException
	 */

	public void propagateOneEvent() throws ContradictionException {
		this.popEvent().propagateEvent();
	}

	/**
	 * Propagates one var in the queue.
	 * @throws choco.ContradictionException
	 */

	public void propagateSomeEvents() throws ContradictionException {
		this.popEvent().propagateEvent();
	}

	/**
	 * Adds a new var in the queue.
	 * @return True if the var had to be added.
	 */

	public boolean pushEvent(final PropagationEvent event) {
		if (!this.partition.isLeft(event)) {
			this.partition.moveLeft(event);
			return true;
		}
		return false;
	}

	/**
	 * Removes the var from the left part.
	 */

	public void remove(final PropagationEvent event) {
		if (this.partition.isLeft(event)) {
			if (!((ConstraintEvent) event).isInitialized()) {
				((ChocEngine) this.engine).decPendingInitConstAwakeEvent();
			}
			this.partition.moveRight(event);
		}
	}

	public int size() {
		return this.partition.getNbLeft();
	}
}
