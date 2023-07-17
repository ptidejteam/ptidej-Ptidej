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

public final class IgnoranceTest extends TestMotifModel {
	public static final char[] A = "A".toCharArray();
	public static final char[] B = "B".toCharArray();
	private static final char[] IGNORANCE_TEST = "IgnoranceTest".toCharArray();
	private static final long serialVersionUID = 2802400327338395072L;

	public IgnoranceTest() {
		final IClass A =
			this.getFactory().createClass(IgnoranceTest.A, IgnoranceTest.A);
		final IClass B =
			this.getFactory().createClass(IgnoranceTest.B, IgnoranceTest.B);
		this.addConstituent(A);
		this.addConstituent(B);
	}
	public char[] getName() {
		return IgnoranceTest.IGNORANCE_TEST;
	}
}
