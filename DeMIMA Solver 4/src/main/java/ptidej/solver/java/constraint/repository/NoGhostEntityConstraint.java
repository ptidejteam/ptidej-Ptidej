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

import ptidej.solver.java.Variable;
import ptidej.solver.java.approximation.IApproximations;
import ptidej.solver.java.constraint.UnaryConstraint;
import ptidej.solver.java.domain.Entity;

/**
 * @author Yann-Gaël Guéhéneuc
 * @since  2007/02/26
 */
public class NoGhostEntityConstraint extends UnaryConstraint {

	public NoGhostEntityConstraint(
			final String name,
			final String command,
			final Variable v0,
			final int weight,
			final IApproximations approximations) {

		super(name, command, v0, weight, approximations);
	}

	@Override
	protected boolean getPropagateCondition(Entity entity) {
		return entity.isGhost();
	}

}