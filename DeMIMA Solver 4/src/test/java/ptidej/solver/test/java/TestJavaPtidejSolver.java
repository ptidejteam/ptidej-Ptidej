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
package ptidej.solver.test.java;

import junit.framework.Test;

/**
 * @author Yann-Gaël Guéhéneuc
 * @since  2004/09/19
 */
public final class TestJavaPtidejSolver extends junit.framework.TestSuite {
	public static Test suite() {
		final TestJavaPtidejSolver suite = new TestJavaPtidejSolver();

		suite.addTest(ptidej.solver.test.java.combinatorial.TestSuite.suite());
		suite.addTest(ptidej.solver.test.java.example.TestSuite.suite());
		suite.addTest(ptidej.solver.test.java.simple.TestSuite.suite());
		
		return suite;
	}
	public TestJavaPtidejSolver() {
	}
	public TestJavaPtidejSolver(final Class theClass) {
		super(theClass);
	}
	public TestJavaPtidejSolver(final String name) {
		super(name);
	}
}
