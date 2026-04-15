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
import padl.kernel.IAggregation;
import padl.kernel.IAssociation;
import padl.kernel.ICodeLevelModel;
import padl.kernel.ICreation;
import padl.kernel.IElement;
import padl.kernel.IField;
import padl.kernel.IFirstClassEntity;
import padl.kernel.IIdiomLevelModel;
import padl.kernel.IUseRelationship;
import padl.kernel.exception.CreationException;
import padl.util.Util;

/**
 * @version	0.2
 * @author 	Yann-Gaël Guéhéneuc
 * @since	2002/08/15
 */
public class RelationshipsTest extends ClassFilePrimitive {
	private static IFirstClassEntity[] FirstClassEntities;

	public RelationshipsTest(final String name) {
		super(name);
	}

	protected void setUp()
			throws CreationException, UnsupportedSourceModelException {

		if (RelationshipsTest.FirstClassEntities == null) {
			final ICodeLevelModel codeLevelModel = ClassFilePrimitive
					.getFactory()
					.createCodeLevelModel("padl.example.relationship");
			codeLevelModel.create(new CompleteClassFileCreator(new String[] {
					"../PADL Creator ClassFile/target/test-classes/padl/example/relationship/" }));

			final IIdiomLevelModel idiomLevelModel = (IIdiomLevelModel) new AACRelationshipsAnalysis()
					.invoke(codeLevelModel);

			RelationshipsTest.FirstClassEntities = Util
					.getArrayOfTopLevelEntities(idiomLevelModel);
		}
	}

	public void testCreationLink_INSTANCE_CREATION_2() {
		int index;
		for (index = 0; !RelationshipsTest.FirstClassEntities[index]
				.getDisplayID()
				.equals("padl.example.relationship.CreationLink_INSTANCE_CREATION_2"); index++)
			;
		final IElement[] elements = Util.getArrayOfElements(
				RelationshipsTest.FirstClassEntities[index]);

		Assert.assertEquals("Identified elements", 4, elements.length);

		Assert.assertEquals("Constructor", "CreationLink_INSTANCE_CREATION_2",
				elements[0].getDisplayName());

		ClassFilePrimitive.assertAssigable("Association relationship type",
				IAssociation.class, elements[1].getClass());
		Assert.assertEquals("Association relationship visibility",
				Access.getAsString(Access.ACC_PUBLIC),
				Access.getAsString(elements[1].getVisibility()));
		Assert.assertEquals("Association relationship cardinality",
				Constants.CARDINALITY_ONE,
				((IAssociation) elements[1]).getCardinality());
		Assert.assertEquals("Association relationship name",
				"padl.kernel.impl.Association:java.lang.Object:1",
				elements[1].getDisplayName());
		Assert.assertEquals("Association relationship target",
				"java.lang.Object",
				((IAssociation) elements[1]).getTargetEntity().getDisplayID());

		Assert.assertEquals("Method", "foo", elements[2].getDisplayName());

		ClassFilePrimitive.assertAssigable("Creation relationship type",
				ICreation.class, elements[3].getClass());
		Assert.assertEquals("Creation relationship visibility",
				Access.getAsString(Access.ACC_PUBLIC),
				Access.getAsString(elements[3].getVisibility()));
		Assert.assertEquals("Creation relationship cardinality",
				Constants.CARDINALITY_ONE,
				((ICreation) elements[3]).getCardinality());
		Assert.assertEquals("Creation relationship name",
				"padl.kernel.impl.Creation:padl.example.relationship.A:1",
				elements[3].getDisplayName());
		Assert.assertEquals("Creation relationship target",
				"padl.example.relationship.A",
				((ICreation) elements[3]).getTargetEntity().getDisplayID());
	}

	public void testEntity1() {
		final IFirstClassEntity entity = RelationshipsTest.FirstClassEntities[9];
		Assert.assertEquals("Entity 1", "padl.example.relationship.A",
				entity.getDisplayID());

		final IElement[] elements = Util.getArrayOfElements(entity);
		Assert.assertEquals("Identified elements", 4, elements.length);

		Assert.assertEquals("Constructor", "A", elements[0].getDisplayName());

		Assert.assertEquals("Association",
				"padl.kernel.impl.Association:java.lang.Object:1",
				elements[1].getDisplayName());

		Assert.assertEquals("Method", "instanceMethod",
				elements[2].getDisplayName());

		Assert.assertEquals("Method", "staticMethod",
				elements[3].getDisplayName());
	}

