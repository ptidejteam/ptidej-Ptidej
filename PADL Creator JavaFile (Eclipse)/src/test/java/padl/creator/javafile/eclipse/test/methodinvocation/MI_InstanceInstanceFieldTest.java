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
import util.io.Files;
import util.io.ProxyConsole;

public class MI_InstanceInstanceFieldTest extends TestCase {

	public MI_InstanceInstanceFieldTest(final String name) {
		super(name);
	}

	public void testINSTANCE_INSTANCE_FROM_FIELD1() {
		final String sourcePath =
			"../PADL Creator JavaFile (Eclipse)/src/test/java/";
		final String[] javaFiles =
			new String[] {
					"../PADL Creator JavaFile (Eclipse)/src/test/java/padl/example/relationship/A.java",
					"../PADL Creator JavaFile (Eclipse)/src/test/java/padl/example/relationship/Aggregation_INSTANCE_INSTANCE_FROM_FIELD_1.java" };
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
					"../PADL Creator JavaFile (Eclipse)/target/test-classes/padl/example/relationship/Aggregation_INSTANCE_INSTANCE_FROM_FIELD_1.class" };
		final ICodeLevelModel classModel =
			Utils.createCompleteJavaClassesPadlModel("", classFiles);

		final IFirstClassEntity javaClass =
			javaModel
				.getTopLevelEntityFromID("padl.example.relationship.Aggregation_INSTANCE_INSTANCE_FROM_FIELD_1");
		final IMethod javaMethod =
			(IMethod) javaClass.getConstituentFromName("foo");
		final IMethodInvocation javaMethodInvocation =
			(IMethodInvocation) javaMethod
				.getConstituentFromID("Method Invocation_>PADL<_1");
		//ProxyConsole.getInstance().debugOutput().println(javaMethod.toString());
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
				.getTopLevelEntityFromID("padl.example.relationship.Aggregation_INSTANCE_INSTANCE_FROM_FIELD_1");
		final IMethod classMethod =
			(IMethod) classClass.getConstituentFromName("foo");
		ProxyConsole
			.getInstance()
			.debugOutput()
			.println("***********************");
		final Iterator iterClass =
			classMethod.getIteratorOnConstituents(IMethodInvocation.class);
		while (iterClass.hasNext()) {
			ProxyConsole
				.getInstance()
				.debugOutput()
				.println("classMethod " + iterClass.next().toString());
		}

		Assert.assertEquals(
			"Method invocation type",
			IMethodInvocation.INSTANCE_INSTANCE_FROM_FIELD,
			javaMethodInvocation.getType());
		Assert
			.assertEquals(
				"Declaring entity",
				"padl.example.relationship.Aggregation_INSTANCE_INSTANCE_FROM_FIELD_1",
				javaMethodInvocation.getFieldDeclaringEntity().getDisplayID());
		Assert.assertEquals("Field name", "a", javaMethodInvocation
			.getFirstCallingField()
			.getDisplayName());
		Assert.assertEquals(
			"Target entity",
			"padl.example.relationship.A",
			javaMethodInvocation.getTargetEntity().getDisplayID());
		Assert.assertEquals(
			"called method name",
			"instanceMethod",
			javaMethodInvocation.getCalledMethod().getDisplayName());
		Assert.assertEquals(
			"method invocation cardinality",
			Constants.CARDINALITY_ONE,
			javaMethodInvocation.getCardinality());
		Assert.assertEquals(
			"method invocation visibility",
			Modifier.PUBLIC,
			javaMethodInvocation.getVisibility());
		Assert.assertTrue(MethodInvocationComparator
			.isMIofJavaModelMethodIncludedInMIofClassModelMethod(
				javaMethod,
				classMethod));

	}

	public void testINSTANCE_INSTANCE_FROM_FIELD2() {
		final String sourcePath =
			"../PADL Creator JavaFile (Eclipse)/src/test/java/";
		final String[] javaFiles =
			new String[] {
					"../PADL Creator JavaFile (Eclipse)/src/test/java/padl/example/relationship/A.java",
					"../PADL Creator JavaFile (Eclipse)/src/test/java/padl/example/relationship/Aggregation_INSTANCE_INSTANCE_FROM_FIELD_2.java" };
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
					"../PADL Creator JavaFile (Eclipse)/target/test-classes/padl/example/relationship/Aggregation_INSTANCE_INSTANCE_FROM_FIELD_2.class" };
		final ICodeLevelModel classModel =
			Utils.createCompleteJavaClassesPadlModel("", classFiles);

		final IFirstClassEntity javaClass =
			javaModel
				.getTopLevelEntityFromID("padl.example.relationship.Aggregation_INSTANCE_INSTANCE_FROM_FIELD_2");
		final IMethod javaMethod =
			(IMethod) javaClass.getConstituentFromName("foo");
		final IMethodInvocation javaMethodInvocation =
			(IMethodInvocation) javaMethod
				.getConstituentFromID("Method Invocation_>PADL<_1");
		//ProxyConsole.getInstance().debugOutput().println(javaMethod.toString());
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
				.getTopLevelEntityFromID("padl.example.relationship.Aggregation_INSTANCE_INSTANCE_FROM_FIELD_2");
		final IMethod classMethod =
			(IMethod) classClass.getConstituentFromName("foo");

		final Iterator iterClass =
			classMethod.getIteratorOnConstituents(IMethodInvocation.class);
		ProxyConsole
			.getInstance()
			.debugOutput()
			.println("***********************");
		while (iterClass.hasNext()) {
			ProxyConsole
				.getInstance()
				.debugOutput()
				.println("classMethod " + iterClass.next().toString());
		}

		Assert.assertEquals(
			"Method invocation type",
			IMethodInvocation.INSTANCE_INSTANCE_FROM_FIELD,
			javaMethodInvocation.getType());
		Assert
			.assertEquals(
				"Declaring entity",
				"padl.example.relationship.Aggregation_INSTANCE_INSTANCE_FROM_FIELD_2",
				javaMethodInvocation.getFieldDeclaringEntity().getDisplayID());
		Assert.assertEquals("Field name", "a", javaMethodInvocation
			.getFirstCallingField()
			.getDisplayName());
		Assert.assertEquals(
			"Target entity",
			"padl.example.relationship.A",
			javaMethodInvocation.getTargetEntity().getDisplayID());
		Assert.assertEquals(
			"called method name",
			"instanceMethod",
			javaMethodInvocation.getCalledMethod().getDisplayName());
		Assert.assertEquals(
			"method invocation cardinality",
			Constants.CARDINALITY_MANY,
			javaMethodInvocation.getCardinality());
		Assert.assertEquals(
			"method invocation visibility",
			Modifier.PUBLIC,
			javaMethodInvocation.getVisibility());
		Assert.assertTrue(MethodInvocationComparator
			.isMIofJavaModelMethodIncludedInMIofClassModelMethod(
				javaMethod,
				classMethod));

	}

}
