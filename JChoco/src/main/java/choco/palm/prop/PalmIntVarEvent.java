//     ___  ___         PaLM library for Choco system
//    /__ \/ __\        (c) 2001 - 2004 -- Narendra Jussien
//   ____\ |/_____
//  /_____\/_____ \     Explanation based constraint programming tool
// |/   (_)(_)   \|
//       \ /            Version 0.1
//       \ /            January 2004
//       \ /
// ______\_/_______     Contibutors: Franeois Laburthe, Hadrien Cambazard, Guillaume Rochart...

package choco.palm.prop;

import org.junit.Assert;
import choco.AbstractVar;
import choco.Constraint;
import choco.ContradictionException;
import choco.Var;
import choco.integer.IntVar;
import choco.integer.var.IntVarEvent;
import choco.palm.integer.PalmBitSetIntDomain;
import choco.palm.integer.PalmIntVar;
import choco.palm.integer.PalmIntVarListener;
import choco.prop.VarEvent;
import choco.util.BitSet;
import choco.util.IntIterator;

public class PalmIntVarEvent extends IntVarEvent {

	public int oldEventType;

	/**
	 * This boolean was before in the queue
	 */
	public boolean isPopping = false;

	/**
	 * Constant value associated to the inf bound restoration prop.
	 */

	public final static int RESTINF = 5;

	/**
	 * Constant value associated to the sup bound restoration prop.
	 */

	public final static int RESTSUP = 6;

	/**
	 * Constant value associated to the value restoration prop.
	 */

	public final static int RESTVAL = 7;

	/**
	 * Creates an prop for the specified variable.
	 * @param var The variable this prop is reponsible for.
	 */

	public PalmIntVarEvent(final Var var) {
		super((AbstractVar) var);
	}

	/**
	 * Computes the priority of the prop. Actually, it returns 0 for restoration events (that is urgent
	 * events) and 1 for the others.
	 * @return The priority of this prop.
	 */

	public int getPriority() {
		if (this.getEventType() >= PalmIntVarEvent.RESTINF) {
			return 0;
		}
		return 1;
	}

	/**
	 * Returns an iterator on the chain containing all the restored values.
	 */

	public IntIterator getRestoreIterator() {
		return ((PalmBitSetIntDomain) ((IntVar) this.modifiedVar).getDomain())
			.getRepairIterator();
	}

	/**
	 * Generic propagation method. Calls relevant methods depending on the kind of event. It handles some
	 * new events for restoration purpose and call the Choco method <code>super.propagateEvent()</code>.
	 * @throws ContradictionException
	 */

	public boolean propagateEvent() throws ContradictionException {
		// isPopping, oldEventType et oldCause permettent de remettre en etat l'evenement si une contradiction
		// a lieu pendant le traitement de cet evenement. Cf public void reset() et
		// PalmVarEventQueue.resetPopping().
		this.isPopping = true;
		this.oldEventType = this.eventType;
		Assert.assertTrue(this.oldEventType != VarEvent.EMPTYEVENT);
		final int oldCause = this.cause;

		// Traitement des restarations
		this.cause = VarEvent.NOEVENT;
		this.eventType = VarEvent.EMPTYEVENT;
		if (BitSet.getBit(this.oldEventType, PalmIntVarEvent.RESTINF)) {
			this.propagateRestInfEvent(oldCause);
		}
		if (BitSet.getBit(this.oldEventType, PalmIntVarEvent.RESTSUP)) {
			this.propagateRestSupEvent(oldCause);
		}
		if (BitSet.getBit(this.oldEventType, PalmIntVarEvent.RESTVAL)) {
			this.propagateRestValEvent(oldCause); //,getRestoreIterator());
		}

		// On sauvegarde l'etat apres le traitement des restaurations
		final int newEventType = this.eventType;
		final int newCause = this.cause;
		if (this.eventType != VarEvent.EMPTYEVENT) {
			this.modifiedVar
				.getProblem()
				.getChocEngine()
				.getVarEventQueue()
				.remove(this);
		}

		// On fait comme si Palm n'etait pas la pour Choco
		this.eventType = this.oldEventType & 31;
		this.cause = oldCause;

		// On laisse Choco propager
		final boolean ret = super.propagateEvent();

		// On retablit des valeurs consistantes avec les resultats obtenus avec Palm
		if (this.eventType == VarEvent.EMPTYEVENT
				&& newEventType != VarEvent.EMPTYEVENT) {
			this.modifiedVar
				.getProblem()
				.getChocEngine()
				.getVarEventQueue()
				.pushEvent(this);
		}
		this.eventType |= newEventType;
		if (this.cause == -2) {
			this.cause = newCause;
		}
		else if (newCause != -2) {
			this.cause = -1;
		}

		// On reinitialise l'evenement puisqu'il n'y a pas eu de contradiction !
		this.isPopping = false;
		if (((PalmIntVar) this.modifiedVar).hasEnumeratedDomain()) {
			((PalmBitSetIntDomain) ((PalmIntVar) this.modifiedVar).getDomain())
				.releaseRepairDomain();
		}

		// Rappel : ^ = xor logique
		Assert.assertTrue(this.eventType == VarEvent.EMPTYEVENT
				^ ((PalmVarEventQueue) this.modifiedVar
					.getProblem()
					.getChocEngine()
					.getVarEventQueue()).contains(this));

		return ret;
	}

