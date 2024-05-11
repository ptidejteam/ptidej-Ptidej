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

import java.util.Iterator;
import org.junit.Assert;
import junit.framework.TestCase;
import padl.creator.javafile.eclipse.test.util.Utils;
import padl.kernel.ICodeLevelModel;
import padl.kernel.IFirstClassEntity;
import padl.kernel.IMethod;
import padl.kernel.IMethodInvocation;
import util.io.ProxyConsole;

public class MI_ChainOfMessagesTest extends TestCase {

	public MI_ChainOfMessagesTest(final String name) {
		super(name);
	}

	public void testChainOfMessages() {
		final String sourcePath =
				"../PADL Creator JavaFile (Eclipse)/src/test/java/";
		final String[] javaFiles =
			new String[] { "../PADL Creator JavaFile (Eclipse)/src/test/java/padl/example/relationship/ChainOfMessages.java" };
		final String classPathEntry = "";
		final ICodeLevelModel javaModel =
			Utils.createCompleteJavaFilesPadlModel(
				"",
				sourcePath,
				classPathEntry,
				javaFiles);

		final String[] classFiles =
			new String[] { "../PADL Creator JavaFile (Eclipse)/target/test-classes/padl/example/relationship/ChainOfMessages.class" };
		final ICodeLevelModel classModel =
			Utils.createCompleteJavaClassesPadlModel("", classFiles);

		final IFirstClassEntity javaClass =
			javaModel
				.getTopLevelEntityFromID("padl.example.relationship.ChainOfMessages");

		final IMethod javaMethod =
			(IMethod) javaClass.getConstituentFromName("foo");

		final Iterator iterJava =
			javaMethod.getIteratorOnConstituents(IMethodInvocation.class);
		while (iterJava.hasNext()) {
			ProxyConsole
				.getInstance()
				.debugOutput()
				.println("javaMethod " + iterJava.next().toString());
		}

		final IFirstClassEntity classClass =
			classModel
				.getTopLevelEntityFromID("padl.example.relationship.ChainOfMessages");
		final IMethod classMethod =
			(IMethod) classClass.getConstituentFromName("foo");

		ProxyConsole
			.getInstance()
			.debugOutput()
			.println("********************************************");
		final Iterator iterClass =
			classMethod.getIteratorOnConstituents(IMethodInvocation.class);
		while (iterClass.hasNext()) {
			ProxyConsole
				.getInstance()
				.debugOutput()
				.println("classMethod " + iterClass.next().toString());
		}
		Assert.assertTrue(MethodInvocationComparator
			.isMIofJavaModelMethodIncludedInMIofClassModelMethod(
				javaMethod,
				classMethod));
	}
}
