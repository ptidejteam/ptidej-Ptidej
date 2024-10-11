package util.lang.test;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.ibm.toad.cfparse.ClassFile;
import com.ibm.toad.cfparse.ConstantPool;
import com.ibm.toad.cfparse.ConstantPoolEntry;
import com.ibm.toad.cfparse.FieldInfo;
import com.ibm.toad.cfparse.FieldInfoList;

class ReflectiveInstantiation {

	@Test
	void test1()
			throws FileNotFoundException, IOException, NoSuchMethodException,
			SecurityException, InstantiationException, IllegalAccessException,
			IllegalArgumentException, InvocationTargetException {

		final String classFile_Path = "../CPL/target/test-classes/Random ClassFiles/NameDialog.class";

		final ClassFile classFile_CFParse_Original = new ClassFile(
				new FileInputStream(classFile_Path));

		final int numberOfCPEntries = classFile_CFParse_Original.getCP()
				.length();
		System.out.println(numberOfCPEntries);
		for (int i = 0; i < numberOfCPEntries; i++) {
			final ConstantPoolEntry cpEntry = classFile_CFParse_Original.getCP()
					.get(i);
			System.out.println("====== " + i);
			if (cpEntry != null) {
				System.out.println(cpEntry.getAsJava());
				final int[] indices = cpEntry.getIndices();
				for (int j = 0; indices != null && j < indices.length; j++) {
					int k = indices[j];
					System.out.println(k);
				}
			}
			else {
				System.out.println("null?");
			}
		}
		final FieldInfoList fieldInfoList = classFile_CFParse_Original
				.getFields();
		final FieldInfo fieldInfo_CFParse_Original = fieldInfoList.get(0);

		final ConstantPool constantPool = new ConstantPool();

		final Class<FieldInfo> fieldInfoClass = FieldInfo.class;
		final Constructor<FieldInfo> constructor = fieldInfoClass
				.getDeclaredConstructor(ClassFile.class, ConstantPool.class,
						String.class);
		constructor.setAccessible(true);
		// final FieldInfo fieldInfo_CFParse_BCEL = constructor.newInstance(null, null, "");

		// Assertions.assertEquals(fieldInfo_CFParse_Original.getAccess(), fieldInfo_CFParse_BCEL.getAccess());
	}

}
