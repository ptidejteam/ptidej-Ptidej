package jct.test;

import jct.test.cases.JCTFactoryInitializer;
import jct.test.cases.JCTMisc;
import jct.test.cases.JCTUsingJCT;
import jct.test.cases.JCTUsingSnpSht;
import jct.test.listofunique.Test1;
import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * @author Yann 
 * since   2009/03/21
 */
public class TestCreatorJavaFileUsingJavaCParser extends TestSuite {
	public static Test suite() {
		final TestCreatorJavaFileUsingJavaCParser suite = new TestCreatorJavaFileUsingJavaCParser();

		// TODO Add this test later after checking its meaning
		// suite.addTestSuite(JCTComments.class);
		suite.addTestSuite(JCTFactoryInitializer.class);
		suite.addTestSuite(JCTMisc.class);
		suite.addTestSuite(JCTUsingSnpSht.class);
		suite.addTestSuite(JCTUsingJCT.class);
		suite.addTestSuite(Test1.class);

		return suite;
	}
}
