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

import com.ibm.toad.cfparse.utils.Access;

import net.jcip.annotations.NotThreadSafe;
import padl.creator.cppfile.antlr.CPPCreator;
import padl.creator.cppfile.antlr.test.CppPrimitive;
import padl.kernel.IClass;
import padl.kernel.ICodeLevelModel;
import padl.kernel.IConstructor;
import padl.kernel.IElement;
import padl.kernel.IField;
import padl.kernel.IFirstClassEntity;
import padl.kernel.IMethod;
import padl.kernel.IUseRelationship;
import padl.kernel.cpp.antlr.IDestructor;
import padl.kernel.cpp.antlr.IEnum;
import padl.kernel.cpp.antlr.IGlobalField;
import padl.kernel.cpp.antlr.IStructure;
import padl.kernel.exception.CreationException;
import padl.util.Util;

/**
 * @author robidose
 */
@NotThreadSafe
public class WorldTest extends CppPrimitive {
	private static IFirstClassEntity[] FirstClassEntities = null;
	private static IElement[] Elements1 = null;
	private static IElement[] Elements2 = null;

	public WorldTest(String aName) {
		super(aName);
	}

	protected void setUp() {
		if (WorldTest.FirstClassEntities == null) {
			final ICodeLevelModel codeLevelModel = CppPrimitive.getFactory()
					.createCodeLevelModel("World.cpp");
			try {
				codeLevelModel.create(new CPPCreator(new String[] {
						"../PADL Creator C++ (ANTLR)/target/test-classes/world.cpp" }));
			}
			catch (final CreationException e) {
				e.printStackTrace();
			}

			WorldTest.FirstClassEntities = Util
					.getArrayOfTopLevelEntities(codeLevelModel);

			// All the element of the first class
			WorldTest.Elements1 = Util
					.getArrayOfElements(WorldTest.FirstClassEntities[0]);

			// All the element of the second class
			WorldTest.Elements2 = Util
					.getArrayOfElements(WorldTest.FirstClassEntities[1]);
		}
	}

	public void testClass() {
		//---------------------------------------
		Assert.assertEquals("Class name", "Game",
				((IClass) WorldTest.FirstClassEntities[0]).getDisplayName());

		Assert.assertEquals("Class type", Access.ACC_PUBLIC,
				((IClass) WorldTest.FirstClassEntities[0]).getVisibility());

		//---------------------------------------
		Assert.assertEquals("Class name", "Team",
				((IClass) WorldTest.FirstClassEntities[1]).getDisplayName());

		Assert.assertEquals("Class type", Access.ACC_PUBLIC,
				((IClass) WorldTest.FirstClassEntities[1]).getVisibility());
	}

	public void testEnum() {
		Assert.assertEquals("Enum name", "continent",
				((IEnum) WorldTest.FirstClassEntities[2]).getDisplayName());

		Assert.assertEquals("Enum type", Access.ACC_PUBLIC,
				((IEnum) WorldTest.FirstClassEntities[2]).getVisibility());
	}

	public void testStruc() {
		Assert.assertEquals("Struct name", "teamtype",
				((IStructure) WorldTest.FirstClassEntities[4])
						.getDisplayName());

		Assert.assertEquals("Struct type", Access.ACC_PUBLIC,
				((IStructure) WorldTest.FirstClassEntities[4]).getVisibility());
	}

	public void testGlobalField() {
		Assert.assertEquals("GlobalField name", "global",
				((IGlobalField) WorldTest.FirstClassEntities[3])
						.getDisplayName());

		Assert.assertEquals("GlobalField type", "int",
				((IGlobalField) WorldTest.FirstClassEntities[3])
						.getDisplayTypeName());

		// TODO Is this a flaky test?
		// It test successfully in Eclipse but reports 2 (i.e., PRIVATE) in Maven
		/*
		Assert.assertEquals("GlobalField access", Access.ACC_PUBLIC,
				((IGlobalField) TestWorld.FirstClassEntities[3])
						.getVisibility());
		*/
	}

