/*******************************************************************************
 * Copyright (c) 2001-2014 Yann-Gaël Guéhéneuc  and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/old-licenses/gpl-2.0.html
 * 
 * Contributors:
 *     Yann-Gaël Guéhéneuc  and others, see in file; API and its implementation
 ******************************************************************************/
package ptidej.solver.claire.test;

import junit.framework.Test;
import junit.framework.TestSuite;
import ptidej.solver.claire.test.approximate.TestSuiteApproximate;
import ptidej.solver.claire.test.approximate.combinatorial.TestSuiteApproximateCombinatorial;
import ptidej.solver.claire.test.defect.TestSuiteDefect;
import ptidej.solver.claire.test.example.TestSuiteExample;
import ptidej.solver.claire.test.roundtrip.TestSuiteRoundTrip;
import ptidej.solver.claire.test.simple.TestSuiteSimple;

public final class TestPtidejSolver3 extends TestSuite {
	public static Test suite() {
		final TestPtidejSolver3 suite = new TestPtidejSolver3();

		suite.addTest(TestSuiteApproximate.suite());
		suite.addTest(TestSuiteApproximateCombinatorial.suite());
		suite.addTest(TestSuiteDefect.suite());
		suite.addTest(TestSuiteExample.suite());
		suite.addTest(TestSuiteRoundTrip.suite());
		suite.addTest(TestSuiteSimple.suite());

		return suite;
	}

	public TestPtidejSolver3() {
	}

	public TestPtidejSolver3(final Class theClass) {
		super(theClass);
	}

	public TestPtidejSolver3(final String name) {
		super(name);
	}
}
