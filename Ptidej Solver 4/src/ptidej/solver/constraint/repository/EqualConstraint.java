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
import choco.palm.explain.PalmConstraintPlugin;
import choco.palm.integer.PalmIntDomain;
import choco.palm.integer.constraints.PalmEqualXYC;
import choco.util.IntIterator;

/**
 * Writen in CLAIRE by
 * @author Yann-Gaël Guéhéneuc
 * Translated and adapted from CLAIRE version to JAVA by
 * @author Iyadh Sidhom
 * @author Salim Bensemmane
 * @author Fayçal Skhiri
 */
public class EqualConstraint extends PalmEqualXYC implements Constraint {
	private String command = null;
	private String name = null;

	public EqualConstraint(
		final String name,
		final String commande,
		final Variable v0,
		final Variable v1,
		final int weight,
		final IApproximations approximations) {

		super(v0, v1, 0);
		this.name = name;
		this.command = commande;
		this.hook = new PalmConstraintPlugin(this);
		((PalmConstraintPlugin) this.hook).setWeight(weight);
		// TODO: Throw and exception if either one of the variable is not enumerated!
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
		return " == ";
	}
	public int getWeight() {
		return 0;
	}
	public String getXCommand() {
		return this.command;
	}
	public void propagate() {
		if (this.v0.getDomainSize() == 1) {
			final IntIterator iterator0 = this.v0.getDomain().getIterator();
			final int index0 = iterator0.next();

			final IntIterator iterator1 = this.v1.getDomain().getIterator();
			while (iterator1.hasNext()) {
				int index1 = iterator1.next();
				if (index0 != index1) {
					choco.palm.explain.Explanation expl =
						((Problem) this.getProblem()).makeExplanation();
					((PalmConstraintPlugin) this.getPlugIn()).self_explain(
						expl);
					((Variable) this.v0).self_explain(
						PalmIntDomain.DOM,
						expl);
					((Variable) this.v1).removeVal(index1, this.cIdx1, expl);
				}
			}
		}

		if (this.v1.getDomainSize() == 1) {
			final IntIterator iterator1 = this.v1.getDomain().getIterator();
			final int index1 = iterator1.next();

			final IntIterator iterator0 = this.v0.getDomain().getIterator();
			while (iterator0.hasNext()) {
				int index0 = iterator0.next();
				if (index1 != index0) {
					choco.palm.explain.Explanation expl =
						((Problem) this.getProblem()).makeExplanation();
					((PalmConstraintPlugin) this.getPlugIn()).self_explain(
						expl);
					((Variable) this.v1).self_explain(
						PalmIntDomain.DOM,
						expl);
					((Variable) this.v0).removeVal(index0, this.cIdx1, expl);
				}
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
