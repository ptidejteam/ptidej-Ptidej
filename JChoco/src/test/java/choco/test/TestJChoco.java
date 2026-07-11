package choco.test;

import java.util.logging.Logger;

import junit.framework.Test;
import junit.framework.TestSuite;

public class TestJChoco extends TestSuite {
	private static Logger logger = Logger.getLogger("choco.test");

	public static Test suite() {
		final TestSuite test = new TestJChoco();

		TestJChoco.logger.fine("Build main TestSuite for choco.test");

		test.addTest(choco.palm.test.PalmTestSuite.suite());

		test.addTestSuite(ChocoSolveTest.class);

		test.addTest(choco.test.integer.TestSuite.suite());
		test.addTest(choco.test.mem.TestSuite.suite());
		test.addTest(choco.test.search.TestSuite.suite());
		test.addTest(choco.test.util.TestSuite.suite());

		return test;
	}
}
