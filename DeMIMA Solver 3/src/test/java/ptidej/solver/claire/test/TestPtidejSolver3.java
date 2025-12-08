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
import ptidej.solver.claire.test.approximate.BadComposition;
import ptidej.solver.claire.test.approximate.BadInheritance;
import ptidej.solver.claire.test.approximate.Composite;
import ptidej.solver.claire.test.approximate.Facade;
import ptidej.solver.claire.test.approximate.FactoryMethod;
import ptidej.solver.claire.test.approximate.Mediator;
import ptidej.solver.claire.test.approximate.combinatorial.BadComposition1;
import ptidej.solver.claire.test.approximate.combinatorial.BadComposition2;
import ptidej.solver.claire.test.approximate.combinatorial.BadComposition3;
import ptidej.solver.claire.test.defect.AssociationDistanceAssociationPattern;
import ptidej.solver.claire.test.defect.InheritanceTreeDepthComposite2;
import ptidej.solver.claire.test.example.Composite1;
import ptidej.solver.claire.test.example.Composite2;
import ptidej.solver.claire.test.example.Composite3;
import ptidej.solver.claire.test.example.Composite4;
import ptidej.solver.claire.test.example.Composite5;
import ptidej.solver.claire.test.roundtrip.CompleteRoundtrip;
import ptidej.solver.claire.test.roundtrip.HalfRoundtrip;
import ptidej.solver.claire.test.simple.CompositionTest;
import ptidej.solver.claire.test.simple.CreationTest;
import ptidej.solver.claire.test.simple.GoodInheritanceTest;
import ptidej.solver.claire.test.simple.IgnoranceTest;
import ptidej.solver.claire.test.simple.InheritancePath;
import ptidej.solver.claire.test.simple.InheritanceTest;
import ptidej.solver.claire.test.simple.StrictInheritance;
import ptidej.solver.claire.test.simple.UseTest;

public final class TestPtidejSolver3 extends TestSuite {
	public static Test suite() {
		final TestPtidejSolver3 suite = new TestPtidejSolver3();

		suite.addTestSuite(BadComposition.class);
		suite.addTestSuite(BadInheritance.class);
		suite.addTestSuite(Composite.class);
		suite.addTestSuite(Facade.class);
		suite.addTestSuite(FactoryMethod.class);
		suite.addTestSuite(Mediator.class);

		suite.addTestSuite(BadComposition1.class);
		suite.addTestSuite(BadComposition2.class);
		suite.addTestSuite(BadComposition3.class);

		suite.addTestSuite(AssociationDistanceAssociationPattern.class);
		// TODO Add this test back
		//		suite.addTestSuite(AssociationDistanceComposite2.class);
		suite.addTestSuite(InheritanceTreeDepthComposite2.class);

		suite.addTestSuite(Composite1.class);
		suite.addTestSuite(Composite2.class);
		suite.addTestSuite(Composite3.class);
		suite.addTestSuite(Composite4.class);
		suite.addTestSuite(Composite5.class);

		suite.addTestSuite(CompleteRoundtrip.class);
		suite.addTestSuite(HalfRoundtrip.class);

		suite.addTestSuite(CompositionTest.class);
		suite.addTestSuite(CreationTest.class);
		suite.addTestSuite(GoodInheritanceTest.class);
		suite.addTestSuite(IgnoranceTest.class);
		suite.addTestSuite(InheritanceTest.class);
		suite.addTestSuite(InheritancePath.class);
		suite.addTestSuite(UseTest.class);
		suite.addTestSuite(StrictInheritance.class);

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
