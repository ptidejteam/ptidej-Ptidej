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
import padl.kernel.IElement;
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
public class UseRelationship_CLASS_CLASS_1 extends ClassFilePrimitive {
	private static IFirstClassEntity[] FirstClassEntities = null;
	private static IElement[] Elements = null;

	public UseRelationship_CLASS_CLASS_1(final String name) {
		super(name);
	}
	protected void setUp() throws CreationException,
			UnsupportedSourceModelException {

		if (UseRelationship_CLASS_CLASS_1.FirstClassEntities == null
				|| UseRelationship_CLASS_CLASS_1.Elements == null) {

			final ICodeLevelModel codeLevelModel =
				ClassFilePrimitive.getFactory().createCodeLevelModel(
					"ptidej.example.relationship");
			codeLevelModel
				.create(new CompleteClassFileCreator(
					new String[] { "../PADL Creator ClassFile Tests/bin/padl/example/relationship/UseRelationship_CLASS_CLASS_1.class" }));

			final IIdiomLevelModel idiomLevelModel =
				(IIdiomLevelModel) new AACRelationshipsAnalysis()
					.invoke(codeLevelModel);

			UseRelationship_CLASS_CLASS_1.FirstClassEntities =
				Util.getArrayOfTopLevelEntities(idiomLevelModel);

			UseRelationship_CLASS_CLASS_1.Elements =
				Util
					.getArrayOfElements(UseRelationship_CLASS_CLASS_1.FirstClassEntities[2]);
		}
	}
	public void testConstructor() {
		Assert.assertEquals(
			"Constructor",
			"UseRelationship_CLASS_CLASS_1",
			UseRelationship_CLASS_CLASS_1.Elements[0].getDisplayName());
	}
	public void testAssociation1() {
		ClassFilePrimitive.assertAssigable(
			"Association relationship type",
			IAssociation.class,
			UseRelationship_CLASS_CLASS_1.Elements[1].getClass());
		Assert.assertEquals(
			"Association relationship visibility",
			Modifier.toString(Modifier.PUBLIC),
			Modifier.toString(UseRelationship_CLASS_CLASS_1.Elements[1]
				.getVisibility()));
		Assert.assertEquals(
			"Association relationship cardinality",
			Constants.CARDINALITY_ONE,
			((IAssociation) UseRelationship_CLASS_CLASS_1.Elements[1])
				.getCardinality());
		Assert.assertEquals(
			"Association relationship name",
			"padl.kernel.impl.Association:java.lang.Object:1",
			UseRelationship_CLASS_CLASS_1.Elements[1].getDisplayName());
		Assert.assertEquals(
			"Use relationship target",
			"java.lang.Object",
			((IAssociation) UseRelationship_CLASS_CLASS_1.Elements[1])
				.getTargetEntity()
				.getDisplayID());
	}
	public void testMethod() {
		Assert.assertEquals(
			"Method",
			"foo",
			UseRelationship_CLASS_CLASS_1.Elements[2].getDisplayName());
	}
	public void testUseRelationship() {
		ClassFilePrimitive.assertAssigable(
			"Use relationship type",
			IUseRelationship.class,
			UseRelationship_CLASS_CLASS_1.Elements[3].getClass());
		Assert.assertEquals(
			"Use relationship visibility",
			Modifier.toString(Modifier.PUBLIC | Modifier.STATIC),
			Modifier.toString(UseRelationship_CLASS_CLASS_1.Elements[3]
				.getVisibility()));
		Assert.assertEquals(
			"Use relationship cardinality",
			Constants.CARDINALITY_ONE,
			((IUseRelationship) UseRelationship_CLASS_CLASS_1.Elements[3])
				.getCardinality());
		Assert.assertEquals(
			"Use relationship name",
			"padl.kernel.impl.UseRelationship:padl.example.relationship.A:1",
			UseRelationship_CLASS_CLASS_1.Elements[3].getDisplayName());
		Assert.assertEquals(
			"Use relationship target",
			"padl.example.relationship.A",
			((IUseRelationship) UseRelationship_CLASS_CLASS_1.Elements[3])
				.getTargetEntity()
				.getDisplayID());
	}
	public void testAssociation2() {
		ClassFilePrimitive.assertAssigable(
			"Association relationship type",
			IAssociation.class,
			UseRelationship_CLASS_CLASS_1.Elements[4].getClass());
		Assert.assertEquals(
			"Association relationship visibility",
			Modifier.toString(Modifier.PUBLIC | Modifier.STATIC),
			Modifier.toString(UseRelationship_CLASS_CLASS_1.Elements[4]
				.getVisibility()));
		Assert.assertEquals(
			"Association relationship cardinality",
			Constants.CARDINALITY_ONE,
			((IAssociation) UseRelationship_CLASS_CLASS_1.Elements[4])
				.getCardinality());
		Assert.assertEquals(
			"Association relationship name",
			"padl.kernel.impl.Association:padl.example.relationship.A:1",
			UseRelationship_CLASS_CLASS_1.Elements[4].getDisplayName());
		Assert.assertEquals(
			"Use relationship target",
			"padl.example.relationship.A",
			((IAssociation) UseRelationship_CLASS_CLASS_1.Elements[4])
				.getTargetEntity()
				.getDisplayID());
	}
	public void testMainEntityName() {
		Assert.assertEquals(
			"Entity name",
			"padl.example.relationship.UseRelationship_CLASS_CLASS_1",
			UseRelationship_CLASS_CLASS_1.FirstClassEntities[2].getDisplayID());
	}
	public void testNumberOfElements() {
		Assert.assertEquals(
			"Number of elements",
			5,
			UseRelationship_CLASS_CLASS_1.Elements.length);
	}
	public void testNumberOfEntities() {
		Assert.assertEquals(
			"Number of entities",
			3,
			UseRelationship_CLASS_CLASS_1.FirstClassEntities.length);
	}
}
