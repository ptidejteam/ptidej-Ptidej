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

import padl.creator.classfile.CompleteClassFileCreator;
import padl.creator.classfile.test.ClassFilePrimitive;
import padl.kernel.Constants;
import padl.kernel.IAggregation;
import padl.kernel.IAssociation;
import padl.kernel.ICodeLevelModel;
import padl.kernel.IElement;
import padl.kernel.IField;
import padl.kernel.IFirstClassEntity;
import padl.kernel.IUseRelationship;
import padl.kernel.exception.CreationException;
import padl.util.Util;
import util.lang.Modifier;

/**
 * @version	0.2
 * @author 	Yann-Gaël Guéhéneuc
 * @since	2002/08/15
 */
public class Aggregation_CLASS_INSTANCE_FROM_FIELD_1 extends ClassFilePrimitive {
	private static IElement[] Elements = null;
	private static IFirstClassEntity[] FirstClassEntities = null;

	public Aggregation_CLASS_INSTANCE_FROM_FIELD_1(final String name) {
		super(name);
	}

	protected void setUp() throws CreationException {
		if (Aggregation_CLASS_INSTANCE_FROM_FIELD_1.FirstClassEntities == null
				|| Aggregation_CLASS_INSTANCE_FROM_FIELD_1.Elements == null) {

			final ICodeLevelModel codeLevelModel = ClassFilePrimitive.getFactory()
					.createCodeLevelModel("ptidej.example.relationship");
			codeLevelModel.create(new CompleteClassFileCreator(new String[] {
					"../PADL Creator ClassFile Tests/bin/padl/example/relationship/Aggregation_CLASS_INSTANCE_FROM_FIELD_1.class" }));

			Aggregation_CLASS_INSTANCE_FROM_FIELD_1.FirstClassEntities = Util
					.getArrayOfTopLevelEntities(codeLevelModel);

			Aggregation_CLASS_INSTANCE_FROM_FIELD_1.Elements = Util
					.getArrayOfElements(Aggregation_CLASS_INSTANCE_FROM_FIELD_1.FirstClassEntities[2]);
		}
	}

	public void testAggregation() {
		ClassFilePrimitive.assertAssigable("Aggregation link type", IAggregation.class,
				Aggregation_CLASS_INSTANCE_FROM_FIELD_1.Elements[5].getClass());
		Assert.assertEquals("Aggregation link visibility", Modifier.toString(Modifier.PUBLIC),
				Modifier.toString(Aggregation_CLASS_INSTANCE_FROM_FIELD_1.Elements[5].getVisibility()));
		Assert.assertEquals("Aggregation link cardinality", Constants.CARDINALITY_ONE,
				((IAggregation) Aggregation_CLASS_INSTANCE_FROM_FIELD_1.Elements[5]).getCardinality());
		Assert.assertEquals("Aggregation name", "padl.kernel.impl.Aggregation:padl.example.relationship.A:1",
				Aggregation_CLASS_INSTANCE_FROM_FIELD_1.Elements[5].getDisplayName());
		Assert.assertEquals("Aggregation target", "padl.example.relationship.A",
				((IAggregation) Aggregation_CLASS_INSTANCE_FROM_FIELD_1.Elements[5]).getTargetEntity().getDisplayID());
	}

	public void testAssociation() {
		ClassFilePrimitive.assertAssigable("Association link type", IAssociation.class,
				Aggregation_CLASS_INSTANCE_FROM_FIELD_1.Elements[1].getClass());
		Assert.assertEquals("Association link visibility", Modifier.toString(Modifier.PUBLIC),
				Modifier.toString(Aggregation_CLASS_INSTANCE_FROM_FIELD_1.Elements[1].getVisibility()));
		Assert.assertEquals("Association link cardinality", Constants.CARDINALITY_ONE,
				((IAssociation) Aggregation_CLASS_INSTANCE_FROM_FIELD_1.Elements[1]).getCardinality());
		Assert.assertEquals("Association name", "padl.kernel.impl.Association:java.lang.Object:1",
				Aggregation_CLASS_INSTANCE_FROM_FIELD_1.Elements[1].getDisplayName());
		Assert.assertEquals("Association target", "java.lang.Object",
				((IAssociation) Aggregation_CLASS_INSTANCE_FROM_FIELD_1.Elements[1]).getTargetEntity().getDisplayID());
	}

