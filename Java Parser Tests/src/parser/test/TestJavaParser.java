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
package parser.test;

import junit.framework.Test;
import junit.framework.TestSuite;

public class TestJavaParser extends TestSuite {
	public TestJavaParser() {
	}
	public TestJavaParser(final Class<?> theClass) {
		super(theClass);
	}
	public TestJavaParser(final String name) {
		super(name);
	}
	public static Test suite() {
		TestJavaParser suite = new TestJavaParser();

		suite.addTestSuite(ConciseParseTest.class);
		suite.addTestSuite(FileListJavaProjectTest.class);
		suite.addTestSuite(JavaParserLineAndBlockCommentTest.class);
		suite.addTestSuite(SimpleJavaParserTest.class);
		suite.addTestSuite(VerboseParseTest.class);

		return suite;
	}
}
