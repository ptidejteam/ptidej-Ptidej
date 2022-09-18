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
import ptidej.solver.constraint.repository.InheritancePathConstraint;
import ptidej.solver.domain.Manager;
import ptidej.solver.fingerprint.Problem;
import ptidej.solver.fingerprint.ReducedDomainBuilder;
import ptidej.solver.fingerprint.Rule;
import util.multilingual.MultilingualManager;

/**
 * @author Jean-Yves Guyomarc'h
 * @since  2004/09/05 
 */
public final class CommandMotif {
	public static Problem getProblem(
		final List allEntities,
		final ReducedDomainBuilder rdg) {
		final Problem pb =
			new Problem(90, MultilingualManager.getString(
				"Problem_CDM",
				CommandMotif.class), allEntities);

		final Variable concretecommand =
			new Variable(pb, "concretecommand", true, Manager.build(rdg
				.computeReducedDomain(Rule.CM_CC)));
		final Variable invoker =
			new Variable(pb, "invoker", true, Manager.build(rdg
				.computeReducedDomain(Rule.CM_I)));
		final Variable receiver =
			new Variable(pb, "receiver", false, Manager.build(rdg
				.computeReducedDomain(Rule.CM_R)));
		final Variable client =
			new Variable(pb, "client", false, Manager.build(rdg
				.computeReducedDomain(Rule.CM_C)));
		final Variable command = new Variable(pb, "command", false);

		pb.addVar(concretecommand);
		pb.addVar(invoker);
		pb.addVar(receiver);
		pb.addVar(client);
		pb.addVar(command);

		final InheritancePathConstraint c1 =
			new InheritancePathConstraint(
				"ConcreteCommand -|>- ... -|>- Command",
				"command",
				concretecommand,
				command,
				90,
				DefaultNoApproximations.getDefaultApproximations());

		pb.post(c1);

		return pb;
	}
}
