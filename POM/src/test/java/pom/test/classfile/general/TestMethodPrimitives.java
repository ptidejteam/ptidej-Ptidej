/*******************************************************************************
 * Copyright (c) 2001-2014 Yann-Gaël Guéhéneuc  and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/old-licenses/gpl-2.0.html
 * 
 * Contributors:
 *     Yann-Gaël Guéhéneuc  and others, see in file; API and its implementation
 ******************************************************************************/
package pom.test.classfile.general;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;

import padl.creator.classfile.CompleteClassFileCreator;
import padl.kernel.ICodeLevelModel;
import padl.kernel.IFirstClassEntity;
import padl.kernel.IMethod;
import padl.kernel.impl.Factory;
import pom.operators.Operators;
import pom.primitives.MethodPrimitives;

/**
 * @author Farouk ZAIDI - 2004-02-05
 * zaidifar - MetricsByPrimitives
 *
 */
public class TestMethodPrimitives extends junit.framework.TestCase {
	private final String root = "../POM/target/test-classes/Metric Specific for Java/bin/pom/test/rsc/";
	private final String id1 = "MethodDump";
	private final String id2 = "MethodDumpv2";
	private ICodeLevelModel currentModel;
	private final Operators operators = Operators.getInstance();

	public TestMethodPrimitives(final String aName) {
		super(aName);
	}

	/*
	 * @see TestCase#setUp()
	 */
	protected void setUp() throws Exception {
		super.setUp();
		if (this.currentModel == null) {
			this.currentModel =
				Factory.getInstance().createCodeLevelModel(
					"Test.TestMethodPrimitives");
			this.currentModel.create(new CompleteClassFileCreator(
				new String[] { this.root }));
		}
	}

	public void testHandledFieldsBy() {
		final String methodName = "doNothing()";
		final String[] usedFields = { "str", "p", "str" };
		final IFirstClassEntity iEntity =
			(IFirstClassEntity) this.currentModel
				.getTopLevelEntityFromID("pom.test.rsc." + this.id1);
		final List expectedList = new ArrayList();
		for (int i = 0; i < usedFields.length; i++) {
			expectedList.add(iEntity.getConstituentFromID(usedFields[i]));
		}

		final MethodPrimitives mp = MethodPrimitives.getInstance();
		final IMethod method =
			(IMethod) iEntity.getConstituentFromID(methodName);
		final List list = mp.listOfFieldsUsedByMethod(iEntity, method);

		Assert.assertTrue(this.operators.equal(list, expectedList));
	}

	public void testMI() {
		final String methodName = "doNothing()";
		final String[] usedFields =
			{ "p", "p", "p", "p", "p", "p", "p", "p", "p", "p", "p", "p",
					"p", "p", "p", "a", "p", "p", "a", "f3", "f3", "f3", "p" };
		final IFirstClassEntity iEntity =
			(IFirstClassEntity) this.currentModel
				.getTopLevelEntityFromID("pom.test.rsc." + this.id2);
		final List expectedList = new ArrayList();
		for (int i = 0; i < usedFields.length; i++) {
			expectedList.add(iEntity.getConstituentFromID(usedFields[i]));
		}

		final MethodPrimitives mp = MethodPrimitives.getInstance();
		final IMethod method =
			(IMethod) iEntity.getConstituentFromID(methodName);
		final List list = mp.listOfFieldsUsedByMethod(iEntity, method);

		Assert.assertTrue(this.operators.equal(list, expectedList));
	}

	public void testInvokedMethods() {
		MethodPrimitives mp = MethodPrimitives.getInstance();
		IFirstClassEntity iEntity =
			(IFirstClassEntity) this.currentModel
				.getTopLevelEntityFromID("pom.test.rsc.A");
		IFirstClassEntity targetEntity =
			(IFirstClassEntity) this.currentModel
				.getTopLevelEntityFromID("pom.test.rsc.B");

		String methodName = "fooA1()";
		String[] invokedMethods = { "foo1()" };
		IMethod method = (IMethod) iEntity.getConstituentFromID(methodName);
		List reference =
			mp.listOfSisterMethodCalledByMethod(targetEntity, method);
		String[] arrayReference = new String[reference.size()];
		int length = arrayReference.length;
		for (int i = 0; i < length; i++) {
			arrayReference[i] = ((IMethod) (reference.get(i))).getDisplayID();
		}
		Assert.assertTrue(this.compareArrays(invokedMethods, arrayReference));

		methodName = "fooA2()";
		String[] invokedMethods2 = { "foo4()", "foo3()", "foo6()" };
		method = (IMethod) iEntity.getConstituentFromID(methodName);
		reference = mp.listOfSisterMethodCalledByMethod(targetEntity, method);
		arrayReference = new String[reference.size()];
		length = arrayReference.length;
		for (int i = 0; i < length; i++) {
			arrayReference[i] = ((IMethod) (reference.get(i))).getDisplayID();
		}
		Assert.assertTrue(this.compareArrays(invokedMethods2, arrayReference));
	}
	private boolean compareArrays(String[] waitedResult, String[] reference) {
		if (waitedResult.length != reference.length)
			return false;
		for (int i = 0; i < waitedResult.length; i++) {
			if (!waitedResult[i].equals(reference[i])) {
				System.out.println("Problem with the number " + i);
				return false;
			}
		}
		return true;
	}
}
