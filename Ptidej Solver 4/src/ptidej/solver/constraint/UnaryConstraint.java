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
package ptidej.solver.constraint;

import java.util.Set;
import ptidej.solver.Constraint;
import ptidej.solver.Variable;
import ptidej.solver.approximation.IApproximations;
import choco.ContradictionException;
import choco.palm.explain.PalmConstraintPlugin;
import choco.palm.integer.AbstractPalmUnIntConstraint;

/**
 * @author Yann-Gaël Guéhéneuc
 * @since  2014/03/31
 */
// TODO Move into this abstract class all the common code among its subclasses!
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
	public abstract void awakeOnRem(int idx, int index_e);
	public abstract void awakeOnRestoreVal(int idx, int val)
			throws ContradictionException;
	public abstract void doAwake() throws ContradictionException;
	public final IApproximations getApproximations() {
		return this.approximations;
	}
	public final String getName() {
		return this.name;
	}
	public abstract String getNextConstraint();
	public abstract Class getNextConstraintConstructor(
		final String nextConstraint);
	public abstract String getSymbol();
	public abstract int getWeight();
	public final String getXCommand() {
		return this.command;
	}
	public abstract boolean isSatisfied();
	public abstract void propagate();
	protected abstract void setStrict(final boolean flag);
	protected abstract String setSymbol(final String symbol);
	public abstract Set whyIsFalse();
	public abstract Set whyIsTrue();
}
