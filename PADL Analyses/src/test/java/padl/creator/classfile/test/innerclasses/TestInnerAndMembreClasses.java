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
package padl.creator.classfile.test.innerclasses;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * @author Yann-Gaël Guéhéneuc
 * @since  2005/08/14
 */
public class TestInnerAndMembreClasses extends TestSuite {
	public static Test suite() {
		final TestInnerAndMembreClasses suite = new TestInnerAndMembreClasses();
		suite.addTest(new TestInnerAndMembreClasses(TestInnerClasses.class));
		suite.addTest(new TestInnerAndMembreClasses(TestMemberClasses.class));
		suite.addTest(new TestInnerAndMembreClasses(TestMemberClasses2.class));
		suite.addTest(new TestInnerAndMembreClasses(TestMemberClasses3.class));
		suite.addTest(new TestInnerAndMembreClasses(TestMemberClasses4.class));
		suite.addTest(new TestInnerAndMembreClasses(TestMemberClasses5.class));
		suite.addTest(new TestInnerAndMembreClasses(TestMemberEntities.class));
		suite.addTest(new TestInnerAndMembreClasses(TestMemberInterfaces.class));
		return suite;
	}
	public TestInnerAndMembreClasses() {
	}
	public TestInnerAndMembreClasses(final Class theClass) {
		super(theClass);
	}
	public TestInnerAndMembreClasses(final String aName) {
		super(aName);
	}
}
