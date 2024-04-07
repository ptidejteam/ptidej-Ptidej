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
package padl.creator.javafile.eclipse.test.basic;

import org.junit.Assert;
import junit.framework.TestCase;
import padl.creator.javafile.eclipse.test.util.Utils;
import padl.kernel.Constants;
import padl.kernel.IClass;
import padl.kernel.ICodeLevelModel;
import padl.kernel.IConstructor;
import padl.kernel.IField;
import padl.kernel.IGhost;
import padl.kernel.IMemberClass;
import padl.kernel.IMethod;
import padl.kernel.IPackage;
import padl.kernel.IParameter;
import padl.test.helper.ModelComparator;
import util.io.ProxyConsole;

public class ClassTest extends TestCase {
	private ICodeLevelModel model;

	public ClassTest(final String aName) {
		super(aName);
	}

	protected void setUp() {

		final String sourcePath =
			"../PADL Creator JavaFile (Eclipse) Tests/rsc/PADL testdata/";
		final String[] javaFiles =
			new String[] { "../PADL Creator JavaFile (Eclipse) Tests/rsc/PADL testdata/padl/example/clazz/" };
		final String classPathEntry = "";

		this.model =
			Utils.createLightJavaFilesPadlModel(
				"",
				sourcePath,
				classPathEntry,
				javaFiles);
	}

	//test for classes
	public void testClasses() {
		final IClass clazz =
			(IClass) this.model
				.getTopLevelEntityFromID("padl.example.clazz.TestA");
		Assert.assertNotNull(clazz);

		final IClass clazz1 =
			(IClass) this.model
				.getTopLevelEntityFromID("padl.example.clazz.TestB");
		Assert.assertNotNull(clazz1);

		final IClass clazz2 =
			(IClass) this.model.getTopLevelEntityFromID("TestB");
		Assert.assertNull(clazz2);

		final IClass clazz3 =
			(IClass) this.model
				.getTopLevelEntityFromID("padl.example.clazz.Test3B");
		Assert.assertNull(clazz3);

		final int classesNumber = 2;
		try {
			Assert.assertEquals(classesNumber, this.model
				.getNumberOfTopLevelEntities(Class
					.forName("padl.kernel.impl.Class")));
		}
		catch (final ClassNotFoundException e) {
			e.printStackTrace(ProxyConsole.getInstance().errorOutput());
		}
	}

	//member class
	public void testClassMembers() {

		final IClass clazz =
			(IClass) this.model
				.getTopLevelEntityFromID("padl.example.clazz.TestB");
		Assert.assertNotNull(clazz);

		final IMemberClass memberClass =
			(IMemberClass) clazz.getConstituentFromName("MemberClass");
		Assert.assertNotNull(memberClass);

		final IMemberClass memberClass1 =
			(IMemberClass) clazz.getConstituentFromName("MemberClass1");
		Assert.assertNull(memberClass1);

		final IMethod method =
			(IMethod) memberClass.getConstituentFromName("m1");
		Assert.assertNotNull(method);

		final int parametersNumber = 3;
		try {
			Assert.assertEquals(parametersNumber, method
				.getNumberOfConstituents(Class
					.forName("padl.kernel.impl.Parameter")));
		}
		catch (final ClassNotFoundException e) {
			e.printStackTrace(ProxyConsole.getInstance().errorOutput());
		}

		//model comparator can't handle class member 
		this.model.walk(new ModelComparator(this.model));
	}

	//test for constructors
	//no added default constructor because contains one constructor
	public void testConstructors() {
		final IClass clazz =
			(IClass) this.model
				.getTopLevelEntityFromID("padl.example.clazz.TestA");

		final int constructorsNumber = 1;

		try {
			Assert.assertEquals(
				constructorsNumber,
				clazz.getNumberOfConstituents(Class
					.forName("padl.kernel.impl.Constructor"))
						- clazz.getNumberOfConstituents(Class
							.forName("padl.kernel.impl.Method")));
		}
		catch (final ClassNotFoundException e) {
			e.printStackTrace(ProxyConsole.getInstance().errorOutput());
		}

		final IConstructor c =
			(IConstructor) clazz.getConstituentFromName("TestA");
		Assert.assertNotNull(c);

		final IConstructor c1 =
			(IConstructor) clazz.getConstituentFromName("Test2A");
		Assert.assertNull(c1);
	}

	//Test - adding default constructor
	public void testDefaultConstructorAddedAutomatically() {
		final IClass clazz =
			(IClass) this.model
				.getTopLevelEntityFromID("padl.example.clazz.TestB");
		final IConstructor c =
			(IConstructor) clazz.getConstituentFromName("TestB");
		Assert.assertNotNull(c);
		final int constructorsNumber = 1;

		try {
			Assert.assertEquals(
				constructorsNumber,
				clazz.getNumberOfConstituents(Class
					.forName("padl.kernel.impl.Constructor"))
						- clazz.getNumberOfConstituents(Class
							.forName("padl.kernel.impl.Method")));
		}
		catch (final ClassNotFoundException e) {
			e.printStackTrace(ProxyConsole.getInstance().errorOutput());
		}
	}

