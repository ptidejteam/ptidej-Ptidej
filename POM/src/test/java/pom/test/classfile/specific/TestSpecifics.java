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
package pom.test.classfile.specific;

import junit.framework.Test;
import junit.framework.TestSuite;

public class TestSpecifics {
	public static Test suite() {
		final TestSuite suite = new TestSuite("Test for pom.test.specific");

		suite.addTestSuite(TestAID.class);
		suite.addTestSuite(TestCBO.class);
		suite.addTestSuite(TestDIT.class);
		suite.addTestSuite(TestNMI.class);
		suite.addTestSuite(TestNOC.class);
		suite.addTestSuite(TestUnaryCBO.class);
		suite.addTestSuite(TestWMC.class);
		suite.addTestSuite(TestWMC1.class);

		return suite;
	}
}
