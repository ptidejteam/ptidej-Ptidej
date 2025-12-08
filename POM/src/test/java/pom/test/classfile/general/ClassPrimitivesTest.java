/*******************************************************************************
 * Copyright (c) 2001-2014 Yann-Gaël Guéhéneuc  and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/old-licenses/gpl-2.0.html
 * 
 * Contributors:
 *     Yann-Gaël Guéhéneuc  and others, see in file; API and its implementation
 ******************************************************************************/
package pom.test.classfile.general;

import java.util.ArrayList;
import java.util.Iterator;

import org.junit.Assert;

import junit.framework.TestCase;
import padl.creator.classfile.CompleteClassFileCreator;
import padl.kernel.ICodeLevelModel;
import padl.kernel.IFirstClassEntity;
import padl.kernel.IGhost;
import padl.kernel.impl.Factory;
import pom.operators.Operators;
import pom.primitives.ClassPrimitives;

/**
 * @author zaidifar
 *
 * We consider the package java.lang. The arborescence seems to be good for doing tests.
 * We consider some classes on the middle of the hierarchy. We are using JDK1.2.2. However,
 * a representation of the tree is available at : 
 * http://java.sun.com/j2se/1.3/docs/api/java/lang/package-tree.html
 */
public final class ClassPrimitivesTest extends TestCase {
	// Main path.
	private static final String ROOT = "../POM/target/test-classes/";

	// Jar files to load.
	private static final String[] JAR_FILES = { "java.lang" };
	private static final String MAIN_PACKAGE = "java.lang";

	// Extension for jar files.
	private static final String EXTENSION = ".jar";

	private static final Operators OPERATORS = Operators.getInstance();

	private static ICodeLevelModel CurrentModel = null;
	private static ClassPrimitives ClazzPrimitives = null;
	private static IFirstClassEntity EntitySerializable = null;

	// Constituent ID to launch
	private String id; // = "java.lang.Integer";

	public ClassPrimitivesTest(final String aName) {
		super(aName);
	}

	/*
	 * @see TestCase#setUp()
	 */
	protected void setUp() throws Exception {
		super.setUp();
		if (ClassPrimitivesTest.CurrentModel == null) {
			ClassPrimitivesTest.CurrentModel = Factory.getInstance()
					.createCodeLevelModel("Test.TestClassPrimitives");
			ClassPrimitivesTest.CurrentModel
					.create(new CompleteClassFileCreator(
							new String[] { ClassPrimitivesTest.ROOT
									+ ClassPrimitivesTest.JAR_FILES[0]
									+ ClassPrimitivesTest.EXTENSION }));

			ClassPrimitivesTest.EntitySerializable = (IFirstClassEntity) ClassPrimitivesTest.CurrentModel
					.getTopLevelEntityFromID("java.io.Serializable");

			/**
			 * The idiomLevelModel creates ghosts that do not belong
			 * to the java.lang package. I want to remove its from the idiomLevelModel
			 * The list of non-java.lang classes is initialized. Then, classes are removed.
			 */
			// Yann 2005/10/12: Iterator!
			// I have now an iterator able to iterate over a
			// specified type of constituent of a list.
			Iterator iterator = ClassPrimitivesTest.CurrentModel
					.getIteratorOnConstituents(IGhost.class);
			while (iterator.hasNext()) {
				ClassPrimitivesTest.CurrentModel.removeConstituentFromID(
						((IGhost) iterator.next()).getID());
			}
			ClassPrimitivesTest.ClazzPrimitives = ClassPrimitives.getInstance();
		}
	}

	/**
	 * Unit test for parent method.
	 * According to the actorID, we get the entity.
	 * The list of its parents is compared with our list.
	 * @throws ClassNotFoundException
	 */
	public void testParents() throws ClassNotFoundException {
		this.id = "java.lang.Short";
		final IFirstClassEntity iEntity = (IFirstClassEntity) ClassPrimitivesTest.CurrentModel
				.getTopLevelEntityFromID(this.id);
		final String[] classes = { "Number", "Comparable" };
		final ArrayList entities = this.stringsToEntities(classes);
		Assert.assertTrue(ClassPrimitivesTest.OPERATORS.equal(entities,
				ClassPrimitivesTest.ClazzPrimitives
						.listOfAllDirectParents(iEntity)));
	}

	/**
	 * Unit test for children method.
	 * According to the actorID, we get the entity.
	 * The list of its children is compared with our list.
	 * @throws ClassNotFoundException
	 */
	public void testChildren() throws ClassNotFoundException {
		this.id = "java.lang.Error";
		final IFirstClassEntity iEntity = (IFirstClassEntity) ClassPrimitivesTest.CurrentModel
				.getTopLevelEntityFromID(this.id);

		final String[] classes = { "LinkageError", "ThreadDeath",
				"VirtualMachineError" };

		final ArrayList entities = stringsToEntities(classes);
		Assert.assertEquals(entities.size(), ClassPrimitivesTest.ClazzPrimitives
				.getNumberOfChildren(iEntity));
	}

	public void testAncestor() throws ClassNotFoundException {
		this.id = "java.lang.Short";
		IFirstClassEntity iEntity = (IFirstClassEntity) ClassPrimitivesTest.CurrentModel
				.getTopLevelEntityFromID(this.id);
		String[] classes = { "Number", "Comparable", "Object" };

		ArrayList entities = this.stringsToEntities(classes);
		entities.add(ClassPrimitivesTest.EntitySerializable);
		//Because the Serializable interface is a ghost, we need to get this entity. and to add it in the list to compare.

		Assert.assertTrue(ClassPrimitivesTest.OPERATORS.equal(entities,
				ClassPrimitivesTest.ClazzPrimitives.listOfAncestors(iEntity)));
	}

