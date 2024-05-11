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
package ptidej.ui.kernel;

import padl.aspectj.kernel.IInterTypeDeclareParents;
import ptidej.ui.primitive.IPrimitiveFactory;

/**
 * @author Jean-Yves Guyomarc'h
 * @since 2005-08-16
 */
public final class InterTypeDeclareParents extends Element {
	private IInterTypeDeclareParents interTypeDeclareParents;

	public InterTypeDeclareParents(
		final IPrimitiveFactory primitiveFactory,
		final IInterTypeDeclareParents aInterTypeDeclareParents) {
		super(primitiveFactory);
		this.interTypeDeclareParents = aInterTypeDeclareParents;
	}
	public void build() {
		// Nothing to do.
	}
	public String getName() {
		final StringBuffer name = new StringBuffer("IT: ");
		name.append(this.interTypeDeclareParents.getDisplayName());
		return name.toString();
	}
	public void paint(final int xOffset, final int yOffset) {
		// Nothing to do.
	}
	public String toString() {
		final StringBuffer buffer = new StringBuffer();
		buffer.append("IT: ");
		buffer.append(this.interTypeDeclareParents.getDisplayName());
		return buffer.toString();
	}
}
