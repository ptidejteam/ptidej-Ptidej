package util.lang.test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.FileInputStream;
import java.io.IOException;

import org.apache.bcel.classfile.ClassParser;
import org.apache.bcel.classfile.JavaClass;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.ibm.toad.cfparse.ClassFile;

import util.lang.CFParseBCELConvertor;

class CFParseBCELConvertorTest {

	@BeforeEach
	void setUp() {
		// Nothing to do?
	}

	@Test
	void test() throws IOException {
		final String classFile_Path = "../CPL/target/test-classes/Random ClassFiles/NameDialog.class";

		final ClassFile classFile_CFParse_Original = new ClassFile(
				new FileInputStream(classFile_Path));

		final JavaClass classFile_BCEL = new ClassParser(
				new FileInputStream(classFile_Path), "").parse();

		final ClassFile classFile_CFParse_Convert = CFParseBCELConvertor
				.convertClassFile(classFile_BCEL);

		assertEquals(classFile_CFParse_Original.getAccess(),
				classFile_CFParse_Convert.getAccess());
		// assertEquals(classFile_CFParse_Original.getAttrs(), classFile_CFParse_Convert.getAttrs());
		assertEquals(classFile_CFParse_Original.getName(),
				classFile_CFParse_Convert.getName());

		assertEquals(classFile_CFParse_Original.getFields().get(0).toString(),
				classFile_CFParse_Convert.getFields().get(0).toString());
	}

}
