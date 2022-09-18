// **************************************************
// *                   J-CHOCO                      *
// *   Copyright (C) F. Laburthe, 1999-2003         *
// **************************************************
// *  an open-source Constraint Programming Kernel  *
// *     for Research and Education                 *
// **************************************************

/* File choco.test.search.TestSuite.java, last modified by Francois 3 déc. 2003 00:01:54 */
package choco.test.search;

import java.util.logging.Logger;
import junit.framework.Test;

public class TestSuite extends junit.framework.TestSuite {
	private static Logger logger = Logger.getLogger("choco.test.search");

	public static Test suite() {
		final TestSuite test = new TestSuite();

		TestSuite.logger.fine("Build TestSuite for choco.test.search");
		test.addTestSuite(SolveTest.class);
		test.addTestSuite(QueensTest.class);
		test.addTestSuite(ZebraTest.class);
		return test;
	}
}