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
package sad.detection.generators;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.LineNumberReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import padl.analysis.UnsupportedSourceModelException;
import padl.analysis.repository.AACRelationshipsAnalysis;
import padl.creator.aolfile.AOLCreator;
import padl.creator.classfile.CompleteClassFileCreator;
import padl.creator.javafile.eclipse.CompleteJavaFileCreator;
import padl.creator.javafile.javac.JavaFileCreator;
import padl.kernel.IAbstractLevelModel;
import padl.kernel.ICodeLevelModel;
import padl.kernel.IIdiomLevelModel;
import padl.kernel.exception.CreationException;
import padl.kernel.impl.Factory;
import ptidej.solver.Occurrence;
import ptidej.solver.OccurrenceBuilder;
import ptidej.solver.OccurrenceComponent;
import sad.designsmell.detection.IDesignSmellDetection;
import util.io.ProxyConsole;
import util.io.ProxyDisk;
import util.io.ReaderInputStream;

/**
 * @author Yann-Gaël Guéhéneuc
 * @since  2007/03/02
 * @author Naouel Moha
 * @since  2008/07/15
 * Modified to extract the name of classes and number of defects 
 * for the association between defects and bugs
 * 
 * TODO: This class should not exist and calls should be redirected to ModelGenerator!
 */
public final class SmellDetectionHelper {
	public static final String[] SMELLS = new String[] { "AntiSingleton",
			"BaseClassKnowsDerivedClass", "BaseClassShouldBeAbstract", "Blob",
			"ClassDataShouldBePrivate", "ComplexClass",
			"FunctionalDecomposition", "LargeClass", "LazyClass", "LongMethod",
			"LongParameterList", "ManyFieldAttributesButNotComplex",
			"MessageChains", "RefusedParentBequest", "SpaghettiCode",
			"SpeculativeGenerality", "SwissArmyKnife", "TraditionBreaker" };
	//	public static final String[] SMELLS = new String[] { "LongMethod",
	//			"SpaghettiCode" };

	private SmellDetectionHelper() {
	}

	public static void main(final String[] args) {
		try {
			final LineNumberReader reader =
				new LineNumberReader(ProxyDisk.getInstance().fileAbsoluteInput(
					"rsc/ArgoUML0198_Test.csv"));
			final List<String> listOfPaths = new ArrayList<String>();
			String line;
			while ((line = reader.readLine()) != null) {
				String className = line.substring(0, line.indexOf(';'));
				className = className.replace('.', '/');
				className =
					"D:/Software/P-MARt Workspace/ArgoUML v0.19.8/bin/"
							+ className;
				className += ".class";
				listOfPaths.add(className);
			}

			final String[] somePaths = new String[listOfPaths.size()];
			listOfPaths.toArray(somePaths);

			SmellDetectionHelper.analyseCodeLevelModelFromJavaClassFiles(
				somePaths,
				"ArgoUML",
				"rsc/ArgoUML v0.19.8 Abdou's SUBSET/");
		}
		catch (final FileNotFoundException e) {
			e.printStackTrace();
		}
		catch (final IOException e) {
			e.printStackTrace();
		}
	}

