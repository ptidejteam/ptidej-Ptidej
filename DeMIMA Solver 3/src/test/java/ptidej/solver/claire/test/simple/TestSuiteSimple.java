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
package ptidej.solver.claire.test.simple;

import junit.framework.Test;
import junit.framework.TestSuite;

public final class TestSuiteSimple extends TestSuite {
	public static Test suite() {
		final TestSuiteSimple suite = new TestSuiteSimple();

		suite.addTestSuite(CompositionTest.class);
		suite.addTestSuite(CreationTest.class);
		suite.addTestSuite(GoodInheritanceTest.class);
		suite.addTestSuite(IgnoranceTest.class);
		suite.addTestSuite(InheritanceTest.class);
		suite.addTestSuite(InheritancePath.class);
		suite.addTestSuite(UseTest.class);
		suite.addTestSuite(StrictInheritance.class);

		return suite;
	}

	public TestSuiteSimple() {
	}

	public TestSuiteSimple(final Class theClass) {
		super(theClass);
	}

	public TestSuiteSimple(final String name) {
		super(name);
	}
}
