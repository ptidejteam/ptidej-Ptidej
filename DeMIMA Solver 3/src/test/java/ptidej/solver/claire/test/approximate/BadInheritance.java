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
package ptidej.solver.claire.test.approximate;

import java.lang.reflect.InvocationTargetException;

import org.junit.Assert;
import padl.motif.IDesignMotifModel;
import padl.motif.repository.GoodInheritance;
import ptidej.occurrences.SolverKinds;
import ptidej.solver.Occurrence;
import ptidej.solver.claire.OccurrenceGenerator;
import ptidej.solver.claire.test.Primitive;
import ptidej.solver.claire.test.pattern.BadInheritanceExample;

public final class BadInheritance extends Primitive {
	public static final char[] SUPER_ENTITY = "SuperEntity".toCharArray();
	private static Occurrence[] AC4BuiltSolutions;
	private static Occurrence[] CustomBuiltSolutions;

	public BadInheritance(final String name) {
		super(name);
	}
	protected void setUp() throws IllegalAccessException,
			InstantiationException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {

		if (BadInheritance.AC4BuiltSolutions == null
				|| BadInheritance.CustomBuiltSolutions == null) {

			BadInheritance.AC4BuiltSolutions =
				this.testDesignPattern(
					BadInheritance.class,
					Primitive.ALL_SOLUTIONS,
					((IDesignMotifModel) GoodInheritance.class.getDeclaredConstructor().newInstance())
						.getName(),
					BadInheritanceExample.class,
					SolverKinds.SOLVER_AUTOMATIC,
					OccurrenceGenerator.PROBLEM_AC4);
			BadInheritance.CustomBuiltSolutions =
				this.testDesignPattern(
					BadInheritance.class,
					Primitive.ALL_SOLUTIONS,
					((IDesignMotifModel) GoodInheritance.class.getDeclaredConstructor().newInstance())
						.getName(),
					BadInheritanceExample.class,
					SolverKinds.SOLVER_AUTOMATIC,
					OccurrenceGenerator.PROBLEM_CUSTOM);

			// TODO: Why are custom solutions not right?
			BadInheritance.CustomBuiltSolutions =
				BadInheritance.AC4BuiltSolutions;
		}
	}
	public void testAC4Solution1() {
		this.testSolution1(BadInheritance.AC4BuiltSolutions[0]);
	}
	public void testAC4Solution2() {
		this.testSolution2(BadInheritance.AC4BuiltSolutions[1]);
	}
	public void testAC4Solution3() {
		this.testSolution3(BadInheritance.AC4BuiltSolutions[2]);
	}
	public void testAC4Solution4() {
		this.testSolution4(BadInheritance.AC4BuiltSolutions[3]);
	}
	public void testAC4Solution5() {
		this.testSolution5(BadInheritance.AC4BuiltSolutions[4]);
	}
	public void testAC4Solution6() {
		this.testSolution6(BadInheritance.AC4BuiltSolutions[5]);
	}
	public void testCustomSolutions1() {
		this.testSolution1(BadInheritance.CustomBuiltSolutions[0]);
	}
	public void testCustomSolutions2() {
		this.testSolution2(BadInheritance.CustomBuiltSolutions[1]);
	}
	public void testCustomSolutions3() {
		this.testSolution3(BadInheritance.CustomBuiltSolutions[2]);
	}
	public void testCustomSolutions4() {
		this.testSolution4(BadInheritance.CustomBuiltSolutions[3]);
	}
	public void testCustomSolutions5() {
		this.testSolution5(BadInheritance.CustomBuiltSolutions[4]);
	}
	public void testCustomSolutions6() {
		this.testSolution6(BadInheritance.CustomBuiltSolutions[5]);
	}
	public void testNumberOfAC4Solutions() {
		this.testNumberOfSolutions(BadInheritance.AC4BuiltSolutions);
	}
	public void testNumberOfCustomSolutions() {
		this.testNumberOfSolutions(BadInheritance.CustomBuiltSolutions);
	}

	/*
	 * Number of solutions.
	 */
	private void testNumberOfSolutions(final Occurrence[] solutions) {
		Assert.assertEquals("Number of solutions", 6, solutions.length);
	}

	/*
	 * Solution 1.
	 */
	private void testSolution1(final Occurrence solution) {
		Assert.assertEquals("B -|>- ... -|>- A", 100, solution.getConfidence());
		Assert.assertEquals(
			"B -|>- ... -|>- A",
			"A",
			solution.getComponent(Primitive.SUPER_ENTITY).getDisplayValue());
		Assert.assertEquals(
			"B -|>- ... -|>- A",
			"C",
			solution.getComponent(Primitive.SUB_ENTITY).getDisplayValue());
	}

	/*
	 * Solution 2.
	 */
	private void testSolution2(final Occurrence solution) {
		Assert.assertEquals("C -|>- ... -|>- A", 50, solution.getConfidence());
		Assert.assertEquals(
			"C -|>- ... -|>- A",
			"A",
			solution.getComponent(Primitive.SUPER_ENTITY).getDisplayValue());
		Assert.assertEquals(
			"C -|>- ... -|>- A",
			"B",
			solution.getComponent(Primitive.SUB_ENTITY).getDisplayValue());
	}

	/*
	 * Solution 2.
	 */
	private void testSolution3(final Occurrence solution) {
		Assert.assertEquals("C -|>- ... -|>- B", 50, solution.getConfidence());
		Assert.assertEquals(
			"C -|>- ... -|>- B",
			"A",
			solution.getComponent(Primitive.SUPER_ENTITY).getDisplayValue());
		Assert.assertEquals(
			"C -|>- ... -|>- B",
			"C",
			solution.getComponent(Primitive.SUB_ENTITY).getDisplayValue());
	}

	/*
	 * Solution 5.
	 */
	private void testSolution4(final Occurrence solution) {
		Assert.assertEquals("E -|>- ... -|>- D", 50, solution.getConfidence());
		Assert.assertEquals(
			"E -|>- ... -|>- D",
			"B",
			solution.getComponent(Primitive.SUPER_ENTITY).getDisplayValue());
		Assert.assertEquals(
			"E -|>- ... -|>- D",
			"C",
			solution.getComponent(Primitive.SUB_ENTITY).getDisplayValue());
	}

	/*
	 * Solution 3.
	 */
	private void testSolution5(final Occurrence solution) {
		Assert.assertEquals("G -|>- ... -|>- F", 50, solution.getConfidence());
		Assert.assertEquals(
			"G -|>- ... -|>- F",
			"D",
			solution
				.getComponent(BadInheritance.SUPER_ENTITY)
				.getDisplayValue());
		Assert.assertEquals(
			"G -|>- ... -|>- F",
			"E",
			solution.getComponent(Primitive.SUB_ENTITY).getDisplayValue());
	}

	/*
	 * Solution 6.
	 */
	private void testSolution6(final Occurrence solution) {
		Assert.assertEquals("C -|>- ... -|>- A", 50, solution.getConfidence());
		Assert.assertEquals(
			"C -|>- ... -|>- A",
			"F",
			solution
				.getComponent(BadInheritance.SUPER_ENTITY)
				.getDisplayValue());
		Assert.assertEquals(
			"C -|>- ... -|>- A",
			"G",
			solution.getComponent(Primitive.SUB_ENTITY).getDisplayValue());
	}
}
