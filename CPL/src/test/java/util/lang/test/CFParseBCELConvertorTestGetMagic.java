package util.lang.test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.stream.IntStream;

import org.apache.bcel.classfile.ClassParser;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.ibm.toad.cfparse.ClassFile;
import com.ibm.toad.cfparse.MethodInfo;
import com.ibm.toad.cfparse.MethodInfoList;

import util.lang.CFParseBCELConvertor;


/**
 * @author Luca Scistri
 * @since 2024/10/17
 */
class CFParseBCELConvertorTestGetMagic {
	private ClassFile classFile_CFParse_Original;
	private ClassFile classFile_CFParse_Converted;

	@BeforeEach
	void setUp() throws IOException {
		final String classFile_Path = "../CPL/target/test-classes/Random ClassFiles/NameDialog.class";

		this.classFile_CFParse_Original = new ClassFile(
				new FileInputStream(classFile_Path));

		this.classFile_CFParse_Converted = CFParseBCELConvertor
				.convertClassFile(
						new ClassParser(new FileInputStream(classFile_Path), "")
								.parse());
	}
	
	@Test
	void testGetMagic() {
		assertEquals(
				this.classFile_CFParse_Original.getMagic(),
				this.classFile_CFParse_Converted.getMagic());
	}
	
	@Test
	void testGetMajor() {
		assertEquals(
				this.classFile_CFParse_Original.getMajor(),
				this.classFile_CFParse_Converted.getMajor());
	}
	
	@Test
	void testGetMinor() {
		assertEquals(
				this.classFile_CFParse_Original.getMinor(),
				this.classFile_CFParse_Converted.getMinor());
	}
	
	@Test
	void testGetMethods() {
		
		final int numMethods = this.classFile_CFParse_Original.getMethods().length();
		final MethodInfoList methodInfoList_CFParse_Original = this.classFile_CFParse_Original.getMethods();
		final MethodInfoList methodInfoList_CFParse_Converted = this.classFile_CFParse_Converted.getMethods();
		
		for (int i = 0; i<numMethods; i++) {
			//As long as the methods are parsed in the same order in BCEL and CFParse, this is fine
			final MethodInfo method_CFParse_Original = methodInfoList_CFParse_Original.get(i);
			final MethodInfo method_CFParse_Converted = methodInfoList_CFParse_Converted.get(i);
			
			assertEquals(
					method_CFParse_Original.getAbout(),
					method_CFParse_Converted.getAbout());
			assertEquals(
					method_CFParse_Original.getAccess(),
					method_CFParse_Converted.getAccess());
			assertEquals(
					method_CFParse_Original.getDesc(),
					method_CFParse_Converted.getDesc());
			assertEquals(
					method_CFParse_Original.getName(),
					method_CFParse_Converted.getName());
			assertArrayEquals(
					method_CFParse_Original.getParams(),
					method_CFParse_Converted.getParams());
			assertEquals(
					method_CFParse_Original.getReturnType(),
					method_CFParse_Converted.getReturnType());
			// As long as we are confident that toString is reliable, the test is fine
			assertEquals(
					method_CFParse_Original.getAttrs().toString(),
					method_CFParse_Converted.getAttrs().toString());
			
		}
		
	}
}
