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

import padl.creator.classfile.CompleteClassFileCreator;
import padl.kernel.ICodeLevelModel;
import padl.kernel.exception.CreationException;
import padl.kernel.impl.Factory;
import ptidej.solver.Occurrence;
import ptidej.solver.claire.OccurrenceGenerator;
import ptidej.solver.claire.test.Primitive;

public final class InheritanceTreeDepthComposite2 extends Primitive {
	private static final char[] DEPTH = "Depth".toCharArray();
	private static final char[] ENTITY = "SubEntity".toCharArray();
	private static final char[] INHERITANCE_TREE_DEPTH_TEST = "InheritanceTreeDepthTest"
			.toCharArray();
	private static Occurrence[] Solutions;
	private static final char[] STRING = "SuperEntity".toCharArray();

	public InheritanceTreeDepthComposite2(final String name) {
		super(name);
	}

	protected void setUp() {
		if (InheritanceTreeDepthComposite2.Solutions == null) {
			final ICodeLevelModel codeLevelModel = Factory.getInstance()
					.createCodeLevelModel("ptidej.example.composite2");
			try {
				codeLevelModel.create(new CompleteClassFileCreator(
						new String[] {
								"../DeMIMA/target/test-classes/ptidej/example/composite2/" },
						false));
			}
			catch (final CreationException e) {
				e.printStackTrace();
			}

			InheritanceTreeDepthComposite2.Solutions = this.testDesignPattern(
					InheritanceTreeDepthComposite2.class,
					Primitive.ALL_SOLUTIONS,
					InheritanceTreeDepthComposite2.INHERITANCE_TREE_DEPTH_TEST,
					codeLevelModel, OccurrenceGenerator.SOLVER_AUTOMATIC,
					OccurrenceGenerator.PROBLEM_CUSTOM);
		}
	}

	public void testNumberOfSolutions() {
		Assert.assertEquals("Number of solutions", 4,
				InheritanceTreeDepthComposite2.Solutions.length);
	}

	public void testSolution1() {
		//	3.100.Sub-entity = ptidej.example.composite2.Title
		//	3.100.Super-entity = java.lang.Object
		//	3.100.Depth = 3
		Assert.assertEquals("depth(Sub-entity -|>- Super-entity) >= 3",
				"ptidej.example.composite2.IndentedParagraph",
				InheritanceTreeDepthComposite2.Solutions[0]
						.getComponent(InheritanceTreeDepthComposite2.ENTITY)
						.getDisplayValue());
		Assert.assertEquals("depth(Sub-entity -|>- Super-entity) >= 3",
				"ptidej.example.composite2.AbstractDocument",
				InheritanceTreeDepthComposite2.Solutions[0]
						.getComponent(InheritanceTreeDepthComposite2.STRING)
						.getDisplayValue());
		Assert.assertEquals("depth(Sub-entity -|>- Super-entity) >= 3", "3",
				InheritanceTreeDepthComposite2.Solutions[0]
						.getComponent(InheritanceTreeDepthComposite2.DEPTH)
						.getDisplayValue());
	}

	public void testSolution2() {
		//	1.100.Sub-entity = ptidej.example.composite2.IndentedParagraph
		//	1.100.Super-entity = ptidej.example.composite2.AbstractDocument
		//	1.100.Depth = 3
		Assert.assertEquals("depth(Sub-entity -|>- Super-entity) >= 3",
				"ptidej.example.composite2.Paragraph",
				InheritanceTreeDepthComposite2.Solutions[1]
						.getComponent(InheritanceTreeDepthComposite2.ENTITY)
						.getDisplayValue());
		Assert.assertEquals("depth(Sub-entity -|>- Super-entity) >= 3",
				"java.lang.Object",
				InheritanceTreeDepthComposite2.Solutions[1]
						.getComponent(InheritanceTreeDepthComposite2.STRING)
						.getDisplayValue());
		Assert.assertEquals("depth(Sub-entity -|>- Super-entity) >= 3", "3",
				InheritanceTreeDepthComposite2.Solutions[1]
						.getComponent(InheritanceTreeDepthComposite2.DEPTH)
						.getDisplayValue());
	}

	public void testSolution3() {
		//	2.100.Sub-entity = ptidej.example.composite2.Paragraph
		//	2.100.Super-entity = java.lang.Object
		//	2.100.Depth = 3
		Assert.assertEquals("depth(Sub-entity -|>- Super-entity) >= 3",
				"ptidej.example.composite2.Title",
				InheritanceTreeDepthComposite2.Solutions[2]
						.getComponent(InheritanceTreeDepthComposite2.ENTITY)
						.getDisplayValue());
		Assert.assertEquals("depth(Sub-entity -|>- Super-entity) >= 3",
				"java.lang.Object",
				InheritanceTreeDepthComposite2.Solutions[2]
						.getComponent(InheritanceTreeDepthComposite2.STRING)
						.getDisplayValue());
		Assert.assertEquals("depth(Sub-entity -|>- Super-entity) >= 3", "3",
				InheritanceTreeDepthComposite2.Solutions[2]
						.getComponent(InheritanceTreeDepthComposite2.DEPTH)
						.getDisplayValue());
	}

	public void testSolution4() {
		//	4.100.Sub-entity = ptidej.example.composite2.IndentedParagraph
		//	4.100.Super-entity = java.lang.Object
		//	4.100.Depth = 4
		Assert.assertEquals("depth(Sub-entity -|>- Super-entity) >= 3",
				"ptidej.example.composite2.IndentedParagraph",
				InheritanceTreeDepthComposite2.Solutions[3]
						.getComponent(InheritanceTreeDepthComposite2.ENTITY)
						.getDisplayValue());
		Assert.assertEquals("depth(Sub-entity -|>- Super-entity) >= 3",
				"java.lang.Object",
				InheritanceTreeDepthComposite2.Solutions[3]
						.getComponent(InheritanceTreeDepthComposite2.STRING)
						.getDisplayValue());
		Assert.assertEquals("depth(Sub-entity -|>- Super-entity) >= 3", "4",
				InheritanceTreeDepthComposite2.Solutions[3]
						.getComponent(InheritanceTreeDepthComposite2.DEPTH)
						.getDisplayValue());
	}

	public void testSolutionPercentage() {
		for (int i = 0; i < InheritanceTreeDepthComposite2.Solutions.length; i++) {
			Assert.assertEquals("Solution with all constraints", 100,
					InheritanceTreeDepthComposite2.Solutions[i]
							.getConfidence());
		}
	}
}
