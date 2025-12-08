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
package ptidej.solver.claire.test;

import junit.framework.Test;
import junit.framework.TestSuite;
import ptidej.solver.claire.test.approximate.BadCompositionTest;
import ptidej.solver.claire.test.approximate.BadInheritanceTest;
import ptidej.solver.claire.test.approximate.CompositeTest;
import ptidej.solver.claire.test.approximate.FacadeTest;
import ptidej.solver.claire.test.approximate.FactoryMethodTest;
import ptidej.solver.claire.test.approximate.MediatorTest;
import ptidej.solver.claire.test.approximate.combinatorial.BadComposition1Test;
import ptidej.solver.claire.test.approximate.combinatorial.BadComposition2Test;
import ptidej.solver.claire.test.approximate.combinatorial.BadComposition3Test;
import ptidej.solver.claire.test.defect.AssociationDistanceAssociationPatternTest;
import ptidej.solver.claire.test.defect.InheritanceTreeDepthComposite2Test;
import ptidej.solver.claire.test.example.Composite1Test;
import ptidej.solver.claire.test.example.Composite2Test;
import ptidej.solver.claire.test.example.Composite3Test;
import ptidej.solver.claire.test.example.Composite4Test;
import ptidej.solver.claire.test.example.Composite5Test;
import ptidej.solver.claire.test.roundtrip.CompleteRoundtripTest;
import ptidej.solver.claire.test.roundtrip.HalfRoundtripTest;
import ptidej.solver.claire.test.simple.CompositionTest;
import ptidej.solver.claire.test.simple.CreationTest;
import ptidej.solver.claire.test.simple.GoodInheritanceTest;
import ptidej.solver.claire.test.simple.IgnoranceTest;
import ptidej.solver.claire.test.simple.InheritancePathTest;
import ptidej.solver.claire.test.simple.InheritanceTest;
import ptidej.solver.claire.test.simple.StrictInheritanceTest;
import ptidej.solver.claire.test.simple.UseTest;

public final class TestPtidejSolver3 extends TestSuite {
	public static Test suite() {
		final TestPtidejSolver3 suite = new TestPtidejSolver3();

		suite.addTestSuite(BadCompositionTest.class);
		suite.addTestSuite(BadInheritanceTest.class);
		suite.addTestSuite(CompositeTest.class);
		suite.addTestSuite(FacadeTest.class);
		suite.addTestSuite(FactoryMethodTest.class);
		suite.addTestSuite(MediatorTest.class);

		suite.addTestSuite(BadComposition1Test.class);
		suite.addTestSuite(BadComposition2Test.class);
		suite.addTestSuite(BadComposition3Test.class);

		suite.addTestSuite(AssociationDistanceAssociationPatternTest.class);
		// TODO Add this test back
		//		suite.addTestSuite(AssociationDistanceComposite2.class);
		suite.addTestSuite(InheritanceTreeDepthComposite2Test.class);

		suite.addTestSuite(Composite1Test.class);
		suite.addTestSuite(Composite2Test.class);
		suite.addTestSuite(Composite3Test.class);
		suite.addTestSuite(Composite4Test.class);
		suite.addTestSuite(Composite5Test.class);

		suite.addTestSuite(CompleteRoundtripTest.class);
		suite.addTestSuite(HalfRoundtripTest.class);

		suite.addTestSuite(CompositionTest.class);
		suite.addTestSuite(CreationTest.class);
		suite.addTestSuite(GoodInheritanceTest.class);
		suite.addTestSuite(IgnoranceTest.class);
		suite.addTestSuite(InheritanceTest.class);
		suite.addTestSuite(InheritancePathTest.class);
		suite.addTestSuite(UseTest.class);
		suite.addTestSuite(StrictInheritanceTest.class);

		return suite;
	}

	public TestPtidejSolver3() {
	}

	public TestPtidejSolver3(final Class theClass) {
		super(theClass);
	}

	public TestPtidejSolver3(final String name) {
		super(name);
	}
}
