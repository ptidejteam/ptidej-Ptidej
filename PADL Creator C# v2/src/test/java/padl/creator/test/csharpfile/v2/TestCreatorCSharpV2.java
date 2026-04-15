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
package padl.creator.test.csharpfile.v2;

import junit.framework.TestSuite;

/**
 * TestCase for our CSharp to PADL parser/converter
 */
public class TestCreatorCSharpV2 extends TestSuite {
	public static TestSuite suite() {
		final TestCreatorCSharpV2 suite = new TestCreatorCSharpV2();
		suite.setName(TestCreatorCSharpV2.class.getName());

		suite.addTestSuite(SanityTest.class);

		return suite;
	}
}
