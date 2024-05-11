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
package ptidej.solver.fingerprint.test.comparison;

import org.junit.Assert;

import ptidej.solver.Occurrence;
import ptidej.solver.fingerprint.Rule;
import ptidej.solver.fingerprint.problem.CompositeMotif;

public final class CompositeComposite2Comparison extends Primitive {
	private static Occurrence[] BuiltSolutions;
	private static Occurrence[] BuiltSolutionsNoRule;

	public CompositeComposite2Comparison(final String name) {
		super(name);
	}

	protected void setUp() {
		if (CompositeComposite2Comparison.BuiltSolutions == null) {
			CompositeComposite2Comparison.BuiltSolutions = Primitive
					.automaticSolve(CompositeMotif.class,
							"../DeMIMA/target/test-classes/ptidej/example/composite2/",
							"composite2", Rule.C_LEAF_TEST);
		}

		if (CompositeComposite2Comparison.BuiltSolutionsNoRule == null) {
			CompositeComposite2Comparison.BuiltSolutionsNoRule = Primitive
					.automaticSolve(CompositeMotif.class,
							"../DeMIMA/target/test-classes/ptidej/example/composite2/",
							"composite2");
		}
	}

	public void test1() {
		Assert.assertEquals("Number of solutions", 129,
				CompositeComposite2Comparison.BuiltSolutions.length);
	}

}
