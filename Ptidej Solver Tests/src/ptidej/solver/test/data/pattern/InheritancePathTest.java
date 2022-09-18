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
package ptidej.solver.test.data.pattern;

import padl.kernel.IClass;
import padl.motif.models.TestMotifModel;

public final class InheritancePathTest extends TestMotifModel {
	private static final char[] INHERITANCE_PATH_TEST = "InheritancePathTest"
		.toCharArray();
	private static final long serialVersionUID = -6700186454979650085L;
	public static final char[] SUB_ENTITY = "SubEntity".toCharArray();
	public static final char[] SUPER_ENTITY = "SuperEntity".toCharArray();

	public InheritancePathTest() {
		final IClass superclass =
			this.getFactory().createClass(
				InheritancePathTest.SUPER_ENTITY,
				InheritancePathTest.SUPER_ENTITY);
		final IClass subclass =
			this.getFactory().createClass(
				InheritancePathTest.SUB_ENTITY,
				InheritancePathTest.SUB_ENTITY);
		subclass.addInheritedEntity(superclass);
		this.addConstituent(superclass);
		this.addConstituent(subclass);
	}
	public char[] getName() {
		return InheritancePathTest.INHERITANCE_PATH_TEST;
	}
}
