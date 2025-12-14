package padl.creator.xmiclassdiagram.test;

import junit.framework.Test;
import junit.framework.TestSuite;

public class TestCreatorXMI extends TestSuite {
	public static Test suite() {
		final TestCreatorXMI suite = new TestCreatorXMI();
		suite.setName(TestCreatorXMI.class.getName());

		suite.addTestSuite(ClassDiagram1Test.class);

		return suite;
	}
}
