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

public final class CompositeJUnit extends Primitive {
	private static Occurrence[] BuiltSolutions;
	private static Occurrence[] BuiltSolutionsNoRule;

	public CompositeJUnit(final String name) {
		super(name);
	}
	protected void setUp() {
		if (CompositeJUnit.BuiltSolutions == null) {
			CompositeJUnit.BuiltSolutions =
				Primitive.automaticSolve(
					ptidej.solver.fingerprint.problem.CompositeMotif.class,
					"../JUnit v3.7/bin/",
					"JUnit",
					Rule.C_LEAF_ROLE_1);
		}
		if (CompositeJUnit.BuiltSolutionsNoRule == null) {
			CompositeJUnit.BuiltSolutionsNoRule =
				Primitive.automaticSolve(
					ptidej.solver.java.problem.CompositeMotif.class,
					"../JUnit v3.7/bin/",
					"JUnit");
		}
	}

	public void testNumberSolution() {
		Assert
			.assertTrue(
				"Less solution with rules.",
				CompositeJUnit.BuiltSolutions.length < CompositeJUnit.BuiltSolutionsNoRule.length);
	}
}
