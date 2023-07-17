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
package ptidej.solver.test.claire;

import junit.framework.Test;

public final class TestClairePtidejSolver extends junit.framework.TestSuite {
	public static Test suite() {
		final TestClairePtidejSolver suite = new TestClairePtidejSolver();
		suite.addTest(ptidej.solver.test.claire.defect.TestSuite.suite());
		suite.addTest(ptidej.solver.test.claire.approximate.TestSuite.suite());
		suite
			.addTest(ptidej.solver.test.claire.approximate.combinatorial.TestSuite
				.suite());
		suite.addTest(ptidej.solver.test.claire.example.TestSuite.suite());
		suite.addTest(ptidej.solver.test.claire.roundtrip.TestSuite.suite());
		suite.addTest(ptidej.solver.test.claire.simple.TestSuite.suite());
		return suite;
	}
	public TestClairePtidejSolver() {
	}
	public TestClairePtidejSolver(final Class theClass) {
		super(theClass);
	}
	public TestClairePtidejSolver(final String name) {
		super(name);
	}
}
