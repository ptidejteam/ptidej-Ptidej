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
package squad.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import padl.analysis.UnsupportedSourceModelException;
import padl.analysis.repository.AACRelationshipsAnalysis;
import padl.creator.classfile.CompleteClassFileCreator;
import padl.kernel.IAbstractModel;
import padl.kernel.ICodeLevelModel;
import padl.kernel.IEntity;
import padl.kernel.IIdiomLevelModel;
import padl.kernel.exception.CreationException;
import padl.kernel.impl.Factory;
import padl.statement.creator.classfiles.ConditionalModelAnnotator;
import padl.statement.creator.classfiles.LOCModelAnnotator;
import ptidej.solver.Occurrence;
import ptidej.solver.OccurrenceBuilder;
import ptidej.solver.Problem;
import ptidej.solver.domain.Manager;
import sad.codesmell.detection.CodeSmellDetectionsRepository;
import sad.codesmell.detection.ICodeSmellDetection;
import sad.designsmell.detection.DesignSmellDetectionsRepository;
import sad.designsmell.detection.IDesignSmellDetection;
import util.io.ProxyConsole;
import util.io.ProxyDisk;
import util.io.ReaderInputStream;

public class SimpleSmellDetection {

	/**
	 * @param args
	 */
	// private static final String[] SMELLS =
	// new String[] { "AntiSingleton", "BaseClassKnowsDerivedClass",
	// "BaseClassShouldBeAbstract", "Blob",
	// "ClassDataShouldBePrivate", "ComplexClass",
	// "FunctionalDecomposition", "LargeClass", "LazyClass",
	// "LongMethod", "LongParameterList",
	// "ManyFieldAttributesButNotComplex", "MessageChains",
	// "RefusedParentBequest", "SpaghettiCode",
	// "SpeculativeGenerality", "SwissArmyKnife", "TraditionBreaker" };
	private static final String[] SMELLS = new String[] { "Blob" };
	// private static final String[] SMELLS = new String[] { "ComplexClass" };

	// private static final String[] CODE_SMELLS =
	// new String[] { "ControllerClass", "LargeClass", "LowCohesion" };

	private static final String[] CODE_SMELLS = new String[] {
			"ComplexClassOnly", "LargeClassOnly" };

	//	private static final String[] MOTIFS = new String[] { "AbstractFactory",
	//			"Adapter", "Command", "Composite", "Decorator", "FactoryMethod",
	//			"Observer1", "Observer2", "Prototype", "State", "TemplateMethod",
	//			"Visitor" };

	private static final String[] MOTIFS = new String[] { // "AbstractFactory", 
		//"Adapter", "Command", 
				"Composite", "Decorator"//, "FactoryMethod",
		//"Observer1", "Observer2", "Prototype", "State",
		//"TemplateMethod", "Visitor", "Builder"
		};

