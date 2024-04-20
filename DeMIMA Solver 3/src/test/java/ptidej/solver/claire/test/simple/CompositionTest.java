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
package ptidej.solver.claire.test.simple;

import java.lang.reflect.InvocationTargetException;

import org.junit.Assert;

import padl.motif.IDesignMotifModel;
import ptidej.example.pattern.CompositionExample;
import ptidej.solver.Occurrence;
import ptidej.solver.claire.OccurrenceGenerator;
import ptidej.solver.claire.test.Primitive;
import ptidej.solver.claire.test.pattern.CompositionPattern;

public final class CompositionTest extends Primitive {
	private Occurrence[] builtSolutions;

	// Yann 2001/07/11: Too much!
	// This was too much: The Composition constraint had to many
	// Entity to play with, I only keep the Entity corresponding
	// to the target of the Composition link. The subclasses will
	// be dealt with through the StrictInheritance or
	// InheritancePath constraints.
	/*
	Composition.assertEquals("Number of solutions", 4, builtSolutions.length);
	
	for (int i = 0; i < builtSolutions.length; i++) {
	    Composition.assertEquals(
	        "Solution with all constraints", 
	        100, 
	        builtSolutions[i].getPercentage()); 
	}
	
	Composition.assertEquals(
	    "Aggregate1 == Aggregate1", 
	    "Aggregate1", 
	    builtSolutions[0].getComponent("Aggregate").getDisplayValue()); 
	Composition.assertEquals(
	    "Aggregated1 == Aggregated1", 
	    "Aggregated1", 
	    builtSolutions[0].getComponent("Aggregated").getDisplayValue()); 
	
	Composition.assertEquals(
	    "Aggregate2 == Aggregate2", 
	    "Aggregate2", 
	    builtSolutions[1].getComponent("Aggregate").getDisplayValue()); 
	Composition.assertEquals(
	    "Aggregated2 == Aggregated2", 
	    "Aggregated2", 
	    builtSolutions[1].getComponent("Aggregated").getDisplayValue()); 
	
	Composition.assertEquals(
	    "Aggregate2 == Aggregate2", 
	    "Aggregate2", 
	    builtSolutions[2].getComponent("Aggregate").getDisplayValue()); 
	Composition.assertEquals(
	    "Aggregated2 == Subclass1", 
	    "Subclass1", 
	    builtSolutions[2].getComponent("Aggregated").getDisplayValue()); 
	
	Composition.assertEquals(
	    "Aggregate2 == Aggregate2", 
	    "Aggregate2", 
	    builtSolutions[3].getComponent("Aggregate").getDisplayValue()); 
	Composition.assertEquals(
	    "Aggregated2 == Subclass2", 
	    "Subclass2", 
	    builtSolutions[3].getComponent("Aggregated").getDisplayValue());
	*/

	public CompositionTest(final String name) {
		super(name);
	}

	protected void setUp()
			throws IllegalAccessException, InstantiationException,
			IllegalArgumentException, InvocationTargetException,
			NoSuchMethodException, SecurityException {

		this.builtSolutions = this.testDesignPattern(CompositionTest.class,
				Primitive.ALL_SOLUTIONS,
				((IDesignMotifModel) CompositionPattern.class
						.getDeclaredConstructor().newInstance()).getName(),
				CompositionExample.class, OccurrenceGenerator.SOLVER_AUTOMATIC,
				OccurrenceGenerator.PROBLEM_CUSTOM);
	}

	public void testNumberOfSolutions() {
		Assert.assertEquals("Number of solutions", 2,
				this.builtSolutions.length);
	}

	public void testSolution1() {
		Assert.assertEquals("AggregateClass1 == AggregateClass1",
				"AggregateClass1",
				this.builtSolutions[0]
						.getComponent(CompositionPattern.AGGREGATE)
						.getDisplayValue());
		Assert.assertEquals("AggregatedClass1 == AggregatedClass1",
				"AggregatedClass1",
				this.builtSolutions[0]
						.getComponent(CompositionPattern.AGGREGATED)
						.getDisplayValue());
	}

	public void testSolution2() {
		Assert.assertEquals("AggregateClass2 == AggregateClass2",
				"AggregateClass2",
				this.builtSolutions[1]
						.getComponent(CompositionPattern.AGGREGATE)
						.getDisplayValue());
		Assert.assertEquals("AggregatedClass2 == AggregatedClass2",
				"AggregatedClass2",
				this.builtSolutions[1]
						.getComponent(CompositionPattern.AGGREGATED)
						.getDisplayValue());
	}

	public void testSolutionPercentage() {
		for (int i = 0; i < this.builtSolutions.length; i++) {
			Assert.assertEquals("Solution with all constraints", 100,
					this.builtSolutions[i].getConfidence());
		}
	}
}
