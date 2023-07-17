package choco.test.integer;

import java.util.logging.Logger;
import junit.framework.Test;

public class TestSuite extends junit.framework.TestSuite {
	private static Logger logger = Logger.getLogger("choco.test.search");

	public static Test suite() {
		final TestSuite test = new TestSuite();

		TestSuite.logger.fine("Build TestSuite for choco.test.search");
		//test.addTestSuite(LinkedListIntDomainTest.class);
		test.addTestSuite(BitSetIntDomainTest.class);
		test.addTestSuite(IntervalIntDomainTest.class);
		test.addTestSuite(IntVarEventTest.class);
		test.addTestSuite(EqualXCTest.class);
		test.addTestSuite(EqualXYCTest.class);
		test.addTestSuite(GreaterOrEqualXCTest.class);
		test.addTestSuite(GreaterOrEqualXYCTest.class);
		test.addTestSuite(LessOrEqualXCTest.class);
		test.addTestSuite(NotEqualXCTest.class);
		test.addTestSuite(NotEqualXYCTest.class);
		test.addTestSuite(IntLinCombTest.class);

		return test;
	}
}
