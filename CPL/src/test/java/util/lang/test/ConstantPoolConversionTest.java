package util.lang.test;

import java.io.FileInputStream;
import java.io.IOException;

import org.apache.bcel.classfile.ClassParser;

import com.ibm.toad.cfparse.ClassFile;
import com.ibm.toad.cfparse.ConstantPool;

import junit.framework.TestCase;
import util.lang.CFParseBCELConvertorAdhoc;

/* *
 * @author Peter Yefi
 * @author Vishnu Rameshbabu
 */

public class ConstantPoolConversionTest extends TestCase {
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
		assertEquals(this.classFile_CFParse_Original.getName(),
				this.classFile_CFParse_Converted.getName());

	}

	public void testGetAccess() {
		assertEquals(this.classFile_CFParse_Original.getName(),
				this.classFile_CFParse_Converted.getName());

		assertEquals(
				this.classFile_CFParse_Original.getFields().get(0).toString(),
				this.classFile_CFParse_Converted.getFields().get(0).toString());
	}

	private boolean constantPoolConverter(String constantPoolStringOriginal) {
		if (this.classFile_CFParse_Converted.getCP().toString()
				.indexOf(constantPoolStringOriginal) >= 0) {
			System.out.println(constantPoolStringOriginal + " "
					+ this.classFile_CFParse_Converted.getCP().toString()
							.indexOf(constantPoolStringOriginal));
			return true;
		}
		else {
			System.out.println(constantPoolStringOriginal + " "
					+ this.classFile_CFParse_Converted.getCP().toString()
							.indexOf(constantPoolStringOriginal));
			return false;
		}
	}

	public void testGetCP() {
			assertEquals(this.classFile_CFParse_Original.getCP().length(),this.classFile_CFParse_Converted.getCP().length());
		ConstantPool cpOriginal = this.classFile_CFParse_Original.getCP();
		String[] cpOriginalArray = cpOriginal.toString().trim().split("\n");
		for (String cpOriginalArrayVal : cpOriginalArray) {
			if (!cpOriginalArrayVal.equals("CONSTANT POOL:")) {
				String[] cpOriginalArrayValInnerLoop = cpOriginalArrayVal.trim()
						.split(" ");
				String classType = cpOriginalArrayValInnerLoop[1];
				if (classType.equals("Class:")) {
					assertEquals(this.constantPoolConverter(
							cpOriginalArrayValInnerLoop[3]), true);
				}

				else if (classType.equals("Utf8:")) {
					System.out.println(this.constantPoolConverter(
							cpOriginalArrayValInnerLoop[2]));
					assertEquals(this.constantPoolConverter(
							cpOriginalArrayValInnerLoop[2]), true);
				}
				else if (classType.equals("String:")) {
					System.out.println(this.constantPoolConverter(
							cpOriginalArrayValInnerLoop[3]));
					assertEquals(this.constantPoolConverter(
							cpOriginalArrayValInnerLoop[3]), true);
				}
								else if(classType.equals("Methodref:")) {
									assertEquals(this.constantPoolConverter(cpOriginalArrayValInnerLoop[2]),true);
								}
								else if(classType.equals("InterfaceMethodref:")) {
									assertEquals(this.constantPoolConverter(cpOriginalArrayValInnerLoop[2]),true);
								}
						

			}
		}
	}

	public void testGetAttrs() {
		 assertEquals(this.classFile_CFParse_Original.getAttrs().toString(), this.classFile_CFParse_Converted.getAttrs().toString());
	}

	public void testGetFields() {
		assertEquals(
				this.classFile_CFParse_Original.getFields().get(0).toString(),
				this.classFile_CFParse_Converted.getFields().get(0).toString());
	}

}