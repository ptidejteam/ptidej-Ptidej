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
package padl.creator.classfile.test.method;

import org.junit.Assert;
import junit.framework.TestCase;
import padl.analysis.repository.AACRelationshipsAnalysis;
import padl.creator.classfile.CompleteClassFileCreator;
import padl.kernel.IClass;
import padl.kernel.ICodeLevelModel;
import padl.kernel.IIdiomLevelModel;
import padl.kernel.impl.Factory;

/**
 * Test for methods wrongly included in an IClass
 * after being parsed by the PADL Creator ClassFile.
 * 
 * @author Stephane Vaucher
 * @author Yann-Gaël Guéhéneuc
 * @since  2005/11/11
 */
public class TestPrivateConstructor extends TestCase {
	private static IIdiomLevelModel IdiomLevelModel;
	public TestPrivateConstructor(final String aName) {
		super(aName);
	}
	public void setUp() throws Exception {
		if (TestPrivateConstructor.IdiomLevelModel == null) {
			final ICodeLevelModel codeLevelModel =
				Factory.getInstance().createCodeLevelModel("");
			codeLevelModel
				.create(new CompleteClassFileCreator(
					new String[] { "../PADL Analyses/target/test-classes/RestrictedCreation.class" }));

			TestPrivateConstructor.IdiomLevelModel =
				(IIdiomLevelModel) new AACRelationshipsAnalysis()
					.invoke(codeLevelModel);
		}
	}
	public void testNumberOfEntities() {
		Assert.assertEquals(
			"Number of entities",
			2,
			TestPrivateConstructor.IdiomLevelModel
				.getNumberOfTopLevelEntities());
	}
	public void testElementsOfRestrictedCreation() {
		final IClass clazz =
			(IClass) TestPrivateConstructor.IdiomLevelModel
				.getTopLevelEntityFromID("RestrictedCreation");
		Assert.assertEquals(
			"Number of elements",
			5,
			clazz.getNumberOfConstituents());
		Assert.assertNotNull(
			"Constructor",
			clazz.getConstituentFromID("<init>()"));
	}
}
