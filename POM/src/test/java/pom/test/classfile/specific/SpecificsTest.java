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

public class SpecificsTest {
	public static Test suite() {
		final TestSuite suite = new TestSuite("Test for pom.test.specific");

		suite.addTestSuite(AIDTest.class);
		suite.addTestSuite(CBOTest.class);
		suite.addTestSuite(DITTest.class);
		suite.addTestSuite(NMITest.class);
		suite.addTestSuite(NOCTest.class);
		suite.addTestSuite(UnaryCBOTest.class);
		suite.addTestSuite(WMC1Test.class);
		suite.addTestSuite(WMC2Test.class);

		return suite;
	}
}
