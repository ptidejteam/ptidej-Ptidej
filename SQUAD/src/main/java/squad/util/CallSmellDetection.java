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
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import padl.analysis.UnsupportedSourceModelException;
import padl.analysis.repository.AACRelationshipsAnalysis;
import padl.creator.classfile.CompleteClassFileCreator;
import padl.kernel.ICodeLevelModel;
import padl.kernel.IIdiomLevelModel;
import padl.kernel.exception.CreationException;
import padl.kernel.impl.Factory;
import ptidej.solver.Occurrence;
import ptidej.solver.OccurrenceBuilder;
import sad.codesmell.detection.CodeSmellDetectionsRepository;
import sad.codesmell.detection.ICodeSmellDetection;
import sad.designsmell.detection.IDesignSmellDetection;
import util.io.ProxyConsole;
import util.io.ProxyDisk;
import util.io.ReaderInputStream;

public class CallSmellDetection {

	/**
	 * @author Foutse Khomh
	 * @since 2008/12/02
	 * 
	 */

	private static Pattern pattern;
	private static Matcher matcher;
	// private static final String[] SMELLS =
	// new String[] { "AntiSingleton", "BaseClassKnowsDerivedClass",
	// "BaseClassShouldBeAbstract", "Blob",
	// "ClassDataShouldBePrivate", "ComplexClass",
	// "FunctionalDecomposition", "LargeClass", "LazyClass",
	// "LongMethod", "LongParameterList",
	// "ManyFieldAttributesButNotComplex", "MessageChains",
	// "RefusedParentBequest", "SpaghettiCode",
	// "SpeculativeGenerality", "SwissArmyKnife", "TraditionBreaker" };

	private static final String[] SMELLS = new String[] { "MessageChains",
			"RefusedParentBequest" };

	@SuppressWarnings("unused")
	private static final String[] CODE_SMELLS =
		new String[] { "ComplexClassOnlyDetection" };

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		// String dirName = "D:/Documents/Workspace/Software-downloads/test2";
		computeAntipatterns(args[0]);
		// computeCodeSmells(args[0]);
		System.out.println("Computation of smells completed !!!");

