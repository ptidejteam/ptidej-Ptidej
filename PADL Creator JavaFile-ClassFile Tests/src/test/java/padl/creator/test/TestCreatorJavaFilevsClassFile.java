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
/**
 * (c) Copyright 2001-2004 Yann-Gaël Guéhéneuc,
 * University of Montreal.
 * 
 * Use and copying of this software and preparation of derivative works
 * based upon this software are permitted. Any copy of this software or
 * of any derivative work must include the above copyright notice of
 * the author, this paragraph and the one after it.
 * 
 * This software is made available AS IS, and THE AUTHOR DISCLAIMS
 * ALL WARRANTIES, EXPRESS OR IMPLIED, INCLUDING WITHOUT LIMITATION THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
 * PURPOSE, AND NOT WITHSTANDING ANY OTHER PROVISION CONTAINED HEREIN,
 * ANY LIABILITY FOR DAMAGES RESULTING FROM THE SOFTWARE OR ITS USE IS
 * EXPRESSLY DISCLAIMED, WHETHER ARISING IN CONTRACT, TORT (INCLUDING
 * NEGLIGENCE) OR STRICT LIABILITY, EVEN IF THE AUTHOR IS ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGES.
 * 
 * All Rights Reserved.
 */
package padl.creator.test;

import junit.framework.Test;
import junit.framework.TestSuite;
import padl.creator.test.relationships.RelationshipsTest;

/**
 * @author Yann-Gaël Guéhéneuc
 * @since  2004/01/25
 */
public final class TestCreatorJavaFilevsClassFile extends TestSuite {
	public static Test suite() {
		final TestCreatorJavaFilevsClassFile suite =
			new TestCreatorJavaFilevsClassFile();
		suite.addTestSuite(padl.creator.test.comparison.jct.Composite1.class);
		suite
			.addTestSuite(padl.creator.test.comparison.eclipse.Composite1.class);
		suite.addTestSuite(RelationshipsTest.class);
		return suite;
	}
	public TestCreatorJavaFilevsClassFile() {
	}
	public TestCreatorJavaFilevsClassFile(final Class<?> theClass) {
		super(theClass);
	}
	public TestCreatorJavaFilevsClassFile(final String name) {
		super(name);
	}
}
