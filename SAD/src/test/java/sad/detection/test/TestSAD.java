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
package sad.detection.test;

import junit.framework.TestSuite;
import sad.detection.test.classfile.swt.DetectionAntipatternSWTTest;
import sad.detection.test.classfile.xerces.SpaghettiCode1Test;
import sad.detection.test.classfile.xerces.SpaghettiCode2Test;
import sad.detection.test.classfile.xerces.VariousSmellsTest;
import sad.detection.test.comparison.xerces.BlobTest;
import sad.detection.test.generic.BoxPlotTest;
import sad.detection.test.javafile.ideasimsyn.SomeSmellsTest;

/**
 * @author Yann-Gaël Guéhéneuc
 * @since  2004/01/25
 */
public final class TestSAD extends TestSuite {
	public static TestSuite suite() {
		final TestSAD suite = new TestSAD();
		suite.setName(TestSAD.class.getName());

		suite.addTestSuite(DetectionAntipatternSWTTest.class);
		suite.addTestSuite(SpaghettiCode1Test.class);
		suite.addTestSuite(SpaghettiCode2Test.class);
		suite.addTestSuite(VariousSmellsTest.class);
		suite.addTestSuite(BlobTest.class);
		// TODO Add these tests back
		//		suite.addTestSuite(FewSmellsTest.class);
		//		suite.addTestSuite(FewSmellsTest.class);
		//		suite.addTestSuite(RingDaemon.class);
		suite.addTestSuite(BoxPlotTest.class);
		suite.addTestSuite(SomeSmellsTest.class);

		return suite;
	}
}
