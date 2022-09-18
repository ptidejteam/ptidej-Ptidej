/*
 * $Id: AttributedTreePrinter.java,v 1.1 2004/03/01 15:37:17 guehene Exp $
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

public class AttributedTreePrinter implements TreeVisitor {
	AttributedTree tree;
	int indent;

	public AttributedTreePrinter(final AttributedTree tree) {
		this.tree = tree;
	}

	protected void indent() {
		for (int i = 0; i < this.indent; i++) {
			System.out.print(" ");
		}
	}
	public void inorder(final int node) {
	}
	public void postorder(final int node) {
		this.indent -= 2;
		this.indent();
		System.out.println("</node><!-- n='" + node + "' -->");
	}
	public void preorder(final int node) {
		this.indent();
		System.out.println("<node n='" + node + "'");
		this.indent++;
		for (int i = 0; i < this.tree.getAttributeCount(); i++) {
			final AttributeVector v = this.tree.getAttributeVector(i);
			this.indent();
			System.out.println(v.getName() + "='" + v.getValue(node) + "'");
		}
		this.indent();
		System.out.println(">");
		this.indent++;
	}
}
