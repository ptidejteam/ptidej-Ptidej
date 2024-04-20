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

import ptidej.example.pattern.InheritanceExample;
import ptidej.solver.Occurrence;
import ptidej.solver.java.test.problem.StrictInheritanceProblem;

public final class StrictInheritance extends Primitive {
	private static Occurrence[] BuiltSolutions;

	public StrictInheritance(final String name) {
		super(name);
	}
	protected void setUp() {
		if (StrictInheritance.BuiltSolutions == null) {
			StrictInheritance.BuiltSolutions =
				Primitive.automaticSolve(
					StrictInheritanceProblem.class,
					InheritanceExample.class);
		}
	}
	public void testNumberOfSolutions() {
		Assert.assertEquals(
			"Number of solutions",
			6,
			StrictInheritance.BuiltSolutions.length);
	}
	public void testSolution1() {
		Assert.assertEquals(
			"A -|>- java.lang.Object",
			"java.lang.Object",
			StrictInheritance.BuiltSolutions[0].getComponent(
				StrictInheritanceProblem.SUPER_ENTITY).getDisplayValue());
		Assert.assertEquals(
			"A -|>- java.lang.Object",
			"A",
			StrictInheritance.BuiltSolutions[0].getComponent(
				StrictInheritanceProblem.SUB_ENTITY).getDisplayValue());
	}
	public void testSolution2() {
		Assert.assertEquals(
			"B -|>- A",
			"A",
			StrictInheritance.BuiltSolutions[1].getComponent(
				StrictInheritanceProblem.SUPER_ENTITY).getDisplayValue());
		Assert.assertEquals(
			"B -|>- A",
			"B",
			StrictInheritance.BuiltSolutions[1].getComponent(
				StrictInheritanceProblem.SUB_ENTITY).getDisplayValue());
	}
	public void testSolution3() {
		Assert.assertEquals(
			"C -|>- B",
			"B",
			StrictInheritance.BuiltSolutions[2].getComponent(
				StrictInheritanceProblem.SUPER_ENTITY).getDisplayValue());
		Assert.assertEquals(
			"C -|>- B",
			"C",
			StrictInheritance.BuiltSolutions[2].getComponent(
				StrictInheritanceProblem.SUB_ENTITY).getDisplayValue());
	}
	public void testSolution4() {
		Assert.assertEquals(
			"D -|>- java.lang.Object",
			"java.lang.Object",
			StrictInheritance.BuiltSolutions[3].getComponent(
				StrictInheritanceProblem.SUPER_ENTITY).getDisplayValue());
		Assert.assertEquals(
			"D -|>- java.lang.Object",
			"D",
			StrictInheritance.BuiltSolutions[3].getComponent(
				StrictInheritanceProblem.SUB_ENTITY).getDisplayValue());
	}
	public void testSolution5() {
		Assert.assertEquals(
			"E -|>- D",
			"D",
			StrictInheritance.BuiltSolutions[4].getComponent(
				StrictInheritanceProblem.SUPER_ENTITY).getDisplayValue());
		Assert.assertEquals(
			"E -|>- D",
			"E",
			StrictInheritance.BuiltSolutions[4].getComponent(
				StrictInheritanceProblem.SUB_ENTITY).getDisplayValue());
	}
	public void testSolution6() {
		Assert.assertEquals(
			"F -|>- java.lang.Object",
			"java.lang.Object",
			StrictInheritance.BuiltSolutions[5].getComponent(
				StrictInheritanceProblem.SUPER_ENTITY).getDisplayValue());
		Assert.assertEquals(
			"F -|>- java.lang.Object",
			"F",
			StrictInheritance.BuiltSolutions[5].getComponent(
				StrictInheritanceProblem.SUB_ENTITY).getDisplayValue());
	}
	public void testSolutionPercentage() {
		for (int i = 0; i < StrictInheritance.BuiltSolutions.length; i++) {
			Assert.assertEquals(
				"Solution with all constraints",
				100,
				StrictInheritance.BuiltSolutions[i].getConfidence());
		}
	}
}
