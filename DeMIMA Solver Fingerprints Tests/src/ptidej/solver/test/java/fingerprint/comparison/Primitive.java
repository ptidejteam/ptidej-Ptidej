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
package ptidej.solver.test.java.fingerprint.comparison;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringReader;
import java.io.StringWriter;
import java.lang.reflect.InvocationTargetException;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import junit.framework.TestCase;
import padl.creator.classfile.CompleteClassFileCreator;
import padl.kernel.ICodeLevelModel;
import padl.kernel.IGhost;
import padl.kernel.exception.CreationException;
import padl.kernel.impl.Factory;
import ptidej.solver.Occurrence;
import ptidej.solver.OccurrenceBuilder;
import ptidej.solver.OccurrenceComponent;
import ptidej.solver.Problem;
import ptidej.solver.domain.Manager;
import ptidej.solver.fingerprint.ReducedDomainBuilder;
import ptidej.solver.fingerprint.Rule;
import util.io.ProxyDisk;
import util.io.ReaderInputStream;
import choco.integer.IntVar;

/**
 * @author Jean-Yves Guyomarc'h
 * @since  2004/09/19
 */
public abstract class Primitive extends TestCase {
	private static Logger Logger = null;
	protected static Occurrence[] automaticSolve(
		final Class testClass,
		final String path,
		final String ilmName) {

		Primitive.Logger
			.setMode(ptidej.solver.test.java.fingerprint.comparison.Logger.WITHOUT_RULE);
		final Occurrence[] sol =
			Primitive.solveAndLog(testClass, path, ilmName, null);
		printSolution(ilmName + "_Sol_NoRule", sol);
		return sol;
	}
	protected static Occurrence[] automaticSolve(
		final Class testClass,
		final String path,
		final String ilmName,
		final Rule role) {

		Primitive.Logger
			.setMode(ptidej.solver.test.java.fingerprint.comparison.Logger.WITH_RULE);
		final Occurrence[] sol =
			Primitive.solveAndLog(testClass, path, ilmName, role);
		printSolution(ilmName + "_Sol", sol);
		return sol;
	}
	private static int countGhostSolution(
		final Occurrence[] someSolutions,
		final ICodeLevelModel aCodeLevelModel) {

		int cpt = 0;
		for (int i = 0; i < someSolutions.length; i++) {
			boolean ghosted = false;
			final Iterator iter = someSolutions[i].getComponents().iterator();
			while (iter.hasNext()) {
				// TEST IF INSTANCE OF GHOST
				OccurrenceComponent sc = (OccurrenceComponent) iter.next();
				if (aCodeLevelModel.getConstituentFromName(sc.getValue()) instanceof IGhost) {
					ghosted = true;
				}
			}
			if (ghosted) {
				cpt++;
			}
		}

		return cpt;
	}
	protected static void initialize(final String testName) {
		Primitive.Logger = new Logger(testName);
	}
	protected static void setLoggerMode(final int loggerMode) {
		Primitive.Logger.setMode(loggerMode);
	}
	private static void printSolution(
		final String testName,
		final Occurrence[] sol) {

		final PrintWriter out =
			new PrintWriter(ProxyDisk.getInstance().fileTempOutput(
				"rsc\\" + testName + ".txt"));
		out.println("Solutions for : " + testName + "\n");
		for (int i = 0; i < sol.length; i++) {
			out.println(sol[i]);
		}
		out.flush();
		out.close();
	}
	private static Occurrence[] solveAndLog(
		final Class testClass,
		final String path,
		final String ilmName,
		final Rule role) {

		Occurrence[] solutions = new Occurrence[0];
		ICodeLevelModel codeLevelModel = null;
		try {
			Primitive.Logger.setStart(System.currentTimeMillis());

			codeLevelModel =
				Factory.getInstance().createCodeLevelModel(ilmName);
			try {
				codeLevelModel.create(new CompleteClassFileCreator(
					new String[] { path }));
			}
			catch (final CreationException e) {
				e.printStackTrace();
			}
			Primitive.Logger.setILMCreation(System.currentTimeMillis());

			Problem problem = null;
			if (role != null) {
				Primitive.Logger.setRole(role);
				final ReducedDomainBuilder rdg =
					new ReducedDomainBuilder(codeLevelModel);
				problem =
					(Problem) testClass
						.getMethod(
							"getProblem",
							new Class[] { List.class, List.class })
						.invoke(
							null,
							new Object[] {
									Manager.build(codeLevelModel),
									Manager.build(rdg
										.computeReducedDomain(role)) });

			}
			else {
				problem =
					(Problem) testClass.getMethod(
						"getProblem",
						new Class[] { List.class }).invoke(
						null,
						new Object[] { Manager.build(codeLevelModel) });
			}

			Primitive.Logger.setProblemCreation(System.currentTimeMillis());

			IntVar[] vars = problem.getVars();
			Primitive.Logger.setDomainSize(problem.getAllEntities().size());
			for (int i = 0; i < vars.length; i++) {
				Primitive.Logger
					.addVar(
						vars[i].toString(),
						vars[i].getDomainSize(),
						(((double) (problem.getAllEntities().size() - (double) vars[i]
							.getDomainSize()) / (double) problem
							.getAllEntities()
							.size()) * 100.0));
			}

			// GC
			for (int i = 0; i < 5; i++) {
				System.gc();
			}

			final StringWriter writer = new StringWriter();
			problem.setWriter(new PrintWriter(writer));
			problem.combinatorialAutomaticSolve(true);

			final Properties properties = new Properties();

			properties.load(new ReaderInputStream(new StringReader(writer
				.getBuffer()
				.toString())));

			final OccurrenceBuilder solutionBuilder =
				OccurrenceBuilder.getInstance();

			solutions = solutionBuilder.getCanonicalOccurrences(properties);
		}
		catch (final IllegalArgumentException e) {
			e.printStackTrace();
		}
		catch (final SecurityException e) {
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
		catch (final IOException e) {
			e.printStackTrace();
		}

		Primitive.Logger.setEndProcess(System.currentTimeMillis());
		Primitive.Logger.setNumSolutions(solutions.length);
		Primitive.Logger.setGhostedSolutions(countGhostSolution(
			solutions,
			codeLevelModel));

		if (role == null) {
			Primitive.Logger.print();
		}

		return solutions;
	}
	public Primitive(final String name) {
		super(name);
	}
}
