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
package padl.generator.helper;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import padl.analysis.UnsupportedSourceModelException;
import padl.analysis.repository.AACRelationshipsAnalysis;
import padl.cpp.kernel.impl.CPPFactoryEclipse;
import padl.creator.aolfile.AOLCreator;
import padl.creator.classfile.CompleteClassFileCreator;
import padl.creator.msefile.MSECreator;
import padl.event.IModelListener;
import padl.kernel.ICodeLevelModel;
import padl.kernel.ICodeLevelModelCreator;
import padl.kernel.IIdiomLevelModel;
import padl.kernel.exception.CreationException;
import padl.kernel.impl.Factory;
import util.io.ProxyConsole;

public final class ModelGenerator {
	private static final String OUTPUT = "kind=\"output\" path=\"";

	private static void extractFilesFromDir(
		final String aPath,
		final String anExtension,
		final List aListOfFiles) {

		final File pathFile = new File(aPath);
		final String[] subPaths = pathFile.list();
		if (subPaths != null) {
			for (int i = 0; i < subPaths.length; i++) {
				final String fileName = aPath + '/' + subPaths[i];
				final File file = new File(fileName);
				if (file.isDirectory()) {
					ModelGenerator.extractFilesFromDir(
						fileName,
						anExtension,
						aListOfFiles);
				}
				else {
					if (// fileName.indexOf("org.eclipse.") > 0 && 
					fileName.endsWith(anExtension)) {
						aListOfFiles.add(fileName);
					}
				}
			}
		}
		else {
			throw new RuntimeException(new CreationException(
				"No subdirectories with JAR files in " + aPath));
		}
	}
	private static IIdiomLevelModel finishModelCreation(
		final ICodeLevelModel aCodeLevelModel) {

		// TODO Annotate the model with LOC and conditionals?

		IIdiomLevelModel idiomLevelModel = null;
		try {
			idiomLevelModel =
				(IIdiomLevelModel) new AACRelationshipsAnalysis()
					.invoke(aCodeLevelModel);
		}
		catch (final UnsupportedSourceModelException e) {
			e.printStackTrace(ProxyConsole.getInstance().errorOutput());
		}
		return idiomLevelModel;
	}
	public static ICodeLevelModel generateModelFromAOLCodeFile(
		final String aName,
		String anAOLFile,
		final IModelListener aModelListener) {

		return ModelGenerator.generateModelFromAOLCodeFiles(
			aName,
			new String[] { anAOLFile },
			aModelListener);
	}
	public static ICodeLevelModel generateModelFromAOLCodeFiles(
		final String aName,
		final String[] someAOLFiles) {

		return ModelGenerator.generateModelFromAOLCodeFiles(
			aName,
			someAOLFiles,
			null);
	}
	public static ICodeLevelModel generateModelFromAOLCodeFiles(
		final String aName,
		final String[] someAOLFiles,
		final IModelListener aModelListener) {

		final ICodeLevelModel codeLevelModel =
			Factory.getInstance().createCodeLevelModel(aName);
		if (aModelListener != null) {
			codeLevelModel.addModelListener(aModelListener);
		}
		final AOLCreator aolCreator = new AOLCreator(someAOLFiles);
		aolCreator.create(codeLevelModel, true);

		// TODO Add MethodInvocationAnalyser?

		padl.statement.creator.aol.ModelAnnotator annotator;
		ICodeLevelModel annotatedCodeLevelModel = null;
		try {
			annotator =
				new padl.statement.creator.aol.ConditionalModelAnnotator(
					someAOLFiles);
			annotatedCodeLevelModel =
				(ICodeLevelModel) annotator.invoke(codeLevelModel);
		}
		catch (final UnsupportedSourceModelException e) {
			e.printStackTrace(ProxyConsole.getInstance().errorOutput());
		}
		try {
			annotator =
				new padl.statement.creator.aol.LOCModelAnnotator(someAOLFiles);
			annotatedCodeLevelModel =
				(ICodeLevelModel) annotator.invoke(annotatedCodeLevelModel);
		}
		catch (final UnsupportedSourceModelException e) {
			e.printStackTrace(ProxyConsole.getInstance().errorOutput());
		}

		return codeLevelModel;
	}
	public static IIdiomLevelModel generateModelFromAOLDesignFiles(
		final String aName,
		final String[] someAOLFiles) {

		return ModelGenerator.generateModelFromAOLDesignFiles(
			aName,
			someAOLFiles,
			null);
	}

