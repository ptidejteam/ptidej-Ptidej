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
import padl.kernel.IFirstClassEntity;
import padl.kernel.IMethod;
import padl.kernel.IMethodInvocation;
import padl.kernel.impl.Factory;

public class Provider_INSTANCE_CLASS_MANY extends AbstractProvider implements
		ITestProvider {

	public String getHelperClassName() {
		return "padl.creator.test.relationships.providers.A";
	}
	public IMethodInvocation getExpectedMethodInvocation() {
		final IFirstClassEntity targetEntity =
			Factory.getInstance().createClass(
				"padl.creator.test.relationships.providers.A".toCharArray(),
				"A".toCharArray());

		final IMethodInvocation methodInvocation =
			Factory.getInstance().createMethodInvocation(
				IMethodInvocation.INSTANCE_CLASS,
				padl.kernel.Constants.CARDINALITY_MANY,
				Modifier.PUBLIC,
				targetEntity);

		final IMethod calledMethod =
			Factory.getInstance().createMethod(
				"staticMethod()".toCharArray(),
				"staticMethod".toCharArray());
		methodInvocation.setCalledMethod(calledMethod);

		return methodInvocation;
	}
}

class Test_INSTANCE_CLASS_MANY {
	private A[] a;

	public void foo() {
		this.a[0].staticMethod();
	}
}
