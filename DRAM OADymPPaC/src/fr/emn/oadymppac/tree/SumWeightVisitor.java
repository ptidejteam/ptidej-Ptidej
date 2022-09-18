/*
 * $Id: SumWeightVisitor.java,v 1.1 2004/03/01 15:37:17 guehene Exp $
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

public class SumWeightVisitor implements TreeVisitor {
	AttributedTree tree;
	FloatAttributeVector weight;
	FloatAttributeVector sum_weight;

	public SumWeightVisitor(final AttributedTree tree, final String name) {
		this.tree = tree;
		this.weight = FloatAttributeVector.find(name, tree);
		this.sum_weight = FloatAttributeVector.find(name + "_sum", tree);
	}

	public void inorder(final int node) {
	}
	public void postorder(final int node) {
		float sum = this.weight.getAttribute(node);
		for (int child = this.tree.firstChild(node); child != Tree.NIL; child =
			this.tree.next(child)) {
			sum += this.sum_weight.getAttribute(child);
		}
		this.sum_weight.setAttribute(node, sum);
	}
	public void preorder(final int node) {
	}
}
