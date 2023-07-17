package choco.bool;

import org.junit.Assert;
import choco.AbstractConstraint;
import choco.ContradictionException;
import choco.integer.IntConstraint;
import choco.util.IntIterator;

// **************************************************
// *                   J-CHOCO                      *
// *   Copyright (C) F. Laburthe, 1999-2003         *
// **************************************************
// *  an open-source Constraint Programming Kernel  *
// *     for Research and Education                 *
// **************************************************

/* File choco.bool.BinDisjunction.java, last modified by flaburthe 31 mars 2004 14:18:56 */

public class BinDisjunction extends AbstractBinBoolConstraint {

	public BinDisjunction(
		final AbstractConstraint c1,
		final AbstractConstraint c2) {
		super(c1, c2);
	}

	public void awakeOnBounds(final int i) throws ContradictionException {
		if (i < this.offset) {
			if (this.getStatus(0) == null) {
				if (this.getTargetStatus(0) == Boolean.TRUE) {
					((IntConstraint) this.const0).awakeOnBounds(i);
				}
				else {
					// if the target status is not TRUE, it must be null (never FALSE for disjunctions).
					this.checkStatus(0);
				}
			}
		}
		else if (this.getStatus(1) == null) {
			if (this.getTargetStatus(1) == Boolean.TRUE) {
				((IntConstraint) this.const1).awakeOnBounds(i - this.offset);
			}
			else {
				// if the target status is not TRUE, it must be null (never FALSE for disjunctions).
				this.checkStatus(1);
			}
		}
	}

	/**
	 * Default propagation on improved lower bound: propagation on domain revision.
	 */

	public void awakeOnInf(final int i) throws ContradictionException {
		if (i < this.offset) {
			if (this.getStatus(0) == null) {
				if (this.getTargetStatus(0) == Boolean.TRUE) {
					((IntConstraint) this.const0).awakeOnInf(i);
				}
				else {
					// if the target status is not TRUE, it must be null (never FALSE for disjunctions).
					this.checkStatus(0);
				}
			}
		}
		else if (this.getStatus(1) == null) {
			if (this.getTargetStatus(1) == Boolean.TRUE) {
				((IntConstraint) this.const1).awakeOnInf(i - this.offset);
			}
			else {
				// if the target status is not TRUE, it must be null (never FALSE for disjunctions).
				this.checkStatus(1);
			}
		}
	}

	public void awakeOnInst(final int i) throws ContradictionException {
		if (i < this.offset) {
			if (this.getStatus(0) == null) {
				if (this.getTargetStatus(0) == Boolean.TRUE) {
					((IntConstraint) this.const0).awakeOnInst(i);
				}
				else {
					// if the target status is not TRUE, it must be null (never FALSE for disjunctions).
					this.checkStatus(0);
				}
			}
		}
		else if (this.getStatus(1) == null) {
			if (this.getTargetStatus(1) == Boolean.TRUE) {
				((IntConstraint) this.const1).awakeOnInst(i - this.offset);
			}
			else {
				// if the target status is not TRUE, it must be null (never FALSE for disjunctions).
				this.checkStatus(1);
			}
		}
	}

	public void awakeOnRem(final int i, final int x)
			throws ContradictionException {
		if (i < this.offset) {
			if (this.getStatus(0) == null) {
				if (this.getTargetStatus(0) == Boolean.TRUE) {
					((IntConstraint) this.const0).awakeOnRem(i, x);
				}
				else {
					// if the target status is not TRUE, it must be null (never FALSE for disjunctions).
					this.checkStatus(0);
				}
			}
		}
		else if (this.getStatus(1) == null) {
			if (this.getTargetStatus(1) == Boolean.TRUE) {
				((IntConstraint) this.const1).awakeOnRem(i - this.offset, x);
			}
			else {
				// if the target status is not TRUE, it must be null (never FALSE for disjunctions).
				this.checkStatus(1);
			}
		}
	}

