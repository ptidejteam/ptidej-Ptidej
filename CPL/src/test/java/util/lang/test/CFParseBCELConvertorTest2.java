package util.lang.test;

import java.io.FileInputStream;
import java.io.IOException;

import org.apache.bcel.classfile.ClassParser;
import org.junit.Assert;

import com.ibm.toad.cfparse.ClassFile;

import util.lang.CFParseBCELConvertor;

class CFParseBCELConvertorTest2 {
	private ClassFile classFile_CFParse_Original;
	private ClassFile classFile_CFParse_Converted;

	public 
	void setUp() throws IOException {
		final String classFile_Path = "../CPL/target/test-classes/Random ClassFiles/NameDialog.class";

		this.classFile_CFParse_Original = new ClassFile(
				new FileInputStream(classFile_Path));

		this.classFile_CFParse_Converted = CFParseBCELConvertor
				.convertClassFile(
						new ClassParser(new FileInputStream(classFile_Path), "")
								.parse());
	}

	public 
	void testGetAccess() {
		Assert.assertEquals(this.classFile_CFParse_Original.getAccess(),
				this.classFile_CFParse_Converted.getAccess());
	}

	public 
	void testGetAttrs() {
		// Assert.assertEquals(this.classFile_CFParse_Original.getAttrs(), this.classFile_CFParse_Converted.getAttrs());
	}

	public 
	void testGetName() {
		Assert.assertEquals(this.classFile_CFParse_Original.getName(),
				this.classFile_CFParse_Converted.getName());
	}

	public 
	void testGetFields() {
		Assert.assertEquals(
				this.classFile_CFParse_Original.getFields().get(0).toString(),
				this.classFile_CFParse_Converted.getFields().get(0).toString());
	}

}
