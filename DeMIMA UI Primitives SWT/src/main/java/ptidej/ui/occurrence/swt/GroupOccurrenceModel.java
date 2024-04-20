/*******************************************************************************
 * Copyright (c) 2001-2014 Yann-Gaël Guéhéneuc  and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/old-licenses/gpl-2.0.html
 * 
 * Contributors:
 *     Yann-Gaël Guéhéneuc  and others, see in file; API and its implementation
 ******************************************************************************/
package ptidej.ui.occurrence.swt;

import java.awt.Dimension;
import java.awt.Point;

import org.eclipse.swt.graphics.Device;
import org.eclipse.swt.graphics.GC;

import ptidej.ui.IVisibility;
import ptidej.ui.RGB;
import ptidej.ui.kernel.ModelGraph;

public final class GroupOccurrenceModel extends GroupOccurrenceTip
		implements IVisibility, ptidej.ui.occurrence.IGroupOccurrenceModel {

	private final ModelGraph pattern;
	private final int offsetY;

	GroupOccurrenceModel(final Device device, final GC graphics,
			final Point position, final ModelGraph pattern, final String tip,
			final RGB color) {

		super(device, graphics, position, tip, color);

		this.pattern = pattern;
		this.offsetY = this.getDimension().height;
		this.setDimension(new Dimension(
				Math.max(this.getDimension().width,
						this.pattern.getDimension().width),
				this.getDimension().height
						+ this.pattern.getDimension().height));
	}

	public int getVisibleElements() {
		return this.pattern.getVisibleElements();
	}

	public void paint(final int xOffset, final int yOffset) {
		super.paint(xOffset, yOffset);
		this.pattern.paint(this.getPosition().x + xOffset,
				this.getPosition().y + yOffset + this.offsetY);
	}

	public void setVisibleElements(final int visibility) {
		this.pattern.setVisibleElements(visibility);
	}
}
