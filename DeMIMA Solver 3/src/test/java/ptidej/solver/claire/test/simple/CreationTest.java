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
import ptidej.example.pattern.CreationExample;
import ptidej.occurrences.SolverKinds;
import ptidej.solver.Occurrence;
import ptidej.solver.claire.OccurrenceGenerator;
import ptidej.solver.claire.test.Primitive;
import ptidej.solver.claire.test.pattern.CreationPattern;

public final class CreationTest extends Primitive {
	private static Occurrence[] BuiltSolutions;

	public CreationTest(final String name) {
		super(name);
	}

	protected void setUp()
			throws IllegalAccessException, InstantiationException,
			IllegalArgumentException, InvocationTargetException,
			NoSuchMethodException, SecurityException {

		if (CreationTest.BuiltSolutions == null) {
			CreationTest.BuiltSolutions = this.testDesignPattern(CreationTest.class,
					Primitive.ALL_SOLUTIONS,
					((IDesignMotifModel) CreationPattern.class
							.getDeclaredConstructor().newInstance()).getName(),
					CreationExample.class, SolverKinds.SOLVER_AUTOMATIC,
					OccurrenceGenerator.PROBLEM_AC4);
		}
	}

	public void testNumberOfSolutions() {
		Assert.assertEquals("Number of solutions", 2,
				CreationTest.BuiltSolutions.length);
	}

	public void testSolution1() {
		Assert.assertEquals("Compiler -*--> ProgramNodeBuilder", "Compiler",
				CreationTest.BuiltSolutions[0].getComponent(CreationPattern.CREATOR)
						.getDisplayValue());
		Assert.assertEquals("Compiler -*--> ProgramNodeBuilder",
				"ProgramNodeBuilder",
				CreationTest.BuiltSolutions[0].getComponent(CreationPattern.CREATED)
						.getDisplayValue());
	}

	public void testSolution2() {
		Assert.assertEquals("ProgramNodeBuilder -*--> ProgramNode",
				"ProgramNodeBuilder",
				CreationTest.BuiltSolutions[1].getComponent(CreationPattern.CREATOR)
						.getDisplayValue());
		Assert.assertEquals("ProgramNodeBuilder -*--> ProgramNode",
				"ProgramNode",
				CreationTest.BuiltSolutions[1].getComponent(CreationPattern.CREATED)
						.getDisplayValue());
	}

	public void testSolutionPercentage() {
		for (int i = 0; i < CreationTest.BuiltSolutions.length; i++) {
			Assert.assertEquals("Solution with all constraints", 100,
					CreationTest.BuiltSolutions[i].getConfidence());
		}
	}
}
