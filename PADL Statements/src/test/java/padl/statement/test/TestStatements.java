package padl.statement.test;

import junit.framework.TestSuite;
import padl.statement.kernel.impl.MethodCloneTest;

public class TestStatements extends TestSuite {
	public static TestSuite suite() {
		final TestStatements suite = new TestStatements();
		suite.setName(TestStatements.class.getName());

		suite.addTestSuite(MethodCloneTest.class);

		return suite;
	}
}