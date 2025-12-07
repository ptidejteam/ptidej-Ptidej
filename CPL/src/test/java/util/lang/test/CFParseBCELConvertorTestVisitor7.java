package util.lang.test;

import java.io.FileInputStream;
import java.io.IOException;

import org.apache.bcel.classfile.ClassParser;
import org.junit.Assert;

import com.ibm.toad.cfparse.ClassFile;
import com.ibm.toad.cfparse.FieldInfo;
import com.ibm.toad.cfparse.MethodInfo;

import junit.framework.TestCase;
import util.lang.CFParseBCELConvertorVisitor;

public class CFParseBCELConvertorTestVisitor7 extends TestCase {
	private ClassFile classFile_CFParse_Original;
	private ClassFile classFile_CFParse_Converted;

	@Override
	public void setUp() throws IOException {
		final String classFile_Path = "../CPL/target/test-classes/util/lang/test/FullClass.class";

		this.classFile_CFParse_Original = new ClassFile(
				new FileInputStream(classFile_Path));

		this.classFile_CFParse_Converted = CFParseBCELConvertorVisitor
				.convertClassFile(
						new ClassParser(new FileInputStream(classFile_Path), "")
								.parse());
	}

	public void testGetName() {
		Assert.assertEquals("Class names differ",
				this.classFile_CFParse_Original.getName(),
				this.classFile_CFParse_Converted.getName());
	}

	public void testGetAccess() {
		Assert.assertEquals("Access flags differ",
				this.classFile_CFParse_Original.getAccess(),
				this.classFile_CFParse_Converted.getAccess());
	}

	public void testGetAttrs() {
		Assert.assertEquals("Attribute list differs",
				this.classFile_CFParse_Original.getAttrs().toString(),
				this.classFile_CFParse_Converted.getAttrs().toString());
	}

	public void testGetMethods() {
		final int count = this.classFile_CFParse_Original.getMethods().length();
		Assert.assertEquals("Number of methods differ", count,
				this.classFile_CFParse_Converted.getMethods().length());

		for (int i = 0; i < count; i++) {
			final MethodInfo original = this.classFile_CFParse_Original
					.getMethods().get(i);
			final MethodInfo converted = this.classFile_CFParse_Converted
					.getMethods().get(i);

			Assert.assertEquals("Method differs at index " + i,
					original.toString(), converted.toString());
		}
	}

	public void testGetFields() {
		final int count = this.classFile_CFParse_Original.getFields().length();
		Assert.assertEquals("Number of fields differ", count,
				this.classFile_CFParse_Converted.getFields().length());

		for (int i = 0; i < count; i++) {
			final FieldInfo original = this.classFile_CFParse_Original
					.getFields().get(i);
			final FieldInfo converted = this.classFile_CFParse_Converted
					.getFields().get(i);

			Assert.assertEquals("Field differs at index " + i,
					original.toString(), converted.toString());
		}
	}

	// TODO To re-enable
	// public void testFullToStringComparison() {
	// 	Assert.assertEquals("Full class toString differs",
	// 			this.classFile_CFParse_Original.toString(),
	// 			this.classFile_CFParse_Converted.toString());
	// }
}
