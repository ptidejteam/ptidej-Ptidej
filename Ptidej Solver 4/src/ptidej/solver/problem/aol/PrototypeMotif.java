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
package ptidej.solver.problem.aol;

import java.util.List;

import ptidej.solver.Problem;
import ptidej.solver.Variable;
import ptidej.solver.approximation.DefaultNoApproximations;
import ptidej.solver.approximation.TSE07ExtensibleStrictInheritanceApproximations;
import ptidej.solver.constraint.repository.AggregationConstraint;
import ptidej.solver.constraint.repository.HasMethodConstraint;
import ptidej.solver.constraint.repository.IgnoranceConstraint;
import ptidej.solver.constraint.repository.NoGhostEntityConstraint;
import ptidej.solver.constraint.repository.StrictInheritanceConstraint;

/**
 * @author Yann-Gaël Guéhéneuc
 * @since  2007/02/18 
 */
public final class PrototypeMotif {
	public static Problem getProblem(final List allEntities) {
		final Problem pb =
			new Problem(90, "Singleton Design Motif", allEntities);

		final Variable client = new Variable(pb, "client", false);
		final Variable prototype = new Variable(pb, "prototype", true);
		final Variable concreteprototype =
			new Variable(pb, "concreteprototype", false);

		pb.addVar(client);
		pb.addVar(prototype);
		pb.addVar(concreteprototype);

		// Constraints
		pb.post(
			new AggregationConstraint(
				"Client <>--> Prototype",
				"",
				client,
				prototype,
				100,
				DefaultNoApproximations.getDefaultApproximations()));
		pb.post(
			new HasMethodConstraint(
				"Prototype",
				"",
				prototype,
				"clone",
				100,
				DefaultNoApproximations.getDefaultApproximations()));
		pb.post(
			new NoGhostEntityConstraint(
				"Prototype",
				"",
				prototype,
				100,
				DefaultNoApproximations.getDefaultApproximations()));

		pb.post(
			new StrictInheritanceConstraint(
				"ConcretePrototype -|>- Prototype",
				"",
				concreteprototype,
				prototype,
				50,
				TSE07ExtensibleStrictInheritanceApproximations
					.getDefaultApproximations()));

		pb.post(
			new IgnoranceConstraint(
				"Prototype -/--> Client",
				"",
				prototype,
				client,
				100,
				DefaultNoApproximations.getDefaultApproximations()));

		pb.post(
			new IgnoranceConstraint(
				"Client -/--> ConcretePrototype",
				"",
				client,
				concreteprototype,
				100,
				DefaultNoApproximations.getDefaultApproximations()));

		pb.post(
			new IgnoranceConstraint(
				"ConcretePrototype -/--> Client",
				"",
				concreteprototype,
				client,
				100,
				DefaultNoApproximations.getDefaultApproximations()));

		return pb;
	}
}
