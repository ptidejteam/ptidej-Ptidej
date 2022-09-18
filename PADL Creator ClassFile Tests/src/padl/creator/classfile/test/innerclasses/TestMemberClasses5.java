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
import padl.kernel.IField;
import padl.kernel.IFirstClassEntity;
import padl.kernel.IMemberClass;
import padl.kernel.IPackage;
import padl.kernel.exception.ModelDeclarationException;

/**
 * @author Yann-Gaël Guéhéneuc
 * @since  2011/01/03
 */
public class TestMemberClasses5 extends ClassFilePrimitive {
	private static ICodeLevelModel CodeLevelModel = null;

	public TestMemberClasses5(final String aName) {
		super(aName);
	}
	protected void setUp() {
		if (TestMemberClasses5.CodeLevelModel == null) {
			TestMemberClasses5.CodeLevelModel =
				ClassFilePrimitive.getFactory().createCodeLevelModel(
					"ptidej.example.innerclasses");

			final IPackage packaje =
				ClassFilePrimitive.getFactory().createPackage(
					Constants.DEFAULT_PACKAGE_ID);
			final IClass topLevelClass =
				ClassFilePrimitive.getFactory().createClass(
					"TopLevelClass".toCharArray(),
					"TopLevelClass".toCharArray());
			final IMemberClass memberClass =
				ClassFilePrimitive.getFactory().createMemberClass(
					"MemberClass".toCharArray(),
					"MemberClass".toCharArray());

			TestMemberClasses5.CodeLevelModel.addConstituent(packaje);
			packaje.addConstituent(topLevelClass);
			topLevelClass.addConstituent(memberClass);
		}
	}
	public void testMemberEntities() {
		//	final IConstituent constituent =
		//		((IContainer) TestMemberClasses4.CodeLevelModel
		//			.getConstituentFromID("toplevelclass".toCharArray()))
		//			.getConstituentFromName("MemberClass".toCharArray());
		final IFirstClassEntity topLevelClass =
			Utils.searchForEntity(
				TestMemberClasses5.CodeLevelModel,
				"TopLevelClass".toCharArray());
		final IField field =
			ClassFilePrimitive.getFactory().createField(
				"MemberClass".toCharArray(),
				"MemberClass".toCharArray(),
				"String".toCharArray(),
				Constants.CARDINALITY_ONE);
		try {
			topLevelClass.addConstituent(field);
		}
		catch (final ModelDeclarationException e) {
			Assert.assertTrue(true);
			return;
		}
		Assert.assertTrue(false);
	}
}