	public static final void analyseCodeLevelModel(
		final String[] someSmells,
		final String aName,
		final IAbstractLevelModel idiomLevelModel,
		final String anOutputDirectory) {

		try {
			for (int i = 0; i < someSmells.length; i++) {
				final String antipatternName = someSmells[i];

				final long startTime = System.currentTimeMillis();
				final Class<?> detectionClass =
					Class.forName("sad.designsmell.detection.repository."
							+ antipatternName + '.' + antipatternName
							+ "Detection");
				final IDesignSmellDetection detection =
					(IDesignSmellDetection) detectionClass.newInstance();

				detection.detect(idiomLevelModel);

				final String path =
					anOutputDirectory + "DetectionResults in " + aName
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

				//	this.extractClassesDefects(
				//		anOutputDirectory,
				//		aName,
				//		idiomLevelModel,
				//		antipatternName,
				//		properties,
				//		solutionBuilder);
			}
		}
		catch (final Exception e) {
			e.printStackTrace(ProxyConsole.getInstance().errorOutput());
			throw new RuntimeException(e);
		}
	}
	public static final void analyseCodeLevelModel(
		final String[] someSmells,
		final String aName,
		final ICodeLevelModel codeLevelModel,
		final String anOutputDirectory) {

		try {
			final IIdiomLevelModel idiomLevelModel =
				(IIdiomLevelModel) new AACRelationshipsAnalysis()
					.invoke(codeLevelModel);

			SmellDetectionHelper.analyseCodeLevelModel(
				someSmells,
				aName,
				idiomLevelModel,
				anOutputDirectory);
		}
		catch (final UnsupportedSourceModelException e) {
			e.printStackTrace(ProxyConsole.getInstance().errorOutput());
		}
	}
	public static final void analyseCodeLevelModelFromAOL(
		final String anAOLFile,
		final String aName,
		final String anOutputDirectory) {

		System.out.print("Analysing ");
		System.out.print(aName);
		System.out.println("...");

		try {
			final long startTime = System.currentTimeMillis();
			final ICodeLevelModel codeLevelModel =
				Factory.getInstance().createCodeLevelModel(aName);
			codeLevelModel.create(new AOLCreator(new String[] { anAOLFile }));

			padl.statement.creator.aol.ModelAnnotator annotator;
			ICodeLevelModel annotatedCodeLevelModel;
			annotator =
				new padl.statement.creator.aol.ConditionalModelAnnotator(
					new String[] { anAOLFile });
			annotatedCodeLevelModel =
				(ICodeLevelModel) annotator.invoke(codeLevelModel);
			annotator =
				new padl.statement.creator.aol.LOCModelAnnotator(
					new String[] { anAOLFile });
			annotatedCodeLevelModel =
				(ICodeLevelModel) annotator.invoke(annotatedCodeLevelModel);

			final long endTime = System.currentTimeMillis();
			System.out.print("Model built in ");
			System.out.print(endTime - startTime);
			System.out.println(" ms.");

			SmellDetectionHelper.analyseCodeLevelModel(
				SmellDetectionHelper.SMELLS,
				aName,
				annotatedCodeLevelModel,
				anOutputDirectory);
		}
		catch (final CreationException e) {
			e.printStackTrace(ProxyConsole.getInstance().errorOutput());
		}
		catch (final UnsupportedSourceModelException e) {
			e.printStackTrace(ProxyConsole.getInstance().errorOutput());
		}
	}
	public static final void analyseCodeLevelModelFromEclipse(
		final String anEclipsePath,
		final String anOutputDirectory) {

		final String pluginsPath = anEclipsePath + "plugins/";
		final File pluginsDir = new File(pluginsPath);
		final List<String> listOfJarFiles = new ArrayList<String>();
		final String[] pluginsList = pluginsDir.list();
		for (int i = 0; i < pluginsList.length; i++) {
			final String plugin = pluginsList[i];
			final File pluginFile = new File(pluginsPath + plugin);
			if (pluginFile.isDirectory()) {
				final String[] jarFiles = pluginFile.list(new FilenameFilter() {
					public boolean accept(final File dir, final String name) {
						return name.endsWith(".jar");
					}
				});

				for (int j = 0; j < jarFiles.length; j++) {
					final String jarPath =
						pluginsPath + plugin + '/' + jarFiles[j];
					listOfJarFiles.add(jarPath);
				}
			}
			else if (pluginFile.getAbsolutePath().endsWith(".jar")) {
				listOfJarFiles.add(pluginFile.getAbsolutePath());
			}
		}

		final String[] arrayOfJarFiles =
			(String[]) listOfJarFiles.toArray(new String[0]);
		SmellDetectionHelper.analyseCodeLevelModelFromJARs(
			arrayOfJarFiles,
			anEclipsePath,
			anOutputDirectory);
	}
	public static final void analyseCodeLevelModelFromJARs(
		final String[] someJARFiles,
		final String aName,
		final String anOutputDirectory) {

		System.out.print("Analysing ");
		System.out.print(aName);
		System.out.println("...");

		try {
			final long startTime = System.currentTimeMillis();
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

			final long endTime = System.currentTimeMillis();
			System.out.print("Model built in ");
			System.out.print(endTime - startTime);
			System.out.println(" ms.");

			SmellDetectionHelper.analyseCodeLevelModel(
				SmellDetectionHelper.SMELLS,
				aName,
				codeLevelModel,
				anOutputDirectory);
		}
		catch (final CreationException e) {
			e.printStackTrace(ProxyConsole.getInstance().errorOutput());
		}
		catch (final UnsupportedSourceModelException e) {
			e.printStackTrace(ProxyConsole.getInstance().errorOutput());
		}
	}
	public static final void analyseCodeLevelModelFromJavaClassFiles(
		final String aClassPath,
		final String aName,
		final String anOutputDirectoryName) {

		SmellDetectionHelper.analyseCodeLevelModelFromJavaClassFiles(
			SmellDetectionHelper.SMELLS,
			aClassPath,
			aName,
			anOutputDirectoryName);
	}

