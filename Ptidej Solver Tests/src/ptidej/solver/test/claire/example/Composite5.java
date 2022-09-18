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
package ptidej.solver.test.claire.example;

import java.lang.reflect.InvocationTargetException;

import org.junit.Assert;
import padl.creator.classfile.CompleteClassFileCreator;
import padl.kernel.ICodeLevelModel;
import padl.kernel.exception.CreationException;
import padl.kernel.impl.Factory;
import padl.motif.IDesignMotifModel;
import ptidej.solver.Occurrence;
import ptidej.solver.OccurrenceGenerator;
import ptidej.solver.test.claire.Primitive;

public class Composite5 extends Primitive {
	private static int NumberOfExpectedSolutions;
	private static Occurrence[] FoundSolutions;
	private static Occurrence[] ExpectedSolutions;

	public Composite5(final String name) {
		super(name);
	}
	protected void setUp() throws IllegalAccessException,
			InstantiationException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {

		if (Composite5.FoundSolutions == null) {
			final IDesignMotifModel pattern =
				(IDesignMotifModel) padl.motif.repository.Composite.class
					.getDeclaredConstructor().newInstance();

			final ICodeLevelModel codeLevelModel =
				Factory.getInstance().createCodeLevelModel(
					"ptidej.example.composite5");
			try {
				codeLevelModel
					.create(new CompleteClassFileCreator(
						new String[] { "../Ptidej Tests/bin/ptidej/example/composite5/" }));
			}
			catch (final CreationException e) {
				e.printStackTrace();
			}

			// Expected solutions.
			Composite5.ExpectedSolutions =
				SolutionReader.getExpectedSolutions(
					"Composite5",
					codeLevelModel);
			Composite5.NumberOfExpectedSolutions =
				SolutionReader.getExpectedNumberOfSolutions(
					"Composite5",
					codeLevelModel);

			// Solutions found.
			Composite5.FoundSolutions =
				this.testDesignPattern(
					Composite4.class,
					Primitive.ALL_SOLUTIONS,
					pattern.getName(),
					codeLevelModel,
					OccurrenceGenerator.SOLVER_COMBINATORIAL_AUTOMATIC,
					OccurrenceGenerator.PROBLEM_AC4);
		}
	}
	public void testNumberOfSolutions() {
		Assert.assertEquals(
			"Number of solutions",
			Composite5.NumberOfExpectedSolutions,
			Composite5.FoundSolutions.length);
	}
	public void testSolutions() {
		for (int i = 0; i < Composite5.NumberOfExpectedSolutions; i++) {
			Assert.assertEquals(
				"",
				Composite5.ExpectedSolutions[i],
				Composite5.FoundSolutions[i]);
		}
	}
	//	public void testSolution1() {
	//		final int[] solutions = new int[] { 0, 1, 2, 6, 7, 8 };
	//		for (int i = 0; i < solutions.length; i++) {
	//			this.testSolutionComponent(
	//				this.solutions[solutions[i]],
	//				"Component",
	//				"ptidej.example.composite5.Element");
	//			this.testSolutionComponent(
	//				this.solutions[solutions[i]],
	//				"Composite",
	//				"ptidej.example.composite5.Document");
	//			this.testSolutionComponent(
	//				this.solutions[solutions[i]],
	//				"Leaf-1",
	//				"ptidej.example.composite5.IndentedParagraph");
	//			this.testSolutionComponent(
	//				this.solutions[solutions[i]],
	//				"Leaf-2",
	//				"ptidej.example.composite5.Paragraph");
	//			this.testSolutionComponent(
	//				this.solutions[solutions[i]],
	//				"Leaf-3",
	//				"ptidej.example.composite5.Title");
	//		}
	//	}
	//	public void testSolution2() {
	//		final int[] solutions = new int[] { 3, 4, 5, 9, 10, 11 };
	//		for (int i = 0; i < solutions.length; i++) {
	//			this.testSolutionComponent(
	//				this.solutions[solutions[i]],
	//				"Component",
	//				"ptidej.example.composite5.Element");
	//			this.testSolutionComponent(
	//				this.solutions[solutions[i]],
	//				"Composite",
	//				"ptidej.example.composite5.Document");
	//			this.testSolutionComponent(
	//				this.solutions[solutions[i]],
	//				"Leaf-1",
	//				"ptidej.example.composite5.IndentedParagraph");
	//			this.testSolutionComponent(
	//				this.solutions[solutions[i]],
	//				"Leaf-2",
	//				"ptidej.example.composite5.Paragraph");
	//			this.testSolutionComponent(
	//				this.solutions[solutions[i]],
	//				"Leaf-3",
	//				"ptidej.example.composite5.SpecialIndentedParagraph");
	//			this.testSolutionComponent(
	//				this.solutions[solutions[i]],
	//				"Leaf-4",
	//				"ptidej.example.composite5.Title");
	//		}
	//	}
	//	public void testSolutionPercentage() {
	//		Composite5.assertEquals(
	//			"Distorted solution percentage",
	//			25,
	//			this.solutions[0].getPercentage());
	//		Composite5.assertEquals(
	//			"Distorted solution percentage",
	//			12,
	//			this.solutions[1].getPercentage());
	//		Composite5.assertEquals(
	//			"Distorted solution percentage",
	//			6,
	//			this.solutions[2].getPercentage());
	//		Composite5.assertEquals(
	//			"Distorted solution percentage",
	//			7,
	//			this.solutions[3].getPercentage());
	//		Composite5.assertEquals(
	//			"Distorted solution percentage",
	//			3,
	//			this.solutions[4].getPercentage());
	//		Composite5.assertEquals(
	//			"Distorted solution percentage",
	//			1,
	//			this.solutions[5].getPercentage());
	//		Composite5.assertEquals(
	//			"Distorted solution percentage",
	//			2,
	//			this.solutions[6].getPercentage());
	//		Composite5.assertEquals(
	//			"Distorted solution percentage",
	//			1,
	//			this.solutions[7].getPercentage());
	//		Composite5.assertEquals(
	//			"Distorted solution percentage",
	//			1,
	//			this.solutions[8].getPercentage());
	//		Composite5.assertEquals(
	//			"Distorted solution percentage",
	//			1,
	//			this.solutions[9].getPercentage());
	//		Composite5.assertEquals(
	//			"Distorted solution percentage",
	//			1,
	//			this.solutions[10].getPercentage());
	//		Composite5.assertEquals(
	//			"Distorted solution percentage",
	//			1,
	//			this.solutions[11].getPercentage());
	//	}
}
