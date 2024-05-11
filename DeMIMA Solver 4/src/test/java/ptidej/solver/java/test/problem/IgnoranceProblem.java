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
import ptidej.solver.java.constraint.repository.IgnoranceConstraint;
import ptidej.solver.java.constraint.repository.NotEqualConstraint;

/**
 * @author Yann-Gaël Guéhéneuc
 * @since  2004/09/05 
 */
public final class IgnoranceProblem {
	public static final char[] A = "A".toCharArray();
	public static final char[] B = "B".toCharArray();

	public static Problem getProblem(final List allEntities) {
		final Problem pb = new Problem(90, "Ignorance Test", allEntities);

		final Variable a = new Variable(pb, IgnoranceProblem.A, true);
		final Variable b = new Variable(pb, IgnoranceProblem.B, true);

		pb.addVar(a);
		pb.addVar(b);

		final IgnoranceConstraint c1 =
			new IgnoranceConstraint(
				"A -/--> B",
				"throw new RuntimeException(\"A -/--> B\");",
				a,
				b,
				100,
				DefaultNoApproximations.getDefaultApproximations());
		final NotEqualConstraint c2 =
			new NotEqualConstraint(
				"A <> B",
				"throw new RuntimeException(\"A <> B\");",
				a,
				b,
				100,
				DefaultNoApproximations.getDefaultApproximations());

		pb.post(c1);
		pb.post(c2);

		return pb;
	}
}
