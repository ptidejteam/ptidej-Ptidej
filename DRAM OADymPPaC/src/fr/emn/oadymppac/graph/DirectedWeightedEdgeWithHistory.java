/*
 * Created on 2005-04-19
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package fr.emn.oadymppac.graph;

import salvo.jesus.graph.DirectedWeightedEdge;
import salvo.jesus.graph.Vertex;

/**
 * @author rachedsa
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class DirectedWeightedEdgeWithHistory extends DirectedEdgeWithHistory
		implements DirectedWeightedEdge {

	/**
	 * 
	 */
	private static final long serialVersionUID = -781136794878834932L;
	private double weight;
	public DirectedWeightedEdgeWithHistory(
		final Vertex sourceVertex,
		final Vertex sinkVertex,
		final double weight) {
		super(sourceVertex, sinkVertex);
		this.weight = weight;
	}
	public double getWeight() {
		return this.weight;
	}

	public void setWeight(final double weight) {
		this.weight = weight;
	}

}
