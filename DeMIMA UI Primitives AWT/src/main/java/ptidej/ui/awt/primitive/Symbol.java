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
import ptidej.ui.RGB;

public abstract class Symbol extends Primitive {
	Symbol(
		final PrimitiveFactory primitiveFactory,
		final Point origin,
		final Dimension dimension,
		final RGB color) {

		super(primitiveFactory, origin, dimension, color);
	}
	int[][] computeCoordinates(final int symbolWidth, final int symbolHeight) {
		final int xOrg = this.getPosition().x;
		final int yOrg = this.getPosition().y;
		int xDest = 0;
		int yDest = 0;
		int xMid1 = 0;
		int yMid1 = 0;
		int xMid2 = 0;
		int yMid2 = 0;

		if (this.getDimension().width == 0 && this.getDimension().height > 0) {
			xDest = xOrg;
			yDest = yOrg + -symbolHeight;

			final int xMid = (xOrg + xDest) / 2;
			final int yMid = (yOrg + yDest) / 2;
			xMid1 = xMid - symbolWidth / 2;
			yMid1 = yMid;
			xMid2 = xMid + symbolWidth / 2;
			yMid2 = yMid;
		}
		else if (this.getDimension().width == 0
				&& this.getDimension().height < 0) {

			xDest = xOrg;
			yDest = yOrg + symbolHeight;

			final int xMid = (xOrg + xDest) / 2;
			final int yMid = (yOrg + yDest) / 2;
			xMid1 = xMid - symbolWidth / 2;
			yMid1 = yMid;
			xMid2 = xMid + symbolWidth / 2;
			yMid2 = yMid;
		}
		else if (this.getDimension().width > 0
				&& this.getDimension().height == 0) {

			xDest = xOrg - symbolWidth;
			yDest = yOrg;

			final int xMid = (xOrg + xDest) / 2;
			final int yMid = (yOrg + yDest) / 2;
			xMid1 = xMid - symbolWidth / 2;
			yMid1 = yMid - symbolHeight / 4;
			xMid2 = xMid - symbolWidth / 2;
			yMid2 = yMid + symbolHeight / 4;
		}
		else if (this.getDimension().width < 0
				&& this.getDimension().height == 0) {

			xDest = xOrg + symbolWidth;
			yDest = yOrg;

			final int xMid = (xOrg + xDest) / 2;
			final int yMid = (yOrg + yDest) / 2;
			xMid1 = xMid + symbolWidth / 2;
			yMid1 = yMid - symbolHeight / 4;
			xMid2 = xMid + symbolWidth / 2;
			yMid2 = yMid + symbolHeight / 4;
		}
		else if (this.getDimension().width < 0) {
			final double slope =
				(double) this.getDimension().height
						/ (double) this.getDimension().width;
			final double alpha = Math.atan(slope);

			xDest = (int) Math.round(xOrg + symbolHeight * Math.cos(alpha));
			yDest = (int) Math.round(yOrg + symbolHeight * Math.sin(alpha));

			final double xMid = (xOrg + xDest) / 2;
			final double yMid = (yOrg + yDest) / 2;
			xMid1 = (int) Math.round(xMid - symbolWidth * Math.sin(alpha) / 2);
			yMid1 = (int) Math.round(yMid + symbolWidth * Math.cos(alpha) / 2);
			xMid2 = (int) Math.round(xMid + symbolWidth * Math.sin(alpha) / 2);
			yMid2 = (int) Math.round(yMid - symbolWidth * Math.cos(alpha) / 2);
		}
		else if (this.getDimension().width > 0) {
			final double slope =
				(double) this.getDimension().height
						/ (double) this.getDimension().width;
			final double alpha = Math.atan(slope);

			xDest = (int) Math.round(xOrg - symbolHeight * Math.cos(alpha));
			yDest = (int) Math.round(yOrg - symbolHeight * Math.sin(alpha));

			final double xMid = (xOrg + xDest) / 2;
			final double yMid = (yOrg + yDest) / 2;
			xMid1 = (int) Math.round(xMid - symbolWidth * Math.sin(alpha) / 2);
			yMid1 = (int) Math.round(yMid + symbolWidth * Math.cos(alpha) / 2);
			xMid2 = (int) Math.round(xMid + symbolWidth * Math.sin(alpha) / 2);
			yMid2 = (int) Math.round(yMid - symbolWidth * Math.cos(alpha) / 2);
		}

		return new int[][] { { xOrg, xMid1, xDest, xMid2 },
				{ yOrg, yMid1, yDest, yMid2 } };
	}
}
