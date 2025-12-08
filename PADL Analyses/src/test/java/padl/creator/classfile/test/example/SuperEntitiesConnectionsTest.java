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
public class SuperEntitiesConnectionsTest extends ClassFilePrimitive {
	private static IFirstClassEntity[] FirstClassEntities;

	public SuperEntitiesConnectionsTest(final String name) {
		super(name);
	}

	protected void setUp()
			throws IllegalAccessException, InstantiationException,
			CreationException, UnsupportedSourceModelException {

		if (SuperEntitiesConnectionsTest.FirstClassEntities == null) {
			final ICodeLevelModel codeLevelModel = ClassFilePrimitive
					.getFactory()
					.createCodeLevelModel("padl.example.composite1");
			codeLevelModel.create(new CompleteClassFileCreator(new String[] {
					"../PADL Creator ClassFile/target/test-classes/padl/example/composite1/" }));

			final IIdiomLevelModel idiomLevelModel = (IIdiomLevelModel) new AACRelationshipsAnalysis()
					.invoke(codeLevelModel);

			SuperEntitiesConnectionsTest.FirstClassEntities = Util
					.getArrayOfTopLevelEntities(idiomLevelModel);
		}
	}

	public void testNumberOfEntities() {
		Assert.assertEquals("Number of entities", 11,
				SuperEntitiesConnectionsTest.FirstClassEntities.length);
	}

	public void testDocument() {
		ClassFilePrimitive.assertAssigable("Document class type", IClass.class,
				SuperEntitiesConnectionsTest.FirstClassEntities[5].getClass());
		Assert.assertEquals("Document class name",
				"padl.example.composite1.Document",
				SuperEntitiesConnectionsTest.FirstClassEntities[5].getDisplayID());

		Assert.assertTrue("Document class number of super-classes",
				((IClass) SuperEntitiesConnectionsTest.FirstClassEntities[5])
						.getIteratorOnInheritedEntities().hasNext());
		final Object object = ((IClass) SuperEntitiesConnectionsTest.FirstClassEntities[5])
				.getIteratorOnInheritedEntities().next();
		Assert.assertTrue("Document class number of super-classes",
				((IClass) SuperEntitiesConnectionsTest.FirstClassEntities[5])
						.getIteratorOnInheritedEntities().hasNext());
		ClassFilePrimitive.assertAssigable("Document class super-class type",
				IGhost.class, object.getClass());
		Assert.assertEquals("Document class super-class name",
				"java.lang.Object", ((IGhost) object).getDisplayID());

		Assert.assertFalse("Document class number of super-interfaces",
				((IClass) SuperEntitiesConnectionsTest.FirstClassEntities[5])
						.getIteratorOnImplementedInterfaces().hasNext());
	}

	public void testIndentedParagraph() {
		ClassFilePrimitive.assertAssigable("IndentedParagraph class type",
				IClass.class,
				SuperEntitiesConnectionsTest.FirstClassEntities[7].getClass());
		Assert.assertEquals("IndentedParagraph class name",
				"padl.example.composite1.IndentedParagraph",
				SuperEntitiesConnectionsTest.FirstClassEntities[7].getDisplayID());

		final Iterator iterator = ((IClass) SuperEntitiesConnectionsTest.FirstClassEntities[7])
				.getIteratorOnInheritedEntities();
		Assert.assertTrue("IndentedParagraph class number of super-classes",
				iterator.hasNext());
		final Object object = iterator.next();
		Assert.assertFalse("IndentedParagraph class number of super-classes",
				iterator.hasNext());
		ClassFilePrimitive.assertAssigable(
				"IndentedParagraph class super-class type", IClass.class,
				object.getClass());
		Assert.assertEquals("IndentedParagraph class super-class name",
				"padl.example.composite1.Paragraph",
				((IClass) object).getDisplayID());

		Assert.assertFalse("IndentedParagraph class number of super-interfaces",
				((IClass) SuperEntitiesConnectionsTest.FirstClassEntities[7])
						.getIteratorOnImplementedInterfaces().hasNext());
	}

