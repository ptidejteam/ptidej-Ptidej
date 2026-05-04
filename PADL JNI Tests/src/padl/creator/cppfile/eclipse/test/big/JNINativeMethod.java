package padl.creator.cppfile.eclipse.test.big;

import junit.framework.TestCase;
import padl.kernel.exception.CreationException;

public class JNINativeMethod extends TestCase {
	protected void setUp() throws Exception {
		super.setUp();
	}

	public void testNativeMethod() throws CreationException {
		try {
			PadlModelJNI JNI = new PadlModelJNI();
			int nb = JNI.NBNatif();
			assertTrue(nb >= 0);
		}
		catch (final Throwable parserFailure) {
			assertTrue(true);
		}
	}
}
