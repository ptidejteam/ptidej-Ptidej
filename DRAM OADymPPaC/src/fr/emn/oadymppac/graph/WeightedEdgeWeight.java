package fr.emn.oadymppac.graph;

import salvo.jesus.graph.Edge;
import salvo.jesus.graph.WeightedEdge;

/**
 * Towards InstrumentalJazz
 * @author Jean-Daniel Fekete
 * @date 10 sept. 2002
 */
public class WeightedEdgeWeight implements EdgeWeightGetter {
	float maxWeight;

	public WeightedEdgeWeight(final float max) {
		this.maxWeight = max;
	}
	/**
	 * Returns the maxWeight.
	 * @return float
	 */
	public float getMaxWeight() {
		return this.maxWeight;
	}

	/**
	 * @see fr.emn.oadymppac.graph.EdgeWeightGetter#getWeight(Edge)
	 */
	public float getWeight(final Edge e) {
		return (float) ((WeightedEdge) e).getWeight() / this.maxWeight;
	}

	/**
	 * Sets the maxWeight.
	 * @param maxWeight The maxWeight to set
	 */
	public void setMaxWeight(final float maxWeight) {
		this.maxWeight = maxWeight;
	}

}
