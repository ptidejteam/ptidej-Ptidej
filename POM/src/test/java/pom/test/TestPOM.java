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
import pom.test.classfile.general.ClassPrimitivesTest;
import pom.test.classfile.general.CouplingCohesionMetricsTest;
import pom.test.classfile.general.JDK10LoadTest;
import pom.test.classfile.general.MethodPrimitivesTest;
import pom.test.classfile.general.MetricRepositoryTest;
import pom.test.classfile.general.Pattern4JNMIandNMOTest;
import pom.test.classfile.general.OperatorsTest;
import pom.test.classfile.general.UnaryMetricsTest;
import pom.test.classfile.specific.AIDTest;
import pom.test.classfile.specific.CBOTest;
import pom.test.classfile.specific.CacheTest;
import pom.test.classfile.specific.DITTest;
import pom.test.classfile.specific.NMITest;
import pom.test.classfile.specific.NOCTest;
import pom.test.classfile.specific.UnaryCBOTest;
import pom.test.classfile.specific.WMC1Test;
import pom.test.classfile.specific.WMC2Test;
import util.lang.MavenTestGuard;

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

		suite.addTestSuite(CacheTest.class);

		suite.addTestSuite(ClassPrimitivesTest.class);
		suite.addTestSuite(CouplingCohesionMetricsTest.class);
		// Yann 25/11/10: Long, too long!
		// This test takes too long and is not really
		// necessary to run in GitHub or elsewhere
		// when compiling/testing with Maven.
		if (MavenTestGuard.getInstance().isRunningOutsideMavenTest()) {
			// TODO Re-enable
			// suite.addTestSuite(TestLoadJDK10.class);
		}
		suite.addTestSuite(MethodPrimitivesTest.class);
		suite.addTestSuite(MetricRepositoryTest.class);
		suite.addTestSuite(Pattern4JNMIandNMOTest.class);
		suite.addTestSuite(OperatorsTest.class);
		suite.addTestSuite(UnaryMetricsTest.class);

		suite.addTestSuite(AIDTest.class);
		suite.addTestSuite(CacheTest.class);
		suite.addTestSuite(CBOTest.class);
		suite.addTestSuite(DITTest.class);
		suite.addTestSuite(NMITest.class);
		suite.addTestSuite(NOCTest.class);
		suite.addTestSuite(UnaryCBOTest.class);
		suite.addTestSuite(WMC1Test.class);
		suite.addTestSuite(WMC2Test.class);

		return suite;
	}
}
