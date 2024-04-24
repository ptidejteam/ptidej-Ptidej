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
import ptidej.ui.Constants;
import ptidej.ui.RGB;
import ptidej.ui.primitive.IAggregationSymbol;

public final class AggregationSymbol extends Symbol implements
		IAggregationSymbol {

	AggregationSymbol(
		final Device device,
		final GC graphics,
		final Point origin,
		final Dimension dimension,
		final RGB color) {

		super(device, graphics, origin, dimension, color);
	}
	public void paint(final int xOffset, final int yOffset) {
		final int[][] coordinates =
			this.computeCoordinates(
				Constants.AAC_SYMBOL_DIMENSION.width,
				Constants.AAC_SYMBOL_DIMENSION.height);

		final int[] x = coordinates[0];
		final int[] y = coordinates[1];

		final int[] vertices = new int[x.length + y.length];
		for (int i = 0, j = 0; i < x.length; i++) {
			vertices[j++] = x[i] + xOffset;
			vertices[j++] = y[i] + yOffset;
		}

		this
			.getGraphics()
			.setForeground(
				Primitive.convertColor(
					this.getDevice(),
					Constants.BACKGROUND_COLOR));
		this.getGraphics().fillPolygon(vertices);
		this.getGraphics().setForeground(this.getSWTColor());
		this.getGraphics().drawPolygon(vertices);
	}
}
