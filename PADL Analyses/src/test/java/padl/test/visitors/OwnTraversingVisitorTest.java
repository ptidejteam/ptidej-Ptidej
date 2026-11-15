package padl.test.visitors;

import junit.framework.TestCase;
import org.junit.Assert;
import padl.analysis.plantUMLGenerator.PlantUMLGenerator;
import padl.creator.classfile.CompleteClassFileCreator;
import padl.kernel.ICodeLevelModel;
import padl.kernel.IMethod;
import padl.kernel.impl.Factory;
import padl.visitor.OwnTraversingVisitor;

public class OwnTraversingVisitorTest extends TestCase {
	private static final String PATH =
			"../PADL Analyses/target/test-classes/padl/test/visitors/testdata/";
	private ICodeLevelModel codeLevelModel;

	protected void setUp() throws Exception {
		super.setUp();

		this.codeLevelModel = Factory.getInstance()
				.createCodeLevelModel("Model for OwnTraversingVisitorTest");
		this.codeLevelModel.create(new CompleteClassFileCreator(
				new String[] { OwnTraversingVisitorTest.PATH }, true));
	}

	public void testOwnTraversingVisitorVsPlantUMLGenerator() {
		final String expected =
				this.codeLevelModel.generate(new PlantUMLGenerator());
		final String actual = this.codeLevelModel.generate(
				new OwnTraversingVisitor(new PlantUMLGenerator()));
		Assert.assertEquals(expected, actual);
	}

	public static class MethodCountingGenerator extends PlantUMLGenerator {
		public int methodsOpened = 0;

		@Override
		public void open(final IMethod aMethod) {
			this.methodsOpened++;
			super.open(aMethod);
		}
	}

	public void testMethodVisitedCount() {
		final MethodCountingGenerator viaAccept = new MethodCountingGenerator();
		this.codeLevelModel.generate(viaAccept);

		final MethodCountingGenerator viaOwn = new MethodCountingGenerator();
		this.codeLevelModel.generate(new OwnTraversingVisitor(viaOwn));

		Assert.assertEquals(viaAccept.methodsOpened, viaOwn.methodsOpened);
	}
}