package choco.integer.var;

import choco.AbstractVar;
import choco.Constraint;
import choco.ContradictionException;
import choco.integer.IntConstraint;
import choco.prop.VarEvent;
import choco.util.BitSet;
import choco.util.IntIterator;

// *********************************************
// *                   J-CHOCO                 *
// *   Copyright (c) F. Laburthe, 1999-2003    *
// *********************************************
// * Event-base contraint programming Engine   *
// *********************************************

// CVS Information
// File:               $RCSfile: IntVarEvent.java,v $
// Version:            $Revision: 1.1 $
// Last Modification:  $Date: 2004/05/04 22:23:16 $
// Last Contributor:   $Author: guehene $

public class IntVarEvent extends VarEvent {

	/**
	 * Constants for the <i>eventType</i> bitvector: index of bit for updates to lower bound of IntVars
	 */
	public static final int INCINF = 0;

	/**
	 * Constants for the <i>eventType</i> bitvector: index of bit for updates to upper bound of IntVars
	 */
	public static final int DECSUP = 1;

	/**
	 * Constants for the <i>eventType</i> bitvector: index of bit for holes in the domain of IntVars
	 */
	public static final int REMVAL = 2;

	/**
	 * Constants for the <i>eventType</i> bitvector: index of bit for instantiations of IntVars
	 */
	public static final int INSTINT = 3;

	/**
	 * Constants for the <i>eventType</i> bitvector: value of bitvector for updates to lower bound of IntVars
	 */
	public static final int INCINFbitvector = 1;

	/**
	 * Constants for the <i>eventType</i> bitvector: value of bitvector for updates to upper bound of IntVars
	 */
	public static final int DECSUPbitvector = 2;

	/**
	 * Constants for the <i>eventType</i> bitvector: value of bitvector for updates to both bound of IntVars
	 */
	public static final int BOUNDSbitvector = 3;

	/**
	 * Constants for the <i>eventType</i> bitvector: value of bitvector for holes in the domain of IntVars
	 */
	public static final int REMVALbitvector = 4;

	/**
	 * Constants for the <i>eventType</i> bitvector: value of bitvector for instantiations of IntVars
	 */
	public static final int INSTINTbitvector = 8;

	public IntVarEvent(final AbstractVar var) {
		super(var);
		this.eventType = VarEvent.EMPTYEVENT;
	}

	/**
	 * Clears the var: delegates to the basic events.
	 */
	public void clear() {
		this.eventType = VarEvent.EMPTYEVENT;
		this.getIntVar().getDomain().clearDeltaDomain();
	}

	protected void freeze() {
		this.getIntVar().getDomain().freezeDeltaDomain();
		this.cause = VarEvent.NOEVENT;
		this.eventType = 0;
	}

	/**
	 * Returns an iterator over the set of removed values
	 * @return an iterator over the set of values that have been removed from the domain
	 */
	public IntIterator getEventIterator() {
		return this.getIntVar().getDomain().getDeltaIterator();
	}

	/**
	 * Retrieves the event type
	 */
	public int getEventType() {
		return this.eventType;
		//return BitSet.getHeavierBit(eventType);
	}

	public IntDomainVar getIntVar() {
		return (IntDomainVar) this.modifiedVar;
	}

	public boolean getReleased() {
		return this.getIntVar().getDomain().getReleasedDeltaDomain();
	}

	/**
	 * Propagates the update to the domain lower and upper bounds
	 */
	public void propagateBoundsEvent(final int evtCause)
			throws ContradictionException {
		final AbstractVar v = this.getModifiedVar();
		final Constraint[] constraints = v.getConstraints();
		final Integer[] indices = v.getIndices();

		for (final IntIterator cit = this.getAllListenersButIterator(evtCause); cit
			.hasNext();) {
			final int index = cit.next();
			((IntConstraint) constraints[index]).awakeOnBounds(indices[index]
				.intValue());
		}
	}

	/**
	 * Propagates the event through calls to the propagation engine.
	 * @return true if the event has been fully propagated (and can thus be discarded), false otherwise
	 * @throws ContradictionException
	 */
	public boolean propagateEvent() throws ContradictionException {
		VarEvent.logger.finer("propagate " + this.toString());
		// first, mark event
		final int evtType = this.eventType;
		final int evtCause = this.cause;
		this.freeze();

		if (evtType >= IntVarEvent.INSTINTbitvector) {
			this.propagateInstEvent(evtCause);
		}
		else if (evtType <= IntVarEvent.BOUNDSbitvector) { // only two first bits (bounds) are on
			if (evtType == IntVarEvent.INCINFbitvector) {
				this.propagateInfEvent(evtCause);
			}
			else if (evtType == IntVarEvent.DECSUPbitvector) {
				this.propagateSupEvent(evtCause);
			}
			else if (evtType == IntVarEvent.BOUNDSbitvector) {
				this.propagateBoundsEvent(evtCause);
			}
		}
		else if (evtType >= IntVarEvent.REMVALbitvector) { // the REMVAL bit is on + maybe INCINF / DECSUP bits
			this.propagateRemovalsEvent(evtCause);
			// Note: once all removals have been propagated, the bound events are themselves propagated
			// This supposes that a constraint reacts only once to updates
			// (the reaction corresponding to a removal that happens to be a bound should be performed once,
			//  either in awakeOnInf/Sup/Bound or in awakeOnRem)
			final int deltaEvtType = evtType - IntVarEvent.REMVALbitvector;
			if (deltaEvtType > 0) {
				if (deltaEvtType == IntVarEvent.INCINFbitvector) {
					this.propagateInfEvent(evtCause);
				}
				else if (deltaEvtType == IntVarEvent.DECSUPbitvector) {
					this.propagateSupEvent(evtCause);
				}
				else if (deltaEvtType == IntVarEvent.BOUNDSbitvector) {
					this.propagateBoundsEvent(evtCause);
				}
			}
		}
		// last, release event
		return this.release();
	}

