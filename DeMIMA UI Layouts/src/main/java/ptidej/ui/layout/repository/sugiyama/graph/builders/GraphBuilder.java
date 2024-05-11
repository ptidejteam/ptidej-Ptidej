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

import java.util.Iterator;
import java.util.List;
import ptidej.ui.layout.repository.sugiyama.graph.Graph;
import ptidej.ui.layout.repository.sugiyama.graph.Node;

/**
 * @author kahlamoh
 */
// TODO: Is this class really needed? Why not create an instance
// of Graph right away in the "doLayout" method?
// TODO Should be a Singleton!
public class GraphBuilder {
	private List graphNodes;
	private int nbLevels;
	private Graph graph;

	public GraphBuilder(final List aGraphNodes, final int anNbLevels) {
		this.graphNodes = aGraphNodes;
		this.nbLevels = anNbLevels;
	}
	public void buildGraph() {
		this.graph = new Graph(this.nbLevels);
		final Iterator nodesItr = this.graphNodes.iterator();
		while (nodesItr.hasNext()) {
			Node next = (Node) nodesItr.next();
			this.graph.addNode(next);
		}
	}
	public Graph getGraph() {
		return this.graph;
	}
}
