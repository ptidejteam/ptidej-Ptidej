package fr.emn.oadymppac.graph.aggregation;

/**
 * @author Mohammad Ghoniem
 * @version $Revision: 1.1 $
 *
 * Concrete class that computes the max of an array of
 * <code>int</code>s, <code>float</code>s and <code>double</code>s.
 */
public class NumberMax extends NumberAggregationFunction {

	public final String PROPERTY_NAME = "Max";

	/**
	 * @see fr.emn.oadymppac.graph.aggregation.NumberAggregationFunction#compute(double[])
	 */
	double compute(final double[] args) {
		double max = Double.MIN_VALUE;
		for (int i = 0; i < args.length; i++) {
			max = Math.max(max, args[i]);
		}
		return max;
	}

	/**
	 * @see fr.emn.oadymppac.graph.aggregation.NumberAggregationFunction#compute(float[])
	 */
	float compute(final float[] args) {
		float max = Float.MIN_VALUE;
		for (int i = 0; i < args.length; i++) {
			max = Math.max(max, args[i]);
		}
		return max;
	}

	/**
	 * @see fr.emn.oadymppac.graph.aggregation.NumberAggregationFunction#compute(int[])
	 */
	int compute(final int[] args) {
		int max = Integer.MIN_VALUE;
		for (int i = 0; i < args.length; i++) {
			max = Math.max(max, args[i]);
		}
		return max;
	}

	public String getFunctionDescription() {
		return this.PROPERTY_NAME;
	}
}
