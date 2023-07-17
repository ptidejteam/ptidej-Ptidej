package jct.test;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * @author Yann 
 * since   2009/03/21
 */
public class TestJCT extends TestSuite {
	public static Test suite() {
		final TestJCT suite = new TestJCT();

		suite.addTestSuite(JCTFactoryInitializer.class);
		suite.addTestSuite(JCTTComments.class);

		suite.addTestSuite(JCTDTMisc.class);
		suite.addTestSuite(JCTDTUsingSnpSht.class);
		suite.addTestSuite(JCTDTUsingJCT.class);

		return suite;
	}
}
