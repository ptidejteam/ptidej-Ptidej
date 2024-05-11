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
package ptidej.ui.layout.repository.sugiyama.graph;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import ptidej.ui.layout.repository.sugiyama.Parents;

/**
 * @author kahlamoh
 * @since 20/07/2006
 * contains all the nodes at this level
 */
// TODO: What are levels for? If it is to hold information
// about the level in which nodes belong to, why put them
// first in List of List (HierarchiesBuilder) then put them
// in levels?
public class Level {
	private final int levelIndex;
	private final List nodes;

	public Level(final List someNodes, int index) {
		this.nodes = someNodes;
		this.levelIndex = index;
	}
	public Level(final int index) {
		this.nodes = new ArrayList();
		this.levelIndex = index;
	}
	public void addNode(final INode aNode) {
		this.nodes.add(aNode);
	}
	public void removeNode(final INode aNode) {
		this.nodes.remove(aNode);
	}
	public List getChildren(final INode aParent) {
		final List children = new ArrayList();
		final Iterator itr = this.nodes.iterator();
		while (itr.hasNext()) {
			final INode next = (Node) itr.next();
			final Parents parents = next.getParents();
			if (parents.contains(aParent)) {
				children.add(next);
			}
		}
		return children;
	}
	public INode[] getTabChildren(final INode aParent) {
		final List children = this.getChildren(aParent);
		final INode[] tabChildren = new Node[children.size()];
		for (int i = 0; i < tabChildren.length; i++) {
			tabChildren[i] = (INode) children.get(i);
		}

		return tabChildren;
	}
	public List getNodes() {
		return this.nodes;
	}
	public INode[] getTabNodes() {
		final INode[] tabChildren = new Node[this.nodes.size()];
		for (int i = 0; i < tabChildren.length; i++) {
			tabChildren[i] = (INode) this.nodes.get(i);
		}

		return tabChildren;
	}
	public int getLevelIndex() {
		return this.levelIndex;
	}
}
