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

import junit.framework.Test;
import sad.detection.test.classfile.swt.DetectionAntipatternSWTTest;
import sad.detection.test.classfile.xerces.SpaghettiCodeTest;
import sad.detection.test.classfile.xerces.SpaghettiCodeTest2;
import sad.detection.test.classfile.xerces.VariousSmellsTest;
import sad.detection.test.comparison.xerces.BlobTest;
import sad.detection.test.generic.BoxPlotTest;
import sad.detection.test.javafile.ideasimsyn.SomeSmellsTest;

/**
 * @author Yann-Gaël Guéhéneuc
 * @since  2004/01/25
 */
// TODO: Implement and test the tests!
// Non-fuzzy first, then fuzzy...
public final class TestSAD extends junit.framework.TestSuite {
	public static Test suite() {
		final TestSAD suite = new TestSAD();

		suite.addTestSuite(DetectionAntipatternSWTTest.class);
		suite.addTestSuite(SpaghettiCodeTest.class);
		suite.addTestSuite(SpaghettiCodeTest2.class);
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

	public TestSAD() {
		// Empty block.
	}

	public TestSAD(final Class theClass) {
		super(theClass);
	}

	public TestSAD(final String name) {
		super(name);
	}
}
