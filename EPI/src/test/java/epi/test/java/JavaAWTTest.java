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
package epi.test.java;

import java.util.Hashtable;

import org.junit.Assert;

import epi.solver.Approximation;
import epi.solver.EPISolver;
import epi.solver.OptimisedBitVectorSolver;
import epi.solver.Solution;
import epi.solver.StringBuilder;
import junit.framework.TestCase;
import padl.analysis.UnsupportedSourceModelException;
import padl.analysis.repository.AACRelationshipsAnalysis;
import padl.creator.classfile.CompleteClassFileCreator;
import padl.kernel.ICodeLevelModel;
import padl.kernel.IIdiomLevelModel;
import padl.kernel.exception.CreationException;
import padl.kernel.impl.Factory;
import util.io.ProxyConsole;
import util.io.ProxyDisk;

/**
 * @author Yann-Gaël Guéhéneuc
 * @since  2007/01/24
 */

public class JavaAWTTest extends TestCase {
	private static int NumberOfExpectedSolutions;
	private static Solution[] FoundSolutions;

	public JavaAWTTest(final String name) {
		super(name);
	}
	protected void setUp() throws IllegalAccessException,
			InstantiationException {

		if (JavaAWTTest.FoundSolutions == null) {
			try {
				final long startTime = System.currentTimeMillis();

				final ICodeLevelModel codeLevelModel =
					Factory.getInstance().createCodeLevelModel(
						"Java AWT v1.4.2_04");
				codeLevelModel.create(new CompleteClassFileCreator(
						new String[] {
								"../EPI/target/test-classes/Java AWT v1.4.2_04/" },
					true));
				final IIdiomLevelModel idiomLevelModel =
					(IIdiomLevelModel) new AACRelationshipsAnalysis()
						.invoke(codeLevelModel);
				final String programString =
					StringBuilder.buildModelString(idiomLevelModel);
				final String motifString =
					StringBuilder.buildPatternString("Composite");
				final Hashtable domains = null;
				final EPISolver solver =
					new OptimisedBitVectorSolver(
						programString,
						motifString,
						"Composite",
						new Approximation(),
						domains);
				JavaAWTTest.FoundSolutions = solver.computeSolutions();

				ProxyConsole
					.getInstance()
					.debugOutput()
					.print(JavaAWTTest.FoundSolutions.length);
				ProxyConsole
					.getInstance()
					.debugOutput()
					.print(" solutions in ");
				ProxyConsole
					.getInstance()
					.debugOutput()
					.print(System.currentTimeMillis() - startTime);
				ProxyConsole.getInstance().debugOutput().println(" ms.");
				Solution.print(JavaAWTTest.FoundSolutions, ProxyDisk
					.getInstance()
					.fileTempOutput("Java AWT v1.4.2_04.solutions.txt"));
			}
			catch (final CreationException e) {
				e.printStackTrace(ProxyConsole.getInstance().errorOutput());
			}
			catch (final UnsupportedSourceModelException e) {
				e.printStackTrace(ProxyConsole.getInstance().errorOutput());
			}
		}
	}
	public void testNumberOfSolutions() {
		Assert.assertEquals(
			"Number of solutions",
			JavaAWTTest.NumberOfExpectedSolutions,
			JavaAWTTest.FoundSolutions.length);
	}
}
