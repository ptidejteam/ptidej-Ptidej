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
package ptidej.solver.test.data.problem;

import java.util.List;
import ptidej.solver.Problem;
import ptidej.solver.Variable;
import ptidej.solver.approximation.DefaultNoApproximations;
import ptidej.solver.constraint.repository.NotEqualConstraint;
import ptidej.solver.constraint.repository.UseConstraint;

/**
 * @author Yann-Gaël Guéhéneuc
 * @since  2004/09/05 
 */
public final class UseTest {
	public static final char[] CALLEE = "Callee".toCharArray();
	public static final char[] CALLER = "Caller".toCharArray();

	public static Problem getProblem(final List allEntities) {
		final Problem pb =
			new Problem(90, "Strict Inheritance Test", allEntities);

		final Variable caller = new Variable(pb, UseTest.CALLER, true);
		final Variable callee = new Variable(pb, UseTest.CALLEE, true);

		pb.addVar(caller);
		pb.addVar(callee);

		final UseConstraint c1 =
			new UseConstraint(
				"Caller ----> Callee",
				"throw new RuntimeException(\"Caller ----> Callee\");",
				caller,
				callee,
				100,
				DefaultNoApproximations.getDefaultApproximations());
		final NotEqualConstraint c2 =
			new NotEqualConstraint(
				"Caller <> Callee",
				"throw new RuntimeException(\"Caller <> Callee\");",
				caller,
				callee,
				100,
				DefaultNoApproximations.getDefaultApproximations());

		pb.post(c1);
		pb.post(c2);

		return pb;
	}
}
