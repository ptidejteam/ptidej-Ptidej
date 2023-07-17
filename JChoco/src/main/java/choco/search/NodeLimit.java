package choco.search;

// **************************************************
// *                   J-CHOCO                      *
// *   Copyright (C) F. Laburthe, 1999-2003         *
// **************************************************
// *  an open-source Constraint Programming Kernel  *
// *     for Research and Education                 *
// **************************************************

public final class NodeLimit extends AbstractGlobalSearchLimit {

	public NodeLimit(final int theLimit) {
		super(theLimit);
		this.unit = "nodes";
	}

	public boolean endNode(final AbstractGlobalSearchSolver solver) {
		return true;
	}

	public boolean newNode(final AbstractGlobalSearchSolver solver) {
		this.nb++;
		return this.nb < this.nbMax;
	}

}
