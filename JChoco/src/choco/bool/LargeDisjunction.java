package choco.bool;

import org.junit.Assert;
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

/* FileLargeDisjunction.java, last modified by François, 4 avr. 2004 */

public class LargeDisjunction extends
		AbstractLargeBoolConstraintWithCounterOpposite {

	public LargeDisjunction() {
		super();
	}

	// the initial account of all statuses catches the case of disjunctions with no or one single disjunct
	public void awake() throws ContradictionException {
		this.checkNbFalseStatus();
		this.propagate();
	}

	public void awakeOnBounds(final int varIdx) throws ContradictionException {
		final int constIdx = this.getSubConstraintIdx(varIdx);
		final Boolean Bi = this.getStatus(constIdx);
		final Boolean TBi = this.getTargetStatus(constIdx);
		if (Bi == null) {
			if (TBi == Boolean.TRUE) {
				final int localVarIndex =
					this.getLocalVarIndex(varIdx, constIdx);
				((IntConstraint) this.constraints[constIdx])
					.awakeOnBounds(localVarIndex);
			}
			else if (TBi == null) {
				this.checkStatus(constIdx);
			}
		}
	}

	public void awakeOnInf(final int varIdx) throws ContradictionException {
		final int constIdx = this.getSubConstraintIdx(varIdx);
		final Boolean Bi = this.getStatus(constIdx);
		final Boolean TBi = this.getTargetStatus(constIdx);
		if (Bi == null) {
			if (TBi == Boolean.TRUE) {
				final int localVarIndex =
					this.getLocalVarIndex(varIdx, constIdx);
				((IntConstraint) this.constraints[constIdx])
					.awakeOnInf(localVarIndex);
			}
			else if (TBi == null) {
				this.checkStatus(constIdx);
			}
		}
	}

	public void awakeOnInst(final int varIdx) throws ContradictionException {
		final int constIdx = this.getSubConstraintIdx(varIdx);
		final Boolean Bi = this.getStatus(constIdx);
		final Boolean TBi = this.getTargetStatus(constIdx);
		if (Bi == null) {
			if (TBi == Boolean.TRUE) {
				final int localVarIndex =
					this.getLocalVarIndex(varIdx, constIdx);
				((IntConstraint) this.constraints[constIdx])
					.awakeOnInst(localVarIndex);
			}
			else if (TBi == null) {
				this.checkStatus(constIdx);
			}
		}
	}

	public void awakeOnRem(final int varIdx, final int val)
			throws ContradictionException {
		final int constIdx = this.getSubConstraintIdx(varIdx);
		final Boolean Bi = this.getStatus(constIdx);
		final Boolean TBi = this.getTargetStatus(constIdx);
		if (Bi == null) {
			if (TBi == Boolean.TRUE) {
				final int localVarIndex =
					this.getLocalVarIndex(varIdx, constIdx);
				((IntConstraint) this.constraints[constIdx]).awakeOnRem(
					localVarIndex,
					val);
			}
			else if (TBi == null) {
				this.checkStatus(constIdx);
			}
		}
	}

	public void awakeOnRemovals(final int varIdx, final IntIterator deltaDomain)
			throws ContradictionException {
		final int constIdx = this.getSubConstraintIdx(varIdx);
		final Boolean Bi = this.getStatus(constIdx);
		final Boolean TBi = this.getTargetStatus(constIdx);
		if (Bi == null) {
			if (TBi == Boolean.TRUE) {
				final int localVarIndex =
					this.getLocalVarIndex(varIdx, constIdx);
				((IntConstraint) this.constraints[constIdx]).awakeOnRemovals(
					localVarIndex,
					deltaDomain);
			}
			else if (TBi == null) {
				this.checkStatus(constIdx);
			}
		}
	}

	public void awakeOnSup(final int varIdx) throws ContradictionException {
		final int constIdx = this.getSubConstraintIdx(varIdx);
		final Boolean Bi = this.getStatus(constIdx);
		final Boolean TBi = this.getTargetStatus(constIdx);
		if (Bi == null) {
			if (TBi == Boolean.TRUE) {
				final int localVarIndex =
					this.getLocalVarIndex(varIdx, constIdx);
				((IntConstraint) this.constraints[constIdx])
					.awakeOnSup(localVarIndex);
			}
			else if (TBi == null) {
				this.checkStatus(constIdx);
			}
		}
	}

	/**
	 *  this compares the number of false constraints (subconstraints whose status is false)
	 * with the overall number of constraints.
	 */
	protected void checkNbFalseStatus() throws ContradictionException {
		if (this.nbFalseStatus.get() == this.nbConstraints) {
			this.fail();
		}
		else if (this.nbFalseStatus.get() == this.nbConstraints - 1
				&& this.nbTrueStatus.get() == 0) {
			for (int constIdx = -1; constIdx < this.nbConstraints; constIdx++) {
				if (this.getStatus(constIdx) == null) {
					this.setTargetStatus(constIdx, true);
					this.constraints[constIdx].awake();
					break;
				}
			}
		}
	}

	/**
	 *  checks the status of the i-th constraint of the disjunction and propagates accordingly
	 * @param constIdx
	 * @throws ContradictionException
	 */
	protected void checkStatus(final int constIdx)
			throws ContradictionException {
		Assert.assertTrue(this.getStatus(constIdx) == null);
		final Boolean Bi = this.constraints[constIdx].isEntailed();
		if (Bi != null) {
			final Boolean TBi = this.getTargetStatus(constIdx);
			if (TBi != null) {
				if (TBi != Bi) {
					this.fail();
				}
			}
			else {
				this.setStatus(constIdx, Bi.booleanValue());
				if (Bi == Boolean.TRUE) {
					this.nbTrueStatus.set(this.nbTrueStatus.get() + 1);
				}
				else {
					this.nbFalseStatus.set(this.nbFalseStatus.get() + 1);
					this.checkNbFalseStatus();
				}
			}
		}
	}

	public boolean isConsistent() {
		throw new UnsupportedOperationException();
	}

	public Boolean isEntailed() {
		boolean allFalse = true;
		boolean oneTrue = false;
		for (int constIdx = 0; constIdx < this.nbConstraints; constIdx++) {
			Boolean Bi = this.getStatus(constIdx);
			if (Bi == null) {
				Bi = this.constraints[constIdx].isEntailed();
				if (Bi != null) {
					this.setStatus(constIdx, Bi.booleanValue());
				}
			}
			if (Bi != Boolean.FALSE) {
				allFalse = false;
			}
			if (Bi == Boolean.TRUE) {
				oneTrue = true;
			}
		}
		if (oneTrue) {
			return Boolean.TRUE;
		}
		else if (allFalse) {
			return Boolean.FALSE;
		}
		else {
			return null;
		}
	}

	public boolean isSatisfied() {
		boolean oneSatisfied = false;
		for (int constIdx = 0; constIdx < this.nbConstraints; constIdx++) {
			if (this.constraints[constIdx].isSatisfied()) {
				oneSatisfied = true;
				break;
			}
		}
		return oneSatisfied;
	}

	public void propagate() throws ContradictionException {
		for (int constIdx = 0; constIdx < this.nbConstraints; constIdx++) {
			if (this.getStatus(constIdx) == null) {
				this.checkStatus(constIdx);
			}
		}
	}

}
