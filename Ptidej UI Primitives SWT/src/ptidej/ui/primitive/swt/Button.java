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
package ptidej.ui.primitive.swt;

import java.awt.Dimension;
import java.awt.Point;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Device;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.GC;
import ptidej.ui.Constants;
import ptidej.ui.RGB;
import ptidej.ui.event.MouseEvent;

public class Button extends Primitive implements ptidej.ui.primitive.IButton {
	private final Font font;
	private String label;
	private boolean isSelected;
	private final boolean centerText;
	private int cacheNumberOfLines = 0;
	private String cacheLongestLine = "";

	protected Button(
		final Device device,
		final GC graphics,
		final String s,
		final Point position,
		final boolean centerText,
		final RGB color) {

		this(
			device,
			graphics,
			s,
			position,
			new Dimension(0, 0),
			centerText,
			color);
		this.reSizeToTextDimension();
	}
	protected Button(
		final Device device,
		final GC graphics,
		final String s,
		final Point position,
		final Dimension dimension,
		final boolean centerText,
		final RGB color) {

		super(device, graphics, position, dimension, color);

		this.font =
			new Font(device, "Courrier", Constants.FONT_HEIGHT
					- Constants.FONT_HEIGHT_SWT_SHIFT, SWT.NORMAL);
		this.setLabel(s);
		this.centerText = centerText;
	}
	public String getLabel() {
		return this.label;
	}
	private String getLongestLines() {
		if (!this.cacheLongestLine.equals(this.label)) {
			int prevIndex = 0, index = 0;
			String tempString;
			while ((index = this.label.indexOf('\n', prevIndex + 1)) > 0) {
				tempString = this.label.substring(prevIndex, index);
				if (this.cacheLongestLine.length() < tempString.length()) {
					this.cacheLongestLine = tempString;
				}
				prevIndex = index;
			}
		}
		return this.cacheLongestLine;
	}
	private int getNumberOfLines() {
		if (this.cacheNumberOfLines == 0) {
			int index = 0;
			while ((index = this.label.indexOf('\n', index + 1)) > 0) {
				this.cacheNumberOfLines++;
			}
		}
		return this.cacheNumberOfLines;
	}
	public boolean isSelected() {
		return this.isSelected;
	}
	public void paint(final int xOffset, final int yOffset) {
		// System.out.println("paint(final int xOffset, final int yOffset)");
		// System.out.println("this.getDevice() = " + this.getDevice());
		// System.out.println("this.getGraphics() = " + this.getGraphics());

		final int x = this.getPosition().x + xOffset;
		final int y = this.getPosition().y + yOffset;
		final int h = this.getDimension().height;
		final int w = this.getDimension().width;

		if (this.isSelected()) {
			this.getGraphics().setBackground(this.getSWTColor());
			this.getGraphics().setForeground(
				Primitive.convertColor(
					this.getDevice(),
					Constants.BACKGROUND_COLOR));
			this.getGraphics().fillRectangle(x, y, w + 1, h); // +1 ???
		}
		else {
			this.getGraphics().setBackground(
				Primitive.convertColor(
					this.getDevice(),
					Constants.BACKGROUND_COLOR));
			this.getGraphics().setForeground(this.getSWTColor());
			this.getGraphics().fillRectangle(x, y, w, h);
			this.getGraphics().drawRectangle(x, y, w, h);
		}

		// I remember the current font.
		// final Font previousFont = this.getGraphics().getFont();
		// I set the new font for the button text.
		this.getGraphics().setFont(this.font);

		// I print out the text of the buttons.
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
					y + (i - 1) * height + Constants.FONT_HEIGHT_SWT_SHIFT);
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
					y + (i - 1) * height + Constants.FONT_HEIGHT_SWT_SHIFT);
			}
		}

		// I reset the font for the following graphics.
		// this.getGraphics().setFont(previousFont);
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
				+ Constants.FONT_HEIGHT * this.getNumberOfLines()
				+ Constants.FONT_HEIGHT_SWT_SHIFT));
	}
	public void setLabel(final String label) {
		this.label = label;
		if (!this.label.endsWith("\n")) {
			this.label += '\n';
		}
	}
	public void setSelected(final boolean isSelected) {
		this.isSelected = isSelected;
	}
	public String toString() {
		return "Button: " + this.label;
	}
}
