package choco.bool;

import choco.ContradictionException;
import choco.integer.IntConstraint;
import choco.util.IntIterator;

// **************************************************
// *                   J-CHOCO                      *
// *   Copyright (C) F. Laburthe, 1999-2004         *
// **************************************************
// *  an open-source Constraint Programming Kernel  *
// *     for Research and Education                 *
// **************************************************

/* FileLargeConjunction.java, last modified by François, 4 avr. 2004 */

public class LargeConjunction extends AbstractLargeBoolConstraint {

	public LargeConjunction() {
		super();
	}

	public void awake() throws ContradictionException {
		for (int constIdx = 0; constIdx < this.nbConstraints; constIdx++) {
			if (this.getStatus(constIdx) != Boolean.TRUE) {
				this.constraints[constIdx].awake();
			}
		}
	}

	public void awakeOnBounds(final int varIndex) throws ContradictionException {
		final int constIdx = this.getSubConstraintIdx(varIndex);
		final IntConstraint subConstraint =
			(IntConstraint) this.constraints[constIdx];
		final int localIdx =
			constIdx == 0 ? varIndex : varIndex - this.offsets[constIdx - 1];
		subConstraint.awakeOnBounds(localIdx);
	}

	public void awakeOnInf(final int idx) throws ContradictionException {
		final int constIdx = this.getSubConstraintIdx(idx);
		final IntConstraint subConstraint =
			(IntConstraint) this.constraints[constIdx];
		final int localIdx =
			constIdx == 0 ? idx : idx - this.offsets[constIdx - 1];
		subConstraint.awakeOnInf(localIdx);
	}

	public void awakeOnInst(final int idx) throws ContradictionException {
		final int constIdx = this.getSubConstraintIdx(idx);
		final IntConstraint subConstraint =
			(IntConstraint) this.constraints[constIdx];
		final int localIdx =
			constIdx == 0 ? idx : idx - this.offsets[constIdx - 1];
		subConstraint.awakeOnInst(localIdx);
	}

	public void awakeOnRem(final int idx, final int x)
			throws ContradictionException {
		final int constIdx = this.getSubConstraintIdx(idx);
		final IntConstraint subConstraint =
			(IntConstraint) this.constraints[constIdx];
		final int localIdx =
			constIdx == 0 ? idx : idx - this.offsets[constIdx - 1];
		subConstraint.awakeOnRem(localIdx, x);
	}

	public void awakeOnRemovals(final int idx, final IntIterator deltaDomain)
			throws ContradictionException {
		final int constIdx = this.getSubConstraintIdx(idx);
		final IntConstraint subConstraint =
			(IntConstraint) this.constraints[constIdx];
		final int localIdx =
			constIdx == 0 ? idx : idx - this.offsets[constIdx - 1];
		subConstraint.awakeOnRemovals(localIdx, deltaDomain);
	}

	public void awakeOnSup(final int idx) throws ContradictionException {
		final int constIdx = this.getSubConstraintIdx(idx);
		final IntConstraint subConstraint =
			(IntConstraint) this.constraints[constIdx];
		final int localIdx =
			constIdx == 0 ? idx : idx - this.offsets[constIdx - 1];
		subConstraint.awakeOnSup(localIdx);
	}

	public boolean isConsistent() {
		boolean consistent = true;
		for (int constIdx = 0; constIdx < this.nbConstraints; constIdx++) {
			if (!this.constraints[constIdx].isConsistent()) {
				consistent = false;
				break;
			}
		}
		return consistent;
	}

	public Boolean isEntailed() {
		boolean allTrue = true;
		boolean oneFalse = false;
		for (int constIdx = 0; constIdx < this.nbConstraints; constIdx++) {
			Boolean Bi = this.getStatus(constIdx);
			if (Bi == null) {
				Bi = this.constraints[constIdx].isEntailed();
				if (Bi != null) {
					this.setStatus(constIdx, Bi.booleanValue());
				}
			}
			if (Bi != Boolean.TRUE) {
				allTrue = false;
			}
			if (Bi == Boolean.FALSE) {
				oneFalse = true;
			}
		}
		if (allTrue) {
			return Boolean.TRUE;
		}
		else if (oneFalse) {
			return Boolean.FALSE;
		}
		else {
			return null;
		}
	}

	public boolean isSatisfied() {
		boolean satisfied = true;
		for (int constIdx = 0; constIdx < this.nbConstraints; constIdx++) {
			if (!this.constraints[constIdx].isSatisfied()) {
				satisfied = false;
				break;
			}
		}
		return satisfied;
	}

	public void propagate() throws ContradictionException {
		for (int constIdx = 0; constIdx < this.nbConstraints; constIdx++) {
			if (this.getStatus(constIdx) != Boolean.TRUE) {
				this.constraints[constIdx].propagate();
			}
		}
	}

}