	public void testMain() {
		ClassFilePrimitive.assertAssigable("Main class type", IClass.class,
				SuperEntitiesConnectionsTest.FirstClassEntities[8].getClass());
		Assert.assertEquals("Main class name", "padl.example.composite1.Main",
				SuperEntitiesConnectionsTest.FirstClassEntities[8].getDisplayID());

		final Iterator iterator = ((IClass) SuperEntitiesConnectionsTest.FirstClassEntities[8])
				.getIteratorOnInheritedEntities();
		Assert.assertTrue("Main class number of super-classes",
				iterator.hasNext());
		final Object object = iterator.next();
		Assert.assertFalse("Main class number of super-classes",
				iterator.hasNext());
		ClassFilePrimitive.assertAssigable("Main class super-class type",
				IGhost.class, object.getClass());
		Assert.assertEquals("Main class super-class name", "java.lang.Object",
				((IGhost) object).getDisplayID());

		Assert.assertFalse("Main class number of super-interfaces",
				((IClass) SuperEntitiesConnectionsTest.FirstClassEntities[8])
						.getIteratorOnImplementedInterfaces().hasNext());
	}

	public void testParagraph() {
		ClassFilePrimitive.assertAssigable("Paragraph class type", IClass.class,
				SuperEntitiesConnectionsTest.FirstClassEntities[9].getClass());
		Assert.assertEquals("Paragraph class name",
				"padl.example.composite1.Paragraph",
				SuperEntitiesConnectionsTest.FirstClassEntities[9].getDisplayID());

		Iterator iterator = ((IClass) SuperEntitiesConnectionsTest.FirstClassEntities[9])
				.getIteratorOnInheritedEntities();
		Assert.assertTrue("Paragraph class number of super-classes",
				iterator.hasNext());
		Object object = iterator.next();
		Assert.assertFalse("Paragraph class number of super-classes",
				iterator.hasNext());
		ClassFilePrimitive.assertAssigable("Paragraph class super-class type",
				IGhost.class, object.getClass());
		Assert.assertEquals("Paragraph class super-class name",
				"java.lang.Object", ((IGhost) object).getDisplayID());

		iterator = ((IClass) SuperEntitiesConnectionsTest.FirstClassEntities[9])
				.getIteratorOnImplementedInterfaces();
		Assert.assertTrue("Paragraph class number of super-interfaces",
				iterator.hasNext());
		object = iterator.next();
		Assert.assertFalse("Paragraph class number of super-interfaces",
				iterator.hasNext());
		ClassFilePrimitive.assertAssigable(
				"Paragraph class super-interface type", IInterface.class,
				object.getClass());
		Assert.assertEquals("Paragraph class super-interface name",
				"padl.example.composite1.Element",
				((IInterface) object).getDisplayID());
	}

	public void testTitle() {
		ClassFilePrimitive.assertAssigable("Title class type", IClass.class,
				SuperEntitiesConnectionsTest.FirstClassEntities[10].getClass());
		Assert.assertEquals("Title class name", "padl.example.composite1.Title",
				SuperEntitiesConnectionsTest.FirstClassEntities[10].getDisplayID());

		Iterator iterator = ((IClass) SuperEntitiesConnectionsTest.FirstClassEntities[10])
				.getIteratorOnInheritedEntities();
		Assert.assertTrue("Title class number of super-classes",
				iterator.hasNext());
		Object object = iterator.next();
		Assert.assertFalse("Title class number of super-classes",
				iterator.hasNext());
		ClassFilePrimitive.assertAssigable("Title class super-class type",
				IGhost.class, object.getClass());
		Assert.assertEquals("Title class super-class name", "java.lang.Object",
				((IGhost) object).getDisplayID());

		iterator = ((IClass) SuperEntitiesConnectionsTest.FirstClassEntities[10])
				.getIteratorOnImplementedInterfaces();
		Assert.assertTrue("Title class number of super-interfaces",
				iterator.hasNext());
		object = iterator.next();
		Assert.assertFalse("Title class number of super-interfaces",
				iterator.hasNext());
		ClassFilePrimitive.assertAssigable("Title class super-interface type",
				IInterface.class, object.getClass());
		Assert.assertEquals("Title class super-interface name",
				"padl.example.composite1.Element",
				((IInterface) object).getDisplayID());
	}

