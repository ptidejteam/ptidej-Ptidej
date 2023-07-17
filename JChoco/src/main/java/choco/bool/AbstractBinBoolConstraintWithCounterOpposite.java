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

/* File choco.bool.AbstractBinBoolConstraintWithCounterOpposite.java, last modified by flaburthe 30 mars 2004 15:05:57 */

/**
 * An abstract class storing the counterpart of each subconstraint + an index correspondence for variables
 * (between subconstraints and opposite subconstraints)
 */
public abstract class AbstractBinBoolConstraintWithCounterOpposite extends
		AbstractBinBoolConstraint {
	/**
	 * the counterpart of the first subconstraint
	 */
	AbstractConstraint oppositeConst0;

	/**
	 * the counterpart of the second subconstraint
	 */
	AbstractConstraint oppositeConst1;

	/**
	 * the index correspondance for the variables of the first subconstraint (with its counterpart)
	 */
	int[] indicesInOpposite0;

	/**
	 * the index correspondance for the variables of the first subconstraint (with its counterpart)
	 */
	int[] indicesInOpposite1;

	public AbstractBinBoolConstraintWithCounterOpposite(
		final AbstractConstraint c1,
		final AbstractConstraint c2) {
		super(c1, c2);
		this.oppositeConst0 = this.const0.opposite();
		this.oppositeConst1 = this.const1.opposite();
		Assert.assertTrue(this.const0.getNbVars() == this.oppositeConst0.getNbVars());
		Assert.assertTrue(this.const1.getNbVars() == this.oppositeConst1.getNbVars());
		this.indicesInOpposite0 = new int[this.const0.getNbVars()];
		for (int k = 0; k < this.const0.getNbVars(); k++) {
			this.indicesInOpposite0[k] = this.const0.getVarIdxInOpposite(k);
		}
		this.indicesInOpposite1 = new int[this.const1.getNbVars()];
		for (int k = 0; k < this.const1.getNbVars(); k++) {
			this.indicesInOpposite1[k] = this.const1.getVarIdxInOpposite(k);
		}
	}

	public int assignIndices(final AbstractCompositeConstraint root, final int i) {
		int j = i;
		j = this.const0.assignIndices(root, j);
		this.offset = j - i;
		for (int k = 0; k < this.offset; k++) {
			this.oppositeConst0.setConstraintIndex(
				this.indicesInOpposite0[k],
				this.const0.getConstraintIdx(k));
		}
		j = this.const1.assignIndices(root, j);
		for (int k = 0; k < this.offset; k++) {
			this.oppositeConst1.setConstraintIndex(
				this.indicesInOpposite1[k],
				this.const1.getConstraintIdx(k));
		}
		return j;
	}
}
