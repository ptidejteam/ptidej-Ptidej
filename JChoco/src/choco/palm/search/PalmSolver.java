//     ___  ___         PaLM library for Choco system
//    /__ \/ __\        (c) 2001 - 2004 -- Narendra Jussien
//   ____\ |/_____
//  /_____\/_____ \     Explanation based constraint programming tool
// |/   (_)(_)   \|
//       \ /            Version 0.1
//       \ /            January 2004
//       \ /
// ______\_/_______     Contibutors: Fran?ois Laburthe, Hadrien Cambazard, Guillaume Rochart...

package choco.palm.search;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Logger;
import choco.Constraint;
import choco.ContradictionException;
import choco.Problem;
import choco.palm.PalmProblem;
import choco.palm.explain.Explanation;
import choco.palm.explain.PalmConstraintPlugin;
import choco.palm.integer.PalmIntDomain;
import choco.palm.integer.PalmIntVar;
import choco.palm.prop.PalmEngine;
import choco.search.Solve;

/**
 * A default solver for Palm. By default, it uses <code>mac-dbt</code> algorithm (for extension and repairing).
 */

public class PalmSolver extends Solve {

	/**
	 * Runtime statistics.
	 */

	protected int[] statistics;

	/**
	 * States if the search is finished.
	 */

	protected boolean finished = false;

	/**
	 * States if the problem is feasible.
	 */

	protected boolean feasible = false;

	/**
	 * Maintains the state of the search (past branching decisions).
	 */

	protected PalmState state;

	/**
	 * Learning algorithms.
	 */

	protected PalmLearn learning;

	/**
	 * Extension algrithm using some branching strategies.
	 */

	protected PalmExtend extending;

	/**
	 * Repairing algorithm.
	 */

	protected PalmRepair repairing;

	/**
	 * Creates a solver for the specified problem. It initiliazes all contained structures (repairer, learner...).
	 * @param pb The problem to search.
	 */

	public PalmSolver(final PalmProblem pb) {
		super(pb);
		this.statistics = new int[] { 0, 0, 0 };
		this.problem = pb;

		//this.attachPalmState(new TraceState(new GenericExplanation(this.problem)));
		this.attachPalmState(new PalmState(((PalmProblem) this.problem)
			.makeExplanation()));
		this.attachPalmExtend(new PalmExtend());
		final ArrayList l = new ArrayList();
		l.add(new PalmAssignVar());
		this.attachPalmBranchings(l);
		this.attachPalmLearn(new PalmLearn());
		this.attachPalmRepair(new PalmRepair());
		//this.varsToStore = new LinkedList();
		this.solutions = new ArrayList();
	}

	/* /**
	 * Adds a var in the list of variables to store.
	 * @param var
	 *

	public void addVarToStore(Var var) {
	    this.varsToStore.add(var);
	} */

	/**
	 * Attaches a new list of branching. It creates a linked list from the specified list of branching.
	 */

	public void attachPalmBranchings(final List lbr) {
		PalmAbstractBranching previous = null;
		for (final Iterator iterator = lbr.iterator(); iterator.hasNext();) {
			final PalmAbstractBranching branching =
				(PalmAbstractBranching) iterator.next();
			if (previous != null) {
				previous.setNextBranching(branching);
			}
			branching.setExtender(this.extending);
			previous = branching;
		}
		if (lbr.size() > 0) {
			this.extending.setBranching((PalmAbstractBranching) lbr.get(0));
		}
	}

	/**
	 * Attaches a new extension tool.
	 */

	public void attachPalmExtend(final PalmExtend ext) {
		this.extending = ext;
		ext.setManager(this);
	}

	/**
	 * Attache a new learning tool.
	 * @param ext The learning extension the solver shoudl use.
	 */

	public void attachPalmLearn(final PalmLearn ext) {
		this.learning = ext;
		ext.setManager(this);
	}

	/**
	 * Attaches a new PalmRepair algorithm.
	 * @param ext The new repairing algorithm the solver must use.
	 */

	public void attachPalmRepair(final PalmRepair ext) {
		this.repairing = ext;
		ext.setManager(this);
	}

	/**
	 * Attaches a new PalmState to the solver.
	 * @param ext The PalmState that should be used for maintaining state of the search.
	 */

	public void attachPalmState(final PalmState ext) {
		this.state = ext;
		ext.setManager(this);
	}

	/**
	 * Decrements runtime statistics.
	 * @param statistic Satistic constant (<code>PalmProblem.RLX/CPU/EXT</code>).
	 * @param dec Value to remove from the current one.
	 */

	public void decRuntimeStatistic(final int statistic, final int dec) {
		this.statistics[statistic] -= dec;
	}

	/**
	 * Extends the state of the search thanks to the extending algorithm.
	 * @throws ContradictionException
	 */

	public void extend() throws ContradictionException {
		this.extending.explore(this.extending.getBranching());
	}

	/**
	 * Returns global statistic that is statistic for all solutions (sum of all runtime statistics).
	 * @param statistic Satistic constant (<code>PalmProblem.RLX/CPU/EXT</code>).
	 */

	public int getGlobalStatistics(final int statistic) {
		int total = 0;
		PalmSolution[] sols = new PalmSolution[this.solutions.size()];
		sols = (PalmSolution[]) this.solutions.toArray(sols);
		for (int i = 0; i < sols.length; i++) {
			final PalmSolution sol = sols[i];
			total += sol.getStatistic(statistic);
		}
		if (!this.getProblem().isFeasible().booleanValue()) {
			total += this.getRuntimeStatistics(statistic);
		}
		return total;
	}

	/**
	 * Gets the PalmLearn tool.
	 */

	public PalmLearn getLearning() {
		return this.learning;
	}

	public Problem getProblem() {
		return this.problem;
	}

