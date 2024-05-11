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
package padl.creator.classfile.test.visitor;

import junit.framework.Test;

public final class TestVisitor extends junit.framework.TestSuite {
	public TestVisitor() {
	}
	public TestVisitor(final Class theClass) {
		super(theClass);
	}
	public TestVisitor(final String name) {
		super(name);
	}
	public static Test suite() {
		final TestVisitor suite = new TestVisitor();
		suite.addTest(new TestVisitor(Composite1.class));
		return suite;
	}
}
