package fr.emn.oadymppac.graph;

import salvo.jesus.graph.Edge;

/**
 * Towards InstrumentalJazz
 * @author Jean-Daniel Fekete
 * @date 10 sept. 2002
 */
public class LogEdgeWeightGetter implements EdgeWeightGetter {
	EdgeWeightGetter getter;
	final static float log2 = (float) Math.log(2);

	public LogEdgeWeightGetter(final EdgeWeightGetter g) {
		this.getter = g;
	}
	/**
	 * @see fr.emn.oadymppac.graph.EdgeWeightGetter#getWeight(Edge)
	 */
	public float getWeight(final Edge e) {
		return (float) Math.log(1 + this.getter.getWeight(e))
				/ LogEdgeWeightGetter.log2;
	}

}
