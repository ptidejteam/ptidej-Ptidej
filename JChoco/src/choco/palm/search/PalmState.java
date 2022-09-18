//     ___  ___         PaLM library for Choco system
//    /__ \/ __\        (c) 2001 - 2004 -- Narendra Jussien
//   ____\ |/_____
//  /_____\/_____ \     Explanation based constraint programming tool
// |/   (_)(_)   \|
//       \ /            Version 0.1
//       \ /            January 2004
//       \ /
// ______\_/_______     Contibutors: François Laburthe, Hadrien Cambazard, Guillaume Rochart...

package choco.palm.search;

import choco.Constraint;
import choco.ContradictionException;
import choco.palm.explain.Explanation;
import choco.palm.prop.PalmEngine;

/**
 * Tool for maintaining the state of the search taht is the active posted decision constraint.
 */

public class PalmState extends PalmAbstractSolverTool {

	/**
	* The current state.
	*/

	protected choco.palm.explain.Explanation path;

	/**
	 * Initializes the PalmState with the specified explain.
	 * @param expl The initial state.
	 */

	public PalmState(final choco.palm.explain.Explanation expl) {
		this.path = expl;
	}

	/**
	 * Adds a new decision constraints in the state.
	 * @param constraint New decision constraint posted.
	 */

	public void addDecision(final Constraint constraint) {
		this.path.add(constraint);
	}

	/**
	 * Discards the current solutions in order to find the next one : it raises a fake contradiction and tries
	 * repairing the state.
	 */

	public boolean discardCurrentSolution() {
		try {
			this.manager.reset();
			try {
				((PalmEngine) this.manager.getProblem().getPropagationEngine())
					.raisePalmFakeContradiction((Explanation) this.path.copy());
			}
			catch (final PalmContradiction e) {
				this.manager.repair();
			}
			return true;
		}
		catch (final ContradictionException e) {
			return false;
		}
	}

	public Explanation getPath() {
		return this.path;
	}

	/**
	 * Removes a decision constraint.
	 * @param constraint The involved constraint.
	 */

	public void removeDecision(final Constraint constraint) {
		this.manager.getLearning().learnFromRemoval(constraint);
		this.path.delete(constraint);
	}

	/**
	 * Reverses a decision constraint. (The difference with removing is that it does not call the learning
	 * tool).
	 * @param constraint The constraint to reverse.
	 */

	public void reverseDecision(final Constraint constraint) {
		this.path.delete(constraint);
	}
}
