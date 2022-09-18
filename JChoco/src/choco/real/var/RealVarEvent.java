package choco.real.var;

import choco.AbstractVar;
import choco.Constraint;
import choco.ContradictionException;
import choco.prop.VarEvent;
import choco.real.RealVar;
import choco.real.constraint.RealListener;
import choco.util.BitSet;
import choco.util.IntIterator;

/**
 * An event for real interval variable modifications.
 */
public class RealVarEvent extends VarEvent {
	public static final int INCINF = 0;
	public static final int DECSUP = 1;

	public final static int EMPTYEVENT = 0;
	public final static int BOUNDSEVENT = 3;
	public final static int INFEVENT = 1;
	public final static int SUPEVENT = 2;

	//protected int eventType = EMPTYEVENT;  // TODO : mettre dans VarEvent !!

	public RealVarEvent(final AbstractVar var) {
		super(var);
	}

	public void clear() {
		this.eventType = RealVarEvent.EMPTYEVENT;
		((RealVar) this.modifiedVar).getDomain().clearDeltaDomain();
	}

	protected void freeze() {
		((RealVar) this.modifiedVar).getDomain().freezeDeltaDomain();
		this.cause = VarEvent.NOEVENT;
		this.eventType = 0;
	}

	/*  public boolean isEnqueued() {  // TODO : mettre dans VarEvent !!
	    return (eventType != EMPTYEVENT);
	  }  */

	/*  public void recordEventTypeAndCause(int basicEvt, int idx) { // TODO : mettre dans VarEvent !!
	    // if no such event was active on the same variable
	    if ( (cause == NOEVENT) || (eventType == EMPTYEVENT)) {  // note: these two tests should be equivalent
	      // the varevent is reduced to basicEvt, and the cause is recorded
	      eventType = (1 << basicEvt);
	      cause = idx;
	    } else {
	      // otherwise, this basic event is added to all previous updates that are possibly mending on the same variable
	      eventType = (eventType | (1 << basicEvt));
	      // in case the cause of this update is different from the previous cause, all causes are forgotten
	      // (so that the constraints that caused the event will be reawaken)
	      if (cause != idx) {
	        cause = NOCAUSE;
	      }
	    }
	  }    */

	public boolean getReleased() {
		return ((RealVar) this.modifiedVar)
			.getDomain()
			.getReleasedDeltaDomain();
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
			((RealListener) constraints[index]).awakeOnInf(indices[index]
				.intValue());
			((RealListener) constraints[index]).awakeOnSup(indices[index]
				.intValue());
		}
	}

	public boolean propagateEvent() throws ContradictionException {
		VarEvent.logger.finer("propagate " + this.toString());
		// first, mark event
		final int evtType = this.eventType;
		final int evtCause = this.cause;
		this.freeze();

		if (evtType <= RealVarEvent.BOUNDSEVENT) { // only two first bits (bounds) are on
			if (evtType == RealVarEvent.INFEVENT) {
				this.propagateInfEvent(evtCause);
			}
			else if (evtType == RealVarEvent.SUPEVENT) {
				this.propagateSupEvent(evtCause);
			}
			else if (evtType == RealVarEvent.BOUNDSEVENT) {
				this.propagateBoundsEvent(evtCause);
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
			((RealListener) constraints[index]).awakeOnInf(indices[index]
				.intValue());
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
			((RealListener) constraints[index]).awakeOnSup(indices[index]
				.intValue());
		}
	}

	protected boolean release() {
		return ((RealVar) this.modifiedVar).getDomain().releaseDeltaDomain();
	}

	public String toString() {
		return "VarEvt("
				+ this.modifiedVar.toString()
				+ ")["
				+ this.eventType
				+ ":"
				+ (BitSet.getBit(this.eventType, RealVarEvent.INCINF) ? "I"
						: "")
				+ (BitSet.getBit(this.eventType, RealVarEvent.DECSUP) ? "S"
						: "") + "]";
	}
}
