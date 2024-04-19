package padl.creator.msefile.test;

import junit.framework.Test;
import junit.framework.TestSuite;

public class TestCreatorMSE extends TestSuite {
	public static Test suite() {
		final TestCreatorMSE suite = new TestCreatorMSE();

		suite.addTestSuite(Test1.class);

		return suite;
	}
}
