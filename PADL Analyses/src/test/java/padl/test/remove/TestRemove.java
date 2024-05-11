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
package padl.test.remove;

import junit.framework.Test;

public final class TestRemove extends junit.framework.TestSuite {
	public TestRemove() {
	}
	public TestRemove(final Class theClass) {
		super(theClass);
	}
	public TestRemove(final String name) {
		super(name);
	}
	public static Test suite() {
		final TestRemove suite = new TestRemove();
		suite.addTest(new TestRemove(Remove.class));
		suite.addTest(new TestRemove(ConstituentRemove.class));
		suite.addTest(new TestRemove(RemoveAndIterator.class));
		return suite;
	}
}