		// try {
		// new File("rsc/Eclipse" + args[0] + "/Stats").mkdir();
		// final PrintDefectsBugsResults evol =
		// new PrintDefectsBugsResults(
		// "D:/Software/Tests Workspace/foutsekh-program-BugDefects/rsc/Eclipse"
		// + args[0] + "/Smells/");
		// evol
		// .output3("D:/Software/Tests
		// Workspace/foutsekh-program-BugDefects/rsc/Eclipse"
		// + args[0] + "/Stats/");
		// }
		// catch (FileNotFoundException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }
		// catch (IOException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }

	}

	// fin test

	public static void computeAntipatterns(String args) {
		// String dirName =
		// "D:/Documents/Workspace/Software-downloads/eclipse-SDK-" + args
		// + "-win32/plugins";
		//		
		String dirName = "rsc/eclipse-SDK-" + args + "-win32/plugins";

		final String t =
			"org.eclipse.equinox(.[a-z]*)+|org.eclipse.core(.[a-z]*)+|org.eclipse.platform(.[a-z]*)+";
		List<String> files = new ArrayList<String>();

		// final SmellDetectionCaller caller = new SmellDetectionCaller();

		final CallSmellDetection detection = new CallSmellDetection();
		// /// tests

		// detection.extractJarsFromDir(dirName, files);

		// /////////////
		// commented for test.
		/*
		 * File dir = new File(dirName); String[] listfiles;
		 * 
		 * int i; //j = 0; listfiles = dir.list(); List jarfiles = new ArrayList<String>();
		 * 
		 * //String[] jarfiles = new String[listfiles.length]; for (i = 0; i <
		 * listfiles.length; i++) { if (listfiles[i].endsWith(".jar") == true) {
		 * jarfiles.add(dirName + "/" + listfiles[i]); //j++;
		 *  } }
		 */
		int i;
		List<String> jarfiles = detection.extractJarsFromDir(dirName, files);

		System.out.println(jarfiles.size());

		List<String> equinoxfiles = new ArrayList<String>();
		List<String> elsefiles = new ArrayList<String>();

		for (i = 0; i < jarfiles.size(); i++) {
			StringTokenizer st = new StringTokenizer((String) jarfiles.get(i));
			while (st.hasMoreTokens()) {

				pattern = Pattern.compile(t);
				matcher = pattern.matcher(st.nextToken());
				if (matcher.find()) {
					// equinoxfiles.add(matcher.group());
					System.out.println("Match found: " + matcher.group() + " "
							+ jarfiles.get(i));
					equinoxfiles.add((String) jarfiles.get(i));
				}
				else {

					elsefiles.add((String) jarfiles.get(i));
					// System.out.println("no match for me :"+jarfiles[i]);

				}

			}
		}
		System.out.println("equinoxfiles number: " + equinoxfiles.size());
		System.out.println("other files number: " + elsefiles.size());

		new File("rsc/EclipseICSE" + args + "/").mkdir();
		if (elsefiles.size() > 0) {
			for (i = 0; i < elsefiles.size(); i++) {

				new File("rsc/EclipseICSE" + args + "/Smells/").mkdir();
				analyseCodeLevelModelFromJAR(
					createArrayOfJars(equinoxfiles, elsefiles.get(i)),
					"Eclipse v " + args,
					"rsc/EclipseICSE" + args + "/Smells/",
					i);

			}
		}
		else {
			analyseCodeLevelModelFromJAR(
				createArrayOfJars(equinoxfiles),
				"Eclipse v " + args,
				"rsc/EclipseICSE" + args + "/Smells/",
				i);

		}

	}

	/*
	 * compute code smells from system
	 * 
	 * 
	 */

	public static void computeCodeSmells(String args) {
		// String dirName =
		// "D:/Documents/Workspace/Software-downloads/eclipse-SDK-" + args
		// + "-win32/plugins";

		String dirName = "rsc/eclipse-SDK-" + args + "-win32/plugins";

		final String t =
			"org.eclipse.equinox(.[a-z]*)+|org.eclipse.core(.[a-z]*)+|org.eclipse.platform(.[a-z]*)+";
		List<String> files = new ArrayList<String>();

		final CallSmellDetection detection = new CallSmellDetection();
		int i;
		List<String> jarfiles = detection.extractJarsFromDir(dirName, files);

		List<String> equinoxfiles = new ArrayList<String>();
		List<String> elsefiles = new ArrayList<String>();

		for (i = 0; i < jarfiles.size(); i++) {
			StringTokenizer st = new StringTokenizer((String) jarfiles.get(i));
			while (st.hasMoreTokens()) {

				pattern = Pattern.compile(t);
				matcher = pattern.matcher(st.nextToken());
				if (matcher.find()) {
					// equinoxfiles.add(matcher.group());
					System.out.println("Match found: " + matcher.group() + " "
							+ jarfiles.get(i));
					equinoxfiles.add((String) jarfiles.get(i));
				}
				else {

					elsefiles.add((String) jarfiles.get(i));
					// System.out.println("no match for me :"+jarfiles[i]);

				}

			}
		}

		new File("rsc/Eclipse" + args + "/").mkdir();
		if (elsefiles.size() > 0) {
			for (i = 0; i < elsefiles.size(); i++) {

				new File("rsc/Eclipse" + args + "/CodeSmells/").mkdir();
				analyseCodeLevelModelFromJAR(
					createArrayOfJars(equinoxfiles, elsefiles.get(i)),
					"Eclipse v " + args,
					"rsc/Eclipse" + args + "/CodeSmells/",
					i);

			}
		}
		else {
			analyseCodeLevelModelFromJAR(
				createArrayOfJars(equinoxfiles),
				"Eclipse v " + args,
				"rsc/Eclipse" + args + "/CodeSmells/",
				i);

		}

		System.out.println("Computation of smells completed !!!");
	}

	public static String[] createArrayOfJars(List<String> l, Object s) {
		String[] files = new String[l.size() + 1];
		for (int i = 0; i < l.size(); i++) {
			files[i] = (String) l.get(i);

		}
		files[l.size()] = (String) s;
		return files;
	}

	public static String[] createArrayOfJars(List<String> l) {
		String[] files = new String[l.size()];
		for (int i = 0; i < l.size(); i++) {
			files[i] = (String) l.get(i);

		}
		return files;
	}

	public final static void analyseCodeLevelModelFromJAR(
		final String[] someJARFiles,
		final String aName,
		final String anOutputDirectory,
		final int index) {

		System.out.print("Analysing ");
		System.out.print(aName);
		System.out.println("...");

		try {
			ICodeLevelModel codeLevelModel =
				Factory.getInstance().createCodeLevelModel(aName);
			codeLevelModel.create(new CompleteClassFileCreator(
				someJARFiles,
				true));

			final padl.statement.creator.classfiles.ConditionalModelAnnotator annotator =
				new padl.statement.creator.classfiles.ConditionalModelAnnotator(
					someJARFiles);
			codeLevelModel = (ICodeLevelModel) annotator.invoke(codeLevelModel);

			final padl.statement.creator.classfiles.LOCModelAnnotator annotator2 =
				new padl.statement.creator.classfiles.LOCModelAnnotator(
					someJARFiles);
			codeLevelModel =
				(ICodeLevelModel) annotator2.invoke(codeLevelModel);
			// in case of direct antipatterns we call the following

			analyseCodeLevelModel(
				aName,
				codeLevelModel,
				anOutputDirectory,
				index);

			// in case of direct codesmell we call the following
			// analyseCodeSmellInModel(
			// aName,
			// codeLevelModel,
			// anOutputDirectory,
			// index);

		}
		catch (final CreationException e) {
			e.printStackTrace(ProxyConsole.getInstance().errorOutput());
		}
		catch (final UnsupportedSourceModelException e) {
			e.printStackTrace(ProxyConsole.getInstance().errorOutput());
		}
	}

	public final static void analyseCodeLevelModel(
		final String aName,
		final ICodeLevelModel codeLevelModel,
		final String anOutputDirectory,
		int index) {

		try {
			final IIdiomLevelModel idiomLevelModel =
				(IIdiomLevelModel) new AACRelationshipsAnalysis()
					.invoke(codeLevelModel);

			for (int i = 0; i < CallSmellDetection.SMELLS.length; i++) {
				final String antipatternName = CallSmellDetection.SMELLS[i];

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
					anOutputDirectory + "DetectionResults" + index + " in "
							+ aName + " for " + antipatternName + ".ini";
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
		catch (UnsupportedSourceModelException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		}
	}

	/*
	 * Extractor of jarfiles from directories recursively
	 * 
	 */
	public List<String> extractJarsFromDir(String path, List<String> jarfiles) {
		final File pathFile = new File(path);
		final String[] subPaths = pathFile.list();
		for (int i = 0; i < subPaths.length; i++) {
			final String fileName = path + "/" + subPaths[i];
			final File file = new File(fileName);
			if (file.isDirectory()) {
				this.extractJarsFromDir(fileName, jarfiles);
			}
			else {

				if (fileName.endsWith(".jar") == true) {
					jarfiles.add(fileName);
					// j++;
					// System.out.println(fileName);
				}
			}

		}
		// System.out.println(jarfiles.size());

		return jarfiles;
	}

	/*
	 * Computing code smells independently from anti-patterns in systems
	 * 
	 */
	public final static void analyseCodeSmellInModel(
		final String aName,
		final ICodeLevelModel codeLevelModel,
		final String anOutputDirectory,
		int index) {

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
					anOutputDirectory + "DetectionResults" + index + " in "
							+ aName + " for " + detectioncodeSmell.getName()
							+ ".ini";
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

}
