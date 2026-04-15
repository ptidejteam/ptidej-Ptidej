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
package ptidej.solver.claire.test.defect;

import org.junit.Assert;

import padl.analysis.UnsupportedSourceModelException;
import padl.analysis.repository.AACRelationshipsAnalysis;
import padl.creator.classfile.LightClassFileCreator;
import padl.kernel.ICodeLevelModel;
import padl.kernel.IIdiomLevelModel;
import padl.kernel.exception.CreationException;
import padl.kernel.impl.Factory;
import ptidej.occurrences.SolverKinds;
import ptidej.solver.Occurrence;
import ptidej.solver.claire.OccurrenceGenerator;
import ptidej.solver.claire.test.Primitive;
import ptidej.solver.claire.test.example.Composite2Test;

public final class AssociationDistanceComposite2Test extends Primitive {
	private static final char[] ASSOCIATED_ENTITY = "AssociatedEntity"
			.toCharArray();
	private static final char[] ASSOCIATION_DISTANCE = "AssociationDistance"
			.toCharArray();
	private static final char[] ASSOCIATION_DISTANCE_TEST = "AssociationDistanceTest"
			.toCharArray();
	private static final char[] ENTITY = "Entity".toCharArray();
	private static Occurrence[] Solutions;

	public AssociationDistanceComposite2Test(final String name) {
		super(name);
	}

	protected void setUp()
			throws IllegalAccessException, InstantiationException,
			CreationException, UnsupportedSourceModelException {

		if (AssociationDistanceComposite2Test.Solutions == null) {
			final ICodeLevelModel codeLevelModel = Factory.getInstance()
					.createCodeLevelModel("ptidej.example.composite2");
			codeLevelModel.create(new LightClassFileCreator(new String[] {
					"../DeMIMA/target/test-classes/ptidej/example/composite2/" }));

			final IIdiomLevelModel idiomLevelModel = (IIdiomLevelModel) new AACRelationshipsAnalysis()
					.invoke(codeLevelModel);

			AssociationDistanceComposite2Test.Solutions = this
					.testDesignPattern(Composite2Test.class,
							Primitive.ALL_SOLUTIONS,
							AssociationDistanceComposite2Test.ASSOCIATION_DISTANCE_TEST,
							idiomLevelModel, SolverKinds.SOLVER_AUTOMATIC,
							OccurrenceGenerator.PROBLEM_CUSTOM);
		}
	}

	public void testNumberOfSolutions() {
		Assert.assertEquals("Number of solutions", 6,
				AssociationDistanceComposite2Test.Solutions.length);
	}

	public void testSolution1() {
		//	1.100.Name = AssociationDistance Pattern Problem
		//	1.100.XCommand = System.out.println("No transformation required.");
		//	1.100.Entity = ptidej.example.composite2.AbstractDocument
		//	1.100.AssociatedEntity = java.io.PrintStream
		//	1.100.AssociationDistance = 1
		Assert.assertEquals("charge(Entity ----> AssociatedEntity) >= 2",
				"ptidej.example.composite2.AbstractDocument",
				String.valueOf(AssociationDistanceComposite2Test.Solutions[0]
						.getComponent(AssociationDistanceComposite2Test.ENTITY)
						.getValue()));
		Assert.assertEquals("charge(Entity ----> AssociatedEntity) >= 2",
				"java.io.PrintStream",
				String.valueOf(AssociationDistanceComposite2Test.Solutions[0]
						.getComponent(
								AssociationDistanceComposite2Test.ASSOCIATED_ENTITY)
						.getValue()));
		Assert.assertEquals("charge(Entity ----> AssociatedEntity) >= 2", "1",
				String.valueOf(AssociationDistanceComposite2Test.Solutions[0]
						.getComponent(
								AssociationDistanceComposite2Test.ASSOCIATION_DISTANCE)
						.getValue()));
	}

	public void testSolution2() {
		//	2.100.Name = AssociationDistance Pattern Problem
		//	2.100.XCommand = System.out.println("No transformation required.");
		//	2.100.Entity = ptidej.example.composite2.Document
		//	2.100.AssociatedEntity = ptidej.example.composite2.Element
		//	2.100.AssociationDistance = 2
		Assert.assertEquals("charge(Entity ----> AssociatedEntity) >= 1",
				"ptidej.example.composite2.Document",
				AssociationDistanceComposite2Test.Solutions[1]
						.getComponent(AssociationDistanceComposite2Test.ENTITY)
						.getValue());
		Assert.assertEquals("charge(Entity ----> AssociatedEntity) >= 1",
				"ptidej.example.composite2.Element",
				AssociationDistanceComposite2Test.Solutions[1].getComponent(
						AssociationDistanceComposite2Test.ASSOCIATED_ENTITY)
						.getValue());
		Assert.assertEquals("charge(Entity ----> AssociatedEntity) >= 1", "2",
				AssociationDistanceComposite2Test.Solutions[1].getComponent(
						AssociationDistanceComposite2Test.ASSOCIATION_DISTANCE)
						.getValue());
	}

