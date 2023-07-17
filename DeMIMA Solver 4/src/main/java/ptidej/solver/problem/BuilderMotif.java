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
import ptidej.solver.approximation.TSE07AggregationApproximations;
import ptidej.solver.approximation.TSE07ExtensibleStrictInheritanceApproximations;
import ptidej.solver.constraint.repository.AbstractEntityConstraint;
import ptidej.solver.constraint.repository.AggregationConstraint;
import ptidej.solver.constraint.repository.CreationConstraint;
import ptidej.solver.constraint.repository.NoGhostEntityConstraint;
import ptidej.solver.constraint.repository.NoObjectEntityConstraint;
import ptidej.solver.constraint.repository.StrictInheritanceConstraint;

/**
 * @author Yann-Gaël Guéhéneuc
 * @since  2008/12/13 
 */
public final class BuilderMotif {
	public static Problem getProblem(final List allEntities) {
		final Problem pb =
			new Problem(90, "Builder Design Motif", allEntities);

		final Variable director = new Variable(pb, "director", false);
		final Variable builder = new Variable(pb, "builder", true);
		final Variable concretebuilder =
			new Variable(pb, "concretebuilder", false);
		final Variable product =
			new Variable(pb, "product", false);

		pb.addVar(director);
		pb.addVar(builder);
		pb.addVar(concretebuilder);
		pb.addVar(product);

		pb.post(
			new NoGhostEntityConstraint(
				"Builder <> ?",
				"",
				builder,
				100,
				DefaultNoApproximations.getDefaultApproximations()));
		pb.post(
			new NoGhostEntityConstraint(
				"ConcreteBuilder <> ?",
				"",
				concretebuilder,
				100,
				DefaultNoApproximations.getDefaultApproximations()));
		pb.post(
			new NoGhostEntityConstraint(
				"Product <> ?",
				"",
				product,
				100,
				DefaultNoApproximations.getDefaultApproximations()));

		pb.post(
			new NoObjectEntityConstraint(
				"Director <> java.lang.Object",
				"",
				director,
				100,
				DefaultNoApproximations.getDefaultApproximations()));
		pb.post(
			new NoObjectEntityConstraint(
				"Product <> java.lang.Object",
				"",
				product,
				100,
				DefaultNoApproximations.getDefaultApproximations()));
		pb.post(
			new AbstractEntityConstraint(
				"Builder must be abstract",
				"",
				builder,
				100,
				DefaultNoApproximations.getDefaultApproximations()));

		pb.post(
			new AggregationConstraint(
				"Director <>--> Builder",
				"",
				director,
				builder,
				50,
				TSE07AggregationApproximations.getDefaultApproximations()));
		pb.post(
			new StrictInheritanceConstraint(
				"ConcreteBuilder -|>- Builder",
				"",
				concretebuilder,
				builder,
				50,
				TSE07ExtensibleStrictInheritanceApproximations
					.getDefaultApproximations()));
		pb.post(
			new CreationConstraint(
				"ConcreteBuilder -*--> Product",
				"",
				concretebuilder,
				product,
				100,
				DefaultNoApproximations.getDefaultApproximations()));

		return pb;
	}
}
