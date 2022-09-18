/** -------------------------------------------------
 *                   J-CHOCO
 *   Copyright (C) F. Laburthe, 1999-2003
 * --------------------------------------------------
 *    an open-source Constraint Programming Kernel
 *          for Research and Education
 * --------------------------------------------------
 *
 * file: choco.test.util.TestSuite.java
 * last modified by Francois 28 août 2003:14:59:09
 */
package choco.test.util;

import java.util.logging.Logger;
import junit.framework.Test;

public class TestSuite extends junit.framework.TestSuite {
	private static Logger logger = Logger.getLogger("choco.test.util");

	public static Test suite() {
		final TestSuite test = new TestSuite();

		TestSuite.logger.fine("Build TestSuite for choco.test.util");
		test.addTestSuite(BitSetTest.class);
		test.addTestSuite(BipartiteSetTest.class);
		test.addTestSuite(PriorityQueueTest.class);
		test.addTestSuite(StoredPointerCycleTest.class);

		return test;
	}
}
