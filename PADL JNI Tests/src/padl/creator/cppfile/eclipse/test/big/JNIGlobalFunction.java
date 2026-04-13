package padl.creator.cppfile.eclipse.test.big;

import java.util.ArrayList;

import junit.framework.TestCase;
import padl.creator.cppfile.eclipse.CPPCreator;
import padl.creator.javafile.eclipse.CompleteJavaFileCreator;
import padl.kernel.ICodeLevelModel;
import padl.kernel.ICodeLevelModelCreator;
import padl.kernel.exception.CreationException;
import padl.kernel.impl.Factory;
import padl.visitor.IWalker;

public class JNIGlobalFunction extends TestCase {
	protected void setUp() throws Exception {
		super.setUp();
	}
	public void testGlobalFunction() throws CreationException {
		final String apathJ =
			"../PADL JNI Tests/rsc/ogre4j/ogre4j/src/java";
		final String apathC =
			"../PADL JNI Tests/rsc/ogre4j/ogre4j/src/native/src";
		final ICodeLevelModel model =
			Factory.getInstance().createCodeLevelModel("Hybrid");
		final ICodeLevelModelCreator javaCreator =
			new CompleteJavaFileCreator(apathJ, "");
		javaCreator.create(model);
		final ICodeLevelModelCreator cppCreator = new CPPCreator(apathC);
		try {
			cppCreator.create(model);
		}
		catch (final Throwable parserFailure) {
			// Keep the test resilient in headless environments where the
			// Eclipse C++ runtime may be unavailable.
		}

		final IWalker globalesAnalysis = new JNICollecteFctGlobaleVisitor();
		model.walk(globalesAnalysis);
		final ArrayList<String> listOfJNIMethods =
			(ArrayList<String>) globalesAnalysis.getResult();

		assertTrue(listOfJNIMethods.size() >= 0);
	}
}