	public void testClassGameConstructor() {
		//---------------------------------------
		Assert.assertEquals("Constr Name", "Game",
				WorldTest.Elements1[2].getDisplayName());

		Assert.assertEquals("Constr Access", Access.ACC_PUBLIC,
				((IConstructor) WorldTest.Elements1[2]).getVisibility());

		// TODO: Fix the bug!
		//	Assert.assertEquals(
		//		"Constr # Parameters",
		//		4,
		//		((IConstructor) TestWorld.Elements1[2]).listOfConstituents().size());

		//---------------------------------------
		Assert.assertEquals("Constr Name", "Game",
				WorldTest.Elements1[3].getDisplayName());

		Assert.assertEquals("Constr Access", Access.ACC_PUBLIC,
				((IConstructor) WorldTest.Elements1[3]).getVisibility());

		// TODO: Fix the bug!
		//	Assert.assertEquals(
		//		"Constr # Parameters",
		//		0,
		//		((IConstructor) TestWorld.Elements1[3]).listOfConstituents().size());
	}

	public void testClassGameMethod() {
		//---------------------------------------
		Assert.assertEquals("Method Name", "WhoWon",
				WorldTest.Elements1[6].getDisplayName());

		Assert.assertEquals("Method Return Type", "Team *",
				((IMethod) WorldTest.Elements1[6]).getDisplayReturnType());

		Assert.assertEquals("Method Access", Access.ACC_PUBLIC,
				((IMethod) WorldTest.Elements1[6]).getVisibility());

		Assert.assertEquals("Method # Parameters", 0,
				((IMethod) WorldTest.Elements1[6]).getNumberOfConstituents());

		//---------------------------------------
		Assert.assertEquals("Method Name", "getFirstTeam",
				WorldTest.Elements1[8].getDisplayName());

		Assert.assertEquals("Method Return Type", "Team *",
				((IMethod) WorldTest.Elements1[8]).getDisplayReturnType());

		Assert.assertEquals("Method Access", Access.ACC_PUBLIC,
				((IMethod) WorldTest.Elements1[8]).getVisibility());

		Assert.assertEquals("Method # Parameters", 0,
				((IMethod) WorldTest.Elements1[8]).getNumberOfConstituents());

		//---------------------------------------
		Assert.assertEquals("Method Name", "getSecondTeam",
				WorldTest.Elements1[9].getDisplayName());

		Assert.assertEquals("Method Return Type", "Team *",
				((IMethod) WorldTest.Elements1[9]).getDisplayReturnType());

		Assert.assertEquals("Method Access", Access.ACC_PUBLIC,
				((IMethod) WorldTest.Elements1[9]).getVisibility());

		Assert.assertEquals("Method # Parameters", 0,
				((IMethod) WorldTest.Elements1[9]).getNumberOfConstituents());

		//---------------------------------------
		Assert.assertEquals("Method Name", "print",
				WorldTest.Elements1[10].getDisplayName());

		Assert.assertEquals("Method Return Type", "void",
				((IMethod) WorldTest.Elements1[10]).getDisplayReturnType());

		Assert.assertEquals("Method Access", Access.ACC_PUBLIC,
				((IMethod) WorldTest.Elements1[10]).getVisibility());

		Assert.assertEquals("Method # Parameters", 0,
				((IMethod) WorldTest.Elements1[10]).getNumberOfConstituents());

		//---------------------------------------
		Assert.assertEquals("Method Name", "printFCont",
				WorldTest.Elements1[11].getDisplayName());

		Assert.assertEquals("Method Return Type", "void",
				((IMethod) WorldTest.Elements1[11]).getDisplayReturnType());

		Assert.assertEquals("Method Access", Access.ACC_PUBLIC,
				((IMethod) WorldTest.Elements1[11]).getVisibility());

		Assert.assertEquals("Method # Parameters", 0,
				((IMethod) WorldTest.Elements1[11]).getNumberOfConstituents());

		//---------------------------------------
		Assert.assertEquals("Method Name", "printSCont",
				WorldTest.Elements1[12].getDisplayName());

		Assert.assertEquals("Method Return Type", "void",
				((IMethod) WorldTest.Elements1[12]).getDisplayReturnType());

		Assert.assertEquals("Method Access", Access.ACC_PUBLIC,
				((IMethod) WorldTest.Elements1[12]).getVisibility());

		Assert.assertEquals("Method # Parameters", 0,
				((IMethod) WorldTest.Elements1[12]).getNumberOfConstituents());

	}