	/**
	 * Propagates the lower bound restoration event.
	 */

	public void propagateRestInfEvent(final int evtCause)
			throws ContradictionException {
		final AbstractVar v = this.getModifiedVar();
		final Constraint[] constraints = v.getConstraints();
		final Integer[] indices = v.getIndices();

		for (final IntIterator cit = this.getAllListenersButIterator(evtCause); cit
			.hasNext();) {
			final int index = cit.next();
			((PalmIntVarListener) constraints[index])
				.awakeOnRestoreInf(indices[index].intValue());
		}
	}

	/**
	 * Propagates the upper bound restoration event.
	 */

	public void propagateRestSupEvent(final int evtCause)
			throws ContradictionException {
		final AbstractVar v = this.getModifiedVar();
		final Constraint[] constraints = v.getConstraints();
		final Integer[] indices = v.getIndices();

		for (final IntIterator cit = this.getAllListenersButIterator(evtCause); cit
			.hasNext();) {
			final int index = cit.next();
			((PalmIntVarListener) constraints[index])
				.awakeOnRestoreSup(indices[index].intValue());
		}
	}

	/**
	 * Propagates a value restoration event.
	 */
	public void propagateRestValEvent(final int evtCause)
			throws ContradictionException {
		final AbstractVar v = this.getModifiedVar();
		final Constraint[] constraints = v.getConstraints();
		final Integer[] indices = v.getIndices();

		for (final IntIterator cit = this.getAllListenersButIterator(evtCause); cit
			.hasNext();) {
			final int index = cit.next();
			//System.out.println("Restore Constraint : " + (IntConstraint) constraints[index]);
			((PalmIntVarListener) constraints[index]).awakeOnRestoreVal(
				indices[index].intValue(),
				this.getRestoreIterator());
		}
	}

	/**
	 * If a contradiction occurs when the event is handled, the event is reinitialized.
	 */

	public void reset() {
		// Il faut remettre le bon type d'evenement pour eviter de ne remettre cet evenement dans la queue alors
		// qu'il y ait deja. Cela peut entrainer des probleme de chaines cycliques pour la liste des valeurs
		// restaurees.
		this.eventType = this.oldEventType;
		this.cause = VarEvent.NOCAUSE;
		if (((IntVar) this.modifiedVar).hasEnumeratedDomain()) {
			((PalmBitSetIntDomain) ((IntVar) this.modifiedVar).getDomain())
				.resetRemovalChain();
		}

		Assert.assertTrue(this.eventType != VarEvent.EMPTYEVENT);
	}

	/**
	 * Updates explanations for the variable: when bounds are completely restored, the unrelevant explanations
	 * are removed.
	 */

	public void restoreVariableExplanation() {
		if (BitSet.getBit(this.eventType, PalmIntVarEvent.RESTINF)) {
			((PalmIntVar) this.getModifiedVar()).resetExplanationOnInf();
		}
		if (BitSet.getBit(this.eventType, PalmIntVarEvent.RESTSUP)) {
			((PalmIntVar) this.getModifiedVar()).resetExplanationOnSup();
		}
	}
}
