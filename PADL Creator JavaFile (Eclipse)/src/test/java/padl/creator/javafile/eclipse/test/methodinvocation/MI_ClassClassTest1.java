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

import java.lang.reflect.Modifier;
import java.util.Iterator;
import org.junit.Assert;
import junit.framework.TestCase;
import padl.creator.javafile.eclipse.test.util.Utils;
import padl.kernel.Constants;
import padl.kernel.ICodeLevelModel;
import padl.kernel.IFirstClassEntity;
import padl.kernel.IMethod;
import padl.kernel.IMethodInvocation;
import util.io.ProxyConsole;

public class MI_ClassClassTest1 extends TestCase {

	public MI_ClassClassTest1(final String name) {
		super(name);
	}
	//02022012- Aminata S
	//In the byte code, an INVOKE Static ignore if the invocation is from field or not,so 
	//we will now consider only the type CLASS_CLASS and not CLASS_CLASS_FROM_FIELD
	public void testCLASS_CLASS1() {
		final String sourcePath =
				"../PADL Creator JavaFile (Eclipse)/src/test/java/";
		final String[] javaFiles =
			new String[] {
				"../PADL Creator JavaFile (Eclipse)/src/test/java/padl/example/relationship/A.java",
				"../PADL Creator JavaFile (Eclipse)/src/test/java/padl/example/relationship/Aggregation_CLASS_CLASS_FROM_FIELD_1.java" };
		final String classPathEntry = "";
		final ICodeLevelModel javaModel =
			Utils.createCompleteJavaFilesPadlModel(
				"",
				sourcePath,
				classPathEntry,
				javaFiles);

		final String[] classFiles =
			new String[] {
				"../PADL Creator JavaFile (Eclipse)/target/test-classes/padl/example/relationship/A.class",
				"../PADL Creator JavaFile (Eclipse)/target/test-classes/padl/example/relationship/Aggregation_CLASS_CLASS_FROM_FIELD_1.class" };
		final ICodeLevelModel classModel =
			Utils.createCompleteJavaClassesPadlModel("", classFiles);

		final IFirstClassEntity javaClass =
			javaModel
				.getTopLevelEntityFromID("padl.example.relationship.Aggregation_CLASS_CLASS_FROM_FIELD_1");
		final IMethod javaMethod =
			(IMethod) javaClass.getConstituentFromName("foo");
		final IMethodInvocation javaMethodInvocation =
			(IMethodInvocation) javaMethod
				.getConstituentFromID("Method Invocation_>PADL<_1");
		// ProxyConsole.getInstance().debugOutput().println(javaMethod.toString());
		ProxyConsole
			.getInstance()
			.debugOutput()
			.println("*********************************************");
		final Iterator iterJava = javaMethod.getIteratorOnConstituents();
		while (iterJava.hasNext()) {
			ProxyConsole
				.getInstance()
				.debugOutput()
				.println("javaMethod " + iterJava.next().toString());
		}

		final IFirstClassEntity classClass =
			classModel
				.getTopLevelEntityFromID("padl.example.relationship.Aggregation_CLASS_CLASS_FROM_FIELD_1");
		final IMethod classMethod =
			(IMethod) classClass.getConstituentFromName("foo");

		ProxyConsole
			.getInstance()
			.debugOutput()
			.println(
				"************************************************************");
		final Iterator iterClass = classMethod.getIteratorOnConstituents();
		while (iterClass.hasNext()) {
			ProxyConsole
				.getInstance()
				.debugOutput()
				.println(iterClass.next().toString());
			// 2 method invocations of type 1, no fields in both, called method
			// only in the second
		}

		Assert.assertEquals(
			"Method invocation type",
			IMethodInvocation.CLASS_CLASS,
			javaMethodInvocation.getType());
		Assert.assertNull(
			"Declaring entity is null",
			javaMethodInvocation.getFieldDeclaringEntity());
		Assert.assertNull(
			"Field is null",
			javaMethodInvocation.getFirstCallingField());
		Assert.assertEquals(
			"Target entity",
			"padl.example.relationship.A",
			javaMethodInvocation.getTargetEntity().getDisplayID());
		Assert.assertEquals(
			"called method name",
			"staticMethod",
			javaMethodInvocation.getCalledMethod().getDisplayName());
		Assert.assertEquals(
			"method invocation cardinality",
			Constants.CARDINALITY_ONE,
			javaMethodInvocation.getCardinality());
		Assert.assertEquals("method invocation visibility", Modifier.PUBLIC
				+ Modifier.STATIC, javaMethodInvocation.getVisibility());

		Assert.assertTrue(MethodInvocationComparator
			.isMIofJavaModelMethodIncludedInMIofClassModelMethod(
				javaMethod,
				classMethod));

	}

