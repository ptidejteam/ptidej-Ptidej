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

import padl.kernel.IAssociation;
import padl.kernel.IClass;
import padl.kernel.IPackage;
import padl.motif.models.TestMotifModel;

public final class AssociationPattern extends TestMotifModel {
	private static final long serialVersionUID = -2125455711226326226L;

	public AssociationPattern() {
		// Aggregate1 ----> Aggregated1
		// Aggregated1 ----> Associated
		// Aggregate2 ----> Aggregated2
		// Subclass1 -|>- Aggregated2
		// Subclass2 -|>- Aggregated2
		// Subclass3 -|>- Subclass2

		final IClass anAggregateClass1 = this.getFactory().createClass(
				"AggregateClass1".toCharArray(),
				"AggregateClass1".toCharArray());
		final IClass anAggregatedClass1 = this.getFactory().createClass(
				"AggregatedClass1".toCharArray(),
				"AggregatedClass1".toCharArray());
		final IAssociation anAssociation1 = this.getFactory()
				.createAssociationRelationship("association".toCharArray(),
						anAggregatedClass1, 1);
		anAggregateClass1.addConstituent(anAssociation1);

		final IClass anAssociatedClass = this.getFactory().createClass(
				"AssociatedClass1".toCharArray(),
				"AssociatedClass1".toCharArray());
		final IAssociation anAssociation2 = this.getFactory()
				.createAssociationRelationship("association".toCharArray(),
						anAssociatedClass, 1);
		anAggregatedClass1.addConstituent(anAssociation2);

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
		final IAssociation anAssociation3 = this.getFactory()
				.createAssociationRelationship("association".toCharArray(),
						anAggregatedClass2, 1);
		anAggregateClass2.addConstituent(anAssociation3);

		final IPackage enclosingPackage = this.getFactory()
				.createPackage("AssociationTest".toCharArray());
		enclosingPackage.addConstituent(anAggregateClass1);
		enclosingPackage.addConstituent(anAggregatedClass1);
		enclosingPackage.addConstituent(anAssociatedClass);
		enclosingPackage.addConstituent(anAggregateClass2);
		enclosingPackage.addConstituent(anAggregatedClass2);
		enclosingPackage.addConstituent(subclass1);
		enclosingPackage.addConstituent(subclass2);
		enclosingPackage.addConstituent(subclass3);

		this.addConstituent(enclosingPackage);
	}
}
