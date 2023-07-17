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
package epi.test.helper;

import java.io.PrintStream;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.StringTokenizer;
import padl.analysis.UnsupportedSourceModelException;
import padl.analysis.repository.AACRelationshipsAnalysis;
import padl.creator.classfile.CompleteClassFileCreator;
import padl.kernel.ICodeLevelModel;
import padl.kernel.IFirstClassEntity;
import padl.kernel.IIdiomLevelModel;
import padl.kernel.exception.CreationException;
import padl.kernel.impl.Factory;
import util.io.ProxyConsole;
import util.io.ProxyDisk;
import util.io.WriterOutputStream;
import epi.solver.OptimisedBitVectorSolver;
import epi.solver.Solution;
import epi.solver.StringBuilder;
import epi.test.data.AbstractFactory;
import epi.test.data.Adapter;
import epi.test.data.Approximations;
import epi.test.data.BitVectorPattern;
import epi.test.data.Command;
import epi.test.data.Composite;
import epi.test.data.Decorator;
import epi.test.data.Observer;
import epi.test.data.ObserverWithConcreteSubject;
import epi.test.data.State;
import epi.test.data.UniqueState;

/**
 * @author Olivier Kaczor
 */
public class JavaEPICaller {
	private static final BitVectorPattern[] PATTERNS = new BitVectorPattern[] {
			// AbstractFactory.getInstance(),
			// Composite.getInstance(),
			Decorator.getInstance(), Observer.getInstance(),
			ObserverWithConcreteSubject.getInstance(), State.getInstance(),
			UniqueState.getInstance(), Adapter.getInstance(),
			Command.getInstance() };
	private static final String[] PROGRAM_NAMES = new String[] {
			//	"Juzzle",
			//	"JUnit",
			//	"JHotDraw",
			//	"Trove",
			"QuickUML", "Gantt", "JRefactory", "Azureus" };
	private static final String[] PROGRAM_PATHS =
		new String[] {
				// "../../P-MARt Workspace/Juzzle/bin/org/game/Juzzle/",
				// "../../P-MARt Workspace/JUnit v3.7/bin/junit/",
				// "../../P-MARt Workspace/JHotDraw v5.1/bin/",
				// "../../P-MARt Workspace/Trove/bin/gnu/trove/",
				"../../P-MARt Workspace/QuickUML 2001/bin/",
				"../../P-MARt Workspace/GanttProject v1.10.2/bin/net/sourceforge/ganttproject/",
				"../../P-MARt Workspace/JRefactory v2.6.24/bin/",
				"../../P-MARt Workspace/Azureus v2.3.0.6/bin/com/" };