	public static void main(String[] args) throws FileNotFoundException,
			IOException {
		// String dirName = "D:/Software/P-MARt Workspace/Azureus
		// v2.3.0.6/bin/";
		// SimpleSmellDetection.analyseCodeLevelModelFromDir(
		// dirName,
		// "Azureus v2.3.0.6",
		// "rsc/QSIC/");
		//		
		//		
		// String dirName1 = "D:/Software/P-MARt Workspace/ArgoUML v.0.18/bin/";
		// SimpleSmellDetection.analyseCodeLevelModelFromDir(
		// dirName1,
		// "ArgoUML v.0.18",
		// "rsc/QSIC/");

		// String dirName2 = "D:/Software/P-MARt Workspace/GanttProject
		// v1.10.2/bin/";
		// SimpleSmellDetection.analyseCodeLevelModelFromDir(
		// dirName2,
		// "GanttProject v1.10.2",
		// "rsc/QSIC/");

		// String dirName2 = "D:/Software/P-MARt Workspace/guehene-dpl-xerces
		// v.2.7.0 (dom2)/bin/";
		// SimpleSmellDetection.analyseCodeLevelModelFromDir(
		// dirName2,
		// "Xerces v.2.7.0 (dom2)",
		// "rsc/QSIC/");

		/*
		 * String dirName2 = "D:/Software/P-MARt Workspace/JHotDraw v5.1/bin/";
		 * new File("rsc/JHotDraw v.5.1/").mkdir();
		 * 
		 * SimpleSmellDetection.analyseCodeLevelModelFromDir( dirName2,
		 * "JHotDraw v.5.1", "rsc/JHotDraw v.5.1/");
		 */

		/*
		 * String dirName2 = "D:/Software/P-MARt Workspace/JHotDraw v.7.1/bin/";
		 * new File("rsc/JHotDraw v.7.1/").mkdir();
		 * 
		 * SimpleSmellDetection.analyseCodeLevelModelFromDir( dirName2,
		 * "JHotDraw v.7.1", "rsc/JHotDraw v.7.1/");
		 */

		// Computing code smells for Azureus
		// Azureus3.1.0.0.jar
		// String dirName2 = "rsc/ArgoUML/ArgoUML-" + args[0] + "/argouml.jar";
		// new File("rsc/ArgoUML-" + args[0] + "/").mkdir();
		//
		// SimpleSmellDetection.analyseCodeLevelModelFromJAR(
		// new String[] { dirName2 },
		// "ArgoUML-" + args[0],
		// "rsc/ArgoUML-" + args[0] + "/");
		// //computing Antipatterns for Rhino
		// String dirName2 = "D:/Software/P-MARt Workspace/rhino-" + args[0] +
		// "/";
		// new File("rsc/rhino-" + args[0] + "/").mkdir();
		// SimpleSmellDetection.analyseCodeLevelModelFromDir(dirName2, "rhino-"
		// + args[0], "rsc/rhino-" + args[0] + "/");
		//		
		// computing mylyn
		// String dirName = "rsc/Mylyn/mylyn-" + args[0] + "-e3.3";
		//		
		// //String dirName = "rsc/Mylyn/mylar-site-" + args[0] + "-e3.3";
		//		
		// new File("rsc/Results of Antipatterns/Mylyn/mylyn-" + args[0] + "/")
		// .mkdir();
		// System.out.println(SimpleSmellDetection
		// .getClasses("rsc/QSIC/Smells/group1Xerces.csv")[0]);
		// String dirName = "rsc/Mylyn/mylyn-" + args[0] + "-e3.3";
		//		
		// //String dirName = "rsc/Mylyn/mylar-site-" + args[0] + "-e3.3";
		//		
		// new File("rsc/Results of Antipatterns/Mylyn/mylyn-" + args[0] + "/")
		// .mkdir();
		//		
		// List files = new ArrayList();
		//
		// final CallSmellDetection detection = new CallSmellDetection();
		// List jarfiles = detection.extractJarsFromDir(dirName, files);
		//
		// SimpleSmellDetection.analyseCodeLevelModelFromJAR(
		// createArrayOfJars(jarfiles),
		// "mylyn-" + args[0],
		// "rsc/Results of Antipatterns/Mylyn/mylyn-" + args[0] + "/");
		//
		// System.out.println("Computations completed for " + args[0] + " !!!
		// ");
		//		
		/**
		 * Computing design patterns in Mylyn's versions
		 */
		// String dirName = "rsc/Mylyn/mylyn-" + args[0] + "-e3.3";
		//
		// // String dirName = "rsc/Mylyn/mylar-site-" + args[0] + "-e3.3";
		//
		// new File("rsc/ResultsDesignPatterns/Mylyn/mylyn-" + args[0] + "/")
		// .mkdir();
		//
		// List files = new ArrayList();
		//
		// final CallSmellDetection detection = new CallSmellDetection();
		// List jarfiles = detection.extractJarsFromDir(dirName, files);
		//
		// SimpleSmellDetection.analyseCodeLevelModelFromJAR(
		// createArrayOfJars(jarfiles),
		// "mylyn-" + args[0],
		// "rsc/ResultsDesignPatterns/Mylyn/mylyn-" + args[0] + "/");
		//		
		// I compute classes of new ArgoUML versions
		// String dirName1 = "rsc/ArgoUML - New/ArgoUML-" + args[0]
		// + "/argouml.jar";
		//
		// SimpleSmellDetection.analyseCodeLevelModelFromJAR(
		// new String[] { dirName1 }, "ArgoUML-" + args[0],
		// "rsc/ArgoUML - DECOR Results/ArgoUML-" + args[0] + "/");
		//
		// System.out.println("Computations completed for " + args[0] + " !!!
		// ");
		// String dirName1 = "rsc/ArgoUML - New/ArgoUML-" + args[0] +"/argouml-"
		// + args[0] +"/argouml.jar";
		//		
		// SimpleSmellDetection.analyseCodeLevelModelFromJAR(
		// new String[] { dirName1 },
		// "ArgoUML-" + args[0] ,
		// "rsc/ArgoUML - DECOR Results/ArgoUML-" + args[0] +"/");
		//		
		// System.out.println("Computations completed for " + args[0] +" !!! ");
		//	
		//	
		String dirName1 = "rsc/Azureus/Azureus" + args[0] + ".jar";
		new File("rsc/Azureus - PTIDEJ Results/Azureusv" + args[0] + "/")
			.mkdir();
		SimpleSmellDetection.analyseCodeLevelModelFromJAR(
			new String[] { dirName1 },
			"Azureusv" + args[0],
			"rsc/Azureus - PTIDEJ Results/Azureusv" + args[0] + "/");

		System.out.println("Computations completed for " + args[0] + " !!! ");
	}

