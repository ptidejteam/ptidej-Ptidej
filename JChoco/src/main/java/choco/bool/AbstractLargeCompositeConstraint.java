// **************************************************
// *                   J-CHOCO                      *
// *   Copyright (C) F. Laburthe, 1999-2003         *
// **************************************************
// *  an open-source Constraint Programming Kernel  *
// *     for Research and Education                 *
// **************************************************

/* File choco.bool.AbstractLargeCompositeConstraint.java, last modified by FLABURTHE 17 mars 2004 */
package choco.bool;

import org.junit.Assert;
import choco.AbstractConstraint;
import choco.Var;
import choco.integer.IntVar;
import choco.integer.var.IntDomainVar;

public abstract class AbstractLargeCompositeConstraint extends
		AbstractCompositeConstraint {

	/**
	 * all subconstraints from the composition
	 */
	protected AbstractConstraint[] constraints;

	/**
	 * offsets[i] is the total number of variables in all previous terms constraints[0]...constraints[i-1]
	 * Therefore it is the offset in the variable indexing of constraints[i]
	 */
	protected int[] offsets;

	/**
	 * the number of sub-constraints
	 */
	protected int nbConstraints;

	/**
	 * variables in the composition that are not in the subconstraints
	 */
	protected IntDomainVar[] additionalVars;

	/**
	 * constraint indices (for the constraint network management) corresponding to the additional variables
	 */
	protected int[] additionalIndices;

	public AbstractLargeCompositeConstraint() {
		this.nbConstraints = this.constraints.length;
		int nbVars = 0;
		this.offsets = new int[this.nbConstraints];
		for (int i = 0; i < this.nbConstraints; i++) {
			nbVars += this.constraints[i].getNbVars();
			this.offsets[i] = nbVars;
		}
		this.additionalIndices = new int[this.additionalVars.length];
		for (int j = 0; j < this.additionalVars.length; j++) {
			this.additionalIndices[j] = 0;
		}
		Assert.assertTrue(nbVars + this.additionalVars.length == this.getNbVars());
	}

	public int getConstraintIdx(final int i) {
		final int constIdx = this.getSubConstraintIdx(i);
		final int varIdx = constIdx == 0 ? i : i - this.offsets[constIdx - 1];
		return this.constraints[constIdx].getConstraintIdx(varIdx);
	}

	public IntVar getIntVar(final int varIdx) {
		return (IntVar) this.getVar(varIdx);
	}

	protected int getLocalVarIndex(final int varIdx, final int constIdx) {
		return constIdx == 0 ? varIdx : varIdx - this.offsets[constIdx - 1];
	}

	public int getNbVars() {
		return this.offsets[this.nbConstraints - 1]
				+ this.additionalVars.length;
	}

	public int getNbVarsFromSubConst() {
		return this.offsets[this.offsets.length - 1];
	}

	public int getSubConstraintIdx(final int varIdx) {
		for (int constIdx = 0; constIdx < this.nbConstraints; constIdx++) {
			if (this.offsets[constIdx] >= varIdx) {
				return constIdx;
			}
		}
		return -1;
	}

	public Var getVar(final int i) {
		final int constIdx = this.getSubConstraintIdx(i);
		final int varIdx = constIdx == 0 ? i : i - this.offsets[constIdx - 1];
		return this.constraints[constIdx].getVar(varIdx);
	}

	public int getVarIdxInOpposite(final int i) {
		final int constIdx = this.getSubConstraintIdx(i);
		if (constIdx == 0) {
			return this.constraints[0].getVarIdxInOpposite(i);
		}
		else {
			Assert.assertTrue(constIdx < this.constraints.length);
			final int off = this.offsets[constIdx - 1];
			return this.constraints[constIdx].getVarIdxInOpposite(i - off)
					+ off;
		}
	}

	public boolean isCompletelyInstantiated() {
		boolean instantiated = true;
		for (int constIdx = 0; constIdx < this.nbConstraints; constIdx++) {
			if (!this.constraints[constIdx].isCompletelyInstantiated()) {
				instantiated = false;
				break;
			}
		}
		for (int varIdx = 0; varIdx < this.additionalVars.length; varIdx++) {
			if (!this.additionalVars[varIdx].isInstantiated()) {
				instantiated = false;
				break;
			}
		}
		return instantiated;
	}

	public void setConstraintIndex(final int i, final int idx) {
		final int constIdx = this.getSubConstraintIdx(i);
		final int varIdx = constIdx == 0 ? i : i - this.offsets[constIdx - 1];
		this.constraints[constIdx].setConstraintIndex(varIdx, idx);
	}

}
