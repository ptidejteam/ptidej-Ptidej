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
 * @version	0.2
 * @author 	Yann-Gaël Guéhéneuc
 * @since	2002/08/15
 */
public class Aggregations extends ClassFilePrimitive {
	private static IFirstClassEntity[] FirstClassEntities;

	public Aggregations(final String name) {
		super(name);
	}
	protected void setUp() throws CreationException,
			UnsupportedSourceModelException {
		if (Aggregations.FirstClassEntities == null) {
			final ICodeLevelModel codeLevelModel =
				ClassFilePrimitive.getFactory().createCodeLevelModel(
					"padl.example.aggregation");
			codeLevelModel
				.create(new CompleteClassFileCreator(
					new String[] { "../PADL Creator ClassFile/target/test-classes/padl/example/aggregation/" }));

			final IIdiomLevelModel idiomLevelModel =
				(IIdiomLevelModel) new AACRelationshipsAnalysis()
					.invoke(codeLevelModel);

			Aggregations.FirstClassEntities =
				Util.getArrayOfTopLevelEntities(idiomLevelModel);
		}
	}
	public void testAggregation12() {
		Assert.assertEquals(
			"Aggregation 12",
			"padl.example.aggregation.Aggregation12",
			Aggregations.FirstClassEntities[6].getDisplayID());

		final IElement[] elements =
			Util.getArrayOfElements(Aggregations.FirstClassEntities[6]);

		Assert.assertEquals("Identified elements", 9, elements.length);

		Assert.assertEquals(
			"Constructor",
			"Aggregation12",
			elements[0].getDisplayName());

		ClassFilePrimitive.assertAssigable(
			"Association type",
			IAssociation.class,
			elements[1].getClass());
		Assert.assertEquals(
			"Association visibility",
			Modifier.toString(Modifier.PUBLIC),
			Modifier.toString(elements[1].getVisibility()));
		Assert.assertEquals(
			"Association cardinality",
			1,
			((IAssociation) elements[1]).getCardinality());
		Assert.assertEquals(
			"Association name",
			"padl.kernel.impl.Association:java.lang.Object:1",
			elements[1].getDisplayName());
		Assert.assertEquals(
			"Association target",
			"java.lang.Object",
			((IAssociation) elements[1]).getTargetEntity().getDisplayID());

		Assert.assertEquals(
			"Instance field visibility",
			Modifier.PRIVATE,
			elements[2].getVisibility());
		Assert.assertEquals(
			"Instance field type",
			"padl.example.aggregation.A",
			((IField) elements[2]).getDisplayTypeName());
		Assert.assertEquals(
			"Instance field name",
			"a",
			elements[2].getDisplayName());

		ClassFilePrimitive.assertAssigable(
			"Container aggregation type",
			IContainerAggregation.class,
			elements[3].getClass());
		Assert.assertEquals(
			"Container aggregation visibility",
			Modifier.toString(Modifier.DEFAULT),
			Modifier.toString(elements[3].getVisibility()));
		Assert.assertEquals(
			"Container aggregation cardinality",
			2,
			((IContainerAggregation) elements[3]).getCardinality());
		Assert
			.assertEquals(
				"Container aggregation name",
				"padl.kernel.impl.ContainerAggregation:padl.example.aggregation.A:2",
				elements[3].getDisplayName());
		Assert.assertEquals(
			"Container aggregation target",
			"padl.example.aggregation.A",
			((IContainerAggregation) elements[3])
				.getTargetEntity()
				.getDisplayID());

		Assert.assertEquals(
			"Method addA()",
			"addA",
			elements[4].getDisplayName());

		ClassFilePrimitive.assertAssigable(
			"Use relationship type",
			IUseRelationship.class,
			elements[5].getClass());
		Assert.assertEquals(
			"Use relationship visibility",
			Modifier.toString(Modifier.DEFAULT),
			Modifier.toString(elements[5].getVisibility()));
		Assert.assertEquals(
			"Use relationship cardinality",
			1,
			((IUseRelationship) elements[5]).getCardinality());
		Assert.assertEquals(
			"Use relationship name",
			"padl.kernel.impl.UseRelationship:padl.example.aggregation.A:1",
			elements[5].getDisplayName());
		Assert.assertEquals(
			"Use relationship target",
			"padl.example.aggregation.A",
			((IUseRelationship) elements[5]).getTargetEntity().getDisplayID());

		Assert.assertEquals(
			"Method getA()",
			"getA",
			elements[6].getDisplayName());

		Assert.assertEquals(
			"Method removeA()",
			"removeA",
			elements[8].getDisplayName());
	}
	public void testAggregation2() {
		Assert.assertEquals(
			"Aggregation 2",
			"padl.example.aggregation.Aggregation2",
			Aggregations.FirstClassEntities[9].getDisplayID());

		final IElement[] elements =
			Util.getArrayOfElements(Aggregations.FirstClassEntities[9]);

		Assert.assertEquals("Identified elements", 5, elements.length);

		Assert.assertEquals(
			"Constructor",
			"Aggregation2",
			elements[0].getDisplayName());

		ClassFilePrimitive.assertAssigable(
			"Association type",
			IAssociation.class,
			elements[1].getClass());
		Assert.assertEquals(
			"Association visibility",
			Modifier.toString(Modifier.PUBLIC),
			Modifier.toString(elements[1].getVisibility()));
		Assert.assertEquals(
			"Association cardinality",
			Constants.CARDINALITY_ONE,
			((IAssociation) elements[1]).getCardinality());
		Assert.assertEquals(
			"Association name",
			"padl.kernel.impl.Association:java.lang.Object:1",
			elements[1].getDisplayName());
		Assert.assertEquals(
			"Association target",
			"java.lang.Object",
			((IAssociation) elements[1]).getTargetEntity().getDisplayID());

		Assert.assertEquals(
			"Instance field visibility",
			Modifier.PRIVATE,
			elements[2].getVisibility());
		Assert.assertEquals(
			"Instance field type",
			"padl.example.aggregation.A",
			((IField) elements[2]).getDisplayTypeName());
		Assert.assertEquals(
			"Instance field name",
			"a",
			elements[2].getDisplayName());

		Assert.assertEquals("Method", "getA", elements[3].getDisplayName());

		ClassFilePrimitive.assertAssigable(
			"Use relationship type",
			IAssociation.class,
			elements[4].getClass());
		Assert.assertEquals(
			"Use relationship visibility",
			Modifier.toString(Modifier.DEFAULT),
			Modifier.toString(elements[4].getVisibility()));
		Assert.assertEquals(
			"Use relationship cardinality",
			Constants.CARDINALITY_ONE,
			((IAssociation) elements[4]).getCardinality());
		Assert.assertEquals(
			"Use relationship name",
			"padl.kernel.impl.Association:padl.example.aggregation.A:1",
			elements[4].getDisplayName());
		Assert.assertEquals(
			"Use relationship target",
			"padl.example.aggregation.A",
			((IAssociation) elements[4]).getTargetEntity().getDisplayID());
	}
	public void testAggregation3() {
		Assert.assertEquals(
			"Aggregation 3",
			"padl.example.aggregation.Aggregation3",
			Aggregations.FirstClassEntities[10].getDisplayID());

		final IElement[] elements =
			Util.getArrayOfElements(Aggregations.FirstClassEntities[10]);

		Assert.assertEquals("Identified elements", 5, elements.length);

		Assert.assertEquals(
			"Constructor",
			"Aggregation3",
			elements[0].getDisplayName());

		ClassFilePrimitive.assertAssigable(
			"Association type",
			IAssociation.class,
			elements[1].getClass());
		Assert.assertEquals(
			"Association visibility",
			Modifier.toString(Modifier.PUBLIC),
			Modifier.toString(elements[1].getVisibility()));
		Assert.assertEquals(
			"Association cardinality",
			Constants.CARDINALITY_ONE,
			((IAssociation) elements[1]).getCardinality());
		Assert.assertEquals(
			"Association name",
			"padl.kernel.impl.Association:java.lang.Object:1",
			elements[1].getDisplayName());
		Assert.assertEquals(
			"Association target",
			"java.lang.Object",
			((IAssociation) elements[1]).getTargetEntity().getDisplayID());

		Assert.assertEquals(
			"Instance field visibility",
			Modifier.PRIVATE,
			elements[2].getVisibility());
		Assert.assertEquals(
			"Instance field type",
			"padl.example.aggregation.A",
			((IField) elements[2]).getDisplayTypeName());
		Assert.assertEquals(
			"Instance field name",
			"a",
			elements[2].getDisplayName());

		Assert.assertEquals(
			"Method setA()",
			"setA",
			elements[3].getDisplayName());

		ClassFilePrimitive.assertAssigable(
			"Use relationship type",
			IUseRelationship.class,
			elements[4].getClass());
		Assert.assertEquals(
			"Use relationship visibility",
			Modifier.toString(Modifier.DEFAULT),
			Modifier.toString(elements[4].getVisibility()));
		Assert.assertEquals(
			"Use relationship cardinality",
			1,
			((IUseRelationship) elements[4]).getCardinality());
		Assert.assertEquals(
			"Use relationship name",
			"padl.kernel.impl.UseRelationship:padl.example.aggregation.A:1",
			elements[4].getDisplayName());
		Assert.assertEquals(
			"Use relationship target",
			"padl.example.aggregation.A",
			((IUseRelationship) elements[4]).getTargetEntity().getDisplayID());
	}
	public void testAggregation4() {
		Assert.assertEquals(
			"Aggregation 4",
			"padl.example.aggregation.Aggregation4",
			Aggregations.FirstClassEntities[11].getDisplayID());

		final IElement[] elements =
			Util.getArrayOfElements(Aggregations.FirstClassEntities[11]);

		Assert.assertEquals("Identified elements", 9, elements.length);

		Assert.assertEquals(
			"Constructor",
			"Aggregation4",
			elements[0].getDisplayName());

		ClassFilePrimitive.assertAssigable(
			"Association type",
			IAssociation.class,
			elements[1].getClass());
		Assert.assertEquals(
			"Association visibility",
			Modifier.toString(Modifier.PUBLIC),
			Modifier.toString(elements[1].getVisibility()));
		Assert.assertEquals(
			"Association cardinality",
			1,
			((IAssociation) elements[1]).getCardinality());
		Assert.assertEquals(
			"Association name",
			"padl.kernel.impl.Association:java.lang.Object:1",
			elements[1].getDisplayName());
		Assert.assertEquals(
			"Association target",
			"java.lang.Object",
			((IAssociation) elements[1]).getTargetEntity().getDisplayID());

		Assert.assertEquals(
			"Instance field visibility",
			Modifier.PRIVATE,
			elements[2].getVisibility());
		Assert.assertEquals(
			"Instance field type",
			"padl.example.aggregation.A",
			((IField) elements[2]).getDisplayTypeName());
		Assert.assertEquals(
			"Instance field name",
			"a",
			elements[2].getDisplayName());

		ClassFilePrimitive.assertAssigable(
			"ContainerAggregation link type",
			IContainerAggregation.class,
			elements[3].getClass());
		Assert.assertEquals(
			"ContainerAggregation link visibility",
			Modifier.toString(Modifier.DEFAULT),
			Modifier.toString(elements[3].getVisibility()));
		Assert.assertEquals(
			"ContainerAggregation link cardinality",
			2,
			((IContainerAggregation) elements[3]).getCardinality());
		Assert
			.assertEquals(
				"ContainerAggregation link name",
				"padl.kernel.impl.ContainerAggregation:padl.example.aggregation.A:2",
				elements[3].getDisplayName());
		Assert.assertEquals(
			"ContainerAggregation link target",
			"padl.example.aggregation.A",
			((IContainerAggregation) elements[3])
				.getTargetEntity()
				.getDisplayID());

		Assert.assertEquals(
			"Method addA()",
			"addA",
			elements[4].getDisplayName());

		ClassFilePrimitive.assertAssigable(
			"Use relationship type",
			IUseRelationship.class,
			elements[5].getClass());
		Assert.assertEquals(
			"Use relationship visibility",
			Modifier.toString(Modifier.DEFAULT),
			Modifier.toString(elements[5].getVisibility()));
		Assert.assertEquals(
			"Use relationship cardinality",
			1,
			((IUseRelationship) elements[5]).getCardinality());
		Assert
			.assertEquals(
				"Use relationship name",
				"padl.kernel.impl.UseRelationship:padl.example.aggregation.A:1+padl.kernel.impl.UseRelationship:padl.example.aggregation.A:1",
				elements[5].getDisplayName());
		Assert.assertEquals(
			"Use relationship target",
			"padl.example.aggregation.A",
			((IUseRelationship) elements[5]).getTargetEntity().getDisplayID());

		Assert.assertEquals(
			"Method getA()",
			"getA",
			elements[6].getDisplayName());

		Assert.assertEquals(
			"Method removeA()",
			"removeA",
			elements[8].getDisplayName());
	}
	public void testAggregation5() {
		Assert.assertEquals(
			"Aggregation 5",
			"padl.example.aggregation.Aggregation5",
			Aggregations.FirstClassEntities[12].getDisplayID());

		final IElement[] elements =
			Util.getArrayOfElements(Aggregations.FirstClassEntities[12]);

		Assert.assertEquals("Identified elements", 8, elements.length);

		Assert.assertEquals(
			"Constructor",
			"Aggregation5",
			elements[0].getDisplayName());

		ClassFilePrimitive.assertAssigable(
			"Association type",
			IAssociation.class,
			elements[1].getClass());
		Assert.assertEquals(
			"Association visibility",
			Modifier.toString(Modifier.PUBLIC),
			Modifier.toString(elements[1].getVisibility()));
		Assert.assertEquals(
			"Association cardinality",
			1,
			((IAssociation) elements[1]).getCardinality());
		Assert.assertEquals(
			"Association name",
			"padl.kernel.impl.Association:java.lang.Object:1",
			elements[1].getDisplayName());
		Assert.assertEquals(
			"Association target",
			"java.lang.Object",
			((IAssociation) elements[1]).getTargetEntity().getDisplayID());

		Assert.assertEquals(
			"Instance field visibility",
			Modifier.PRIVATE,
			elements[2].getVisibility());
		Assert.assertEquals(
			"Instance field type",
			"padl.example.aggregation.A",
			((IField) elements[2]).getDisplayTypeName());
		Assert.assertEquals(
			"Instance field name",
			"a",
			elements[2].getDisplayName());

		ClassFilePrimitive.assertAssigable(
			"Container aggregation type",
			IContainerAggregation.class,
			elements[3].getClass());
		Assert.assertEquals(
			"Container aggregation visibility",
			Modifier.toString(Modifier.DEFAULT),
			Modifier.toString(elements[3].getVisibility()));
		Assert.assertEquals(
			"Container aggregation cardinality",
			2,
			((IContainerAggregation) elements[3]).getCardinality());
		Assert
			.assertEquals(
				"Container aggregation name",
				"padl.kernel.impl.ContainerAggregation:padl.example.aggregation.A:2",
				elements[3].getDisplayName());
		Assert.assertEquals(
			"Container aggregation target",
			"padl.example.aggregation.A",
			((IContainerAggregation) elements[3])
				.getTargetEntity()
				.getDisplayID());

		Assert.assertEquals(
			"Method addA()",
			"addA",
			elements[4].getDisplayName());

		ClassFilePrimitive.assertAssigable(
			"Use relationship type",
			IUseRelationship.class,
			elements[5].getClass());
		Assert.assertEquals(
			"Use relationship visibility",
			Modifier.toString(Modifier.DEFAULT),
			Modifier.toString(elements[5].getVisibility()));
		Assert.assertEquals(
			"Use relationship cardinality",
			1,
			((IUseRelationship) elements[5]).getCardinality());
		Assert.assertEquals(
			"Use relationship name",
			"padl.kernel.impl.UseRelationship:padl.example.aggregation.A:1",
			elements[5].getDisplayName());
		Assert.assertEquals(
			"Use relationship target",
			"padl.example.aggregation.A",
			((IUseRelationship) elements[5]).getTargetEntity().getDisplayID());

		Assert.assertEquals(
			"Method getA()",
			"getA",
			elements[6].getDisplayName());
	}
	public void testAggregation6() {
		Assert.assertEquals(
			"Aggregation 6",
			"padl.example.aggregation.Aggregation6",
			Aggregations.FirstClassEntities[13].getDisplayID());

		final IElement[] elements =
			Util.getArrayOfElements(Aggregations.FirstClassEntities[13]);

		Assert.assertEquals("Identified elements", 6, elements.length);

		Assert.assertEquals(
			"Constructor",
			"Aggregation6",
			elements[0].getDisplayName());

		ClassFilePrimitive.assertAssigable(
			"Association type",
			IAssociation.class,
			elements[1].getClass());
		Assert.assertEquals(
			"Association visibility",
			Modifier.toString(Modifier.PUBLIC),
			Modifier.toString(elements[1].getVisibility()));
		Assert.assertEquals(
			"Association cardinality",
			Constants.CARDINALITY_ONE,
			((IAssociation) elements[1]).getCardinality());
		Assert.assertEquals(
			"Association name",
			"padl.kernel.impl.Association:java.lang.Object:1",
			elements[1].getDisplayName());
		Assert.assertEquals(
			"Association target",
			"java.lang.Object",
			((IAssociation) elements[1]).getTargetEntity().getDisplayID());

		ClassFilePrimitive.assertAssigable(
			"Association type",
			IAssociation.class,
			elements[1].getClass());
		Assert.assertEquals(
			"Association visibility",
			Modifier.toString(Modifier.PUBLIC),
			Modifier.toString(elements[1].getVisibility()));
		Assert.assertEquals(
			"Association cardinality",
			1,
			((IAssociation) elements[1]).getCardinality());
		Assert.assertEquals(
			"Association name",
			"padl.kernel.impl.Association:java.lang.Object:1",
			elements[1].getDisplayName());
		Assert.assertEquals(
			"Association target",
			"java.lang.Object",
			((IAssociation) elements[1]).getTargetEntity().getDisplayID());

		Assert.assertEquals(
			"Instance field visibility",
			Modifier.PRIVATE,
			elements[2].getVisibility());
		Assert.assertEquals(
			"Instance field type",
			"padl.example.aggregation.A",
			((IField) elements[2]).getDisplayTypeName());
		Assert.assertEquals(
			"Instance field name",
			"a",
			elements[2].getDisplayName());

		Assert.assertEquals("Method", "addA", elements[3].getDisplayName());

		ClassFilePrimitive.assertAssigable(
			"Use relationship type",
			IUseRelationship.class,
			elements[4].getClass());
		Assert.assertEquals(
			"Use relationship visibility",
			Modifier.toString(Modifier.DEFAULT),
			Modifier.toString(elements[4].getVisibility()));
		Assert.assertEquals(
			"Use relationship cardinality",
			1,
			((IUseRelationship) elements[4]).getCardinality());
		Assert.assertEquals(
			"Use relationship name",
			"padl.kernel.impl.UseRelationship:padl.example.aggregation.A:1",
			elements[4].getDisplayName());
		Assert.assertEquals(
			"Use relationship target",
			"padl.example.aggregation.A",
			((IUseRelationship) elements[4]).getTargetEntity().getDisplayID());

		Assert.assertEquals("Method", "removeA", elements[5].getDisplayName());
	}
	public void testAggregation7() {
		Assert.assertEquals(
			"Aggregation 7",
			"padl.example.aggregation.Aggregation7",
			Aggregations.FirstClassEntities[14].getDisplayID());

		final IElement[] elements =
			Util.getArrayOfElements(Aggregations.FirstClassEntities[14]);

		Assert.assertEquals("Identified elements", 6, elements.length);

		Assert.assertEquals(
			"Constructor",
			"Aggregation7",
			elements[0].getDisplayName());

		ClassFilePrimitive.assertAssigable(
			"Association type",
			IAssociation.class,
			elements[1].getClass());
		Assert.assertEquals(
			"Association visibility",
			Modifier.toString(Modifier.PUBLIC),
			Modifier.toString(elements[1].getVisibility()));
		Assert.assertEquals(
			"Association cardinality",
			Constants.CARDINALITY_ONE,
			((IAssociation) elements[1]).getCardinality());
		Assert.assertEquals(
			"Association name",
			"padl.kernel.impl.Association:java.lang.Object:1",
			elements[1].getDisplayName());
		Assert.assertEquals(
			"Association target",
			"java.lang.Object",
			((IAssociation) elements[1]).getTargetEntity().getDisplayID());

		Assert.assertEquals(
			"Instance field visibility",
			Modifier.PRIVATE,
			elements[2].getVisibility());
		Assert.assertEquals(
			"Instance field type",
			"padl.example.aggregation.A",
			((IField) elements[2]).getDisplayTypeName());
		Assert.assertEquals(
			"Instance field name",
			"a",
			elements[2].getDisplayName());

		Assert.assertEquals("Method", "getA", elements[3].getDisplayName());

		ClassFilePrimitive.assertAssigable(
			"Use relationship type",
			IAssociation.class,
			elements[4].getClass());
		Assert.assertEquals(
			"Use relationship visibility",
			Modifier.toString(Modifier.DEFAULT),
			Modifier.toString(elements[4].getVisibility()));
		Assert.assertEquals(
			"Use relationship cardinality",
			Constants.CARDINALITY_ONE,
			((IAssociation) elements[4]).getCardinality());
		Assert.assertEquals(
			"Use relationship name",
			"padl.kernel.impl.Association:padl.example.aggregation.A:1",
			elements[4].getDisplayName());
		Assert.assertEquals(
			"Use relationship target",
			"padl.example.aggregation.A",
			((IAssociation) elements[4]).getTargetEntity().getDisplayID());

		Assert.assertEquals("Method", "removeA", elements[5].getDisplayName());
	}
	public void testAggregation8() {
		Assert.assertEquals(
			"Aggregation 8",
			"padl.example.aggregation.Aggregation8",
			Aggregations.FirstClassEntities[15].getDisplayID());

		final IElement[] elements =
			Util.getArrayOfElements(Aggregations.FirstClassEntities[15]);

		Assert.assertEquals("Identified elements", 5, elements.length);

		Assert.assertEquals(
			"Constructor",
			"Aggregation8",
			elements[0].getDisplayName());

		ClassFilePrimitive.assertAssigable(
			"Association type",
			IAssociation.class,
			elements[1].getClass());
		Assert.assertEquals(
			"Association visibility",
			Modifier.toString(Modifier.PUBLIC),
			Modifier.toString(elements[1].getVisibility()));
		Assert.assertEquals(
			"Association cardinality",
			Constants.CARDINALITY_ONE,
			((IAssociation) elements[1]).getCardinality());
		Assert.assertEquals(
			"Association name",
			"padl.kernel.impl.Association:java.lang.Object:1",
			elements[1].getDisplayName());
		Assert.assertEquals(
			"Association target",
			"java.lang.Object",
			((IAssociation) elements[1]).getTargetEntity().getDisplayID());

		Assert.assertEquals(
			"Instance field visibility",
			Modifier.PRIVATE,
			elements[2].getVisibility());
		Assert.assertEquals(
			"Instance field type",
			"padl.example.aggregation.A",
			((IField) elements[2]).getDisplayTypeName());
		Assert.assertEquals(
			"Instance field name",
			"a",
			elements[2].getDisplayName());

		Assert.assertEquals("Method", "addA", elements[3].getDisplayName());

		ClassFilePrimitive.assertAssigable(
			"Use relationship type",
			IUseRelationship.class,
			elements[4].getClass());
		Assert.assertEquals(
			"Use relationship visibility",
			Modifier.toString(Modifier.DEFAULT),
			Modifier.toString(elements[4].getVisibility()));
		Assert.assertEquals(
			"Use relationship cardinality",
			1,
			((IUseRelationship) elements[4]).getCardinality());
		Assert.assertEquals(
			"Use relationship name",
			"padl.kernel.impl.UseRelationship:padl.example.aggregation.A:1",
			elements[4].getDisplayName());
		Assert.assertEquals(
			"Use relationship target",
			"padl.example.aggregation.A",
			((IUseRelationship) elements[4]).getTargetEntity().getDisplayID());
	}
	public void testAggregation9() {
		Assert.assertEquals(
			"Aggregation 9",
			"padl.example.aggregation.Aggregation9",
			Aggregations.FirstClassEntities[16].getDisplayID());

		final IElement[] elements =
			Util.getArrayOfElements(Aggregations.FirstClassEntities[16]);

		Assert.assertEquals("Identified elements", 9, elements.length);

		Assert.assertEquals(
			"Constructor",
			"Aggregation9",
			elements[0].getDisplayName());

		ClassFilePrimitive.assertAssigable(
			"Association type",
			IAssociation.class,
			elements[1].getClass());
		Assert.assertEquals(
			"Association visibility",
			Modifier.toString(Modifier.PUBLIC),
			Modifier.toString(elements[1].getVisibility()));
		Assert.assertEquals(
			"Association cardinality",
			1,
			((IAssociation) elements[1]).getCardinality());
		Assert.assertEquals(
			"Association name",
			"padl.kernel.impl.Association:java.lang.Object:1",
			elements[1].getDisplayName());
		Assert.assertEquals(
			"Association target",
			"java.lang.Object",
			((IAssociation) elements[1]).getTargetEntity().getDisplayID());

		Assert.assertEquals(
			"Instance field visibility",
			Modifier.PRIVATE,
			elements[2].getVisibility());
		Assert.assertEquals(
			"Instance field type",
			"padl.example.aggregation.A",
			((IField) elements[2]).getDisplayTypeName());
		Assert.assertEquals(
			"Instance field name",
			"a",
			elements[2].getDisplayName());

		ClassFilePrimitive.assertAssigable(
			"ContainerAggregation link type",
			IContainerAggregation.class,
			elements[3].getClass());
		Assert.assertEquals(
			"ContainerAggregation link visibility",
			Modifier.toString(Modifier.DEFAULT),
			Modifier.toString(elements[3].getVisibility()));
		Assert.assertEquals(
			"ContainerAggregation link cardinality",
			2,
			((IContainerAggregation) elements[3]).getCardinality());
		Assert
			.assertEquals(
				"ContainerAggregation link name",
				"padl.kernel.impl.ContainerAggregation:padl.example.aggregation.A:2",
				elements[3].getDisplayName());
		Assert.assertEquals(
			"ContainerAggregation link target",
			"padl.example.aggregation.A",
			((IContainerAggregation) elements[3])
				.getTargetEntity()
				.getDisplayID());

		Assert.assertEquals(
			"Method addToA()",
			"addToA",
			elements[4].getDisplayName());

		ClassFilePrimitive.assertAssigable(
			"Use relationship type",
			IUseRelationship.class,
			elements[5].getClass());
		Assert.assertEquals(
			"Use relationship visibility",
			Modifier.toString(Modifier.DEFAULT),
			Modifier.toString(elements[5].getVisibility()));
		Assert.assertEquals(
			"Use relationship cardinality",
			1,
			((IUseRelationship) elements[5]).getCardinality());
		Assert
			.assertEquals(
				"Use relationship name",
				"padl.kernel.impl.UseRelationship:padl.example.aggregation.A:1+padl.kernel.impl.UseRelationship:padl.example.aggregation.A:1",
				elements[5].getDisplayName());
		Assert.assertEquals(
			"Use relationship target",
			"padl.example.aggregation.A",
			((IUseRelationship) elements[5]).getTargetEntity().getDisplayID());

		Assert.assertEquals(
			"Method getA()",
			"getA",
			elements[6].getDisplayName());

		Assert.assertEquals(
			"Method removeFromA()",
			"removeFromA",
			elements[8].getDisplayName());
	}
	public void testEntity1() {
		Assert.assertEquals(
			"Entity 1",
			"padl.example.aggregation.A",
			Aggregations.FirstClassEntities[2].getDisplayID());

		final IElement[] elements =
			Util.getArrayOfElements(Aggregations.FirstClassEntities[2]);

		Assert.assertEquals("Identified elements", 4, elements.length);

		Assert.assertEquals("Constructor", "A", elements[0].getDisplayName());

		ClassFilePrimitive.assertAssigable(
			"Association type",
			IAssociation.class,
			elements[1].getClass());
		Assert.assertEquals(
			"Association visibility",
			Modifier.toString(Modifier.PUBLIC),
			Modifier.toString(elements[1].getVisibility()));
		Assert.assertEquals(
			"Association cardinality",
			Constants.CARDINALITY_ONE,
			((IAssociation) elements[1]).getCardinality());
		Assert.assertEquals(
			"Association name",
			"padl.kernel.impl.Association:java.lang.Object:1",
			elements[1].getDisplayName());
		Assert.assertEquals(
			"Association target",
			"java.lang.Object",
			((IAssociation) elements[1]).getTargetEntity().getDisplayID());

		Assert.assertEquals(
			"Constructor",
			"instanceMethod",
			elements[2].getDisplayName());

		Assert.assertEquals(
			"Constructor",
			"staticMethod",
			elements[3].getDisplayName());
	}
	public void testEntity2() {
		Assert.assertEquals(
			"Entity 2",
			"java.lang.Object",
			Aggregations.FirstClassEntities[0].getDisplayID());

		final IElement[] elements =
			Util.getArrayOfElements(Aggregations.FirstClassEntities[0]);
		// Farouk 2004/04/01: Method invocations.
		// (This is not a joke.) The value 0 has been replaced by 1.
		// The method <init>() has been added to java.lang.Object.
		Assert.assertEquals("Identified elements", 1, elements.length);
	}
	public void testEntity3() {
		Assert.assertEquals(
			"Entity 3",
			"java.util.List",
			Aggregations.FirstClassEntities[1].getDisplayID());

		final IElement[] elements =
			Util.getArrayOfElements(Aggregations.FirstClassEntities[1]);
		// Farouk 2004/04/01: Method invocations.
		// (This is not a joke.) The value 0 has been replaced by 4.
		// The methods (two add() and two remove()) have been added
		// to java.util.List.
		Assert.assertEquals("Identified elements", 4, elements.length);
	}
	public void testNumberOfEntities() {
		Assert.assertEquals(
			"Number of entities",
			17,
			Aggregations.FirstClassEntities.length);
	}
}
