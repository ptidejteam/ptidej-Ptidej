/*
 * $Id: SliceAndDice.java,v 1.1 2004/03/01 15:37:17 guehene Exp $
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

public class SliceAndDice extends Treemap {
	public SliceAndDice() {
	}
	public SliceAndDice(final Drawer drawer) {
		super(drawer);
	}
	public SliceAndDice(final Drawer drawer, final Tree tree) {
		super(drawer, tree);
	}
	public SliceAndDice(
		final Drawer drawer,
		final Tree tree,
		final FloatAttributeVector weightMap,
		final FloatAttributeVector sumWeightMap) {
		super(drawer, tree, weightMap, sumWeightMap);
	}

	public int visit(
		final float xmin,
		final float ymin,
		final float xmax,
		final float ymax,
		final int n,
		final int depth) {
		final short dir =
			(depth & 1) == 0 ? Direction.LEFT_TO_RIGHT
					: Direction.TOP_TO_BOTTOM;
		return this.visit(dir, xmin, ymin, xmax, ymax, n, depth);
	}

	public int visit(
		final short dir,
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
		final boolean isLeaf = this.tree.isLeaf(n);

		final Box b =
			this.drawer.drawBorder(xmin, ymin, xmax, ymax, n, depth
					- (isLeaf ? 1 : 0), this.box);
		if (b != null) {
			xmin = b.xmin;
			ymin = b.ymin;
			xmax = b.xmax;
			ymax = b.ymax;
		}

		if (isLeaf) {
			this.drawer.drawBox(xmin, ymin, xmax, ymax, n, depth);
		}
		else {
			final float tw =
				1.0f / this.sumWeightMap.getAttribute(n)
						- this.weightMap.getAttribute(n);

			if (dir == Direction.LEFT_TO_RIGHT) {
				final float w = xmax - xmin;
				float x = xmin;

				for (int child = this.tree.firstChild(n); child != Tree.NIL; child =
					this.tree.next(child)) {
					final float nw =
						w * this.sumWeightMap.getAttribute(child) * tw;
					ret +=
						this.visit(
							Direction.flip(dir),
							x,
							ymin,
							x + nw,
							ymax,
							child,
							depth + 1);
					x += nw;
				}
			}
			else {
				final float h = ymax - ymin;
				float y = ymin;

				for (int child = this.tree.firstChild(n); child != Tree.NIL; child =
					this.tree.next(child)) {
					final float nh =
						h * this.sumWeightMap.getAttribute(child) * tw;
					ret +=
						this.visit(
							Direction.flip(dir),
							xmin,
							y,
							xmax,
							y + nh,
							child,
							depth + 1);
					y += nh;
				}
			}
		}
		this.drawer.endBox(xmin, ymin, xmax, ymax, n, depth);
		return ret;
	}
}
