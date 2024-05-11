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
package ptidej.solver.java.constraint.repository;

import java.util.List;
import java.util.Set;

import util.io.ProxyConsole;
import choco.ContradictionException;
import choco.palm.explain.PalmConstraintPlugin;
import choco.util.IntIterator;
import ptidej.solver.java.Problem;
import ptidej.solver.java.Variable;
import ptidej.solver.java.approximation.IApproximations;
import ptidej.solver.java.constraint.UnaryConstraint;
import ptidej.solver.java.domain.Entity;

/**
 * @author Yann-Gaël Guéhéneuc
 * @since  2007/02/27
 */
public class InterfaceEntityConstraint extends UnaryConstraint {

	public InterfaceEntityConstraint(
		final String name,
		final String command,
		final Variable v0,
		final int weight,
		final IApproximations approximations) {

		super(name, command, v0, weight, approximations);
	}
	public void awakeOnRem(int idx, int index_e) {
		this.propagate();
	}
	public void awakeOnRestoreVal(int idx, int val)
			throws ContradictionException {

		this.propagate();
	}
	public void doAwake() throws ContradictionException {
		this.propagate();
	}
	public String getFieldName() {
		return null;
	}
	public String getNextConstraint() {
		return "ptidej.solver.java.constraint.repository.NoAbstractEntityConstraint";
	}
	public Class getNextConstraintConstructor(final String nextConstraint) {
		try {
			return Class.forName(nextConstraint);
		}
		catch (ClassNotFoundException e) {
			e.printStackTrace(ProxyConsole.getInstance().errorOutput());
		}
		return null;
	}
	public String getSymbol() {
		return "!?";
	}
	public int getWeight() {
		return 100;
	}
	public boolean isSatisfied() {
		return false;
	}
	public void propagate() {
		final List allEntities = ((Problem) this.getProblem()).getAllEntities();

		// Yann 2004/10/16: Fingerprints
		// It is possible when defining domain using fingerprints
		// that the domain of a variable be empty. Then, I don't
		// propagate anything.
		if (this.v0.getDomain().getSize() > 0) {
			final IntIterator iterator0 = this.v0.getDomain().getIterator();
			while (iterator0.hasNext()) {
				final int index_e0 = iterator0.next();

				// Yann 2004/10/16: Fingerprints
				// It is possible when defining domain using fingerprints
				// that the domain of a variable be empty. This seems to
				// upset the combinatorial automatique solver which produces
				// temporary variable with INF and SUP equal to -1, which leads
				// an offset of -1 in the BitSetIntDomain which then returns -1.
				if (index_e0 > -1) {
					final Entity e0 = (Entity) allEntities.get(index_e0);

					if (!e0.isGhost() && !e0.isInterface()) {
						choco.palm.explain.Explanation expl =
							((Problem) this.getProblem()).makeExplanation();
						((PalmConstraintPlugin) this.getPlugIn())
							.self_explain(expl);
						((Variable) this.v0).removeVal(
							index_e0,
							this.cIdx0,
							expl);
					}
				}
			}
		}
	}
	public void setFieldName(final String fieldName) {
	}
	public void setStrict(final boolean flag) {
	}
	public String setSymbol(final String symbol) {
		return null;
	}
	public Set whyIsFalse() {
		return null;
	}
	public Set whyIsTrue() {
		return null;
	}
}
