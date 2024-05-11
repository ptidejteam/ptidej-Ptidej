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
package ptidej.solver.java.test.simple;

import org.junit.Assert;

import ptidej.solver.Occurrence;
import ptidej.solver.java.test.data.pattern.CompositionAndInheritance;
import ptidej.solver.java.test.problem.CompositionAndInheritanceProblem;

public final class CompositionAndInheritanceTest extends Primitive {
	private static Occurrence[] BuiltSolutions;

	public CompositionAndInheritanceTest(final String name) {
		super(name);
	}

	protected void setUp() {
		if (CompositionAndInheritanceTest.BuiltSolutions == null) {
			CompositionAndInheritanceTest.BuiltSolutions = Primitive
					.automaticSolve(CompositionAndInheritanceProblem.class,
							CompositionAndInheritance.class);
		}
	}

	public void testNumberOfSolutions() {
		Assert.assertEquals("Number of solutions", 1,
				CompositionAndInheritanceTest.BuiltSolutions.length);
	}

	public void testSolution() {
		Assert.assertEquals("SuperEntity", "java.lang.Object",
				CompositionAndInheritanceTest.BuiltSolutions[0]
						.getComponent(
								CompositionAndInheritanceProblem.SUPER_ENTITY)
						.getDisplayValue());
		Assert.assertEquals("SubEntity", "B",
				CompositionAndInheritanceTest.BuiltSolutions[0]
						.getComponent(
								CompositionAndInheritanceProblem.SUB_ENTITY)
						.getDisplayValue());
	}

	public void testSolutionPercentage() {
		for (int i = 0; i < CompositionAndInheritanceTest.BuiltSolutions.length; i++) {
			Assert.assertEquals("Percentages of the solutions", 20,
					CompositionAndInheritanceTest.BuiltSolutions[i]
							.getConfidence());
		}
	}
}
