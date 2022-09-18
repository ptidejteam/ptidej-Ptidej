/*
 * $Id: BorderDrawer.java,v 1.1 2004/03/01 15:37:17 guehene Exp $
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

package fr.emn.oadymppac.tree;

public class BorderDrawer {
	float width = 2;

	public boolean beginBorder(
		final float xmin,
		final float ymin,
		final float xmax,
		final float ymax,
		final int node,
		final int depth) {
		return xmax - xmin > this.width + 1 && ymax - ymin > this.width + 1;
	}
	public float bottomBorder(
		final float xmin,
		final float ymin,
		final float xmax,
		final float ymax,
		final int node,
		final int depth) {
		return this.width;
	}
	public float leftBorder(
		final float xmin,
		final float ymin,
		final float xmax,
		final float ymax,
		final int node,
		final int depth) {
		return this.width;
	}
	public Box remainingBox(
		final float xmin,
		final float ymin,
		final float xmax,
		final float ymax,
		final int node,
		final int depth,
		final Box box) {
		box.xmin = xmin + this.leftBorder(xmin, ymin, xmax, ymax, node, depth);
		box.xmax = xmax - this.rightBorder(xmin, ymin, xmax, ymax, node, depth);
		box.ymin = ymin + this.topBorder(xmin, ymin, xmax, ymax, node, depth);
		box.ymax =
			ymax - this.bottomBorder(xmin, ymin, xmax, ymax, node, depth);
		return box;
	}
	public float rightBorder(
		final float xmin,
		final float ymin,
		final float xmax,
		final float ymax,
		final int node,
		final int depth) {
		return this.width;
	}
	public float topBorder(
		final float xmin,
		final float ymin,
		final float xmax,
		final float ymax,
		final int node,
		final int depth) {
		return this.width;
	}
}