	public void testEntity3() {
		final IFirstClassEntity entity = RelationshipsTest.FirstClassEntities[11];
		Assert.assertEquals("Entity 3",
				"padl.example.relationship.Aggregation_CLASS_INSTANCE_FROM_FIELD_2",
				entity.getDisplayID());

		final IElement[] elements = Util.getArrayOfElements(entity);
		Assert.assertEquals("Identified elements", 7, elements.length);

		Assert.assertEquals("Constructor",
				"Aggregation_CLASS_INSTANCE_FROM_FIELD_2",
				elements[0].getDisplayName());

		ClassFilePrimitive.assertAssigable("Association relationship type",
				IAssociation.class, elements[1].getClass());
		Assert.assertEquals("Association relationship visibility",
				Access.getAsString(Access.ACC_PUBLIC),
				Access.getAsString(elements[1].getVisibility()));
		Assert.assertEquals("Association relationship cardinality",
				Constants.CARDINALITY_ONE,
				((IAssociation) elements[1]).getCardinality());
		Assert.assertEquals("Association relationship name",
				"padl.kernel.impl.Association:java.lang.Object:1",
				elements[1].getDisplayName());
		Assert.assertEquals("Association relationship target",
				"java.lang.Object",
				((IAssociation) elements[1]).getTargetEntity().getDisplayID());

		Assert.assertEquals("Static field visibility",
				Access.getAsString(Access.ACC_PRIVATE | Access.ACC_STATIC),
				Access.getAsString(elements[2].getVisibility()));
		Assert.assertEquals("Static field type", "padl.example.relationship.A",
				((IField) elements[2]).getDisplayTypeName());
		Assert.assertEquals("Static field name", "a",
				elements[2].getDisplayName());

		Assert.assertEquals("Method", "foo", elements[3].getDisplayName());

		ClassFilePrimitive.assertAssigable("Use relationship type",
				IUseRelationship.class, elements[4].getClass());
		Assert.assertEquals("Use relationship visibility",
				Access.getAsString(Access.ACC_PUBLIC | Access.ACC_STATIC),
				Access.getAsString(elements[4].getVisibility()));
		Assert.assertEquals("Use relationship cardinality",
				Constants.CARDINALITY_ONE,
				((IUseRelationship) elements[4]).getCardinality());
		Assert.assertEquals("Use relationship name",
				"padl.kernel.impl.UseRelationship:padl.example.relationship.Aggregation_CLASS_INSTANCE_FROM_FIELD_2:1",
				elements[4].getDisplayName());
		Assert.assertEquals("Use relationship target",
				"padl.example.relationship.Aggregation_CLASS_INSTANCE_FROM_FIELD_2",
				((IUseRelationship) elements[4]).getTargetEntity()
						.getDisplayID());

		ClassFilePrimitive.assertAssigable("Aggregation relationship type",
				IAggregation.class, elements[5].getClass());
		Assert.assertEquals("Aggregation relationship visibility",
				Access.getAsString(Access.ACC_PUBLIC | Access.ACC_STATIC),
				Access.getAsString(elements[5].getVisibility()));
		Assert.assertEquals("Aggregation relationship cardinality",
				Constants.CARDINALITY_ONE,
				((IAggregation) elements[5]).getCardinality());
		Assert.assertEquals("Aggregation relationship name",
				"padl.kernel.impl.Aggregation:padl.example.relationship.A:1",
				elements[5].getDisplayName());
		Assert.assertEquals("Aggregation relationship target",
				"padl.example.relationship.A",
				((IAggregation) elements[5]).getTargetEntity().getDisplayID());

		ClassFilePrimitive.assertAssigable("Association relationship type",
				IUseRelationship.class, elements[6].getClass());
		Assert.assertEquals("Association relationship visibility",
				Access.getAsString(Access.ACC_PUBLIC | Access.ACC_STATIC),
				Access.getAsString(elements[6].getVisibility()));
		Assert.assertEquals("Association relationship cardinality",
				Constants.CARDINALITY_ONE,
				((IUseRelationship) elements[6]).getCardinality());
		Assert.assertEquals("Association relationship name",
				"padl.kernel.impl.UseRelationship:padl.example.relationship.A:1",
				elements[6].getDisplayName());
		Assert.assertEquals("Association relationship target",
				"padl.example.relationship.A", ((IUseRelationship) elements[6])
						.getTargetEntity().getDisplayID());
	}

