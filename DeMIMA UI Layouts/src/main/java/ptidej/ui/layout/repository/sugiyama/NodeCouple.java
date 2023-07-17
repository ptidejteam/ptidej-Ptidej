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

import ptidej.ui.layout.repository.sugiyama.graph.INode;

/**
 * @author: Yann
 * @since: 2013/06/04
 * 
 */
// TODO: What is this class for?
// TODO: Should this class really be here?
public class NodeCouple implements Comparable {
	private final INode node;
	private final double value;

	public NodeCouple(final INode aNode, final double aValue) {
		this.node = aNode;
		this.value = aValue;
	}
	public INode getNode() {
		return this.node;
	}
	public double getValue() {
		return this.value;
	}
	public int compareTo(final Object o) {
		// Yann 2014/04/11: New better implementation!
		//	if (this.value < ((NodeCouple) o).getValue())
		//		return -1;
		//	if (this.value > ((NodeCouple) o).getValue())
		//		return 1;
		//	return 0; // they are equal
		return Double.compare(this.value, ((NodeCouple) o).getValue());
	}
}
