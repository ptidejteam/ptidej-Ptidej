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

import java.util.Iterator;
import padl.kernel.IMethod;
import padl.kernel.IParameter;
import ptidej.ui.primitive.IPrimitiveFactory;
import util.lang.Modifier;

public final class Method extends Element {
	private IMethod method;

	public Method(
		final IPrimitiveFactory primitiveFactory,
		final IMethod aMethod) {

		super(primitiveFactory);
		this.method = aMethod;
	}
	public void build() {
		// Nothing to do.
	}
	public String getName() {
		final StringBuffer name =
			new StringBuffer(this.method.getDisplayName());
		name.append("()");
		return name.toString();
	}
	public void paint(final int xOffset, final int yOffset) {
		// Nothing to do.
	}
	public String toString() {
		final StringBuffer buffer = new StringBuffer();
		buffer.append(Modifier.toString(this.method.getVisibility()));
		buffer.append(' ');
		buffer.append(this.method.getReturnType());
		buffer.append(' ');
		buffer.append(this.method.getDisplayName());
		// buffer.append("(...)");
		buffer.append('(');
		final Iterator iterator =
			this.method.getIteratorOnConstituents(IParameter.class);
		while (iterator.hasNext()) {
			final IParameter parameter = (IParameter) iterator.next();
			buffer.append(parameter.getTypeName());
			if (iterator.hasNext()) {
				buffer.append(", ");
			}
		}
		buffer.append(')');
		return buffer.toString();
	}
}
