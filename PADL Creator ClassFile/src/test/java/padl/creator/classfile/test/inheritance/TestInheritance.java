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
package padl.creator.classfile.test.inheritance;

import junit.framework.Test;
import junit.framework.TestSuite;

public final class TestInheritance extends TestSuite {
	public TestInheritance() {
	}
	public TestInheritance(final Class theClass) {
		super(theClass);
	}
	public TestInheritance(final String name) {
		super(name);
	}
	public static Test suite() {
		final TestInheritance suite = new TestInheritance();
		suite.addTestSuite(Inheritance.class);
		return suite;
	}
}
