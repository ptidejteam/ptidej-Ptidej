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
import padl.kernel.IGhost;
import padl.kernel.IMethod;
import padl.kernel.IUseRelationship;
import padl.kernel.exception.CreationException;
import padl.util.Util;
import util.lang.Modifier;

/**
 * @author robidose
 */
public class Csegment2Test extends CppPrimitive {
	private static IFirstClassEntity[] FirstClassEntities = null;
	private static IElement[] Elements = null;

	public Csegment2Test(String aName) {
		super(aName);
	}

	protected void setUp() {

		if (Csegment2Test.FirstClassEntities == null
				|| Csegment2Test.Elements == null) {

			final ICodeLevelModel codeLevelModel =
				CppPrimitive.getFactory().createCodeLevelModel("csegment2.cpp");
			try {
				codeLevelModel
					.create(new CPPCreator(
						new String[] { "../PADL Creator C++ (ANTLR)/target/test-classes/csegment2.cpp" }));
			}
			catch (final CreationException e) {
				e.printStackTrace();
			}

			Csegment2Test.FirstClassEntities =
				Util.getArrayOfTopLevelEntities(codeLevelModel);

			// All the element of the first class
			Csegment2Test.Elements =
				Util.getArrayOfElements(Csegment2Test.FirstClassEntities[1]);
		}
	}

	public void testClass() {
		//---------------------------------------
		Assert.assertEquals(
			"Class name",
			"CSegment",
			((IClass) Csegment2Test.FirstClassEntities[1]).getDisplayName());

		Assert.assertEquals(
			"Class type",
			Modifier.PUBLIC,
			((IClass) Csegment2Test.FirstClassEntities[1]).getVisibility());

	}

	public void testGhost() {
		//---------------------------------------
		Assert.assertEquals(
			"Ghost name",
			"CPoint",
			((IGhost) Csegment2Test.FirstClassEntities[0]).getDisplayName());

		Assert.assertEquals(
			"Ghost type",
			Modifier.PUBLIC,
			((IGhost) Csegment2Test.FirstClassEntities[0]).getVisibility());

	}

	public void testClassCsegmentConstructor() {
		//---------------------------------------
		Assert.assertEquals(
			"Constr Name",
			"CSegment",
			Csegment2Test.Elements[0].getDisplayName());

		Assert.assertEquals(
			"Constr Access",
			Modifier.PUBLIC,
			((IConstructor) Csegment2Test.Elements[0]).getVisibility());

		final int num1 =
			((IConstructor) Csegment2Test.Elements[0])
				.getNumberOfConstituents();
		Assert.assertTrue("Constr # Parameters", num1 == 2 || num1 == 0);

		//---------------------------------------
		Assert.assertEquals(
			"Constr Name",
			"CSegment",
			Csegment2Test.Elements[1].getDisplayName());

		Assert.assertEquals(
			"Constr Access",
			Modifier.PUBLIC,
			((IConstructor) Csegment2Test.Elements[1]).getVisibility());

		final int num2 =
			((IConstructor) Csegment2Test.Elements[1])
				.getNumberOfConstituents();
		Assert.assertTrue("Constr # Parameters", num2 == 0 || num2 == 2);

	}

