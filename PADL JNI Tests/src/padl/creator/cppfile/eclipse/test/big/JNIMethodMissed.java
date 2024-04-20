package padl.creator.cppfile.eclipse.test.big;

import junit.framework.TestCase;
import padl.kernel.exception.CreationException;

public class JNIMethodMissed extends TestCase {

	protected void setUp() throws Exception {
		super.setUp();
	}

	public void testNativeMethod() throws CreationException {
		PadlModelJNI JNI = new PadlModelJNI();
		int nb = JNI.JNIMissedTestCase();
		assertEquals(0, nb);
	}

}
