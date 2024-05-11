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

import choco.Constraint;
import choco.ContradictionException;
import choco.palm.PalmProblem;
import choco.palm.explain.Explanation;
import choco.palm.explain.PalmConstraintPlugin;
import choco.palm.integer.PalmIntVar;
import choco.palm.search.PalmContradiction;
import choco.prop.ChocEngine;
import choco.prop.VarEvent;

public class PalmEngine extends ChocEngine {
	/**
	 * Indicates is a contradiction has be found.
	 */

	private boolean contradictory;

	/**
	 * A dummy variable. Useful for raising PalmFakeContradictions.
	 */

	private PalmIntVar dummyVariable;

	/**
	 * Constructs an engine with the specified problem.
	 * @param pb The problem to associate with this engine.
	 */

	public PalmEngine(final choco.Problem pb) {
		super(pb);
		this.varEventQueue = null; // force le GC pour la queue cr??e
		this.varEventQueue = new PalmVarEventQueue();
		this.problem = pb;
	}

	/**
	 * Checks if a contradiction has been reached.
	 * @return True if a contradiciton happened.
	 */

	public boolean isContradictory() {
		return this.contradictory;
	}

	/**
	 * Posts an inf bound restoration prop.
	 * @param v The variable on which the inf bound is restored.
	 */

	public void postRestoreInf(final PalmIntVar v) {
		this._postEvent(v, VarEvent.NOCAUSE, PalmIntVarEvent.RESTINF);
	}

	/**
	 * Posts a sup bound restoration prop.
	 * @param v The variable on which the sup bound is restored.
	 */

	public void postRestoreSup(final PalmIntVar v) {
		this._postEvent(v, VarEvent.NOCAUSE, PalmIntVarEvent.RESTSUP);
	}

	/**
	 * Posts value restoration.
	 * @param v The variable that should be modified.
	 * @param value The value restored.
	 */

	public void postRestoreVal(final PalmIntVar v, final int value) {
		this._postEvent(v, VarEvent.NOCAUSE, PalmIntVarEvent.RESTVAL);
	}

	/**
	 * Posts value removal. Needs to be overriden by Palm ?
	 * @param v The modified variable.
	 * @param x The removed value.
	 * @param idx The index of the responsible constraint.
	 */

	/*public void postRemoveVal(PalmIntVar v, int x, int idx) {
	    _postEvent(v,idx,PalmIntVarEvent.REMVAL);
	    // TODO : ask naren
	}   */

	/**
	 * Deletes all the events. <b>Sould be in Choco, not here !!</b>
	 */

	/*    public void flushEvents() {
	        // c'est dans Choco !
	        this.varEventQueue.flushEventQueue();
	    }  */

	/**
	 * Raises a Palm Contradiction caused by the specified variable.
	 * @param var The variable which is responsible of the contradiction.
	 */

	public void raisePalmContradiction(final PalmIntVar var) {
		this.contradictionCause = var;
		this.contradictory = true;
		this.resetEvents();
		throw new PalmContradiction();
	}

	/**
	 * Raises a fake Contradiction with the specified explain. Useful when the contradiction is not
	 * due to only one domain.
	 * @param expl The explain of the contradiction.
	 */

	public void raisePalmFakeContradiction(final Explanation expl) {
		if (this.dummyVariable == null) {
			this.dummyVariable =
				(PalmIntVar) ((PalmProblem) this.problem).makeBoundIntVar(
					"*dummy*",
					0,
					1,
					false);
		}
		this.dummyVariable.updateInf(2, VarEvent.NOCAUSE, expl);
	}

	/**
	 * Raise a System Contradiction, that is a Choco Contradiction, that means that no solution can be found
	 * anymore without removing constraint with a level upper that <code>PalmProblem.MAX_RELAX_LEVEL</code>.
	 * @throws ContradictionException
	 */

	public void raiseSystemContradiction() throws ContradictionException {
		this.contradictory = false;
		this.flushEvents();
		throw new ContradictionException(this.problem);
	}

	/**
	 * Removes properly a constraint: the constraint is deactivated, and all depending filtering decisions are
	 * undone.
	 * @param constraint The constraint to be removed.
	 */

	public void remove(final Constraint constraint) {
		constraint.setPassive();
		((PalmConstraintPlugin) constraint.getPlugIn()).undo();
		this.restoreVariableExplanations();
	}

	/**
	 * Removes several constraints.
	 * @param constraints An array with all the constraints to remove.
	 */

	public void remove(final Constraint[] constraints) {
		for (int i = 0; i < constraints.length; i++) {
			final Constraint constraint = constraints[i];
			constraint.setPassive();
			((PalmConstraintPlugin) constraint.getPlugIn()).undo();
		}
		this.restoreVariableExplanations();
	}

	/**
	 * Resets the dummy variable.
	 */

	public void resetDummyVariable() {
		this.dummyVariable = null;
	}

	/**
	 * Resets all the events in the queue (no cause, and in the queue (no popping events anymore)).
	 */

	public void resetEvents() {
		((PalmVarEventQueue) this.varEventQueue).reset();
	}

	/**
	 * Updates explanations when constraints are removed.
	 */

	private void restoreVariableExplanations() {
		((PalmVarEventQueue) this.varEventQueue).restoreVariableExplanations();
	}

	/**
	 * Sets if a contradiciton has been reached.
	 * @param c The value to set.
	 */

	public void setContradictory(final boolean c) {
		this.contradictory = c;
	}
}