	public void testClassCpointMethod() {
		//---------------------------------------
		Assert.assertEquals(
			"Method Name",
			"afficher",
			Csegment2Test.Elements[2].getDisplayName());

		Assert.assertEquals(
			"Method Return Type",
			"void",
			((IMethod) Csegment2Test.Elements[2]).getDisplayReturnType());

		Assert.assertEquals(
			"Method Access",
			Modifier.PUBLIC,
			((IMethod) Csegment2Test.Elements[2]).getVisibility());

		Assert.assertEquals(
			"Method # Parameters",
			0,
			((IMethod) Csegment2Test.Elements[2]).getNumberOfConstituents());

		//---------------------------------------
		Assert.assertEquals(
			"Method Name",
			"calculer_longueur",
			Csegment2Test.Elements[3].getDisplayName());

		Assert.assertEquals(
			"Method Return Type",
			"double",
			((IMethod) Csegment2Test.Elements[3]).getDisplayReturnType());

		Assert.assertEquals(
			"Method Access",
			Modifier.PUBLIC,
			((IMethod) Csegment2Test.Elements[3]).getVisibility());

		Assert.assertEquals(
			"Method # Parameters",
			0,
			((IMethod) Csegment2Test.Elements[3]).getNumberOfConstituents());

		//---------------------------------------
		Assert.assertEquals(
			"Method Name",
			"dephasage",
			Csegment2Test.Elements[4].getDisplayName());

		Assert.assertEquals(
			"Method Return Type",
			"void",
			((IMethod) Csegment2Test.Elements[4]).getDisplayReturnType());

		Assert.assertEquals(
			"Method Access",
			Modifier.PUBLIC,
			((IMethod) Csegment2Test.Elements[4]).getVisibility());

		Assert.assertEquals(
			"Method # Parameters",
			1,
			((IMethod) Csegment2Test.Elements[4]).getNumberOfConstituents());

		//---------------------------------------
		Assert.assertEquals("Method Name", "ecrire", Csegment2Test.Elements[5]
			.getDisplayName());

		Assert.assertEquals(
			"Method Return Type",
			"void",
			((IMethod) Csegment2Test.Elements[5]).getDisplayReturnType());

		Assert.assertEquals(
			"Method Access",
			Modifier.PUBLIC,
			((IMethod) Csegment2Test.Elements[5]).getVisibility());

		Assert.assertEquals(
			"Method # Parameters",
			2,
			((IMethod) Csegment2Test.Elements[5]).getNumberOfConstituents());

		//---------------------------------------
		Assert.assertEquals("Method Name", "getp1", Csegment2Test.Elements[6]
			.getDisplayName());

		Assert.assertEquals(
			"Method Return Type",
			"CPoint",
			((IMethod) Csegment2Test.Elements[6]).getDisplayReturnType());

		Assert.assertEquals(
			"Method Access",
			Modifier.PUBLIC,
			((IMethod) Csegment2Test.Elements[6]).getVisibility());

		Assert.assertEquals(
			"Method # Parameters",
			0,
			((IMethod) Csegment2Test.Elements[6]).getNumberOfConstituents());

		//---------------------------------------
		Assert.assertEquals("Method Name", "getp2", Csegment2Test.Elements[7]
			.getDisplayName());

		Assert.assertEquals(
			"Method Return Type",
			"CPoint",
			((IMethod) Csegment2Test.Elements[7]).getDisplayReturnType());

		Assert.assertEquals(
			"Method Access",
			Modifier.PUBLIC,
			((IMethod) Csegment2Test.Elements[7]).getVisibility());

		Assert.assertEquals(
			"Method # Parameters",
			0,
			((IMethod) Csegment2Test.Elements[7]).getNumberOfConstituents());

	}

	public void testClassCpointField() {

		//---------------------------------------
		Assert.assertEquals("Field Name", "m_point1", Csegment2Test.Elements[8]
			.getDisplayName());

		Assert.assertEquals(
			"Field Type",
			"CPoint",
			((IField) Csegment2Test.Elements[8]).getDisplayTypeName());

		Assert.assertEquals(
			"Field Access",
			Modifier.PRIVATE,
			((IField) Csegment2Test.Elements[8]).getVisibility());

		//---------------------------------------
		Assert.assertEquals("Field Name", "m_point2", Csegment2Test.Elements[9]
			.getDisplayName());

		Assert.assertEquals(
			"Field Type",
			"CPoint",
			((IField) Csegment2Test.Elements[9]).getDisplayTypeName());

		Assert.assertEquals(
			"Field Access",
			Modifier.PRIVATE,
			((IField) Csegment2Test.Elements[9]).getVisibility());

	}

	public void testClassCpointRelationship() {
		Assert.assertEquals(
			"Relationship target name",
			"CPoint",
			((IUseRelationship) Csegment2Test.Elements[10])
				.getTargetEntity()
				.getDisplayName());
	}
}