	public void testEntity4() {
		final IFirstClassEntity entity = RelationshipsTest.FirstClassEntities[12];
		Assert.assertEquals("Entity 4",
				"padl.example.relationship.Aggregation_CLASS_INSTANCE_FROM_FIELD_3",
				entity.getDisplayID());

		final IElement[] elements = Util.getArrayOfElements(entity);
		Assert.assertEquals("Identified elements", 6, elements.length);

		Assert.assertEquals("Constructor",
				"Aggregation_CLASS_INSTANCE_FROM_FIELD_3",
				elements[0].getDisplayName());

		ClassFilePrimitive.assertAssigable("Association relationship type",
				IAssociation.class, elements[1].getClass());
		Assert.assertEquals("Association relationship visibility",
				Access.getAsString(Access.ACC_PUBLIC),
				Access.getAsString(elements[1].getVisibility()));
		Assert.assertEquals("Association relationship cardinality",
				Constants.CARDINALITY_ONE,
				((IAssociation) elements[1]).getCardinality());
		Assert.assertEquals("Association relationship name",
				"padl.kernel.impl.Association:java.lang.Object:1",
				elements[1].getDisplayName());
		Assert.assertEquals("Association relationship target",
				"java.lang.Object",
				((IAssociation) elements[1]).getTargetEntity().getDisplayID());

		Assert.assertEquals("Static field visibility",
				Access.getAsString(Access.ACC_PRIVATE | Access.ACC_STATIC),
				Access.getAsString(elements[2].getVisibility()));
		Assert.assertEquals("Static field type", "padl.example.relationship.A",
				((IField) elements[2]).getDisplayTypeName());
		Assert.assertEquals("Static field name", "a",
				elements[2].getDisplayName());

		Assert.assertEquals("Method", "foo", elements[3].getDisplayName());

		ClassFilePrimitive.assertAssigable("Use relationship type",
				IUseRelationship.class, elements[4].getClass());
		Assert.assertEquals("Use relationship visibility",
				Access.getAsString(Access.ACC_PUBLIC | Access.ACC_STATIC),
				Access.getAsString(elements[4].getVisibility()));
		Assert.assertEquals("Use relationship cardinality",
				Constants.CARDINALITY_ONE,
				((IUseRelationship) elements[4]).getCardinality());
		Assert.assertEquals("Use relationship name",
				"padl.kernel.impl.UseRelationship:padl.example.relationship.Aggregation_CLASS_INSTANCE_FROM_FIELD_3:1",
				elements[4].getDisplayName());
		Assert.assertEquals("Use relationship target",
				"padl.example.relationship.Aggregation_CLASS_INSTANCE_FROM_FIELD_3",
				((IUseRelationship) elements[4]).getTargetEntity()
						.getDisplayID());

		ClassFilePrimitive.assertAssigable("Aggregation relationship type",
				IUseRelationship.class, elements[5].getClass());
		Assert.assertEquals("Aggregation relationship visibility",
				Access.getAsString(Access.ACC_PUBLIC | Access.ACC_STATIC),
				Access.getAsString(elements[5].getVisibility()));
		Assert.assertEquals("Aggregation relationship cardinality",
				Constants.CARDINALITY_MANY,
				((IUseRelationship) elements[5]).getCardinality());
		Assert.assertEquals("Aggregation relationship name",
				"padl.kernel.impl.UseRelationship:padl.example.relationship.A:2+padl.kernel.impl.UseRelationship:padl.example.relationship.A:2",
				elements[5].getDisplayName());
		Assert.assertEquals("Aggregation relationship target",
				"padl.example.relationship.A", ((IUseRelationship) elements[5])
						.getTargetEntity().getDisplayID());

		// Yann 2012/02/03: Not valid anymore!
		// After the changed made to conform with Aminata's parser.
		//	ClassFilePrimitive.assertAssigable(
		//		"Association relationship type",
		//		IAssociation.class,
		//		elements[6].getClass());
		//	Assert.assertEquals("Association relationship visibility", Modifier
		//		.toString(Access.ACC_PUBLIC | Access.ACC_STATIC), Modifier
		//		.toString(elements[6].getVisibility()));
		//	Assert.assertEquals(
		//		"Association relationship cardinality",
		//		Constants.CARDINALITY_ONE,
		//		((IAssociation) elements[6]).getCardinality());
		//	Assert.assertEquals(
		//		"Association relationship name",
		//		"padl.kernel.impl.Association:padl.example.relationship.A:1",
		//		elements[6].getDisplayName());
		//	Assert.assertEquals(
		//		"Association relationship target",
		//		"padl.example.relationship.A",
		//		((IAssociation) elements[6]).getTargetEntity().getDisplayID());
	}

