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
package padl.refactoring.test;

import padl.refactoring.test.method.RefactoringMoveMethodTest;
import padl.refactoring.test.method.RefactoringPullUpMethodTest;
import padl.refactoring.test.method.RefactoringPushDownMethodTest;
import padl.refactoring.test.method.RefactoringRenameMethodTest;
import junit.framework.Test;

/**
 * @author Yann-Gaël Guéhéneuc
 * @since  2006/08/08
 */
public final class TestRefactorings extends junit.framework.TestSuite {
	public TestRefactorings() {
	}
	public TestRefactorings(final Class theClass) {
		super(theClass);
	}
	public TestRefactorings(final String name) {
		super(name);
	}
	public static Test suite() {
		final TestRefactorings suite = new TestRefactorings();
		suite.addTestSuite(RefactoringMoveMethodTest.class);
		suite.addTestSuite(RefactoringPullUpMethodTest.class);
		suite.addTestSuite(RefactoringPushDownMethodTest.class);
		suite.addTestSuite(RefactoringRenameMethodTest.class);
		return suite;
	}
}
