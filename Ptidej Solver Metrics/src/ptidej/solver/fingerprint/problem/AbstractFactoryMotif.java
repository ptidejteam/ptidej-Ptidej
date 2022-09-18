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
import ptidej.solver.Variable;
import ptidej.solver.approximation.DefaultNoApproximations;
import ptidej.solver.constraint.repository.AssociationConstraint;
import ptidej.solver.constraint.repository.CreationConstraint;
import ptidej.solver.constraint.repository.InheritancePathConstraint;
import ptidej.solver.domain.Manager;
import ptidej.solver.fingerprint.Problem;
import ptidej.solver.fingerprint.ReducedDomainBuilder;
import ptidej.solver.fingerprint.Rule;
import util.multilingual.MultilingualManager;

/**
 * @author Jean-Yves Guyomarc'h
 * @since 2004/10/28
 */
public final class AbstractFactoryMotif {
	public static Problem getProblem(
		final List allEntities,
		final ReducedDomainBuilder rdg) {

		final Problem pb =
			new Problem(90, MultilingualManager.getString(
				"Problem_FMDM",
				AbstractFactoryMotif.class), allEntities);

		final Variable abstractfactory =
			new Variable(pb, "abstractfactory", true, Manager.build(rdg
				.computeReducedDomain(Rule.AF_AF)));
		final Variable abstractproduct =
			new Variable(pb, "abstractproduct", true, Manager.build(rdg
				.computeReducedDomain(Rule.AF_AP)));
		final Variable product = new Variable(pb, "product", true);
		final Variable concretefactory =
			new Variable(pb, "concretefactory", true);
		//		final Variable client =
		//			new Variable(
		//				pb,
		//				"client",
		//				false,
		//				Manager.build(rdg.computeReducedDomain(Rule.AF_C)));

		// Constraints

		final InheritancePathConstraint c1 =
			new InheritancePathConstraint(
				"ConcreteFactory -|>- ... -|>- AbstractFactory",
				"command",
				concretefactory,
				abstractfactory,
				90,
				DefaultNoApproximations.getDefaultApproximations());

		final InheritancePathConstraint c2 =
			new InheritancePathConstraint(
				"Product -|>- ... -|>- AbstractProduct",
				"command",
				product,
				abstractproduct,
				90,
				DefaultNoApproximations.getDefaultApproximations());

		final AssociationConstraint c3 =
			new AssociationConstraint(
				"ConcreteFactory ----> Product",
				"command",
				concretefactory,
				product,
				90,
				DefaultNoApproximations.getDefaultApproximations());

		final CreationConstraint c4 =
			new CreationConstraint(
				"ConcreteFactory -*--> Product",
				"command",
				concretefactory,
				product,
				90,
				DefaultNoApproximations.getDefaultApproximations());

		//		final UseConstraint c5 =
		//			new UseConstraint(
		//				"Client ----> AbstractFactory",
		//				"command",
		//				client,
		//				abstractfactory,
		//				90);
		//
		//		final UseConstraint c6 =
		//			new UseConstraint(
		//				"Client ----> AbstractProduct",
		//				"command",
		//				client,
		//				abstractproduct,
		//				90);

		//		pb.post(c6);
		//		pb.post(c5);
		pb.post(c4);
		pb.post(c3);
		pb.post(c2);
		pb.post(c1);
		pb.addVar(abstractfactory);
		pb.addVar(abstractproduct);
		pb.addVar(product);
		pb.addVar(concretefactory);
		// pb.addVar(client);

		return pb;
	}
}
