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

import java.util.Iterator;
import org.junit.Assert;
import padl.creator.classfile.CompleteClassFileCreator;
import padl.creator.classfile.test.ClassFilePrimitive;
import padl.kernel.ICodeLevelModel;
import padl.kernel.IFirstClassEntity;
import padl.kernel.IGhost;
import padl.kernel.IInterface;
import padl.kernel.exception.CreationException;
import padl.util.Util;

/**
 * @version	0.2
 * @author 	Yann-Gaël Guéhéneuc
 * @since   2003/12/08
 */
public class Interfaces extends ClassFilePrimitive {
	private static IFirstClassEntity[] FirstClassEntities = null;

	public Interfaces(final String name) {
		super(name);
	}
	protected void setUp() throws CreationException {
		if (Interfaces.FirstClassEntities == null) {
			final ICodeLevelModel codeLevelModel = ClassFilePrimitive
				.getFactory()
				.createCodeLevelModel("padl.example.interfaces");

			// TODO: Load all types from padl.example.interfaces
			codeLevelModel.create(
				new CompleteClassFileCreator(
					new String[] {
							"../PADL Creator ClassFile Tests/bin/padl/example/interfaces/Element.class",
							"../PADL Creator ClassFile Tests/bin/padl/example/interfaces/IAdaptable.class",
							"../PADL Creator ClassFile Tests/bin/padl/example/interfaces/IJavaElement.class",
							"../PADL Creator ClassFile Tests/bin/padl/example/interfaces/Simple.class" }));

			Interfaces.FirstClassEntities =
				Util.getArrayOfTopLevelEntities(codeLevelModel);
		}
	}
	public void testNumberOfEntities() {
		Assert.assertEquals(
			"Number of entities",
			6,
			Interfaces.FirstClassEntities.length);
	}
	//	public void testObject() {
	//		Interfaces.assertAssigable(
	//			"Object ghost entity type",
	//			IGhost.class,
	//			Interfaces.Entities[0].getClass());
	//		Assert.assertEquals(
	//			"Object ghost entity name",
	//			"java.lang.Object",
	//			Interfaces.Entities[0].getDisplayName());
	//	}
	public void testAbstractDocument() {
		ClassFilePrimitive.assertAssigable(
			"AbstractDocument ghost entity type",
			IGhost.class,
			Interfaces.FirstClassEntities[1].getClass());
		Assert.assertEquals(
			"AbstractDocument ghost entity name",
			"padl.example.interfaces.AbstractDocument",
			Interfaces.FirstClassEntities[1].getDisplayID());
	}
	public void testElement() {
		ClassFilePrimitive.assertAssigable(
			"Element interface entity type",
			IInterface.class,
			Interfaces.FirstClassEntities[2].getClass());
		Assert.assertEquals(
			"Element interface entity name",
			"padl.example.interfaces.Element",
			Interfaces.FirstClassEntities[2].getDisplayID());

		final Iterator inheritedEntities =
			Interfaces.FirstClassEntities[2].getIteratorOnInheritedEntities();
		Assert.assertEquals(
			"Element interface number of inherited entities",
			true,
			inheritedEntities.hasNext());
		Object object = inheritedEntities.next();
		Assert.assertEquals(
			"Element interface number of inherited entities",
			true,
			inheritedEntities.hasNext());
		object = inheritedEntities.next();
		Assert.assertEquals(
			"Element interface number of inherited entities",
			false,
			inheritedEntities.hasNext());
		ClassFilePrimitive.assertAssigable(
			"Element interface type of the super-entity",
			IGhost.class,
			object.getClass());
		Assert.assertEquals(
			"Element interface name of the super-entity",
			"padl.example.interfaces.AbstractDocument",
			((IGhost) object).getDisplayID());
	}
	public void testIAdaptable() {
		ClassFilePrimitive.assertAssigable(
			"IAdaptable interface entity type",
			IInterface.class,
			Interfaces.FirstClassEntities[3].getClass());
		Assert.assertEquals(
			"IAdaptable interface entity name",
			"padl.example.interfaces.IAdaptable",
			Interfaces.FirstClassEntities[3].getDisplayID());

		Assert.assertEquals(
			"IAdaptable interface number of inherited entities",
			true,
			Interfaces.FirstClassEntities[2]
				.getIteratorOnInheritedEntities()
				.hasNext());
		ClassFilePrimitive.assertAssigable(
			"IAdaptable interface type of the super-entity",
			IGhost.class,
			Interfaces.FirstClassEntities[2]
				.getIteratorOnInheritedEntities()
				.next()
				.getClass());
		Assert.assertEquals(
			"IAdaptable interface name of the super-entity",
			"java.lang.Object",
			((IGhost) Interfaces.FirstClassEntities[2]
				.getIteratorOnInheritedEntities()
				.next()).getDisplayID());
	}
	public void testIJavaElement() {
		ClassFilePrimitive.assertAssigable(
			"IJavaElement interface entity type",
			IInterface.class,
			Interfaces.FirstClassEntities[4].getClass());
		Assert.assertEquals(
			"IJavaElement interface entity name",
			"padl.example.interfaces.IJavaElement",
			Interfaces.FirstClassEntities[4].getDisplayID());

		final Iterator iterator =
			Interfaces.FirstClassEntities[4].getIteratorOnInheritedEntities();
		Assert.assertEquals(
			"IJavaElement interface number of inherited entities",
			true,
			iterator.hasNext());
		Object object = iterator.next();
		Assert.assertEquals(
			"IJavaElement interface number of inherited entities",
			true,
			iterator.hasNext());
		object = iterator.next();
		Assert.assertEquals(
			"IJavaElement interface number of inherited entities",
			false,
			iterator.hasNext());
		ClassFilePrimitive.assertAssigable(
			"IJavaElement interface type of the super-entity",
			IInterface.class,
			object.getClass());
		Assert.assertEquals(
			"IJavaElement interface name of the super-entity",
			"padl.example.interfaces.IAdaptable",
			((IInterface) object).getDisplayID());
	}
	public void testSimple() {
		ClassFilePrimitive.assertAssigable(
			"Simple interface entity type",
			IInterface.class,
			Interfaces.FirstClassEntities[5].getClass());
		Assert.assertEquals(
			"Simple interface entity name",
			"padl.example.interfaces.Simple",
			Interfaces.FirstClassEntities[5].getDisplayID());

		Assert.assertEquals(
			"Simple interface number of inherited entities",
			true,
			Interfaces.FirstClassEntities[5]
				.getIteratorOnInheritedEntities()
				.hasNext());
		ClassFilePrimitive.assertAssigable(
			"Simple interface type of the super-entity",
			IGhost.class,
			Interfaces.FirstClassEntities[5]
				.getIteratorOnInheritedEntities()
				.next()
				.getClass());
		Assert.assertEquals(
			"Simple interface name of the super-entity",
			"java.lang.Object",
			((IGhost) Interfaces.FirstClassEntities[5]
				.getIteratorOnInheritedEntities()
				.next()).getDisplayID());
	}
}
