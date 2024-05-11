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
package ptidej.solver.fingerprint.test.comparison;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringReader;
import java.io.StringWriter;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import junit.framework.TestCase;
import padl.creator.classfile.CompleteClassFileCreator;
import padl.kernel.IAbstractLevelModel;
import padl.kernel.ICodeLevelModel;
import padl.kernel.IGhost;
import padl.kernel.exception.CreationException;
import padl.kernel.impl.Factory;
import ptidej.solver.Occurrence;
import ptidej.solver.OccurrenceBuilder;
import ptidej.solver.OccurrenceComponent;
import ptidej.solver.fingerprint.ReducedDomainBuilder;
import ptidej.solver.fingerprint.Rule;
import ptidej.solver.java.Problem;
import ptidej.solver.java.domain.Manager;
import util.io.ProxyDisk;
import util.io.ReaderInputStream;

/**
 * @author Jean-Yves Guyomarc'h
 * @since  2004/09/19
 */
abstract class Primitive extends TestCase {
	protected static Occurrence[] automaticSolve(final Class testClass,
			final String path, final String ilmName) {

		final Occurrence[] sol = Primitive.solveAndLog(testClass, path, ilmName,
				null);
		printSolution(ilmName + "_Sol_NoRule", sol);
		return sol;
	}

	protected static Occurrence[] automaticSolve(final Class testClass,
			final String path, final String ilmName, final Rule role) {

		final Occurrence[] sol = Primitive.solveAndLog(testClass, path, ilmName,
				role);
		printSolution(ilmName + "_Sol", sol);
		return sol;
	}

	private static int countGhostSolution(final Occurrence[] someSolutions,
			final ICodeLevelModel aCodeLevelModel) {

		int cpt = 0;
		for (int i = 0; i < someSolutions.length; i++) {
			boolean ghosted = false;
			final Iterator iter = someSolutions[i].getComponents().iterator();
			while (iter.hasNext()) {
				// TEST IF INSTANCE OF GHOST
				OccurrenceComponent sc = (OccurrenceComponent) iter.next();
				if (aCodeLevelModel.getConstituentFromName(
						sc.getValue()) instanceof IGhost) {
					ghosted = true;
				}
			}
			if (ghosted) {
				cpt++;
			}
		}

		return cpt;
	}

	private static void printSolution(final String testName,
			final Occurrence[] sol) {

		final PrintWriter out = new PrintWriter(ProxyDisk.getInstance()
				.fileTempOutput("rsc\\" + testName + ".txt"));
		out.println("Solutions for : " + testName + "\n");
		for (int i = 0; i < sol.length; i++) {
			out.println(sol[i]);
		}
		out.flush();
		out.close();
	}

	private static Occurrence[] solveAndLog(final Class testClass,
			final String path, final String ilmName, final Rule role) {

		Occurrence[] solutions = new Occurrence[0];
		ICodeLevelModel codeLevelModel = null;
		try {
			codeLevelModel = Factory.getInstance()
					.createCodeLevelModel(ilmName);
			try {
				codeLevelModel.create(new CompleteClassFileCreator(
						new String[] { path }, true));
			}
			catch (final CreationException e) {
				e.printStackTrace();
			}

			Problem problem = null;
			if (role != null) {
				final ReducedDomainBuilder rdg = new ReducedDomainBuilder(
						codeLevelModel);
				final IAbstractLevelModel newModel = rdg
						.computeReducedDomain(role);
				final Method problemMethod = testClass.getMethod("getProblem",
						new Class[] { List.class, ReducedDomainBuilder.class });
				final Object[] problemArguments = new Object[] {
						Manager.build(newModel),
						new ReducedDomainBuilder(newModel) };
				problem = (Problem) problemMethod.invoke(null,
						problemArguments);
			}
			else {
				final ReducedDomainBuilder rdg = new ReducedDomainBuilder(
						codeLevelModel);
				final Method problemMethod = testClass.getMethod("getProblem",
						new Class[] { List.class, ReducedDomainBuilder.class });
				final Object[] problemArguments = new Object[] {
						Manager.build(codeLevelModel), rdg };
				problem = (Problem) problemMethod.invoke(null,
						problemArguments);
			}

			// GC
			for (int i = 0; i < 5; i++) {
				System.gc();
			}

			final StringWriter writer = new StringWriter();
			problem.setWriter(new PrintWriter(writer));
			problem.combinatorialAutomaticSolve(true);

			final Properties properties = new Properties();

			properties.load(new ReaderInputStream(
					new StringReader(writer.getBuffer().toString())));

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

		return solutions;
	}

	public Primitive(final String name) {
		super(name);
	}
}
