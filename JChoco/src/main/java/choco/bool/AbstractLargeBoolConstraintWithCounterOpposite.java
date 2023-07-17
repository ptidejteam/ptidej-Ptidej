package choco.bool;

import org.junit.Assert;
import choco.AbstractConstraint;

// **************************************************
// *                   J-CHOCO                      *
// *   Copyright (C) F. Laburthe, 1999-2003         *
// **************************************************
// *  an open-source Constraint Programming Kernel  *
// *     for Research and Education                 *
// **************************************************

/* File choco.bool.AbstractLargeBoolConstraintWithCounterOpposite.java, last modified by flaburthe 30 mars 2004 15:34:29 */

/**
 * An abstract class storing the counterpart of each subconstraint + an index correspondence for variables
 * (between subconstraints and opposite subconstraints)
 */
public abstract class AbstractLargeBoolConstraintWithCounterOpposite extends
		AbstractLargeBoolConstraint {

	/**
	 * the counterpart of the first subconstraint
	 */
	AbstractConstraint[] opposites;

	/**
	 * the index correspondance for the variables of the subconstraints and their counterparts
	 */
	int[][] indicesInOpposites;

	public AbstractLargeBoolConstraintWithCounterOpposite() {
		super();
		this.opposites = new AbstractConstraint[this.constraints.length];
		for (int constIdx = 0; constIdx < this.constraints.length; constIdx++) {
			final AbstractConstraint subConst = this.constraints[constIdx];
			this.opposites[constIdx] = subConst.opposite();
			for (int varIdx = 0; varIdx < subConst.getNbVars(); varIdx++) {
				subConst.getVarIdxInOpposite(varIdx);
			}
		}
	}

	public int assignIndices(final AbstractCompositeConstraint root, final int i) {
		int j = i;
		for (int constIdx = 0; constIdx < this.nbConstraints; constIdx++) {
			final int j0 = j;
			final AbstractConstraint subc = this.constraints[constIdx];
			j = subc.assignIndices(root, j);
			Assert.assertTrue(this.offsets[constIdx] == j - i);
			for (int varIdx = 0; varIdx < j - j0; varIdx++) {
				this.opposites[constIdx].setConstraintIndex(
					this.indicesInOpposites[constIdx][varIdx],
					subc.getConstraintIdx(varIdx));
			}
		}
		Assert.assertTrue(this.additionalIndices.length == this.additionalVars.length);
		for (int varIdx = 0; varIdx < this.additionalVars.length; varIdx++) {
			j++;
			root.connectVar(this.additionalVars[varIdx], j);
			this.additionalIndices[varIdx] =
				this.additionalVars[varIdx].getNbConstraints();
		}
		return j;
	}

}