	//test package and default package
	//test class in default package
	public void testDefaultPackages() {

		final String sourcePath =
			"../PADL Creator JavaFile (Eclipse) Tests/rsc/PADL testdata/";
		final String[] javaFiles =
			new String[] {
					"../PADL Creator JavaFile (Eclipse) Tests/rsc/Aminata testdata/MyDefaultClass.java",
					"../PADL Creator JavaFile (Eclipse) Tests/rsc/Aminata testdata/MyDefaultClass2.java" };
		final String classPathEntry = "";

		final ICodeLevelModel model =
			Utils.createLightJavaFilesPadlModel(
				"",
				sourcePath,
				classPathEntry,
				javaFiles);

		final IPackage packag =
			(IPackage) model.getConstituentFromID(Constants.DEFAULT_PACKAGE_ID);

		Assert.assertNotNull(packag);

		//	try {
		//		int numberOfPackagesExpected = 1;
		//		int numberOfPackagesActual =
		//			model.getNumberOfConstituents(Class
		//				.forName("padl.kernel.impl.Package"))
		//					- model.getNumberOfConstituents(Class
		//						.forName("padl.kernel.impl.PackageGhost"));
		//
		//	}
		//	catch (ClassNotFoundException e) {
		//		e.printStackTrace(Output.getInstance().errorOutput());
		//	}

		final IClass clazz1 =
			(IClass) packag.getConstituentFromName("MyDefaultClass");
		Assert.assertNotNull(clazz1);
		final IClass clazz2 =
			(IClass) packag.getConstituentFromName("MyDefaultClass2");
		Assert.assertNotNull(clazz2);

		final IClass clazz3 =
			(IClass) packag.getConstituentFromName("MyDefaultClass1");
		Assert.assertNull(clazz3);

		final int nomberOfConstituentsExpected = 2;
		final int nomberOfConstituentsActual = packag.getNumberOfConstituents();

		Assert.assertEquals(
			nomberOfConstituentsExpected,
			nomberOfConstituentsActual);

	}

	//Fields
	public void testFields() {
		final IClass clazz =
			(IClass) this.model
				.getTopLevelEntityFromID("padl.example.clazz.TestA");
		final int fieldsNumber = 4;

		try {
			Assert.assertEquals(fieldsNumber, clazz
				.getNumberOfConstituents(Class
					.forName("padl.kernel.impl.Field")));
		}
		catch (final ClassNotFoundException e) {
			e.printStackTrace(ProxyConsole.getInstance().errorOutput());
		}

		final IField f = (IField) clazz.getConstituentFromName("s");
		Assert.assertNotNull(f);

		final IField f1 = (IField) clazz.getConstituentFromName("str");
		Assert.assertNull(f1);

	}

	//test for ghosts
	public void testGhosts() {
		final IGhost ghost =
			(IGhost) this.model
				.getTopLevelEntityFromID("padl.example.clazz1.Test2A");
		Assert.assertNotNull(ghost);

		final IGhost ghost1 =
			(IGhost) this.model
				.getTopLevelEntityFromID("unknown.ghost.packag.C");
		Assert.assertNotNull(ghost1);

		final IGhost ghost2 =
			(IGhost) this.model
				.getTopLevelEntityFromID("unknown.ghost.packag.K");
		Assert.assertNull(ghost2);

		final IGhost ghost3 = (IGhost) this.model.getTopLevelEntityFromID("K");
		Assert.assertNull(ghost3);

	}

	//methods parameters
	public void testmethodParameters() {
		final IClass clazz =
			(IClass) this.model
				.getTopLevelEntityFromID("padl.example.clazz.TestA");

		final IMethod method = (IMethod) clazz.getConstituentFromName("setA2");
		final int parametersNumber = 1;

		try {
			Assert.assertEquals(parametersNumber, method
				.getNumberOfConstituents(Class
					.forName("padl.kernel.impl.Parameter")));
		}
		catch (final ClassNotFoundException e) {
			e.printStackTrace(ProxyConsole.getInstance().errorOutput());
		}

		final IParameter p = (IParameter) method.getConstituentFromName("_a2");
		Assert.assertNotNull(p);

		Assert.assertEquals("padl.example.clazz.TestB", p.getDisplayTypeName());
		final IParameter p1 = (IParameter) method.getConstituentFromName("_a");
		Assert.assertNull(p1);
	}

	//methods
	public void testmethods() {
		final IClass clazz =
			(IClass) this.model
				.getTopLevelEntityFromID("padl.example.clazz.TestA");
		final int methodsNumberExpected = 2;
		int methodsNumberActual;
		try {
			methodsNumberActual =
				clazz.getNumberOfConstituents(Class
					.forName("padl.kernel.impl.Method"));

			Assert.assertEquals(methodsNumberExpected, methodsNumberActual);
		}
		catch (final ClassNotFoundException e) {
			e.printStackTrace(ProxyConsole.getInstance().errorOutput());
		}

		//method name
		final IMethod method = (IMethod) clazz.getConstituentFromName("setA2");
		Assert.assertNotNull(method);

		//method name
		final IMethod method1 = (IMethod) clazz.getConstituentFromName("foo");
		Assert.assertNull(method1);
	}

	//test inheritance and implementation (see in MemberGhostTest)

}