	/*
	 * compute array of files
	 */
	// public static String[] getClasses(String groupname)
	// throws FileNotFoundException, CorruptIndexException, IOException,
	// ParseException {
	// LineNumberReader fr = null;
	// fr = new LineNumberReader(new FileReader(groupname));
	// String[] group = new String[500];
	// fr.readLine();
	// String line;
	// int i = 0;
	// while ((line = fr.readLine()) != null) {
	// String[] ucharFields = line.split("", 3);
	// group[0] = ucharFields[0];
	// i++;
	// }
	// return group;
	// }
	/*
	 * Method to analyse codelevel models from jars
	 * 
	 */
	public final static void analyseCodeLevelModelFromJAR(
		final String[] someJARFiles,
		final String aName,
		final String anOutputDirectory) {

		System.out.print("Analysing ");
		System.out.print(aName);
		System.out.println("...");

		try {
			ICodeLevelModel codeLevelModel =
				Factory.getInstance().createCodeLevelModel(aName);

			codeLevelModel.create(new CompleteClassFileCreator(
				someJARFiles,
				true));

			final ConditionalModelAnnotator annotator =
				new ConditionalModelAnnotator(someJARFiles);
			final ICodeLevelModel annotatedCodeLevelModel1 =
				(ICodeLevelModel) annotator.invoke(codeLevelModel);

			final LOCModelAnnotator annotator2 =
				new LOCModelAnnotator(someJARFiles);
			codeLevelModel =
				(ICodeLevelModel) annotator2.invoke(annotatedCodeLevelModel1);

			/*
			 * We start by generating the list of all classes in the model
			 * 
			 */

			Iterator<?> iter = codeLevelModel.getIteratorOnTopLevelEntities();

			final Writer out =
				ProxyDisk.getInstance().fileTempOutput(
					anOutputDirectory + "ClassNames.csv",
					true);

			// BufferedWriter out = new BufferedWriter(fw);

			while (iter.hasNext()) {

				IEntity anElement = (IEntity) iter.next();

				out.write(anElement.getDisplayName());
				out.flush();
				out.write('\n');
				out.flush();

			}

			out.close();

			// for antipatterns......
			// analyseCodeLevelModel(aName, codeLevelModel, anOutputDirectory);

			/*
			 * for computing Antipatterns from the repository of Antipatterns
			 * 
			 */

			//analyseAntipatternsFromRep(aName, codeLevelModel, anOutputDirectory);
			// for the smells directly
			//		
			// computeCodeSmells(aName, codeLevelModel, anOutputDirectory);
			/*
			 * for computing design patterns
			 * 
			 */
			computePatterns(codeLevelModel, anOutputDirectory);
			// in case of direct codesmell from the repository of code smells we
			// call the following
			// analyseCodeSmellFromRep(aName, codeLevelModel,
			// anOutputDirectory);
			System.out.print("Detection completed with success!!! ");
		}
		catch (final CreationException e) {
			e.printStackTrace(ProxyConsole.getInstance().errorOutput());
		}
		catch (final UnsupportedSourceModelException e) {
			e.printStackTrace(ProxyConsole.getInstance().errorOutput());
		}

		catch (final IOException e) {
			e.printStackTrace();
		}
	}

	/*
	 * Method to analyse codelevel models from directories
	 * 
	 */

