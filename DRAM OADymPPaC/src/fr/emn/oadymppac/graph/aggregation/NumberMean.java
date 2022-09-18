package fr.emn.oadymppac.graph.aggregation;

/**
 * @author Mohammad Ghoniem
 * @version $Revision: 1.1 $
 *
 * Concrete class that computes the mean of an array of
 * <code>int</code>s, <code>float</code>s and <code>double</code>s.
 */
public class NumberMean extends NumberAggregationFunction {

	public final String PROPERTY_NAME = "Mean";

	/**
	 * @see fr.emn.oadymppac.graph.aggregation.NumberAggregationFunction#compute(double[])
	 */
	double compute(final double[] args) {
		double buffer = 0;
		for (int i = 0; i < args.length; i++) {
			buffer += args[i];
		}
		return buffer / args.length;
	}

	/**
	 * @see fr.emn.oadymppac.graph.aggregation.NumberAggregationFunction#compute(float[])
	 */
	float compute(final float[] args) {
		float buffer = 0;
		for (int i = 0; i < args.length; i++) {
			buffer += args[i];
		}
		return buffer / args.length;
	}

	/**
	 * @see fr.emn.oadymppac.graph.aggregation.NumberAggregationFunction#compute(int[])
	 */
	int compute(final int[] args) {
		int buffer = 0;
		for (int i = 0; i < args.length; i++) {
			buffer += args[i];
		}
		return buffer / args.length;
	}

	public String getFunctionDescription() {
		return this.PROPERTY_NAME;
	}
}
