package choco;

// **************************************************
// *                   J-CHOCO                      *
// *   Copyright (C) F. Laburthe, 1999-2003         *
// **************************************************
// *  an open-source Constraint Programming Kernel  *
// *     for Research and Education                 *
// **************************************************

/* File choco.Solution.java, last modified by flaburthe 21 oct. 2003 11:07:01 */

/**
 * A class storing a state of the problem
 */
public class Solution {
	/**
	 * the problem owning the solution
	 */
	protected Problem problem;

	/**
	 * data storage for values of search variables
	 */
	protected int[] intVarValues;

	protected int objectiveValue;

	/**
	 * Constructor
	 * @param pb the problem owning the solution
	 */
	public Solution(final Problem pb) {
		this.problem = pb;
		final int nbv = this.problem.getNbIntVars();
		this.intVarValues = new int[nbv];
		for (int i = 0; i < nbv; i++) {
			this.intVarValues[i] = Integer.MAX_VALUE;
		}
		this.objectiveValue = Integer.MAX_VALUE;
	}

	/**
	 * Accessor to the value of a variable in a solution
	 * @param varIndex the index of the variable among all variables of the problem
	 * @return its value (whenever it is instantiated in the solution), or Integer.MAX_VALUE otherwise
	 */
	public int getValue(final int varIndex) {
		return this.intVarValues[varIndex];
	}

	public void recordIntObjective(final int intObjectiveValue) {
		this.objectiveValue = intObjectiveValue;
	}

	public void recordIntValue(final int intVarIndex, final int intVarValue) {
		this.intVarValues[intVarIndex] = intVarValue;
	}

	public void restore() {
		try {
			for (int i = 0; i < this.intVarValues.length; i++) {
				if (this.intVarValues[i] != Integer.MAX_VALUE) {
					this.problem.getIntVar(i).setVal(this.intVarValues[i]);
				}
			}
			this.problem.propagate();
		}
		catch (final ContradictionException e) {
			System.out.println("BUG in restoring solution !!!!!!!!!!!!!!!!");
			// TODO
		}
	}

}
