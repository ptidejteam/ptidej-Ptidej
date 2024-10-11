package util.lang.test;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;
import java.util.stream.IntStream;

import org.apache.bcel.classfile.ClassParser;
import org.apache.bcel.classfile.Field;
import org.apache.bcel.classfile.JavaClass;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import com.ibm.toad.cfparse.ClassFile;
import com.ibm.toad.cfparse.InterfaceList;
/*
 * @author Imen Trabelsi
 * @author Sikandar Ejaz
 * @since 2024/10/11
 */

class InterfaceListConversionTest {

	@Test
	void testLengh() throws FileNotFoundException, IOException, ClassNotFoundException {
		final String classFile_Path = "../CPL/target/test-classes/Random ClassFiles/IClass.class";

		final ClassFile classFile_CFParse_Original = new ClassFile(new FileInputStream(classFile_Path));

		final InterfaceList interfaceList_CFParse = classFile_CFParse_Original.getInterfaces();

		final JavaClass classFile_BCEL = new ClassParser(new FileInputStream(classFile_Path), "").parse();

		final JavaClass[] interfacesList_BCEL = classFile_BCEL.getInterfaces();

		Assertions.assertEquals(interfaceList_CFParse.length(), interfacesList_BCEL.length);

	}

	@Test
	void testGet() throws FileNotFoundException, IOException, ClassNotFoundException {
		final String classFile_Path = "../CPL/target/test-classes/Random ClassFiles/IClass.class";

		final ClassFile classFile_CFParse_Original = new ClassFile(new FileInputStream(classFile_Path));

		final InterfaceList interfaceList_CFParse = classFile_CFParse_Original.getInterfaces();

		final JavaClass classFile_BCEL = new ClassParser(new FileInputStream(classFile_Path), "").parse();

		final JavaClass[] interfacesList_BCEL = classFile_BCEL.getInterfaces();

		for (int i = 0; i < interfaceList_CFParse.length(); i++) {
			String interface_CFParse = interfaceList_CFParse.get(i);
			String interface_BCEL = interfacesList_BCEL[i].getClassName();

			Assertions.assertEquals(interface_CFParse, interface_BCEL, "Interfaces do not match " + i);
		}
	}

	@Test
	void testAdd() throws FileNotFoundException, IOException, ClassNotFoundException {
		final String classFile_Path = "../CPL/target/test-classes/Random ClassFiles/IClass.class";

		final ClassFile classFile_CFParse_Original = new ClassFile(new FileInputStream(classFile_Path));

		final InterfaceList interfaceList_CFParse = classFile_CFParse_Original.getInterfaces();

		final JavaClass classFile_BCEL = new ClassParser(new FileInputStream(classFile_Path), "").parse();

		final JavaClass[] interfacesList_BCEL = classFile_BCEL.getInterfaces();
		int initialNumInterfaces_CFParse = interfaceList_CFParse.length();
		int initialNumInterfaces_BCEL = interfacesList_BCEL.length;

		String newInterface = "com/example/NewInterface";
		interfaceList_CFParse.add(newInterface);

		int updatedNumInterfaces_CFParse = interfaceList_CFParse.length();
		Assertions.assertEquals(initialNumInterfaces_CFParse + 1, updatedNumInterfaces_CFParse);

	}

	@Test
	void testGetName() throws FileNotFoundException, IOException, ClassNotFoundException {
		final String classFile_Path = "../CPL/target/test-classes/Random ClassFiles/IClass.class";

		final ClassFile classFile_CFParse_Original = new ClassFile(new FileInputStream(classFile_Path));

		final InterfaceList interfaceList_CFParse = classFile_CFParse_Original.getInterfaces();

		final JavaClass classFile_BCEL = new ClassParser(new FileInputStream(classFile_Path), "").parse();

		final JavaClass[] interfacesList_BCEL = classFile_BCEL.getInterfaces();

		for (int i = 0; i < interfaceList_CFParse.length(); i++) {
			String interface_CFParse = interfaceList_CFParse.getInterfaceName(i);
			String interface_BCEL = interfacesList_BCEL[i].getClassName();

			Assertions.assertEquals(interface_CFParse, interface_BCEL, "Interfaces do not match " + i);
		}

	}

	@Test
	void testSort() throws FileNotFoundException, IOException, ClassNotFoundException {
		final String classFile_Path = "../CPL/target/test-classes/Random ClassFiles/IClass.class";

		final ClassFile classFile_CFParse_Original = new ClassFile(new FileInputStream(classFile_Path));

		final InterfaceList interfaceList_CFParse = classFile_CFParse_Original.getInterfaces();

		final JavaClass classFile_BCEL = new ClassParser(new FileInputStream(classFile_Path), "").parse();

		final JavaClass[] interfacesList_BCEL = classFile_BCEL.getInterfaces();

		int numInterfaces_CFParse = interfaceList_CFParse.length();

		String[] interfaces_CFParse = IntStream.range(0, numInterfaces_CFParse).mapToObj(interfaceList_CFParse::get)
				.toArray(String[]::new);

		String[] interfaces_BCEL = Arrays.stream(interfacesList_BCEL).map(JavaClass::getClassName)
				.toArray(String[]::new);

		Arrays.sort(interfaces_CFParse);
		
		Arrays.sort(interfaces_BCEL);

		Assertions.assertArrayEquals(interfaces_BCEL, interfaces_CFParse,
				"The sorted interface names from CFParse should match those from BCEL.");

	}

}
