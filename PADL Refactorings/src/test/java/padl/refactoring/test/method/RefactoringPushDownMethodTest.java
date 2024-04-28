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
package padl.refactoring.test.method;

import java.util.Iterator;

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
import padl.refactoring.method.RefactoringPushDownMethod;

/**
 * @author Saliha Bouden
 * @since 2006/03/31
 * 
 */
public class RefactoringPushDownMethodTest extends TestCase {
	private static IIdiomLevelModel IdiomLevelModel;

	private static final String[] paths = {
			"../PADL Refactorings/target/test-classes/padl/refactoring/test/method/data/PushDownMethod/classD.class",
			"../PADL Refactorings/target/test-classes/padl/refactoring/test/method/data/PushDownMethod/classE.class",
			"../PADL Refactorings/target/test-classes/padl/refactoring/test/method/data/PushDownMethod/classF.class" };

	public RefactoringPushDownMethodTest(String name) {
		super(name);
	}

	protected void setUp() throws Exception {
		if (RefactoringPushDownMethodTest.IdiomLevelModel == null) {
			final ICodeLevelModel codeLevelModel = Factory.getInstance()
					.createCodeLevelModel("");
			codeLevelModel.create(new CompleteClassFileCreator(paths, true));
			RefactoringPushDownMethodTest.IdiomLevelModel = (IIdiomLevelModel) new AACRelationshipsAnalysis()
					.invoke(codeLevelModel);
		}
	}

	public void testPushDownMethod() {

		final RefactoringPushDownMethod refactoringMethod = new RefactoringPushDownMethod(
				RefactoringPushDownMethodTest.IdiomLevelModel);

		final IMethod method = refactoringMethod.getMethodToRefactor(
				"padl.refactoring.test.method.data.PushDownMethod.classD",
				"foo");
		if (method != null) {
			System.out.println("Before Refactoring Push Down Method");
			final IClass superClass = (IClass) RefactoringPushDownMethodTest.IdiomLevelModel
					.getConstituentFromName(
							"padl.refactoring.test.method.data.PushDownMethod.classD"
									.toCharArray());
			if (superClass != null) {
				System.out.println("-----­" + superClass.getDisplayName());
				System.out.println("-----­" + method.getDisplayName());

				final Iterator iterator = superClass
						.getIteratorOnInheritingEntities();
				while (iterator.hasNext()) {
					final IClass theClass = (IClass) iterator.next();
					final IMethod theMethod = refactoringMethod
							.getMethodToRefactor(theClass.getDisplayName(),
									"foo");
					if (theMethod != null) {
						System.out
								.println("-----­" + theClass.getDisplayName());
						System.out
								.println("-----­" + theMethod.getDisplayName());

					}
				}
				refactoringMethod.pushDownMethod("foo",
						"padl.refactoring.test.method.data.PushDownMethod.classD",
						"padl.refactoring.test.method.data.PushDownMethod.classE");
				System.out.println("After Refactoring Push Down Method");
				final IMethod aMethod = refactoringMethod.getMethodToRefactor(
						"padl.refactoring.test.method.data.PushDownMethod.classD",
						"foo");
				if (aMethod != null) {
					System.out.println("-----­" + aMethod.getDisplayName());
					System.out.println("-----­" + superClass.getDisplayName());
				}
				final Iterator iter = superClass
						.getIteratorOnInheritingEntities();
				while (iter.hasNext()) {
					final IClass theClass = (IClass) iter.next();
					final IMethod theMethod = refactoringMethod
							.getMethodToRefactor(theClass.getDisplayName(),
									"foo");
					if (theMethod != null) {
						System.out
								.println("-----­" + theClass.getDisplayName());
						System.out
								.println("-----­" + theMethod.getDisplayName());
						Assert.assertEquals("Name of the new method pushdowned",
								"foo", theMethod.getName());

					}
				}

			}
		}
	}

