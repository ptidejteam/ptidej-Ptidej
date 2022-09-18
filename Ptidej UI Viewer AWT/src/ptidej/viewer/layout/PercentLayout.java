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
package ptidej.viewer.layout;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.LayoutManager2;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import util.multilingual.MultilingualManager;

/**
 * Lays out components within a IContainer such that each component takes a fixed percentage of the size.
 * 
 * Each Component added to the IContainer must have a Constraint object that specifies what proportion 
 * of the container it will fill. The Component will be stretched to fill exactly that percentage.
 * 
 * @see Constraint
 */
public class PercentLayout implements LayoutManager2 {
	private Map mapOfConstraints = new HashMap();

	public float getLayoutAlignmentX(Container p1) {
		return 0.5f;
	}
	public float getLayoutAlignmentY(Container p1) {
		return 0.5f;
	}
	public void addLayoutComponent(Component component, Object constraint) {
		if (constraint instanceof Constraint) {
			this.mapOfConstraints.put(component, constraint);
		}
		else {
			throw new IllegalArgumentException(MultilingualManager.getString(
				"IAException_INVALID_CONSTRAINT",
				PercentLayout.class));
		}
	}
	public void addLayoutComponent(String constraint, Component comp) {
		throw new IllegalArgumentException(MultilingualManager.getString(
			"IAException_INVALID_CONSTRAINT",
			PercentLayout.class));
	}
	public void invalidateLayout(Container p1) {
	}
	public void layoutContainer(final Container p1) {
		final Dimension size = p1.getSize();
		final Insets insets = p1.getInsets();
		final Iterator keys = this.mapOfConstraints.keySet().iterator();
		while (keys.hasNext()) {
			// Yann 2003/06/06: Min width and height!
			// I must compute the bound of the components
			// for which the minWidth and minHeight value
			// are different from zero!
			Component comp = (Component) keys.next();
			Constraint constraint =
				(Constraint) this.mapOfConstraints.get(comp);
			// if (constraint.minWidth != 0 || constraint.minHeight != 0) {
			int x = (int) (size.width * constraint.x / 100);
			int y = (int) (size.height * constraint.y / 100) + insets.top;
			int width =
				Math.max(
					(int) (size.width * constraint.width / 100),
					(int) constraint.minWidth);
			int height =
				Math.max(
					(int) (size.height * constraint.height / 100)
							- (insets.top + insets.bottom),
					(int) constraint.minHeight);
			comp.setBounds(x, y, width, height);
		}

		// TODO: I should also compensate the position and width by
		// insets.left and insets.right
	}
	public Dimension maximumLayoutSize(Container p1) {
		int maxx = Integer.MAX_VALUE;
		int maxy = Integer.MAX_VALUE;
		final Iterator keys = this.mapOfConstraints.keySet().iterator();
		while (keys.hasNext()) {
			Component comp = (Component) keys.next();
			Constraint constraint =
				(Constraint) this.mapOfConstraints.get(comp);
			Dimension max = comp.getMaximumSize();
			int mx =
				max.width == Integer.MAX_VALUE ? max.width : Math.max(
					(int) (max.width * 100 / constraint.width),
					(int) constraint.minWidth);
			int my =
				max.height == Integer.MAX_VALUE ? max.height : Math.max(
					(int) (max.height * 100 / constraint.height),
					(int) constraint.minHeight);
			if (mx < maxx) {
				maxx = mx;
			}

			if (my < maxy) {
				maxy = my;
			}
		}

		return new Dimension(maxx, maxy);
	}
	public Dimension minimumLayoutSize(Container p1) {
		int minx = 0;
		int miny = 0;
		final Iterator keys = this.mapOfConstraints.keySet().iterator();
		while (keys.hasNext()) {
			Component comp = (Component) keys.next();
			Constraint constraint =
				(Constraint) this.mapOfConstraints.get(comp);
			Dimension min = comp.getMinimumSize();
			int mx =
				Math.max(
					(int) (min.width * 100 / constraint.width),
					(int) constraint.minWidth);
			int my =
				Math.max(
					(int) (min.height * 100 / constraint.height),
					(int) constraint.minHeight);
			if (mx > minx) {
				minx = mx;
			}

			if (my > miny) {
				miny = my;
			}
		}

		return new Dimension(minx, miny);
	}
	public Dimension preferredLayoutSize(Container p1) {
		int prefx = 0;
		int prefy = 0;
		final Iterator keys = this.mapOfConstraints.keySet().iterator();
		while (keys.hasNext()) {
			Component comp = (Component) keys.next();
			Constraint constraint =
				(Constraint) this.mapOfConstraints.get(comp);
			Dimension pref = comp.getPreferredSize();
			prefx +=
				Math.max(
					pref.width * 100 / constraint.width,
					(int) constraint.minWidth);
			prefy +=
				Math.max(
					pref.height * 100 / constraint.height,
					(int) constraint.minHeight);
		}

		int n = this.mapOfConstraints.size();
		return new Dimension(prefx / n, prefy / n);
	}
	public void removeLayoutComponent(Component component) {
		this.mapOfConstraints.remove(component);
	}

	public static class Constraint {
		private final double x;
		private final double y;
		private final double width;
		private final double height;
		private final double minWidth;
		private final double minHeight;

		/**
		 * Creates a Constraint Object. 
		 * @param x The X position of the top left corner of the component (0-100)
		 * @param y The Y position of the top left corner of the component (0-100)
		 * @param width The percentage width of the component (0-100)
		 * @param height The percentage height of the component (0-100)
		 */
		public Constraint(double x, double y, double width, double height) {
			this.x = x;
			this.y = y;
			this.height = height;
			this.width = width;
			this.minHeight = 0;
			this.minWidth = 0;
		}

		/**
		 * Creates a Constraint Object. 
		 * @param x The X position of the top left corner of the component (0-100)
		 * @param y The Y position of the top left corner of the component (0-100)
		 * @param width The percentage width of the component (0-100)
		 * @param height The percentage height of the component (0-100)
		 */
		public Constraint(
			double x,
			double y,
			double width,
			double height,
			double minWidth,
			double minHeight) {

			this.x = x;
			this.y = y;
			this.height = height;
			this.width = width;
			this.minHeight = minHeight;
			this.minWidth = minWidth;
		}
	}
}