	public void testEntity5() {
		final IFirstClassEntity entity = RelationshipsTest.FirstClassEntities[13];
		Assert.assertEquals("Entity 5",
				"padl.example.relationship.Aggregation_INSTANCE_INSTANCE_FROM_FIELD_1",
				entity.getDisplayID());

		final IElement[] elements = Util.getArrayOfElements(entity);
		Assert.assertEquals("Identified elements", 6, elements.length);

		Assert.assertEquals("Constructor",
				"Aggregation_INSTANCE_INSTANCE_FROM_FIELD_1",
				elements[0].getDisplayName());

		ClassFilePrimitive.assertAssigable("Association relationship type",
				IAssociation.class, elements[1].getClass());
		Assert.assertEquals("Association relationship visibility",
				Access.getAsString(Access.ACC_PUBLIC),
				Access.getAsString(elements[1].getVisibility()));
		Assert.assertEquals("Association relationship cardinality",
				Constants.CARDINALITY_ONE,
				((IAssociation) elements[1]).getCardinality());
		Assert.assertEquals("Association relationship name",
				"padl.kernel.impl.Association:java.lang.Object:1",
				elements[1].getDisplayName());
		Assert.assertEquals("Association relationship target",
				"java.lang.Object",
				((IAssociation) elements[1]).getTargetEntity().getDisplayID());

		Assert.assertEquals("Instance field visibility",
				Access.getAsString(Access.ACC_PRIVATE),
				Access.getAsString(elements[2].getVisibility()));
		Assert.assertEquals("Instance field type",
				"padl.example.relationship.A",
				((IField) elements[2]).getDisplayTypeName());
		Assert.assertEquals("Instance field name", "a",
				elements[2].getDisplayName());

		Assert.assertEquals("Method", "foo", elements[3].getDisplayName());

		ClassFilePrimitive.assertAssigable("Aggregation type",
				IAssociation.class, elements[4].getClass());
		Assert.assertEquals("Aggregation visibility",
				Access.getAsString(Access.ACC_PUBLIC),
				Access.getAsString(elements[4].getVisibility()));
		Assert.assertEquals("Aggregation cardinality",
				Constants.CARDINALITY_ONE,
				((IAssociation) elements[4]).getCardinality());
		Assert.assertEquals("Aggregation name",
				"padl.kernel.impl.Association:padl.example.relationship.A:1",
				elements[4].getDisplayName());
		Assert.assertEquals("Aggregation target", "padl.example.relationship.A",
				((IAssociation) elements[4]).getTargetEntity().getDisplayID());

		ClassFilePrimitive.assertAssigable("Association relationship type",
				IAggregation.class, elements[5].getClass());
		Assert.assertEquals("Association relationship visibility",
				Access.getAsString(Access.ACC_PUBLIC),
				Access.getAsString(elements[5].getVisibility()));
		Assert.assertEquals("Association relationship cardinality",
				Constants.CARDINALITY_ONE,
				((IAggregation) elements[5]).getCardinality());
		Assert.assertEquals("Association relationship name",
				"padl.kernel.impl.Aggregation:padl.example.relationship.A:1",
				elements[5].getDisplayName());
		Assert.assertEquals("Association relationship target",
				"padl.example.relationship.A",
				((IAggregation) elements[5]).getTargetEntity().getDisplayID());
	}

	public void testEntity6() {
		final IFirstClassEntity entity = RelationshipsTest.FirstClassEntities[14];
		Assert.assertEquals("Entity 6",
				"padl.example.relationship.Aggregation_INSTANCE_INSTANCE_FROM_FIELD_2",
				entity.getDisplayID());

		final IElement[] elements = Util.getArrayOfElements(entity);
		Assert.assertEquals("Identified elements", 6, elements.length);

		Assert.assertEquals("Constructor",
				"Aggregation_INSTANCE_INSTANCE_FROM_FIELD_2",
				elements[0].getDisplayName());

		ClassFilePrimitive.assertAssigable("Association relationship type",
				IAssociation.class, elements[1].getClass());
		Assert.assertEquals("Association relationship visibility",
				Access.getAsString(Access.ACC_PUBLIC),
				Access.getAsString(elements[1].getVisibility()));
		Assert.assertEquals("Association relationship cardinality",
				Constants.CARDINALITY_ONE,
				((IAssociation) elements[1]).getCardinality());
		Assert.assertEquals("Association relationship name",
				"padl.kernel.impl.Association:java.lang.Object:1",
				elements[1].getDisplayName());
		Assert.assertEquals("Association relationship target",
				"java.lang.Object",
				((IAssociation) elements[1]).getTargetEntity().getDisplayID());

		Assert.assertEquals("Private field visibility",
				Access.getAsString(Access.ACC_PRIVATE),
				Access.getAsString(elements[2].getVisibility()));
		Assert.assertEquals("Private field type", "padl.example.relationship.A",
				((IField) elements[2]).getDisplayTypeName());
		Assert.assertEquals("Private field name", "a",
				elements[2].getDisplayName());

		Assert.assertEquals("Method", "foo", elements[3].getDisplayName());

		ClassFilePrimitive.assertAssigable("Aggregation type",
				IAssociation.class, elements[4].getClass());
		Assert.assertEquals("Aggregation visibility",
				Access.getAsString(Access.ACC_PUBLIC),
				Access.getAsString(elements[4].getVisibility()));
		Assert.assertEquals("Aggregation cardinality",
				Constants.CARDINALITY_MANY,
				((IAssociation) elements[4]).getCardinality());
		Assert.assertEquals("Aggregation name",
				"padl.kernel.impl.Association:padl.example.relationship.A:2",
				elements[4].getDisplayName());
		Assert.assertEquals("Aggregation target", "padl.example.relationship.A",
				((IAssociation) elements[4]).getTargetEntity().getDisplayID());

		ClassFilePrimitive.assertAssigable("Association relationship type",
				IAggregation.class, elements[5].getClass());
		Assert.assertEquals("Association relationship visibility",
				Access.getAsString(Access.ACC_PUBLIC),
				Access.getAsString(elements[5].getVisibility()));
		Assert.assertEquals("Association relationship cardinality",
				Constants.CARDINALITY_MANY,
				((IAggregation) elements[5]).getCardinality());
		Assert.assertEquals("Association relationship name",
				"padl.kernel.impl.Aggregation:padl.example.relationship.A:2",
				elements[5].getDisplayName());
		Assert.assertEquals("Association relationship target",
				"padl.example.relationship.A",
				((IAggregation) elements[5]).getTargetEntity().getDisplayID());
	}