	public void testObject() {
		ClassFilePrimitive.assertAssigable("Object ghost type", IGhost.class,
				SuperEntitiesConnectionsTest.FirstClassEntities[0].getClass());
		Assert.assertEquals("Object ghost name", "java.lang.Object",
				SuperEntitiesConnectionsTest.FirstClassEntities[0].getDisplayID());
	}

	public void testAbstractDocument() {
		ClassFilePrimitive.assertAssigable("AbstractDocument interface type",
				IInterface.class,
				SuperEntitiesConnectionsTest.FirstClassEntities[4].getClass());
		Assert.assertEquals("AbstractDocument interface name",
				"padl.example.composite1.AbstractDocument",
				SuperEntitiesConnectionsTest.FirstClassEntities[4].getDisplayID());

		Assert.assertTrue("AbstractDocument class number of super-interfaces",
				((IInterface) SuperEntitiesConnectionsTest.FirstClassEntities[4])
						.getIteratorOnInheritedEntities().hasNext());
		ClassFilePrimitive.assertAssigable(
				"AbstractDocument class super-interface type", IGhost.class,
				SuperEntitiesConnectionsTest.FirstClassEntities[4]
						.getIteratorOnInheritedEntities().next().getClass());
		Assert.assertEquals("AbstractDocument class super-interface name",
				"java.lang.Object",
				((IGhost) SuperEntitiesConnectionsTest.FirstClassEntities[4]
						.getIteratorOnInheritedEntities().next())
						.getDisplayID());
	}

	public void testElement() {
		ClassFilePrimitive.assertAssigable("Element interface type",
				IInterface.class,
				SuperEntitiesConnectionsTest.FirstClassEntities[6].getClass());
		Assert.assertEquals("Element interface name",
				"padl.example.composite1.Element",
				SuperEntitiesConnectionsTest.FirstClassEntities[6].getDisplayID());

		Assert.assertTrue("Element class number of super-interfaces",
				((IInterface) SuperEntitiesConnectionsTest.FirstClassEntities[6])
						.getIteratorOnInheritedEntities().hasNext());
		final Iterator iterator = SuperEntitiesConnectionsTest.FirstClassEntities[6]
				.getIteratorOnInheritedEntities();
		iterator.next();
		final Object object = ((IInterface) iterator.next());
		Assert.assertTrue("Element class number of super-interfaces",
				((IInterface) SuperEntitiesConnectionsTest.FirstClassEntities[6])
						.getIteratorOnInheritedEntities().hasNext());
		ClassFilePrimitive.assertAssigable("Element class super-interface type",
				IInterface.class, object.getClass());
		Assert.assertEquals("Element class super-interface name",
				"padl.example.composite1.AbstractDocument",
				((IInterface) object).getDisplayID());
	}

	public void testVector() {
		ClassFilePrimitive.assertAssigable("Vector ghost type", IGhost.class,
				SuperEntitiesConnectionsTest.FirstClassEntities[3].getClass());
		Assert.assertEquals("Vector ghost name", "java.util.Vector",
				SuperEntitiesConnectionsTest.FirstClassEntities[3].getDisplayID());
	}

	public void testEnumeration() {
		ClassFilePrimitive.assertAssigable("Enumeration ghost type",
				IGhost.class,
				SuperEntitiesConnectionsTest.FirstClassEntities[2].getClass());
		Assert.assertEquals("Enumeration ghost name", "java.util.Enumeration",
				SuperEntitiesConnectionsTest.FirstClassEntities[2].getDisplayID());
	}

	public void testString() {
		ClassFilePrimitive.assertAssigable("String ghost type", IGhost.class,
				SuperEntitiesConnectionsTest.FirstClassEntities[1].getClass());
		Assert.assertEquals("String ghost name", "java.lang.String",
				SuperEntitiesConnectionsTest.FirstClassEntities[1].getDisplayID());
	}
}
