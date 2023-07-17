package choco.palm.integer;

import choco.ContradictionException;
import choco.integer.IntVar;
import choco.integer.constraints.AbstractTernIntConstraint;
import choco.palm.PalmConstraint;
import choco.util.IntIterator;

/**
 * Created by IntelliJ IDEA.
 * User: Administrateur
 * Date: 30 janv. 2004
 * Time: 09:00:21
 * To change this template use Options | File Templates.
 */
public abstract class AbstractPalmTernIntConstraint extends
		AbstractTernIntConstraint implements PalmIntVarListener, PalmConstraint {

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
	}

	public IntVar getIntVar(final int i) {
		if (i == 0) {
			return this.v0;
		}
		if (i == 1) {
			return this.v1;
		}
		if (i == 2) {
			return this.v2;
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
