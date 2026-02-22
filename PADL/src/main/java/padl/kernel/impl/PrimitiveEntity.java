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
package padl.kernel.impl;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import padl.event.IEvent;
import padl.event.IModelListener;
import padl.kernel.IObservable;
import padl.kernel.IPrimitiveEntity;
import padl.path.IConstants;
import padl.visitor.IVisitor;

class PrimitiveEntity extends Constituent
		implements IPrimitiveEntity, IObservable {

	private static final long serialVersionUID = 3604943383503049188L;

	// Yann 2010/10/10: DB4O
	// Used to be:
	//	private final char[] getName();
	// I removed the final to make DB4O works...
	// TODO: Understand how to keep it final with DB4O!
	public PrimitiveEntity(final char[] aPrimitiveEntityName) {
		super(aPrimitiveEntityName);
	}

	public void accept(final IVisitor aVisitor) {
		aVisitor.visit(this);
	}

	public boolean equals(final Object anotherPrimitiveEntity) {
		if (!(anotherPrimitiveEntity instanceof PrimitiveEntity)) {
			return false;
		}
		else {
			return Arrays.equals(this.getName(),
					((PrimitiveEntity) anotherPrimitiveEntity).getName());
		}
	}

	protected char getPathSymbol() {
		return IConstants.PRIMITIVE_ENTITY_SYMBOL;
	}

	public void addModelListener(IModelListener aModelListener) {

	}

	public void addModelListeners(List aListOfModelListeners) {

	}

	public void fireModelChange(String anEventType, IEvent anEvent) {

	}

	public Iterator getIteratorOnModelListeners() {
		return null;
	}

	public void removeModelListener(IModelListener aModelListener) {

	}

	public void removeModelListeners(List modelListeners) {

	}

	public String toString(final int tab) {
		final StringBuffer codeEq = new StringBuffer();
		codeEq.append(super.toString(tab));
		codeEq.append(' ');
		codeEq.append(this.getName());
		return codeEq.toString();
	}
}
