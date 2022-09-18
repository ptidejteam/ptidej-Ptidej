package choco.search;

import choco.Problem;
import choco.branch.IntBranching;

// **************************************************
// *                   J-CHOCO                      *
// *   Copyright (C) F. Laburthe, 1999-2003         *
// **************************************************
// *  an open-source Constraint Programming Kernel  *
// *     for Research and Education                 *
// **************************************************

/* File choco.search.AbstractSearchHeuristic.java, last modified by flaburthe 21 oct. 2003 11:29:27 */

/**
 * An abstract class for all heuristics (variable, value, branching heuristics) related to search
 */
public class AbstractSearchHeuristic {
	/**
	 * the branching object owning the variable heuristic
	 */
	protected AbstractIntBranching branching;

	/**
	 * the problem to which the heuristic is related
	 */
	protected Problem problem;

	/**
	 * each IVarSelector is associated to a branching strategy
	 * @return the associated branching strategy
	 */
	public IntBranching getBranching() {
		return this.branching;
	}
}
