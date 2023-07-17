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
import padl.creator.classfile.CompleteClassFileCreator;
import padl.creator.classfile.test.ClassFilePrimitive;
import padl.kernel.ICodeLevelModel;
import padl.kernel.IFirstClassEntity;
import padl.kernel.IGhost;
import padl.kernel.exception.CreationException;
import padl.util.Util;

/**
 * @author Yann-Gaël Guéhéneuc
 * @since  2006/02/08
 */
public class TestMemberClasses3 extends ClassFilePrimitive {
	private static IFirstClassEntity[] FirstClassEntities = null;

	public TestMemberClasses3(final String aName) {
		super(aName);
	}
	protected void setUp() throws CreationException {
		if (TestMemberClasses3.FirstClassEntities == null) {
			final ICodeLevelModel codeLevelModel =
				ClassFilePrimitive.getFactory().createCodeLevelModel(
					"ptidej.example.innerclasses");
			codeLevelModel
				.create(new CompleteClassFileCreator(
					new String[] { "../PADL Creator ClassFile Tests/rsc/Multiple Same-name Member Classes/" }));

			TestMemberClasses3.FirstClassEntities =
				Util.getArrayOfTopLevelEntities(codeLevelModel);
		}
	}
	public void testMemberEntities() {
		for (int i = 0; i < TestMemberClasses3.FirstClassEntities.length; i++) {
			Assert.assertTrue(
				"Member entity "
						+ TestMemberClasses3.FirstClassEntities[i]
							.getDisplayName() + " (" + i
						+ ") outside of its enclosing entity!",
				TestMemberClasses3.FirstClassEntities[i]
					.getDisplayName()
					.indexOf('$') == -1);
		}
	}
	public void testMemberEntitiesInheritance() {
		for (int i = 0; i < TestMemberClasses3.FirstClassEntities.length; i++) {
			if (TestMemberClasses3.FirstClassEntities[i].getDisplayID().equals(
				"java.util.Iterator")) {

				Assert.assertEquals(
					"Member entity "
							+ TestMemberClasses3.FirstClassEntities[i]
								.getDisplayName() + " (" + i
							+ ") inherits from the proper classes/interfaces!",
					2,
					((IGhost) TestMemberClasses3.FirstClassEntities[i])
						.getNumberOfInheritingEntities());
			}
		}
	}
}
