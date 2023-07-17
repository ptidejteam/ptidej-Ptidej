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
import padl.kernel.IContainerAggregation;
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
public class Aggregation11 extends ClassFilePrimitive {
	private static IElement[] Elements = null;
	private static IFirstClassEntity[] FirstClassEntities = null;

	public Aggregation11(final String name) {
		super(name);
	}
	protected void setUp() throws CreationException,
			UnsupportedSourceModelException {

		if (Aggregation11.FirstClassEntities == null
				|| Aggregation11.Elements == null) {

			final ICodeLevelModel codeLevelModel =
				ClassFilePrimitive.getFactory().createCodeLevelModel(
					"ptidej.example.messagetype");
			codeLevelModel
				.create(new CompleteClassFileCreator(
					new String[] { "../PADL Creator ClassFile Tests/bin/padl/example/aggregation/Aggregation11.class" }));

			final IIdiomLevelModel idiomLevelModel =
				(IIdiomLevelModel) new AACRelationshipsAnalysis()
					.invoke(codeLevelModel);

			Aggregation11.FirstClassEntities =
				Util.getArrayOfTopLevelEntities(idiomLevelModel);

			Aggregation11.Elements =
				Util.getArrayOfElements(Aggregation11.FirstClassEntities[3]);
		}
	}
	public void testAddAggregation() {
		final int elementPosition =
			ClassFilePrimitive.getConstituentPosition(
				IAggregation.class,
				Aggregation11.Elements)[0];

		ClassFilePrimitive.assertAssigable(
			"Aggregation type",
			IAggregation.class,
			Aggregation11.Elements[elementPosition].getClass());
		Assert.assertEquals("Aggregation visibility", Modifier
			.toString(Modifier.DEFAULT), Modifier
			.toString(Aggregation11.Elements[elementPosition].getVisibility()));
		Assert.assertEquals(
			"Aggregation cardinality",
			Constants.CARDINALITY_MANY,
			((IAggregation) Aggregation11.Elements[elementPosition])
				.getCardinality());
		Assert
			.assertEquals(
				"Aggregation name",
				"padl.kernel.impl.Aggregation:java.util.List:2+padl.kernel.impl.Aggregation:java.util.List:2",
				Aggregation11.Elements[elementPosition].getDisplayName());
		Assert.assertEquals(
			"Aggregation target",
			"java.util.List",
			((IAggregation) Aggregation11.Elements[elementPosition])
				.getTargetEntity()
				.getDisplayID());
	}
	public void testAddAMethod() {
		Assert.assertEquals(
			"Method addA()",
			"addA",
			Aggregation11.Elements[2].getDisplayName());
	}
	public void testAddUseRelationship() {
		final int elementPosition =
			ClassFilePrimitive.getConstituentPosition(
				IUseRelationship.class,
				Aggregation11.Elements)[0];

		ClassFilePrimitive.assertAssigable(
			"Use relationship type",
			IUseRelationship.class,
			Aggregation11.Elements[elementPosition].getClass());
		Assert.assertEquals("Use relationship visibility", Modifier
			.toString(Modifier.DEFAULT), Modifier
			.toString(Aggregation11.Elements[elementPosition].getVisibility()));
		Assert.assertEquals(
			"Use relationship cardinality",
			1,
			((IUseRelationship) Aggregation11.Elements[elementPosition])
				.getCardinality());
		Assert
			.assertEquals(
				"Use relationship name",
				"padl.kernel.impl.UseRelationship:padl.example.aggregation.A:1+padl.kernel.impl.UseRelationship:padl.example.aggregation.A:1",
				Aggregation11.Elements[elementPosition].getDisplayName());
		Assert.assertEquals(
			"Use relationship target",
			"padl.example.aggregation.A",
			((IUseRelationship) Aggregation11.Elements[elementPosition])
				.getTargetEntity()
				.getDisplayID());
	}
	public void testAssociation1() {
		final int elementPosition =
			ClassFilePrimitive.getConstituentPosition(
				IAssociation.class,
				Aggregation11.Elements)[1];

		ClassFilePrimitive.assertAssigable(
			"Association relationship type",
			IAssociation.class,
			Aggregation11.Elements[elementPosition].getClass());
		Assert.assertEquals("Association relationship visibility", Modifier
			.toString(Modifier.DEFAULT), Modifier
			.toString(Aggregation11.Elements[elementPosition].getVisibility()));
		Assert.assertEquals(
			"Association relationship cardinality",
			Constants.CARDINALITY_ONE,
			((IAssociation) Aggregation11.Elements[elementPosition])
				.getCardinality());
		Assert
			.assertEquals(
				"Association relationship name",
				"padl.kernel.impl.Association:java.util.List:1+padl.kernel.impl.Association:java.util.List:1",
				Aggregation11.Elements[elementPosition].getDisplayName());
		Assert.assertEquals(
			"Association relationship target",
			"java.util.List",
			((IAssociation) Aggregation11.Elements[elementPosition])
				.getTargetEntity()
				.getDisplayID());
	}
	//	public void testAssociation2() {
	//		Primitive.assertAssignable(
	//			"Association relationship type",
	//			IAssociation.class,
	//			Aggregation11.Elements[11].getClass());
	//		Assert.assertEquals(
	//			"Association relationship visibility",
	//			Modifier.toString(Modifier.DEFAULT),
	//			Modifier.toString(Aggregation11.Elements[11].getVisibility()));
	//		Assert.assertEquals(
	//			"Association relationship cardinality",
	//			2,
	//			((IAssociation) Aggregation11.Elements[11]).getCardinality());
	//		Assert.assertEquals(
	//			"Association relationship name",
	//			"removeA_6",
	//			Aggregation11.Elements[11].getDisplayName());
	//		Assert.assertEquals(
	//			"Association relationship target",
	//			"java.util.List",
	//			((IAssociation) Aggregation11.Elements[11])
	//				.getTargetEntity()
	//				.getDisplayName());
	//	}
	public void testConstructor() {
		Assert.assertEquals(
			"Constructor",
			"Aggregation11",
			Aggregation11.Elements[0].getDisplayName());
	}
	public void testConstructorAssociation() {
		ClassFilePrimitive.assertAssigable(
			"Association relationship type",
			IAssociation.class,
			Aggregation11.Elements[1].getClass());
		Assert.assertEquals(
			"Association relationship visibility",
			Modifier.toString(Modifier.PUBLIC),
			Modifier.toString(Aggregation11.Elements[1].getVisibility()));
		Assert.assertEquals(
			"Association relationship cardinality",
			Constants.CARDINALITY_ONE,
			((IAssociation) Aggregation11.Elements[1]).getCardinality());
		Assert.assertEquals(
			"Association relationship name",
			"padl.kernel.impl.Association:java.lang.Object:1",
			Aggregation11.Elements[1].getDisplayName());
		Assert.assertEquals(
			"Association relationship target",
			"java.lang.Object",
			((IAssociation) Aggregation11.Elements[1])
				.getTargetEntity()
				.getDisplayID());
	}
	public void testContainerAggregation() {
		ClassFilePrimitive.assertAssigable(
			"ContainerAggregation relationship type",
			IContainerAggregation.class,
			Aggregation11.Elements[7].getClass());
		Assert.assertEquals(
			"ContainerAggregation relationship visibility",
			Modifier.toString(Modifier.DEFAULT),
			Modifier.toString(Aggregation11.Elements[7].getVisibility()));
		Assert.assertEquals(
			"ContainerAggregation relationship cardinality",
			Constants.CARDINALITY_MANY,
			((IContainerAggregation) Aggregation11.Elements[7])
				.getCardinality());
		Assert
			.assertEquals(
				"ContainerAggregation relationship name",
				"padl.kernel.impl.ContainerAggregation:padl.example.aggregation.A:2",
				Aggregation11.Elements[7].getDisplayName());
		Assert.assertEquals(
			"ContainerAggregation relationship target",
			"padl.example.aggregation.A",
			((IContainerAggregation) Aggregation11.Elements[7])
				.getTargetEntity()
				.getDisplayID());
	}
	public void testMainEntityName() {
		Assert.assertEquals(
			"Entity name",
			"padl.example.aggregation.Aggregation11",
			Aggregation11.FirstClassEntities[3].getDisplayID());
	}
	public void testNumberOfElements() {
		Assert.assertEquals(
			"Number of elements",
			9,
			Aggregation11.Elements.length);
	}
	public void testNumberOfEntities() {
		Assert.assertEquals(
			"Number of entities",
			4,
			Aggregation11.FirstClassEntities.length);
	}
	//	public void testRemoveAggregation() {
	//		Primitive.assertAssignable(
	//			"Aggregation type",
	//			IAggregation.class,
	//			Aggregation11.Elements[9].getClass());
	//		Assert.assertEquals(
	//			"Aggregation visibility",
	//			Modifier.toString(Modifier.DEFAULT),
	//			Modifier.toString(Aggregation11.Elements[9].getVisibility()));
	//		Assert.assertEquals(
	//			"Aggregation cardinality",
	//			2,
	//			((IAggregation) Aggregation11.Elements[9]).getCardinality());
	//		Assert.assertEquals(
	//			"Aggregation name",
	//			"removeA_4",
	//			Aggregation11.Elements[9].getDisplayName());
	//		Assert.assertEquals(
	//			"Aggregation target",
	//			"java.lang.Object",
	//			((IAggregation) Aggregation11.Elements[9])
	//				.getTargetEntity()
	//				.getDisplayName());
	//	}
	public void testRemoveAMethod() {
		Assert.assertEquals(
			"Method removeA()",
			"removeA",
			Aggregation11.Elements[8].getDisplayName());
	}
	//	public void testRemoveUseRelationship() {
	//		Primitive.assertAssignable(
	//			"Use relationship type",
	//			IUseRelationship.class,
	//			Aggregation11.Elements[10].getClass());
	//		Assert.assertEquals(
	//			"Use relationship visibility",
	//			Modifier.toString(Modifier.DEFAULT),
	//			Modifier.toString(Aggregation11.Elements[10].getVisibility()));
	//		Assert.assertEquals(
	//			"Use relationship cardinality",
	//			1,
	//			((IUseRelationship) Aggregation11.Elements[10]).getCardinality());
	//		Assert.assertEquals(
	//			"Use relationship name",
	//			"removeA_5",
	//			Aggregation11.Elements[10].getDisplayName());
	//		Assert.assertEquals(
	//			"Use relationship target",
	//			"padl.example.aggregation.A",
	//			((IUseRelationship) Aggregation11.Elements[10])
	//				.getTargetEntity()
	//				.getDisplayName());
	//	}
	public void testStaticField() {
		Assert.assertEquals(
			"Static field visibility",
			Modifier.toString(Modifier.PRIVATE),
			Modifier.toString(Aggregation11.Elements[6].getVisibility()));
		Assert.assertEquals(
			"Static field type",
			"java.util.List",
			((IField) Aggregation11.Elements[6]).getDisplayTypeName());
		Assert.assertEquals(
			"Static field name",
			"listOfAs",
			Aggregation11.Elements[6].getDisplayName());
	}
}
