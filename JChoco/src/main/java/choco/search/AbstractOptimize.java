package choco.search;

import choco.ContradictionException;
import choco.util.Arithm;

// **************************************************
// *                   J-CHOCO                      *
// *   Copyright (C) F. Laburthe, 1999-2003         *
// **************************************************
// *  an open-source Constraint Programming Kernel  *
// *     for Research and Education                 *
// **************************************************

/* File choco.search.AbstractOptimize.java, last modified by flaburthe 31 oct. 2003 18:33:22 */

public abstract class AbstractOptimize extends AbstractGlobalSearchSolver {
	/**
	 * a boolean indicating whether we want to maximize (true) or minize (false) the objective variable
	 */
	public boolean doMaximize;
	/**
	 * the variable modelling the objective value
	 */
	public choco.integer.var.IntDomainVar objective;
	/**
	 * the lower bound of the objective value.
	 * This value comes from the problem definition; it is strengthened by the search history (solutions found & no-goods)
	 */
	public int lowerBound = Integer.MIN_VALUE;
	/**
	 * the upper bound of the objective value
	 * This value comes from the problem definition; it is strengthened by the search history (solutions found & no-goods)
	 */
	public int upperBound = Integer.MAX_VALUE;

	/**
	 * a tentative upper bound
	 */
	public int targetUpperBound = Integer.MAX_VALUE;

	/**
	 * a tentative lower bound
	 */
	public int targetLowerBound = Integer.MIN_VALUE;

	/**
	 * constructor
	 * @param obj the objective variable
	 * @param maximize maximization or minimization ?
	 */
	protected AbstractOptimize(
		final choco.integer.var.IntDomainVar obj,
		final boolean maximize) {
		super(obj.getProblem());
		this.objective = obj;
		this.doMaximize = maximize;
	}

	public int getBestObjectiveValue() {
		if (this.doMaximize) {
			return this.lowerBound;
		}
		else {
			return this.upperBound;
		}
	}

	/**
	 * the target for the objective function: we are searching for a solution at least as good as this (tentative bound)
	 */
	public int getObjectiveTarget() {
		if (this.doMaximize) {
			return this.targetLowerBound;
		}
		else {
			return this.targetUpperBound;
		}
	}

	/**
	 *  v1.0 accessing the objective value of an optimization problem
	 * (note that the objective value may not be instantiated, while all other variables are)
	 * @return the current objective value
	 */
	public int getObjectiveValue() {
		if (this.doMaximize) {
			return this.objective.getSup();
		}
		else {
			return this.objective.getInf();
		}
	}

	/**
	 * initialization of the optimization bound data structure
	 */
	public void initBounds() {
		this.lowerBound = this.objective.getInf();
		this.upperBound = this.objective.getSup();
		this.targetLowerBound = this.objective.getInf();
		this.targetUpperBound = this.objective.getSup();

	}

	/**
	 *  propagating the optimization cuts from the new target bounds
	 */
	public void postTargetBound() throws ContradictionException {
		if (this.doMaximize) {
			this.postTargetLowerBound();
		}
		else {
			this.postTargetUpperBound();
		}
	}

	public void postTargetLowerBound() throws ContradictionException {
		this.objective.setMin(this.targetLowerBound);
	}

	public void postTargetUpperBound() throws ContradictionException {
		this.objective.setMax(this.targetUpperBound);
	}

	/**
	 * TODO
	 */
	public void recordSolution() {
		this.problem.feasible = Boolean.TRUE;
		this.nbSolutions = this.nbSolutions + 1;
		// trace(SVIEW,"... solution with ~A:~S [~S]\n",obj.name,objval,a.limits),  // v1.011 <thb>
		this.setBound();
		this.setTargetBound();
		if (this.maxNbSolutionStored > 0) {
			this.recordSolution();
		}
	}

	/**
	 * resetting the optimization bounds
	 */
	public void setBound() {
		final int objval = this.getObjectiveValue();
		if (this.doMaximize) {
			this.lowerBound = Arithm.max(this.lowerBound, objval);
		}
		else {
			this.upperBound = Arithm.min(this.upperBound, objval);
		}
	}

	/**
	 * resetting the values of the target bounds (bounds for the remaining search)
	 */
	public void setTargetBound() {
		if (this.doMaximize) {
			this.setTargetLowerBound();
		}
		else {
			this.setTargetUpperBound();
		}
	}

	protected void setTargetLowerBound() {
		final int newBound = this.lowerBound + 1;
		if (this.problem.feasible != Boolean.TRUE) {
			// trace(STALK,"search first sol ...")
		}
		else {
			// trace(STALK,"search target: ~A >= ~S ... ",a.objective.name,newBound))
			this.targetLowerBound = newBound;
		}
	}

	protected void setTargetUpperBound() {
		final int newBound = this.upperBound - 1;
		if (this.problem.feasible != Boolean.TRUE) {
			// trace(STALK,"search first sol ...")
		}
		else {
			// trace(STALK,"search target: ~A <= ~S ... ",a.objective.name,newBound))
			this.targetUpperBound = newBound;
		}
	}

}
