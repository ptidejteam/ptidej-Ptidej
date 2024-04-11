/*******************************************************************************
 * Copyright (c) 2001-2014 Yann-Ga�l Gu�h�neuc and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/old-licenses/gpl-2.0.html
 * 
 * Contributors:
 *     Yann-Ga�l Gu�h�neuc and others, see in file; API and its implementation
 ******************************************************************************/
package padl.refactoring.test.method;

import org.junit.Assert;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import padl.analysis.repository.AACRelationshipsAnalysis;
import padl.creator.classfile.CompleteClassFileCreator;
import padl.kernel.IClass;
import padl.kernel.ICodeLevelModel;
import padl.kernel.IIdiomLevelModel;
import padl.kernel.IMethod;
import padl.kernel.impl.Factory;
import padl.refactoring.method.RefactoringMoveMethod;
import util.io.Files;

/**
 * @author Saliha Bouden
 * @since 2006/03/31
 * 
 */
public class RefactoringMoveMethodTest extends TestCase {
	public static IIdiomLevelModel IdiomLevelModel;

	// TODO: Do not a new model of QuickUML for each tests but
	// keep an idiom-level model in cache using a static variable.
	private static final String path =
		"../PADL Refactorings Tests/rsc/applications/QuickUML2001.jar";

	public RefactoringMoveMethodTest(String name) {
		super(name);
	}

	// construire le mod�le
	protected void setUp() throws Exception {
		//if (RefactoringMoveMethodTest.IdiomLevelModel == null) {
		final ICodeLevelModel codeLevelModel =
			Factory.getInstance().createCodeLevelModel(path);
		codeLevelModel.create(new CompleteClassFileCreator(
			new String[] { path },
			true));
		RefactoringMoveMethodTest.IdiomLevelModel =
			(IIdiomLevelModel) new AACRelationshipsAnalysis()
				.invoke(codeLevelModel);
		// }
	}

	// TODO: Document tests, i.e., what do they show? Are they suppose to fail???
	public void testMoveMethod() {
		final RefactoringMoveMethod refactoringMethod =
			new RefactoringMoveMethod(RefactoringMoveMethodTest.IdiomLevelModel);
		IClass sourceClass =
			refactoringMethod.getClassContainsMethodToRefactor(
				"acme.GifEncoder",
				"encodeStart");
		System.out.println("Before Refactoring Move Method");

		final IMethod aMethod =
			refactoringMethod.getMethodToRefactor(
				"acme.GifEncoder",
				"encodeStart");
		if (aMethod != null) {
			System.out.println("-----�" + sourceClass.getDisplayName());
			System.out.println("-----�" + aMethod.getDisplayName());

			refactoringMethod.moveMethod(
				"encodeStart",
				"acme.GifEncoder",
				"diagram.AbstractDiagramModel");
			System.out.println("After Refactoring Move Method");
			sourceClass =
				refactoringMethod.getClassContainsMethodToRefactor(
					"acme.GifEncoder",
					"encodeStart");
			if (sourceClass != null) {
				System.out.println("-----�" + sourceClass.getDisplayName());
				System.out.println("-----�" + aMethod.getDisplayName());
			}
			IClass targetClass =
				refactoringMethod.getClassContainsMethodToRefactor(
					"diagram.AbstractDiagramModel",
					"encodeStart");
			if (targetClass != null) {
				final IMethod theMethod =
					refactoringMethod.getMethodToRefactor(
						targetClass.getDisplayName(),
						"encodeStart");
				if (theMethod != null) {

					System.out.println("-----�" + targetClass.getDisplayName());
					System.out.println("-----�" + theMethod.getDisplayName());
					Assert.assertEquals(
						"Name of the new Class",
						"diagram.AbstractDiagramModel",
						targetClass.getDisplayName());
				}
			}
		}
	}

