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
import ptidej.solver.fingerprint.test.simple.CompositionTest1;
import ptidej.solver.fingerprint.test.simple.CompositionTest2;
import ptidej.solver.fingerprint.test.simple.CompositionTest3;
import ptidej.solver.fingerprint.test.simple.CompositionTest4;

public final class TestPtidejSolverFingerprints extends TestSuite {
	public TestPtidejSolverFingerprints() {
	}

	public TestPtidejSolverFingerprints(final Class theClass) {
		super(theClass);
	}

	public TestPtidejSolverFingerprints(final String name) {
		super(name);
	}

	public static Test suite() {
		final TestPtidejSolverFingerprints suite = new TestPtidejSolverFingerprints();

		// TODO Add these tests back
		//		suite.addTestSuite(CompositeComposite2Comparison.class);
		//		suite.addTestSuite(CompositeJHotDraw.class);
		//		suite.addTestSuite(CompositeJUnit.class);
		//		suite.addTestSuite(CompositeLexi.class);
		//		suite.addTestSuite(CompositeQuickUml.class);
		//		suite.addTestSuite(CompositeComposite1.class);
		//		suite.addTestSuite(CompositeComposite2.class);
		suite.addTestSuite(CompositionTest1.class);
		suite.addTestSuite(CompositionTest2.class);
		suite.addTestSuite(CompositionTest3.class);
		suite.addTestSuite(CompositionTest4.class);

		return suite;
	}
}
