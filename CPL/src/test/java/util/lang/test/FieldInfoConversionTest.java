package util.lang.test;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.bcel.classfile.ClassParser;
import org.apache.bcel.classfile.Field;
import org.apache.bcel.classfile.JavaClass;
import org.junit.Assert;

import com.ibm.toad.cfparse.ClassFile;
import com.ibm.toad.cfparse.FieldInfo;
import com.ibm.toad.cfparse.FieldInfoList;

import junit.framework.TestCase;

/*
 * @author Luca Scistri
 * @author Zongo Meyo
 * @since 2024/10/11
 */
public class FieldInfoConversionTest extends TestCase {
	private static FieldInfo field_CFPARSE;
	private static Field field_BCEL;

	public void setUp() throws FileNotFoundException, IOException {
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

	public void testGetAccess() throws FileNotFoundException, IOException {
		Assert.assertEquals(field_CFPARSE.getAccess(),
				field_BCEL.getAccessFlags());
	}

	public void testGetType() throws FileNotFoundException, IOException {
		Assert.assertEquals(field_CFPARSE.getType(),
				field_BCEL.getType().getClassName());
	}

	public void testGetName() throws FileNotFoundException, IOException {
		Assert.assertEquals(field_CFPARSE.getName(), field_BCEL.getName());
	}

	public void testGetDesc() {
		String Description = "J";

		Assert.assertEquals(field_CFPARSE.getDesc(), Description);
	}

	public void testGetAttrsLength() {
		Assert.assertEquals(field_CFPARSE.getAttrs().length(),
				field_BCEL.getAttributes().length);
	}

	public void testGetAttrsName() {
		Assert.assertNotEquals(field_CFPARSE.getAttrs().getName(0),
				field_BCEL.getName());
	}
}
