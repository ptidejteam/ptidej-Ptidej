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
package ptidej.test.all;

import jct.test.TestJCT;
import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * @author Yann-Ga�l Gu�h�neuc
 * @since 2008/12/04
 */
public final class TestAllPtidejButCreators extends TestSuite {
	public static Test suite() {
		final TestAllPtidejButCreators suite = new TestAllPtidejButCreators();

		// suite.addTest(TestCaffeine.suite());
		// suite.addTest(TestEPI.suite());
		// suite.addTest(TestJavaParser.suite());
		suite.addTest(TestJCT.suite());
		// suite.addTest(TestMoDecSolver.suite());
		// suite.addTest(TestPADL.suite());
		// suite.addTest(TestPADLAnalyses.suite());
		// suite.addTest(TestPADLGenerator.suite());
		//		suite.addTest(TestPADLGeneratorPageRank.suite());
		//		suite.addTest(TestPADLJNI.suite());
		//		suite.addTest(TestMicroPatterns.suite());
		//		suite.addTest(TestRefactorings.suite());
		//		suite.addTest(TestDB4OSerialiser.suite());
		//		suite.addTest(TestJOSSerialiser.suite());
		//		suite.addTest(TestPOM.suite());
		//		suite.addTest(TestPtidejSolver.suite());
		//		suite.addTest(TestMetricalPtidejSolver.suite());
		//		suite.addTestSuite(TestAspectJBuilder.class);
		//		suite.addTest(TestSAD.suite());

		return suite;
	}
}
