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
package padl.creator.classfile.test;

import junit.framework.Test;
import junit.framework.TestSuite;
import padl.creator.classfile.test.example.TestClassFileCompleteCreator;
import padl.creator.classfile.test.inheritance.TestInheritance;
import padl.creator.classfile.test.innerclasses.TestInnerAndMembreClasses;
import padl.creator.classfile.test.method.TestMethodInclusion;
import padl.creator.classfile.test.method.TestPrivateConstructor;
import padl.creator.classfile.test.methodInvocation.MethodInvocationMissingtest;
import padl.creator.classfile.test.path.TestPath;
import padl.creator.classfile.test.topLevelEntity.TopLevelEntityTest;
import padl.creator.classfile.test.visitor.TestVisitor;

/**
 * @author Yann-Gaël Guéhéneuc
 * @since  2004/01/25
 */
public final class TestCreatorClassFile extends TestSuite {
	public TestCreatorClassFile() {
	}
	public TestCreatorClassFile(final Class theClass) {
		super(theClass);
	}
	public TestCreatorClassFile(final String name) {
		super(name);
	}
	public static Test suite() {
		final TestCreatorClassFile suite = new TestCreatorClassFile();
		suite.addTest(TestClassFileCompleteCreator.suite());
		suite.addTest(TestVisitor.suite());
		suite.addTest(TestInnerAndMembreClasses.suite());
		// suite.addTest(TestMemoryFootprint.suite());
		suite.addTestSuite(TestMethodInclusion.class);
		suite.addTest(TestInheritance.suite());
		suite.addTestSuite(TestPrivateConstructor.class);
		suite.addTest(TestPath.suite());
		suite.addTestSuite(MethodInvocationMissingtest.class);
		suite.addTestSuite(TopLevelEntityTest.class);
		return suite;
	}
}
