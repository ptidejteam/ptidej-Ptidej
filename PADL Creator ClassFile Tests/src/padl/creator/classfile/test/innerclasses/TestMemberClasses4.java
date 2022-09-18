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
import padl.creator.classfile.test.ClassFilePrimitive;
import padl.creator.classfile.util.Utils;
import padl.kernel.Constants;
import padl.kernel.IClass;
import padl.kernel.ICodeLevelModel;
import padl.kernel.IConstituent;
import padl.kernel.IField;
import padl.kernel.IMemberClass;
import padl.kernel.IPackage;
import padl.kernel.exception.CreationException;

/**
 * @author Yann-Gaël Guéhéneuc
 * @since  2006/02/08
 */
public class TestMemberClasses4 extends ClassFilePrimitive {
	private static ICodeLevelModel CodeLevelModel = null;

	public TestMemberClasses4(final String aName) {
		super(aName);
	}
	protected void setUp() throws CreationException {
		if (TestMemberClasses4.CodeLevelModel == null) {
			TestMemberClasses4.CodeLevelModel =
				ClassFilePrimitive.getFactory().createCodeLevelModel(
					"ptidej.example.innerclasses");

			final IPackage packaje =
				ClassFilePrimitive.getFactory().createPackage(
					Constants.DEFAULT_PACKAGE_ID);
			// The test below works only because, historically,
			// the ID and name of top-level entities are identical!
			// TODO: distinguish ID and name for top-level entities.
			final IClass topLevelClass =
				ClassFilePrimitive.getFactory().createClass(
					"TopLevelClass".toCharArray(),
					"TopLevelClass".toCharArray());
			// PADL works best if a member class has for 
			// ID its fully-qualified name (JVM format).
			// TODO: Remove the unwritten constraint that a member class must of its fully-qualified JVM name as ID. 
			final IMemberClass memberClass =
				ClassFilePrimitive.getFactory().createMemberClass(
					"TopLevelClass$MemberClass".toCharArray(),
					"MemberClass".toCharArray());
			final IField field =
				ClassFilePrimitive.getFactory().createField(
					"MemberClass".toCharArray(),
					"MemberClass".toCharArray(),
					"String".toCharArray(),
					Constants.CARDINALITY_ONE);

			TestMemberClasses4.CodeLevelModel.addConstituent(packaje);
			packaje.addConstituent(topLevelClass);
			topLevelClass.addConstituent(memberClass);
			topLevelClass.addConstituent(field);
		}
	}
	public void testMemberEntities() {
		//	final IConstituent constituent =
		//		((IContainer) TestMemberClasses4.CodeLevelModel
		//			.getConstituentFromID("toplevelclass".toCharArray()))
		//			.getConstituentFromName("MemberClass".toCharArray());
		final IConstituent constituent =
			Utils.searchForEntity(
				TestMemberClasses4.CodeLevelModel,
				"TopLevelClass$MemberClass".toCharArray());
		Assert.assertTrue("", constituent instanceof IMemberClass);
	}
}
