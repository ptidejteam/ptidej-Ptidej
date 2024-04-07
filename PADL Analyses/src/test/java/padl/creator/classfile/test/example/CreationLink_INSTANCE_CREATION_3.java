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
import padl.kernel.ICreation;
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
public class CreationLink_INSTANCE_CREATION_3 extends ClassFilePrimitive {
	private static IFirstClassEntity[] FirstClassEntities = null;
	private static IElement[] Elements = null;

	public CreationLink_INSTANCE_CREATION_3(final String name) {
		super(name);
	}
	protected void setUp() throws CreationException,
			UnsupportedSourceModelException {

		if (CreationLink_INSTANCE_CREATION_3.FirstClassEntities == null
				|| CreationLink_INSTANCE_CREATION_3.Elements == null) {

			final ICodeLevelModel codeLevelModel =
				ClassFilePrimitive.getFactory().createCodeLevelModel(
					"ptidej.example.relationship");
			codeLevelModel
				.create(new CompleteClassFileCreator(
					new String[] { "../PADL Creator ClassFile Tests/bin/padl/example/relationship/CreationLink_INSTANCE_CREATION_3.class" }));

			final IIdiomLevelModel idiomLevelModel =
				(IIdiomLevelModel) new AACRelationshipsAnalysis()
					.invoke(codeLevelModel);

			CreationLink_INSTANCE_CREATION_3.FirstClassEntities =
				Util.getArrayOfTopLevelEntities(idiomLevelModel);

			CreationLink_INSTANCE_CREATION_3.Elements =
				Util
					.getArrayOfElements(CreationLink_INSTANCE_CREATION_3.FirstClassEntities[4]);
		}
	}
	public void testConstructor() {
		Assert.assertEquals(
			"Constructor",
			"CreationLink_INSTANCE_CREATION_3",
			CreationLink_INSTANCE_CREATION_3.Elements[0].getDisplayName());
	}
	public void testAssociation1() {
		ClassFilePrimitive.assertAssigable(
			"Association relationship type",
			IAssociation.class,
			CreationLink_INSTANCE_CREATION_3.Elements[1].getClass());
		Assert.assertEquals("Association relationship visibility", Modifier
			.toString(Modifier.PUBLIC), Modifier
			.toString(CreationLink_INSTANCE_CREATION_3.Elements[1]
				.getVisibility()));
		Assert.assertEquals(
			"Association relationship cardinality",
			Constants.CARDINALITY_ONE,
			((IAssociation) CreationLink_INSTANCE_CREATION_3.Elements[1])
				.getCardinality());
		Assert.assertEquals(
			"Association relationship name",
			"padl.kernel.impl.Association:java.lang.Object:1",
			CreationLink_INSTANCE_CREATION_3.Elements[1].getDisplayName());
		Assert.assertEquals(
			"Association relationship target",
			"java.lang.Object",
			((IAssociation) CreationLink_INSTANCE_CREATION_3.Elements[1])
				.getTargetEntity()
				.getDisplayID());
	}
	public void testMethod() {
		Assert.assertEquals(
			"Method",
			"foo",
			CreationLink_INSTANCE_CREATION_3.Elements[2].getDisplayName());
	}
	public void testCreationLink() {
		ClassFilePrimitive.assertAssigable(
			"Creation relationship type",
			ICreation.class,
			CreationLink_INSTANCE_CREATION_3.Elements[3].getClass());
		Assert.assertEquals("Creation relationship visibility", Modifier
			.toString(Modifier.PUBLIC), Modifier
			.toString(CreationLink_INSTANCE_CREATION_3.Elements[3]
				.getVisibility()));
		Assert.assertEquals(
			"Creation relationship cardinality",
			Constants.CARDINALITY_MANY,
			((ICreation) CreationLink_INSTANCE_CREATION_3.Elements[3])
				.getCardinality());
		Assert.assertEquals(
			"Creation relationship name",
			"padl.kernel.impl.Creation:padl.example.relationship.A:2",
			CreationLink_INSTANCE_CREATION_3.Elements[3].getDisplayName());
		Assert.assertEquals(
			"Creation relationship target",
			"padl.example.relationship.A",
			((ICreation) CreationLink_INSTANCE_CREATION_3.Elements[3])
				.getTargetEntity()
				.getDisplayID());
	}
	public void testUseRelationship1() {
		ClassFilePrimitive.assertAssigable(
			"Use relationship type",
			IUseRelationship.class,
			CreationLink_INSTANCE_CREATION_3.Elements[4].getClass());
		Assert.assertEquals("Use relationship visibility", Modifier
			.toString(Modifier.PUBLIC), Modifier
			.toString(CreationLink_INSTANCE_CREATION_3.Elements[4]
				.getVisibility()));
		Assert.assertEquals(
			"Use relationship cardinality",
			Constants.CARDINALITY_ONE,
			((IUseRelationship) CreationLink_INSTANCE_CREATION_3.Elements[4])
				.getCardinality());
		Assert.assertEquals(
			"Use relationship name",
			"padl.kernel.impl.UseRelationship:java.lang.System:1",
			CreationLink_INSTANCE_CREATION_3.Elements[4].getDisplayName());
		Assert.assertEquals(
			"Use relationship target",
			"java.lang.System",
			((IUseRelationship) CreationLink_INSTANCE_CREATION_3.Elements[4])
				.getTargetEntity()
				.getDisplayID());
	}
	public void testUseRelationship2() {
		ClassFilePrimitive.assertAssigable(
			"Use relationship type",
			IUseRelationship.class,
			CreationLink_INSTANCE_CREATION_3.Elements[5].getClass());
		Assert.assertEquals("Use relationship visibility", Modifier
			.toString(Modifier.PUBLIC), Modifier
			.toString(CreationLink_INSTANCE_CREATION_3.Elements[5]
				.getVisibility()));
		Assert.assertEquals(
			"Use relationship cardinality",
			Constants.CARDINALITY_ONE,
			((IUseRelationship) CreationLink_INSTANCE_CREATION_3.Elements[5])
				.getCardinality());
		Assert.assertEquals(
			"Use relationship name",
			"padl.kernel.impl.UseRelationship:java.io.PrintStream:1",
			CreationLink_INSTANCE_CREATION_3.Elements[5].getDisplayName());
		Assert.assertEquals(
			"Use relationship target",
			"java.io.PrintStream",
			((IUseRelationship) CreationLink_INSTANCE_CREATION_3.Elements[5])
				.getTargetEntity()
				.getDisplayID());
	}
	public void testAssociation2() {
		ClassFilePrimitive.assertAssigable(
			"Association relationship type",
			IAssociation.class,
			CreationLink_INSTANCE_CREATION_3.Elements[6].getClass());
		Assert.assertEquals("Association relationship visibility", Modifier
			.toString(Modifier.PUBLIC), Modifier
			.toString(CreationLink_INSTANCE_CREATION_3.Elements[6]
				.getVisibility()));
		Assert.assertEquals(
			"Association relationship cardinality",
			Constants.CARDINALITY_ONE,
			((IAssociation) CreationLink_INSTANCE_CREATION_3.Elements[6])
				.getCardinality());
		Assert.assertEquals(
			"Association relationship name",
			"padl.kernel.impl.Aggregation:java.io.PrintStream:1",
			CreationLink_INSTANCE_CREATION_3.Elements[6].getDisplayName());
		Assert.assertEquals(
			"Association relationship target",
			"java.io.PrintStream",
			((IAssociation) CreationLink_INSTANCE_CREATION_3.Elements[6])
				.getTargetEntity()
				.getDisplayID());
	}
	public void testMainEntityName() {
		Assert.assertEquals(
			"Entity name",
			"padl.example.relationship.CreationLink_INSTANCE_CREATION_3",
			CreationLink_INSTANCE_CREATION_3.FirstClassEntities[4]
				.getDisplayID());
	}
	public void testNumberOfElements() {
		Assert.assertEquals(
			"Number of elements",
			7,
			CreationLink_INSTANCE_CREATION_3.Elements.length);
	}
	public void testNumberOfEntities() {
		Assert.assertEquals(
			"Number of entities",
			5,
			CreationLink_INSTANCE_CREATION_3.FirstClassEntities.length);
	}
}
