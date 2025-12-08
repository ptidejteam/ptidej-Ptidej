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
package padl.creator.cppfile.antlr.test.all;

import org.junit.Assert;
import padl.creator.cppfile.antlr.CPPCreator;
import padl.creator.cppfile.antlr.test.CppPrimitive;
import padl.kernel.IClass;
import padl.kernel.ICodeLevelModel;
import padl.kernel.IConstructor;
import padl.kernel.IElement;
import padl.kernel.IField;
import padl.kernel.IFirstClassEntity;
import padl.kernel.IMethod;
import padl.kernel.exception.CreationException;
import padl.util.Util;
import util.lang.Modifier;

/**
 * @author robidose
 */
public class CpointTest extends CppPrimitive {
	private static IFirstClassEntity[] FirstClassEntities = null;
	private static IElement[] Elements = null;

	public CpointTest(String aName) {
		super(aName);
	}

	protected void setUp() {
		if (CpointTest.FirstClassEntities == null
				|| CpointTest.Elements == null) {
			final ICodeLevelModel codeLevelModel =
				CppPrimitive.getFactory().createCodeLevelModel("Cpoint.cpp");
			try {
				codeLevelModel
					.create(new CPPCreator(
						new String[] { "../PADL Creator C++ (ANTLR)/target/test-classes/Cpoint.cpp" }));
			}
			catch (final CreationException e) {
				e.printStackTrace();
			}

			CpointTest.FirstClassEntities =
				Util.getArrayOfTopLevelEntities(codeLevelModel);

			// All the element of the first class
			CpointTest.Elements =
				Util.getArrayOfElements(CpointTest.FirstClassEntities[0]);
		}
	}

	public void testClass() {
		//---------------------------------------
		Assert.assertEquals(
			"Class name",
			"CPoint",
			((IClass) CpointTest.FirstClassEntities[0]).getDisplayName());

		Assert.assertEquals(
			"Class type",
			Modifier.PUBLIC,
			((IClass) CpointTest.FirstClassEntities[0]).getVisibility());

	}

	public void testClassCpointConstructor() {
		//---------------------------------------
		Assert.assertEquals(
			"Constr Name",
			"CPoint",
			CpointTest.Elements[1].getDisplayName());

		Assert.assertEquals(
			"Constr Access",
			Modifier.PUBLIC,
			((IConstructor) CpointTest.Elements[1]).getVisibility());

		// TODO: Fix the bug!
		//	Assert.assertEquals(
		//		"Constr # Parameters",
		//		2,
		//		((IConstructor) TestCpoint.Elements[1]).listOfConstituents().size());

		//---------------------------------------
		Assert.assertEquals(
			"Constr Name",
			"afficher",
			CpointTest.Elements[2].getDisplayName());

		Assert.assertEquals(
			"Constr Access",
			Modifier.PUBLIC,
			((IConstructor) CpointTest.Elements[2]).getVisibility());

		// TODO: Fix the bug!
		//	Assert.assertEquals(
		//		"Constr # Parameters",
		//		0,
		//		((IConstructor) TestCpoint.Elements[2]).listOfConstituents().size());
	}

	public void testClassCpointMethod() {
		//---------------------------------------
		Assert.assertEquals(
			"Method Name",
			"afficher",
			CpointTest.Elements[2].getDisplayName());

		Assert.assertEquals(
			"Method Return Type",
			"void",
			((IMethod) CpointTest.Elements[2]).getDisplayReturnType());

		Assert.assertEquals(
			"Method Access",
			Modifier.PUBLIC,
			((IMethod) CpointTest.Elements[2]).getVisibility());

		Assert.assertEquals(
			"Method # Parameters",
			0,
			((IMethod) CpointTest.Elements[2]).getNumberOfConstituents());

		//---------------------------------------
		Assert.assertEquals(
			"Method Name",
			"calculer_origine",
			CpointTest.Elements[3].getDisplayName());

		Assert.assertEquals(
			"Method Return Type",
			"double",
			((IMethod) CpointTest.Elements[3]).getDisplayReturnType());

		Assert.assertEquals(
			"Method Access",
			Modifier.PUBLIC,
			((IMethod) CpointTest.Elements[3]).getVisibility());

		Assert.assertEquals(
			"Method # Parameters",
			0,
			((IMethod) CpointTest.Elements[3]).getNumberOfConstituents());

		//---------------------------------------
		Assert.assertEquals(
			"Method Name",
			"dephasage",
			CpointTest.Elements[4].getDisplayName());

		Assert.assertEquals(
			"Method Return Type",
			"void",
			((IMethod) CpointTest.Elements[4]).getDisplayReturnType());

		Assert.assertEquals(
			"Method Access",
			Modifier.PUBLIC,
			((IMethod) CpointTest.Elements[4]).getVisibility());

		Assert.assertEquals(
			"Method # Parameters",
			1,
			((IMethod) CpointTest.Elements[4]).getNumberOfConstituents());

		//---------------------------------------
		Assert.assertEquals(
			"Method Name",
			"ecrire",
			CpointTest.Elements[5].getDisplayName());

		Assert.assertEquals(
			"Method Return Type",
			"void",
			((IMethod) CpointTest.Elements[5]).getDisplayReturnType());

		Assert.assertEquals(
			"Method Access",
			Modifier.PUBLIC,
			((IMethod) CpointTest.Elements[5]).getVisibility());

		Assert.assertEquals(
			"Method # Parameters",
			2,
			((IMethod) CpointTest.Elements[5]).getNumberOfConstituents());

		//---------------------------------------
		Assert.assertEquals(
			"Method Name",
			"lire_x",
			CpointTest.Elements[6].getDisplayName());

		Assert.assertEquals(
			"Method Return Type",
			"double",
			((IMethod) CpointTest.Elements[6]).getDisplayReturnType());

		Assert.assertEquals(
			"Method Access",
			Modifier.PUBLIC,
			((IMethod) CpointTest.Elements[6]).getVisibility());

		Assert.assertEquals(
			"Method # Parameters",
			0,
			((IMethod) CpointTest.Elements[6]).getNumberOfConstituents());

		//---------------------------------------
		Assert.assertEquals(
			"Method Name",
			"lire_y",
			CpointTest.Elements[7].getDisplayName());

		Assert.assertEquals(
			"Method Return Type",
			"double",
			((IMethod) CpointTest.Elements[7]).getDisplayReturnType());

		Assert.assertEquals(
			"Method Access",
			Modifier.PUBLIC,
			((IMethod) CpointTest.Elements[7]).getVisibility());

		Assert.assertEquals(
			"Method # Parameters",
			0,
			((IMethod) CpointTest.Elements[7]).getNumberOfConstituents());
	}

	public void testClassCpointField() {

		//---------------------------------------
		Assert.assertEquals(
			"Field Name",
			"m_x",
			CpointTest.Elements[8].getDisplayName());

		Assert.assertEquals(
			"Field Type",
			"double",
			((IField) CpointTest.Elements[8]).getDisplayTypeName());

		Assert.assertEquals(
			"Field Access",
			Modifier.PRIVATE,
			((IField) CpointTest.Elements[8]).getVisibility());

		//---------------------------------------
		Assert.assertEquals(
			"Field Name",
			"m_y",
			CpointTest.Elements[9].getDisplayName());

		Assert.assertEquals(
			"Field Type",
			"double",
			((IField) CpointTest.Elements[9]).getDisplayTypeName());

		Assert.assertEquals(
			"Field Access",
			Modifier.PRIVATE,
			((IField) CpointTest.Elements[9]).getVisibility());

	}
}
