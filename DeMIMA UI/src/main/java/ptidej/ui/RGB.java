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
package ptidej.ui;

/**
 * This class represents a graphic-framework independant color.
 * It is ready for storage in Maps and Lists (see methods
 * hashCode() and equals(Object)).
 * 
 * @version	0.2
 * @author 	Yann-Gaël Guéhéneuc
 */
public class RGB {
	public static RGB computePositivePercentagedColor(final int aPercentage) {
		final int red =
			(int) (Constants.BACKGROUND_COLOR.getRed()
				- Constants.BACKGROUND_COLOR.getRed()
					* Math.min(
						Math.max(
							aPercentage / 100d,
							Constants.MINIMUM_PERCENTAGE),
						1.0d));
		final int green = Constants.BACKGROUND_COLOR.getGreen();
		final int blue =
			(int) (Constants.BACKGROUND_COLOR.getBlue()
				- Constants.BACKGROUND_COLOR.getBlue()
					* Math.min(
						Math.max(
							aPercentage / 100d,
							Constants.MINIMUM_PERCENTAGE),
						1.0d));

		return new RGB(red, green, blue);
	}
	public static RGB computeNegativePercentagedColor(final int aPercentage) {
		final int red = Constants.BACKGROUND_COLOR.getRed();
		final int green =
			(int) (Constants.BACKGROUND_COLOR.getGreen()
				- Constants.BACKGROUND_COLOR.getGreen()
					* Math.min(
						Math.max(
							aPercentage / 100d,
							Constants.MINIMUM_PERCENTAGE),
						1.0d));
		final int blue =
			(int) (Constants.BACKGROUND_COLOR.getBlue()
				- Constants.BACKGROUND_COLOR.getBlue()
					* Math.min(
						Math.max(
							aPercentage / 100d,
							Constants.MINIMUM_PERCENTAGE),
						1.0d));

		return new RGB(red, green, blue);
	}

	private final int red, green, blue;
	public RGB(final int red, final int green, final int blue) {
		this.red = red;
		this.green = green;
		this.blue = blue;
	}
	public boolean equals(final Object o) {
		if (o instanceof RGB) {
			final RGB other = (RGB) o;
			return this.red == other.red
				&& this.green == other.green
				&& this.blue == other.blue;
		}
		return false;
	}
	public int getBlue() {
		return this.blue;
	}
	public int getGreen() {
		return this.green;
	}
	public int getRed() {
		return this.red;
	}
	public int hashCode() {
		return this.red + this.green + this.blue;
	}
	public String toString() {
		final StringBuffer buffer = new StringBuffer(18);
		buffer.append("RGB(");
		buffer.append(this.red);
		buffer.append(", ");
		buffer.append(this.green);
		buffer.append(", ");
		buffer.append(this.blue);
		buffer.append(')');
		return buffer.toString();
	}
}
