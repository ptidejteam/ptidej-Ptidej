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
package ptidej.solver.test.claire.simple;

import java.lang.reflect.InvocationTargetException;

import org.junit.Assert;
import padl.motif.IDesignMotifModel;
import ptidej.solver.Occurrence;
import ptidej.solver.OccurrenceGenerator;
import ptidej.solver.test.claire.Primitive;
import ptidej.solver.test.data.pattern.IgnoranceTest;
import ptidej.solver.test.data.source.UseTest;

public final class Ignorance extends Primitive {
	private static Occurrence[] BuiltSolutions;

	public Ignorance(final String name) {
		super(name);
	}
	protected void setUp() throws IllegalAccessException,
			InstantiationException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {

		if (Ignorance.BuiltSolutions == null) {
			Ignorance.BuiltSolutions =
				this.testDesignPattern(
					Ignorance.class,
					Primitive.ALL_SOLUTIONS,
					((IDesignMotifModel) IgnoranceTest.class.getDeclaredConstructor().newInstance())
						.getName(),
					UseTest.class,
					OccurrenceGenerator.SOLVER_AUTOMATIC,
					OccurrenceGenerator.PROBLEM_AC4);
		}
	}
	public void testNumberOfSolutions() {
		Assert.assertEquals(
			"Number of solutions",
			4,
			Ignorance.BuiltSolutions.length);
	}
	public void testSolution1() {
		Assert.assertEquals(
			"Compiler -/--> ProgramNode",
			"Compiler",
			Ignorance.BuiltSolutions[0]
				.getComponent(IgnoranceTest.A)
				.getDisplayValue());
		Assert.assertEquals(
			"Compiler -/--> ProgramNode",
			"ProgramNode",
			Ignorance.BuiltSolutions[0]
				.getComponent(IgnoranceTest.B)
				.getDisplayValue());
	}
	public void testSolution2() {
		Assert.assertEquals(
			"ProgramNodeBuilder -/--> Compiler",
			"ProgramNodeBuilder",
			Ignorance.BuiltSolutions[1]
				.getComponent(IgnoranceTest.A)
				.getDisplayValue());
		Assert.assertEquals(
			"ProgramNodeBuilder -/--> Compiler",
			"Compiler",
			Ignorance.BuiltSolutions[1]
				.getComponent(IgnoranceTest.B)
				.getDisplayValue());
	}
	public void testSolution3() {
		Assert.assertEquals(
			"ProgramNode -/--> Compiler",
			"ProgramNode",
			Ignorance.BuiltSolutions[2]
				.getComponent(IgnoranceTest.A)
				.getDisplayValue());
		Assert.assertEquals(
			"ProgramNode -/--> Compiler",
			"Compiler",
			Ignorance.BuiltSolutions[2]
				.getComponent(IgnoranceTest.B)
				.getDisplayValue());
	}
	public void testSolution4() {
		Assert.assertEquals(
			"ProgramNode -/--> ProgramNodeBuilder",
			"ProgramNode",
			Ignorance.BuiltSolutions[3]
				.getComponent(IgnoranceTest.A)
				.getDisplayValue());
		Assert.assertEquals(
			"ProgramNode -/--> ProgramNodeBuilder",
			"ProgramNodeBuilder",
			Ignorance.BuiltSolutions[3]
				.getComponent(IgnoranceTest.B)
				.getDisplayValue());
	}
	public void testSolutionPercentage() {
		for (int i = 0; i < Ignorance.BuiltSolutions.length; i++) {
			Assert.assertEquals(
				"Solution with all constraints",
				100,
				Ignorance.BuiltSolutions[i].getConfidence());
		}
	}
}
