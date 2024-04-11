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
package padl.creator.javafile.eclipse.test.methodinvocation;

import org.junit.Assert;

import junit.framework.TestCase;
import padl.creator.javafile.eclipse.test.util.Utils;
import padl.kernel.IClass;
import padl.kernel.ICodeLevelModel;
import padl.kernel.IMethod;
import padl.kernel.IMethodInvocation;
import util.io.Files;

public class MethodInvocationTest1 extends TestCase {
	public MethodInvocationTest1(final String name) {
		super(name);
	}

	//normal failure, it is to show the difference about the number of MIs of the 2 models
	public void testMethodInvocation() {
		final String sourcePath =
			"../PADL Creator JavaFile (Eclipse)/target/test-classes//PADL testdata/";
		final String[] javaFiles =
			new String[] { "../PADL Creator JavaFile (Eclipse)/target/test-classes//PADL testdata/padl/example/methodInvocation/" };
		final String classPathEntry = "";
		final ICodeLevelModel javaModel =
			Utils.createCompleteJavaFilesPadlModel(
				"",
				sourcePath,
				classPathEntry,
				javaFiles);

		final String classFiles =
			"../PADL Creator JavaFile (Eclipse)/target/test-classes//PADL testdata/padl/example/methodInvocation/bin/";
		final ICodeLevelModel classModel =
			Utils.createCompleteJavaClassesPadlModel(
				"",
				new String[] { classFiles });

		final IClass javaClass1 =
			(IClass) javaModel
				.getTopLevelEntityFromID("padl.example.methodinvocation.A");
		final IMethod javaMethod1 =
			(IMethod) javaClass1.getConstituentFromName("main");
		final int nbJavaMI1 =
			javaMethod1.getNumberOfConstituents(IMethodInvocation.class);

		final IClass javaClass2 =
			(IClass) javaModel
				.getTopLevelEntityFromID("padl.example.methodinvocation.B");
		final IMethod javaMethod2 =
			(IMethod) javaClass2.getConstituentFromName("m");
		final int nbJavaMI2 =
			javaMethod2.getNumberOfConstituents(IMethodInvocation.class);

		final IClass classClass1 =
			(IClass) classModel
				.getTopLevelEntityFromID("padl.example.methodinvocation.A");
		final IMethod classMethod1 =
			(IMethod) classClass1.getConstituentFromName("main");
		final int nbClassMI1 =
			classMethod1.getNumberOfConstituents(IMethodInvocation.class);

		final IClass classClass2 =
			(IClass) classModel
				.getTopLevelEntityFromID("padl.example.methodinvocation.B");
		final IMethod classMethod2 =
			(IMethod) classClass2.getConstituentFromName("m");
		final int nbClassMI2 =
			classMethod2.getNumberOfConstituents(IMethodInvocation.class);

		Assert.assertEquals(nbClassMI1, nbJavaMI1);
		Assert.assertEquals(nbClassMI2, nbJavaMI2);
	}
}
