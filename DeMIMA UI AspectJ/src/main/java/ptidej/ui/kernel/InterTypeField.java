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

import padl.aspectj.kernel.IInterTypeField;
import ptidej.ui.primitive.IPrimitiveFactory;

/**
 * @author Jean-Yves Guyomarc'h
 * @since 2005-08-16
 */
public final class InterTypeField extends Element {
	private IInterTypeField interTypeField;

	public InterTypeField(
		final IPrimitiveFactory primitiveFactory,
		final IInterTypeField aInterTypeField) {

		super(primitiveFactory);
		this.interTypeField = aInterTypeField;
	}
	public void build() {
		// Nothing to do.
	}
	public String getName() {
		final StringBuffer name = new StringBuffer("ITField: ");
		name.append(this.interTypeField.getDisplayName());
		return name.toString();
	}
	public void paint(int xOffset, int yOffset) {
		// Nothing to do.
	}
	public String toString() {
		final StringBuffer buffer = new StringBuffer();
		buffer.append("IT: ");
		switch (this.interTypeField.getVisibility()) {
			case 1 :
				buffer.append("+ ");
				break;
			case 2 :
				buffer.append("- ");
				break;
			case 4 :
				buffer.append("# ");
				break;
			default :
				break;
		}
		buffer.append(this.interTypeField.getType());
		buffer.append(" ");
		buffer.append(this.interTypeField.getDisplayName());
		return buffer.toString();
	}
}
