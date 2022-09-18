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
package ptidej.solver.helper;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;
import java.io.PrintWriter;
import java.io.Writer;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import padl.analysis.UnsupportedSourceModelException;
import padl.analysis.repository.AACRelationshipsAnalysis;
import padl.creator.classfile.CompleteClassFileCreator;
import padl.kernel.ICodeLevelModel;
import padl.kernel.IIdiomLevelModel;
import padl.kernel.exception.CreationException;
import padl.kernel.impl.Factory;
import ptidej.solver.Occurrence;
import ptidej.solver.OccurrenceBuilder;
import ptidej.solver.Problem;
import ptidej.solver.domain.Manager;
import util.io.ProxyConsole;
import util.io.ProxyDisk;
import util.io.ReaderInputStream;

/**
 * @author Yann-Gaël Guéhéneuc
 * @since  2006/11/28
 */
public class DesignMotifIdentificationCallerForPatternsAndTests {
	//	private static final String[] MOTIFS = new String[] { // "AbstractFactory", "Adapter", 
	//		"Builder", "Command", "Composite", "Decorator", "FactoryMethod",
	//				"Observer1", "Observer2", "Prototype", "State",
	//				"TemplateMethod", "Visitor" };

	private static final String[] MOTIFS = new String[] { "Composite",
			"Decorator", "Observer1", "Observer2", "Visitor" };
	public static void main(final String[] args) {

		try {
			final FileOutputStream logStream =
				new FileOutputStream(
					"../Ptidej Solver Data/ConstraintResults.ini");
			//			final MultiChannelOutputStream outStream =
			//				new MultiChannelOutputStream(System.out, logStream);
			//			System.setOut(new PrintStream(outStream));
			//			final MultiChannelOutputStream errStream =
			//				new MultiChannelOutputStream(System.err, logStream);
			//			System.setErr(new PrintStream(errStream));

			final DesignMotifIdentificationCallerForPatternsAndTests ptidejSolver =
				new DesignMotifIdentificationCallerForPatternsAndTests();

			//folder of jars to analyse
			//			final String path = "/Polymtl/Data/PatternsAndTests/checkstyle/inputs/checkstyle_binaries_jars/";
			//			String destinationPath =
			//				"../Ptidej Solver Data/Checkstyle Versions/";
			//folder of jars to analyse
			final String path =
				"/Polymtl/Data/PatternsAndTests/argouml/inputs/argouml_binaries_jars/";
			final String destinationPath =
				"../Ptidej Solver Data/ArgoUml Versions 1/";
			final String[] directories = new File(path).list();

			//find the jars to analyse
			for (int i = 0; i < directories.length; i++) {
				final String directoryName = directories[i];
				final String directory = path + directoryName;
				if (new File(directory).isDirectory()) {
					final String[] filesPaths = new File(directory).list();
					final List filesAbsolutePathsList = new ArrayList();
					for (int j = 0; j < filesPaths.length; j++) {
						final String currentPath =
							new StringBuffer()
								.append(directory)
								.append("/")
								.append(filesPaths[j])
								.toString();
						if (currentPath.endsWith(".jar")) {
							filesAbsolutePathsList.add(currentPath);
						}

					}
					final String[] jarsPaths =
						new String[filesAbsolutePathsList.size()];
					for (int k = 0; k < filesAbsolutePathsList.size(); k++) {
						jarsPaths[k] = (String) filesAbsolutePathsList.get(k);
					}

					ptidejSolver.analyseCodeLevelModelFromJAR(
						jarsPaths,
						directoryName,
						destinationPath);

				}

				logStream.close();
			}
		}
		catch (final FileNotFoundException e) {
			e.printStackTrace();
		}
		catch (final IOException e) {
			e.printStackTrace();
		}
	}

