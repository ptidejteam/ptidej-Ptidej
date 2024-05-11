package choco.bool;

import org.junit.Assert;
import choco.AbstractConstraint;
import choco.ContradictionException;
import choco.integer.constraints.AbstractIntConstraint;
import choco.util.IntIterator;

// **************************************************
// *                   J-CHOCO                      *
// *   Copyright (C) F. Laburthe, 1999-2004         *
// **************************************************
// *  an open-source Constraint Programming Kernel  *
// *     for Research and Education                 *
// **************************************************

/* FileGuard.java, last modified by Franeois, 4 avr. 2004 */

/**
 * A class for implications (if (c1,c2)) propagated in a lazy way (nothing done until c1 proven true)
 */
public class Guard extends AbstractBinBoolConstraint {

	public Guard(final AbstractConstraint c1, final AbstractConstraint c2) {
		super(c1, c2);
	}

	public void awakeOnBounds(final int varIndex) throws ContradictionException {
		if (varIndex < this.offset) {
			this.checkStatus(0);
		}
		else if (this.getTargetStatus(1) != null) {
			Assert.assertTrue(this.getTargetStatus(1) == Boolean.TRUE);
			((AbstractIntConstraint) this.const1).awakeOnBounds(varIndex
					- this.offset);
		}
	}

	public void awakeOnInf(final int idx) throws ContradictionException {
		if (idx < this.offset) {
			this.checkStatus(0);
		}
		else if (this.getTargetStatus(1) != null) {
			Assert.assertTrue(this.getTargetStatus(1) == Boolean.TRUE);
			((AbstractIntConstraint) this.const1).awakeOnInf(idx - this.offset);
		}
	}

	public void awakeOnInst(final int idx) throws ContradictionException {
		if (idx < this.offset) {
			this.checkStatus(0);
		}
		else if (this.getTargetStatus(1) != null) {
			Assert.assertTrue(this.getTargetStatus(1) == Boolean.TRUE);
			((AbstractIntConstraint) this.const1)
				.awakeOnInst(idx - this.offset);
		}
	}

	public void awakeOnRem(final int idx, final int x)
			throws ContradictionException {
		if (idx < this.offset) {
			this.checkStatus(0);
		}
		else if (this.getTargetStatus(1) != null) {
			Assert.assertTrue(this.getTargetStatus(1) == Boolean.TRUE);
			((AbstractIntConstraint) this.const1).awakeOnRem(
				idx - this.offset,
				x);
		}
	}

	public void awakeOnRemovals(final int idx, final IntIterator deltaDomain)
			throws ContradictionException {
		if (idx < this.offset) {
			this.checkStatus(0);
		}
		else if (this.getTargetStatus(1) != null) {
			Assert.assertTrue(this.getTargetStatus(1) == Boolean.TRUE);
			((AbstractIntConstraint) this.const1).awakeOnRemovals(idx
					- this.offset, deltaDomain);
		}
	}

	public void awakeOnSup(final int idx) throws ContradictionException {
		if (idx < this.offset) {
			this.checkStatus(0);
		}
		else if (this.getTargetStatus(1) != null) {
			Assert.assertTrue(this.getTargetStatus(1) == Boolean.TRUE);
			((AbstractIntConstraint) this.const1).awakeOnSup(idx - this.offset);
		}
	}

	// Note: checkStatus is always called on a sub-constraint that we want to propagate
	//    checkStatus asks for entailment, updates the status field, and,
	//    when necessary, propagates the subconstraint through doAwake
	//
	protected void checkStatus(final int i) throws ContradictionException {
		Assert.assertTrue(i == 0);
		Assert.assertTrue(this.getTargetStatus(0) == null);
		if (this.getStatus(0) == null) {
			final Boolean b = this.const0.isEntailed();
			if (b != null) {
				this.setStatus(1, b.booleanValue());
				if (b.booleanValue() && this.getTargetStatus(1) != null) {
					this.setTargetStatus(1, true);
					this.const1.awake();
				}
			}
		}
	}

	public boolean isConsistent() {
		throw new UnsupportedOperationException();
	}

	public Boolean isEntailed() {
		final Boolean B0 = this.getStatus(0);
		Boolean B1 = this.getStatus(1);
		if (B0 != null) {
			if (B0.booleanValue()) {
				// case of an implication with a true condition
				Assert.assertTrue(this.getTargetStatus(1) == Boolean.TRUE);
				if (B1 == null) {
					B1 = this.const1.isEntailed();
					if (B1 != null) {
						final boolean b1 = B1.booleanValue();
						this.setStatus(1, b1);
					}
				}
				return B1;
			}
			else {
				// case of an implication with false condition : it is alays true
				return Boolean.TRUE;
			}
			// case of an implication with an unsettled condition (may be true, may be false)
		}
		else {
			Assert.assertTrue(this.getTargetStatus(1) == null);
			// if the conclusion is true, the the constraint is defintely satisfied, otherwise, we do not know
			if (B1 == null) {
				B1 = this.const1.isEntailed();
				if (B1 != null) {
					final boolean b1 = B1.booleanValue();
					this.setStatus(1, b1);
				}
			}
			return B1 == Boolean.TRUE ? Boolean.TRUE : null;
		}
	}

	public boolean isSatisfied() {
		return !this.const0.isSatisfied() || this.const1.isSatisfied();
	}

	public void propagate() throws ContradictionException {
		if (this.getTargetStatus(1) != null) {
			Assert.assertTrue(this.getTargetStatus(1) == Boolean.TRUE);
			this.const1.propagate();
		}
		else {
			this.checkStatus(0);
		}
	}
}
