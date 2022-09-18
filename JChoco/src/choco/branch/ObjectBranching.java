package choco.branch;

// **************************************************
// *                   J-CHOCO                      *
// *   Copyright (C) F. Laburthe, 1999-2003         *
// **************************************************
// *  an open-source Constraint Programming Kernel  *
// *     for Research and Education                 *
// **************************************************

/**
 * ObjectBranching objects are specific branching objects where each branch is labeled with an Object.
 * This is typically useful for palm control objects (where the label happens to be a List of Constraint).
 */
public interface ObjectBranching extends Branching {
	/**
	 * When several decisions can be taken (for unsure extension for instance), this methos allows to
	 * choose next decisions.
	 *
	 * @param branchingItem  the branching object under scrutiny
	 * @param previousBranch the object labelling the previous branch
	 * @return the object labelling the current branch
	 */
	Object getNextBranch(Object branchingItem, Object previousBranch);

	/**
	 * Computes decisions that can be taken on the specified item by the solver.
	 *
	 * @param item The item the solver branchs on.
	 */
	Object selectFirstBranch(Object item);
}
