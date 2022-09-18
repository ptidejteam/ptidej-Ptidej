package com.sdmetrics.test;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;

import com.sdmetrics.metrics.StronglyConnectedComponents;


/**
 * Graph of string nodes to test the strongly connected components. 
 */
public class Graph implements StronglyConnectedComponents.Graph<String> {

	/** Graph info, keys are the nodes, values are the set of neighbor nodes. */
	HashMap<String,HashSet<String>> graph=new HashMap<String,HashSet<String>>();

	/**
	 * Add a node without neighbors to the graph.
	 * @param node Node to add
	 */
	public void addIsolatedNode(String node) {
		graph.put(node, new HashSet<String>());
	}
	
	/** Add an edge to the graph.
	 * @param fromNode Source node, will be added if the graph has no such node yet
	 * @param toNode Target node of the edge
	 */
	public void addEdge(String fromNode, String toNode) {
		HashSet<String> neighbors=graph.get(fromNode);
		if(neighbors==null) {
			neighbors=new HashSet<String>();
			graph.put(fromNode, neighbors);
		}
		neighbors.add(toNode);
	}
	
	@Override
	public Collection<String> getNodes() {
		return graph.keySet();
	}

	@Override
	public Collection<String> getNeighbors(String node) {
		return graph.get(node);
	}
	
}