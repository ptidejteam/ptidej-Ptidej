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

public final class Creation extends Primitive {
	private static Occurrence[] BuiltSolutions;

	public Creation(final String name) {
		super(name);
	}
	protected void setUp() {
		if (Creation.BuiltSolutions == null) {
			Creation.BuiltSolutions =
				Primitive.automaticSolve(
					ptidej.solver.java.test.problem.CreationProblem.class,
					ptidej.example.pattern.CreationExample.class);
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
				.getComponent(CreationProblem.CREATOR)
				.getDisplayValue());
		Assert.assertEquals(
			"Compiler -*--> ProgramNodeBuilder",
			"ProgramNodeBuilder",
			Creation.BuiltSolutions[0]
				.getComponent(CreationProblem.CREATED)
				.getDisplayValue());
	}
	public void testSolution2() {
		Assert.assertEquals(
			"ProgramNodeBuilder -*--> ProgramNode",
			"ProgramNodeBuilder",
			Creation.BuiltSolutions[1]
				.getComponent(CreationProblem.CREATOR)
				.getDisplayValue());
		Assert.assertEquals(
			"ProgramNodeBuilder -*--> ProgramNode",
			"ProgramNode",
			Creation.BuiltSolutions[1]
				.getComponent(CreationProblem.CREATED)
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
