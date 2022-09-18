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

import choco.Problem;
import choco.Solution;
import choco.integer.IntVar;
import choco.palm.PalmProblem;

/**
 * Solution for a Palm problem.
 */

public class PalmSolution extends Solution {

	/**
	 * States if the solutions is feasible.
	 */

	protected boolean feasible;

	/**
	 * Statistics for this solution (CPU, extensiions...).
	 */

	protected int[] lstat;

	/**
	 * Creates a solution with the specified values.
	 * @param values The values of the solution.
	 * @param feasible Specifies if the solutions is feasible.
	 * @param stat Statistics about this solution.
	 */

	public PalmSolution(
		final Problem pb,
		final int[] values,
		final boolean feasible,
		final int[] stat) {
		super(pb);
		this.intVarValues = values;
		this.feasible = feasible;
		this.lstat = stat;
	}

	/**
	 * Returns a statistic about the solution.
	 * @param stat The statitic constant (<code>PalmProblem.RLX/EXT/CPU</code>).
	 */

	public int getStatistic(final int stat) {
		return this.lstat[stat];
	}

	public int getValue(final int idx) { // TODO : dans Choco ?
		return this.intVarValues[idx];
	}

	/**
	 * Pretty display of the solution.
	 */

	public String toString() {
		final StringBuffer buf = new StringBuffer();
		if (this.feasible) {
			/*            buf.append("SOL  ");
			            List l = this.algo.getVarsToStore();
			            for (int i = 0; i < l.size(); i++) {
			                Var var = (Var) l.get(i);
			                buf.append(var + ":" + this.lval[i] + "  ");
			            }*/
			buf.append("SOL ");
			for (int i = 0; i < this.problem.getNbIntVars(); i++) {
				final IntVar var = this.problem.getIntVar(i);
				buf.append(var + ":" + this.intVarValues[i] + "  ");
			}
		}
		else {
			buf.append("NO SOL ");
		}
		buf.append("in " + this.lstat[PalmProblem.RLX] + " repairs "
				+ this.lstat[PalmProblem.EXT] + " extensions "
				+ this.lstat[PalmProblem.CPU] + " ms");
		return buf.toString();
	}
}
