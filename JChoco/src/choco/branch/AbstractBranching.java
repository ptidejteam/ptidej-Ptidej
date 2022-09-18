package choco.branch;

import java.util.logging.Logger;
import choco.search.AbstractGlobalSearchSolver;

// **************************************************
// *                   J-CHOCO                      *
// *   Copyright (C) F. Laburthe, 1999-2003         *
// **************************************************
// *  an open-source Constraint Programming Kernel  *
// *     for Research and Education                 *
// **************************************************

public class AbstractBranching {
	/**
	 * the main control object (responsible for the whole exploration, while the branching object
	 * is responsible only at the choice point level
	 */
	protected AbstractGlobalSearchSolver manager;
	/**
	 * a link towards the next branching object (once this one is exhausted)
	 */
	protected AbstractBranching nextBranching;
	/**
	 * an object for logging trace statements
	 */
	protected static Logger logger = Logger.getLogger("choco.search");

	/**
	 * Gets the next branching.
	 */
	public AbstractBranching getNextBranching() {
		return this.nextBranching;
	}

	/**
	 * Sets the next branching.
	 */
	public void setNextBranching(final AbstractBranching nextBranching) {
		this.nextBranching = nextBranching;
	}

	public void setSolver(final AbstractGlobalSearchSolver s) {
		this.manager = s;
	}
}
