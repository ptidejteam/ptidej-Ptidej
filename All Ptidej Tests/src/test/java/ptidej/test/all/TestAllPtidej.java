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
package ptidej.test.all;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * @author Yann-Gaël Guéhéneuc
 * @since  2008/12/04
 */
public final class TestAllPtidej extends TestSuite {
	public static Test suite() {
		final TestAllPtidej suite = new TestAllPtidej();

		suite.addTest(TestCreators.suite());
		suite.addTest(TestDeMIMA.suite());
		suite.addTest(TestOthers.suite());

		return suite;
	}
}
