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
package ptidej.solver.claire.test.approximate;

import java.lang.reflect.InvocationTargetException;

import org.junit.Assert;

import padl.motif.IDesignMotifModel;
import ptidej.occurrences.SolverKinds;
import ptidej.solver.Occurrence;
import ptidej.solver.claire.OccurrenceGenerator;
import ptidej.solver.claire.test.Primitive;
import ptidej.solver.claire.test.pattern.BadCompositionExample1;
import ptidej.solver.claire.test.pattern.BadCompositionPattern;

public final class BadCompositionTest extends Primitive {
	private Occurrence[] builtSolutions;

	public BadCompositionTest(final String name) {
		super(name);
	}

	protected void setUp()
			throws IllegalAccessException, InstantiationException,
			IllegalArgumentException, InvocationTargetException,
			NoSuchMethodException, SecurityException {

		this.builtSolutions = this.testDesignPattern(BadCompositionTest.class,
				Primitive.ALL_SOLUTIONS,
				((IDesignMotifModel) BadCompositionPattern.class
						.getDeclaredConstructor().newInstance()).getName(),
				BadCompositionExample1.class, SolverKinds.SOLVER_AUTOMATIC,
				OccurrenceGenerator.PROBLEM_AC4);
	}

	public void testNumberOfSolutions() {
		// Yann 2002/08/27: That's right!
		// This problem has no solution with the automatic solver.
		// Indeed, the composition constraint is of weight 90, thus
		// the composition constraint is not relaxable.
		Assert.assertEquals("Number of solutions", 0,
				this.builtSolutions.length);
	}
}
