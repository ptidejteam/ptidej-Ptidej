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
package pom.test.classfile.specific;

import org.junit.Assert;

import junit.framework.TestCase;
import padl.kernel.IAbstractLevelModel;
import padl.kernel.IClass;
import padl.kernel.IField;
import padl.kernel.IMethod;
import padl.kernel.IMethodInvocation;
import padl.kernel.IPackage;
import padl.kernel.impl.Factory;
import pom.metrics.IUnaryMetric;
import pom.metrics.MetricsRepository;

/**
 * Basic test case for CBO that is implementented using 
 * simple PADL components like fields, methods, and 
 * method invocations.
 * 
 * This test would **NOT** function with an implementation
 * that uses complex entities like IUseRelationships.
 * 
 * @author vauchers
 */
public class TestUnaryCBO extends TestCase {
	/** CBO **/
	private static IUnaryMetric Metric;

	/** Class under test **/
	private static IClass TestedClass;

	/** Classes representing different types of relationships **/
	private static IClass InheritedClass;
	private static IClass FieldClass;
	private static IClass UsedClass;

	/** Test model **/
	private static IAbstractLevelModel Model;

	public TestUnaryCBO(final String aName) {
		super(aName);
	}

	/**
	 * Class with no incoming or outgoing coupling
	 *  
	 * Test method for 'pom.metrics.impl.CBO.compute(TestUnaryCBO.Model,IEntity)'
	 */
	public void testNoCoupling() {
		Assert.assertEquals(
				"No coupling from this class", 0.0d, TestUnaryCBO.Metric
						.compute(TestUnaryCBO.Model, TestUnaryCBO.TestedClass),
				1.0d);

	}

	/**
	 * Class inherits of another with no method invocation /field access
	 * 
	 * Test method for 'pom.metrics.impl.CBO.compute(TestUnaryCBO.Model,IEntity)'
	 */
	public void testInheritance() {
		TestUnaryCBO.TestedClass
				.addInheritedEntity(TestUnaryCBO.InheritedClass);
		Assert.assertEquals("A link through inheritence does not count", 0.0d,
				TestUnaryCBO.Metric.compute(TestUnaryCBO.Model,
						TestUnaryCBO.TestedClass),
				1.0d);
		TestUnaryCBO.TestedClass
				.removeInheritedEntity(TestUnaryCBO.InheritedClass);
	}

	/**
	 * Class has a field that is not used 
	 * 
	 * Test method for 'pom.metrics.impl.CBO.compute(TestUnaryCBO.Model,IEntity)'
	 */
	public void testNotUsedField() {
		final IField field = getUniqueField(TestUnaryCBO.TestedClass);
		field.setType(TestUnaryCBO.FieldClass.getID());

		Assert.assertEquals("A field should not count unless it is used.", 0.0d,
				TestUnaryCBO.Metric.compute(TestUnaryCBO.Model,
						TestUnaryCBO.TestedClass),
				1.0d);
	}

	/**
	 * Class has a field that it uses.
	 * 
	 * Test method for 'pom.metrics.impl.CBO.compute(TestUnaryCBO.Model,IEntity)'
	 */
	public void testUsedFieldByMethod() {
		final IField field = this.getUniqueField(TestUnaryCBO.TestedClass);
		field.setType(TestUnaryCBO.FieldClass.getID());

		final IMethod method = this.getUniqueMethod(TestUnaryCBO.TestedClass);
		final IMethodInvocation mi = Factory.getInstance()
				.createMethodInvocation(
						IMethodInvocation.CLASS_INSTANCE_FROM_FIELD, 1, 0,
						TestUnaryCBO.FieldClass);
		mi.setPublic(true);

		final IMethod methodInvoked = getUniqueMethod(FieldClass);
		mi.setCalledMethod(methodInvoked);

		Assert.assertEquals(methodInvoked, mi.getCalledMethod());
		method.addConstituent(mi);

		Assert.assertEquals("A field should not count unless it is used.", 0.0d,
				TestUnaryCBO.Metric.compute(TestUnaryCBO.Model,
						TestUnaryCBO.TestedClass),
				1.0d);
	}

	protected void setUp() throws Exception {
		super.setUp();

		if (TestUnaryCBO.Model == null) {
			TestUnaryCBO.Model = Factory.getInstance()
					.createCodeLevelModel("Model");

			TestUnaryCBO.TestedClass = Factory.getInstance()
					.createClass("Class".toCharArray(), "Class".toCharArray());
			TestUnaryCBO.InheritedClass = Factory.getInstance().createClass(
					"InheritedClass".toCharArray(),
					"InheritedClass".toCharArray());
			TestUnaryCBO.UsedClass = Factory.getInstance().createClass(
					"UsedClass".toCharArray(), "UsedClass".toCharArray());
			TestUnaryCBO.FieldClass = Factory.getInstance().createClass(
					"FieldClass".toCharArray(), "FieldClass".toCharArray());

			this.setupClass(TestUnaryCBO.TestedClass);
			this.setupClass(TestUnaryCBO.InheritedClass);
			this.setupClass(TestUnaryCBO.FieldClass);
			this.setupClass(TestUnaryCBO.UsedClass);

			final IPackage enclosingPackage = Factory.getInstance()
					.createPackage("(POM test default)".toCharArray());
			TestUnaryCBO.Model.addConstituent(enclosingPackage);

			enclosingPackage.addConstituent(TestUnaryCBO.TestedClass);
			enclosingPackage.addConstituent(TestUnaryCBO.InheritedClass);
			enclosingPackage.addConstituent(TestUnaryCBO.FieldClass);
			enclosingPackage.addConstituent(TestUnaryCBO.UsedClass);

			TestUnaryCBO.Metric = (IUnaryMetric) MetricsRepository.getInstance()
					.getMetric("CBO");
		}
	}

	// Utility methods to access unique fields and methods
	// of different classes created
	private IField getUniqueField(final IClass clazz) {
		return (IField) clazz.getConstituentFromID(this.fieldId(clazz));
	}

	private IMethod getUniqueMethod(final IClass clazz) {
		return (IMethod) clazz.getConstituentFromID(this.methodId(clazz));
	}

	private String fieldId(final IClass clazz) {
		return clazz.getDisplayName() + "-field";
	}

	private String methodId(final IClass clazz) {
		return clazz.getDisplayName() + "-method";
	}

	/**
	 * Gives a class a field and a method
	 * 
	 * @param clazz
	 */
	private void setupClass(final IClass clazz) {
		final IMethod method = Factory.getInstance().createMethod(
				this.methodId(clazz).toCharArray(),
				this.methodId(clazz).toCharArray());
		final IField field = Factory.getInstance().createField(
				this.fieldId(clazz).toCharArray(),
				this.fieldId(clazz).toCharArray(), "int".toCharArray(), 1);

		clazz.addConstituent(method);
		clazz.addConstituent(field);
	}

}
