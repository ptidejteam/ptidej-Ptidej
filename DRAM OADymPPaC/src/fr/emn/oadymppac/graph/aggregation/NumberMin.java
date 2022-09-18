package fr.emn.oadymppac.graph.aggregation;

/**
 * @author Mohammad Ghoniem
 * @version $Revision: 1.1 $
 *
 * Concrete class that computes the min of an array of
 * <code>int</code>s, <code>float</code>s and <code>double</code>s.
 */
public class NumberMin extends NumberAggregationFunction {

	public final String PROPERTY_NAME = "Min";

	/**
	 * @see fr.emn.oadymppac.graph.aggregation.NumberAggregationFunction#compute(double[])
	 */
	double compute(final double[] args) {
		double min = Double.MAX_VALUE;
		for (int i = 0; i < args.length; i++) {
			min = Math.min(min, args[i]);
		}
		return min;
	}

	/**
	 * @see fr.emn.oadymppac.graph.aggregation.NumberAggregationFunction#compute(float[])
	 */
	float compute(final float[] args) {
		float min = Float.MAX_VALUE;
		for (int i = 0; i < args.length; i++) {
			min = Math.min(min, args[i]);
		}
		return min;
	}

	/**
	 * @see fr.emn.oadymppac.graph.NumberAggregationFunction#compute(int[])
	 */
	int compute(final int[] args) {
		int min = Integer.MAX_VALUE;
		for (int i = 0; i < args.length; i++) {
			min = Math.min(min, args[i]);
		}
		return min;
	}

	public String getFunctionDescription() {
		return this.PROPERTY_NAME;
	}
}
