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

public class MI_FieldAccessTest extends TestCase {

	public MI_FieldAccessTest(final String name) {
		super(name);
	}

	public void testFieldAccess_CLASS_CLASS() {
		final String sourcePath =
			"../PADL Creator JavaFile (Eclipse) Tests/src/";
		final String[] javaFiles =
			new String[] {
					"../PADL Creator JavaFile (Eclipse) Tests/src/padl/example/relationship/A.java",
					"../PADL Creator JavaFile (Eclipse) Tests/src/padl/example/relationship/FieldAccess_CLASS_CLASS.java" };
		final String classPathEntry = "";
		final ICodeLevelModel javaModel =
			Utils.createCompleteJavaFilesPadlModel(
				"",
				sourcePath,
				classPathEntry,
				javaFiles);

		final String[] classFiles =
			new String[] {
					"../PADL Creator JavaFile (Eclipse) Tests/bin/padl/example/relationship/A.class",
					"../PADL Creator JavaFile (Eclipse) Tests/bin/padl/example/relationship/FieldAccess_CLASS_CLASS.class" };
		final ICodeLevelModel classModel =
			Utils.createCompleteJavaClassesPadlModel("", classFiles);

		final IFirstClassEntity javaClass =
			javaModel
				.getTopLevelEntityFromID("padl.example.relationship.FieldAccess_CLASS_CLASS");
		final IMethod javaMethod =
			(IMethod) javaClass.getConstituentFromName("foo");
		//	final IMethodInvocation javaMethodInvocation =
		//		(IMethodInvocation) javaMethod
		//			.getConstituentFromID("Method Invocation_1");
		//ProxyConsole.getInstance().debugOutput().println(javaMethod.toString());
		final Iterator iter = javaMethod.getIteratorOnConstituents();
		while (iter.hasNext()) {
			ProxyConsole
				.getInstance()
				.debugOutput()
				.println("javaMethod " + iter.next().toString());
		}

		final IFirstClassEntity classClass =
			classModel
				.getTopLevelEntityFromID("padl.example.relationship.FieldAccess_CLASS_CLASS");
		final IMethod classMethod =
			(IMethod) classClass.getConstituentFromName("foo");
		//	final IMethodInvocation classMethodInvocation =
		//		(IMethodInvocation) classMethod
		//			.getConstituentFromID("Method Invocation_1");
		//ProxyConsole.getInstance().debugOutput().println(classMethod.toString());
		final Iterator iter1 = classMethod.getIteratorOnConstituents();
		while (iter1.hasNext()) {
			ProxyConsole
				.getInstance()
				.debugOutput()
				.println("classMethod " + iter1.next().toString());
		}

		Assert.assertTrue(MethodInvocationComparator
			.isMIofJavaModelMethodIncludedInMIofClassModelMethod(
				javaMethod,
				classMethod));

		//		Assert.assertEquals(IMethodInvocation.CLASS_CLASS,javaMethodInvocation.getType());
		//		Assert.assertEquals(classMethodInvocation.getType(),javaMethodInvocation.getType());
		//		Assert.assertEquals("padl.example.relationship.Aggregation_CLASS_INSTANCE_FROM_FIELD_1",javaMethodInvocation.getFieldDeclaringEntity().getDisplayID());
		//		Assert.assertEquals("a",javaMethodInvocation.getFirstCallingField().getDisplayName());
		//		Assert.assertEquals("padl.example.relationship.A",javaMethodInvocation.getTargetEntity().getDisplayID());
		//		Assert.assertEquals("instanceMethod",javaMethodInvocation.getCalledMethod().getDisplayName());
		//		Assert.assertEquals(Constants.CARDINALITY_ONE, javaMethodInvocation.getCardinality());
		//		Assert.assertEquals(Modifier.PUBLIC, javaMethodInvocation.getVisibility());
		//JavaMethodInvocationorg.junit.AssertionFailedError: expected:<4> but was:<8>

	}

