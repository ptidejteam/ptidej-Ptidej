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
package ptidej.solver.java.helper;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.Writer;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Properties;
import padl.analysis.UnsupportedSourceModelException;
import padl.analysis.repository.AACRelationshipsAnalysis;
import padl.creator.classfile.CompleteClassFileCreator;
import padl.generator.helper.ModelGenerator;
import padl.kernel.IAbstractLevelModel;
import padl.kernel.ICodeLevelModel;
import padl.kernel.IIdiomLevelModel;
import padl.kernel.exception.CreationException;
import padl.kernel.impl.Factory;
import padl.visitor.IWalker;
import ptidej.solver.Occurrence;
import ptidej.solver.OccurrenceBuilder;
import ptidej.solver.java.Problem;
import ptidej.solver.java.domain.Generator;
import ptidej.solver.java.domain.Manager;
import util.io.MultiChannelOutputStream;
import util.io.ProxyConsole;
import util.io.ProxyDisk;
import util.io.ReaderInputStream;

/**
 * @author Yann-Gaël Guéhéneuc 
 * @since 2006/11/28
 */
public class DesignMotifIdentificationCaller {
	public static final String[] MOTIFS = new String[] { // "AbstractFactory",
				// "Adapter",
				// "GoodInheritance",
				"Adapter", "Builder", "Decorator", "State", "Observer2"};

