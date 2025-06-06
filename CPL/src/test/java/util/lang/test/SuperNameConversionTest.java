package util.lang.test;

import java.io.FileInputStream;
import java.io.IOException;

import org.apache.bcel.classfile.ClassParser;
import org.junit.Assert;

import com.ibm.toad.cfparse.ClassFile;

import junit.framework.TestCase;
import util.lang.CFParseBCELConvertorAdhoc;

public class SuperNameConversionTest extends TestCase {
	private ClassFile classFile_CFParse_Original;
	private ClassFile classFile_CFParse_Converted;

	public void setUp() throws IOException {
		final String classFile_Path = "../CPL/target/test-classes/Random ClassFiles/NameDialog.class";

		this.classFile_CFParse_Original = new ClassFile(
				new FileInputStream(classFile_Path));

		this.classFile_CFParse_Converted = CFParseBCELConvertorAdhoc
				.convertClassFile(
						new ClassParser(new FileInputStream(classFile_Path), "")
								.parse());
	}

	public void testGetAccess() {
		assertEquals(this.classFile_CFParse_Original.getAccess(),
				this.classFile_CFParse_Converted.getAccess());
	}

	public void testGetAttrs() {
		 Assert.assertEquals(this.classFile_CFParse_Original.getAttrs().toString(), this.classFile_CFParse_Converted.getAttrs().toString());
	}

	public void testGetSuperName() {
		assertEquals(this.classFile_CFParse_Original.getSuperName(),
				this.classFile_CFParse_Converted.getSuperName());
	}

	public void testGetSourceFileName() {
	
		
		assertEquals(this.classFile_CFParse_Original.getSourceFilename(),
				this.classFile_CFParse_Converted.getSourceFilename());
		
	}

	public void testGetName() {
		assertEquals(this.classFile_CFParse_Original.getName(),
				this.classFile_CFParse_Converted.getName());
	}

	public void testGetFields() {
		assertEquals(
				this.classFile_CFParse_Original.getFields().get(0).toString(),
				this.classFile_CFParse_Converted.getFields().get(0).toString());
	}

}