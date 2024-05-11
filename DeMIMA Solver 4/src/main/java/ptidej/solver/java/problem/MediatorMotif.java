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
import ptidej.solver.java.constraint.repository.AggregationConstraint;
import ptidej.solver.java.constraint.repository.AssociationConstraint;
import ptidej.solver.java.constraint.repository.IgnoranceConstraint;
import ptidej.solver.java.constraint.repository.InterfaceEntityConstraint;
import ptidej.solver.java.constraint.repository.NoGhostEntityConstraint;
import ptidej.solver.java.constraint.repository.NotEqualConstraint;
import ptidej.solver.java.constraint.repository.StrictInheritanceConstraint;

/**
 * @author Lucas Nelaupe, Ferrand Anthony, Tran Quang Dung, Verdier Frederic 
 * @since  2014/06/01 
 */
public final class MediatorMotif {
	public static Problem getProblem(final List allEntities) {
		final Problem pb = new Problem(90, "Meditor Design Motif", allEntities);
		final Variable interfacemediator =
			new Variable(pb, "interfacemediator", true);
		final Variable concretemediator =
			new Variable(pb, "concretemediator", true);
		final Variable interfacecolleague =
			new Variable(pb, "interfacecolleague", true);
		final Variable concretecolleague =
			new Variable(pb, "concretecolleague", false);

		pb.addVar(interfacemediator);
		pb.addVar(concretemediator);
		pb.addVar(interfacecolleague);
		pb.addVar(concretecolleague);

		// Constraints
		pb.post(new NoGhostEntityConstraint(
			"ConcreteColleague <>?",
			"",
			concretecolleague,
			100,
			DefaultNoApproximations.getDefaultApproximations()));
		pb.post(new NoGhostEntityConstraint(
			"ConcreteMediator <>?",
			"",
			concretemediator,
			100,
			DefaultNoApproximations.getDefaultApproximations()));

		pb.post(new NoGhostEntityConstraint(
			"InterfaceColleague <>?",
			"",
			interfacecolleague,
			100,
			DefaultNoApproximations.getDefaultApproximations()));

		pb.post(new InterfaceEntityConstraint(
			"InterfaceColleague is an interface",
			"",
			interfacecolleague,
			100,
			DefaultNoApproximations.getDefaultApproximations()));
		pb.post(new NoGhostEntityConstraint(
			"InterfaceMediator <>?",
			"",
			interfacemediator,
			100,
			DefaultNoApproximations.getDefaultApproximations()));
		pb.post(new InterfaceEntityConstraint(
			"InterfaceMediator is an interface",
			"",
			interfacemediator,
			100,
			DefaultNoApproximations.getDefaultApproximations()));
		pb.post(new StrictInheritanceConstraint(
			"ConcreteColleague -|>- InterfaceColleague",
			"",
			concretecolleague,
			interfacecolleague,
			100,
			DefaultNoApproximations.getDefaultApproximations()));
		pb.post(new StrictInheritanceConstraint(
			"ConcreteMediator -|>- InterfaceMediator",
			"",
			concretemediator,
			interfacemediator,
			100,
			DefaultNoApproximations.getDefaultApproximations()));
		pb.post(new NotEqualConstraint(
			"InterfaceMediator <> InterfaceColleague",
			"",
			interfacemediator,
			interfacecolleague,
			100,
			DefaultNoApproximations.getDefaultApproximations()));
		pb.post(new AggregationConstraint(
			"ConcreteMediator ---e> ConcreteColleague",
			"",
			concretemediator,
			concretecolleague,
			100,
			DefaultNoApproximations.getDefaultApproximations()));
		pb.post(new AssociationConstraint(
			"ConcreteColleague ---e> InterfaceMediator",
			"",
			concretecolleague,
			interfacemediator,
			100,
			DefaultNoApproximations.getDefaultApproximations()));
		pb.post(new IgnoranceConstraint(
			"ConcreteColleague ---e> InterfaceMediator",
			"",
			concretecolleague,
			concretecolleague,
			100,
			DefaultNoApproximations.getDefaultApproximations()));

		return pb;
	}
}
