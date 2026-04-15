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
import padl.kernel.IAssociation;
import padl.kernel.ICodeLevelModel;
import padl.kernel.IElement;
import padl.kernel.IFirstClassEntity;
import padl.kernel.IIdiomLevelModel;
import padl.kernel.exception.CreationException;
import padl.util.Util;

/**
 * @author 	Yann-Gaël Guéhéneuc
 * @since	2002/08/15
 */
public class Aggregation14Test extends ClassFilePrimitive {
	private static IElement[] Elements = null;
	private static IFirstClassEntity[] FirstClassEntities = null;

	public Aggregation14Test(final String name) {
		super(name);
	}

	protected void setUp()
			throws CreationException, UnsupportedSourceModelException {

		if (Aggregation14Test.FirstClassEntities == null
				|| Aggregation14Test.Elements == null) {

			final ICodeLevelModel codeLevelModel = ClassFilePrimitive
					.getFactory()
					.createCodeLevelModel("ptidej.example.aggregations");
			codeLevelModel.create(new CompleteClassFileCreator(new String[] {
					"../PADL Creator ClassFile/target/test-classes/padl/example/aggregation/Aggregation14.class" }));

			final IIdiomLevelModel idiomLevelModel = (IIdiomLevelModel) new AACRelationshipsAnalysis()
					.invoke(codeLevelModel);

			Aggregation14Test.FirstClassEntities = Util
					.getArrayOfTopLevelEntities(idiomLevelModel);

			Aggregation14Test.Elements = Util.getArrayOfElements(
					Aggregation14Test.FirstClassEntities[2]);
		}
	}

	public void testConstructor() {
		Assert.assertEquals("Constructor", "Aggregation14",
				Aggregation14Test.Elements[0].getDisplayName());
	}

	public void testConstructorAssociation() {
		ClassFilePrimitive.assertAssigable("Association type",
				IAssociation.class, Aggregation14Test.Elements[1].getClass());
		Assert.assertEquals("Association visibility",
				Access.getAsString(Access.ACC_PUBLIC), Access.getAsString(
						Aggregation14Test.Elements[1].getVisibility()));
		Assert.assertEquals("Association cardinality",
				Constants.CARDINALITY_ONE,
				((IAssociation) Aggregation14Test.Elements[1])
						.getCardinality());
		Assert.assertEquals("Association name",
				"padl.kernel.impl.Association:java.lang.Object:1",
				Aggregation14Test.Elements[1].getDisplayName());
		Assert.assertEquals("Association target", "java.lang.Object",
				((IAssociation) Aggregation14Test.Elements[1]).getTargetEntity()
						.getDisplayID());
	}

	public void testGetAMethod() {
		Assert.assertEquals("Method getA()", "getA",
				Aggregation14Test.Elements[2].getDisplayName());
	}

	public void testMainEntityName() {
		Assert.assertEquals("Entity name",
				"padl.example.aggregation.Aggregation14",
				Aggregation14Test.FirstClassEntities[2].getDisplayID());
	}

	public void testNumberOfElements() {
		Assert.assertEquals("Number of elements", 4,
				Aggregation14Test.Elements.length);
	}

	public void testNumberOfEntities() {
		Assert.assertEquals("Number of entities", 3,
				Aggregation14Test.FirstClassEntities.length);
	}

	public void testGetAAssociation() {
		ClassFilePrimitive.assertAssigable("Association type",
				IAssociation.class, Aggregation14Test.Elements[3].getClass());
		Assert.assertEquals("Association visibility",
				Access.getAsString(Access.ACC_NONE), Access.getAsString(
						Aggregation14Test.Elements[3].getVisibility()));
		Assert.assertEquals("Association cardinality",
				Constants.CARDINALITY_ONE,
				((IAssociation) Aggregation14Test.Elements[3])
						.getCardinality());
		Assert.assertEquals("Association name",
				"padl.kernel.impl.Association:padl.example.aggregation.A:1",
				Aggregation14Test.Elements[3].getDisplayName());
		Assert.assertEquals("Association target", "padl.example.aggregation.A",
				((IAssociation) Aggregation14Test.Elements[3]).getTargetEntity()
						.getDisplayID());
	}
}
