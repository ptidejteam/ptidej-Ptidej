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

import java.io.IOException;

import padl.kernel.IElement;
import padl.kernel.IMemberClass;
import padl.path.IConstants;

/**
 * @author Yann-Gaël Guéhéneuc
 * @since 2005/08/15
 */

// Yann 2013/07/17: Accesses!
// Must be public for subclasses in other projects

public class MemberClass extends Class implements IMemberClass {
	private static final long serialVersionUID = 561945038924822504L;

	// Sikandar Ejaz 2026/02/27: Removed the attachedElement field!
	// The problem was that MemberClass extends Class which ultimately extends
	// Constituent — not Element. So MemberClass doesn't inherit from Element and
	// therefore doesn't get the attachment logic for free. This means MemberClass
	// implements IElement directly but its parent chain doesn't go through Element.

	private AttachmentSupport attachment = new AttachmentSupport();

	private void readObject(final java.io.ObjectInputStream in) throws IOException, ClassNotFoundException {
		in.defaultReadObject();
		if (this.attachment == null) {
			this.attachment = new AttachmentSupport();
		}
	}

	public MemberClass(final char[] anID, final char[] aName) {
		super(anID, aName);
	}

	public void attachTo(final IElement anElement) {
		this.attachment.attachTo(this, anElement);
	}

	public void detach() {
		this.attachment.detach();
	}

	public IElement getAttachedElement() {
		return this.attachment.getAttachedElement();
	}

	protected char getPathSymbol() {
		return IConstants.MEMBER_ENTITY_SYMBOL;
	}
}