	public static final void analyseCodeLevelModelFromJavaClassFiles(
		final String[] somePaths,
		final String aName,
		final String anOutputDirectoryName) {

		SmellDetectionHelper.analyseCodeLevelModelFromJavaClassFiles(
			SmellDetectionHelper.SMELLS,
			somePaths,
			aName,
			anOutputDirectoryName);
	}
	public static final void analyseCodeLevelModelFromJavaClassFiles(
		final String[] someSmells,
		final String aClassPath,
		final String aName,
		final String anOutputDirectoryName) {

		System.out.print("Analysing ");
		System.out.print(aName);
		System.out.println("...");

		try {
			final long startTime = System.currentTimeMillis();
			final ICodeLevelModel codeLevelModel =
				Factory.getInstance().createCodeLevelModel(aName);
			codeLevelModel.create(new CompleteClassFileCreator(
				new String[] { aClassPath },
				true));

			//	final ModelAnnotatorLOC modelAnnotatorLOC = new ModelAnnotatorLOC();
			//	modelAnnotatorLOC.annotateFromDirs(
			//		new String[] { aClassPath },
			//		codeLevelModel);

			final padl.statement.creator.classfiles.ConditionalModelAnnotator annotator =
				new padl.statement.creator.classfiles.ConditionalModelAnnotator(
					new String[] { aClassPath });
			final ICodeLevelModel annotatedCodeLevelModel1 =
				(ICodeLevelModel) annotator.invoke(codeLevelModel);

			final padl.statement.creator.classfiles.LOCModelAnnotator annotator2 =
				new padl.statement.creator.classfiles.LOCModelAnnotator(
					new String[] { aClassPath });
			final ICodeLevelModel annotatedCodeLevelModel2 =
				(ICodeLevelModel) annotator2.invoke(annotatedCodeLevelModel1);

			// Create the output directory if needed.
			final String newOutputDirectoryName =
				anOutputDirectoryName + aName + File.separatorChar;

			// Yann 2013/05/30
			// Not necessary thanks to ProxyDisk
			//	final File newOutputDirectiory = new File(newOutputDirectoryName);
			//	if (!newOutputDirectiory.exists()) {
			//		newOutputDirectiory.mkdirs();
			//	}

			final long endTime = System.currentTimeMillis();
			System.out.print("Model built in ");
			System.out.print(endTime - startTime);
			System.out.println(" ms.");

			SmellDetectionHelper.analyseCodeLevelModel(
				someSmells,
				aName,
				annotatedCodeLevelModel2,
				newOutputDirectoryName);
		}
		catch (final SecurityException e) {
			e.printStackTrace(ProxyConsole.getInstance().errorOutput());
		}
		catch (final IllegalArgumentException e) {
			e.printStackTrace(ProxyConsole.getInstance().errorOutput());
		}
		catch (final CreationException e) {
			e.printStackTrace(ProxyConsole.getInstance().errorOutput());
		}
		catch (final UnsupportedSourceModelException e) {
			e.printStackTrace(ProxyConsole.getInstance().errorOutput());
		}
	}
	public static final void analyseCodeLevelModelFromJavaClassFiles(
		final String[] someSmells,
		final String[] somePaths,
		final String aName,
		final String anOutputDirectoryName) {

		System.out.print("Analysing ");
		System.out.print(aName);
		System.out.println("...");

		try {
			final long startTime = System.currentTimeMillis();
			final ICodeLevelModel codeLevelModel =
				Factory.getInstance().createCodeLevelModel(aName);
			codeLevelModel
				.create(new CompleteClassFileCreator(somePaths, true));

			//	final ModelAnnotatorLOC modelAnnotatorLOC = new ModelAnnotatorLOC();
			//	modelAnnotatorLOC.annotateFromDirs(
			//		new String[] { aClassPath },
			//		codeLevelModel);

			final padl.statement.creator.classfiles.ConditionalModelAnnotator annotator =
				new padl.statement.creator.classfiles.ConditionalModelAnnotator(
					somePaths);
			final ICodeLevelModel annotatedCodeLevelModel1 =
				(ICodeLevelModel) annotator.invoke(codeLevelModel);

			final padl.statement.creator.classfiles.LOCModelAnnotator annotator2 =
				new padl.statement.creator.classfiles.LOCModelAnnotator(
					somePaths);
			final ICodeLevelModel annotatedCodeLevelModel2 =
				(ICodeLevelModel) annotator2.invoke(annotatedCodeLevelModel1);

			// Create the output directory if needed.
			final String newOutputDirectoryName =
				anOutputDirectoryName + aName + File.separatorChar;

			// Yann 2013/05/30
			// Not necessary thanks to ProxyDisk
			//	final File newOutputDirectiory = new File(newOutputDirectoryName);
			//	if (!newOutputDirectiory.exists()) {
			//		newOutputDirectiory.mkdirs();
			//	}

			final long endTime = System.currentTimeMillis();
			System.out.print("Model built in ");
			System.out.print(endTime - startTime);
			System.out.println(" ms.");

			SmellDetectionHelper.analyseCodeLevelModel(
				someSmells,
				aName,
				annotatedCodeLevelModel2,
				newOutputDirectoryName);
		}
		catch (final SecurityException e) {
			e.printStackTrace(ProxyConsole.getInstance().errorOutput());
		}
		catch (final IllegalArgumentException e) {
			e.printStackTrace(ProxyConsole.getInstance().errorOutput());
		}
		catch (final CreationException e) {
			e.printStackTrace(ProxyConsole.getInstance().errorOutput());
		}
		catch (final UnsupportedSourceModelException e) {
			e.printStackTrace(ProxyConsole.getInstance().errorOutput());
		}
	}

