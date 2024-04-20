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
package ptidej.solver.claire.test.roundtrip;

import junit.framework.Test;
import junit.framework.TestSuite;

public final class TestSuiteRoundTrip extends TestSuite {
	public static Test suite() {
		final TestSuiteRoundTrip suite = new TestSuiteRoundTrip();

		suite.addTestSuite(CompleteRoundtrip.class);
		suite.addTestSuite(HalfRoundtrip.class);

		return suite;
	}

	public TestSuiteRoundTrip() {
	}

	public TestSuiteRoundTrip(final Class theClass) {
		super(theClass);
	}

	public TestSuiteRoundTrip(final String name) {
		super(name);
	}
}
