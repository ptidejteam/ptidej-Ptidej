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
package padl.creator.test.relationships;

import java.io.File;
import java.lang.reflect.InvocationTargetException;

import org.junit.Assert;
import junit.framework.TestCase;
import padl.creator.classfile.CompleteClassFileCreator;
import padl.creator.javafile.eclipse.CompleteJavaFileCreator;
import padl.creator.javafile.eclipse.test.methodinvocation.MethodInvocationComparator;
import padl.creator.test.relationships.providers.ITestProvider;
import padl.kernel.IClass;
import padl.kernel.ICodeLevelModel;
import padl.kernel.IMethod;
import padl.kernel.IMethodInvocation;
import padl.kernel.exception.CreationException;
import padl.kernel.impl.Factory;


public class RelationshipsTest extends TestCase {
	public RelationshipsTest(final String aName) {
		super(aName);
	}

	/**
	 * Test the existence of the same MI in the JavaFile model as in the ClassFile model.
	 */
	private void testInclusionOfMIsFromJavaFilesModelInClassFilesModel(
		final ITestProvider aProvider,
		final IMethod aMethodFromJavaFilesModel,
		final IMethod aMethodFromClassFilesModel) {

		Assert.assertTrue(
			"Method invocations built for " + aProvider.getTestClassName()
					+ " are different!",
			MethodInvocationComparator
				.isMIofJavaModelMethodIncludedInMIofClassModelMethod(
					aMethodFromJavaFilesModel,
					aMethodFromClassFilesModel));
	}

	/**
	 * Tests methods have been separated - see also the method callMIinclusionTest
	 * @param aProvider
	 */
	private void testExpectedMIs(
		final ITestProvider aProvider,
		final IMethod theTestMethodFromJavaFilesModel) {

		// Get methodFromJavaFileModel's method invocation
		final IMethodInvocation javaMethodInvocation =
			(IMethodInvocation) theTestMethodFromJavaFilesModel
				.getConstituentFromID("Method Invocation_>PADL<_1");
		final IMethodInvocation expectedMethodInvocation =
			aProvider.getExpectedMethodInvocation();

		// Test that the method invocation is which is expected
		Assert.assertEquals(
			" TestClass " + aProvider.getTestClassName()
					+ ": doest not contain the expected method invocation",
			expectedMethodInvocation,
			javaMethodInvocation);
	}
	private ICodeLevelModel createModelFromClassFiles(
		final String[] pathsToFiles) {

		try {
			final ICodeLevelModel codeLevelModel =
				Factory.getInstance().createCodeLevelModel("Test Model");
			codeLevelModel.create(new CompleteClassFileCreator(pathsToFiles));
			return codeLevelModel;
		}
		catch (final CreationException e) {
			throw new RuntimeException(e);
		}
	}
	private ICodeLevelModel createModelFromJavaFiles(final String[] pathsToFiles) {
		try {
			final ICodeLevelModel codeLevelModel =
				Factory.getInstance().createCodeLevelModel("Test Model");
			codeLevelModel.create(new CompleteJavaFileCreator(
				"../PADL Creator JavaFile-ClassFile Tests/src/",
				"",
				pathsToFiles));
			return codeLevelModel;
		}
		catch (final CreationException e) {
			throw new RuntimeException(e);
		}
	}
	private IMethod getTestMethodFromModel(
		final ICodeLevelModel aCodeLevelModel,
		final String aTestClassName) {

		final IClass testClass =
			(IClass) aCodeLevelModel.getTopLevelEntityFromID(aTestClassName);
		Assert.assertNotNull(testClass);

		final IMethod testMethod =
			(IMethod) testClass.getConstituentFromID("foo()");
		Assert.assertNotNull(testMethod);

		return testMethod;
	}
	public void testRelationships() {
		final String testProviderClassName = ITestProvider.class.getName();
		final String testProviderPackageName =
			testProviderClassName.substring(
				0,
				testProviderClassName.lastIndexOf('.'));
		final File pathToTest =
			new File("../PADL Creator JavaFile-ClassFile Tests/bin/"
					+ testProviderPackageName.replace('.', '/'));
		final String[] potentialTests = pathToTest.list();
		for (int i = 0; i < potentialTests.length; i++) {
			final String potentialTest = potentialTests[i];
			if (potentialTest.startsWith(ITestProvider.PROVIDER_CLASS_PREFIX)) {
				try {
					final StringBuffer testClassName = new StringBuffer();
					testClassName.append(testProviderPackageName);
					testClassName.append('.');
					testClassName.append(potentialTest.substring(
						0,
						potentialTest.indexOf('.')));
					final ITestProvider provider =
						(ITestProvider) Class
							.forName(testClassName.toString())
							.getDeclaredConstructor().newInstance();

					// Create the needed data...
					final String pathToByteCodeOfTestClass =
						provider.getPathToByteCodeOfTestClass();
					final String pathToByteCodeOfHelperClass =
						provider.getPathToByteCodeOfHelperClass();

					final String pathToJavaCodeOfTestClass =
						provider.getPathToJavaCodeOfTestClass();
					final String pathToJavaCodeOfHelperClass =
						provider.getPathToJavaCodeOfHelperClass();

					final ICodeLevelModel classFilesModel =
						this.createModelFromClassFiles(new String[] {
								pathToByteCodeOfTestClass,
								pathToByteCodeOfHelperClass });
					final ICodeLevelModel javaFilesModel =
						this.createModelFromJavaFiles(new String[] {
								pathToJavaCodeOfTestClass,
								pathToJavaCodeOfHelperClass });
					final IMethod methodFromClassFileModel =
						this.getTestMethodFromModel(
							classFilesModel,
							provider.getTestClassName());

					final IMethod methodFromJavaFileModel =
						this.getTestMethodFromModel(
							javaFilesModel,
							provider.getTestClassName());

					// Test code...
					this.testExpectedMIs(provider, methodFromJavaFileModel);
					this.testInclusionOfMIsFromJavaFilesModelInClassFilesModel(
						provider,
						methodFromJavaFileModel,
						methodFromClassFileModel);
				}
				catch (final InstantiationException e) {
					throw new RuntimeException(e);
				}
				catch (final IllegalAccessException e) {
					throw new RuntimeException(e);
				}
				catch (final ClassNotFoundException e) {
					throw new RuntimeException(e);
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					e.printStackTrace();
				} catch (NoSuchMethodException e) {
					e.printStackTrace();
				} catch (SecurityException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
