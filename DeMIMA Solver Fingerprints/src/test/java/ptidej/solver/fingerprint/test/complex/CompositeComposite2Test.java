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

public class CompositeComposite2Test extends TestCase {
	private static int NumberOfExpectedSolutions;
	private static Occurrence[] FoundSolutions;
	private static Occurrence[] ExpectedSolutions;

	public CompositeComposite2Test(final String name) {
		super(name);
	}

	protected void setUp()
			throws IllegalAccessException, InstantiationException {

		if (CompositeComposite2Test.FoundSolutions == null) {
			final ICodeLevelModel codeLevelModel = Factory.getInstance()
					.createCodeLevelModel("ptidej.example.composite2");
			try {
				codeLevelModel
						.create(new CompleteClassFileCreator(new String[] {
								"../DeMIMA/target/test-classes/ptidej/example/composite2/" }));
			}
			catch (final CreationException e) {
				e.printStackTrace();
			}

			// Expected solutions.
			CompositeComposite2Test.ExpectedSolutions = SolutionReader
					.getExpectedSolutions("Composite2", codeLevelModel);
			CompositeComposite2Test.NumberOfExpectedSolutions = SolutionReader
					.getExpectedNumberOfSolutions("Composite2", codeLevelModel);

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
			CompositeComposite2Test.FoundSolutions = solutionBuilder
					.getCanonicalOccurrences(properties);
		}
	}

	public void testNumberOfSolutions() {
		Assert.assertEquals("Number of solutions",
				CompositeComposite2Test.NumberOfExpectedSolutions,
				CompositeComposite2Test.FoundSolutions.length);
	}

	public void testSolutions() {
		for (int i = 0; i < CompositeComposite2Test.NumberOfExpectedSolutions; i++) {
			Assert.assertEquals("", CompositeComposite2Test.ExpectedSolutions[i],
					CompositeComposite2Test.FoundSolutions[i]);
		}
	}
}
