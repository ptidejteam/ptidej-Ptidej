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

/**
 * @author robidose
 */

import padl.cpp.kernel.IDestructor;
import ptidej.ui.primitive.IPrimitiveFactory;

public final class Destructor extends Element {
	private IDestructor destructor;

	public Destructor(
		final IPrimitiveFactory aPrimitiveFactory,
		final IDestructor aDestructor) {

		super(aPrimitiveFactory);
		this.destructor = aDestructor;
	}
	public void build() {
		// Nothing to do.
	}
	public String getName() {
		final StringBuffer name =
			new StringBuffer(this.destructor.getDisplayName());
		name.append("()");
		return name.toString();
	}
	public void paint(final int xOffset, final int yOffset) {
		// Nothing to do.
	}
	public String toString() {
		final StringBuffer buffer = new StringBuffer();
		buffer.append(this.destructor.getDisplayName());
		buffer.append("(...)");
		return buffer.toString();
	}
}
