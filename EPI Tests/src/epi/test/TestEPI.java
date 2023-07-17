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
package epi.test;

import epi.test.java.JavaAWTTest;
import epi.test.java.JavaSwingTest;
import junit.framework.Test;

/**
 * @author Yann-Gaël Guéhéneuc
 * @since  2004/01/25
 */
// TODO: Implement and test the tests!
// Non-fuzzy first, then fuzzy...
public final class TestEPI extends junit.framework.TestSuite {
	public TestEPI() {
		//empty block
	}
	public TestEPI(final Class theClass) {
		super(theClass);
	}
	public TestEPI(final String name) {
		super(name);
	}
	public static Test suite() {
		final TestEPI suite = new TestEPI();
		suite.addTestSuite(JavaAWTTest.class);
		suite.addTestSuite(JavaSwingTest.class);
		return suite;
	}
}
