package choco.search;

import java.util.ArrayList;
import java.util.logging.Level;
import org.junit.Assert;
import choco.AbstractSolver;
import choco.ContradictionException;
import choco.Problem;

// **************************************************
// *                   J-CHOCO                      *
// *   Copyright (C) F. Laburthe, 1999-2003         *
// **************************************************
// *  an open-source Constraint Programming Kernel  *
// *     for Research and Education                 *
// **************************************************

/* File choco.search.AbstractGlobalSearchSolver.java, last modified by flaburthe 21 oct. 2003 09:52:56 */

/**
 * An abstract class for controlling tree search in various ways
 */
public abstract class AbstractGlobalSearchSolver extends AbstractSolver {
	/**
	 * indicates whether the control should stop after the first solution is found
	 */
	public boolean stopAtFirstSol = true;

	/**
	 * count of the solutions found during search
	 */
	public int nbSolutions = 0;

	/**
	 * count of the backtracks made during search
	 */
	//  public int nbBacktracks = 0;

	/**
	 * maximal search depth for logging statements
	 */
	public int loggingMaxDepth = 5;

	/**
	 /**
	 * the goal that needs be solved to drive the exploration
	 */
	public AbstractIntBranching mainGoal;

	/**
	 *  the index of the world where the search started
	 */
	public int baseWorld = 0;

	/**
	 * A set of limits controlling the exploration
	 */
	//  public AbstractGlobalSearchLimit[] limits;
	public ArrayList limits;

	protected AbstractGlobalSearchSolver(final Problem pb) {
		this.problem = pb;
		//    limits = new AbstractGlobalSearchLimit[0];
		this.limits = new ArrayList();
	}

	/**
	 * called after a node is expanded in the search tree (choice point creation)
	 */
	public void endTreeNode() throws ContradictionException {
		for (int i = 0; i < this.limits.size(); i++) {
			final AbstractGlobalSearchLimit lim =
				(AbstractGlobalSearchLimit) this.limits.get(i);
			lim.endNode(this);
		}
	}

	/**
	 * called before a new search tree is explored
	 */
	public void endTreeSearch() {
		for (int i = 0; i < this.limits.size(); i++) {
			final AbstractGlobalSearchLimit lim =
				(AbstractGlobalSearchLimit) this.limits.get(i);
			lim.reset(false);
		}
		if (AbstractSolver.logger.isLoggable(Level.SEVERE)) {
			if (this.problem.feasible.booleanValue()) {
				AbstractSolver.logger.log(Level.SEVERE, "solve => "
						+ Integer.valueOf(this.nbSolutions) + " solutions");
			}
			else {
				AbstractSolver.logger.severe("solve => no solution");
			}
			for (int i = 0; i < this.limits.size(); i++) {
				final AbstractGlobalSearchLimit lim =
					(AbstractGlobalSearchLimit) this.limits.get(i);
				AbstractSolver.logger.severe(lim.pretty());
			}
		}
	}

	/**
	 * called before a node is expanded in the search tree (choice point creation)
	 */
	public void newTreeNode() throws ContradictionException {
		for (int i = 0; i < this.limits.size(); i++) {
			final AbstractGlobalSearchLimit lim =
				(AbstractGlobalSearchLimit) this.limits.get(i);
			if (lim.newNode(this) == false) {
				this.problem.getPropagationEngine().raiseContradiction(lim);
			}
		}
	}

	/**
	 * called before a new search tree is explored
	 */
	public void newTreeSearch() throws ContradictionException {
		Assert.assertTrue(this.problem.getSolver().getSearchSolver() == this);
		for (int i = 0; i < this.limits.size(); i++) {
			final AbstractGlobalSearchLimit lim =
				(AbstractGlobalSearchLimit) this.limits.get(i);
			lim.reset(true);
		}
		this.nbSolutions = 0;
		this.baseWorld = this.problem.getEnvironment().getWorldIndex();
	}

	/**
	 *  called before going down into each branch of the choice point
	 */
	public void postDynamicCut() throws ContradictionException {
	}

	/**
	 *  called when a solution is encountered: printing and, if needed, storing the solution
	 */
	public void recordSolution() {
		this.problem.feasible = Boolean.TRUE;
		this.nbSolutions = this.nbSolutions + 1;
		//      trace(SVIEW,"... solution [~S]\n",a.limits),  // v1.011 <thb>
		if (this.maxNbSolutionStored > 0) {
			super.recordSolution();
		}
	}

	/**
	 * main entry point: searching for one solution
	 * Note: the initial propagation must be done before pushing any world level.
	 * It is therefore kept before restoring a solution
	 */
	public void run() {
		boolean feasibleRootState = true;
		try {
			this.newTreeSearch();
			this.problem.propagate();
		}
		catch (final ContradictionException e) {
			feasibleRootState = false;
		}
		if (feasibleRootState) {
			this.problem.worldPush();
			this.mainGoal.explore(1);
			this.problem.worldPop();
			// explore is responsible for recordinf problem.feasible = Boolean.TRUE in case a solution is found
			if (this.problem.feasible == null) {
				this.problem.feasible = Boolean.TRUE;
			}
			else if (this.maxNbSolutionStored > 0 && !this.stopAtFirstSol
					&& this.existsSolution()) {
				this.restoreBestSolution();
			}
		}
		else {
			this.problem.feasible = Boolean.FALSE;
		}
		this.endTreeSearch();
	}
}
