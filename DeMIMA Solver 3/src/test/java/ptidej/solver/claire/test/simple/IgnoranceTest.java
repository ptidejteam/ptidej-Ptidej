/*******************************************************************************
 * Copyright (c) 2001-2014 Yann-Gaël Guéhéneuc  and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/old-licenses/gpl-2.0.html
 * 
 * Contributors:
 *     Yann-Gaël Guéhéneuc  and others, see in file; API and its implementation
 ******************************************************************************/
package ptidej.solver.claire.test.simple;

import java.lang.reflect.InvocationTargetException;

import org.junit.Assert;

import padl.motif.IDesignMotifModel;
import ptidej.example.pattern.UseExample;
import ptidej.solver.Occurrence;
import ptidej.solver.claire.OccurrenceGenerator;
import ptidej.solver.claire.test.Primitive;
import ptidej.solver.claire.test.pattern.IgnoranceExample;

public final class IgnoranceTest extends Primitive {
	private static Occurrence[] BuiltSolutions;

	public IgnoranceTest(final String name) {
		super(name);
	}

	protected void setUp()
			throws IllegalAccessException, InstantiationException,
			IllegalArgumentException, InvocationTargetException,
			NoSuchMethodException, SecurityException {

		if (IgnoranceTest.BuiltSolutions == null) {
			IgnoranceTest.BuiltSolutions = this.testDesignPattern(IgnoranceTest.class,
					Primitive.ALL_SOLUTIONS,
					((IDesignMotifModel) IgnoranceExample.class
							.getDeclaredConstructor().newInstance()).getName(),
					UseExample.class, OccurrenceGenerator.SOLVER_AUTOMATIC,
					OccurrenceGenerator.PROBLEM_AC4);
		}
	}

	public void testNumberOfSolutions() {
		Assert.assertEquals("Number of solutions", 4,
				IgnoranceTest.BuiltSolutions.length);
	}

	public void testSolution1() {
		Assert.assertEquals("Compiler -/--> ProgramNode", "Compiler",
				IgnoranceTest.BuiltSolutions[0].getComponent(IgnoranceExample.A)
						.getDisplayValue());
		Assert.assertEquals("Compiler -/--> ProgramNode", "ProgramNode",
				IgnoranceTest.BuiltSolutions[0].getComponent(IgnoranceExample.B)
						.getDisplayValue());
	}

	public void testSolution2() {
		Assert.assertEquals("ProgramNodeBuilder -/--> Compiler",
				"ProgramNodeBuilder", IgnoranceTest.BuiltSolutions[1]
						.getComponent(IgnoranceExample.A).getDisplayValue());
		Assert.assertEquals("ProgramNodeBuilder -/--> Compiler", "Compiler",
				IgnoranceTest.BuiltSolutions[1].getComponent(IgnoranceExample.B)
						.getDisplayValue());
	}

	public void testSolution3() {
		Assert.assertEquals("ProgramNode -/--> Compiler", "ProgramNode",
				IgnoranceTest.BuiltSolutions[2].getComponent(IgnoranceExample.A)
						.getDisplayValue());
		Assert.assertEquals("ProgramNode -/--> Compiler", "Compiler",
				IgnoranceTest.BuiltSolutions[2].getComponent(IgnoranceExample.B)
						.getDisplayValue());
	}

	public void testSolution4() {
		Assert.assertEquals("ProgramNode -/--> ProgramNodeBuilder",
				"ProgramNode", IgnoranceTest.BuiltSolutions[3]
						.getComponent(IgnoranceExample.A).getDisplayValue());
		Assert.assertEquals("ProgramNode -/--> ProgramNodeBuilder",
				"ProgramNodeBuilder", IgnoranceTest.BuiltSolutions[3]
						.getComponent(IgnoranceExample.B).getDisplayValue());
	}

	public void testSolutionPercentage() {
		for (int i = 0; i < IgnoranceTest.BuiltSolutions.length; i++) {
			Assert.assertEquals("Solution with all constraints", 100,
					IgnoranceTest.BuiltSolutions[i].getConfidence());
		}
	}
}
