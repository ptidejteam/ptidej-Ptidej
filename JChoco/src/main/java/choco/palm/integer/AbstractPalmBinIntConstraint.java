//     ___  ___         PaLM library for Choco system
//    /__ \/ __\        (c) 2001 - 2004 -- Narendra Jussien
//   ____\ |/_____
//  /_____\/_____ \     Explanation based constraint programming tool
// |/   (_)(_)   \|
//       \ /            Version 0.1
//       \ /            January 2004
//       \ /
// ______\_/_______     Contibutors: François Laburthe, Hadrien Cambazard, Guillaume Rochart...

package choco.palm.integer;

import choco.ContradictionException;
import choco.integer.IntVar;
import choco.integer.constraints.AbstractBinIntConstraint;
import choco.palm.PalmConstraint;
import choco.util.IntIterator;

public abstract class AbstractPalmBinIntConstraint extends
		AbstractBinIntConstraint implements PalmIntVarListener, PalmConstraint {

	public void awakeOnInst(final int idx) {
	}

	public void awakeOnRestoreInf(final int index)
			throws ContradictionException {
		this.propagate();
	}

	public void awakeOnRestoreSup(final int index)
			throws ContradictionException {
		this.propagate();
	}

	public void awakeOnRestoreVal(final int idx, final IntIterator repairDomain)
			throws ContradictionException {
		for (; repairDomain.hasNext();) {
			this.awakeOnRestoreVal(idx, repairDomain.next());
		}
		/*for (int val = repairDomain.next(); repairDomain.hasNext(); val=repairDomain.next()) {
		  awakeOnRestoreVal(idx, val);
		}*/
	}

	public IntVar getIntVar(final int i) {
		if (i == 0) {
			return this.v0;
		}
		if (i == 1) {
			return this.v1;
		}
		return null;
		//throw new NetworkOutOfBoundException();
	}

	public void takeIntoAccountStatusChange(final int index) {
	}

	public void updateDataStructuresOnConstraint(
		final int idx,
		final int select,
		final int newValue,
		final int oldValue) {
	}

	public void updateDataStructuresOnRestoreConstraint(
		final int idx,
		final int select,
		final int newValue,
		final int oldValue) {
	}
}
