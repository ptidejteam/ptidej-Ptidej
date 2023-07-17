package jct.test;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * @author Yann 
 * since   2009/03/21
 */
public class TestJCTLong extends TestSuite {
	public static Test suite() {
		final TestJCTLong suite = new TestJCTLong();

		suite.addTestSuite(JCTDTUsingJCT.class);

		suite.addTestSuite(JCTFactoryInitializer.class);
		suite.addTestSuite(JCTTComments.class);

		suite.addTestSuite(JCTCFSCSTMisc.class);
		//	suite.addTestSuite(JCTCFSCSTUsingSnpSht.class);
		//	suite.addTestSuite(JCTCFSCSTUsingJCT.class);

		suite.addTestSuite(JCTDTMisc.class);
		suite.addTestSuite(JCTDTUsingSnpSht.class);

		return suite;
	}
}
