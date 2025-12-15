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
package ptidej.test.all;

import junit.framework.Test;
import junit.framework.TestSuite;
import padl.creator.aolfile.test.TestCreatorAOL;
import padl.creator.aspectj.test.TestCreatorAspectJ;
import padl.creator.classfile.test.TestCreatorClassFile;
import padl.creator.cppfile.antlr.test.TestCreatorCPPFileUsingANTLR;
import padl.creator.javafile.eclipse.test.TestCreatorJavaFileUsingEclipse;
import padl.creator.javafile.javac.test.TestCreatorJavaFileUsingJavaC;
import padl.creator.msefile.test.TestCreatorMSE;
import padl.creator.test.TestCreatorJavaFilevsClassFile;
import padl.creator.xmiclassdiagram.test.TestCreatorXMI;

/**
 * @author Yann-Gaël Guéhéneuc
 * @since 2013/05/10
 */
public final class TestCreators extends TestSuite {
	public static Test suite() {
		final TestCreators suite = new TestCreators();
		suite.setName(TestCreators.class.getName());

		suite.addTest(TestCreatorAOL.suite());
		suite.addTest(TestCreatorAspectJ.suite());

		// TODO Create proper test suites
		suite.addTestSuite(
				padl.creator.test.csharpfile.v1.CreatorCSharpTest.class);
		suite.addTestSuite(
				padl.creator.test.csharpfile.v2.CreatorCSharpTest.class);

		suite.addTest(TestCreatorCPPFileUsingANTLR.suite());
		// TODO Add test suite
		//	suite.addTest(TestCreatorCPPFileUsingEclipse.suite());
		suite.addTest(TestCreatorClassFile.suite());
		suite.addTest(TestCreatorJavaFileUsingEclipse.suite());
		suite.addTest(TestCreatorJavaFileUsingJavaC.suite());
		suite.addTest(TestCreatorJavaFilevsClassFile.suite());
		suite.addTest(TestCreatorMSE.suite());
		suite.addTest(TestCreatorXMI.suite());

		return suite;
	}
}
