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
package ptidej.solver.fingerprint.test.simple;

import org.junit.Assert;

import ptidej.example.pattern.CompositionExample;
import ptidej.solver.Occurrence;
import ptidej.solver.fingerprint.test.problem.CompositionProblem4;

public final class CompositionTest4 extends Primitive {
	private static Occurrence[] BuiltSolutions;

	public CompositionTest4(final String name) {
		super(name);
	}

	protected void setUp() {
		if (CompositionTest4.BuiltSolutions == null) {
			CompositionTest4.BuiltSolutions = Primitive.automaticSolve(
					CompositionProblem4.class, CompositionExample.class);
		}
	}

	public void testNumberOfSolutions() {
		Assert.assertEquals("Number of solutions", 2,
				CompositionTest4.BuiltSolutions.length);
	}

	public void testSolution1() {
		Assert.assertEquals("AggregateClass1 == AggregateClass1",
				"AggregateClass1",
				CompositionTest4.BuiltSolutions[0]
						.getComponent(CompositionProblem4.AGGREGATE)
						.getDisplayValue());
		Assert.assertEquals("AggregatedClass1 == AggregatedClass1",
				"AggregatedClass1",
				CompositionTest4.BuiltSolutions[0]
						.getComponent(CompositionProblem4.AGGREGATED)
						.getDisplayValue());
	}

	public void testSolution2() {
		Assert.assertEquals("AggregateClass2 == AggregateClass2",
				"AggregateClass2",
				CompositionTest4.BuiltSolutions[1]
						.getComponent(CompositionProblem4.AGGREGATE)
						.getDisplayValue());
		Assert.assertEquals("AggregatedClass2 == AggregatedClass2",
				"AggregatedClass2",
				CompositionTest4.BuiltSolutions[1]
						.getComponent(CompositionProblem4.AGGREGATED)
						.getDisplayValue());
	}

	public void testSolutionPercentage() {
		for (int i = 0; i < CompositionTest4.BuiltSolutions.length; i++) {
			Assert.assertEquals("Solution with all constraints", 100,
					CompositionTest4.BuiltSolutions[i].getConfidence());
		}
	}
}
