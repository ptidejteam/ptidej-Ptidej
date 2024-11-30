package util.lang.test;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import org.junit.Assert;

import com.ibm.toad.cfparse.ClassFile;
import com.ibm.toad.cfparse.ConstantPool;
import com.ibm.toad.cfparse.FieldInfo;
import com.ibm.toad.cfparse.FieldInfoList;

import junit.framework.TestCase;

public class ReflectiveInstantiation extends TestCase {
	public void test1()
			throws FileNotFoundException, IOException, NoSuchMethodException,
			SecurityException, InstantiationException, IllegalAccessException,
			IllegalArgumentException, InvocationTargetException {

		final String classFile_Path = "../CPL/target/test-classes/Random ClassFiles/NameDialog.class";

		final ClassFile classFile_CFParse_Original = new ClassFile(
				new FileInputStream(classFile_Path));

		final FieldInfoList fieldInfoList_CFParse_Original = classFile_CFParse_Original
				.getFields();
		final FieldInfo fieldInfo_CFParse_Original = fieldInfoList_CFParse_Original
				.get(0);

		final ClassFile classFile_CFParse_BCEL = new ClassFile();
		final ConstantPool constantPool_CFParse_BCEL = classFile_CFParse_BCEL
				.getCP();

		final Class<FieldInfo> fieldInfoClass = FieldInfo.class;
		final Constructor<FieldInfo> constructor = fieldInfoClass
				.getDeclaredConstructor(ClassFile.class, ConstantPool.class,
						String.class);
		constructor.setAccessible(true);
		final FieldInfo fieldInfo_CFParse_BCEL = constructor.newInstance(
				classFile_CFParse_BCEL, constantPool_CFParse_BCEL,
				"private static final long serialVersionUID = 3685715927255933454");

		Assert.assertEquals(fieldInfo_CFParse_Original.getAccess(),
				fieldInfo_CFParse_BCEL.getAccess());
		Assert.assertEquals(fieldInfo_CFParse_Original.getDesc(),
				fieldInfo_CFParse_BCEL.getDesc());
		Assert.assertEquals(fieldInfo_CFParse_Original.getName(),
				fieldInfo_CFParse_BCEL.getName());
		Assert.assertEquals(fieldInfo_CFParse_Original.getType(),
				fieldInfo_CFParse_BCEL.getType());
		Assert.assertEquals(fieldInfo_CFParse_Original.getAttrs().length(),
				fieldInfo_CFParse_BCEL.getAttrs().length());
		Assert.assertEquals(
				fieldInfo_CFParse_Original.getAttrs().get(0).getName(),
				fieldInfo_CFParse_BCEL.getAttrs().get(0).getName());
	}

}
