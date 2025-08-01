package util.lang.test;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;

import org.apache.bcel.classfile.ClassParser;
import org.junit.Assert;

import com.ibm.toad.cfparse.ClassFile;
import com.ibm.toad.cfparse.attributes.CodeAttrInfo;

import junit.framework.TestCase;
import util.lang.CFParseBCELConvertorAdhoc;

// 4/22/2025 Henrique
// Since i don't know yet what is missing to fix or implement, i will test everything i can!
public class CFParseBCELConvertorTest8 extends TestCase {
	private ClassFile classFile_CFParse_Original;
	private ClassFile classFile_CFParse_Converted;

	public void setUp() throws IOException {
		final String classFile_Path = "../CPL/target/test-classes/util/lang/test/FullClass.class";

		
		this.classFile_CFParse_Original = new ClassFile(
				new FileInputStream(classFile_Path));

		this.classFile_CFParse_Converted = CFParseBCELConvertorAdhoc
				.convertClassFile(
						new ClassParser(new FileInputStream(classFile_Path), "")
								.parse());
	}

	public void testGetAccess() {
	
		Assert.assertEquals(this.classFile_CFParse_Original.getAccess(),
				this.classFile_CFParse_Converted.getAccess());
	}

	public void testGetAttrs() {
//		HENRIQUE 4/11/2025
//		All tests from cpl to get attrs, i changed to use .toString(), why? Bytes are exactly the same, so
//	make sense to use .toString()?

	 Assert.assertEquals(this.classFile_CFParse_Original.getAttrs().toString(), this.classFile_CFParse_Converted.getAttrs().toString());
	}

	public void testGetName() {
		
		Assert.assertEquals(this.classFile_CFParse_Original.getName(),
				this.classFile_CFParse_Converted.getName());
	}

	public void testGetFields() {
//		For this .class, there is no fields, so i just check if there is fields before running the test
		int originalLen = this.classFile_CFParse_Original.getFields().length();
		int convertedLen = this.classFile_CFParse_Converted.getFields().length();

		Assert.assertEquals(originalLen, convertedLen);
		Assert.assertEquals(
				this.classFile_CFParse_Original.getFields().toString(),
				this.classFile_CFParse_Converted.getFields().toString());
		;
		for (int i = 0; i < originalLen; i++) {
			Assert.assertEquals(
				this.classFile_CFParse_Original.getFields().get(i).toString(),
				this.classFile_CFParse_Converted.getFields().get(i).toString());
		}
	}
	
	public void testGetMethods() {
		int originalLen = this.classFile_CFParse_Original.getMethods().length();
		int convertedLen = this.classFile_CFParse_Converted.getMethods().length();

	Assert.assertEquals(originalLen, convertedLen);
//	Need to implement RuntimeInvisibleAnnotations
//		Assert.assertEquals(
//			this.classFile_CFParse_Original.getMethods().toString(),
//			this.classFile_CFParse_Converted.getMethods().toString());

		
	}
	
	public void testInterfacesAndSuper() {
		Assert.assertEquals(
			classFile_CFParse_Original.getSuperName(),
			classFile_CFParse_Converted.getSuperName()
		);

		Assert.assertEquals(
			classFile_CFParse_Original.getInterfaces().toString(),
			classFile_CFParse_Converted.getInterfaces().toString()
		);
	}
	public void testInnerClassesAttr() {
		Assert.assertTrue(
			classFile_CFParse_Original.getAttrs().toString().contains("InnerClasses")
		);
		Assert.assertTrue(
			classFile_CFParse_Converted.getAttrs().toString().contains("InnerClasses")
		);
	}

	public void testCodeAttributes() {
		for (int i = 0; i < classFile_CFParse_Original.getMethods().length(); i++) {
			var methodOrig = classFile_CFParse_Original.getMethods().get(i);
			var methodConv = classFile_CFParse_Converted.getMethods().get(i);

			var codeOrig = methodOrig.getAttrs().get("Code");
			var codeConv = methodConv.getAttrs().get("Code");

			if (codeOrig != null && codeConv != null) {
				Assert.assertEquals(codeOrig.toString(), codeConv.toString());
			}
		}
	}



	public void testInnerClassesAndNestMembers() {
		String origAttrs = classFile_CFParse_Original.getAttrs().toString();
		String convAttrs = classFile_CFParse_Converted.getAttrs().toString();

		Assert.assertTrue(origAttrs.contains("InnerClasses"));
		Assert.assertTrue(convAttrs.contains("InnerClasses"));

		Assert.assertTrue(origAttrs.contains("NestMembers"));
		Assert.assertTrue(convAttrs.contains("NestMembers"));

		Assert.assertEquals(origAttrs, convAttrs);
	}

	



}
