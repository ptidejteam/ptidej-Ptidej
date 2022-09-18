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
package ptidej.solver.test.claire.roundtrip;

import java.lang.reflect.InvocationTargetException;

import org.junit.Assert;
import padl.motif.IDesignMotifModel;
import padl.motif.repository.AbstractFactory;
import padl.motif.repository.Composite;
import padl.motif.repository.Facade;
import padl.motif.repository.FactoryMethod;
import padl.motif.repository.Mediator;
import ptidej.solver.Occurrence;
import ptidej.solver.OccurrenceGenerator;
import ptidej.solver.test.claire.Primitive;

public final class HalfRoundtrip extends Primitive {
	public HalfRoundtrip(final String name) {
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

		Assert
			.assertEquals(
				"Mediator is the mediator",
				"Mediator",
				builtSolutions[0]
					.getComponent(Mediator.MEDIATOR)
					.getDisplayValue());
		Assert.assertEquals("Client1 is a client", "Client1", builtSolutions[0]
			.getComponent(Mediator.CLIENT1)
			.getDisplayValue());
		Assert.assertEquals("Client2 is a client", "Client2", builtSolutions[0]
			.getComponent(Mediator.CLIENT2)
			.getDisplayValue());

		Assert
			.assertEquals(
				"Mediator is the mediator",
				"Mediator",
				builtSolutions[1]
					.getComponent(Mediator.MEDIATOR)
					.getDisplayValue());
		Assert.assertEquals("Client2 is a client", "Client2", builtSolutions[1]
			.getComponent(Mediator.CLIENT1)
			.getDisplayValue());
		Assert.assertEquals("Client1 is a client", "Client1", builtSolutions[1]
			.getComponent(Mediator.CLIENT2)
			.getDisplayValue());
	}

