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
package padl.creator.javafile.eclipse.test.others;

import org.junit.Assert;
import junit.framework.TestCase;
import padl.analysis.UnsupportedSourceModelException;
import padl.analysis.repository.AACRelationshipsAnalysis;
import padl.creator.javafile.eclipse.test.util.Utils;
import padl.kernel.ICodeLevelModel;
import padl.kernel.ICreation;
import padl.kernel.IFirstClassEntity;
import padl.kernel.IIdiomLevelModel;

public class NesrineTest extends TestCase {
	public NesrineTest(final String name) {
		super(name);
	}

	public void test1erCas() {
		final String sourcePath =
			"../PADL Creator JavaFile (Eclipse) Tests/data/Nesrine's/1er Cas/";
		final String classPathEntry = "";

		try {
			final ICodeLevelModel codeLevelModel =
				Utils.createCompleteJavaFilesPadlModel(
					"",
					sourcePath,
					classPathEntry);

			final IIdiomLevelModel idiomLevelModel =
				(IIdiomLevelModel) new AACRelationshipsAnalysis()
					.invoke(codeLevelModel);

			final IFirstClassEntity clazz =
				idiomLevelModel
					.getTopLevelEntityFromID("galleryService.implementation.ImageServiceImpl");
			final ICreation creation =
				(ICreation) clazz.getConstituentFromID("addImage_>PADL<_2");
			Assert.assertNotNull(creation);
		}
		catch (final UnsupportedSourceModelException e) {
			e.printStackTrace();
		}
	}
	public void test2èmeCas() {
		final String sourcePath =
			"../PADL Creator JavaFile (Eclipse) Tests/data/Nesrine's/2ème Cas/";
		final String classPathEntry = "";

		try {
			final ICodeLevelModel codeLevelModel =
				Utils.createCompleteJavaFilesPadlModel(
					"",
					sourcePath,
					classPathEntry);

			final IIdiomLevelModel idiomLevelModel =
				(IIdiomLevelModel) new AACRelationshipsAnalysis()
					.invoke(codeLevelModel);

			Assert.assertNotNull(idiomLevelModel);
		}
		catch (final UnsupportedSourceModelException e) {
			e.printStackTrace();
		}
	}
	public void test3èmeCas() {
		final String sourcePath =
			"../PADL Creator JavaFile (Eclipse) Tests/data/Nesrine's/3ème Cas/";
		final String classPathEntry = "";

		try {
			final ICodeLevelModel codeLevelModel =
				Utils.createCompleteJavaFilesPadlModel(
					"",
					sourcePath,
					classPathEntry);

			final IIdiomLevelModel idiomLevelModel =
				(IIdiomLevelModel) new AACRelationshipsAnalysis()
					.invoke(codeLevelModel);

			final IFirstClassEntity clazz =
				idiomLevelModel
					.getTopLevelEntityFromID("com.mycompany.business.OrgManager");
			final ICreation creation =
				(ICreation) clazz.getConstituentFromID("OrgManager_>PADL<_2");
			Assert.assertNotNull(creation);
		}
		catch (final UnsupportedSourceModelException e) {
			e.printStackTrace();
		}
	}
}