	public void testEntity7() {
		final IFirstClassEntity entity = RelationshipsTest.FirstClassEntities[15];
		Assert.assertEquals("Entity 7",
				"padl.example.relationship.Association_CLASS_INSTANCE_1",
				entity.getDisplayID());

		final IElement[] elements = Util.getArrayOfElements(entity);
		// Yann 2004/08/07: Bug in the analyzer?
		// The analyzer for relationships create an extra association
		// with cardinality 1 through "instanceMethod" instead of
		// cardinality 2. It seems to come for the code where a
		// method invocation is created *without* a field...
		// TODO: Fix this bug!
		Assert.assertEquals("Identified elements", 5, elements.length);

		Assert.assertEquals("Constructor", "Association_CLASS_INSTANCE_1",
				elements[0].getDisplayName());

		ClassFilePrimitive.assertAssigable("Association relationship type",
				IAssociation.class, elements[1].getClass());
		Assert.assertEquals("Association relationship visibility",
				Access.getAsString(Access.ACC_PUBLIC),
				Access.getAsString(elements[1].getVisibility()));
		Assert.assertEquals("Association relationship cardinality",
				Constants.CARDINALITY_ONE,
				((IAssociation) elements[1]).getCardinality());
		Assert.assertEquals("Association relationship name",
				"padl.kernel.impl.Association:java.lang.Object:1",
				elements[1].getDisplayName());
		Assert.assertEquals("Association relationship target",
				"java.lang.Object",
				((IAssociation) elements[1]).getTargetEntity().getDisplayID());

		Assert.assertEquals("Method foo()", "foo",
				elements[2].getDisplayName());

		ClassFilePrimitive.assertAssigable("Association relationship type",
				IAssociation.class, elements[3].getClass());
		Assert.assertEquals("Association relationship visibility",
				Access.getAsString(Access.ACC_PUBLIC | Access.ACC_STATIC),
				Access.getAsString(elements[3].getVisibility()));
		Assert.assertEquals("Association relationship cardinality",
				Constants.CARDINALITY_MANY,
				((IAssociation) elements[3]).getCardinality());
		Assert.assertEquals("Association relationship name",
				"padl.kernel.impl.Association:padl.example.relationship.A:2+padl.kernel.impl.Association:padl.example.relationship.A:2",
				elements[3].getDisplayName());
		Assert.assertEquals("Association relationship target",
				"padl.example.relationship.A",
				((IAssociation) elements[3]).getTargetEntity().getDisplayID());

		ClassFilePrimitive.assertAssigable("Use relationship type",
				IUseRelationship.class, elements[4].getClass());
		Assert.assertEquals("Use relationship visibility",
				Access.getAsString(Access.ACC_PUBLIC | Access.ACC_STATIC),
				Access.getAsString(elements[4].getVisibility()));
		Assert.assertEquals("Use relationship cardinality",
				Constants.CARDINALITY_MANY,
				((IUseRelationship) elements[4]).getCardinality());
		Assert.assertEquals("Use relationship name",
				"padl.kernel.impl.UseRelationship:padl.example.relationship.A:2",
				elements[4].getDisplayName());
		Assert.assertEquals("Use relationship target",
				"padl.example.relationship.A", ((IUseRelationship) elements[4])
						.getTargetEntity().getDisplayID());
	}

