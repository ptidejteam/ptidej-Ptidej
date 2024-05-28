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
package padl.creator.cppfile.antlr.test;

import junit.framework.Test;
import junit.framework.TestSuite;
import padl.creator.cppfile.antlr.test.all.TestCPPCreator;
import padl.kernel.cpp.antlr.ICPPFactoryANTLR;
import padl.kernel.cpp.antlr.impl.CPPFactoryANTLR;

/**
 * @author robidose
 */
public class TestCreatorCPPFileUsingANTLR extends TestSuite {
	public TestCreatorCPPFileUsingANTLR() {
	}
	public TestCreatorCPPFileUsingANTLR(final Class theClass) {
		super(theClass);
	}
	public TestCreatorCPPFileUsingANTLR(final String name) {
		super(name);
	}
	public static Test suite() {
		CppPrimitive.setFactory((ICPPFactoryANTLR) CPPFactoryANTLR
			.getInstance());

		final TestCreatorCPPFileUsingANTLR suite =
			new TestCreatorCPPFileUsingANTLR();
		suite.addTest(TestCPPCreator.suite());
		return suite;
	}
}
