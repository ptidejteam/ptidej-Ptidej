package padl.jni.test;

import org.junit.Assert;

import junit.framework.TestCase;
import padl.kernel.exception.CreationException;

public class JNINativeMethodMissed extends TestCase {
	public void testJNI() throws CreationException {
		try {
			final int nb = Helper.getMissingNativeMethods();
			Assert.assertTrue(nb > 0);
		}
		catch (final Exception e) {
			Assert.assertTrue(false);
		}
	}

}
