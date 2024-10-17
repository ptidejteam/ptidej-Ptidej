package util.lang.test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.FileInputStream;
import java.io.IOException;

import org.apache.bcel.classfile.ClassParser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.ibm.toad.cfparse.ClassFile;
import com.ibm.toad.cfparse.ConstantPool;

import util.lang.CFParseBCELConverter_CP;

/* *
 * @author Peter Yefi
 * @author Vishnu Rameshbabu
 */


public class CFParseBCELConverterTest_CP {
	private ClassFile classFile_CFParse_Original;
	private ClassFile classFile_CFParse_Converted;

	@BeforeEach
	void setUp() throws IOException {
		final String classFile_Path = "../CPL/target/test-classes/Random ClassFiles/SimpleGenerator.class";

		this.classFile_CFParse_Original = new ClassFile(
				new FileInputStream(classFile_Path));

		this.classFile_CFParse_Converted = CFParseBCELConverter_CP
				.convertClassFile(
						new ClassParser(new FileInputStream(classFile_Path), "")
								.parse());
	}

	@Test
	void testGetName() {
		assertEquals(this.classFile_CFParse_Original.getName(),
				this.classFile_CFParse_Converted.getName());

	}

	@Test
	void testGetAccess() {
		assertEquals(this.classFile_CFParse_Original.getName(),
				this.classFile_CFParse_Converted.getName());

		assertEquals(
				this.classFile_CFParse_Original.getFields().get(0).toString(),
				this.classFile_CFParse_Converted.getFields().get(0).toString());
	} 
	
	boolean constantPoolConverter(String constantPoolStringOriginal ) {
		if(this.classFile_CFParse_Converted.getCP().toString().indexOf(constantPoolStringOriginal) >= 0) {
			System.out.println(constantPoolStringOriginal + " " +this.classFile_CFParse_Converted.getCP().toString().indexOf(constantPoolStringOriginal));
			return true;
		}
		else {
			System.out.println(constantPoolStringOriginal + " " + this.classFile_CFParse_Converted.getCP().toString().indexOf(constantPoolStringOriginal));
			return false;
		}
	}
	@Test
	void testGetCP(){
	//	assertEquals(this.classFile_CFParse_Original.getCP().length(),this.classFile_CFParse_Converted.getCP().length());
		ConstantPool cpOriginal = this.classFile_CFParse_Original.getCP();
		int lengthOriginal = 1;
		String[] cpOriginalArray = cpOriginal.toString().trim().split("\n");
		for(String cpOriginalArrayVal : cpOriginalArray) {
			if(!cpOriginalArrayVal.equals("CONSTANT POOL:")) {
				String[] cpOriginalArrayValInnerLoop = cpOriginalArrayVal.trim().split(" ");
				String classType = cpOriginalArrayValInnerLoop[1];
				if(classType.equals("Class:")) {
					assertEquals(this.constantPoolConverter(cpOriginalArrayValInnerLoop[3]),true);
				}
				
				else if(classType.equals("Utf8:")) {
				System.out.println(this.constantPoolConverter(cpOriginalArrayValInnerLoop[2]));
				assertEquals(this.constantPoolConverter(cpOriginalArrayValInnerLoop[2]),true);
				}
				else if(classType.equals("String:")) {
				System.out.println(this.constantPoolConverter(cpOriginalArrayValInnerLoop[3]));
				assertEquals(this.constantPoolConverter(cpOriginalArrayValInnerLoop[3]),true);
				}
//				else if(classType.equals("Methodref:")) {
//					assertEquals(this.constantPoolConverter(cpOriginalArrayValInnerLoop[2]),true);
//				}
//				else if(classType.equals("InterfaceMethodref:")) {
//					assertEquals(this.constantPoolConverter(cpOriginalArrayValInnerLoop[2]),true);
//				}
//			}
			
			
		}
		
		
		
	}}
	@Test
	void testGetAttrs() {
		// assertEquals(this.classFile_CFParse_Original.getAttrs(), this.classFile_CFParse_Converted.getAttrs());
	}

	@Test
	void testGetFields() {
		assertEquals(
				this.classFile_CFParse_Original.getFields().get(0).toString(),
				this.classFile_CFParse_Converted.getFields().get(0).toString());
	}

}