	public final static void analyseCodeLevelModelFromDir(
		final String aPath,
		final String aName,
		final String anOutputDirectory) {

		System.out.print("Analysing ");
		System.out.print(aName);
		System.out.println("...");

		try {
			ICodeLevelModel codeLevelModel =
				Factory.getInstance().createCodeLevelModel(aName);

			codeLevelModel.create(new CompleteClassFileCreator(
				new String[] { aPath },
				true));

			final ConditionalModelAnnotator annotator =
				new ConditionalModelAnnotator(new String[] { aPath });
			final ICodeLevelModel annotatedCodeLevelModel1 =
				(ICodeLevelModel) annotator.invoke(codeLevelModel);

			final LOCModelAnnotator annotator2 =
				new LOCModelAnnotator(new String[] { aPath });
			codeLevelModel =
				(ICodeLevelModel) annotator2.invoke(annotatedCodeLevelModel1);
			/*
			 * We start by generating the list of all classes in the model
			 * 
			 */

			Iterator<?> iter = codeLevelModel.getIteratorOnTopLevelEntities();

			final Writer out =
				ProxyDisk.getInstance().fileTempOutput(
					anOutputDirectory + "ClassNames.csv",
					true);

			// BufferedWriter out = new BufferedWriter(fw);

			while (iter.hasNext()) {

				IEntity anElement = (IEntity) iter.next();

				out.write(anElement.getDisplayName());
				out.flush();
				out.write('\n');
				out.flush();

			}

			out.close();
			// for Antipatterns......

			// analyseCodeLevelModel(aName, codeLevelModel, anOutputDirectory);

			/*
			 * for computing Antipatterns from the repository of Antipatterns
			 * 
			 */

			analyseAntipatternsFromRep(aName, codeLevelModel, anOutputDirectory);

			// for the smells directly
			//		
			// computeCodeSmells(aName, codeLevelModel, anOutputDirectory);

			// computing design patterns

			// computePatterns( codeLevelModel);

			/*
			 * For computing codesmells from the repository of smells
			 * 
			 */

			// analyseCodeSmellFromRep(aName, codeLevelModel,
			// anOutputDirectory);
		}
		catch (final CreationException e) {
			e.printStackTrace(ProxyConsole.getInstance().errorOutput());
		}
		catch (final UnsupportedSourceModelException e) {
			e.printStackTrace(ProxyConsole.getInstance().errorOutput());
		}
		catch (final IOException e) {
			e.printStackTrace();
		}
	}

	public final static void analyseCodeLevelModel(
		final String aName,
		final ICodeLevelModel codeLevelModel,
		final String anOutputDirectory) {

		try {
			final IIdiomLevelModel idiomLevelModel =
				(IIdiomLevelModel) new AACRelationshipsAnalysis()
					.invoke(codeLevelModel);

			for (int i = 0; i < SimpleSmellDetection.SMELLS.length; i++) {
				final String antipatternName = SimpleSmellDetection.SMELLS[i];

				final long startTime = System.currentTimeMillis();
				final Class<?> detectionClass =
					Class.forName("sad.designsmell.detection."
							+ antipatternName + '.' + antipatternName
							+ "Detection");
				final IDesignSmellDetection detection =
					(IDesignSmellDetection) detectionClass.getDeclaredConstructor().newInstance();
				try {
					detection.detect(idiomLevelModel);
				}
				catch (final RuntimeException e1) {
					e1.printStackTrace();
				}
				final String path =
					anOutputDirectory + "DetectionResults" + " in " + aName
							+ " for " + antipatternName + ".ini";
				detection.output(new PrintWriter(ProxyDisk
					.getInstance()
					.fileTempOutput(path)));

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
				System.out.print(antipatternName);
				System.out.print(" in ");
				System.out.print(aName);
				System.out.print(" in ");
				System.out.print(System.currentTimeMillis() - startTime);
				System.out.println(" ms.");

			}
		}
		catch (final FileNotFoundException e) {
			e.printStackTrace();
		}
		catch (final ClassNotFoundException e) {
			e.printStackTrace();
		}
		catch (final InstantiationException e) {
			e.printStackTrace();
		}
		catch (final IllegalAccessException e) {
			e.printStackTrace();
		}
		catch (final IOException e) {
			e.printStackTrace();
		}
		catch (final UnsupportedSourceModelException e) {
			e.printStackTrace();
		} catch (final IllegalArgumentException e) {
			e.printStackTrace();
		} catch (final InvocationTargetException e) {
			e.printStackTrace();
		} catch (final NoSuchMethodException e) {
			e.printStackTrace();
		} catch (final SecurityException e) {
			e.printStackTrace();
		}
	}

