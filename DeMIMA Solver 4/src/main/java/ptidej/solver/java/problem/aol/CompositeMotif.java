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
package ptidej.solver.java.problem.aol;

import java.util.List;

import ptidej.solver.java.Problem;
import ptidej.solver.java.Variable;
import ptidej.solver.java.approximation.DefaultNoApproximations;
import ptidej.solver.java.approximation.TSE07ContainerCompositionApproximations;
import ptidej.solver.java.approximation.TSE07ExtensibleInheritanceOrNoneApproximations;
import ptidej.solver.java.approximation.TSE07ExtensibleStrictInheritanceApproximations;
import ptidej.solver.java.constraint.repository.ContainerCompositionConstraint;
import ptidej.solver.java.constraint.repository.NoGhostEntityConstraint;
import ptidej.solver.java.constraint.repository.NotEqualConstraint;
import ptidej.solver.java.constraint.repository.StrictInheritanceConstraint;

/**
 * @author Yann-Gaël Guéhéneuc
 * @since  2004/09/05 
 */
public final class CompositeMotif {
	public static Problem getProblem(final List allEntities) {
		final Problem pb =
			new Problem(90, "Composite Design Motif", allEntities);

		final Variable component = new Variable(pb, "component", true);
		final Variable composite = new Variable(pb, "composite", true);
		final Variable leaf = new Variable(pb, "leaf", false);

		pb.addVar(leaf);
		pb.addVar(composite);
		pb.addVar(component);

		pb.post(
			new NoGhostEntityConstraint(
				"Component <> ?",
				"",
				component,
				100,
				DefaultNoApproximations.getDefaultApproximations()));
		pb.post(
			new StrictInheritanceConstraint(
				"Composite -|>- Component",
				"",
				composite,
				component,
				50,
				TSE07ExtensibleInheritanceOrNoneApproximations
					.getDefaultApproximations()));
		pb.post(
			new ContainerCompositionConstraint(
				"Composite <#>-> Component",
				"",
				composite,
				component,
				50,
				TSE07ContainerCompositionApproximations
					.getDefaultApproximations()));
		pb.post(
			new StrictInheritanceConstraint(
				"Leaf -|>- Component",
				"",
				leaf,
				component,
				50,
				TSE07ExtensibleStrictInheritanceApproximations
					.getDefaultApproximations()));

		pb.post(
			new NotEqualConstraint(
				"Component <> Composite",
				"",
				component,
				composite,
				100,
				DefaultNoApproximations.getDefaultApproximations()));
		pb.post(
			new NotEqualConstraint(
				"Component <> Leaf",
				"",
				component,
				leaf,
				100,
				DefaultNoApproximations.getDefaultApproximations()));
		pb.post(
			new NotEqualConstraint(
				"Composite <> Leaf",
				"",
				composite,
				leaf,
				100,
				DefaultNoApproximations.getDefaultApproximations()));

		return pb;
	}
}
