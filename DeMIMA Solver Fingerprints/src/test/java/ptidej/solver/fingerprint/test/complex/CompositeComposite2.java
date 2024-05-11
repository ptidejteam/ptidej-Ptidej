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
package ptidej.solver.fingerprint.test.complex;

import org.junit.Assert;
import junit.framework.TestCase;
import padl.creator.classfile.CompleteClassFileCreator;
import padl.kernel.IAbstractLevelModel;
import padl.kernel.ICodeLevelModel;
import padl.kernel.exception.CreationException;
import padl.kernel.impl.Factory;
import ptidej.solver.fingerprint.ReducedDomainBuilder;
import ptidej.solver.fingerprint.Rule;

public final class CompositeComposite2 extends TestCase {
	private static IAbstractLevelModel BuiltAbstractLevelModel;

	public CompositeComposite2(final String name) {
		super(name);
	}
	protected void setUp() {
		if (CompositeComposite2.BuiltAbstractLevelModel == null) {
			final ICodeLevelModel codeLevelModel =
				Factory.getInstance().createCodeLevelModel(
					"ptidej.example.composite2");
			try {
				codeLevelModel
					.create(new CompleteClassFileCreator(
						new String[] { "../DeMIMA/target/test-classes/ptidej/example/composite2/" }));
			}
			catch (final CreationException e) {
				e.printStackTrace();
			}
			final ReducedDomainBuilder rdg =
				new ReducedDomainBuilder(codeLevelModel);
			CompositeComposite2.BuiltAbstractLevelModel =
				rdg.computeReducedDomain(Rule.C_LEAF_ROLE_1);
		}
	}
	public void testNumberOfSolutions() {
		Assert.assertEquals(
			"Number of entity",
			4,
			CompositeComposite2.BuiltAbstractLevelModel
				.getNumberOfConstituents());
	}
}
