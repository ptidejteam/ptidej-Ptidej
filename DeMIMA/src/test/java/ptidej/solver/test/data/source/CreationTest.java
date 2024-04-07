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
import padl.kernel.IClass;
import padl.kernel.ICreation;
import padl.kernel.IPackage;
import padl.kernel.impl.Factory;

public final class CreationTest extends TestAbstractLevelModel {
	private static final long serialVersionUID = 3318802277423728223L;

	public CreationTest() {
		this.setFactory(Factory.getInstance());

		// Compiler -*--> ProgramNodeBuilder -*--> ProgramNode

		final IClass compiler =
			this.getFactory().createClass(
				"Compiler".toCharArray(),
				"Compiler".toCharArray());
		final IClass programNodeBuilder =
			this.getFactory().createClass(
				"ProgramNodeBuilder".toCharArray(),
				"ProgramNodeBuilder".toCharArray());
		final IClass programNode =
			this.getFactory().createClass(
				"ProgramNode".toCharArray(),
				"ProgramNode".toCharArray());

		final ICreation link1 =
			this.getFactory().createCreationRelationship(
				"message".toCharArray(),
				programNodeBuilder,
				Constants.CARDINALITY_ONE);
		final ICreation link2 =
			this.getFactory().createCreationRelationship(
				"message".toCharArray(),
				programNode,
				Constants.CARDINALITY_ONE);

		compiler.addConstituent(link1);
		programNodeBuilder.addConstituent(link2);

		final IPackage enclosingPackage =
			this.getFactory().createPackage("CreationTest".toCharArray());
		enclosingPackage.addConstituent(compiler);
		enclosingPackage.addConstituent(programNodeBuilder);
		enclosingPackage.addConstituent(programNode);

		this.addConstituents(enclosingPackage);
	}
}
