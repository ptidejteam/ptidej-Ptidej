package padl.creator.cppfile.eclipse.test.big;

import junit.framework.TestCase;
import padl.kernel.exception.CreationException;

public class TestCaseJniMethodJNIMissed extends TestCase {

	protected void setUp() throws Exception {
		super.setUp();
	}

	public void testNatif() throws CreationException {
		PadlModelJNI JNI = new PadlModelJNI();
		int nb = JNI.JNIMissedTestCase();
		assertEquals(0, nb);
	}

}
