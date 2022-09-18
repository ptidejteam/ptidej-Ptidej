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
import ptidej.solver.test.data.pattern.CreationTest;

public final class Creation extends Primitive {
	private static Occurrence[] BuiltSolutions;

	public Creation(final String name) {
		super(name);
	}
	protected void setUp() throws IllegalAccessException,
			InstantiationException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {

		if (Creation.BuiltSolutions == null) {
			Creation.BuiltSolutions =
				this
					.testDesignPattern(
						Creation.class,
						Primitive.ALL_SOLUTIONS,
						((IDesignMotifModel) ptidej.solver.test.data.pattern.CreationTest.class
							.getDeclaredConstructor().newInstance()).getName(),
						ptidej.solver.test.data.source.CreationTest.class,
						OccurrenceGenerator.SOLVER_AUTOMATIC,
						OccurrenceGenerator.PROBLEM_AC4);
		}
	}
	public void testNumberOfSolutions() {
		Assert.assertEquals(
			"Number of solutions",
			2,
			Creation.BuiltSolutions.length);
	}
	public void testSolution1() {
		Assert.assertEquals(
			"Compiler -*--> ProgramNodeBuilder",
			"Compiler",
			Creation.BuiltSolutions[0]
				.getComponent(CreationTest.CREATOR)
				.getDisplayValue());
		Assert.assertEquals(
			"Compiler -*--> ProgramNodeBuilder",
			"ProgramNodeBuilder",
			Creation.BuiltSolutions[0]
				.getComponent(CreationTest.CREATED)
				.getDisplayValue());
	}
	public void testSolution2() {
		Assert.assertEquals(
			"ProgramNodeBuilder -*--> ProgramNode",
			"ProgramNodeBuilder",
			Creation.BuiltSolutions[1]
				.getComponent(CreationTest.CREATOR)
				.getDisplayValue());
		Assert.assertEquals(
			"ProgramNodeBuilder -*--> ProgramNode",
			"ProgramNode",
			Creation.BuiltSolutions[1]
				.getComponent(CreationTest.CREATED)
				.getDisplayValue());
	}
	public void testSolutionPercentage() {
		for (int i = 0; i < Creation.BuiltSolutions.length; i++) {
			Assert.assertEquals(
				"Solution with all constraints",
				100,
				Creation.BuiltSolutions[i].getConfidence());
		}
	}
}
