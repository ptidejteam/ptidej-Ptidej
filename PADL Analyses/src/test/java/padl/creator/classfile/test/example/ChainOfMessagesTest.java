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
package padl.creator.classfile.test.example;

import org.junit.Assert;

import com.ibm.toad.cfparse.utils.Access;

import padl.analysis.UnsupportedSourceModelException;
import padl.analysis.repository.AACRelationshipsAnalysis;
import padl.creator.classfile.CompleteClassFileCreator;
import padl.creator.classfile.test.ClassFilePrimitive;
import padl.kernel.Constants;
import padl.kernel.IAssociation;
import padl.kernel.ICodeLevelModel;
import padl.kernel.IElement;
import padl.kernel.IFirstClassEntity;
import padl.kernel.IIdiomLevelModel;
import padl.kernel.exception.CreationException;
import padl.util.Util;

/**
 * @version	0.2
 * @author 	Yann-Gaël Guéhéneuc
 * @since	2002/08/15
 */
public class ChainOfMessagesTest extends ClassFilePrimitive {
	private static IFirstClassEntity[] FirstClassEntities = null;
	private static IElement[] Elements = null;

	public ChainOfMessagesTest(final String name) {
		super(name);
	}

	protected void setUp()
			throws CreationException, UnsupportedSourceModelException {

		if (ChainOfMessagesTest.FirstClassEntities == null
				|| ChainOfMessagesTest.Elements == null) {

			final ICodeLevelModel codeLevelModel = ClassFilePrimitive
					.getFactory()
					.createCodeLevelModel("ptidej.example.relationship");
			codeLevelModel.create(new CompleteClassFileCreator(new String[] {
					"../PADL Creator ClassFile/target/test-classes/padl/example/relationship/ChainOfMessages.class" }));

			final IIdiomLevelModel idiomLevelModel = (IIdiomLevelModel) new AACRelationshipsAnalysis()
					.invoke(codeLevelModel);

			ChainOfMessagesTest.FirstClassEntities = Util
					.getArrayOfTopLevelEntities(idiomLevelModel);

			ChainOfMessagesTest.Elements = Util.getArrayOfElements(
					ChainOfMessagesTest.FirstClassEntities[3]);
		}
	}

	public void testConstructor() {
		Assert.assertEquals("Constructor", "ChainOfMessages",
				ChainOfMessagesTest.Elements[0].getDisplayName());
	}

	public void testMethod() {
		Assert.assertEquals("Method", "foo",
				ChainOfMessagesTest.Elements[2].getDisplayName());
	}

	public void testAssociation0() {
		ClassFilePrimitive.assertAssigable("Association link type",
				IAssociation.class, ChainOfMessagesTest.Elements[1].getClass());
		Assert.assertEquals("Association link visibility",
				Access.getAsString(Access.ACC_PUBLIC), Access.getAsString(
						ChainOfMessagesTest.Elements[1].getVisibility()));
		Assert.assertEquals("Association link cardinality",
				Constants.CARDINALITY_ONE,
				((IAssociation) ChainOfMessagesTest.Elements[1])
						.getCardinality());
		Assert.assertEquals("Association link name",
				"padl.kernel.impl.Association:java.lang.Object:1+padl.kernel.impl.Association:java.lang.Object:1",
				ChainOfMessagesTest.Elements[1].getDisplayName());
		Assert.assertEquals("Association link target", "java.lang.Object",
				((IAssociation) ChainOfMessagesTest.Elements[1])
						.getTargetEntity().getDisplayID());
	}

	public void testAssociation1() {
		ClassFilePrimitive.assertAssigable("Association link type",
				IAssociation.class, ChainOfMessagesTest.Elements[3].getClass());
		Assert.assertEquals("Association link visibility",
				Access.getAsString(Access.ACC_PUBLIC), Access.getAsString(
						ChainOfMessagesTest.Elements[3].getVisibility()));
		Assert.assertEquals("Association link cardinality",
				Constants.CARDINALITY_ONE,
				((IAssociation) ChainOfMessagesTest.Elements[3])
						.getCardinality());
		Assert.assertEquals("Association link name",
				"padl.kernel.impl.Association:java.lang.Class:1+padl.kernel.impl.Association:java.lang.Class:1",
				ChainOfMessagesTest.Elements[3].getDisplayName());
		Assert.assertEquals("Association link target", "java.lang.Class",
				((IAssociation) ChainOfMessagesTest.Elements[3])
						.getTargetEntity().getDisplayID());
	}

