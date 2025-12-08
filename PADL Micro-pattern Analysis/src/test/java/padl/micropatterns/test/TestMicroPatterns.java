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
package padl.micropatterns.test;

import padl.micropatterns.test.cases.AugmentedTypeTest;
import padl.micropatterns.test.cases.BoxTest;
import padl.micropatterns.test.cases.CanopyTest;
import padl.micropatterns.test.cases.CobolLikeTest;
import padl.micropatterns.test.cases.CommonStateTest;
import padl.micropatterns.test.cases.CompoundBoxTest;
import padl.micropatterns.test.cases.DataManagerTest;
import padl.micropatterns.test.cases.DesignatorTest;
import padl.micropatterns.test.cases.ExtenderTest;
import padl.micropatterns.test.cases.FunctionObjectTest;
import padl.micropatterns.test.cases.FunctionPointerTest;
import padl.micropatterns.test.cases.ImmutableTest;
import padl.micropatterns.test.cases.ImplementorTest;
import padl.micropatterns.test.cases.JoinerTest;
import padl.micropatterns.test.cases.OutlineTest;
import padl.micropatterns.test.cases.OverriderTest;
import padl.micropatterns.test.cases.PoolTest;
import padl.micropatterns.test.cases.PseudoClassTest;
import padl.micropatterns.test.cases.PureTypeTest;
import padl.micropatterns.test.cases.RecordTest;
import padl.micropatterns.test.cases.RepositoryTest;
import padl.micropatterns.test.cases.RestrictedCreationTest;
import padl.micropatterns.test.cases.SamplerTest;
import padl.micropatterns.test.cases.SinkTest;
import padl.micropatterns.test.cases.StateMachineTest;
import padl.micropatterns.test.cases.StatelessTest;
import padl.micropatterns.test.cases.TaxonormyTest;
import padl.micropatterns.test.cases.TraitTest;
import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * @author tanterij
 * @author Yann
 */
public class TestMicroPatterns extends TestSuite {
	public TestMicroPatterns() {
	}
	public TestMicroPatterns(final Class theClass) {
		super(theClass);
	}
	public TestMicroPatterns(final String name) {
		super(name);
	}
	public static Test suite() {
		final TestMicroPatterns suite = new TestMicroPatterns();

		suite.addTest(new TestSuite(RepositoryTest.class));

		suite.addTest(new TestSuite(DesignatorTest.class));
		suite.addTest(new TestSuite(TaxonormyTest.class));
		suite.addTest(new TestSuite(JoinerTest.class));
		suite.addTest(new TestSuite(PoolTest.class));
		suite.addTest(new TestSuite(FunctionPointerTest.class));
		suite.addTest(new TestSuite(FunctionObjectTest.class));
		suite.addTest(new TestSuite(CobolLikeTest.class));
		suite.addTest(new TestSuite(StatelessTest.class));
		suite.addTest(new TestSuite(CommonStateTest.class));
		suite.addTest(new TestSuite(ImmutableTest.class));
		suite.addTest(new TestSuite(RestrictedCreationTest.class));
		suite.addTest(new TestSuite(SamplerTest.class));
		suite.addTest(new TestSuite(BoxTest.class));
		suite.addTest(new TestSuite(CanopyTest.class));
		suite.addTest(new TestSuite(CompoundBoxTest.class));
		suite.addTest(new TestSuite(RecordTest.class));
		suite.addTest(new TestSuite(DataManagerTest.class));
		suite.addTest(new TestSuite(SinkTest.class));
		suite.addTest(new TestSuite(OutlineTest.class));
		suite.addTest(new TestSuite(TraitTest.class));
		suite.addTest(new TestSuite(StateMachineTest.class));
		suite.addTest(new TestSuite(PureTypeTest.class));
		suite.addTest(new TestSuite(AugmentedTypeTest.class));
		suite.addTest(new TestSuite(PseudoClassTest.class));
		suite.addTest(new TestSuite(ImplementorTest.class));
		suite.addTest(new TestSuite(OverriderTest.class));
		suite.addTest(new TestSuite(ExtenderTest.class));
		return suite;
	}
}