	public void testEntity8() {
		final IFirstClassEntity entity = RelationshipsTest.FirstClassEntities[16];
		Assert.assertEquals("Entity 8",
				"padl.example.relationship.Association_CLASS_INSTANCE_2",
				entity.getDisplayID());

		final IElement[] elements = Util.getArrayOfElements(entity);
		Assert.assertEquals("Identified elements", 5, elements.length);

		Assert.assertEquals("Constructor", "Association_CLASS_INSTANCE_2",
				elements[0].getDisplayName());

		ClassFilePrimitive.assertAssigable("Association relationship type",
				IAssociation.class, elements[1].getClass());
		Assert.assertEquals("Association relationship visibility",
				Access.getAsString(Access.ACC_PUBLIC),
				Access.getAsString(elements[1].getVisibility()));
		Assert.assertEquals("Association relationship cardinality",
				Constants.CARDINALITY_ONE,
				((IAssociation) elements[1]).getCardinality());
		Assert.assertEquals("Association relationship name",
				"padl.kernel.impl.Association:java.lang.Object:1",
				elements[1].getDisplayName());
		Assert.assertEquals("Association relationship target",
				"java.lang.Object",
				((IAssociation) elements[1]).getTargetEntity().getDisplayID());

		Assert.assertEquals("Method foo()", "foo",
				elements[2].getDisplayName());

		ClassFilePrimitive.assertAssigable("Association relationship type",
				IAssociation.class, elements[3].getClass());
		Assert.assertEquals("Association relationship visibility",
				Access.getAsString(Access.ACC_PUBLIC | Access.ACC_STATIC),
				Access.getAsString(elements[3].getVisibility()));
		Assert.assertEquals("Association relationship cardinality",
				Constants.CARDINALITY_ONE,
				((IAssociation) elements[3]).getCardinality());
		Assert.assertEquals("Association relationship name",
				"padl.kernel.impl.Association:padl.example.relationship.A:1+padl.kernel.impl.Association:padl.example.relationship.A:1",
				elements[3].getDisplayName());
		Assert.assertEquals("Association relationship target",
				"padl.example.relationship.A",
				((IAssociation) elements[3]).getTargetEntity().getDisplayID());

		ClassFilePrimitive.assertAssigable("Use relationship type",
				IUseRelationship.class, elements[4].getClass());
		Assert.assertEquals("Use relationship visibility",
				Access.getAsString(Access.ACC_PUBLIC | Access.ACC_STATIC),
				Access.getAsString(elements[4].getVisibility()));
		Assert.assertEquals("Use relationship cardinality",
				Constants.CARDINALITY_ONE,
				((IUseRelationship) elements[4]).getCardinality());
		Assert.assertEquals("Association relationship name",
				"padl.kernel.impl.UseRelationship:padl.example.relationship.A:1",
				elements[4].getDisplayName());
		Assert.assertEquals("Use relationship target",
				"padl.example.relationship.A", ((IUseRelationship) elements[4])
						.getTargetEntity().getDisplayID());

		//	Relationships.assertAssigable(
		//		"Association relationship type",
		//		IAssociation.class,
		//		elements[5].getClass());
		//	Assert.assertEquals(
		//		"Association relationship visibility",
		//		Access.getAsString(Access.ACC_PUBLIC | Access.ACC_STATIC),
		//		Access.getAsString(elements[5].getVisibility()));
		//	Assert.assertEquals(
		//		"Association relationship cardinality",
		//		1,
		//		((IAssociation) elements[5]).getCardinality());
		//	Assert.assertEquals(
		//		"Association relationship name",
		//		"foo_3",
		//		elements[5].getDisplayName());
		//	Assert.assertEquals(
		//		"Association relationship target",
		//		"padl.example.relationship.A",
		//		((IAssociation) elements[5]).getTargetEntity().getDisplayName());
	}

	public void testEntity9() {
		final IFirstClassEntity entity = RelationshipsTest.FirstClassEntities[17];
		Assert.assertEquals("Entity 9",
				"padl.example.relationship.Association_INSTANCE_INSTANCE_1",
				entity.getDisplayID());

		final IElement[] elements = Util.getArrayOfElements(entity);
		Assert.assertEquals("Identified elements", 5, elements.length);

		Assert.assertEquals("Constructor", "Association_INSTANCE_INSTANCE_1",
				elements[0].getDisplayName());

		ClassFilePrimitive.assertAssigable("Association relationship type",
				IAssociation.class, elements[1].getClass());
		Assert.assertEquals("Association relationship visibility",
				Access.getAsString(Access.ACC_PUBLIC),
				Access.getAsString(elements[1].getVisibility()));
		Assert.assertEquals("Association relationship cardinality",
				Constants.CARDINALITY_ONE,
				((IAssociation) elements[1]).getCardinality());
		Assert.assertEquals("Association relationship name",
				"padl.kernel.impl.Association:java.lang.Object:1",
				elements[1].getDisplayName());
		Assert.assertEquals("Association relationship target",
				"java.lang.Object",
				((IAssociation) elements[1]).getTargetEntity().getDisplayID());

		Assert.assertEquals("Method foo()", "foo",
				elements[2].getDisplayName());

		ClassFilePrimitive.assertAssigable("Association relationship type",
				IAssociation.class, elements[3].getClass());
		Assert.assertEquals("Association relationship visibility",
				Access.getAsString(Access.ACC_PUBLIC),
				Access.getAsString(elements[3].getVisibility()));
		Assert.assertEquals("Association relationship cardinality",
				Constants.CARDINALITY_ONE,
				((IAssociation) elements[3]).getCardinality());
		Assert.assertEquals("Association relationship visibility",
				"padl.kernel.impl.Association:padl.example.relationship.A:1+padl.kernel.impl.Association:padl.example.relationship.A:1",
				elements[3].getDisplayName());
		Assert.assertEquals("Association relationship target",
				"padl.example.relationship.A",
				((IAssociation) elements[3]).getTargetEntity().getDisplayID());

		ClassFilePrimitive.assertAssigable("Use relationship type",
				IUseRelationship.class, elements[4].getClass());
		Assert.assertEquals("Use relationship visibility",
				Access.getAsString(Access.ACC_PUBLIC),
				Access.getAsString(elements[4].getVisibility()));
		Assert.assertEquals("Use relationship cardinality",
				Constants.CARDINALITY_ONE,
				((IUseRelationship) elements[4]).getCardinality());
		Assert.assertEquals("Use relationship visibility",
				"padl.kernel.impl.UseRelationship:padl.example.relationship.A:1",
				elements[4].getDisplayName());
		Assert.assertEquals("Use relationship target",
				"padl.example.relationship.A", ((IUseRelationship) elements[4])
						.getTargetEntity().getDisplayID());

		//	Relationships.assertAssigable(
		//		"Association relationship type",
		//		IAssociation.class,
		//		elements[5].getClass());
		//	Assert.assertEquals(
		//		"Association relationship visibility",
		//		Access.getAsString(Access.ACC_PUBLIC),
		//		Access.getAsString(elements[5].getVisibility()));
		//	Assert.assertEquals(
		//		"Association relationship cardinality",
		//		1,
		//		((IAssociation) elements[5]).getCardinality());
		//	Assert.assertEquals(
		//		"Association relationship name",
		//		"foo_3",
		//		elements[5].getDisplayName());
		//	Assert.assertEquals(
		//		"Association relationship target",
		//		"padl.example.relationship.A",
		//		((IAssociation) elements[5]).getTargetEntity().getDisplayName());
	}

