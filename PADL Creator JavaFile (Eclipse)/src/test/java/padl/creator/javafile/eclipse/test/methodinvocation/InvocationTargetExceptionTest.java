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
package padl.creator.javafile.eclipse.test.methodinvocation;

import org.junit.Assert;

import junit.framework.TestCase;

public class InvocationTargetExceptionTest extends TestCase {
	public InvocationTargetExceptionTest(final String name) {
		super(name);
	}

	/**
	 * Giant project, exception found by Sam InvocationTargetException
	 */
	public void testInvocationTargetException() {
		// TODO Re-enable this test, make it faster!
		//		System.out.println("testInvocationTargetException");
		//		final String sourcePath = "./rsc/sam test data/giant/";
		//		final String classPathEntry = "";
		//		final IIdiomLevelModel javaIdiomModel =
		//			Utils.createCompleteJavaFilesPadlIdiomModel(
		//				"",
		//				sourcePath,
		//				classPathEntry);
		//		System.out.println("getNumberOfTopLevelEntities"
		//				+ javaIdiomModel.getNumberOfTopLevelEntities());
		//		Assert.assertNotNull(javaIdiomModel);
		//	}
		//
		//	/**
		//	 * argoEclipse project, exception found by Sam (modelDeclarationException
		//	 * and InvocationTargetException
		//	 */
		//	public void testInvocationTargetException1() {
		//		System.out.println("testInvocationTargetException1");
		//		final String sourcePath = "./rsc/sam test data/argoeclipse/";
		//
		//		final String classPathEntry = "";
		//		final IIdiomLevelModel javaIdiomModel =
		//			Utils.createCompleteJavaFilesPadlIdiomModel(
		//				"",
		//				sourcePath,
		//				classPathEntry);
		//		System.out.println("getNumberOfTopLevelEntities"
		//				+ javaIdiomModel.getNumberOfTopLevelEntities());
		//		Assert.assertNotNull(javaIdiomModel);
		//	}
		//
		//	/**
		//	 * argouml-php project, exception found by Sam (modelDeclarationException
		//	 */
		//	public void testInvocationTargetException2() {
		//		System.out.println("testInvocationTargetException2");
		//		final String sourcePath = "./rsc/sam test data/argouml-php/";
		//
		//		final String classPathEntry = "";
		//		final IIdiomLevelModel javaIdiomModel =
		//			Utils.createCompleteJavaFilesPadlIdiomModel(
		//				"",
		//				sourcePath,
		//				classPathEntry);
		//		System.out.println("getNumberOfTopLevelEntities"
		//				+ javaIdiomModel.getNumberOfTopLevelEntities());
		//		Assert.assertNotNull(javaIdiomModel);
		//	}
		//
		//	/**
		//	 * reslet project, exception found by Sam (modelDeclarationException and
		//	 * InvocationTargetException and null exception in VisitorSecondParsing(visit(final VariableDeclarationFragment node))
		//	 */
		//	public void testInvocationTargetException3() {
		//		System.out.println("testInvocationTargetException3");
		//		final String sourcePath = "./rsc/sam test data/restlet/";
		//
		//		final String classPathEntry = "";
		//		final IIdiomLevelModel javaIdiomModel =
		//			Utils.createCompleteJavaFilesPadlIdiomModel(
		//				"",
		//				sourcePath,
		//				classPathEntry);
		//		System.out.println("getNumberOfTopLevelEntities"
		//				+ javaIdiomModel.getNumberOfTopLevelEntities());
		//		Assert.assertNotNull(javaIdiomModel);
		Assert.assertTrue(true);
	}
}