	public void testSolution3() {
		//	3.100.Name = AssociationDistance Pattern Problem
		//	3.100.XCommand = System.out.println("No transformation required.");
		//	3.100.Entity = ptidej.example.composite2.Document
		//	3.100.AssociatedEntity = java.util.Vector
		//	3.100.AssociationDistance = 1
		Assert.assertEquals("charge(Entity ----> AssociatedEntity) >= 1",
				"ptidej.example.composite2.Document",
				AssociationDistanceComposite2Test.Solutions[2]
						.getComponent(AssociationDistanceComposite2Test.ENTITY)
						.getValue());
		Assert.assertEquals("charge(Entity ----> AssociatedEntity) >= 1",
				"java.util.Vector",
				AssociationDistanceComposite2Test.Solutions[2].getComponent(
						AssociationDistanceComposite2Test.ASSOCIATED_ENTITY)
						.getValue());
		Assert.assertEquals("charge(Entity ----> AssociatedEntity) >= 1", "1",
				AssociationDistanceComposite2Test.Solutions[2].getComponent(
						AssociationDistanceComposite2Test.ASSOCIATION_DISTANCE)
						.getValue());
	}

	public void testSolution4() {
		//	4.100.Name = AssociationDistance Pattern Problem
		//	4.100.XCommand = System.out.println("No transformation required.");
		//	4.100.Entity = ptidej.example.composite2.Main
		//	4.100.AssociatedEntity = ptidej.example.composite2.Document
		//	4.100.AssociationDistance = 1
		Assert.assertEquals("charge(Entity ----> AssociatedEntity) >= 1",
				"ptidej.example.composite2.Main",
				AssociationDistanceComposite2Test.Solutions[3]
						.getComponent(AssociationDistanceComposite2Test.ENTITY)
						.getValue());
		Assert.assertEquals("charge(Entity ----> AssociatedEntity) >= 1",
				"ptidej.example.composite2.Document",
				AssociationDistanceComposite2Test.Solutions[3].getComponent(
						AssociationDistanceComposite2Test.ASSOCIATED_ENTITY)
						.getValue());
		Assert.assertEquals("charge(Entity ----> AssociatedEntity) >= 1", "2",
				AssociationDistanceComposite2Test.Solutions[3].getComponent(
						AssociationDistanceComposite2Test.ASSOCIATION_DISTANCE)
						.getValue());
	}

	public void testSolution5() {
		//	5.100.Name = AssociationDistance Pattern Problem
		//	5.100.XCommand = System.out.println("No transformation required.");
		//	5.100.Entity = ptidej.example.composite2.Main
		//	5.100.AssociatedEntity = ptidej.example.composite2.Element
		//	5.100.AssociationDistance = 3
		Assert.assertEquals("charge(Entity ----> AssociatedEntity) >= 1",
				"ptidej.example.composite2.Main",
				AssociationDistanceComposite2Test.Solutions[4]
						.getComponent(AssociationDistanceComposite2Test.ENTITY)
						.getValue());
		Assert.assertEquals("charge(Entity ----> AssociatedEntity) >= 1",
				"ptidej.example.composite2.Element",
				AssociationDistanceComposite2Test.Solutions[4].getComponent(
						AssociationDistanceComposite2Test.ASSOCIATED_ENTITY)
						.getValue());
		Assert.assertEquals("charge(Entity ----> AssociatedEntity) >= 1", "1",
				AssociationDistanceComposite2Test.Solutions[4].getComponent(
						AssociationDistanceComposite2Test.ASSOCIATION_DISTANCE)
						.getValue());
	}

	public void testSolution6() {
		//	6.100.Name = AssociationDistance Pattern Problem
		//	6.100.XCommand = System.out.println("No transformation required.");
		//	6.100.Entity = ptidej.example.composite2.Main
		//	6.100.AssociatedEntity = java.util.Vector
		//	6.100.AssociationDistance = 2
		Assert.assertEquals("charge(Entity ----> AssociatedEntity) >= 1",
				"ptidej.example.composite2.Main",
				AssociationDistanceComposite2Test.Solutions[5]
						.getComponent(AssociationDistanceComposite2Test.ENTITY)
						.getValue());
		Assert.assertEquals("charge(Entity ----> AssociatedEntity) >= 1",
				"java.util.Vector",
				AssociationDistanceComposite2Test.Solutions[5].getComponent(
						AssociationDistanceComposite2Test.ASSOCIATED_ENTITY)
						.getValue());
		Assert.assertEquals("charge(Entity ----> AssociatedEntity) >= 1", "3",
				AssociationDistanceComposite2Test.Solutions[5].getComponent(
						AssociationDistanceComposite2Test.ASSOCIATION_DISTANCE)
						.getValue());
	}

	public void testSolutionPercentage() {
		for (int i = 0; i < AssociationDistanceComposite2Test.Solutions.length; i++) {
			Assert.assertEquals("Solution with all constraints", 100,
					AssociationDistanceComposite2Test.Solutions[i]
							.getConfidence());
		}
	}
}
