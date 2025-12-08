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
package ptidej.solver.claire.test.defect;

import java.lang.reflect.InvocationTargetException;

import org.junit.Assert;

import ptidej.occurrences.SolverKinds;
import ptidej.solver.Occurrence;
import ptidej.solver.claire.OccurrenceGenerator;
import ptidej.solver.claire.test.Primitive;
import ptidej.solver.claire.test.example.Composite2Test;

public final class AssociationDistanceAssociationPatternTest extends Primitive {
	private static final char[] ASSOCIATED_ENTITY = "AssociatedEntity"
		.toCharArray();
	private static final char[] ASSOCIATION_DISTANCE = "AssociationDistance"
		.toCharArray();
	private static final char[] ENTITY = "Entity".toCharArray();
	private static Occurrence[] Solutions;

	public AssociationDistanceAssociationPatternTest(final String name) {
		super(name);
	}
	protected void setUp() throws IllegalAccessException,
			InstantiationException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {

		if (AssociationDistanceAssociationPatternTest.Solutions == null) {
			//	final IIdiomLevelModel idiomLevelModel =
			//		Factory.getUniqueInstance().createIdiomLevelModel(
			//			"ptidej.example.composite2");
			//	idiomLevelModel.create(
			//		new LightClassFileCreator(
			//			DefaultFileRepository.getDefaultFileRepository(),
			//			new String[] { PropertyManager.getExamplesDirectory()+"ptidej/example/composite2/" }));

			AssociationDistanceAssociationPatternTest.Solutions =
				this.testDesignPattern(
					Composite2Test.class,
					Primitive.ALL_SOLUTIONS,
					"AssociationDistanceTest".toCharArray(),
					AssociationPattern.class,
					SolverKinds.SOLVER_AUTOMATIC,
					OccurrenceGenerator.PROBLEM_CUSTOM);
		}
	}
	public void testNumberOfSolutions() {
		Assert.assertEquals(
			"Number of solutions",
			4,
			AssociationDistanceAssociationPatternTest.Solutions.length);
	}
	public void testSolution1() {
		//	1.100.Name = AssociationDistance Pattern Problem
		//	1.100.XCommand = System.out.println("No transformation required.");
		//	1.100.Entity = AggregateClass1
		//	1.100.AssociatedEntity = AggregatedClass1
		//	1.100.AssociationDistance = 1
		try {
			Assert.assertEquals(
				"Solution 1",
				"AggregateClass1",
				AssociationDistanceAssociationPatternTest.Solutions[0].getComponent(
					AssociationDistanceAssociationPatternTest.ENTITY).getDisplayValue());
			Assert.assertEquals(
				"Solution 1",
				"AggregatedClass1",
				AssociationDistanceAssociationPatternTest.Solutions[0].getComponent(
					AssociationDistanceAssociationPatternTest.ASSOCIATED_ENTITY).getDisplayValue());
			Assert
				.assertEquals(
					"Solution 1",
					"1",
					AssociationDistanceAssociationPatternTest.Solutions[0]
						.getComponent(AssociationDistanceAssociationPatternTest.ASSOCIATION_DISTANCE)
						.getDisplayValue());
		}
		catch (final Exception e) {
			e.printStackTrace();
		}
	}
	public void testSolution2() {
		//	2.100.Name = AssociationDistance Pattern Problem
		//	2.100.XCommand = System.out.println("No transformation required.");
		//	2.100.Entity = AggregateClass2
		//	2.100.AssociatedEntity = AggregatedClass2
		//	2.100.AssociationDistance = 1
		Assert.assertEquals(
			"Solution 2",
			"AggregateClass2",
			AssociationDistanceAssociationPatternTest.Solutions[1].getComponent(
				AssociationDistanceAssociationPatternTest.ENTITY).getDisplayValue());
		Assert.assertEquals(
			"Solution 2",
			"AggregatedClass2",
			AssociationDistanceAssociationPatternTest.Solutions[1].getComponent(
				AssociationDistanceAssociationPatternTest.ASSOCIATED_ENTITY).getDisplayValue());
		Assert.assertEquals("Solution 2", "1", AssociationDistanceAssociationPatternTest.Solutions[1]
			.getComponent(AssociationDistanceAssociationPatternTest.ASSOCIATION_DISTANCE)
			.getDisplayValue());
	}
	public void testSolution3() {
		//	3.100.Name = AssociationDistance Pattern Problem
		//	3.100.XCommand = System.out.println("No transformation required.");
		//	3.100.Entity = AggregatedClass1
		//	3.100.AssociatedEntity = AssociatedClass1
		//	3.100.AssociationDistance = 1
		Assert.assertEquals(
			"Solution 3",
			"AggregatedClass1",
			AssociationDistanceAssociationPatternTest.Solutions[2].getComponent(
				AssociationDistanceAssociationPatternTest.ENTITY).getDisplayValue());
		Assert.assertEquals(
			"Solution 3",
			"AssociatedClass1",
			AssociationDistanceAssociationPatternTest.Solutions[2].getComponent(
				AssociationDistanceAssociationPatternTest.ASSOCIATED_ENTITY).getDisplayValue());
		Assert.assertEquals("Solution 3", "1", AssociationDistanceAssociationPatternTest.Solutions[2]
			.getComponent(AssociationDistanceAssociationPatternTest.ASSOCIATION_DISTANCE)
			.getDisplayValue());
	}
	public void testSolution4() {
		//	4.100.Name = AssociationDistance Pattern Problem
		//	4.100.XCommand = System.out.println("No transformation required.");
		//	4.100.Entity = AggregateClass1
		//	4.100.AssociatedEntity = AssociatedClass1
		//	4.100.AssociationDistance = 2
		Assert.assertEquals(
			"Solution 4",
			"AggregateClass1",
			AssociationDistanceAssociationPatternTest.Solutions[3].getComponent(
				AssociationDistanceAssociationPatternTest.ENTITY).getDisplayValue());
		Assert.assertEquals(
			"Solution 4",
			"AssociatedClass1",
			AssociationDistanceAssociationPatternTest.Solutions[3].getComponent(
				AssociationDistanceAssociationPatternTest.ASSOCIATED_ENTITY).getDisplayValue());
		Assert.assertEquals("Solution 4", "2", AssociationDistanceAssociationPatternTest.Solutions[3]
			.getComponent(AssociationDistanceAssociationPatternTest.ASSOCIATION_DISTANCE)
			.getDisplayValue());
	}
}
