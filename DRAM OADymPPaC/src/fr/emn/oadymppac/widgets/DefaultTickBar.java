/*
 * $Id: DefaultTickBar.java,v 1.1 2004/03/01 15:37:16 guehene Exp $
 *
 * Copyright (c) 2001-2002 Jean-Daniel Fekete, Mohammad Ghoniem and
 * Ecole des Mines de Nantes.  All rights reserved.
 *
 * This software is proprietary information of Jean-Daniel Fekete and
 * Ecole des Mines de Nantes.  You shall use it only in accordance
 * with the terms of the license agreement you accepted when
 * downloading this software.  The license is available in the file
 * licence.txt and at the following URL:
 * http://www.emn.fr/fekete/oadymppac/licence.html
 */
package fr.emn.oadymppac.widgets;

import java.awt.Graphics;

/**
 * A default tick bar that displays its segment in the default color.
 *
 * @author Jean-Daniel Fekete
 * @version $Revision: 1.1 $
 */
public class DefaultTickBar implements TickBar {
	double min;
	double max;

	DefaultTickBar(final double min, final double max) {
		this.min = min;
		this.max = max;
	}

	/**
	 * Get the value of max.
	 * @return value of max.
	 */
	public double getMax() {
		return this.max;
	}

	/**
	 * Get the value of min.
	 * @return value of min.
	 */
	public double getMin() {
		return this.min;
	}

	/**
	 * Maximum value for this bar.  The <code>JTickBar</code> first
	 * computes the min and max values to set the bounds.
	 */
	public double max() {
		return this.getMax();
	}

	/**
	 * Minimum value for this bar.  The <code>JTickBar</code> first
	 * computes the min and max values to set the bounds.
	 */
	public double min() {
		return this.getMin();
	}

	public void paint(
		final Graphics g,
		final int x,
		final int y,
		final int h,
		final double origin,
		final double scale) {
		//System.err.println("origin="+origin+", scale="+scale);
		final int y1 = h - (int) ((this.min - origin) * scale) + y;
		int y2 = h - (int) ((this.max - origin) * scale) + y;
		if (y2 == y1) {
			y2 = y1 + 1;
		}
		//System.err.println("y1="+y1);
		//System.err.println("y2="+y2);
		g.drawLine(x, y1, x, y2);
	}

	/**
	 * Set the value of max.
	 * @param v  Value to assign to max.
	 */
	public void setMax(final double v) {
		this.max = v;
	}

	/**
	 * Set the value of min.
	 * @param v  Value to assign to min.
	 */
	public void setMin(final double v) {
		this.min = v;
	}

}
