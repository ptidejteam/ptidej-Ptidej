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
package padl.analysis.test;

import junit.framework.Test;
import padl.analysis.aac.test.TestAACAnalyses;
import padl.analysis.packagebuilder.test.TestPackageBuilder;
import padl.creator.classfile.test.example.TestClassFileCompleteCreator;
import padl.creator.classfile.test.innerclasses.TestInnerAndMembreClasses;
import padl.creator.classfile.test.method.TestMethodInclusion;
import padl.creator.classfile.test.method.TestPrivateConstructor;
import padl.creator.classfile.test.visitor.TestVisitor;
import padl.test.clone.TestCloning;
import padl.test.path.TestPath;
import padl.test.remove.TestRemove;
import padl.test.setter.TestGetterAndSetter;

/**
 * @author Yann-Gaël Guéhéneuc
 * @since 2004/11/11
 */
public final class TestPADLAnalyses extends junit.framework.TestSuite {
	public TestPADLAnalyses() {
	}

	public TestPADLAnalyses(final Class theClass) {
		super(theClass);
	}

	public TestPADLAnalyses(final String name) {
		super(name);
	}

	public static Test suite() {
		final TestPADLAnalyses suite = new TestPADLAnalyses();

		suite.addTest(TestAACAnalyses.suite());
		suite.addTest(TestPackageBuilder.suite());
		suite.addTest(TestCloning.suite());
		suite.addTest(TestRemove.suite());
		suite.addTest(TestGetterAndSetter.suite());
		suite.addTestSuite(TestPath.class);

		suite.addTest(TestClassFileCompleteCreator.suite());
		suite.addTest(TestVisitor.suite());
		suite.addTest(TestInnerAndMembreClasses.suite());
		suite.addTestSuite(TestMethodInclusion.class);
		suite.addTestSuite(TestPrivateConstructor.class);

		return suite;
	}
}