	public static void analyseCodeLevelModel(
		final String[] someMotifs,
		final String aName,
		final IAbstractLevelModel anAbstractLevelModel,
		final boolean isAOL,
		final IWalker aWalker,
		final String anOutputFolder) {

		try {
			final long overallStartTime = System.currentTimeMillis();
			int overallNumberOfOccurrences = 0;

			for (int i = 0; i < someMotifs.length; i++) {
				final String motifName =
					DesignMotifIdentificationCaller.MOTIFS[i];
				System.out.println("Identifying occurrences of the "
						+ motifName + " design motif...");

				final Class problemClass;
				if (isAOL) {
					// TODO Is this block still needed?
					problemClass =
						Class.forName("ptidej.solver.java.problem.aol." + motifName
								+ "Motif");
				}
				else {
					problemClass =
						Class.forName("ptidej.solver.java.problem." + motifName
								+ "Motif");
				}
				final Method getProblemMethod =
					problemClass.getMethod(
						"getProblem",
						new Class[] { List.class });
				final Problem problem =
					(Problem) getProblemMethod.invoke(
						null,
						new Object[] { Manager.build(
							anAbstractLevelModel,
							aWalker) });

				final String path = anOutputFolder + "ConstraintResults in "
				//final String path = "../Ptidej Solver Data/ConstraintResults in "
						+ aName + " for " + motifName + ".ini";
				final long startTime = System.currentTimeMillis();

				final Writer writer =
					ProxyDisk.getInstance().fileTempOutput(path);
				problem.setWriter(new PrintWriter(writer));
				problem.automaticSolve(true);

				final Properties properties = new Properties();
				properties.load(new ReaderInputStream(ProxyDisk
					.getInstance()
					.fileTempInput(path)));
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
			System.out.print(DesignMotifIdentificationCaller.MOTIFS.length);
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
			System.out.println(e.getCause().getMessage());
			e.printStackTrace(ProxyConsole.getInstance().errorOutput());
		}
		catch (final IOException e) {
			e.printStackTrace(ProxyConsole.getInstance().errorOutput());
		}
		catch (final ClassNotFoundException e) {
			e.printStackTrace(ProxyConsole.getInstance().errorOutput());
		}
	}

	public static void main(final String[] args) {
		// if (args.length != 1) {
		// System.out
		// .print("CallDesignMotifIdentification <ECLIPSE/ARGOUML VERSION>");
		// System.exit(-1);
		// }
		try {
			final FileOutputStream logStream =
				new FileOutputStream(
					"ConstraintResults.log");
			final MultiChannelOutputStream outStream =
				new MultiChannelOutputStream(ProxyConsole
					.getInstance()
					.normalOutput(), logStream);
			System.setOut(new PrintStream(outStream));
			final MultiChannelOutputStream errStream =
				new MultiChannelOutputStream(ProxyConsole
					.getInstance()
					.normalOutput(), logStream);
			System.setErr(new PrintStream(errStream));

			// CallDesignMotifIdentification.MOTIFS[0] = args[1];

			// ptidejSolver.analyseCodeLevelModelFromJava(
			// "../../P-MARt Workspace/SIP Communicator v1.0-draft/bin/",
			// "SIP Communicator v1.0-draft");
			// ptidejSolver.analyseCodeLevelModelFromJava(
			// "../../P-MARt Workspace/Pooka v2.0/bin/",
			// "Pooka v2.0");

			// ptidejSolver
			// .analyseCodeLevelModelFromJARs(
			// "/repository/software/Versions/Eclipse/eclipse-SDK-2.0-win32/plugins/",
			// "Eclipse v2.0");
			// ptidejSolver
			// .analyseCodeLevelModelFromJARs(
			// "/repository/software/Versions/Eclipse/eclipse-SDK-2.1.1-win32/plugins/",
			// "Eclipse v2.1.1");

			//	final String root = Files.getRunningRootPath() + "../../P-MARt Workspace/";
			//	final String[] list = new File(root).list();
			//	for (int i = 0; i < list.length; i++) {
			//		final String directory = list[i];
			//		// if (directory.startsWith("Mylyn v3")
			//		// && !directory.equals("Mylyn v3.0.0 20080619-1900-e3.3")) {
			//		if (directory.toLowerCase().startsWith("xerces v")) {
			//			ptidejSolver.analyseCodeLevelModelFromJava(root + directory
			//					+ "/bin/", directory);
			//		}
			//	}
			final IIdiomLevelModel idiomLevelModel =
				ModelGenerator
					.generateModelFromJAR("C:\\AI\\datasets\\DP\\java_projects\\QuickUML-2001.jar");
			System.out.print("Number of top-level entities: ");
			System.out.println(idiomLevelModel.getNumberOfTopLevelEntities());
			DesignMotifIdentificationCaller.analyseCodeLevelModel(
				DesignMotifIdentificationCaller.MOTIFS,
				"QuickUML-2001",
				idiomLevelModel,
				false,
				new Generator(),
				"QuickUML-2001/");

			logStream.close();
		}
		catch (final FileNotFoundException e) {
			e.printStackTrace();
		}
		catch (final IOException e) {
			e.printStackTrace();
		}
	}
	public void analyseCodeLevelModelFromJava(
		final String aClassPath,
		final String aName) {

		System.out.print("Analysing ");
		System.out.print(aName);
		System.out.println("...");

		try {
			final ICodeLevelModel codeLevelModel =
				Factory.getInstance().createCodeLevelModel(aName);
			codeLevelModel.create(new CompleteClassFileCreator(
				new String[] { aClassPath },
				true));

			this
				.callPtidejSolver(aName, codeLevelModel, false, new Generator());
		}
		catch (final CreationException e) {
			e.printStackTrace();
		}
	}
	private void callPtidejSolver(
		final String aName,
		final ICodeLevelModel aCodeLevelModel,
		final boolean isAOL,
		final IWalker aWalker) {

		try {
			System.out.println("Creating idiom-level model...");
			final IIdiomLevelModel idiomLevelModel =
				(IIdiomLevelModel) new AACRelationshipsAnalysis()
					.invoke(aCodeLevelModel);
			System.out.println("Idiom-level model with "
					+ idiomLevelModel.getNumberOfTopLevelEntities()
					+ " entities.");

			DesignMotifIdentificationCaller.analyseCodeLevelModel(
				DesignMotifIdentificationCaller.MOTIFS,
				aName,
				idiomLevelModel,
				isAOL,
				aWalker,
				"");
		}
		catch (final UnsupportedSourceModelException e) {
			e.printStackTrace();
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
