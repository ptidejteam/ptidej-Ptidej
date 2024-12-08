package util.lang.test;

import java.io.FileInputStream;
import java.io.IOException;

import org.apache.bcel.classfile.ClassParser;
import org.junit.Assert;

import com.ibm.toad.cfparse.ClassFile;

import junit.framework.TestCase;
import util.lang.CFParseBCELConvertor;

/**
 * @since 2024/10/11
 * @author Rushin Dipak Makwana
 * @author Nicolas C. Rousse
 */
public class GetMethodsConversionTest extends TestCase {
	private ClassFile classFile_CFParse_Original;
	private ClassFile classFile_CFParse_Converted;

	public void setUp() throws IOException {
		final String classFile_Path = "../CPL/target/test-classes/Random ClassFiles/NameDialog.class";

		this.classFile_CFParse_Original = new ClassFile(
				new FileInputStream(classFile_Path));

		this.classFile_CFParse_Converted = CFParseBCELConvertor
				.convertClassFile(
						new ClassParser(new FileInputStream(classFile_Path), "")
								.parse());
	}

	public void testGetAccess() {
		Assert.assertEquals(this.classFile_CFParse_Original.getAccess(),
				this.classFile_CFParse_Converted.getAccess());
	}

	public void testGetAttrs() {
		// assertEquals(this.classFile_CFParse_Original.getAttrs(), this.classFile_CFParse_Converted.getAttrs());
	}

	public void testGetName() {
		Assert.assertEquals(this.classFile_CFParse_Original.getName(),
				this.classFile_CFParse_Converted.getName());
	}

	public void testGetFields() {
		// TODO Should be toString() instead of getName()
		Assert.assertEquals(
				this.classFile_CFParse_Original.getFields().get(0).getName(),
				this.classFile_CFParse_Converted.getFields().get(0).getName());
	}

	public void testGetDesc() {
		int fieldCount = this.classFile_CFParse_Original.getFields().length();
		for (int i = 0; i < fieldCount; i++) {
			Assert.assertEquals(
					this.classFile_CFParse_Original.getFields().get(i)
							.getDesc(),
					this.classFile_CFParse_Converted.getFields().get(i)
							.getDesc());
		}
	}

	public void testGetMethods() {
		// TODO Add this test back after having fixed the converter
		/*
		int fieldCount = this.classFile_CFParse_Original.getMethods().length();
		for (int i = 0; i < fieldCount; i++) {
			Assert.assertEquals(
					this.classFile_CFParse_Original.getMethods().get(i)
							.toString(),
					this.classFile_CFParse_Converted.getMethods().get(i)
							.toString());
		}
		*/
	}
}