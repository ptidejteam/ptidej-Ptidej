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
import padl.creator.classfile.CompleteClassFileCreator;
import padl.kernel.ICodeLevelModel;
import padl.kernel.exception.CreationException;
import padl.kernel.impl.Factory;
import ptidej.solver.Occurrence;
import ptidej.solver.OccurrenceGenerator;
import ptidej.solver.test.claire.Primitive;

public final class InheritanceTreeDepth extends Primitive {
	private static final char[] DEPTH = "Depth".toCharArray();
	private static final char[] ENTITY = "SubEntity".toCharArray();
	private static final char[] INHERITANCE_TREE_DEPTH_TEST =
		"InheritanceTreeDepthTest".toCharArray();
	private static Occurrence[] Solutions;
	private static final char[] STRING = "SuperEntity".toCharArray();

	public InheritanceTreeDepth(final String name) {
		super(name);
	}
	protected void setUp() {
		if (InheritanceTreeDepth.Solutions == null) {
			final ICodeLevelModel codeLevelModel =
				Factory.getInstance().createCodeLevelModel(
					"ptidej.example.composite2");
			try {
				codeLevelModel
					.create(new CompleteClassFileCreator(
						new String[] { "../Ptidej Tests/bin/ptidej/example/composite2/" },
						false));
			}
			catch (final CreationException e) {
				e.printStackTrace();
			}

			InheritanceTreeDepth.Solutions =
				this.testDesignPattern(
					InheritanceTreeDepth.class,
					Primitive.ALL_SOLUTIONS,
					InheritanceTreeDepth.INHERITANCE_TREE_DEPTH_TEST,
					codeLevelModel,
					OccurrenceGenerator.SOLVER_AUTOMATIC,
					OccurrenceGenerator.PROBLEM_CUSTOM);
		}
	}
	public void testNumberOfSolutions() {
		Assert.assertEquals(
			"Number of solutions",
			4,
			InheritanceTreeDepth.Solutions.length);
	}
	public void testSolution1() {
		//	3.100.Sub-entity = ptidej.example.composite2.Title
		//	3.100.Super-entity = java.lang.Object
		//	3.100.Depth = 3
		Assert.assertEquals(
			"depth(Sub-entity -|>- Super-entity) >= 3",
			"ptidej.example.composite2.IndentedParagraph",
			InheritanceTreeDepth.Solutions[0].getComponent(
				InheritanceTreeDepth.ENTITY).getDisplayValue());
		Assert.assertEquals(
			"depth(Sub-entity -|>- Super-entity) >= 3",
			"ptidej.example.composite2.AbstractDocument",
			InheritanceTreeDepth.Solutions[0].getComponent(
				InheritanceTreeDepth.STRING).getDisplayValue());
		Assert.assertEquals(
			"depth(Sub-entity -|>- Super-entity) >= 3",
			"3",
			InheritanceTreeDepth.Solutions[0].getComponent(
				InheritanceTreeDepth.DEPTH).getDisplayValue());
	}
	public void testSolution2() {
		//	1.100.Sub-entity = ptidej.example.composite2.IndentedParagraph
		//	1.100.Super-entity = ptidej.example.composite2.AbstractDocument
		//	1.100.Depth = 3
		Assert.assertEquals(
			"depth(Sub-entity -|>- Super-entity) >= 3",
			"ptidej.example.composite2.Paragraph",
			InheritanceTreeDepth.Solutions[1].getComponent(
				InheritanceTreeDepth.ENTITY).getDisplayValue());
		Assert.assertEquals(
			"depth(Sub-entity -|>- Super-entity) >= 3",
			"java.lang.Object",
			InheritanceTreeDepth.Solutions[1].getComponent(
				InheritanceTreeDepth.STRING).getDisplayValue());
		Assert.assertEquals(
			"depth(Sub-entity -|>- Super-entity) >= 3",
			"3",
			InheritanceTreeDepth.Solutions[1].getComponent(
				InheritanceTreeDepth.DEPTH).getDisplayValue());
	}
	public void testSolution3() {
		//	2.100.Sub-entity = ptidej.example.composite2.Paragraph
		//	2.100.Super-entity = java.lang.Object
		//	2.100.Depth = 3
		Assert.assertEquals(
			"depth(Sub-entity -|>- Super-entity) >= 3",
			"ptidej.example.composite2.Title",
			InheritanceTreeDepth.Solutions[2].getComponent(
				InheritanceTreeDepth.ENTITY).getDisplayValue());
		Assert.assertEquals(
			"depth(Sub-entity -|>- Super-entity) >= 3",
			"java.lang.Object",
			InheritanceTreeDepth.Solutions[2].getComponent(
				InheritanceTreeDepth.STRING).getDisplayValue());
		Assert.assertEquals(
			"depth(Sub-entity -|>- Super-entity) >= 3",
			"3",
			InheritanceTreeDepth.Solutions[2].getComponent(
				InheritanceTreeDepth.DEPTH).getDisplayValue());
	}
	public void testSolution4() {
		//	4.100.Sub-entity = ptidej.example.composite2.IndentedParagraph
		//	4.100.Super-entity = java.lang.Object
		//	4.100.Depth = 4
		Assert.assertEquals(
			"depth(Sub-entity -|>- Super-entity) >= 3",
			"ptidej.example.composite2.IndentedParagraph",
			InheritanceTreeDepth.Solutions[3].getComponent(
				InheritanceTreeDepth.ENTITY).getDisplayValue());
		Assert.assertEquals(
			"depth(Sub-entity -|>- Super-entity) >= 3",
			"java.lang.Object",
			InheritanceTreeDepth.Solutions[3].getComponent(
				InheritanceTreeDepth.STRING).getDisplayValue());
		Assert.assertEquals(
			"depth(Sub-entity -|>- Super-entity) >= 3",
			"4",
			InheritanceTreeDepth.Solutions[3].getComponent(
				InheritanceTreeDepth.DEPTH).getDisplayValue());
	}
	public void testSolutionPercentage() {
		for (int i = 0; i < InheritanceTreeDepth.Solutions.length; i++) {
			Assert.assertEquals(
				"Solution with all constraints",
				100,
				InheritanceTreeDepth.Solutions[i].getConfidence());
		}
	}
}
