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
package ptidej.ui.primitive.awt;

import java.awt.Dimension;
import java.awt.Point;
import ptidej.ui.Constants;
import ptidej.ui.RGB;

public final class DottedSquareLine extends SquareLine implements
		ptidej.ui.primitive.IDottedSquareLine {

	DottedSquareLine(
		final PrimitiveFactory primitiveFactory,
		final Point origin,
		final Dimension dimension,
		final RGB color) {

		super(primitiveFactory, origin, dimension, color);
	}

	public void paint(final int xOffset, final int yOffset) {
		final Point origin = this.getPosition();
		final Point destination = this.getDestination();

		this.getGraphics().setColor(this.getAWTColor());

		if (origin.y < destination.y) {
			for (int movingY = origin.y; movingY < destination.y; movingY +=
				Constants.DOT_LENGTH * 2) {

				this.getGraphics().drawLine(
					origin.x + xOffset,
					movingY + yOffset,
					origin.x + xOffset,
					Math.min(movingY + Constants.DOT_LENGTH, destination.y)
							+ yOffset);
			}
		}
		else {
			for (int movingY = destination.y; movingY < origin.y; movingY +=
				Constants.DOT_LENGTH * 2) {

				this.getGraphics().drawLine(
					origin.x + xOffset,
					movingY + yOffset,
					origin.x + xOffset,
					Math.min(movingY + Constants.DOT_LENGTH, origin.y)
							+ yOffset);
			}
		}

		if (origin.x < destination.x) {
			for (int movingX = origin.x; movingX < destination.x; movingX +=
				Constants.DOT_LENGTH * 2) {

				this.getGraphics().drawLine(
					movingX + xOffset,
					destination.y + yOffset,
					Math.min(movingX + Constants.DOT_LENGTH, destination.x)
							+ xOffset,
					destination.y + yOffset);
			}
		}
		else {
			for (int movingX = destination.x; movingX < origin.x; movingX +=
				Constants.DOT_LENGTH * 2) {

				this.getGraphics().drawLine(
					movingX + xOffset,
					destination.y + yOffset,
					Math.min(movingX + Constants.DOT_LENGTH, origin.x)
							+ xOffset,
					destination.y + yOffset);
			}
		}
	}
}
