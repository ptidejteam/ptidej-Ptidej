package util.lang.test;

import java.io.IOException;

import org.junit.Assert;

import com.ibm.toad.cfparse.ClassFile;

import junit.framework.TestCase;
import util.io.SubtypeLoader;

public class SubtypeLoaderTest extends TestCase {
	public void test() throws IOException {
		// final String classFile_Path = "../CPL/target/test-classes/Random ClassFiles/";
		final String classFile_Path = "../CPL/src/test/resources/Random ClassFiles/NameDialog.class";
		final ClassFile[] classFiles = SubtypeLoader.loadSubtypeFromFile(
				"java.awt.Dialog", classFile_Path, ".class");

		Assert.assertEquals(1, classFiles.length);
	}
}
