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
package ptidej.solver.fingerprint.test.problem;

import java.util.List;

import ptidej.solver.java.Problem;
import ptidej.solver.java.Variable;
import ptidej.solver.java.approximation.DefaultNoApproximations;
import ptidej.solver.java.constraint.repository.CompositionConstraint;
import ptidej.solver.java.constraint.repository.NotEqualConstraint;

/**
 * @author Yann-Gaël Guéhéneuc
 * @since  2004/09/05 
 */
public final class CompositionProblem2 {
	public static Problem getProblem(final List allEntities) {
		final Problem pb = new Problem(90, "Composition Test", allEntities);

		//	final ArrayList aggregateList = new ArrayList();
		//	aggregateList.add(allEntities.get(0));
		//	aggregateList.add(allEntities.get(3));

		//	final ArrayList aggregatedList = new ArrayList();
		//	aggregatedList.add(allEntities.get(2));
		//	aggregatedList.add(allEntities.get(5));

		final Variable aggregate = new Variable(pb, "Aggregate", true);
		final Variable aggregated = new Variable(pb, "Aggregated", true);

		pb.addVar(aggregate);
		pb.addVar(aggregated);

		final CompositionConstraint c1 =
			new CompositionConstraint(
				"Aggregate <>--> Aggregated",
				"throw new RuntimeException(\"Composite <>--> Component\");",
				aggregate,
				aggregated,
				100,
				DefaultNoApproximations.getDefaultApproximations());
		final NotEqualConstraint c2 =
			new NotEqualConstraint(
				"Composite <> leaf",
				"throw new RuntimeException(\"Aggregate <> Aggregated\");",
				aggregate,
				aggregated,
				100,
				DefaultNoApproximations.getDefaultApproximations());

		pb.post(c1);
		pb.post(c2);

		return pb;
	}
}
