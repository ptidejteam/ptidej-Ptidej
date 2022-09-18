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
import padl.kernel.IPackage;
import padl.kernel.IUseRelationship;
import padl.kernel.impl.Factory;

public final class UseTest extends TestAbstractLevelModel {
	private static final long serialVersionUID = -69654226450493126L;

	public UseTest() {
		this.setFactory(Factory.getInstance());

		// Compiler ----> ProgramNodeBuilder ----> ProgramNode

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

		final IUseRelationship link1 =
			this.getFactory().createUseRelationship(
				"message".toCharArray(),
				programNodeBuilder,
				Constants.CARDINALITY_ONE);
		final IUseRelationship link2 =
			this.getFactory().createUseRelationship(
				"message".toCharArray(),
				programNode,
				Constants.CARDINALITY_ONE);

		compiler.addConstituent(link1);
		programNodeBuilder.addConstituent(link2);

		final IPackage enclosingPackage =
			this.getFactory().createPackage("UseTest".toCharArray());
		enclosingPackage.addConstituent(compiler);
		enclosingPackage.addConstituent(programNodeBuilder);
		enclosingPackage.addConstituent(programNode);

		this.addConstituents(enclosingPackage);
	}
}
