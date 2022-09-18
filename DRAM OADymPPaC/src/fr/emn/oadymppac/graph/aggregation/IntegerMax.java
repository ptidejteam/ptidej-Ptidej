package fr.emn.oadymppac.graph.aggregation;

/**
 * @author Mohammad Ghoniem
 * @version $Revision: 1.1 $
 *
 * This class encapsulates the max of an 
 * array of <code>Integer</code>s.
 */
public class IntegerMax implements AggregationFunction {

	/**
	 * @see fr.emn.oadymppac.graph.aggregation.AggregationFunction#compute(Object[])
	 */
	public Object compute(final Object[] args) {
		int max = Integer.MIN_VALUE;
		try {
			for (int i = 0; i < args.length; i++) {
				max = Math.max(max, ((Integer) args[i]).intValue());
			}
		}
		catch (final ClassCastException ex) {
			ex.printStackTrace();
		}
		return Integer.valueOf(max);
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
