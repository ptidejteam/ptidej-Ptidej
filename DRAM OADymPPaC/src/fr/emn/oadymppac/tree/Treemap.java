/*
 * $Id: Treemap.java,v 1.1 2004/03/01 15:37:17 guehene Exp $
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

public abstract class Treemap {
	Drawer drawer;
	Tree tree;
	FloatAttributeVector weightMap;
	FloatAttributeVector sumWeightMap;
	protected Box box = new Box(); // reuse instead of allocating

	Treemap() {
		this(null, null, null, null);
	}
	public Treemap(final Drawer drawer) {
		this(drawer, null, null, null);
	}
	public Treemap(final Drawer drawer, final Tree tree) {
		this(drawer, tree, null, null);
	}
	public Treemap(
		final Drawer drawer,
		final Tree tree,
		final FloatAttributeVector weightMap,
		final FloatAttributeVector sumWeightMap) {
		this.drawer = drawer;
		this.tree = tree;
		this.weightMap = weightMap;
		this.sumWeightMap = sumWeightMap;
	}

	public void finish() {
		this.drawer.finish();
	}

	/**
	 * Get the value of drawer.
	 * @return value of drawer.
	 */
	public Drawer getDrawer() {
		return this.drawer;
	}

	/**
	 * Get the value of sumWeightMap.
	 * @return value of sumWeightMap.
	 */
	public FloatAttributeVector getSumWeightMap() {
		return this.sumWeightMap;
	}

	/**
	 * Get the value of tree.
	 * @return value of tree.
	 */
	public Tree getTree() {
		return this.tree;
	}

	/**
	 * Get the value of weightMap.
	 * @return value of weightMap.
	 */
	public FloatAttributeVector getWeightMap() {
		return this.weightMap;
	}

	/**
	 * Set the value of drawer.
	 * @param v  Value to assign to drawer.
	 */
	public void setDrawer(final Drawer v) {
		this.drawer = v;
	}

	/**
	 * Set the value of sumWeightMap.
	 * @param v  Value to assign to sumWeightMap.
	 */
	public void setSumWeightMap(final FloatAttributeVector v) {
		this.sumWeightMap = v;
	}

	/**
	 * Set the value of tree.
	 * @param v  Value to assign to tree.
	 */
	public void setTree(final Tree v) {
		this.tree = v;
	}

	/**
	 * Set the value of weightMap.
	 * @param v  Value to assign to weightMap.
	 */
	public void setWeightMap(final FloatAttributeVector v) {
		this.weightMap = v;
	}

	public void start() {
		this.drawer.start();
	}

	abstract public int visit(
		float xmin,
		float ymin,
		float xmax,
		float ymax,
		int n,
		int depth);
}
