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
import padl.kernel.IAssociation;
import padl.kernel.IClass;
import padl.kernel.IComposition;
import padl.kernel.IPackage;
import padl.kernel.IUseRelationship;
import padl.kernel.impl.Factory;

public final class BadInheritanceTest extends TestAbstractLevelModel {
	private static final long serialVersionUID = -2800020575042406684L;

	public BadInheritanceTest() {
		this.setFactory(Factory.getInstance());

		// A -<|- B -<|- C
		// D -<|- E
		// F -<|- G
		// A -k--> B
		// B ----> C
		// D <>--> E
		// F <#>-> G

		final IClass A =
			this.getFactory().createClass("A".toCharArray(), "A".toCharArray());
		final IClass B =
			this.getFactory().createClass("B".toCharArray(), "B".toCharArray());
		final IClass C =
			this.getFactory().createClass("C".toCharArray(), "C".toCharArray());
		final IClass D =
			this.getFactory().createClass("D".toCharArray(), "D".toCharArray());
		final IClass E =
			this.getFactory().createClass("E".toCharArray(), "E".toCharArray());
		final IClass F =
			this.getFactory().createClass("F".toCharArray(), "F".toCharArray());
		final IClass G =
			this.getFactory().createClass("G".toCharArray(), "G".toCharArray());

		B.addInheritedEntity(A);
		C.addInheritedEntity(B);
		E.addInheritedEntity(D);
		G.addInheritedEntity(F);

		final IUseRelationship link1 =
			this.getFactory().createUseRelationship(
				"link1".toCharArray(),
				B,
				Constants.CARDINALITY_ONE);
		A.addConstituent(link1);
		final IAssociation link2 =
			this.getFactory().createAssociationRelationship(
				"link2".toCharArray(),
				C,
				Constants.CARDINALITY_ONE);
		B.addConstituent(link2);
		final IAggregation link3 =
			this.getFactory().createAggregationRelationship(
				"link3".toCharArray(),
				E,
				Constants.CARDINALITY_ONE);
		D.addConstituent(link3);
		final IComposition link4 =
			this.getFactory().createCompositionRelationship(
				"link4".toCharArray(),
				G,
				Constants.CARDINALITY_ONE);
		F.addConstituent(link4);

		final IPackage enclosingPackage =
			this.getFactory().createPackage("BadInheritanceTest".toCharArray());
		enclosingPackage.addConstituent(A);
		enclosingPackage.addConstituent(B);
		enclosingPackage.addConstituent(C);
		enclosingPackage.addConstituent(D);
		enclosingPackage.addConstituent(E);
		enclosingPackage.addConstituent(F);
		enclosingPackage.addConstituent(G);

		this.addConstituents(enclosingPackage);
	}
}
