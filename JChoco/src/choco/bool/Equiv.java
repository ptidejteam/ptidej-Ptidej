package choco.bool;

import org.junit.Assert;
import choco.AbstractConstraint;
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

/* FileEquiv.java, last modified by François, 4 avr. 2004 */
/**
 * A class for equivalences (c1 if and only if c2)
 */
public class Equiv extends AbstractBinBoolConstraintWithCounterOpposite {

	public Equiv(final AbstractConstraint c1, final AbstractConstraint c2) {
		super(c1, c2);
	}

	public void awakeOnBounds(final int idx) throws ContradictionException {
		if (idx < this.offset) {
			if (this.getStatus(0) == null) {
				final Boolean TB0 = this.getTargetStatus(0);
				if (TB0 == Boolean.TRUE) {
					((IntConstraint) this.const0).awakeOnInf(idx);
				}
				else if (TB0 == Boolean.TRUE) {
					((IntConstraint) this.oppositeConst0).awakeOnBounds(idx);
				}
				else {
					this.checkStatus(0);
				}
			}
		}
		else {
			if (this.getStatus(1) == null) {
				final Boolean TB1 = this.getTargetStatus(1);
				if (TB1 == Boolean.TRUE) {
					((IntConstraint) this.const1).awakeOnInf(idx);
				}
				else if (TB1 == Boolean.TRUE) {
					((IntConstraint) this.oppositeConst1).awakeOnBounds(idx);
				}
				else {
					this.checkStatus(1);
				}
			}
		}
	}

	public void awakeOnInf(final int idx) throws ContradictionException {
		if (idx < this.offset) {
			if (this.getStatus(0) == null) {
				final Boolean TB0 = this.getTargetStatus(0);
				if (TB0 == Boolean.TRUE) {
					((IntConstraint) this.const0).awakeOnInf(idx);
				}
				else if (TB0 == Boolean.TRUE) {
					((IntConstraint) this.oppositeConst0).awakeOnInf(idx);
				}
				else {
					this.checkStatus(0);
				}
			}
		}
		else {
			if (this.getStatus(1) == null) {
				final Boolean TB1 = this.getTargetStatus(1);
				if (TB1 == Boolean.TRUE) {
					((IntConstraint) this.const1).awakeOnInf(idx);
				}
				else if (TB1 == Boolean.TRUE) {
					((IntConstraint) this.oppositeConst1).awakeOnInf(idx);
				}
				else {
					this.checkStatus(1);
				}
			}
		}
	}

	public void awakeOnInst(final int idx) throws ContradictionException {
		if (idx < this.offset) {
			if (this.getStatus(0) == null) {
				final Boolean TB0 = this.getTargetStatus(0);
				if (TB0 == Boolean.TRUE) {
					((IntConstraint) this.const0).awakeOnInf(idx);
				}
				else if (TB0 == Boolean.TRUE) {
					((IntConstraint) this.oppositeConst0).awakeOnInst(idx);
				}
				else {
					this.checkStatus(0);
				}
			}
		}
		else {
			if (this.getStatus(1) == null) {
				final Boolean TB1 = this.getTargetStatus(1);
				if (TB1 == Boolean.TRUE) {
					((IntConstraint) this.const1).awakeOnInf(idx);
				}
				else if (TB1 == Boolean.TRUE) {
					((IntConstraint) this.oppositeConst1).awakeOnInst(idx);
				}
				else {
					this.checkStatus(1);
				}
			}
		}
	}

	public void awakeOnRem(final int idx, final int x)
			throws ContradictionException {
		if (idx < this.offset) {
			if (this.getStatus(0) == null) {
				final Boolean TB0 = this.getTargetStatus(0);
				if (TB0 == Boolean.TRUE) {
					((IntConstraint) this.const0).awakeOnInf(idx);
				}
				else if (TB0 == Boolean.TRUE) {
					((IntConstraint) this.oppositeConst0).awakeOnRem(idx, x);
				}
				else {
					this.checkStatus(0);
				}
			}
		}
		else {
			if (this.getStatus(1) == null) {
				final Boolean TB1 = this.getTargetStatus(1);
				if (TB1 == Boolean.TRUE) {
					((IntConstraint) this.const1).awakeOnInf(idx);
				}
				else if (TB1 == Boolean.TRUE) {
					((IntConstraint) this.oppositeConst1).awakeOnRem(idx, x);
				}
				else {
					this.checkStatus(1);
				}
			}
		}
	}

