package padl.jni.helper;

import java.util.ArrayList;
//import junit.framework.TestCase;
import java.util.Iterator;
import java.util.List;

import padl.creator.cppfile.eclipse.CPPCreator;
import padl.creator.javafile.eclipse.CompleteJavaFileCreator;
import padl.jni.JNICollecteFctGlobaleVisitor;
import padl.jni.JNICollecteNativeVisitor;
import padl.kernel.ICodeLevelModel;
import padl.kernel.ICodeLevelModelCreator;
import padl.kernel.exception.CreationException;
import padl.kernel.impl.Factory;
import padl.visitor.IWalker;

public class PADLModelJNICreator {
	// Method that check if all the natives methods have an implementation in
	// C++ files, in others words for each
	// native methods we check if there is a JNI implementation in a c++ files.
	public static List<String> getMissingNativeMethods(
			final List<String> listNativeMethods,
			final List<String> listJNIFunctions) {

		final List<String> intersection = new ArrayList<String>();
		final Iterator it = listNativeMethods.iterator();
		while (it.hasNext()) {
			final Object o = it.next();
			if (!listJNIFunctions.contains(o)) {
				intersection.add((String) o);
			}
		}

		return intersection;
	}

	// The opposite direction of NatifMissed.
	public static List<String> getMissingJNIFunctions(
			final List<String> listNativeMethods,
			final List<String> listJNIFunctions) {

		final List<String> intersection = new ArrayList<String>();
		final Iterator it = listJNIFunctions.iterator();
		while (it.hasNext()) {
			final Object o = it.next();
			if (!listNativeMethods.contains(o)) {
				intersection.add((String) o);
			}
		}

		return intersection;
	}

	public static void main(String[] args) throws CreationException {
		final String apathJ = "../PADL JNI Tests/rsc/ogre4j/ogre4j/src/java";
		// Faut compiler les fichiers.java 
		final String apathC = "../PADL JNI Tests/rsc/ogre4j/ogre4j/src/native/src";

		final ICodeLevelModel hybrid = Factory.getInstance()
				.createCodeLevelModel("Hybrid");
		final ICodeLevelModelCreator javaCreator = new CompleteJavaFileCreator(
				apathJ, "");
		javaCreator.create(hybrid);
		final ICodeLevelModelCreator cppCreator = new CPPCreator(apathC);
		cppCreator.create(hybrid);

		System.out.println(
				"******************List Of Natives Methods*************************");
		final IWalker nativeAnalysis = new JNICollecteNativeVisitor();
		hybrid.walk(nativeAnalysis);

		final ArrayList<String> listOfNativeMethods = (ArrayList<String>) nativeAnalysis
				.getResult();
		System.out.println(listOfNativeMethods.size());
		System.out.println(listOfNativeMethods);
		System.out.println(
				"********************List Of JNI Methods***********************");//PB: pas d'affichage des methodes globales qui retournent Void
		final IWalker globalesAnalysis = new JNICollecteFctGlobaleVisitor();
		hybrid.walk(globalesAnalysis);
		final ArrayList<String> listOfJNIMethods = (ArrayList<String>) globalesAnalysis
				.getResult();
		System.out.println(listOfJNIMethods.size());
		System.out.println(listOfJNIMethods);

		System.out.println(
				"******************List Of Native Methods Missed on JNI methods**************************");
		System.out.println(
				getMissingNativeMethods(listOfNativeMethods, listOfJNIMethods)
						.size());
		System.out.println(
				getMissingNativeMethods(listOfNativeMethods, listOfJNIMethods));

		System.out.println(
				"******************List Of JNI Methods Missed on Natives methods**************************");
		System.out.println(
				getMissingJNIFunctions(listOfNativeMethods, listOfJNIMethods)
						.size());
		System.out.println(
				getMissingJNIFunctions(listOfNativeMethods, listOfJNIMethods));

	}
}
