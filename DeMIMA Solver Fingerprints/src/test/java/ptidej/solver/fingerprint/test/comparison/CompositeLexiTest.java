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
package ptidej.solver.fingerprint.test.comparison;

import org.junit.Assert;

import ptidej.solver.Occurrence;
import ptidej.solver.fingerprint.Rule;

public final class CompositeLexiTest extends Primitive {
	private static Occurrence[] BuiltSolutions;
	private static Occurrence[] BuiltSolutionsNoRule;

	public CompositeLexiTest(final String name) {
		super(name);
	}

	protected void setUp() {
		if (CompositeLexiTest.BuiltSolutions == null) {
			CompositeLexiTest.BuiltSolutions = Primitive.automaticSolve(
					ptidej.solver.fingerprint.problem.CompositeMotif.class,
					"../DeMIMA Solver Fingerprints/target/test-classes/Lexi v0.1.1 alph/bin/",
					"Lexi", Rule.C_LEAF_ROLE_1);
		}

		if (CompositeLexiTest.BuiltSolutionsNoRule == null) {
			CompositeLexiTest.BuiltSolutionsNoRule = Primitive.automaticSolve(
					ptidej.solver.java.problem.CompositeMotif.class,
					"../DeMIMA Solver Fingerprints/target/test-classes/Lexi v0.1.1 alph/bin/",
					"Lexi");
		}
	}

	public void testNumberSolution() {
		Assert.assertTrue("Less solution with rules.",
				CompositeLexiTest.BuiltSolutions.length < CompositeLexiTest.BuiltSolutionsNoRule.length);
	}
}
