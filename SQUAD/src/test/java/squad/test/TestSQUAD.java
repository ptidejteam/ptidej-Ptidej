package squad.test;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class TestSQUAD extends TestCase {
	public static Test suite() {
		final TestSuite suite = new TestSuite(TestSQUAD.class.getName());

		suite.addTestSuite(Test1.class);

		return suite;
	}
}
