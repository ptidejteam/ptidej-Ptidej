/*******************************************************************************
 * Copyright (c) 2001-2014 Yann-Gaël Guéhéneuc  and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/old-licenses/gpl-2.0.html
 * 
 * Contributors:
 *     Yann-Gaël Guéhéneuc  and others, see in file; API and its implementation
 ******************************************************************************/
package padl.kernel.impl;

/*
 * Classe Element used for type checking and the "attached" protocol.
 * In this protocol, delegation is used instead of bound properties in order to
 * retablish element in its previous state, when detachment occurs...
 * (even if the protocol provides events propagation). 
 */

import org.apache.commons.lang3.ArrayUtils;
import padl.kernel.Constants;
import padl.kernel.IElement;
import padl.kernel.exception.ModelDeclarationException;
import padl.path.IConstants;
import util.multilingual.MultilingualManager;

public abstract class Element extends Constituent implements IElement {
	private static final long serialVersionUID = -600323363393722203L;
	private IElement attachedElement;

	public Element(final char[] actorID) {
		super(actorID);
	}
	protected char getPathSymbol() {
		return IConstants.ELEMENT_SYMBOL;
	}
	public void attachTo(final IElement anElement) {
		if (anElement != null) {
			if (anElement == this) {
				throw new ModelDeclarationException(
					MultilingualManager
						.getString("ELEM_ATTACH", IElement.class));
			}

			if (!anElement.getClass().isInstance(this)) {
				throw new ModelDeclarationException(
					MultilingualManager.getString(
						"ATTACH",
						IElement.class,
						new Object[] { anElement.getClass() }));
			}

			this.detach();
			this.attachedElement = anElement;
		}
	}
	public void detach() {
		final IElement oldAttachedElement = this.getAttachedElement();

		if (oldAttachedElement == null) {
			return;
		}

		this.attachedElement = null;
	}
	public IElement getAttachedElement() {
		return this.attachedElement;
	}
	public char[] getName() {
		// Delegation is used...
		if (this.getAttachedElement() == null) {
			return super.getName();
		}
		return this.getAttachedElement().getName();
	}
	/**
	 * This methods is used by the clone protocol.
	 */
	public void performCloneSession() {
		if (this.getAttachedElement() != null) {
			((Element) this.getClone()).attachedElement =
				(Element) this.getAttachedElement().getClone();
		}

		super.performCloneSession();
	}
	public void startCloneSession() {
		super.startCloneSession();

		// Yann 2010/10/03: Unique ID... again!
		// I used to concatenate a unique number for certain element,
		// for example binary class relationships, because a class
		// could have two with the same ID, name, etc. However, when
		// cloning, this unique number got duplicated unnecessarily,
		// so I must take care of it...
		// TODO: It would be cleaner to have two separate "unique IDs"
		// one containing the invariable ID, the other the variable number.
		// This code is actually dumb! Because the ID of an element
		// can contain legitimate underscores... So, I replace such
		// underscore with something that only PADL would do...
		final int index =
			this.getDisplayID().indexOf(Constants.NUMBER_SEPARATOR);
		if (index > 0) {
			((Element) this.getClone()).setID(ArrayUtils.subarray(
				this.getID(),
				0,
				index));
		}

		((Element) this.getClone()).attachedElement = null;
	}
}
