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
import ptidej.solver.java.approximation.TSE07AbstractnessApproximations;
import ptidej.solver.java.approximation.TSE07AggregationApproximations;
import ptidej.solver.java.approximation.TSE07ExtensibleInheritanceOrNoneApproximations;
import ptidej.solver.java.constraint.repository.AbstractEntityConstraint;
import ptidej.solver.java.constraint.repository.AggregationConstraint;
import ptidej.solver.java.constraint.repository.InterfaceEntityConstraint;
import ptidej.solver.java.constraint.repository.NoGhostEntityConstraint;
import ptidej.solver.java.constraint.repository.NotEqualConstraint;
import ptidej.solver.java.constraint.repository.StrictInheritanceConstraint;

/**
 * @author Lucas Nelaupe, Ferrand Anthony, Tran Quang Dung, Verdier Frederic 
 * @since  2014/06/01 
 */
public final class BridgeMotif {
	public static Problem getProblem(final List allEntities) 
	{
		//Creation du probleme
		final Problem pb = new Problem(90, "Bridge Design Motif", allEntities);
		//Creation des entites
		final Variable abstraction = new Variable(pb, "Abstraction", true);
		final Variable refinedAbstraction = new Variable(pb, "RefinedAbstraction", false);
		final Variable implementor = new Variable(pb, "Implementor", true);
		final Variable concreteImplementor = new Variable(pb, "ConcreteImplementor", false);
		
		//Ajout des entites au probleme.
		pb.addVar(abstraction);
		pb.addVar(refinedAbstraction);
		pb.addVar(implementor);
		pb.addVar(concreteImplementor);
		//Pas de detection sur les entites fantomes.
		pb.post(
				new NoGhostEntityConstraint(
					"Abstraction <> ?",
					"",
					abstraction,
					100,
					DefaultNoApproximations.getDefaultApproximations()));
		pb.post(
				new NoGhostEntityConstraint(
					"RefinedAbstraction <> ?",
					"",
					refinedAbstraction,
					100,
					DefaultNoApproximations.getDefaultApproximations()));
		pb.post(
				new NoGhostEntityConstraint(
						"Implementor <> ?",
						"",
						implementor, 
						100, 
						DefaultNoApproximations.getDefaultApproximations()));
		pb.post(
				new NoGhostEntityConstraint(
						"ConcreteImplementor <> ?",
						"",
						concreteImplementor, 
						100, 
						DefaultNoApproximations.getDefaultApproximations()));
		//Heritage
		pb.post(
				new StrictInheritanceConstraint(
					"RefinedAbstraction -|>- Abstraction",
					"",
					refinedAbstraction,
					abstraction,
					100,
					TSE07ExtensibleInheritanceOrNoneApproximations
						.getDefaultApproximations()));
		pb.post(
				new StrictInheritanceConstraint(
					"ConcreteImplementor -|>- Implementor",
					"",
					concreteImplementor,
					implementor,
					100,
					TSE07ExtensibleInheritanceOrNoneApproximations
						.getDefaultApproximations()));
		//Les entites doivent etre distinctes
		pb.post(
				new NotEqualConstraint(
					"Abstraction <> RefinedAbstraction",
					"",
					abstraction,
					refinedAbstraction,
					100,
					DefaultNoApproximations.getDefaultApproximations()));
		pb.post(
				new NotEqualConstraint(
					"Implementor <> ConcreteImplementor",
					"",
					implementor,
					concreteImplementor,
					100,
					DefaultNoApproximations.getDefaultApproximations()));
		pb.post(
				new NotEqualConstraint(
					"Implementor <> Abstraction",
					"",
					implementor,
					abstraction,
					100,
					DefaultNoApproximations.getDefaultApproximations()));
		pb.post(
				new NotEqualConstraint(
					"RefinedAbstraction <> ConcreteImplementor",
					"",
					refinedAbstraction,
					concreteImplementor,
					100,
					DefaultNoApproximations.getDefaultApproximations()));
		pb.post(
				new NotEqualConstraint(
					"Implementor <> RefinedAbstraction",
					"",
					implementor,
					refinedAbstraction,
					100,
					DefaultNoApproximations.getDefaultApproximations()));
		pb.post(
				new NotEqualConstraint(
					"Abstraction <> ConcreteImplementor",
					"",
					abstraction,
					concreteImplementor,
					100,
					DefaultNoApproximations.getDefaultApproximations()));
		//Implementor est une interface
		pb.post(
				new InterfaceEntityConstraint(
						"Implementor <<interface>>", 
						"", 
						implementor, 
						100, 
						DefaultNoApproximations.getDefaultApproximations()));
		//Abstraction est une classe abstraite
		pb.post(
				new AbstractEntityConstraint(
						"Abstraction <<abstract>>", 
						"", 
						abstraction, 
						100, 
						TSE07AbstractnessApproximations.getDefaultApproximations()));
		pb.post(
				new AggregationConstraint(
						"Abstraction <>-> Implementor",
						"",
						refinedAbstraction,
						implementor,
						100,
						TSE07AggregationApproximations.getDefaultApproximations()));
		return pb;
	}
}
