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

import java.awt.Dimension;
import java.awt.Point;
import ptidej.ui.Constants;
import ptidej.ui.RGB;
import ptidej.ui.primitive.ICompositionSymbol;

public final class CompositionSymbol extends Symbol implements
		ICompositionSymbol {

	CompositionSymbol(
		final PrimitiveFactory primitiveFactory,
		final Point origin,
		final Dimension dimension,
		final RGB color) {

		super(primitiveFactory, origin, dimension, color);
	}
	public void paint(final int xOffset, final int yOffset) {
		// Yann 2007/04/01: April's fool's fix!
		// I pretend the direction of the arrow is upsidedown
		// for symbols at the origin of an arrow.
		final Dimension dimension = this.getDimension();
		this.setDimension(new Dimension(-dimension.width, -dimension.height));

		final int[][] coordinates =
			this.computeCoordinates(
				Constants.AAC_SYMBOL_DIMENSION.width,
				Constants.AAC_SYMBOL_DIMENSION.height);

		this.setDimension(new Dimension(dimension.width, dimension.height));

		final int[] x = coordinates[0];
		final int[] y = coordinates[1];

		this.getGraphics().translate(xOffset, yOffset);
		this.getGraphics().setColor(this.getAWTColor());
		this.getGraphics().fillPolygon(x, y, 4);
		this.getGraphics().translate(-xOffset, -yOffset);
	}
}
