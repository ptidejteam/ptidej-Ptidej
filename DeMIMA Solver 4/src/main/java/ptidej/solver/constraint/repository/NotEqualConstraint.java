/*******************************************************************************
 * Copyright (c) 2001-2014 Yann-Gaël Guéhéneuc and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/old-licenses/gpl-2.0.html
 * 
 * Contributors:
 *     Yann-Gaël Guéhéneuc and others, see in file; API and its implementation
 ******************************************************************************/
package ptidej.solver.constraint.repository;

import ptidej.solver.Constraint;
import ptidej.solver.Problem;
import ptidej.solver.Variable;
import ptidej.solver.approximation.DefaultNoApproximations;
import ptidej.solver.approximation.IApproximations;
import choco.ContradictionException;
import choco.integer.var.IntDomain;
import choco.palm.explain.PalmConstraintPlugin;
import choco.palm.integer.PalmIntDomain;
import choco.palm.integer.constraints.PalmNotEqualXYC;
import choco.util.IntIterator;

/**
 * Writen in CLAIRE by
 * @author Yann-Gaël Guéhéneuc
 * Translated and adapted from CLAIRE version to JAVA by
 * @author Iyadh Sidhom
 * @author Salim Bensemmane
 * @author Fayçal Skhiri
 */
public class NotEqualConstraint
	extends PalmNotEqualXYC
	implements Constraint {
	private String command;

	private String name;

	public NotEqualConstraint(
		final String name,
		final String command,
		final Variable v0,
		final Variable v1,
		final int weight,
		final IApproximations approximations) {

		super(v0, v1, 0);
		this.name = name;
		this.command = command;
		this.hook = new PalmConstraintPlugin(this);
		((PalmConstraintPlugin) this.hook).setWeight(weight);
	}
	public void awakeOnRem(int idx, int index_e) {
		this.propagate();
	}
	public void awakeOnRestoreVal(int idx, int val) {
		this.propagate();
	}
	public void doAwake() throws ContradictionException {
		this.propagate();
	}
	public IApproximations getApproximations() {
		return DefaultNoApproximations.getDefaultApproximations();
	}
	public int getConstant() {
		return this.cste;
	}
	public String getFieldName() {
		return null;
	}
	public String getName() {
		return this.name;
	}
	public String getNextConstraint() {
		return null;
	}
	public Class getNextConstraintConstructor(String nextConstraint) {
		return null;
	}
	public String getSymbol() {
		return " <> ";
	}
	public int getWeight() {
		return 0;
	}
	public String getXCommand() {
		return this.command;
	}
	public void propagate() {
		// I tried to have NotEqual on non-enumerated variables...
		// didn't quite work it out yet!
		//	final IntDomain domV0 = v0.getDomain();
		//	final IntDomain domV1 = v1.getDomain();
		//
		//	final IntIterator iterator0 = domV0.getIterator();
		//	while (iterator0.hasNext()) {
		//		final int index = iterator0.next();
		//		if (domV1.contains(index)) {
		//			choco.palm.explain.Explanation expl =
		//				((Problem) this.getProblem()).makeExplanation();
		//			((PalmConstraintPlugin) this.getPlugIn()).self_explain(expl);
		//			((Variable) this.v1).self_explain(
		//				PalmIntDomain.VAL,
		//				index,
		//				expl);
		//			((Variable) v0).removeVal(index, this.cIdx0, expl);
		//		}
		//	}
		//
		//	final IntIterator iterator1 = domV1.getIterator();
		//	while (iterator1.hasNext()) {
		//		final int index = iterator1.next();
		//		if (domV0.contains(index)) {
		//			choco.palm.explain.Explanation expl =
		//				((Problem) this.getProblem()).makeExplanation();
		//			((PalmConstraintPlugin) this.getPlugIn()).self_explain(expl);
		//			((Variable) this.v0).self_explain(
		//				PalmIntDomain.VAL,
		//				index,
		//				expl);
		//			((Variable) v1).removeVal(index, this.cIdx1, expl);
		//		}
		//	}

		if (this.v0.getDomainSize() == 1) {
			int index = 0;
			IntIterator iterator0 = this.v0.getDomain().getIterator();
			if (iterator0.hasNext()) {
				index = iterator0.next();
			}
			boolean areDifferent = true;
			IntDomain domV1 = this.v1.getDomain();
			if (domV1.contains(index)) {
				areDifferent = false;
			}
			if (!areDifferent) {
				choco.palm.explain.Explanation expl =
					((Problem) this.getProblem()).makeExplanation();
				((PalmConstraintPlugin) this.getPlugIn()).self_explain(expl);
				((Variable) this.v0).self_explain(PalmIntDomain.DOM, expl);
				((Variable) this.v1).removeVal(index, this.cIdx1, expl);
			}
		}

		if (this.v1.getDomainSize() == 1) {
			int index = 0;
			IntIterator iterator1 = this.v1.getDomain().getIterator();
			if (iterator1.hasNext()) {
				index = iterator1.next();
			}
			boolean areDifferent = true;
			IntDomain domV0 = this.v0.getDomain();
			if (domV0.contains(index)) {
				areDifferent = false;
			}
			if (!areDifferent) {
				choco.palm.explain.Explanation expl =
					((Problem) this.getProblem()).makeExplanation();
				((PalmConstraintPlugin) this.getPlugIn()).self_explain(expl);
				((Variable) this.v1).self_explain(PalmIntDomain.DOM, expl);
				((Variable) this.v0).removeVal(index, this.cIdx0, expl);
			}
		}
	}
	public void setFieldName(String fieldName) {
	}
	public void setStrict(boolean flag) {
	}
	public String setSymbol(String symbol) {
		return null;
	}
}