	public void awakeOnRemovals(final int i, final IntIterator deltaDomain)
			throws ContradictionException {
		if (i < this.offset) {
			if (this.getStatus(0) == null) {
				if (this.getTargetStatus(0) == Boolean.TRUE) {
					((IntConstraint) this.const0).awakeOnRemovals(
						i,
						deltaDomain);
				}
				else {
					// if the target status is not TRUE, it must be null (never FALSE for disjunctions).
					this.checkStatus(0);
				}
			}
		}
		else if (this.getStatus(1) == null) {
			if (this.getTargetStatus(1) == Boolean.TRUE) {
				((IntConstraint) this.const1).awakeOnRemovals(
					i - this.offset,
					deltaDomain);
			}
			else {
				// if the target status is not TRUE, it must be null (never FALSE for disjunctions).
				this.checkStatus(1);
			}
		}
	}

	public void awakeOnSup(final int i) throws ContradictionException {
		if (i < this.offset) {
			if (this.getStatus(0) == null) {
				if (this.getTargetStatus(0) == Boolean.TRUE) {
					((IntConstraint) this.const0).awakeOnSup(i);
				}
				else {
					// if the target status is not TRUE, it must be null (never FALSE for disjunctions).
					this.checkStatus(0);
				}
			}
		}
		else if (this.getStatus(1) == null) {
			if (this.getTargetStatus(1) == Boolean.TRUE) {
				((IntConstraint) this.const1).awakeOnSup(i - this.offset);
			}
			else {
				// if the target status is not TRUE, it must be null (never FALSE for disjunctions).
				this.checkStatus(1);
			}
		}
	}

	// this function is always called on a constraint that we want to propagate (either a root constraint
	// or a subconstraint that now needs to be propagated, eg such that targetStatus=true)
	protected void checkStatus(final int i) throws ContradictionException {
		Assert.assertTrue(this.getStatus(i) == null);
		final AbstractConstraint c0 = i == 0 ? this.const0 : this.const1;
		final Boolean b = c0.isEntailed();
		final Boolean tgtb = this.getTargetStatus(i);
		if (b != null) {
			if (tgtb != null) {
				if (b != tgtb) {
					this.fail();
				}
			}
			this.setStatus(i, b.booleanValue());
			// note (v1.02): below, we may already have targetStatus2=true without having status1=false
			//   (for instance if the disjunction has been settled within a choice point)
			//    therefore we explicitly check that the targetStatus is not already known before setting it to true
			if (b == Boolean.FALSE && this.getTargetStatus(1 - i) == null) {
				this.setTargetStatus(1 - i, true);
				final AbstractConstraint c1 =
					i == 0 ? this.const1 : this.const0;
				c1.awake();
			}
		}
	}

	public boolean isConsistent() {
		throw new UnsupportedOperationException();
	}

	public Boolean isEntailed() {
		Boolean st0 = this.getStatus(0);
		if (st0 == null) {
			st0 = this.const0.isEntailed();
			if (st0 != null) {
				this.setStatus(0, st0.booleanValue());
			}
		}
		Boolean st1 = this.getStatus(1);
		if (st1 == null) {
			st1 = this.const1.isEntailed();
			if (st1 != null) {
				this.setStatus(1, st1.booleanValue());
			}
		}
		if (st0 == Boolean.TRUE) {
			return Boolean.TRUE;
		}
		else if (st1 == Boolean.TRUE) {
			return Boolean.TRUE;
		}
		else if (st0 == Boolean.FALSE && st1 == Boolean.FALSE) {
			return Boolean.FALSE;
		}
		else {
			return null;
		}
	}

	public boolean isSatisfied() {
		Assert.assertTrue(this.getStatus(0) != null && this.getStatus(1) != null);
		return this.getStatus(0).booleanValue()
				|| this.getStatus(1).booleanValue();
		//    return ((const0.isSatisfied()) ||(const1.isSatisfied()));
	}

	public void propagate() throws ContradictionException {
		if (this.getStatus(0) == null) {
			this.checkStatus(0);
		}
		if (this.getStatus(1) == null) {
			this.checkStatus(1);
		}
	}

}