	private static void callEPISolver1() {
		for (int i = 0; i < PROGRAM_NAMES.length; i++) {
			final IIdiomLevelModel model =
				JavaEPICaller.createModel(
					PROGRAM_PATHS[i],
					null,
					new PrintWriter(ProxyConsole.getInstance().debugOutput()));

			ProxyConsole
				.getInstance()
				.debugOutput()
				.println("\n--------------------------------------------------");
			ProxyConsole
				.getInstance()
				.debugOutput()
				.println("Creating string...");
			final long start = System.currentTimeMillis();
			final String programString = StringBuilder.buildModelString(model);
			final long end = System.currentTimeMillis();

			final StringTokenizer tokenizer =
				new StringTokenizer(programString, " ");

			final String header =
				"String Creation Time: "
						+ (end - start)
						/ 1000
						+ "\nString Length in Tokens: "
						+ tokenizer.countTokens()
						+ "\n--------------------------------------------------\n";

			try {
				final PrintWriter out =
					new PrintWriter(ProxyDisk.getInstance().fileTempOutput(
						"rsc/" + PROGRAM_NAMES[i] + ".abstractfactory.txt"));
				out.println(header);

				JavaEPICaller.callEPISolverForAbstractFactory(
					model,
					programString,
					out);
			}
			catch (final Exception e) {
			}

			try {
				final PrintWriter out =
					new PrintWriter(ProxyDisk.getInstance().fileTempOutput(
						"rsc/" + PROGRAM_NAMES[i] + ".composite.txt"));
				out.println(header);

				JavaEPICaller.callEPISolverForComposite(
					model,
					programString,
					out);
			}
			catch (final Exception e) {
			}

			try {
				final PrintWriter out =
					new PrintWriter(ProxyDisk.getInstance().fileTempOutput(
						"rsc/" + PROGRAM_NAMES[i] + ".decorator.txt"));
				out.println(header);

				JavaEPICaller.callEPISolverForDecorator(
					model,
					programString,
					out);
			}
			catch (final Exception e) {
			}

			try {
				final PrintWriter out =
					new PrintWriter(ProxyDisk.getInstance().fileTempOutput(
						"rsc/" + PROGRAM_NAMES[i] + ".observer.txt"));
				out.println(header);

				JavaEPICaller.callEPISolverForObserver(
					model,
					programString,
					out);
			}
			catch (final Exception e) {
			}

			try {
				final PrintWriter out =
					new PrintWriter(ProxyDisk.getInstance().fileTempOutput(
						"rsc/" + PROGRAM_NAMES[i]
								+ ".observerwithconcretesubject.txt"));
				out.println(header);

				JavaEPICaller.callEPISolverForObserverWithConcreteSubject(
					model,
					programString,
					out);
			}
			catch (final Exception e) {
			}

			try {
				final PrintWriter out =
					new PrintWriter(ProxyDisk.getInstance().fileTempOutput(
						"rsc/" + PROGRAM_NAMES[i] + ".state.txt"));
				out.println(header);

				JavaEPICaller.callEPISolverForState(model, programString, out);
			}
			catch (final Exception e) {
			}

			try {
				final PrintWriter out =
					new PrintWriter(ProxyDisk.getInstance().fileTempOutput(
						"rsc/" + PROGRAM_NAMES[i] + ".uniquestate.txt"));
				out.println(header);

				JavaEPICaller.callEPISolverForUniqueState(
					model,
					programString,
					out);
			}
			catch (final Exception e) {
			}

			try {
				final PrintWriter out =
					new PrintWriter(ProxyDisk.getInstance().fileTempOutput(
						"rsc/" + PROGRAM_NAMES[i] + ".adapter.txt"));
				out.println(header);

				JavaEPICaller
					.callEPISolverForAdapter(model, programString, out);
			}
			catch (final Exception e) {
				e.printStackTrace();
			}

			try {
				final PrintWriter out =
					new PrintWriter(ProxyDisk.getInstance().fileTempOutput(
						"rsc/" + PROGRAM_NAMES[i] + ".command.txt"));
				out.println(header);

				JavaEPICaller
					.callEPISolverForCommand(model, programString, out);
			}
			catch (final Exception e) {
			}
		}
	}
	private static void callEPISolver2(
		final int programNumber,
		final int patternNumber) {

		ProxyConsole.getInstance().debugOutput().print("Program: ");
		ProxyConsole
			.getInstance()
			.debugOutput()
			.print(PROGRAM_NAMES[programNumber]);
		ProxyConsole.getInstance().debugOutput().print(" with pattern: ");
		ProxyConsole
			.getInstance()
			.debugOutput()
			.println(PATTERNS[patternNumber].getName());

		try {
			final PrintWriter out =
				new PrintWriter(ProxyDisk.getInstance().fileTempOutput(
					"rsc/" + PROGRAM_NAMES[programNumber] + "."
							+ PATTERNS[patternNumber].getName() + ".txt"));
			System.setErr(new PrintStream(new WriterOutputStream(out)));
			System.setOut(new PrintStream(new WriterOutputStream(out)));

			final IIdiomLevelModel model =
				JavaEPICaller.createModel(
					PROGRAM_PATHS[programNumber],
					null,
					out);

			out.println("\n--------------------------------------------------");
			out.println("Creating string...");
			final long start = System.currentTimeMillis();
			final String programString = StringBuilder.buildModelString(model);
			final long end = System.currentTimeMillis();

			final StringTokenizer tokenizer =
				new StringTokenizer(programString, " ");

			out.println("String Creation Time: " + (end - start) / 1000
					+ "\nString Length in Tokens: " + tokenizer.countTokens()
					+ "\n--------------------------------------------------\n");

			out.println("\n--------------------------------------------------");
			out.println("Computing solutions...");
			for (int j = 0; j < Approximations.APPROXIMATIONS_NAMES.length; j++) {
				final String approximationName =
					Approximations.APPROXIMATIONS_NAMES[j];
				out.println(approximationName);
				final OptimisedBitVectorSolver solver =
					new OptimisedBitVectorSolver(
						programString,
						PATTERNS[patternNumber].getStrings()[j],
						PATTERNS[patternNumber].getName(),
						Approximations.APPROXIMATIONS[j]);
				final long start2 = System.currentTimeMillis();
				final Solution[] solutions = solver.computeSolutions();
				out.println("Solution Computation Time:"
						+ (System.currentTimeMillis() - start2) / 1000);
				out.println("Number of Solutions With and Without Ghosts:"
						+ solutions.length);
				final Solution[] solWithoutGhost =
					Solution.getSolutionsWithoutGhost(solutions, model);
				out.println("Number of Solutions Without Ghosts:"
						+ solWithoutGhost.length);
				out.println("Number of Solutions With Ghosts:"
						+ (solutions.length - solWithoutGhost.length));
				out.println("\n");
				Solution.print(solutions, out);
				out.println("---------------------------------------------");
			}
		}
		catch (final Exception e) {
			e.printStackTrace();
		}
	}
	private static void callEPISolverForAbstractFactory(
		final IIdiomLevelModel model,
		final String programString,
		final PrintWriter out) {

		ProxyConsole
			.getInstance()
			.debugOutput()
			.println("\n--------------------------------------------------");
		ProxyConsole
			.getInstance()
			.debugOutput()
			.println("Computing domains...");
		//	final Hashtable domains =
		//		CallEPISolver.computeDomains(ABSTRACT_FACTORY_NAME, model);

		ProxyConsole
			.getInstance()
			.debugOutput()
			.println("\n--------------------------------------------------");
		ProxyConsole
			.getInstance()
			.debugOutput()
			.println("Computing solutions...");
		for (int i = 0; i < Approximations.APPROXIMATIONS_NAMES.length; i++) {
			final String approximationName =
				Approximations.APPROXIMATIONS_NAMES[i];
			out.println(approximationName);
			final OptimisedBitVectorSolver solver =
				new OptimisedBitVectorSolver(
					programString,
					AbstractFactory.getInstance().getStrings()[i],
					AbstractFactory.getInstance().getName(),
					Approximations.APPROXIMATIONS[i]);
			final long start = System.currentTimeMillis();
			final Solution[] solutions = solver.computeSolutions();
			out.println("Solution Computation Time:"
					+ (System.currentTimeMillis() - start) / 1000);
			out.println("Number of Solutions With and Without Ghosts:"
					+ solutions.length);
			final Solution[] solWithoutGhost =
				Solution.getSolutionsWithoutGhost(solutions, model);
			out.println("Number of Solutions Without Ghosts:"
					+ solWithoutGhost.length);
			out.println("Number of Solutions With Ghosts:"
					+ (solutions.length - solWithoutGhost.length));
			out.println("\n");
			Solution.print(solutions, out);
			out.println("---------------------------------------------");
		}
	}
	private static void callEPISolverForAdapter(
		final IIdiomLevelModel model,
		final String programString,
		final PrintWriter out) {

		ProxyConsole
			.getInstance()
			.debugOutput()
			.println("\n--------------------------------------------------");
		ProxyConsole
			.getInstance()
			.debugOutput()
			.println("Computing domains...");
		//	final Hashtable domains =
		//		CallEPISolver.computeDomains(ABSTRACT_FACTORY_NAME, model);

		ProxyConsole
			.getInstance()
			.debugOutput()
			.println("\n--------------------------------------------------");
		ProxyConsole
			.getInstance()
			.debugOutput()
			.println("Computing solutions...");
		for (int i = 0; i < Approximations.APPROXIMATIONS_NAMES.length; i++) {
			final String approximationName =
				Approximations.APPROXIMATIONS_NAMES[i];
			out.println(approximationName);
			final OptimisedBitVectorSolver solver =
				new OptimisedBitVectorSolver(
					programString,
					Adapter.getInstance().getStrings()[i],
					Adapter.getInstance().getName(),
					Approximations.APPROXIMATIONS[i]);
			final long start = System.currentTimeMillis();
			final Solution[] solutions = solver.computeSolutions();
			out.println("Solution Computation Time:"
					+ (System.currentTimeMillis() - start) / 1000);
			out.println("Number of Solutions With and Without Ghosts:"
					+ solutions.length);
			final Solution[] solWithoutGhost =
				Solution.getSolutionsWithoutGhost(solutions, model);
			out.println("Number of Solutions Without Ghosts:"
					+ solWithoutGhost.length);
			out.println("Number of Solutions With Ghosts:"
					+ (solutions.length - solWithoutGhost.length));
			out.println("\n");
			Solution.print(solutions, out);
			out.println("---------------------------------------------");
		}
	}
	private static void callEPISolverForCommand(
		final IIdiomLevelModel model,
		final String programString,
		final PrintWriter out) {

		ProxyConsole
			.getInstance()
			.debugOutput()
			.println("\n--------------------------------------------------");
		ProxyConsole
			.getInstance()
			.debugOutput()
			.println("Computing domains...");
		//	final Hashtable domains =
		//		CallEPISolver.computeDomains(ABSTRACT_FACTORY_NAME, model);

		ProxyConsole
			.getInstance()
			.debugOutput()
			.println("\n--------------------------------------------------");
		ProxyConsole
			.getInstance()
			.debugOutput()
			.println("Computing solutions...");
		for (int i = 0; i < Approximations.APPROXIMATIONS_NAMES.length; i++) {
			final String approximationName =
				Approximations.APPROXIMATIONS_NAMES[i];
			out.println(approximationName);
			final OptimisedBitVectorSolver solver =
				new OptimisedBitVectorSolver(
					programString,
					Command.getInstance().getStrings()[i],
					Command.getInstance().getName(),
					Approximations.APPROXIMATIONS[i]);
			final long start = System.currentTimeMillis();
			final Solution[] solutions = solver.computeSolutions();
			out.println("Solution Computation Time:"
					+ (System.currentTimeMillis() - start) / 1000);
			out.println("Number of Solutions With and Without Ghosts:"
					+ solutions.length);
			final Solution[] solWithoutGhost =
				Solution.getSolutionsWithoutGhost(solutions, model);
			out.println("Number of Solutions Without Ghosts:"
					+ solWithoutGhost.length);
			out.println("Number of Solutions With Ghosts:"
					+ (solutions.length - solWithoutGhost.length));
			out.println("\n");
			Solution.print(solutions, out);
			out.println("---------------------------------------------");
		}
	}
	private static void callEPISolverForComposite(
		final IIdiomLevelModel model,
		final String programString,
		final PrintWriter out) {

		ProxyConsole
			.getInstance()
			.debugOutput()
			.println("\n--------------------------------------------------");
		ProxyConsole
			.getInstance()
			.debugOutput()
			.println("Computing domains...");
		//	final Hashtable domains =
		//		CallEPISolver.computeDomains(COMPOSITE_NAME, model);

		ProxyConsole
			.getInstance()
			.debugOutput()
			.println("\n--------------------------------------------------");
		ProxyConsole
			.getInstance()
			.debugOutput()
			.println("Computing solutions...");
		for (int i = 0; i < Approximations.APPROXIMATIONS_NAMES.length; i++) {
			final String approximationName =
				Approximations.APPROXIMATIONS_NAMES[i];
			out.println(approximationName);
			final OptimisedBitVectorSolver solver =
				new OptimisedBitVectorSolver(
					programString,
					Composite.getInstance().getStrings()[i],
					Composite.getInstance().getName(),
					Approximations.APPROXIMATIONS[i]);
			final long start = System.currentTimeMillis();
			final Solution[] solutions = solver.computeSolutions();
			out.println("Solution Computation Time:"
					+ (System.currentTimeMillis() - start) / 1000);
			out.println("Number of Solutions With and Without Ghosts:"
					+ solutions.length);
			final Solution[] solWithoutGhost =
				Solution.getSolutionsWithoutGhost(solutions, model);
			out.println("Number of Solutions Without Ghosts:"
					+ solWithoutGhost.length);
			out.println("Number of Solutions With Ghosts:"
					+ (solutions.length - solWithoutGhost.length));
			out.println("\n");
			Solution.print(solutions, out);
			out.println("---------------------------------------------");
		}
	}
	private static void callEPISolverForDecorator(
		final IIdiomLevelModel model,
		final String programString,
		final PrintWriter out) {

		ProxyConsole
			.getInstance()
			.debugOutput()
			.println("\n--------------------------------------------------");
		ProxyConsole
			.getInstance()
			.debugOutput()
			.println("Computing domains...");
		//	final Hashtable domains =
		//		CallEPISolver.computeDomains(ABSTRACT_FACTORY_NAME, model);

		ProxyConsole
			.getInstance()
			.debugOutput()
			.println("\n--------------------------------------------------");
		ProxyConsole
			.getInstance()
			.debugOutput()
			.println("Computing solutions...");
		for (int i = 0; i < Approximations.APPROXIMATIONS_NAMES.length; i++) {
			final String approximationName =
				Approximations.APPROXIMATIONS_NAMES[i];
			out.println(approximationName);
			final OptimisedBitVectorSolver solver =
				new OptimisedBitVectorSolver(
					programString,
					Decorator.getInstance().getStrings()[i],
					Decorator.getInstance().getName(),
					Approximations.APPROXIMATIONS[i]);
			final long start = System.currentTimeMillis();
			final Solution[] solutions = solver.computeSolutions();
			out.println("Solution Computation Time:"
					+ (System.currentTimeMillis() - start) / 1000);
			out.println("Number of Solutions With and Without Ghosts:"
					+ solutions.length);
			final Solution[] solWithoutGhost =
				Solution.getSolutionsWithoutGhost(solutions, model);
			out.println("Number of Solutions Without Ghosts:"
					+ solWithoutGhost.length);
			out.println("Number of Solutions With Ghosts:"
					+ (solutions.length - solWithoutGhost.length));
			out.println("\n");
			Solution.print(solutions, out);
			out.println("---------------------------------------------");
		}
	}
	private static void callEPISolverForObserver(
		final IIdiomLevelModel model,
		final String programString,
		final PrintWriter out) {

		ProxyConsole
			.getInstance()
			.debugOutput()
			.println("\n--------------------------------------------------");
		ProxyConsole
			.getInstance()
			.debugOutput()
			.println("Computing domains...");
		//	final Hashtable domains =
		//		CallEPISolver.computeDomains(ABSTRACT_FACTORY_NAME, model);

		ProxyConsole
			.getInstance()
			.debugOutput()
			.println("\n--------------------------------------------------");
		ProxyConsole
			.getInstance()
			.debugOutput()
			.println("Computing solutions...");
		for (int i = 0; i < Approximations.APPROXIMATIONS_NAMES.length; i++) {
			final String approximationName =
				Approximations.APPROXIMATIONS_NAMES[i];
			out.println(approximationName);
			final OptimisedBitVectorSolver solver =
				new OptimisedBitVectorSolver(
					programString,
					Observer.getInstance().getStrings()[i],
					Observer.getInstance().getName(),
					Approximations.APPROXIMATIONS[i]);
			final long start = System.currentTimeMillis();
			final Solution[] solutions = solver.computeSolutions();
			out.println("Solution Computation Time:"
					+ (System.currentTimeMillis() - start) / 1000);
			out.println("Number of Solutions With and Without Ghosts:"
					+ solutions.length);
			final Solution[] solWithoutGhost =
				Solution.getSolutionsWithoutGhost(solutions, model);
			out.println("Number of Solutions Without Ghosts:"
					+ solWithoutGhost.length);
			out.println("Number of Solutions With Ghosts:"
					+ (solutions.length - solWithoutGhost.length));
			out.println("\n");
			Solution.print(solutions, out);
			out.println("---------------------------------------------");
		}
	}
	private static void callEPISolverForObserverWithConcreteSubject(
		final IIdiomLevelModel model,
		final String programString,
		final PrintWriter out) {

		ProxyConsole
			.getInstance()
			.debugOutput()
			.println("\n--------------------------------------------------");
		ProxyConsole
			.getInstance()
			.debugOutput()
			.println("Computing domains...");
		//	final Hashtable domains =
		//		CallEPISolver.computeDomains(ABSTRACT_FACTORY_NAME, model);

		ProxyConsole
			.getInstance()
			.debugOutput()
			.println("\n--------------------------------------------------");
		ProxyConsole
			.getInstance()
			.debugOutput()
			.println("Computing solutions...");
		for (int i = 0; i < Approximations.APPROXIMATIONS_NAMES.length; i++) {
			final String approximationName =
				Approximations.APPROXIMATIONS_NAMES[i];
			out.println(approximationName);
			final OptimisedBitVectorSolver solver =
				new OptimisedBitVectorSolver(
					programString,
					ObserverWithConcreteSubject.getInstance().getStrings()[i],
					ObserverWithConcreteSubject.getInstance().getName(),
					Approximations.APPROXIMATIONS[i]);
			final long start = System.currentTimeMillis();
			final Solution[] solutions = solver.computeSolutions();
			out.println("Solution Computation Time:"
					+ (System.currentTimeMillis() - start) / 1000);
			out.println("Number of Solutions With and Without Ghosts:"
					+ solutions.length);
			final Solution[] solWithoutGhost =
				Solution.getSolutionsWithoutGhost(solutions, model);
			out.println("Number of Solutions Without Ghosts:"
					+ solWithoutGhost.length);
			out.println("Number of Solutions With Ghosts:"
					+ (solutions.length - solWithoutGhost.length));
			out.println("\n");
			Solution.print(solutions, out);
			out.println("---------------------------------------------");
		}
	}
	private static void callEPISolverForState(
		final IIdiomLevelModel model,
		final String programString,
		final PrintWriter out) {

		ProxyConsole
			.getInstance()
			.debugOutput()
			.println("\n--------------------------------------------------");
		ProxyConsole
			.getInstance()
			.debugOutput()
			.println("Computing domains...");
		//	final Hashtable domains =
		//		CallEPISolver.computeDomains(ABSTRACT_FACTORY_NAME, model);

		ProxyConsole
			.getInstance()
			.debugOutput()
			.println("\n--------------------------------------------------");
		ProxyConsole
			.getInstance()
			.debugOutput()
			.println("Computing solutions...");
		for (int i = 0; i < Approximations.APPROXIMATIONS_NAMES.length; i++) {
			final String approximationName =
				Approximations.APPROXIMATIONS_NAMES[i];
			out.println(approximationName);
			final OptimisedBitVectorSolver solver =
				new OptimisedBitVectorSolver(
					programString,
					State.getInstance().getStrings()[i],
					State.getInstance().getName(),
					Approximations.APPROXIMATIONS[i]);
			final long start = System.currentTimeMillis();
			final Solution[] solutions = solver.computeSolutions();
			out.println("Solution Computation Time:"
					+ (System.currentTimeMillis() - start) / 1000);
			out.println("Number of Solutions With and Without Ghosts:"
					+ solutions.length);
			final Solution[] solWithoutGhost =
				Solution.getSolutionsWithoutGhost(solutions, model);
			out.println("Number of Solutions Without Ghosts:"
					+ solWithoutGhost.length);
			out.println("Number of Solutions With Ghosts:"
					+ (solutions.length - solWithoutGhost.length));
			out.println("\n");
			Solution.print(solutions, out);
			out.println("---------------------------------------------");
		}
	}
	private static void callEPISolverForUniqueState(
		final IIdiomLevelModel model,
		final String programString,
		final PrintWriter out) {

		ProxyConsole
			.getInstance()
			.debugOutput()
			.println("\n--------------------------------------------------");
		ProxyConsole
			.getInstance()
			.debugOutput()
			.println("Computing domains...");
		//	final Hashtable domains =
		//		CallEPISolver.computeDomains(ABSTRACT_FACTORY_NAME, model);

		ProxyConsole
			.getInstance()
			.debugOutput()
			.println("\n--------------------------------------------------");
		ProxyConsole
			.getInstance()
			.debugOutput()
			.println("Computing solutions...");
		for (int i = 0; i < Approximations.APPROXIMATIONS_NAMES.length; i++) {
			final String approximationName =
				Approximations.APPROXIMATIONS_NAMES[i];
			out.println(approximationName);
			final OptimisedBitVectorSolver solver =
				new OptimisedBitVectorSolver(
					programString,
					UniqueState.getInstance().getStrings()[i],
					UniqueState.getInstance().getName(),
					Approximations.APPROXIMATIONS[i]);
			final long start = System.currentTimeMillis();
			final Solution[] solutions = solver.computeSolutions();
			out.println("Solution Computation Time:"
					+ (System.currentTimeMillis() - start) / 1000);
			out.println("Number of Solutions With and Without Ghosts:"
					+ solutions.length);
			final Solution[] solWithoutGhost =
				Solution.getSolutionsWithoutGhost(solutions, model);
			out.println("Number of Solutions Without Ghosts:"
					+ solWithoutGhost.length);
			out.println("Number of Solutions With Ghosts:"
					+ (solutions.length - solWithoutGhost.length));
			out.println("\n");
			Solution.print(solutions, out);
			out.println("---------------------------------------------");
		}
	}
	private static IIdiomLevelModel createModel(
		final String path,
		final String[] packageNames,
		final PrintWriter out) {

		String[] computePaths = null;
		if (packageNames != null) {
			computePaths = new String[packageNames.length];
			for (int i = 0; i < packageNames.length; i++)
				computePaths[i] = path + packageNames[i].replace('.', '/');
		}
		else {
			computePaths = new String[1];
			computePaths[0] = path;
		}

		final ICodeLevelModel codeLevelModel =
			Factory.getInstance().createCodeLevelModel("");

		out.println("\n--------------------------------------------------");
		out.println("Creating model...");
		try {
			codeLevelModel.create(new CompleteClassFileCreator(
				computePaths,
				true));

			if (packageNames != null) {
				// TODO: This code must be tested!
				final Iterator entities =
					codeLevelModel.getIteratorOnTopLevelEntities();
				while (entities.hasNext()) {
					final IFirstClassEntity firstClassEntity =
						(IFirstClassEntity) entities.next();
					final String entityName = firstClassEntity.getDisplayName();
					final String packageName =
						entityName.substring(0, entityName.lastIndexOf('.'));
					boolean toBeRemoved = true;
					for (int i = 0; i < packageNames.length && toBeRemoved; i++) {
						if (packageName.equals(packageNames[i])) {
							toBeRemoved = false;
						}
					}
					if (toBeRemoved) {
						codeLevelModel.removeConstituentFromID(entityName
							.toCharArray());
					}
				}
			}
		}
		catch (final CreationException e) {
		}

		out.println("\n--------------------------------------------------");
		out.println("Analysing the model...");
		try {
			final IIdiomLevelModel idiomLevelModel =
				(IIdiomLevelModel) new AACRelationshipsAnalysis()
					.invoke(codeLevelModel);

			return idiomLevelModel;
		}
		catch (final UnsupportedSourceModelException e) {
			e.printStackTrace(ProxyConsole.getInstance().errorOutput());
			return null;
		}
	}
	public static void main(final String[] args) {
		JavaEPICaller.callEPISolver1();
		JavaEPICaller.callEPISolver2(
			Integer.parseInt(args[0]),
			Integer.parseInt(args[1]));
	}
	//	private static Hashtable computeDomains(
	//		final String patternName,
	//		final ICodeLevelModel codeLevelModel) {
	//
	//		Hashtable domains = new Hashtable();
	//		Problem result = null;
	//		try {
	//			Class patternProblemClass =
	//				Class.forName("epi.solver.problem." + patternName);
	//			Class[] types = { ICodeLevelModel.class };
	//			java.lang.reflect.Constructor constructor =
	//				patternProblemClass.getConstructor(types);
	//			Object[] params = { codeLevelModel };
	//			result = (Problem) constructor.newInstance(params);
	//			domains = result.getDomains();
	//		}
	//		catch (Exception ex) {
	//			System.err.println(
	//				"Cannot create Problem using name: " + patternName);
	//			ex.printStackTrace();
	//		}
	//		return domains;
	//	}
}
