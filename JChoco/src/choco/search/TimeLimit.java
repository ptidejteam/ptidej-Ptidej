package choco.search;

// **************************************************
// *                   J-CHOCO                      *
// *   Copyright (C) F. Laburthe, 1999-2003         *
// **************************************************
// *  an open-source Constraint Programming Kernel  *
// *     for Research and Education                 *
// **************************************************

public class TimeLimit extends AbstractGlobalSearchLimit {
	long starth = 0;

	public TimeLimit(final int theLimit) {
		super(theLimit);
		this.unit = "millis.";
	}

	public boolean endNode(final AbstractGlobalSearchSolver solver) {
		return true;
	}

	public boolean newNode(final AbstractGlobalSearchSolver solver) {
		this.nb = (int) (System.currentTimeMillis() - this.starth);
		return this.nb < this.nbMax;
	}

	public void reset(final boolean first) {
		super.reset(first);
		this.starth = System.currentTimeMillis();
	}
}