	public void testJavaLangObject() {
		int index;
		for (index = 0; !RelationshipsTest.FirstClassEntities[index]
				.getDisplayID().equals("java.lang.Object"); index++)
			;
		Assert.assertEquals("Identified elements", 2,
				RelationshipsTest.FirstClassEntities[index]
						.getNumberOfConstituents());
	}

	public void testUseRelationship_CLASS_CLASS_2() {
		int index;
		for (index = 0; !RelationshipsTest.FirstClassEntities[index]
				.getDisplayID()
				.equals("padl.example.relationship.UseRelationship_CLASS_CLASS_2"); index++)
			;
		final IElement[] elements = Util.getArrayOfElements(
				RelationshipsTest.FirstClassEntities[index]);

		Assert.assertEquals("Identified elements", 4, elements.length);

		Assert.assertEquals("Constructor", "UseRelationship_CLASS_CLASS_2",
				elements[0].getDisplayName());

		ClassFilePrimitive.assertAssigable("Association relationship type",
				IAssociation.class, elements[1].getClass());
		Assert.assertEquals("Association relationship visibility",
				Access.getAsString(Access.ACC_PUBLIC),
				Access.getAsString(elements[1].getVisibility()));
		Assert.assertEquals("Association relationship cardinality",
				Constants.CARDINALITY_ONE,
				((IAssociation) elements[1]).getCardinality());
		Assert.assertEquals("Association relationship name",
				"padl.kernel.impl.Association:java.lang.Object:1",
				elements[1].getDisplayName());
		Assert.assertEquals("Association relationship target",
				"java.lang.Object",
				((IAssociation) elements[1]).getTargetEntity().getDisplayID());

		Assert.assertEquals("Method", "foo", elements[2].getDisplayName());

		ClassFilePrimitive.assertAssigable("Use relationship type",
				IUseRelationship.class, elements[3].getClass());
		Assert.assertEquals("Use relationship visibility",
				Access.getAsString(Access.ACC_PUBLIC | Access.ACC_STATIC),
				Access.getAsString(elements[3].getVisibility()));
		Assert.assertEquals("Use relationship cardinality",
				Constants.CARDINALITY_ONE,
				((IUseRelationship) elements[3]).getCardinality());
		Assert.assertEquals("Use relationship name",
				"padl.kernel.impl.UseRelationship:padl.example.relationship.A:1",
				elements[3].getDisplayName());
		Assert.assertEquals("Use relationship target",
				"padl.example.relationship.A", ((IUseRelationship) elements[3])
						.getTargetEntity().getDisplayID());
	}

	public void testUseRelationship_INSTANCE_CLASS_1() {
		int index;
		for (index = 0; !RelationshipsTest.FirstClassEntities[index]
				.getDisplayID()
				.equals("padl.example.relationship.UseRelationship_INSTANCE_CLASS_1"); index++)
			;
		final IElement[] elements = Util.getArrayOfElements(
				RelationshipsTest.FirstClassEntities[index]);

		Assert.assertEquals("Identified elements", 4, elements.length);

		Assert.assertEquals("Constructor", "UseRelationship_INSTANCE_CLASS_1",
				elements[0].getDisplayName());

		ClassFilePrimitive.assertAssigable("Association relationship type",
				IAssociation.class, elements[1].getClass());
		Assert.assertEquals("Association relationship visibility",
				Access.getAsString(Access.ACC_PUBLIC),
				Access.getAsString(elements[1].getVisibility()));
		Assert.assertEquals("Association relationship cardinality",
				Constants.CARDINALITY_ONE,
				((IAssociation) elements[1]).getCardinality());
		Assert.assertEquals("Association relationship name",
				"padl.kernel.impl.Association:java.lang.Object:1",
				elements[1].getDisplayName());
		Assert.assertEquals("Association relationship target",
				"java.lang.Object",
				((IAssociation) elements[1]).getTargetEntity().getDisplayID());

		Assert.assertEquals("Method", "foo", elements[2].getDisplayName());

		ClassFilePrimitive.assertAssigable("Use relationship type",
				IUseRelationship.class, elements[3].getClass());
		Assert.assertEquals("Use relationship visibility",
				Access.getAsString(Access.ACC_PUBLIC),
				Access.getAsString(elements[3].getVisibility()));
		Assert.assertEquals("Use relationship cardinality",
				Constants.CARDINALITY_ONE,
				((IUseRelationship) elements[3]).getCardinality());
		Assert.assertEquals("Use relationship name",
				"padl.kernel.impl.UseRelationship:padl.example.relationship.A:1+padl.kernel.impl.UseRelationship:padl.example.relationship.A:1",
				elements[3].getDisplayName());
		Assert.assertEquals("Use relationship target",
				"padl.example.relationship.A", ((IUseRelationship) elements[3])
						.getTargetEntity().getDisplayID());
	}