	private void analyseCodeLevelModelFromJAR(
		final String[] someJARFiles,
		final String aName,
		final String destinationPath) {

		System.out.print("Analysing ");
		System.out.print(aName);
		System.out.println("...");

		try {
			final ICodeLevelModel codeLevelModel =
				Factory.getInstance().createCodeLevelModel(aName);
			codeLevelModel.create(new CompleteClassFileCreator(
				someJARFiles,
				true));

			if (codeLevelModel.getNumberOfTopLevelEntities() != 0) {

				this.callPtidejSolver(
					aName,
					codeLevelModel,
					false,
					destinationPath);
			}
			else {
				System.out
					.println("**********************Code model is empty*********************");
			}
		}
		catch (final CreationException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 
	 * @param anAOLIdiomLevelFilePath
	 * @param aName
	 * @param destinationPath
	 */
	//	private void analyseIdiomLevelModel(
	//		final String anAOLIdiomLevelFilePath,
	//		final char[] aName,
	//		final String destinationPath) {
	//
	//		System.out.print("Analysing ");
	//		System.out.print(aName);
	//		System.out.println("...");
	//
	//		final IIdiomLevelModel idiomLevelModel =
	//			Factory.getInstance().createIdiomLevelModel(aName);
	//		final AOLCreator aolCreator =
	//			new AOLCreator(new String[] { anAOLIdiomLevelFilePath });
	//		aolCreator.create(idiomLevelModel, true, true);
	//
	//		final ConsituentCounterVisitor counter = new ConsituentCounterVisitor();
	//		idiomLevelModel.walk(counter);
	//
	//		// this.callPtidejSolver(aName, idiomLevelModel, true);
	//		System.out.print(counter.getResult());
	//		System.out.print(" in ");
	//		System.out.println(aName);
	//		System.out
	//			.println("--------------------------------------------------------------------------------");
	//	}

	/**
	 * 
	 * @param aName
	 * @param aCodeLevelModel
	 * @param isAOL
	 */
	private void callPtidejSolver(
		final String aName,
		final ICodeLevelModel aCodeLevelModel,
		final boolean isAOL,
		final String destinationPath) {

		try {
			System.out.println("Creating idiom-level model...");
			final IIdiomLevelModel idiomLevelModel =
				(IIdiomLevelModel) new AACRelationshipsAnalysis()
					.invoke(aCodeLevelModel);
			System.out.println("Idiom-level model with "
					+ idiomLevelModel.getNumberOfTopLevelEntities()
					+ " entities.");

			this.callPtidejSolver(
				aName,
				idiomLevelModel,
				isAOL,
				destinationPath);
		}
		catch (final UnsupportedSourceModelException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 
	 * @param aName
	 * @param idiomLevelModel
	 * @param isAOL
	 * @param destinationPath
	 */
	private void callPtidejSolver(
		final String aName,
		final IIdiomLevelModel idiomLevelModel,
		final boolean isAOL,
		final String destinationPath) {

		try {
			final long overallStartTime = System.currentTimeMillis();

			int overallNumberOfOccurrences = 0;

			final String resultPath =
				new StringBuffer()
					.append(destinationPath)
					.append(aName)
					.append(File.separator)
					.toString();
			final File resultDirectory = new File(resultPath);
			if (!resultDirectory.exists()) {
				resultDirectory.mkdir();
			}
			for (int i = 0; i < DesignMotifIdentificationCallerForPatternsAndTests.MOTIFS.length; i++) {
				final String motifName =
					DesignMotifIdentificationCallerForPatternsAndTests.MOTIFS[i];

				System.out.println("Identifying occurrences of the "
						+ motifName + " design motif...");

				final Class problemClass;
				if (isAOL) {
					problemClass =
						Class.forName("ptidej.solver.problem.aol." + motifName
								+ "Motif");
				}
				else {
					problemClass =
						Class.forName("ptidej.solver.problem." + motifName
								+ "Motif");
				}
				final Method getProblemMethod =
					problemClass.getMethod(
						"getProblem",
						new Class[] { List.class });
				final Problem problem =
					(Problem) getProblemMethod.invoke(
						null,
						new Object[] { Manager.build(idiomLevelModel) });
				final String path =
					new StringBuffer()
						.append(resultPath)
						.append("ConstraintResults in ")
						.append(aName)
						.append(" for ")
						.append(motifName)
						.append(".ini")
						.toString();
				final long startTime = System.currentTimeMillis();
				final Writer writer =
					ProxyDisk.getInstance().fileTempOutput(path);
				problem.setWriter(new PrintWriter(writer));
				problem.automaticSolve(true);

				final Properties properties = new Properties();
				properties.load(new ReaderInputStream(new FileReader(path)));
				final OccurrenceBuilder solutionBuilder =
					OccurrenceBuilder.getInstance();
				final Occurrence[] solutions =
					solutionBuilder.getCanonicalOccurrences(properties);

				System.out.print(solutions.length);
				System.out.print(" solutions for ");
				System.out.print(motifName);
				System.out.print(" in ");
				System.out.print(aName);
				System.out.print(" in ");
				System.out.print(System.currentTimeMillis() - startTime);
				System.out.println(" ms.");

				overallNumberOfOccurrences += solutions.length;
			}

			final long overallEndTime = System.currentTimeMillis();
			System.out.print(overallNumberOfOccurrences);
			System.out.print(" occurrences of ");
			System.out
				.print(DesignMotifIdentificationCallerForPatternsAndTests.MOTIFS.length);
			System.out.print(" design motifs computed in ");
			System.out.print(overallEndTime - overallStartTime);
			System.out.println(" ms.");
			System.out
				.print("Done computing occurrences of design motifs for ");
			System.out.println(aName);
		}
		catch (final FileNotFoundException e) {
			e.printStackTrace(ProxyConsole.getInstance().errorOutput());
		}
		catch (final NoSuchMethodException e) {
			e.printStackTrace(ProxyConsole.getInstance().errorOutput());
		}
		catch (final IllegalAccessException e) {
			e.printStackTrace(ProxyConsole.getInstance().errorOutput());
		}
		catch (final InvocationTargetException e) {
			e.printStackTrace(ProxyConsole.getInstance().errorOutput());
		}
		catch (final IOException e) {
			e.printStackTrace(ProxyConsole.getInstance().errorOutput());
		}
		catch (final ClassNotFoundException e) {
			e.printStackTrace(ProxyConsole.getInstance().errorOutput());
		}
	}
	public int returnTotalLOC(final String aMetFileName) {
		int totalLOC = 0;
		try {
			final LineNumberReader reader =
				new LineNumberReader(new FileReader(aMetFileName));
			String line;
			while ((line = reader.readLine()) != null) {
				final int index = line.indexOf("LOC ");
				if (index > -1) {
					totalLOC += Integer.parseInt(line.substring(index + 4));
				}
			}
			reader.close();
		}
		catch (final FileNotFoundException e) {
			e.printStackTrace();
		}
		catch (final IOException e) {
			e.printStackTrace();
		}
		return totalLOC;
	}
}
