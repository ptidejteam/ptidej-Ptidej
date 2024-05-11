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

import padl.kernel.IElement;
import padl.kernel.IMemberClass;
import padl.kernel.exception.ModelDeclarationException;
import padl.path.IConstants;
import util.multilingual.MultilingualManager;

/**
 * @author Yann-Gaël Guéhéneuc
 * @since  2005/08/15
 */
// Yann 2013/07/17: Accesses!
// Must be public for subclasses in other projects
public class MemberClass extends Class implements IMemberClass {
	private static final long serialVersionUID = 561945038924822504L;
	// TODO: Remove this attachedElement field!
	private IElement attachedElement;

	public MemberClass(final char[] anID, final char[] aName) {
		super(anID, aName);
	}
	public void attachTo(final IElement anElement) {
		if (anElement != null) {
			if (anElement == this) {
				throw new ModelDeclarationException(
					MultilingualManager.getString("ELEM_ATTACH", IElement.class));
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
	protected char getPathSymbol() {
		return IConstants.MEMBER_ENTITY_SYMBOL;
	}
}
