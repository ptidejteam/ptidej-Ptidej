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
package ptidej.solver.test.java.combinatorial;

import org.junit.Assert;
import ptidej.solver.Occurrence;
import ptidej.solver.test.data.problem.CompositionAndInheritanceTest;
import ptidej.solver.test.java.Primitive;

public final class CompositionAndInheritance extends Primitive {
	private static Occurrence[] BuiltSolutions;

	public CompositionAndInheritance(final String name) {
		super(name);
	}
	protected void setUp() {
		if (CompositionAndInheritance.BuiltSolutions == null) {
			CompositionAndInheritance.BuiltSolutions =
				Primitive
					.automaticSolve(
						ptidej.solver.test.data.problem.CompositionAndInheritanceTest.class,
						ptidej.solver.test.data.source.CompositionAndInheritanceTest.class);
		}
	}
	public void testNumberOfSolutions() {
		Assert.assertEquals(
			"Number of solutions",
			1,
			CompositionAndInheritance.BuiltSolutions.length);
	}
	public void testSolution() {
		Assert.assertEquals(
			"SuperEntity",
			"java.lang.Object",
			CompositionAndInheritance.BuiltSolutions[0].getComponent(
				CompositionAndInheritanceTest.SUPER_ENTITY).getDisplayValue());
		Assert.assertEquals(
			"SubEntity",
			"B",
			CompositionAndInheritance.BuiltSolutions[0].getComponent(
				CompositionAndInheritanceTest.SUB_ENTITY).getDisplayValue());
	}
	public void testSolutionPercentage() {
		for (int i = 0; i < CompositionAndInheritance.BuiltSolutions.length; i++) {
			Assert.assertEquals(
				"Percentages of the solutions",
				20,
				CompositionAndInheritance.BuiltSolutions[i].getConfidence());
		}
	}
}
