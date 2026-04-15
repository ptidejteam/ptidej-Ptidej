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
public class MemberClasses3Test extends ClassFilePrimitive {
	private static IFirstClassEntity[] FirstClassEntities = null;

	public MemberClasses3Test(final String aName) {
		super(aName);
	}
	protected void setUp() throws CreationException {
		if (MemberClasses3Test.FirstClassEntities == null) {
			final ICodeLevelModel codeLevelModel =
				ClassFilePrimitive.getFactory().createCodeLevelModel(
					"ptidej.example.innerclasses");
			codeLevelModel
				.create(new CompleteClassFileCreator(
					new String[] { "../PADL Creator ClassFile/target/test-classes/Multiple Same-name Member Classes/" }));

			MemberClasses3Test.FirstClassEntities =
				Util.getArrayOfTopLevelEntities(codeLevelModel);
		}
	}
	public void testMemberEntities() {
		for (int i = 0; i < MemberClasses3Test.FirstClassEntities.length; i++) {
			Assert.assertTrue(
				"Member entity "
						+ MemberClasses3Test.FirstClassEntities[i]
							.getDisplayName() + " (" + i
						+ ") outside of its enclosing entity!",
				MemberClasses3Test.FirstClassEntities[i]
					.getDisplayName()
					.indexOf('$') == -1);
		}
	}
	public void testMemberEntitiesInheritance() {
		for (int i = 0; i < MemberClasses3Test.FirstClassEntities.length; i++) {
			if (MemberClasses3Test.FirstClassEntities[i].getDisplayID().equals(
				"java.util.Iterator")) {

				Assert.assertEquals(
					"Member entity "
							+ MemberClasses3Test.FirstClassEntities[i]
								.getDisplayName() + " (" + i
							+ ") inherits from the proper classes/interfaces!",
					2,
					((IGhost) MemberClasses3Test.FirstClassEntities[i])
						.getNumberOfInheritingEntities());
			}
		}
	}
}