	public void testUseRelationship_INSTANCE_CLASS_2() {
		int index;
		for (index = 0; !RelationshipsTest.FirstClassEntities[index]
				.getDisplayID()
				.equals("padl.example.relationship.UseRelationship_INSTANCE_CLASS_2"); index++)
			;
		final IElement[] elements = Util.getArrayOfElements(
				RelationshipsTest.FirstClassEntities[index]);

		Assert.assertEquals("Identified elements", 4, elements.length);

		Assert.assertEquals("Constructor", "UseRelationship_INSTANCE_CLASS_2",
				elements[0].getDisplayName());

		ClassFilePrimitive.assertAssigable("Association relationship type",
				IAssociation.class, elements[1].getClass());
		Assert.assertEquals("Association relationship visibility",
				Access.getAsString(Access.ACC_PUBLIC),
				Access.getAsString(elements[1].getVisibility()));
		Assert.assertEquals("Association relationship cardinality",
				Constants.CARDINALITY_ONE,
				((IAssociation) elements[1]).getCardinality());
		Assert.assertEquals("Association relationship name",
				"padl.kernel.impl.Association:java.lang.Object:1",
				elements[1].getDisplayName());
		Assert.assertEquals("Association relationship target",
				"java.lang.Object",
				((IAssociation) elements[1]).getTargetEntity().getDisplayID());

		Assert.assertEquals("Method", "foo", elements[2].getDisplayName());

		ClassFilePrimitive.assertAssigable("Use relationship type",
				IUseRelationship.class, elements[3].getClass());
		Assert.assertEquals("Use relationship visibility",
				Access.getAsString(Access.ACC_PUBLIC),
				Access.getAsString(elements[3].getVisibility()));
		Assert.assertEquals("Use relationship cardinality",
				Constants.CARDINALITY_ONE,
				((IUseRelationship) elements[3]).getCardinality());
		Assert.assertEquals("Use relationship name",
				"padl.kernel.impl.UseRelationship:padl.example.relationship.A:1",
				elements[3].getDisplayName());
		Assert.assertEquals("Use relationship target",
				"padl.example.relationship.A", ((IUseRelationship) elements[3])
						.getTargetEntity().getDisplayID());
	}

	public void testUseRelationship_INSTANCE_CLASS_3() {
		int index;
		for (index = 0; !RelationshipsTest.FirstClassEntities[index]
				.getDisplayID()
				.equals("padl.example.relationship.UseRelationship_INSTANCE_CLASS_3"); index++)
			;
		final IElement[] elements = Util.getArrayOfElements(
				RelationshipsTest.FirstClassEntities[index]);

		Assert.assertEquals("Identified elements", 4, elements.length);

		Assert.assertEquals("Constructor", "UseRelationship_INSTANCE_CLASS_3",
				elements[0].getDisplayName());

		ClassFilePrimitive.assertAssigable("Association relationship type",
				IAssociation.class, elements[1].getClass());
		Assert.assertEquals("Association relationship visibility",
				Access.getAsString(Access.ACC_PUBLIC),
				Access.getAsString(elements[1].getVisibility()));
		Assert.assertEquals("Association relationship cardinality",
				Constants.CARDINALITY_ONE,
				((IAssociation) elements[1]).getCardinality());
		Assert.assertEquals("Association relationship name",
				"padl.kernel.impl.Association:java.lang.Object:1",
				elements[1].getDisplayName());
		Assert.assertEquals("Association relationship target",
				"java.lang.Object",
				((IAssociation) elements[1]).getTargetEntity().getDisplayID());

		Assert.assertEquals("Method", "foo", elements[2].getDisplayName());

		ClassFilePrimitive.assertAssigable("Use relationship type",
				IUseRelationship.class, elements[3].getClass());
		Assert.assertEquals("Use relationship visibility",
				Access.getAsString(Access.ACC_PUBLIC),
				Access.getAsString(elements[3].getVisibility()));
		Assert.assertEquals("Use relationship cardinality",
				Constants.CARDINALITY_ONE,
				((IUseRelationship) elements[3]).getCardinality());
		Assert.assertEquals("Use relationship name",
				"padl.kernel.impl.UseRelationship:padl.example.relationship.A:1",
				elements[3].getDisplayName());
		Assert.assertEquals("Use relationship target",
				"padl.example.relationship.A", ((IUseRelationship) elements[3])
						.getTargetEntity().getDisplayID());
	}
}
