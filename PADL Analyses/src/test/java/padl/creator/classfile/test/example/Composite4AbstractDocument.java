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
public class Composite4AbstractDocument extends ClassFilePrimitive {
	private static IFirstClassEntity[] FirstClassEntities = null;
	private static IElement[] Elements = null;

	public Composite4AbstractDocument(final String name) {
		super(name);
	}
	protected void setUp() throws CreationException,
			UnsupportedSourceModelException {

		if (Composite4AbstractDocument.FirstClassEntities == null
				|| Composite4AbstractDocument.Elements == null) {

			final ICodeLevelModel codeLevelModel =
				ClassFilePrimitive.getFactory().createCodeLevelModel(
					"ptidej.example.relationship");
			codeLevelModel
				.create(new CompleteClassFileCreator(
					new String[] { "../PADL Creator ClassFile/target/test-classes/padl/example/relationship/Composite4AbstractDocument.class" }));

			final IIdiomLevelModel idiomLevelModel =
				(IIdiomLevelModel) new AACRelationshipsAnalysis()
					.invoke(codeLevelModel);

			Composite4AbstractDocument.FirstClassEntities =
				Util.getArrayOfTopLevelEntities(idiomLevelModel);

			Composite4AbstractDocument.Elements =
				Util
					.getArrayOfElements(Composite4AbstractDocument.FirstClassEntities[4]);
		}
	}
	public void testConstructor() {
		Assert.assertEquals(
			"Constructor",
			"Composite4AbstractDocument",
			Composite4AbstractDocument.Elements[0].getDisplayName());
	}
	public void testAssociation1() {
		ClassFilePrimitive.assertAssigable(
			"Association relationship type",
			IAssociation.class,
			Composite4AbstractDocument.Elements[1].getClass());
		Assert.assertEquals("Association relationship visibility", Modifier
			.toString(Modifier.PUBLIC), Modifier
			.toString(Composite4AbstractDocument.Elements[1].getVisibility()));
		Assert.assertEquals(
			"Association relationship cardinality",
			Constants.CARDINALITY_ONE,
			((IAssociation) Composite4AbstractDocument.Elements[1])
				.getCardinality());
		Assert
			.assertEquals(
				"Association relationship name",
				"padl.kernel.impl.Association:java.lang.Object:1+padl.kernel.impl.Association:java.lang.Object:1",
				Composite4AbstractDocument.Elements[1].getDisplayName());
		Assert.assertEquals(
			"Association relationship target",
			"java.lang.Object",
			((IAssociation) Composite4AbstractDocument.Elements[1])
				.getTargetEntity()
				.getDisplayID());
	}
	public void testMethod() {
		Assert.assertEquals(
			"Method",
			"print",
			Composite4AbstractDocument.Elements[2].getDisplayName());
	}
	public void testUseRelationship1() {
		ClassFilePrimitive.assertAssigable(
			"Use relationship type",
			IUseRelationship.class,
			Composite4AbstractDocument.Elements[3].getClass());
		Assert.assertEquals("Use relationship visibility", Modifier
			.toString(Modifier.PUBLIC), Modifier
			.toString(Composite4AbstractDocument.Elements[3].getVisibility()));
		Assert.assertEquals(
			"Use relationship cardinality",
			Constants.CARDINALITY_ONE,
			((IUseRelationship) Composite4AbstractDocument.Elements[3])
				.getCardinality());
		Assert.assertEquals(
			"Use relationship name",
			"padl.kernel.impl.UseRelationship:java.lang.System:1",
			Composite4AbstractDocument.Elements[3].getDisplayName());
		Assert.assertEquals(
			"Use relationship target",
			"java.lang.System",
			((IUseRelationship) Composite4AbstractDocument.Elements[3])
				.getTargetEntity()
				.getDisplayID());
	}
	public void testUseRelationship2() {
		ClassFilePrimitive.assertAssigable(
			"Use relationship type",
			IUseRelationship.class,
			Composite4AbstractDocument.Elements[4].getClass());
		Assert.assertEquals("Use relationship visibility", Modifier
			.toString(Modifier.PUBLIC), Modifier
			.toString(Composite4AbstractDocument.Elements[4].getVisibility()));
		Assert.assertEquals(
			"Use relationship cardinality",
			Constants.CARDINALITY_ONE,
			((IUseRelationship) Composite4AbstractDocument.Elements[4])
				.getCardinality());
		Assert.assertEquals(
			"Use relationship name",
			"padl.kernel.impl.UseRelationship:java.lang.Class:1",
			Composite4AbstractDocument.Elements[4].getDisplayName());
		Assert.assertEquals(
			"Use relationship target",
			"java.lang.Class",
			((IUseRelationship) Composite4AbstractDocument.Elements[4])
				.getTargetEntity()
				.getDisplayID());
	}
	public void testAssociation2() {
		ClassFilePrimitive.assertAssigable(
			"Association relationship type",
			IAssociation.class,
			Composite4AbstractDocument.Elements[5].getClass());
		Assert.assertEquals("Association relationship visibility", Modifier
			.toString(Modifier.PUBLIC), Modifier
			.toString(Composite4AbstractDocument.Elements[5].getVisibility()));
		Assert.assertEquals(
			"Association relationship cardinality",
			Constants.CARDINALITY_ONE,
			((IAssociation) Composite4AbstractDocument.Elements[5])
				.getCardinality());
		Assert.assertEquals(
			"Association relationship name",
			"padl.kernel.impl.Association:java.io.PrintStream:1",
			Composite4AbstractDocument.Elements[5].getDisplayName());
		Assert.assertEquals(
			"Association relationship target",
			"java.io.PrintStream",
			((IAssociation) Composite4AbstractDocument.Elements[5])
				.getTargetEntity()
				.getDisplayID());
	}
	//	public void testAssociation3() {
	//		Composite4AbstractDocument.assertAssigable(
	//			"Association relationship type",
	//			IAssociation.class,
	//			Composite4AbstractDocument.Elements[6].getClass());
	//		Assert.assertEquals(
	//			"Association relationship visibility",
	//			Modifier.toString(Modifier.PUBLIC),
	//			Modifier.toString(
	//				Composite4AbstractDocument.Elements[6].getVisibility()));
	//		Assert.assertEquals(
	//			"Association relationship cardinality",
	//			1,
	//			((IAssociation) Composite4AbstractDocument.Elements[6])
	//				.getCardinality());
	//		Assert.assertEquals(
	//			"Association relationship name",
	//			"print_5",
	//			Composite4AbstractDocument.Elements[6].getDisplayName());
	//		Assert.assertEquals(
	//			"Association relationship target",
	//			"java.lang.Object",
	//			((IAssociation) Composite4AbstractDocument.Elements[6])
	//				.getTargetEntity()
	//				.getDisplayName());
	//	}
	//	public void testAssociation4() {
	//		Composite4AbstractDocument.assertAssigable(
	//			"Association relationship type",
	//			IAssociation.class,
	//			Composite4AbstractDocument.Elements[7].getClass());
	//		Assert.assertEquals(
	//			"Association relationship visibility",
	//			Modifier.toString(Modifier.PUBLIC),
	//			Modifier.toString(
	//				Composite4AbstractDocument.Elements[7].getVisibility()));
	//		Assert.assertEquals(
	//			"Association relationship cardinality",
	//			1,
	//			((IAssociation) Composite4AbstractDocument.Elements[7])
	//				.getCardinality());
	//		Assert.assertEquals(
	//			"Association relationship name",
	//			"print_6",
	//			Composite4AbstractDocument.Elements[7].getDisplayName());
	//		Assert.assertEquals(
	//			"Association relationship target",
	//			"java.io.PrintStream",
	//			((IAssociation) Composite4AbstractDocument.Elements[7])
	//				.getTargetEntity()
	//				.getDisplayName());
	//	}
	public void testNumberOfElements() {
		Assert.assertEquals(
			"Number of elements",
			7,
			Composite4AbstractDocument.Elements.length);
	}
	public void testNumberOfEntities() {
		Assert.assertEquals(
			"Number of entities",
			5,
			Composite4AbstractDocument.FirstClassEntities.length);
	}
}
