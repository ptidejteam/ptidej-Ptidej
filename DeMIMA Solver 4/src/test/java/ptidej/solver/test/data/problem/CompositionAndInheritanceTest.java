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
import ptidej.solver.approximation.DefaultInheritanceApproximations;
import ptidej.solver.approximation.DefaultNoApproximations;
import ptidej.solver.constraint.repository.ContainerCompositionConstraint;
import ptidej.solver.constraint.repository.StrictInheritanceConstraint;

/**
 * @author Yann-Gaël Guéhéneuc
 * @since  2004/09/05 
 */
public final class CompositionAndInheritanceTest {
	public static final char[] SUPER_ENTITY = "SuperEntity".toCharArray();
	public static final char[] SUB_ENTITY = "SubEntity".toCharArray();

	public static Problem getProblem(final List allEntities) {
		final Problem pb =
			new Problem(
				90,
				"Composition and Strict Inheritance Test",
				allEntities);

		final Variable subEntity =
			new Variable(pb, CompositionAndInheritanceTest.SUB_ENTITY, true);
		final Variable superEntity =
			new Variable(pb, CompositionAndInheritanceTest.SUPER_ENTITY, true);

		pb.addVar(subEntity);
		pb.addVar(superEntity);

		final StrictInheritanceConstraint c1 =
			new StrictInheritanceConstraint(
				"SuperEntity -|>- SubEntity",
				"throw new RuntimeException(\"SuperEntity -|>- SubEntity\");",
				subEntity,
				superEntity,
				50,
				DefaultInheritanceApproximations.getDefaultApproximations());
		final ContainerCompositionConstraint c2 =
			new ContainerCompositionConstraint(
				"SubEntity <#>-> SuperEntity",
				"throw new RuntimeException(\"SubEntity <#>-> SuperEntity\");",
				subEntity,
				superEntity,
				100,
				DefaultNoApproximations.getDefaultApproximations());

		pb.post(c1);
		pb.post(c2);

		return pb;
	}
}
