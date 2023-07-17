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
import padl.analysis.UnsupportedSourceModelException;
import padl.analysis.repository.AACRelationshipsAnalysis;
import padl.creator.classfile.CompleteClassFileCreator;
import padl.creator.classfile.test.ClassFilePrimitive;
import padl.kernel.IClass;
import padl.kernel.ICodeLevelModel;
import padl.kernel.IFirstClassEntity;
import padl.kernel.IGhost;
import padl.kernel.IIdiomLevelModel;
import padl.kernel.IInterface;
import padl.kernel.exception.CreationException;
import padl.util.Util;

/**
 * @version	0.1
 * @author 	Yann-Gaël Guéhéneuc
 */
public class SuperEntitiesConnection extends ClassFilePrimitive {
	private static IFirstClassEntity[] FirstClassEntities;

	public SuperEntitiesConnection(final String name) {
		super(name);
	}
	protected void setUp()
			throws IllegalAccessException, InstantiationException,
			CreationException, UnsupportedSourceModelException {

		if (SuperEntitiesConnection.FirstClassEntities == null) {
			final ICodeLevelModel codeLevelModel = ClassFilePrimitive
				.getFactory()
				.createCodeLevelModel("padl.example.composite1");
			codeLevelModel.create(
				new CompleteClassFileCreator(
					new String[] {
							"../PADL Creator ClassFile Tests/bin/padl/example/composite1/" }));

			final IIdiomLevelModel idiomLevelModel =
				(IIdiomLevelModel) new AACRelationshipsAnalysis()
					.invoke(codeLevelModel);

			SuperEntitiesConnection.FirstClassEntities =
				Util.getArrayOfTopLevelEntities(idiomLevelModel);
		}
	}
	public void testNumberOfEntities() {
		Assert.assertEquals(
			"Number of entities",
			11,
			SuperEntitiesConnection.FirstClassEntities.length);
	}
	public void testDocument() {
		ClassFilePrimitive.assertAssigable(
			"Document class type",
			IClass.class,
			SuperEntitiesConnection.FirstClassEntities[5].getClass());
		Assert.assertEquals(
			"Document class name",
			"padl.example.composite1.Document",
			SuperEntitiesConnection.FirstClassEntities[5].getDisplayID());

		Assert.assertEquals(
			"Document class number of super-classes",
			true,
			((IClass) SuperEntitiesConnection.FirstClassEntities[5])
				.getIteratorOnInheritedEntities()
				.hasNext());
		final Object object =
			((IClass) SuperEntitiesConnection.FirstClassEntities[5])
				.getIteratorOnInheritedEntities()
				.next();
		Assert.assertEquals(
			"Document class number of super-classes",
			true,
			((IClass) SuperEntitiesConnection.FirstClassEntities[5])
				.getIteratorOnInheritedEntities()
				.hasNext());
		ClassFilePrimitive.assertAssigable(
			"Document class super-class type",
			IGhost.class,
			object.getClass());
		Assert.assertEquals(
			"Document class super-class name",
			"java.lang.Object",
			((IGhost) object).getDisplayID());

		Assert.assertEquals(
			"Document class number of super-interfaces",
			false,
			((IClass) SuperEntitiesConnection.FirstClassEntities[5])
				.getIteratorOnImplementedInterfaces()
				.hasNext());
	}
	public void testIndentedParagraph() {
		ClassFilePrimitive.assertAssigable(
			"IndentedParagraph class type",
			IClass.class,
			SuperEntitiesConnection.FirstClassEntities[7].getClass());
		Assert.assertEquals(
			"IndentedParagraph class name",
			"padl.example.composite1.IndentedParagraph",
			SuperEntitiesConnection.FirstClassEntities[7].getDisplayID());

		final Iterator iterator =
			((IClass) SuperEntitiesConnection.FirstClassEntities[7])
				.getIteratorOnInheritedEntities();
		Assert.assertEquals(
			"IndentedParagraph class number of super-classes",
			true,
			iterator.hasNext());
		final Object object = iterator.next();
		Assert.assertEquals(
			"IndentedParagraph class number of super-classes",
			false,
			iterator.hasNext());
		ClassFilePrimitive.assertAssigable(
			"IndentedParagraph class super-class type",
			IClass.class,
			object.getClass());
		Assert.assertEquals(
			"IndentedParagraph class super-class name",
			"padl.example.composite1.Paragraph",
			((IClass) object).getDisplayID());

		Assert.assertEquals(
			"IndentedParagraph class number of super-interfaces",
			false,
			((IClass) SuperEntitiesConnection.FirstClassEntities[7])
				.getIteratorOnImplementedInterfaces()
				.hasNext());
	}
	public void testMain() {
		ClassFilePrimitive.assertAssigable(
			"Main class type",
			IClass.class,
			SuperEntitiesConnection.FirstClassEntities[8].getClass());
		Assert.assertEquals(
			"Main class name",
			"padl.example.composite1.Main",
			SuperEntitiesConnection.FirstClassEntities[8].getDisplayID());

		final Iterator iterator =
			((IClass) SuperEntitiesConnection.FirstClassEntities[8])
				.getIteratorOnInheritedEntities();
		Assert.assertEquals(
			"Main class number of super-classes",
			true,
			iterator.hasNext());
		final Object object = iterator.next();
		Assert.assertEquals(
			"Main class number of super-classes",
			false,
			iterator.hasNext());
		ClassFilePrimitive.assertAssigable(
			"Main class super-class type",
			IGhost.class,
			object.getClass());
		Assert.assertEquals(
			"Main class super-class name",
			"java.lang.Object",
			((IGhost) object).getDisplayID());

		Assert.assertEquals(
			"Main class number of super-interfaces",
			false,
			((IClass) SuperEntitiesConnection.FirstClassEntities[8])
				.getIteratorOnImplementedInterfaces()
				.hasNext());
	}
	public void testParagraph() {
		ClassFilePrimitive.assertAssigable(
			"Paragraph class type",
			IClass.class,
			SuperEntitiesConnection.FirstClassEntities[9].getClass());
		Assert.assertEquals(
			"Paragraph class name",
			"padl.example.composite1.Paragraph",
			SuperEntitiesConnection.FirstClassEntities[9].getDisplayID());

		Iterator iterator =
			((IClass) SuperEntitiesConnection.FirstClassEntities[9])
				.getIteratorOnInheritedEntities();
		Assert.assertEquals(
			"Paragraph class number of super-classes",
			true,
			iterator.hasNext());
		Object object = iterator.next();
		Assert.assertEquals(
			"Paragraph class number of super-classes",
			false,
			iterator.hasNext());
		ClassFilePrimitive.assertAssigable(
			"Paragraph class super-class type",
			IGhost.class,
			object.getClass());
		Assert.assertEquals(
			"Paragraph class super-class name",
			"java.lang.Object",
			((IGhost) object).getDisplayID());

		iterator = ((IClass) SuperEntitiesConnection.FirstClassEntities[9])
			.getIteratorOnImplementedInterfaces();
		Assert.assertEquals(
			"Paragraph class number of super-interfaces",
			true,
			iterator.hasNext());
		object = iterator.next();
		Assert.assertEquals(
			"Paragraph class number of super-interfaces",
			false,
			iterator.hasNext());
		ClassFilePrimitive.assertAssigable(
			"Paragraph class super-interface type",
			IInterface.class,
			object.getClass());
		Assert.assertEquals(
			"Paragraph class super-interface name",
			"padl.example.composite1.Element",
			((IInterface) object).getDisplayID());
	}
	public void testTitle() {
		ClassFilePrimitive.assertAssigable(
			"Title class type",
			IClass.class,
			SuperEntitiesConnection.FirstClassEntities[10].getClass());
		Assert.assertEquals(
			"Title class name",
			"padl.example.composite1.Title",
			SuperEntitiesConnection.FirstClassEntities[10].getDisplayID());

		Iterator iterator =
			((IClass) SuperEntitiesConnection.FirstClassEntities[10])
				.getIteratorOnInheritedEntities();
		Assert.assertEquals(
			"Title class number of super-classes",
			true,
			iterator.hasNext());
		Object object = iterator.next();
		Assert.assertEquals(
			"Title class number of super-classes",
			false,
			iterator.hasNext());
		ClassFilePrimitive.assertAssigable(
			"Title class super-class type",
			IGhost.class,
			object.getClass());
		Assert.assertEquals(
			"Title class super-class name",
			"java.lang.Object",
			((IGhost) object).getDisplayID());

		iterator = ((IClass) SuperEntitiesConnection.FirstClassEntities[10])
			.getIteratorOnImplementedInterfaces();
		Assert.assertEquals(
			"Title class number of super-interfaces",
			true,
			iterator.hasNext());
		object = iterator.next();
		Assert.assertEquals(
			"Title class number of super-interfaces",
			false,
			iterator.hasNext());
		ClassFilePrimitive.assertAssigable(
			"Title class super-interface type",
			IInterface.class,
			object.getClass());
		Assert.assertEquals(
			"Title class super-interface name",
			"padl.example.composite1.Element",
			((IInterface) object).getDisplayID());
	}
	public void testObject() {
		ClassFilePrimitive.assertAssigable(
			"Object ghost type",
			IGhost.class,
			SuperEntitiesConnection.FirstClassEntities[0].getClass());
		Assert.assertEquals(
			"Object ghost name",
			"java.lang.Object",
			SuperEntitiesConnection.FirstClassEntities[0].getDisplayID());
	}
	public void testAbstractDocument() {
		ClassFilePrimitive.assertAssigable(
			"AbstractDocument interface type",
			IInterface.class,
			SuperEntitiesConnection.FirstClassEntities[4].getClass());
		Assert.assertEquals(
			"AbstractDocument interface name",
			"padl.example.composite1.AbstractDocument",
			SuperEntitiesConnection.FirstClassEntities[4].getDisplayID());

		Assert.assertEquals(
			"AbstractDocument class number of super-interfaces",
			true,
			((IInterface) SuperEntitiesConnection.FirstClassEntities[4])
				.getIteratorOnInheritedEntities()
				.hasNext());
		ClassFilePrimitive.assertAssigable(
			"AbstractDocument class super-interface type",
			IGhost.class,
			SuperEntitiesConnection.FirstClassEntities[4]
				.getIteratorOnInheritedEntities()
				.next()
				.getClass());
		Assert.assertEquals(
			"AbstractDocument class super-interface name",
			"java.lang.Object",
			((IGhost) SuperEntitiesConnection.FirstClassEntities[4]
				.getIteratorOnInheritedEntities()
				.next()).getDisplayID());
	}
	public void testElement() {
		ClassFilePrimitive.assertAssigable(
			"Element interface type",
			IInterface.class,
			SuperEntitiesConnection.FirstClassEntities[6].getClass());
		Assert.assertEquals(
			"Element interface name",
			"padl.example.composite1.Element",
			SuperEntitiesConnection.FirstClassEntities[6].getDisplayID());

		Assert.assertEquals(
			"Element class number of super-interfaces",
			true,
			((IInterface) SuperEntitiesConnection.FirstClassEntities[6])
				.getIteratorOnInheritedEntities()
				.hasNext());
		final Iterator iterator = SuperEntitiesConnection.FirstClassEntities[6]
			.getIteratorOnInheritedEntities();
		iterator.next();
		final Object object = ((IInterface) iterator.next());
		Assert.assertEquals(
			"Element class number of super-interfaces",
			true,
			((IInterface) SuperEntitiesConnection.FirstClassEntities[6])
				.getIteratorOnInheritedEntities()
				.hasNext());
		ClassFilePrimitive.assertAssigable(
			"Element class super-interface type",
			IInterface.class,
			object.getClass());
		Assert.assertEquals(
			"Element class super-interface name",
			"padl.example.composite1.AbstractDocument",
			((IInterface) object).getDisplayID());
	}
	public void testVector() {
		ClassFilePrimitive.assertAssigable(
			"Vector ghost type",
			IGhost.class,
			SuperEntitiesConnection.FirstClassEntities[3].getClass());
		Assert.assertEquals(
			"Vector ghost name",
			"java.util.Vector",
			SuperEntitiesConnection.FirstClassEntities[3].getDisplayID());
	}
	public void testEnumeration() {
		ClassFilePrimitive.assertAssigable(
			"Enumeration ghost type",
			IGhost.class,
			SuperEntitiesConnection.FirstClassEntities[2].getClass());
		Assert.assertEquals(
			"Enumeration ghost name",
			"java.util.Enumeration",
			SuperEntitiesConnection.FirstClassEntities[2].getDisplayID());
	}
	public void testString() {
		ClassFilePrimitive.assertAssigable(
			"String ghost type",
			IGhost.class,
			SuperEntitiesConnection.FirstClassEntities[1].getClass());
		Assert.assertEquals(
			"String ghost name",
			"java.lang.String",
			SuperEntitiesConnection.FirstClassEntities[1].getDisplayID());
	}
}
