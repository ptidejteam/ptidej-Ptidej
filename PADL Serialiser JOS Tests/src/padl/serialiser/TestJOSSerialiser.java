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
package padl.serialiser;

import junit.framework.Test;
import junit.framework.TestSuite;
import padl.serialiser.test.TestArgoUML0198;
import padl.serialiser.test.TestArgoUML020;
import padl.serialiser.test.TestComposite;
import padl.serialiser.test.TestJHotDraw;

/**
 * @author Yann 
 * since   2009/03/21
 */
public class TestJOSSerialiser extends TestSuite {
	public TestJOSSerialiser() {
		super();
	}
	public TestJOSSerialiser(final Class theClass) {
		super(theClass);
	}
	public TestJOSSerialiser(final String name) {
		super(name);
	}
	public static Test suite() {
		final TestJOSSerialiser suite = new TestJOSSerialiser();

		suite.addTestSuite(TestComposite.class);
		suite.addTestSuite(TestJHotDraw.class);
		suite.addTestSuite(TestArgoUML0198.class);
		suite.addTestSuite(TestArgoUML020.class);

		return suite;
	}
}
