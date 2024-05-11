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
package ptidej.ui.awt.primitive;

import java.awt.Point;
import ptidej.ui.Constants;
import ptidej.ui.RGB;
import ptidej.ui.primitive.ITriangle;

public abstract class Triangle extends Primitive implements ITriangle {
	private final int direction;
	protected Triangle(
		final PrimitiveFactory primitiveFactory,
		final Point origin,
		final int direction,
		final RGB color) {

		super(
			primitiveFactory,
			origin,
			Constants.INHERITANCE_SYMBOL_DIMENSION,
			color);
		this.direction = direction;
	}
	public int getDirection() {
		return this.direction;
	}
}
