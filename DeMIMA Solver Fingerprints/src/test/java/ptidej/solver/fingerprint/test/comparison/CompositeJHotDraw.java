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

public final class CompositeJHotDraw extends Primitive {
	private static Occurrence[] BuiltSolutions;
	private static Occurrence[] BuiltSolutionsNoRule;

	public CompositeJHotDraw(final String name) {
		super(name);
	}

	protected void setUp() {
		if (CompositeJHotDraw.BuiltSolutions == null) {
			CompositeJHotDraw.BuiltSolutions = Primitive.automaticSolve(
					CompositeMotif.class,
					"../DeMIMA Solver Metrics/target/test-classes/JHotDraw v5.1/bin/",
					"JHotDraw", Rule.C_LEAF_ROLE_1);
		}
		if (CompositeJHotDraw.BuiltSolutionsNoRule == null) {
			CompositeJHotDraw.BuiltSolutionsNoRule = Primitive.automaticSolve(
					ptidej.solver.java.problem.CompositeMotif.class,
					"../DeMIMA Solver Metrics/target/test-classes/JHotDraw v5.1/bin/", "JHotDraw");
		}
	}

	public void testNumberOfSolutions1() {
		Assert.assertTrue("Less solution with rules.",
				CompositeJHotDraw.BuiltSolutions.length < CompositeJHotDraw.BuiltSolutionsNoRule.length);
	}

	public void testNumberOfSolutions2() {
		Assert.assertEquals("Number of solutions", 2,
				CompositeJHotDraw.BuiltSolutionsNoRule.length);
	}
}
