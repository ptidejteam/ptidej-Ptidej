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
import ptidej.solver.java.test.problem.CompositionProblem;

public final class CompositionTest extends Primitive {
	private static Occurrence[] BuiltSolutions;

	public CompositionTest(final String name) {
		super(name);
	}
	protected void setUp() {
		if (CompositionTest.BuiltSolutions == null) {
			CompositionTest.BuiltSolutions =
				Primitive.automaticSolve(
					ptidej.solver.java.test.problem.CompositionProblem.class,
					ptidej.example.pattern.CompositionExample.class);
		}
	}
	public void testNumberOfSolutions() {
		Assert.assertEquals(
			"Number of solutions",
			2,
			CompositionTest.BuiltSolutions.length);
	}
	public void testSolution1() {
		Assert.assertEquals(
			"AggregateClass1 == AggregateClass1",
			"AggregateClass1",
			CompositionTest.BuiltSolutions[0].getComponent(
				CompositionProblem.AGGREGATE).getDisplayValue());
		Assert.assertEquals(
			"AggregatedClass1 == AggregatedClass1",
			"AggregatedClass1",
			CompositionTest.BuiltSolutions[0].getComponent(
				CompositionProblem.AGGREGATED).getDisplayValue());
	}
	public void testSolution2() {
		Assert.assertEquals(
			"AggregateClass2 == AggregateClass2",
			"AggregateClass2",
			CompositionTest.BuiltSolutions[1].getComponent(
				CompositionProblem.AGGREGATE).getDisplayValue());
		Assert.assertEquals(
			"AggregatedClass2 == AggregatedClass2",
			"AggregatedClass2",
			CompositionTest.BuiltSolutions[1].getComponent(
				CompositionProblem.AGGREGATED).getDisplayValue());
	}
	public void testSolutionPercentage() {
		for (int i = 0; i < CompositionTest.BuiltSolutions.length; i++) {
			Assert.assertEquals(
				"Solution with all constraints",
				100,
				CompositionTest.BuiltSolutions[i].getConfidence());
		}
	}
}
