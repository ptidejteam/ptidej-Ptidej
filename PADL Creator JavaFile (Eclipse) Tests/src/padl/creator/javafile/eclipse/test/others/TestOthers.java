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
package padl.creator.javafile.eclipse.test.others;

import junit.framework.Test;
import junit.framework.TestSuite;

public class TestOthers extends TestSuite {
	public static Test suite() {
		final TestOthers suite = new TestOthers();
		suite.addTestSuite(CompleteCreatorTest.class);
		suite.addTestSuite(EclipseSnapshotTest.class);
		suite.addTestSuite(PADLParserUtilsTest.class);
		suite.addTestSuite(ArgoUMLTest.class);
		return suite;
	}
	public TestOthers() {
	}
	public TestOthers(final Class theClass) {
		super(theClass);
	}
	public TestOthers(final String name) {
		super(name);
	}
}
