package choco.palm.integer;

import choco.ContradictionException;
import choco.integer.IntVar;
import choco.integer.constraints.AbstractLargeIntConstraint;
import choco.palm.PalmConstraint;
import choco.util.IntIterator;

/**
 * Created by IntelliJ IDEA.
 * User: Administrateur
 * Date: 27 janv. 2004
 * Time: 15:26:56
 * To change this template use Options | File Templates.
 */
public abstract class AbstractPalmLargeIntConstraint extends
		AbstractLargeIntConstraint implements PalmIntVarListener,
		PalmConstraint {

	public AbstractPalmLargeIntConstraint(final int n) {
		super(n);
	}

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
		if (i >= 0 && i < this.getNbVars()) {
			return this.vars[i];
		}
		else {
			return null;
		}
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
