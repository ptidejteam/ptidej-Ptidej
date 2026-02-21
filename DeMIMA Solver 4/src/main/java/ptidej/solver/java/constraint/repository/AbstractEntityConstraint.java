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

import util.io.ProxyConsole;
import ptidej.solver.java.Variable;
import ptidej.solver.java.approximation.IApproximations;
import ptidej.solver.java.constraint.UnaryConstraint;
import ptidej.solver.java.domain.Entity;

/**
 * @author Yann-Gaël Guéhéneuc
 * @since  2007/02/27
 */
public class AbstractEntityConstraint extends UnaryConstraint {

	public AbstractEntityConstraint(
			final String name,
			final String command,
			final Variable v0,
			final int weight,
			final IApproximations approximations) {

		super(name, command, v0, weight, approximations);
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

	@Override
	protected boolean getPropagateCondition(Entity entity) {
		return !entity.isAbstract();
	}

}