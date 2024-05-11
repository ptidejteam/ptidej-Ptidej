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

import junit.framework.Test;
import ptidej.solver.java.test.example.Composite1FromClassFilesTest;
import ptidej.solver.java.test.example.Composite1FromJavaFilesTest;
import ptidej.solver.java.test.manager.ManagerTest;
import ptidej.solver.java.test.simple.Composition;
import ptidej.solver.java.test.simple.CompositionAndInheritanceTest;
import ptidej.solver.java.test.simple.Creation;
import ptidej.solver.java.test.simple.GoodInheritance;
import ptidej.solver.java.test.simple.Ignorance;
import ptidej.solver.java.test.simple.Inheritance;
import ptidej.solver.java.test.simple.InheritancePath;
import ptidej.solver.java.test.simple.StrictInheritance;
import ptidej.solver.java.test.simple.Use;

//import ptidej.solver.test.java.TestJavaPtidejSolver;

/**
 * @author Yann-Gaël Guéhéneuc 
 * @since 2004/09/19
 */
public final class TestPtidejSolver4 extends junit.framework.TestSuite {
	public static Test suite() {
		final TestPtidejSolver4 suite = new TestPtidejSolver4();

		suite.addTestSuite(ManagerTest.class);
		suite.addTestSuite(CompositionAndInheritanceTest.class);
		suite.addTestSuite(Composite1FromClassFilesTest.class);
		suite.addTestSuite(Composite1FromJavaFilesTest.class);
		suite.addTestSuite(Composition.class);
		suite.addTestSuite(Creation.class);
		suite.addTestSuite(GoodInheritance.class);
		suite.addTestSuite(Ignorance.class);
		suite.addTestSuite(Inheritance.class);
		suite.addTestSuite(InheritancePath.class);
		suite.addTestSuite(StrictInheritance.class);
		suite.addTestSuite(Use.class);

		return suite;
	}

	public TestPtidejSolver4() {
	}

	public TestPtidejSolver4(final Class theClass) {
		super(theClass);
	}

	public TestPtidejSolver4(final String name) {
		super(name);
	}
}
