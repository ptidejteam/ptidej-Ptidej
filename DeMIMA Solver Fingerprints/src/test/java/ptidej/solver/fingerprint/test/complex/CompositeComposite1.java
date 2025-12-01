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
package ptidej.solver.fingerprint.test.complex;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.Properties;

import org.junit.Assert;

import junit.framework.TestCase;
import padl.creator.classfile.CompleteClassFileCreator;
import padl.kernel.ICodeLevelModel;
import padl.kernel.exception.CreationException;
import padl.kernel.impl.Factory;
import padl.visitor.IWalker;
import ptidej.solver.Occurrence;
import ptidej.solver.OccurrenceBuilder;
import ptidej.solver.fingerprint.ReducedDomainBuilder;
import ptidej.solver.fingerprint.problem.CompositeMotif;
import ptidej.solver.java.Problem;
import ptidej.solver.java.domain.GeneratorExcludingGhosts;
import ptidej.solver.java.domain.Manager;
import util.io.ReaderInputStream;

public class CompositeComposite1 extends TestCase {
	private static int NumberOfExpectedSolutions;
	private static Occurrence[] FoundSolutions;
	private static Occurrence[] ExpectedSolutions;

	public CompositeComposite1(final String name) {
		super(name);
	}

	protected void setUp()
			throws IllegalAccessException, InstantiationException {

		if (CompositeComposite1.FoundSolutions == null) {
			final ICodeLevelModel codeLevelModel = Factory.getInstance()
					.createCodeLevelModel("ptidej.example.composite1");
			try {
				codeLevelModel
						.create(new CompleteClassFileCreator(new String[] {
								"../DeMIMA/target/test-classes/ptidej/example/composite1/" }));
			}
			catch (final CreationException e) {
				e.printStackTrace();
			}

			// Expected solutions.
			CompositeComposite1.ExpectedSolutions = SolutionReader
					.getExpectedSolutions("Composite1", codeLevelModel);
			CompositeComposite1.NumberOfExpectedSolutions = SolutionReader
					.getExpectedNumberOfSolutions("Composite1", codeLevelModel);

			final IWalker generator = new GeneratorExcludingGhosts();
			final ReducedDomainBuilder rdg = new ReducedDomainBuilder(
					codeLevelModel);

			// Solutions found.
			final Problem problem = CompositeMotif
					.getProblem(Manager.build(codeLevelModel, generator), rdg);

			final StringWriter writer = new StringWriter();
			problem.setWriter(new PrintWriter(writer));
			problem.combinatorialAutomaticSolve(true);

			final Properties properties = new Properties();
			try {
				properties.load(new ReaderInputStream(
						new StringReader(writer.getBuffer().toString())));
			}
			catch (final IOException e) {
				e.printStackTrace();
			}
			final OccurrenceBuilder solutionBuilder = OccurrenceBuilder
					.getInstance();
			CompositeComposite1.FoundSolutions = solutionBuilder
					.getCanonicalOccurrences(properties);
		}
	}

	public void testNumberOfSolutions() {
		Assert.assertEquals("Number of solutions",
				CompositeComposite1.NumberOfExpectedSolutions,
				CompositeComposite1.FoundSolutions.length);
	}

	public void testSolutions() {
		for (int i = 0; i < CompositeComposite1.NumberOfExpectedSolutions; i++) {
			Assert.assertEquals("", CompositeComposite1.ExpectedSolutions[i],
					CompositeComposite1.FoundSolutions[i]);
		}
	}

	//	public void testComponentSolutionComponent() {
	//		final int[] solutionWithAbstractDocument = new int[] { 0, 3, 6, 9 };
	//		for (int i = 0; i < solutionWithAbstractDocument.length; i++) {
	//			this.testSolutionComponent(
	//				Composite1.FoundSolutions[solutionWithAbstractDocument[i]],
	//				"Component",
	//				"ptidej.example.composite1.AbstractDocument");
	//		}
	//
	//		final int[] solutionWithElement =
	//			new int[] { 1, 2, 4, 5, 7, 8, 10, 11 };
	//		for (int i = 0; i < solutionWithAbstractDocument.length; i++) {
	//			this.testSolutionComponent(
	//				Composite1.FoundSolutions[solutionWithElement[i]],
	//				"Component",
	//				"ptidej.example.composite1.Element");
	//		}
	//	}
	//	public void testCompositeSolutionComponent() {
	//		for (int i = 0; i < Composite1.NUMBER_OF_EXPECTED_SOLUTIONS; i++) {
	//			this.testSolutionComponent(
	//				Composite1.FoundSolutions[i],
	//				"Composite",
	//				"ptidej.example.composite1.Document");
	//		}
	//	}
	//	public void testLeaf1SolutionComponent() {
	//		for (int i = 0; i < Composite1.NUMBER_OF_EXPECTED_SOLUTIONS; i++) {
	//			this.testSolutionComponent(
	//				Composite1.FoundSolutions[i],
	//				"Leaf-1",
	//				"ptidej.example.composite1.IndentedParagraph");
	//		}
	//	}
	//	public void testLeaf2SolutionComponent() {
	//		for (int i = 0; i < Composite1.NUMBER_OF_EXPECTED_SOLUTIONS; i++) {
	//			this.testSolutionComponent(
	//				Composite1.FoundSolutions[i],
	//				"Leaf-2",
	//				"ptidej.example.composite1.Paragraph");
	//		}
	//	}
	//	public void testLeaf3SolutionComponent() {
	//		for (int i = 0; i < Composite1.NUMBER_OF_EXPECTED_SOLUTIONS; i++) {
	//			this.testSolutionComponent(
	//				Composite1.FoundSolutions[i],
	//				"Leaf-3",
	//				"ptidej.example.composite1.Title");
	//		}
	//	}
	//	public void testLeaf4SolutionComponent() {
	//		final int[] solutionWithAbstractDocument = new int[] { 0, 3, 6, 9 };
	//		for (int i = 0; i < solutionWithAbstractDocument.length; i++) {
	//			this.testSolutionComponent(
	//				Composite1.FoundSolutions[solutionWithAbstractDocument[i]],
	//				"Leaf-4",
	//				"ptidej.example.composite1.Element");
	//		}
	//	}
	//	public void testSolutionPercentage() {
	//		Composite1.assertEquals(
	//			"Distorted solution percentage",
	//			12,
	//			Composite1.FoundSolutions[0].getPercentage());
	//		Composite1.assertEquals(
	//			"Distorted solution percentage",
	//			12,
	//			Composite1.FoundSolutions[1].getPercentage());
	//		Composite1.assertEquals(
	//			"Distorted solution percentage",
	//			6,
	//			Composite1.FoundSolutions[2].getPercentage());
	//		Composite1.assertEquals(
	//			"Distorted solution percentage",
	//			3,
	//			Composite1.FoundSolutions[3].getPercentage());
	//		Composite1.assertEquals(
	//			"Distorted solution percentage",
	//			3,
	//			Composite1.FoundSolutions[4].getPercentage());
	//		for (int i = 5; i < 11; i++) {
	//			Composite1.assertEquals(
	//				"Distorted solution percentage",
	//				1,
	//				Composite1.FoundSolutions[i].getPercentage());
	//		}
	//	}
}
