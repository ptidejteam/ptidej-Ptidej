package padl.creator.msefile.test;

import junit.framework.TestCase;
import padl.creator.msefile.MSECreator;
import padl.creator.msefile.misc.Element;

import java.io.*;
import java.lang.reflect.Method;
import java.lang.reflect.InvocationTargetException;

public class Test1 extends TestCase {

    public void testParseMSEFile() throws IOException, IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        // ✅ Step 1: Get the same directory as this test file
        String testFilePath = "src/test/java/padl/creator/msefile/test/tempTest.mse";
        File testFile = new File(testFilePath);

        // ✅ Step 2: Ensure the parent directory exists
        testFile.getParentFile().mkdirs();

        // ✅ Step 3: Create a temporary MSE file in the same folder as the test
        FileWriter writer = new FileWriter(testFile);
        writer.write("(FAMIX.Class (id: 1) (name 'TestClass'))"); // Simulated valid MSE content
        writer.close();

        // ✅ Step 4: Check if file exists and has content
        System.out.println("File created at: " + testFile.getAbsolutePath());
        System.out.println("File exists: " + testFile.exists());
        System.out.println("File size: " + testFile.length() + " bytes");

        // ✅ Step 5: Create MSECreator instance with the test file
        MSECreator creator = new MSECreator(new String[]{testFilePath});

        // ✅ Step 6: Use reflection to call private parseMSEFile() method
        Method parseMethod = MSECreator.class.getDeclaredMethod("parseMSEFile");
        parseMethod.setAccessible(true); // Bypass private access

        // ✅ Step 7: Invoke parseMSEFile and capture the result
        Element[] elements = (Element[]) parseMethod.invoke(creator);

        // ✅ Step 8: Debugging output
        if (elements == null) {
            System.out.println("❌ Error: elements array is null.");
        } else {
            System.out.println("✅ Parsing successful! Number of elements: " + elements.length);
        }

        // ✅ Step 9: Assertions
        assertNotNull("Parsing should return non-null elements", elements);
        assertTrue("There should be at least one element", elements.length > 0);
    }
}
