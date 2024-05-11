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

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import padl.kernel.IClass;
import padl.kernel.IElement;
import padl.kernel.IEntityMarker;
import padl.kernel.IFirstClassEntity;
import padl.kernel.IInterface;
import util.lang.Modifier;

class Interface extends AbstractInterface implements IEntityMarker, IInterface {
	private static final long serialVersionUID = 3362527126531938666L;
	// Yann 2010/10/10: DB4O
	// Used to be:
	//	private final List shouldImplementEventList = new ArrayList();
	// I removed the final to make DB4O works...
	// TODO: Understand how to keep it final with DB4O!
	private List shouldImplementEventList = new ArrayList();

	public Interface(final char[] anID, final char[] aName) {
		super(anID, aName);
	}
	public void addConstituent(final IElement anElement) {
		anElement.setAbstract(true);
		super.addConstituent(anElement);
	}
	public void endCloneSession() {
		super.endCloneSession();
		this.shouldImplementEventList.clear();
	}
	public void startCloneSession() {
		super.startCloneSession();

		// Duplicate hierarchy.
		final Interface clonedPInterface = (Interface) this.getClone();
		final Iterator iterator = this.shouldImplementEventList.iterator();
		while (iterator.hasNext()) {
			final IClass currentTarget = (IClass) iterator.next();
			currentTarget.removeImplementedInterface(this);
			currentTarget.addImplementedInterface(clonedPInterface);
		}
	}
	public String toString() {
		final StringBuffer codeEq = new StringBuffer();
		codeEq.append(Modifier.toString(this.getVisibility()));
		codeEq.append(" interface ");
		codeEq.append(getName());
		final Iterator iterator = this.getIteratorOnInheritedEntities();
		if (iterator.hasNext()) {
			codeEq.append(" extends ");
			while (iterator.hasNext()) {
				codeEq
					.append(((IFirstClassEntity) (iterator.next())).getName());
				if (iterator.hasNext())
					codeEq.append(", ");
			}
		}
		return codeEq.toString();
	}
}
