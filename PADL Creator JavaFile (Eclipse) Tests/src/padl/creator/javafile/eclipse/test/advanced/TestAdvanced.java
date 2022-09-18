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
package padl.creator.javafile.eclipse.test.advanced;

import junit.framework.Test;
import junit.framework.TestSuite;

public class TestAdvanced extends TestSuite {
	public static Test suite() {
		final TestAdvanced suite = new TestAdvanced();
		suite.addTestSuite(DuplicationTest.class);
		suite.addTestSuite(EssaiTest.class);
		suite.addTestSuite(ExclusionTest.class);
		suite.addTestSuite(FieldsAndReturnTypesTest.class);
		suite.addTestSuite(WeirdTest.class);
		return suite;
	}
	public TestAdvanced() {
	}
	public TestAdvanced(final Class theClass) {
		super(theClass);
	}
	public TestAdvanced(final String name) {
		super(name);
	}
}
