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
package padl.statement.kernel.impl;

import java.util.Iterator;
import padl.kernel.IConstituent;
import padl.kernel.IStatement;
import padl.kernel.impl.AbstractGenericContainerOfConstituents;
import padl.kernel.impl.GenericContainerConstants;
import padl.kernel.impl.GenericContainerOfInsertionOrderedConstituents;
import padl.statement.kernel.IBlock;

/**
 * @author tagmouty
 */
class Block extends Statement implements IBlock {
	private static final long serialVersionUID = 788273358202402182L;

	private AbstractGenericContainerOfConstituents container =
		new GenericContainerOfInsertionOrderedConstituents(
			this,
			GenericContainerConstants.INITIAL_SIZE_ENTITIES);
	public Block(final char[] anID) {
		super(anID);
	}
	public void addConstituent(final IStatement anStatement) {
		this.container.directlyAddConstituentWithUniqueID(anStatement);
	}
	public Iterator getIteratorOnConstituents() {
		return this.container.getIteratorOnConstituents();
	}
	public void startCloneSession() {
		// The shallow copy must include a shallow copy of
		// member entities, because the clones of methods,
		// fields, and so on, depend on it!
		super.startCloneSession();

		// Yann 2010/10/03: Objects!
		// The "container" is now an instance of a class
		// and must be assigned a new instance independently.
		// ((Package) this.getClone()).container.resetListOfConstituents();
		((Block) this.getClone()).container =
			new GenericContainerOfInsertionOrderedConstituents(
				((Block) this.getClone()));

		// Yann 2015/09/01: Clone of listeners!
		// I don't forget to clone the listners too...
		// TODO To implement

		final Iterator iterator = this.getIteratorOnConstituents();
		while (iterator.hasNext()) {
			final IConstituent constituent = (IConstituent) iterator.next();
			constituent.startCloneSession();
			((IBlock) this.getClone()).addConstituent((IStatement) constituent
				.getClone());
		}
	}
}
