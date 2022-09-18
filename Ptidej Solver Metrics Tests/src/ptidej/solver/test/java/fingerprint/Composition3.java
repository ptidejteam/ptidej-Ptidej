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
package ptidej.solver.test.java.fingerprint;

import org.junit.Assert;
import ptidej.solver.Occurrence;
import ptidej.solver.test.data.problem.fingerprint.CompositionTest3;
import ptidej.solver.test.java.Primitive;

public final class Composition3 extends Primitive {
	private static Occurrence[] BuiltSolutions;

	public Composition3(final String name) {
		super(name);
	}
	protected void setUp() {
		if (Composition3.BuiltSolutions == null) {
			Composition3.BuiltSolutions =
				Primitive.automaticSolve(
					CompositionTest3.class,
					ptidej.solver.test.data.source.CompositionTest.class);
		}
	}
	public void testNumberOfSolutions() {
		Assert.assertEquals(
			"Number of solutions",
			1,
			Composition3.BuiltSolutions.length);
	}
	public void testSolution() {
		Assert.assertEquals(
			"AggregateClass1 == AggregateClass1",
			"AggregateClass1",
			Composition3.BuiltSolutions[0].getComponent(
				CompositionTest3.AGGREGATE).getDisplayValue());
		Assert.assertEquals(
			"AggregatedClass1 == AggregatedClass1",
			"AggregatedClass1",
			Composition3.BuiltSolutions[0].getComponent(
				CompositionTest3.AGGREGATED).getDisplayValue());
	}
	public void testSolutionPercentage() {
		for (int i = 0; i < Composition3.BuiltSolutions.length; i++) {
			Assert.assertEquals(
				"Solution with all constraints",
				100,
				Composition3.BuiltSolutions[i].getConfidence());
		}
	}
}
