/*******************************************************************************
 * Copyright (c) 2001-2014 Yann-Gaël Guéhéneuc and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/old-licenses/gpl-2.0.html
 * 
 * Contributors:
 *     Yann-Gaël Guéhéneuc and others, see in file; API and its implementation
 ******************************************************************************/
package padl.creator.javafile.eclipse.test.advanced;

import org.junit.Assert;
import junit.framework.TestCase;
import padl.creator.javafile.eclipse.test.util.Utils;
import padl.creator.javafile.eclipse.util.PadlParserUtil;
import padl.kernel.IClass;
import padl.kernel.ICodeLevelModel;
import padl.kernel.IMemberClass;

public class DuplicationTest extends TestCase {

	public DuplicationTest(final String aName) {
		super(aName);

	}

	// /**
	// * test testClassesDuplication() 1, 2 and 3 : if there are 2 classes in
	// the
	// * input source which are the same name and the same package, only one
	// * should be visited
	// */
	public void testClassesDuplication() {

		final String sourcePath =
			"../PADL Creator JavaFile (Eclipse) Tests/rsc/PADL testdata/";
		final String[] javaFiles =
			new String[] {
					"../PADL Creator JavaFile (Eclipse) Tests/rsc/PADL testdata/padl/example/eclipse/duplication/classes/in/A.java",
					"../PADL Creator JavaFile (Eclipse) Tests/rsc/PADL testdata/padl/example/eclipse/duplication/classes/out/A.java", };
		final String classPathEntry = "";

		final ICodeLevelModel model =
			Utils.createLightJavaFilesPadlModel(
				"",
				sourcePath,
				classPathEntry,
				javaFiles);
		int nbClasses = 0;
		try {
			nbClasses =
				model.getNumberOfTopLevelEntities(Class
					.forName("padl.kernel.impl.Class"));
		}
		catch (final ClassNotFoundException e) {

			e.printStackTrace();
		}
		Assert.assertEquals(1, nbClasses);

	}

	public void testClassesDuplication2() {

		final String sourcePath =
			"../PADL Creator JavaFile (Eclipse) Tests/rsc/PADL testdata/";
		final String[] javaFiles =
			new String[] { "../PADL Creator JavaFile (Eclipse) Tests/rsc/PADL testdata/padl/example/eclipse/duplication/classes/script/" };
		// contains 2 java.lang.Class.java
		final String classPathEntry = "";

		final ICodeLevelModel model =
			Utils.createLightJavaFilesPadlModel(
				"",
				sourcePath,
				classPathEntry,
				javaFiles);
		int nbClasses = 0;
		try {
			nbClasses =
				model.getNumberOfTopLevelEntities(Class
					.forName("padl.kernel.impl.Class"));
		}
		catch (final ClassNotFoundException e) {

			e.printStackTrace();
		}
		Assert.assertEquals(1, nbClasses);

	}

	public void testClassesDuplication3() {

		final String sourcePath =
			"../PADL Creator JavaFile (Eclipse) Tests/rsc/PADL testdata/";
		final String[] javaFiles =
			new String[] { "../PADL Creator JavaFile (Eclipse) Tests/rsc/PADL testdata/padl/example/eclipse/duplication/classes/" };
		// contains 4 java.lang.Class.java
		final String classPathEntry = "";

		final ICodeLevelModel model =
			Utils.createLightJavaFilesPadlModel(
				"",
				sourcePath,
				classPathEntry,
				javaFiles);
		int nbClasses = 0;
		try {
			nbClasses =
				model.getNumberOfTopLevelEntities(Class
					.forName("padl.kernel.impl.Class"));
		}
		catch (final ClassNotFoundException e) {

			e.printStackTrace();
		}
		Assert.assertEquals(2, nbClasses);

	}

	// many classes in the same file, one public, 3 no public and not member
	// public class ManyClassesInOneFile
	// class ManyClassesInOneFile (node.resolveBinding() == null)
	// class ThirdClass
	// class ThirdClass (node.resolveBinding() == null)
	public void testClassesDuplication4() {

		final String sourcePath =
			"../PADL Creator JavaFile (Eclipse) Tests/rsc/PADL testdata/";
		final String[] javaFiles =
			new String[] { "../PADL Creator JavaFile (Eclipse) Tests/rsc/PADL testdata/padl/example/eclipse/duplication/classes1/" };

		final String classPathEntry = "";

		final ICodeLevelModel model =
			Utils.createLightJavaFilesPadlModel(
				"",
				sourcePath,
				classPathEntry,
				javaFiles);
		int nbClasses = 0;
		try {
			nbClasses =
				model.getNumberOfTopLevelEntities(Class
					.forName("padl.kernel.impl.Class"));
		}
		catch (final ClassNotFoundException e) {

			e.printStackTrace();
		}
		Assert.assertEquals(2, nbClasses);

	}

