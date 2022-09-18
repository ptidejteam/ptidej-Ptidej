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
import padl.kernel.IMemberClass;
import padl.kernel.exception.CreationException;
import padl.util.Util;

/**
 * @author Yann-Gaël Guéhéneuc
 * @since  2006/02/08
 */
public class TestMemberEntities extends ClassFilePrimitive {
	private static IFirstClassEntity[] FirstClassEntities = null;

	public TestMemberEntities(final String aName) {
		super(aName);
	}
	protected void setUp() throws CreationException,
			UnsupportedSourceModelException {

		if (TestMemberEntities.FirstClassEntities == null) {
			final ICodeLevelModel codeLevelModel =
				ClassFilePrimitive.getFactory().createCodeLevelModel("");
			codeLevelModel
				.create(new CompleteClassFileCreator(
					new String[] {
							"../PADL Creator ClassFile Tests/rsc/ArgoUML/org/argouml/application/events/ArgoEventPump.class",
							"../PADL Creator ClassFile Tests/rsc/ArgoUML/org/argouml/application/events/ArgoEventPump$Pair.class" }));

			final IIdiomLevelModel idiomLevelModel =
				(IIdiomLevelModel) new AACRelationshipsAnalysis()
					.invoke(codeLevelModel);

			TestMemberEntities.FirstClassEntities =
				Util.getArrayOfTopLevelEntities(idiomLevelModel);
		}
	}
	public void testMemberEntities() {
		Assert.assertTrue(
			"Member class should have methods!",
			((IMemberClass) TestMemberEntities.FirstClassEntities[17]
				.getConstituentFromName("Pair")).getNumberOfConstituents() > 0);
	}
}
