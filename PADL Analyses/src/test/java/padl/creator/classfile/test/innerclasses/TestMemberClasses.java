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
package padl.creator.classfile.test.innerclasses;

import org.junit.Assert;
import padl.analysis.UnsupportedSourceModelException;
import padl.analysis.repository.AACRelationshipsAnalysis;
import padl.creator.classfile.CompleteClassFileCreator;
import padl.creator.classfile.test.ClassFilePrimitive;
import padl.kernel.ICodeLevelModel;
import padl.kernel.IElement;
import padl.kernel.IFirstClassEntity;
import padl.kernel.IIdiomLevelModel;
import padl.kernel.exception.CreationException;
import padl.util.Util;

/**
 * @author Yann-Gaël Guéhéneuc
 * @since  2005/08/14
 */
public class TestMemberClasses extends ClassFilePrimitive {
	private static IElement[] Elements = null;
	private static IFirstClassEntity[] FirstClassEntities = null;

	public TestMemberClasses(final String aName) {
		super(aName);
	}
	protected void setUp() throws CreationException,
			UnsupportedSourceModelException {

		if (TestMemberClasses.FirstClassEntities == null
				|| TestMemberClasses.Elements == null) {

			final ICodeLevelModel codeLevelModel =
				ClassFilePrimitive.getFactory().createCodeLevelModel(
					"ptidej.example.innerclasses");
			codeLevelModel
				.create(new CompleteClassFileCreator(
					new String[] {
							"../PADL Creator ClassFile Tests/bin/padl/example/innerclasses/Member.class",
							"../PADL Creator ClassFile Tests/bin/padl/example/innerclasses/Member$B.class",
							"../PADL Creator ClassFile Tests/bin/padl/example/innerclasses/Anonymous$1B.class" }));

			final IIdiomLevelModel idiomLevelModel =
				(IIdiomLevelModel) new AACRelationshipsAnalysis()
					.invoke(codeLevelModel);

			TestMemberClasses.FirstClassEntities =
				Util.getArrayOfTopLevelEntities(idiomLevelModel);

			TestMemberClasses.Elements =
				Util
					.getArrayOfElements(TestMemberClasses.FirstClassEntities[2]);
		}
	}
	public void testClasses() {
		Assert.assertEquals(
			"Number of entities",
			3,
			TestMemberClasses.FirstClassEntities.length);
	}
	public void testMemberClass() {
		Assert.assertEquals(
			"Number of elements",
			3,
			TestMemberClasses.Elements.length);
	}
}
