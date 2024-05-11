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
import ptidej.ui.kernel.IntermediaryPoint;
import ptidej.ui.primitive.IPlainDoubleSquareLine;

/**
 * @author Mohamed Kahla
 * @since  2006/05/16
 *
 */
public final class PlainDoubleSquareLine extends DoubleSquareLine implements
		IPlainDoubleSquareLine {

	public PlainDoubleSquareLine(
		final PrimitiveFactory primitiveFactory,
		final Point origin,
		final Dimension dimension,
		final RGB color) {
		super(primitiveFactory, origin, dimension, color);

	}
	// 18-05-2006
	// Mohamed Kahla
	public void paint(final int xOffset, final int yOffset) {
		if (this.intermediaryPoints.length == 0) {
			this.getGraphics().setColor(this.getAWTColor());
			this.getGraphics().drawLine(
				this.getPosition().x + xOffset,
				this.getPosition().y + yOffset,
				this.getDestination().x + xOffset,
				this.getDestination().y + yOffset);
		}
		else {
			this.paintSugiyamaLine(xOffset, yOffset);
		}
	}
	// 07-07-2006
	// Mohamed Kahla
	private void paintSugiyamaLine(final int xOffset, final int yOffset) {
		this.getGraphics().setColor(this.getAWTColor());

		final IntermediaryPoint first = this.intermediaryPoints[0];
		int xDummyPosition = first.getX();
		int yDummyPosition = first.getY();

		this.getGraphics().drawLine(
			this.getPosition().x + xOffset,
			this.getPosition().y + yOffset,
			xDummyPosition + xOffset,
			yDummyPosition + yOffset);

		IntermediaryPoint lastDummy = first;

		for (int i = 1; i < this.intermediaryPoints.length; i++) {
			final IntermediaryPoint dummy = this.intermediaryPoints[i];
			xDummyPosition = dummy.getX();
			yDummyPosition = dummy.getY();

			this.getGraphics().drawLine(
				lastDummy.getX() + xOffset,
				lastDummy.getY() + yOffset,
				xDummyPosition + xOffset,
				yDummyPosition + yOffset);

			lastDummy = dummy;
		}

		this.getGraphics().drawLine(
			lastDummy.getX() + xOffset,
			lastDummy.getY() + yOffset,
			this.getDestination().x + xOffset,
			this.getDestination().y + yOffset);
	}
}
