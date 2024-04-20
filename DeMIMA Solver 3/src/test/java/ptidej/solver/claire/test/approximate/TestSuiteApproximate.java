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
package ptidej.solver.claire.test.approximate;

import junit.framework.Test;
import junit.framework.TestSuite;

public final class TestSuiteApproximate extends TestSuite {
	public static Test suite() {
		final TestSuiteApproximate suite = new TestSuiteApproximate();

		suite.addTestSuite(BadComposition.class);
		suite.addTestSuite(BadInheritance.class);
		suite.addTestSuite(Composite.class);
		suite.addTestSuite(Facade.class);
		suite.addTestSuite(FactoryMethod.class);
		suite.addTestSuite(Mediator.class);

		return suite;
	}

	public TestSuiteApproximate() {
	}

	public TestSuiteApproximate(final Class theClass) {
		super(theClass);
	}

	public TestSuiteApproximate(final String name) {
		super(name);
	}
}
