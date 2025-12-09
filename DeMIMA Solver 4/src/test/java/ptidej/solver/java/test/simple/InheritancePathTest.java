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
import ptidej.solver.java.test.problem.InheritancePathProblem;

public final class InheritancePathTest extends Primitive {
	private static Occurrence[] BuiltSolutions = null;

	public InheritancePathTest(final String name) {
		super(name);
	}
	protected void setUp() {
		if (InheritancePathTest.BuiltSolutions == null) {
			InheritancePathTest.BuiltSolutions =
				Primitive.automaticSolve(
					InheritancePathProblem.class,
					InheritanceExample.class);
		}
	}
	public void testNumberOfSolutions() {
		Assert.assertEquals(
			"Number of solutions",
			10,
			InheritancePathTest.BuiltSolutions.length);
	}
	public void testSolution1() {
		Assert.assertEquals(
			"A -|>- java.lang.Object",
			"java.lang.Object",
			InheritancePathTest.BuiltSolutions[0].getComponent(
				InheritancePathProblem.SUPER_ENTITY).getDisplayValue());
		Assert.assertEquals(
			"A -|>- java.lang.Object",
			"A",
			InheritancePathTest.BuiltSolutions[0].getComponent(
				InheritancePathProblem.SUB_ENTITY).getDisplayValue());
	}
	public void testSolution10() {
		Assert.assertEquals(
			"F -|>- java.lang.Object",
			"java.lang.Object",
			InheritancePathTest.BuiltSolutions[9].getComponent(
				InheritancePathProblem.SUPER_ENTITY).getDisplayValue());
		Assert.assertEquals(
			"F -|>- java.lang.Object",
			"F",
			InheritancePathTest.BuiltSolutions[9].getComponent(
				InheritancePathProblem.SUB_ENTITY).getDisplayValue());
	}
	public void testSolution2() {
		Assert.assertEquals("B -|>- A", "A", InheritancePathTest.BuiltSolutions[1]
			.getComponent(InheritancePathProblem.SUPER_ENTITY)
			.getDisplayValue());
		Assert.assertEquals("B -|>- A", "B", InheritancePathTest.BuiltSolutions[1]
			.getComponent(InheritancePathProblem.SUB_ENTITY)
			.getDisplayValue());
	}
	public void testSolution3() {
		Assert.assertEquals(
			"B -|>- java.lang.Object",
			"java.lang.Object",
			InheritancePathTest.BuiltSolutions[2].getComponent(
				InheritancePathProblem.SUPER_ENTITY).getDisplayValue());
		Assert.assertEquals(
			"B -|>- java.lang.Object",
			"B",
			InheritancePathTest.BuiltSolutions[2].getComponent(
				InheritancePathProblem.SUB_ENTITY).getDisplayValue());
	}
	public void testSolution4() {
		Assert.assertEquals("C -|>- A", "A", InheritancePathTest.BuiltSolutions[3]
			.getComponent(InheritancePathProblem.SUPER_ENTITY)
			.getDisplayValue());
		Assert.assertEquals("C -|>- A", "C", InheritancePathTest.BuiltSolutions[3]
			.getComponent(InheritancePathProblem.SUB_ENTITY)
			.getDisplayValue());
	}
	public void testSolution5() {
		Assert.assertEquals("C -|>- B", "B", InheritancePathTest.BuiltSolutions[4]
			.getComponent(InheritancePathProblem.SUPER_ENTITY)
			.getDisplayValue());
		Assert.assertEquals("C -|>- B", "C", InheritancePathTest.BuiltSolutions[4]
			.getComponent(InheritancePathProblem.SUB_ENTITY)
			.getDisplayValue());
	}
	public void testSolution6() {
		Assert.assertEquals(
			"C -|>- java.lang.Object",
			"java.lang.Object",
			InheritancePathTest.BuiltSolutions[5].getComponent(
				InheritancePathProblem.SUPER_ENTITY).getDisplayValue());
		Assert.assertEquals(
			"C -|>- java.lang.Object",
			"C",
			InheritancePathTest.BuiltSolutions[5].getComponent(
				InheritancePathProblem.SUB_ENTITY).getDisplayValue());
	}
	public void testSolution7() {
		Assert.assertEquals(
			"D -|>- java.lang.Object",
			"java.lang.Object",
			InheritancePathTest.BuiltSolutions[6].getComponent(
				InheritancePathProblem.SUPER_ENTITY).getDisplayValue());
		Assert.assertEquals(
			"D -|>- java.lang.Object",
			"D",
			InheritancePathTest.BuiltSolutions[6].getComponent(
				InheritancePathProblem.SUB_ENTITY).getDisplayValue());
	}
	public void testSolution8() {
		Assert.assertEquals("E -|>- D", "D", InheritancePathTest.BuiltSolutions[7]
			.getComponent(InheritancePathProblem.SUPER_ENTITY)
			.getDisplayValue());
		Assert.assertEquals("E -|>- D", "E", InheritancePathTest.BuiltSolutions[7]
			.getComponent(InheritancePathProblem.SUB_ENTITY)
			.getDisplayValue());
	}
	public void testSolution9() {
		Assert.assertEquals(
			"E -|>- java.lang.Object",
			"java.lang.Object",
			InheritancePathTest.BuiltSolutions[8].getComponent(
				InheritancePathProblem.SUPER_ENTITY).getDisplayValue());
		Assert.assertEquals(
			"E -|>- java.lang.Object",
			"E",
			InheritancePathTest.BuiltSolutions[8].getComponent(
				InheritancePathProblem.SUB_ENTITY).getDisplayValue());
	}
	public void testSolutionPercentage() {
		for (int i = 0; i < InheritancePathTest.BuiltSolutions.length; i++) {
			Assert.assertEquals(
				"Solution with all constraints",
				100,
				InheritancePathTest.BuiltSolutions[i].getConfidence());
		}
	}
}
