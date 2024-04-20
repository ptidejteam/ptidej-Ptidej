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
package ptidej.solver.fingerprint.test.simple;

import org.junit.Assert;

import ptidej.example.pattern.CompositionExample;
import ptidej.solver.Occurrence;
import ptidej.solver.fingerprint.test.problem.CompositionProblem2;

public final class CompositionTest2 extends Primitive {
	private static Occurrence[] BuiltSolutions;

	public CompositionTest2(final String name) {
		super(name);
	}

	protected void setUp() {
		if (CompositionTest2.BuiltSolutions == null) {
			CompositionTest2.BuiltSolutions = Primitive.automaticSolve(
					CompositionProblem2.class, CompositionExample.class);
		}
	}

	public void testNumberOfSolutions() {
		Assert.assertEquals("Number of solutions", 2,
				CompositionTest2.BuiltSolutions.length);
	}

	public void testSolutionPercentage() {
		for (int i = 0; i < CompositionTest2.BuiltSolutions.length; i++) {
			Assert.assertEquals("Solution with all constraints", 100,
					CompositionTest2.BuiltSolutions[i].getConfidence());
		}
	}
}
