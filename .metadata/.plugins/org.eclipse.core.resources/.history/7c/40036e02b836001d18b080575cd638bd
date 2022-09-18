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
package ptidej.solver.test.claire.approximate.combinatorial;

import java.lang.reflect.InvocationTargetException;

import org.junit.Assert;
import padl.motif.IDesignMotifModel;
import ptidej.solver.Occurrence;
import ptidej.solver.OccurrenceGenerator;
import ptidej.solver.test.claire.Primitive;
import ptidej.solver.test.data.pattern.BadCompositionTest;
import ptidej.solver.test.data.source.BadCompositionTest3;

public final class BadComposition3 extends Primitive {
	private Occurrence[] builtSolutions;

	public BadComposition3(final String name) {
		super(name);
	}
	protected void setUp() throws IllegalAccessException,
			InstantiationException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {

		this.builtSolutions =
			this.testDesignPattern(
				BadComposition3.class,
				Primitive.ALL_SOLUTIONS,
				((IDesignMotifModel) BadCompositionTest.class.getDeclaredConstructor().newInstance())
					.getName(),
				BadCompositionTest3.class,
				OccurrenceGenerator.SOLVER_COMBINATORIAL_AUTOMATIC,
				OccurrenceGenerator.PROBLEM_AC4);
	}
	public void testNumberOfSolutions() {
		Assert.assertEquals(
			"Number of solutions",
			2,
			this.builtSolutions.length);
	}
	public void testSolution1() {
		Assert.assertEquals(
			"AggregateClass1 == AggregateClass1",
			"AggregateClass1",
			this.builtSolutions[0]
				.getComponent(BadCompositionTest.AGGREGATE)
				.getDisplayValue());
		Assert.assertEquals(
			"AggregatedClass1 == AggregatedClass1",
			"AggregatedClass1",
			this.builtSolutions[0]
				.getComponent(BadCompositionTest.AGGREGATED)
				.getDisplayValue());
	}
	public void testSolution2() {
		Assert.assertEquals(
			"AggregateClass2 == AggregateClass2",
			"AggregateClass2",
			this.builtSolutions[1]
				.getComponent(BadCompositionTest.AGGREGATE)
				.getDisplayValue());
		Assert.assertEquals(
			"AggregatedClass2 == AggregatedClass2",
			"AggregatedClass2",
			this.builtSolutions[1]
				.getComponent(BadCompositionTest.AGGREGATED)
				.getDisplayValue());
	}
	public void testSolutionPercentage() {
		Assert.assertEquals(
			"Solution with all constraints",
			100,
			this.builtSolutions[0].getConfidence());
		Assert.assertEquals(
			"Solution with all constraints",
			100,
			this.builtSolutions[1].getConfidence());
	}
}
