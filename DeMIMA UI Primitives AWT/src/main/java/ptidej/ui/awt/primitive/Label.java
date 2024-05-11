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
import java.awt.FontMetrics;
import java.awt.Frame;
import java.awt.Point;
import ptidej.ui.Constants;
import ptidej.ui.RGB;

public final class Label extends Primitive implements
		ptidej.ui.primitive.ILabel {
	private static FontMetrics cacheFontMetrics;
	private static FontMetrics computeFontMetrics() {
		if (Label.cacheFontMetrics == null) {
			Frame frame = new Frame();
			frame.setVisible(true);
			Label.cacheFontMetrics = frame.getGraphics().getFontMetrics();
			frame.dispose();
			frame = null;
		}
		return Label.cacheFontMetrics;
	}
	public static int getHeight() {
		return Label.computeFontMetrics().getHeight() / 2;
	}
	public static int getWidth(final String someText) {
		return Label.computeFontMetrics().stringWidth(someText);
	}

	private String label;
	private int minH;
	private int minW;
	Label(
		final PrimitiveFactory primitiveFactory,
		final String s,
		final Point position,
		final Dimension dimension,
		final RGB color) {

		super(primitiveFactory, position, dimension, color);
		this.setLabel(s);
	}
	public void paint(final int xOffset, final int yOffset) {
		final int x = this.getPosition().x + xOffset;
		final int y = this.getPosition().y + yOffset;
		final int h = this.getDimension().height;
		final int w = this.getDimension().width;

		this.getGraphics().setColor(
			Primitive.convertColor(Constants.BACKGROUND_COLOR));
		this.getGraphics().fillRect(x, y, w, h);
		this.getGraphics().setColor(this.getAWTColor());
		this.getGraphics().drawString(
			this.label,
			x + (w - this.minW) / 2,
			y + (h - this.minH) / 2 + this.minH);
	}
	private void setLabel(final String s) {
		this.label = s;
		this.minW = Label.getWidth(s);
		this.minH = Label.getHeight();
	}
}
