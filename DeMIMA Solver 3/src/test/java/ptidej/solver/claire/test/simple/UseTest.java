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
import ptidej.occurrences.SolverKinds;
import ptidej.solver.Occurrence;
import ptidej.solver.claire.OccurrenceGenerator;
import ptidej.solver.claire.test.Primitive;
import ptidej.solver.claire.test.pattern.UsePattern;

public final class UseTest extends Primitive {
	private static Occurrence[] BuiltSolutions;

	public UseTest(final String name) {
		super(name);
	}

	protected void setUp()
			throws IllegalAccessException, InstantiationException,
			IllegalArgumentException, InvocationTargetException,
			NoSuchMethodException, SecurityException {

		if (UseTest.BuiltSolutions == null) {
			UseTest.BuiltSolutions = this.testDesignPattern(UseTest.class,
					Primitive.ALL_SOLUTIONS,
					((IDesignMotifModel) UsePattern.class
							.getDeclaredConstructor().newInstance()).getName(),
					UseExample.class, SolverKinds.SOLVER_AUTOMATIC,
					OccurrenceGenerator.PROBLEM_AC4);
		}
	}

	public void testNumberOfSolutions() {
		Assert.assertEquals("Number of solutions", 2,
				UseTest.BuiltSolutions.length);
	}

	public void testSolution1() {
		Assert.assertEquals("Compiler ----> ProgramNodeBuilder", "Compiler",
				UseTest.BuiltSolutions[0].getComponent(UsePattern.CALLER)
						.getDisplayValue());
		Assert.assertEquals("Compiler ----> ProgramNodeBuilder",
				"ProgramNodeBuilder", UseTest.BuiltSolutions[0]
						.getComponent(UsePattern.CALLEE).getDisplayValue());
	}

	public void testSolution2() {
		Assert.assertEquals("ProgramNodeBuilder ----> ProgramNode",
				"ProgramNodeBuilder", UseTest.BuiltSolutions[1]
						.getComponent(UsePattern.CALLER).getDisplayValue());
		Assert.assertEquals("ProgramNodeBuilder ----> ProgramNode",
				"ProgramNode", UseTest.BuiltSolutions[1]
						.getComponent(UsePattern.CALLEE).getDisplayValue());
	}

	public void testSolutionPercentage() {
		for (int i = 0; i < UseTest.BuiltSolutions.length; i++) {
			Assert.assertEquals("Solution with all constraints", 100,
					UseTest.BuiltSolutions[i].getConfidence());
		}
	}
}
