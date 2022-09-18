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

import padl.kernel.IClass;
import padl.kernel.IFirstClassEntity;
import padl.kernel.IPackage;
import padl.kernel.impl.Factory;

public final class InheritanceTest extends TestAbstractLevelModel {
	private static final long serialVersionUID = 2188891814109630235L;

	public InheritanceTest() {
		this.setFactory(Factory.getInstance());

		// java.lang.Object -<|- A -<|- B -<|- C
		// java.lang.Object -<|- D -<|- E
		// java.lang.Object -<|- F

		final IFirstClassEntity Object =
			this.getFactory().createHierarchyRoot();
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

		A.addInheritedEntity(Object);
		D.addInheritedEntity(Object);
		F.addInheritedEntity(Object);
		B.addInheritedEntity(A);
		C.addInheritedEntity(B);
		E.addInheritedEntity(D);

		final IPackage enclosingPackage =
			this.getFactory().createPackage("InheritanceTest".toCharArray());
		enclosingPackage.addConstituent(Object);
		enclosingPackage.addConstituent(A);
		enclosingPackage.addConstituent(B);
		enclosingPackage.addConstituent(C);
		enclosingPackage.addConstituent(D);
		enclosingPackage.addConstituent(E);
		enclosingPackage.addConstituent(F);

		this.addConstituents(enclosingPackage);
	}
}
