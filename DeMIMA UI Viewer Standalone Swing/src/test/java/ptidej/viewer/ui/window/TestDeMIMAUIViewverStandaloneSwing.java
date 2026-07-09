package ptidej.viewer.ui.window;

import junit.framework.TestSuite;

public class TestDeMIMAUIViewverStandaloneSwing extends TestSuite {
	public static TestSuite suite() {
		final TestDeMIMAUIViewverStandaloneSwing suite = new TestDeMIMAUIViewverStandaloneSwing();
		suite.setName(TestDeMIMAUIViewverStandaloneSwing.class.getName());

		suite.addTestSuite(DesktopPaneTest.class);

		return suite;
	}
}
