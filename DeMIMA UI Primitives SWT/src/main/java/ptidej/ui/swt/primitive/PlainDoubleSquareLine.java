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
/**
 * 
 */
package ptidej.ui.swt.primitive;

import java.awt.Dimension;
import java.awt.Point;
import org.eclipse.swt.graphics.Device;
import org.eclipse.swt.graphics.GC;
import ptidej.ui.RGB;
import ptidej.ui.kernel.IntermediaryPoint;
import ptidej.ui.primitive.IPlainDoubleSquareLine;

/**
 * @author mohamedkahla
 * @date 	16-05-2006
 *
 */
public final class PlainDoubleSquareLine extends DoubleSquareLine implements
		IPlainDoubleSquareLine {

	// 23-05-2006
	// Mohamed Kahla
	private int splitter = 45;

	/**
	 * @param primitiveFactory
	 * @param origin
	 * @param dimension
	 * @param color
	 */
	PlainDoubleSquareLine(
		final Device device,
		final GC graphics,
		final Point origin,
		final Dimension dimension,
		final RGB color) {

		super(device, graphics, origin, dimension, color);
	}

	public void paint(final int xOffset, final int yOffset) {
		this.getGraphics().setForeground(this.getSWTColor());

		// draw the first line
		this.getGraphics().drawLine(
			this.getPosition().x + xOffset,
			this.getPosition().y + yOffset,
			this.getPosition().x + xOffset,
			this.getPosition().y + this.splitter + yOffset);

		// draw the second line
		this.getGraphics().drawLine(
			this.getPosition().x + xOffset,
			this.getPosition().y + this.splitter + yOffset,
			this.getDestination().x + xOffset,
			this.getPosition().y + this.splitter + yOffset);

		//		 draw the third line
		this.getGraphics().drawLine(
			this.getDestination().x + xOffset,
			this.getPosition().y + this.splitter + yOffset,
			this.getDestination().x + xOffset,
			this.getDestination().y + yOffset);

	}

	public void setEdgeList(final IntermediaryPoint[] someIntermediaryPoints) {
		// TODO Auto-generated method stub
	}

	public void setSplitter(final int split) {
		this.splitter = split;
	}
}
