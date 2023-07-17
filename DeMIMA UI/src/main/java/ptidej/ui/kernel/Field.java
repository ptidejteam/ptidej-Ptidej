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

import padl.kernel.IField;
import ptidej.ui.primitive.IPrimitiveFactory;
import util.lang.Modifier;

public final class Field extends Element {
	private IField field;

	public Field(final IPrimitiveFactory primitiveFactory, final IField pField) {
		super(primitiveFactory);
		this.field = pField;
	}
	public void build() {
		// Nothing to do.
	}
	public String getName() {
		return this.field.getDisplayName();
	}
	public void paint(int xOffset, int yOffset) {
		// Nothing to do.
	}
	public String toString() {
		final StringBuffer buffer = new StringBuffer();
		buffer.append(Modifier.toString(this.field.getVisibility()));
		buffer.append(' ');
		buffer.append(this.field.getType());
		buffer.append(' ');
		buffer.append(this.field.getDisplayName());
		buffer.append(";");
		return buffer.toString();
	}
}