	public void testAssociation2() {
		ClassFilePrimitive.assertAssigable("Association link type",
				IAssociation.class, ChainOfMessagesTest.Elements[4].getClass());
		Assert.assertEquals("Association link visibility",
				Access.getAsString(Access.ACC_PUBLIC), Access.getAsString(
						ChainOfMessagesTest.Elements[4].getVisibility()));
		Assert.assertEquals("Association link cardinality",
				Constants.CARDINALITY_ONE,
				((IAssociation) ChainOfMessagesTest.Elements[4])
						.getCardinality());
		Assert.assertEquals("Association link name",
				"padl.kernel.impl.Association:java.lang.ClassLoader:1+padl.kernel.impl.Association:java.lang.ClassLoader:1+padl.kernel.impl.Association:java.lang.ClassLoader:1",
				ChainOfMessagesTest.Elements[4].getDisplayName());
		Assert.assertEquals("Association link target", "java.lang.ClassLoader",
				((IAssociation) ChainOfMessagesTest.Elements[4])
						.getTargetEntity().getDisplayID());
	}

	//	public void testAssociation3() {
	//		ChainOfMessages.assertAssigable(
	//			"Association link type",
	//			IAssociation.class,
	//			ChainOfMessages.Elements[5].getClass());
	//		Assert.assertEquals(
	//			"Association link visibility",
	//			Access.getAsString(Access.ACC_PUBLIC),
	//			Access.getAsString(ChainOfMessages.Elements[5].getVisibility()));
	//		Assert.assertEquals(
	//			"Association link cardinality",
	//			1,
	//			((IAssociation) ChainOfMessages.Elements[5]).getCardinality());
	//		Assert.assertEquals(
	//			"Association link name",
	//			"foo_3",
	//			ChainOfMessages.Elements[5].getDisplayName());
	//		Assert.assertEquals(
	//			"Association link target",
	//			"java.lang.ClassLoader",
	//			((IAssociation) ChainOfMessages.Elements[5])
	//				.getTargetEntity()
	//				.getDisplayName());
	//	}
	//	public void testAssociation4() {
	//		ChainOfMessages.assertAssigable(
	//			"Association link type",
	//			IAssociation.class,
	//			ChainOfMessages.Elements[6].getClass());
	//		Assert.assertEquals(
	//			"Association link visibility",
	//			Access.getAsString(Access.ACC_PUBLIC),
	//			Access.getAsString(ChainOfMessages.Elements[6].getVisibility()));
	//		Assert.assertEquals(
	//			"Association link cardinality",
	//			1,
	//			((IAssociation) ChainOfMessages.Elements[6]).getCardinality());
	//		Assert.assertEquals(
	//			"Association link name",
	//			"foo_7",
	//			ChainOfMessages.Elements[6].getDisplayName());
	//		Assert.assertEquals(
	//			"Association link target",
	//			"java.lang.Object",
	//			((IAssociation) ChainOfMessages.Elements[6])
	//				.getTargetEntity()
	//				.getDisplayName());
	//	}
	//	public void testAssociation5() {
	//		ChainOfMessages.assertAssigable(
	//			"Association link type",
	//			IAssociation.class,
	//			ChainOfMessages.Elements[7].getClass());
	//		Assert.assertEquals(
	//			"Association link visibility",
	//			Access.getAsString(Access.ACC_PUBLIC),
	//			Access.getAsString(ChainOfMessages.Elements[7].getVisibility()));
	//		Assert.assertEquals(
	//			"Association link cardinality",
	//			1,
	//			((IAssociation) ChainOfMessages.Elements[7]).getCardinality());
	//		Assert.assertEquals(
	//			"Association link name",
	//			"foo_8",
	//			ChainOfMessages.Elements[7].getDisplayName());
	//		Assert.assertEquals(
	//			"Association link target",
	//			"java.lang.Class",
	//			((IAssociation) ChainOfMessages.Elements[7])
	//				.getTargetEntity()
	//				.getDisplayName());
	//	}
	//	public void testAssociation6() {
	//		ChainOfMessages.assertAssigable(
	//			"Association link type",
	//			IAssociation.class,
	//			ChainOfMessages.Elements[8].getClass());
	//		Assert.assertEquals(
	//			"Association link visibility",
	//			Access.getAsString(Access.ACC_PUBLIC),
	//			Access.getAsString(ChainOfMessages.Elements[8].getVisibility()));
	//		Assert.assertEquals(
	//			"Association link cardinality",
	//			1,
	//			((IAssociation) ChainOfMessages.Elements[8]).getCardinality());
	//		Assert.assertEquals(
	//			"Association link name",
	//			"foo_9",
	//			ChainOfMessages.Elements[8].getDisplayName());
	//		Assert.assertEquals(
	//			"Association link target",
	//			"java.lang.ClassLoader",
	//			((IAssociation) ChainOfMessages.Elements[8])
	//				.getTargetEntity()
	//				.getDisplayName());
	//	}
	public void testNumberOfElements() {
		Assert.assertEquals("Number of elements", 5,
				ChainOfMessagesTest.Elements.length);
	}

	public void testNumberOfEntities() {
		Assert.assertEquals("Number of entities", 4,
				ChainOfMessagesTest.FirstClassEntities.length);
	}
}
