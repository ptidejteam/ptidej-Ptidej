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
package ptidej.solver.test.data.source;

import padl.kernel.Constants;
import padl.kernel.IAggregation;
import padl.kernel.IClass;
import padl.kernel.IPackage;
import padl.kernel.impl.Factory;

public final class BadCompositionTest1 extends TestAbstractLevelModel {
	private static final long serialVersionUID = 5581269961073362427L;

	public BadCompositionTest1() {
		this.setFactory(Factory.getInstance());

		// Aggregate1 <>--> Aggregated1
		// Aggregate2 <>--> Aggregated2
		// Subclass1 -|>- Aggregated2
		// Subclass2 -|>- Aggregated2
		// Subclass3 -|>- Subclass2

		final IClass anAggregateClass1 =
			this.getFactory().createClass(
				"AggregateClass1".toCharArray(),
				"AggregateClass1".toCharArray());
		final IClass anAggregatedClass1 =
			this.getFactory().createClass(
				"AggregatedClass1".toCharArray(),
				"AggregatedClass1".toCharArray());
		final IAggregation anAggregation1 =
			this.getFactory().createAggregationRelationship(
				"aggregation".toCharArray(),
				anAggregatedClass1,
				Constants.CARDINALITY_ONE);
		anAggregateClass1.addConstituent(anAggregation1);

		final IClass anAggregateClass2 =
			this.getFactory().createClass(
				"AggregateClass2".toCharArray(),
				"AggregateClass2".toCharArray());
		final IClass anAggregatedClass2 =
			this.getFactory().createClass(
				"AggregatedClass2".toCharArray(),
				"AggregatedClass2".toCharArray());
		final IClass subclass1 =
			this.getFactory().createClass(
				"Subclass1".toCharArray(),
				"Subclass1".toCharArray());
		final IClass subclass2 =
			this.getFactory().createClass(
				"Subclass2".toCharArray(),
				"Subclass2".toCharArray());
		final IClass subclass3 =
			this.getFactory().createClass(
				"Subclass3".toCharArray(),
				"Subclass3".toCharArray());
		subclass1.addInheritedEntity(anAggregatedClass2);
		subclass2.addInheritedEntity(anAggregatedClass2);
		subclass3.addInheritedEntity(subclass2);
		final IAggregation anAggregation2 =
			this.getFactory().createAggregationRelationship(
				"aggregation".toCharArray(),
				anAggregatedClass2,
				Constants.CARDINALITY_ONE);
		anAggregateClass2.addConstituent(anAggregation2);

		final IPackage enclosingPackage =
			this.getFactory().createPackage("BadCompositonTest1".toCharArray());
		enclosingPackage.addConstituent(anAggregateClass1);
		enclosingPackage.addConstituent(anAggregatedClass1);
		enclosingPackage.addConstituent(anAggregateClass2);
		enclosingPackage.addConstituent(anAggregatedClass2);
		enclosingPackage.addConstituent(subclass1);
		enclosingPackage.addConstituent(subclass2);
		enclosingPackage.addConstituent(subclass3);

		this.addConstituents(enclosingPackage);
	}
}
