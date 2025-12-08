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
package ptidej.solver.java.test.simple;

import org.junit.Assert;
import ptidej.solver.Occurrence;
import ptidej.solver.java.test.problem.CreationProblem;

public final class CreationTest extends Primitive {
	private static Occurrence[] BuiltSolutions;

	public CreationTest(final String name) {
		super(name);
	}
	protected void setUp() {
		if (CreationTest.BuiltSolutions == null) {
			CreationTest.BuiltSolutions =
				Primitive.automaticSolve(
					ptidej.solver.java.test.problem.CreationProblem.class,
					ptidej.example.pattern.CreationExample.class);
		}
	}
	public void testNumberOfSolutions() {
		Assert.assertEquals(
			"Number of solutions",
			2,
			CreationTest.BuiltSolutions.length);
	}
	public void testSolution1() {
		Assert.assertEquals(
			"Compiler -*--> ProgramNodeBuilder",
			"Compiler",
			CreationTest.BuiltSolutions[0]
				.getComponent(CreationProblem.CREATOR)
				.getDisplayValue());
		Assert.assertEquals(
			"Compiler -*--> ProgramNodeBuilder",
			"ProgramNodeBuilder",
			CreationTest.BuiltSolutions[0]
				.getComponent(CreationProblem.CREATED)
				.getDisplayValue());
	}
	public void testSolution2() {
		Assert.assertEquals(
			"ProgramNodeBuilder -*--> ProgramNode",
			"ProgramNodeBuilder",
			CreationTest.BuiltSolutions[1]
				.getComponent(CreationProblem.CREATOR)
				.getDisplayValue());
		Assert.assertEquals(
			"ProgramNodeBuilder -*--> ProgramNode",
			"ProgramNode",
			CreationTest.BuiltSolutions[1]
				.getComponent(CreationProblem.CREATED)
				.getDisplayValue());
	}
	public void testSolutionPercentage() {
		for (int i = 0; i < CreationTest.BuiltSolutions.length; i++) {
			Assert.assertEquals(
				"Solution with all constraints",
				100,
				CreationTest.BuiltSolutions[i].getConfidence());
		}
	}
}
