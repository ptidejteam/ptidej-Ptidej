package fr.emn.oadymppac.graph.aggregation;

/**
 * @author Mohammad Ghoniem
 * @version $Revision: 1.1 $
 *
 * This class encapsulates the sum of an 
 * array of <code>Integer</code>s.
 */
public class IntegerSum implements AggregationFunction {

	/**
	 * @see fr.emn.oadymppac.graph.aggregation.AggregationFunction#compute(Object[])
	 */
	public Object compute(final Object[] args) {
		int buffer = 0;
		try {
			for (int i = 0; i < args.length; i++) {
				buffer += ((Integer) args[i]).intValue();
			}
		}
		catch (final ClassCastException ex) {
			ex.printStackTrace();
		}
		return Integer.valueOf(buffer);
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
