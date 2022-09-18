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
package ptidej.solver.test.java.fingerprint;

import junit.framework.Test;

public final class TestMetricalPtidejSolver extends junit.framework.TestSuite {
	public TestMetricalPtidejSolver() {
	}
	public TestMetricalPtidejSolver(final Class theClass) {
		super(theClass);
	}
	public TestMetricalPtidejSolver(final String name) {
		super(name);
	}
	public static Test suite() {
		final TestMetricalPtidejSolver suite = new TestMetricalPtidejSolver();
		suite.addTest(new TestMetricalPtidejSolver(Composition.class));
		suite.addTest(new TestMetricalPtidejSolver(Composition2.class));
		suite.addTest(new TestMetricalPtidejSolver(Composition3.class));
		suite.addTest(new TestMetricalPtidejSolver(Composition4.class));
		
		return suite;
	}
}
