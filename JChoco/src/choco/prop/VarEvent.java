// *********************************************
// *                   J-CHOCO                 *
// *   Copyright (c) F. Laburthe, 1999-2003    *
// *********************************************
// * Event-base contraint programming Engine   *
// *********************************************

// CVS Information
// File:               $RCSfile: VarEvent.java,v $
// Version:            $Revision: 1.1 $
// Last Modification:  $Date: 2004/05/04 22:23:15 $
// Last Contributor:   $Author: guehene $

package choco.prop;

import java.util.logging.Logger;
import choco.AbstractVar;
import choco.ContradictionException;
import choco.Problem;
import choco.mem.StoredIntVector;
import choco.util.IPrioritizable;
import choco.util.IntIterator;
import choco.util.StoredPointerCycle;

/**
 * Implements an
 * {@link choco.prop.PropagationEvent} for the variable events.
 */
public abstract class VarEvent implements PropagationEvent, IPrioritizable {

	/**
	 * empty bitvector for the event type.
	 */
	public final static int NOEVENT = -2;

	/**
	 * Semantic of the cause of an event: -1 means that the event is active without
	 * any precise cause. (Equivalent of 0 in Claire version)
	 */
	public final static int NOCAUSE = -1;

	/**
	 * Cause of this basic var.
	 */
	protected int cause = VarEvent.NOEVENT;

	/**
	 * empty bitvector for the event type.
	 */
	public final static int EMPTYEVENT = 0;

	/**
	 * The touched variable.
	 */

	protected AbstractVar modifiedVar;

	/**
	 * A linked list with all the constraint to propagate.
	 */

	protected StoredIntVector nextConst;

	/**
	 * Reference to the root Logger, an object for logging trace statements related to propagation events (using the java.util.logging package)
	 */

	protected static Logger logger = Logger.getLogger("choco.prop");

	/**
	 * A pointer cycle encoding the set of constraints connected to the event
	 */
	protected StoredPointerCycle activeCycle;

	/**
	 * stores the type of update performed on the variable
	 */
	protected int eventType = VarEvent.EMPTYEVENT;

	/**
	 * Constructs a variable event for the specified variable and with the given
	 * basic events.
	 */

	public VarEvent(final AbstractVar var) {
		this.modifiedVar = var;
		this.activeCycle =
			new StoredPointerCycle(this.getProblem().getEnvironment());
	}

	/**
	 * Activates the listeners.
	 * @param idx the index of the listener among all listeners connected to the variable
	 */
	public void activateListener(final int idx) { // ex disconnectEvent
		this.activeCycle.setInCycle(idx);
	}

	/**
	 * Adds the constraint <code>idx</code> as a listener.
	 * @param idx the index of the listener among all listeners connected to the variable
	 * @param active  Specifies if the listener should be active.
	 */
	public void addListener(final int idx, final boolean active) { // ex connectEvent
		this.activeCycle.add(idx, active);
	}

	/**
	 * Clears the var: delegates to the basic events.
	 */
	public abstract void clear();

	/**
	 * Desactivates the listeners.
	 * @param idx the index of the listener among all listeners connected to the variable
	 */

	public void deactivateListener(final int idx) { // ex reconnectEvent
		this.activeCycle.setOutOfCycle(idx);
	}

	/**
	 * freezes the state of the "delta domain": the set of values that are considered for removal
	 * from the domain. Further removals will be treated as a further event.
	 */
	protected void freeze() {
		this.cause = VarEvent.NOEVENT;
	}

	public IntIterator getAllListenersButIterator(final int avoidIndex) {
		return this.activeCycle.getCycleButIterator(avoidIndex);
	}

	/**
	 * Returns the cause of this basic var.
	 */
	public int getCause() {
		return this.cause;
	}

	/**
	 * Returns the touched variable.
	 */

	public Object getModifiedObject() {
		return this.modifiedVar;
	}

	/**
	 * Returns the touched variable.
	 */

	public AbstractVar getModifiedVar() {
		return this.modifiedVar;
	}

	public int getNbListeners() {
		return this.activeCycle.size();
	}

	public int getPriority() {
		return 1;
	}

	/**
	 * Retrieving the problem.
	 */

	public Problem getProblem() {
		return this.modifiedVar.getProblem();
	}

	/**
	 * Checks if a given listener is active or not
	 * @param idx the index of the listener among all listeners connected to the variable
	 */

	public boolean isActive(final int idx) {
		return this.activeCycle.isInCycle(idx);
	}

	/**
	 * tests whether the event is currently active (present in some queue) or not
	 * @return true if and only if the event is present in some queue, waiting to be handled
	 * (returns false if the event is either absent from the queue or is the current event,
	 * just popped from the queue and being propagated)
	 */
	public boolean isEnqueued() {
		return this.eventType != VarEvent.EMPTYEVENT;
	}

	/**
	 * Propagates the event through calls to the propagation engine.
	 * @return true if the event has been fully propagated (and can thus be discarded), false otherwise
	 * @throws ContradictionException
	 */
	public abstract boolean propagateEvent() throws ContradictionException;

	public void recordEventTypeAndCause(final int basicEvt, final int idx) {
		// if no such event was active on the same variable
		if (this.cause == VarEvent.NOEVENT
				|| this.eventType == VarEvent.EMPTYEVENT) { // note: these two tests should be equivalent
			// the varevent is reduced to basicEvt, and the cause is recorded
			this.eventType = 1 << basicEvt;
			this.cause = idx;
		}
		else {
			// otherwise, this basic event is added to all previous updates that are possibly mending on the same variable
			this.eventType = this.eventType | 1 << basicEvt;
			// in case the cause of this update is different from the previous cause, all causes are forgotten
			// (so that the constraints that caused the event will be reawaken)
			if (this.cause != idx) {
				this.cause = VarEvent.NOCAUSE;
			}
		}
	}

	protected boolean release() {
		final boolean anyUpdateSinceFreeze = this.cause != VarEvent.NOEVENT;
		return anyUpdateSinceFreeze;
	}

}
