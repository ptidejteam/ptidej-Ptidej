package util.lang.test;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

import org.apache.bcel.classfile.ClassParser;
import org.junit.Assert;

import com.ibm.toad.cfparse.ClassFile;

import junit.framework.TestCase;
import util.lang.CFParseBCELConvertorAdhoc;

public class CFParseBCELConvertor7Test extends TestCase {
	private ClassFile classFile_CFParse_Original;
	private ClassFile classFile_CFParse_Converted;

	public void setUp() throws IOException {
		final String classFile_Path = "../CPL/target/test-classes/Random ClassFiles/DefaultEditorKit$InsertBreakAction.class";
		
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

		for (int i = 0; i < originalLen; i++) {
			Assert.assertEquals(
				this.classFile_CFParse_Original.getFields().get(i).toString(),
				this.classFile_CFParse_Converted.getFields().get(i).toString());
		}
	}


}
