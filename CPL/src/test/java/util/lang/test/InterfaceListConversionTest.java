package util.lang.test;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;

import org.apache.bcel.classfile.ClassParser;
import org.apache.bcel.classfile.JavaClass;
import org.junit.Assert;

import com.ibm.toad.cfparse.ClassFile;
import com.ibm.toad.cfparse.InterfaceList;
/*
 * @author Imen Trabelsi
 * @author Sikandar Ejaz
 * @since 2024/10/11
 */

class InterfaceListConversionTest {

	public void testLengh()
			throws FileNotFoundException, IOException, ClassNotFoundException {
		final String classFile_Path = "../CPL/target/test-classes/Random ClassFiles/NameDialog.class";

		final ClassFile classFile_CFParse_Original = new ClassFile(
				new FileInputStream(classFile_Path));

		final InterfaceList interfaceList_CFParse = classFile_CFParse_Original
				.getInterfaces();

		final JavaClass classFile_BCEL = new ClassParser(
				new FileInputStream(classFile_Path), "").parse();

		final JavaClass[] interfacesList_BCEL = classFile_BCEL.getInterfaces();

		Assert.assertEquals(interfaceList_CFParse.length(),
				interfacesList_BCEL.length);

	}

	public void testGet()
			throws FileNotFoundException, IOException, ClassNotFoundException {
		final String classFile_Path = "../CPL/target/test-classes/Random ClassFiles/NameDialog.class";

		final ClassFile classFile_CFParse_Original = new ClassFile(
				new FileInputStream(classFile_Path));

		final InterfaceList interfaceList_CFParse = classFile_CFParse_Original
				.getInterfaces();

		final JavaClass classFile_BCEL = new ClassParser(
				new FileInputStream(classFile_Path), "").parse();

		final JavaClass[] interfacesList_BCEL = classFile_BCEL.getInterfaces();

		for (int i = 0; i < interfaceList_CFParse.length(); i++) {
			String interface_CFParse = interfaceList_CFParse.get(i);
			String interface_BCEL = interfacesList_BCEL[i].getClassName();

			Assert.assertEquals(interface_CFParse, interface_BCEL,
					"Interfaces do not match " + i);
		}
	}

	public void testAdd()
			throws FileNotFoundException, IOException, ClassNotFoundException {
		final String classFile_Path = "../CPL/target/test-classes/Random ClassFiles/NameDialog.class";

		final ClassFile classFile_CFParse_Original = new ClassFile(
				new FileInputStream(classFile_Path));

		final InterfaceList interfaceList_CFParse = classFile_CFParse_Original
				.getInterfaces();

		final JavaClass classFile_BCEL = new ClassParser(
				new FileInputStream(classFile_Path), "").parse();

		final JavaClass[] interfacesList_BCEL = classFile_BCEL.getInterfaces();
		int initialNumInterfaces_CFParse = interfaceList_CFParse.length();
		int initialNumInterfaces_BCEL = interfacesList_BCEL.length;

		String newInterface = "com/example/NewInterface";
		interfaceList_CFParse.add(newInterface);

		int updatedNumInterfaces_CFParse = interfaceList_CFParse.length();
		Assert.assertEquals(initialNumInterfaces_CFParse + 1,
				updatedNumInterfaces_CFParse);

	}

	public void testGetName()
			throws FileNotFoundException, IOException, ClassNotFoundException {
		final String classFile_Path = "../CPL/target/test-classes/Random ClassFiles/NameDialog.class";

		final ClassFile classFile_CFParse_Original = new ClassFile(
				new FileInputStream(classFile_Path));

		final InterfaceList interfaceList_CFParse = classFile_CFParse_Original
				.getInterfaces();

		final JavaClass classFile_BCEL = new ClassParser(
				new FileInputStream(classFile_Path), "").parse();

		final JavaClass[] interfacesList_BCEL = classFile_BCEL.getInterfaces();

		for (int i = 0; i < interfaceList_CFParse.length(); i++) {
			String interface_CFParse = interfaceList_CFParse
					.getInterfaceName(i);
			String interface_BCEL = interfacesList_BCEL[i].getClassName();

			Assert.assertEquals(interface_CFParse, interface_BCEL,
					"Interfaces do not match " + i);
		}

	}

	public void testSort()
			throws FileNotFoundException, IOException, ClassNotFoundException {

		final String classFile_Path = "../CPL/target/test-classes/Random ClassFiles/NameDialog.class";

		final ClassFile classFile_CFParse_Original = new ClassFile(
				new FileInputStream(classFile_Path));
		final InterfaceList interfaceList_CFParse = classFile_CFParse_Original
				.getInterfaces();
		final int numInterfaces_CFParse = interfaceList_CFParse.length();
		final String[] interfaces_CFParse = new String[numInterfaces_CFParse];
		for (int i = 0; i < numInterfaces_CFParse; i++) {
			final String interfaceName = interfaceList_CFParse.get(i);
			interfaces_CFParse[i] = interfaceName;
		}
		Arrays.sort(interfaces_CFParse);

		final JavaClass classFile_BCEL = new ClassParser(
				new FileInputStream(classFile_Path), "").parse();
		final JavaClass[] interfacesList_BCEL = classFile_BCEL.getInterfaces();
		final int numInterfaces_BCEL = interfacesList_BCEL.length;
		final String[] interfaces_BCEL = new String[numInterfaces_BCEL];
		for (int i = 0; i < numInterfaces_BCEL; i++) {
			final String interfaceName = interfacesList_BCEL[i].getClassName();
			interfaces_BCEL[i] = interfaceName;
		}
		Arrays.sort(interfaces_BCEL);

		Assert.assertArrayEquals(
				"The sorted interface names from CFParse should match those from BCEL.",
				interfaces_BCEL, interfaces_CFParse);
	}

}
