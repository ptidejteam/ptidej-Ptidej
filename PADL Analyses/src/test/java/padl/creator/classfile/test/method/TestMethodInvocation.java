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
package padl.creator.classfile.test.method;

import org.junit.Assert;

import junit.framework.TestCase;
import padl.creator.classfile.CompleteClassFileCreator;
import padl.kernel.IClass;
import padl.kernel.ICodeLevelModel;
import padl.kernel.IMethod;
import padl.kernel.IMethodInvocation;
import padl.kernel.impl.Factory;

/**
 * Test for methods wrongly included in an IClass
 * after being parsed by the PADL Creator ClassFile.
 * 
 * @author Stephane Vaucher
 * @author Yann-Gaël Guéhéneuc
 * @since  2005/11/11
 */
public class TestMethodInvocation extends TestCase {
	private static ICodeLevelModel CodeLevelModel;

	public TestMethodInvocation(final String aName) {
		super(aName);
	}

	public void setUp() throws Exception {
		if (TestMethodInvocation.CodeLevelModel == null) {
			TestMethodInvocation.CodeLevelModel = Factory.getInstance()
					.createCodeLevelModel("");
			TestMethodInvocation.CodeLevelModel
					.create(new CompleteClassFileCreator(new String[] {
							"../PADL Creator ClassFile Tests/bin/padl/example/method/MethodInvocation.class" }));
		}
	}

	public void testMethodInvocation1() {
		Assert.assertEquals("Number of entities", 2,
				TestMethodInvocation.CodeLevelModel.getNumberOfConstituents());
	}

	public void testMethodInvocation2() {
		final IClass clazz = (IClass) TestMethodInvocation.CodeLevelModel
				.getTopLevelEntityFromID(
						"padl.example.method.MethodInvocation");
		Assert.assertEquals("Number of elemets", 5,
				clazz.getNumberOfConstituents());
	}

	public void testMethodInvocation3() {
		final IClass clazz = (IClass) TestMethodInvocation.CodeLevelModel
				.getTopLevelEntityFromID("padl.example.method.MethodInvocation");
		final IMethod method = (IMethod) clazz
				.getConstituentFromName("SomeMethod".toCharArray());
		Assert.assertEquals("Number of elemets", 2,
				method.getNumberOfConstituents());
		Assert.assertEquals("Assignation", "=",
				((IMethodInvocation) method
						.getConstituentFromID("Method Invocation_>PADL<_1"))
						.getCalledMethod().getDisplayName());
		Assert.assertEquals("Assignation", "=",
				((IMethodInvocation) method
						.getConstituentFromID("Method Invocation_>PADL<_2"))
						.getCalledMethod().getDisplayName());
	}
}
