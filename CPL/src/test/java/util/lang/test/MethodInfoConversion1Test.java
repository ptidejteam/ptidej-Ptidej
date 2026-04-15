package util.lang.test;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;

import org.apache.bcel.classfile.ClassParser;
import org.apache.bcel.classfile.JavaClass;
import org.apache.bcel.classfile.Method;
import org.junit.Assert;

import com.ibm.toad.cfparse.ClassFile;
import com.ibm.toad.cfparse.MethodInfo;
import com.ibm.toad.cfparse.MethodInfoList;

import junit.framework.TestCase;
import util.BCELUtils;

/**
 * @author Laurent Voisard
 * @author Henrique De Freitas Serra
 * @since  2024/10/11
 */
public class MethodInfoConversion1Test extends TestCase {
	private static MethodInfo method_CFPARSE;
	private static Method method_BCEL;

	public void setUp() throws FileNotFoundException, IOException {
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

	public void testGetAccess() throws FileNotFoundException, IOException {
		// access modifier
		Assert.assertEquals(method_CFPARSE.getAccess(),
				method_BCEL.getAccessFlags());
	}

	public void testGetAbout() throws FileNotFoundException, IOException {
		// get about
		int max_stacks = method_BCEL.getCode().getMaxStack();
		int max_locals = method_BCEL.getCode().getMaxLocals();
		int bytes = method_BCEL.getCode().getCode().length;
		String BCELGetAbout = String.format(
				"max_stack: %d max_locals: %d #bytes %d", max_stacks,
				max_locals, bytes);

		Assert.assertEquals(method_CFPARSE.getAbout(), BCELGetAbout);
	}

	public void testGetAttributes() {
	    String expected = method_CFPARSE.getAttrs().toString().trim();
	    String actual = BCELUtils.formatMethodAttributes(method_BCEL).trim();

	    Assert.assertEquals(expected, actual);
	}


	public void testGetDesc() throws FileNotFoundException, IOException {
		// get Desc

		Assert.assertEquals(method_CFPARSE.getDesc(),
				method_BCEL.getSignature());
	}

	public void testGetName() throws FileNotFoundException, IOException {
		Assert.assertEquals(method_CFPARSE.getName(), method_BCEL.getName());
	}

	public void testGetParams() throws FileNotFoundException, IOException {
		Assert.assertEquals(method_CFPARSE.getParams().length,
				method_BCEL.getArgumentTypes().length);
		for (int i = 0; i < method_CFPARSE.getParams().length; i++) {

			Assert.assertEquals(method_CFPARSE.getParams()[i],
					method_BCEL.getArgumentTypes()[i].toString());
		}
	}

	public void testGetReturnType() throws FileNotFoundException, IOException {
		Assert.assertEquals(method_CFPARSE.getReturnType(),
				method_BCEL.getReturnType().toString());
	}
}