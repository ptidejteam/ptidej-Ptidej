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

public class CFParseBCELConvertorTest1 extends TestCase {
	private ClassFile classFile_CFParse_Original;
	private ClassFile classFile_CFParse_Converted;

	public void setUp() throws IOException {
		final String classFile_Path = "../CPL/target/test-classes/Random ClassFiles/SimpleGenerator.class";

		this.classFile_CFParse_Original = new ClassFile(
				new FileInputStream(classFile_Path));

		this.classFile_CFParse_Converted = CFParseBCELConvertorAdhoc
				.convertClassFile(
						new ClassParser(new FileInputStream(classFile_Path), "")
								.parse());
	}

	public void testGetName() {
		Assert.assertEquals(this.classFile_CFParse_Original.getName(),
				this.classFile_CFParse_Converted.getName());

	}

	public void testGetAccess() {
		Assert.assertEquals(this.classFile_CFParse_Original.getAccess(),
				this.classFile_CFParse_Converted.getAccess());
	}

	public void testGetAttrs() {
	
// The bytes are exactly the same - Henrique 4/10/2025

		 Assert.assertEquals(this.classFile_CFParse_Original.getAttrs().toString(), this.classFile_CFParse_Converted.getAttrs().toString());
	}

	public void testGetFields() {
		Assert.assertEquals(
				this.classFile_CFParse_Original.getFields().get(0).toString(),
				this.classFile_CFParse_Converted.getFields().get(0).toString());
	}

}