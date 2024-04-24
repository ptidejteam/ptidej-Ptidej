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
package ptidej.ui.awt.occurrence;

import java.awt.Dimension;
import java.awt.Point;
import java.util.StringTokenizer;
import ptidej.ui.Constants;
import ptidej.ui.RGB;
import ptidej.ui.awt.primitive.Primitive;
import ptidej.ui.awt.primitive.PrimitiveFactory;

public class GroupOccurrenceTip extends Primitive implements
		ptidej.ui.occurrence.IGroupOccurrenceTip {

	private final String tip;
	private final int lineHeight;

	GroupOccurrenceTip(
		final PrimitiveFactory primitiveFactory,
		final Point position,
		final String tip,
		final RGB color) {

		super(primitiveFactory, position, Constants.NULL_DIMENSION, color);

		this.setPosition(new Point(
			position.x + Constants.TIP_GAP.width,
			position.y + Constants.TIP_GAP.height));

		final StringBuffer buffer = new StringBuffer();
		char c;
		for (int i = 0; i < tip.length(); i++) {
			c = tip.charAt(i);
			if (c != '\t') {
				buffer.append(c);
			}
			else {
				for (int j = 0; j < Constants.TAB_LENGTH; j++) {
					buffer.append(' ');
				}
			}
		}

		this.tip = buffer.toString();
		this.lineHeight = this.getGraphics().getFontMetrics().getHeight();

		final StringTokenizer tokenizer = new StringTokenizer(this.tip, "\n");
		final int numberOfLines = tokenizer.countTokens();
		int longestLineWidth = 0;
		while (tokenizer.hasMoreTokens()) {
			longestLineWidth =
				Math.max(longestLineWidth, this
					.getGraphics()
					.getFontMetrics()
					.stringWidth(tokenizer.nextToken()));
		}
		longestLineWidth += Constants.TIP_GAP.width;

		this.setDimension(new Dimension(longestLineWidth, numberOfLines
				* this.lineHeight + Constants.TIP_GAP.height));
	}
	public int getVisibleElements() {
		return 0;
	}
	public void paint(final int xOffset, final int yOffset) {
		this.getGraphics().setColor(
			Primitive.convertColor(Constants.TIP_BACKGROUND_COLOR));
		this.getGraphics().fillRect(
			this.getPosition().x + xOffset,
			this.getPosition().y + yOffset,
			this.getDimension().width,
			this.getDimension().height);
		this.getGraphics().setColor(
			Primitive.convertColor(Constants.FOREGROUND_COLOR));
		this.getGraphics().drawRect(
			this.getPosition().x + xOffset,
			this.getPosition().y + yOffset,
			this.getDimension().width,
			this.getDimension().height);

		final StringTokenizer tokenizer = new StringTokenizer(this.tip, "\n");
		int yPos = this.lineHeight;
		while (tokenizer.hasMoreTokens()) {
			this.getGraphics().drawString(
				tokenizer.nextToken(),
				this.getPosition().x + xOffset + Constants.TIP_GAP.width / 2,
				this.getPosition().y + yOffset + yPos);
			yPos += this.lineHeight;
		}
	}
	public void setVisibleElements(final int visibility) {
	}
	public String toString() {
		return this.tip;
	}
}
