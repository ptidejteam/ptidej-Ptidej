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
package ptidej.solver.fingerprint.problem;

import java.util.List;

import ptidej.solver.fingerprint.Problem;
import ptidej.solver.fingerprint.ReducedDomainBuilder;
import ptidej.solver.fingerprint.Rule;
import ptidej.solver.java.Variable;
import ptidej.solver.java.approximation.DefaultAssociationApproximations;
import ptidej.solver.java.approximation.DefaultInheritanceApproximations;
import ptidej.solver.java.approximation.DefaultNoApproximations;
import ptidej.solver.java.constraint.repository.AssociationConstraint;
import ptidej.solver.java.constraint.repository.CreationConstraint;
import ptidej.solver.java.constraint.repository.NotEqualConstraint;
import ptidej.solver.java.constraint.repository.StrictInheritanceConstraint;
import ptidej.solver.java.domain.Manager;
import util.multilingual.MultilingualManager;

/**
 * @author Jean-Yves Guyomarc'h
 * @since 2004/10/28
 */
public final class FactoryMethodMotif {
	public static Problem getProblem(
		final List allEntities,
		final ReducedDomainBuilder rdg) {

		final Problem pb =
			new Problem(90, MultilingualManager.getString(
				"Problem_FMDM",
				FactoryMethodMotif.class), allEntities);

		final Variable creator = new Variable(pb, "creator", true);
		final Variable concreteCreator =
			new Variable(pb, "concreteCreator", true, Manager.build(rdg
				.computeReducedDomain(Rule.FM_CC)));
		final Variable product = new Variable(pb, "product", true);
		final Variable concreteProduct =
			new Variable(pb, "concreteProduct", true, Manager.build(rdg
				.computeReducedDomain(Rule.FM_CP)));

		pb.addVar(creator);
		pb.addVar(concreteCreator);
		pb.addVar(product);
		pb.addVar(concreteProduct);

		//Constraint

		final StrictInheritanceConstraint c1 =
			new StrictInheritanceConstraint(
				"ConcreteCreator -|>- Creator",
				"command",
				concreteCreator,
				creator,
				50,
				DefaultInheritanceApproximations.getDefaultApproximations());

		final StrictInheritanceConstraint c2 =
			new StrictInheritanceConstraint(
				"ConcreteCreator -|>- Product",
				"command",
				concreteProduct,
				product,
				50,
				DefaultInheritanceApproximations.getDefaultApproximations());

		final AssociationConstraint c3 =
			new AssociationConstraint(
				"Creator ----> Product",
				"command",
				creator,
				product,
				80,
				DefaultAssociationApproximations.getDefaultApproximations());

		final CreationConstraint c4 =
			new CreationConstraint(
				"ConcreteCreator -*--> ConcreteProduct",
				"command",
				concreteCreator,
				concreteProduct,
				90,
				DefaultNoApproximations.getDefaultApproximations());

		final NotEqualConstraint c5 =
			new NotEqualConstraint(
				"Creator <> Product",
				"command",
				creator,
				product,
				90,
				DefaultNoApproximations.getDefaultApproximations());

		final NotEqualConstraint c6 =
			new NotEqualConstraint(
				"Creator <> ConcreteCreator",
				"command",
				creator,
				concreteCreator,
				90,
				DefaultNoApproximations.getDefaultApproximations());

		final NotEqualConstraint c7 =
			new NotEqualConstraint(
				"Creator <> ConcreteProduct",
				"command",
				creator,
				concreteProduct,
				90,
				DefaultNoApproximations.getDefaultApproximations());

		final NotEqualConstraint c8 =
			new NotEqualConstraint(
				"Product <> ConcreteProduct",
				"command",
				product,
				concreteProduct,
				90,
				DefaultNoApproximations.getDefaultApproximations());

		final NotEqualConstraint c9 =
			new NotEqualConstraint(
				"ConcreteCreator <> ConcreteProduct",
				"command",
				concreteCreator,
				concreteProduct,
				90,
				DefaultNoApproximations.getDefaultApproximations());

		final NotEqualConstraint c10 =
			new NotEqualConstraint(
				"ConcreteCraetor <> Product",
				"command",
				concreteCreator,
				product,
				90,
				DefaultNoApproximations.getDefaultApproximations());

		pb.post(c1);
		pb.post(c2);
		pb.post(c3);
		pb.post(c4);
		pb.post(c5);
		pb.post(c6);
		pb.post(c7);
		pb.post(c8);
		pb.post(c9);
		pb.post(c10);

		return pb;
	}
}