	/**
	 * Returns runtime statistics.
	 * @param statistic Satistic constant (<code>PalmProblem.RLX/CPU/EXT</code>).
	 */

	public int getRuntimeStatistics(final int statistic) {
		return this.statistics[statistic];
	}

	/**
	 * Returns all the found solutions.
	 */

	public PalmSolution[] getSolutions() {
		PalmSolution[] solus = new PalmSolution[this.solutions.size()];
		solus = (PalmSolution[]) this.solutions.toArray(solus);
		return solus;
	}

	// TODO : devrait etre dans Choco !

	/**
	 * Gets the PalmState tool.
	 */

	public PalmState getState() {
		return this.state;
	}

	/**
	 * Increments runtime statistics.
	 * @param statistic Satistic constant (<code>PalmProblem.RLX/CPU/EXT</code>).
	 * @param inc Value to add to the current one.
	 */

	public void incRuntimeStatistic(final int statistic, final int inc) {
		this.statistics[statistic] += inc;
	}

	/**
	 * Chechs if the solver has finished searching a solution.
	 */

	public boolean isFinished() {
		return this.finished;
	}

	/**
	 * Tries to repair the state for finding a consistent state.
	 * @throws ContradictionException
	 */

	public void repair() throws ContradictionException {
		final PalmEngine pe = (PalmEngine) this.problem.getPropagationEngine();
		if (pe.isContradictory()) {
			final PalmIntVar cause = (PalmIntVar) pe.getContradictionCause();
			final Explanation expl =
				((PalmProblem) this.problem).makeExplanation();

			cause.self_explain(PalmIntDomain.DOM, expl);
			Logger.getLogger("choco.palm").fine("Repairing");
			Logger.getLogger("choco.palm").fine("Cause : " + cause);
			Logger.getLogger("choco.palm").fine("Expl : " + expl);

			pe.setContradictory(false);
			this.learning.learnFromContradiction(expl);

			if (expl.isEmpty()) {
				pe.raiseSystemContradiction();
			}
			else {
				final Constraint constraint =
					this.repairing.selectDecisionToUndo(expl);
				if (constraint != null) {
					if (((PalmConstraintPlugin) constraint.getPlugIn())
						.getWeight() <= ((PalmProblem) this.problem)
						.getMaxRelaxLevel()) {
						this.incRuntimeStatistic(PalmProblem.RLX, 1);
						if (((PalmConstraintPlugin) constraint.getPlugIn())
							.getWeight() > 0) {
						}
						else {
							this.state.removeDecision(constraint);
						}

						try {
							((PalmProblem) this.problem).remove(constraint);
							this.problem.propagate();
						}
						catch (final PalmContradiction e) {
							this.repair();
						}

						if (((PalmConstraintPlugin) constraint.getPlugIn())
							.getWeight() == 0) {
							// Negation
							expl.delete(constraint);

							final Constraint negCons =
								((DecisionConstraint) constraint).negate();
							if (negCons != null) {
								if (expl.isValid()) {
									expl.clear();
									try {
										Logger.getLogger("choco.palm").fine(
											"Negation posted and propagated.");
										((PalmProblem) this.problem).post(
											negCons,
											expl);
										this.problem.propagate();
									}
									catch (final PalmContradiction e) {
										this.repair();
									}
								}
							}
						}
					}
					else {
						System.out.println("Contradiction because of: " + expl);
						pe.raiseSystemContradiction();
					}
				}
				else {
					System.out.println("Contradiction because of: " + expl);
					pe.raiseSystemContradiction();
				}
			}
		}
	}

	/**
	 * Resets the solver (statistics and business data).
	 */

	public void reset() {
		this.finished = false;
		((PalmEngine) this.problem.getPropagationEngine()).resetDummyVariable();
		for (int i = 0; i < 3; i++) {
			this.setRuntimeStatistic(i, 0);
		}
	}

	/**
	 * Starts solving.
	 */

	public void run() {
		final long time = System.currentTimeMillis();
		try {
			this.finished = false;
			try {
				this.problem.propagate();
			}
			catch (final PalmContradiction e) {
				this.repair();
			}

			while (!this.finished) {
				try {
					Logger.getLogger("choco.palm").fine(
						"New extension launched.");
					this.extend();
					this.problem.propagate();
				}
				catch (final PalmContradiction e) {
					this.repair();
				}
			}
			this.problem.feasible = Boolean.TRUE;
		}
		catch (final ContradictionException e) {
			this.finished = true;
			this.problem.feasible = Boolean.FALSE;
		}
		this.setRuntimeStatistic(
			PalmProblem.CPU,
			(int) (System.currentTimeMillis() - time));
	}

	/**
	 * Sets if the solver has finished searching a solution.
	 * @param f New value.
	 */

	public void setFinished(final boolean f) {
		this.finished = f;
	}

	/**
	 * Sets runtime statistics.
	 * @param statistic Satistic constant (<code>PalmProblem.RLX/CPU/EXT</code>).
	 * @param val New value.
	 */

	public void setRuntimeStatistic(final int statistic, final int val) {
		this.statistics[statistic] = val;
	}

	/**
	 * Stores the current solution.
	 */

	public void storeSolution() {
		if (this.problem.feasible.booleanValue()) {
			final int[] values = new int[this.problem.getNbIntVars()];
			for (int i = 0; i < values.length; i++) {
				values[i] = this.problem.getIntVar(i).getInf();
			}
			final int[] stats = new int[3];
			System.arraycopy(this.statistics, 0, stats, 0, 3);
			final PalmSolution solution =
				new PalmSolution(
					this.problem,
					values,
					this.problem.feasible.booleanValue(),
					stats);
			this.solutions.add(solution);
			//System.out.println("Solution " + solutions.size());
		}
	}
}