	public void testPushDownMethodAcceptOverloading() {

		final RefactoringPushDownMethod refactoringMethod = new RefactoringPushDownMethod(
				RefactoringPushDownMethodTest.IdiomLevelModel);

		final IMethod method = refactoringMethod.getMethodToRefactor(
				"padl.refactoring.test.method.data.PushDownMethod.classD",
				"fo");
		if (method != null) {
			System.out.println(
					"Before Refactoring Push Down Method Accept Overloading");
			final IClass superClass = (IClass) RefactoringPushDownMethodTest.IdiomLevelModel
					.getConstituentFromName(
							"padl.refactoring.test.method.data.PushDownMethod.classD"
									.toCharArray());
			if (superClass != null) {
				System.out.println("-----­" + superClass.getDisplayName());
				System.out.println("-----­" + method.getDisplayName());

				final Iterator iterator = superClass
						.getIteratorOnInheritingEntities();
				while (iterator.hasNext()) {
					final IClass theClass = (IClass) iterator.next();
					final IMethod theMethod = refactoringMethod
							.getMethodToRefactor(theClass.getDisplayName(),
									"fo");
					if (theMethod != null) {
						System.out
								.println("-----­" + theClass.getDisplayName());
						System.out
								.println("-----­" + theMethod.getDisplayName());

					}
				}
				refactoringMethod.pushDownMethodAcceptOverloading("fo",
						"padl.refactoring.test.method.data.PushDownMethod.classD",
						"padl.refactoring.test.method.data.PushDownMethod.classE");
				System.out.println(
						"After Refactoring Push Down Method Accept Overloading");
				final IMethod aMethod = refactoringMethod.getMethodToRefactor(
						"padl.refactoring.test.method.data.PushDownMethod.classD",
						"fo");
				if (aMethod != null) {
					System.out.println("-----­" + aMethod.getDisplayName());
					System.out.println("-----­" + superClass.getDisplayName());
				}
				final Iterator iter = superClass
						.getIteratorOnInheritingEntities();
				while (iter.hasNext()) {
					final IClass theClass = (IClass) iter.next();
					final IMethod theMethod = refactoringMethod
							.getMethodToRefactor(theClass.getDisplayName(),
									"fo");
					if (theMethod != null) {
						System.out
								.println("-----­" + theClass.getDisplayName());
						System.out
								.println("-----­" + theMethod.getDisplayName());
						Assert.assertEquals("Name of the new method renamed",
								"fo", theMethod.getName());

					}
				}

			}
		}
	}

	public void testPushDownMethod2() {

		final RefactoringPushDownMethod refactoringMethod = new RefactoringPushDownMethod(
				RefactoringPushDownMethodTest.IdiomLevelModel);

		final IMethod method = refactoringMethod.getMethodToRefactor(
				"padl.refactoring.test.method.data.PushDownMethod.classD",
				"fo");
		if (method != null) {
			System.out.println("Before Refactoring Push Down Method");
			final IClass superClass = (IClass) RefactoringPushDownMethodTest.IdiomLevelModel
					.getConstituentFromName(
							"padl.refactoring.test.method.data.PushDownMethod.classD"
									.toCharArray());
			if (superClass != null) {
				System.out.println("-----­" + superClass.getDisplayName());
				System.out.println("-----­" + method.getDisplayName());

				final Iterator iterator = superClass
						.getIteratorOnInheritingEntities();
				while (iterator.hasNext()) {
					final IClass theClass = (IClass) iterator.next();
					final IMethod theMethod = refactoringMethod
							.getMethodToRefactor(theClass.getDisplayName(),
									"fo");
					if (theMethod != null) {
						System.out
								.println("-----­" + theClass.getDisplayName());
						System.out
								.println("-----­" + theMethod.getDisplayName());

					}
				}
				refactoringMethod.pushDownMethod("fo",
						"padl.refactoring.test.method.data.PushDownMethod.classD",
						"padl.refactoring.test.method.data.PushDownMethod.classE");
				System.out.println("After Refactoring Push Down Method");
				final IMethod aMethod = refactoringMethod.getMethodToRefactor(
						"padl.refactoring.test.method.data.PushDownMethod.classD",
						"fo");
				if (aMethod != null) {
					System.out.println("-----­" + aMethod.getDisplayName());
					System.out.println("-----­" + superClass.getDisplayName());
				}
				final Iterator iter = superClass
						.getIteratorOnInheritingEntities();
				while (iter.hasNext()) {
					final IClass theClass = (IClass) iter.next();
					final IMethod theMethod = refactoringMethod
							.getMethodToRefactor(theClass.getDisplayName(),
									"fo");
					if (theMethod != null) {
						System.out
								.println("-----­" + theClass.getDisplayName());
						System.out
								.println("-----­" + theMethod.getDisplayName());
						Assert.assertEquals("Name of the new method renamed",
								"fo", theMethod.getName());

					}
				}

			}
		}
	}

	public static Test suite() {
		TestSuite suite = new TestSuite();
		suite.addTest(new RefactoringPushDownMethodTest("testPushDownMethod"));
		//		suite.addTest(new RefactoringPushDownMethodTest(
		//			"testPushDownMethod2"));
		//		suite.addTest(new RefactoringPushDownMethodTest(
		//			"testPushDownMethodAcceptOverloading"));

		return suite;
	}

}
