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
package ptidej.solver.test.data.pattern;

import padl.kernel.IClass;
import padl.kernel.IComposition;
import padl.motif.models.TestMotifModel;

public final class CompositionTest extends TestMotifModel {
	public static final char[] AGGREGATE = "Aggregate".toCharArray();
	public static final char[] AGGREGATED = "Aggregated".toCharArray();
	private static final char[] COMPOSITION = "composition".toCharArray();
	private static final char[] COMPOSITION_TEST = "CompositionTest"
		.toCharArray();
	private static final long serialVersionUID = 8546899923749077555L;

	public CompositionTest() {
		final IClass anAggregateClass =
			this.getFactory().createClass(
				CompositionTest.AGGREGATE,
				CompositionTest.AGGREGATE);
		final IClass anAggregatedClass =
			this.getFactory().createClass(
				CompositionTest.AGGREGATED,
				CompositionTest.AGGREGATED);
		final IComposition aComposition =
			this.getFactory().createCompositionRelationship(
				CompositionTest.COMPOSITION,
				anAggregatedClass,
				2);

		anAggregateClass.addConstituent(aComposition);

		this.addConstituent(anAggregateClass);
		this.addConstituent(anAggregatedClass);
	}
	public char[] getName() {
		return CompositionTest.COMPOSITION_TEST;
	}
}
