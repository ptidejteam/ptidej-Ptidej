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
package padl.analysis.packagebuilder.test;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * @author Yann-Gaël Guéhéneuc
 * @since 2004/11/11
 */
public final class TestPackageBuilder
	extends junit.framework.TestSuite {

	public TestPackageBuilder() {
	}
	public TestPackageBuilder(final Class theClass) {
		super(theClass);
	}
	public TestPackageBuilder(final String name) {
		super(name);
	}
	public static Test suite() {
		final TestPackageBuilder suite =
			new TestPackageBuilder();
		suite.addTest(new TestSuite(Test1.class));
		return suite;
	}
}
