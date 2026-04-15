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

public final class CompositeJUnitTest extends Primitive {
	private static Occurrence[] BuiltSolutions;
	private static Occurrence[] BuiltSolutionsNoRule;

	public CompositeJUnitTest(final String name) {
		super(name);
	}

	protected void setUp() {
		if (CompositeJUnitTest.BuiltSolutionsNoRule == null) {
			CompositeJUnitTest.BuiltSolutionsNoRule = Primitive.automaticSolve(
					ptidej.solver.java.problem.CompositeMotif.class,
					"../DeMIMA Solver Fingerprints/target/test-classes/JUnit v3.7/bin/",
					"JUnit");
		}

		if (CompositeJUnitTest.BuiltSolutions == null) {
			CompositeJUnitTest.BuiltSolutions = Primitive.automaticSolve(
					ptidej.solver.fingerprint.problem.CompositeMotif.class,
					"../DeMIMA Solver Fingerprints/target/test-classes/JUnit v3.7/bin/",
					"JUnit", Rule.C_LEAF_ROLE_1);
		}
	}

	public void testNumberSolution() {
		Assert.assertTrue("Less solution with rules.",
				CompositeJUnitTest.BuiltSolutions.length < CompositeJUnitTest.BuiltSolutionsNoRule.length);
	}
}
