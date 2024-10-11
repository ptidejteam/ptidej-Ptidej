package util.lang.test;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.bcel.classfile.ClassParser;
import org.apache.bcel.classfile.JavaClass;
import org.apache.bcel.classfile.Method;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.ibm.toad.cfparse.ClassFile;
import com.ibm.toad.cfparse.MethodInfo;
import com.ibm.toad.cfparse.MethodInfoList;

/**
 * @author Laurent Voisard
 * @author Henrique De Freitas Serra
 * @since  2024/10/11
 */
class MethodInfoConversionTest {

	private static MethodInfo method_CFPARSE;
	private static Method method_BCEL;

	@BeforeAll
	static void setup() throws FileNotFoundException, IOException {
		// MUST NOT USE OUTSIDE FILES, WHY?
		// final String classFile_Path = "../PADL Analyses\\target\\test-classes\\padl\\creator\\classfile\\test\\visitor\\SimpleGenerator.class";
        final String classFile_Path = "../CPL/target/test-classes/Random ClassFiles/SimpleGenerator.class";
		final ClassFile classFile_CFParse_Original = new ClassFile(
				new FileInputStream(classFile_Path));
		final MethodInfoList methodInfoList = classFile_CFParse_Original
				.getMethods();
		method_CFPARSE = methodInfoList.get(0);

		JavaClass classFile_BCEL = new ClassParser(
				new FileInputStream(classFile_Path), "").parse();

		method_BCEL = classFile_BCEL.getMethods()[0];

	}

	@Test
	void testGetAccess() throws FileNotFoundException, IOException {

		// access modifier
		Assertions.assertEquals(method_CFPARSE.getAccess(),
				method_BCEL.getAccessFlags());

	}

	@Test
	void testGetAbout() throws FileNotFoundException, IOException {

		// get about

		int max_stacks = method_BCEL.getCode().getMaxStack();
		int max_locals = method_BCEL.getCode().getMaxLocals();
		int bytes = method_BCEL.getCode().getCode().length;
		String BCELGetAbout = String.format(
				"max_stack: %d max_locals: %d #bytes %d", max_stacks,
				max_locals, bytes);

		Assertions.assertEquals(method_CFPARSE.getAbout(), BCELGetAbout);

	}

	@Test
	void testGetAttributes() throws FileNotFoundException, IOException {

		// need attributes
		// Assertions.assertEquals(method_CFPARSE.getAttrs(),
		// method_BCEL.getAttributes());

	}

	@Test
	void testGetDesc() throws FileNotFoundException, IOException {

		// get Desc

		Assertions.assertEquals(method_CFPARSE.getDesc(),
				method_BCEL.getSignature());

	}

	@Test
	void testGetName() throws FileNotFoundException, IOException {

		Assertions.assertEquals(method_CFPARSE.getName(),
				method_BCEL.getName());

	}

	@Test
	void testGetParams() throws FileNotFoundException, IOException {

		Assertions.assertEquals(method_CFPARSE.getParams().length,
				method_BCEL.getArgumentTypes().length);
		for (int i = 0; i < method_CFPARSE.getParams().length; i++) {

			Assertions.assertEquals(method_CFPARSE.getParams()[i],
					method_BCEL.getArgumentTypes()[i].toString());
		}

	}

	@Test
	void testGetReturnType() throws FileNotFoundException, IOException {

		Assertions.assertEquals(method_CFPARSE.getReturnType(),
				method_BCEL.getReturnType().toString());

	}

}