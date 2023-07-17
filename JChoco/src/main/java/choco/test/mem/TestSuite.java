package choco.test.mem;

import java.util.logging.Logger;
import junit.framework.Test;

public class TestSuite extends junit.framework.TestSuite {
	private static Logger logger = Logger.getLogger("choco.test.mem");

	public static Test suite() {
		final TestSuite test = new TestSuite();

		TestSuite.logger.fine("Build TestSuite for choco.test.mem");
		test.addTestSuite(StoredIntTest.class);
		test.addTestSuite(StoredIntVectorTest.class);
		test.addTestSuite(StoredBitSetTest.class);

		return test;
	}
}
