package jct.test;

import jct.test.cases.JCTFactoryInitializerTest;
import jct.test.cases.JCTMiscTest;
import jct.test.cases.JCTUsingJCTTest;
import jct.test.cases.JCTUsingSnpShtTest;
import jct.test.listofunique.SanityTest;
import junit.framework.TestSuite;

/**
 * @author Yann 
 * since   2009/03/21
 */
public class TestCreatorJavaFileUsingJavaCParser extends TestSuite {
	public static TestSuite suite() {
		final TestCreatorJavaFileUsingJavaCParser suite = new TestCreatorJavaFileUsingJavaCParser();
		suite.setName(TestCreatorJavaFileUsingJavaCParser.class.getName());

		// TODO Add this test later after checking its meaning
		//	suite.addTestSuite(JCTComments.class);
		suite.addTestSuite(JCTFactoryInitializerTest.class);
		suite.addTestSuite(JCTMiscTest.class);
		suite.addTestSuite(JCTUsingSnpShtTest.class);
		suite.addTestSuite(JCTUsingJCTTest.class);
		suite.addTestSuite(SanityTest.class);

		return suite;
	}
}
