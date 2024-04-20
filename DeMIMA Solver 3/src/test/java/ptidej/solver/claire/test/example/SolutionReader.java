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

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import padl.kernel.IAbstractLevelModel;
import ptidej.solver.Occurrence;
import ptidej.solver.OccurrenceBuilder;

/**
 * @author Yann-Gaël Guéhéneuc 
 * @since  2004/08/16
 */
public class SolutionReader {
	private static String CurrentTestName;
	private static Occurrence[] ExpectedSolutions;
	private static int NumberOfExpectedSolutions;

	public static int getExpectedNumberOfSolutions(final String testName,
			final IAbstractLevelModel anAbstractLevelModel) {

		SolutionReader.readExpectedSolutions(testName, anAbstractLevelModel);
		return SolutionReader.NumberOfExpectedSolutions;
	}

	public static Occurrence[] getExpectedSolutions(final String testName,
			final IAbstractLevelModel anAbstractLevelModel) {

		SolutionReader.readExpectedSolutions(testName, anAbstractLevelModel);
		return SolutionReader.ExpectedSolutions;
	}

	private static void readExpectedSolutions(final String testName,
			final IAbstractLevelModel anAbstractLevelModel) {

		if (SolutionReader.ExpectedSolutions == null
				|| SolutionReader.CurrentTestName == null
				|| SolutionReader.CurrentTestName != null
						&& !SolutionReader.CurrentTestName.equals(testName)) {

			SolutionReader.CurrentTestName = testName;

			final OccurrenceBuilder solutionBuilder = OccurrenceBuilder
					.getInstance();
			final Properties properties = new Properties();
			try {
				properties.load(new FileInputStream(
						"../DeMIMA Solver 3/target/test-classes/ptidej/solver/claire/test/example/ConstraintResultsFor"
								+ testName + ".ini"));
			}
			catch (final FileNotFoundException fnfe) {
				fnfe.printStackTrace();
			}
			catch (final IOException ioe) {
				ioe.printStackTrace();
			}
			SolutionReader.ExpectedSolutions = solutionBuilder
					.getAllOccurrences(properties);
			SolutionReader.NumberOfExpectedSolutions = SolutionReader.ExpectedSolutions.length;
		}
	}

	private SolutionReader() {
	}
}
