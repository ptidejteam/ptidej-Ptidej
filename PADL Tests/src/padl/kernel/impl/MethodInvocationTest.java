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
package padl.kernel.impl;

import java.util.HashSet;
import junit.framework.TestCase;
import padl.kernel.IFirstClassEntity;
import padl.kernel.IMethodInvocation;
import com.ibm.toad.cfparse.utils.Access;

/**
 * Tests contract for a MethodInvocation.
 * 
 * @author Stephane Vaucher
 * @since  2005/11/25
 */
public class MethodInvocationTest extends TestCase {
	public MethodInvocationTest(final String aName) {
		super(aName);
	}

	/*
	 * Test method for 'padl.kernel.impl.MethodInvocation.equals(IMethodInvocation)'
	 */
	public void testEqualsIMethodInvocation() {
		IFirstClassEntity firstClassEntity =
			new FirstClassEntity("foo".toCharArray()) {
				private static final long serialVersionUID =
					-2438802743951158575L;
			};

		final MethodInvocation methodInv1 =
			new MethodInvocation(
				IMethodInvocation.INSTANCE_CLASS,
				0,
				Access.ACC_PUBLIC,
				firstClassEntity);
		final MethodInvocation methodInv2 =
			new MethodInvocation(
				IMethodInvocation.INSTANCE_CLASS,
				0,
				Access.ACC_PUBLIC,
				firstClassEntity);

		assertEquals(
			"Methods with same target and type should be equivalent.",
			methodInv1,
			methodInv2);

		HashSet set = new HashSet(2);
		set.add(methodInv1);
		set.add(methodInv2);

		assertEquals(
			"Two equals method invocations cannot coexist in a set.",
			1,
			set.size());
	}
}
