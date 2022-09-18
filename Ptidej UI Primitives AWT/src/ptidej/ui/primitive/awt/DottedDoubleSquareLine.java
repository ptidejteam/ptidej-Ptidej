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
import ptidej.ui.kernel.IntermediaryPoint;
import ptidej.ui.primitive.IDottedDoubleSquareLine;

/**
 * @author Mohamed Kahla
 * @since  2006/05/16
 */
public final class DottedDoubleSquareLine extends DoubleSquareLine implements
		IDottedDoubleSquareLine {

	public DottedDoubleSquareLine(
		final PrimitiveFactory primitiveFactory,
		final Point origin,
		final Dimension dimension,
		final RGB color) {

		super(primitiveFactory, origin, dimension, color);
	}
	private void drawDottedLine(
		final int xOffset,
		final int yOffset,
		final int xFrom,
		final int yFrom,
		final int xTo,
		final int yTo) {

		this.getGraphics().setColor(this.getAWTColor());

		final int x1 = xFrom + xOffset;
		final int y1 = yFrom + yOffset;
		final int x2 = xTo + xOffset;
		final int y2 = yTo + yOffset;

		int segStartX, segStartY;
		int segEndX, segEndY;

		final int dx = x2 - x1;
		final int dy = y2 - y1;
		final int dxdx = dx * dx;
		final int dydy = dy * dy;

		final int length = (int) Math.sqrt(dxdx + dydy);

		int i = 0;
		while (i < length) {
			segStartX = x1 + dx * i / length;
			segStartY = y1 + dy * i / length;
			i += Constants.DOT_LENGTH;
			if (i >= length) {
				segEndX = x2;
				segEndY = y2;
			}
			else {
				segEndX = x1 + dx * i / length;
				segEndY = y1 + dy * i / length;
			}
			this.getGraphics().drawLine(segStartX, segStartY, segEndX, segEndY);
			i += Constants.DOT_LENGTH;
		}
	}
	// 06-07-2006
	// Mohamed Kahla
	public void paint(final int xOffset, final int yOffset) {
		if (this.intermediaryPoints.length == 0) {
			this.getGraphics().setColor(this.getAWTColor());
			this.drawDottedLine(
				xOffset,
				yOffset,
				this.getPosition().x,
				this.getPosition().y,
				this.getDestination().x,
				this.getDestination().y);
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

		this.drawDottedLine(
			xOffset,
			yOffset,
			this.getPosition().x,
			this.getPosition().y,
			xDummyPosition,
			yDummyPosition);

		IntermediaryPoint lastDummy = first;

		for (int i = 1; i < this.intermediaryPoints.length; i++) {
			final IntermediaryPoint dummy = this.intermediaryPoints[i];
			xDummyPosition = dummy.getX();
			yDummyPosition = dummy.getY();

			this.drawDottedLine(
				xOffset,
				yOffset,
				lastDummy.getX(),
				lastDummy.getY(),
				xDummyPosition,
				yDummyPosition);

			lastDummy = dummy;
		}

		this.drawDottedLine(
			xOffset,
			yOffset,
			lastDummy.getX(),
			lastDummy.getY(),
			this.getDestination().x,
			this.getDestination().y);
	}
}
