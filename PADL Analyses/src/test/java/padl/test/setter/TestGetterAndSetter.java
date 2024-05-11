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
package padl.test.setter;

import junit.framework.Test;

public final class TestGetterAndSetter extends junit.framework.TestSuite {
	public TestGetterAndSetter() {
	}
	public TestGetterAndSetter(final Class theClass) {
		super(theClass);
	}
	public TestGetterAndSetter(final String name) {
		super(name);
	}
	public static Test suite() {
		final TestGetterAndSetter suite = new TestGetterAndSetter();
		suite.addTest(new TestGetterAndSetter(TestSetter.class));
		suite.addTest(new TestGetterAndSetter(TestGetter.class));
		return suite;
	}
}
