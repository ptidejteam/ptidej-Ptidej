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
 * @author 	Yann-Gaël Guéhéneuc
 * @since	2002/08/15
 */
public class Aggregation13 extends ClassFilePrimitive {
	private static IElement[] Elements = null;
	private static IFirstClassEntity[] FirstClassEntities = null;

	public Aggregation13(final String name) {
		super(name);
	}
	protected void setUp() throws CreationException,
			UnsupportedSourceModelException {

		if (Aggregation13.FirstClassEntities == null
				|| Aggregation13.Elements == null) {

			final ICodeLevelModel codeLevelModel =
				ClassFilePrimitive.getFactory().createCodeLevelModel(
					"ptidej.example.aggregations");
			codeLevelModel
				.create(new CompleteClassFileCreator(
					new String[] { "../PADL Creator ClassFile Tests/bin/padl/example/aggregation/Aggregation13.class" }));

			final IIdiomLevelModel idiomLevelModel =
				(IIdiomLevelModel) new AACRelationshipsAnalysis()
					.invoke(codeLevelModel);

			Aggregation13.FirstClassEntities =
				Util.getArrayOfTopLevelEntities(idiomLevelModel);

			Aggregation13.Elements =
				Util.getArrayOfElements(Aggregation13.FirstClassEntities[2]);
		}
	}
	public void testAddAAggregation() {
		ClassFilePrimitive.assertAssigable(
			"Use relationship type",
			IUseRelationship.class,
			Aggregation13.Elements[6].getClass());
		Assert.assertEquals(
			"Use relationship visibility",
			Modifier.toString(Modifier.DEFAULT),
			Modifier.toString(Aggregation13.Elements[6].getVisibility()));
		Assert.assertEquals(
			"Use relationship cardinality",
			1,
			((IUseRelationship) Aggregation13.Elements[6]).getCardinality());
		Assert
			.assertEquals(
				"Use relationship name",
				"padl.kernel.impl.UseRelationship:padl.example.aggregation.A:1+padl.kernel.impl.UseRelationship:padl.example.aggregation.A:1+padl.kernel.impl.UseRelationship:padl.example.aggregation.A:1+padl.kernel.impl.UseRelationship:padl.example.aggregation.A:1",
				Aggregation13.Elements[6].getDisplayName());
		Assert.assertEquals(
			"Use relationship target",
			"padl.example.aggregation.A",
			((IUseRelationship) Aggregation13.Elements[6])
				.getTargetEntity()
				.getDisplayID());
	}
	public void testAddAMethod() {
		Assert.assertEquals(
			"Method addA()",
			"addA",
			Aggregation13.Elements[5].getDisplayName());
	}
	public void testAddAnotherA() {
		Assert.assertEquals(
			"Method addAnotherA()",
			"addAnotherA",
			Aggregation13.Elements[7].getDisplayName());
	}
	//	public void testAddAnotherAAggregation() {
	//		Aggregation13.assertAssigable(
	//			"Use relationship type",
	//			IUseRelationship.class,
	//			Aggregation13.Elements[8].getClass());
	//		Assert.assertEquals(
	//			"Use relationship visibility",
	//			Modifier.toString(Modifier.DEFAULT),
	//			Modifier.toString(Aggregation13.Elements[8].getVisibility()));
	//		Assert.assertEquals(
	//			"Use relationship cardinality",
	//			1,
	//			((IUseRelationship) Aggregation13.Elements[8]).getCardinality());
	//		Assert.assertEquals(
	//			"Use relationship name",
	//			"addAnotherA_2",
	//			Aggregation13.Elements[8].getDisplayName());
	//		Assert.assertEquals(
	//			"Use relationship target",
	//			"padl.example.aggregation.A",
	//			((IUseRelationship) Aggregation13.Elements[8])
	//				.getTargetEntity()
	//				.getDisplayName());
	//	}
	public void testConstructor() {
		Assert.assertEquals(
			"Constructor",
			"Aggregation13",
			Aggregation13.Elements[0].getDisplayName());
	}
	public void testConstructorAssociation() {
		ClassFilePrimitive.assertAssigable(
			"Association type",
			IAssociation.class,
			Aggregation13.Elements[1].getClass());
		Assert.assertEquals(
			"Association visibility",
			Modifier.toString(Modifier.PUBLIC),
			Modifier.toString(Aggregation13.Elements[1].getVisibility()));
		Assert.assertEquals(
			"Association cardinality",
			Constants.CARDINALITY_ONE,
			((IAssociation) Aggregation13.Elements[1]).getCardinality());
		Assert.assertEquals(
			"Association name",
			"padl.kernel.impl.Association:java.lang.Object:1",
			Aggregation13.Elements[1].getDisplayName());
		Assert.assertEquals(
			"Association target",
			"java.lang.Object",
			((IAssociation) Aggregation13.Elements[1])
				.getTargetEntity()
				.getDisplayID());
	}
	public void testContainerAggregation0() {
		ClassFilePrimitive.assertAssigable(
			"ContainerAggregation relationship type",
			IContainerAggregation.class,
			Aggregation13.Elements[3].getClass());
		Assert.assertEquals(
			"ContainerAggregation relationship visibility",
			Modifier.toString(Modifier.DEFAULT),
			Modifier.toString(Aggregation13.Elements[3].getVisibility()));
		Assert.assertEquals(
			"ContainerAggregation relationship cardinality",
			Constants.CARDINALITY_MANY,
			((IContainerAggregation) Aggregation13.Elements[3])
				.getCardinality());
		Assert
			.assertEquals(
				"ContainerAggregation relationship name",
				"padl.kernel.impl.ContainerAggregation:padl.example.aggregation.A:2",
				Aggregation13.Elements[3].getDisplayName());
		Assert.assertEquals(
			"ContainerAggregation relationship target",
			"padl.example.aggregation.A",
			((IContainerAggregation) Aggregation13.Elements[3])
				.getTargetEntity()
				.getDisplayID());
	}
	public void testContainerAggregation1() {
		ClassFilePrimitive.assertAssigable(
			"ContainerAggregation relationship type",
			IContainerAggregation.class,
			Aggregation13.Elements[4].getClass());
		Assert.assertEquals(
			"ContainerAggregation relationship visibility",
			Modifier.toString(Modifier.DEFAULT),
			Modifier.toString(Aggregation13.Elements[4].getVisibility()));
		Assert.assertEquals(
			"ContainerAggregation relationship cardinality",
			Constants.CARDINALITY_MANY,
			((IContainerAggregation) Aggregation13.Elements[4])
				.getCardinality());
		Assert
			.assertEquals(
				"ContainerAggregation relationship name",
				"padl.kernel.impl.ContainerAggregation:padl.example.aggregation.A:2",
				Aggregation13.Elements[4].getDisplayName());
		Assert.assertEquals(
			"ContainerAggregation relationship target",
			"padl.example.aggregation.A",
			((IContainerAggregation) Aggregation13.Elements[4])
				.getTargetEntity()
				.getDisplayID());
	}
	//	public void testGetAUseRelationship() {
	//		Aggregation13.assertAssigable(
	//			"Use relationship type",
	//			IUseRelationship.class,
	//			Aggregation13.Elements[10].getClass());
	//		Assert.assertEquals(
	//			"Use relationship visibility",
	//			Modifier.toString(Modifier.DEFAULT),
	//			Modifier.toString(Aggregation13.Elements[10].getVisibility()));
	//		Assert.assertEquals(
	//			"Use relationship cardinality",
	//			1,
	//			((IUseRelationship) Aggregation13.Elements[10]).getCardinality());
	//		Assert.assertEquals(
	//			"Use relationship name",
	//			"getA_3",
	//			Aggregation13.Elements[10].getDisplayName());
	//		Assert.assertEquals(
	//			"Use relationship target",
	//			"padl.example.aggregation.A",
	//			((IUseRelationship) Aggregation13.Elements[10])
	//				.getTargetEntity()
	//				.getDisplayName());
	//	}
	public void testGetAMethod() {
		Assert.assertEquals(
			"Method getA()",
			"getA",
			Aggregation13.Elements[8].getDisplayName());
	}
	//	public void testGetAnotherAUseRelationship() {
	//		Aggregation13.assertAssigable(
	//			"Use relationship type",
	//			IUseRelationship.class,
	//			Aggregation13.Elements[12].getClass());
	//		Assert.assertEquals(
	//			"Use relationship visibility",
	//			Modifier.toString(Modifier.DEFAULT),
	//			Modifier.toString(Aggregation13.Elements[12].getVisibility()));
	//		Assert.assertEquals(
	//			"Use relationship cardinality",
	//			1,
	//			((IUseRelationship) Aggregation13.Elements[12]).getCardinality());
	//		Assert.assertEquals(
	//			"Use relationship name",
	//			"getAnotherA_4",
	//			Aggregation13.Elements[12].getDisplayName());
	//		Assert.assertEquals(
	//			"Use relationship target",
	//			"padl.example.aggregation.A",
	//			((IUseRelationship) Aggregation13.Elements[12])
	//				.getTargetEntity()
	//				.getDisplayName());
	//	}
	public void testGetAnotherAMethod() {
		Assert.assertEquals(
			"Method getAnotherA()",
			"getAnotherA",
			Aggregation13.Elements[10].getDisplayName());
	}
	public void testInstanceField() {
		Assert.assertEquals(
			"Instance field visibility",
			Modifier.toString(Modifier.PRIVATE),
			Modifier.toString(Aggregation13.Elements[2].getVisibility()));
		Assert.assertEquals(
			"Instance field type",
			"padl.example.aggregation.A",
			((IField) Aggregation13.Elements[2]).getDisplayTypeName());
		Assert.assertEquals(
			"Instance field name",
			"a",
			Aggregation13.Elements[2].getDisplayName());
	}
	public void testMainEntityName() {
		Assert.assertEquals(
			"Entity name",
			"padl.example.aggregation.Aggregation13",
			Aggregation13.FirstClassEntities[2].getDisplayID());
	}
	public void testNumberOfElements() {
		Assert.assertEquals(
			"Number of elements",
			13,
			Aggregation13.Elements.length);
	}
	public void testNumberOfEntities() {
		Assert.assertEquals(
			"Number of entities",
			3,
			Aggregation13.FirstClassEntities.length);
	}
	//	public void testRemoveAUseRelationship() {
	//		Aggregation13.assertAssigable(
	//			"Use relationship type",
	//			IUseRelationship.class,
	//			Aggregation13.Elements[14].getClass());
	//		Assert.assertEquals(
	//			"Use relationship visibility",
	//			Modifier.toString(Modifier.DEFAULT),
	//			Modifier.toString(Aggregation13.Elements[14].getVisibility()));
	//		Assert.assertEquals(
	//			"Use relationship cardinality",
	//			1,
	//			((IUseRelationship) Aggregation13.Elements[14]).getCardinality());
	//		Assert.assertEquals(
	//			"Use relationship name",
	//			"removeA_5",
	//			Aggregation13.Elements[14].getDisplayName());
	//		Assert.assertEquals(
	//			"Use relationship target",
	//			"padl.example.aggregation.A",
	//			((IUseRelationship) Aggregation13.Elements[14])
	//				.getTargetEntity()
	//				.getDisplayName());
	//	}
	public void testRemoveAMethod() {
		Assert.assertEquals(
			"Method removeA()",
			"removeA",
			Aggregation13.Elements[11].getDisplayName());
	}
	//	public void testRemoveAnotherAUseRelationship() {
	//		Aggregation13.assertAssigable(
	//			"Use relationship type",
	//			IUseRelationship.class,
	//			Aggregation13.Elements[16].getClass());
	//		Assert.assertEquals(
	//			"Use relationship visibility",
	//			Modifier.toString(Modifier.DEFAULT),
	//			Modifier.toString(Aggregation13.Elements[16].getVisibility()));
	//		Assert.assertEquals(
	//			"Use relationship cardinality",
	//			1,
	//			((IUseRelationship) Aggregation13.Elements[16]).getCardinality());
	//		Assert.assertEquals(
	//			"Use relationship name",
	//			"removeAnotherA_6",
	//			Aggregation13.Elements[16].getDisplayName());
	//		Assert.assertEquals(
	//			"Use relationship target",
	//			"padl.example.aggregation.A",
	//			((IUseRelationship) Aggregation13.Elements[16])
	//				.getTargetEntity()
	//				.getDisplayName());
	//	}
	public void testRemoveAnotherAMethod() {
		Assert.assertEquals(
			"Method removeAnotherA()",
			"removeAnotherA",
			Aggregation13.Elements[12].getDisplayName());
	}
	//	public void testRemoveAnotherAUseRelationship() {
	//		Aggregation13.assertAssigable(
	//			"Use relationship type",
	//			IUseRelationship.class,
	//			Aggregation13.Elements[16].getClass());
	//		Assert.assertEquals(
	//			"Use relationship visibility",
	//			Modifier.toString(Modifier.DEFAULT),
	//			Modifier.toString(Aggregation13.Elements[16].getVisibility()));
	//		Assert.assertEquals(
	//			"Use relationship cardinality",
	//			1,
	//			((IUseRelationship) Aggregation13.Elements[16]).getCardinality());
	//		Assert.assertEquals(
	//			"Use relationship name",
	//			"removeAnotherA_6",
	//			Aggregation13.Elements[16].getDisplayName());
	//		Assert.assertEquals(
	//			"Association target",
	//			"padl.example.aggregation.A",
	//			((IUseRelationship) Aggregation13.Elements[16])
	//				.getTargetEntity()
	//				.getDisplayName());
	//	}
}
