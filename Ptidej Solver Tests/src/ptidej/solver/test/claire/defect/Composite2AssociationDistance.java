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
package ptidej.solver.test.claire.defect;

import org.junit.Assert;
import padl.creator.classfile.LightClassFileCreator;
import padl.kernel.ICodeLevelModel;
import padl.kernel.exception.CreationException;
import padl.kernel.impl.Factory;
import ptidej.solver.Occurrence;
import ptidej.solver.OccurrenceGenerator;
import ptidej.solver.test.claire.Primitive;
import ptidej.solver.test.claire.example.Composite2;

public final class Composite2AssociationDistance extends Primitive {
	private static final char[] ASSOCIATED_ENTITY = "AssociatedEntity"
		.toCharArray();
	private static final char[] ASSOCIATION_DISTANCE = "AssociationDistance"
		.toCharArray();
	private static final char[] ASSOCIATION_DISTANCE_TEST =
		"AssociationDistanceTest".toCharArray();
	private static final char[] ENTITY = "Entity".toCharArray();
	private static Occurrence[] Solutions;

	public Composite2AssociationDistance(final String name) {
		super(name);
	}
	protected void setUp() throws IllegalAccessException,
			InstantiationException {

		if (Composite2AssociationDistance.Solutions == null) {
			final ICodeLevelModel codeLevelModel =
				Factory.getInstance().createCodeLevelModel(
					"ptidej.example.composite2");
			try {
				codeLevelModel
					.create(new LightClassFileCreator(
						new String[] { "../Ptidej Tests/bin/ptidej/example/composite2/" }));
			}
			catch (final CreationException e) {
				e.printStackTrace();
			}

			Composite2AssociationDistance.Solutions =
				this.testDesignPattern(
					Composite2.class,
					Primitive.ALL_SOLUTIONS,
					Composite2AssociationDistance.ASSOCIATION_DISTANCE_TEST,
					codeLevelModel,
					OccurrenceGenerator.SOLVER_AUTOMATIC,
					OccurrenceGenerator.PROBLEM_CUSTOM);
		}
	}
	public void testNumberOfSolutions() {
		Assert.assertEquals(
			"Number of solutions",
			6,
			Composite2AssociationDistance.Solutions.length);
	}
	public void testSolution1() {
		//	1.100.Name = AssociationDistance Pattern Problem
		//	1.100.XCommand = System.out.println("No transformation required.");
		//	1.100.Entity = ptidej.example.composite2.AbstractDocument
		//	1.100.AssociatedEntity = java.io.PrintStream
		//	1.100.AssociationDistance = 1
		Assert.assertEquals(
			"charge(Entity ----> AssociatedEntity) >= 2",
			"ptidej.example.composite2.AbstractDocument",
			Composite2AssociationDistance.Solutions[0].getComponent(
				Composite2AssociationDistance.ENTITY).getValue());
		Assert.assertEquals(
			"charge(Entity ----> AssociatedEntity) >= 2",
			"java.io.PrintStream",
			Composite2AssociationDistance.Solutions[0].getComponent(
				Composite2AssociationDistance.ASSOCIATED_ENTITY).getValue());
		Assert.assertEquals(
			"charge(Entity ----> AssociatedEntity) >= 2",
			"1",
			Composite2AssociationDistance.Solutions[0].getComponent(
				Composite2AssociationDistance.ASSOCIATION_DISTANCE).getValue());
	}
	public void testSolution2() {
		//	2.100.Name = AssociationDistance Pattern Problem
		//	2.100.XCommand = System.out.println("No transformation required.");
		//	2.100.Entity = ptidej.example.composite2.Document
		//	2.100.AssociatedEntity = ptidej.example.composite2.Element
		//	2.100.AssociationDistance = 2
		Assert.assertEquals(
			"charge(Entity ----> AssociatedEntity) >= 1",
			"ptidej.example.composite2.Document",
			Composite2AssociationDistance.Solutions[1].getComponent(
				Composite2AssociationDistance.ENTITY).getValue());
		Assert.assertEquals(
			"charge(Entity ----> AssociatedEntity) >= 1",
			"ptidej.example.composite2.Element",
			Composite2AssociationDistance.Solutions[1].getComponent(
				Composite2AssociationDistance.ASSOCIATED_ENTITY).getValue());
		Assert.assertEquals(
			"charge(Entity ----> AssociatedEntity) >= 1",
			"2",
			Composite2AssociationDistance.Solutions[1].getComponent(
				Composite2AssociationDistance.ASSOCIATION_DISTANCE).getValue());
	}
	public void testSolution3() {
		//	3.100.Name = AssociationDistance Pattern Problem
		//	3.100.XCommand = System.out.println("No transformation required.");
		//	3.100.Entity = ptidej.example.composite2.Document
		//	3.100.AssociatedEntity = java.util.Vector
		//	3.100.AssociationDistance = 1
		Assert.assertEquals(
			"charge(Entity ----> AssociatedEntity) >= 1",
			"ptidej.example.composite2.Document",
			Composite2AssociationDistance.Solutions[2].getComponent(
				Composite2AssociationDistance.ENTITY).getValue());
		Assert.assertEquals(
			"charge(Entity ----> AssociatedEntity) >= 1",
			"java.util.Vector",
			Composite2AssociationDistance.Solutions[2].getComponent(
				Composite2AssociationDistance.ASSOCIATED_ENTITY).getValue());
		Assert.assertEquals(
			"charge(Entity ----> AssociatedEntity) >= 1",
			"1",
			Composite2AssociationDistance.Solutions[2].getComponent(
				Composite2AssociationDistance.ASSOCIATION_DISTANCE).getValue());
	}
	public void testSolution4() {
		//	4.100.Name = AssociationDistance Pattern Problem
		//	4.100.XCommand = System.out.println("No transformation required.");
		//	4.100.Entity = ptidej.example.composite2.Main
		//	4.100.AssociatedEntity = ptidej.example.composite2.Document
		//	4.100.AssociationDistance = 1
		Assert.assertEquals(
			"charge(Entity ----> AssociatedEntity) >= 1",
			"ptidej.example.composite2.Main",
			Composite2AssociationDistance.Solutions[3].getComponent(
				Composite2AssociationDistance.ENTITY).getValue());
		Assert.assertEquals(
			"charge(Entity ----> AssociatedEntity) >= 1",
			"ptidej.example.composite2.Document",
			Composite2AssociationDistance.Solutions[3].getComponent(
				Composite2AssociationDistance.ASSOCIATED_ENTITY).getValue());
		Assert.assertEquals(
			"charge(Entity ----> AssociatedEntity) >= 1",
			"2",
			Composite2AssociationDistance.Solutions[3].getComponent(
				Composite2AssociationDistance.ASSOCIATION_DISTANCE).getValue());
	}
	public void testSolution5() {
		//	5.100.Name = AssociationDistance Pattern Problem
		//	5.100.XCommand = System.out.println("No transformation required.");
		//	5.100.Entity = ptidej.example.composite2.Main
		//	5.100.AssociatedEntity = ptidej.example.composite2.Element
		//	5.100.AssociationDistance = 3
		Assert.assertEquals(
			"charge(Entity ----> AssociatedEntity) >= 1",
			"ptidej.example.composite2.Main",
			Composite2AssociationDistance.Solutions[4].getComponent(
				Composite2AssociationDistance.ENTITY).getValue());
		Assert.assertEquals(
			"charge(Entity ----> AssociatedEntity) >= 1",
			"ptidej.example.composite2.Element",
			Composite2AssociationDistance.Solutions[4].getComponent(
				Composite2AssociationDistance.ASSOCIATED_ENTITY).getValue());
		Assert.assertEquals(
			"charge(Entity ----> AssociatedEntity) >= 1",
			"1",
			Composite2AssociationDistance.Solutions[4].getComponent(
				Composite2AssociationDistance.ASSOCIATION_DISTANCE).getValue());
	}
	public void testSolution6() {
		//	6.100.Name = AssociationDistance Pattern Problem
		//	6.100.XCommand = System.out.println("No transformation required.");
		//	6.100.Entity = ptidej.example.composite2.Main
		//	6.100.AssociatedEntity = java.util.Vector
		//	6.100.AssociationDistance = 2
		Assert.assertEquals(
			"charge(Entity ----> AssociatedEntity) >= 1",
			"ptidej.example.composite2.Main",
			Composite2AssociationDistance.Solutions[5].getComponent(
				Composite2AssociationDistance.ENTITY).getValue());
		Assert.assertEquals(
			"charge(Entity ----> AssociatedEntity) >= 1",
			"java.util.Vector",
			Composite2AssociationDistance.Solutions[5].getComponent(
				Composite2AssociationDistance.ASSOCIATED_ENTITY).getValue());
		Assert.assertEquals(
			"charge(Entity ----> AssociatedEntity) >= 1",
			"3",
			Composite2AssociationDistance.Solutions[5].getComponent(
				Composite2AssociationDistance.ASSOCIATION_DISTANCE).getValue());
	}
	public void testSolutionPercentage() {
		for (int i = 0; i < Composite2AssociationDistance.Solutions.length; i++) {
			Assert.assertEquals(
				"Solution with all constraints",
				100,
				Composite2AssociationDistance.Solutions[i].getConfidence());
		}
	}
}
