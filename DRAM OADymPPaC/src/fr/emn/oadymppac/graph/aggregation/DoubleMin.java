package fr.emn.oadymppac.graph.aggregation;

/**
 * @author Mohammad Ghoniem
 * @version $Revision: 1.1 $
 *
 * This class encapsulates the min of an
 * array of <code>Double</code>s.
 */
public class DoubleMin implements AggregationFunction {

	/**
	 * @see fr.emn.oadymppac.graph.aggregation.AggregationFunction#compute(Object[])
	 */
	public Object compute(final Object[] args) {
		double min = Double.MAX_VALUE;
		try {
			for (int i = 0; i < args.length; i++) {
				min = Math.min(min, ((Double) args[i]).doubleValue());
			}
		}
		catch (final ClassCastException ex) {
			ex.printStackTrace();
		}
		return Double.valueOf(min);
	}

	/**
	 * @see fr.emn.oadymppac.graph.aggregation.AggregationFunction#getFunctionDescription()
	 */
	public String getFunctionDescription() {
		return this
			.getClass()
			.getName()
			.substring(this.getClass().getName().lastIndexOf("."));
	}
}
