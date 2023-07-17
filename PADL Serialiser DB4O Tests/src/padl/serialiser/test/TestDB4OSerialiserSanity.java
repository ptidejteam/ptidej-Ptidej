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
import padl.kernel.IFirstClassEntity;
import padl.motif.IDesignMotifModel;
import padl.motif.repository.Composite;
import padl.serialiser.DB4OSerialiser;
import padl.util.Util;

public class TestDB4OSerialiserSanity extends TestCase {
	private IDesignMotifModel originalModel;
	private IDesignMotifModel serialisedModel;
	private String serialisedFileName;

	public TestDB4OSerialiserSanity(String name) {
		super(name);
	}
	protected void setUp() {
		this.originalModel = new Composite();
		this.serialisedFileName =
			DB4OSerialiser.getInstance().serialiseWithAutomaticNaming(
				this.originalModel);
		this.serialisedModel =
			(IDesignMotifModel) DB4OSerialiser.getInstance().deserialise(
				this.serialisedFileName);
	}
	protected void tearDown() {
		final File serialisedFile = new File(this.serialisedFileName);
		serialisedFile.delete();
	}
	public void testNames() {
		Assert.assertEquals(
			this.originalModel.getDisplayName(),
			this.serialisedModel.getDisplayName());
	}
	public void testElements() {
		final IFirstClassEntity[] originalEntities =
			Util.getArrayOfTopLevelEntities(this.originalModel);
		final IFirstClassEntity[] clonedEntities =
			Util.getArrayOfTopLevelEntities(this.serialisedModel);

		Assert.assertEquals(
			"Number of entities",
			originalEntities.length,
			clonedEntities.length);

		for (int i = 0; i < originalEntities.length; i++) {
			Assert.assertEquals(
				"Number of inherited or implemented entities",
				((IFirstClassEntity) originalEntities[i])
					.getNumberOfInheritedEntities(),
				((IFirstClassEntity) clonedEntities[i])
					.getNumberOfInheritedEntities());

			final Iterator originalPEntityInherits =
				((IFirstClassEntity) originalEntities[i])
					.getIteratorOnInheritedEntities();
			final Iterator clonedPEntityInherits =
				((IFirstClassEntity) clonedEntities[i])
					.getIteratorOnInheritedEntities();
			while (clonedPEntityInherits.hasNext()) {
				final IFirstClassEntity originalEntity =
					(IFirstClassEntity) originalPEntityInherits.next();
				final IFirstClassEntity clonedEntity =
					(IFirstClassEntity) clonedPEntityInherits.next();

				Assert.assertEquals(originalEntity, clonedEntity);
				Assert.assertTrue(
					"Identity hashcodes of the entities are different",
					System.identityHashCode(originalEntity) != System
						.identityHashCode(clonedEntity));
			}
		}
	}
	public void testEntities() {
		final IFirstClassEntity[] originalEntities =
			Util.getArrayOfTopLevelEntities(this.originalModel);
		final IFirstClassEntity[] clonedEntities =
			Util.getArrayOfTopLevelEntities(this.serialisedModel);

		Assert.assertEquals(
			"Number of entities",
			originalEntities.length,
			clonedEntities.length);

		for (int i = 0; i < originalEntities.length; i++) {
			// Yann 2006/02/21: Member entities...
			// Two member entities may have identical names,
			// as well as identical other attributes but for
			// their JVM-based object ID, which I now use for
			// equals(). In the case of clones, I test on their
			// hash codes...
			Assert.assertEquals(
				"Entities are equal",
				originalEntities[i].getDisplayID(),
				clonedEntities[i].getDisplayID());
			Assert.assertTrue(
				"Identity hashcodes of the entities are different",
				System.identityHashCode(originalEntities[i]) != System
					.identityHashCode(clonedEntities[i]));
		}
	}
	public void testInheritance() {
		final IFirstClassEntity[] originalEntities =
			Util.getArrayOfTopLevelEntities(this.originalModel);
		final IFirstClassEntity[] clonedEntities =
			Util.getArrayOfTopLevelEntities(this.serialisedModel);

		Assert.assertEquals(
			"Number of entities",
			originalEntities.length,
			clonedEntities.length);

		for (int i = 0; i < originalEntities.length; i++) {
			Assert.assertEquals(
				"Number of inherited or implemented entities",
				((IFirstClassEntity) originalEntities[i])
					.getNumberOfInheritedEntities(),
				((IFirstClassEntity) clonedEntities[i])
					.getNumberOfInheritedEntities());

			final Iterator originalPEntityInherits =
				((IFirstClassEntity) originalEntities[i])
					.getIteratorOnInheritedEntities();
			final Iterator clonedPEntityInherits =
				((IFirstClassEntity) clonedEntities[i])
					.getIteratorOnInheritedEntities();
			while (clonedPEntityInherits.hasNext()) {
				final IFirstClassEntity originalEntity =
					(IFirstClassEntity) originalPEntityInherits.next();
				final IFirstClassEntity clonedEntity =
					(IFirstClassEntity) clonedPEntityInherits.next();

				Assert.assertEquals(originalEntity, clonedEntity);
				Assert.assertTrue(
					"Identity hashcodes of the entities are different",
					System.identityHashCode(originalEntity) != System
						.identityHashCode(clonedEntity));
			}
		}
	}
	public void testModels() {
		Assert.assertEquals("Models", this.originalModel, this.serialisedModel);
		Assert.assertEquals(
			"Model names",
			this.originalModel.getDisplayName(),
			this.serialisedModel.getDisplayName());
		Assert.assertEquals(
			"Model IDs",
			this.originalModel.getDisplayID(),
			this.serialisedModel.getDisplayID());
		Assert.assertTrue(
			"Identity hashcodes of the model are different",
			System.identityHashCode(this.originalModel) != System
				.identityHashCode(this.serialisedModel));
	}
}
