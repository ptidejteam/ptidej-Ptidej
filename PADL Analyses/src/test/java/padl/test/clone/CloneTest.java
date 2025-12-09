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

import java.util.Iterator;
import org.junit.Assert;
import junit.framework.TestCase;
import padl.kernel.IElement;
import padl.kernel.IFirstClassEntity;
import padl.motif.IDesignMotifModel;
import padl.motif.repository.Composite;
import padl.util.Util;

public final class CloneTest extends TestCase {
	private IDesignMotifModel compositePattern;
	private IDesignMotifModel clonedCompositePattern;
	public CloneTest(String name) {
		super(name);
	}
	protected void setUp() throws CloneNotSupportedException {
		this.compositePattern = new Composite();
		this.clonedCompositePattern =
			(IDesignMotifModel) this.compositePattern.clone();
	}
	public void testElements() {
		final IFirstClassEntity[] originalEntities =
			Util.getArrayOfTopLevelEntities(this.compositePattern);
		final IFirstClassEntity[] clonedEntities =
			Util.getArrayOfTopLevelEntities(this.clonedCompositePattern);

		Assert.assertEquals(
			"Number of entities",
			originalEntities.length,
			clonedEntities.length);

		for (int i = 0; i < originalEntities.length; i++) {
			Assert.assertTrue(
				"Number of elements per entities",
				((IFirstClassEntity) originalEntities[i])
					.getNumberOfConstituents() != 0);
			Assert.assertTrue(
				"Number of elements per entities",
				((IFirstClassEntity) clonedEntities[i])
					.getNumberOfConstituents() != 0);

			Assert.assertEquals(
				"Number of elements per entities",
				((IFirstClassEntity) originalEntities[i])
					.getNumberOfConstituents(),
				((IFirstClassEntity) clonedEntities[i])
					.getNumberOfConstituents());

			final Iterator originalEntityElements =
				((IFirstClassEntity) originalEntities[i])
					.getIteratorOnConstituents();
			final Iterator clonedEntityElements =
				((IFirstClassEntity) clonedEntities[i])
					.getIteratorOnConstituents();
			while (originalEntityElements.hasNext()) {
				final IElement originalEntityElement =
					(IElement) originalEntityElements.next();
				final IElement clonedEntityElement =
					(IElement) clonedEntityElements.next();
				Assert.assertEquals(originalEntityElement, clonedEntityElement);
				Assert.assertTrue(
					"Identity hashcodes of the elements are different",
					System.identityHashCode(originalEntityElement) != System
						.identityHashCode(clonedEntityElement));
			}
		}
	}
	public void testEntities() {
		final IFirstClassEntity[] originalEntities =
			Util.getArrayOfTopLevelEntities(this.compositePattern);
		final IFirstClassEntity[] clonedEntities =
			Util.getArrayOfTopLevelEntities(this.clonedCompositePattern);

		Assert.assertEquals("Number of entities", 3, originalEntities.length);
		Assert.assertEquals("Number of entities", 3, clonedEntities.length);

		Assert.assertEquals(
			"Number of entities",
			originalEntities.length,
			clonedEntities.length);

		for (int i = 0; i < originalEntities.length; i++) {
			// Yann 2006/02/21: Member entities...
			// Two member entities may have indentical names,
			// as well as identical other attributes but for
			// their JVM-based object id, which I now use for
			// equals(). In the case of clones, I test on their
			// hash codes...
			Assert.assertEquals(
				"Entities are equal",
				originalEntities[i].hashCode(),
				clonedEntities[i].hashCode());
			Assert.assertTrue(
				"Identity hashcodes of the entities are different",
				System.identityHashCode(originalEntities[i]) != System
					.identityHashCode(clonedEntities[i]));
		}
	}
	public void testInheritance() {
		final IFirstClassEntity[] originalEntities =
			Util.getArrayOfTopLevelEntities(this.compositePattern);
		final IFirstClassEntity[] clonedEntities =
			Util.getArrayOfTopLevelEntities(this.clonedCompositePattern);

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

			final Iterator originalEntityInherits =
				((IFirstClassEntity) originalEntities[i])
					.getIteratorOnInheritedEntities();
			final Iterator clonedEntityInherits =
				((IFirstClassEntity) clonedEntities[i])
					.getIteratorOnInheritedEntities();

			while (originalEntityInherits.hasNext()) {
				final IFirstClassEntity originalInheritedEntity =
					(IFirstClassEntity) originalEntityInherits.next();
				final IFirstClassEntity clonedInheritedEntity =
					(IFirstClassEntity) clonedEntityInherits.next();
				Assert.assertEquals(
					originalInheritedEntity,
					clonedInheritedEntity);
				Assert.assertTrue(
					"Identity hashcodes of the entities are different",
					System.identityHashCode(originalInheritedEntity) != System
						.identityHashCode(clonedInheritedEntity));
			}
		}
	}
	public void testPatterns() {
		Assert.assertEquals(
			"Pattern names",
			this.compositePattern.getName(),
			this.clonedCompositePattern.getName());
		Assert.assertTrue(
			"Identity hashcodes of the patterns are different",
			System.identityHashCode(this.compositePattern) != System
				.identityHashCode(this.clonedCompositePattern));
	}
}
