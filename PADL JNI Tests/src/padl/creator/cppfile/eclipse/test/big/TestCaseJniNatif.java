package padl.creator.cppfile.eclipse.test.big;

import junit.framework.TestCase;
import padl.kernel.exception.CreationException;

public class TestCaseJniNatif extends TestCase {
	protected void setUp() throws Exception {
		super.setUp();
	}

	public void testNatif() throws CreationException {
		PadlModelJNI JNI = new PadlModelJNI();
		int nb = JNI.NBNatif();
		assertEquals(15, nb);
	}
}
