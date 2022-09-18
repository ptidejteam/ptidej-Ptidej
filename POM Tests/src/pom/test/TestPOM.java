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
package pom.test;

import junit.framework.Test;
import junit.framework.TestSuite;
import pom.test.classfile.general.TestClassPrimitives;
import pom.test.classfile.general.TestCouplingCohesionMetrics;
import pom.test.classfile.general.TestLoadJDK10;
import pom.test.classfile.general.TestMethodPrimitives;
import pom.test.classfile.general.TestMetricRepository;
import pom.test.classfile.general.TestNMIandNMOwithPattern4J;
import pom.test.classfile.general.TestOperators;
import pom.test.classfile.general.TestUnaryMetrics;
import pom.test.classfile.specific.TestAID;
import pom.test.classfile.specific.TestCBO;
import pom.test.classfile.specific.TestCache;
import pom.test.classfile.specific.TestDIT;
import pom.test.classfile.specific.TestNMI;
import pom.test.classfile.specific.TestNOC;
import pom.test.classfile.specific.TestUnaryCBO;
import pom.test.classfile.specific.TestWMC;
import pom.test.classfile.specific.TestWMC1;

/**
 * @author Farouk Zaidi
 * @author Yann 
 * since   2004-02-16
 */
public class TestPOM extends TestSuite {
	public TestPOM() {
		super();
	}
	public TestPOM(final Class theClass) {
		super(theClass);
	}
	public TestPOM(final String name) {
		super(name);
	}
	public static Test suite() {
		final TestPOM suite = new TestPOM();

		suite.addTestSuite(TestCache.class);

		suite.addTestSuite(TestClassPrimitives.class);
		suite.addTestSuite(TestCouplingCohesionMetrics.class);
		suite.addTestSuite(TestLoadJDK10.class);
		suite.addTestSuite(TestMethodPrimitives.class);
		suite.addTestSuite(TestMetricRepository.class);
		suite.addTestSuite(TestNMIandNMOwithPattern4J.class);
		suite.addTestSuite(TestOperators.class);
		suite.addTestSuite(TestUnaryMetrics.class);

		suite.addTestSuite(TestAID.class);
		suite.addTestSuite(TestCache.class);
		suite.addTestSuite(TestCBO.class);
		suite.addTestSuite(TestDIT.class);
		suite.addTestSuite(TestNMI.class);
		suite.addTestSuite(TestNOC.class);
		suite.addTestSuite(TestUnaryCBO.class);
		suite.addTestSuite(TestWMC.class);
		suite.addTestSuite(TestWMC1.class);

		return suite;
	}
}
