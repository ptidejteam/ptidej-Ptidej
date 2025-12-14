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
package padl.creator.cppfile.eclipse.test.big;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * @author yann
 */
public class TestPADLJNI extends TestSuite {
	public static Test suite() {
		final TestPADLJNI suite = new TestPADLJNI();
		suite.setName(TestPADLJNI.class.getName());

		suite.addTestSuite(JNIGlobalFunction.class);
		suite.addTestSuite(JNIMethodMissed.class);
		suite.addTestSuite(JNIModel.class);
		suite.addTestSuite(JNINativeMethod.class);
		suite.addTestSuite(JNINativeMethodMissed.class);

		return suite;
	}
}
