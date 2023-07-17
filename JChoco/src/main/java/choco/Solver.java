package choco;

import choco.integer.search.IncreasingDomain;
import choco.integer.search.MinDomain;
import choco.search.AbstractGlobalSearchSolver;
import choco.search.AbstractIntBranching;
import choco.search.AssignVar;
import choco.search.BranchAndBound;
import choco.search.NodeLimit;
import choco.search.OptimizeWithRestarts;
import choco.search.Solve;
import choco.search.TimeLimit;

// *********************************************
// *                   J-CHOCO                 *
// *   Copyright (c) F. Laburthe, 1999-2003    *
// *********************************************
// * Event-base contraint programming Engine   *
// *********************************************

// CVS Information
// File:               $RCSfile: Solver.java,v $
// Version:            $Revision: 1.1 $
// Last Modification:  $Date: 2004/05/04 22:23:15 $
// Last Contributor:   $Author: guehene $

/**
 * This class serves both as a factory and as a handler for AbstractGlobalSearchSolvers:
 */
public class Solver extends AbstractEntity {

	/**
	 * The variable modelling the objective function
	 */
	protected choco.integer.var.IntDomainVar objective;

	/**
	 *  Maximization / Minimization problem
	 */
	protected boolean doMaximize;

	/**
	 * Do we want to restart a new search after each solution
	 */
	protected boolean restart = false;

	/**
	 * do we want to explore one or all solutions (default=one solution)
	 */
	protected boolean firstSolution = true;

	/**
	 * The object controlling the global search exploration
	 */
	protected AbstractGlobalSearchSolver solver;

	protected int timeLimit = Integer.MAX_VALUE;

	protected int nodeLimit = Integer.MAX_VALUE;

	public Solver(final Problem pb) {
		super(pb);
	}

	public void addGoal(final AbstractIntBranching branching) {
		branching.setSolver(this.solver);
		AbstractIntBranching br = this.solver.mainGoal;
		while (br.getNextBranching() != null) {
			br = (AbstractIntBranching) br.getNextBranching();
		}
		br.setNextBranching(branching);
	}

	private void attachGoal(final AbstractIntBranching branching) {
		branching.setSolver(this.solver);
		this.solver.mainGoal = branching;
	}

	private void generateGoal(final Problem pb) {
		this
			.attachGoal(new AssignVar(new MinDomain(pb), new IncreasingDomain()));
	}

	public void generateSearchSolver(final Problem pb) {
		this.problem = pb;
		if (null == this.objective) {
			this.solver = new Solve(this.getProblem());
		}
		else if (this.restart) {
			this.solver =
				new OptimizeWithRestarts(this.objective, this.doMaximize);
		}
		else {
			this.solver = new BranchAndBound(this.objective, this.doMaximize);
		}
		this.solver.stopAtFirstSol = this.firstSolution;

		this.solver.limits.add(new TimeLimit(this.timeLimit));
		this.solver.limits.add(new NodeLimit(this.nodeLimit));

		this.generateGoal(pb);
	}

	public boolean getFirstSolution() {
		return this.firstSolution;
	}

	/**
	 * returns the number of solutions encountered during the search
	 * @return the number of solutions to the problem that were encountered during the search
	 */
	public int getNbSolutions() {
		return this.solver.nbSolutions;
	}

	public AbstractGlobalSearchSolver getSearchSolver() {
		return this.solver;
	}

	/**
	 * commands the solver to start
	 */
	public void launch() {
		this.solver.run();
	}

	public void setFirstSolution(final boolean firstSolution) {
		this.firstSolution = firstSolution;
	}

	public void setNodeLimit(final int nodeLimit) {
		this.nodeLimit = nodeLimit;
	}

	public void setTimeLimit(final int timeLimit) {
		this.timeLimit = timeLimit;
	}
}
