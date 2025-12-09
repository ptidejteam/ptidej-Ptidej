package padl.creator.msefile.test;

import junit.framework.Test;
import junit.framework.TestSuite;

public class TestCreatorMSE extends TestSuite {
	public static Test suite() {
		final TestCreatorMSE suite = new TestCreatorMSE();

		suite.addTestSuite(Sanity1Test.class);
		suite.addTestSuite(Sanity2Test.class);
		
		return suite;
	}
}
