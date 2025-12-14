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
package padl.serialiser.test;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * @author Yann 
 * since   2009/03/21
 */
public class TestJOSSerialiser extends TestSuite {
	public static Test suite() {
		final TestJOSSerialiser suite = new TestJOSSerialiser();
		suite.setName(TestJOSSerialiser.class.getName());

		suite.addTestSuite(CompositeTest.class);
		suite.addTestSuite(JHotDrawTest.class);
		suite.addTestSuite(ArgoUML0198Test.class);
		suite.addTestSuite(ArgoUML020Test.class);

		return suite;
	}
}
