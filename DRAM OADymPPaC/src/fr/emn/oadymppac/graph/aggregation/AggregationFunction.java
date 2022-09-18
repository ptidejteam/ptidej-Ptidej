package fr.emn.oadymppac.graph.aggregation;

/**
 * @author Mohammad Ghoniem
 * @version $Revision: 1.1 $
 *
 * This interface describes a general function
 * applying to an array of arguments and returns
 * a result.
 */
public interface AggregationFunction {
	public Object compute(Object[] args);
	public String getFunctionDescription();
}
