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
package padl.creator.cppfile.antlr.test.all;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * @author robidose
 */
public class TestCPPCreator extends TestSuite {

	public TestCPPCreator() {
	}
	public TestCPPCreator(final Class theClass) {
		super(theClass);
	}
	public TestCPPCreator(final String name) {
		super(name);
	}
	public static Test suite() {
		final TestCPPCreator suite = new TestCPPCreator();
		suite.addTest(new TestCPPCreator(TestWorld.class));
		suite.addTest(new TestCPPCreator(TestInheritance.class));
		suite.addTest(new TestCPPCreator(TestCpoint.class));
		suite.addTest(new TestCPPCreator(TestCsegment2.class));
		return suite;
	}
}
