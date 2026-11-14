package padl.test.visitors;

import junit.framework.TestCase;
import org.junit.Assert;
import padl.analysis.plantUMLGenerator.PlantUMLGenerator;
import padl.creator.classfile.CompleteClassFileCreator;
import padl.kernel.ICodeLevelModel;
import padl.kernel.impl.Factory;

public class PlantUMLGeneratorVisitorTest extends TestCase {
	private static final String PATH =
			"../PADL Analyses/target/test-classes/padl/test/visitors/testdata/";
	private ICodeLevelModel codeLevelModel;
	private String plantUML;

	protected void setUp() throws Exception {
		super.setUp();

		this.codeLevelModel = Factory.getInstance()
				.createCodeLevelModel("Model for PlantUMLGeneratorVisitorTest");
		this.codeLevelModel.create(new CompleteClassFileCreator(
				new String[] { PlantUMLGeneratorVisitorTest.PATH }, true));

		final PlantUMLGenerator generator = new PlantUMLGenerator();
		this.plantUML = codeLevelModel.generate(generator);
	}

	public void testWellFormed() {
		Assert.assertTrue(this.plantUML.trim().startsWith("@startuml"));
		Assert.assertTrue(this.plantUML.trim().endsWith("@enduml"));
	}

	public void testEntitiesVisited() {
		Assert.assertTrue(this.plantUML.contains("interface Test0"));
		Assert.assertTrue(this.plantUML.contains("abstract class Test1"));
		Assert.assertTrue(this.plantUML.contains("class Test2"));
	}

	public void testRelationshipsGenerated() {
		Assert.assertTrue(this.plantUML.contains("Test1 --^ Test2"));
		Assert.assertTrue(this.plantUML.contains("Test0 ..^ Test2"));
	}
}