package fr.emn.oadymppac.graph.aggregation;

/**
 * @author Mohammad Ghoniem
 * @version $Revision: 1.1 $
 *
 * This class encapsulates the sum of an
 * array of <code>Float</code>s.
 */
public class FloatSum implements AggregationFunction {

	/**
	 * @see fr.emn.oadymppac.graph.aggregation.AggregationFunction#compute(Object[])
	 */
	public Object compute(final Object[] args) {
		float buffer = 0;
		try {
			for (int i = 0; i < args.length; i++) {
				buffer += ((Float) args[i]).floatValue();
			}
		}
		catch (final ClassCastException ex) {
			ex.printStackTrace();
		}
		return Float.valueOf(buffer);
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
