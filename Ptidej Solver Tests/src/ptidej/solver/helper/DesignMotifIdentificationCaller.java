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
import ptidej.solver.Problem;
import ptidej.solver.domain.Generator;
import ptidej.solver.domain.Manager;
import util.io.MultiChannelOutputStream;
import util.io.ProxyConsole;
import util.io.ProxyDisk;
import util.io.ReaderInputStream;

/**
 * @author Yann-Gaï¿½l Guï¿½hï¿½neuc
 * @since 2006/11/28
 */
public class DesignMotifIdentificationCaller {
	public static final String[] MOTIFS = new String[] { // "AbstractFactory",
				// "Adapter",
				// "GoodInheritance",
				"Builder", "Command", "Composite", "Decorator",
				"FactoryMethod", "Observer1", "Observer2", "Prototype",
				"State", "TemplateMethod", "Visitor" };

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

	//	private void analyseCodeLevelModelFromAOL(
	//		final String anAOLCodeLevelFilePath,
	//		final String aName) {
	//
	//		System.out.print("Analysing ");
	//		System.out.print(aName);
	//		System.out.println("...");
	//
	//		final String name =
	//			anAOLCodeLevelFilePath.substring(
	//				anAOLCodeLevelFilePath.lastIndexOf('/') + 1,
	//				anAOLCodeLevelFilePath.indexOf('-'));
	//		final String cldFileName =
	//			anAOLCodeLevelFilePath.replaceAll("concat_", "").replaceAll(
	//				".aol",
	//				".cld");
	//		final String metFileName =
	//			anAOLCodeLevelFilePath.replaceAll("concat_", "").replaceAll(
	//				".aol",
	//				".met");
	//
	//		ICodeLevelModel codeLevelModel =
	//			Factory.getInstance().createCodeLevelModel(name);
	//		new AOLCreator(new String[] { anAOLCodeLevelFilePath }).create(
	//			codeLevelModel,
	//			true);
	//
	//		try {
	//			final MethodInvocationAnalyser methodInvocationAdder =
	//				new MethodInvocationAnalyser();
	//			methodInvocationAdder.setCLDFile(cldFileName);
	//			codeLevelModel =
	//				(ICodeLevelModel) methodInvocationAdder.invoke(codeLevelModel);
	//
	//			final IIdiomLevelModel idiomLevelModel =
	//				(IIdiomLevelModel) new AACRelationshipsAnalysis(false)
	//					.invoke(codeLevelModel);
	//
	//			final ConsituentCounterVisitor counter =
	//				new ConsituentCounterVisitor();
	//			idiomLevelModel.walk(counter);
	//
	//			// this.callPtidejSolver(aName, idiomLevelModel, true);
	//			System.out.print(this.returnTotalLOC(metFileName));
	//			System.out.print(" & ");
	//			System.out.print(counter.getResult());
	//			System.out.print(" % ");
	//			System.out.println(aName);
	//			System.out
	//				.println("--------------------------------------------------------------------------------");
	//		}
	//		catch (final UnsupportedSourceModelException e) {
	//			e.printStackTrace(Output.getInstance().errorOutput());
	//		}
	//	}

	//	private void analyseCodeLevelModelFromJAR(
	//		final String[] someJARFiles,
	//		final String aName) {
	//
	//		System.out.print("Analysing ");
	//		System.out.print(aName);
	//		System.out.println("...");
	//
	//		try {
	//			final ICodeLevelModel codeLevelModel =
	//				Factory.getInstance().createCodeLevelModel(aName);
	//			codeLevelModel.create(new CompleteClassFileCreator(
	//				ConstituentRepository.getInstance(ClassFileRepository
	//					.getInstance(ConstituentRepository.class)),
	//				someJARFiles,
	//				true));
	//
	//			this
	//				.callPtidejSolver(aName, codeLevelModel, false, new Generator());
	//		}
	//		catch (final CreationException e) {
	//			e.printStackTrace();
	//		}
	//	}

	//	private void analyseCodeLevelModelFromJARs(
	//		final String aPluginPath,
	//		final String aName) {
	//
	//		final IIdiomLevelModel idiomLevelModel =
	//			ModelGenerator.generateModelFromJARs(aPluginPath);
	//		this.callPtidejSolver(aName, idiomLevelModel, false, new Generator());
	//	}
	public static void analyseCodeLevelModel(
		final String[] someMotifs,
		final String aName,
		final IAbstractLevelModel anAbstractLevelModel,
		final String anOutputFolder) {

		DesignMotifIdentificationCaller.analyseCodeLevelModel(
			someMotifs,
			aName,
			anAbstractLevelModel,
			false,
			new Generator(),
			anOutputFolder);
	}
	//	private void analyseIdiomLevelModel(
	//		final String anAOLIdiomLevelFilePath,
	//		final char[] aName) {
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

