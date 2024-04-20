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
package ptidej.solver.test.java.fingerprint.comparison;

import java.util.Date;
import junit.framework.Test;
import junit.framework.TestResult;

public final class TestSuite extends junit.framework.TestSuite {
	private Date start = null;

	public TestSuite() {
	}
	public TestSuite(final Class theClass) {
		super(theClass);
	}
	public TestSuite(final String name) {
		super(name);
	}
	public static Test suite() {
		final TestSuite suite = new TestSuite();
		suite.addTestSuite(CompositeComposite1.class);
		suite.addTestSuite(CompositeComposite2.class);
		suite.addTestSuite(CompositeJHotDraw.class);
		suite.addTestSuite(CompositeJUnit.class);
		suite.addTestSuite(CompositeLexi.class);
		suite.addTestSuite(CompositeQuickUml.class);
		suite.addTestSuite(SimpleCompositeComposite2.class);
		return suite;
	}

	public void run(final TestResult t) {
		if (this.start == null) {
			this.start = new Date(System.currentTimeMillis());
		}
		super.run(t);
		System.out.print(this.start);
		System.out.print(" --> ");
		System.out.println(new Date(System.currentTimeMillis()));
	}
}
