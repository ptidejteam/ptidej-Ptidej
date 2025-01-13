package util.io.test;

import java.io.IOException;

import org.junit.Assert;

import com.ibm.toad.cfparse.ClassFile;
import com.ibm.toad.cfparse.MethodInfo;
import com.ibm.toad.cfparse.MethodInfoList;
import com.ibm.toad.cfparse.attributes.AttrInfoList;
import com.ibm.toad.cfparse.attributes.CodeAttrInfo;
import com.ibm.toad.cfparse.instruction.ImmutableCodeSegment;

import junit.framework.TestCase;
import util.io.SubtypeLoader;

public class SubtypeLoaderTest extends TestCase {
	public void test1() throws IOException {
		final String classFile_Path = "../CPL/src/test/resources/Random ClassFiles/NameDialog.class";
		final ClassFile[] classFiles = SubtypeLoader.loadSubtypeFromFile(
				"java.awt.Dialog", classFile_Path, ".class");

		Assert.assertEquals(1, classFiles.length);
	}

	public void test2() throws IOException {
		final String classFile_Path = "../CPL/src/test/resources/Random ClassFiles/QuadraticHorizentalSolver.class";
		final ClassFile[] classFiles = SubtypeLoader.loadSubtypeFromFile(
				"ptidej.ui.layout.repository.sugiyama.horizentalLayout.HorizentalSolver",
				classFile_Path, ".class");

		Assert.assertEquals(1, classFiles.length);
	}

	public void test3() throws IOException {
		final String classFile_Path = "../CPL/src/test/resources/Random ClassFiles/Constituent.class";
		final ClassFile[] classFiles = SubtypeLoader.loadSubtypeFromFile(
				"padl.kernel.IConstituent", classFile_Path, ".class");
		Assert.assertEquals(1, classFiles.length);

		final MethodInfoList methodInfoList = classFiles[0].getMethods();
		final MethodInfo methodInfo = methodInfoList.get(5);
		final AttrInfoList attributeInfoList = methodInfo.getAttrs();
		final CodeAttrInfo codeAttributeInfo = (CodeAttrInfo) attributeInfoList
				.get("Code");

		final byte[] rawCode = codeAttributeInfo.getCode();
		final ImmutableCodeSegment immutableCodeSegment = new ImmutableCodeSegment(
				rawCode);
		Assert.assertNotNull(immutableCodeSegment);
	}

	public void test4() throws IOException {
		final String classFile_Path = "../CPL/src/test/resources/Random ClassFiles/DefaultEditorKit$InsertBreakAction.class";
		final ClassFile[] classFiles = SubtypeLoader.loadSubtypeFromFile(
				"javax.swing.text.TextAction", classFile_Path, ".class");

		Assert.assertEquals(1, classFiles.length);
	}

	public void test5() throws IOException {
		final String classFile_Path = "../CPL/src/test/resources/Random ClassFiles/Double.class";
		final ClassFile[] classFiles = SubtypeLoader.loadSubtypeFromFile(
				"java.lang.Number", classFile_Path, ".class");

		Assert.assertEquals(1, classFiles.length);
	}
}
