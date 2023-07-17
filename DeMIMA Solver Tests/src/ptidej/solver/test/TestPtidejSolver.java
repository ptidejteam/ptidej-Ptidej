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
package ptidej.solver.test;

import junit.framework.Test;
import junit.framework.TestSuite;
import ptidej.solver.domain.test.Manager;
import ptidej.solver.test.claire.TestClairePtidejSolver;
import ptidej.solver.test.java.TestJavaPtidejSolver;

//import ptidej.solver.test.java.TestJavaPtidejSolver;

/**
 * @author Yann-Gaï¿½l Guï¿½hï¿½neuc
 * @since  2004/09/19
 */
public final class TestPtidejSolver extends junit.framework.TestSuite {
	public static Test suite() {
		final TestPtidejSolver suite = new TestPtidejSolver();
		suite.addTest(new TestSuite(Manager.class));
		suite.addTest(TestClairePtidejSolver.suite());
		suite.addTest(TestJavaPtidejSolver.suite());
		return suite;
	}
	public TestPtidejSolver() {
	}
	public TestPtidejSolver(final Class theClass) {
		super(theClass);
	}
	public TestPtidejSolver(final String name) {
		super(name);
	}
}