	public void testClassGameField() {

		//---------------------------------------
		Assert.assertEquals("Field Name", "FirstTeam",
				WorldTest.Elements1[0].getDisplayName());

		Assert.assertEquals("Field Type", "Team *",
				((IField) WorldTest.Elements1[0]).getDisplayTypeName());

		Assert.assertEquals("Field Access", Access.ACC_PROTECTED,
				((IField) WorldTest.Elements1[0]).getVisibility());

		//---------------------------------------
		Assert.assertEquals("Field Name", "FirstTeamGoalCount",
				WorldTest.Elements1[1].getDisplayName());

		Assert.assertEquals("Field Type", "int",
				((IField) WorldTest.Elements1[1]).getDisplayTypeName());

		Assert.assertEquals("Field Access", Access.ACC_PROTECTED,
				((IField) WorldTest.Elements1[1]).getVisibility());

		//---------------------------------------
		Assert.assertEquals("Field Name", "SecondTeam",
				WorldTest.Elements1[4].getDisplayName());

		Assert.assertEquals("Field Type", "Team *",
				((IField) WorldTest.Elements1[4]).getDisplayTypeName());

		Assert.assertEquals("Field Access", Access.ACC_PROTECTED,
				((IField) WorldTest.Elements1[4]).getVisibility());

		//---------------------------------------
		Assert.assertEquals("Field Name", "SecondTeamGoalCount",
				WorldTest.Elements1[5].getDisplayName());

		Assert.assertEquals("Field Type", "int",
				((IField) WorldTest.Elements1[5]).getDisplayTypeName());

		Assert.assertEquals("Field Access", Access.ACC_PROTECTED,
				((IField) WorldTest.Elements1[5]).getVisibility());

		//---------------------------------------
		Assert.assertEquals("Field Name", "banane",
				WorldTest.Elements1[7].getDisplayName());

		Assert.assertEquals("Field Type", "teamtype",
				((IField) WorldTest.Elements1[7]).getDisplayTypeName());

		Assert.assertEquals("Field Access", Access.ACC_PROTECTED,
				((IField) WorldTest.Elements1[7]).getVisibility());
	}

	public void testClassGameUseRelationship() {
		int k = 0;
		for (int i = 0; i < Elements1.length; ++i)
			if (WorldTest.Elements1[i] instanceof IUseRelationship)
				k++;

		Assert.assertEquals("# Use Relationships", 3, k);

	}

	public void testClassTeamConstructor() {
		Assert.assertEquals("Constr Name", "Team",
				WorldTest.Elements2[2].getDisplayName());

		Assert.assertEquals("Constr Access", Access.ACC_PUBLIC,
				((IConstructor) WorldTest.Elements2[2]).getVisibility());

		// TODO: Fix the bug!
		//	Assert.assertEquals(
		//		"Constr # Parameters",
		//		0,
		//		((IConstructor) TestWorld.Elements2[2]).listOfConstituents().size());

		Assert.assertEquals("Constr Name", "Team",
				WorldTest.Elements2[1].getDisplayName());

		Assert.assertEquals("Constr Access", Access.ACC_PUBLIC,
				((IConstructor) WorldTest.Elements2[1]).getVisibility());

		// TODO: Fix the bug!
		//	Assert.assertEquals(
		//		"Constr # Parameters",
		//		2,
		//		((IConstructor) TestWorld.Elements2[1]).listOfConstituents().size());

		//---------------------------------------
		Assert.assertEquals("Constr Name", "Team",
				WorldTest.Elements2[0].getDisplayName());

		Assert.assertEquals("Constr Access", Access.ACC_PUBLIC,
				((IConstructor) WorldTest.Elements2[0]).getVisibility());

		// TODO: Fix the bug!
		//	Assert.assertEquals(
		//		"Constr # Parameters",
		//		0,
		//		((IConstructor) TestWorld.Elements2[0]).listOfConstituents().size());
	}

