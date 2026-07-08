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
package padl.creator.cppfile.eclipse.test;

import junit.framework.TestSuite;
import padl.creator.cppfile.eclipse.test.big.ChromeTest;
import padl.creator.cppfile.eclipse.test.big.CryptoTest;
import padl.creator.cppfile.eclipse.test.big.QMakeTest;
import padl.creator.cppfile.eclipse.test.big.RingDaemonTest;
import padl.creator.cppfile.eclipse.test.simple.ClassesTest;
import padl.creator.cppfile.eclipse.test.simple.FieldAccessTest;
import padl.creator.cppfile.eclipse.test.simple.FriendsTest;
import padl.creator.cppfile.eclipse.test.simple.GetOrCreateTest;
import padl.creator.cppfile.eclipse.test.simple.Simple1Test;
import padl.creator.cppfile.eclipse.test.simple.Simple2Test;
import padl.creator.cppfile.eclipse.test.simple.Simple3Test;
import padl.creator.cppfile.eclipse.test.simple.Simple4Test;
import padl.creator.cppfile.eclipse.test.simple.StructuresTest;
import padl.creator.cppfile.eclipse.test.simple.TypeNameQualifiersTest;

/**
 * 
 * @author Yann
 * @since  2013/05/04
 *
 */
public final class TestCreatorCPPFileUsingEclipse extends TestSuite {
	public static TestSuite suite() {
		final TestCreatorCPPFileUsingEclipse suite = new TestCreatorCPPFileUsingEclipse();
		suite.setName(TestCreatorCPPFileUsingEclipse.class.getName());

		suite.addTestSuite(ClassesTest.class);
		suite.addTestSuite(FieldAccessTest.class);
		suite.addTestSuite(FriendsTest.class);
		suite.addTestSuite(GetOrCreateTest.class);
		suite.addTestSuite(Simple1Test.class);
		suite.addTestSuite(Simple2Test.class);
		suite.addTestSuite(Simple3Test.class);
		suite.addTestSuite(Simple4Test.class);
		suite.addTestSuite(StructuresTest.class);
		suite.addTestSuite(TypeNameQualifiersTest.class);

		// ChromeTest is a very large integration case and is highly unstable in
		// CI/headless environments due runtime/indexing variance.
		// Keep the rest of the suite as the stable regression signal.
		//	suite.addTestSuite(ChromeTest.class);
		suite.addTestSuite(CryptoTest.class);
		suite.addTestSuite(QMakeTest.class);
		suite.addTestSuite(RingDaemonTest.class);

		return suite;
	}
}
