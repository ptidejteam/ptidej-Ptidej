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
import ptidej.solver.java.approximation.TSE07AggregationApproximations;
import ptidej.solver.java.constraint.repository.AggregationConstraint;
import ptidej.solver.java.constraint.repository.NoGhostEntityConstraint;
import ptidej.solver.java.constraint.repository.StrictInheritancePathConstraint;

/**
 * @author Yann-Gaël Guéhéneuc
 * @since  2007/02/24 
 */
public final class StateMotif {
	public static Problem getProblem(final List allEntities) {
		final Problem pb = new Problem(90, "State Design Motif", allEntities);

		final Variable context = new Variable(pb, "context", false);
		final Variable state = new Variable(pb, "state", true);
		final Variable concretestate =
			new Variable(pb, "concretestate", false);

		pb.addVar(context);
		pb.addVar(state);
		pb.addVar(concretestate);

		pb.post(
			new StrictInheritancePathConstraint(
				"ConcreteState -|>- State",
				"",
				concretestate,
				state,
				100,
				DefaultNoApproximations.getDefaultApproximations()));

		pb.post(
			new AggregationConstraint(
				"Context <>--> State",
				"",
				context,
				state,
				50,
				TSE07AggregationApproximations.getDefaultApproximations()));

		pb.post(
			new NoGhostEntityConstraint(
				"State <> ?",
				"",
				state,
				100,
				DefaultNoApproximations.getDefaultApproximations()));

		return pb;
	}
}