	/**
	 * Propagates the update to the lower bound
	 */
	public void propagateInfEvent(final int evtCause)
			throws ContradictionException {
		final AbstractVar v = this.getModifiedVar();
		final Constraint[] constraints = v.getConstraints();
		final Integer[] indices = v.getIndices();

		for (final IntIterator cit = this.getAllListenersButIterator(evtCause); cit
			.hasNext();) {
			final int index = cit.next();
			((IntConstraint) constraints[index]).awakeOnInf(indices[index]
				.intValue());
		}
	}

	/**
	 * Propagates the instantiation event
	 */
	public void propagateInstEvent(final int evtCause)
			throws ContradictionException {
		final AbstractVar v = this.getModifiedVar();
		final Constraint[] constraints = v.getConstraints();
		final Integer[] indices = v.getIndices();

		for (final IntIterator cit = this.getAllListenersButIterator(evtCause); cit
			.hasNext();) {
			final int index = cit.next();
			((IntConstraint) constraints[index]).awakeOnInst(indices[index]
				.intValue());
		}
	}

	/**
	 * Propagates a set of value removals
	 */
	public void propagateRemovalsEvent(final int evtCause)
			throws ContradictionException {
		final AbstractVar v = this.getModifiedVar();
		final Constraint[] constraints = v.getConstraints();
		final Integer[] indices = v.getIndices();

		for (final IntIterator cit = this.getAllListenersButIterator(evtCause); cit
			.hasNext();) {
			final int index = cit.next();
			//System.out.println("Constraint : " + (IntConstraint) constraints[index]);
			((IntConstraint) constraints[index]).awakeOnRemovals(
				indices[index].intValue(),
				this.getEventIterator());
		}
	}

	/**
	 * Propagates the update to the upper bound
	 */
	public void propagateSupEvent(final int evtCause)
			throws ContradictionException {
		final AbstractVar v = this.getModifiedVar();
		final Constraint[] constraints = v.getConstraints();
		final Integer[] indices = v.getIndices();

		for (final IntIterator cit = this.getAllListenersButIterator(evtCause); cit
			.hasNext();) {
			final int index = cit.next();
			((IntConstraint) constraints[index]).awakeOnSup(indices[index]
				.intValue());
		}
	}

	/**
	 * the event had been "frozen", (since the call to freeze), while it was handled by the propagation engine:
	 * This meant that the meaning of the event could not be changed: it represented
	 * a static set of value removals, during propagation.
	 * Now, the event becomes "open" again: new value removals can be hosted, the delta domain can
	 * accept that further values are removed.
	 * In case value removals happened while the event was frozen, the release method returns false
	 * (the event cannot be released, it must be handled once more). Otherwise (the standard behavior),
	 * the method returns true
	 */
	protected boolean release() {
		// we no longer use the shortcut (if eventType == EMPTYEVENT => nothing to do) because of event transformation
		// (in case a removal turned out to be an instantiation, we need to release the delta domain associated to the removal)
		// boolean anyUpdateSinceFreeze = ((eventType != EMPTYEVENT) || (cause != NOEVENT));  // note: these two tests should be equivalent
		// anyUpdateSinceFreeze = (anyUpdateSinceFreeze || !(getIntVar().getDomain().releaseDeltaDomain()));
		// return !anyUpdateSinceFreeze;
		return this.getIntVar().getDomain().releaseDeltaDomain();
	}

	/**
	 * records the event type
	 */
	public void setEventType(final int evtType) {
		this.eventType = BitSet.setBit(this.eventType, evtType);
		//  with bit codes
		// INCINF = 0;
		// DECSUP = 1;
		// REMVAL = 2;
		// REMMANYVALS = 3;
		// INSTINT = 4;
	}

	/**
	 * useful for debugging
	 */
	public String toString() {
		return "VarEvt("
				+ this.modifiedVar.toString()
				+ ")["
				+ this.eventType
				+ ":"
				+ (BitSet.getBit(this.eventType, IntVarEvent.INCINF) ? "I" : "")
				+ (BitSet.getBit(this.eventType, IntVarEvent.DECSUP) ? "S" : "")
				+ (BitSet.getBit(this.eventType, IntVarEvent.REMVAL) ? "r" : "")
				+ (BitSet.getBit(this.eventType, IntVarEvent.INSTINT) ? "X"
						: "") + "]";
	}

}