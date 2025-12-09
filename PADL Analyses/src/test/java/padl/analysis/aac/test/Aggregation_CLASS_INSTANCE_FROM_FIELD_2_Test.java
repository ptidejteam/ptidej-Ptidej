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
package padl.analysis.aac.test;

import java.util.Iterator;

import org.junit.Assert;

import junit.framework.TestCase;
import padl.analysis.UnsupportedSourceModelException;
import padl.analysis.repository.AACRelationshipsAnalysis;
import padl.creator.classfile.CompleteClassFileCreator;
import padl.kernel.Constants;
import padl.kernel.IAssociation;
import padl.kernel.ICodeLevelModel;
import padl.kernel.IElement;
import padl.kernel.IField;
import padl.kernel.IFirstClassEntity;
import padl.kernel.IIdiomLevelModel;
import padl.kernel.IMethod;
import padl.kernel.IMethodInvocation;
import padl.kernel.IUseRelationship;
import padl.kernel.exception.CreationException;
import padl.kernel.impl.Factory;
import padl.util.Util;
import util.io.ProxyConsole;
import util.lang.Modifier;

/**
 * @author 	Yann-Gaël Guéhéneuc
 * @since	2005/08/08
 */
public class Aggregation_CLASS_INSTANCE_FROM_FIELD_2_Test extends TestCase {
	private static IElement[] Elements = null;
	private static IFirstClassEntity[] FirstClassEntities = null;

	public static void assertAssigable(final String aMessage,
			final Class anInterface, final Class aClass) {

		if (!anInterface.isAssignableFrom(aClass)) {
			Assert.fail(aMessage);
		}
	}

	public Aggregation_CLASS_INSTANCE_FROM_FIELD_2_Test(final String name) {
		super(name);
	}

	protected void setUp() {
		if (Aggregation_CLASS_INSTANCE_FROM_FIELD_2_Test.FirstClassEntities == null
				|| Aggregation_CLASS_INSTANCE_FROM_FIELD_2_Test.Elements == null) {

			final ICodeLevelModel codeLevelModel = Factory.getInstance()
					.createCodeLevelModel(
							"ptidej.example.relationship [code-level]");
			try {
				codeLevelModel
						.create(new CompleteClassFileCreator(new String[] {
								"../PADL Analyses/target/test-classes/padl/analysis/aac/data/A.class",
								"../PADL Analyses/target/test-classes/padl/analysis/aac/data/Aggregation_CLASS_INSTANCE_FROM_FIELD_2.class" }));

				final AACRelationshipsAnalysis analysis = new AACRelationshipsAnalysis();
				final IIdiomLevelModel idiomLevelModel = (IIdiomLevelModel) analysis
						.invoke(codeLevelModel);

				Aggregation_CLASS_INSTANCE_FROM_FIELD_2_Test.FirstClassEntities = Util
						.getArrayOfTopLevelEntities(idiomLevelModel);

				Aggregation_CLASS_INSTANCE_FROM_FIELD_2_Test.Elements = Util
						.getArrayOfElements(
								Aggregation_CLASS_INSTANCE_FROM_FIELD_2_Test.FirstClassEntities[2]);
			}
			catch (final CreationException e) {
				e.printStackTrace(ProxyConsole.getInstance().errorOutput());
			}
			catch (final UnsupportedSourceModelException e) {
				e.printStackTrace(ProxyConsole.getInstance().errorOutput());
			}
		}
	}

	public void testAssociation() {
		Aggregation_CLASS_INSTANCE_FROM_FIELD_2_Test.assertAssigable(
				"Association link type", IAssociation.class,
				Aggregation_CLASS_INSTANCE_FROM_FIELD_2_Test.Elements[1].getClass());
		Assert.assertEquals("Association link visibility",
				Modifier.toString(Modifier.PUBLIC),
				Modifier.toString(
						Aggregation_CLASS_INSTANCE_FROM_FIELD_2_Test.Elements[1]
								.getVisibility()));
		Assert.assertEquals("Association link cardinality",
				Constants.CARDINALITY_ONE,
				((IAssociation) Aggregation_CLASS_INSTANCE_FROM_FIELD_2_Test.Elements[1])
						.getCardinality());
		Assert.assertEquals("Association name",
				"padl.kernel.impl.Association:java.lang.Object:1",
				Aggregation_CLASS_INSTANCE_FROM_FIELD_2_Test.Elements[1]
						.getDisplayName());
		Assert.assertEquals("Association target", "java.lang.Object",
				((IAssociation) Aggregation_CLASS_INSTANCE_FROM_FIELD_2_Test.Elements[1])
						.getTargetEntity().getDisplayID());
	}

	public void testConstructor() {
		Assert.assertEquals("Constructor",
				"Aggregation_CLASS_INSTANCE_FROM_FIELD_2",
				Aggregation_CLASS_INSTANCE_FROM_FIELD_2_Test.Elements[0]
						.getDisplayName());
	}

