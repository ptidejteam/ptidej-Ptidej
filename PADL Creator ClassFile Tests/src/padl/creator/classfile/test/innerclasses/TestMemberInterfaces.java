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
import padl.kernel.IFirstClassEntity;
import padl.kernel.IIdiomLevelModel;
import padl.kernel.exception.CreationException;
import padl.util.Util;

/**
 * @author Yann-Gaël Guéhéneuc
 * @since  2005/08/14
 */
public class TestMemberInterfaces extends ClassFilePrimitive {
	private static IFirstClassEntity[] FirstClassEntities = null;

	public TestMemberInterfaces(final String aName) {
		super(aName);
	}
	protected void setUp() throws CreationException,
			UnsupportedSourceModelException {

		if (TestMemberInterfaces.FirstClassEntities == null) {
			final ICodeLevelModel codeLevelModel =
				ClassFilePrimitive.getFactory().createCodeLevelModel(
					"ptidej.example.innerclasses");
			codeLevelModel
				.create(new CompleteClassFileCreator(
					new String[] {
							"../PADL Creator ClassFile Tests/bin/padl/example/innerclasses/IA.class",
							"../PADL Creator ClassFile Tests/bin/padl/example/innerclasses/IA$IAMember.class",
							"../PADL Creator ClassFile Tests/bin/padl/example/innerclasses/B.class",
							"../PADL Creator ClassFile Tests/bin/padl/example/innerclasses/B$BMember.class",
							"../PADL Creator ClassFile Tests/bin/padl/example/innerclasses/C.class" }));

			final IIdiomLevelModel idiomLevelModel =
				(IIdiomLevelModel) new AACRelationshipsAnalysis()
					.invoke(codeLevelModel);

			TestMemberInterfaces.FirstClassEntities =
				Util.getArrayOfTopLevelEntities(idiomLevelModel);
		}
	}
	public void testClasses() {
		Assert.assertEquals(
			"Number of entities",
			7,
			TestMemberInterfaces.FirstClassEntities.length);
	}
}
