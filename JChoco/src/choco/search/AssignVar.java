package choco.search;

import choco.ContradictionException;
import choco.integer.search.AbstractIntVarSelector;
import choco.integer.search.IValIterator;

// **************************************************
// *                   J-CHOCO                      *
// *   Copyright (C) F. Laburthe, 1999-2003         *
// **************************************************
// *  an open-source Constraint Programming Kernel  *
// *     for Research and Education                 *
// **************************************************

/* File choco.search.AssignVar.java, last modified by flaburthe 21 oct. 2003 11:58:59 */

public final class AssignVar extends AbstractLargeIntBranching {
	private final AbstractIntVarSelector varHeuristic;
	private final IValIterator valHeuristic;

	public AssignVar(
		final AbstractIntVarSelector varSel,
		final IValIterator valHeuri) {
		this.varHeuristic = varSel;
		this.valHeuristic = valHeuri;
	}

	public boolean finishedBranching(final Object x, final int i) {
		return !this.valHeuristic.hasNextVal(
			(choco.integer.var.IntDomainVar) x,
			i);
	}

	public int getFirstBranch(final Object x) {
		return this.valHeuristic
			.getFirstVal((choco.integer.var.IntDomainVar) x);
	}

	public int getNextBranch(final Object x, final int i) {
		return this.valHeuristic.getNextVal(
			(choco.integer.var.IntDomainVar) x,
			i);
	}

	public void goDownBranch(final Object x, final int i)
			throws ContradictionException {
		super.goDownBranch(x, i);
		final choco.integer.var.IntDomainVar y =
			(choco.integer.var.IntDomainVar) x;
		y.setVal(i);
		this.manager.problem.propagate();
	}

	public void goUpBranch(final Object x, final int i)
			throws ContradictionException {
		super.goUpBranch(x, i);
		final choco.integer.var.IntDomainVar y =
			(choco.integer.var.IntDomainVar) x;
		y.remVal(i);
		this.manager.problem.propagate();
	}

	/**
	 * selecting the object under scrutiny (that object on which an alternative will be set)
	 * @return the object on which an alternative will be set (often  a variable)
	 */
	public Object selectBranchingObject() {
		return this.varHeuristic.selectVar();
	}

}
