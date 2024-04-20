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
package ptidej.solver.claire.test.example;

import junit.framework.Test;
import junit.framework.TestSuite;

public final class TestSuiteExample extends TestSuite {
	public static Test suite() {
		final TestSuiteExample suite = new TestSuiteExample();

		suite.addTestSuite(Composite1.class);
		suite.addTestSuite(Composite2.class);
		suite.addTestSuite(Composite3.class);
		suite.addTestSuite(Composite4.class);
		suite.addTestSuite(Composite5.class);

		return suite;
	}

	public TestSuiteExample() {
	}

	public TestSuiteExample(final Class theClass) {
		super(theClass);
	}

	public TestSuiteExample(final String name) {
		super(name);
	}
}
