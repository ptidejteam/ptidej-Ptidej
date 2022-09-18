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
package ptidej.solver.test.claire.simple;

import junit.framework.Test;

public final class TestSuite extends junit.framework.TestSuite {
	public static Test suite() {
		final TestSuite suite = new TestSuite();
		suite.addTest(new TestSuite(Composition.class));
		suite.addTest(new TestSuite(Creation.class));
		suite.addTest(new TestSuite(GoodInheritance.class));
		suite.addTest(new TestSuite(Ignorance.class));
		suite.addTest(new TestSuite(Inheritance.class));
		suite.addTest(new TestSuite(InheritancePath.class));
		suite.addTest(new TestSuite(Use.class));
		suite.addTest(new TestSuite(StrictInheritance.class));
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
