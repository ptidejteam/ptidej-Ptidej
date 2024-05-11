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
import ptidej.solver.java.test.problem.InheritanceProblem;

public final class GoodInheritance extends Primitive {
	private static Occurrence[] BuiltSolutions;

	public GoodInheritance(final String name) {
		super(name);
	}
	protected void setUp() {
		if (GoodInheritance.BuiltSolutions == null) {
			GoodInheritance.BuiltSolutions =
				Primitive.automaticSolve(
					InheritanceProblem.class,
					ptidej.example.pattern.InheritanceExample.class);
		}
	}
	public void testNumberOfSolutions() {
		Assert.assertEquals(
			"Number of solutions",
			13,
			GoodInheritance.BuiltSolutions.length);
	}
	public void testSolution1() {
		Assert.assertEquals("A == A", "A", GoodInheritance.BuiltSolutions[0]
			.getComponent(InheritanceProblem.SUPER_ENTITY)
			.getDisplayValue());
		Assert.assertEquals("A == A", "A", GoodInheritance.BuiltSolutions[0]
			.getComponent(InheritanceProblem.SUB_ENTITY)
			.getDisplayValue());
	}
	public void testSolution10() {
		Assert.assertEquals("E == E", "E", GoodInheritance.BuiltSolutions[9]
			.getComponent(InheritanceProblem.SUPER_ENTITY)
			.getDisplayValue());
		Assert.assertEquals("E == E", "E", GoodInheritance.BuiltSolutions[9]
			.getComponent(InheritanceProblem.SUB_ENTITY)
			.getDisplayValue());
	}
	public void testSolution11() {
		Assert.assertEquals("F == F", "F", GoodInheritance.BuiltSolutions[10]
			.getComponent(InheritanceProblem.SUPER_ENTITY)
			.getDisplayValue());
		Assert.assertEquals("F == F", "F", GoodInheritance.BuiltSolutions[10]
			.getComponent(InheritanceProblem.SUB_ENTITY)
			.getDisplayValue());
	}
	public void testSolution12() {
		Assert.assertEquals(
			"F -|>- java.lang.Object",
			"java.lang.Object",
			GoodInheritance.BuiltSolutions[11].getComponent(
				InheritanceProblem.SUPER_ENTITY).getDisplayValue());
		Assert.assertEquals(
			"F -|>- java.lang.Object",
			"F",
			GoodInheritance.BuiltSolutions[11].getComponent(
				InheritanceProblem.SUB_ENTITY).getDisplayValue());
	}
	public void testSolution13() {
		Assert.assertEquals(
			"java.lang.Object == java.lang.Object",
			"java.lang.Object",
			GoodInheritance.BuiltSolutions[12].getComponent(
				InheritanceProblem.SUPER_ENTITY).getDisplayValue());
		Assert.assertEquals(
			"java.lang.Object == java.lang.Object",
			"java.lang.Object",
			GoodInheritance.BuiltSolutions[12].getComponent(
				InheritanceProblem.SUB_ENTITY).getDisplayValue());
	}
	public void testSolution2() {
		Assert.assertEquals(
			"A -|>- java.lang.Object",
			"java.lang.Object",
			GoodInheritance.BuiltSolutions[1].getComponent(
				InheritanceProblem.SUPER_ENTITY).getDisplayValue());
		Assert.assertEquals(
			"A -|>- java.lang.Object",
			"A",
			GoodInheritance.BuiltSolutions[1].getComponent(
				InheritanceProblem.SUB_ENTITY).getDisplayValue());
	}
	public void testSolution3() {
		Assert.assertEquals("B -|>- A", "A", GoodInheritance.BuiltSolutions[2]
			.getComponent(InheritanceProblem.SUPER_ENTITY)
			.getDisplayValue());
		Assert.assertEquals("B -|>- A", "B", GoodInheritance.BuiltSolutions[2]
			.getComponent(InheritanceProblem.SUB_ENTITY)
			.getDisplayValue());
	}
	public void testSolution4() {
		Assert.assertEquals("B == B", "B", GoodInheritance.BuiltSolutions[3]
			.getComponent(InheritanceProblem.SUPER_ENTITY)
			.getDisplayValue());
		Assert.assertEquals("B == B", "B", GoodInheritance.BuiltSolutions[3]
			.getComponent(InheritanceProblem.SUB_ENTITY)
			.getDisplayValue());
	}
	public void testSolution5() {
		Assert.assertEquals("C -|>- B", "B", GoodInheritance.BuiltSolutions[4]
			.getComponent(InheritanceProblem.SUPER_ENTITY)
			.getDisplayValue());
		Assert.assertEquals("C -|>- B", "C", GoodInheritance.BuiltSolutions[4]
			.getComponent(InheritanceProblem.SUB_ENTITY)
			.getDisplayValue());
	}
	public void testSolution6() {
		Assert.assertEquals("C == C", "C", GoodInheritance.BuiltSolutions[5]
			.getComponent(InheritanceProblem.SUPER_ENTITY)
			.getDisplayValue());
		Assert.assertEquals("C == C", "C", GoodInheritance.BuiltSolutions[5]
			.getComponent(InheritanceProblem.SUB_ENTITY)
			.getDisplayValue());
	}
	public void testSolution7() {
		Assert.assertEquals("D == D", "D", GoodInheritance.BuiltSolutions[6]
			.getComponent(InheritanceProblem.SUPER_ENTITY)
			.getDisplayValue());
		Assert.assertEquals("D == D", "D", GoodInheritance.BuiltSolutions[6]
			.getComponent(InheritanceProblem.SUB_ENTITY)
			.getDisplayValue());
	}
	public void testSolution8() {
		Assert.assertEquals(
			"D -|>- java.lang.Object",
			"java.lang.Object",
			GoodInheritance.BuiltSolutions[7].getComponent(
				InheritanceProblem.SUPER_ENTITY).getDisplayValue());
		Assert.assertEquals(
			"D -|>- java.lang.Object",
			"D",
			GoodInheritance.BuiltSolutions[7].getComponent(
				InheritanceProblem.SUB_ENTITY).getDisplayValue());
	}
	public void testSolution9() {
		Assert.assertEquals("E -|>- D", "D", GoodInheritance.BuiltSolutions[8]
			.getComponent(InheritanceProblem.SUPER_ENTITY)
			.getDisplayValue());
		Assert.assertEquals("E -|>- D", "E", GoodInheritance.BuiltSolutions[8]
			.getComponent(InheritanceProblem.SUB_ENTITY)
			.getDisplayValue());
	}
	public void testSolutionPercentage() {
		for (int i = 0; i < GoodInheritance.BuiltSolutions.length; i++) {
			Assert.assertEquals(
				"Solution with all constraints",
				100,
				GoodInheritance.BuiltSolutions[i].getConfidence());
		}
	}
}