	/**
	* Member classes with the same name (node.resolveBinding() == null)
	*/
	public void testMemberClassesDuplication() {

		final String sourcePath =
			"../PADL Creator JavaFile (Eclipse) Tests/rsc/PADL testdata/";
		final String[] javaFiles =
			new String[] { "../PADL Creator JavaFile (Eclipse) Tests/rsc/PADL testdata/padl/example/eclipse/duplication/memberClasses/$className$.java" };

		final String classPathEntry = "";

		final ICodeLevelModel model =
			Utils.createLightJavaFilesPadlModel(
				"",
				sourcePath,
				classPathEntry,
				javaFiles);

		final IClass clazz =
			(IClass) model.getTopLevelEntityFromID("$packageName$.$className$");

		try {
			Assert.assertEquals(5, clazz.getNumberOfConstituents(Class
				.forName("padl.kernel.impl.MemberClass")));
		}
		catch (final ClassNotFoundException e) {
			e.printStackTrace();
		}

	}

	/**
	* Member classes with the same name (node.resolveBinding() == null)
	*/
	public void testMemberClassesDuplication1() {

		final String sourcePath =
			"../PADL Creator JavaFile (Eclipse) Tests/rsc/PADL testdata/";
		final String[] javaFiles =
			new String[] { "../PADL Creator JavaFile (Eclipse) Tests/rsc/PADL testdata/padl/example/eclipse/duplication/memberClasses/TestB.java" };

		final String classPathEntry = "";

		final ICodeLevelModel model =
			Utils.createLightJavaFilesPadlModel(
				"",
				sourcePath,
				classPathEntry,
				javaFiles);

		final IClass clazz =
			(IClass) model
				.getTopLevelEntityFromID("padl.example.eclipse.duplication.memberClasses.TestB");

		try {
			Assert.assertEquals(1, clazz.getNumberOfConstituents(Class
				.forName("padl.kernel.impl.MemberClass")));
		}
		catch (final ClassNotFoundException e) {
			e.printStackTrace();
		}

	}

	/**
	 * methods with the same name signature in the same class
	 */
	public void testMethodsAndFieldsDuplication() {
		final String sourcePath =
			"../PADL Creator JavaFile (Eclipse) Tests/rsc/PADL testdata/";
		final String[] javaFiles =
			new String[] { "../PADL Creator JavaFile (Eclipse) Tests/rsc/PADL testdata/padl/example/eclipse/duplication/methods/DuplicateMethods.java" };

		final String classPathEntry = "";

		final ICodeLevelModel model =
			Utils.createLightJavaFilesPadlModel(
				"",
				sourcePath,
				classPathEntry,
				javaFiles);

		//contains 2 methods with the same signature
		//contains 2 fields with the same name (type=null for the second)
		final IClass clazz =
			(IClass) model
				.getTopLevelEntityFromID("padl.example.eclipse.duplication.methods.DuplicateMethods");

		//contains 2 methods with the same signature
		final IMemberClass memberClazz =
			(IMemberClass) clazz
				.getConstituentFromID("padl.example.eclipse.duplication.methods.DuplicateMethods$Inner");

		try {
			Assert.assertEquals(1, clazz.getNumberOfConstituents(Class
				.forName("padl.kernel.impl.Method")));

			Assert.assertEquals(1, memberClazz.getNumberOfConstituents(Class
				.forName("padl.kernel.impl.Method")));

			Assert.assertEquals(1, clazz.getNumberOfConstituents(Class
				.forName("padl.kernel.impl.Field")));

		}
		catch (final ClassNotFoundException e) {

			e.printStackTrace();
		}
	}

	/**
	 * Package has a class with the same name
	 */
	public void testPackageWithClasseDuplication1() {

		final String sourcePath =
			"../PADL Creator JavaFile (Eclipse) Tests/rsc/PADL testdata/";
		final String[] javaFiles =
			new String[] { "../PADL Creator JavaFile (Eclipse) Tests/rsc/PADL testdata/padl/example/eclipse/duplication/Packaje1/Packaje1.java" };

		final String classPathEntry = "";

		final ICodeLevelModel model =
			Utils.createLightJavaFilesPadlModel(
				"",
				sourcePath,
				classPathEntry,
				javaFiles);

		Assert.assertNotNull(PadlParserUtil.getPackage(
			"padl.example.eclipse.duplication.Packaje1",
			model));
		Assert
			.assertNotNull(model
				.getTopLevelEntityFromID("padl.example.eclipse.duplication.Packaje1.Packaje1"));
		Assert
			.assertTrue(model
				.getTopLevelEntityFromID("padl.example.eclipse.duplication.Packaje1.Packaje1") instanceof IClass);

	}

}
