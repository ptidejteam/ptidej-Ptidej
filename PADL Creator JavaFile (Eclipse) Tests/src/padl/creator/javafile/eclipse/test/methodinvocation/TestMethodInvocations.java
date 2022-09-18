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

import junit.framework.Test;
import junit.framework.TestSuite;
import padl.creator.javafile.eclipse.test.others.CompleteCreatorTest;
import padl.kernel.impl.MethodInvocationTest;

public class TestMethodInvocations extends TestSuite {
	public static Test suite() {
		final TestMethodInvocations suite = new TestMethodInvocations();
		suite.addTestSuite(JavaFilesModelMethodInvocationExplorer.class);
		suite.addTestSuite(MethodInvocationExplorer.class);
		// normal failure, it is to show the difference
		suite.addTestSuite(MethodInvocationTest.class);
		suite.addTestSuite(CompleteCreatorTest.class);
		suite.addTestSuite(MI_ChainOfMessagesTest.class);
		suite.addTestSuite(MI_ClassClassTest1.class);
		suite.addTestSuite(MI_ClassClassTest.class);
		suite.addTestSuite(MI_ClassInstanceFieldTest.class);
		suite.addTestSuite(MI_ClassInstanceTest.class);
		suite.addTestSuite(MI_Composite4AbstractDocumentTest.class);
		suite.addTestSuite(MI_FieldAccessTest.class);
		suite.addTestSuite(MI_InstanceClassTest2.class);
		suite.addTestSuite(MI_InstanceClassTest.class);
		suite.addTestSuite(MI_InstanceCreationTest.class);
		suite.addTestSuite(MI_InstanceInstanceFieldTest.class);
		suite.addTestSuite(MI_InstanceInstanceTest.class);
		suite.addTestSuite(PooyaTest.class);
		return suite;
	}
	public TestMethodInvocations() {
	}
	public TestMethodInvocations(final Class theClass) {
		super(theClass);
	}
	public TestMethodInvocations(final String name) {
		super(name);
	}
}
