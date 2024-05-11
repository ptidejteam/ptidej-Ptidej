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
package ptidej.example.pattern;

import padl.kernel.IClass;
import padl.kernel.IComposition;
import padl.kernel.IPackage;
import padl.motif.models.TestMotifModel;

public final class CompositionExample extends TestMotifModel {
	private static final long serialVersionUID = 8466992687341747490L;

	public CompositionExample() {
		// Aggregate1 <>--> Aggregated1
		// Aggregate2 <>--> Aggregated2
		// Subclass1 -|>- Aggregated2
		// Subclass2 -|>- Aggregated2
		// Subclass3 -|>- Subclass2

		final IClass anAggregateClass1 = this.getFactory().createClass(
				"AggregateClass1".toCharArray(),
				"AggregateClass1".toCharArray());
		final IClass anAggregatedClass1 = this.getFactory().createClass(
				"AggregatedClass1".toCharArray(),
				"AggregatedClass1".toCharArray());
		final IComposition anComposition1 = this.getFactory()
				.createCompositionRelationship("composition".toCharArray(),
						anAggregatedClass1, 1);
		anAggregateClass1.addConstituent(anComposition1);

		final IClass anAggregateClass2 = this.getFactory().createClass(
				"AggregateClass2".toCharArray(),
				"AggregateClass2".toCharArray());
		final IClass anAggregatedClass2 = this.getFactory().createClass(
				"AggregatedClass2".toCharArray(),
				"AggregatedClass2".toCharArray());
		final IClass subclass1 = this.getFactory().createClass(
				"Subclass1".toCharArray(), "Subclass1".toCharArray());
		final IClass subclass2 = this.getFactory().createClass(
				"Subclass2".toCharArray(), "Subclass2".toCharArray());
		final IClass subclass3 = this.getFactory().createClass(
				"Subclass3".toCharArray(), "Subclass3".toCharArray());
		subclass1.addInheritedEntity(anAggregatedClass2);
		subclass2.addInheritedEntity(anAggregatedClass2);
		subclass3.addInheritedEntity(subclass2);
		final IComposition anComposition2 = this.getFactory()
				.createCompositionRelationship("composition".toCharArray(),
						anAggregatedClass2, 1);
		anAggregateClass2.addConstituent(anComposition2);

		final IPackage enclosingPackage = this.getFactory()
				.createPackage("CompositionTest".toCharArray());
		enclosingPackage.addConstituent(anAggregateClass1);
		enclosingPackage.addConstituent(anAggregatedClass1);
		enclosingPackage.addConstituent(anAggregateClass2);
		enclosingPackage.addConstituent(anAggregatedClass2);
		enclosingPackage.addConstituent(subclass1);
		enclosingPackage.addConstituent(subclass2);
		enclosingPackage.addConstituent(subclass3);

		this.addConstituent(enclosingPackage);
	}
}
