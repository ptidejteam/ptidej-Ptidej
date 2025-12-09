package padl.analysis.plantUMLGenerator.test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Timestamp;

import org.junit.Assert;

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
 * @author Vishnu Rameshbabu
 * @since 2024/05/10
 */
public class SanityTest extends TestCase {

	private static ICodeLevelModel intendedCodeLevelModel;
	private static IIdiomLevelModel intendedIdiomLevelModel;
	private static String generatedUMLContent;
	private static String testAgainstUMLContent;
	private static String filePath = "../PADL Analyses/target/test-classes/padl/example/composite2/";
	// TODO Use regular temporary file from ProxyDisk
	private static String plantUMLGeneratedFiles = "../PADL Analyses/src/test/java/padl/analysis/plantUMLGenerator/test/exampleFile/composite2_example_2024-05-07.txt";
	private static String plantUMLGeneratedFileIncorrect1 = "../PADL Analyses/src/test/java/padl/analysis/plantUMLGenerator/test/exampleFile/composite2_example_2024-05-07.txt";
	private static String testAgainstPlantUMLGeneratedFileIncorrect1;

	public SanityTest(final String testName) {
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
			SanityTest.testAgainstUMLContent = textBuilder.toString();
			SanityTest.testAgainstUMLContent = SanityTest.testAgainstUMLContent
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
			SanityTest.testAgainstPlantUMLGeneratedFileIncorrect1 = textBuilder
					.toString();
			SanityTest.testAgainstPlantUMLGeneratedFileIncorrect1 = SanityTest.testAgainstPlantUMLGeneratedFileIncorrect1
					.replace("\r", "").replaceFirst("[\n]+$", "");
			ProxyConsole.getInstance().normalOutput()
					.println("Test against UML Content");
			ProxyConsole.getInstance().normalOutput()
					.println(testAgainstUMLContent);
			SanityTest.intendedCodeLevelModel = Factory.getInstance()
					.createCodeLevelModel("");
			SanityTest.intendedCodeLevelModel.create(new CompleteClassFileCreator(
					new String[] { filePath }, true));
			intendedIdiomLevelModel = (IIdiomLevelModel) new AACRelationshipsAnalysis()
					.invoke(intendedCodeLevelModel);
			PlantUMLGenerator PlantUMLGeneratorNew = new PlantUMLGenerator();
			intendedIdiomLevelModel.generate(PlantUMLGeneratorNew);
			String umlContent = (String) PlantUMLGeneratorNew.getCode();
			String timeStampForUMLTextFile = new Timestamp(
					System.currentTimeMillis()).toString().split(" ")[0];
			System.out.println(timeStampForUMLTextFile);
			SanityTest.generatedUMLContent = umlContent;
			ProxyConsole.getInstance().normalOutput()
					.println("Generated UML Content");
			ProxyConsole.getInstance().normalOutput()
					.println(SanityTest.generatedUMLContent);
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
		Assert.assertEquals(SanityTest.testAgainstUMLContent, generatedUMLContent);
		Assert.assertNotEquals(SanityTest.testAgainstUMLContent,
				(generatedUMLContent + "test"));
	}

	public void testGeneratedUMLContentIncorrect() {
		Assert.assertNotEquals(SanityTest.testAgainstPlantUMLGeneratedFileIncorrect1,
				(generatedUMLContent + "test"));
	}

}
