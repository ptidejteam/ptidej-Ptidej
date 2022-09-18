/*
 * $Id: Squarified.java,v 1.2 2005/10/14 21:34:08 guehene Exp $
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

public class Squarified extends Treemap {
	static public class Orient {
		public boolean isHorizontal(
			final float xmin,
			final float ymin,
			final float xmax,
			final float ymax,
			final int n,
			final int depth) {
			return ymax - ymin > xmax - xmin;
		}
	}
	static public class StripOrient extends Orient {
		public boolean isHorizontal(
			final float xmin,
			final float ymin,
			final float xmax,
			final float ymax,
			final int n,
			final int depth) {
			return true;
		}
	}

	static final public Orient squarifiedOrient = new Orient();
	static final public Orient stripOrient = new StripOrient();
	Orient orient = Squarified.squarifiedOrient;

	public Squarified() {
	}
	public Squarified(final Drawer drawer) {
		super(drawer);
	}
	public Squarified(final Drawer drawer, final Tree tree) {
		super(drawer, tree);
	}
	public Squarified(
		final Drawer drawer,
		final Tree tree,
		final FloatAttributeVector weightMap,
		final FloatAttributeVector sumWeightMap) {
		super(drawer, tree, weightMap, sumWeightMap);
	}

	public int visit(
		float xmin,
		float ymin,
		float xmax,
		float ymax,
		final int n,
		final int depth) {
		if (!this.drawer.beginBox(xmin, ymin, xmax, ymax, n, depth)) {
			return 0;
		}
		int ret = 1;

		final Box b =
			this.drawer.drawBorder(xmin, ymin, xmax, ymax, n, depth, this.box);
		if (b != null) {
			xmin = b.xmin;
			ymin = b.ymin;
			xmax = b.xmax;
			ymax = b.ymax;
		}
		if (this.tree.isLeaf(n)) {
			this.drawer.drawBox(xmin, ymin, xmax, ymax, n, depth);
		}
		else {
			final float tw =
				1.0f / this.sumWeightMap.getAttribute(n)
						- this.weightMap.getAttribute(n);
			final float scale = (xmax - xmin) * (ymax - ymin) * tw;
			int child = this.tree.firstChild(n);

			if (scale == 0) {
				while (child != Tree.NIL) {
					ret += this.visit(xmin, ymin, xmax, ymax, child, depth + 1);
					child = this.tree.next(child);
				}
			}
			else {
				while (child != Tree.NIL) {
					//	if (orient.isHorizontal(xmin, ymin, xmax, ymax, n, depth)) {
					//	    float w = xmax - xmin;
					//	    float x = xmin;
					//	}
					//	else {
					//	}
				}
			}
		}
		this.drawer.endBox(xmin, ymin, xmax, ymax, n, depth);
		return ret;
	}
}
