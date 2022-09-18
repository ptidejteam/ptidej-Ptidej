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

import junit.framework.Test;
import junit.framework.TestSuite;

public class TestBasic extends TestSuite {
	public static Test suite() {
		final TestBasic suite = new TestBasic();
		suite.addTestSuite(ClassTest.class);
		suite.addTestSuite(InterfaceTest.class);
		suite.addTestSuite(LightModelCreatorTest.class);
		suite.addTestSuite(MemberGhostTest.class);
		suite.addTestSuite(ManyClassesInOneFileTest.class);
		suite.addTestSuite(PackagesTest.class);
		suite.addTestSuite(ParametrizedTypesTest.class);
		suite.addTestSuite(IndirectSourcePathTest.class);
		return suite;
	}
	public TestBasic() {
	}
	public TestBasic(final Class theClass) {
		super(theClass);
	}
	public TestBasic(final String name) {
		super(name);
	}
}
