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
package ptidej.solver.java.test;

import junit.framework.TestSuite;
import ptidej.solver.java.test.example.Composite1FromClassFilesTest;
import ptidej.solver.java.test.example.Composite1FromJavaFilesTest;
import ptidej.solver.java.test.manager.ManagerTest;
import ptidej.solver.java.test.simple.CompositionAndInheritanceTest;
import ptidej.solver.java.test.simple.CompositionTest;
import ptidej.solver.java.test.simple.CreationTest;
import ptidej.solver.java.test.simple.GoodInheritanceTest;
import ptidej.solver.java.test.simple.IgnoranceTest;
import ptidej.solver.java.test.simple.InheritancePathTest;
import ptidej.solver.java.test.simple.InheritanceTest;
import ptidej.solver.java.test.simple.StrictInheritanceTest;
import ptidej.solver.java.test.simple.UseTest;

/**
 * @author Yann-Gaël Guéhéneuc 
 * @since 2004/09/19
 */
public final class TestPtidejSolver4 extends TestSuite {
	public static TestSuite suite() {
		final TestPtidejSolver4 suite = new TestPtidejSolver4();
		suite.setName(TestPtidejSolver4.class.getName());

		suite.addTestSuite(ManagerTest.class);
		suite.addTestSuite(CompositionAndInheritanceTest.class);
		suite.addTestSuite(Composite1FromClassFilesTest.class);
		suite.addTestSuite(Composite1FromJavaFilesTest.class);
		suite.addTestSuite(CompositionTest.class);
		suite.addTestSuite(CreationTest.class);
		suite.addTestSuite(GoodInheritanceTest.class);
		suite.addTestSuite(IgnoranceTest.class);
		suite.addTestSuite(InheritanceTest.class);
		suite.addTestSuite(InheritancePathTest.class);
		suite.addTestSuite(StrictInheritanceTest.class);
		suite.addTestSuite(UseTest.class);

		return suite;
	}
}
