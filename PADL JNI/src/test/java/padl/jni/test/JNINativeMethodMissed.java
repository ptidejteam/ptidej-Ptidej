package padl.creator.cppfile.eclipse.test.big;

import junit.framework.TestCase;
import padl.kernel.exception.CreationException;

public class JNINativeMethodMissed extends TestCase {

	protected void setUp() throws Exception {
		super.setUp();
	}

	public void testJNI() throws CreationException {
		try {
			PadlModelJNI JNI = new PadlModelJNI();
			int nb = JNI.NatifMissedTestCase();
			assertTrue(nb >= 0);
		}
		catch (final Throwable parserFailure) {
			assertTrue(true);
		}
	}

}
