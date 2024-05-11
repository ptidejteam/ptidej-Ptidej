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
package padl.creator.javafile.eclipse.test.util;

import java.io.IOException;
import java.io.Writer;
import padl.analysis.UnsupportedSourceModelException;
import padl.creator.classfile.CompleteClassFileCreator;
import padl.creator.classfile.LightClassFileCreator;
import padl.creator.javafile.eclipse.CompleteJavaFileCreator;
import padl.creator.javafile.eclipse.LightJavaFileCreator;
import padl.event.IModelListener;
import padl.kernel.ICodeLevelModel;
import padl.kernel.exception.CreationException;
import padl.kernel.impl.Factory;
import padl.util.ModelStatistics;
import padl.visitor.repository.JavaGenerator;
import util.io.ProxyConsole;
import util.io.ProxyDisk;

public class Utils {
	/**
	 * create an idiom model (padl model with relationships from binaries)
	 * 
	 * @param aBinPath
	 * @return
	 */
	public static ICodeLevelModel createAnnotatedPadlClassModel(
		final String aBinPath) {

		ICodeLevelModel codeLevelModel =
			Factory.getInstance().createCodeLevelModel("");
		final ModelStatistics statisticModelListener = new ModelStatistics();
		codeLevelModel.addModelListener(statisticModelListener);

		try {
			codeLevelModel.create(new CompleteClassFileCreator(
				new String[] { aBinPath },
				true));

			final padl.statement.creator.classfiles.ConditionalModelAnnotator annotator =
				new padl.statement.creator.classfiles.ConditionalModelAnnotator(
					new String[] { aBinPath });
			final ICodeLevelModel annotatedCodeLevelModel =
				(ICodeLevelModel) annotator.invoke(codeLevelModel);

			final padl.statement.creator.classfiles.LOCModelAnnotator annotator2 =
				new padl.statement.creator.classfiles.LOCModelAnnotator(
					new String[] { aBinPath });

			codeLevelModel =
				(ICodeLevelModel) annotator2.invoke(annotatedCodeLevelModel);

			return codeLevelModel;

		}
		catch (final CreationException e) {
			e.printStackTrace();
		}
		catch (final UnsupportedSourceModelException e) {
			e.printStackTrace();
		}
		return null;

	}

	/**
	 * Creation from all the source code of a padl idiom model
	 * 
	 * @param aModeName
	 * @param aSourceCodePath
	 * @param aClassPathEntry
	 * @return
	 */
	public static ICodeLevelModel createCompleteAnnotatedJavaFilesPadlModel(
		final String aModelName,
		final String aSourceCodePath,
		final String aClassPathEntry) {

		final ICodeLevelModel model =
			Factory.getInstance().createCodeLevelModel(aModelName);

		try {
			model.create(new CompleteJavaFileCreator(
				aSourceCodePath,
				aClassPathEntry));

			return model;

		}
		catch (final CreationException e) {
			e.printStackTrace(ProxyConsole.getInstance().errorOutput());
		}

		return null;
	}

	/**
	 * Create a padl model from class files
	 * 
	 * @param aModelName
	 * @param aClassFilesFolderPath
	 * @return
	 */
	public static ICodeLevelModel createCompleteJavaClassesPadlModel(
		final String aModelName,
		final String[] someBinPath) {

		// Model from .class
		final ICodeLevelModel padlModelFromClassFiles =
			Factory.getInstance().createCodeLevelModel(aModelName);
		try {
			padlModelFromClassFiles.create(new CompleteClassFileCreator(
				someBinPath,
				true));
		}
		catch (final CreationException e) {
			e.printStackTrace(ProxyConsole.getInstance().errorOutput());
		}

		return padlModelFromClassFiles;
	}

	/**
	 * Creation from all the source code
	 * 
	 * @param aModeName
	 * @param aSourceCodePath
	 * @param aClassPathEntry
	 * @return
	 */
	public static ICodeLevelModel createCompleteJavaFilesPadlModel(
		final String aModelName,
		final String aSourceCodePath,
		final String aClassPathEntry) {

		final ICodeLevelModel model =
			Factory.getInstance().createCodeLevelModel(aModelName);

		try {
			model.create(new CompleteJavaFileCreator(
				aSourceCodePath,
				aClassPathEntry));
		}
		catch (final CreationException e) {
			e.printStackTrace(ProxyConsole.getInstance().errorOutput());
		}
		return model;
	}