	public void testConstructor() {
		Assert.assertEquals("Constructor", "Aggregation_CLASS_INSTANCE_FROM_FIELD_1",
				Aggregation_CLASS_INSTANCE_FROM_FIELD_1.Elements[0].getDisplayName());
	}

	public void testFooAssociation() {
		ClassFilePrimitive.assertAssigable("Association link type", IUseRelationship.class,
				Aggregation_CLASS_INSTANCE_FROM_FIELD_1.Elements[6].getClass());
		Assert.assertEquals("Association link visibility", Modifier.toString(Modifier.PUBLIC),
				Modifier.toString(Aggregation_CLASS_INSTANCE_FROM_FIELD_1.Elements[6].getVisibility()));
		Assert.assertEquals("Association link cardinality", Constants.CARDINALITY_ONE,
				((IUseRelationship) Aggregation_CLASS_INSTANCE_FROM_FIELD_1.Elements[6]).getCardinality());
		Assert.assertEquals("Association name", "padl.kernel.impl.UseRelationship:padl.example.relationship.A:1",
				Aggregation_CLASS_INSTANCE_FROM_FIELD_1.Elements[6].getDisplayName());
		Assert.assertEquals("Association target", "padl.example.relationship.A",
				((IUseRelationship) Aggregation_CLASS_INSTANCE_FROM_FIELD_1.Elements[6]).getTargetEntity()
						.getDisplayID());
	}

	public void testUseRelationship() {
		ClassFilePrimitive.assertAssigable("Use relationship type", IUseRelationship.class,
				Aggregation_CLASS_INSTANCE_FROM_FIELD_1.Elements[4].getClass());
		Assert.assertEquals("Use relationship visibility", Modifier.toString(Modifier.PUBLIC),
				Modifier.toString(Aggregation_CLASS_INSTANCE_FROM_FIELD_1.Elements[4].getVisibility()));
		Assert.assertEquals("Use relationship cardinality", Constants.CARDINALITY_ONE,
				((IUseRelationship) Aggregation_CLASS_INSTANCE_FROM_FIELD_1.Elements[4]).getCardinality());
		Assert.assertEquals("Use relationship name",
				"padl.kernel.impl.UseRelationship:padl.example.relationship.Aggregation_CLASS_INSTANCE_FROM_FIELD_1:1",
				Aggregation_CLASS_INSTANCE_FROM_FIELD_1.Elements[4].getDisplayName());
		Assert.assertEquals("Use relationship target",
				"padl.example.relationship.Aggregation_CLASS_INSTANCE_FROM_FIELD_1",
				((IUseRelationship) Aggregation_CLASS_INSTANCE_FROM_FIELD_1.Elements[4]).getTargetEntity()
						.getDisplayID());
	}

	public void testMainEntityName() {
		Assert.assertEquals("Entity name", "padl.example.relationship.Aggregation_CLASS_INSTANCE_FROM_FIELD_1",
				Aggregation_CLASS_INSTANCE_FROM_FIELD_1.FirstClassEntities[2].getDisplayID());
	}

	public void testMethod() {
		Assert.assertEquals("Method", "foo", Aggregation_CLASS_INSTANCE_FROM_FIELD_1.Elements[3].getDisplayName());
	}

	public void testNumberOfElements() {
		Assert.assertEquals("Number of elements", 7, Aggregation_CLASS_INSTANCE_FROM_FIELD_1.Elements.length);
	}

	public void testNumberOfEntities() {
		Assert.assertEquals("Number of entities", 3, Aggregation_CLASS_INSTANCE_FROM_FIELD_1.FirstClassEntities.length);
	}

	public void testStaticField() {
		Assert.assertEquals("Static field visibility", Modifier.toString(Modifier.PRIVATE | Modifier.STATIC),
				Modifier.toString(Aggregation_CLASS_INSTANCE_FROM_FIELD_1.Elements[2].getVisibility()));
		Assert.assertEquals("Static field type", "padl.example.relationship.A",
				((IField) Aggregation_CLASS_INSTANCE_FROM_FIELD_1.Elements[2]).getDisplayTypeName());
		Assert.assertEquals("Static field name", "a",
				Aggregation_CLASS_INSTANCE_FROM_FIELD_1.Elements[2].getDisplayName());
	}
}
