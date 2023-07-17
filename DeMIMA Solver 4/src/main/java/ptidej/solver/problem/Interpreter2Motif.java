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
import ptidej.solver.approximation.TSE07ExtensibleInheritanceOrNoneApproximations;
import ptidej.solver.constraint.repository.AbstractEntityConstraint;
import ptidej.solver.constraint.repository.AssociationConstraint;
import ptidej.solver.constraint.repository.NoGhostEntityConstraint;
import ptidej.solver.constraint.repository.NoObjectEntityConstraint;
import ptidej.solver.constraint.repository.NotEqualConstraint;
import ptidej.solver.constraint.repository.StrictInheritanceConstraint;
import ptidej.solver.constraint.repository.UseConstraint;

/**
 * @author Lucas Nelaupe, Ferrand Anthony, Tran Quang Dung, Verdier Frédéric 
 * @since  2014/06/01 
 */
public class Interpreter2Motif {
	public static Problem getProblem(final List allEntities) {
		final Problem pb =
			new Problem(90, "Interpreter Design Motif", allEntities);
		//Creation des entites
		final Variable abstractExpression =
			new Variable(pb, "AbstractExpression", true);
		final Variable terminalExpression =
			new Variable(pb, "TerminalExpression", false);
		final Variable context = new Variable(pb, "Context", true);
		final Variable client = new Variable(pb, "Client", true);
		//Ajout des entites au probleme.
		pb.addVar(abstractExpression);
		pb.addVar(terminalExpression);
		pb.addVar(context);
		pb.addVar(client);
		//Pas de detection sur les entites fantomes.
		pb.post(new NoGhostEntityConstraint(
			"AbstractExpression <> ?",
			"",
			abstractExpression,
			100,
			DefaultNoApproximations.getDefaultApproximations()));
		pb.post(new NoGhostEntityConstraint(
			"TerminalExpression <> ?",
			"",
			terminalExpression,
			100,
			DefaultNoApproximations.getDefaultApproximations()));
		pb.post(new NoGhostEntityConstraint(
			"Client <> ?",
			"",
			client,
			100,
			DefaultNoApproximations.getDefaultApproximations()));
		pb.post(new NoObjectEntityConstraint(
			"Context <> Object",
			"",
			context,
			100,
			DefaultNoApproximations.getDefaultApproximations()));
		pb.post(new StrictInheritanceConstraint(
			"TerminalExpression -|>- AbstractExpression",
			"",
			terminalExpression,
			abstractExpression,
			100,
			TSE07ExtensibleInheritanceOrNoneApproximations
				.getDefaultApproximations()));
		pb.post(new AbstractEntityConstraint(
			"AbstractExpression <<Abstract>>",
			"",
			abstractExpression,
			100,
			DefaultNoApproximations.getDefaultApproximations()));
		//Empecher context redondant
		pb.post(new NotEqualConstraint(
			"Context <> AbstractExpression",
			"",
			context,
			abstractExpression,
			100,
			DefaultNoApproximations.getDefaultApproximations()));
		pb.post(new NotEqualConstraint(
			"Context <> TerminalExpression",
			"",
			context,
			terminalExpression,
			100,
			DefaultNoApproximations.getDefaultApproximations()));
		pb.post(new UseConstraint(
			"AbstractExpression ----> Context",
			"",
			abstractExpression,
			context,
			100,
			DefaultNoApproximations.getDefaultApproximations()));
		//Determination du role de point d'entree du client.
		pb.post(new AssociationConstraint(
			"Client ----> Context",
			"",
			client,
			context,
			100,
			DefaultNoApproximations.getDefaultApproximations()));
		pb.post(new AssociationConstraint(
			"Client ----> TerminalExpression",
			"",
			client,
			terminalExpression,
			100,
			DefaultNoApproximations.getDefaultApproximations()));
		return pb;
	}
}
