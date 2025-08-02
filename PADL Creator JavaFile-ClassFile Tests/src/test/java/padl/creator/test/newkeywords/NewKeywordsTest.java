/*******************************************************************************
 * Copyright (c) 2001-2025 Yann-Gaël Guéhéneuc and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/old-licenses/gpl-2.0.html
 * 
 * Contributors:
 *     Yann-Gaël Guéhéneuc and others, see in file; API and its implementation
 ******************************************************************************/
package padl.creator.test.newkeywords;

import org.junit.Assert;

import junit.framework.TestCase;
import padl.creator.classfile.CompleteClassFileCreator;
import padl.creator.javafile.eclipse.CompleteJavaFileCreator;
import padl.creator.javafile.javac.JavaFileCreator;
import padl.kernel.ICodeLevelModel;
import padl.kernel.IFirstClassEntity;
import padl.kernel.IInterface;
import padl.kernel.IMethod;
import padl.kernel.exception.CreationException;
import padl.kernel.impl.Factory;
import util.lang.Modifier;

public class NewKeywordsTest extends TestCase {

	private ICodeLevelModel createModelFromClassFiles(
			final String[] pathsToFiles) {

		try {
			final ICodeLevelModel codeLevelModel = Factory.getInstance()
					.createCodeLevelModel("Test Model");
			codeLevelModel.create(new CompleteClassFileCreator(pathsToFiles));
			return codeLevelModel;
		}
		catch (final CreationException e) {
			throw new RuntimeException(e);
		}
	}

	private ICodeLevelModel createModelFromJavaFilesEclipse(
			final String[] pathsToFiles) {
		try {
			final ICodeLevelModel codeLevelModel = Factory.getInstance()
					.createCodeLevelModel("Test Model");
			codeLevelModel.create(new CompleteJavaFileCreator(
					"../PADL Creator JavaFile-ClassFile Tests/src/test/java/",
					"", pathsToFiles));
			return codeLevelModel;
		}
		catch (final CreationException e) {
			throw new RuntimeException(e);
		}
	}

	private ICodeLevelModel createModelFromJavaFilesJavaC(
			final String[] pathsToFiles) {
		try {
			final ICodeLevelModel codeLevelModel = Factory.getInstance()
					.createCodeLevelModel("Test Model");
			codeLevelModel.create(new JavaFileCreator(
					"../PADL Creator JavaFile-ClassFile Tests/src/test/java/",
					pathsToFiles));
			return codeLevelModel;
		}
		catch (final CreationException e) {
			throw new RuntimeException(e);
		}
	}

	private void testDefaultMethods(final ICodeLevelModel aCodeLevelModel) {
		Assert.assertNotNull(aCodeLevelModel);

		final IFirstClassEntity entity = aCodeLevelModel
				.getTopLevelEntityFromID(
						"padl.example.defaultmethods.Example1");
		Assert.assertNotNull(entity);

		final IMethod method = (IMethod) entity
				.getConstituentFromID("ExampleDefaultMethod()");
		Assert.assertNotNull(method);

		// From java.lang.reflect.Method.isDefault()
		// "Default methods are public non-abstract instance methods
		// declared in an interface."
		Assert.assertTrue(Modifier.isPublic(method.getVisibility()));
		Assert.assertFalse(Modifier.isAbstract(method.getVisibility()));
		Assert.assertFalse(Modifier.isStatic(method.getVisibility()));
		Assert.assertTrue(entity instanceof IInterface);
	}

	public void testDefaultMethodsFromClassFiles() {
		final ICodeLevelModel modelFromClassFiles = this
				.createModelFromClassFiles(new String[] {
						"../PADL Creator JavaFile-ClassFile Tests/target/test-classes/padl/example/defaultmethods/" });
		this.testDefaultMethods(modelFromClassFiles);
	}

	public void testDefaultMethodsFromJavaFilesEclipse() {
		final ICodeLevelModel modelFromJavaFiles = this
				.createModelFromJavaFilesEclipse(new String[] {
						"../PADL Creator JavaFile-ClassFile Tests/src/test/java/padl/example/defaultmethods/" });
		this.testDefaultMethods(modelFromJavaFiles);
	}

	public void testDefaultMethodsFromJavaFilesJavaC() {
		final ICodeLevelModel modelFromJavaFiles = this
				.createModelFromJavaFilesJavaC(new String[] {
						"../PADL Creator JavaFile-ClassFile Tests/src/test/java/padl/example/defaultmethods/Example1.java" });
		this.testDefaultMethods(modelFromJavaFiles);
	}

	private void testSealedClasses(final ICodeLevelModel aCodeLevelModel) {
		final IFirstClassEntity entity = aCodeLevelModel
				.getTopLevelEntityFromID("padl.example.sealedclasses.Vehicle");
		Assert.assertNotNull(entity);
		Assert.assertEquals(3, entity.getNumberOfInheritingEntities());
	}

	public void testSealedClassesFromClassFiles() {
		final ICodeLevelModel modelFromClassFiles = this
				.createModelFromClassFiles(new String[] {
						"../PADL Creator JavaFile-ClassFile Tests/target/test-classes/padl/example/sealedclasses/" });
		this.testSealedClasses(modelFromClassFiles);
	}

	public void testSealedClassesFromJavaFilesEclipse() {
		final ICodeLevelModel modelFromClassFiles = this
				.createModelFromJavaFilesEclipse(new String[] {
						"../PADL Creator JavaFile-ClassFile Tests/src/test/java/padl/example/sealedclasses/" });
		this.testSealedClasses(modelFromClassFiles);
	}

	public void testSealedClassesFromJavaFilesJavaC() {
		final ICodeLevelModel modelFromClassFiles = this
				.createModelFromJavaFilesJavaC(new String[] {
						"../PADL Creator JavaFile-ClassFile Tests/src/test/java/padl/example/sealedclasses/Bicycle.java",
						"../PADL Creator JavaFile-ClassFile Tests/src/test/java/padl/example/sealedclasses/Bus.java",
						"../PADL Creator JavaFile-ClassFile Tests/src/test/java/padl/example/sealedclasses/Car.java",
						"../PADL Creator JavaFile-ClassFile Tests/src/test/java/padl/example/sealedclasses/ElectricCar.java",
						"../PADL Creator JavaFile-ClassFile Tests/src/test/java/padl/example/sealedclasses/HumanPoweredCar.java",
						"../PADL Creator JavaFile-ClassFile Tests/src/test/java/padl/example/sealedclasses/Vehicle.java" });
		this.testSealedClasses(modelFromClassFiles);
	}

}
