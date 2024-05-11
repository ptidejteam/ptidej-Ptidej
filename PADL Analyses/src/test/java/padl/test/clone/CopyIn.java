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
package padl.test.clone;

import org.junit.Assert;
import junit.framework.TestCase;
import padl.analysis.UnsupportedSourceModelException;
import padl.analysis.repository.AACRelationshipsAnalysis;
import padl.kernel.Constants;
import padl.kernel.ICodeLevelModel;
import padl.kernel.IField;
import padl.kernel.IFirstClassEntity;
import padl.kernel.IIdiomLevelModel;
import padl.kernel.IMethod;
import padl.kernel.IPackage;
import padl.kernel.IParameter;
import padl.kernel.impl.Factory;
import padl.util.Util;
import util.io.ProxyConsole;

public final class CopyIn extends TestCase {
	private static final char[] ORIGINAL_MODEL_NAME = "Original Model"
		.toCharArray();
	private static final char[] DESTINATION_MODEL_NAME = "Destination Model"
		.toCharArray();
	private static IIdiomLevelModel OriginalIdiomLevelModel;
	private static IIdiomLevelModel DestinationIdiomLevelModel;

	public CopyIn(String name) {
		super(name);
	}
	protected void setUp() {
		if (CopyIn.OriginalIdiomLevelModel == null) {
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
					Factory.getInstance().createCodeLevelModel(
						CopyIn.ORIGINAL_MODEL_NAME);
				aCodeLevelModel.addConstituent(aPackage);

				CopyIn.OriginalIdiomLevelModel =
					(IIdiomLevelModel) new AACRelationshipsAnalysis()
						.invoke(aCodeLevelModel);
			}
			catch (final UnsupportedSourceModelException e) {
				e.printStackTrace(ProxyConsole.getInstance().errorOutput());
			}

			CopyIn.DestinationIdiomLevelModel =
				Factory.getInstance().createIdiomLevelModel(
					CopyIn.DESTINATION_MODEL_NAME);
			CopyIn.OriginalIdiomLevelModel
				.moveIn(CopyIn.DestinationIdiomLevelModel);
		}
	}
	public void testElements() {
		final IFirstClassEntity[] clonedEntities =
			Util.getArrayOfTopLevelEntities(CopyIn.DestinationIdiomLevelModel);

		Assert.assertEquals("Number of entities", 1, clonedEntities.length);
		Assert.assertEquals(
			"/Destination Model|p|A",
			clonedEntities[0].getDisplayPath());

		final IField field =
			(IField) clonedEntities[0].getConstituentFromID("a");
		Assert.assertEquals(
			"/Destination Model|p|A|a",
			field.getDisplayPath());

		final IMethod getter =
			(IMethod) clonedEntities[0].getConstituentFromID("get");
		Assert.assertEquals(
			"/Destination Model|p|A|get()",
			getter.getDisplayPath());

		final IMethod setter =
			(IMethod) clonedEntities[0].getConstituentFromID("set");
		Assert.assertEquals(
			"/Destination Model|p|A|set(A)",
			setter.getDisplayPath());
	}
	public void testEntities() {
		final IFirstClassEntity[] originalEntities =
			Util.getArrayOfTopLevelEntities(CopyIn.OriginalIdiomLevelModel);
		final IFirstClassEntity[] clonedEntities =
			Util.getArrayOfTopLevelEntities(CopyIn.DestinationIdiomLevelModel);

		Assert.assertEquals("Number of entities", 0, originalEntities.length);
		Assert.assertEquals("Number of entities", 1, clonedEntities.length);

		Assert.assertEquals(
			1,
			clonedEntities[0].getDisplayPath().indexOf(
				String.valueOf(CopyIn.DESTINATION_MODEL_NAME)));
		Assert.assertEquals(
			-1,
			clonedEntities[0].getDisplayPath().indexOf(
				String.valueOf(CopyIn.ORIGINAL_MODEL_NAME)));
	}
	public void testInheritance() {
		final IFirstClassEntity[] clonedEntities =
			Util.getArrayOfTopLevelEntities(CopyIn.DestinationIdiomLevelModel);

		Assert.assertEquals("Number of entities", 1, clonedEntities.length);
	}
	public void testModelIdentities() {
		Assert.assertTrue(
			"Identity hashcodes of the patterns are different",
			System.identityHashCode(CopyIn.OriginalIdiomLevelModel) != System
				.identityHashCode(CopyIn.DestinationIdiomLevelModel));
	}
}
