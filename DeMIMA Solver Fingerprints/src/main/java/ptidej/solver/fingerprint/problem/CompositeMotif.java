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
import ptidej.solver.java.approximation.DefaultContainerAssociationApproximations;
import ptidej.solver.java.approximation.DefaultIgnoranceApproximations;
import ptidej.solver.java.approximation.DefaultInheritanceApproximations;
import ptidej.solver.java.approximation.DefaultNoApproximations;
import ptidej.solver.java.constraint.repository.ContainerCompositionConstraint;
import ptidej.solver.java.constraint.repository.IgnoranceConstraint;
import ptidej.solver.java.constraint.repository.NotEqualConstraint;
import ptidej.solver.java.constraint.repository.StrictInheritanceConstraint;
import ptidej.solver.java.constraint.repository.StrictInheritancePathConstraint;
import ptidej.solver.java.domain.Manager;
import util.multilingual.MultilingualManager;

/**
 * @author Jean-Yves Guyomarc'h
 * @since  2004/10/15 
 */
public final class CompositeMotif {
	public static Problem getProblem(
		final List allEntities,
		final ReducedDomainBuilder rdg) {

		final Problem pb =
			new Problem(90, MultilingualManager.getString(
				"Problem_CDM",
				CompositeMotif.class), allEntities);

		final Variable component = new Variable(pb, "component", true);
		final Variable composite = new Variable(pb, "composite", true);
		final Variable leaf =
			new Variable(pb, "leaf", true, Manager.build(rdg
				.computeReducedDomain(Rule.C_LEAF_ROLE_1)));

		pb.addVar(leaf);
		pb.addVar(composite);
		pb.addVar(component);

		final StrictInheritanceConstraint c1 =
			new StrictInheritanceConstraint(
				"Composite -|>- Component",
				"command",
				composite,
				component,
				50,
				DefaultInheritanceApproximations.getDefaultApproximations());
		final StrictInheritancePathConstraint c2 =
			new StrictInheritancePathConstraint(
				"Leaf -|>- Component",
				"command",
				leaf,
				component,
				100,
				DefaultNoApproximations.getDefaultApproximations());
		final ContainerCompositionConstraint c3 =
			new ContainerCompositionConstraint(
				"Composite <>--> Component",
				"command",
				composite,
				component,
				50,
				DefaultContainerAssociationApproximations
					.getDefaultApproximations());
		final IgnoranceConstraint c4 =
			new IgnoranceConstraint(
				"Component -/--> Leaf",
				"command",
				component,
				leaf,
				10,
				DefaultIgnoranceApproximations.getDefaultApproximations());
		final IgnoranceConstraint c5 =
			new IgnoranceConstraint(
				"Leaf -/--> Composite",
				"command",
				leaf,
				composite,
				30,
				DefaultIgnoranceApproximations.getDefaultApproximations());
		final NotEqualConstraint c6 =
			new NotEqualConstraint(
				"Component <> Composite",
				"command",
				component,
				composite,
				100,
				DefaultNoApproximations.getDefaultApproximations());
		final NotEqualConstraint c7 =
			new NotEqualConstraint(
				"Component <> Leaf",
				"command",
				component,
				leaf,
				100,
				DefaultNoApproximations.getDefaultApproximations());
		final NotEqualConstraint c8 =
			new NotEqualConstraint(
				"Composite <> Leaf",
				"command",
				composite,
				leaf,
				100,
				DefaultNoApproximations.getDefaultApproximations());

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
