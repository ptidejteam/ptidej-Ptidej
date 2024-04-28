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
import padl.refactoring.method.RefactoringPullUpMethod;

/**
 * @author Saliha Bouden
 * @since 2006/03/31
 * 
 */
public class RefactoringPullUpMethodTest extends TestCase {

	private static IIdiomLevelModel IdiomLevelModel;
	private static final String[] paths = {
			"../PADL Refactorings/target/test-classes/padl/refactoring/test/method/data/PullUpMethod/classA.class",
			"../PADL Refactorings/target/test-classes/padl/refactoring/test/method/data/PullUpMethod/classB.class",
			"../PADL Refactorings/target/test-classes/padl/refactoring/test/method/data/PullUpMethod/classC.class" };

	public RefactoringPullUpMethodTest(String name) {
		super(name);
	}

	// construire le modèle
	protected void setUp() throws Exception {
		if (RefactoringPullUpMethodTest.IdiomLevelModel == null) {
			final ICodeLevelModel codeLevelModel = Factory.getInstance()
					.createCodeLevelModel("");
			codeLevelModel.create(new CompleteClassFileCreator(paths, true));
			RefactoringPullUpMethodTest.IdiomLevelModel = (IIdiomLevelModel) new AACRelationshipsAnalysis()
					.invoke(codeLevelModel);
		}
	}

	public void testPullUpMethod() {
		final RefactoringPullUpMethod refactoringMethod = new RefactoringPullUpMethod(
				RefactoringPullUpMethodTest.IdiomLevelModel);
		final IMethod method = refactoringMethod.getMethodToRefactor(
				"padl.refactoring.test.method.data.PullUpMethod.classB", "foo");
		if (method != null) {
			System.out.println("Before Refactoring PullUpMethod");
			final IClass superClass = (IClass) RefactoringPullUpMethodTest.IdiomLevelModel
					.getTopLevelEntityFromID(
							"padl.refactoring.test.method.data.PullUpMethod.classA");
			final Iterator iterator = superClass
					.getIteratorOnInheritingEntities();
			while (iterator.hasNext()) {
				final IClass theClass = (IClass) iterator.next();
				final IMethod theMethod = refactoringMethod
						.getMethodToRefactor(theClass.getDisplayName(), "foo");
				if (theMethod != null) {
					System.out.println("-----­" + theClass.getDisplayName());
					System.out.println("-----­" + theMethod.getDisplayName());

				}
			}
			refactoringMethod.pullUpMethod("foo",
					"padl.refactoring.test.method.data.PullUpMethod.classA");
			System.out.println("After Refactoring PullUpMethod");
			IClass theSuperClass = refactoringMethod
					.getClassContainsMethodToRefactor(
							"padl.refactoring.test.method.data.PullUpMethod.classA",
							"foo");
			if (theSuperClass != null) {
				System.out.println("-----­" + theSuperClass.getDisplayName());
				System.out.println("-----­" + method.getDisplayName());
				final Iterator iter = superClass
						.getIteratorOnInheritingEntities();
				while (iter.hasNext()) {
					final IClass theClass = (IClass) iter.next();
					final IMethod theMethod = refactoringMethod
							.getMethodToRefactor(theClass.getDisplayName(),
									method.getDisplayName());
					if (theMethod != null) {
						System.out
								.println("-----­" + theClass.getDisplayName());
						System.out
								.println("-----­" + theMethod.getDisplayName());
						Assert.assertEquals("Name of the method pull uped",
								"foo", theMethod.getDisplayName());
					}
				}
			}
		}
	}

	public static Test suite() {
		TestSuite suite = new TestSuite();
		suite.addTest(new RefactoringPullUpMethodTest("testPullUpMethod"));
		return suite;
	}

}
