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
package ptidej.solver.test.claire.approximate;

import java.lang.reflect.InvocationTargetException;

import org.junit.Assert;

import padl.motif.IDesignMotifModel;
import ptidej.solver.Occurrence;
import ptidej.solver.OccurrenceGenerator;
import ptidej.solver.test.claire.Primitive;
import ptidej.solver.test.data.pattern.BadCompositionTest;
import ptidej.solver.test.data.source.BadCompositionTest1;

public final class BadComposition extends Primitive {
	private Occurrence[] builtSolutions;

	public BadComposition(final String name) {
		super(name);
	}
	protected void setUp() throws IllegalAccessException,
			InstantiationException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {

		this.builtSolutions =
			this.testDesignPattern(
				BadComposition.class,
				Primitive.ALL_SOLUTIONS,
				((IDesignMotifModel) BadCompositionTest.class.getDeclaredConstructor().newInstance())
					.getName(),
				BadCompositionTest1.class,
				OccurrenceGenerator.SOLVER_AUTOMATIC,
				OccurrenceGenerator.PROBLEM_AC4);
	}
	public void testNumberOfSolutions() {
		// Yann 2002/08/27: That's right!
		// This problem has no solution with the automatic solver.
		// Indeed, the composition constraint is of weight 90, thus
		// the composition constraint is not relaxable.
		Assert.assertEquals(
			"Number of solutions",
			0,
			this.builtSolutions.length);
	}
}
