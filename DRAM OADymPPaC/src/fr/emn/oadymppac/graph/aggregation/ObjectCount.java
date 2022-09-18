package fr.emn.oadymppac.graph.aggregation;

/**
 * @author Mohammad Ghoniem
 * @version $Revision: 1.1 $
 *
 * This class returns the count of the objects
 * in the passed array.
 */
public class ObjectCount implements AggregationFunction {

	public static final String PROPERTY_NAME = "Count";

	/**
	 * @see fr.emn.oadymppac.graph.aggregation.AggregationFunction#compute(Object[])
	 */
	public Object compute(final Object[] args) {
		return Integer.valueOf(args.length);
	}

	/**
	 * @see fr.emn.oadymppac.graph.aggregation.AggregationFunction#compute(Object[])
	 */
	public String getFunctionDescription() {
		return ObjectCount.PROPERTY_NAME;
	}

}
