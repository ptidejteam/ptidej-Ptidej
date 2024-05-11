package choco.bool;

import org.junit.Assert;
import choco.mem.Environment;
import choco.mem.StoredInt;
import choco.mem.StoredIntVector;
import choco.util.BitSet;

// **************************************************
// *                   J-CHOCO                      *
// *   Copyright (C) F. Laburthe, 1999-2004         *
// **************************************************
// *  an open-source Constraint Programming Kernel  *
// *     for Research and Education                 *
// **************************************************

/* FileAbstractLargeBoolConstraint.java, last modified by Franeois, 28 mars 2004 */

/**
 * An abstract class for Boolean composition of more than two constraints
 */
public abstract class AbstractLargeBoolConstraint extends
		AbstractLargeCompositeConstraint implements BoolConstraint {
	protected StoredIntVector statusBitVector;
	protected StoredInt nbTrueStatus;
	protected StoredInt nbFalseStatus;

	public AbstractLargeBoolConstraint() {
		super();
		final Environment env = this.getProblem().getEnvironment();
		this.statusBitVector = env.makeIntVector(this.nbConstraints / 8, 0);
		this.nbTrueStatus = (StoredInt) env.makeInt(0);
		this.nbFalseStatus = (StoredInt) env.makeInt(0);
	}

	public int assignIndices(final AbstractCompositeConstraint root, final int i) {
		int j = i;
		for (int constIdx = 0; constIdx < this.nbConstraints; constIdx++) {
			j = this.constraints[constIdx].assignIndices(root, i);
		}
		for (int k = 0; k < this.additionalVars.length; k++) {
			j++;
			root.connectVar(this.additionalVars[k], j);
		}
		return j;
	}

	public Boolean getStatus(final int constIdx) {
		final int i1 = constIdx / 8;
		final int i2 = 4 * (constIdx % 8);
		if (BitSet.getBit(this.statusBitVector.get(i1), i2)) {
			if (BitSet.getBit(this.statusBitVector.get(i1), i2 + 1)) {
				return Boolean.TRUE;
			}
			else {
				return Boolean.FALSE;
			}
		}
		else {
			return null;
		}
	}

	public Boolean getTargetStatus(final int constIdx) {
		final int i1 = constIdx / 8;
		final int i2 = 4 * (constIdx % 8);
		if (BitSet.getBit(this.statusBitVector.get(i1), i2 + 2)) {
			if (BitSet.getBit(this.statusBitVector.get(i1), i2 + 3)) {
				return Boolean.TRUE;
			}
			else {
				return Boolean.FALSE;
			}
		}
		else {
			return null;
		}
	}

	public void setStatus(final int constIdx, final boolean st) {
		Assert.assertTrue(this.getStatus(constIdx) == null);
		final int i1 = constIdx / 8;
		final int i2 = 4 * (constIdx % 8);
		this.statusBitVector.set(
			i1,
			BitSet.setBit(this.statusBitVector.get(i1), i2));
		if (st) {
			if (this.getStatus(constIdx) == Boolean.FALSE) {
				this.statusBitVector.set(
					i1,
					BitSet.setBit(this.statusBitVector.get(i1), i2 + 1));
			}
		}
		else {
			if (this.getStatus(constIdx) == Boolean.TRUE) {
				this.statusBitVector.set(
					i1,
					BitSet.unsetBit(this.statusBitVector.get(i1), i2 + 1));
			}
		}
		Assert.assertTrue(this.getStatus(constIdx) == Boolean.valueOf(st));
	}

	public void setTargetStatus(final int constIdx, final boolean st) {
		Assert.assertTrue(this.getTargetStatus(constIdx) == null);
		final int i1 = constIdx / 8;
		final int i2 = 4 * (constIdx % 8);
		this.statusBitVector.set(
			i1,
			BitSet.setBit(this.statusBitVector.get(i1), i2 + 2));
		if (st) {
			if (this.getTargetStatus(constIdx) == Boolean.FALSE) {
				this.statusBitVector.set(
					i1,
					BitSet.setBit(this.statusBitVector.get(i1), i2 + 3));
			}
		}
		else {
			if (this.getTargetStatus(constIdx) == Boolean.TRUE) {
				this.statusBitVector.set(
					i1,
					BitSet.unsetBit(this.statusBitVector.get(i1), i2 + 3));
			}
		}
		Assert.assertTrue(this.getTargetStatus(constIdx) == Boolean.valueOf(st));
	}

}