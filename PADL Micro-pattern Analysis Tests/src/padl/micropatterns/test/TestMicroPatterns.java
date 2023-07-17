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

import padl.micropatterns.test.cases.TestAugmentedType;
import padl.micropatterns.test.cases.TestBox;
import padl.micropatterns.test.cases.TestCanopy;
import padl.micropatterns.test.cases.TestCobolLike;
import padl.micropatterns.test.cases.TestCommonState;
import padl.micropatterns.test.cases.TestCompoundBox;
import padl.micropatterns.test.cases.TestDataManager;
import padl.micropatterns.test.cases.TestDesignator;
import padl.micropatterns.test.cases.TestExtender;
import padl.micropatterns.test.cases.TestFunctionObject;
import padl.micropatterns.test.cases.TestFunctionPointer;
import padl.micropatterns.test.cases.TestImmutable;
import padl.micropatterns.test.cases.TestImplementor;
import padl.micropatterns.test.cases.TestJoiner;
import padl.micropatterns.test.cases.TestOutline;
import padl.micropatterns.test.cases.TestOverrider;
import padl.micropatterns.test.cases.TestPool;
import padl.micropatterns.test.cases.TestPseudoClass;
import padl.micropatterns.test.cases.TestPureType;
import padl.micropatterns.test.cases.TestRecord;
import padl.micropatterns.test.cases.TestRepository;
import padl.micropatterns.test.cases.TestRestrictedCreation;
import padl.micropatterns.test.cases.TestSampler;
import padl.micropatterns.test.cases.TestSink;
import padl.micropatterns.test.cases.TestStateMachine;
import padl.micropatterns.test.cases.TestStateless;
import padl.micropatterns.test.cases.TestTaxonormy;
import padl.micropatterns.test.cases.TestTrait;
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

		suite.addTest(new TestSuite(TestRepository.class));

		suite.addTest(new TestSuite(TestDesignator.class));
		suite.addTest(new TestSuite(TestTaxonormy.class));
		suite.addTest(new TestSuite(TestJoiner.class));
		suite.addTest(new TestSuite(TestPool.class));
		suite.addTest(new TestSuite(TestFunctionPointer.class));
		suite.addTest(new TestSuite(TestFunctionObject.class));
		suite.addTest(new TestSuite(TestCobolLike.class));
		suite.addTest(new TestSuite(TestStateless.class));
		suite.addTest(new TestSuite(TestCommonState.class));
		suite.addTest(new TestSuite(TestImmutable.class));
		suite.addTest(new TestSuite(TestRestrictedCreation.class));
		suite.addTest(new TestSuite(TestSampler.class));
		suite.addTest(new TestSuite(TestBox.class));
		suite.addTest(new TestSuite(TestCanopy.class));
		suite.addTest(new TestSuite(TestCompoundBox.class));
		suite.addTest(new TestSuite(TestRecord.class));
		suite.addTest(new TestSuite(TestDataManager.class));
		suite.addTest(new TestSuite(TestSink.class));
		suite.addTest(new TestSuite(TestOutline.class));
		suite.addTest(new TestSuite(TestTrait.class));
		suite.addTest(new TestSuite(TestStateMachine.class));
		suite.addTest(new TestSuite(TestPureType.class));
		suite.addTest(new TestSuite(TestAugmentedType.class));
		suite.addTest(new TestSuite(TestPseudoClass.class));
		suite.addTest(new TestSuite(TestImplementor.class));
		suite.addTest(new TestSuite(TestOverrider.class));
		suite.addTest(new TestSuite(TestExtender.class));
		return suite;
	}
}
