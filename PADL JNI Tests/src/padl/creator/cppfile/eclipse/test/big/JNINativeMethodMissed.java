package padl.creator.cppfile.eclipse.test.big;

import junit.framework.TestCase;
import padl.kernel.exception.CreationException;

public class JNINativeMethodMissed extends TestCase {

	protected void setUp() throws Exception {
		super.setUp();
	}

	public void testJNI() throws CreationException {
		PadlModelJNI JNI = new PadlModelJNI();
		int nb = JNI.NatifMissedTestCase();
		assertEquals(3, nb);
	}

}
