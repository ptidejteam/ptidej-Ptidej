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
package ptidej.solver.test.claire.approximate;

import junit.framework.Test;

public final class TestSuite extends junit.framework.TestSuite {
	public static Test suite() {
		final TestSuite suite = new TestSuite();
		suite.addTest(new TestSuite(BadComposition.class));
		suite.addTest(new TestSuite(BadInheritance.class));
		suite.addTest(new TestSuite(Composite.class));
		suite.addTest(new TestSuite(Facade.class));
		suite.addTest(new TestSuite(FactoryMethod.class));
		suite.addTest(new TestSuite(Mediator.class));
		return suite;
	}
	public TestSuite() {
	}
	public TestSuite(final Class theClass) {
		super(theClass);
	}
	public TestSuite(final String name) {
		super(name);
	}
}
