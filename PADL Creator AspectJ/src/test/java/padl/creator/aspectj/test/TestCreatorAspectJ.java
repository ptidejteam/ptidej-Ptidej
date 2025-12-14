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
package padl.creator.aspectj.test;

import junit.framework.Test;
import junit.framework.TestSuite;

public class TestCreatorAspectJ extends TestSuite {
	public static Test suite() {
		final TestCreatorAspectJ suite = new TestCreatorAspectJ();
		suite.setName(TestCreatorAspectJ.class.getName());

		suite.addTestSuite(AOTest.class);
		suite.addTestSuite(ModelCreationTest.class);
		suite.addTestSuite(OOTest.class);

		return suite;
	}
}
