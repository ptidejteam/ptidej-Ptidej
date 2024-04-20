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
import ptidej.solver.Occurrence;
import ptidej.solver.claire.OccurrenceGenerator;
import ptidej.solver.claire.test.Primitive;

public final class Facade extends Primitive {
	public Facade(final String name) {
		super(name);
	}

	/*
	 * Facade.
	 */
	private void testFacadeDesignPattern(final Occurrence[] builtSolutions) {
		Assert.assertEquals("Number of solutions", 1, builtSolutions.length);

		Assert.assertEquals(
			"One solution with all constraints",
			100,
			builtSolutions[0].getConfidence());

		Assert.assertEquals("Facade is the facade", "Facade", builtSolutions[0]
			.getComponent(padl.motif.repository.Facade.FACADE)
			.getDisplayValue());
		Assert.assertEquals(
			"SubsystemEntity is the subsystem entity",
			"SubsystemEntity",
			builtSolutions[0]
				.getComponent(padl.motif.repository.Facade.SUBSYSTEM_ENTITY)
				.getDisplayValue());
		Assert.assertEquals("Client is the Client", "Client", builtSolutions[0]
			.getComponent(padl.motif.repository.Facade.CLIENT)
			.getDisplayValue());
	}
	public void testFacadeDesignPattern1() throws IllegalAccessException,
			InstantiationException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {

		final Occurrence[] builtSolutions =
			this.testDesignPattern(
				Facade.class,
				Primitive.ALL_SOLUTIONS,
				((IDesignMotifModel) padl.motif.repository.Facade.class
					.getDeclaredConstructor().newInstance()).getName(),
				padl.motif.repository.Facade.class,
				OccurrenceGenerator.SOLVER_AUTOMATIC,
				OccurrenceGenerator.PROBLEM_AC4);

		this.testFacadeDesignPattern(builtSolutions);
	}
	public void testFacadeDesignPattern2() throws IllegalAccessException,
			InstantiationException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {

		final Occurrence[] builtSolutions =
			this.testDesignPattern(
				Facade.class,
				Primitive.ALL_SOLUTIONS,
				((IDesignMotifModel) padl.motif.repository.Facade.class
					.getDeclaredConstructor().newInstance()).getName(),
				padl.motif.repository.Facade.class,
				OccurrenceGenerator.SOLVER_AUTOMATIC,
				OccurrenceGenerator.PROBLEM_CUSTOM);

		this.testFacadeDesignPattern(builtSolutions);
	}

}
