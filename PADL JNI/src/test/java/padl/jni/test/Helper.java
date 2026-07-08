package padl.jni.test;

import java.util.ArrayList;
//import junit.framework.TestCase;
import java.util.Iterator;

import padl.creator.cppfile.eclipse.CPPCreator;
import padl.creator.javafile.eclipse.CompleteJavaFileCreator;
import padl.jni.JNICollecteFctGlobaleVisitor;
import padl.jni.JNICollecteNativeVisitor;
import padl.kernel.ICodeLevelModel;
import padl.kernel.ICodeLevelModelCreator;
import padl.kernel.exception.CreationException;
import padl.kernel.impl.Factory;
import padl.visitor.IWalker;

public class Helper {
	// This method is used in the Test Case and it allow us to return the Model
	// Hybrid which contain the constituents
	// for Java files and C++ files.
	public static ICodeLevelModel createModel() throws CreationException {
		final String aPathJ = "../PADL JNI/target/test-classes/ogre4j/ogre4j/src/java";
		final String aPathC = "../PADL JNI/target/test-classes/ogre4j/src/native/src";
		final ICodeLevelModel hybrid = Factory.getInstance()
				.createCodeLevelModel("Hybrid");
		final ICodeLevelModelCreator javaCreator = new CompleteJavaFileCreator(
				aPathJ, "");
		javaCreator.create(hybrid);
		final ICodeLevelModelCreator cppCreator = new CPPCreator(aPathC);
		try {
			cppCreator.create(hybrid);
		}
		catch (final Exception e) {
			// Keep the JNI analysis helpers usable even when the Eclipse C++
			// parser runtime is not fully available in headless mode.
		}
		return (hybrid);
	}

	// a Method used for the test case to count the number of JNI methods exist
	// in C++ files that they don't have
	// a native declaration in java files. (the opposite direction of
	// NatifMissedTestCase).
	public static int getMissingJNIFunctions() throws CreationException {
		ICodeLevelModel hybrid = Helper.createModel();
		final IWalker nativeAnalysis = new JNICollecteNativeVisitor();
		hybrid.walk(nativeAnalysis);
		final ArrayList<String> listOfNativeMethods = (ArrayList<String>) nativeAnalysis
				.getResult();
		final IWalker globalesAnalysis = new JNICollecteFctGlobaleVisitor();
		hybrid.walk(globalesAnalysis);
		final ArrayList<String> listOfJNIMethods = (ArrayList<String>) globalesAnalysis
				.getResult();
		final ArrayList<String> NatifIntersect = new ArrayList<String>();
		Iterator it = listOfJNIMethods.iterator();
		while (it.hasNext()) {
			Object o = it.next();
			if (!listOfNativeMethods.contains(o)) {
				NatifIntersect.add((String) o);
			}
		}

		return NatifIntersect.size();
	}

	// The testCases
	// The same method as 'NatifMissed' but here we use it for our test case to
	// count the number of natives methods
	// that don't have a JNI implementation in a c++ files.
	public static int getMissingNativeMethods() throws CreationException {
		ICodeLevelModel hybrid = Helper.createModel();
		final IWalker nativeAnalysis = new JNICollecteNativeVisitor();
		hybrid.walk(nativeAnalysis);
		final ArrayList<String> listOfNativeMethods = (ArrayList<String>) nativeAnalysis
				.getResult();
		final IWalker globalesAnalysis = new JNICollecteFctGlobaleVisitor();
		hybrid.walk(globalesAnalysis);
		final ArrayList<String> listOfJNIMethods = (ArrayList<String>) globalesAnalysis
				.getResult();
		final ArrayList<String> JniNatifIntersect = new ArrayList<String>();
		Iterator it = listOfNativeMethods.iterator();
		while (it.hasNext()) {
			Object o = it.next();
			if (!listOfJNIMethods.contains(o)) {
				JniNatifIntersect.add((String) o);
			}
		}

		return JniNatifIntersect.size();
	}

	// Method used in the test case and which give the number of natives methods
	// existing in java files.
	public static int getNumberOfNativeMethods() throws CreationException {
		ICodeLevelModel model = Helper.createModel();
		final IWalker nativeAnalysis = new JNICollecteNativeVisitor();
		model.walk(nativeAnalysis);
		final ArrayList<String> listOfNativeMethods = (ArrayList<String>) nativeAnalysis
				.getResult();
		return listOfNativeMethods.size();
	}
}
