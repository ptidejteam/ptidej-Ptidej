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
package ptidej.solver.test.java.fingerprint.comparison;

import org.junit.Assert;
import ptidej.solver.Occurrence;
import ptidej.solver.fingerprint.Rule;

public final class SimpleCompositeComposite2 extends Primitive {
	private static Occurrence[] BuiltSolutions;
	private static Occurrence[] BuiltSolutionsNoRule;

	public SimpleCompositeComposite2(final String name) {
		super(name);
	}
	protected void setUp() {
		Primitive.initialize("Composite2");

		if (SimpleCompositeComposite2.BuiltSolutions == null) {
			SimpleCompositeComposite2.BuiltSolutions =
				Primitive.automaticSolve(
					ptidej.solver.fingerprint.problem.CompositeMotif.class,
					"../Ptidej Tests/bin/ptidej/example/composite2/",
					"composite2",
					Rule.C_LEAF_TEST);
		}

		if (SimpleCompositeComposite2.BuiltSolutionsNoRule == null) {
			SimpleCompositeComposite2.BuiltSolutionsNoRule =
				Primitive.automaticSolve(
					ptidej.solver.problem.CompositeMotif.class,
					"../Ptidej Tests/bin/ptidej/example/composite2/",
					"composite2");
		}
	}
	public void test1() {
		Assert.assertEquals(
			"Number of solutions",
			129,
			SimpleCompositeComposite2.BuiltSolutions.length);
	}

}
