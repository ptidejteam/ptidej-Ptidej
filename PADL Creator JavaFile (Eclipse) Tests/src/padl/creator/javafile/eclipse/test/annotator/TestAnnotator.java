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
package padl.creator.javafile.eclipse.test.annotator;

import junit.framework.Test;
import junit.framework.TestSuite;

public class TestAnnotator extends TestSuite {
	public static Test suite() {

		final TestAnnotator suite = new TestAnnotator();
		suite.addTestSuite(ConditionalModelAnnotatorTest.class);
		suite.addTestSuite(LOCModelAnnotatorTest.class);

		return suite;
	}
	public TestAnnotator() {
	}
	public TestAnnotator(final Class theClass) {
		super(theClass);
	}
	public TestAnnotator(final String name) {
		super(name);
	}
}
