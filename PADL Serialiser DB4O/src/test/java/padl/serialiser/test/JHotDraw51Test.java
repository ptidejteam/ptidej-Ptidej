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
package padl.serialiser.test;

import java.io.File;
import java.util.Iterator;

import org.junit.Assert;

import junit.framework.TestCase;
import padl.analysis.UnsupportedSourceModelException;
import padl.analysis.repository.AACRelationshipsAnalysis;
import padl.creator.classfile.CompleteClassFileCreator;
import padl.kernel.IAbstractLevelModel;
import padl.kernel.ICodeLevelModel;
import padl.kernel.IFirstClassEntity;
import padl.kernel.IIdiomLevelModel;
import padl.kernel.exception.CreationException;
import padl.kernel.impl.Factory;
import padl.serialiser.DB4OSerialiser;
import padl.util.Util;

public class JHotDraw51Test extends TestCase {
	private static IAbstractLevelModel AbstractLevelModel;
	private static IAbstractLevelModel SerialisedAbstractLevelModel;
	private static String SerialisedFileName;

	public JHotDraw51Test(String name) {
		super(name);
	}

	protected void setUp()
			throws UnsupportedSourceModelException, CreationException {

		if (JHotDraw51Test.AbstractLevelModel == null) {
			final ICodeLevelModel codeLevelModel = Factory.getInstance()
					.createCodeLevelModel("JHotDraw v5.1");
			codeLevelModel.create(new CompleteClassFileCreator(new String[] {
					"../PADL Serialiser DB4O/target/test-classes/JHotDraw v5.1/" },
					true));

			JHotDraw51Test.AbstractLevelModel = (IIdiomLevelModel) new AACRelationshipsAnalysis()
					.invoke(codeLevelModel);

			JHotDraw51Test.SerialisedFileName = DB4OSerialiser.getInstance()
					.serialiseWithAutomaticNaming(
							JHotDraw51Test.AbstractLevelModel);
			JHotDraw51Test.SerialisedAbstractLevelModel = (IAbstractLevelModel) DB4OSerialiser
					.getInstance()
					.deserialise(JHotDraw51Test.SerialisedFileName);
		}
	}

	protected void tearDown() {
		final File serialisedFile = new File(JHotDraw51Test.SerialisedFileName);
		serialisedFile.delete();
	}

	public void testNames() {
		Assert.assertEquals(JHotDraw51Test.AbstractLevelModel.getDisplayName(),
				JHotDraw51Test.SerialisedAbstractLevelModel.getDisplayName());
	}

	public void testElements() {
		final IFirstClassEntity[] originalEntities = Util
				.getArrayOfTopLevelEntities(JHotDraw51Test.AbstractLevelModel);
		final IFirstClassEntity[] clonedEntities = Util
				.getArrayOfTopLevelEntities(
						JHotDraw51Test.SerialisedAbstractLevelModel);

		Assert.assertEquals("Number of entities", originalEntities.length,
				clonedEntities.length);

		for (int i = 0; i < originalEntities.length; i++) {
			Assert.assertEquals("Number of inherited or implemented entities",
					((IFirstClassEntity) originalEntities[i])
							.getNumberOfInheritedEntities(),
					((IFirstClassEntity) clonedEntities[i])
							.getNumberOfInheritedEntities());

			final Iterator originalPEntityInherits = ((IFirstClassEntity) originalEntities[i])
					.getIteratorOnInheritedEntities();
			final Iterator clonedPEntityInherits = ((IFirstClassEntity) clonedEntities[i])
					.getIteratorOnInheritedEntities();
			while (clonedPEntityInherits.hasNext()) {
				final IFirstClassEntity originalEntity = (IFirstClassEntity) originalPEntityInherits
						.next();
				final IFirstClassEntity clonedEntity = (IFirstClassEntity) clonedPEntityInherits
						.next();

				Assert.assertEquals(originalEntity.getDisplayName(),
						clonedEntity.getDisplayName());
				Assert.assertTrue(
						"Identity hashcodes of the entities are different",
						System.identityHashCode(originalEntity) != System
								.identityHashCode(clonedEntity));
			}
		}
	}

	public void testEntities() {
		final IFirstClassEntity[] originalEntities = Util
				.getArrayOfTopLevelEntities(JHotDraw51Test.AbstractLevelModel);
		final IFirstClassEntity[] clonedEntities = Util
				.getArrayOfTopLevelEntities(
						JHotDraw51Test.SerialisedAbstractLevelModel);

		Assert.assertEquals("Number of entities", originalEntities.length,
				clonedEntities.length);

		for (int i = 0; i < originalEntities.length; i++) {
			// Yann 2006/02/21: Member entities...
			// Two member entities may have identical names,
			// as well as identical other attributes but for
			// their JVM-based object ID, which I now use for
			// equals(). In the case of clones, I test on their
			// hash codes...
			Assert.assertEquals("Entities are equal",
					originalEntities[i].getDisplayID(),
					clonedEntities[i].getDisplayID());
			Assert.assertTrue(
					"Identity hashcodes of the entities are different",
					System.identityHashCode(originalEntities[i]) != System
							.identityHashCode(clonedEntities[i]));
		}
	}

	public void testInheritance() {
		final IFirstClassEntity[] originalEntities = Util
				.getArrayOfTopLevelEntities(JHotDraw51Test.AbstractLevelModel);
		final IFirstClassEntity[] clonedEntities = Util
				.getArrayOfTopLevelEntities(
						JHotDraw51Test.SerialisedAbstractLevelModel);

		Assert.assertEquals("Number of entities", originalEntities.length,
				clonedEntities.length);

		for (int i = 0; i < originalEntities.length; i++) {
			Assert.assertEquals("Number of inherited or implemented entities",
					((IFirstClassEntity) originalEntities[i])
							.getNumberOfInheritedEntities(),
					((IFirstClassEntity) clonedEntities[i])
							.getNumberOfInheritedEntities());

			final Iterator originalPEntityInherits = ((IFirstClassEntity) originalEntities[i])
					.getIteratorOnInheritedEntities();
			final Iterator clonedPEntityInherits = ((IFirstClassEntity) clonedEntities[i])
					.getIteratorOnInheritedEntities();
			while (clonedPEntityInherits.hasNext()) {
				final IFirstClassEntity originalEntity = (IFirstClassEntity) originalPEntityInherits
						.next();
				final IFirstClassEntity clonedEntity = (IFirstClassEntity) clonedPEntityInherits
						.next();

				Assert.assertEquals(originalEntity.getDisplayName(),
						clonedEntity.getDisplayName());
				Assert.assertTrue(
						"Identity hashcodes of the entities are different",
						System.identityHashCode(originalEntity) != System
								.identityHashCode(clonedEntity));
			}
		}
	}
}
