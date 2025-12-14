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
package padl.creator.aolfile.test;

import junit.framework.Test;
import junit.framework.TestSuite;

public class TestCreatorAOL extends TestSuite {
	public static Test suite() {
		final TestCreatorAOL suite = new TestCreatorAOL();
		suite.setName(TestCreatorAOL.class.getName());

		suite.addTestSuite(Sanity1Test.class);
		suite.addTestSuite(Sanity2Test.class);
		// TODO Add this test
		//	suite.addTestSuite(TestPierreNCode.class);

		return suite;
	}
}
