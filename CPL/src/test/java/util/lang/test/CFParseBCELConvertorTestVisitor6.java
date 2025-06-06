package util.lang.test;

import java.io.FileInputStream;
import java.io.IOException;

import org.apache.bcel.classfile.ClassParser;
import org.junit.Assert;

import com.ibm.toad.cfparse.ClassFile;

import junit.framework.TestCase;
import util.lang.CFParseBCELConvertorVisitor;

public class CFParseBCELConvertorTestVisitor6 extends TestCase {
	private ClassFile classFile_CFParse_Original;
	private ClassFile classFile_CFParse_Converted;

	@Override
	public void setUp() throws IOException {
		final String classFile_Path = "../CPL/target/test-classes/Random ClassFiles/QuadraticHorizentalSolver.class";

		this.classFile_CFParse_Original = new ClassFile(
				new FileInputStream(classFile_Path));

		this.classFile_CFParse_Converted = CFParseBCELConvertorVisitor
				.convertClassFile(
						new ClassParser(new FileInputStream(classFile_Path), "")
								.parse());
	}

	public void testGetName() {
		Assert.assertEquals(this.classFile_CFParse_Original.getName(),
				this.classFile_CFParse_Converted.getName());
	}

	public void testGetAccess() {
		
		Assert.assertEquals(this.classFile_CFParse_Original.getAccess(),
				this.classFile_CFParse_Converted.getAccess());
	}

	public void testGetAttrs() {
	
		Assert.assertEquals(this.classFile_CFParse_Original.getAttrs().toString(),
				this.classFile_CFParse_Converted.getAttrs().toString());
	}
	
//	public void testFull() {
//		
//		Assert.assertEquals(this.classFile_CFParse_Original.toString(),
//				this.classFile_CFParse_Converted.toString());
//	}
	
	
//	HENRIQUE 4/29/2025 StackTable is not working yet?
//	public void testGetMethods() {
//		
//		
//		int fieldCount = this.classFile_CFParse_Original.getMethods().length();
//		for (int i = 0; i < fieldCount; i++) {
//			Assert.assertEquals(
//					this.classFile_CFParse_Original.getMethods().get(i)
//							.toString(),
//					this.classFile_CFParse_Converted.getMethods().get(i)
//							.toString());
//		}
//		
//	}

	public void testGetFields() {
		Assert.assertEquals(
				this.classFile_CFParse_Original.getFields().get(0).toString(),
				this.classFile_CFParse_Converted.getFields().get(0).toString());
	}
}
