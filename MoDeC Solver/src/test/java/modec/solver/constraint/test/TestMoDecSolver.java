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
package modec.solver.constraint.test;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * @author Yann-Gaël Guéhéneuc
 * @since 2013/04/28
 */
public final class TestMoDecSolver extends TestSuite {
	public static Test suite() {
		final TestMoDecSolver suite = new TestMoDecSolver();
		suite.setName(TestMoDecSolver.class.getName());

		suite.addTestSuite(CallerTest.class);
		suite.addTestSuite(MessageFollowsImmediatelyTest.class);

		return suite;
	}
}
