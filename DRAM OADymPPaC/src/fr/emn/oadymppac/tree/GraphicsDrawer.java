/*
 * $Id: GraphicsDrawer.java,v 1.2 2006/08/11 23:11:03 guehene Exp $
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

import java.awt.Graphics;

public class GraphicsDrawer implements Drawer {
	Graphics graphics;
	ColorDrawer color;
	BorderDrawer border;

	public GraphicsDrawer() {
		this(null, new ColorDrawer(), new BorderDrawer());
	}
	public GraphicsDrawer(
		final Graphics graphics,
		final ColorDrawer color,
		final BorderDrawer border) {
		this.graphics = graphics;
		this.color = color;
		this.border = border;
	}

	public boolean beginBox(
		final float xmin,
		final float ymin,
		final float xmax,
		final float ymax,
		final int node,
		final int depth) {
		return (int) xmin < (int) xmax && (int) ymin < (int) ymax;
	}

	public Box drawBorder(
		float xmin,
		float ymin,
		float xmax,
		float ymax,
		final int node,
		final int depth,
		final Box box) {
		if (this.border.beginBorder(xmin, ymin, xmax, ymax, node, depth)) {
			float w;
			w = this.border.leftBorder(xmin, ymin, xmax, ymax, node, depth);
			if (w != 0) {
				this.drawBox(xmin, ymin, xmin + w, ymax, node, depth);
				xmin += w;
			}
			w = this.border.topBorder(xmin, ymin, xmax, ymax, node, depth);
			if (w != 0) {
				this.drawBox(xmin, ymax - w, xmax, ymax, node, depth);
				ymax -= w;
			}
			w = this.border.rightBorder(xmin, ymin, xmax, ymax, node, depth);
			if (w != 0) {
				this.drawBox(xmax - w, ymin, xmax, ymax, node, depth);
				xmax -= w;
			}
			w = this.border.bottomBorder(xmin, ymin, xmax, ymax, node, depth);
			if (w != 0) {
				this.drawBox(xmin, ymin, xmax, ymin + w, node, depth);
				ymin += w;
			}
			box.xmin = xmin;
			box.ymin = ymin;
			box.xmax = xmax;
			box.ymax = ymax;
			return box;
		}
		return null;
	}

	public void drawBox(
		final float xmin,
		final float ymin,
		final float xmax,
		final float ymax,
		final int node,
		final int depth) {
		this.graphics.setColor(this.color.getColor(
			xmin,
			ymin,
			xmax,
			ymax,
			node,
			depth));
		final int xm = (int) xmin, xM = (int) xmax, ym = (int) ymin, yM =
			(int) ymax;
		this.graphics.fillRect(xm, ym, xM - xm, yM - ym);
	}

	public void endBox(
		final float xmin,
		final float ymin,
		final float xmax,
		final float ymax,
		final int node,
		final int depth) {
	}

	public void finish() {
	}

	/**
	 * Get the value of border.
	 * @return value of border.
	 */
	public BorderDrawer getBorder() {
		return this.border;
	}

	/**
	 * Get the value of color.
	 * @return value of color.
	 */
	public ColorDrawer getColor() {
		return this.color;
	}
	/**
	 * Get the value of graphics.
	 * @return value of graphics.
	 */
	public Graphics getGraphics() {
		return this.graphics;
	}

	public Box removeBorder(
		final float xmin,
		final float ymin,
		final float xmax,
		final float ymax,
		final int node,
		final int depth,
		final Box box) {
		if (this.border.beginBorder(xmin, ymin, xmax, ymax, node, depth)) {
			return this.border.remainingBox(
				xmin,
				ymin,
				xmax,
				ymax,
				node,
				depth,
				box);
		}
		return null;
	}
	/**
	 * Set the value of border.
	 * @param v  Value to assign to border.
	 */
	public void setBorder(final BorderDrawer v) {
		this.border = v;
	}
	/**
	 * Set the value of color.
	 * @param v  Value to assign to color.
	 */
	public void setColor(final ColorDrawer v) {
		this.color = v;
	}
	/**
	 * Set the value of graphics.
	 * @param v  Value to assign to graphics.
	 */
	public void setGraphics(final Graphics v) {
		this.graphics = v;
	}
	public void start() {
	}

}
