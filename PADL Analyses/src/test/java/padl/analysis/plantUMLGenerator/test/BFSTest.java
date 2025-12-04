package padl.analysis.plantUMLGenerator.test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Timestamp;

import org.junit.Assert;
import padl.analysis.plantUMLGenerator.BFSGenerator;
import padl.analysis.plantUMLGenerator.BFSPlantUMLGenerator;
import junit.framework.TestCase;
import padl.analysis.UnsupportedSourceModelException;
import padl.analysis.plantUMLGenerator.PlantUMLGenerator;
import padl.analysis.repository.AACRelationshipsAnalysis;
import padl.creator.classfile.CompleteClassFileCreator;
import padl.kernel.ICodeLevelModel;
import padl.kernel.IIdiomLevelModel;
import padl.kernel.exception.CreationException;
import padl.kernel.impl.Factory;
import util.io.ProxyConsole;

/**
 * @author Amr Abdalla
 * @since 2025/12/04
 */
public class BFSTest extends TestCase {

	private static ICodeLevelModel intendedCodeLevelModel;
	private static IIdiomLevelModel intendedIdiomLevelModel;
	private static String generatedUMLContent;
	private static String testAgainstUMLContent;
	private static String filePath = "../PADL Analyses/target/test-classes/padl/example/composite2/";
	// TODO Use regular temporary file from ProxyDisk
	private static String plantUMLGeneratedFiles = "../PADL Analyses/src/test/java/padl/analysis/plantUMLGenerator/test/exampleFile/composite2_BFS_example_2025-12-4.txt";
	private static String plantUMLGeneratedFileIncorrect1 = "../PADL Analyses/src/test/java/padl/analysis/plantUMLGenerator/test/exampleFile/composite2_BFS_example_2025-12-4.txt";
	private static String testAgainstPlantUMLGeneratedFileIncorrect1;

	public BFSTest(final String testName) {
		super(testName);
	}

	protected void setUp() {
		try {
			BufferedReader readText = new BufferedReader(
					new FileReader(new File(plantUMLGeneratedFiles)));
			StringBuffer textBuilder = new StringBuffer();
			String fileLine;
			while ((fileLine = readText.readLine()) != null) {
				textBuilder.append(fileLine);
				textBuilder.append(System.getProperty("line.separator"));
			}
			if (readText != null)
				readText.close();
			BFSTest.testAgainstUMLContent = textBuilder.toString();
			BFSTest.testAgainstUMLContent = BFSTest.testAgainstUMLContent
					.replace("\r", "").replaceFirst("[\n]+$", "");
			readText = new BufferedReader(
					new FileReader(new File(plantUMLGeneratedFileIncorrect1)));
			textBuilder = new StringBuffer();
			fileLine = new String();
			while ((fileLine = readText.readLine()) != null) {
				textBuilder.append(fileLine);
				textBuilder.append(System.getProperty("line.separator"));
			}
			if (readText != null)
				readText.close();
			BFSTest.testAgainstPlantUMLGeneratedFileIncorrect1 = textBuilder
					.toString();
			BFSTest.testAgainstPlantUMLGeneratedFileIncorrect1 = BFSTest.testAgainstPlantUMLGeneratedFileIncorrect1
					.replace("\r", "").replaceFirst("[\n]+$", "");
			ProxyConsole.getInstance().normalOutput()
					.println("Test against UML Content");
			ProxyConsole.getInstance().normalOutput()
					.println(testAgainstUMLContent);
			BFSTest.intendedCodeLevelModel = Factory.getInstance()
					.createCodeLevelModel("");
			BFSTest.intendedCodeLevelModel.create(new CompleteClassFileCreator(
					new String[] { filePath }, true));
			intendedIdiomLevelModel = (IIdiomLevelModel) new AACRelationshipsAnalysis()
					.invoke(intendedCodeLevelModel);
			BFSPlantUMLGenerator PlantUMLGeneratorNew = new BFSPlantUMLGenerator(new PlantUMLGenerator());
			intendedIdiomLevelModel.generate(PlantUMLGeneratorNew);
			String umlContent = (String) PlantUMLGeneratorNew.getCode();
			String timeStampForUMLTextFile = new Timestamp(
					System.currentTimeMillis()).toString().split(" ")[0];
			System.out.println(timeStampForUMLTextFile);
			BFSTest.generatedUMLContent = umlContent;
			ProxyConsole.getInstance().normalOutput()
					.println("Generated UML Content");
			ProxyConsole.getInstance().normalOutput()
					.println(BFSTest.generatedUMLContent);
		}
		catch (CreationException e) {
			e.printStackTrace();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		catch (UnsupportedSourceModelException e) {
			e.printStackTrace();
		}

	}

	public void testGeneratedUMLContent() {
		Assert.assertEquals(BFSTest.testAgainstUMLContent, generatedUMLContent);
		Assert.assertNotEquals(BFSTest.testAgainstUMLContent,
				(generatedUMLContent + "test"));
	}

	public void testGeneratedUMLContentIncorrect() {
		Assert.assertNotEquals(BFSTest.testAgainstPlantUMLGeneratedFileIncorrect1,
				(generatedUMLContent + "test"));
	}

}
