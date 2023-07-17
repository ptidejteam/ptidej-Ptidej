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

import padl.kernel.Constants;
import padl.kernel.IClass;
import padl.kernel.IUseRelationship;
import padl.motif.models.TestMotifModel;

public final class UseTest extends TestMotifModel {
	public static final char[] CALLEE = "Callee".toCharArray();
	public static final char[] CALLER = "Caller".toCharArray();
	private static final char[] MESSAGE = "message".toCharArray();
	private static final long serialVersionUID = -8903957402751001456L;
	private static final char[] USE_TEST = "UseTest".toCharArray();

	public UseTest() {
		final IClass caller =
			this.getFactory().createClass(UseTest.CALLER, UseTest.CALLER);
		final IClass callee =
			this.getFactory().createClass(UseTest.CALLEE, UseTest.CALLEE);
		final IUseRelationship useRelationship =
			this.getFactory().createUseRelationship(
				UseTest.MESSAGE,
				callee,
				Constants.CARDINALITY_ONE);
		caller.addConstituent(useRelationship);
		this.addConstituent(caller);
		this.addConstituent(callee);
	}
	public char[] getName() {
		return UseTest.USE_TEST;
	}
}
