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
package padl.aspectj.kernel.impl;

import java.util.Iterator;
import padl.aspectj.kernel.IAspect;
import padl.aspectj.kernel.IAspectElement;
import padl.kernel.IConstituentOfEntity;
import padl.kernel.IElement;
import padl.kernel.IFirstClassEntity;
import padl.kernel.IInterfaceActor;
import padl.kernel.exception.ModelDeclarationException;
import padl.kernel.impl.Class;

/**
 * @author Jean-Yves Guyomarc'h
 */
class Aspect extends Class implements IAspect {
	private static final long serialVersionUID = 769206959474845212L;

	public Aspect(final char[] anID) {
		super(anID, anID);
	}
	public Aspect(final char[] anID, final IFirstClassEntity inheritedEntity) {
		super(anID, anID);
		this.addInheritedEntity(inheritedEntity);
	}
	public void addConstituent(final IAspectElement anAspectElement) {

		super.addConstituent(anAspectElement);
	}
	public void addConstituent(final IConstituentOfEntity aConstituent) {
		if (aConstituent instanceof IElement
				|| aConstituent instanceof IAspectElement) {
			super.addConstituent(aConstituent);
		}
		else {
			throw new ModelDeclarationException(
				"Only an element can be added to an entity.");
		}
	}
	public void addImplementedInterface(final IFirstClassEntity anEntity) {
	}
	public void addInheritedEntity(final IAspect anAspect) {
	}
	public IInterfaceActor getImplementedInterface(final String aName) {
		return null;
	}
	public Iterator getIteratorOnImplementedInterfaces() {
		return null;
	}
	public void removeImplementedInterface(final IFirstClassEntity anEntity) {
	}
	public void removeInheritingEntity(final IFirstClassEntity anEntity) {
	}
}
