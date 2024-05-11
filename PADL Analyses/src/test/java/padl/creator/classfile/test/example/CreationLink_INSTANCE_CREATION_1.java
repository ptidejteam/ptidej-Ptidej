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
import padl.kernel.exception.CreationException;
import padl.util.Util;
import util.lang.Modifier;

/**
 * @version	0.2
 * @author 	Yann-Gaël Guéhéneuc
 * @since	2002/08/15
 */
public class CreationLink_INSTANCE_CREATION_1 extends ClassFilePrimitive {
	private static IFirstClassEntity[] FirstClassEntities = null;
	private static IElement[] Elements = null;

	public CreationLink_INSTANCE_CREATION_1(final String name) {
		super(name);
	}
	protected void setUp() throws CreationException,
			UnsupportedSourceModelException {

		if (CreationLink_INSTANCE_CREATION_1.FirstClassEntities == null
				|| CreationLink_INSTANCE_CREATION_1.Elements == null) {

			final ICodeLevelModel codeLevelModel =
				ClassFilePrimitive.getFactory().createCodeLevelModel(
					"ptidej.example.relationship");
			codeLevelModel
				.create(new CompleteClassFileCreator(
					new String[] { "../PADL Creator ClassFile/target/test-classes/padl/example/relationship/CreationLink_INSTANCE_CREATION_1.class" }));

			final IIdiomLevelModel idiomLevelModel =
				(IIdiomLevelModel) new AACRelationshipsAnalysis()
					.invoke(codeLevelModel);

			CreationLink_INSTANCE_CREATION_1.FirstClassEntities =
				Util.getArrayOfTopLevelEntities(idiomLevelModel);

			CreationLink_INSTANCE_CREATION_1.Elements =
				Util
					.getArrayOfElements(CreationLink_INSTANCE_CREATION_1.FirstClassEntities[2]);
		}
	}
	public void testConstructor() {
		Assert.assertEquals(
			"Constructor",
			"CreationLink_INSTANCE_CREATION_1",
			CreationLink_INSTANCE_CREATION_1.Elements[0].getDisplayName());
	}
	public void testMethod() {
		Assert.assertEquals(
			"Method",
			"foo",
			CreationLink_INSTANCE_CREATION_1.Elements[2].getDisplayName());
	}
	public void testAssociation() {
		ClassFilePrimitive.assertAssigable(
			"Association relationship type",
			IAssociation.class,
			CreationLink_INSTANCE_CREATION_1.Elements[1].getClass());
		Assert.assertEquals("Association relationship visibility", Modifier
			.toString(Modifier.PUBLIC), Modifier
			.toString(CreationLink_INSTANCE_CREATION_1.Elements[1]
				.getVisibility()));
		Assert.assertEquals(
			"Association relationship cardinality",
			Constants.CARDINALITY_ONE,
			((IAssociation) CreationLink_INSTANCE_CREATION_1.Elements[1])
				.getCardinality());
		Assert.assertEquals(
			"Association relationship name",
			"padl.kernel.impl.Association:java.lang.Object:1",
			CreationLink_INSTANCE_CREATION_1.Elements[1].getDisplayName());
		Assert.assertEquals(
			"Association relationship target",
			"java.lang.Object",
			((IAssociation) CreationLink_INSTANCE_CREATION_1.Elements[1])
				.getTargetEntity()
				.getDisplayID());
	}
	public void testCreationLink() {
		ClassFilePrimitive.assertAssigable(
			"Creation link type",
			ICreation.class,
			CreationLink_INSTANCE_CREATION_1.Elements[3].getClass());
		Assert.assertEquals("Creation link visibility", Modifier
			.toString(Modifier.PUBLIC | Modifier.STATIC), Modifier
			.toString(CreationLink_INSTANCE_CREATION_1.Elements[3]
				.getVisibility()));
		Assert.assertEquals(
			"Creation link cardinality",
			Constants.CARDINALITY_ONE,
			((ICreation) CreationLink_INSTANCE_CREATION_1.Elements[3])
				.getCardinality());
		Assert.assertEquals(
			"Creation link name",
			"padl.kernel.impl.Creation:padl.example.relationship.A:1",
			CreationLink_INSTANCE_CREATION_1.Elements[3].getDisplayName());
		Assert.assertEquals(
			"Creation link target",
			"padl.example.relationship.A",
			((ICreation) CreationLink_INSTANCE_CREATION_1.Elements[3])
				.getTargetEntity()
				.getDisplayID());
	}
	public void testMainEntityName() {
		Assert.assertEquals(
			"Entity name",
			"padl.example.relationship.CreationLink_INSTANCE_CREATION_1",
			CreationLink_INSTANCE_CREATION_1.FirstClassEntities[2]
				.getDisplayID());
	}
	public void testNumberOfElements() {
		Assert.assertEquals(
			"Number of elements",
			4,
			CreationLink_INSTANCE_CREATION_1.Elements.length);
	}
	public void testNumberOfEntities() {
		Assert.assertEquals(
			"Number of entities",
			3,
			CreationLink_INSTANCE_CREATION_1.FirstClassEntities.length);
	}
}
