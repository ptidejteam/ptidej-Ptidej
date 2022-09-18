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
package ptidej.solver.problem;

import java.util.List;

import ptidej.solver.Problem;
import ptidej.solver.Variable;
import ptidej.solver.approximation.DefaultNoApproximations;
import ptidej.solver.constraint.repository.CreationConstraint;
import ptidej.solver.constraint.repository.IgnoranceConstraint;

/**
 * @author Yann-Gaël Guéhéneuc
 * @since  2007/02/18 
 */
public final class SingletonMotif {
	public static Problem getProblem(final List allEntities) {
		final Problem pb =
			new Problem(90, "Singleton Design Motif", allEntities);

		final Variable singleton = new Variable(pb, "singleton", true);
		final Variable others = new Variable(pb, "others", false);

		pb.addVar(singleton);

		// Constraints
		final CreationConstraint c1 =
			new CreationConstraint(
				"Singleton -*--> Singleton",
				"",
				singleton,
				singleton,
				100,
				DefaultNoApproximations.getDefaultApproximations());

		final IgnoranceConstraint c2 =
			new IgnoranceConstraint(
				"Singleton -/--> Others",
				"",
				singleton,
				others,
				100,
				DefaultNoApproximations.getDefaultApproximations());

		pb.post(c1);
		pb.post(c2);

		return pb;
	}
}
