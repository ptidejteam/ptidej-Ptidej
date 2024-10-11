package util.lang.test;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.bcel.classfile.ClassParser;
import org.apache.bcel.classfile.Field;
import org.apache.bcel.classfile.JavaClass;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.ibm.toad.cfparse.ClassFile;
import com.ibm.toad.cfparse.FieldInfo;
import com.ibm.toad.cfparse.FieldInfoList;

import util.lang.CFParseBCELConvertor;

class FieldInfoConversionTest {

	FieldInfo field_CFPARSE;
	FieldInfo field_BCEL_Converted;
	
	@BeforeEach
	void setup() throws FileNotFoundException, IOException {
		final String classFile_Path = "../CPL/target/test-classes/Random ClassFiles/NameDialog.class";

		final ClassFile classFile_CFParse_Original = new ClassFile(
				new FileInputStream(classFile_Path));
		final FieldInfoList fieldInfoList = classFile_CFParse_Original
				.getFields();
		field_CFPARSE = fieldInfoList.get(0);

		final JavaClass classFile_BCEL = new ClassParser(
				new FileInputStream(classFile_Path), "").parse();
		
		final ClassFile classFile_BCEL_Converted = CFParseBCELConvertor.convertClassFile(classFile_BCEL);
		
		final FieldInfoList fieldInfoList_BCEL = classFile_BCEL_Converted.getFields();
		field_BCEL_Converted = fieldInfoList_BCEL.get(0);
	}

	
	@Test
	void testGetAccess() throws FileNotFoundException, IOException {
		final String classFile_Path = "../CPL/target/test-classes/Random ClassFiles/NameDialog.class";

		final ClassFile classFile_CFParse_Original = new ClassFile(
				new FileInputStream(classFile_Path));
		final FieldInfoList fieldInfoList = classFile_CFParse_Original
				.getFields();
		final FieldInfo field_CFPARSE = fieldInfoList.get(0);

		final JavaClass classFile_BCEL = new ClassParser(
				new FileInputStream(classFile_Path), "").parse();
		
		final Field field_BCEL = classFile_BCEL.getFields()[0];

		Assertions.assertEquals(field_CFPARSE.getAccess(),
				field_BCEL.getAccessFlags());

	}
	
	@Test
	void testGetType() throws FileNotFoundException, IOException {
		
		final String classFile_Path = "../CPL/target/test-classes/Random ClassFiles/NameDialog.class";

		final ClassFile classFile_CFParse_Original = new ClassFile(
				new FileInputStream(classFile_Path));
		final FieldInfoList fieldInfoList = classFile_CFParse_Original
				.getFields();
		final FieldInfo field_CFPARSE = fieldInfoList.get(0);

		final JavaClass classFile_BCEL = new ClassParser(
				new FileInputStream(classFile_Path), "").parse();
		
		final Field field_BCEL = classFile_BCEL.getFields()[0];
		
		Assertions.assertEquals(field_CFPARSE.getType(),
				field_BCEL.getType().getClassName());
	}
	
	@Test
	void testToString(){
		

		Assertions.assertEquals(field_CFPARSE.toString(),
				field_BCEL_Converted.toString());
		
	}
	
	@Test
	void testGetName() throws FileNotFoundException, IOException {
		
		final String classFile_Path = "../CPL/target/test-classes/Random ClassFiles/NameDialog.class";

		final ClassFile classFile_CFParse_Original = new ClassFile(
				new FileInputStream(classFile_Path));
		final FieldInfoList fieldInfoList = classFile_CFParse_Original
				.getFields();
		final FieldInfo field_CFPARSE = fieldInfoList.get(0);

		final JavaClass classFile_BCEL = new ClassParser(
				new FileInputStream(classFile_Path), "").parse();
		
		final Field field_BCEL = classFile_BCEL.getFields()[0];		
		
		Assertions.assertEquals(field_CFPARSE.getName(),
				field_BCEL.getName());
	}

	
	@Test
	void testGetDesc(){
		
		Assertions.assertEquals(field_CFPARSE.getDesc(),
				field_BCEL_Converted.getDesc());
	}

	
	@Test
	void testGetAttrsToString(){
		
		Assertions.assertEquals(field_CFPARSE.getAttrs().toString(),
				field_BCEL_Converted.getAttrs().toString());
	}
	
	@Test
	void testGetAttrsSize() {
		
		Assertions.assertNotEquals(field_CFPARSE.getAttrs().size(),
				field_BCEL_Converted.getAttrs().size());
	}

	/*
	@Test
	void test3() throws FileNotFoundException, IOException {
		final String classFile_Path = "../CPL/target/test-classes/Random ClassFiles/NameDialog.class";

		final ClassFile classFile_CFParse_Original = new ClassFile(
				new FileInputStream(classFile_Path));
		final FieldInfoList fieldInfoList = classFile_CFParse_Original
				.getFields();
		final FieldInfo fieldInfo_CFPARSE_ORIGINAL = fieldInfoList.get(0);

		final FieldInfo fieldInfo_BCEL_CONVERTED = new FieldInfo(aaa, bbb, ccc, ddd...);
		
		Assertions.assertEquals(fieldInfo_CFPARSE_ORIGINAL.getAccess(), fieldInfo_BCEL_CONVERTED.getAccess());
		Assertions.assertEquals(fieldInfo_CFPARSE_ORIGINAL.getAccess(), fieldInfo_BCEL_CONVERTED.getAccess());
		Assertions.assertEquals(fieldInfo_CFPARSE_ORIGINAL.getAccess(), fieldInfo_BCEL_CONVERTED.getAccess());
		Assertions.assertEquals(fieldInfo_CFPARSE_ORIGINAL.getAccess(), fieldInfo_BCEL_CONVERTED.getAccess());
		Assertions.assertEquals(fieldInfo_CFPARSE_ORIGINAL.getAccess(), fieldInfo_BCEL_CONVERTED.getAccess());
		Assertions.assertEquals(fieldInfo_CFPARSE_ORIGINAL.getAccess(), fieldInfo_BCEL_CONVERTED.getAccess());
	} */

}