	public void testFieldAccess_INSTANCE_CLASS() {
		final String sourcePath =
			"../PADL Creator JavaFile (Eclipse) Tests/src/";
		final String[] javaFiles =
			new String[] {
					"../PADL Creator JavaFile (Eclipse) Tests/src/padl/example/relationship/A.java",
					"../PADL Creator JavaFile (Eclipse) Tests/src/padl/example/relationship/FieldAccess_INSTANCE_CLASS.java" };
		final String classPathEntry = "";
		final ICodeLevelModel javaModel =
			Utils.createCompleteJavaFilesPadlModel(
				"",
				sourcePath,
				classPathEntry,
				javaFiles);

		final String[] classFiles =
			new String[] {
					"../PADL Creator JavaFile (Eclipse) Tests/bin/padl/example/relationship/A.class",
					"../PADL Creator JavaFile (Eclipse) Tests/bin/padl/example/relationship/FieldAccess_INSTANCE_CLASS.class" };
		final ICodeLevelModel classModel =
			Utils.createCompleteJavaClassesPadlModel("", classFiles);

		final IFirstClassEntity javaClass =
			javaModel
				.getTopLevelEntityFromID("padl.example.relationship.FieldAccess_INSTANCE_CLASS");
		final IMethod javaMethod =
			(IMethod) javaClass.getConstituentFromName("foo");
		final IMethodInvocation javaMethodInvocation =
			(IMethodInvocation) javaMethod
				.getConstituentFromID("Method Invocation_>PADL<_1");
		//ProxyConsole.getInstance().debugOutput().println(javaMethod.toString());
		final Iterator iter = javaMethod.getIteratorOnConstituents();
		while (iter.hasNext()) {
			ProxyConsole
				.getInstance()
				.debugOutput()
				.println("javaMethod " + iter.next().toString());
		}

		final IFirstClassEntity classClass =
			classModel
				.getTopLevelEntityFromID("padl.example.relationship.FieldAccess_INSTANCE_CLASS");
		final IMethod classMethod =
			(IMethod) classClass.getConstituentFromName("foo");
		final IMethodInvocation classMethodInvocation =
			(IMethodInvocation) classMethod
				.getConstituentFromID("Method Invocation_>PADL<_1");
		//ProxyConsole.getInstance().debugOutput().println(classMethod.toString());
		final Iterator iter1 = classMethod.getIteratorOnConstituents();
		while (iter1.hasNext()) {
			ProxyConsole
				.getInstance()
				.debugOutput()
				.println("classMethod " + iter1.next().toString());
		}

		Assert.assertEquals(
			IMethodInvocation.INSTANCE_CLASS,
			javaMethodInvocation.getType());
		Assert.assertEquals(
			classMethodInvocation.getType(),
			javaMethodInvocation.getType());

		Assert.assertTrue(MethodInvocationComparator
			.isMIofJavaModelMethodIncludedInMIofClassModelMethod(
				javaMethod,
				classMethod));

	}

	public void testFieldAccess_INSTANCE_INSTANCE() {
		final String sourcePath =
			"../PADL Creator JavaFile (Eclipse) Tests/src/";
		final String[] javaFiles =
			new String[] {
					"../PADL Creator JavaFile (Eclipse) Tests/src/padl/example/relationship/A.java",
					"../PADL Creator JavaFile (Eclipse) Tests/src/padl/example/relationship/FieldAccess_INSTANCE_INSTANCE.java" };
		final String classPathEntry = "";
		final ICodeLevelModel javaModel =
			Utils.createCompleteJavaFilesPadlModel(
				"",
				sourcePath,
				classPathEntry,
				javaFiles);

		final String[] classFiles =
			new String[] {
					"../PADL Creator JavaFile (Eclipse) Tests/bin/padl/example/relationship/A.class",
					"../PADL Creator JavaFile (Eclipse) Tests/bin/padl/example/relationship/FieldAccess_INSTANCE_INSTANCE.class" };
		final ICodeLevelModel classModel =
			Utils.createCompleteJavaClassesPadlModel("", classFiles);

		final IFirstClassEntity javaClass =
			javaModel
				.getTopLevelEntityFromID("padl.example.relationship.FieldAccess_INSTANCE_INSTANCE");
		final IMethod javaMethod =
			(IMethod) javaClass.getConstituentFromName("foo");
		final IMethodInvocation javaMethodInvocation =
			(IMethodInvocation) javaMethod
				.getConstituentFromID("Method Invocation_>PADL<_1");
		//ProxyConsole.getInstance().debugOutput().println(javaMethod.toString());
		final Iterator iter = javaMethod.getIteratorOnConstituents();
		while (iter.hasNext()) {
			ProxyConsole
				.getInstance()
				.debugOutput()
				.println("javaMethod " + iter.next().toString());
		}

		final IFirstClassEntity classClass =
			classModel
				.getTopLevelEntityFromID("padl.example.relationship.FieldAccess_INSTANCE_INSTANCE");
		final IMethod classMethod =
			(IMethod) classClass.getConstituentFromName("foo");
		final IMethodInvocation classMethodInvocation =
			(IMethodInvocation) classMethod
				.getConstituentFromID("Method Invocation_>PADL<_3");
		//ProxyConsole.getInstance().debugOutput().println(classMethod.toString());
		final Iterator iter1 = classMethod.getIteratorOnConstituents();
		while (iter1.hasNext()) {
			ProxyConsole
				.getInstance()
				.debugOutput()
				.println("classMethod " + iter1.next().toString());
		}

		Assert.assertEquals(
			IMethodInvocation.CLASS_INSTANCE_FROM_FIELD,
			javaMethodInvocation.getType());
		Assert.assertEquals(
			classMethodInvocation.getType(),
			javaMethodInvocation.getType());

		Assert.assertTrue(MethodInvocationComparator
			.isMIofJavaModelMethodIncludedInMIofClassModelMethod(
				javaMethod,
				classMethod));

	}

}
