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
import ptidej.solver.java.approximation.DefaultIgnoranceApproximations;
import ptidej.solver.java.approximation.DefaultNoApproximations;
import ptidej.solver.java.constraint.repository.AssociationConstraint;
import ptidej.solver.java.constraint.repository.IgnoranceConstraint;
import ptidej.solver.java.constraint.repository.StrictInheritanceConstraint;
import ptidej.solver.java.domain.Manager;
import util.multilingual.MultilingualManager;

/**
 * @author Jean-Yves Guyomarc'h
 * @since 2004/10/28
 */
public final class AdapterMotif {
	public static Problem getProblem(
		final List allEntities,
		final ReducedDomainBuilder rdg) {
		final Problem pb =
			new Problem(90, MultilingualManager.getString(
				"Problem_ADM",
				AdapterMotif.class), allEntities);

		final Variable target =
			new Variable(pb, "target", true, Manager.build(rdg
				.computeReducedDomain(Rule.A_T)));
		final Variable adapter = new Variable(pb, "adapter", true);
		final Variable adaptee = new Variable(pb, "adpatee", true);
		final Variable client = new Variable(pb, "client", true);

		pb.addVar(target);
		pb.addVar(adapter);
		pb.addVar(adaptee);
		pb.addVar(client);

		// Constraints
		final StrictInheritanceConstraint c1 =
			new StrictInheritanceConstraint(
				"Adapter -|>- Target",
				"command",
				adapter,
				target,
				90,
				DefaultNoApproximations.getDefaultApproximations());

		final AssociationConstraint c2 =
			new AssociationConstraint(
				"Adapter ---e> Adaptee",
				"command",
				adapter,
				adaptee,
				90,
				DefaultNoApproximations.getDefaultApproximations());
		final AssociationConstraint c3 =
			new AssociationConstraint(
				"Client ---e> Target",
				"command",
				client,
				target,
				90,
				DefaultNoApproximations.getDefaultApproximations());

		final IgnoranceConstraint c4 =
			new IgnoranceConstraint(
				"Adapter -/--> Client",
				"command",
				adapter,
				client,
				70,
				DefaultIgnoranceApproximations.getDefaultApproximations());
		final IgnoranceConstraint c5 =
			new IgnoranceConstraint(
				"Adaptee -/--> Adapter",
				"command",
				adaptee,
				adapter,
				70,
				DefaultIgnoranceApproximations.getDefaultApproximations());
		final IgnoranceConstraint c6 =
			new IgnoranceConstraint(
				"Client -/--> Adaptee",
				"command",
				client,
				adaptee,
				70,
				DefaultIgnoranceApproximations.getDefaultApproximations());
		final IgnoranceConstraint c7 =
			new IgnoranceConstraint(
				"Target -/--> Adaptee",
				"command",
				target,
				adaptee,
				70,
				DefaultIgnoranceApproximations.getDefaultApproximations());
		final IgnoranceConstraint c8 =
			new IgnoranceConstraint(
				"Target -/--> Adapter",
				"command",
				target,
				adapter,
				70,
				DefaultIgnoranceApproximations.getDefaultApproximations());

		pb.post(c1);
		pb.post(c2);
		pb.post(c3);
		pb.post(c4);
		pb.post(c5);
		pb.post(c6);
		pb.post(c7);
		pb.post(c8);

		return pb;
	}
}
