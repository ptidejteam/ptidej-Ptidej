/*
 * $Id: JTreemap.java,v 1.2 2006/08/11 23:11:03 guehene Exp $
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
import javax.swing.JComponent;
import fr.emn.oadymppac.tree.AttributedTree;
import fr.emn.oadymppac.tree.BorderDrawer;
import fr.emn.oadymppac.tree.ColorDrawer;
import fr.emn.oadymppac.tree.FloatAttributeVector;
import fr.emn.oadymppac.tree.GraphicsDrawer;
import fr.emn.oadymppac.tree.SliceAndDice;
import fr.emn.oadymppac.tree.Tree;
import fr.emn.oadymppac.tree.Treemap;

public class JTreemap extends JComponent {
	/**
	 * 
	 */
	private static final long serialVersionUID = 5591891586161234132L;
	AttributedTree tree;
	GraphicsDrawer drawer;
	Treemap treemap;

	public JTreemap(final AttributedTree tree, final String weight) {
		this.tree = tree;
		this.drawer = new GraphicsDrawer();
		this.treemap = new SliceAndDice(this.drawer, tree);
		this.treemap.setWeightMap(FloatAttributeVector.find(weight, tree));
		this.treemap
			.setSumWeightMap(FloatAttributeVector.findSum(weight, tree));
	}

	public JTreemap(
		final AttributedTree tree,
		final String weight,
		final ColorDrawer color,
		final BorderDrawer border) {
		this.drawer = new GraphicsDrawer(null, color, border);
		this.treemap = new SliceAndDice(this.drawer, tree);
		this.treemap.setWeightMap(FloatAttributeVector.find(weight, tree));
		this.treemap.setWeightMap(FloatAttributeVector.findSum(weight, tree));
	}

	public JTreemap(final Treemap treemap) {
		this.tree = (AttributedTree) treemap.getTree();
		this.drawer = new GraphicsDrawer();
		treemap.setDrawer(this.drawer);
	}

	protected void paintComponent(final Graphics g) {
		this.drawer.setGraphics(g);
		this.treemap.start();
		this.treemap.visit(
			0,
			0,
			this.getWidth(),
			this.getHeight(),
			Tree.ROOT,
			0);
		this.treemap.finish();
	}
}
