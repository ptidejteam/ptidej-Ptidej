package util.lang.test;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.bcel.classfile.ClassParser;
import org.apache.bcel.classfile.Field;
import org.apache.bcel.classfile.JavaClass;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.ibm.toad.cfparse.ClassFile;
import com.ibm.toad.cfparse.FieldInfo;
import com.ibm.toad.cfparse.FieldInfoList;

/*
 * @author Luca Scistri
 * @author Zongo Meyo
 * @since 2024/10/11
 */

class FieldInfoConversionTest {

	private static FieldInfo field_CFPARSE;
	private static Field field_BCEL;
	
	@BeforeAll
	static void setup() throws FileNotFoundException, IOException {
		final String classFile_Path = "../CPL/target/test-classes/Random ClassFiles/NameDialog.class";

		final ClassFile classFile_CFParse_Original = new ClassFile(
				new FileInputStream(classFile_Path));
		final FieldInfoList fieldInfoList = classFile_CFParse_Original
				.getFields();
		field_CFPARSE = fieldInfoList.get(0);

		final JavaClass classFile_BCEL = new ClassParser(
				new FileInputStream(classFile_Path), "").parse();
	
		field_BCEL = classFile_BCEL.getFields()[0];
	}

	
	@Test
	void testGetAccess() throws FileNotFoundException, IOException {
		Assertions.assertEquals(field_CFPARSE.getAccess(),
				field_BCEL.getAccessFlags());
	}
	
	@Test
	void testGetType() throws FileNotFoundException, IOException {
		Assertions.assertEquals(field_CFPARSE.getType(),
				field_BCEL.getType().getClassName());
	}
	
	@Test
	void testGetName() throws FileNotFoundException, IOException {	
		Assertions.assertEquals(field_CFPARSE.getName(),
				field_BCEL.getName());
	}

	
	@Test
	void testGetDesc(){
		String Description = "J";
		
		Assertions.assertEquals(field_CFPARSE.getDesc(),
				Description);
	}

	
	@Test
	void testGetAttrsLength(){
		Assertions.assertEquals(field_CFPARSE.getAttrs().length(),
				field_BCEL.getAttributes().length);
	}
	
	@Test
	void testGetAttrsName() {
		Assertions.assertNotEquals(field_CFPARSE.getAttrs().getName(0),
				field_BCEL.getName());
	}

}
