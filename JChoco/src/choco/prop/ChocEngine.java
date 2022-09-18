// *********************************************
// *                   J-CHOCO                 *
// *   Copyright (c) F. Laburthe, 1999-2003    *
// *********************************************
// * Event-base contraint programming Engine   *
// *********************************************

// CVS Information
// File:               $RCSfile: ChocEngine.java,v $
// Version:            $Revision: 1.1 $
// Last Modification:  $Date: 2004/05/04 22:23:15 $
// Last Contributor:   $Author: guehene $

package choco.prop;

import java.util.logging.Logger;
import choco.Constraint;
import choco.ContradictionException;
import choco.Entity;
import choco.Problem;
import choco.Var;
import choco.integer.var.IntVarEvent;
import choco.real.RealVar;
import choco.real.var.RealVarEvent;

/**
 * Implementation of an {@link choco.prop.AbstractPropagationEngine} for Choco.
 */
public class ChocEngine extends AbstractPropagationEngine {

	/**
	  * Reference to object for logging trace statements related to propagation events (using the java.util.logging package)
	  */

	private static Logger logger = Logger.getLogger("choco.prop");

	/**
	 * the number of queues for storing constraint events
	 */
	protected static int NB_CONST_QUEUES = 4;

	/**
	 * The different queues for the constraint awake events.
	 */

	private final ConstraintEventQueue[] constEventQueues;

	/**
	 * Number of pending init constraint awake events.
	 */

	protected int nbPendingInitConstAwakeEvent;

	/**
	 * The queue with all the variable events.
	 */

	protected VarEventQueue varEventQueue;

	/**
	 * Constructs a new engine by initializing the var queues.
	 */

	public ChocEngine(final Problem pb) {
		super(pb);
		this.constEventQueues =
			new ConstraintEventQueue[ChocEngine.NB_CONST_QUEUES];
		for (int i = 0; i < ChocEngine.NB_CONST_QUEUES; i++) {
			this.constEventQueues[i] = new ConstraintEventQueue(this);
		}
		this.nbPendingInitConstAwakeEvent = 0;
		this.varEventQueue = new VarEventQueue();
	}

	/**
	 * Private method for completing the bound var posting.
	 * @param basicEvt The basic event posted.
	 * @param idx      The index of the constraint which is responsible of the var.
	 */
	// idee: - si on est "frozen", devenir en plus "redondant" (ie: double).
	//       - par ailleurs, noter le changement (garder la vieille valeur de la borne ou
	//       - devenir enqueued
	protected void _postEvent(final Var v, final int idx, final int basicEvt) {
		final VarEvent event = v.getEvent();
		ChocEngine.logger.finest("post Event " + event.toString()
				+ " for basicEvt:" + basicEvt);
		/*event.setEventType(basicEvt);
		event.setCause(idx);*/
		final boolean alreadyEnqueued = event.isEnqueued();
		event.recordEventTypeAndCause(basicEvt, idx);
		if (!alreadyEnqueued) {
			this.varEventQueue.pushEvent(event);
		}
		else {
			this.varEventQueue.updatePriority(event);
		}
		ChocEngine.logger.finest("posted Event " + event.toString());
	}

	public boolean checkCleanState() {
		boolean ok = true;
		final Problem pb = this.getProblem();
		final int nbiv = pb.getNbIntVars();
		for (int i = 0; i < nbiv; i++) {
			final IntVarEvent evt = (IntVarEvent) pb.getIntVar(i).getEvent();
			if (!evt.getReleased()) {
				System.out.println("var event non released " + evt.toString());
				ok = false;
			}
		}
		return ok;
	}

	/**
	 * Decrements the number of init constraint awake events.
	 */

	public void decPendingInitConstAwakeEvent() {
		this.nbPendingInitConstAwakeEvent--;
	}

	/**
	 * Removes all pending events (used when interrupting a propagation because
	 * a contradiction has been raised)
	 */
	public void flushEvents() {
		for (int i = 0; i < ChocEngine.NB_CONST_QUEUES; i++) {
			this.constEventQueues[i].flushEventQueue();
		}
		this.varEventQueue.flushEventQueue();
	}

	public int getNbPendingEvents() {
		int nbEvts = this.varEventQueue.size();
		for (int i = 0; i < ChocEngine.NB_CONST_QUEUES; i++) {
			nbEvts += this.constEventQueues[i].size();
		}
		return nbEvts;
	}

	/**
	 * Returns the next constraint var queue from which an event should be propagated.
	 */

	public EventQueue getNextActiveConstraintEventQueue() {
		for (int i = 0; i < ChocEngine.NB_CONST_QUEUES; i++) {
			if (!this.constEventQueues[i].isEmpty()) {
				return this.constEventQueues[i];
			}
		}
		return null;
	}

	/**
	 * Returns the next queue from which an event should be propagated.
	 */

	public EventQueue getNextActiveEventQueue() {
		if (this.nbPendingInitConstAwakeEvent > 0) {
			return this.getNextActiveConstraintEventQueue();
		}
		else if (!this.varEventQueue.isEmpty()) {
			return this.varEventQueue;
		}
		else {
			return this.getNextActiveConstraintEventQueue();
		}
	}

