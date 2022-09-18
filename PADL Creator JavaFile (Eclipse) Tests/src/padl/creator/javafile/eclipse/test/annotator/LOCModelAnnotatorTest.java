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
package padl.creator.javafile.eclipse.test.annotator;

import org.junit.Assert;
import junit.framework.TestCase;
import padl.creator.javafile.eclipse.test.util.Utils;
import padl.kernel.IClass;
import padl.kernel.ICodeLevelModel;
import padl.kernel.IConstructor;
import padl.kernel.IInterface;
import padl.kernel.IMethod;

public class LOCModelAnnotatorTest extends TestCase {

	public LOCModelAnnotatorTest(final String name) {
		super(name);

	}
	public void testLoc() {
		final String sourcePath =
			"../PADL Creator JavaFile (Eclipse) Tests/rsc/PADL testdata/";
		final String[] javaFiles =
			new String[] { "../PADL Creator JavaFile (Eclipse) Tests/src/padl/example/annotator/" };
		final String classPathEntry = "";
		final ICodeLevelModel javaModel =
			Utils.createLightJavaFilesPadlModel(
				"",
				sourcePath,
				classPathEntry,
				javaFiles);
		final IClass entity =
			(IClass) javaModel
				.getTopLevelEntityFromID("padl.example.annotator.E");

		final IMethod m1 =
			(IMethod) entity.getConstituentFromName("main".toCharArray());
		Assert.assertEquals(10, m1.getCodeLines().length);

		final IConstructor c1 =
			(IConstructor) entity.getConstituentFromName("E".toCharArray());
		Assert.assertEquals(4, c1.getCodeLines().length);
	}

	/**
	 * tests loc with abstract class and abstract methods
	 */
	public void testLocAbstractClass() {
		final String sourcePath =
			"../PADL Creator JavaFile (Eclipse) Tests/rsc/PADL testdata/";
		final String[] javaFiles =
			new String[] { "../PADL Creator JavaFile (Eclipse) Tests/src/padl/example/annotator/" };
		final String classPathEntry = "";
		final ICodeLevelModel javaModel =
			Utils.createLightJavaFilesPadlModel(
				"",
				sourcePath,
				classPathEntry,
				javaFiles);

		final String classFiles =
			"../PADL Creator JavaFile (Eclipse) Tests/rsc/PADL testdata/padl/example/annotator/bin/";
		final ICodeLevelModel classModel =
			Utils.createAnnotatedPadlClassModel(classFiles);

		final IClass javaClass =
			(IClass) javaModel
				.getTopLevelEntityFromID("padl.example.annotator.AbstractClass");
		final IMethod javaMethodWithLines =
			(IMethod) javaClass
				.getConstituentFromName("myMethod".toCharArray());
		final IMethod javaAbstractMethod =
			(IMethod) javaClass.getConstituentFromName("abstractMethod"
				.toCharArray());
		Assert.assertEquals(4, javaMethodWithLines.getCodeLines().length);
		Assert.assertNull(javaAbstractMethod.getCodeLines());

		final IClass classClass =
			(IClass) classModel
				.getTopLevelEntityFromID("padl.example.annotator.AbstractClass");
		final IMethod classAbstarctMethod =
			(IMethod) classClass.getConstituentFromName("abstractMethod"
				.toCharArray());
		Assert.assertNull(classAbstarctMethod.getCodeLines());

	}

	/**
	 * tests loc with interface
	 * adding a condition to avoid the visit of interfaces to have the same behaviour athe .class model
	 */
	public void testLocInterface() {
		final String sourcePath =
			"../PADL Creator JavaFile (Eclipse) Tests/rsc/PADL testdata/";
		final String[] javaFiles =
			new String[] { "../PADL Creator JavaFile (Eclipse) Tests/src/padl/example/annotator/" };
		final String classPathEntry = "";
		final ICodeLevelModel javaModel =
			Utils.createLightJavaFilesPadlModel(
				"",
				sourcePath,
				classPathEntry,
				javaFiles);

		final String classFiles =
			"../PADL Creator JavaFile (Eclipse) Tests/rsc/PADL testdata/padl/example/annotator/bin/";
		final ICodeLevelModel classModel =
			Utils.createAnnotatedPadlClassModel(classFiles);

		final IInterface javaInterface =
			(IInterface) javaModel
				.getTopLevelEntityFromID("padl.example.annotator.Interface1");
		final IMethod javaMethod1 =
			(IMethod) javaInterface.getConstituentFromName("method1"
				.toCharArray());
		final IMethod javaMethod2 =
			(IMethod) javaInterface.getConstituentFromName("method2"
				.toCharArray());
		Assert.assertEquals(0, javaMethod1.getCodeLines().length);
		Assert.assertEquals(0, javaMethod2.getCodeLines().length);

		final IInterface classClass =
			(IInterface) classModel
				.getTopLevelEntityFromID("padl.example.annotator.Interface1");
		final IMethod classMethod1 =
			(IMethod) classClass
				.getConstituentFromName("method1".toCharArray());
		final IMethod classMethod2 =
			(IMethod) classClass
				.getConstituentFromName("method2".toCharArray());
		Assert.assertNull(classMethod1.getCodeLines());
		Assert.assertNull(classMethod2.getCodeLines());

	}
}
