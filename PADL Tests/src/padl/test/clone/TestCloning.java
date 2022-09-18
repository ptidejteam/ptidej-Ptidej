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
package padl.test.clone;

import junit.framework.Test;

public final class TestCloning extends junit.framework.TestSuite {
	public TestCloning() {
	}
	public TestCloning(final Class theClass) {
		super(theClass);
	}
	public TestCloning(final String name) {
		super(name);
	}
	public static Test suite() {
		final TestCloning suite = new TestCloning();
		suite.addTestSuite(Clone.class);
		suite.addTestSuite(CopyIn.class);
		suite.addTestSuite(Remove.class);
		return suite;
	}
}