	public static final void analyseCodeLevelModelFromJavaSourceFilesEclipse(
		final String aSourcePath,
		final String aName,
		final String anOutputDirectoryName) {

		SmellDetectionHelper.analyseCodeLevelModelFromJavaSourceFilesEclipse(
			SmellDetectionHelper.SMELLS,
			aSourcePath,
			aName,
			anOutputDirectoryName);
	}
	public static final void analyseCodeLevelModelFromJavaSourceFilesEclipse(
		final String[] someSmells,
		final String aSourcePath,
		final String aName,
		final String anOutputDirectoryName) {

		SmellDetectionHelper.analyseCodeLevelModelFromJavaSourceFilesEclipse(
			SmellDetectionHelper.SMELLS,
			new String[] { aSourcePath },
			new String[] { aSourcePath },
			aName,
			anOutputDirectoryName);
	}
	public static final void analyseCodeLevelModelFromJavaSourceFilesEclipse(
		final String[] someSmells,
		final String[] someSourceRootPaths,
		final String[] someSourceFilePaths,
		final String aName,
		final String anOutputDirectoryName) {

		System.out.print("Analysing ");
		System.out.print(aName);
		System.out.println("...");

		//	Output.getInstance().setNormalOutput(new PrintWriter(System.out));
		//	Output.getInstance().setDebugOutput(new PrintWriter(System.out));
		//	Output.getInstance().setErrorOutput(new PrintWriter(System.err));

		try {
			final long startTime = System.currentTimeMillis();
			final CompleteJavaFileCreator creator =
				new CompleteJavaFileCreator(
					someSourceRootPaths,
					new String[] { "" },
					someSourceFilePaths);
			final ICodeLevelModel codeLevelModel =
				Factory.getInstance().createCodeLevelModel(aName);
			codeLevelModel.create(creator);
			final long endTime = System.currentTimeMillis();
			System.out.print("Model built in ");
			System.out.print(endTime - startTime);
			System.out.println(" ms.");
			System.out.print("Model contains ");
			System.out.print(codeLevelModel.getNumberOfTopLevelEntities());
			System.out.println(" top-level entities.");

			//	try {
			final padl.creator.javafile.eclipse.astVisitors.LOCModelAnnotator annotator2 =
				new padl.creator.javafile.eclipse.astVisitors.LOCModelAnnotator(
					codeLevelModel);
			creator.applyAnnotator(annotator2);
			//	}
			//	catch (final UnsupportedSourceModelException e) {
			//		e.printStackTrace();
			//	}

			//	try {
			final padl.creator.javafile.eclipse.astVisitors.ConditionalModelAnnotator annotator1 =
				new padl.creator.javafile.eclipse.astVisitors.ConditionalModelAnnotator(
					codeLevelModel);
			creator.applyAnnotator(annotator1);
			//	}
			//	catch (final UnsupportedSourceModelException e) {
			//		e.printStackTrace();
			//	}

			// Create the output directory if needed.
			final String newOutputDirectoryName =
				anOutputDirectoryName + aName + File.separatorChar;

			// Yann 2013/05/30
			// Not necessary thanks to ProxyDisk
			//	final File newOutputDirectiory = new File(newOutputDirectoryName);
			//	if (!newOutputDirectiory.exists()) {
			//		newOutputDirectiory.mkdirs();
			//	}

			SmellDetectionHelper.analyseCodeLevelModel(
				someSmells,
				aName,
				codeLevelModel,
				newOutputDirectoryName);
		}
		catch (final SecurityException e) {
			e.printStackTrace(ProxyConsole.getInstance().errorOutput());
		}
		catch (final IllegalArgumentException e) {
			e.printStackTrace(ProxyConsole.getInstance().errorOutput());
		}
		catch (final CreationException e) {
			e.printStackTrace(ProxyConsole.getInstance().errorOutput());
		}
		//	catch (final UnsupportedSourceModelException e) {
		//		e.printStackTrace(Output.getInstance().errorOutput());
		//	}
	}
	public static final void analyseCodeLevelModelFromJavaSourceFilesJCT(
		final String aSourcePath,
		final String aName,
		final String anOutputDirectoryName) {

		SmellDetectionHelper.analyseCodeLevelModelFromJavaSourceFilesJCT(
			SmellDetectionHelper.SMELLS,
			aSourcePath,
			aName,
			anOutputDirectoryName);
	}

