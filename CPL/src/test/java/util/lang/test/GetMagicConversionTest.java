package util.lang.test;

import java.io.FileInputStream;
import java.io.IOException;

import org.apache.bcel.classfile.ClassParser;
import org.junit.Assert;

import com.ibm.toad.cfparse.ClassFile;
import com.ibm.toad.cfparse.MethodInfo;
import com.ibm.toad.cfparse.MethodInfoList;

import junit.framework.TestCase;
import util.lang.CFParseBCELConvertorAdhoc;

/**
 * @author Luca Scistri
 * @since 2024/10/17
 */
public class GetMagicConversionTest extends TestCase {
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

	public void testGetMagic() {
		Assert.assertEquals(this.classFile_CFParse_Original.getMagic(),
				this.classFile_CFParse_Converted.getMagic());
	}

	public void testGetMajor() {
		Assert.assertEquals(this.classFile_CFParse_Original.getMajor(),
				this.classFile_CFParse_Converted.getMajor());
	}

	public void testGetMinor() {
		Assert.assertEquals(this.classFile_CFParse_Original.getMinor(),
				this.classFile_CFParse_Converted.getMinor());
	}

	public void testGetMethods() {
		final int numMethods = this.classFile_CFParse_Original.getMethods()
				.length();
		final MethodInfoList methodInfoList_CFParse_Original = this.classFile_CFParse_Original
				.getMethods();
		final MethodInfoList methodInfoList_CFParse_Converted = this.classFile_CFParse_Converted
				.getMethods();

		for (int i = 0; i < numMethods; i++) {
			// As long as the methods are parsed in the same order in BCEL and CFParse, this is fine
			final MethodInfo method_CFParse_Original = methodInfoList_CFParse_Original
					.get(i);
			final MethodInfo method_CFParse_Converted = methodInfoList_CFParse_Converted
					.get(i);

			Assert.assertEquals(method_CFParse_Original.getAbout(),
					method_CFParse_Converted.getAbout());
			Assert.assertEquals(method_CFParse_Original.getAccess(),
					method_CFParse_Converted.getAccess());
			Assert.assertEquals(method_CFParse_Original.getDesc(),
					method_CFParse_Converted.getDesc());
			Assert.assertEquals(method_CFParse_Original.getName(),
					method_CFParse_Converted.getName());
			Assert.assertArrayEquals(method_CFParse_Original.getParams(),
					method_CFParse_Converted.getParams());
			Assert.assertEquals(method_CFParse_Original.getReturnType(),
					method_CFParse_Converted.getReturnType());

			 Assert.assertEquals(method_CFParse_Original.getAttrs().toString(), method_CFParse_Converted.getAttrs().toString());
		}
	}
}
