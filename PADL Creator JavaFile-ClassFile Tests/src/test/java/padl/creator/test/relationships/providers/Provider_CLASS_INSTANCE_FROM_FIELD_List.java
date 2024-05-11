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

public class Provider_CLASS_INSTANCE_FROM_FIELD_List extends AbstractProvider
		implements ITestProvider {

	public String getHelperClassName() {
		return "padl.creator.test.relationships.providers.VectorTest";
	}
	public IMethodInvocation getExpectedMethodInvocation() {
		final IFirstClassEntity targetEntity =
			Factory.getInstance().createClass(
				"java.util.List".toCharArray(),
				"List".toCharArray());

		final IFirstClassEntity fieldDeclaringEntity =
			Factory
				.getInstance()
				.createClass(
					"padl.creator.test.relationships.providers.Test_CLASS_INSTANCE_FROM_FIELD_List".toCharArray(),
					"Test_CLASS_INSTANCE_FROM_FIELD_List".toCharArray());

		final IMethodInvocation methodInvocation =
			Factory.getInstance().createMethodInvocation(
				IMethodInvocation.CLASS_INSTANCE_FROM_FIELD,
				padl.kernel.Constants.CARDINALITY_MANY,
				Modifier.PUBLIC + Modifier.STATIC,
				targetEntity,
				fieldDeclaringEntity);

		final IMethod calledMethod =
			Factory.getInstance().createMethod(
				"get(int)".toCharArray(),
				"get".toCharArray());
		methodInvocation.setCalledMethod(calledMethod);
		final IField invocationField =
			Factory.getInstance().createField(
				"list".toCharArray(),
				"list".toCharArray(),
				"java.util.List".toCharArray(),
				padl.kernel.Constants.CARDINALITY_MANY);
		final List listCallingFields = new ArrayList();
		listCallingFields.add(invocationField);
		methodInvocation.setCallingField(listCallingFields);

		return methodInvocation;
	}
}

class Test_CLASS_INSTANCE_FROM_FIELD_List {
	private static List list;

	public static void foo() {
		Test_CLASS_INSTANCE_FROM_FIELD_List.list.get(0);
	}
}
