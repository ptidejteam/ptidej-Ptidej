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
package padl.test;

import junit.framework.Test;
import junit.framework.TestSuite;
import padl.kernel.impl.AbstractContainerTest;
import padl.kernel.impl.MethodInvocationTest;
import padl.test.clone.TestCloning;
import padl.test.defaultpackage.TestDefaultPackage;
import padl.test.path.TestPath;
import padl.test.remove.TestRemove;
import padl.test.setter.TestGetterAndSetter;

/**
 * @author Yann-Gaël Guéhéneuc
 * @since  2004/01/25
 */
public final class TestPADL extends TestSuite {
	public TestPADL() {
	}
	public TestPADL(final Class theClass) {
		super(theClass);
	}
	public TestPADL(final String name) {
		super(name);
	}
	public static Test suite() {
		final TestPADL suite = new TestPADL();
		suite.addTestSuite(AbstractContainerTest.class);
		suite.addTestSuite(MethodInvocationTest.class);
		suite.addTest(TestCloning.suite());
		suite.addTest(TestRemove.suite());
		suite.addTest(TestGetterAndSetter.suite());
		suite.addTestSuite(TestPath.class);
		suite.addTestSuite(TestDefaultPackage.class); // Added by Mathieu Lemoine, 2009-05-15
		return suite;
	}
}