	public void testMoveMethod2() {
		final RefactoringMoveMethod refactoringMethod =
			new RefactoringMoveMethod(RefactoringMoveMethodTest.IdiomLevelModel);
		IClass sourceClass =
			refactoringMethod.getClassContainsMethodToRefactor(
				"acme.ImageEncoder",
				"encodePixelsWrapper");
		System.out.println("Before Refactoring Move Method");

		final IMethod aMethod =
			refactoringMethod.getMethodToRefactor(
				"acme.ImageEncoder",
				"encodePixelsWrapper");
		if (aMethod != null) {
			System.out.println("-----�" + sourceClass.getDisplayName());
			System.out.println("-----�" + aMethod.getDisplayName());

			refactoringMethod.moveMethod(
				"encodePixelsWrapper",
				"acme.ImageEncoder",
				"diagram.AbstractDiagramModel");
			System.out.println("After Refactoring Move Method");
			sourceClass =
				refactoringMethod.getClassContainsMethodToRefactor(
					"acme.ImageEncoder",
					"encodePixelsWrapper");
			if (sourceClass != null) {
				System.out.println("-----�" + sourceClass.getDisplayName());
				System.out.println("-----�" + aMethod.getDisplayName());
			}
			IClass targetClass =
				refactoringMethod.getClassContainsMethodToRefactor(
					"diagram.AbstractDiagramModel",
					"encodePixelsWrapper");
			if (targetClass != null) {
				final IMethod theMethod =
					refactoringMethod.getMethodToRefactor(
						targetClass.getDisplayName(),
						"encodePixelsWrapper");
				if (theMethod != null) {

					System.out.println("-----�" + targetClass.getDisplayName());
					System.out.println("-----�" + theMethod.getDisplayName());
					Assert.assertEquals(
						"Name of the new Class",
						"diagram.AbstractDiagramModel",
						targetClass.getName());
				}
			}
		}
	}
	public void testMoveMethod3() {
		final RefactoringMoveMethod refactoringMethod =
			new RefactoringMoveMethod(RefactoringMoveMethodTest.IdiomLevelModel);
		IClass sourceClass =
			refactoringMethod.getClassContainsMethodToRefactor(
				"acme.GifEncoder",
				"GetPixel");
		System.out.println("Before Refactoring Move Method");

		final IMethod aMethod =
			refactoringMethod
				.getMethodToRefactor("acme.GifEncoder", "GetPixel");
		if (aMethod != null) {
			System.out.println("-----�" + sourceClass.getDisplayName());
			System.out.println("-----�" + aMethod.getDisplayName());

			refactoringMethod.moveMethod(
				"GetPixel",
				"acme.GifEncoder",
				"diagram.AbstractDiagramModel");
			System.out.println("After Refactoring Move Method");
			sourceClass =
				refactoringMethod.getClassContainsMethodToRefactor(
					"acme.GifEncoder",
					"GetPixel");
			if (sourceClass != null) {
				System.out.println("-----�" + sourceClass.getDisplayName());
				System.out.println("-----�" + aMethod.getDisplayName());
			}
			IClass targetClass =
				refactoringMethod.getClassContainsMethodToRefactor(
					"diagram.AbstractDiagramModel",
					"GetPixel");
			if (targetClass != null) {
				final IMethod theMethod =
					refactoringMethod.getMethodToRefactor(
						targetClass.getDisplayName(),
						"GetPixel");
				if (theMethod != null) {

					System.out.println("-----�" + targetClass.getDisplayName());
					System.out.println("-----�" + theMethod.getDisplayName());
					Assert.assertEquals(
						"Name of the new Class",
						"diagram.AbstractDiagramModel",
						targetClass.getName());
				}
			}
		}
	}
	public static Test suite() {
		TestSuite suite = new TestSuite();
		//		suite.addTest(new RefactoringMoveMethodTest("testMoveMethod"));
		suite.addTest(new RefactoringMoveMethodTest("testMoveMethod2"));
		//        suite.addTest(new RefactoringMoveMethodTest("testMoveMethod3"));

		return suite;
	}

}
