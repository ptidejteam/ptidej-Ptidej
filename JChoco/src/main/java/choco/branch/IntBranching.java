package choco.branch;

import choco.ContradictionException;

// **************************************************
// *                   J-CHOCO                      *
// *   Copyright (C) F. Laburthe, 1999-2003         *
// **************************************************
// *  an open-source Constraint Programming Kernel  *
// *     for Research and Education                 *
// **************************************************

/* File choco.branch.IntBranching.java, last modified by flaburthe 20 oct. 2003 10:20:48 */

/**
 * IntBranching objects are specific branching objects where each branch is labeled with an integer.
 * This is typically useful for choice points in search trees
 */
public interface IntBranching extends Branching {

	/**
	 * A method exploring the i-th branch of choice point
	 * @param x the current branching object
	 * @param i the index of the branch
	 * @return true if the subtree below that branch lead to a solution or not
	 */
	public boolean branchOn(Object x, int i) throws ContradictionException;

	/**
	 * A method launching the exploration of a subtree in order to satisfy the current goal
	 * @param n current depth in the search tree
	 * @return true iff the search was successful
	 */
	public boolean explore(int n);

	/**
	 * Checks whether all branches have already been explored at the current choice point
	 * @param x the object on which the alternative is set
	 * @param i the index of the last branch
	 * @return true if no more branches can be generated
	 */
	public boolean finishedBranching(Object x, int i);

	/**
	 * Computes the search index of the first branch of the choice point
	 * @param x the object on which the alternative is set
	 * @return the index of the first branch
	 */
	public int getFirstBranch(Object x);

	/**
	 * Computes the search index of the next branch of the choice point
	 * @param x the object on which the alternative is set
	 * @param i the index of the current branch
	 * @return the index of the next branch
	 */
	public int getNextBranch(Object x, int i);

	/**
	 * performs the action, so that we go down a branch from the current choice point
	 * @param x the object on which the alternative is set
	 * @param i the label of the branch that we want to go down
	 */
	public void goDownBranch(Object x, int i) throws ContradictionException;

	/**
	 * performs the action, so that we go down up the current branch to the father choice point
	 * @param x the object on which the alternative has been set at the father choice point
	 * @param i the label of the branch that has been travelled down from the father choice point
	 */
	public void goUpBranch(Object x, int i) throws ContradictionException;
}
