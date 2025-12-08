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
package padl.test.path;

import java.util.Iterator;
import org.junit.Assert;
import junit.framework.TestCase;
import padl.analysis.UnsupportedSourceModelException;
import padl.analysis.repository.AACRelationshipsAnalysis;
import padl.kernel.Constants;
import padl.kernel.IClass;
import padl.kernel.ICodeLevelModel;
import padl.kernel.IConstituent;
import padl.kernel.IField;
import padl.kernel.IFirstClassEntity;
import padl.kernel.IIdiomLevelModel;
import padl.kernel.IMethod;
import padl.kernel.IPackage;
import padl.kernel.IParameter;
import padl.kernel.IRelationship;
import padl.kernel.impl.Factory;
import util.io.ProxyConsole;

/**
 * @author Yann-Gaël Guéhéneuc
 * @since  2006/11/28
 */
public class SanityTest extends TestCase {
	private static IIdiomLevelModel IdiomLevelModel;
	public SanityTest(final String name) {
		super(name);
	}
	public void setUp() {
		try {
			final char[] entityName = "A".toCharArray();
			final IFirstClassEntity entity =
				Factory.getInstance().createClass(entityName, entityName);

			final IMethod aGetter =
				Factory.getInstance().createMethod(
					"get".toCharArray(),
					"get".toCharArray());
			aGetter.setReturnType(entityName);
			// aGetter.addConstituent(aParameter1);

			final IParameter aParameter2 =
				Factory.getInstance().createParameter(
					entity,
					"a".toCharArray(),
					Constants.CARDINALITY_ONE);
			final IMethod aSetter =
				Factory.getInstance().createMethod(
					"set".toCharArray(),
					"set".toCharArray());
			aSetter.addConstituent(aParameter2);

			final IField aField =
				Factory.getInstance().createField(
					"a".toCharArray(),
					"a".toCharArray(),
					entityName,
					2);
			aField.setPrivate(true);

			entity.addConstituent(aGetter);
			entity.addConstituent(aSetter);
			entity.addConstituent(aField);

			final IPackage aPackage =
				Factory.getInstance().createPackage("p".toCharArray());
			aPackage.addConstituent(entity);

			final ICodeLevelModel aCodeLevelModel =
				Factory.getInstance().createCodeLevelModel("Model");
			aCodeLevelModel.addConstituent(aPackage);

			SanityTest.IdiomLevelModel =
				(IIdiomLevelModel) new AACRelationshipsAnalysis()
					.invoke(aCodeLevelModel);
		}
		catch (final UnsupportedSourceModelException e) {
			e.printStackTrace(ProxyConsole.getInstance().errorOutput());
		}
	}
	public void testClass() {
		Assert.assertEquals(
			"Class A exists",
			1,
			SanityTest.IdiomLevelModel.getNumberOfTopLevelEntities());
	}
	public void testPathOfMethods() {
		final IClass clazz =
			(IClass) SanityTest.IdiomLevelModel.getTopLevelEntityFromID("A");
		final Iterator iterator = clazz.getConcurrentIteratorOnConstituents();
		while (iterator.hasNext()) {
			final IConstituent constituent = (IConstituent) iterator.next();
			Assert.assertTrue("Path is right", constituent
				.getDisplayPath()
				.startsWith("/Model|p|A"));
		}
	}
	public void testMethods() {
		final IFirstClassEntity firstClassEntity =
			SanityTest.IdiomLevelModel.getTopLevelEntityFromID("A");
		Assert.assertEquals(
			"Getter and setter exist",
			4,
			firstClassEntity.getNumberOfConstituents());
	}
	public void testParameters() {
		final IFirstClassEntity firstClassEntity =
			SanityTest.IdiomLevelModel.getTopLevelEntityFromID("A");
		final IMethod getter =
			(IMethod) firstClassEntity.getConstituentFromID("set");
		Assert.assertEquals(
			"Parameter exists",
			1,
			getter.getNumberOfConstituents());
	}
	public void testPath() {
		final IFirstClassEntity firstClassEntity =
			SanityTest.IdiomLevelModel.getTopLevelEntityFromID("A");
		final IRelationship relationship =
			(IRelationship) firstClassEntity
				.getConstituentFromID("a_ContainerAggregation_>PADL<_1");
		Assert.assertEquals(
			"Path is right",
			"/Model|p|A|a_ContainerAggregation_>PADL<_1",
			relationship.getDisplayPath());
	}
	public void testRemoveAddContainerAggregation() {
		final IFirstClassEntity firstClassEntity =
			SanityTest.IdiomLevelModel.getTopLevelEntityFromID("A");
		final IRelationship relationship =
			(IRelationship) firstClassEntity
				.getConstituentFromID("a_ContainerAggregation_>PADL<_1");
		firstClassEntity.removeConstituentFromID("a_ContainerAggregation_>PADL<_1"
			.toCharArray());
		firstClassEntity.addConstituent(relationship);
		Assert.assertEquals(
			"Path is right",
			"/Model|p|A|a_ContainerAggregation_>PADL<_2",
			relationship.getDisplayPath());
	}
	public void testRemoveAddMethod() {
		final IFirstClassEntity firstClassEntity =
			SanityTest.IdiomLevelModel.getTopLevelEntityFromID("A");
		final IMethod getter =
			(IMethod) firstClassEntity.getConstituentFromID("get");
		firstClassEntity.removeConstituentFromID("get".toCharArray());
		firstClassEntity.addConstituent(getter);
		Assert.assertEquals(
			"Path is right",
			"/Model|p|A|get()",
			getter.getDisplayPath());
	}
	public void testRemoveAddParameter() {
		final IFirstClassEntity firstClassEntity =
			SanityTest.IdiomLevelModel.getTopLevelEntityFromID("A");
		final IMethod setter =
			(IMethod) firstClassEntity.getConstituentFromID("set");
		final IParameter parameter =
			Factory.getInstance().createParameter(firstClassEntity, 2);
		setter.addConstituent(parameter);
		Assert.assertEquals("Method ID", "set", setter.getDisplayID());
		Assert.assertEquals("Method Name", "set", setter.getDisplayName());
		Assert.assertEquals(
			"Method Path",
			"/Model|p|A|set(A, A[])",
			setter.getDisplayPath());

		// Yann 2013/05/14: Remove what I added
		// When running the tests in the complete test suite
		// tests may be run in different order, so I make sure
		// that I leave the getter "as it was".
		setter.removeConstituentFromID(parameter.getID());
		Assert.assertEquals(
			"Method Path",
			"/Model|p|A|set(A)",
			setter.getDisplayPath());
	}
	public void testAddRemoveClass1() {
		final IFirstClassEntity firstClassEntity =
			Factory.getInstance().createClass(
				"ClassToRemoveForTest".toCharArray(),
				"ClassToRemoveForTest".toCharArray());
		SanityTest.IdiomLevelModel.addConstituent(firstClassEntity);
		SanityTest.IdiomLevelModel.removeTopLevelEntityFromID(firstClassEntity
			.getID());
		Assert.assertEquals(
			"Class name",
			"ClassToRemoveForTest",
			firstClassEntity.getDisplayPath());
	}
	public void testAddRemoveClass2() {
		final IFirstClassEntity firstClassEntity =
			Factory.getInstance().createClass(
				"ClassToRemoveForTest".toCharArray(),
				"ClassToRemoveForTest".toCharArray());
		((IPackage) SanityTest.IdiomLevelModel.getConstituentFromID("p"))
			.addConstituent(firstClassEntity);
		SanityTest.IdiomLevelModel.removeTopLevelEntityFromID(firstClassEntity
			.getID());
		Assert.assertEquals(
			"Class name",
			"ClassToRemoveForTest",
			firstClassEntity.getDisplayPath());
	}
}