	/*
	 * Computing code smells in systems
	 * 
	 */

	public final static void computeCodeSmells(
		final String aName,
		final ICodeLevelModel codeLevelModel,
		final String anOutputDirectory) {

		try {
			final IIdiomLevelModel idiomLevelModel =
				(IIdiomLevelModel) new AACRelationshipsAnalysis()
					.invoke(codeLevelModel);

			for (int i = 0; i < SimpleSmellDetection.CODE_SMELLS.length; i++) {
				final String codesmellName =
					SimpleSmellDetection.CODE_SMELLS[i];

				final long startTime = System.currentTimeMillis();
				final Class<?> detectionClass =
					Class.forName("sad.codesmell.detection.ComplexClass."
							+ codesmellName + "Detection");

				final ICodeSmellDetection detection =
					(ICodeSmellDetection) detectionClass.getDeclaredConstructor().newInstance();
				try {
					detection.detect(idiomLevelModel);
				}
				catch (final RuntimeException e1) {
					e1.printStackTrace();
				}
				final String path =
					anOutputDirectory + "DetectionResults" + " in " + aName
							+ " for " + codesmellName + ".ini";
				detection.output(new PrintWriter(ProxyDisk
					.getInstance()
					.fileTempOutput(path)));

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
				System.out.print(codesmellName);
				System.out.print(" in ");
				System.out.print(aName);
				System.out.print(" in ");
				System.out.print(System.currentTimeMillis() - startTime);
				System.out.println(" ms.");

			}
		}
		catch (final FileNotFoundException e) {
			e.printStackTrace();
		}
		catch (final ClassNotFoundException e) {
			e.printStackTrace();
		}
		catch (final InstantiationException e) {
			e.printStackTrace();
		}
		catch (final IllegalAccessException e) {
			e.printStackTrace();
		}
		catch (final IOException e) {
			e.printStackTrace();
		}
		catch (final UnsupportedSourceModelException e) {
			e.printStackTrace();
		} catch (final IllegalArgumentException e) {
			e.printStackTrace();
		} catch (final InvocationTargetException e) {
			e.printStackTrace();
		} catch (final NoSuchMethodException e) {
			e.printStackTrace();
		} catch (final SecurityException e) {
			e.printStackTrace();
		}
	}

