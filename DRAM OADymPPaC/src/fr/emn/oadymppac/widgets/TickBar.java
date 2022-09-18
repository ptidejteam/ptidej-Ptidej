/*
 * $Id: TickBar.java,v 1.1 2004/03/01 15:37:16 guehene Exp $
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
 * A tick bar shows a vertical line in a <code>JTickBars</code>
 * component.
 *
 * @author Jean-Daniel Fekete
 * @version $Revision: 1.1 $
 */
public interface TickBar {
	/**
	 * Maximum value for this bar.  The <code>JTickBar</code> first
	 * computes the min and max values to set the bounds.
	 */
	public double max();

	/**
	 * Minimum value for this bar.  The <code>JTickBar</code> first
	 * computes the min and max values to set the bounds.
	 */
	public double min();

	/**
	 * Paint the bar on the <code>JTickBar</code>.  A default color
	 * is set either by the component or by the previous tick bar.
	 *
	 * @param g	the Graphics for drawing
	 * @param x	the x coord in the component
	 * @param y the lower y coord in the component
	 * @param h	the height of the component
	 * @param orgin	the mimimum value of all the tick bars in the
	 * component.
	 * @param scale	the scale required so that all tick bars fit
	 * in the component's height
	 */
	public void paint(
		Graphics g,
		int x,
		int y,
		int h,
		double origin,
		double scale);

}
