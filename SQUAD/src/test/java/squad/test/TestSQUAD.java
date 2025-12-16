package squad.test;

import junit.framework.TestSuite;

public class TestSQUAD extends TestSuite {
	public static TestSuite suite() {
		final TestSQUAD suite = new TestSQUAD();
		suite.setName(TestSQUAD.class.getName());

		suite.addTestSuite(SanityTest.class);

		return suite;
	}
}
