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
package padl.analysis.test;

import junit.framework.Test;
import padl.analysis.aac.test.TestAACAnalyses;
import padl.analysis.packagebuilder.test.TestPackageBuilder;

/**
 * @author Yann-Gaël Guéhéneuc
 * @since 2004/11/11
 */
@SuppressWarnings("rawtypes")
public final class TestPADLAnalyses extends junit.framework.TestSuite {
	public TestPADLAnalyses() {
	}
	public TestPADLAnalyses(final Class theClass) {
		super(theClass);
	}
	public TestPADLAnalyses(final String name) {
		super(name);
	}
	public static Test suite() {
		final TestPADLAnalyses suite = new TestPADLAnalyses();
		suite.addTest(TestAACAnalyses.suite());
		suite.addTest(TestPackageBuilder.suite());
		return suite;
	}
}
