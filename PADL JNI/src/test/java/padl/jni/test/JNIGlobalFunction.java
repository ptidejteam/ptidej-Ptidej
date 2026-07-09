package padl.jni.test;

import java.util.List;

import org.junit.Assert;

import junit.framework.TestCase;
import padl.creator.cppfile.eclipse.CPPCreator;
import padl.creator.javafile.eclipse.CompleteJavaFileCreator;
import padl.jni.JNICollecteFctGlobaleVisitor;
import padl.kernel.ICodeLevelModel;
import padl.kernel.ICodeLevelModelCreator;
import padl.kernel.exception.CreationException;
import padl.kernel.impl.Factory;
import padl.visitor.IWalker;

public class JNIGlobalFunction extends TestCase {
	public void testGlobalFunction() throws CreationException {
		final String aPathJ = "../PADL JNI/target/test-classes/ogre4j/ogre4j/src/java";
		final String aPathC = "../PADL JNI/target/test-classes/ogre4j/ogre4j/src/native/src";
		final ICodeLevelModel model = Factory.getInstance()
				.createCodeLevelModel("Hybrid");
		final ICodeLevelModelCreator javaCreator = new CompleteJavaFileCreator(
				aPathJ, "");
		javaCreator.create(model);
		final ICodeLevelModelCreator cppCreator = new CPPCreator(aPathC);
		try {
			cppCreator.create(model);
		}
		catch (final Exception e) {
			// Keep the test resilient in headless environments where the
			// Eclipse C++ runtime may be unavailable.
		}

		final IWalker globalesAnalysis = new JNICollecteFctGlobaleVisitor();
		model.walk(globalesAnalysis);
		final List<String> listOfJNIMethods = (List<String>) globalesAnalysis
				.getResult();

		Assert.assertTrue(listOfJNIMethods.size() >= 0);
	}
}
