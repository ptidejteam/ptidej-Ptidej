package cpl.test;

import junit.framework.Test;
import junit.framework.TestSuite;
import util.lang.test.ConstantPoolConversionTest;
import util.lang.test.ConstantPoolConversionTest2;
import util.lang.test.ConstantPoolConversionTest3;
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
import util.lang.test.CFParseBCELConvertorTest3;
import util.lang.test.CFParseBCELConvertorTest4;
import util.lang.test.CFParseBCELConvertorTest5;
import util.lang.test.CFParseBCELConvertorTest6;
import util.lang.test.CFParseBCELConvertorTest7;
import util.lang.test.CFParseBCELConvertorTest8;
import util.lang.test.CFParseBCELConvertorTestVisitor1;
import util.lang.test.CFParseBCELConvertorTestVisitor2;
import util.lang.test.CFParseBCELConvertorTestVisitor3;
import util.lang.test.CFParseBCELConvertorTestVisitor4;
import util.lang.test.CFParseBCELConvertorTestVisitor5;
import util.lang.test.CFParseBCELConvertorTestVisitor6;
import util.lang.test.MethodInfoConversionTest;
import util.lang.test.MethodInfoConversionTest2;
import util.lang.test.MethodInfoConversionTest3;
import util.lang.test.MethodInfoConversionTest4;
import util.lang.test.MethodInfoConversionTest5;
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
		
//	Henrique code 4/21/2025	
// added the rest of the test classes for full conversion capability
		suite.addTestSuite(CFParseBCELConvertorTest3.class);
		suite.addTestSuite(CFParseBCELConvertorTest4.class);
		suite.addTestSuite(CFParseBCELConvertorTest5.class);
		suite.addTestSuite(CFParseBCELConvertorTest6.class);
		suite.addTestSuite(CFParseBCELConvertorTest7.class);
		suite.addTestSuite(CFParseBCELConvertorTest8.class);
		
//		
		suite.addTestSuite(FieldInfoConversionTest.class);
		suite.addTestSuite(FieldInfoListConversionTest.class);
		suite.addTestSuite(InterfaceListConversionTest.class);
		
//		Henrique code 4/18/2025
		suite.addTestSuite(MethodInfoConversionTest.class);
		suite.addTestSuite(MethodInfoConversionTest2.class);
		suite.addTestSuite(MethodInfoConversionTest3.class);
		suite.addTestSuite(MethodInfoConversionTest4.class);
		suite.addTestSuite(MethodInfoConversionTest5.class);
		suite.addTestSuite(ConstantPoolConversionTest2.class);
		suite.addTestSuite(ConstantPoolConversionTest3.class);
//		
		
//	VISITOR Henrique 4/28/2025
		suite.addTestSuite(CFParseBCELConvertorTestVisitor1.class);	
		suite.addTestSuite(CFParseBCELConvertorTestVisitor2.class);	
		suite.addTestSuite(CFParseBCELConvertorTestVisitor3.class);
		suite.addTestSuite(CFParseBCELConvertorTestVisitor4.class);	
		suite.addTestSuite(CFParseBCELConvertorTestVisitor5.class);	
		suite.addTestSuite(CFParseBCELConvertorTestVisitor6.class);	
		
//		

		suite.addTestSuite(MethodInfoListConversionTest.class);
		suite.addTestSuite(ReflectiveInstantiationTest.class);
		suite.addTestSuite(SubtypeLoaderTest.class);
		return suite;
	}
}
