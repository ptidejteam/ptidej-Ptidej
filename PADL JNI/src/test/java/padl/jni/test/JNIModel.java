package padl.jni.test;

import org.junit.Assert;

import junit.framework.TestCase;
import padl.kernel.ICodeLevelModel;
import padl.kernel.exception.CreationException;

public class JNIModel extends TestCase {
	public void testModel() throws CreationException {
		try {
			final ICodeLevelModel model = Helper.createModel();
			Assert.assertTrue(model.getNumberOfConstituents() > 0);
		}
		catch (final Exception e) {
			Assert.assertTrue(false);
		}
	}
}
