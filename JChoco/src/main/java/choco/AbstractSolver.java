package choco;

import java.util.ArrayList;
import java.util.logging.Logger;
import choco.search.AbstractOptimize;

// **************************************************
// *                   J-CHOCO                      *
// *   Copyright (C) F. Laburthe, 1999-2003         *
// **************************************************
// *  an open-source Constraint Programming Kernel  *
// *     for Research and Education                 *
// **************************************************

/* File choco.AbstractSolver.java, last modified by flaburthe 21 oct. 2003 11:03:17 */

/**
 * An abstract class handling the control for solving a problem
 */
public abstract class AbstractSolver {
	/**
	 * The problem to which the solver is applied
	 */
	public Problem problem;

	/**
	 * The historical record of solutions that were found
	 */
	public ArrayList solutions; //Solution[]

	/**
	 * capacity of the history record (keeping solutions)
	 */
	public int maxNbSolutionStored = 5;

	/**
	 * an object for logging trace statements
	 */
	protected static Logger logger = Logger.getLogger("choco.search");

	public AbstractSolver() {
		this.solutions = new ArrayList();
	}

	public boolean existsSolution() {
		return this.solutions.size() > 0;
	}

	protected Solution makeSolutionFromCurrentState() {
		final int nbv = this.problem.getNbIntVars();
		final Solution sol = new Solution(this.problem);
		// sol.time = time_read()
		for (int i = 0; i < nbv; i++) {
			final choco.integer.IntVar vari = this.problem.getIntVar(i);
			if (vari.isInstantiated()) {
				sol.recordIntValue(i, vari.getValue());
			}
		}
		if (this instanceof AbstractOptimize) {
			sol.recordIntObjective(((AbstractOptimize) this)
				.getObjectiveValue());
		}
		return sol;
	}

	/**
	 * recording the current state as a solution
	 * stores information from the current state in the next solution of the problem
	 * note: only instantiated variables are recorded in the Solution object
	 *       either all variables or a user-defined subset of them are recorded
	 *       this may erase a soolution that was previously stored in the ith position
	 *       this may also increase the size of the pb.solutions vector.
	 */
	public void recordSolution() {
		final Solution sol = this.makeSolutionFromCurrentState();
		this.storeSolution(sol);
	}

	public void restoreBestSolution() {
		((Solution) this.solutions.get(0)).restore();
	}

	/**
	 * main entry point: running the search algorithm controlled the Solver object
	 */
	public abstract void run();

	/**
	 * showing information abuot the last solution
	 */
	public void showSolution() {
		System.out.println(this.problem.pretty());
		// TODO
	}

	protected void storeSolution(final Solution sol) {
		//[SVIEW] store solution ~S // sol,
		if (this.solutions.size() == this.maxNbSolutionStored) {
			this.solutions.remove(this.solutions.size() - 1);
		}
		this.solutions.add(0, sol);
	}
}
