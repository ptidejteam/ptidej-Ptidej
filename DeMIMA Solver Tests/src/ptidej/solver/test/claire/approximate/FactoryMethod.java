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

public final class FactoryMethod extends Primitive {
	public FactoryMethod(final String name) {
		super(name);
	}

	/*
	 * Abstract Factory.
	 */
	private void testFactoryMethodDesignPattern(
		final Occurrence[] builtSolutions,
		final int aPercentage) {

		// Yann 2002/12/21: PaLM!?
		// It seems that PaLM is smarter and
		// answers less solutions when they
		// are alike! I should ask Naren
		// about that...

		Assert.assertEquals("Number of solutions", 1, builtSolutions.length);

		Assert.assertEquals(
			"Solution with all constraints",
			aPercentage,
			builtSolutions[0].getConfidence());

		//	Assert.assertEquals(
		//		"Solution with all constraints",
		//		30,
		//		builtSolutions[1].getPercentage());

		for (int i = 0; i < 1; i++) {
			Assert.assertEquals(
				"FactoryMethod is the abstract creator",
				"Creator",
				builtSolutions[i]
					.getComponent(padl.motif.repository.FactoryMethod.CREATOR)
					.getDisplayValue());
			Assert.assertEquals(
				"ConcreteCreator is the concrete creator",
				"ConcreteCreator",
				builtSolutions[i]
					.getComponent(
						padl.motif.repository.FactoryMethod.CONCRETE_CREATOR)
					.getDisplayValue());
			Assert.assertEquals(
				"AbstractProduct is the abstract product",
				"Product",
				builtSolutions[i]
					.getComponent(padl.motif.repository.FactoryMethod.PRODUCT)
					.getDisplayValue());
			Assert.assertEquals(
				"ConcreteProduct is the concrete product",
				"ConcreteProduct",
				builtSolutions[i]
					.getComponent(
						padl.motif.repository.FactoryMethod.CONCRETE_PRODUCT)
					.getDisplayValue());
		}
	}
	public void testFactoryMethodDesignPattern1()
			throws IllegalAccessException, InstantiationException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {

		final Occurrence[] builtSolutions =
			this.testDesignPattern(
				FactoryMethod.class,
				Primitive.CANONICAL_SOLUTIONS,
				((IDesignMotifModel) padl.motif.repository.FactoryMethod.class
					.getDeclaredConstructor().newInstance()).getName(),
				padl.motif.repository.FactoryMethod.class,
				OccurrenceGenerator.SOLVER_AUTOMATIC,
				OccurrenceGenerator.PROBLEM_AC4);

		this.testFactoryMethodDesignPattern(builtSolutions, 100);
	}
	public void testFactoryMethodDesignPattern2()
			throws IllegalAccessException, InstantiationException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {

		final Occurrence[] builtSolutions =
			this.testDesignPattern(
				FactoryMethod.class,
				Primitive.CANONICAL_SOLUTIONS,
				((IDesignMotifModel) padl.motif.repository.FactoryMethod.class
					.getDeclaredConstructor().newInstance()).getName(),
				padl.motif.repository.FactoryMethod.class,
				OccurrenceGenerator.SOLVER_AUTOMATIC,
				OccurrenceGenerator.PROBLEM_CUSTOM);

		this.testFactoryMethodDesignPattern(builtSolutions, 30);
	}

}
