package padl.analysis.plantUMLGenerator.test;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
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

/**
 * @author Vishnu Rameshbabu
 * @since 2024/05/10
 */
public class Test1 extends TestCase {

    private static ICodeLevelModel intendedCodeLevelModel;
    private static IIdiomLevelModel intendedIdiomLevelModel;
    private static String generatedUMLContent;
    private static String testAgainstUMLContent;
    private static String filePath = "../DeMIMA/target/test-classes/ptidej/example/composite2/";
    private static String plantUMLGeneratedFiles = "../PADL Analyses/src/test/java/padl/analysis/plantUMLGenerator/test/exampleFile/composite2_example_2024-05-07.txt";
    private static String plantUMLGeneratedFileIncorrect1 = "../PADL Analyses/src/test/java/padl/analysis/plantUMLGenerator/test/exampleFile/composite2_example_2024-05-07.txt";
    private static String testAgainstPlantUMLGeneratedFileIncorrect1;

    public Test1(final String testName) {
        super(testName);
    }

    protected void setUp() {
        try {
            Test1.testAgainstUMLContent = readAllLines(plantUMLGeneratedFiles);//new String (Files.readAllBytes(Paths.get(plantUMLGeneratedFiles)));
            Test1.testAgainstPlantUMLGeneratedFileIncorrect1 = readAllLines(plantUMLGeneratedFileIncorrect1);
            System.out.println("Test against UML Content");
            System.out.println(testAgainstUMLContent);
            Test1.intendedCodeLevelModel = Factory.getInstance().createCodeLevelModel("");
            Test1.intendedCodeLevelModel.create(new CompleteClassFileCreator(new String[]{filePath}, true));
            intendedIdiomLevelModel = (IIdiomLevelModel) new AACRelationshipsAnalysis().invoke(intendedCodeLevelModel);
            PlantUMLGenerator PlantUMLGeneratorNew = new PlantUMLGenerator();
            intendedIdiomLevelModel.generate(PlantUMLGeneratorNew);
            String umlContent = (String) PlantUMLGeneratorNew.getCode();
            String timeStampForUMLTextFile = new Timestamp(System.currentTimeMillis()).toString().split(" ")[0];
            System.out.println(timeStampForUMLTextFile);
            Test1.generatedUMLContent = umlContent;
            System.out.println("Generated UML Content");
            System.out.println(Test1.generatedUMLContent);
        } catch (CreationException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (UnsupportedSourceModelException e) {
            e.printStackTrace();
        }

    }

    public void testGeneratedUMLContent() {
        Assert.assertEquals(Test1.testAgainstUMLContent, generatedUMLContent);
        Assert.assertNotEquals(Test1.testAgainstUMLContent, (generatedUMLContent + "test"));
    }

    public void testGeneratedUMLContentIncorrect() {
        Assert.assertNotEquals(Test1.testAgainstPlantUMLGeneratedFileIncorrect1, (generatedUMLContent + "test"));
    }

    public String readAllLines(String path) throws IOException {
        StringBuffer fileContents = new StringBuffer();

        BufferedReader reader = null;

        reader = new BufferedReader(new FileReader(path));
        String line;
        boolean firstLine = false;

        // Read the file line by line
        while ((line = reader.readLine()) != null) {
            if (firstLine) {
                fileContents.append(System.getProperty("line.separator"));
            }
            fileContents.append(line);
            firstLine = true;

        }

        // Convert the StringBuilder to a String
        return fileContents.toString();
    }
}
