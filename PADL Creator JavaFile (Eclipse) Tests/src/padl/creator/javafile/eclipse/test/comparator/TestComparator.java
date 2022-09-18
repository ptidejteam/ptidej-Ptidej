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
package padl.creator.javafile.eclipse.test.comparator;

import junit.framework.Test;
import junit.framework.TestSuite;

public class TestComparator extends TestSuite {
	public static Test suite() {
		final TestComparator suite = new TestComparator();
		suite.addTestSuite(LightModelsWithStatisticVisitor.class);
		suite.addTestSuite(ModelsDifferencesReporter.class);
		suite.addTestSuite(ClassAndFileConventionTest.class);
		suite.addTestSuite(LightModelsWithModelComparatorTest.class);
		return suite;
	}
	public TestComparator() {
	}
	public TestComparator(final Class theClass) {
		super(theClass);
	}
	public TestComparator(final String name) {
		super(name);
	}
}
