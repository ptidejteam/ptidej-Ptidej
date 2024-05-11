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
package ptidej.solver.java.problem;

import java.util.List;

import ptidej.solver.java.Problem;
import ptidej.solver.java.Variable;
import ptidej.solver.java.approximation.DefaultNoApproximations;
import ptidej.solver.java.approximation.TSE07CollapsableInheritanceApproximations;
import ptidej.solver.java.approximation.TSE07ExtensibleStrictInheritanceApproximations;
import ptidej.solver.java.constraint.repository.AssociationConstraint;
import ptidej.solver.java.constraint.repository.CreationConstraint;
import ptidej.solver.java.constraint.repository.NoGhostEntityConstraint;
import ptidej.solver.java.constraint.repository.NoInheritancePathConstraint;
import ptidej.solver.java.constraint.repository.NotEqualConstraint;
import ptidej.solver.java.constraint.repository.StrictInheritanceConstraint;

/**
 * @author Jean-Yves Guyomarc'h
 * @author Yann-Gaël Guéhéneuc
 */
public final class AbstractFactoryMotif {
	public static Problem getProblem(final List allEntities) {
		final Problem pb =
			new Problem(90, "Abstract Factory Design Motif", allEntities);

		final Variable abstractfactory =
			new Variable(pb, "abstractfactory", true);
		final Variable concretefactory =
			new Variable(pb, "concretefactory", true);
		final Variable abstractproduct =
			new Variable(pb, "abstractproduct", true);
		final Variable concreteproduct =
			new Variable(pb, "concreteproduct", false, 3);

		pb.addVar(abstractfactory);
		pb.addVar(concretefactory);
		pb.addVar(abstractproduct);
		pb.addVar(concreteproduct);

		// Constraints
		pb.post(
			new StrictInheritanceConstraint(
				"ConcreteFactory -|>- AbstractFactory",
				"",
				concretefactory,
				abstractfactory,
				50,
				TSE07CollapsableInheritanceApproximations
					.getDefaultApproximations()));

		pb.post(
			new StrictInheritanceConstraint(
				"ConcreteProduct -|>- AbstractProduct",
				"",
				concreteproduct,
				abstractproduct,
				50,
				TSE07ExtensibleStrictInheritanceApproximations
					.getDefaultApproximations()));

		pb.post(
			new NoInheritancePathConstraint(
				"ConcreteProduct -|>- .../... -|>- AbstractFactory",
				"",
				concreteproduct,
				abstractfactory,
				100,
				DefaultNoApproximations.getDefaultApproximations()));

		pb.post(
			new NoInheritancePathConstraint(
				"ConcreteFactory -|>- .../... -|>- AbstractProduct",
				"",
				concretefactory,
				abstractproduct,
				100,
				DefaultNoApproximations.getDefaultApproximations()));

		pb.post(
			new NoGhostEntityConstraint(
				"AbstractProduct <> ?",
				"",
				abstractproduct,
				100,
				DefaultNoApproximations.getDefaultApproximations()));

		pb.post(
			new AssociationConstraint(
				"ConcreteFactory ----> AbstractProduct",
				"",
				concretefactory,
				abstractproduct,
				90,
				DefaultNoApproximations.getDefaultApproximations()));

		pb.post(
			new CreationConstraint(
				"ConcreteFactory -*--> ConcreteProduct",
				"",
				concretefactory,
				concreteproduct,
				90,
				DefaultNoApproximations.getDefaultApproximations()));

		pb.post(
			new NotEqualConstraint(
				"AbstractFactory <> AbstractProduct",
				"",
				abstractfactory,
				abstractproduct,
				100,
				DefaultNoApproximations.getDefaultApproximations()));
		pb.post(
			new NotEqualConstraint(
				"ConcreteFactory <> ConcreteProduct",
				"",
				concretefactory,
				concreteproduct,
				100,
				DefaultNoApproximations.getDefaultApproximations()));
		pb.post(
			new NotEqualConstraint(
				"AbstractFactory <> ConcreteProduct",
				"",
				abstractfactory,
				concreteproduct,
				100,
				DefaultNoApproximations.getDefaultApproximations()));
		pb.post(
			new NotEqualConstraint(
				"ConcreteFactory <> AbstractProduct",
				"",
				concretefactory,
				abstractproduct,
				100,
				DefaultNoApproximations.getDefaultApproximations()));

		return pb;
	}
}
