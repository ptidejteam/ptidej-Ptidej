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
package ptidej.solver.java.test.data.pattern;

import padl.kernel.IClass;
import padl.kernel.IContainerComposition;
import padl.kernel.IFirstClassEntity;
import padl.kernel.IPackage;
import padl.motif.models.TestMotifModel;

/**
 * @author Yann-Gaël Guéhéneuc
 * @since  2004/09/20
 */
public final class CompositionAndInheritance extends TestMotifModel {
	private static final long serialVersionUID = 1272372464314373630L;

	public CompositionAndInheritance() {
		// java.lang.Object -<|- A -<|- B
		// B <#>-> java.lang.Object

		final IFirstClassEntity object = this.getFactory()
				.createHierarchyRoot();
		final IClass A = this.getFactory().createClass("A".toCharArray(),
				"A".toCharArray());
		final IClass B = this.getFactory().createClass("B".toCharArray(),
				"B".toCharArray());

		A.addInheritedEntity(object);
		B.addInheritedEntity(A);

		final IContainerComposition containerComposition = this.getFactory()
				.createContainerCompositionRelationship(
						"containerComposition".toCharArray(), object, 2);
		B.addConstituent(containerComposition);

		final IPackage enclosingPackage = this.getFactory()
				.createPackage("CompositionAndInheritanceTest".toCharArray());
		enclosingPackage.addConstituent(object);
		enclosingPackage.addConstituent(A);
		enclosingPackage.addConstituent(B);

		this.addConstituent(enclosingPackage);
	}
}
