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
import ptidej.solver.fingerprint.test.problem.CompositionProblem3;

public final class CompositionTest3 extends Primitive {
	private static Occurrence[] BuiltSolutions;

	public CompositionTest3(final String name) {
		super(name);
	}

	protected void setUp() {
		if (CompositionTest3.BuiltSolutions == null) {
			CompositionTest3.BuiltSolutions = Primitive.automaticSolve(
					CompositionProblem3.class, CompositionExample.class);
		}
	}

	public void testNumberOfSolutions() {
		Assert.assertEquals("Number of solutions", 1,
				CompositionTest3.BuiltSolutions.length);
	}

	public void testSolution() {
		Assert.assertEquals("AggregateClass1 == AggregateClass1",
				"AggregateClass1",
				CompositionTest3.BuiltSolutions[0]
						.getComponent(CompositionProblem3.AGGREGATE)
						.getDisplayValue());
		Assert.assertEquals("AggregatedClass1 == AggregatedClass1",
				"AggregatedClass1",
				CompositionTest3.BuiltSolutions[0]
						.getComponent(CompositionProblem3.AGGREGATED)
						.getDisplayValue());
	}

	public void testSolutionPercentage() {
		for (int i = 0; i < CompositionTest3.BuiltSolutions.length; i++) {
			Assert.assertEquals("Solution with all constraints", 100,
					CompositionTest3.BuiltSolutions[i].getConfidence());
		}
	}
}