	/** getter without side effect:
	 *  returns the i-ht pending event (without popping any event from the queues)
	 */
	public PropagationEvent getPendingEvent(int idx) {
		if (this.nbPendingInitConstAwakeEvent > 0) {
			idx += this.varEventQueue.size();
		}
		if (idx < this.varEventQueue.size()) {
			return this.varEventQueue.get(idx);
		}
		else {
			EventQueue q = this.varEventQueue;
			int qidx = 0;
			do {
				idx = idx - q.size();
				q = this.constEventQueues[qidx];
				qidx++;
			}
			while (idx > q.size() && qidx < ChocEngine.NB_CONST_QUEUES);
			if (idx <= q.size()) {
				return q.get(idx); // return an event from one of the constraint event queues
			}
			else if (this.nbPendingInitConstAwakeEvent > 0
					&& idx < this.varEventQueue.size()) {
				return this.varEventQueue.get(idx); // return an event from the variable event queues
			}
			else {
				return null; // return no event, as the index is greater than the total number of pending events
			}
		}
	}

	/**
	 * Gets the queue for a given priority of var.
	 * @param event The var for which the queue is searched.
	 */

	public ConstraintEventQueue getQueue(final ConstraintEvent event) {
		final int prio = event.getPriority();
		if (prio < ChocEngine.NB_CONST_QUEUES) {
			return this.constEventQueues[prio];
		}
		else {
			ChocEngine.logger
				.warning("wrong constraint priority. It should be between 0 and 3.");
			return this.constEventQueues[3];
		}
	}

	/**
	 * Returns the variable var queue.
	 */

	public VarEventQueue getVarEventQueue() {
		return this.varEventQueue;
	}

	/**
	 * Increments the number of init constraint awake events.
	 */

	public void incPendingInitConstAwakeEvent() {
		this.nbPendingInitConstAwakeEvent++;
	}

	/**
	 * Posts a constraint awake var.
	 * @param constraint The constraint that must be awaken.
	 * @param init       Specifies if the constraint must be initialized
	 *                   (awake instead of propagate).
	 */

	public boolean postConstAwake(
		final Constraint constraint,
		final boolean init) {
		final ConstraintEvent event = (ConstraintEvent) constraint.getEvent();
		final ConstraintEventQueue queue = this.getQueue(event);
		if (queue.pushEvent(event)) {
			event.setInitialized(!init);
			if (init) {
				this.incPendingInitConstAwakeEvent();
			}
			return true;
		}
		else {
			return false;
		}
	}

	/**
	 * Posts an Inst var.
	 * @param v    The variable that is instantiated.
	 * @param idx  The index of the constraint which is responsible of the var.
	 */

	public void postInstInt(
		final choco.integer.var.IntDomainVar v,
		final int idx) {
		this._postEvent(v, idx, IntVarEvent.INSTINT);
	}

	/**
	 * Posts an Remove var.
	 * @param v    The variable the value is removed from.
	 * @param idx  The index of the constraint which is responsible of the var.
	 */

	public void postRemoveVal(
		final choco.integer.var.IntDomainVar v,
		final int x,
		final int idx) {
		this._postEvent(v, idx, IntVarEvent.REMVAL);
	}

	/**
	 * Posts an IncInf event
	 * @param v    The variable the bound is modified.
	 * @param idx  The index of the constraint which is responsible of the var.
	 */

	public void postUpdateInf(
		final choco.integer.var.IntDomainVar v,
		final int idx) {
		this._postEvent(v, idx, IntVarEvent.INCINF);
	}

	/**
	 * Posts an lower bound event for a real variable.
	 * @param v
	 * @param idx
	 */
	public void postUpdateInf(final RealVar v, final int idx) {
		this._postEvent(v, idx, RealVarEvent.INCINF);
	}

	/**
	 * Posts a DecSup event
	 * @param v    The variable the bound is modified.
	 * @param idx  The index of the constraint which is responsible of the var.
	 */

	public void postUpdateSup(
		final choco.integer.var.IntDomainVar v,
		final int idx) {
		this._postEvent(v, idx, IntVarEvent.DECSUP);
	}

	/**
	 * Posts an upper bound event for a real variable
	 * @param v
	 * @param idx
	 */
	public void postUpdateSup(final RealVar v, final int idx) {
		this._postEvent(v, idx, RealVarEvent.DECSUP);
	}

	/**
	 * Throws a contradiction without cause.
	 * @throws choco.ContradictionException
	 */

	public void raiseContradiction() throws ContradictionException {
		throw new ContradictionException(this.getProblem());
	}

	/**
	 * Throws a contradiction with the specified cause.
	 * @throws choco.ContradictionException
	 */

	public void raiseContradiction(final Entity cause)
			throws ContradictionException {
		throw new ContradictionException(cause);
	}

	/**
	 * Registers an event in the queue. It should be called before using the queue to add
	 * the var in the available events of the queue.
	 * @param event
	 */

	public void registerEvent(final ConstraintEvent event) {
		final ConstraintEventQueue queue = this.getQueue(event);
		queue.add(event);
	}

}
