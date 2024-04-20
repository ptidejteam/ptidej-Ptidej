/*******************************************************************************
 * Copyright (c) 2001-2014 Yann-Gaël Guéhéneuc  and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/old-licenses/gpl-2.0.html
 * 
 * Contributors:
 *     Yann-Gaël Guéhéneuc  and others, see in file; API and its implementation
 ******************************************************************************/
package ptidej.solver.claire.test.defect;

import junit.framework.Test;
import junit.framework.TestSuite;

public final class TestSuiteDefect extends TestSuite {
	public static Test suite() {
		final TestSuiteDefect suite = new TestSuiteDefect();

		suite.addTestSuite(AssociationDistanceAssociationPattern.class);
		// TODO Add this test back
		//		suite.addTestSuite(AssociationDistanceComposite2.class);
		suite.addTestSuite(InheritanceTreeDepthComposite2.class);

		return suite;
	}

	public TestSuiteDefect() {
	}

	public TestSuiteDefect(final Class theClass) {
		super(theClass);
	}

	public TestSuiteDefect(final String name) {
		super(name);
	}
}
