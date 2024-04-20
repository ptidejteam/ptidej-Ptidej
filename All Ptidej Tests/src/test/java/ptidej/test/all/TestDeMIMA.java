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

import junit.framework.Test;
import junit.framework.TestSuite;
import ptidej.solver.claire.test.TestPtidejSolver3;
import ptidej.solver.fingerprint.test.TestPtidejSolverFingerprints;
import ptidej.solver.java.test.TestPtidejSolver4;

/**
 * @author Yann-Gaël Guéhéneuc
 * @since 2008/12/04
 */
public final class TestDeMIMA extends TestSuite {
	public static Test suite() {
		final TestDeMIMA suite = new TestDeMIMA();

		suite.addTest(TestPtidejSolver3.suite());
		suite.addTest(TestPtidejSolver4.suite());
		suite.addTest(TestPtidejSolverFingerprints.suite());

		return suite;
	}
}
