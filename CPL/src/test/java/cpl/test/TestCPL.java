package cpl.test;

import junit.framework.TestSuite;
import util.io.test.SubtypeLoaderTest;
import util.lang.test.CFParseBCELConvertor1Test;
import util.lang.test.CFParseBCELConvertor2Test;
import util.lang.test.CFParseBCELConvertor3Test;
import util.lang.test.CFParseBCELConvertor4Test;
import util.lang.test.CFParseBCELConvertor5Test;
import util.lang.test.CFParseBCELConvertor6Test;
import util.lang.test.CFParseBCELConvertor7Test;
import util.lang.test.CFParseBCELConvertor8Test;
import util.lang.test.CFParseBCELConvertorVisitor1Test;
import util.lang.test.CFParseBCELConvertorVisitor2Test;
import util.lang.test.CFParseBCELConvertorVisitor3Test;
import util.lang.test.CFParseBCELConvertorVisitor4Test;
import util.lang.test.CFParseBCELConvertorVisitor5Test;
import util.lang.test.CFParseBCELConvertorVisitor6Test;
import util.lang.test.CFParseBCELConvertorVisitor7Test;
import util.lang.test.ConstantPoolConversion1Test;
import util.lang.test.ConstantPoolConversion2Test;
import util.lang.test.ConstantPoolConversion3Test;
import util.lang.test.ConvertorsComparisonTest;
import util.lang.test.FieldInfoConversionTest;
import util.lang.test.FieldInfoListConversionTest;
import util.lang.test.GetDescConversionTest;
import util.lang.test.GetMagicConversionTest;
import util.lang.test.GetMethodsConversionTest;
import util.lang.test.InterfaceListConversionTest;
import util.lang.test.MethodInfoConversion1Test;
import util.lang.test.MethodInfoConversion2Test;
import util.lang.test.MethodInfoConversion3Test;
import util.lang.test.MethodInfoConversion4Test;
import util.lang.test.MethodInfoConversion5Test;
import util.lang.test.MethodInfoListConversionTest;
import util.lang.test.ReflectiveInstantiationTest;
import util.lang.test.SuperNameConversionTest;

public class TestCPL extends TestSuite {
	public static TestSuite suite() {
		final TestCPL suite = new TestCPL();
		suite.setName(TestCPL.class.getName());

		suite.addTestSuite(ConstantPoolConversion1Test.class);
		suite.addTestSuite(GetDescConversionTest.class);
		suite.addTestSuite(GetMagicConversionTest.class);
		suite.addTestSuite(GetMethodsConversionTest.class);
		suite.addTestSuite(SuperNameConversionTest.class);
		suite.addTestSuite(CFParseBCELConvertor1Test.class);
		suite.addTestSuite(CFParseBCELConvertor2Test.class);

		// Henrique code 25/04/21	
		// Added the rest of the test classes for full conversion capability
		suite.addTestSuite(CFParseBCELConvertor3Test.class);
		suite.addTestSuite(CFParseBCELConvertor4Test.class);
		suite.addTestSuite(CFParseBCELConvertor5Test.class);
		suite.addTestSuite(CFParseBCELConvertor6Test.class);
		suite.addTestSuite(CFParseBCELConvertor7Test.class);
		suite.addTestSuite(CFParseBCELConvertor8Test.class);

		suite.addTestSuite(FieldInfoConversionTest.class);
		suite.addTestSuite(FieldInfoListConversionTest.class);
		suite.addTestSuite(InterfaceListConversionTest.class);

		// Henrique code 25/04/18
		suite.addTestSuite(MethodInfoConversion1Test.class);
		suite.addTestSuite(MethodInfoConversion2Test.class);
		suite.addTestSuite(MethodInfoConversion3Test.class);
		suite.addTestSuite(MethodInfoConversion4Test.class);
		suite.addTestSuite(MethodInfoConversion5Test.class);
		suite.addTestSuite(ConstantPoolConversion2Test.class);
		suite.addTestSuite(ConstantPoolConversion3Test.class);

		//Henrique 25/04/28
		suite.addTestSuite(CFParseBCELConvertorVisitor1Test.class);
		suite.addTestSuite(CFParseBCELConvertorVisitor2Test.class);
		suite.addTestSuite(CFParseBCELConvertorVisitor3Test.class);
		suite.addTestSuite(CFParseBCELConvertorVisitor4Test.class);
		suite.addTestSuite(CFParseBCELConvertorVisitor5Test.class);
		suite.addTestSuite(CFParseBCELConvertorVisitor6Test.class);
		suite.addTestSuite(CFParseBCELConvertorVisitor7Test.class);

		suite.addTestSuite(MethodInfoListConversionTest.class);
		suite.addTestSuite(ReflectiveInstantiationTest.class);
		suite.addTestSuite(SubtypeLoaderTest.class);

		suite.addTestSuite(ConvertorsComparisonTest.class);

		return suite;
	}
}