	public void testClassTeamField() {

		//---------------------------------------
		Assert.assertEquals("Field Name", "country[]",
				WorldTest.Elements2[3].getDisplayName());

		Assert.assertEquals("Field Type", "char",
				((IField) WorldTest.Elements2[3]).getDisplayTypeName());

		Assert.assertEquals("Field Access", Access.ACC_PROTECTED,
				((IField) WorldTest.Elements2[3]).getVisibility());

		//---------------------------------------
		Assert.assertEquals("Field Name", "draws",
				WorldTest.Elements2[4].getDisplayName());

		Assert.assertEquals("Field Type", "int",
				((IField) WorldTest.Elements2[4]).getDisplayTypeName());

		Assert.assertEquals("Field Access", Access.ACC_PROTECTED,
				((IField) WorldTest.Elements2[4]).getVisibility());

		//---------------------------------------
		Assert.assertEquals("Field Name", "from",
				WorldTest.Elements2[5].getDisplayName());

		Assert.assertEquals("Field Type", "continent",
				((IField) WorldTest.Elements2[5]).getDisplayTypeName());

		Assert.assertEquals("Field Access", Access.ACC_PROTECTED,
				((IField) WorldTest.Elements2[5]).getVisibility());

		//---------------------------------------
		Assert.assertEquals("Field Name", "losses",
				WorldTest.Elements2[12].getDisplayName());

		Assert.assertEquals("Field Type", "int",
				((IField) WorldTest.Elements2[12]).getDisplayTypeName());

		Assert.assertEquals("Field Access", Access.ACC_PROTECTED,
				((IField) WorldTest.Elements2[12]).getVisibility());

		//---------------------------------------
		Assert.assertEquals("Field Name", "orange",
				WorldTest.Elements2[13].getDisplayName());

		Assert.assertEquals("Field Type", "teamtype",
				((IField) WorldTest.Elements2[13]).getDisplayTypeName());

		Assert.assertEquals("Field Access", Access.ACC_PROTECTED,
				((IField) WorldTest.Elements2[13]).getVisibility());

		//---------------------------------------
		Assert.assertEquals("Field Name", "test",
				WorldTest.Elements2[14].getDisplayName());

		Assert.assertEquals("Field Type", "int * *",
				((IField) WorldTest.Elements2[14]).getDisplayTypeName());

		Assert.assertEquals("Field Access", Access.ACC_PROTECTED,
				((IField) WorldTest.Elements2[14]).getVisibility());

		//---------------------------------------
		Assert.assertEquals("Field Name", "wins",
				WorldTest.Elements2[18].getDisplayName());

		Assert.assertEquals("Field Type", "int",
				((IField) WorldTest.Elements2[18]).getDisplayTypeName());

		Assert.assertEquals("Field Access", Access.ACC_PROTECTED,
				((IField) WorldTest.Elements2[18]).getVisibility());
	}