	/**
	 * creation from some java files
	 * 
	 * @param aModeName
	 * @param aSourceCodePath
	 * @param aClassPathEntry
	 * @param aListOfFiles
	 * @return
	 */
	public static ICodeLevelModel createCompleteJavaFilesPadlModel(
		final String aModelName,
		final String aSourceCodePath,
		final String aClassPathEntry,
		final String[] aListOfFiles) {

		final ICodeLevelModel model =
			Factory.getInstance().createCodeLevelModel(aModelName);
		try {
			model.create(new CompleteJavaFileCreator(
				aSourceCodePath,
				aClassPathEntry,
				aListOfFiles));
		}
		catch (final CreationException e) {
			e.printStackTrace(ProxyConsole.getInstance().errorOutput());
		}
		return model;
	}

	/**
	 * Create a padl model from class files
	 * 
	 * @param aModelName
	 * @param aClassFilesFolderPath
	 * @return
	 */
	public static ICodeLevelModel createLightJavaClassesPadlModel(
		final String aModelName,
		final String aClassFilesFolderPath) {

		// Model from .class
		final ICodeLevelModel padlModelFromClassFiles =
			Factory.getInstance().createCodeLevelModel(aModelName);
		try {
			padlModelFromClassFiles.create(new LightClassFileCreator(
				new String[] { aClassFilesFolderPath },
				true));
		}
		catch (final CreationException e) {
			e.printStackTrace(ProxyConsole.getInstance().errorOutput());
		}

		return padlModelFromClassFiles;
	}

	/**
	 * Creation from all the source code
	 * 
	 * @param aModeName
	 * @param aSourceCodePath
	 * @param aClassPathEntry
	 * @return
	 */
	public static ICodeLevelModel createLightJavaFilesPadlModel(
		final String aModelName,
		final String aSourceCodePath,
		final String aClassPathEntry) {

		final ICodeLevelModel model =
			Factory.getInstance().createCodeLevelModel(aModelName);

		try {
			model.create(new LightJavaFileCreator(
				aSourceCodePath,
				aClassPathEntry));
		}
		catch (final CreationException e) {
			e.printStackTrace(ProxyConsole.getInstance().errorOutput());
		}
		return model;
	}

	/**
	 * creation from some java files
	 * 
	 * @param aModeName
	 * @param aSourceCodePath
	 * @param aClassPathEntry
	 * @param aListOfFiles
	 * @return
	 */
	public static ICodeLevelModel createLightJavaFilesPadlModel(
		final String aModelName,
		final String aSourceCodePath,
		final String aClassPathEntry,
		final String[] aListOfFiles) {

		final ICodeLevelModel model =
			Factory.getInstance().createCodeLevelModel(aModelName);
		try {
			model.create(new LightJavaFileCreator(
				aSourceCodePath,
				aClassPathEntry,
				aListOfFiles));
		}
		catch (final CreationException e) {
			e.printStackTrace(ProxyConsole.getInstance().errorOutput());
		}
		return model;
	}

	/**
	 * print information about the model with the statistic listener and the
	 * java generator
	 * 
	 * @param aStatisticListener
	 * @param model
	 */
	public static void printModelSummary(
		final IModelListener aStatisticListener,
		final ICodeLevelModel model,
		final String outputFile) {

		// create a file and print in this file the informations
		final StringBuffer pathBuffer = new StringBuffer();
		pathBuffer.append(outputFile);

		try {
			final Writer writer =
				ProxyDisk.getInstance().fileAbsoluteOutput(outputFile);
			writer.write("Summary for :\n");
			writer.write(model.getDisplayName());
			writer.write(aStatisticListener.toString());

			// Print the model by the generator
			final JavaGenerator javaGenerator = new JavaGenerator();
			model.generate(javaGenerator);

			writer.write(" result from the java generator");
			writer.write(javaGenerator.getResult().toString());
			writer.close();

			ProxyConsole
				.getInstance()
				.normalOutput()
				.println("Summary for :\n");
			ProxyConsole
				.getInstance()
				.normalOutput()
				.println(aStatisticListener.toString());
			ProxyConsole.getInstance().normalOutput().println();
			ProxyConsole
				.getInstance()
				.normalOutput()
				.println(" result from the java generator");
			ProxyConsole
				.getInstance()
				.normalOutput()
				.println(javaGenerator.getResult().toString());
		}
		catch (final IOException e) {
			e.printStackTrace(ProxyConsole.getInstance().errorOutput());
		}
	}
}
