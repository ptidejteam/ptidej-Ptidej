package padl.creator.cppfile.eclipse.test.big;

import java.util.ArrayList;
//import junit.framework.TestCase;
import java.util.Iterator;
import padl.creator.cppfile.eclipse.CPPCreator;
import padl.creator.javafile.eclipse.CompleteJavaFileCreator;
import padl.kernel.ICodeLevelModel;
import padl.kernel.ICodeLevelModelCreator;
import padl.kernel.exception.CreationException;
import padl.kernel.impl.Factory;
import padl.visitor.IWalker;

public class PadlModelJNI {
	// Method that check if all the natives methods have an implementation in
	// C++ files, in others words for each
	// native methods we check if there is a JNI implementation in a c++ files.
	public static ArrayList<String> NatifMissed(
		ArrayList<String> listNatif,
		ArrayList<String> listJNI) {
		final ArrayList<String> JniNatifIntersect = new ArrayList<String>();
		Iterator it = listNatif.iterator();
		while (it.hasNext()) {
			Object o = it.next();
			if (!listJNI.contains(o)) {
				JniNatifIntersect.add((String) o);
			}
		}

		return JniNatifIntersect;
	}

	// The opposite direction of NatifMissed.
	public static ArrayList<String> JNIMissed(
		ArrayList<String> listNatif,
		ArrayList<String> listJNI) {
		final ArrayList<String> NatifIntersect = new ArrayList<String>();
		Iterator it = listJNI.iterator();
		while (it.hasNext()) {
			Object o = it.next();
			if (!listNatif.contains(o)) {
				NatifIntersect.add((String) o);
			}
		}

		return NatifIntersect;
	}

	public static void main(String[] args) throws CreationException {
		final String apathJ =
			"C:/Users/manel/Desktop/TheseManel/Recherche/ProgrammesJNIcollectésparOpenHub/ogre4j/ogre4j/src/java/org/ogre4j - testmanel";
		// Faut compiler les fichiers.java 
		final String apathC =
			"C:/Users/manel/Desktop/TheseManel/Recherche/ProgrammesJNIcollectésparOpenHub/ogre4j/ogre4j/src/native/src - testmanel";

		final ICodeLevelModel hybrid =
			Factory.getInstance().createCodeLevelModel("Hybrid");
		final ICodeLevelModelCreator javaCreator =
			new CompleteJavaFileCreator(apathJ, "");
		javaCreator.create(hybrid);
		final ICodeLevelModelCreator cppCreator = new CPPCreator(apathC);
		cppCreator.create(hybrid);

		System.out.println(
			"******************List Of Natives Methods*************************");
		final IWalker nativeAnalysis = new JNICollecteNativeVisitor();
		hybrid.walk(nativeAnalysis);

		final ArrayList<String> listOfNativeMethods =
			(ArrayList<String>) nativeAnalysis.getResult();
		System.out.println(listOfNativeMethods.size());
		System.out.println(listOfNativeMethods);
		System.out.println(
			"********************List Of JNI Methods***********************");//PB: pas d'affichage des methodes globales qui retournent Void
		final IWalker globalesAnalysis = new JNICollecteFctGlobaleVisitor();
		hybrid.walk(globalesAnalysis);
		final ArrayList<String> listOfJNIMethods =
			(ArrayList<String>) globalesAnalysis.getResult();
		System.out.println(listOfJNIMethods.size());
		System.out.println(listOfJNIMethods);

		System.out.println(
			"******************List Of Native Methods Missed on JNI methods**************************");
		System.out
			.println(NatifMissed(listOfNativeMethods, listOfJNIMethods).size());
		System.out.println(NatifMissed(listOfNativeMethods, listOfJNIMethods));

		System.out.println(
			"******************List Of JNI Methods Missed on Natives methods**************************");
		System.out
			.println(JNIMissed(listOfNativeMethods, listOfJNIMethods).size());
		System.out.println(JNIMissed(listOfNativeMethods, listOfJNIMethods));

	}

	// The testCases
	// The same method as 'NatifMissed' but here we use it for our test case to
	// count the number of natives methods
	// that don't have a JNI implementation in a c++ files.
	public int NatifMissedTestCase() throws CreationException {
		ICodeLevelModel hybrid = this.CreateModelTestCase();
		final IWalker nativeAnalysis = new JNICollecteNativeVisitor();
		hybrid.walk(nativeAnalysis);
		final ArrayList<String> listOfNativeMethods =
			(ArrayList<String>) nativeAnalysis.getResult();
		final IWalker globalesAnalysis = new JNICollecteFctGlobaleVisitor();
		hybrid.walk(globalesAnalysis);
		final ArrayList<String> listOfJNIMethods =
			(ArrayList<String>) globalesAnalysis.getResult();
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

	// a Method used for the test case to count the number of JNI methods exist
	// in C++ files that they don't have
	// a native declaration in java files. (the opposite direction of
	// NatifMissedTestCase).
	public int JNIMissedTestCase() throws CreationException {
		ICodeLevelModel hybrid = this.CreateModelTestCase();
		final IWalker nativeAnalysis = new JNICollecteNativeVisitor();
		hybrid.walk(nativeAnalysis);
		final ArrayList<String> listOfNativeMethods =
			(ArrayList<String>) nativeAnalysis.getResult();
		final IWalker globalesAnalysis = new JNICollecteFctGlobaleVisitor();
		hybrid.walk(globalesAnalysis);
		final ArrayList<String> listOfJNIMethods =
			(ArrayList<String>) globalesAnalysis.getResult();
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

	// This method is used in the Test Case and it allow us to return the Model
	// Hybrid which contain the constituents
	// for Java files and C++ files.
	public ICodeLevelModel CreateModelTestCase() throws CreationException {
		final String apathJ =
			"C:/Users/manel/Desktop/TheseManel/Recherche/ProgrammesJNIcollectésparOpenHub/ogre4j/ogre4j/src/java/org/ogre4j - testmanel";
		final String apathC =
			"C:/Users/manel/Desktop/TheseManel/Recherche/ProgrammesJNIcollectésparOpenHub/ogre4j/ogre4j/src/native/src - testmanel";
		final ICodeLevelModel hybrid =
			Factory.getInstance().createCodeLevelModel("Hybrid");
		final ICodeLevelModelCreator javaCreator =
			new CompleteJavaFileCreator(apathJ, "");
		javaCreator.create(hybrid);
		final ICodeLevelModelCreator cppCreator = new CPPCreator(apathC);
		cppCreator.create(hybrid);
		return (hybrid);
	}

	// Method used in the test case and which give the number of natives methods
	// existing in java files.
	public int NBNatif() throws CreationException {
		ICodeLevelModel model = this.CreateModelTestCase();
		final IWalker nativeAnalysis = new JNICollecteNativeVisitor();
		model.walk(nativeAnalysis);
		final ArrayList<String> listOfNativeMethods =
			(ArrayList<String>) nativeAnalysis.getResult();
		return listOfNativeMethods.size();
	}
}
