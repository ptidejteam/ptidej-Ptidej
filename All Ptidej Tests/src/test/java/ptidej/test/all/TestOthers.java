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
package ptidej.test.all;

import caffeine.test.TestCaffeine;
import cpl.test.TestCPL;
import jct.test.TestCreatorJavaFileUsingJavaCParser;
import junit.framework.Test;
import junit.framework.TestSuite;
import padl.analysis.test.TestPADLAnalyses;
import padl.generator.test.TestPADLGenerator;
import padl.micropatterns.test.TestMicroPatterns;
import padl.pagerank.test.TestPADLGeneratorPageRank;
import padl.refactoring.test.TestRefactorings;
import padl.serialiser.test.TestDB4OSerialiser;
import padl.serialiser.test.TestJOSSerialiser;
import padl.test.TestPADL;
import pom.test.TestPOM;
import ptidej.ui.kernel.builder.test.AspectJBuilderTest;
import sad.detection.test.TestSAD;
import squad.test.TestSQUAD;
import test.TestCreatorJavaFileUsingEclipseParser;

/**
 * @author Yann-Gaël Guéhéneuc
 * @since 2008/12/04
 */
public final class TestOthers extends TestSuite {
	public static Test suite() {
		final TestOthers suite = new TestOthers();
		suite.setName(TestOthers.class.getName());

		suite.addTest(TestCPL.suite());
		suite.addTest(TestCaffeine.suite());
		suite.addTest(TestPADL.suite());
		suite.addTest(TestPADLAnalyses.suite());
		suite.addTest(TestPADLGenerator.suite());
		suite.addTest(TestPADLGeneratorPageRank.suite());
		// TODO Add this test
		//		suite.addTest(TestPADLJNI.suite());
		suite.addTest(TestMicroPatterns.suite());
		suite.addTest(TestRefactorings.suite());
		suite.addTest(TestDB4OSerialiser.suite());
		suite.addTest(TestJOSSerialiser.suite());
		suite.addTest(TestPOM.suite());
		suite.addTest(TestSAD.suite());
		suite.addTest(TestSQUAD.suite());

		suite.addTestSuite(AspectJBuilderTest.class);

		suite.addTest(TestCreatorJavaFileUsingEclipseParser.suite());
		suite.addTest(TestCreatorJavaFileUsingJavaCParser.suite());

		return suite;
	}
}
