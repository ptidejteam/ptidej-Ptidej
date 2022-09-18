package choco.branch;

// **************************************************
// *                   J-CHOCO                      *
// *   Copyright (C) F. Laburthe, 1999-2003         *
// **************************************************
// *  an open-source Constraint Programming Kernel  *
// *     for Research and Education                 *
// **************************************************

/**
 * Branching objects are responsible for controlling the execution of the program at a point where
 * the control flow may be split between different branches
 */
public interface Branching {

	/**
	 * selecting the object under scrutiny (that object on which an alternative will be set)
	 * @return the object on which an alternative will be set (often  a variable)
	 */
	public Object selectBranchingObject();
}