	public void testFooAssociation() {
		Aggregation_CLASS_INSTANCE_FROM_FIELD_2_Test.assertAssigable(
				"Association link type", IUseRelationship.class,
				Aggregation_CLASS_INSTANCE_FROM_FIELD_2_Test.Elements[5].getClass());
		Assert.assertEquals("Association link visibility",
				Modifier.toString(Modifier.PUBLIC | Modifier.STATIC),
				Modifier.toString(
						Aggregation_CLASS_INSTANCE_FROM_FIELD_2_Test.Elements[5]
								.getVisibility()));
		Assert.assertEquals("Association link cardinality",
				Constants.CARDINALITY_MANY,
				((IUseRelationship) Aggregation_CLASS_INSTANCE_FROM_FIELD_2_Test.Elements[5])
						.getCardinality());
		Assert.assertEquals("Association name",
				"padl.kernel.impl.UseRelationship:padl.analysis.aac.data.A:2+padl.kernel.impl.UseRelationship:padl.analysis.aac.data.A:2",
				Aggregation_CLASS_INSTANCE_FROM_FIELD_2_Test.Elements[5]
						.getDisplayName());
		Assert.assertEquals("Association target", "padl.analysis.aac.data.A",
				((IUseRelationship) Aggregation_CLASS_INSTANCE_FROM_FIELD_2_Test.Elements[5])
						.getTargetEntity().getDisplayID());
	}

	public void testUseRelationship() {
		Aggregation_CLASS_INSTANCE_FROM_FIELD_2_Test.assertAssigable(
				"Use relationship type", IUseRelationship.class,
				Aggregation_CLASS_INSTANCE_FROM_FIELD_2_Test.Elements[4].getClass());
		Assert.assertEquals("Use relationship visibility",
				Modifier.toString(Modifier.PUBLIC | Modifier.STATIC),
				Modifier.toString(
						Aggregation_CLASS_INSTANCE_FROM_FIELD_2_Test.Elements[4]
								.getVisibility()));
		Assert.assertEquals("Use relationship cardinality",
				Constants.CARDINALITY_ONE,
				((IUseRelationship) Aggregation_CLASS_INSTANCE_FROM_FIELD_2_Test.Elements[4])
						.getCardinality());
		Assert.assertEquals("Use relationship name",
				"padl.kernel.impl.UseRelationship:padl.analysis.aac.data.Aggregation_CLASS_INSTANCE_FROM_FIELD_2:1",
				Aggregation_CLASS_INSTANCE_FROM_FIELD_2_Test.Elements[4]
						.getDisplayName());
		Assert.assertEquals("Use relationship target",
				"padl.analysis.aac.data.Aggregation_CLASS_INSTANCE_FROM_FIELD_2",
				((IUseRelationship) Aggregation_CLASS_INSTANCE_FROM_FIELD_2_Test.Elements[4])
						.getTargetEntity().getDisplayID());
	}

	public void testMainEntityName() {
		Assert.assertEquals("Entity name",
				"padl.analysis.aac.data.Aggregation_CLASS_INSTANCE_FROM_FIELD_2",
				Aggregation_CLASS_INSTANCE_FROM_FIELD_2_Test.FirstClassEntities[2]
						.getDisplayID());
	}

	public void testMethodAndMethodInvocations() {
		final IMethod foo = (IMethod) Aggregation_CLASS_INSTANCE_FROM_FIELD_2_Test.Elements[3];
		Assert.assertEquals("Method", "foo", foo.getDisplayName());

		final Iterator iteratorOnMethodInvocations = foo
				.getConcurrentIteratorOnConstituents(IMethodInvocation.class);
		iteratorOnMethodInvocations.next();
		final IMethodInvocation secondMethodInvocation = (IMethodInvocation) iteratorOnMethodInvocations
				.next();
		Assert.assertNull(secondMethodInvocation.getFieldDeclaringEntity());
		Assert.assertEquals(IMethodInvocation.CLASS_INSTANCE_FROM_FIELD,
				secondMethodInvocation.getType());
	}

	public void testNumberOfElements() {
		Assert.assertEquals("Number of elements", 6,
				Aggregation_CLASS_INSTANCE_FROM_FIELD_2_Test.Elements.length);
	}

	public void testNumberOfEntities() {
		Assert.assertEquals("Number of entities", 3,
				Aggregation_CLASS_INSTANCE_FROM_FIELD_2_Test.FirstClassEntities.length);
	}

	public void testStaticField() {
		Assert.assertEquals("Static field visibility",
				Modifier.toString(Modifier.PRIVATE | Modifier.STATIC),
				Modifier.toString(
						Aggregation_CLASS_INSTANCE_FROM_FIELD_2_Test.Elements[2]
								.getVisibility()));
		Assert.assertEquals("Static field type", "padl.analysis.aac.data.A",
				((IField) Aggregation_CLASS_INSTANCE_FROM_FIELD_2_Test.Elements[2])
						.getDisplayTypeName());
		Assert.assertEquals("Static field name", "a",
				Aggregation_CLASS_INSTANCE_FROM_FIELD_2_Test.Elements[2]
						.getDisplayName());
	}
}
