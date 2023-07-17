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
import ptidej.solver.approximation.TSE07CompositionApproximations;
import ptidej.solver.approximation.TSE07ExtensibleStrictInheritanceApproximations;
import ptidej.solver.constraint.repository.AssociationConstraint;
import ptidej.solver.constraint.repository.CompositionConstraint;
import ptidej.solver.constraint.repository.CreationConstraint;
import ptidej.solver.constraint.repository.NoGhostEntityConstraint;
import ptidej.solver.constraint.repository.NotEqualConstraint;
import ptidej.solver.constraint.repository.StrictInheritanceConstraint;

/**
 * @author Jean-Yves Guyomarc'h
 * @author Yann-Gaël Guéhéneuc
 * @since  2004/09/05 
 */
public final class CommandMotif {
	public static Problem getProblem(final List allEntities) {
		final Problem pb =
			new Problem(90, "Command Design Motif", allEntities);

		final Variable invoker = new Variable(pb, "invoker", false);
		final Variable command = new Variable(pb, "command", true);
		final Variable concretecommand =
			new Variable(pb, "concretecommand", true);
		final Variable receiver = new Variable(pb, "receiver", true);
		final Variable client = new Variable(pb, "client", true);

		pb.addVar(invoker);
		pb.addVar(command);
		pb.addVar(concretecommand);
		pb.addVar(receiver);
		pb.addVar(client);

		pb.post(
			new NotEqualConstraint(
				"Invoker <> Command",
				"",
				invoker,
				command,
				100,
				DefaultNoApproximations.getDefaultApproximations()));
		pb.post(
			new NotEqualConstraint(
				"Invoker <> ConcreteCommand",
				"",
				invoker,
				concretecommand,
				100,
				DefaultNoApproximations.getDefaultApproximations()));
		pb.post(
			new NotEqualConstraint(
				"Invoker <> Receiver",
				"",
				invoker,
				receiver,
				100,
				DefaultNoApproximations.getDefaultApproximations()));
		pb.post(
			new NotEqualConstraint(
				"Invoker <> Client",
				"",
				invoker,
				client,
				100,
				DefaultNoApproximations.getDefaultApproximations()));
		pb.post(
			new NotEqualConstraint(
				"Command <> ConcreteCommand",
				"",
				command,
				concretecommand,
				100,
				DefaultNoApproximations.getDefaultApproximations()));
		pb.post(
			new NotEqualConstraint(
				"Command <> Receiver",
				"",
				command,
				receiver,
				100,
				DefaultNoApproximations.getDefaultApproximations()));
		pb.post(
			new NotEqualConstraint(
				"Command <> Client",
				"",
				command,
				client,
				100,
				DefaultNoApproximations.getDefaultApproximations()));
		pb.post(
			new NotEqualConstraint(
				"ConcreteCommand <> Receiver",
				"",
				concretecommand,
				receiver,
				100,
				DefaultNoApproximations.getDefaultApproximations()));
		pb.post(
			new NotEqualConstraint(
				"ConcreteCommand <> Client",
				"",
				concretecommand,
				client,
				100,
				DefaultNoApproximations.getDefaultApproximations()));
		pb.post(
			new NotEqualConstraint(
				"Receiver <> Client",
				"",
				receiver,
				client,
				100,
				DefaultNoApproximations.getDefaultApproximations()));

		pb.post(
			new NoGhostEntityConstraint(
				"Command <> ?",
				"",
				command,
				100,
				DefaultNoApproximations.getDefaultApproximations()));
		pb.post(
			new NoGhostEntityConstraint(
				"Receiver <> ?",
				"",
				receiver,
				100,
				DefaultNoApproximations.getDefaultApproximations()));
		pb.post(
			new StrictInheritanceConstraint(
				"ConcreteCommand -|>- Command",
				"",
				concretecommand,
				command,
				50,
				TSE07ExtensibleStrictInheritanceApproximations
					.getDefaultApproximations()));
		pb.post(
			new CompositionConstraint(
				"Invoker <#>-> Command",
				"",
				invoker,
				command,
				50,
				TSE07CompositionApproximations.getDefaultApproximations()));
		pb.post(
			new AssociationConstraint(
				"ConcreteCommand ----> Receiver",
				"",
				concretecommand,
				receiver,
				100,
				DefaultNoApproximations.getDefaultApproximations()));
		pb.post(
			new AssociationConstraint(
				"Client ----> Receiver",
				"",
				client,
				receiver,
				100,
				DefaultNoApproximations.getDefaultApproximations()));
		pb.post(
			new CreationConstraint(
				"Client -*--> ConcreteCommand",
				"",
				client,
				concretecommand,
				100,
				DefaultNoApproximations.getDefaultApproximations()));

		return pb;
	}
}
