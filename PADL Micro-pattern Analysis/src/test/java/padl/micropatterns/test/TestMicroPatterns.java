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

import junit.framework.Test;
import junit.framework.TestSuite;
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

/**
 * @author tanterij
 * @author Yann
 */
public class TestMicroPatterns extends TestSuite {
	public static Test suite() {
		final TestMicroPatterns suite = new TestMicroPatterns();
		suite.setName(TestMicroPatterns.class.getName());

		suite.addTestSuite(RepositoryTest.class);
		suite.addTestSuite(DesignatorTest.class);
		suite.addTestSuite(TaxonormyTest.class);
		suite.addTestSuite(JoinerTest.class);
		suite.addTestSuite(PoolTest.class);
		suite.addTestSuite(FunctionPointerTest.class);
		suite.addTestSuite(FunctionObjectTest.class);
		suite.addTestSuite(CobolLikeTest.class);
		suite.addTestSuite(StatelessTest.class);
		suite.addTestSuite(CommonStateTest.class);
		suite.addTestSuite(ImmutableTest.class);
		suite.addTestSuite(RestrictedCreationTest.class);
		suite.addTestSuite(SamplerTest.class);
		suite.addTestSuite(BoxTest.class);
		suite.addTestSuite(CanopyTest.class);
		suite.addTestSuite(CompoundBoxTest.class);
		suite.addTestSuite(RecordTest.class);
		suite.addTestSuite(DataManagerTest.class);
		suite.addTestSuite(SinkTest.class);
		suite.addTestSuite(OutlineTest.class);
		suite.addTestSuite(TraitTest.class);
		suite.addTestSuite(StateMachineTest.class);
		suite.addTestSuite(PureTypeTest.class);
		suite.addTestSuite(AugmentedTypeTest.class);
		suite.addTestSuite(PseudoClassTest.class);
		suite.addTestSuite(ImplementorTest.class);
		suite.addTestSuite(OverriderTest.class);
		suite.addTestSuite(ExtenderTest.class);

		return suite;
	}
}
