package choco.bool;

import org.junit.Assert;
import choco.AbstractConstraint;
import choco.mem.StoredInt;
import choco.util.BitSet;

// **************************************************
// *                   J-CHOCO                      *
// *   Copyright (C) F. Laburthe, 1999-2004         *
// **************************************************
// *  an open-source Constraint Programming Kernel  *
// *     for Research and Education                 *
// **************************************************

/* FileAbstractBinBoolConstraint.java, last modified by François, 28 mars 2004 */

/**
 * An abstract class for binary Boolean composition
 */
public abstract class AbstractBinBoolConstraint extends
		AbstractBinCompositeConstraint implements BoolConstraint {

	/** using a bitvector to store four three-valued data (true, false, unknown): status/targetStatus of c.const0/2
	 *  Each data is stored on two bits:
	 *   - the first bit indicates whether the data is known or unknown
	 *   - the second bit indicates whether the data is true or false
	 *  Therefore, the second bit is meaningful only when the first one is true.
	 */
	protected StoredInt statusBitVector;

	public AbstractBinBoolConstraint(
		final AbstractConstraint c1,
		final AbstractConstraint c2) {
		super(c1, c2);
		this.statusBitVector =
			new StoredInt(this.getProblem().getEnvironment(), 0);
	}
	public int assignIndices(final AbstractCompositeConstraint root, final int i) {
		int j = i;
		j = this.const0.assignIndices(root, j);
		this.offset = j - i;
		j = this.const1.assignIndices(root, j);
		return j;
	}

	public Boolean getStatus(final int constIdx) {
		if (BitSet.getBit(this.statusBitVector.get(), 4 * constIdx)) {
			if (BitSet.getBit(this.statusBitVector.get(), 4 * constIdx + 1)) {
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
		if (BitSet.getBit(this.statusBitVector.get(), 4 * constIdx + 2)) {
			if (BitSet.getBit(this.statusBitVector.get(), 4 * constIdx + 3)) {
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
		Assert.assertTrue(this.getStatus(constIdx) == null); // TODO! move into a junit test
		this.statusBitVector.set(BitSet.setBit(
			this.statusBitVector.get(),
			4 * constIdx));
		if (st) {
			if (this.getStatus(constIdx) == Boolean.FALSE) {
				this.statusBitVector.set(BitSet.setBit(
					this.statusBitVector.get(),
					4 * constIdx + 1));
			}
		}
		else {
			if (this.getStatus(constIdx) == Boolean.TRUE) {
				this.statusBitVector.set(BitSet.unsetBit(
					this.statusBitVector.get(),
					4 * constIdx + 1));
			}
		}
		Assert.assertTrue(!(this.getStatus(constIdx) == null)); // TODO! move into a junit test
		Assert.assertTrue(this.getStatus(constIdx) == Boolean.valueOf(st)); // TODO! move into a junit test
	}

	public void setTargetStatus(final int constIdx, final boolean st) {
		Assert.assertTrue(!(this.getTargetStatus(constIdx) == null)); // TODO! move into a junit test
		this.statusBitVector.set(BitSet.setBit(
			this.statusBitVector.get(),
			4 * constIdx + 2));
		if (st) {
			if (this.getTargetStatus(constIdx) == Boolean.FALSE) {
				this.statusBitVector.set(BitSet.setBit(
					this.statusBitVector.get(),
					4 * constIdx + 3));
			}
		}
		else {
			if (this.getTargetStatus(constIdx) == Boolean.TRUE) {
				this.statusBitVector.set(BitSet.unsetBit(
					this.statusBitVector.get(),
					4 * constIdx + 3));
			}
		}
		Assert.assertTrue(!(this.getTargetStatus(constIdx) == null)); // TODO! move into a junit test
		Assert.assertTrue(this.getTargetStatus(constIdx) == Boolean.valueOf(st)); // TODO! move into a junit test
	}

}
