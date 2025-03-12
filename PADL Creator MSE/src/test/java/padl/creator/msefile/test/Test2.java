package padl.creator.msefile.test;

import junit.framework.TestCase;
import padl.creator.msefile.MSECreator;

import java.lang.reflect.Method;

public class Test2 extends TestCase {

    public void test() throws Exception {
        // ✅ Step 1: Prepare input text with double single-quotes
        StringBuffer buffer = new StringBuffer("This is a ''test'' string.");

        // ✅ Step 2: Create MSECreator instance (filename doesn't matter for this test)
        MSECreator creator = new MSECreator(new String[]{"dummy.mse"});

        // ✅ Step 3: Use reflection to access private `replace()` method
        Method replaceMethod = MSECreator.class.getDeclaredMethod("replace", StringBuffer.class, String.class, String.class);
        replaceMethod.setAccessible(true); // Bypass private access

        // ✅ Step 4: Invoke the replace method
        replaceMethod.invoke(creator, buffer, "''", "\"");

        // ✅ Step 5: Assertions
        assertEquals("This is a \"test\" string.", buffer.toString());
    }
}
