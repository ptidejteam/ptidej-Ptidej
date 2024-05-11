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
package ptidej.ui.layout.repository.sugiyama.graph.builders;

import ptidej.ui.layout.repository.sugiyama.graph.Edge;
import ptidej.ui.layout.repository.sugiyama.graph.EdgeSet;
import ptidej.ui.layout.repository.sugiyama.graph.INode;

/**
 * @author kahlamoh
 * @since 25/07/2006
 */
// TODO: This class does not enough or should be removed?
// TODO Should be a Singleton!
public class EdgeBuilder {
	private final EdgeSet edges;

	public EdgeBuilder() {
		this.edges = new EdgeSet();
	}
	public EdgeSet getEdgeSet() {
		return this.edges;
	}
	//	public void buildEdges(Graph aGraph) {
	//
	//		// for all the levels
	//		for (int i = 0; i < aGraph.getNbLevels(); i++) {
	//			
	//		}
	public void buildEdge(
		final INode aParent,
		final INode aChild,
		int aDirection) {

		this.edges.addEdge(new Edge(aDirection, null, aParent, aChild));
		// Node aParent, Node aChild, int aDirection
	}
}
