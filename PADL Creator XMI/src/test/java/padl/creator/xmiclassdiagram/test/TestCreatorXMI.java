package padl.creator.xmiclassdiagram.test;

import junit.framework.TestSuite;

public class TestCreatorXMI extends TestSuite {
	public static TestSuite suite() {
		final TestCreatorXMI suite = new TestCreatorXMI();
		suite.setName(TestCreatorXMI.class.getName());

		suite.addTestSuite(ClassDiagram1Test.class);

		return suite;
	}
}
