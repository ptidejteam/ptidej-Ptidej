package cpl.test;

import junit.framework.Test;
import junit.framework.TestSuite;
import util.lang.test.ConstantPoolConversionTest;
import util.lang.test.FieldInfoListConversionTest;
import util.lang.test.FieldInfoConversionTest;
import util.lang.test.GetDescConversionTest;
import util.lang.test.GetMagicConversionTest;
import util.lang.test.GetMethodsConversionTest;
import util.lang.test.InterfaceListConversionTest;
import util.lang.test.SuperNameConversionTest;
import util.io.test.SubtypeLoaderTest;
import util.lang.test.CFParseBCELConvertorTest1;
import util.lang.test.CFParseBCELConvertorTest2;
import util.lang.test.MethodInfoConversionTest;
import util.lang.test.MethodInfoListConversionTest;
import util.lang.test.ReflectiveInstantiationTest;

public class TestCPL extends TestSuite {
	public TestCPL() {
	}

	public TestCPL(final Class theClass) {
		super(theClass);
	}

	public TestCPL(final String name) {
		super(name);
	}

	public static Test suite() {
		final TestCPL suite = new TestCPL();
		suite.addTestSuite(ConstantPoolConversionTest.class);
		suite.addTestSuite(GetDescConversionTest.class);
		suite.addTestSuite(GetMagicConversionTest.class);
		suite.addTestSuite(GetMethodsConversionTest.class);
		suite.addTestSuite(SuperNameConversionTest.class);
		suite.addTestSuite(CFParseBCELConvertorTest1.class);
		suite.addTestSuite(CFParseBCELConvertorTest2.class);
		suite.addTestSuite(FieldInfoConversionTest.class);
		suite.addTestSuite(FieldInfoListConversionTest.class);
		suite.addTestSuite(InterfaceListConversionTest.class);
		suite.addTestSuite(MethodInfoConversionTest.class);
		suite.addTestSuite(MethodInfoListConversionTest.class);
		suite.addTestSuite(ReflectiveInstantiationTest.class);
		suite.addTestSuite(SubtypeLoaderTest.class);
		return suite;
	}
}
