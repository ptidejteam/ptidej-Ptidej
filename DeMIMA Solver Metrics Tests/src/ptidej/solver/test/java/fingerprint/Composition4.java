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
import ptidej.solver.test.data.problem.fingerprint.CompositionTest4;
import ptidej.solver.test.java.Primitive;

public final class Composition4 extends Primitive {
	private static Occurrence[] BuiltSolutions;

	public Composition4(final String name) {
		super(name);
	}
	protected void setUp() {
		if (Composition4.BuiltSolutions == null) {
			Composition4.BuiltSolutions =
				Primitive.automaticSolve(
					CompositionTest4.class,
					ptidej.solver.test.data.source.CompositionTest.class);
		}
	}
	public void testNumberOfSolutions() {
		Assert.assertEquals(
			"Number of solutions",
			2,
			Composition4.BuiltSolutions.length);
	}
	public void testSolution1() {
		Assert.assertEquals(
			"AggregateClass1 == AggregateClass1",
			"AggregateClass1",
			Composition4.BuiltSolutions[0].getComponent(
				CompositionTest4.AGGREGATE).getDisplayValue());
		Assert.assertEquals(
			"AggregatedClass1 == AggregatedClass1",
			"AggregatedClass1",
			Composition4.BuiltSolutions[0].getComponent(
				CompositionTest4.AGGREGATED).getDisplayValue());
	}
	public void testSolution2() {
		Assert.assertEquals(
			"AggregateClass2 == AggregateClass2",
			"AggregateClass2",
			Composition4.BuiltSolutions[1].getComponent(
				CompositionTest4.AGGREGATE).getDisplayValue());
		Assert.assertEquals(
			"AggregatedClass2 == AggregatedClass2",
			"AggregatedClass2",
			Composition4.BuiltSolutions[1].getComponent(
				CompositionTest4.AGGREGATED).getDisplayValue());
	}
	public void testSolutionPercentage() {
		for (int i = 0; i < Composition4.BuiltSolutions.length; i++) {
			Assert.assertEquals(
				"Solution with all constraints",
				100,
				Composition4.BuiltSolutions[i].getConfidence());
		}
	}
}
