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

import java.util.Iterator;
import padl.kernel.IConstituent;
import padl.kernel.IGetter;
import padl.kernel.IMethod;

/**
 * @author Yann-Gaël Guéhéneuc
 * @since  2005/08/05
 */
class Getter extends Method implements IGetter {
	private static final long serialVersionUID = 3309069360684034235L;
	private int cardinality;
	public Getter(final IMethod aMethod) {
		super(aMethod.getID());

		this.setAbstract(aMethod.isAbstract());
		if (!aMethod.isAbstract()) {
			this.setCodeLines(aMethod.getCodeLines());
		}
		this.setComment(aMethod.getComment());
		this.setDisplayName(aMethod.getDisplayName());
		this.setName(aMethod.getName());
		this.setPrivate(aMethod.isPrivate());
		this.setPublic(aMethod.isPublic());
		this.setReturnType(aMethod.getReturnType());
		this.setStatic(aMethod.isStatic());
		this.setVisibility(aMethod.getVisibility());
		this.setWeight(aMethod.getWeight());

		final Iterator iterator = aMethod.getIteratorOnConstituents();
		while (iterator.hasNext()) {
			final IConstituent constituent = (IConstituent) iterator.next();
			// Yann 2007/11/14: Clone?
			// Why did I clone the constituent in the method
			// before "moving" them into the getter?
			//	constituent.startCloneSession();
			//	constituent.performCloneSession();
			//	final IConstituent clone = constituent.getClone();
			//	constituent.endCloneSession();
			//	this.addConstituent(clone);
			this.addConstituent(constituent);
		}
	}
	public Getter(final char[] anID) {
		super(anID);
	}
	public int getCardinality() {
		return this.cardinality;
	}
	public void setCardinality(final int aCardinality) {
		this.cardinality = aCardinality;
	}
}
