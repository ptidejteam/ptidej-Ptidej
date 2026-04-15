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
package padl.creator.classfile.test;

import junit.framework.TestSuite;
import padl.creator.classfile.test.compare.ComparisonTest;
import padl.creator.classfile.test.fieldaccess.FieldAccessTest;
import padl.creator.classfile.test.inheritance.InheritanceTest;
import padl.creator.classfile.test.methodInvocation.MethodInvocationMissingTest;
import padl.creator.classfile.test.path.ArgoUMLPathTest;
import padl.creator.classfile.test.topLevelEntity.TopLevelEntityTest;

/**
 * @author Yann-Gaël Guéhéneuc
 * @since  2004/01/25
 */
public final class TestCreatorClassFile extends TestSuite {
	public static TestSuite suite() {
		final TestCreatorClassFile suite = new TestCreatorClassFile();
		suite.setName(TestCreatorClassFile.class.getName());

		suite.addTestSuite(ComparisonTest.class);
		suite.addTestSuite(FieldAccessTest.class);
		suite.addTestSuite(InheritanceTest.class);
		suite.addTestSuite(MethodInvocationMissingTest.class);
		suite.addTestSuite(ArgoUMLPathTest.class);
		suite.addTestSuite(TopLevelEntityTest.class);

		return suite;
	}
}
