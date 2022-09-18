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
import ptidej.solver.approximation.TSE07AbstractnessApproximations;
import ptidej.solver.approximation.TSE07CompositionApproximations;
import ptidej.solver.approximation.TSE07ExtensibleStrictInheritanceApproximations;
import ptidej
	.solver
	.approximation
	.TSE07ExtensibleInheritanceOrNoneApproximations;
import ptidej.solver.constraint.repository.AbstractEntityConstraint;
import ptidej.solver.constraint.repository.CompositionConstraint;
import ptidej.solver.constraint.repository.NoGhostEntityConstraint;
import ptidej.solver.constraint.repository.NotEqualConstraint;
import ptidej.solver.constraint.repository.StrictInheritanceConstraint;

/**
 * @author Yann-Gaël Guéhéneuc
 * @since  2004/09/05 
 */
public final class DecoratorMotif {
	public static Problem getProblem(final List allEntities) {
		final Problem pb =
			new Problem(90, "Decorator Design Motif", allEntities);

		final Variable component = new Variable(pb, "component", true);
		final Variable concreteComponent =
			new Variable(pb, "concretecomponent", false);
		final Variable decorator = new Variable(pb, "decorator", true);
		final Variable concreteDecorator =
			new Variable(pb, "concretedecorator", false);

		pb.addVar(component);
		pb.addVar(concreteComponent);
		pb.addVar(decorator);
		pb.addVar(concreteDecorator);

		pb.post(
			new NoGhostEntityConstraint(
				"Component <> ?",
				"",
				component,
				100,
				DefaultNoApproximations.getDefaultApproximations()));
		pb.post(
			new StrictInheritanceConstraint(
				"Decorator -|>- Component",
				"",
				decorator,
				component,
				50,
				TSE07ExtensibleInheritanceOrNoneApproximations
					.getDefaultApproximations()));
		pb.post(
			new CompositionConstraint(
				"Decorator <>--> Component",
				"",
				decorator,
				component,
				50,
				TSE07CompositionApproximations.getDefaultApproximations()));
		pb.post(
			new AbstractEntityConstraint(
				"Decorator",
				"",
				decorator,
				50,
				TSE07AbstractnessApproximations.getDefaultApproximations()));
		pb.post(
			new StrictInheritanceConstraint(
				"ConcreteDecorator -|>- Decorator",
				"",
				concreteDecorator,
				decorator,
				100,
				DefaultNoApproximations.getDefaultApproximations()));
		pb.post(
			new StrictInheritanceConstraint(
				"ConcreteComponent -|>- Component",
				"",
				concreteComponent,
				component,
				50,
				TSE07ExtensibleStrictInheritanceApproximations
					.getDefaultApproximations()));

		pb.post(
			new NotEqualConstraint(
				"Component <> Decorator",
				"",
				component,
				decorator,
				100,
				DefaultNoApproximations.getDefaultApproximations()));
		pb.post(
			new NotEqualConstraint(
				"Component <> ConcreteComponent",
				"",
				component,
				concreteComponent,
				100,
				DefaultNoApproximations.getDefaultApproximations()));
		pb.post(
			new NotEqualConstraint(
				"Decorator <> ConcreteComponent",
				"",
				decorator,
				concreteComponent,
				100,
				DefaultNoApproximations.getDefaultApproximations()));

		return pb;
	}
}
