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
import java.awt.Font;
import java.awt.Point;
import ptidej.ui.Constants;
import ptidej.ui.RGB;
import ptidej.ui.event.MouseEvent;

public class Button extends Primitive implements ptidej.ui.primitive.IButton {
	private static final Font FONT = new Font(
		"Courrier",
		Font.PLAIN,
		Constants.FONT_HEIGHT);

	private final Font font;
	private String label;
	private final boolean centerText;

	protected Button(
		final PrimitiveFactory primitiveFactory,
		final String s,
		final Point position,
		final boolean centerText,
		final RGB color) {

		this(
			primitiveFactory,
			s,
			position,
			new Dimension(0, 0),
			centerText,
			color);
		this.reSizeToTextDimension();
	}
	protected Button(
		final PrimitiveFactory primitiveFactory,
		final String s,
		final Point position,
		final Dimension dimension,
		final boolean centerText,
		final RGB color) {

		super(primitiveFactory, position, dimension, color);

		this.font = Button.FONT;
		this.setLabel(s);
		this.centerText = centerText;
	}
	public String getLabel() {
		return this.label;
	}
	private String getLongestLines() {
		int prevIndex = 0;
		int index = 0;
		String longestLine = "";
		String tempString = "";

		while ((index = this.label.indexOf('\n', prevIndex + 1)) > 0) {
			tempString = this.label.substring(prevIndex, index);
			if (longestLine.length() < tempString.length()) {
				longestLine = tempString;
			}
			prevIndex = index;
		}

		return longestLine;
	}
	private int getNumberOfLines() {
		int numberOfLines = 0;
		int index = 0;
		while ((index = this.label.indexOf('\n', index + 1)) > 0) {
			numberOfLines++;
		}
		return numberOfLines;
	}
	public void paint(final int xOffset, final int yOffset) {
		final int x = this.getPosition().x + xOffset;
		final int y = this.getPosition().y + yOffset;
		final int w = this.getDimension().width;
		final int h = this.getDimension().height;

		if (this.isSelected()) {
			this.getGraphics().setColor(this.getAWTColor());
			this.getGraphics().fillRect(x, y, w + 1, h);
			this.getGraphics().setColor(
				Primitive.convertColor(Constants.BACKGROUND_COLOR));
		}
		else {
			this.getGraphics().setColor(
				Primitive.convertColor(Constants.BACKGROUND_COLOR));
			this.getGraphics().fillRect(x, y, w, h);
			this.getGraphics().setColor(this.getAWTColor());
			this.getGraphics().drawRect(x, y, w, h);
		}

		// I remember the current font.
		final Font previousFont = this.getGraphics().getFont();
		// I set the new font for the button text.
		this.getGraphics().setFont(this.font);

		// I print out the text of the buttons.
		// int height = this.fontMetrics.getHeight();
		final int height = Constants.FONT_HEIGHT;
		if (this.centerText) {
			for (int index = 0, i = 1; i <= this.getNumberOfLines(); i++) {
				final String tempLabel =
					this.label.substring(
						index,
						index = this.label.indexOf('\n', index + 1));
				index++;

				this.getGraphics().drawString(
					tempLabel,
					x + (w - tempLabel.length() * Constants.FONT_WIDTH) / 2,
					y + (i - 1) * height + height);
			}
		}
		else {
			for (int index = 0, i = 1; i <= this.getNumberOfLines(); i++) {
				final String tempLabel =
					this.label.substring(
						index,
						index = this.label.indexOf('\n', index + 1));
				index++;

				this.getGraphics().drawString(
					tempLabel,
					x + Constants.FONT_WIDTH,
					y + (i - 1) * height + height);
			}
		}

		// I reset the font for the following graphics.
		this.getGraphics().setFont(previousFont);
	}
	public boolean processMouseEvent(final MouseEvent me) {
		if (me.getID() == MouseEvent.MOUSE_CLICKED) {
			final boolean isInRange =
				this.getPosition().x < me.getX()
						&& this.getPosition().y < me.getY()
						&& me.getX() < this.getPosition().x
								+ this.getDimension().width
						&& me.getY() < this.getPosition().y
								+ this.getDimension().height;

			if (isInRange) {
				this.setSelected(!this.isSelected());
				return true;
			}
		}
		return false;
	}
	private void reSizeToTextDimension() {
		this.setDimension(new Dimension(Constants.FONT_WIDTH
				+ this.getLongestLines().length() * Constants.FONT_WIDTH
				+ Constants.FONT_WIDTH, Constants.FONT_DESCENT
				+ Constants.FONT_HEIGHT * this.getNumberOfLines()));
	}
	public void setLabel(final String label) {
		this.label = label;
		if (!this.label.endsWith("\n")) {
			this.label += '\n';
		}
		this.reSizeToTextDimension();
	}
	public String toString() {
		return "Button: " + this.label;
	}
}
