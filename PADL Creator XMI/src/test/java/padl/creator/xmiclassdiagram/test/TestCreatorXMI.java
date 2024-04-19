package padl.creator.xmiclassdiagram.test;

import junit.framework.Test;
import junit.framework.TestSuite;

public class TestCreatorXMI extends TestSuite {
	public static Test suite() {
		final TestCreatorXMI suite = new TestCreatorXMI();

		suite.addTestSuite(ClassDiagram1.class);

		return suite;
	}
}
