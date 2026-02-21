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
package ptidej.solver.java.constraint;

import java.util.List;
import java.util.Set;

import choco.ContradictionException;
import choco.palm.explain.PalmConstraintPlugin;
import choco.util.IntIterator;
import choco.palm.integer.AbstractPalmUnIntConstraint;
import ptidej.solver.java.Constraint;
import ptidej.solver.java.Variable;
import ptidej.solver.java.approximation.IApproximations;
import ptidej.solver.java.Problem;
import ptidej.solver.java.domain.Entity;

/**
 * @author Yann-Gaël Guéhéneuc
 * @since  2014/03/31
 */
public abstract class UnaryConstraint extends AbstractPalmUnIntConstraint
		implements Constraint {

	private final IApproximations approximations;
	private final String command;
	private final String name;

	public UnaryConstraint(
			final String name,
			final String command,
			final Variable v0,
			final int weight,
			final IApproximations approximations) {

		this.name = name;
		this.command = command;
		this.v0 = v0;
		this.approximations = approximations;
		this.hook = new PalmConstraintPlugin(this);
		((PalmConstraintPlugin) this.hook).setWeight(weight);
	}

	@Override
	public void awakeOnRem(int idx, int index_e) {
		propagate();
	}

	@Override
	public void awakeOnRestoreVal(int idx, int val) throws ContradictionException {
		propagate();
	}

	@Override
	public void doAwake() throws ContradictionException {
		propagate();
	}

	public String getFieldName() {
		return null;
	}

	@Override
	public String getNextConstraint() {
		return null;
	}

	@Override
	public Class getNextConstraintConstructor(String nextConstraint) {
		return null;
	}

	@Override
	public String getSymbol() {
		return "!?";
	}

	@Override
	public int getWeight() {
		return 100;
	}

	@Override
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

					if (getPropagateCondition(e0)) {
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

	protected abstract boolean getPropagateCondition(Entity entity);

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

	public final IApproximations getApproximations() {
		return this.approximations;
	}

	public final String getName() {
		return this.name;
	}

	public final String getXCommand() {
		return this.command;
	}

}