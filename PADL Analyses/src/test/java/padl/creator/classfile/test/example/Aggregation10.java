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

import padl.analysis.UnsupportedSourceModelException;
import padl.analysis.repository.AACRelationshipsAnalysis;
import padl.creator.classfile.CompleteClassFileCreator;
import padl.creator.classfile.test.ClassFilePrimitive;
import padl.kernel.Constants;
import padl.kernel.IAggregation;
import padl.kernel.IAssociation;
import padl.kernel.ICodeLevelModel;
import padl.kernel.IElement;
import padl.kernel.IField;
import padl.kernel.IFirstClassEntity;
import padl.kernel.IIdiomLevelModel;
import padl.kernel.IUseRelationship;
import padl.kernel.exception.CreationException;
import padl.util.Util;
import util.lang.Modifier;

/**
 * @version	0.2
 * @author 	Yann-Gaël Guéhéneuc
 * @since	2002/08/15
 */
public class Aggregation10 extends ClassFilePrimitive {
	private static IElement[] Elements = null;
	private static IFirstClassEntity[] FirstClassEntities = null;

	public Aggregation10(final String name) {
		super(name);
	}

	protected void setUp()
			throws CreationException, UnsupportedSourceModelException {

		if (Aggregation10.FirstClassEntities == null
				|| Aggregation10.Elements == null) {

			final ICodeLevelModel codeLevelModel = ClassFilePrimitive
					.getFactory()
					.createCodeLevelModel("ptidej.example.messagetype");
			codeLevelModel.create(new CompleteClassFileCreator(new String[] {
					"../PADL Creator ClassFile/target/test-classes/padl/example/aggregation/Aggregation10.class" }));

			final IIdiomLevelModel idiomLevelModel = (IIdiomLevelModel) new AACRelationshipsAnalysis()
					.invoke(codeLevelModel);

			Aggregation10.FirstClassEntities = Util
					.getArrayOfTopLevelEntities(idiomLevelModel);

			Aggregation10.Elements = Util
					.getArrayOfElements(Aggregation10.FirstClassEntities[3]);
		}
	}

	public void testAddAMethod() {
		Assert.assertEquals("Method addA()", "addA",
				Aggregation10.Elements[2].getDisplayName());
	}

	public void testAggregation() {
		final int elementPosition = ClassFilePrimitive.getConstituentPosition(
				IAggregation.class, Aggregation10.Elements)[0];

		ClassFilePrimitive.assertAssigable("Aggregation type",
				IAggregation.class,
				Aggregation10.Elements[elementPosition].getClass());
		Assert.assertEquals("Aggregation visibility",
				Modifier.toString(Modifier.DEFAULT),
				Modifier.toString(Aggregation10.Elements[elementPosition]
						.getVisibility()));
		Assert.assertEquals("Aggregation cardinality",
				Constants.CARDINALITY_MANY,
				((IAggregation) Aggregation10.Elements[elementPosition])
						.getCardinality());
		Assert.assertEquals("Aggregation name",
				"padl.kernel.impl.Aggregation:java.util.List:2+padl.kernel.impl.Aggregation:java.util.List:2",
				Aggregation10.Elements[elementPosition].getDisplayName());
		Assert.assertEquals("Aggregation target", "java.util.List",
				((IAggregation) Aggregation10.Elements[elementPosition])
						.getTargetEntity().getDisplayID());
	}

	public void testAssociationLink() {
		final int elementPosition = ClassFilePrimitive.getConstituentPosition(
				IAssociation.class, Aggregation10.Elements)[1];

		ClassFilePrimitive.assertAssigable("Association link type",
				IAssociation.class,
				Aggregation10.Elements[elementPosition].getClass());
		Assert.assertEquals("Association link visibility",
				Modifier.toString(Modifier.DEFAULT),
				Modifier.toString(Aggregation10.Elements[elementPosition]
						.getVisibility()));
		Assert.assertEquals("Association link cardinality",
				Constants.CARDINALITY_ONE,
				((IAssociation) Aggregation10.Elements[elementPosition])
						.getCardinality());
		Assert.assertEquals("Association link name",
				"padl.kernel.impl.Association:java.util.List:1",
				Aggregation10.Elements[elementPosition].getDisplayName());
		Assert.assertEquals("Association link target", "java.util.List",
				((IAssociation) Aggregation10.Elements[elementPosition])
						.getTargetEntity().getDisplayID());
	}