	/*
	 * compute design patterns on the model
	 * 
	 */
	public final static void computePatterns(
		IAbstractModel codeLevelModel,
		String anOutputDirectory) {

		System.out.print("Analysing ");
		System.out.print(codeLevelModel.getName());
		System.out.println("...");
		//		
		try {
			System.out.println("Creating idiom-level model...");
			final IIdiomLevelModel idiomLevelModel =
				(IIdiomLevelModel) new AACRelationshipsAnalysis()
					.invoke(codeLevelModel);
			System.out.println("Idiom-level model with "
					+ idiomLevelModel.getNumberOfTopLevelEntities()
					+ " entities.");

			for (int i = 0; i < SimpleSmellDetection.MOTIFS.length; i++) {

				final String motifName = SimpleSmellDetection.MOTIFS[i];

				System.out.println("Identifying occurrences of the "
						+ motifName + " design motif...");

				final Class<?> problemClass;

				problemClass =
					Class.forName("ptidej.solver.problem." + motifName
							+ "Motif");

				final Method getProblemMethod =
					problemClass.getMethod(
						"getProblem",
						new Class[] { List.class });
				final Problem problem =
					(Problem) getProblemMethod.invoke(
						null,
						new Object[] { Manager.build(idiomLevelModel) });
				final String path =
					anOutputDirectory + "ConstraintResults in "
							+ codeLevelModel.getDisplayName() + " for "
							+ motifName + ".ini";
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
				System.out.print(codeLevelModel.getName());
				System.out.print(" in ");
				System.out.print(System.currentTimeMillis() - startTime);
				System.out.println(" ms.");
			}
		}
		catch (final FileNotFoundException e) {
			e.printStackTrace();
		}
		catch (final NoSuchMethodException e) {
			e.printStackTrace();
		}
		catch (final IllegalAccessException e) {
			e.printStackTrace();
		}
		catch (final InvocationTargetException e) {
			e.printStackTrace();
		}
		catch (final IOException e) {
			e.printStackTrace();
		}
		catch (final ClassNotFoundException e) {
			e.printStackTrace();
		}
		catch (UnsupportedSourceModelException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/*
	 * Method for the computation of all the code smells from the repository of
	 * smells on a model
	 * 
	 */

	public final static void analyseCodeSmellFromRep(
		final String aName,
		final ICodeLevelModel codeLevelModel,
		final String anOutputDirectory) {

		try {
			final IIdiomLevelModel idiomLevelModel =
				(IIdiomLevelModel) new AACRelationshipsAnalysis()
					.invoke(codeLevelModel);

			final CodeSmellDetectionsRepository codeSmellDetectionsRepository =
				CodeSmellDetectionsRepository.getInstance();

			final ICodeSmellDetection[] listOfsmells =
				codeSmellDetectionsRepository.getCodeSmellDetections();

			for (int i = 0; i < listOfsmells.length; i++) {
				final ICodeSmellDetection detectioncodeSmell = listOfsmells[i];
				final long startTime = System.currentTimeMillis();
				try {
					detectioncodeSmell.detect(idiomLevelModel);
				}
				catch (final RuntimeException e1) {
					e1.printStackTrace();
				}
				final String path =
					anOutputDirectory + "DetectionResults" + " in " + aName
							+ " for " + detectioncodeSmell.getName() + ".ini";
				detectioncodeSmell.output(new PrintWriter(ProxyDisk
					.getInstance()
					.fileTempOutput(path)));

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
				System.out.print(detectioncodeSmell.getName());
				System.out.print(" in ");
				System.out.print(aName);
				System.out.print(" in ");
				System.out.print(System.currentTimeMillis() - startTime);
				System.out.println(" ms.");

				// this.extractClassesDefects(
				// anOutputDirectory,
				// aName,
				// idiomLevelModel,
				// antipatternName,
				// properties,
				// solutionBuilder);

			}
		}
		catch (final FileNotFoundException e) {
			e.printStackTrace();
		}

		catch (final IOException e) {
			e.printStackTrace();
		}
		catch (UnsupportedSourceModelException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/*
	 * this method is to compute antipatterns directly from a repository of all
	 * available antipatterns
	 * 
	 */

	public final static void analyseAntipatternsFromRep(
		final String aName,
		final ICodeLevelModel codeLevelModel,
		final String anOutputDirectory) {

		try {
			final IIdiomLevelModel idiomLevelModel =
				(IIdiomLevelModel) new AACRelationshipsAnalysis()
					.invoke(codeLevelModel);

			final DesignSmellDetectionsRepository designSmellDetectionsRepository =
				DesignSmellDetectionsRepository.getInstance();

			final IDesignSmellDetection[] listOfsmells =
				designSmellDetectionsRepository.getDesignSmellDetections();

			for (int i = 0; i < listOfsmells.length; i++) {
				final IDesignSmellDetection detectiondesignSmell =
					listOfsmells[i];
				final long startTime = System.currentTimeMillis();
				try {
					detectiondesignSmell.detect(idiomLevelModel);
				}
				catch (final RuntimeException e1) {
					e1.printStackTrace();
				}
				final String path =
					anOutputDirectory + "DetectionResults" + " in " + aName
							+ " for " + detectiondesignSmell.getName() + ".ini";
				detectiondesignSmell.output(new PrintWriter(ProxyDisk
					.getInstance()
					.fileTempOutput(path)));

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
				System.out.print(detectiondesignSmell.getName());
				System.out.print(" in ");
				System.out.print(aName);
				System.out.print(" in ");
				System.out.print(System.currentTimeMillis() - startTime);
				System.out.println(" ms.");

				// this.extractClassesDefects(
				// anOutputDirectory,
				// aName,
				// idiomLevelModel,
				// antipatternName,
				// properties,
				// solutionBuilder);

			}
		}
		catch (final FileNotFoundException e) {
			e.printStackTrace();
		}

		catch (final IOException e) {
			e.printStackTrace();
		}
		catch (UnsupportedSourceModelException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/*
	 * 
	 * Create an Array from a list
	 */

	public static String[] createArrayOfJars(List<?> l) {
		String[] files = new String[l.size()];
		for (int i = 0; i < l.size(); i++) {
			files[i] = (String) l.get(i);

		}
		return files;
	}
}
