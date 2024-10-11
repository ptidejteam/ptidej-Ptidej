package util.lang.test;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.bcel.classfile.ClassParser;
import org.apache.bcel.classfile.Field;
import org.apache.bcel.classfile.JavaClass;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.ibm.toad.cfparse.ClassFile;
import com.ibm.toad.cfparse.FieldInfo;
import com.ibm.toad.cfparse.FieldInfoList;

class FieldInfoConversionTest {

	@Test
	void test1() throws FileNotFoundException, IOException {
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

		Assertions.assertEquals(field_CFPARSE.getType(),
				field_BCEL.getType().getClassName());
	}

	@Test
	void test2() throws FileNotFoundException, IOException {
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
	}

}
