package util.lang.test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.FileInputStream;
import java.io.IOException;

import org.apache.bcel.classfile.ClassParser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.ibm.toad.cfparse.ClassFile;

import util.lang.CFParseBCELConvertor;

class CFParseBCELConvertorTest1 {
	private ClassFile classFile_CFParse_Original;
	private ClassFile classFile_CFParse_Converted;

	@BeforeEach
	void setUp() throws IOException {
		final String classFile_Path = "../CPL/target/test-classes/Random ClassFiles/SimpleGenerator.class";

		this.classFile_CFParse_Original = new ClassFile(
				new FileInputStream(classFile_Path));

		this.classFile_CFParse_Converted = CFParseBCELConvertor
				.convertClassFile(
						new ClassParser(new FileInputStream(classFile_Path), "")
								.parse());
	}

	@Test
	void testGetName() {
		assertEquals(this.classFile_CFParse_Original.getName(),
				this.classFile_CFParse_Converted.getName());

	}

	@Test
	void testGetAccess() {
		assertEquals(this.classFile_CFParse_Original.getName(),
				this.classFile_CFParse_Converted.getName());

		assertEquals(
				this.classFile_CFParse_Original.getFields().get(0).toString(),
				this.classFile_CFParse_Converted.getFields().get(0).toString());
	}

	@Test
	void testGetAttrs() {
		// assertEquals(this.classFile_CFParse_Original.getAttrs(), this.classFile_CFParse_Converted.getAttrs());
	}

	@Test
	void testGetFields() {
		assertEquals(
				this.classFile_CFParse_Original.getFields().get(0).toString(),
				this.classFile_CFParse_Converted.getFields().get(0).toString());
	}

}
