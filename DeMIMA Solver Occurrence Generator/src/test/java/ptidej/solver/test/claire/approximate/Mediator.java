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

public final class Mediator extends Primitive {
	public Mediator(final String name) {
		super(name);
	}

	/*
	 * Mediator.
	 */
	private void testAC4MediatorDesignPattern(final Occurrence[] builtSolutions) {
		Assert.assertEquals("Number of solutions", 2, builtSolutions.length);

		Assert.assertEquals(
			"Solution with all constraints",
			100,
			builtSolutions[0].getConfidence());
		Assert.assertEquals(
			"Solution with all constraints",
			100,
			builtSolutions[1].getConfidence());

		Assert.assertEquals(
			"Mediator is the mediator",
			"Mediator",
			builtSolutions[0].getComponent(
				padl.motif.repository.Mediator.MEDIATOR).getDisplayValue());
		Assert.assertEquals("Client1 is a client", "Client1", builtSolutions[0]
			.getComponent(padl.motif.repository.Mediator.CLIENT1)
			.getDisplayValue());
		Assert.assertEquals("Client2 is a client", "Client2", builtSolutions[0]
			.getComponent(padl.motif.repository.Mediator.CLIENT2)
			.getDisplayValue());

		Assert.assertEquals(
			"Mediator is the mediator",
			"Mediator",
			builtSolutions[1].getComponent(
				padl.motif.repository.Mediator.MEDIATOR).getDisplayValue());
		Assert.assertEquals("Client2 is a client", "Client2", builtSolutions[1]
			.getComponent(padl.motif.repository.Mediator.CLIENT1)
			.getDisplayValue());
		Assert.assertEquals("Client1 is a client", "Client1", builtSolutions[1]
			.getComponent(padl.motif.repository.Mediator.CLIENT2)
			.getDisplayValue());
	}
	private void testCustomMediatorDesignPattern(
		final Occurrence[] builtSolutions) {

		Assert.assertEquals("Number of solutions", 2, builtSolutions.length);

		Assert.assertEquals(
			"Solution with all constraints",
			100,
			builtSolutions[0].getConfidence());
		Assert.assertEquals(
			"Solution with all constraints",
			100,
			builtSolutions[1].getConfidence());

		Assert.assertEquals(
			"Mediator is the mediator",
			"Mediator",
			builtSolutions[0].getComponent(
				padl.motif.repository.Mediator.MEDIATOR).getDisplayValue());
		Assert.assertEquals("Client1 is a client", "Client1", builtSolutions[0]
			.getComponent(padl.motif.repository.Mediator.CLIENT1)
			.getDisplayValue());
		Assert.assertEquals("Client2 is a client", "Client2", builtSolutions[0]
			.getComponent(padl.motif.repository.Mediator.CLIENT2)
			.getDisplayValue());

		Assert.assertEquals(
			"Mediator is the mediator",
			"Mediator",
			builtSolutions[1].getComponent(
				padl.motif.repository.Mediator.MEDIATOR).getDisplayValue());
		Assert.assertEquals("Client2 is a client", "Client2", builtSolutions[1]
			.getComponent(padl.motif.repository.Mediator.CLIENT1)
			.getDisplayValue());
		Assert.assertEquals("Client1 is a client", "Client1", builtSolutions[1]
			.getComponent(padl.motif.repository.Mediator.CLIENT2)
			.getDisplayValue());
	}
	public void testMediatorDesignPattern1() throws IllegalAccessException,
			InstantiationException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {

		final Occurrence[] builtSolutions =
			this.testDesignPattern(
				Mediator.class,
				Primitive.ALL_SOLUTIONS,
				((IDesignMotifModel) padl.motif.repository.Mediator.class
					.getDeclaredConstructor().newInstance()).getName(),
				padl.motif.repository.Mediator.class,
				OccurrenceGenerator.SOLVER_AUTOMATIC,
				OccurrenceGenerator.PROBLEM_AC4);

		this.testAC4MediatorDesignPattern(builtSolutions);
	}
	public void testMediatorDesignPattern2() throws IllegalAccessException,
			InstantiationException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {

		final Occurrence[] builtSolutions =
			this.testDesignPattern(
				Mediator.class,
				Primitive.ALL_SOLUTIONS,
				((IDesignMotifModel) padl.motif.repository.Mediator.class
					.getDeclaredConstructor().newInstance()).getName(),
				padl.motif.repository.Mediator.class,
				OccurrenceGenerator.SOLVER_AUTOMATIC,
				OccurrenceGenerator.PROBLEM_CUSTOM);

		this.testCustomMediatorDesignPattern(builtSolutions);
	}
}