	public static IIdiomLevelModel generateModelFromAOLDesignFiles(
		final String aName,
		final String[] someAOLFiles,
		final IModelListener aModelListener) {

		final ICodeLevelModel codeLevelModel =
			ModelGenerator.generateModelFromAOLCodeFiles(
				aName,
				someAOLFiles,
				aModelListener);

		IIdiomLevelModel idiomLevelModel = null;
		try {
			idiomLevelModel =
				(IIdiomLevelModel) new AACRelationshipsAnalysis()
					.invoke(codeLevelModel);
		}
		catch (final UnsupportedSourceModelException e) {
			e.printStackTrace(ProxyConsole.getInstance().errorOutput());
		}
		return idiomLevelModel;
	}
	public static IIdiomLevelModel generateModelFromAspectJLSTFiles(
		final String aName,
		final String[] someAspectJLSTFiles) {

		throw new RuntimeException(new CreationException(
			"Version collision between aspectj.jar and other OSGi uses"));
	}
	public static IIdiomLevelModel generateModelFromClassFilesDirectories(
		final String aName,
		final String aPath,
		final IModelListener aModelListener) {

		return ModelGenerator.generateModelFromClassFilesDirectories(
			aName,
			new String[] { aPath },
			aModelListener);
	}
	public static IIdiomLevelModel generateModelFromClassFilesDirectories(
		final String aName,
		final String[] someSources) {

		return ModelGenerator.generateModelFromClassFilesDirectories(
			aName,
			someSources,
			null);
	}
	public static IIdiomLevelModel generateModelFromClassFilesDirectories(
		final String aName,
		final String[] someSources,
		final IModelListener aModelListener) {

		return ModelGenerator.generateModelFromDirectories(
			new CompleteClassFileCreator(someSources, true),
			aName,
			someSources,
			aModelListener);
	}
	public static IIdiomLevelModel generateModelFromClassFilesDirectories(
		final String[] someSources) {

		return ModelGenerator.generateModelFromDirectories(
			new CompleteClassFileCreator(someSources, true),
			someSources);
	}
	public static IIdiomLevelModel generateModelFromClassFilesDirectory(
		final String aPath) {

		return ModelGenerator.generateModelFromDirectory(
			new CompleteClassFileCreator(new String[] { aPath }, true),
			aPath);
	}
	public static IIdiomLevelModel generateModelFromClassFilesDirectory(
		final String aName,
		final String aPath) {

		return ModelGenerator.generateModelFromClassFilesDirectory(
			aName,
			aPath,
			null);
	}
	public static IIdiomLevelModel generateModelFromClassFilesDirectory(
		final String aName,
		final String aPath,
		final IModelListener aModelListener) {

		return ModelGenerator.generateModelFromDirectory(
			new CompleteClassFileCreator(new String[] { aPath }, true),
			aName,
			aPath,
			aModelListener);
	}
	public static IIdiomLevelModel generateModelFromCppFilesUsingANTLR(
		final String aName,
		final String[] fileNames,
		final IModelListener aModelListener) {

		ICodeLevelModel codeLevelModel = null;
		try {
			final ICodeLevelModelCreator creator =
				new padl.creator.cppfile.antlr.CPPCreator(fileNames);
			codeLevelModel = Factory.getInstance().createCodeLevelModel(aName);
			codeLevelModel.addModelListener(aModelListener);
			codeLevelModel.create(creator);
		}
		catch (final CreationException e) {
			e.printStackTrace(ProxyConsole.getInstance().errorOutput());
		}

		return ModelGenerator.finishModelCreation(codeLevelModel);
	}
	public static IIdiomLevelModel generateModelFromCppFilesUsingEclipse(
		final String aName,
		final String aSourceDirectory) {

		return ModelGenerator.generateModelFromCppFilesUsingEclipse(
			aName,
			aSourceDirectory,
			null);
	}
	public static IIdiomLevelModel generateModelFromCppFilesUsingEclipse(
		final String aName,
		final String aSourceDirectory,
		final IModelListener aModelListener) {

		ICodeLevelModel codeLevelModel = null;
		try {
			final ICodeLevelModelCreator creator =
				new padl.creator.cppfile.eclipse.CPPCreator(aSourceDirectory);
			codeLevelModel =
				CPPFactoryEclipse.getInstance().createCodeLevelModel(aName);
			if (aModelListener != null) {
				codeLevelModel.addModelListener(aModelListener);
			}
			codeLevelModel.create(creator);
		}
		catch (final CreationException e) {
			e.printStackTrace(ProxyConsole.getInstance().errorOutput());
		}

		return ModelGenerator.finishModelCreation(codeLevelModel);
	}
	public static IIdiomLevelModel generateModelFromCppFilesUsingEclipse(
		final String aName,
		final String[] fileNames) {

		return ModelGenerator.generateModelFromCppFilesUsingEclipse(
			aName,
			fileNames[0],
			null);
	}
	public static IIdiomLevelModel generateModelFromCSharpFilesV1(
		final String aName,
		final String[] someCSharpFiles,
		final IModelListener aModelListener) {

		ICodeLevelModel codeLevelModel = null;
		try {
			final ICodeLevelModelCreator creator =
				new padl.creator.csharpfile.v1.CSharpCreator(someCSharpFiles);
			codeLevelModel = Factory.getInstance().createCodeLevelModel(aName);
			if (aModelListener != null) {
				codeLevelModel.addModelListener(aModelListener);
			}
			codeLevelModel.create(creator);
		}
		catch (final CreationException e) {
			e.printStackTrace(ProxyConsole.getInstance().errorOutput());
		}

		return ModelGenerator.finishModelCreation(codeLevelModel);
	}
	public static IIdiomLevelModel generateModelFromCSharpFilesV2(
		final String aName,
		final String aSourceFileOrDirectory,
		final IModelListener aModelListener) {

		ICodeLevelModel codeLevelModel = null;
		try {
			final ICodeLevelModelCreator creator =
				new padl.creator.csharpfile.v2.CSharpCreator(
					aSourceFileOrDirectory);
			codeLevelModel = Factory.getInstance().createCodeLevelModel(aName);
			if (aModelListener != null) {
				codeLevelModel.addModelListener(aModelListener);
			}
			codeLevelModel.create(creator);
		}
		catch (final CreationException e) {
			e.printStackTrace(ProxyConsole.getInstance().errorOutput());
		}

		return ModelGenerator.finishModelCreation(codeLevelModel);
	}
	private static IIdiomLevelModel generateModelFromDirectories(
		final ICodeLevelModelCreator aCreator,
		final String aName,
		final String[] someSources,
		final IModelListener aModelListener) {

		ICodeLevelModel codeLevelModel =
			Factory.getInstance().createCodeLevelModel(aName);
		if (aModelListener != null) {
			codeLevelModel.addModelListener(aModelListener);
		}

		try {
			codeLevelModel.create(aCreator);

			final padl.statement.creator.classfiles.ConditionalModelAnnotator annotator =
				new padl.statement.creator.classfiles.ConditionalModelAnnotator(
					someSources);
			final ICodeLevelModel annotatedCodeLevelModel1 =
				(ICodeLevelModel) annotator.invoke(codeLevelModel);

			final padl.statement.creator.classfiles.LOCModelAnnotator annotator2 =
				new padl.statement.creator.classfiles.LOCModelAnnotator(
					someSources);
			codeLevelModel =
				(ICodeLevelModel) annotator2.invoke(annotatedCodeLevelModel1);

			final IIdiomLevelModel model =
				(IIdiomLevelModel) new AACRelationshipsAnalysis()
					.invoke(codeLevelModel);

			return model;
		}
		catch (final CreationException e) {
			e.printStackTrace(ProxyConsole.getInstance().errorOutput());
		}
		catch (final UnsupportedSourceModelException e) {
			e.printStackTrace(ProxyConsole.getInstance().errorOutput());
		}
		return null;
	}
	private static IIdiomLevelModel generateModelFromDirectories(
		final ICodeLevelModelCreator aCreator,
		final String[] someSources) {

		return ModelGenerator.generateModelFromDirectories(
			aCreator,
			"",
			someSources,
			null);
	}
	private static IIdiomLevelModel generateModelFromDirectory(
		final ICodeLevelModelCreator aCreator,
		final String aPath) {

		return ModelGenerator.generateModelFromDirectories(
			aCreator,
			"",
			new String[] { aPath },
			null);
	}
	private static IIdiomLevelModel generateModelFromDirectory(
		final ICodeLevelModelCreator aCreator,
		final String aName,
		final String aPath,
		final IModelListener aModelListener) {

		return ModelGenerator.generateModelFromDirectories(
			aCreator,
			aName,
			new String[] { aPath },
			aModelListener);
	}
	public static IIdiomLevelModel generateModelFromEclipseProject(
		final String aName,
		final String aPath,
		final IModelListener aModelListener) {

		// I find out the output directory 
		// and the list of JAR files (if any).
		// I am not using an XML parser but simple string matching!
		// TODO: Use a real XML parser.

		try {
			final List dirAndJARPathsList = new ArrayList();
			final File projectPathFile = new File(aPath);
			final String projectFiletPath = projectPathFile.getParent() + '/';
			final File classPathFile =
				new File(projectFiletPath + "/.classpath");
			final BufferedReader reader =
				new BufferedReader(new FileReader(classPathFile));
			String line;
			while ((line = reader.readLine()) != null) {
				int index;
				if ((index = line.indexOf(ModelGenerator.OUTPUT)) > -1) {
					dirAndJARPathsList.add(projectFiletPath
							+ line.substring(
								index + ModelGenerator.OUTPUT.length(),
								line.lastIndexOf("\"")) + '/');
				}
				//	else if (
				//		(index = line.indexOf(EclipseJDTCreator.LIB)) > -1) {
				//		dirAndJARPathsList.add(
				//			projectFiletPath
				//				+ line.substring(
				//					index + EclipseJDTCreator.LIB.length(),
				//					line.lastIndexOf("\"")));
				//	}
			}
			reader.close();

			final String[] dirAndJARPathsArray =
				new String[dirAndJARPathsList.size()];
			dirAndJARPathsList.toArray(dirAndJARPathsArray);

			final ICodeLevelModel codeLevelModel =
				Factory.getInstance().createCodeLevelModel(aName);
			if (aModelListener != null) {
				codeLevelModel.addModelListener(aModelListener);
			}
			final CompleteClassFileCreator classFileCreator =
				new CompleteClassFileCreator(dirAndJARPathsArray, true);
			codeLevelModel.create(classFileCreator);

			final IIdiomLevelModel model =
				(IIdiomLevelModel) new AACRelationshipsAnalysis()
					.invoke(codeLevelModel);

			return model;
		}
		catch (final FileNotFoundException e) {
			e.printStackTrace();
		}
		catch (final IOException e) {
			e.printStackTrace();
		}
		catch (final CreationException e) {
			e.printStackTrace();
		}
		catch (final UnsupportedSourceModelException e) {
			e.printStackTrace();
		}

		return null;
	}
	public static IIdiomLevelModel generateModelFromEclipseProject(
		final String aName,
		final String[] fileNames) {

		return ModelGenerator.generateModelFromEclipseProject(
			aName,
			fileNames[0],
			null);
	}
	public static IIdiomLevelModel generateModelFromJAR(final String aJARFile) {
		return ModelGenerator.generateModelFromJAR("", aJARFile, null);
	}
	public static IIdiomLevelModel generateModelFromJAR(
		final String aName,
		final String aJARFile) {

		return ModelGenerator.generateModelFromJAR(aName, aJARFile, null);
	}
	public static IIdiomLevelModel generateModelFromJAR(
		final String aName,
		final String aJARFile,
		final IModelListener aModelListener) {

		return ModelGenerator.generateModelFromClassFilesDirectories(
			aName,
			aJARFile,
			aModelListener);
	}
	public static IIdiomLevelModel generateModelFromJARs(
		final String aName,
		final String aPath,
		final IModelListener aModelListener) {

		final List jarFiles = new ArrayList();
		ModelGenerator.extractFilesFromDir(aPath, ".jar", jarFiles);

		final String[] listOfJARFiles = new String[jarFiles.size()];
		jarFiles.toArray(listOfJARFiles);
		return ModelGenerator.generateModelFromClassFilesDirectories(
			aName,
			listOfJARFiles,
			aModelListener);
	}
	public static IIdiomLevelModel generateModelFromJARs(
		final String aName,
		final String[] someJARs,
		final IModelListener aModelListener) {

		return ModelGenerator.generateModelFromClassFilesDirectories(
			aName,
			someJARs,
			aModelListener);
	}
	public static IIdiomLevelModel generateModelFromJARs(final String[] someJARs) {
		return ModelGenerator.generateModelFromClassFilesDirectories(
			"",
			someJARs,
			null);
	}
	public static IIdiomLevelModel generateModelFromJavaFilesDirectoriesUsingEclipse(
		final String aSourceRootPath) {

		return ModelGenerator.generateModelFromDirectories(
			new padl.creator.javafile.eclipse.CompleteJavaFileCreator(
				aSourceRootPath,
				""),
			new String[] { aSourceRootPath });
	}
	public static IIdiomLevelModel generateModelFromJavaFilesDirectoriesUsingEclipse(
		final String aName,
		final String aSourceRootPath,
		final String[] someSources,
		final IModelListener aModelListener) {

		return ModelGenerator.generateModelFromDirectories(
			new padl.creator.javafile.eclipse.CompleteJavaFileCreator(
				aSourceRootPath,
				"",
				someSources),
			aName,
			someSources,
			aModelListener);
	}
	public static IIdiomLevelModel generateModelFromJavaFilesDirectoriesUsingEclipse(
		final String aName,
		final String[] someSources) {

		final List javaFilesList = new ArrayList();
		for (int i = 0; i < someSources.length; i++) {
			final String path = someSources[i];
			ModelGenerator.extractFilesFromDir(path, ".java", javaFilesList);
		}
		final String[] javaFilesArray =
			(String[]) javaFilesList.toArray(new String[javaFilesList.size()]);

		return ModelGenerator.generateModelFromDirectories(
			new padl.creator.javafile.eclipse.CompleteJavaFileCreator(
				someSources,
				new String[0],
				javaFilesArray),
			aName,
			someSources,
			null);
	}
	public static IIdiomLevelModel generateModelFromJavaFilesDirectoryUsingEclipse(
		final String aPath) {

		return ModelGenerator
			.generateModelFromDirectory(
				new padl.creator.javafile.eclipse.CompleteJavaFileCreator(
					aPath,
					""),
				aPath);
	}
	public static IIdiomLevelModel generateModelFromJavaFilesDirectoryUsingEclipse(
		final String aName,
		final String aPath,
		final IModelListener aModelListener) {

		return ModelGenerator
			.generateModelFromDirectory(
				new padl.creator.javafile.eclipse.CompleteJavaFileCreator(
					aPath,
					""),
				aName,
				aPath,
				aModelListener);
	}
	public static IIdiomLevelModel generateModelFromJavaFilesDirectoryUsingJavaC(
		final String aName,
		final String aPath,
		final IModelListener aModelListener) {

		ICodeLevelModel codeLevelModel = null;
		try {
			final ICodeLevelModelCreator creator =
				new padl.creator.javafile.javac.JavaFileCreator(aPath);
			codeLevelModel = Factory.getInstance().createCodeLevelModel(aName);
			codeLevelModel.addModelListener(aModelListener);
			codeLevelModel.create(creator);
		}
		catch (final CreationException e) {
			e.printStackTrace(ProxyConsole.getInstance().errorOutput());
		}

		return ModelGenerator.finishModelCreation(codeLevelModel);
	}
	public static IIdiomLevelModel generateModelFromMSEFiles(
		final String aName,
		final String[] someFiles) {

		return ModelGenerator.generateModelFromMSEFiles(aName, someFiles, null);
	}
	public static IIdiomLevelModel generateModelFromMSEFiles(
		final String aName,
		final String[] someFiles,
		final IModelListener aModelListener) {

		ICodeLevelModel codeLevelModel = null;
		try {
			final ICodeLevelModelCreator creator = new MSECreator(someFiles);
			codeLevelModel = Factory.getInstance().createCodeLevelModel(aName);
			codeLevelModel.create(creator);
		}
		catch (final CreationException e) {
			e.printStackTrace(ProxyConsole.getInstance().errorOutput());
		}

		return ModelGenerator.finishModelCreation(codeLevelModel);
	}
}
