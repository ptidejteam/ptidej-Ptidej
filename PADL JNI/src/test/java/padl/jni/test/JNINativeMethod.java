package padl.jni.test;

import org.junit.Assert;

import junit.framework.TestCase;
import padl.kernel.exception.CreationException;

public class JNINativeMethod extends TestCase {
	public void testNativeMethod() throws CreationException {
		try {
			final int nb = Helper.getNumberOfNativeMethods();
			Assert.assertTrue(nb > 0);
		}
		catch (final Exception e) {
			Assert.assertTrue(false);
		}
	}
}
