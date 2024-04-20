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
package ptidej.solver.claire.test.approximate.combinatorial;

import junit.framework.Test;
import junit.framework.TestSuite;

public final class TestSuiteApproximateCombinatorial extends TestSuite {
	public static Test suite() {
		final TestSuiteApproximateCombinatorial suite = new TestSuiteApproximateCombinatorial();

		suite.addTestSuite(BadComposition1.class);
		suite.addTestSuite(BadComposition2.class);
		suite.addTestSuite(BadComposition3.class);

		return suite;
	}

	public TestSuiteApproximateCombinatorial() {
	}

	public TestSuiteApproximateCombinatorial(final Class theClass) {
		super(theClass);
	}

	public TestSuiteApproximateCombinatorial(final String name) {
		super(name);
	}
}