	public void testCLASS_CLASS2() {
		final String sourcePath =
				"../PADL Creator JavaFile (Eclipse)/src/test/java/";
		final String[] javaFiles =
			new String[] {
					"../PADL Creator JavaFile (Eclipse)/src/test/java/padl/example/relationship/A.java",
					"../PADL Creator JavaFile (Eclipse)/src/test/java/padl/example/relationship/Aggregation_CLASS_CLASS_FROM_FIELD_2.java" };
		final String classPathEntry = "";
		final ICodeLevelModel javaModel =
			Utils.createCompleteJavaFilesPadlModel(
				"",
				sourcePath,
				classPathEntry,
				javaFiles);

		final String[] classFiles =
			new String[] {
					"../PADL Creator JavaFile (Eclipse)/target/test-classes/padl/example/relationship/A.class",
					"../PADL Creator JavaFile (Eclipse)/target/test-classes/padl/example/relationship/Aggregation_CLASS_CLASS_FROM_FIELD_2.class" };
		final ICodeLevelModel classModel =
			Utils.createCompleteJavaClassesPadlModel("", classFiles);

		final IFirstClassEntity javaClass =
			javaModel
				.getTopLevelEntityFromID("padl.example.relationship.Aggregation_CLASS_CLASS_FROM_FIELD_2");
		final IMethod javaMethod =
			(IMethod) javaClass.getConstituentFromName("foo");
		final IMethodInvocation javaMethodInvocation =
			(IMethodInvocation) javaMethod
				.getConstituentFromID("Method Invocation_>PADL<_1");
		// ProxyConsole.getInstance().debugOutput().println(javaMethod.toString());
		final Iterator iterJava = javaMethod.getIteratorOnConstituents();
		while (iterJava.hasNext()) {
			ProxyConsole
				.getInstance()
				.debugOutput()
				.println("javaMethod " + iterJava.next().toString());
		}

		final IFirstClassEntity classClass =
			classModel
				.getTopLevelEntityFromID("padl.example.relationship.Aggregation_CLASS_CLASS_FROM_FIELD_2");
		final IMethod classMethod =
			(IMethod) classClass.getConstituentFromName("foo");
		//	final IMethodInvocation classMethodInvocation =
		//		(IMethodInvocation) classMethod
		//			.getConstituentFromID("Method Invocation_4");

		ProxyConsole
			.getInstance()
			.debugOutput()
			.println("*********************************************");
		final Iterator iterClass = classMethod.getIteratorOnConstituents();
		while (iterClass.hasNext()) {
			ProxyConsole
				.getInstance()
				.debugOutput()
				.println("classMethod " + iterClass.next().toString());
		}

		Assert.assertEquals(
			"Method invocation type",
			IMethodInvocation.CLASS_CLASS,
			javaMethodInvocation.getType());
		Assert.assertNull(
			"Declaring entity is null",
			javaMethodInvocation.getFieldDeclaringEntity());
		Assert.assertNull(
			"Field is null",
			javaMethodInvocation.getFirstCallingField());
		Assert.assertEquals(
			"Target entity",
			"padl.example.relationship.A",
			javaMethodInvocation.getTargetEntity().getDisplayID());
		Assert.assertEquals(
			"called method name",
			"staticMethod",
			javaMethodInvocation.getCalledMethod().getDisplayName());
		//02022012 - Aminata S
		//Changed because for INVOKE STATIC, the cardinality is always ONE
		Assert.assertEquals(
			"method invocation cardinality",
			Constants.CARDINALITY_ONE,
			javaMethodInvocation.getCardinality());
		Assert.assertEquals("method invocation visibility", Modifier.PUBLIC
				+ Modifier.STATIC, javaMethodInvocation.getVisibility());

		Assert.assertTrue(MethodInvocationComparator
			.isMIofJavaModelMethodIncludedInMIofClassModelMethod(
				javaMethod,
				classMethod));
	}
}
