// **************************************************
// *                   J-CHOCO                      *
// *   Copyright (C) F. Laburthe, 1999-2003         *
// **************************************************
// *  an open-source Constraint Programming Kernel  *
// *     for Research and Education                 *
// **************************************************

/* File choco.bool.AbstractBinCompositeConstraint.java, last modified by FLABURTHE 17 mars 2004 */
package choco.bool;

import org.junit.Assert;
import choco.AbstractConstraint;
import choco.Var;
import choco.integer.IntVar;

public abstract class AbstractBinCompositeConstraint extends
		AbstractCompositeConstraint {
	/**
	 * the first sub-constraint of the composition
	 */
	protected AbstractConstraint const0;
	/**
	 * the second sub-constraint of the composition
	 */
	protected AbstractConstraint const1;

	/**
	 * the number of variables in the first sub-constraint
	 * (therefore the offset in the numbering of the variables from the second sub-constraint)
	 */
	protected int offset = 0;

	public AbstractBinCompositeConstraint(
		final AbstractConstraint c1,
		final AbstractConstraint c2) {
		this.const0 = c1;
		this.const1 = c2;
		this.offset = this.const0.getNbVars();
	}

	public int getConstraintIdx(final int i) {
		return i < this.offset ? this.const0.getConstraintIdx(i) : this.const1
			.getConstraintIdx(i - this.offset);
	}

	/**
	 * <i>Network management:</i>
	 * Accessing the i-th search variable of a constraint.
	 *
	 * @param varIdx index of the variable among all search variables in the constraint. Numbering start from 0 on.
	 * @return the variable, or null when no such variable is found
	 */

	public IntVar getIntVar(final int varIdx) {
		return (IntVar) this.getVar(varIdx);
	}

	public int getNbVars() {
		return this.const0.getNbVars() + this.const1.getNbVars();
	}

	public int getSubConstraintIdx(final int varIdx) {
		if (varIdx < this.offset) {
			return 0;
		}
		else {
			return 1;
		}
	}

	public Var getVar(final int i) {
		return i < this.offset ? this.const0.getVar(i) : this.const1.getVar(i
				- this.offset);
	}

	public int getVarIdxInOpposite(final int i) {
		final int constIdx = this.getSubConstraintIdx(i);
		if (constIdx == 0) {
			return this.const0.getVarIdxInOpposite(i);
		}
		else {
			Assert.assertTrue(constIdx == 1);
			return this.const1.getVarIdxInOpposite(i - this.offset)
					+ this.offset;
		}
	}

	public boolean isCompletelyInstantiated() {
		return this.const0.isCompletelyInstantiated()
				&& this.const1.isCompletelyInstantiated();
	}

	public void setConstraintIndex(final int i, final int idx) {
		if (i < this.offset) {
			this.const0.setConstraintIndex(i, idx);
		}
		else {
			this.const1.setConstraintIndex(i, idx - this.offset);
		}
	}

}