	public void testConstructor() {
		Assert.assertEquals("Constructor", "Aggregation10",
				Aggregation10.Elements[0].getDisplayName());
	}

	public void testConstructorAssociation() {
		ClassFilePrimitive.assertAssigable("Association link type",
				IAssociation.class, Aggregation10.Elements[1].getClass());
		Assert.assertEquals("Association link visibility",
				Modifier.toString(Modifier.PUBLIC),
				Modifier.toString(Aggregation10.Elements[1].getVisibility()));
		Assert.assertEquals("Association link cardinality",
				Constants.CARDINALITY_ONE,
				((IAssociation) Aggregation10.Elements[1]).getCardinality());
		Assert.assertEquals("Association link name",
				"padl.kernel.impl.Association:java.lang.Object:1",
				Aggregation10.Elements[1].getDisplayName());
		Assert.assertEquals("Association link target", "java.lang.Object",
				((IAssociation) Aggregation10.Elements[1]).getTargetEntity()
						.getDisplayID());
	}

	public void testUseRelationship() {
		final int elementPosition = ClassFilePrimitive.getConstituentPosition(
				IUseRelationship.class, Aggregation10.Elements)[0];

		ClassFilePrimitive.assertAssigable("Use link type",
				IUseRelationship.class,
				Aggregation10.Elements[elementPosition].getClass());
		Assert.assertEquals("Use link visibility",
				Modifier.toString(Modifier.DEFAULT),
				Modifier.toString(Aggregation10.Elements[elementPosition]
						.getVisibility()));
		Assert.assertEquals("Use link cardinality", Constants.CARDINALITY_ONE,
				((IUseRelationship) Aggregation10.Elements[elementPosition])
						.getCardinality());
		Assert.assertEquals("Use link name",
				"padl.kernel.impl.UseRelationship:padl.example.aggregation.A:1",
				Aggregation10.Elements[elementPosition].getDisplayName());
		Assert.assertEquals("Use link target", "padl.example.aggregation.A",
				((IUseRelationship) Aggregation10.Elements[elementPosition])
						.getTargetEntity().getDisplayID());
	}

	public void testMainEntityName() {
		Assert.assertEquals("Entity name",
				"padl.example.aggregation.Aggregation10",
				Aggregation10.FirstClassEntities[3].getDisplayID());
	}

	public void testNumberOfElements() {
		Assert.assertEquals("Number of elements", 8,
				Aggregation10.Elements.length);
	}

	public void testNumberOfEntities() {
		Assert.assertEquals("Number of entities", 4,
				Aggregation10.FirstClassEntities.length);
	}

	public void testRemoveAMethod() {
		Assert.assertEquals("Method removeA()", "removeA",
				Aggregation10.Elements[7].getDisplayName());
	}

	//	public void testRemoveAssociation() {
	//		Aggregation10.assertAssigable(
	//			"Association link type",
	//			IAssociation.class,
	//			Aggregation10.Elements[8].getClass());
	//		Assert.assertEquals(
	//			"Association link visibility",
	//			Modifier.toString(Modifier.DEFAULT),
	//			Modifier.toString(Aggregation10.Elements[8].getVisibility()));
	//		Assert.assertEquals(
	//			"Association link cardinality",
	//			2,
	//			((IAssociation) Aggregation10.Elements[8]).getCardinality());
	//		Assert.assertEquals(
	//			"Association link name",
	//			"removeA_5",
	//			Aggregation10.Elements[8].getDisplayName());
	//		Assert.assertEquals(
	//			"Association link target",
	//			"java.util.List",
	//			((IAssociation) Aggregation10.Elements[8])
	//				.getTargetEntity()
	//				.getDisplayName());
	//	}
	public void testStaticField() {
		Assert.assertEquals("Static field visibility",
				Modifier.toString(Modifier.PRIVATE),
				Modifier.toString(Aggregation10.Elements[6].getVisibility()));
		Assert.assertEquals("Static field type", "java.util.List",
				((IField) Aggregation10.Elements[6]).getDisplayTypeName());
		Assert.assertEquals("Static field name", "listOfAs",
				Aggregation10.Elements[6].getDisplayName());
	}
}