	/**
	 * Unit test for descendents method.
	 * According to the actorID, we get the entity.
	 * The list of its descendents is compared with our list.
	 * @throws ClassNotFoundException
	 */
	public void testDescendents() throws ClassNotFoundException {
		this.id = "java.lang.RuntimeException";
		final IFirstClassEntity iEntity = (IFirstClassEntity) ClassPrimitivesTest.CurrentModel
				.getTopLevelEntityFromID(this.id);

		final String[] classes = { "ArithmeticException", "ArrayStoreException",
				"ClassCastException", "IllegalArgumentException",
				"IllegalThreadStateException", "NumberFormatException",
				"IllegalMonitorStateException", "IllegalStateException",
				"IndexOutOfBoundsException", "ArrayIndexOutOfBoundsException",
				"StringIndexOutOfBoundsException", "NegativeArraySizeException",
				"NullPointerException", "SecurityException",
				"UnsupportedOperationException" };
		final ArrayList entities = stringsToEntities(classes);
		Assert.assertTrue(ClassPrimitivesTest.OPERATORS.equal(entities,
				ClassPrimitivesTest.ClazzPrimitives
						.listOfDescendents(iEntity)));
	}

	/**
	 * Unit test for allMethods method.
	 * According to the actorID, we get the entity. The list of all methods is constructed.
	 * Its size is compared with the size we calculated
	 * @throws ClassNotFoundException
	 */
	public void testAllMethods() throws ClassNotFoundException {
		this.id = "java.lang.Integer";
		final IFirstClassEntity iEntity = (IFirstClassEntity) ClassPrimitivesTest.CurrentModel
				.getTopLevelEntityFromID(this.id);

		//		Assert.assertTrue(TestClassPrimitives.ClazzPrimitives
		//			.allEntityMethods(iEntity)
		//			.size() == 34);

		Assert.assertEquals(33, ClassPrimitivesTest.ClazzPrimitives
				.listOfAllMethods(iEntity).size());
	}

	/** @see ClassPrimitivesTest#testAllMethods*/
	public void testDeclaredMethods() throws ClassNotFoundException {
		this.id = "java.lang.Number";
		final IFirstClassEntity iEntity = (IFirstClassEntity) ClassPrimitivesTest.CurrentModel
				.getTopLevelEntityFromID(this.id);
		Assert.assertEquals(15, ClassPrimitivesTest.ClazzPrimitives
				.listOfInheritedAndAbstractMethods(iEntity).size());
	}

	/** @see ClassPrimitivesTest#testAllMethods*/
	public void testImplementedMethods() throws ClassNotFoundException {
		this.id = "java.lang.Number";
		final IFirstClassEntity iEntity = (IFirstClassEntity) ClassPrimitivesTest.CurrentModel
				.getTopLevelEntityFromID(this.id);
		Assert.assertTrue(ClassPrimitivesTest.ClazzPrimitives
				.listOfOverriddenAndConcreteMethods(iEntity).size() == 2);
	}

	/** @see ClassPrimitivesTest#testAllMethods*/
	public void testNewMethods() throws ClassNotFoundException {
		this.id = "java.lang.Integer";
		final IFirstClassEntity iEntity = (IFirstClassEntity) ClassPrimitivesTest.CurrentModel
				.getTopLevelEntityFromID(this.id);

		//		Assert.assertTrue(TestClassPrimitives.ClazzPrimitives
		//			.newEntityMethods(iEntity)
		//			.size() == 17);
		Assert.assertEquals(18, ClassPrimitivesTest.ClazzPrimitives
				.listOfNewMethods(iEntity).size());
	}

	/** @see ClassPrimitivesTest#testAllMethods*/
	public void testOverloadedMethods() throws ClassNotFoundException {
		this.id = "java.lang.Integer";
		final IFirstClassEntity iEntity = (IFirstClassEntity) ClassPrimitivesTest.CurrentModel
				.getTopLevelEntityFromID(this.id);

		//		Assert.assertTrue(TestClassPrimitives.ClazzPrimitives
		//			.overriddenEntityMethods(iEntity)
		//			.size() == 11);

		Assert.assertEquals(10, ClassPrimitivesTest.ClazzPrimitives
				.listOfOverriddenMethods(iEntity).size());
	}

	/** @see ClassPrimitivesTest#testAllMethods*/
	public void testInheritedMethods() throws ClassNotFoundException {
		this.id = "java.lang.Integer";
		final IFirstClassEntity iEntity = (IFirstClassEntity) ClassPrimitivesTest.CurrentModel
				.getTopLevelEntityFromID(this.id);

		//		Assert.assertTrue(TestClassPrimitives.ClazzPrimitives
		//			.inheritedEntityMethods(iEntity)
		//			.size() == 8);

		Assert.assertEquals(8, ClassPrimitivesTest.ClazzPrimitives
				.listOfInheritedMethods(iEntity).size());
	}

	private ArrayList stringsToEntities(final String[] nameClasses) {
		final ArrayList result = new ArrayList();
		for (int i = 0; i < nameClasses.length; i++) {
			result.add(ClassPrimitivesTest.CurrentModel.getTopLevelEntityFromID(
					ClassPrimitivesTest.MAIN_PACKAGE + "." + nameClasses[i]));
		}
		return result;
	}
}
