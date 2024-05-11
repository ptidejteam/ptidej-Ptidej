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
package padl.creator.test.relationships.providers;

import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;
import padl.kernel.IField;
import padl.kernel.IFirstClassEntity;
import padl.kernel.IMethod;
import padl.kernel.IMethodInvocation;
import padl.kernel.impl.Factory;

public class Provider_CLASS_INSTANCE_FROM_FIELD_ONE extends AbstractProvider
		implements ITestProvider {

	public String getHelperClassName() {
		return "padl.creator.test.relationships.providers.A";
	}
	public IMethodInvocation getExpectedMethodInvocation() {
		final IFirstClassEntity targetEntity =
			Factory.getInstance().createClass(
				"padl.creator.test.relationships.providers.A".toCharArray(),
				"A".toCharArray());

		final IFirstClassEntity fieldDeclaringEntity =
			Factory
				.getInstance()
				.createClass(
					"padl.creator.test.relationships.providers.Test_CLASS_INSTANCE_FROM_FIELD_ONE".toCharArray(),
					"Test_CLASS_INSTANCE_FROM_FIELD_ONE".toCharArray());

		final IMethodInvocation methodInvocation =
			Factory.getInstance().createMethodInvocation(
				IMethodInvocation.CLASS_INSTANCE_FROM_FIELD,
				padl.kernel.Constants.CARDINALITY_ONE,
				Modifier.PUBLIC + Modifier.STATIC,
				targetEntity,
				fieldDeclaringEntity);

		final IMethod calledMethod =
			Factory.getInstance().createMethod(
				"instanceMethod()".toCharArray(),
				"instanceMethod".toCharArray());
		methodInvocation.setCalledMethod(calledMethod);
		final IField invocationField =
			Factory.getInstance().createField(
				"a".toCharArray(),
				"a".toCharArray(),
				"padl.creator.test.relationships.providers.A".toCharArray(),
				padl.kernel.Constants.CARDINALITY_ONE);
		final List listCallingFields = new ArrayList();
		listCallingFields.add(invocationField);
		methodInvocation.setCallingField(listCallingFields);

		return methodInvocation;
	}
}

class Test_CLASS_INSTANCE_FROM_FIELD_ONE {
	private static A a;

	public static void foo() {
		Test_CLASS_INSTANCE_FROM_FIELD_ONE.a.instanceMethod();
	}
}
