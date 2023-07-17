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
package ptidej.ui.layout.repository.sugiyama;

import java.util.ArrayList;
import java.util.List;
import ptidej.ui.layout.repository.sugiyama.graph.INode;

/**
 * @author kahlamoh
 * @since 24/07/2006
 * This is like a list but it adds
 * more functions and flexibilities!
 * 
 *  It's homogene too!
 *
 */
// TODO: Is this class really needed, with respect to Couple, Node, and Level?
public class Children {
	private final List children;

	public Children() {
		this.children = new ArrayList();
	}
	public void add(final INode aChild) {
		this.children.add(aChild);
	}
	public boolean remove(final INode aChild) {
		return this.children.remove(aChild);
	}
	public boolean isEmpty() {
		return this.children.isEmpty();
	}
	public void clear() {
		this.children.clear();
	}
	public boolean contains(final INode aChild) {
		return this.children.contains(aChild);
	}
	public INode[] getChildren() {
		// TODO Use proper method provided by class library
		final INode[] tabChildren = new INode[this.children.size()];
		for (int i = 0; i < tabChildren.length; i++) {
			tabChildren[i] = (INode) this.children.get(i);
		}

		return tabChildren;
	}
	public int getNbChildren() {
		return this.children.size();
	}
}
