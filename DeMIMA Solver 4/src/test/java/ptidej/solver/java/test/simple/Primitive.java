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
package ptidej.solver.java.test.simple;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringReader;
import java.io.StringWriter;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Properties;

import junit.framework.TestCase;
import padl.kernel.IAbstractLevelModel;
import padl.kernel.IAbstractModel;
import ptidej.solver.Occurrence;
import ptidej.solver.OccurrenceBuilder;
import ptidej.solver.java.Problem;
import ptidej.solver.java.domain.Manager;
import util.io.ReaderInputStream;

/**
 * @author Yann-Gaël Guéhéneuc
 * @since  2004/09/19
 */
abstract class Primitive extends TestCase {
	protected static Occurrence[] automaticSolve(final Class problemClass,
			final Class sourceClass) {

		Occurrence[] solutions = new Occurrence[0];

		try {
			final IAbstractModel abstractModel = (IAbstractModel) sourceClass
					.getDeclaredConstructor().newInstance();

			final Problem problem = (Problem) problemClass
					.getMethod("getProblem", new Class[] { List.class })
					.invoke(null,
							new Object[] { Manager.build(abstractModel) });

			final StringWriter writer = new StringWriter();
			problem.setWriter(new PrintWriter(writer));
			problem.automaticSolve(true);

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

			solutions = solutionBuilder.getAllOccurrences(properties);
		}
		catch (final IllegalArgumentException e) {
			e.printStackTrace();
		}
		catch (final SecurityException e) {
			e.printStackTrace();
		}
		catch (final InstantiationException e) {
			e.printStackTrace();
		}
		catch (final IllegalAccessException e) {
			e.printStackTrace();
		}
		catch (final InvocationTargetException e) {
			e.printStackTrace();
		}
		catch (final NoSuchMethodException e) {
			e.printStackTrace();
		}

		return solutions;
	}

	// JYves 6/10/2004
	// Added for Fingerprinting testing
	protected static Occurrence[] automaticSolve(final Problem problem,
			final Class sourceClass) {

		Occurrence[] solutions = new Occurrence[0];

		try {
			//	final IAbstractLevelModel abstractLevelModel =
			//		(IAbstractLevelModel) sourceClass.getDeclaredConstructor().newInstance();

			final StringWriter writer = new StringWriter();
			problem.setWriter(new PrintWriter(writer));
			problem.automaticSolve(true);

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

			solutions = solutionBuilder.getAllOccurrences(properties);
		}
		catch (final IllegalArgumentException e) {
			e.printStackTrace();
		}
		//	catch (final IllegalAccessException e) {
		//		e.printStackTrace();
		//	}
		//	catch (final InstantiationException e) {
		//		e.printStackTrace();
		//	}

		return solutions;
	}

	protected static Occurrence[] combinatorialAutomaticSolve(
			final Class testClass, final Class sourceClass) {

		// Yann 2004/09/20: Duplicate!
		// This method is an exact duplicate of method
		// automaticSolve() but for the call to the solver.

		Occurrence[] solutions = new Occurrence[0];

		try {
			final IAbstractLevelModel abstractLevelModel = (IAbstractLevelModel) sourceClass
					.getDeclaredConstructor().newInstance();

			final Problem problem = (Problem) testClass
					.getMethod("getProblem", new Class[] { List.class })
					.invoke(null,
							new Object[] { Manager.build(abstractLevelModel) });

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

			solutions = solutionBuilder.getCanonicalOccurrences(properties);
		}
		catch (final IllegalArgumentException e) {
			e.printStackTrace();
		}
		catch (final SecurityException e) {
			e.printStackTrace();
		}
		catch (final InstantiationException e) {
			e.printStackTrace();
		}
		catch (final IllegalAccessException e) {
			e.printStackTrace();
		}
		catch (final InvocationTargetException e) {
			e.printStackTrace();
		}
		catch (final NoSuchMethodException e) {
			e.printStackTrace();
		}

		return solutions;
	}

	// JYves 6/10/2004
	// Added for Fingerprinting testing	
	//	protected static Solution[] combinatorialAutomaticSolve(
	//		final Problem problem,
	//		final Class sourceClass) {
	//
	//		// Yann 2004/09/20: Duplicate!
	//		// This method is an exact duplicate of method
	//		// automaticSolve() but for the call to the solver.
	//
	//		Solution[] solutions = new Solution[0];
	//
	//		try {
	//			final IAbstractLevelModel abstractLevelModel =
	//				(IAbstractLevelModel) sourceClass.getDeclaredConstructor().newInstance();
	//
	//			final StringWriter writer = new StringWriter();
	//			problem.setWriter(new PrintWriter(writer));
	//			problem.combinatorialAutomaticSolve(true);
	//
	//			final Properties properties = new Properties();
	//			try {
	//				properties.load(
	//					new ReaderInputStream(
	//						new StringReader(writer.getBuffer().toString())));
	//			}
	//			catch (final IOException e) {
	//				e.printStackTrace();
	//			}
	//			final SolutionBuilder solutionBuilder =
	//				SolutionBuilder.getCurrentSolutionBuilder();
	//
	//			solutions =
	//				solutionBuilder.getCanonicalSolutions(
	//					properties,
	//					abstractLevelModel);
	//		}
	//		catch (final IllegalArgumentException e) {
	//			e.printStackTrace();
	//		}
	//		catch (final IllegalAccessException e) {
	//			e.printStackTrace();
	//		}
	//		catch (final InstantiationException e) {
	//			e.printStackTrace();
	//		}
	//
	//		return solutions;
	//	}
	public Primitive(final String name) {
		super(name);
	}
}
