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

import ptidej.ui.kernel.IntermediaryPoint;

/**
 * @author Mohamed Kahla  
 * @since  29/05/2006
 * 
 * It's a dummy node needed for splitting long span adges
 * a dummy node is used to split a long span edge 
 * connecting two node seperated by a level
 * n dummies nodes are inserted between two nodes
 * if they are separated by n level
 */
public class DummyNode extends Node {
	private final IntermediaryPoint node;

	/**
	 * @param : the level's number
	 * @param : the nodes at the upper level
	 * @param : the nodes at the level under the first one
	 */
	public DummyNode(final int aLevel, final INode up, final INode down) {
		super(null, aLevel);
		this.addParent(up);
		this.addChild(down);
		this.node = new IntermediaryPoint();
	}
	public void setPosition(final int x, final int y) {
		this.node.setPosition(x, y);
	}
	public int getX() {
		return this.node.getX();
	}
	public int getY() {
		return this.node.getY();
	}
}
