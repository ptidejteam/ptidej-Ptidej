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

public final class CompositeComposite2Comparison extends Primitive {
	public CompositeComposite2Comparison(final String name) {
		super(name);
	}

	public void test1() {
		final Occurrence[] solutions = Primitive.automaticSolve(
				ptidej.solver.java.problem.CompositeMotif.class,
				"../DeMIMA/target/test-classes/ptidej/example/composite2/",
				"composite2");
		Assert.assertEquals("Number of solutions", 47, solutions.length);
	}

	public void test2() {
		final Occurrence[] solutions = Primitive.automaticSolve(
				ptidej.solver.fingerprint.problem.CompositeMotif.class,
				"../DeMIMA/target/test-classes/ptidej/example/composite2/",
				"composite2");
		Assert.assertEquals("Number of solutions", 40, solutions.length);
	}

	public void test3() {
		final Occurrence[] solutions = Primitive.automaticSolve(
				ptidej.solver.fingerprint.problem.CompositeMotif.class,
				"../DeMIMA/target/test-classes/ptidej/example/composite2/",
				"composite2", Rule.C_LEAF_TEST);
		Assert.assertEquals("Number of solutions", 40, solutions.length);
	}

}
