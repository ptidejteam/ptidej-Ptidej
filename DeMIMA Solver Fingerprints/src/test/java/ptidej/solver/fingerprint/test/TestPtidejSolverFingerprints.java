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
package ptidej.solver.fingerprint.test;

import junit.framework.Test;
import junit.framework.TestSuite;
import ptidej.solver.fingerprint.test.comparison.CompositeComposite2ComparisonTest;
import ptidej.solver.fingerprint.test.comparison.CompositeJUnitTest;
import ptidej.solver.fingerprint.test.complex.CompositeComposite1Test;
import ptidej.solver.fingerprint.test.complex.CompositeComposite2Test;
import ptidej.solver.fingerprint.test.simple.Composition1Test;
import ptidej.solver.fingerprint.test.simple.Composition2Test;
import ptidej.solver.fingerprint.test.simple.Composition3Test;
import ptidej.solver.fingerprint.test.simple.Composition4Test;

public final class TestPtidejSolverFingerprints extends TestSuite {
	public static Test suite() {
		final TestPtidejSolverFingerprints suite = new TestPtidejSolverFingerprints();
		suite.setName(TestPtidejSolverFingerprints.class.getName());

		suite.addTestSuite(CompositeComposite2ComparisonTest.class);
		// The following test takes a long time...
		suite.addTestSuite(CompositeJUnitTest.class);
		// Tests taking a long time, also missing class files
		//		suite.addTestSuite(CompositeJHotDraw.class);
		//		suite.addTestSuite(CompositeLexi.class);
		//		suite.addTestSuite(CompositeQuickUml.class);
		suite.addTestSuite(CompositeComposite1Test.class);
		suite.addTestSuite(CompositeComposite2Test.class);
		suite.addTestSuite(Composition1Test.class);
		suite.addTestSuite(Composition2Test.class);
		suite.addTestSuite(Composition3Test.class);
		suite.addTestSuite(Composition4Test.class);

		return suite;
	}
}
