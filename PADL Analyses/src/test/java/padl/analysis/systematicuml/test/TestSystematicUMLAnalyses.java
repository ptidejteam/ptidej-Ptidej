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
package padl.analysis.systematicuml.test;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * @author Yann-Gaël Guéhéneuc
 * @since 2005/10/07
 */
public final class TestSystematicUMLAnalyses
	extends junit.framework.TestSuite {

	public TestSystematicUMLAnalyses() {
	}
	public TestSystematicUMLAnalyses(final Class theClass) {
		super(theClass);
	}
	public TestSystematicUMLAnalyses(final String name) {
		super(name);
	}
	public static Test suite() {
		final TestSystematicUMLAnalyses suite =
			new TestSystematicUMLAnalyses();
		suite.addTest(new TestSuite(Test1.class));
		return suite;
	}
}
