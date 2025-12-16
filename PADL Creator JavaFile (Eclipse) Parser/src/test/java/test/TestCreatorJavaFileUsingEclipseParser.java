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
package test;

import junit.framework.TestSuite;

public class TestCreatorJavaFileUsingEclipseParser extends TestSuite {
	public static TestSuite suite() {
		final TestCreatorJavaFileUsingEclipseParser suite = new TestCreatorJavaFileUsingEclipseParser();
		suite.setName(TestCreatorJavaFileUsingEclipseParser.class.getName());

		suite.addTestSuite(ConciseParsingTest.class);
		suite.addTestSuite(FileListJavaProjectTest.class);
		suite.addTestSuite(JavaParserLineAndBlockCommentTest.class);
		suite.addTestSuite(SimpleJavaParserTest.class);
		suite.addTestSuite(VerboseParsingTest.class);

		return suite;
	}
}
