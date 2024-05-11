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
package ptidej.ui.swt.occurrence;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.MouseEvent;
import org.eclipse.swt.graphics.Device;
import org.eclipse.swt.graphics.GC;
import ptidej.ui.Constants;
import ptidej.ui.RGB;
import ptidej.ui.swt.primitive.Button;
import ptidej.ui.swt.primitive.Primitive;

public final class GroupRectangleButton extends Button implements
		ptidej.ui.occurrence.IGroupRectangleButton {

	private final int percentageOfGray;
	private int[][] handlesPositions;

	GroupRectangleButton(
		final Device device,
		final GC graphics,
		final int percentageOfGray,
		final Point position,
		final Dimension dimension,
		final RGB color) {

		super(
			device,
			graphics,
			Integer.toString(percentageOfGray),
			position,
			dimension,
			true,
			color);

		this.percentageOfGray = percentageOfGray;
		this.setPositionAndDimension();
	}
	public void paint(final int xOffset, final int yOffset) {
		this.getGraphics().setForeground(
			Primitive.convertColor(
				this.getDevice(),
				RGB.computePositivePercentagedColor(this.percentageOfGray)));
		this.getGraphics().drawRectangle(
			this.getPosition().x + xOffset,
			this.getPosition().y + yOffset,
			this.getDimension().width,
			this.getDimension().height);
		this.getGraphics().drawRectangle(
			this.getPosition().x + xOffset - 1,
			this.getPosition().y + yOffset - 1,
			this.getDimension().width + 2,
			this.getDimension().height + 2);

		if (this.isSelected()) {
			this
				.getGraphics()
				.setBackground(
					Primitive.convertColor(this.getDevice(), RGB
						.computePositivePercentagedColor(this.percentageOfGray)));
			for (int i = 0; i < this.handlesPositions.length; i++) {
				this.getGraphics().fillRectangle(
					this.handlesPositions[i][0] + xOffset,
					this.handlesPositions[i][1] + yOffset,
					Constants.SOLUTION_HANDLE_DIMENSION.width,
					Constants.SOLUTION_HANDLE_DIMENSION.height);
			}
		}
		else {
			for (int i = 0; i < this.handlesPositions.length; i++) {
				this
					.getGraphics()
					.setForeground(
						Primitive.convertColor(
							this.getDevice(),
							RGB
								.computePositivePercentagedColor(this.percentageOfGray)));
				this.getGraphics().drawRectangle(
					this.handlesPositions[i][0] + xOffset,
					this.handlesPositions[i][1] + yOffset,
					Constants.SOLUTION_HANDLE_DIMENSION.width,
					Constants.SOLUTION_HANDLE_DIMENSION.height);
			}
		}
	}
	public boolean processMouseEvent(final MouseEvent me) {
		if (me.getID() == MouseEvent.MOUSE_CLICKED) {
			boolean isInRange = false;
			for (int i = 0; i < this.handlesPositions.length; i++) {
				isInRange |=
					this.handlesPositions[i][0] <= me.getX()
							&& this.handlesPositions[i][1] <= me.getY()
							&& me.getX() <= this.handlesPositions[i][0]
									+ Constants.SOLUTION_HANDLE_DIMENSION.width
							&& me.getY() <= this.handlesPositions[i][1]
									+ Constants.SOLUTION_HANDLE_DIMENSION.height;
			}

			if (isInRange) {
				if (me.getID() == MouseEvent.MOUSE_CLICKED) {
					this.setSelected(!this.isSelected());
					return true;
				}
			}
		}

		return false;
	}
	public void setDimension(final Dimension dimension) {
		super.setDimension(dimension);
		this.setPositionAndDimension();
	}
	public void setPosition(final Point position) {
		super.setPosition(position);
		this.setPositionAndDimension();
	}
	private void setPositionAndDimension() {
		final int x = this.getPosition().x;
		final int y = this.getPosition().y;
		final int w = this.getDimension().width;
		final int h = this.getDimension().height;

		this.handlesPositions = new int[4][];
		this.handlesPositions[0] = new int[] { x, y };
		this.handlesPositions[1] =
			new int[] { x, y + h - Constants.SOLUTION_HANDLE_DIMENSION.height };
		this.handlesPositions[2] =
			new int[] { x + w - Constants.SOLUTION_HANDLE_DIMENSION.width, y };
		this.handlesPositions[3] =
			new int[] { x + w - Constants.SOLUTION_HANDLE_DIMENSION.width,
					y + h - Constants.SOLUTION_HANDLE_DIMENSION.height };
	}
}
