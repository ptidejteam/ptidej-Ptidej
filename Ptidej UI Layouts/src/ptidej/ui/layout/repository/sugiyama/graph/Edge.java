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
import java.util.List;

/**
 * @author kahlamoh
 * this class are added to bypass the problem of the 
 * 1- the direction of an edge (because of the Sugiyama's algorithme, 
 * we must have an acyclic Digraph, so some edges are inversed for this perpose)
 * 2- some dummy nodes are added sometime and it's one part of an edge
 * so we could find them easly dy calling det dummiesNodes()
 *
 */
// TODO: What is the difference between an Edge and a Link?
public class Edge {
	// 1 for not inversed, -1 for inversed, 0 for not defined or applicable
	private int direction;
	private INode parent;
	private INode child;
	private List dummies;

	public Edge() {
		this.direction = 0;
		this.dummies = new ArrayList();
	}
	public Edge(int aDirection) {
		this.direction = aDirection;
	}
	public Edge(
		final int aDirection,
		final List aDumyList,
		final INode anOrigin,
		final INode aTarget) {

		this.direction = aDirection;
		this.dummies = aDumyList;
		this.parent = anOrigin;
		this.child = aTarget;
	}
	public void setDirection(final int aDirection) {
		this.direction = aDirection;
	}
	public int getDirection() {
		return this.direction;
	}
	public INode getOrigin() {
		return this.parent;
	}
	public INode getTArget() {
		return this.child;
	}
	public void setDummyList(final List aList) {
		this.dummies = aList;
	}
	public List getDummiesNodes() {
		return this.dummies;
	}
}
