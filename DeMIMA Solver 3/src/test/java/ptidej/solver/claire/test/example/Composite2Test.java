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
package ptidej.solver.claire.test.example;

import java.lang.reflect.InvocationTargetException;

import org.junit.Assert;

import padl.creator.classfile.CompleteClassFileCreator;
import padl.kernel.ICodeLevelModel;
import padl.kernel.exception.CreationException;
import padl.kernel.impl.Factory;
import padl.motif.IDesignMotifModel;
import ptidej.occurrences.SolverKinds;
import ptidej.solver.Occurrence;
import ptidej.solver.claire.OccurrenceGenerator;
import ptidej.solver.claire.test.Primitive;

public class Composite2Test extends Primitive {
	private static int NumberOfExpectedSolutions;
	private static Occurrence[] FoundSolutions;
	private static Occurrence[] ExpectedSolutions;

	public Composite2Test(final String name) {
		super(name);
	}

	protected void setUp()
			throws IllegalAccessException, InstantiationException,
			IllegalArgumentException, InvocationTargetException,
			NoSuchMethodException, SecurityException {

		if (Composite2Test.FoundSolutions == null) {
			final IDesignMotifModel pattern = (IDesignMotifModel) padl.motif.repository.Composite.class
					.getDeclaredConstructor().newInstance();

			final ICodeLevelModel codeLevelModel = Factory.getInstance()
					.createCodeLevelModel("ptidej.example.composite2");
			try {
				codeLevelModel.create(new CompleteClassFileCreator(
						new String[] {
								"../DeMIMA/target/test-classes/ptidej/example/composite2/" },
						false));
			}
			catch (final CreationException e) {
				e.printStackTrace();
			}

			// Expected solutions.
			Composite2Test.ExpectedSolutions = SolutionReader
					.getExpectedSolutions("Composite2", codeLevelModel);
			Composite2Test.NumberOfExpectedSolutions = SolutionReader
					.getExpectedNumberOfSolutions("Composite2", codeLevelModel);

			// Solutions found.
			Composite2Test.FoundSolutions = this.testDesignPattern(Composite2Test.class,
					Primitive.ALL_SOLUTIONS, pattern.getName(), codeLevelModel,
					SolverKinds.SOLVER_COMBINATORIAL_AUTOMATIC,
					OccurrenceGenerator.PROBLEM_AC4);
		}
	}

	public void testNumberOfSolutions() {
		Assert.assertEquals("Number of solutions",
				Composite2Test.NumberOfExpectedSolutions,
				Composite2Test.FoundSolutions.length);
	}

	public void testSolutions() {
		for (int i = 0; i < Composite2Test.NumberOfExpectedSolutions; i++) {
			Assert.assertEquals("", Composite2Test.ExpectedSolutions[i],
					Composite2Test.FoundSolutions[i]);
		}
	}
	//	public void testComponentSolutionComponent() {
	//		for (int i = 0; i < Composite2.NUMBER_OF_EXPECTED_SOLUTIONS; i++) {
	//			this.testSolutionComponent(
	//				Composite2.Solutions[i],
	//				"Component",
	//				"ptidej.example.composite2.Element");
	//		}
	//	}
	//	public void testCompositeSolutionComponent() {
	//		for (int i = 0; i < Composite2.NUMBER_OF_EXPECTED_SOLUTIONS; i++) {
	//			this.testSolutionComponent(
	//				Composite2.Solutions[i],
	//				"Composite",
	//				"ptidej.example.composite2.Document");
	//		}
	//	}
	//	public void testLeaf1SolutionComponent() {
	//		for (int i = 0; i < Composite2.NUMBER_OF_EXPECTED_SOLUTIONS; i++) {
	//			this.testSolutionComponent(
	//				Composite2.Solutions[i],
	//				"Leaf-1",
	//				"ptidej.example.composite2.IndentedParagraph");
	//		}
	//	}
	//	public void testLeaf2SolutionComponent() {
	//		for (int i = 0; i < Composite2.NUMBER_OF_EXPECTED_SOLUTIONS; i++) {
	//			this.testSolutionComponent(
	//				Composite2.Solutions[i],
	//				"Leaf-2",
	//				"ptidej.example.composite2.Paragraph");
	//		}
	//	}
	//	public void testLeaf3SolutionComponent() {
	//		for (int i = 0; i < Composite2.NUMBER_OF_EXPECTED_SOLUTIONS; i++) {
	//			this.testSolutionComponent(
	//				Composite2.Solutions[i],
	//				"Leaf-3",
	//				"ptidej.example.composite2.Title");
	//		}
	//	}
	//	public void testSolutionPercentage() {
	//		Composite2.assertEquals(
	//			"Distorted solution percentage",
	//			12,
	//			Composite2.Solutions[0].getPercentage());
	//		Composite2.assertEquals(
	//			"Distorted solution percentage",
	//			6,
	//			Composite2.Solutions[1].getPercentage());
	//		Composite2.assertEquals(
	//			"Distorted solution percentage",
	//			3,
	//			Composite2.Solutions[2].getPercentage());
	//		for (int i = 3; i < Composite2.NUMBER_OF_EXPECTED_SOLUTIONS; i++) {
	//			Composite2.assertEquals(
	//				"Distorted solution percentage",
	//				1,
	//				Composite2.Solutions[i].getPercentage());
	//		}
	//	}
}