	public static final void analyseCodeLevelModelFromJavaSourceFilesJCT(
		final String[] someSmells,
		final String aSourcePath,
		final String aName,
		final String anOutputDirectoryName) {

		System.out.print("Analysing ");
		System.out.print(aName);
		System.out.println("...");

		//	Output.getInstance().setNormalOutput(new PrintWriter(System.out));
		//	Output.getInstance().setDebugOutput(new PrintWriter(System.out));
		//	Output.getInstance().setErrorOutput(new PrintWriter(System.err));

		try {
			final long startTime = System.currentTimeMillis();
			final ICodeLevelModel codeLevelModel =
				Factory.getInstance().createCodeLevelModel(aName);
			codeLevelModel.create(new JavaFileCreator(aSourcePath));
			final long endTime = System.currentTimeMillis();
			System.out.print("Model built in ");
			System.out.print(endTime - startTime);
			System.out.println(" ms.");

			//	final ModelAnnotatorLOC modelAnnotatorLOC = new ModelAnnotatorLOC();
			//	modelAnnotatorLOC.annotateFromDirs(
			//		new String[] { aClassPath },
			//		codeLevelModel);

			//	final ConditionalModelAnnotator annotator =
			//		new ConditionalModelAnnotator(new String[] { aSourcePath });
			//	final ICodeLevelModel annotatedCodeLevelModel1 =
			//		(ICodeLevelModel) annotator.invoke(codeLevelModel);

			//	final LOCModelAnnotator annotator2 =
			//		new LOCModelAnnotator(new String[] { aSourcePath });
			//	final ICodeLevelModel annotatedCodeLevelModel2 =
			//		(ICodeLevelModel) annotator2.invoke(annotatedCodeLevelModel1);

			// Create the output directory if needed.
			final String newOutputDirectoryName =
				anOutputDirectoryName + aName + File.separatorChar;

			// Yann 2013/05/30
			// Not necessary thanks to ProxyDisk
			//	final File newOutputDirectiory = new File(newOutputDirectoryName);
			//	if (!newOutputDirectiory.exists()) {
			//		newOutputDirectiory.mkdirs();
			//	}

			SmellDetectionHelper.analyseCodeLevelModel(
				someSmells,
				aName,
				codeLevelModel,
				newOutputDirectoryName);
		}
		catch (final SecurityException e) {
			e.printStackTrace(ProxyConsole.getInstance().errorOutput());
		}
		catch (final IllegalArgumentException e) {
			e.printStackTrace(ProxyConsole.getInstance().errorOutput());
		}
		catch (final CreationException e) {
			e.printStackTrace(ProxyConsole.getInstance().errorOutput());
		}
	}

	public void extractClassesDefects(
		final String anOutputDirectory,
		final String aName,
		final IIdiomLevelModel idiomLevelModel,
		final String codesmellName,
		final Properties properties,
		final OccurrenceBuilder solutionBuilder) throws IOException {

		final String path2 =
			anOutputDirectory + "Classes Detected in " + aName + " for "
					+ codesmellName + ".csv";
		final PrintWriter w =
			new PrintWriter(ProxyDisk.getInstance().fileTempOutput(path2));

		final Occurrence[] allOccurrences =
			solutionBuilder.getAllOccurrences(properties);
		final int nbAllOcc = allOccurrences.length;
		for (int j = 0; j < nbAllOcc; j++) {
			final Occurrence occ = allOccurrences[j];
			@SuppressWarnings("unchecked")
			final List<OccurrenceComponent> listOccComponents =
				(List<OccurrenceComponent>) occ.getComponents();
			if (!listOccComponents.isEmpty()) {
				final OccurrenceComponent solutionComponent =
					(OccurrenceComponent) listOccComponents.get(0);
				w.println(solutionComponent.getValue());
			}
		}
		w.close();
	}
}
