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
package ptidej.solver.claire.test;

import java.lang.reflect.InvocationTargetException;
import java.util.Properties;
import org.junit.Assert;
import junit.framework.TestCase;
import padl.kernel.IAbstractLevelModel;
import padl.kernel.IAbstractModel;
import padl.motif.IDesignMotifModel;
import ptidej.solver.Occurrence;
import ptidej.solver.OccurrenceBuilder;
import ptidej.solver.claire.OccurrenceGenerator;

public abstract class Primitive extends TestCase {
	protected final static int CANONICAL_SOLUTIONS = 1;
	protected final static int ALL_SOLUTIONS = 2;
	protected final static char[] SUPER_ENTITY = "SuperEntity".toCharArray();
	public static final char[] SUB_ENTITY = "SubEntity".toCharArray();

	private static Class PreviousTestCase;
	private static int PreviousSolutionType;
	private static char[] PreviousPatternName;
	private static IAbstractModel PreviousSource;
	private static int PreviousAlgorithm;
	private static Occurrence[] Solutions;

	public Primitive(final String name) {
		super(name);
	}
	protected Occurrence[] testDesignPattern(
		final Class testCase,
		final int solutionType,
		final char[] patternName,
		final Class sourceClass,
		final int solver,
		final int algorithm) throws IllegalAccessException,
			InstantiationException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {

		// I build an instance of the pattern and of
		// the source code.
		// For the moment, this means that I build
		// instances of subclasses of class AbstractLevelModel
		// that represent a pattern and the  source code.
		return this.testDesignPattern(
			testCase,
			solutionType,
			patternName,
			null,
			(IAbstractModel) sourceClass.getDeclaredConstructor().newInstance(),
			solver,
			algorithm);
	}
	protected Occurrence[] testDesignPattern(
		final Class testCase,
		final int solutionType,
		final char[] patternName,
		final IAbstractLevelModel source,
		final int solver,
		final int algorithm) {

		return this.testDesignPattern(
			testCase,
			solutionType,
			patternName,
			null,
			source,
			solver,
			algorithm);
	}
	private Occurrence[] testDesignPattern(
		final Class testCase,
		final int solutionType,
		final char[] patternName,
		final IDesignMotifModel aMotif,
		final IAbstractModel source,
		final int solver,
		final int algorithm) {

		// Yann 2002/08/11: Memory!
		// For a given test case, I call once and only once the constraint
		// solver by recording the current test case and the constraint
		// solutions. (This avoids calling over again and again the
		// constraint solver.)

		if (Primitive.PreviousTestCase != testCase
				|| Primitive.PreviousSolutionType != solutionType
				|| !Primitive.PreviousPatternName.equals(patternName)
				|| Primitive.PreviousSource == null
				|| !Primitive.PreviousSource.getName().equals(source.getName())
				|| Primitive.PreviousAlgorithm != algorithm) {

			Primitive.PreviousTestCase = testCase;
			Primitive.PreviousSolutionType = solutionType;
			Primitive.PreviousPatternName = patternName;
			Primitive.PreviousSource = source;
			Primitive.PreviousAlgorithm = algorithm;

			// I call the constraint solver.
			final OccurrenceGenerator solutionGenerator =
				OccurrenceGenerator.getInstance();
			final Properties generatedSolutions;
			if (aMotif == null) {
				generatedSolutions =
					solutionGenerator.getOccurrences(
						patternName,
						source,
						OccurrenceGenerator.VERSION_PTIDEJSOLVER3,
						solver,
						algorithm);
			}
			else {
				generatedSolutions =
					solutionGenerator.getOccurrences(
						aMotif,
						source,
						OccurrenceGenerator.VERSION_PTIDEJSOLVER3,
						solver,
						algorithm);
			}

			// I build the solutions from the solutions given by the constraint
			// solver and the instance of class AbstractLevelModel representing the source code.
			final OccurrenceBuilder solutionBuilder =
				OccurrenceBuilder.getInstance();
			if (solutionType == Primitive.ALL_SOLUTIONS) {
				Primitive.Solutions =
					solutionBuilder.getAllOccurrences(generatedSolutions);
			}
			else if (solutionType == Primitive.CANONICAL_SOLUTIONS) {
				Primitive.Solutions =
					solutionBuilder.getCanonicalOccurrences(generatedSolutions);
			}
			else {
				Assert
					.fail("Unrecognized type of solution for Primitive.testDesignPattern(...).");
				return null;
			}
		}

		return Primitive.Solutions;
	}
	protected Occurrence[] testDesignPattern(
		final Class testCase,
		final int solutionType,
		final Class patternClass,
		final Class sourceClass,
		final int solver,
		final int algorithm) throws IllegalAccessException,
			InstantiationException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {

		// I build an instance of the pattern and of
		// the source code.
		// For the moment, this means that I build
		// instances of subclasses of class AbstractLevelModel
		// that represent a pattern and the  source code.
		final IDesignMotifModel pattern =
			(IDesignMotifModel) patternClass.getDeclaredConstructor().newInstance();

		return this.testDesignPattern(
			testCase,
			solutionType,
			pattern.getName(),
			pattern,
			(IAbstractModel) sourceClass.getDeclaredConstructor().newInstance(),
			solver,
			algorithm);
	}
	protected Occurrence[] testDesignPattern(
		final Class testCase,
		final int solutionType,
		final IDesignMotifModel aMotif,
		final IAbstractLevelModel source,
		final int solver,
		final int algorithm) {

		return this.testDesignPattern(
			testCase,
			solutionType,
			aMotif.getName(),
			aMotif,
			source,
			solver,
			algorithm);
	}
	protected void testSolutionComponent(
		final Occurrence solution,
		final char[] solutionComponentName,
		final String expectedValue) {

		Assert.assertEquals(String.valueOf(solutionComponentName)
				+ " solution component value", expectedValue, solution
			.getComponent(solutionComponentName)
			.getValue());
	}
}
