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
package sad.rule.creator.model.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import sad.rule.creator.model.IConstituent;
import sad.rule.creator.model.IContainer;

/**
 * @author Pierre Leduc
 */
abstract class Container extends Constituent implements IContainer {
	private final List listOfConstituents = new ArrayList();

	public Container(final String anID) {
		super(anID);
	}

	public void addConstituent(final IConstituent anEntity) {
		this.listOfConstituents.add(anEntity);
	}

	public IConstituent getConstituentFromID(final String anID) {
		final Iterator iterator = this.getIteratorOnConstituents();
		while (iterator.hasNext()) {
			final IConstituent entity = (IConstituent) iterator.next();
			if (entity.getID().equals(anID)) {
				return entity;
			}
		}
		return null;
	}

	public Iterator getIteratorOnConstituents() {
		return this.listOfConstituents.iterator();
	}

	public String toString() {
		final StringBuffer buffer = new StringBuffer();
		final Iterator iterator = this.listOfConstituents.iterator();
		while (iterator.hasNext()) {
			buffer.append(iterator.next().toString());
			buffer.append("\n-------------------------------------");
			if (iterator.hasNext()) {
				buffer.append('\n');
			}
		}
		return buffer.toString();
	}
}