	public void awakeOnRemovals(final int idx, final IntIterator deltaDomain)
			throws ContradictionException {
		if (idx < this.offset) {
			if (this.getStatus(0) == null) {
				final Boolean TB0 = this.getTargetStatus(0);
				if (TB0 == Boolean.TRUE) {
					((IntConstraint) this.const0).awakeOnInf(idx);
				}
				else if (TB0 == Boolean.TRUE) {
					((IntConstraint) this.oppositeConst0).awakeOnRemovals(
						idx,
						deltaDomain);
				}
				else {
					this.checkStatus(0);
				}
			}
		}
		else {
			if (this.getStatus(1) == null) {
				final Boolean TB1 = this.getTargetStatus(1);
				if (TB1 == Boolean.TRUE) {
					((IntConstraint) this.const1).awakeOnInf(idx);
				}
				else if (TB1 == Boolean.TRUE) {
					((IntConstraint) this.oppositeConst1).awakeOnRemovals(
						idx,
						deltaDomain);
				}
				else {
					this.checkStatus(1);
				}
			}
		}
	}

	public void awakeOnSup(final int idx) throws ContradictionException {
		if (idx < this.offset) {
			if (this.getStatus(0) == null) {
				final Boolean TB0 = this.getTargetStatus(0);
				if (TB0 == Boolean.TRUE) {
					((IntConstraint) this.const0).awakeOnInf(idx);
				}
				else if (TB0 == Boolean.TRUE) {
					((IntConstraint) this.oppositeConst0).awakeOnSup(idx);
				}
				else {
					this.checkStatus(0);
				}
			}
		}
		else {
			if (this.getStatus(1) == null) {
				final Boolean TB1 = this.getTargetStatus(1);
				if (TB1 == Boolean.TRUE) {
					((IntConstraint) this.const1).awakeOnInf(idx);
				}
				else if (TB1 == Boolean.TRUE) {
					((IntConstraint) this.oppositeConst1).awakeOnSup(idx);
				}
				else {
					this.checkStatus(1);
				}
			}
		}
	}

	// if status(i) can be inferred, sets targetStatus(j) and propagate accordingly
	protected void checkStatus(final int i) throws ContradictionException {
		final AbstractConstraint ci = i == 0 ? this.const0 : this.const1;
		final int j = 1 - i;
		final AbstractConstraint cj = j == 0 ? this.const0 : this.const1;
		final AbstractConstraint oppcj =
			j == 0 ? this.oppositeConst0 : this.oppositeConst1;

		Assert.assertTrue(this.getTargetStatus(j) == null);
		final Boolean Bi = ci.isEntailed();
		if (Bi != null) {
			final boolean bi = Bi.booleanValue();
			this.setStatus(i, bi);
			this.setTargetStatus(j, bi);
			final Boolean Bj = this.getStatus(j);
			if (Bj != null) {
				if (Bj.booleanValue() != bi) {
					this.fail();
				}
			}
			else if (bi) {
				cj.awake();
			}
			else {
				oppcj.awake();
			}
		}
	}

	public boolean isConsistent() {
		throw new UnsupportedOperationException();
	}

	public Boolean isEntailed() {
		Boolean B0 = this.getStatus(0);
		if (B0 == null) {
			B0 = this.const0.isEntailed();
			if (B0 != null) {
				this.setStatus(0, B0.booleanValue());
			}
		}
		Boolean B1 = this.getStatus(1);
		if (B1 == null) {
			B1 = this.const1.isEntailed();
			if (B1 != null) {
				this.setStatus(1, B1.booleanValue());
			}
		}
		if (B0 == Boolean.TRUE) {
			return B1;
		}
		else if (B1 == Boolean.TRUE) {
			return B0;
		}
		else if (B0 == Boolean.FALSE && B1 == Boolean.FALSE) {
			return Boolean.TRUE;
		}
		else {
			return null;
		}
	}

	public boolean isSatisfied() {
		return this.const0.isSatisfied() == this.const1.isSatisfied();
	}

	// v1.05 when the target status is known, no need to call awake, propagate is enough
	// (initial propagation was already done when the target status was settled)
	public void propagate() throws ContradictionException {
		final Boolean B0 = this.getTargetStatus(0);
		if (B0 == Boolean.TRUE) {
			this.const0.propagate();
		}
		else if (B0 == Boolean.FALSE) {
			this.oppositeConst0.propagate();
		}
		else {// B0 == null
			if (this.getStatus(1) == null) {
				this.checkStatus(1);
			}
		}
		final Boolean B1 = this.getTargetStatus(1);
		if (B1 == Boolean.TRUE) {
			this.const1.propagate();
		}
		else if (B1 == Boolean.FALSE) {
			this.oppositeConst1.propagate();
		}
		else {// B1 == null
			if (this.getStatus(0) == null) {
				this.checkStatus(0);
			}
		}
	}
}
