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
package padl.creator.javafile.eclipse.helper;

import java.util.Arrays;
import padl.creator.javafile.eclipse.astVisitors.ConditionalModelAnnotator;
import padl.creator.javafile.eclipse.astVisitors.LOCModelAnnotator;
import padl.kernel.ICodeLevelModel;
import padl.kernel.ICodeLevelModelCreator;
import padl.kernel.exception.CreationException;
import parser.input.SourceInputsHolder;
import parser.input.impl.FileSystemJavaProject;
import parser.input.impl.FilesAndDirectoriesJavaProject;
import parser.wrapper.JavaParser;
import util.io.ProxyConsole;

public class ModelAnnotators implements ICodeLevelModelCreator {
	// TODO: What is this class for?
	
	private ICodeLevelModel codeLevelModel;
	private SourceInputsHolder javaProject;

	/**
	 * Constructor for parsing all the source code
	 * @param aSourcePathEntry
	 * @param aClasspathEntry
	 */
	public ModelAnnotators(
		final String aSourcePathEntry,
		final String aClasspathEntry) {

		//the folder of the source code to analyse well organized like a project
		//final String sourcePathEntry = "./rsc/src/";

		final String[] sourcePathEntries = new String[] { aSourcePathEntry };

		//using librairies?

		final String[] classpathEntries = new String[] { aClasspathEntry };

		try {
			this.javaProject =
				new FileSystemJavaProject(
					Arrays.asList(classpathEntries),
					Arrays.asList(sourcePathEntries));

		}
		catch (final Exception e) {
			e.printStackTrace(ProxyConsole.getInstance().errorOutput());
		}

		this.codeLevelModel = null;

	}

	/**
	 *  Constructor for parsing some java file
	 * @param aSourcePathEntry
	 * @param aClasspathEntry
	 * @param aPathFilesList
	 */
	public ModelAnnotators(
		final String aSourcePathEntry,
		final String aClasspathEntry,
		final String[] aPathFilesList) {

		//the folder of the source code to analyse well organized like a project

		final String[] sourcePathEntries = new String[] { aSourcePathEntry };

		//using librairies?

		final String[] classpathEntries = new String[] { aClasspathEntry };

		try {

			this.javaProject =
				new FilesAndDirectoriesJavaProject(
					Arrays.asList(classpathEntries),
					Arrays.asList(sourcePathEntries),
					Arrays.asList(aPathFilesList));

		}
		catch (final Exception e) {
			e.printStackTrace(ProxyConsole.getInstance().errorOutput());
		}

		this.codeLevelModel = null;
	}

	@Override
	public void create(final ICodeLevelModel aCodeLevelModel)
			throws CreationException {

		this.createModelFormSource(aCodeLevelModel, this.javaProject);

	}

	/**
	 * Creation of the model from a source code
	 * @param aCodeLevelModel
	 * @param aSourcePathEntry
	 * @param aClassPathEntry
	 * @return
	 */
	private void createModelFormSource(
		final ICodeLevelModel aCodeLevelModel,
		final SourceInputsHolder javaProject) {

		final JavaParser eclipseSourceCodeParser =
			new JavaParser(this.javaProject);

		final LOCModelAnnotator locAnnotator =
			new LOCModelAnnotator(aCodeLevelModel);

		eclipseSourceCodeParser.parse(locAnnotator);

		final ConditionalModelAnnotator conditionalAnnotator =
			new ConditionalModelAnnotator(aCodeLevelModel);

		eclipseSourceCodeParser.parse(conditionalAnnotator);

		this.codeLevelModel = aCodeLevelModel;

	}

	public ICodeLevelModel getCodeLevelModel() {
		return this.codeLevelModel;
	}
}
