package padl.creator.javafile.javac.test.simple;

import org.junit.Assert;

import junit.framework.TestCase;
import padl.creator.javafile.javac.JavaFileCreator;
import padl.kernel.ICodeLevelModel;
import padl.kernel.IFirstClassEntity;
import padl.kernel.IMethod;
import padl.kernel.impl.Factory;

public class PrimitiveParameterTest extends TestCase {
	private static ICodeLevelModel CodeLevelModel;
	private static final String PATH = "../PADL Creator JavaFile (JavaC)/target/test-classes/";

	public void setUp() throws Exception {
		if (PrimitiveParameterTest.CodeLevelModel == null) {
			PrimitiveParameterTest.CodeLevelModel = Factory.getInstance()
					.createCodeLevelModel("");
			PrimitiveParameterTest.CodeLevelModel.create(new JavaFileCreator(
					PrimitiveParameterTest.PATH,
					new String[] {
							PrimitiveParameterTest.PATH + "AbstractHandle.java",
							PrimitiveParameterTest.PATH + "Drawing.java",
							PrimitiveParameterTest.PATH + "DrawingView.java",
							PrimitiveParameterTest.PATH + "Figure.java" }));
		}
	}

	public void testAbstractHandle() {
		final IFirstClassEntity testClass = PrimitiveParameterTest.CodeLevelModel
				.getTopLevelEntityFromID("javacTestCase.AbstractHandle");
		Assert.assertNotNull("Class AbstractHandle was not found", testClass);
		Assert.assertEquals(10,
				testClass.getNumberOfConstituents(IMethod.class));
		final IMethod invokeEnd = (IMethod) testClass.getConstituentFromID(
				"invokeEnd(int, int, javacTestCase.Drawing)");
		Assert.assertNotNull(invokeEnd);
	}

}
