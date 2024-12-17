package util.lang.test;

import com.ibm.toad.cfparse.ClassFile;
import junit.framework.TestCase;
import org.apache.bcel.classfile.ClassParser;
import org.junit.Assert;
import util.lang.CFParseBCELConvertor;

import java.io.FileInputStream;
import java.io.IOException;

public class CFParseBCELConvertorTest4 extends TestCase {
	private ClassFile classFile_CFParse_Original;
	private ClassFile classFile_CFParse_Converted_Visitor;

	public void setUp() throws IOException {
		final String classFile_Path = "../CPL/target/test-classes/Random ClassFiles/NameDialog.class";

		this.classFile_CFParse_Original = new ClassFile(
				new FileInputStream(classFile_Path));
		this.classFile_CFParse_Converted_Visitor = CFParseBCELConvertor
				.convertClassFileUsingVisitor(
						new ClassParser(new FileInputStream(classFile_Path), "")
								.parse());
	}

	public void testGetAccess() {
		Assert.assertEquals(this.classFile_CFParse_Original.getAccess(),
				this.classFile_CFParse_Converted_Visitor.getAccess());
	}

	public void testGetAttrs() {
		//Assert.assertEquals(this.classFile_CFParse_Original.getAttrs(), this.classFile_CFParse_Converted_Visitor.getAttrs());
	}

	public void testGetName() {
		Assert.assertEquals(this.classFile_CFParse_Original.getName(),
				this.classFile_CFParse_Converted_Visitor.getName());
	}

	public void testGetFields() {
		Assert.assertEquals(
				this.classFile_CFParse_Original.getFields().get(0).getName(),
				this.classFile_CFParse_Converted_Visitor.getFields().get(0).getName());
		// TODO Should be this test

//		Assert.assertEquals(
//				this.classFile_CFParse_Original.getFields().get(0).toString(),
//				this.classFile_CFParse_Converted.getFields().get(0).toString());

	}
}
