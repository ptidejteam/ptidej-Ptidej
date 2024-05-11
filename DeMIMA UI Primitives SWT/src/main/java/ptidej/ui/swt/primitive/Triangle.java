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
package ptidej.ui.swt.primitive;

import java.awt.Point;
import org.eclipse.swt.graphics.Device;
import org.eclipse.swt.graphics.GC;
import ptidej.ui.Constants;
import ptidej.ui.RGB;

public abstract class Triangle extends Primitive implements
		ptidej.ui.primitive.ITriangle {

	private final int direction;

	protected Triangle(
		final Device device,
		final GC grapics,
		final Point origin,
		final int direction,
		final RGB color) {

		super(
			device,
			grapics,
			origin,
			Constants.INHERITANCE_SYMBOL_DIMENSION,
			color);
		this.direction = direction;
	}
	public int getDirection() {
		return this.direction;
	}
}