	public static void main(final String[] args) {
		// if (args.length != 1) {
		// System.out
		// .print("CallDesignMotifIdentification <ECLIPSE/ARGOUML VERSION>");
		// System.exit(-1);
		// }
		try {
			final FileOutputStream logStream =
				new FileOutputStream(
					"../Ptidej Solver Data/ConstraintResults.log");
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

			//	final String root = "../../P-MARt Workspace/";
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
					.generateModelFromJAR("C:/Data/Java Programs/CKJM/ckjm_ext v2.1.jar");
			System.out.print("Number of top-level entities: ");
			System.out.println(idiomLevelModel.getNumberOfTopLevelEntities());
			DesignMotifIdentificationCaller.analyseCodeLevelModel(
				DesignMotifIdentificationCaller.MOTIFS,
				"CKJM",
				idiomLevelModel,
				false,
				new Generator(),
				"CKJM");

			// ptidejSolver.analyseCodeLevelModelFromJava(
			// "../../P-MARt Workspace/Dresden OCL v1.1/bin/",
			// "Dresden OCL v1.1");
			// ptidejSolver.analyseCodeLevelModelFromJava(
			// "../../P-MARt Workspace/Juzzle/bin/",
			// "Juzzle");
			// ptidejSolver.analyseCodeLevelModelFromJava(
			// "../../P-MARt Workspace/JHotDraw v5.1/bin/",
			// "JHotDraw v5.1");
			// ptidejSolver.analyseCodeLevelModelFromJava(
			// "../../P-MARt Workspace/QuickUML 2001/bin/",
			// "QuickUML 2001");

			// if (args[0].equals("0.10")) {
			// ptidejSolver.analyseCodeLevelModelFromJava(
			// "../../P-MARt Workspace/ArgoUML v0.10/",
			// "ArgoUML v0.10");
			// }
			// else if (args[0].equals("0.12")) {
			// ptidejSolver.analyseCodeLevelModelFromJava(
			// "../../P-MARt Workspace/ArgoUML v0.12/",
			// "ArgoUML v0.12");
			// }
			// else if (args[0].equals("0.14")) {
			// ptidejSolver.analyseCodeLevelModelFromJava(
			// "../../P-MARt Workspace/ArgoUML v0.14/",
			// "ArgoUML v0.14");
			// }
			// else if (args[0].equals("0.15.6")) {
			// ptidejSolver.analyseCodeLevelModelFromJava(
			// "../../P-MARt Workspace/ArgoUML v0.15.6/",
			// "ArgoUML v0.15.6");
			// }
			// else if (args[0].equals("0.16.1")) {
			// ptidejSolver.analyseCodeLevelModelFromJava(
			// "../../P-MARt Workspace/ArgoUML v0.16.1/",
			// "ArgoUML v0.16.1");
			// }
			// else if (args[0].equals("0.17.5")) {
			// ptidejSolver.analyseCodeLevelModelFromJava(
			// "../../P-MARt Workspace/ArgoUML v0.17.5/",
			// "ArgoUML v0.17.5");
			// }
			// else if (args[0].equals("0.18.1")) {
			// ptidejSolver.analyseCodeLevelModelFromJava(
			// "../../P-MARt Workspace/ArgoUML v0.18.1/",
			// "ArgoUML v0.18.1");
			// }
			// else if (args[0].equals("0.19.8")) {
			// ptidejSolver.analyseCodeLevelModelFromJava(
			// "../../P-MARt Workspace/ArgoUML v0.19.8/",
			// "ArgoUML v0.19.8");
			// }
			// else if (args[0].equals("0.20")) {
			// ptidejSolver.analyseCodeLevelModelFromJava(
			// "../../P-MARt Workspace/ArgoUML v0.20/",
			// "ArgoUML v0.20");
			// }
			// else if (args[0].equals("1.0")) {
			// ptidejSolver.analyseCodeLevelModelFromJAR(
			// new String[] {
			// "../../P-MARt Workspace/Eclipse JDT v1.0/libs/plugins/org.eclipse.jdt.core/jdtcore.jar",
			// "../../P-MARt Workspace/Eclipse JDT v1.0/libs/plugins/org.eclipse.jdt.debug/jdi.jar",
			// "../../P-MARt Workspace/Eclipse JDT v1.0/libs/plugins/org.eclipse.jdt.debug/jdimodel.jar",
			// "../../P-MARt Workspace/Eclipse JDT v1.0/libs/plugins/org.eclipse.jdt.launching/launching.jar",
			// "../../P-MARt Workspace/Eclipse JDT v1.0/libs/plugins/org.eclipse.jdt.ui/jdt.jar",
			// "../../P-MARt Workspace/Eclipse JDT v1.0/libs/plugins/org.eclipse.jdt.ui/snippetsupport.jar",
			// "../../P-MARt Workspace/Eclipse JDT v1.0/libs/plugins/org.eclipse.jdt.ui.vcm/jdtvcm.jar"
			// },
			// "Eclipse JDT v1.0");
			// }
			// else if (args[0].equals("2.0")) {
			// ptidejSolver.analyseCodeLevelModelFromJAR(
			// new String[] {
			// "../../P-MARt Workspace/Eclipse JDT v2.0/libs/plugins/org.eclipse.jdt.core_2.0.0/jdtCompilerAdapter.jar",
			// "../../P-MARt Workspace/Eclipse JDT v2.0/libs/plugins/org.eclipse.jdt.core_2.0.0/jdtcore.jar"
			// },
			// "Eclipse JDT v2.0 (core)");
			// ptidejSolver.analyseCodeLevelModelFromJAR(
			// new String[] {
			// "../../P-MARt Workspace/Eclipse JDT v2.0/libs/plugins/org.eclipse.jdt.debug.ui_2.0.0/jdiui.jar",
			// "../../P-MARt Workspace/Eclipse JDT v2.0/libs/plugins/org.eclipse.jdt.debug.ui_2.0.0/snippetsupport.jar"
			// },
			// "Eclipse JDT v2.0 (debug ui)");
			// ptidejSolver.analyseCodeLevelModelFromJAR(
			// new String[] {
			// "../../P-MARt Workspace/Eclipse JDT v2.0/libs/plugins/org.eclipse.jdt.debug_2.0.0/jdi.jar",
			// "../../P-MARt Workspace/Eclipse JDT v2.0/libs/plugins/org.eclipse.jdt.debug_2.0.0/jdimodel.jar"
			// },
			// "Eclipse JDT v2.0 (debug)");
			// ptidejSolver.analyseCodeLevelModelFromJAR(
			// new String[] {
			// "../../P-MARt Workspace/Eclipse JDT v2.0/libs/plugins/org.eclipse.jdt.junit_2.0.0/junitsupport.jar",
			// "../../P-MARt Workspace/Eclipse JDT v2.0/libs/plugins/org.eclipse.jdt.launching_2.0.0/launching.jar"
			// },
			// "Eclipse JDT v2.0 (misc)");
			// ptidejSolver.analyseCodeLevelModelFromJAR(
			// new String[] {
			// "../../P-MARt Workspace/Eclipse JDT v2.0/libs/plugins/org.eclipse.jdt.ui_2.0.0/jdt.jar"
			// },
			// "Eclipse JDT v2.0 (ui)");
			// }
			// else if (args[0].equals("2.1")) {
			// ptidejSolver.analyseCodeLevelModelFromJAR(
			// new String[] {
			// "../../P-MARt Workspace/Eclipse JDT v2.1/libs/plugins/org.eclipse.jdt.core_2.1.0/jdtCompilerAdapter.jar",
			// "../../P-MARt Workspace/Eclipse JDT v2.1/libs/plugins/org.eclipse.jdt.core_2.1.0/jdtcore.jar"
			// },
			// "Eclipse JDT v2.1 (core)");
			// ptidejSolver.analyseCodeLevelModelFromJAR(
			// new String[] {
			// "../../P-MARt Workspace/Eclipse JDT v2.1/libs/plugins/org.eclipse.jdt.debug.ui_2.1.0/jdiui.jar",
			// "../../P-MARt Workspace/Eclipse JDT v2.1/libs/plugins/org.eclipse.jdt.debug.ui_2.1.0/snippetsupport.jar"
			// },
			// "Eclipse JDT v2.1 (debug ui)");
			// ptidejSolver.analyseCodeLevelModelFromJAR(
			// new String[] {
			// "../../P-MARt Workspace/Eclipse JDT v2.1/libs/plugins/org.eclipse.jdt.debug_2.1.0/jdi.jar",
			// "../../P-MARt Workspace/Eclipse JDT v2.1/libs/plugins/org.eclipse.jdt.debug_2.1.0/jdimodel.jar"
			// },
			// "Eclipse JDT v2.1 (debug)");
			// ptidejSolver.analyseCodeLevelModelFromJAR(
			// new String[] {
			// "../../P-MARt Workspace/Eclipse JDT v2.1/libs/plugins/org.eclipse.jdt.junit_2.1.0/junitsupport.jar",
			// "../../P-MARt Workspace/Eclipse JDT v2.1/libs/plugins/org.eclipse.jdt.launching_2.1.0/launching.jar"
			// },
			// "Eclipse JDT v2.1 (misc)");
			// ptidejSolver.analyseCodeLevelModelFromJAR(
			// new String[] {
			// "../../P-MARt Workspace/Eclipse JDT v2.1/libs/plugins/org.eclipse.jdt.ui_2.1.0/jdt.jar"
			// },
			// "Eclipse JDT v2.1 (ui)");
			// }
			// else if (args[0].equals("2.1.2")) {
			// ptidejSolver.analyseCodeLevelModelFromJAR(
			// new String[] {
			// "../../P-MARt Workspace/Eclipse JDT v2.1.2/libs/plugins/org.eclipse.jdt.core_2.1.2/jdtCompilerAdapter.jar",
			// "../../P-MARt Workspace/Eclipse JDT v2.1.2/libs/plugins/org.eclipse.jdt.core_2.1.2/jdtcore.jar"
			// },
			// "Eclipse JDT v2.1.2 (core)");
			// ptidejSolver.analyseCodeLevelModelFromJAR(
			// new String[] {
			// "../../P-MARt Workspace/Eclipse JDT v2.1.2/libs/plugins/org.eclipse.jdt.debug.ui_2.1.1/jdiui.jar",
			// "../../P-MARt Workspace/Eclipse JDT v2.1.2/libs/plugins/org.eclipse.jdt.debug.ui_2.1.1/snippetsupport.jar"
			// },
			// "Eclipse JDT v2.1.2 (debug ui)");
			// ptidejSolver.analyseCodeLevelModelFromJAR(
			// new String[] {
			// "../../P-MARt Workspace/Eclipse JDT v2.1.2/libs/plugins/org.eclipse.jdt.debug_2.1.2/jdi.jar",
			// "../../P-MARt Workspace/Eclipse JDT v2.1.2/libs/plugins/org.eclipse.jdt.debug_2.1.2/jdimodel.jar"
			// },
			// "Eclipse JDT v2.1.2 (debug)");
			// ptidejSolver.analyseCodeLevelModelFromJAR(
			// new String[] {
			// "../../P-MARt Workspace/Eclipse JDT v2.1.2/libs/plugins/org.eclipse.jdt.junit_2.1.1/junitsupport.jar",
			// "../../P-MARt Workspace/Eclipse JDT v2.1.2/libs/plugins/org.eclipse.jdt.launching_2.1.1/launching.jar"
			// },
			// "Eclipse JDT v2.1.2 (misc)");
			// ptidejSolver.analyseCodeLevelModelFromJAR(
			// new String[] {
			// "../../P-MARt Workspace/Eclipse JDT v2.1.2/libs/plugins/org.eclipse.jdt.ui_2.1.2/jdt.jar"
			// },
			// "Eclipse JDT v2.1.2 (ui)");
			// }
			// else if (args[0].equals("2.1.3")) {
			// ptidejSolver.analyseCodeLevelModelFromJAR(
			// new String[] {
			// "../../P-MARt Workspace/Eclipse JDT v2.1.3/libs/plugins/org.eclipse.jdt.core_2.1.3/jdtCompilerAdapter.jar",
			// "../../P-MARt Workspace/Eclipse JDT v2.1.3/libs/plugins/org.eclipse.jdt.core_2.1.3/jdtcore.jar"
			// },
			// "Eclipse JDT v2.1.3 (core)");
			// ptidejSolver.analyseCodeLevelModelFromJAR(
			// new String[] {
			// "../../P-MARt Workspace/Eclipse JDT v2.1.3/libs/plugins/org.eclipse.jdt.debug.ui_2.1.1/jdiui.jar",
			// "../../P-MARt Workspace/Eclipse JDT v2.1.3/libs/plugins/org.eclipse.jdt.debug.ui_2.1.1/snippetsupport.jar"
			// },
			// "Eclipse JDT v2.1.3 (debug ui)");
			// ptidejSolver.analyseCodeLevelModelFromJAR(
			// new String[] {
			// "../../P-MARt Workspace/Eclipse JDT v2.1.3/libs/plugins/org.eclipse.jdt.debug_2.1.2/jdi.jar",
			// "../../P-MARt Workspace/Eclipse JDT v2.1.3/libs/plugins/org.eclipse.jdt.debug_2.1.2/jdimodel.jar"
			// },
			// "Eclipse JDT v2.1.3 (debug)");
			// ptidejSolver.analyseCodeLevelModelFromJAR(
			// new String[] {
			// "../../P-MARt Workspace/Eclipse JDT v2.1.3/libs/plugins/org.eclipse.jdt.junit_2.1.1/junitsupport.jar",
			// "../../P-MARt Workspace/Eclipse JDT v2.1.3/libs/plugins/org.eclipse.jdt.launching_2.1.1/launching.jar"
			// },
			// "Eclipse JDT v2.1.3 (misc)");
			// ptidejSolver.analyseCodeLevelModelFromJAR(
			// new String[] {
			// "../../P-MARt Workspace/Eclipse JDT v2.1.3/libs/plugins/org.eclipse.jdt.ui_2.1.3/jdt.jar"
			// },
			// "Eclipse JDT v2.1.3 (ui)");
			// }
			// else if (args[0].equals("3.0")) {
			// ptidejSolver.analyseCodeLevelModelFromJAR(
			// new String[] {
			// "../../P-MARt Workspace/Eclipse JDT v3.0/libs/plugins/org.eclipse.ant.ui_3.0.0/antui.jar",
			// "../../P-MARt Workspace/Eclipse JDT v3.0/libs/plugins/org.eclipse.jdt.junit.runtime_3.0.0/junitruntime.jar",
			// "../../P-MARt Workspace/Eclipse JDT v3.0/libs/plugins/org.eclipse.jdt.launching_3.0.0/launching.jar"
			// },
			// "Eclipse JDT v3.0 (misc)");
			// ptidejSolver.analyseCodeLevelModelFromJAR(
			// new String[] {
			// "../../P-MARt Workspace/Eclipse JDT v3.0/libs/plugins/org.eclipse.jdt.core_3.0.0/jdtCompilerAdapter.jar",
			// "../../P-MARt Workspace/Eclipse JDT v3.0/libs/plugins/org.eclipse.jdt.core_3.0.0/jdtcore.jar"
			// },
			// "Eclipse JDT v3.0 (core)");
			// ptidejSolver.analyseCodeLevelModelFromJAR(
			// new String[] {
			// "../../P-MARt Workspace/Eclipse JDT v3.0/libs/plugins/org.eclipse.jdt.debug.ui_3.0.0/jdiui.jar",
			// "../../P-MARt Workspace/Eclipse JDT v3.0/libs/plugins/org.eclipse.jdt.debug.ui_3.0.0/snippetsupport.jar"
			// },
			// "Eclipse JDT v3.0 (debug ui)");
			// ptidejSolver.analyseCodeLevelModelFromJAR(
			// new String[] {
			// "../../P-MARt Workspace/Eclipse JDT v3.0/libs/plugins/org.eclipse.jdt.debug_3.0.0/jdi.jar",
			// "../../P-MARt Workspace/Eclipse JDT v3.0/libs/plugins/org.eclipse.jdt.debug_3.0.0/jdimodel.jar"
			// },
			// "Eclipse JDT v3.0 (debug)");
			// ptidejSolver.analyseCodeLevelModelFromJAR(
			// new String[] {
			// "../../P-MARt Workspace/Eclipse JDT v3.0/libs/plugins/org.eclipse.jdt.ui_3.0.0/jdt.jar"
			// },
			// "Eclipse JDT v3.0 (ui)");
			// ptidejSolver.analyseCodeLevelModelFromJAR(
			// new String[] {
			// "../../P-MARt Workspace/Eclipse JDT v3.0/libs/plugins/org.eclipse.ltk.core.refactoring_3.0.0/refcore.jar",
			// "../../P-MARt Workspace/Eclipse JDT v3.0/libs/plugins/org.eclipse.ltk.ui.refactoring_3.0.0/refui.jar"
			// },
			// "Eclipse JDT v3.0 (refactoring)");
			// }

			// // +------------------+
			// // | Eclipse JDT v3.0 |
			// // +------------------+
			// ptidejSolver
			// .analyseCodeLevelModelFromJAR(
			// new String[] {
			// "../../P-MARt Workspace/Eclipse JDT v3.0/libs/plugins/org.eclipse.ant.ui_3.0.0/antui.jar",
			// "../../P-MARt Workspace/Eclipse JDT v3.0/libs/plugins/org.eclipse.jdt.junit.runtime_3.0.0/junitruntime.jar",
			// "../../P-MARt Workspace/Eclipse JDT v3.0/libs/plugins/org.eclipse.jdt.launching_3.0.0/launching.jar"
			// },
			// "Eclipse JDT v3.0 (misc)");
			// ptidejSolver
			// .analyseCodeLevelModelFromJAR(
			// new String[] {
			// "../../P-MARt Workspace/Eclipse JDT v3.0/libs/plugins/org.eclipse.jdt.core_3.0.0/jdtCompilerAdapter.jar",
			// "../../P-MARt Workspace/Eclipse JDT v3.0/libs/plugins/org.eclipse.jdt.core_3.0.0/jdtcore.jar"
			// },
			// "Eclipse JDT v3.0 (core)");
			// ptidejSolver
			// .analyseCodeLevelModelFromJAR(
			// new String[] {
			// "../../P-MARt Workspace/Eclipse JDT v3.0/libs/plugins/org.eclipse.jdt.debug.ui_3.0.0/jdiui.jar",
			// "../../P-MARt Workspace/Eclipse JDT v3.0/libs/plugins/org.eclipse.jdt.debug.ui_3.0.0/snippetsupport.jar"
			// },
			// "Eclipse JDT v3.0 (debug ui)");
			// ptidejSolver
			// .analyseCodeLevelModelFromJAR(
			// new String[] {
			// "../../P-MARt Workspace/Eclipse JDT v3.0/libs/plugins/org.eclipse.jdt.debug_3.0.0/jdi.jar",
			// "../../P-MARt Workspace/Eclipse JDT v3.0/libs/plugins/org.eclipse.jdt.debug_3.0.0/jdimodel.jar"
			// },
			// "Eclipse JDT v3.0 (debug)");
			// ptidejSolver
			// .analyseCodeLevelModelFromJAR(
			// new String[] {
			// "../../P-MARt Workspace/Eclipse JDT v3.0/libs/plugins/org.eclipse.jdt.ui_3.0.0/jdt.jar"
			// },
			// "Eclipse JDT v3.0 (ui)");
			// ptidejSolver
			// .analyseCodeLevelModelFromJAR(
			// new String[] {
			// "../../P-MARt Workspace/Eclipse JDT v3.0/libs/plugins/org.eclipse.ltk.core.refactoring_3.0.0/refcore.jar",
			// "../../P-MARt Workspace/Eclipse JDT v3.0/libs/plugins/org.eclipse.ltk.ui.refactoring_3.0.0/refui.jar"
			// },
			// "Eclipse JDT v3.0 (refactoring)");

			// +------------------+
			// | Eclipse JDT v3.1 |
			// +------------------+
			// ptidejSolver
			// .analyseCodeLevelModelFromJAR(
			// new String[] {
			// "../../P-MARt Workspace/Eclipse JDT v3.1/libs/plugins/org.eclipse.jdt_3.1.0.jar",
			// "../../P-MARt Workspace/Eclipse JDT v3.1/libs/plugins/org.eclipse.jdt.core_3.1.2.jar"
			// },
			// "Eclipse JDT v3.1 (core)");
			// ptidejSolver
			// .analyseCodeLevelModelFromJAR(
			// new String[] {
			// "../../P-MARt Workspace/Eclipse JDT v3.1/libs/plugins/org.eclipse.jdt.debug.ui_3.1.2.jar",
			// "../../P-MARt Workspace/Eclipse JDT v3.1/libs/plugins/org.eclipse.jdt.launching_3.1.0.jar",
			// "../../P-MARt Workspace/Eclipse JDT v3.1/libs/plugins/org.eclipse.jdt.ui_3.1.2.jar"
			// },
			// "Eclipse JDT v3.1 (debug ui)");

			// +------------------+
			// | Eclipse JDT v3.3 |
			// +------------------+
			// ptidejSolver
			// .analyseCodeLevelModelFromJAR(
			// new String[] {
			// "../../P-MARt Workspace/Eclipse JDT v3.3/libs/plugins/org.eclipse.jdt.apt.core_3.3.1.R33x_v20070831-0435.jar",
			// "../../P-MARt Workspace/Eclipse JDT v3.3/libs/plugins/org.eclipse.jdt.apt.core_3.3.1.R33x_v20071112-2330.jar",
			// "../../P-MARt Workspace/Eclipse JDT v3.3/libs/plugins/org.eclipse.jdt.apt.pluggable.core_1.0.1.R33x_v20070831-0435.jar",
			// "../../P-MARt Workspace/Eclipse JDT v3.3/libs/plugins/org.eclipse.jdt.apt.pluggable.core_1.0.1.R33x_v20071002-2100.jar",
			// "../../P-MARt Workspace/Eclipse JDT v3.3/libs/plugins/org.eclipse.jdt.apt.ui_3.3.1.R33x_v20070801-1930.jar",
			// "../../P-MARt Workspace/Eclipse JDT v3.3/libs/plugins/org.eclipse.jdt.apt.ui_3.3.1.R33x_v20071002-2100.jar"
			// },
			// "Eclipse JDT v3.3 (apt)");
			// ptidejSolver
			// .analyseCodeLevelModelFromJAR(
			// new String[] {
			// "../../P-MARt Workspace/Eclipse JDT v3.3/libs/plugins/org.eclipse.jdt.compiler.apt_1.0.1.R33x_v20070831-0435.jar",
			// "../../P-MARt Workspace/Eclipse JDT v3.3/libs/plugins/org.eclipse.jdt.compiler.apt_1.0.1.R33x_v20071002-2100.jar",
			// "../../P-MARt Workspace/Eclipse JDT v3.3/libs/plugins/org.eclipse.jdt.compiler.tool_1.0.1.v_780_R33x.jar",
			// "../../P-MARt Workspace/Eclipse JDT v3.3/libs/plugins/org.eclipse.jdt.compiler.tool_1.0.1.v_793_R33x.jar"
			// },
			// "Eclipse JDT v3.3 (compiler)");
			// ptidejSolver
			// .analyseCodeLevelModelFromJAR(
			// new String[] {
			// "../../P-MARt Workspace/Eclipse JDT v3.3/libs/plugins/org.eclipse.jdt_3.3.0.v20070607-1300.jar",
			// "../../P-MARt Workspace/Eclipse JDT v3.3/libs/plugins/org.eclipse.jdt_3.3.1.r332_v20080131.jar",
			// "../../P-MARt Workspace/Eclipse JDT v3.3/libs/plugins/org.eclipse.jdt.core.manipulation_1.1.0.v20070606-0010.jar",
			// "../../P-MARt Workspace/Eclipse JDT v3.3/libs/plugins/org.eclipse.jdt.core_3.3.1.v_780_R33x.jar",
			// "../../P-MARt Workspace/Eclipse JDT v3.3/libs/plugins/org.eclipse.jdt.core_3.3.3.v_793_R33x.jar"
			// },
			// "Eclipse JDT v3.3 (core)");
			// ptidejSolver
			// .analyseCodeLevelModelFromJAR(
			// new String[] {
			// "../../P-MARt Workspace/Eclipse JDT v3.3/libs/plugins/org.eclipse.jdt.debug.ui_3.2.101.v20070718_r331.jar",
			// "../../P-MARt Workspace/Eclipse JDT v3.3/libs/plugins/org.eclipse.jdt.debug.ui_3.2.102.v20071002_r332.jar",
			// "../../P-MARt Workspace/Eclipse JDT v3.3/libs/plugins/org.eclipse.jdt.launching_3.3.1.v20070808_r331.jar",
			// "../../P-MARt Workspace/Eclipse JDT v3.3/libs/plugins/org.eclipse.jdt.launching_3.3.2.v20080115_r332.jar"
			// },
			// "Eclipse JDT v3.3 (debug/lauching)");
			// ptidejSolver
			// .analyseCodeLevelModelFromJAR(new String[] {
			// //
			// "../../P-MARt Workspace/Eclipse JDT v3.3/libs/plugins/org.eclipse.jdt.ui_3.3.1.r331_v20070906.jar",
			// "../../P-MARt Workspace/Eclipse JDT v3.3/libs/plugins/org.eclipse.jdt.ui_3.3.2.r332_20080128.jar"
			// },
			// "Eclipse JDT v3.3 (ui)");

			// +------------------+
			// | Eclipse JDT v3.4 |
			// +------------------+
			// ptidejSolver
			// .analyseCodeLevelModelFromJAR(
			// new String[] {
			// "../../P-MARt Workspace/Eclipse JDT v3.4/libs/plugins/org.eclipse.jdt.apt.core.source_3.3.101.R34x_v20090114-1205.jar",
			// "../../P-MARt Workspace/Eclipse JDT v3.4/libs/plugins/org.eclipse.jdt.apt.core_3.3.101.R34x_v20090114-1205.jar",
			// "../../P-MARt Workspace/Eclipse JDT v3.4/libs/plugins/org.eclipse.jdt.apt.pluggable.core.source_1.0.101.R34x_v20081108-1950.jar",
			// "../../P-MARt Workspace/Eclipse JDT v3.4/libs/plugins/org.eclipse.jdt.apt.pluggable.core_1.0.101.R34x_v20081108-1950.jar",
			// "../../P-MARt Workspace/Eclipse JDT v3.4/libs/plugins/org.eclipse.jdt.apt.ui.source_3.3.100.v20080407-1345.jar",
			// "../../P-MARt Workspace/Eclipse JDT v3.4/libs/plugins/org.eclipse.jdt.apt.ui_3.3.100.v20080407-1345.jar"
			// },
			// "Eclipse JDT v3.4 (apt)");
			// ptidejSolver
			// .analyseCodeLevelModelFromJAR(
			// new String[] {
			// "../../P-MARt Workspace/Eclipse JDT v3.4/libs/plugins/org.eclipse.jdt.compiler.apt.source_1.0.101.R34x_v20090114-1205.jar",
			// "../../P-MARt Workspace/Eclipse JDT v3.4/libs/plugins/org.eclipse.jdt.compiler.apt_1.0.101.R34x_v20090114-1205.jar",
			// "../../P-MARt Workspace/Eclipse JDT v3.4/libs/plugins/org.eclipse.jdt.compiler.tool.source_1.0.100.v_894_R34x.jar",
			// "../../P-MARt Workspace/Eclipse JDT v3.4/libs/plugins/org.eclipse.jdt.compiler.tool_1.0.100.v_894_R34x.jar"
			// },
			// "Eclipse JDT v3.4 (compiler)");
			// ptidejSolver
			// .analyseCodeLevelModelFromJAR(
			// new String[] {
			// "../../P-MARt Workspace/Eclipse JDT v3.4/libs/plugins/org.eclipse.jdt.source_3.4.2.r342_v20090128.jar",
			// "../../P-MARt Workspace/Eclipse JDT v3.4/libs/plugins/org.eclipse.jdt_3.4.2.r342_v20090128.jar",
			// "../../P-MARt Workspace/Eclipse JDT v3.4/libs/plugins/org.eclipse.jdt.core.manipulation.source_1.2.0.v20080603-2000.jar",
			// "../../P-MARt Workspace/Eclipse JDT v3.4/libs/plugins/org.eclipse.jdt.core.manipulation_1.2.0.v20080603-2000.jar",
			// "../../P-MARt Workspace/Eclipse JDT v3.4/libs/plugins/org.eclipse.jdt.core.source_3.4.4.v_894_R34x.jar",
			// "../../P-MARt Workspace/Eclipse JDT v3.4/libs/plugins/org.eclipse.jdt.core_3.4.4.v_894_R34x.jar"
			// },
			// "Eclipse JDT v3.4 (core)");
			// ptidejSolver
			// .analyseCodeLevelModelFromJAR(
			// new String[] {
			// "../../P-MARt Workspace/Eclipse JDT v3.4/libs/plugins/org.eclipse.jdt.debug.source_3.4.1.v20081216_r342.jar",
			// "../../P-MARt Workspace/Eclipse JDT v3.4/libs/plugins/org.eclipse.jdt.debug.ui.source_3.3.1.v20080730_r341.jar",
			// "../../P-MARt Workspace/Eclipse JDT v3.4/libs/plugins/org.eclipse.jdt.debug.ui_3.3.1.v20080730_r341.jar",
			// "../../P-MARt Workspace/Eclipse JDT v3.4/libs/plugins/org.eclipse.jdt.launching.source_3.4.1.v20080729_r341.jar",
			// "../../P-MARt Workspace/Eclipse JDT v3.4/libs/plugins/org.eclipse.jdt.launching_3.4.1.v20080729_r341.jar"
			// },
			// "Eclipse JDT v3.4 (debug and lauching)");
			// ptidejSolver
			// .analyseCodeLevelModelFromJAR(
			// new String[] {
			// "../../P-MARt Workspace/Eclipse JDT v3.4/libs/plugins/org.eclipse.jdt.ui.source_3.4.2.r342_v20090107-0800.jar",
			// "../../P-MARt Workspace/Eclipse JDT v3.4/libs/plugins/org.eclipse.jdt.ui_3.4.2.r342_v20090107-0800.jar"
			// },
			// "Eclipse JDT v3.4 (ui)");

			// ptidejSolver.analyseCodeLevelModelFromJava(
			// CallDesignMotifIdentification.ROOT
			// + "P-MARt Workspace/JHotDraw v5.1/bin/",
			// "JHotDraw v5.1");
			// ptidejSolver.analyseCodeLevelModelFromJava(
			// CallDesignMotifIdentification.ROOT
			// + "P-MARt Workspace/JHotDraw v5.2/bin/",
			// "JHotDraw v5.2");
			// ptidejSolver.analyseCodeLevelModelFromJava(
			// CallDesignMotifIdentification.ROOT
			// + "P-MARt Workspace/JHotDraw v5.3/bin/",
			// "JHotDraw v5.3");
			// ptidejSolver.analyseCodeLevelModelFromJava(
			// CallDesignMotifIdentification.ROOT
			// + "P-MARt Workspace/JHotDraw v5.4b1/bin/",
			// "JHotDraw v5.4b1");
			// ptidejSolver.analyseCodeLevelModelFromJava(
			// CallDesignMotifIdentification.ROOT
			// + "P-MARt Workspace/JHotDraw v5.4b2/bin/",
			// "JHotDraw v5.4b2");

			// ptidejSolver.analyseCodeLevelModelFromJava(
			// "../DPL - JHotDraw v5.1/bin/",
			// "JHotDraw v5.1");
			// ptidejSolver.analyseCodeLevelModelFromJava(
			// "../DPL - JRefactory v2.6.24/bin/",
			// "JRefactory v2.6.24");
			// ptidejSolver.analyseCodeLevelModelFromJava(
			// "../DPL - JUnit v3.7/bin/",
			// "JUnit v3.7");
			// ptidejSolver.analyseCodeLevelModelFromJava(
			// "../DPL - MapperXML v1.9.7/bin/",
			// "MapperXML v1.9.7");
			// ptidejSolver.analyseCodeLevelModelFromJava(
			// "../DPL - QuickUML 2001/bin/",
			// "QuickUML 2001");

			// ptidejSolver.analyseCodeLevelModelFromJava(
			// "../../P-MARt Workspace/Azureus v2.0.3.2/bin/",
			// "Azureus v2.0.3.2");
			// ptidejSolver.analyseCodeLevelModelFromJava(
			// "../../P-MARt Workspace/Azureus v2.0.4.0/bin/",
			// "Azureus v2.0.4.0");
			// ptidejSolver.analyseCodeLevelModelFromJava(
			// "../../P-MARt Workspace/Azureus v2.0.4.2/bin/",
			// "Azureus v2.0.4.2");
			// ptidejSolver.analyseCodeLevelModelFromJava(
			// "../../P-MARt Workspace/Azureus v2.0.6.0/bin/",
			// "Azureus v2.0.6.0");
			// ptidejSolver.analyseCodeLevelModelFromJava(
			// "../../P-MARt Workspace/Azureus v2.0.7.0/bin/",
			// "Azureus v2.0.7.0");
			// ptidejSolver.analyseCodeLevelModelFromJava(
			// "../../P-MARt Workspace/Azureus v2.0.8.0/bin/",
			// "Azureus v2.0.8.0");
			// ptidejSolver.analyseCodeLevelModelFromJava(
			// "../../P-MARt Workspace/Azureus v2.0.8.2/bin/",
			// "Azureus v2.0.8.2");
			// ptidejSolver.analyseCodeLevelModelFromJava(
			// "../../P-MARt Workspace/Azureus v2.0.8.4/bin/",
			// "Azureus v2.0.8.4");
			// ptidejSolver.analyseCodeLevelModelFromJava(
			// "../../P-MARt Workspace/Azureus v2.1.0.0/bin/",
			// "Azureus v2.1.0.0");
			// ptidejSolver.analyseCodeLevelModelFromJava(
			// "../../P-MARt Workspace/Azureus v2.1.0.4/bin/",
			// "Azureus v2.1.0.4");
			// ptidejSolver.analyseCodeLevelModelFromJava(
			// "../../P-MARt Workspace/Azureus v2.2.0.0/bin/",
			// "Azureus v2.2.0.0");
			// ptidejSolver.analyseCodeLevelModelFromJava(
			// "../../P-MARt Workspace/Azureus v2.2.0.2/bin/",
			// "Azureus v2.2.0.2");
			// ptidejSolver.analyseCodeLevelModelFromJava(
			// "../../P-MARt Workspace/Azureus v2.3.0.0/bin/",
			// "Azureus v2.3.0.0");
			// ptidejSolver.analyseCodeLevelModelFromJava(
			// "../../P-MARt Workspace/Azureus v2.3.0.2/bin/",
			// "Azureus v2.3.0.2");
			// ptidejSolver.analyseCodeLevelModelFromJava(
			// "../../P-MARt Workspace/Azureus v2.3.0.4/bin/",
			// "Azureus v2.3.0.4");
			// ptidejSolver.analyseCodeLevelModelFromJava(
			// "../../P-MARt Workspace/Azureus v2.3.0.6/bin/",
			// "Azureus v2.3.0.6");
			// ptidejSolver.analyseCodeLevelModelFromJava(
			// "../../P-MARt Workspace/Azureus v2.4.0.0/bin/",
			// "Azureus v2.4.0.0");
			// ptidejSolver.analyseCodeLevelModelFromJava(
			// "../../P-MARt Workspace/Azureus v2.4.0.2/bin/",
			// "Azureus v2.4.0.2");
			// ptidejSolver.analyseCodeLevelModelFromJava(
			// "../../P-MARt Workspace/Azureus v2.4.0.2/bin/",
			// "Azureus v2.5.0.0");

			// ptidejSolver.analyseCodeLevelModelFromJava(
			// "../../P-MARt Workspace/Xerces v1.0.0/bin/",
			// "Xerces v1.0.0");
			// ptidejSolver.analyseCodeLevelModelFromJava(
			// CallDesignMotifIdentification.ROOT
			// + "P-MARt Workspace/Xerces v1.0.1/bin/",
			// "Xerces v1.0.1");
			// ptidejSolver.analyseCodeLevelModelFromJava(
			// CallDesignMotifIdentification.ROOT
			// + "P-MARt Workspace/Xerces v1.0.2/bin/",
			// "Xerces v1.0.2");
			// ptidejSolver.analyseCodeLevelModelFromJava(
			// CallDesignMotifIdentification.ROOT
			// + "P-MARt Workspace/Xerces v1.0.3/bin/",
			// "Xerces v1.0.3");
			// ptidejSolver.analyseCodeLevelModelFromJava(
			// CallDesignMotifIdentification.ROOT
			// + "P-MARt Workspace/Xerces v1.0.4/bin/",
			// "Xerces v1.0.4");
			// ptidejSolver.analyseCodeLevelModelFromJava(
			// CallDesignMotifIdentification.ROOT
			// + "P-MARt Workspace/Xerces v1.1.0/bin/",
			// "Xerces v1.1.0");
			// ptidejSolver.analyseCodeLevelModelFromJava(
			// CallDesignMotifIdentification.ROOT
			// + "P-MARt Workspace/Xerces v1.1.1/bin/",
			// "Xerces v1.1.1");
			// ptidejSolver.analyseCodeLevelModelFromJava(
			// CallDesignMotifIdentification.ROOT
			// + "P-MARt Workspace/Xerces v1.1.2/bin/",
			// "Xerces v1.1.2");
			// ptidejSolver.analyseCodeLevelModelFromJava(
			// CallDesignMotifIdentification.ROOT
			// + "P-MARt Workspace/Xerces v1.1.3/bin/",
			// "Xerces v1.1.3");
			// ptidejSolver.analyseCodeLevelModelFromJava(
			// CallDesignMotifIdentification.ROOT
			// + "P-MARt Workspace/Xerces v1.2.0/bin/",
			// "Xerces v1.2.0");
			// ptidejSolver.analyseCodeLevelModelFromJava(
			// CallDesignMotifIdentification.ROOT
			// + "P-MARt Workspace/Xerces v1.2.1/bin/",
			// "Xerces v1.2.1");
			// ptidejSolver.analyseCodeLevelModelFromJava(
			// CallDesignMotifIdentification.ROOT
			// + "P-MARt Workspace/Xerces v1.2.2/bin/",
			// "Xerces v1.2.2");
			// ptidejSolver.analyseCodeLevelModelFromJava(
			// CallDesignMotifIdentification.ROOT
			// + "P-MARt Workspace/Xerces v1.2.3/bin/",
			// "Xerces v1.2.3");
			// ptidejSolver.analyseCodeLevelModelFromJava(
			// CallDesignMotifIdentification.ROOT
			// + "P-MARt Workspace/Xerces v1.3.0/bin/",
			// "Xerces v1.3.0");
			// ptidejSolver.analyseCodeLevelModelFromJava(
			// CallDesignMotifIdentification.ROOT
			// + "P-MARt Workspace/Xerces v1.3.1/bin/",
			// "Xerces v1.3.1");
			// ptidejSolver.analyseCodeLevelModelFromJava(
			// CallDesignMotifIdentification.ROOT
			// + "P-MARt Workspace/Xerces v1.4.0/bin/",
			// "Xerces v1.4.0");
			// ptidejSolver.analyseCodeLevelModelFromJava(
			// CallDesignMotifIdentification.ROOT
			// + "P-MARt Workspace/Xerces v1.4.1/bin/",
			// "Xerces v1.4.1");
			// ptidejSolver.analyseCodeLevelModelFromJava(
			// CallDesignMotifIdentification.ROOT
			// + "P-MARt Workspace/Xerces v1.4.2/bin/",
			// "Xerces v1.4.2");
			// ptidejSolver.analyseCodeLevelModelFromJava(
			// CallDesignMotifIdentification.ROOT
			// + "P-MARt Workspace/Xerces v1.4.3/bin/",
			// "Xerces v1.4.3");
			// ptidejSolver.analyseCodeLevelModelFromJava(
			// CallDesignMotifIdentification.ROOT
			// + "P-MARt Workspace/Xerces v1.4.4/bin/",
			// "Xerces v1.4.4");

			// ptidejSolver.analyseCodeLevelModelFromJava(
			// "../../P-MARt Workspace/Xalan v1.0.0/bin/",
			// "Xalan v1.0.0");
			// ptidejSolver.analyseCodeLevelModelFromJava(
			// "../../P-MARt Workspace/Xalan v2.0.0/bin/",
			// "Xalan v2.0.0");
			// ptidejSolver.analyseCodeLevelModelFromJava(
			// "../../P-MARt Workspace/Xalan v2.1.0/bin/",
			// "Xalan v2.1.0");
			// ptidejSolver.analyseCodeLevelModelFromJava(
			// "../../P-MARt Workspace/Xalan v2.2.0/bin/",
			// "Xalan v2.2.0");
			// ptidejSolver.analyseCodeLevelModelFromJava(
			// "../../P-MARt Workspace/Xalan v2.3.0/bin/",
			// "Xalan v2.3.0");
			// ptidejSolver.analyseCodeLevelModelFromJava(
			// "../../P-MARt Workspace/Xalan v2.4.0/bin/",
			// "Xalan v2.4.0");
			// ptidejSolver.analyseCodeLevelModelFromJava(
			// "../../P-MARt Workspace/Xalan v2.5.0/bin/",
			// "Xalan v2.5.0");
			// ptidejSolver.analyseCodeLevelModelFromJava(
			// "../../P-MARt Workspace/Xalan v2.7.0/bin/",
			// "Xalan v2.7.0");

			// ptidejSolver.analyseCodeLevelModel(
			// "D:/Software/Sodalia/Method Invocations/IMS/Admin_2.3.2/Admin_2.3.2-concat_des_2007-01-28113522.aol",
			// "Admin_2.3.2 (Code)");
			// ptidejSolver.analyseCodeLevelModel(
			// "D:/Software/Sodalia/Method Invocations/IMS/Admin_2.3.3/Admin_2.3.3-concat_des_2007-01-28113620.aol",
			// "Admin_2.3.3 (Code)");
			// ptidejSolver.analyseCodeLevelModel(
			// "D:/Software/Sodalia/Method Invocations/IMS/Common_2.5.1/Common_2.5.1-concat_des_2007-01-28113715.aol",
			// "Common_2.5.1 (Code)");
			// ptidejSolver.analyseCodeLevelModel(
			// "D:/Software/Sodalia/Method Invocations/IMS/DB_2.1.2/DB_2.1.2-concat_des_2007-01-28113731.aol",
			// "DB_2.1.2 (Code)");
			// ptidejSolver.analyseCodeLevelModel(
			// "D:/Software/Sodalia/Method Invocations/IMS/ED_2.1.0/ED_2.1.0-concat_des_2007-01-28113750.aol",
			// "ED_2.1.0 (Code)");
			// ptidejSolver.analyseCodeLevelModel(
			// "D:/Software/Sodalia/Method Invocations/IMS/HDOgui_2.4.4/HDOgui_2.4.4-concat_des_2007-01-28113806.aol",
			// "HDOgui_2.4.4 (Code)");
			// ptidejSolver.analyseCodeLevelModel(
			// "D:/Software/Sodalia/Method Invocations/IMS/HDOgui_2.4.5/HDOgui_2.4.5-concat_des_2007-01-28113922.aol",
			// "HDOgui_2.4.5 (Code)");
			// ptidejSolver.analyseCodeLevelModel(
			// "D:/Software/Sodalia/Method Invocations/IMS/HDOgui_2.4.6/HDOgui_2.4.6-concat_des_2007-01-28114038.aol",
			// "HDOgui_2.4.6 (Code)");
			// ptidejSolver.analyseCodeLevelModel(
			// "D:/Software/Sodalia/Method Invocations/IMS/Meta_2.1.0/Meta_2.1.0-concat_des_2007-01-28114140.aol",
			// "Meta_2.1.0 (Code)");
			// ptidejSolver.analyseCodeLevelModel(
			// "D:/Software/Sodalia/Method Invocations/IMS/NMI_2.2.0/NMI_2.2.0-concat_des_2007-01-28114144.aol",
			// "NMI_2.2.0 (Code)");
			// ptidejSolver.analyseCodeLevelModel(
			// "D:/Software/Sodalia/Method Invocations/IMS/OSSI_2.0.2/OSSI_2.0.2-concat_des_2007-01-28114206.aol",
			// "OSSI_2.0.2 (Code)");
			// ptidejSolver.analyseCodeLevelModel(
			// "D:/Software/Sodalia/Method Invocations/IMS/PM_2.0.3/PM_2.0.3-concat_des_2007-01-28114213.aol",
			// "PM_2.0.3 (Code)");
			// ptidejSolver.analyseCodeLevelModel(
			// "D:/Software/Sodalia/Method Invocations/IMS/TM_2.2.0/TM_2.2.0-concat_des_2007-01-28114245.aol",
			// "TM_2.2.0 (Code)");
			// ptidejSolver.analyseCodeLevelModel(
			// "D:/Software/Sodalia/Method Invocations/REUSE/AlarmBrowser/AlarmBrowser-concat_des_2007-01-28115250.aol",
			// "AlarmBrowser (Code)");
			// ptidejSolver.analyseCodeLevelModel(
			// "D:/Software/Sodalia/Method Invocations/REUSE/ApplMgr/ApplMgr-concat_des_2007-01-28115903.aol",
			// "ApplMgr (Code)");
			// ptidejSolver.analyseCodeLevelModel(
			// "D:/Software/Sodalia/Method Invocations/REUSE/BDT/BDT-concat_des_2007-01-28115919.aol",
			// "BDT (Code)");
			// ptidejSolver.analyseCodeLevelModel(
			// "D:/Software/Sodalia/Method Invocations/REUSE/LOGLIB/LOGLIB-concat_des_2007-01-28115932.aol",
			// "LOGLIB (Code)");
			// ptidejSolver.analyseCodeLevelModel(
			// "D:/Software/Sodalia/Method Invocations/VBS/ConfigFileLib/ConfigFileLib-concat_des_2007-01-28123422.aol",
			// "ConfigFileLib (Code)");
			// ptidejSolver.analyseCodeLevelModel(
			// "D:/Software/Sodalia/Method Invocations/VBS/DataRetantion/DataRetantion-concat_des_2007-01-28124217.aol",
			// "DataRetantion (Code)");
			// ptidejSolver.analyseCodeLevelModel(
			// "D:/Software/Sodalia/Method Invocations/VBS/DbConn/DbConn-concat_des_2007-01-28124222.aol",
			// "DbConn (Code)");
			// ptidejSolver.analyseCodeLevelModel(
			// "D:/Software/Sodalia/Method Invocations/VBS/DistPublic/DistPublic-concat_des_2007-01-28122120.aol",
			// "DistPublic (Code)");
			// ptidejSolver.analyseCodeLevelModel(
			// "D:/Software/Sodalia/Method Invocations/VBS/Error_Library/Error_Library-concat_des_2007-01-28124247.aol",
			// "Error_Library (Code)");
			// ptidejSolver.analyseCodeLevelModel(
			// "D:/Software/Sodalia/Method Invocations/VBS/GUIBrowser/GUIBrowser-concat_des_2007-01-28124249.aol",
			// "GUIBrowser (Code)");
			// ptidejSolver.analyseCodeLevelModel(
			// "D:/Software/Sodalia/Method Invocations/VBS/GUIconfig/GUIconfig-concat_des_2007-01-28124328.aol",
			// "GUIConfig (Code)");
			// ptidejSolver.analyseCodeLevelModel(
			// "D:/Software/Sodalia/Method Invocations/VBS/GUIevents/GUIevents-concat_des_2007-01-28124345.aol",
			// "GUIEvents (Code)");
			// ptidejSolver.analyseCodeLevelModel(
			// "D:/Software/Sodalia/Method Invocations/VBS/Trace_Lib/Trace_Lib-concat_des_2007-01-28124406.aol",
			// "Trace_Lib (Code)");
			// ptidejSolver.analyseCodeLevelModel(
			// "D:/Software/Sodalia/Method Invocations/VBS/VIPER_PROCESSING/VIPER_PROCESSING-concat_des_2007-01-28124412.aol",
			// "Viper_Processing (Code)");
			// ptidejSolver.analyseCodeLevelModel(
			// "D:/Software/Sodalia/Method Invocations/VBS/VMCS_Processing/VMCS_Processing-concat_des_2007-01-28124419.aol",
			// "VMCS_Processing (Code)");
			// ptidejSolver.analyseCodeLevelModel(
			// "D:/Software/Sodalia/Method Invocations/VBS/VSPA_Processing/VSPA_Processing-concat_des_2007-01-28124427.aol",
			// "VSPA_Processing (Code)");
			// ptidejSolver.analyseCodeLevelModel(
			// "D:/Software/Sodalia/Method Invocations/VSPS/CollectorAcquisition/CollectorAcquisition-concat_des_2007-01-28130639.aol",
			// "CollectorAcquisition (Code)");
			// ptidejSolver.analyseCodeLevelModel(
			// "D:/Software/Sodalia/Method Invocations/VSPS/CollectorDistribution/CollectorDistribution-concat_des_2007-01-28130647.aol",
			// "CollectorDistribution (Code)");
			// ptidejSolver.analyseCodeLevelModel(
			// "D:/Software/Sodalia/Method Invocations/VSPS/CollectorFormatting/CollectorFormatting-concat_des_2007-01-28130658.aol",
			// "CollectorFormatting (Code)");
			// ptidejSolver.analyseCodeLevelModel(
			// "D:/Software/Sodalia/Method Invocations/VSPS/DispatcherDistribution/DispatcherDistribution-concat_des_2007-01-28130712.aol",
			// "DispatcherDistribution (Code)");

			// ptidejSolver.analyseIdiomLevelModel(
			// "D:/Software/Sodalia/Design and Code/IMS/design/ADM/ADM.aol",
			// "ADM (Design)");
			// ptidejSolver.analyseIdiomLevelModel(
			// "D:/Software/Sodalia/Design and Code/IMS/design/DB/DB.aol",
			// "DB (Design)");
			// ptidejSolver.analyseIdiomLevelModel(
			// "D:/Software/Sodalia/Design and Code/IMS/design/ED/ED.aol",
			// "ED (Design)");
			// ptidejSolver.analyseIdiomLevelModel(
			// "D:/Software/Sodalia/Design and Code/IMS/design/GUI/GUI.aol",
			// "GUI (Design)");
			// ptidejSolver.analyseIdiomLevelModel(
			// "D:/Software/Sodalia/Design and Code/IMS/design/NMI/NMI.aol",
			// "NMI (Design)");
			// ptidejSolver.analyseIdiomLevelModel(
			// "D:/Software/Sodalia/Design and Code/IMS/design/OSSI/OSSI.aol",
			// "OSSI (Design)");
			// ptidejSolver.analyseIdiomLevelModel(
			// "D:/Software/Sodalia/Design and Code/IMS/design/PM/PM.aol",
			// "PM (Design)");
			// ptidejSolver.analyseIdiomLevelModel(
			// "D:/Software/Sodalia/Design and Code/IMS/design/TM/TM.aol",
			// "TM (Design)");
			// ptidejSolver.analyseIdiomLevelModel(
			// "D:/Software/Sodalia/Design and Code/REUSE/design/AlarmBrowser.aol.raw",
			// "AlarmBrowser (Design)");
			// ptidejSolver.analyseIdiomLevelModel(
			// "D:/Software/Sodalia/Design and Code/VBS/design/Config_Browser.aol.raw",
			// "Config_Browser (Design)");
			// ptidejSolver.analyseIdiomLevelModel(
			// "D:/Software/Sodalia/Design and Code/VBS/design/ConfigFileLib.aol.raw",
			// "ConfigFileLib (Design)");
			// ptidejSolver.analyseIdiomLevelModel(
			// "D:/Software/Sodalia/Design and Code/VBS/design/Error_Library.aol.raw",
			// "Error_Library (Design)");
			// ptidejSolver.analyseIdiomLevelModel(
			// "D:/Software/Sodalia/Design and Code/VBS/design/GUIBrowser.aol.raw",
			// "GUIBrowser (Design)");
			// ptidejSolver.analyseIdiomLevelModel(
			// "D:/Software/Sodalia/Design and Code/VBS/design/GUIConfig.aol.raw",
			// "GUIConfig (Design)");
			// ptidejSolver.analyseIdiomLevelModel(
			// "D:/Software/Sodalia/Design and Code/VBS/design/GUIEvents.aol.raw",
			// "GUIEvents (Design)");
			// ptidejSolver.analyseIdiomLevelModel(
			// "D:/Software/Sodalia/Design and Code/VBS/design/Trace_Lib.aol.raw",
			// "Trace_Lib (Design)");
			// ptidejSolver.analyseIdiomLevelModel(
			// "D:/Software/Sodalia/Design and Code/VBS/design/Viper_Processing.aol.raw",
			// "Viper_Processing (Design)");
			// ptidejSolver.analyseIdiomLevelModel(
			// "D:/Software/Sodalia/Design and Code/VBS/design/VMCS_Processing.aol.raw",
			// "VMCS_Processing (Design)");
			// ptidejSolver.analyseIdiomLevelModel(
			// "D:/Software/Sodalia/Design and Code/VBS/design/VSPA_Processing.aol.raw",
			// "VSPA_Processing (Design)");
			// ptidejSolver.analyseIdiomLevelModel(
			// "D:/Software/Sodalia/Design and Code/VSPS/design/CollectorAcquisition.aol.raw",
			// "CollectorAcquisition (Design)");
			// ptidejSolver.analyseIdiomLevelModel(
			// "D:/Software/Sodalia/Design and Code/VSPS/design/CollectorDistribution.aol.raw",
			// "CollectorDistribution (Design)");
			// ptidejSolver.analyseIdiomLevelModel(
			// "D:/Software/Sodalia/Design and Code/VSPS/design/CollectorFormatting.aol.raw",
			// "CollectorFormatting (Design)");
			// ptidejSolver.analyseIdiomLevelModel(
			// "D:/Software/Sodalia/Design and Code/VSPS/design/DispatcherDistribution.aol.raw",
			// "DispatcherDistribution (Design)");
			// ptidejSolver.analyseIdiomLevelModel(
			// "D:/Software/Sodalia/Design and Code/VSPS/design/VP_FM_Filter.aol.raw",
			// "VP_FM_Filter (Design)");

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
