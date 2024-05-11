package choco.bool;

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

/* FileBinConjunction.java, last modified by Franeois, 4 avr. 2004 */
/**
 * A class for Conjunctions (only used in subterms of Boolean formulae (c1 and c2)
 */
// note v1.02: for conjunctions, targetStatus slots are useless -> we only use status fields
public class BinConjunction extends
		AbstractBinBoolConstraintWithCounterOpposite {

	public BinConjunction(
		final AbstractConstraint c1,
		final AbstractConstraint c2) {
		super(c1, c2);
	}

	public void awake() throws ContradictionException {
		this.const0.awake();
		this.const1.awake();
	}

	public void awakeOnBounds(final int varIndex) throws ContradictionException {
		if (varIndex < this.offset) {
			((IntConstraint) this.const0).awakeOnBounds(varIndex);
		}
	}

	public void awakeOnInf(final int idx) throws ContradictionException {
		if (idx < this.offset) {
			((IntConstraint) this.const0).awakeOnInf(idx);
		}
	}

	public void awakeOnInst(final int idx) throws ContradictionException {
		if (idx < this.offset) {
			((IntConstraint) this.const0).awakeOnInst(idx);
		}
	}

	public void awakeOnRem(final int idx, final int x)
			throws ContradictionException {
		if (idx < this.offset) {
			((IntConstraint) this.const0).awakeOnInst(idx);
		}
	}

	public void awakeOnRemovals(final int idx, final IntIterator deltaDomain)
			throws ContradictionException {
		if (idx < this.offset) {
			((IntConstraint) this.const0).awakeOnRemovals(idx, deltaDomain);
		}
	}

	public void awakeOnSup(final int idx) throws ContradictionException {
		if (idx < this.offset) {
			((IntConstraint) this.const0).awakeOnSup(idx);
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
		if (B0 == Boolean.TRUE && B1 == Boolean.TRUE) {
			return Boolean.TRUE;
		}
		else if (B0 == Boolean.FALSE || B1 == Boolean.FALSE) {
			return Boolean.FALSE;
		}
		else {
			return null;
		}
	}

	public boolean isSatisfied() {
		return this.const0.isSatisfied() && this.const1.isSatisfied();
	}

	public void propagate() throws ContradictionException {
		this.const0.propagate();
		this.const1.propagate();
	}

}