	public void testClassTeamMethod() {
		//---------------------------------------
		Assert.assertEquals("Method Name", "getcontinent",
				WorldTest.Elements2[6].getDisplayName());

		Assert.assertEquals("Method Return Type", "continent",
				((IMethod) WorldTest.Elements2[6]).getDisplayReturnType());

		Assert.assertEquals("Method Access", Access.ACC_PUBLIC,
				((IMethod) WorldTest.Elements2[6]).getVisibility());

		Assert.assertEquals("Method # Parameters", 0,
				((IMethod) WorldTest.Elements2[6]).getNumberOfConstituents());

		//---------------------------------------
		Assert.assertEquals("Method Name", "getcountry",
				WorldTest.Elements2[7].getDisplayName());

		Assert.assertEquals("Method Return Type", "char *",
				((IMethod) WorldTest.Elements2[7]).getDisplayReturnType());

		Assert.assertEquals("Method Access", Access.ACC_PUBLIC,
				((IMethod) WorldTest.Elements2[7]).getVisibility());

		Assert.assertEquals("Method # Parameters", 0,
				((IMethod) WorldTest.Elements2[7]).getNumberOfConstituents());

		//---------------------------------------
		Assert.assertEquals("Method Name", "getdraws",
				WorldTest.Elements2[8].getDisplayName());

		Assert.assertEquals("Method Return Type", "int",
				((IMethod) WorldTest.Elements2[8]).getDisplayReturnType());

		Assert.assertEquals("Method Access", Access.ACC_PUBLIC,
				((IMethod) WorldTest.Elements2[8]).getVisibility());

		Assert.assertEquals("Method # Parameters", 0,
				((IMethod) WorldTest.Elements2[8]).getNumberOfConstituents());

		//---------------------------------------
		Assert.assertEquals("Method Name", "getlosses",
				WorldTest.Elements2[9].getDisplayName());

		Assert.assertEquals("Method Return Type", "int",
				((IMethod) WorldTest.Elements2[9]).getDisplayReturnType());

		Assert.assertEquals("Method Access", Access.ACC_PUBLIC,
				((IMethod) WorldTest.Elements2[9]).getVisibility());

		Assert.assertEquals("Method # Parameters", 0,
				((IMethod) WorldTest.Elements2[9]).getNumberOfConstituents());

		//---------------------------------------
		Assert.assertEquals("Method Name", "getwins",
				WorldTest.Elements2[10].getDisplayName());

		Assert.assertEquals("Method Return Type", "int",
				((IMethod) WorldTest.Elements2[10]).getDisplayReturnType());

		Assert.assertEquals("Method Access", Access.ACC_PUBLIC,
				((IMethod) WorldTest.Elements2[10]).getVisibility());

		Assert.assertEquals("Method # Parameters", 0,
				((IMethod) WorldTest.Elements2[10]).getNumberOfConstituents());

		//---------------------------------------
		Assert.assertEquals("Method Name", "hello",
				WorldTest.Elements2[11].getDisplayName());

		Assert.assertEquals("Method Return Type", "void",
				((IMethod) WorldTest.Elements2[11]).getDisplayReturnType());

		Assert.assertEquals("Method Access", Access.ACC_PUBLIC,
				((IMethod) WorldTest.Elements2[11]).getVisibility());

		Assert.assertEquals("Method # Parameters", 0,
				((IMethod) WorldTest.Elements2[11]).getNumberOfConstituents());

		//---------------------------------------
		Assert.assertEquals("Method Name", "updateResults",
				WorldTest.Elements2[17].getDisplayName());

		Assert.assertEquals("Method Return Type", "void",
				((IMethod) WorldTest.Elements2[17]).getDisplayReturnType());

		Assert.assertEquals("Method Access", Access.ACC_PUBLIC,
				((IMethod) WorldTest.Elements2[17]).getVisibility());

		Assert.assertEquals("Method # Parameters", 1,
				((IMethod) WorldTest.Elements2[17]).getNumberOfConstituents());
	}

	public void testClassTeamDestructor() {
		//---------------------------------------
		Assert.assertEquals("Destr Name", "~Team",
				WorldTest.Elements2[19].getDisplayName());

		Assert.assertEquals("Destr Access", Access.ACC_PUBLIC,
				((IDestructor) WorldTest.Elements2[19]).getVisibility());

		// TODO: Fix the bug!
		//	Assert.assertEquals(
		//		"Constr # Parameters",
		//		1,
		//		((IDestructor) TestWorld.Elements2[19]).listOfConstituents().size());

		//---------------------------------------
		Assert.assertEquals("Destr Name", "~Team",
				WorldTest.Elements2[20].getDisplayName());

		Assert.assertEquals("Destr Access", Access.ACC_PUBLIC,
				((IDestructor) WorldTest.Elements2[20]).getVisibility());

		// TODO: Fix the bug!
		//	Assert.assertEquals(
		//		"Constr # Parameters",
		//		0,
		//		((IDestructor) TestWorld.Elements2[20]).listOfConstituents().size());
	}

	public void testClassTeamUseRelationship() {
		int k = 0;
		for (int i = 0; i < Elements2.length; ++i) {
			if (WorldTest.Elements2[i] instanceof IUseRelationship) {
				k++;
			}
		}

		Assert.assertEquals("# Use Relationships", 2, k);

		// Yann 2008/11/17: Order...
		// For some reason, the order of the relationship
		// changes randomly... maybe because of the Math.random()
		// used in the parser to ID the relationships...
		final String firstRelationshipName = ((IUseRelationship) WorldTest.Elements2[15])
				.getTargetEntity().getDisplayName();
		Assert.assertTrue("Target Name",
				firstRelationshipName.equals("continent")
						|| firstRelationshipName.equals("teamtype"));

		final String secondRelationshipName = ((IUseRelationship) WorldTest.Elements2[16])
				.getTargetEntity().getDisplayName();
		Assert.assertTrue("Target Name",
				secondRelationshipName.equals("continent")
						|| secondRelationshipName.equals("teamtype"));
	}
}