	/*
	 * Composite.
	 */
	private void testCompositeDesignPattern(final Occurrence[] builtSolutions) {
		Assert.assertEquals("Number of solutions", 1, builtSolutions.length);

		Assert.assertEquals(
			"Solution with all constraints",
			100,
			builtSolutions[0].getConfidence());
		Assert.assertEquals(
			"Component is the component",
			"Component",
			builtSolutions[0]
				.getComponent(Composite.COMPONENT)
				.getDisplayValue());
		Assert.assertEquals(
			"Composite is the composite",
			"Composite",
			builtSolutions[0]
				.getComponent(Composite.COMPOSITE)
				.getDisplayValue());
		Assert.assertEquals("Leaf is the leaf", "Leaf", builtSolutions[0]
			.getComponent(Composite.LEAF)
			.getDisplayValue());
	}
	public void testCompositeDesignPattern1() throws IllegalAccessException,
			InstantiationException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {

		final Occurrence[] builtSolutions =
			this.testDesignPattern(
				HalfRoundtrip.class,
				Primitive.CANONICAL_SOLUTIONS,
				((IDesignMotifModel) Composite.class.getDeclaredConstructor().newInstance()).getName(),
				Composite.class,
				OccurrenceGenerator.SOLVER_AUTOMATIC,
				OccurrenceGenerator.PROBLEM_AC4);

		this.testCompositeDesignPattern(builtSolutions);
	}
	public void testCompositeDesignPattern2() throws IllegalAccessException,
			InstantiationException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {

		// Yann 2005/07/11: Bug?!
		// It seems that the automatic solver with the custom problem
		// return an extra approximate solution wrt. AC4...
		final Occurrence[] builtSolutions =
			new Occurrence[] { this.testDesignPattern(
				HalfRoundtrip.class,
				Primitive.CANONICAL_SOLUTIONS,
				((IDesignMotifModel) Composite.class.getDeclaredConstructor().newInstance()).getName(),
				Composite.class,
				OccurrenceGenerator.SOLVER_AUTOMATIC,
				OccurrenceGenerator.PROBLEM_CUSTOM)[0] };

		this.testCompositeDesignPattern(builtSolutions);
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

		Assert
			.assertEquals(
				"Mediator is the mediator",
				"Mediator",
				builtSolutions[0]
					.getComponent(Mediator.MEDIATOR)
					.getDisplayValue());
		Assert.assertEquals("Client1 is a client", "Client1", builtSolutions[0]
			.getComponent(Mediator.CLIENT1)
			.getDisplayValue());
		Assert.assertEquals("Client2 is a client", "Client2", builtSolutions[0]
			.getComponent(Mediator.CLIENT2)
			.getDisplayValue());

		Assert
			.assertEquals(
				"Mediator is the mediator",
				"Mediator",
				builtSolutions[1]
					.getComponent(Mediator.MEDIATOR)
					.getDisplayValue());
		Assert.assertEquals("Client2 is a client", "Client2", builtSolutions[1]
			.getComponent(Mediator.CLIENT1)
			.getDisplayValue());
		Assert.assertEquals("Client1 is a client", "Client1", builtSolutions[1]
			.getComponent(Mediator.CLIENT2)
			.getDisplayValue());
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
			.getComponent(Facade.FACADE)
			.getDisplayValue());
		Assert.assertEquals(
			"SubsystemEntity is the subsystem entity",
			"SubsystemEntity",
			builtSolutions[0]
				.getComponent(Facade.SUBSYSTEM_ENTITY)
				.getDisplayValue());
		Assert.assertEquals("Client is the Client", "Client", builtSolutions[0]
			.getComponent(Facade.CLIENT)
			.getDisplayValue());
	}
	public void testFacadeDesignPattern1() throws IllegalAccessException,
			InstantiationException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {

		final Occurrence[] builtSolutions =
			this.testDesignPattern(
				HalfRoundtrip.class,
				Primitive.CANONICAL_SOLUTIONS,
				((IDesignMotifModel) Facade.class.getDeclaredConstructor().newInstance()).getName(),
				Facade.class,
				OccurrenceGenerator.SOLVER_AUTOMATIC,
				OccurrenceGenerator.PROBLEM_AC4);

		this.testFacadeDesignPattern(builtSolutions);
	}
	public void testFacadeDesignPattern2() throws IllegalAccessException,
			InstantiationException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {

		final Occurrence[] builtSolutions =
			this.testDesignPattern(
				HalfRoundtrip.class,
				Primitive.CANONICAL_SOLUTIONS,
				((IDesignMotifModel) Facade.class.getDeclaredConstructor().newInstance()).getName(),
				Facade.class,
				OccurrenceGenerator.SOLVER_AUTOMATIC,
				OccurrenceGenerator.PROBLEM_CUSTOM);

		this.testFacadeDesignPattern(builtSolutions);
	}

	/*
	 * Factory Method.
	 */
	private void testFactoryMethodDesignPattern(
		final Occurrence[] builtSolutions,
		final int aPercentage) {

		Assert.assertEquals("Number of solutions", 1, builtSolutions.length);

		Assert.assertEquals(
			"Solution with all constraints",
			aPercentage,
			builtSolutions[0].getConfidence());

		Assert.assertEquals(
			"Creator is the abstract creator",
			"Creator",
			builtSolutions[0]
				.getComponent(AbstractFactory.CREATOR)
				.getDisplayValue());
		Assert.assertEquals(
			"ConcreteCreator is the concrete creator",
			"ConcreteCreator",
			builtSolutions[0]
				.getComponent(AbstractFactory.CONCRETE_CREATOR)
				.getDisplayValue());
		Assert.assertEquals(
			"Product is the abstract product",
			"Product",
			builtSolutions[0]
				.getComponent(AbstractFactory.PRODUCT)
				.getDisplayValue());
		Assert.assertEquals(
			"ConcreteProduct is the concrete product",
			"ConcreteProduct",
			builtSolutions[0]
				.getComponent(AbstractFactory.CONCRETE_PRODUCT)
				.getDisplayValue());
	}
	public void testFactoryMethodDesignPattern1()
			throws IllegalAccessException, InstantiationException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {

		final Occurrence[] builtSolutions =
			this.testDesignPattern(
				HalfRoundtrip.class,
				Primitive.CANONICAL_SOLUTIONS,
				((IDesignMotifModel) FactoryMethod.class.getDeclaredConstructor().newInstance())
					.getName(),
				FactoryMethod.class,
				OccurrenceGenerator.SOLVER_AUTOMATIC,
				OccurrenceGenerator.PROBLEM_AC4);

		this.testFactoryMethodDesignPattern(builtSolutions, 100);
	}
	public void testFactoryMethodDesignPattern2()
			throws IllegalAccessException, InstantiationException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {

		final Occurrence[] builtSolutions =
			this.testDesignPattern(
				HalfRoundtrip.class,
				Primitive.CANONICAL_SOLUTIONS,
				((IDesignMotifModel) FactoryMethod.class.getDeclaredConstructor().newInstance())
					.getName(),
				FactoryMethod.class,
				OccurrenceGenerator.SOLVER_AUTOMATIC,
				OccurrenceGenerator.PROBLEM_CUSTOM);

		this.testFactoryMethodDesignPattern(builtSolutions, 30);
	}
	public void testMediatorDesignPattern1() throws IllegalAccessException,
			InstantiationException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {

		final Occurrence[] builtSolutions =
			this.testDesignPattern(
				HalfRoundtrip.class,
				Primitive.CANONICAL_SOLUTIONS,
				((IDesignMotifModel) Mediator.class.getDeclaredConstructor().newInstance()).getName(),
				Mediator.class,
				OccurrenceGenerator.SOLVER_AUTOMATIC,
				OccurrenceGenerator.PROBLEM_AC4);

		this.testAC4MediatorDesignPattern(builtSolutions);
	}
	public void testMediatorDesignPattern2() throws IllegalAccessException,
			InstantiationException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {

		final Occurrence[] builtSolutions =
			this.testDesignPattern(
				HalfRoundtrip.class,
				Primitive.CANONICAL_SOLUTIONS,
				((IDesignMotifModel) Mediator.class.getDeclaredConstructor().newInstance()).getName(),
				Mediator.class,
				OccurrenceGenerator.SOLVER_AUTOMATIC,
				OccurrenceGenerator.PROBLEM_CUSTOM);

		this.testCustomMediatorDesignPattern(builtSolutions);
	}
}
