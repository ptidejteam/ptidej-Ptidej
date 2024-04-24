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

import java.awt.Dimension;
import java.awt.Point;
import org.eclipse.swt.graphics.Device;
import org.eclipse.swt.graphics.GC;
import ptidej.ui.RGB;

public final class Line extends Primitive implements ptidej.ui.primitive.ILine {
	Line(
		final Device device,
		final GC graphics,
		final Point origin,
		final Dimension dimension,
		final RGB color) {

		super(device, graphics, origin, dimension, color);
	}
	public void paint(final int xOffset, final int yOffset) {
		final Point origin = this.getPosition();
		final Point destination = this.getDestination();

		this.getGraphics().setForeground(this.getSWTColor());
		this.getGraphics().drawLine(
			origin.x + xOffset,
			origin.y + yOffset,
			destination.x + xOffset,
			destination.y + yOffset);
	}
}
