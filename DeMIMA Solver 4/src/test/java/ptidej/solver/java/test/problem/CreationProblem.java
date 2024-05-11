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
package ptidej.solver.java.test.problem;

import java.util.List;

import ptidej.solver.java.Problem;
import ptidej.solver.java.Variable;
import ptidej.solver.java.approximation.DefaultNoApproximations;
import ptidej.solver.java.constraint.repository.CreationConstraint;
import ptidej.solver.java.constraint.repository.NotEqualConstraint;

/**
 * @author Yann-Gaël Guéhéneuc
 * @since  2004/09/05 
 */
public final class CreationProblem {
	public static final char[] CREATED = "Created".toCharArray();
	public static final char[] CREATOR = "Creator".toCharArray();

	public static Problem getProblem(final List allEntities) {
		final Problem pb = new Problem(90, "Creation Test", allEntities);

		final Variable creator = new Variable(pb, CreationProblem.CREATOR, true);
		final Variable created = new Variable(pb, CreationProblem.CREATED, true);

		pb.addVar(creator);
		pb.addVar(created);

		final CreationConstraint c1 =
			new CreationConstraint(
				"Creator -*--> Created",
				"throw new RuntimeException(\"Creator ----> Created\");",
				creator,
				created,
				100,
				DefaultNoApproximations.getDefaultApproximations());
		final NotEqualConstraint c2 =
			new NotEqualConstraint(
				"Creator <> Created",
				"throw new RuntimeException(\"Creator <> Created\");",
				creator,
				created,
				100,
				DefaultNoApproximations.getDefaultApproximations());

		pb.post(c1);
		pb.post(c2);

		return pb;
	}
}
