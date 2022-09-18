package fr.emn.oadymppac.graph;

import salvo.jesus.graph.Edge;

/**
 * Towards InstrumentalJazz
 * @author Jean-Daniel Fekete
 * @date 10 sept. 2002
 */
public class DefaultEdgeWeightGetter implements EdgeWeightGetter {

	/**
	 * @see fr.emn.oadymppac.graph.EdgeWeightGetter#getWeight(Edge)
	 */
	public float getWeight(final Edge e) {
		if (e == null) {
			return 0;
		}
		return 1.0f;
	}

}
