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
import ptidej.example.pattern.InheritanceExample;
import ptidej.solver.Occurrence;
import ptidej.solver.claire.OccurrenceGenerator;
import ptidej.solver.claire.test.Primitive;
import ptidej.solver.claire.test.pattern.InheritancePattern;

public final class GoodInheritanceTest extends Primitive {
	private static Occurrence[] BuiltSolutions;

	public GoodInheritanceTest(final String name) {
		super(name);
	}

	protected void setUp()
			throws IllegalAccessException, InstantiationException,
			IllegalArgumentException, InvocationTargetException,
			NoSuchMethodException, SecurityException {

		if (GoodInheritanceTest.BuiltSolutions == null) {
			GoodInheritanceTest.BuiltSolutions = this.testDesignPattern(
					GoodInheritanceTest.class, Primitive.ALL_SOLUTIONS,
					((IDesignMotifModel) InheritancePattern.class
							.getDeclaredConstructor().newInstance()).getName(),
					InheritanceExample.class,
					OccurrenceGenerator.SOLVER_AUTOMATIC,
					OccurrenceGenerator.PROBLEM_CUSTOM);
		}
	}

	public void testNumberOfSolutions() {
		Assert.assertEquals("Number of solutions", 13,
				GoodInheritanceTest.BuiltSolutions.length);
	}

	public void testSolution1() {
		Assert.assertEquals("A == A", "A",
				GoodInheritanceTest.BuiltSolutions[0]
						.getComponent(InheritancePattern.SUPER_ENTITY)
						.getDisplayValue());
		Assert.assertEquals("A == A", "A", GoodInheritanceTest.BuiltSolutions[0]
				.getComponent(InheritancePattern.SUB_ENTITY).getDisplayValue());
	}

	public void testSolution10() {
		Assert.assertEquals("E == E", "E",
				GoodInheritanceTest.BuiltSolutions[9]
						.getComponent(InheritancePattern.SUPER_ENTITY)
						.getDisplayValue());
		Assert.assertEquals("E == E", "E", GoodInheritanceTest.BuiltSolutions[9]
				.getComponent(InheritancePattern.SUB_ENTITY).getDisplayValue());
	}

	public void testSolution11() {
		Assert.assertEquals("F == F", "F",
				GoodInheritanceTest.BuiltSolutions[10]
						.getComponent(InheritancePattern.SUPER_ENTITY)
						.getDisplayValue());
		Assert.assertEquals("F == F", "F", GoodInheritanceTest.BuiltSolutions[10]
				.getComponent(InheritancePattern.SUB_ENTITY).getDisplayValue());
	}

	public void testSolution12() {
		Assert.assertEquals("F -|>- java.lang.Object", "java.lang.Object",
				GoodInheritanceTest.BuiltSolutions[11]
						.getComponent(InheritancePattern.SUPER_ENTITY)
						.getDisplayValue());
		Assert.assertEquals("F -|>- java.lang.Object", "F",
				GoodInheritanceTest.BuiltSolutions[11]
						.getComponent(InheritancePattern.SUB_ENTITY)
						.getDisplayValue());
	}

	public void testSolution13() {
		Assert.assertEquals("java.lang.Object == java.lang.Object",
				"java.lang.Object",
				GoodInheritanceTest.BuiltSolutions[12]
						.getComponent(InheritancePattern.SUPER_ENTITY)
						.getDisplayValue());
		Assert.assertEquals("java.lang.Object == java.lang.Object",
				"java.lang.Object",
				GoodInheritanceTest.BuiltSolutions[12]
						.getComponent(InheritancePattern.SUB_ENTITY)
						.getDisplayValue());
	}

	public void testSolution2() {
		Assert.assertEquals("A -|>- java.lang.Object", "java.lang.Object",
				GoodInheritanceTest.BuiltSolutions[1]
						.getComponent(InheritancePattern.SUPER_ENTITY)
						.getDisplayValue());
		Assert.assertEquals("A -|>- java.lang.Object", "A",
				GoodInheritanceTest.BuiltSolutions[1]
						.getComponent(InheritancePattern.SUB_ENTITY)
						.getDisplayValue());
	}

	public void testSolution3() {
		Assert.assertEquals("B -|>- A", "A",
				GoodInheritanceTest.BuiltSolutions[2]
						.getComponent(InheritancePattern.SUPER_ENTITY)
						.getDisplayValue());
		Assert.assertEquals("B -|>- A", "B", GoodInheritanceTest.BuiltSolutions[2]
				.getComponent(InheritancePattern.SUB_ENTITY).getDisplayValue());
	}

	public void testSolution4() {
		Assert.assertEquals("B == B", "B",
				GoodInheritanceTest.BuiltSolutions[3]
						.getComponent(InheritancePattern.SUPER_ENTITY)
						.getDisplayValue());
		Assert.assertEquals("B == B", "B", GoodInheritanceTest.BuiltSolutions[3]
				.getComponent(InheritancePattern.SUB_ENTITY).getDisplayValue());
	}

	public void testSolution5() {
		Assert.assertEquals("C -|>- B", "B",
				GoodInheritanceTest.BuiltSolutions[4]
						.getComponent(InheritancePattern.SUPER_ENTITY)
						.getDisplayValue());
		Assert.assertEquals("C -|>- B", "C", GoodInheritanceTest.BuiltSolutions[4]
				.getComponent(InheritancePattern.SUB_ENTITY).getDisplayValue());
	}

	public void testSolution6() {
		Assert.assertEquals("C == C", "C",
				GoodInheritanceTest.BuiltSolutions[5]
						.getComponent(InheritancePattern.SUPER_ENTITY)
						.getDisplayValue());
		Assert.assertEquals("C == C", "C", GoodInheritanceTest.BuiltSolutions[5]
				.getComponent(InheritancePattern.SUB_ENTITY).getDisplayValue());
	}

	public void testSolution7() {
		Assert.assertEquals("D == D", "D",
				GoodInheritanceTest.BuiltSolutions[6]
						.getComponent(InheritancePattern.SUPER_ENTITY)
						.getDisplayValue());
		Assert.assertEquals("D == D", "D", GoodInheritanceTest.BuiltSolutions[6]
				.getComponent(InheritancePattern.SUB_ENTITY).getDisplayValue());
	}

	public void testSolution8() {
		Assert.assertEquals("D -|>- java.lang.Object", "java.lang.Object",
				GoodInheritanceTest.BuiltSolutions[7]
						.getComponent(InheritancePattern.SUPER_ENTITY)
						.getDisplayValue());
		Assert.assertEquals("D -|>- java.lang.Object", "D",
				GoodInheritanceTest.BuiltSolutions[7]
						.getComponent(InheritancePattern.SUB_ENTITY)
						.getDisplayValue());
	}

	public void testSolution9() {
		Assert.assertEquals("E -|>- D", "D",
				GoodInheritanceTest.BuiltSolutions[8]
						.getComponent(InheritancePattern.SUPER_ENTITY)
						.getDisplayValue());
		Assert.assertEquals("E -|>- D", "E", GoodInheritanceTest.BuiltSolutions[8]
				.getComponent(InheritancePattern.SUB_ENTITY).getDisplayValue());
	}

	public void testSolutionPercentage() {
		for (int i = 0; i < GoodInheritanceTest.BuiltSolutions.length; i++) {
			Assert.assertEquals("Solution with all constraints", 100,
					GoodInheritanceTest.BuiltSolutions[i].getConfidence());
		}
	}
}
