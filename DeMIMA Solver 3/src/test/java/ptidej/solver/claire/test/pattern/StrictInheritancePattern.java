/*******************************************************************************
 * Copyright (c) 2001-2014 Yann-Gaël Guéhéneuc  and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/old-licenses/gpl-2.0.html
 * 
 * Contributors:
 *     Yann-Gaël Guéhéneuc  and others, see in file; API and its implementation
 ******************************************************************************/
package ptidej.solver.claire.test.pattern;

import padl.kernel.IClass;
import padl.motif.models.TestMotifModel;

public final class StrictInheritancePattern extends TestMotifModel {
	private static final long serialVersionUID = -5989354895228483331L;
	private static final char[] STRICT_INHERITANCE_TEST =
		"StrictInheritanceTest".toCharArray();
	public static final char[] SUB_ENTITY = "SubEntity".toCharArray();
	public static final char[] SUPER_ENTITY = "SuperEntity".toCharArray();

	public StrictInheritancePattern() {
		final IClass superclass =
			this.getFactory().createClass(
				StrictInheritancePattern.SUPER_ENTITY,
				StrictInheritancePattern.SUPER_ENTITY);
		final IClass subclass =
			this.getFactory().createClass(
				StrictInheritancePattern.SUB_ENTITY,
				StrictInheritancePattern.SUB_ENTITY);
		subclass.addInheritedEntity(superclass);
		this.addConstituent(superclass);
		this.addConstituent(subclass);
	}
	public char[] getName() {
		return StrictInheritancePattern.STRICT_INHERITANCE_TEST;
	}
}
