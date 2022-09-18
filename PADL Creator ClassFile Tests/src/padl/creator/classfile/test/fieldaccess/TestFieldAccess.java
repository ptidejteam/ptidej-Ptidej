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
package padl.creator.classfile.test.fieldaccess;

import junit.framework.Test;

public final class TestFieldAccess extends junit.framework.TestSuite {
	public TestFieldAccess() {
	}
	public TestFieldAccess(final Class theClass) {
		super(theClass);
	}
	public TestFieldAccess(final String name) {
		super(name);
	}
	public static Test suite() {
		final TestFieldAccess suite = new TestFieldAccess();
		suite.addTest(new TestFieldAccess(FieldAccess.class));
		return suite;
	}
}
