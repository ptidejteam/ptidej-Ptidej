package fr.emn.oadymppac.graph.aggregation;

/**
 * @author Mohammad Ghoniem
 * @version $Revision: 1.1 $
 *
 * This class encapsulates the sum of an
 * array of <code>Double</code>s.
 */
public class DoubleSum implements AggregationFunction {

	/**
	 * @see fr.emn.oadymppac.graph.aggregation.AggregationFunction#compute(Object[])
	 */
	public Object compute(final Object[] args) {
		double buffer = 0;
		try {
			for (int i = 0; i < args.length; i++) {
				buffer += ((Double) args[i]).doubleValue();
			}
		}
		catch (final ClassCastException ex) {
			ex.printStackTrace();
		}
		return Double.valueOf(buffer);
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
